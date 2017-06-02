package com.example.asd.iamhere;

public interface FirebaseUserAuthenticator {
    void registerUser(String email, String password);
    void loginWithCredentials(String email, String password);
    String getLoggedUser();
}
