package com.example.decafe.util;

import javafx.scene.image.Image;

import java.util.Objects;

public class ImageUtil {

    // Method used to create an Image Object
    public static Image getImageFromResources(String fileName) {

        return new Image(Objects.requireNonNull(ImageUtil.class
                .getResourceAsStream("/com/example/decafe/" + fileName)));
    }
}
