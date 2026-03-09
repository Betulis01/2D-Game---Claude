package com.Betulis.Game2D.game.ui.data;

public class SpellBar {
    private static final int SLOTS = 4;
    private final SpellDefinition[] slots = new SpellDefinition[SLOTS];

    public SpellDefinition getSlot(int index) {
        if (index < 0 || index >= SLOTS) return null;
        return slots[index];
    }

    public void setSlot(int index, SpellDefinition spell) {
        if (index < 0 || index >= SLOTS) return;
        slots[index] = spell;
    }

    public int size() { return SLOTS; }
}
