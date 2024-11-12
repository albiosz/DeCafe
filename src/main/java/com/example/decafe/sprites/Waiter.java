package com.example.decafe.sprites;

import com.example.decafe.exception.ImageNotFoundException;
import com.example.decafe.util.ImageUtil; 

import javafx.scene.image.ImageView;

public class Waiter {

  private static class Product {
    public static final String COFFEE = "coffee"; 
    public static final String CAKE = "cake"; 
    public static final String NONE = "none"; 
  }

  private final ImageView waiterImageView;

  public Waiter(ImageView waiterImageView) {
    this.waiterImageView = waiterImageView;
  }

  public ImageView getWaiterImageView() {
    return waiterImageView;
  }

  public void moveX(double stepX) {
    waiterImageView.setLayoutX(waiterImageView.getLayoutX() + stepX);
  }

  public void moveY(double stepY) {
    waiterImageView.setLayoutY(waiterImageView.getLayoutY() + stepY);
  }

  public void move(double stepX, double stepY) {
    moveX(stepX);
    moveY(stepY);
  }

  public void revertMove(double stepX, double stepY) {
    move(-stepX, -stepY);
  }

  private void setImageBasedOnProduct(String direction, String productInHand) throws ImageNotFoundException {
    String imageName;
    switch (productInHand) {
        case Product.NONE -> imageName = "CofiBrew" + direction + ".png";
        case Product.CAKE -> imageName = "CofiBrewCake" + direction + ".png";
        case Product.COFFEE -> imageName = "CofiBrewCoffee" + direction + ".png";
        default -> imageName = "CofiBrew" + direction + ".png";
    }
    waiterImageView.setImage(ImageUtil.getImageFromResources(imageName));
}

  public void pointUp(String productInHand) throws ImageNotFoundException {
    setImageBasedOnProduct("Up", productInHand);
  }

  public void pointDown(String productInHand) throws ImageNotFoundException {
    setImageBasedOnProduct("Down", productInHand);
  }

  public void pointLeft(String productInHand) throws ImageNotFoundException {
    setImageBasedOnProduct("Left", productInHand);
  }

  public void pointRight(String productInHand) throws ImageNotFoundException {
    setImageBasedOnProduct("Right", productInHand);
  }
}