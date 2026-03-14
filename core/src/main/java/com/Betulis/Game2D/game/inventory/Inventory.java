package com.Betulis.Game2D.game.inventory;

public class Inventory {
    private final ItemDefinition[] slots = new ItemDefinition[16];

    /** Inserts item into the first empty slot. Returns false if full. */
    public boolean addItem(ItemDefinition item) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                slots[i] = item;
                return true;
            }
        }
        return false;
    }

    public ItemDefinition getSlot(int i)                   { return slots[i]; }
    public void setSlot(int i, ItemDefinition item)        { slots[i] = item; }
    public void clearSlot(int i)                           { slots[i] = null; }
    public int size()                                      { return slots.length; }
}
