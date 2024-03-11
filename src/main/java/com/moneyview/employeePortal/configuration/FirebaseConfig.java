package com.moneyview.employeePortal.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp firebaseApp() {
        try {
            InputStream serviceAccount = FirebaseConfig.class.getClassLoader().getResourceAsStream("employee-portal-firebase.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            return FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
