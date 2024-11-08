package com.example.decafe;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileUtil {

    // Method used to create an Image Object
    public static Image getImage(String fileName) throws FileNotFoundException {

        // Get filepath of project
        File f = new File("");

        // Get path to certain Image
        String filePath = String.join(File.separator, f.getAbsolutePath(),
                "src", "main", "resources", "com", "example", "decafe", fileName);

        // Convert path into stream
        InputStream stream = new FileInputStream(filePath);

        // Convert stream to Image and return it
        return new Image(stream);
    }
}
