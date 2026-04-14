package com.example; 
// Declares the package where this class is located

// Importing necessary classes for JavaFX UI, Event handling, File I/O, and Lists
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
    // Define the Controller class that manages the login.fxml UI

    // @FXML tags link the visual elements from SceneBuilder to these Java variables
    @FXML private AnchorPane loginScreen; // The main layout container of the login screen
    @FXML private Label notext; // The label used to display error messages to the user
    @FXML private PasswordField password; // The input field where the user types their password
    @FXML private TextField username; // The input field where the user types their username

    @FXML // Indicates that this method is linked to an action in the FXML (the login button)
    void onHelloButtonClick(ActionEvent event)
     { 
        // Triggered when the user clicks the Login button
        
        String inputUser = username.getText(); // Retrieves the text entered in the username field
        String inputPass = password.getText(); // Retrieves the text entered in the password field

        // 1. Initial filtering step: using the validation logic from Lab 1
        try 
        { 
            // Starts a block to catch any validation errors thrown by the User constructor
            
            // Attempt to create a temporary user to test if the input passes the Regex rules
            User validationCheck = new User(inputUser, inputPass); 
            
        } 
        catch (Exception e) 
        { 
            // If the constructor throws an error ( invalid email, short password)
            
            notext.setText(e.getMessage()); // Displays the exact error message from the User class
            notext.setStyle("-fx-text-fill: red;"); // Changes the error message text color to red
            return; // Stops further execution so it won't read the file if input is invalid
        }

        // 2. Only if the input is valid (passed Regex), we proceed to read the text file
        ArrayList<User> validUsers = new ArrayList<>(); 
        // Creates a list to store valid users from the file

        // Opens the users.txt file from the resources folder securely
        try (InputStream is = getClass().getResourceAsStream("/users.txt"); 
             Scanner scanner = new Scanner(is)) 
             { 
                // Uses a Scanner to read the file line by line
            
              while (scanner.hasNextLine()) 
                { // Loops as long as there is another line to read
                String[] parts = scanner.nextLine().split("\\s+"); // Splits the line into words by spaces
                
                if (parts.length >= 2) 
                    { // Ensures the line has at least a username and a password
                    try 
                    { 
                        // Creates a new User object and adds it to our valid users list
                        validUsers.add(new User(parts[0], parts[1])); 
                    } 
                    catch (Exception e)
                    { 
                        /* Skip not valid lines in the file itself without crashing the program */ 
                    }
                }
            }
        } 
        catch (Exception e) 
        { // Catches errors related to file reading (file not found)
            notext.setText("Error: Could not load users.txt from resources"); // Shows file error
            return; // Stops execution
        }

        // 3. Match check: Verify if the typed user exists in the system
        boolean found = false; // A flag to track if we found a matching user
        
        for (User u : validUsers) 
            { // Loops through all the loaded users from the text file
            // Checks if both the username and password match exactly
            if (u.getUsername().equals(inputUser) && u.getPassword().equals(inputPass)) 
            { 
                found = true; // Match found! Sets the flag to true
                break; // Exits the loop early to save time
            }
        }

        if (found) 
        { // If the authentication was successful
            switchToWelcomeScene(); // Calls the helper method to change the screen
        } 
        else 
        { 
            // If the format was valid, but the user wasn't found in the text file
            notext.setText("user or password do not match"); // Displays login failure message
            notext.setStyle("-fx-text-fill: red;"); // Sets the text color to red
        }
    }

    // Helper method to transition to the Welcome screen
    private void switchToWelcomeScene() 
    { 
        try 
        { // Starts a block to handle potential FXML loading errors
            
            // Loads the design of the welcome screen from the resources folder
            AnchorPane root = FXMLLoader.load(getClass().getResource("/welcome.fxml")); 
            
            // Gets the current active window (Stage) using the existing login screen layout
            Stage stage = (Stage) loginScreen.getScene().getWindow(); 
            
            stage.setScene(new Scene(root)); // Replaces the old scene with the new welcome scene
            stage.setTitle("Welcome"); // Changes the title at the top of the window to "Welcome"
            
        } 
        catch (Exception e)
        { // Catches errors like a missing welcome.fxml file
            e.printStackTrace(); // Prints the error details to the console for debugging
        }
    }
}