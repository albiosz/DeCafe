package com.example.decafe;

import static javafx.animation.Timeline.INDEFINITE;

import com.example.decafe.util.ImageUtil;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

// Class used to control all the methods needed to operate a Machine
public class Machine {
    
    private static class Product {
        private static final String NONE   = "none";
        private static final String CAKE   = "cake";
        private static final String COFFEE = "coffee";
    }

    // The duration that is needed to produce a product - How long does it take to produce something?
    private int duration;

    // Boolean that says if a product was already produced of if it needs to be produced
    private boolean produced;

    // Image of the Machine in the default state
    private final String filenameImageMachineWithoutProduct;

    // Image of the Machine with a product already produced
    private final String filenameImageMachineWithProduct;

    // The type of the Machine (cake or coffee)
    private final String productType;

    // Constructor
    public Machine(int duration,
                   String filenameImageMachineWithProduct,
                   String filenameImageMachineWithoutProduct,
                   String productType) {

        this.duration = duration;
        this.produced = false;
        this.filenameImageMachineWithProduct = filenameImageMachineWithProduct;
        this.filenameImageMachineWithoutProduct = filenameImageMachineWithoutProduct;
        this.productType = productType;
    }

    // Method used to animate the progressbar above the Machine
    public void doProgressBarAnimation(
            Timer productionTimer,
            ImageView machineImageView,
            ProgressBar machineProgressBar,
            Image imageProductProduced) {

        // During the Animation the Player should not be able to click the machine therefore disable it
        machineImageView.setDisable(true);

        // The Progressbar gets shown
        machineProgressBar.setVisible(true);

        // For the animation is used a code from Stackoverflow and modified it a bit
        // code from https://stackoverflow.com/questions/18539642/progressbar-animated-javafx
        // create a new Timeline task
        Timeline task = new Timeline(
                // With two KeyFrame Animations
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(machineProgressBar.progressProperty(), 0)
                ),
                new KeyFrame(
                        // Set the duration of the progressbar animation
                        Duration.seconds(this.getDuration()),
                        new KeyValue(machineProgressBar.progressProperty(), 1)
                )
        );

        // Set the maxStatus
        int maxStatus = 12;

        // Create the Property that holds the current status count
        IntegerProperty statusCountProperty = new SimpleIntegerProperty(1);

        // Create the timeline that loops the statusCount till the maxStatus
        Timeline timelineBar = getTimelineBar(statusCountProperty, maxStatus);

        // The animation should be infinite
        timelineBar.setCycleCount(INDEFINITE);

        // play the timeline
        timelineBar.play();

        // Add a listener to the status property
        statusCountProperty.addListener((ov, statusOld, statusNewNumber) -> {

            int statusNew = statusNewNumber.intValue();

            // Remove old status pseudo from progress-bar
            machineProgressBar.pseudoClassStateChanged(
                    PseudoClass.getPseudoClass("status" + statusOld.intValue()), false);

            // Add current status pseudo from progress-bar
            machineProgressBar.pseudoClassStateChanged(
                    PseudoClass.getPseudoClass("status" + statusNew), true);
        });

        task.playFromStart();

        // Schedule how long the animation should go
        // Idea from https://stackoverflow.com/questions/2258066/run-a-java-function-after-a-specific-number-of-seconds
        productionTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {

                        // After a certain time was reached change the Image of the Machine
                        machineImageView.setImage(imageProductProduced);

                        // And make the Machine clickable again
                        machineImageView.setDisable(false);

                        // And stop all timers
                        task.stop();
                        timelineBar.stop();
                        productionTimer.cancel();
                    }
                },
                this.duration * 1000L
        );
    }

    private static Timeline getTimelineBar(IntegerProperty statusCountProperty, int maxStatus) {
        // Set this value for the speed of the animation
        return new Timeline(
                new KeyFrame(
                        // Set this value for the speed of the animation
                        Duration.millis(300),
                        new KeyValue(statusCountProperty, maxStatus)
                )
        );
    }

    public void displayProduct(ImageView waiterImageView,
                               ImageView machineImageView,
                               Player cofiBrew,
                               ProgressBar machineProgressBar) {

        String imageMachine = this.filenameImageMachineWithProduct;

        String imageCofi = (this.produced)
            ? handleProduced(cofiBrew, imageMachine, machineImageView, machineProgressBar)
            : handleNotProduced(cofiBrew, imageMachine, machineImageView, machineProgressBar);

        waiterImageView.setImage(ImageUtil.getImageFromResources(imageCofi));
    }

    private String handleNotProduced(Player cofiBrew,
                                     String imageMachine,
                                     ImageView machineImageView,
                                     ProgressBar machineProgressBar) {

        this.setProduced(true);

        var productInHand = cofiBrew.getProductInHand();

        doProgressBarAnimation(
                new Timer(),
                machineImageView,
                machineProgressBar,
                ImageUtil.getImageFromResources(imageMachine)
        );

        return switch (productInHand) {
            case Product.COFFEE -> cofiBrew.getFilenameImageWithCoffee();
            case Product.CAKE   -> cofiBrew.getFilenameImageWithCake();
            default             -> cofiBrew.getFilenameImageWithoutProduct();
        };
    }

    private String handleProduced(Player cofiBrew,
                                  String imageMachine,
                                  ImageView machineImageView,
                                  ProgressBar machineProgressBar) {

        String imageCofi;

        if (cofiBrew.getProductInHand().equals(Product.NONE)) {

            this.setProduced(false);

            imageMachine = this.filenameImageMachineWithoutProduct;

            cofiBrew.setProductInHand(this.productType);

            imageCofi = (this.productType.equals(Product.COFFEE))
                    ? cofiBrew.getFilenameImageWithCoffee()
                    : cofiBrew.getFilenameImageWithCake();

        } else {

            imageCofi = (cofiBrew.getProductInHand().equals(Product.COFFEE))
                    ? cofiBrew.getFilenameImageWithCoffee()
                    : cofiBrew.getFilenameImageWithCake();
        }

        machineProgressBar.setVisible(this.getProduced());
        machineImageView.setImage(ImageUtil.getImageFromResources(imageMachine));

        return imageCofi;
    }

    // Getter

    public int getDuration() {
        return duration;
    }

    public Boolean getProduced() {
        return produced;
    }

    // Setter

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setProduced(boolean produced) {
        this.produced = produced;
    }
}
