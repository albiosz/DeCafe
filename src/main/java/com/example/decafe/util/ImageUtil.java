package com.example.decafe.util;

import com.example.decafe.exception.ImageNotFoundException;
import javafx.scene.image.Image;

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
}
