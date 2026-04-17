package com.example;

public class App { 
    
    // Static variables to store command-line arguments (n and t)
    public static int maxAttempts = 3; 
    public static int blockTimeSeconds = 10; 
    
    public static void main(String[] args) { 
        // Checks if arguments were passed during execution
        if (args.length >= 2) {
            try {
                maxAttempts = Integer.parseInt(args[0]);
                blockTimeSeconds = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid arguments. Using default settings.");
            }
        }
        
        // Starts the JavaFX application cycle
        Launcher.main(args); 
    } 
}