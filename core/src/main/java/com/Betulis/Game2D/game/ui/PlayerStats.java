package com.Betulis.Game2D.game.ui;

import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.game.inventory.Equipment;
import com.Betulis.Game2D.game.inventory.ItemConfig;
import com.Betulis.Game2D.game.inventory.ItemDefinition;

public class PlayerStats {
    private int baseStrength, baseAgility, baseStamina;
    private float baseSpeed, baseDamage;

    private int strength, agility, stamina;
    private float speed, damage;

    public void init(EntityConfig.Stats s) {
        baseStrength = s.strength;
        baseAgility  = s.agility;
        baseStamina  = s.stamina;
        baseSpeed    = s.moveSpeed;
        baseDamage   = s.attack;
        recalculate(new Equipment());
    }

    public void recalculate(Equipment eq) {
        strength = baseStrength;
        agility  = baseAgility;
        stamina  = baseStamina;
        speed    = baseSpeed;
        damage   = baseDamage;

        for (ItemDefinition item : eq.getAll()) {
            ItemConfig cfg = item.getConfig();
            if (cfg == null) continue;
            if (cfg.stats != null) {
                strength += cfg.stats.strength;
                agility  += cfg.stats.agility;
                stamina  += cfg.stats.stamina;
            }
            if (cfg.damage != null) {
                damage += (cfg.damage.min + cfg.damage.max) / 2f;
            }
            speed += cfg.speed;
        }
    }

    public int   getStrength() { return strength; }
    public int   getAgility()  { return agility; }
    public int   getStamina()  { return stamina; }
    public float getSpeed()    { return speed; }
    public float getDamage()   { return damage; }
}
