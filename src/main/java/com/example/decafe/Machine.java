package com.example.decafe;

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

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

// Class used to control all the methods needed to operate a Machine
public class Machine {

    // The duration that is needed to produce a product - How long does it take to produce something?
    private int duration;

    // Boolean that says if a product was already produced of if it needs to be produced
    private Boolean produced;

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

    public void setProduced(Boolean produced) {
        this.produced = produced;
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
        Timeline timelineBar = new Timeline(
                new KeyFrame(
                        // Set this value for the speed of the animation
                        Duration.millis(300),
                        new KeyValue(statusCountProperty, maxStatus)
                )
        );

        // The animation should be infinite
        timelineBar.setCycleCount(Timeline.INDEFINITE);

        // play the timeline
        timelineBar.play();

        // Add a listener to the status property
        statusCountProperty.addListener((ov, statusOld, statusNewNumber) -> {

            int statusNew = statusNewNumber.intValue();

            // Remove old status pseudo from progress-bar
            machineProgressBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusOld.intValue()), false);

            // Add current status pseudo from progress-bar
            machineProgressBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusNew), true);
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

    // Method to display a Product / change the state of a Machine
    public void displayProduct(
            ImageView waiterImageView,
            ImageView machineImageView,
            Player cofiBrew,
            ProgressBar machineProgressBar
    ) throws FileNotFoundException {

        // create new Timer object
        Timer productionTimer = new Timer();

        // Set default image of Waiter and Machine
        String imageMachine = this.filenameImageMachineWithProduct;
        String imageCofi = cofiBrew.getFilenameImageWithoutProduct();

        // Set boolean got produced to false - used to check if a product was produced when clicked on Machine
        boolean gotProduced = false;

        if (!this.produced) {

            this.setProduced(true);
            gotProduced = true;

            var productInHand = cofiBrew.getProductInHand();

            imageCofi = (productInHand.equals("coffee"))
                    ? cofiBrew.getFilenameImageWithCoffee()
                    : (productInHand.equals("cake"))
                    ? cofiBrew.getFilenameImageWithCake()
                    : imageCofi;

        } else {

            // And the waiter hasn't anything in his hands
            if (cofiBrew.getProductInHand().equals("none")) {

                // Set produced boolean to false since nothing was produces
                this.setProduced(false);

                // Change Image to Machine without product (since product was taken)
                imageMachine = this.filenameImageMachineWithoutProduct;

                // Set the product the player obtain to whatever type the machine is
                cofiBrew.setProductInHand(this.productType);

                imageCofi = (this.productType.equals("coffee"))
                        ? cofiBrew.getFilenameImageWithCoffee()
                        : cofiBrew.getFilenameImageWithCake();

            } else {

                imageCofi = (cofiBrew.getProductInHand().equals("coffee"))
                        ? cofiBrew.getFilenameImageWithCoffee()
                        : cofiBrew.getFilenameImageWithCake();
            }
        }

        // Set the waiter Image to whatever images was chosen above
        waiterImageView.setImage(FileUtil.getImage(imageCofi));

        // If something was produced
        if (gotProduced) {

            // Animate the progress bar and wait till the product gets produced
            doProgressBarAnimation(
                    productionTimer,
                    machineImageView,
                    machineProgressBar,
                    FileUtil.getImage(imageMachine)
            );

        // If nothing was produced
        } else {

            // Make the progress bar visible if something was produced and if not so set it invisible
            machineProgressBar.setVisible(this.getProduced());

            // Set the machine Image to whatever images was chosen above
            machineImageView.setImage(FileUtil.getImage(imageMachine));
        }
    }
}
