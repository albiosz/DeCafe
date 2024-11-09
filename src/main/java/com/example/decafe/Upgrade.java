package com.example.decafe;

import com.example.decafe.exception.ImageNotFoundException;
import com.example.decafe.util.ImageUtil;
import javafx.scene.image.ImageView;

// Function used to control all the methods used for Upgrades
public class Upgrade {

    // The coins needed to use/do the Upgrade
    private final int coinsNeeded;

    // Boolean that indicates if the Upgrade was already used or not
    private boolean alreadyUsedOnce;

    // Image of "deactivated" Upgrade
    private final String filenameUpgradeNotUsed;

    // Image of "activated" Upgrade
    private final String filenameUpgradeUsed;

    // ImageView that is related to the Upgrade
    private final ImageView upgradeImageView;

    // Constructor
    Upgrade(int coinsNeeded,
            boolean alreadyUsedOnce,
            String filenameUpgradeNotUsed,
            String filenameUpgradeUsed,
            ImageView upgradeImageView) {

        this.coinsNeeded = coinsNeeded;
        this.alreadyUsedOnce = alreadyUsedOnce;
        this.filenameUpgradeNotUsed = filenameUpgradeNotUsed;
        this.filenameUpgradeUsed = filenameUpgradeUsed;
        this.upgradeImageView = upgradeImageView;
    }

    // Getter

    public boolean isAlreadyUsedOnce() {
        return alreadyUsedOnce;
    }

    public int getCoinsNeeded() {
        return coinsNeeded;
    }

    public String getFilenameUpgradeUsed() {
        return filenameUpgradeUsed;
    }

    public String getFilenameUpgradeNotUsed() {
        return filenameUpgradeNotUsed;
    }

    public ImageView getUpgradeImageView() {
        return upgradeImageView;
    }

    // Setter

    public void setAlreadyUsedOnce(boolean alreadyUsedOnce) {
        this.alreadyUsedOnce = alreadyUsedOnce;
    }

    // Method used to use an Upgrade
    public int doUpgrades(int coins) throws ImageNotFoundException {

        // Change Image to the "deactivated" Upgrade Image
        this.upgradeImageView.setImage(ImageUtil.getImageFromResources(this.filenameUpgradeUsed));

        // Disable the ImageView
        this.upgradeImageView.setDisable(true);

        // Set the Used variable to true
        this.setAlreadyUsedOnce(true);

        // Decrease the coins score according to the upgrade costs
        coins -= this.getCoinsNeeded();

        // return the new coin score
        return coins;
    }
}
