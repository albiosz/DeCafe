package com.example.decafe.util;

import com.example.decafe.exception.ImageNotFoundException;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageUtil {

    private static final String DEFAULT_BASE_PATH = "/com/example/decafe/";

    private ImageUtil() {
    }

    // Method used to create an Image Object
    public static Image getImageFromResources(String filename) throws ImageNotFoundException {
        InputStream imageStream = ImageUtil.class.getResourceAsStream(DEFAULT_BASE_PATH + filename);

        if (imageStream == null) {
            throw new ImageNotFoundException("Image file not found: " + filename);
        }

        // Convert stream to Image and return it
        return new Image(imageStream);
    }

        // Method used to create an Image Object
    public static Image create(String filename) throws FileNotFoundException {
        File f = new File(""); // Get filepath of project
        // Get path to certain Image
        String filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath); // Convert path into stream
        return new Image(stream); // Convert stream to Image and return it
    }
}
