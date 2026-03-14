package com.Betulis.Game2D.game.inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Equipment {
    private final Map<String, ItemDefinition> slots = new HashMap<>();

    public void equip(String slotKey, ItemDefinition item) { slots.put(slotKey, item); }
    public void unequip(String slotKey)                    { slots.remove(slotKey); }
    public ItemDefinition getSlot(String slotKey)          { return slots.get(slotKey); }
    public Collection<ItemDefinition> getAll()             { return slots.values(); }
}
