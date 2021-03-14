package com.cos.blog.test;

public class Member {
    
    private int id;
    private String username;
    private String password;
    private String state;
    private String email;
    
    
    public Member(int id, String username, String password, String state, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.state = state;
        this.email = email;
    }
    

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    
}
