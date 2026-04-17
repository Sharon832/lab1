package com.example; 

public class User { 
    private String username;
    private String password; 
    
    private int failedAttempts;
    private boolean isBlocked;
    private long blockTime; // New field to store the time of blocking

    public User(String username, String password) throws Exception { 
        if (username.length() > 50) {
            throw new Exception("Username is too long, try something shorter");
        }
        
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";       
        if (!username.matches(emailRegex)) { 
            throw new Exception("Please enter a valid Email as username"); 
        } 

        if (password.length() < 8) { 
            throw new Exception("Your password is too short, add more characters");
        } 

        if (password.length() > 12) { 
            throw new Exception("Your password is too long, try a shorter one"); 
        } 

        String passRegex = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,16}$";
        if (!password.matches(passRegex)) { 
            throw new Exception("Please enter a valid password"); 
        } 

        this.username = username; 
        this.password = password; 
        this.failedAttempts = 0;
        this.isBlocked = false;
        this.blockTime = 0; // Initialize blockTime to 0
    } 

    public String getUsername() { return this.username; } 
    public String getPassword() { return this.password; }    

    public synchronized int getFailedAttempts() { return failedAttempts; }
    public synchronized void setFailedAttempts(int failedAttempts) { this.failedAttempts = failedAttempts; }
    
    public synchronized boolean isBlocked() { return isBlocked; }
    public synchronized void setBlocked(boolean blocked) { this.isBlocked = blocked; }

    // Synchronized methods to get and set the blocking time
    public synchronized long getBlockTime() { return blockTime; }
    public synchronized void setBlockTime(long blockTime) { this.blockTime = blockTime; }

    @Override 
    public String toString() { return this.username + " " + this.password; } 
}