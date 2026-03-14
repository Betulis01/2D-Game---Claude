package com.Betulis.Game2D.engine.config;

public class EntityConfig {
    public String id;
    public String name;
    public Sprite sprite;
    public Collision collision;
    public Hurtbox hurtbox;
    public Hitbox hitbox;
    public Stats stats;

    public static class Sprite {
        public String sheet;
        public int width, height, frames, directions;
        public float frameDuration, idleFrameDuration;
    }

    public static class Collision {
        public int width, height;
        public float offsetX, offsetY;
    }

    public static class Hurtbox {
        public int width, height;
        public float offsetX, offsetY;
    }

    public static class Hitbox {
        public int width, height;
        public float offsetX, offsetY;
    }

    public static class Stats {
        public float moveSpeed, maxHealth, attack, defense, attackSpeed, critChance, damage, cooldown, duration;
        public float xp, maxXp;
        public int strength, agility, stamina;
    }
}
