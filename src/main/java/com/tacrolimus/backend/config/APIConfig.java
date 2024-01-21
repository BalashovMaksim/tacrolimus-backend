package com.tacrolimus.backend.config;

import io.github.cdimascio.dotenv.Dotenv;

public class APIConfig {
    public static void loadEnv(){
        Dotenv dotenv = Dotenv.configure().load();

        String postgresUser = dotenv.get("POSTGRES_USER");
        String postgresPassword = dotenv.get("POSTGRES_PASSWORD");
        String postgresUrl = dotenv.get("POSTGRES_URL");
        String destinationPath = dotenv.get("DESTINATION_FILE_PATH");

        System.setProperty("POSTGRES_USER", postgresUser);
        System.setProperty("POSTGRES_PASSWORD", postgresPassword);
        System.setProperty("POSTGRES_URL", postgresUrl);
        System.setProperty("DESTINATION_FILE_PATH",destinationPath);
    }
}
