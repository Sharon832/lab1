package com.example; 

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginController { 

    @FXML private AnchorPane loginScreen; 
    @FXML private Label notext; 
    @FXML private PasswordField password; 
    @FXML private TextField username; 

    private ArrayList<User> validUsers = new ArrayList<>();

    @FXML
    public void initialize() {
        // Load users from the text file in resources
        try (InputStream is = getClass().getResourceAsStream("/users.txt"); 
             Scanner scanner = new Scanner(is)) { 
            while (scanner.hasNextLine()) { 
                String[] parts = scanner.nextLine().split("\\s+"); 
                if (parts.length >= 2) { 
                    try { 
                        validUsers.add(new User(parts[0], parts[1])); 
                    } catch (Exception e) {
                        // Skip invalid user entries from the file
                    }
                }
            }
        } catch (Exception e) { 
            notext.setText("Error: Could not load users.txt from resources"); 
            notext.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML 
    void onHelloButtonClick(ActionEvent event) { 
        String inputUser = username.getText(); 
        String inputPass = password.getText(); 

        User tempUser = null;
        for (User u : validUsers) { 
            if (u.getUsername().equals(inputUser)) { 
                tempUser = u;
                break; 
            }
        }

        if (tempUser == null) { 
            notext.setText("User does not exist in the system"); 
            notext.setStyle("-fx-text-fill: red;"); 
            return;
        }

        final User currentUser = tempUser;

        if (currentUser.getPassword().equals(inputPass)) { 
            // Requirement 2b: Thread to check if user is blocked upon correct login
            Thread checkBlockThread = new Thread(() -> {
                synchronized (currentUser) {
                    if (currentUser.isBlocked()) {
                        Platform.runLater(() -> {
                            notext.setText("The account is temporarily blocked. Please wait.");
                            notext.setStyle("-fx-text-fill: red;");
                        });
                    } else {
                        currentUser.setFailedAttempts(0); 
                        Platform.runLater(this::switchToWelcomeScene);
                    }
                }
            });
            checkBlockThread.start();
            
        } else { 
            // Requirement 2a: Thread for updating failed attempts and blocking
            Thread updateAttemptsThread = new Thread(() -> {
                synchronized (currentUser) {
                    if (currentUser.isBlocked()) {
                        Platform.runLater(() -> {
                            notext.setText("The account is temporarily blocked. Please wait.");
                            notext.setStyle("-fx-text-fill: red;");
                        });
                        return;
                    }

                    int attempts = currentUser.getFailedAttempts() + 1;
                    currentUser.setFailedAttempts(attempts);

                    // If max attempts reached, block user and save block time
                    if (attempts >= App.maxAttempts) {
                        currentUser.setBlocked(true);
                        currentUser.setBlockTime(System.currentTimeMillis()); // Save blocking time
                        
                        Platform.runLater(() -> {
                            notext.setText("Blocked! Incorrect password entered " + App.maxAttempts + " times.");
                            notext.setStyle("-fx-text-fill: red;");
                        });

                        // Wait for 't' seconds as defined in command line arguments
                        try { 
                            Thread.sleep(App.blockTimeSeconds * 1000L); 
                        } catch (InterruptedException e) { 
                            e.printStackTrace(); 
                        }

                        // Release block after waiting
                        currentUser.setBlocked(false);
                        currentUser.setFailedAttempts(0);
                        
                        Platform.runLater(() -> {
                            notext.setText("Block released. You have " + App.maxAttempts + " attempts again.");
                            notext.setStyle("-fx-text-fill: green;");
                        });

                    } else {
                        int triesLeft = App.maxAttempts - attempts;
                        Platform.runLater(() -> {
                            notext.setText("Wrong password. You have " + triesLeft + " attempts left.");
                            notext.setStyle("-fx-text-fill: red;");
                        });
                    }
                }
            });
            updateAttemptsThread.start();
        }
    }

    private void switchToWelcomeScene() { 
        try { 
            AnchorPane root = FXMLLoader.load(getClass().getResource("/welcome.fxml")); 
            Stage stage = (Stage) loginScreen.getScene().getWindow(); 
            stage.setScene(new Scene(root)); 
            stage.setTitle("Welcome"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }
}