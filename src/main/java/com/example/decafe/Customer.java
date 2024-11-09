package com.example.decafe;

import com.example.decafe.assets.CustomerMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Customer {
    private String order; //The order of the customer
    private ImageView customer; //picture of the customer
    private ImageView orderLabel; //label that displays order
    private int chair; //number of chair the customer is sitting
    private Timer sixtySecondsTimer; //timer for the 60 seconds waiting time
    private static Timer controllerTimer; //timer for leaving, spawning
    private ImageView smiley; //picture of smiley for the mood of the customer
    private ImageView coinImage; //picture of the money the customer is leaving behind
    private boolean alreadyOrdered; //boolean to see if the customer has already ordered
    private CustomerMode mood;
    private boolean leftUnhappy = true; //boolean to see if customer has left unhappy (received wrong order or left after 60 secs)

    public static List<Customer> customersInCoffeeShop = new ArrayList<>(); //list with all customers that are in the café
    public static List<Customer> allCustomers = new ArrayList<>(); //list with all customers ever created
    public static List<Integer> freeChairs; //integer list with all chair numbers
    public static ImageView[] customerImages; //array with all customer pictures
    private static int freeSeatChosen = 0;
    public static ImageView[] smileyImages; //image for smiley
    public static ImageView[] orderLabels; //label for order
    public static ImageView[] coinImages; //image for coins

    // Constructors
    Customer(){}
    Customer(ImageView image, ImageView label, int chair, ImageView smiley, ImageView coinImage) {
        this.customer = image;
        this.orderLabel = label;
        this.alreadyOrdered = false;
        this.chair = chair;
        this.smiley = smiley;
        this.coinImage = coinImage;
        this.sixtySecondsTimer = new Timer();
    }

    // Getter
    public static Timer getControllerTimer() {
        return controllerTimer;
    }

    public Timer getSixtySecondsTimer() {
        return sixtySecondsTimer;
    }

    public static void addFreeSeat(int chairLeft) { //add chair number to the list when customer has left
        freeChairs.add(chairLeft);
    }

    public boolean isAlreadyOrdered() { //return if the customer has already ordered or not
        return this.alreadyOrdered;
    }

    public String getOrder() { //returns the order of the customer
        return order;
    }

    public int getChair() { //get the number of the chair the customer is sitting
        return chair;
    }

    public ImageView getImage() { //returns the image of the customer
        return this.customer;
    }

    public ImageView getLabel() { //returns the label of the customer
        return this.orderLabel;
    }

    public String getRandomOrder() {
        return (order = new Random().nextBoolean() ? "cake" : "coffee");
    }

    public ImageView getCoinImage() { //returns the image of the coin
        return coinImage;
    }

    // Setter
    public void setOrder(String order) { //sets the order of the customer
        this.order = order;
    }

    public static void setControllerTimer(Timer controllerTimer) { //sets the timer
        Customer.controllerTimer = controllerTimer;
    }

    // Method used to create an Image Object
    public Image createImage(String filename) throws FileNotFoundException {
        String path = new File("").getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        return new Image(new FileInputStream(path));
    }

    //Returns the appropriate image for the customer
    public static ImageView getImage(ImageView customer, ImageView[] searchArray) {
        for (int i = 0; i < customerImages.length; i++) {
            if (customerImages[i].equals(customer)) return searchArray[i];
        }
        return new ImageView();
    }

    //Returns the appropriate label for the customer
    public static ImageView getLabel(ImageView customer) {
        for (int i = 0; i < customerImages.length; i++) {
            if (customerImages[i].equals(customer)) return orderLabels[i];
        }
        return new ImageView();
    }

    //Returns random customer picture
    public static ImageView getRandomPic() {
        int index = freeChairs.get(new Random().nextInt(freeChairs.size()));
        freeSeatChosen = index;
        if (!freeChairs.contains(index)) getRandomPic();  //when the customer is already visible make new random number
        freeChairs.remove(Integer.valueOf(index)); //remove the number from the number list of chairs so there are no duplicates
        return customerImages[index];
    }

    //Methode to spawn customers
    public static void spawnCustomers() {
        if (customersInCoffeeShop.size() < 3 && !freeChairs.isEmpty()) {
            ImageView customerImage = getRandomPic();
            customerImage.setVisible(true);
            Customer customer = new Customer(customerImage, getLabel(customerImage), freeSeatChosen, getImage(customerImage, smileyImages), getImage(customerImage, coinImages));
            customersInCoffeeShop.add(customer);
            allCustomers.add(customer);
            playAudio("doorBell.mp3");
            customer.waitingTime();
        }
    }

    private static void playAudio(String filename) {
        String path = new File("").getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        new AudioClip(new File(path).toURI().toString()).play();
    }

    //Timer to spawn the customers
    public void startTimerSpawn(int duration, Timer controllerTimer) {
        controllerTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Customer.spawnCustomers();
                        controllerTimer.purge();
                    }
                },
                duration * 1000L
        );
    }

    //Methode for the timer when customer leaves
    public void startTimerLeave(Customer customer) {
        orderLabel.setVisible(false);
        smiley.setVisible(false);
        controllerTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            leave(customer.getImage());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        controllerTimer.purge();
                    }
                },
                1000
        );
        this.sixtySecondsTimer.cancel(); //cancel the 60 seconds when customer left
    }

    //Methode for the general 60 seconds timer
    public void waitingTime() {
        TimerTask timerTask = new TimerTask() {
            int seconds = 60;

            @Override
            public void run() {
                seconds--; // increment after or before ?
                if (seconds == 0) {
                    startTimerLeave(Customer.this);
                } else if (seconds == 59 || seconds == 30 || seconds == 15) {
                    updateSmiley(seconds);
                }

            }
        };
        sixtySecondsTimer.schedule(timerTask, 0, 1000);
    }

    private void updateSmiley(int seconds) {
        try {
            String imageName = seconds == 59 ? "smileygreen.png" : seconds == 30 ? "smileyyellow.png" : "smileyred.png";
            smiley.setVisible(true);
            smiley.setImage(createImage(imageName));
            setMood(seconds == 59 ? CustomerMode.GREEN : seconds == 30 ? CustomerMode.YELLOW : CustomerMode.RED);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Methode to display order
    public void displayOrder(ImageView orderlabel) throws FileNotFoundException {
        this.order = getRandomOrder();
        setOrder(order);
        if (order.equals("cake")) {
            if (chair == 0 || chair == 1 || chair == 4 || chair == 6) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCakeTopLeft.png"));
            } else if (chair == 2 || chair == 3) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCakeTopRight.png"));
            } else if (chair == 5) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCakeBottomRight.png"));
            }
        } else if (order.equals("coffee")) {
            if (chair == 0 || chair == 1 || chair == 4 || chair == 6) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCoffeeTopLeft.png"));
            } else if (chair == 2 || chair == 3) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCoffeeTopRight.png"));
            } else if (chair == 5) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCoffeeBottomRight.png"));
            }
        }
        this.alreadyOrdered = true;
    }


    //Methode to check if the order is right or wrong
    public boolean checkOrder(Player CofiBrew, Customer customer, ImageView waiterImage) throws FileNotFoundException {
        waiterImage.setImage(createImage(CofiBrew.getFilenameImageWithoutProduct())); //set CofiBrew without order
        if (CofiBrew.getProductInHand().equals(customer.getOrder())) { //if CofiBrew has the right order
            CofiBrew.setProductInHand("none"); // change product hold by player to none
            this.leftUnhappy = false;
            startTimerLeave(this); // start timer to leave the coffee shop (true - it was the right order)
            return true;
        } else {
            CofiBrew.setProductInHand("none"); // change product hold by player to none
            startTimerLeave(this);  // start timer to leave the coffee shop (false - it was the wrong order)
            return false;
        }
    }

    //when the customer leaves after 60 seconds without being served or received wrong order
    public static void noMoneySpent(Customer customer) throws FileNotFoundException {
        customer.coinImage.setVisible(false);
        customer.coinImage.setDisable(true);
        freeChairs.add(customer.getChair());
        customer.startTimerSpawn(5, controllerTimer);
    }

    //Methode for when the customer leaves
    public void leave(ImageView customerImage) throws FileNotFoundException {
        customerImage.setVisible(false);
        customersInCoffeeShop.removeIf(customer -> customer.getImage().equals(customerImage));
        coinImage.setVisible(true);
        coinImage.setDisable(false);
        playAudio(leftUnhappy ? "wrongChoice.mp3" : "rightChoice.mp3");

        if (leftUnhappy) {
            coinImage.setImage(createImage("coin.png"));
            coinImage.setOnMouseClicked(event -> {
                try {
                    noMoneySpent(this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public CustomerMode getMood() {
        return mood;
    }

    public void setMood(CustomerMode mood) {
        this.mood = mood;
    }
}

