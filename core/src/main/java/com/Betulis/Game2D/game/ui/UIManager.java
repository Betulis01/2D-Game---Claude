package com.Betulis.Game2D.game.ui;

import java.util.HashMap;
import java.util.Map;

import com.Betulis.Game2D.engine.system.Game;
import com.Betulis.Game2D.game.components.stats.PlayerXP;
import com.Betulis.Game2D.game.input.InputBindings;
import com.Betulis.Game2D.game.ui.core.UIPanel;
import com.Betulis.Game2D.game.ui.data.SpellBar;
import com.Betulis.Game2D.game.ui.data.SpellDefinition;
import com.Betulis.Game2D.game.ui.panels.BagPanel;
import com.Betulis.Game2D.game.ui.panels.CharacterPanel;
import com.Betulis.Game2D.game.ui.panels.MouseSpellBarPanel;
import com.Betulis.Game2D.game.ui.panels.PanelMenuBar;
import com.Betulis.Game2D.game.ui.panels.SpellBarPanel;
import com.Betulis.Game2D.game.ui.panels.SpellBookPanel;
import com.Betulis.Game2D.game.ui.panels.TalentPanel;
import com.Betulis.Game2D.game.ui.panels.XPBarPanel;
import com.Betulis.Game2D.game.ui.widgets.SpellSlot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UIManager {
    private BitmapFont font;
    private InputBindings input;

    private SpellBarPanel spellBar;
    private MouseSpellBarPanel mouseSpellBar;
    private PanelMenuBar panelMenu;
    private SpellBookPanel spellBook;
    private BagPanel bag;
    private CharacterPanel character;
    private XPBarPanel xpBar;
    private TalentPanel talents;

    private SpellBar spellBarData;
    private SpellBar mouseSpellBarData;

    private SpellDefinition dragging;
    private float dragX, dragY;

    private Texture whitePixel;
    private Texture slotBg;
    private Texture xpFull;
    private Texture xpEmpty;
    private Texture fireballIcon;
    private Texture lightningIcon;
    private Map<String, Texture> equipTextures = new HashMap<>();

    private int screenW, screenH;

    public void init(Game game, PlayerXP playerXP) {
        this.input = game.getInput();
        this.font = new BitmapFont();
        this.screenW = game.getScreenWidth();
        this.screenH = game.getScreenHeight();

        whitePixel = createWhitePixel();
        UIPanel.panelPixel = whitePixel;

        loadTextures();

        spellBarData      = new SpellBar();
        mouseSpellBarData = new SpellBar();

        SpellDefinition fireball = new SpellDefinition(
            "fireball", "Fireball", "data/config/abilities/fireball.json", fireballIcon);
        SpellDefinition lightning = new SpellDefinition(
            "lightning_bolt", "Lightning Bolt", "data/config/abilities/lightning_bolt.json", lightningIcon);

        spellBar      = new SpellBarPanel(screenW, spellBarData, slotBg, input);
        mouseSpellBar = new MouseSpellBarPanel(spellBar, mouseSpellBarData, slotBg, input);
        spellBook     = new SpellBookPanel(screenW, screenH, slotBg, fireball, lightning);
        bag           = new BagPanel(screenW, screenH, slotBg);
        character     = new CharacterPanel(screenW, screenH, equipTextures, slotBg);
        talents       = new TalentPanel(screenW, screenH, slotBg);
        xpBar         = new XPBarPanel(screenW, xpFull, xpEmpty);
        xpBar.setPlayerXP(playerXP);

        // Panel menu — right-aligned at same y as spell bar, to the right of mouse bar
        float menuY = spellBar.getY();
        panelMenu = new PanelMenuBar(0, menuY, whitePixel, input);
        panelMenu.addButton("Bag",   InputBindings.Action.TOGGLE_BAG,       bag);
        panelMenu.addButton("Char",  InputBindings.Action.TOGGLE_CHARACTER, character);
        panelMenu.addButton("Book",  InputBindings.Action.TOGGLE_SPELLBOOK, spellBook);
        panelMenu.addButton("Tal",   InputBindings.Action.TOGGLE_TALENT,    talents);
        panelMenu.setRightEdge(screenW - 10f);

        // Wire drag listeners on spellbook drag-source slots
        SpellSlot.DragListener dragListener = this::startDrag;
        for (SpellSlot slot : spellBook.getSpellSlots()) {
            slot.setDragListener(dragListener);
        }
    }

    private Texture createWhitePixel() {
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(Color.WHITE);
        pm.fill();
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    private void loadTextures() {
        slotBg        = loadUI("/assets/ui/slot.png");
        xpFull        = loadUI("ui/xp_full.png");
        xpEmpty       = loadUI("ui/xp_empty.png");
        fireballIcon  = loadUI("ui/fireball_icon.png");
        lightningIcon = loadUI("ui/lightning_icon.png");

        String[] equipNames = {
            "equip_head", "equip_shoulder", "equip_back", "equip_chest", "equip_wrist",
            "equip_hands", "equip_waist", "equip_legs", "equip_feet",
            "equip_ring", "equip_trinket", "equip_weapon", "equip_relic"
        };
        for (String name : equipNames) {
            equipTextures.put(name, loadUI("ui/" + name + ".png"));
        }
    }

    private Texture loadUI(String path) {
        if (Gdx.files.local(path).exists()) {
            return new Texture(Gdx.files.local(path));
        }
        return whitePixel;
    }

    public void update(float dt) {
        // Keybind toggles
        if (input.isPressed(InputBindings.Action.TOGGLE_SPELLBOOK)) spellBook.toggle();
        if (input.isPressed(InputBindings.Action.TOGGLE_BAG))       bag.toggle();
        if (input.isPressed(InputBindings.Action.TOGGLE_CHARACTER))  character.toggle();
        if (input.isPressed(InputBindings.Action.TOGGLE_TALENT))     talents.toggle();

        dragX = Gdx.input.getX();
        dragY = screenH - Gdx.input.getY();

        if (Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
            handleMouseDown(dragX, dragY);
        }
        if (!Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT) && dragging != null) {
            handleMouseUp(dragX, dragY);
        }

        spellBar.update(dt);
        mouseSpellBar.update(dt);
        spellBook.update(dt);
        bag.update(dt);
        character.update(dt);
        talents.update(dt);
    }

    public void render(SpriteBatch batch) {
        xpBar.render(batch, font);
        spellBar.render(batch, font);
        mouseSpellBar.render(batch, font);
        panelMenu.render(batch, font);
        spellBook.render(batch, font);
        bag.render(batch, font);
        character.render(batch, font);
        talents.render(batch, font);

        if (dragging != null && dragging.getIcon() != null) {
            batch.setColor(Color.WHITE);
            batch.draw(dragging.getIcon(), dragX - 18, dragY - 18, 36, 36);
        }
    }

    public void handleMouseDown(float mx, float my) {
        if (panelMenu.onMouseDown(mx, my)) return;
        if (spellBook.onMouseDown(mx, my)) return;
        if (bag.onMouseDown(mx, my)) return;
        if (character.onMouseDown(mx, my)) return;
        if (talents.onMouseDown(mx, my)) return;
        if (mouseSpellBar.onMouseDown(mx, my)) return;
        spellBar.onMouseDown(mx, my);
    }

    public void handleMouseUp(float mx, float my) {
        if (dragging != null) {
            if (!spellBar.tryDrop(mx, my, dragging)) {
                mouseSpellBar.tryDrop(mx, my, dragging);
            }
            dragging = null;
        }
    }

    public boolean isDragging() { return dragging != null; }

    private void startDrag(SpellDefinition spell, SpellSlot source) {
        this.dragging = spell;
    }

    public SpellBar getSpellBarData()       { return spellBarData; }
    public SpellBar getMouseSpellBarData()  { return mouseSpellBarData; }
}
