package com.example.decafe.assets;

public enum CafeAssets {
    COFFEE("coffee"),
    CAKE("cake"),
    NONE("none"),
    PLAYER("player");

    private final String name;

    public static CafeAssets getValueOf(String name) {
        for (CafeAssets asset : CafeAssets.values()) {
            if (asset.name().equalsIgnoreCase(name)) {
                return asset;
            }
        }
        throw new IllegalArgumentException("No enum constant found for " + name);
    }

    CafeAssets(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
