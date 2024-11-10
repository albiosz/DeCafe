package com.example.decafe;

import com.example.decafe.assets.CustomerMode;
import com.example.decafe.assets.ImageAssets;
import com.example.decafe.exception.ImageNotFoundException;
import com.example.decafe.sprites.Waiter;
import com.example.decafe.util.ImageUtil;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

import java.io.*;
import java.net.URL;
import java.util.*;

// Class that is responsible for every action taken in JavaFX GUI (connected via fxml files)
public class HelloController implements Initializable {

    // Assets of the Start Screen
    public ImageView startButton;
    public ImageView startQuitButton;

    //Assets of the Game Screen
    // Image of the waiter
    public ImageView waiterImageView;

    // Label that shows the current amount of coins earned
    public Label coinsEarnedLabel;
    
    // Used for controlling the movement of the Player
    private PressedButtons pressedButtons = new PressedButtons();

    // Images of the Object the Player can interact with
    public ImageView coffeeMachineImageView;
    public ImageView cakeMachineImageView;
    public ImageView trashcanImageView;

    // Progress bars used to show Production Progress
    public ProgressBar progressBarCoffee;
    public ProgressBar progressBarCake;

    // Images used to control Upgrades
    public ImageView upgradeCoffeeImageView;
    public ImageView upgradeCakeImageView;
    public ImageView upgradePlayerImageView;

    // Labels used for collision detection management
    public Label table1;
    public Label table2;
    public Label table3;
    public Label table4;
    public Label plantsAbove;
    public Label countRight;
    public Label countBelow;
    public Label customerTop1;
    public Label customerTop2;
    public Label customerTop3;
    public Label customerTop4;
    public Label customerBot1;
    public Label customerBot2;
    public Label customerBot3;
    public Label plant;
    public Label edgeBot;
    public Label edgeTop;
    public Label edgeLeft;
    public Label edgeRight;

    //for the customers
    //smiley images
    public ImageView smileyFirst;
    public ImageView smileySecond;
    public ImageView smileyThird;
    public ImageView smileyFourth;
    public ImageView smileyFifth;
    public ImageView smileySixth;
    public ImageView smileySeventh;

    //coin images
    public ImageView coinFirst;
    public ImageView coinSecond;
    public ImageView coinThird;
    public ImageView coinFourth;
    public ImageView coinFifth;
    public ImageView coinSixth;
    public ImageView coinSeventh;

    //order labels
    public ImageView orderlabel1 = new ImageView();
    public ImageView orderlabel2 = new ImageView();
    public ImageView orderlabel3 = new ImageView();
    public ImageView orderlabel4 = new ImageView();
    public ImageView orderlabel5 = new ImageView();
    public ImageView orderlabel6 = new ImageView();
    public ImageView orderlabel7 = new ImageView();

    //customer images
    public ImageView first;
    public ImageView second;
    public ImageView third;
    public ImageView fourth;
    public ImageView fifth;
    public ImageView sixth;
    public ImageView seventh;

    // for end screen
    public ImageView gameStartButton;
    public ImageView cofiBrewImage;
    public ImageView playAgainImage;
    public ImageView backToStartImage;
    public Label labelCredits;
    public ImageView endScreenBackground;
    public ImageView quitEndScreenImage;

    // Player object created to change Images and movement Speed
    public Player player = new Player("CofiBrewUp.png", "CofiBrewCakeLeft.png", "CofiBrewCoffeeLeft.png", 4);
    // Game object used to control main methods
    public Game Play;
    // Label array used for collision detection management
    private Label[] collisions;
    // Timer used to spawn customers or make them leave
    public Timer controllerTimer = new Timer();

    public File f = new File("");
    public String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "backgroundmusic.mp3";
    public AudioClip backgroundMusic = new AudioClip(new File(musicFile).toURI().toString());


    // Method used to load a certain scene according to the name of the fxml file
    public void loadScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    // jump to end screen
    public void switchToEndScreen() throws IOException {
        backgroundMusic.stop();
        loadScene("endScreen.fxml");
    }

    // jump to start screen
    public void switchToStartScreen() throws IOException { // if button BACK TO START MENU is pressed
        loadScene("startScreen.fxml");
    }

    // jump from start screen to game screen
    public void switchToGameScreen() throws IOException { // if button PLAY AGAIN is pressed
        loadScene("gameScreen.fxml");
        if (Customer.customerImages[0] != null) {
            Customer customer = new Customer();
            customer.startTimerSpawn(1, Customer.getControllerTimer());
            customer.startTimerSpawn(5, Customer.getControllerTimer());
            customer.startTimerSpawn(10, Customer.getControllerTimer());
            Customer.allCustomers.add(customer);
        }
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        backgroundMusic.play();
    }

    // jump to instructions
    public void switchToInstructions() throws IOException {
        loadScene("instructions.fxml");
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        pressedButtons.keyPressed(event);
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        pressedButtons.keyReleased(event);
    }
    
    // for smoother motion
    AnimationTimer buttonPressHandler = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {

            double playerVelocity = player.getMovement(); 
            if (pressedButtons.isTwoDiagonalButtonsPressed())
                playerVelocity = player.setDiagonalVelocity();

            // control waiter via wasd keys ([0|0] top-left, [100|100] bottom-right)
            Move upcomingMove = new Move(pressedButtons, playerVelocity);

            Waiter waiterSprite = new Waiter(waiterImageView);
            waiterSprite.move(upcomingMove.getXVelocity(), upcomingMove.getYVelocity());

            if (checkForCollision(waiterSprite.getWaiterImageView())) {
                waiterSprite.revertMove(upcomingMove.getXVelocity(), upcomingMove.getYVelocity());
                upcomingMove.setDirection(Move.NONE);
                return;
            }

            switch (upcomingMove.getDirection()) {
                case Move.UP:
                    try {
                        waiterSprite.pointUp(player.getProductInHand());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return;
                case Move.DOWN:
                    try {
                        waiterSprite.pointDown(player.getProductInHand());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return;
                case Move.LEFT:
                    try {
                        waiterSprite.pointLeft(player.getProductInHand());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return;
                case Move.RIGHT:
                    try {
                        waiterSprite.pointRight(player.getProductInHand());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return;
            }
        }
    };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.pressedButtons.handleButtonPress(buttonPressHandler);

        // transparent labels on top of the images to look for collisions
        collisions = new Label[]{plant, plantsAbove, customerBot1, customerBot2, customerBot3, customerTop1, customerTop2, customerTop3, customerTop4, table1, table2, table3, table4, edgeBot, edgeLeft, edgeRight, edgeTop, countRight, countBelow};

        // initialise ImagesViews and Labels so Customer Class can operate with them
        Customer.customerImages = new ImageView[]{first, second, third, fourth, fifth, sixth, seventh}; //make customer ImageView[]
        Customer.smileyImages = new ImageView[]{smileyFirst, smileySecond, smileyThird, smileyFourth, smileyFifth, smileySixth, smileySeventh}; //make smiley ImageView[]
        Customer.orderLabels = new ImageView[]{orderlabel1, orderlabel2, orderlabel3, orderlabel4, orderlabel5, orderlabel6, orderlabel7}; //make label label[]
        Customer.coinImages = new ImageView[]{coinFirst, coinSecond, coinThird, coinFourth, coinFifth, coinSixth, coinSeventh}; //make coin ImageView[]
        Customer.freeChairs = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6)); //make freeChairs Array
        Customer.setControllerTimer(controllerTimer); //set the static timer t
        Play = new Game(upgradeCoffeeImageView, upgradeCakeImageView, upgradePlayerImageView); // initialise Game Object with upgrade ImageViews
    }

    // start screen - change start button on mouse entered
    public void changeStartCoffeeImage() throws FileNotFoundException {
        startButton.setImage(ImageUtil.create("startCoffeeHot.png"));
    }

    // start screen - change coffee button on mouse exited
    public void changeStartCoffeeImageBack() throws FileNotFoundException {
        startButton.setImage(ImageUtil.create("startCoffee.png"));
    }

    // start screen - change Quit Button on mouse entered
    public void changeQuitStartScreen() throws FileNotFoundException {
        startQuitButton.setImage(ImageUtil.create("quitEndScreenBrighter.png"));
    }

    // start screen - change Quit Button when mouse exited
    public void changeQuitStartScreenBack() throws FileNotFoundException {
        startQuitButton.setImage(ImageUtil.create("quitEndScreen.png"));
    }

    // instructions - change GOT IT! on mouse entered
    public void changeStartImage() throws FileNotFoundException {
        gameStartButton.setImage(ImageUtil.create("instructionsGotIt.png"));
    }

    // instructions - change GOT IT! on mouse exited
    public void changeStartImageBack() throws FileNotFoundException {
        gameStartButton.setImage(ImageUtil.create("instructionsGotItBrighter.png"));
    }

    // end screen - change PlayAgain Button when mouse entered
    public void changePlayAgain() throws FileNotFoundException {
        playAgainImage.setImage(ImageUtil.create("playAgainBrighter.png"));
    }

    // end screen - change PlayAgain Button when mouse exited
    public void changePlayAgainBack() throws FileNotFoundException {
        playAgainImage.setImage(ImageUtil.create("playAgain.png"));
    }

    // end screen - change BackToStartMenu Button when mouse entered
    public void changeBackToStartMenu() throws FileNotFoundException {
        backToStartImage.setImage(ImageUtil.create("backToStartMenuBrighter.png"));
    }

    // end screen - change BackToStartMenu Button when mouse exited
    public void changeBackToStartMenuBack() throws FileNotFoundException {
        backToStartImage.setImage(ImageUtil.create("backToStartMenu.png"));
    }

    // end screen - change Quit Button when mouse entered
    public void changeQuitEndScreen() throws FileNotFoundException {
        quitEndScreenImage.setImage(ImageUtil.create("quitEndScreenBrighter.png"));
    }

    // end screen - change Quit Button when mouse exited
    public void changeQuitEndScreenBack() throws FileNotFoundException {
        quitEndScreenImage.setImage(ImageUtil.create("quitEndScreen.png"));
    }

    // if waiter is near coffee machine, change appearance when clicked
    public void showCoffee() throws FileNotFoundException, ImageNotFoundException {
        if (waiterImageView.getBoundsInParent().intersects(coffeeMachineImageView.getBoundsInParent())) {
            Play.getCoffeeMachine().displayProduct(waiterImageView, coffeeMachineImageView, player, progressBarCoffee);
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "test_sound.wav";
            AudioClip coffeeSound = new AudioClip(new File(musicFile).toURI().toString());
            //MediaPlayer coffeeSound = new MediaPlayer(sound);
            coffeeSound.play();
        }
    }

    // if waiter is near cake machine, change appearance when clicked
    public void showCake() throws FileNotFoundException, ImageNotFoundException {
        if (waiterImageView.getBoundsInParent().intersects(cakeMachineImageView.getBoundsInParent())) {
            Play.getCakeMachine().displayProduct(waiterImageView, cakeMachineImageView, player, progressBarCake);
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "test_sound.wav";
            AudioClip cakeSound = new AudioClip(new File(musicFile).toURI().toString());
            //MediaPlayer cakeSound = new MediaPlayer(sound);
            cakeSound.play();
        }
    }

    // if no product is held by waiter
    public void noProduct() throws FileNotFoundException {
        if (player.getProductInHand().equals("coffee") || player.getProductInHand().equals("cake")) {
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "trashSound.mp3";
            AudioClip trashSound = new AudioClip(new File(musicFile).toURI().toString());
            //MediaPlayer cakeSound = new MediaPlayer(sound);
            trashSound.play();
            waiterImageView.setImage(ImageUtil.create(player.getFilenameImageWithoutProduct()));
            player.setProductInHand("none");
        }
    }

    // find the customer in the customerList and return it
    public Customer findCustomer(List<Customer> customerList, ImageView customerImageView) {
        for (Customer customer : customerList) {
            if (customer.getImage().equals(customerImageView)) {
                return customer;
            }
        }
        return null; // if not found return null
    }

    // Method used to display a customer, check if and order was right and set Images for coin ImagesViews
    public void displayPerson(MouseEvent event) throws IOException, ImageNotFoundException {
        ImageView customerImageView = (ImageView) event.getSource(); //get the Customer of the clicked Image
        Customer customer = findCustomer(Customer.customersInCoffeeShop, customerImageView); //make new customer object

        if (!customer.isAlreadyOrdered()) { //if customer has not ordered yet, display an order
            customer.displayOrder(customer.getLabel());
        } else {
            if (customerImageView.getBoundsInParent().intersects(waiterImageView.getBoundsInParent())) { // If customer has already ordered and waiter is near the customer
                try {
                    customer.startTimerSpawn(5, Customer.getControllerTimer()); // spawn a new customer if a chair is free
                } catch (NullPointerException e) {
                    switchToEndScreen();
                }
                if (customer.checkOrder(player, customer, waiterImageView)) { // check if order the waiter has in his hands is the one the customer ordered
                    String moneyImage = ""; // if so set the relating coin ImageView
                    if (customer.getMood() == CustomerMode.GREEN) { // if customer left happy
                        moneyImage = ImageAssets.DOLLAR.getImage();
                    } else if (customer.getMood() == CustomerMode.YELLOW) { // if customer left normal
                        moneyImage = ImageAssets.FOUR_COINS.getImage();
                    } else if (customer.getMood() == CustomerMode.RED) { // if customer left sad
                        moneyImage = ImageAssets.THREE_COINS.getImage();
                    }
                    customer.getCoinImage().setImage(ImageUtil.create(moneyImage)); //set coin image
                    customer.getCoinImage().setOnMouseClicked(event1 -> { // set click event for coin image
                        try {
                            getMoney(event1, customer); // if coin Image is clicked jump to this method
                        } catch (IOException | ImageNotFoundException e) {
                            try {
                                switchToEndScreen();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    // Method to check if an Upgrade can be made (check if player has earned enough coins and if it was already used or not)
    public void checkUpgradePossible(Upgrade upgrade) throws FileNotFoundException, ImageNotFoundException {
        Play.checkUpgradePossible(upgrade);
    }

    // Method to actively use an Upgrade
    public void doUpgrade(MouseEvent e) throws FileNotFoundException, ImageNotFoundException {
        // activate the Upgrade (according to whatever ImageView was chosen)
        Play.doUpgrade(((ImageView) e.getSource()).getId(), player);
        // set the coin label to the correct amount of coins (coins earned - upgrade costs)
        coinsEarnedLabel.setText(String.valueOf(Play.getCoinsEarned()));

        File f = new File("");
        String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "upgradeSound.wav";
        AudioClip getUpgrade = new AudioClip(new File(musicFile).toURI().toString());
        //MediaPlayer collectMoney = new MediaPlayer(sound);
        getUpgrade.play();

        // check if other upgrades are still possible or if they need to be "deactivated"
        checkUpgradePossible(Play.getCoffeeUpgrade());
        checkUpgradePossible(Play.getCakeUpgrade());
        checkUpgradePossible(Play.getPlayerUpgrade());
    }

    // check if collisions occur
    public boolean checkForCollision(ImageView waiter) {
        for (Label collision : collisions) { // iterate through labels
            if (waiter.getBoundsInParent().intersects(collision.getBoundsInParent())) { // if waiter would collide with a label
                return true; // return true
            }
        }
        return false;
    }

    // Method used when coin Image is clicked on
    public void getMoney(MouseEvent e, Customer customer) throws IOException, ImageNotFoundException {
        File f = new File("");
        String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "coinsSound.wav";
        AudioClip collectMoney = new AudioClip(new File(musicFile).toURI().toString());
        //MediaPlayer collectMoney = new MediaPlayer(sound);
        collectMoney.play();
        Customer.addFreeSeat(customer.getChair()); // add the seat chosen from the customer to the freeSeatsArray again
        Play.setCoinsEarned(customer); // set the money earned according to what amount of money the customer left
        ((ImageView) e.getSource()).setVisible(false); // disable the coin Image and make it invisible
        ((ImageView) e.getSource()).setDisable(true);

        if (Play.getCoinsEarned() < 80) { // check if enough coins were earned to end the game
            checkUpgradePossible(Play.getCoffeeUpgrade()); // if not, check if any upgrades would be possible
            checkUpgradePossible(Play.getCakeUpgrade());
            checkUpgradePossible(Play.getPlayerUpgrade());
            coinsEarnedLabel.setText(String.valueOf(Play.getCoinsEarned())); // refresh the coin score shown in GUI
            try {
                customer.startTimerSpawn(5, Customer.getControllerTimer()); // spawn a new customer
            } catch (NullPointerException y) {
                switchToEndScreen();
            }
        } else { // if enough coins were earned
            stopTimers(); // stop all the timers
            switchToEndScreen(); // switch to the end screen
        }
    }

    // Method used to stop all the timers activated by spawning customers
    public void stopTimers() {
        for (Customer customer : Customer.allCustomers) { // cancel all 60 seconds timers
            if (customer.getSixtySecondsTimer() != null) {
                customer.getSixtySecondsTimer().cancel();
            }
        }
        Customer.allCustomers.clear(); // clear all Lists created by spawning/despawning customer
        Customer.customersInCoffeeShop.clear();
        Customer.freeChairs.clear();
        player.setProductInHand("none"); // clear Cofis hand
        controllerTimer.cancel(); // cancel spawn/leaving timer
        Customer.getControllerTimer().cancel();
    }

    // end game (called when exit clicked) - in Game Screen
    public void endGameQuick() {
        stopTimers();
        backgroundMusic.stop();
        Platform.exit();
        System.exit(0);
    }

    // end game (called when quit button is clicked) - in End Screen and Start Screen
    public void endGame() {
        Platform.exit();
        backgroundMusic.stop();
        System.exit(0);
    }
}
