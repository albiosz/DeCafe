package com.example.decafe.assets;

public enum ImageAssets {
    COFFEE_MACHINE_WITH_COFFEE("coffeeMachineWithCoffee.png"),
    COFFEE_MACHINE("coffeeMachine.png"),
    KITCHEN_AID_USED("kitchenAidUsed.png"),
    KITCHEN_AID("kitchenAid.png"),
    COFFEE_UPGRADE("coffeeUpgrade.png"),
    COFFEE_USED("coffeeUsed.png"),
    CAKE_UPGRADE("cakeUpgrade.png"),
    CAKE_USED("cakeUsed.png"),
    UPGRADE_SKATES("upgradeSkates.png"),
    UPGRADE_SKATES_USED("upgradeSkatesUsed.png"),
    THREE_COINS("3coins.png"),  // Image of small amount of money earned
    FOUR_COINS("4coins.png"),   // Image of normal amount of money earned
    DOLLAR("5coins.png"),       // Image of huge amount of money earned
    MUG_TAB_PIC("mugTabPic.png"),
    COIN("coin.png"),
    CUSTOMER_SMILEY_GREEN("smileygreen.png"),
    CUSTOMER_SMILEY_YELLOW("smileyyellow.png"),
    CUSTOMER_SMILEY_RED("smileyred.png"),
    BUBBLE_COFFEE_TOP_RIGHT("bubbleCoffeeTopRight.png"),
    BUBBLE_COFFEE_TOP_LEFT("bubbleCoffeeTopLeft.png"),
    BUBBLE_COFFEE_BOTTOM_RIGHT("bubbleCoffeeBottomRight.png"),
    BUBBLE_CAKE_TOP_RIGHT("bubbleCakeTopRight.png"),
    BUBBLE_CAKE_TOP_LEFT("bubbleCakeTopLeft.png"),
    BUBBLE_CAKE_BOTTOM_RIGHT("bubbleCakeBottomRight.png");


    private final String image;

    ImageAssets(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
