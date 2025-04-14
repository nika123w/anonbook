package com.anonbook.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class MigrationFileGenerator {
    public static void generateMigrationFile() {
        String path = "src/main/resources/db/migration/V1__init.sql";
        File file = new File(path);

        if (file.exists()) return;

        String sql = """
            CREATE TABLE posts (
                id SERIAL PRIMARY KEY,
                content TEXT NOT NULL,
                photo_path TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            CREATE TABLE comments (
                id SERIAL PRIMARY KEY,
                post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
                content TEXT NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """;

        try {
            Files.createDirectories(file.getParentFile().toPath());
            FileWriter writer = new FileWriter(file);
            writer.write(sql);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
