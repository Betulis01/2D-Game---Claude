package com.Betulis.Game2D.game.ui;

import java.util.HashMap;
import java.util.Map;

import com.Betulis.Game2D.engine.config.ConfigLoader;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.system.Game;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Scene;
import com.Betulis.Game2D.game.components.movement.PlayerController;
import com.Betulis.Game2D.game.components.stats.PlayerXP;
import com.Betulis.Game2D.game.input.InputBindings;
import com.Betulis.Game2D.game.inventory.Equipment;
import com.Betulis.Game2D.game.inventory.Inventory;
import com.Betulis.Game2D.game.inventory.ItemDefinition;
import com.Betulis.Game2D.game.prefabs.items.WorldItemPrefab;
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
import com.Betulis.Game2D.game.ui.widgets.EquipmentSlot;
import com.Betulis.Game2D.game.ui.widgets.ItemSlot;
import com.Betulis.Game2D.game.ui.widgets.SpellSlot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UIManager {
    private BitmapFont font;
    private InputBindings input;
    private Game game;

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

    // Spell drag
    private SpellDefinition dragging;
    private float dragX, dragY;

    // Item drag
    private ItemDefinition draggingItem;
    private int draggingItemSlot = -1;

    // Equipment drag
    private ItemDefinition draggingEquipItem;
    private EquipmentSlot  draggingFromEquipSlot;

    private Inventory inventory;
    private Equipment equipment;
    private PlayerStats playerStats;

    private Texture whitePixel;
    private Texture slotBg;
    private Texture xpFull;
    private Texture xpEmpty;
    private Texture fireballIcon;
    private Texture lightningIcon;
    private Texture btnBag;
    private Texture btnChar;
    private Texture btnBook;
    private Texture btnTal;
    private Map<String, Texture> equipTextures = new HashMap<>();

    private int screenW, screenH;

    public void init(Game game, PlayerXP playerXP) {
        this.game    = game;
        this.input   = game.getInput();
        this.font    = new BitmapFont();
        this.screenW = game.getScreenWidth();
        this.screenH = game.getScreenHeight();

        whitePixel = createWhitePixel();
        UIPanel.panelPixel = whitePixel;

        loadTextures();

        inventory = new Inventory();

        spellBarData      = new SpellBar();
        mouseSpellBarData = new SpellBar();

        SpellDefinition fireball = new SpellDefinition(
            "fireball", "Fireball", "data/config/abilities/fireball.json", fireballIcon);
        SpellDefinition lightning = new SpellDefinition(
            "lightning_bolt", "Lightning Bolt", "data/config/abilities/lightning_bolt.json", lightningIcon);

        spellBar      = new SpellBarPanel(screenW, spellBarData, slotBg, input);
        mouseSpellBar = new MouseSpellBarPanel(spellBar, mouseSpellBarData, slotBg, input);
        spellBook     = new SpellBookPanel(screenW, screenH, slotBg, fireball, lightning);
        bag           = new BagPanel(screenW, screenH, slotBg, inventory);
        character     = new CharacterPanel(screenW, screenH, equipTextures, slotBg);
        talents       = new TalentPanel(screenW, screenH, slotBg);
        xpBar         = new XPBarPanel(screenW, xpFull, xpEmpty);
        xpBar.setPlayerXP(playerXP);

        // Panel menu
        float menuY = spellBar.getY();
        panelMenu = new PanelMenuBar(0, menuY, input);
        panelMenu.addButton("Bag",  InputBindings.Action.TOGGLE_BAG,       bag,       btnBag);
        panelMenu.addButton("Char", InputBindings.Action.TOGGLE_CHARACTER, character, btnChar);
        panelMenu.addButton("Book", InputBindings.Action.TOGGLE_SPELLBOOK, spellBook, btnBook);
        panelMenu.addButton("Tal",  InputBindings.Action.TOGGLE_TALENT,    talents,   btnTal);
        panelMenu.setRightEdge(screenW - 10f);

        // Wire spell drag listeners on spellbook
        SpellSlot.DragListener spellDragListener = this::startDrag;
        for (SpellSlot slot : spellBook.getSpellSlots()) {
            slot.setDragListener(spellDragListener);
        }

        // Wire item drag listeners on bag slots
        ItemSlot.DragListener itemDragListener = this::startItemDrag;
        for (ItemSlot slot : bag.getItemSlots()) {
            slot.setDragListener(itemDragListener);
        }

        // Equipment + PlayerStats
        equipment   = new Equipment();
        playerStats = new PlayerStats();
        EntityConfig playerCfg = new ConfigLoader().load("data/config/player.json");
        playerStats.init(playerCfg.stats);
        character.setPlayerStats(playerStats);

        EquipmentSlot.DragListener equipDragListener = this::startEquipDrag;
        for (EquipmentSlot slot : character.getEquipSlots()) {
            slot.setDragListener(equipDragListener);
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
        fireballIcon  = loadUI("/assets/ui/fireball_icon.png");
        lightningIcon = loadUI("/assets/ui/lightning_icon.png");
        btnBag        = loadUI("/assets/ui/bag.png");
        btnChar       = loadUI("/assets/ui/char.png");
        btnBook       = loadUI("/assets/ui/spellbook.png");
        btnTal        = loadUI("/assets/ui/talent.png");

        String[] equipNames = {
            "head_equip_icon", "shoulder_equip_icon", "back_equip_icon", "chest_equip_icon", "wrist_equip_icon",
            "hands_equip_icon", "waist_equip_icon", "legs_equip_icon", "feet_equip_icon",
            "ring_1_equip_icon", "ring_2_equip_icon", "trinket_equip_icon", "weapon_equip_icon", "relic_equip_icon"
        };
        for (String name : equipNames) {
            equipTextures.put(name, loadUI("assets/ui/panels/icons/" + name + ".png"));
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

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            handleMouseDown(dragX, dragY);
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            handleRightClick(dragX, dragY);
        }
        boolean mouseReleased = !Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        if (mouseReleased && isDragging()) {
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
        if (draggingItem != null && draggingItem.getIcon() != null) {
            batch.setColor(Color.WHITE);
            batch.draw(draggingItem.getIcon(), dragX - 16, dragY - 16, 32, 32);
        }
        if (draggingEquipItem != null && draggingEquipItem.getIcon() != null) {
            batch.setColor(Color.WHITE);
            batch.draw(draggingEquipItem.getIcon(), dragX - 16, dragY - 16, 32, 32);
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
        // Handle spell drag drop
        if (dragging != null) {
            if (!spellBar.tryDrop(mx, my, dragging)) {
                mouseSpellBar.tryDrop(mx, my, dragging);
            }
            dragging = null;
        }

        // Handle item drag drop
        if (draggingItem != null) {
            if (bag.isVisible() && bag.contains(mx, my)) {
                boolean placed = false;
                for (ItemSlot slot : bag.getItemSlots()) {
                    if (slot.contains(mx, my)) {
                        ItemDefinition existing = inventory.getSlot(slot.getSlotIndex());
                        inventory.setSlot(slot.getSlotIndex(), draggingItem);
                        if (existing != null && draggingItemSlot >= 0) {
                            inventory.setSlot(draggingItemSlot, existing);
                        }
                        placed = true;
                        break;
                    }
                }
                if (!placed && draggingItemSlot >= 0) {
                    inventory.setSlot(draggingItemSlot, draggingItem);
                }
                bag.refresh(inventory);
            } else {
                spawnWorldItemAtPlayer(draggingItem);
                bag.refresh(inventory);
            }
            draggingItem = null;
            draggingItemSlot = -1;
        }

        // Handle equipment drag drop
        if (draggingEquipItem != null) {
            boolean placed = false;
            // Try compatible equip slot
            if (character.isVisible()) {
                for (EquipmentSlot es : character.getEquipSlots()) {
                    if (es.contains(mx, my) && es.accepts(draggingEquipItem)) {
                        ItemDefinition displaced = es.getItem();
                        es.setItem(draggingEquipItem);
                        equipment.equip(es.getSlotKey(), draggingEquipItem);
                        if (displaced != null) {
                            int free = inventory.findEmpty();
                            if (free >= 0) inventory.setSlot(free, displaced);
                        }
                        placed = true;
                        break;
                    }
                }
            }
            // Try bag slot
            if (!placed && bag.isVisible()) {
                for (ItemSlot slot : bag.getItemSlots()) {
                    if (slot.contains(mx, my)) {
                        ItemDefinition existing = inventory.getSlot(slot.getSlotIndex());
                        inventory.setSlot(slot.getSlotIndex(), draggingEquipItem);
                        if (existing != null) {
                            // displaced existing — simplification: it's lost if no room
                        }
                        placed = true;
                        break;
                    }
                }
            }
            if (!placed) spawnWorldItemAtPlayer(draggingEquipItem);
            bag.refresh(inventory);
            playerStats.recalculate(equipment);
            draggingEquipItem     = null;
            draggingFromEquipSlot = null;
        }
    }

    private void handleRightClick(float mx, float my) {
        // RMB on bag slot → auto-equip item
        if (bag.isVisible() && bag.contains(mx, my)) {
            for (ItemSlot slot : bag.getItemSlots()) {
                if (slot.contains(mx, my) && slot.getItem() != null) {
                    autoEquip(slot.getItem(), slot.getSlotIndex());
                    return;
                }
            }
        }
        // RMB on equipment slot → auto-unequip
        if (character.isVisible() && character.contains(mx, my)) {
            for (EquipmentSlot slot : character.getEquipSlots()) {
                if (slot.contains(mx, my) && slot.getItem() != null) {
                    autoUnequip(slot);
                    return;
                }
            }
        }
    }

    private void autoEquip(ItemDefinition item, int bagSlotIndex) {
        // Try first compatible empty slot
        for (EquipmentSlot es : character.getEquipSlots()) {
            if (es.accepts(item) && es.getItem() == null) {
                es.setItem(item);
                equipment.equip(es.getSlotKey(), item);
                inventory.clearSlot(bagSlotIndex);
                bag.refresh(inventory);
                playerStats.recalculate(equipment);
                return;
            }
        }
        // Swap with first compatible occupied slot
        for (EquipmentSlot es : character.getEquipSlots()) {
            if (es.accepts(item)) {
                ItemDefinition old = es.getItem();
                es.setItem(item);
                equipment.equip(es.getSlotKey(), item);
                inventory.setSlot(bagSlotIndex, old);
                bag.refresh(inventory);
                playerStats.recalculate(equipment);
                return;
            }
        }
    }

    private void autoUnequip(EquipmentSlot es) {
        int free = inventory.findEmpty();
        if (free < 0) return; // bag full
        inventory.setSlot(free, es.getItem());
        equipment.unequip(es.getSlotKey());
        es.setItem(null);
        bag.refresh(inventory);
        playerStats.recalculate(equipment);
    }

    private void startEquipDrag(ItemDefinition item, EquipmentSlot source) {
        draggingEquipItem     = item;
        draggingFromEquipSlot = source;
        equipment.unequip(source.getSlotKey());
        playerStats.recalculate(equipment);
    }

    public boolean isDragging() {
        return dragging != null || draggingItem != null || draggingEquipItem != null;
    }

    public boolean isMouseOverUI(float mx, float my) {
        if (panelMenu.isVisible()  && panelMenu.contains(mx, my))  return true;
        if (spellBook.isVisible()  && spellBook.contains(mx, my))  return true;
        if (bag.isVisible()        && bag.contains(mx, my))        return true;
        if (character.isVisible()  && character.contains(mx, my))  return true;
        if (talents.isVisible()    && talents.contains(mx, my))    return true;
        if (mouseSpellBar.contains(mx, my))                        return true;
        if (spellBar.contains(mx, my))                             return true;
        return false;
    }

    private void startDrag(SpellDefinition spell, SpellSlot source) {
        this.dragging = spell;
    }

    private void startItemDrag(ItemDefinition item, int slotIndex) {
        this.draggingItem     = item;
        this.draggingItemSlot = slotIndex;
        inventory.clearSlot(slotIndex);
        bag.refresh(inventory);
    }

    private void spawnWorldItemAtPlayer(ItemDefinition item) {
        Scene scene = game.getScene();
        float px = 100, py = 100;
        for (GameObject obj : scene.getObjects()) {
            if (obj.getComponent(PlayerController.class) != null) {
                px = obj.getTransform().getWorldX();
                py = obj.getTransform().getWorldY();
                break;
            }
        }
        float ox = (float) (Math.random() * 40 - 20);
        float oy = (float) (Math.random() * 40 - 20);
        scene.addObject(WorldItemPrefab.create(px + ox, py + oy, item));
    }

    public void refreshBag() {
        bag.refresh(inventory);
    }

    public Inventory getInventory()             { return inventory; }
    public SpellBar getSpellBarData()           { return spellBarData; }
    public SpellBar getMouseSpellBarData()      { return mouseSpellBarData; }
}
