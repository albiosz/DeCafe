package com.example.decafe.sprites;

import java.io.FileNotFoundException;

import com.example.decafe.Player;
import com.example.decafe.util.ImageUtil; 

import javafx.scene.image.ImageView;

public class Waiter {
  public ImageView waiterImageView;

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

  public void pointUp(String productInHand) throws FileNotFoundException {
    switch (productInHand) {
      case "none":
        waiterImageView.setImage(ImageUtil.create("CofiBrewUp.png"));
        return;
      case "cake":
        waiterImageView.setImage(ImageUtil.create("CofiBrewCakeUp.png"));
        return;
      case "coffee":
        waiterImageView.setImage(ImageUtil.create("CofiBrewCoffeeUp.png"));
        return;
      default:
      waiterImageView.setImage(ImageUtil.create("CofiBrewUp.png"));
    }
  }

  public void pointDown(String productInHand) throws FileNotFoundException {
    switch (productInHand) {
      case "none":
        waiterImageView.setImage(ImageUtil.create("CofiBrewDown.png"));
        return;
      case "cake":
        waiterImageView.setImage(ImageUtil.create("CofiBrewCakeDown.png"));
        return;
      case "coffee":
        waiterImageView.setImage(ImageUtil.create("CofiBrewCoffeeDown.png"));
        return;
      default:
        waiterImageView.setImage(ImageUtil.create("CofiBrewDown.png"));
    }
  }

  public void pointLeft(String productInHand) throws FileNotFoundException {
    switch (productInHand) {
      case "none":
        waiterImageView.setImage(ImageUtil.create("CofiBrewLeft.png"));
        return;
      case "cake":
        waiterImageView.setImage(ImageUtil.create("CofiBrewCakeLeft.png"));
        return;
      case "coffee":
        waiterImageView.setImage(ImageUtil.create("CofiBrewCoffeeLeft.png"));
        return;
      default:
        waiterImageView.setImage(ImageUtil.create("CofiBrewLeft.png"));
    }
}

  public void pointRight(String productInHand) throws FileNotFoundException {
    switch (productInHand) {
      case "none":
        waiterImageView.setImage(ImageUtil.create("CofiBrewRight.png"));
        return;
      case "cake":
        waiterImageView.setImage(ImageUtil.create("CofiBrewCakeRight.png"));
        return;
      case "coffee":
        waiterImageView.setImage(ImageUtil.create("CofiBrewCoffeeRight.png"));
        return;
      default:
        waiterImageView.setImage(ImageUtil.create("CofiBrewRight.png"));
    }
  }
}