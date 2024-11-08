package com.example.decafe;

import com.example.decafe.assets.CafeAssets;
import com.example.decafe.assets.CustomerMode;
import com.example.decafe.assets.ImageAssets;
import com.example.decafe.exception.GameException;
import com.example.decafe.exception.ImageNotFoundException;
import com.example.decafe.util.ImageUtil;
import javafx.scene.image.ImageView;

import java.util.Map;

//Class that is used mainly to control certain assets of the Game like Machines, Upgrades and the Coin Score
public class Game {
    private final Machine coffeeMachine; // A Machine Object used to make Coffee
    private final Machine cakeMachine; // A Machine Object used to make Cake
    private final Upgrade coffeeUpgrade; // An Upgrade Object used to upgrade the Coffee Machine
    private final Upgrade cakeUpgrade; // An Upgrade Object used to upgrade the Cake Machine
    private final Upgrade playerUpgrade; // An Upgrade Object used to make the Player faster
    private int coinsEarned; // The amount of Coins earned/used in the Game - 0 at the beginning
    public static final int INITIAL_COINS_EARNED = 0;

    // Constructor
    Game(ImageView upgradeCoffee, ImageView upgradeCake, ImageView upgradePlayer) {
        this.coffeeMachine = new Machine(5, ImageAssets.COFFEE_MACHINE_WITH_COFFEE.getImage(), ImageAssets.COFFEE_MACHINE.getImage(), CafeAssets.COFFEE.getName());
        this.cakeMachine = new Machine(5, ImageAssets.KITCHEN_AID_USED.getImage(), ImageAssets.KITCHEN_AID.getImage(), CafeAssets.CAKE.getName());
        this.coffeeUpgrade = new Upgrade(20, false, ImageAssets.COFFEE_UPGRADE.getImage(), ImageAssets.COFFEE_USED.getImage(), upgradeCoffee);
        this.cakeUpgrade = new Upgrade(20, false, ImageAssets.CAKE_UPGRADE.getImage(), ImageAssets.CAKE_USED.getImage(), upgradeCake);
        this.playerUpgrade = new Upgrade(40, false, ImageAssets.UPGRADE_SKATES.getImage(), ImageAssets.UPGRADE_SKATES_USED.getImage(), upgradePlayer);
        this.coinsEarned = INITIAL_COINS_EARNED;
    }

    // Method to check if the Player can use a certain Upgrade
    public void checkUpgradePossible(Upgrade upgrade) throws ImageNotFoundException {
        if (!upgrade.isAlreadyUsedOnce() && this.coinsEarned >= upgrade.getCoinsNeeded()) { // If upgrade was not already used and the Player earned enough coins to buy it
            // Enable the ImageView
            upgrade.getUpgradeImageView().setDisable(false);
            // Set the Image to the "activated" Upgrade Image
            upgrade.getUpgradeImageView().setImage(ImageUtil.getImageFromResources(upgrade.getFilenameUpgradeNotUsed()));
        } else { // If the upgrade was used already or the Player hasn't enough coins to buy it
            // Disable the Image
            upgrade.getUpgradeImageView().setDisable(true);
            // Set the Image to "deactivated" Upgrade Image
            upgrade.getUpgradeImageView().setImage(ImageUtil.getImageFromResources(upgrade.getFilenameUpgradeUsed()));
        }
    }

    // Method to do a certain upgrade
    public void doUpgrade(String type, Player cofiBrew) throws ImageNotFoundException {
        switch (CafeAssets.getValueOf(type)) { // Switch the type of upgrade you received
            case COFFEE -> { // If the player chose the coffee upgrade
                // Set the coin score according to what the upgrade cost + change Image and Disable upgrade
                coinsEarned = coffeeUpgrade.doUpgrades(coinsEarned);
                // Increase the speed of the Coffee Machine
                coffeeMachine.setDuration(2);
            }
            case CAKE -> { // If the player chose the cake upgrade
                // Set the coin score according to what the upgrade cost + change Image and Disable upgrade
                coinsEarned = cakeUpgrade.doUpgrades(coinsEarned);
                // Increase the speed of the Cake Machine
                cakeMachine.setDuration(2);
            }
            case PLAYER -> { // If the player chose the player upgrade
                // Set the coin score according to what the upgrade cost + change Image and Disable upgrade
                coinsEarned = playerUpgrade.doUpgrades(coinsEarned);
                // Increase the movement speed of the Player
                cofiBrew.setMovement(6);
            }
            default -> throw new GameException("Type of upgrade can not be found");
        }
    }

    // Method to increase coins earned according to how satisfied the customer was
    public void setCoinsEarned(Customer customer) {
        // Assuming we have an enum for customer mood: Green, Yellow, Red
        CustomerMode mood = customer.getMood();

        // Using a map for mood to coins earned mapping
        Map<CustomerMode, Integer> moodToCoins = Map.of(
                CustomerMode.GREEN, 7,
                CustomerMode.YELLOW, 5,
                CustomerMode.RED, 3
        );

        // Increase the coin score based on mood
        this.coinsEarned += moodToCoins.getOrDefault(mood, 0);  // Default to 0 if mood is unknown

    }

    public Machine getCoffeeMachine() {
        return coffeeMachine;
    }

    public Machine getCakeMachine() {
        return cakeMachine;
    }

    public Upgrade getCoffeeUpgrade() {
        return coffeeUpgrade;
    }

    public Upgrade getCakeUpgrade() {
        return cakeUpgrade;
    }

    public Upgrade getPlayerUpgrade() {
        return playerUpgrade;
    }

    public int getCoinsEarned() {
        return coinsEarned;
    }
}
