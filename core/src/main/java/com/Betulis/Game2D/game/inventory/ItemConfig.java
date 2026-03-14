package com.Betulis.Game2D.game.inventory;

public class ItemConfig {
    public String name;
    public int itemLevel;
    public int requiredLevel;
    public String rarity;
    public String slot;
    public String type;
    public Damage damage;
    public float speed;
    public Stats stats;

    public static class Damage {
        public int min, max;
    }

    public static class Stats {
        public int strength, agility;
    }
}
