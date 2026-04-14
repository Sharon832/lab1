package com.example; 
// Declare the package where this class is located

// Importing necessary JavaFX classes for building the graphical user interface
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application 
{ 
    // Define the Launcher class,which inherit from the JavaFX Application class
    
    @Override 
    // Indicates that we are overriding the start method from the parent Application class
    public void start(Stage primaryStage) throws Exception 
    { 
        // The main entry for all JavaFX applications, receiving the primary window (Stage)
        
        // Loads the login.fxml file from the resources folder 
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml")); 
        // Loads the FXML layout file and assigns it to the root node of the scene
        
        primaryStage.setTitle("Users Login"); 
        // Sets the title of the application window
        
        primaryStage.setScene(new Scene(root)); 
        // Creates a new Scene with the loaded layout and attaches it to the primary stage (window)
        
        // Configures the application to terminate completely when the window   is closed 
        
        primaryStage.setOnCloseRequest(e -> System.exit(0)); 
        // Sets an event handler to exit the program (status 0) when the close button (X) is clicked
        
        primaryStage.show();
         // Render(translate code to visual) and displays the window on the screen
    }

    public static void main(String[] args) 
    { 
        launch(args); 
        // Calls the internal JavaFX launch method to start the application cycle
    }
}