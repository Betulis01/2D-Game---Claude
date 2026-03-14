package com.Betulis.Game2D.game.ui.panels;

import java.util.ArrayList;
import java.util.List;

import com.Betulis.Game2D.game.ui.core.UIPanel;
import com.Betulis.Game2D.game.ui.data.SpellDefinition;
import com.Betulis.Game2D.game.ui.widgets.SpellSlot;
import com.Betulis.Game2D.game.ui.widgets.UILabel;
import com.badlogic.gdx.graphics.Texture;

public class SpellBookPanel extends UIPanel {
    private final List<SpellSlot> spellSlots = new ArrayList<>();

    public SpellBookPanel(float screenW, float screenH, Texture slotBg, SpellDefinition fireball, SpellDefinition lightning) {
        super("Spellbook", screenW / 2f - 150f, screenH / 2f - 200f, 300f, 200f);

        loadPanelBg("ui/panels/spellbook_panel.png");

        float startX = x + 20f;
        float slotY = y + 100f;
        float labelY = slotY - 4f;

        // Fireball slot
        SpellSlot fbSlot = new SpellSlot(startX, slotY, 20f, true, slotBg);
        fbSlot.setSpell(fireball);
        spellSlots.add(fbSlot);
        add(fbSlot);
        add(new UILabel("Fireball", startX, labelY));

        // Lightning slot
        float lbX = startX + 100f;
        SpellSlot lbSlot = new SpellSlot(lbX, slotY, 40f, true, slotBg);
        lbSlot.setSpell(lightning);
        spellSlots.add(lbSlot);
        add(lbSlot);
        add(new UILabel("Lightning", lbX, labelY));
    }

    public List<SpellSlot> getSpellSlots() { return spellSlots; }
}
