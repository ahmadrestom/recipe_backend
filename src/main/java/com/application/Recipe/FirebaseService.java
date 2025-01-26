package com.application.Recipe;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import jakarta.annotation.PostConstruct;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseService{

    @PostConstruct
    public void init() throws IOException {
        // Initialize Firebase Admin SDK with the service account
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/reicpe-application-firebase-adminsdk-vxh9d-aaed196116.json");
        FirebaseOptions options =  FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
    }

    public void sendNotification(String token, String title, String body) throws Exception {
        // Build the notification using the builder pattern
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        // Create the message
        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        // Send the message
        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);
    }

}
