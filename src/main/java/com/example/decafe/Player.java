package com.example.decafe;

// Class to handle Methods used to change the Image and movement speed of waiter
public class Player {

    // Image of the waiter without anything in his hands
    private final String filenameImageWithoutProduct;

    // Image of the waiter with coffee in his hands
    private final String filenameImageWithCoffee;

    // Image of the waiter with cake in his hands
    private final String filenameImageWithCake;

    // The type of product the waiter holds in his hands (Coffee or Cake)
    private String productInHand;

    // the movement speed at which the waiter moves
    private double velocity;

    // Constructor
    public Player(String filenameImageWithoutProduct,
                  String filenameImageWithCake,
                  String filenameImageWithCoffee,
                  int movement) {

        this.filenameImageWithoutProduct = filenameImageWithoutProduct;
        this.filenameImageWithCake = filenameImageWithCake;
        this.filenameImageWithCoffee = filenameImageWithCoffee;
        this.productInHand = "none";
        this.velocity = movement;
    }

    // Getter

    public String getProductInHand() {
        return productInHand;
    }

    public String getFilenameImageWithoutProduct() {
        return filenameImageWithoutProduct;
    }

    public String getFilenameImageWithCake() {
        return filenameImageWithCake;
    }

    public String getFilenameImageWithCoffee() {
        return filenameImageWithCoffee;
    }

    public double getMovement() {
        return velocity;
    }

    // Setter

    public void setProductInHand(String productInHand) {
        this.productInHand = productInHand;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double setDiagonalVelocity() {
        return Math.sqrt(Math.pow(velocity, 2) / 2);
    }
}
