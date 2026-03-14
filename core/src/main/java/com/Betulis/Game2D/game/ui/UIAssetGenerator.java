package com.Betulis.Game2D.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class UIAssetGenerator {

    public static void generateIfAbsent() {
        if (!Gdx.files.local("ui/slot.png").exists()) {
            Gdx.files.local("ui/").mkdirs();
            writeSlot();
            writeFireball();
            writeLightning();
            writeXPBars();
            writeEquipSlots();
            System.out.println("UIAssetGenerator: generated UI assets in local/ui/");
        }
        if (!Gdx.files.local("ui/pickup_prompt.png").exists()) {
            Gdx.files.local("ui/").mkdirs();
            writePickupPrompt();
        }
        if (!Gdx.files.local("ui/panels/spellbook_panel.png").exists()) {
            Gdx.files.local("ui/panels/").mkdirs();
            writePanelBg("ui/panels/spellbook_panel.png", 300, 200);
            writePanelBg("ui/panels/character_panel.png", 200, 480);
            writePanelBg("ui/panels/talent_panel.png", 164, 184);
        }
    }

    private static void writePanelBg(String path, int w, int h) {
        Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        // Body fill
        p.setColor(toColor(0x141426EB));
        p.fill();
        // Title bar (top 18px) — Pixmap Y=0 is top
        p.setColor(toColor(0x26264DFF));
        p.fillRectangle(0, 0, w, 18);
        // Border
        drawBorder(p, w, h, 0x8888aaff, 1);
        PixmapIO.writePNG(Gdx.files.local(path), p);
        p.dispose();
    }

    private static void writePickupPrompt() {
        Pixmap p = filledWithBorder(24, 24, 0x1a1a2eff, 0x8888aaff, 2);
        drawCenteredLabel(p, 24, 24, "F");
        PixmapIO.writePNG(Gdx.files.local("ui/pickup_prompt.png"), p);
        p.dispose();
    }

    private static void writeSlot() {
        Pixmap p = filledWithBorder(40, 40, 0x1a1a2eff, 0x8888aaff, 2);
        PixmapIO.writePNG(Gdx.files.local("ui/slot.png"), p);
        p.dispose();
    }

    private static void writeFireball() {
        Pixmap p = filledWithLabel(36, 36, 0xff6600ff, "F");
        PixmapIO.writePNG(Gdx.files.local("ui/fireball_icon.png"), p);
        p.dispose();
    }

    private static void writeLightning() {
        Pixmap p = filledWithLabel(36, 36, 0x4488ffff, "L");
        PixmapIO.writePNG(Gdx.files.local("ui/lightning_icon.png"), p);
        p.dispose();
    }

    private static void writeXPBars() {
        Pixmap full = filledWithBorder(78, 16, 0x00cc44ff, 0x008822ff, 1);
        PixmapIO.writePNG(Gdx.files.local("ui/xp_full.png"), full);
        full.dispose();

        Pixmap empty = filledWithBorder(78, 16, 0x223322ff, 0x446644ff, 1);
        PixmapIO.writePNG(Gdx.files.local("ui/xp_empty.png"), empty);
        empty.dispose();
    }

    private static void writeEquipSlots() {
        String[][] slots = {
            {"equip_head.png",     "HD"},
            {"equip_shoulder.png", "SH"},
            {"equip_back.png",     "BK"},
            {"equip_chest.png",    "CH"},
            {"equip_wrist.png",    "WR"},
            {"equip_hands.png",    "HN"},
            {"equip_waist.png",    "WA"},
            {"equip_legs.png",     "LG"},
            {"equip_feet.png",     "FT"},
            {"equip_ring.png",     "RG"},
            {"equip_trinket.png",  "TR"},
            {"equip_weapon.png",   "WP"},
            {"equip_relic.png",    "RL"},
        };
        for (String[] slot : slots) {
            Pixmap p = equipSlot(slot[1]);
            PixmapIO.writePNG(Gdx.files.local("ui/" + slot[0]), p);
            p.dispose();
        }
    }

    private static Pixmap equipSlot(String label) {
        Pixmap p = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
        p.setColor(toColor(0x2a1a2eff));
        p.fill();
        drawBorder(p, 40, 40, 0xaa88aaff, 2);
        drawCenteredLabel(p, 40, 40, label);
        return p;
    }

    private static Pixmap filledWithBorder(int w, int h, int fillRGBA, int borderRGBA, int borderWidth) {
        Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        p.setColor(toColor(fillRGBA));
        p.fill();
        drawBorder(p, w, h, borderRGBA, borderWidth);
        return p;
    }

    private static Pixmap filledWithLabel(int w, int h, int fillRGBA, String label) {
        Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        p.setColor(toColor(fillRGBA));
        p.fill();
        drawCenteredLabel(p, w, h, label);
        return p;
    }

    private static void drawBorder(Pixmap p, int w, int h, int borderRGBA, int thickness) {
        p.setColor(toColor(borderRGBA));
        for (int t = 0; t < thickness; t++) {
            p.drawRectangle(t, t, w - 2 * t, h - 2 * t);
        }
    }

    /** Draws a simple pixel-art letter centered in the pixmap. */
    private static void drawCenteredLabel(Pixmap p, int w, int h, String label) {
        p.setColor(Color.WHITE);
        // Draw each character as a 3x5 pixel block
        int charW = 3, charH = 5, gap = 1;
        int totalW = label.length() * charW + (label.length() - 1) * gap;
        int startX = (w - totalW) / 2;
        int startY = (h - charH) / 2;
        for (int ci = 0; ci < label.length(); ci++) {
            int cx = startX + ci * (charW + gap);
            drawPixelChar(p, label.charAt(ci), cx, startY);
        }
    }

    private static void drawPixelChar(Pixmap p, char c, int x, int y) {
        // Minimal 3×5 pixel font for letters A-Z and digits 0-9
        boolean[][] pattern = getCharPattern(c);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                if (row < pattern.length && col < pattern[row].length && pattern[row][col]) {
                    p.drawPixel(x + col, y + row);
                }
            }
        }
    }

    private static boolean[][] getCharPattern(char c) {
        switch (Character.toUpperCase(c)) {
            case 'F': return new boolean[][]{{true,true,true},{true,true,false},{true,true,false},{true,false,false},{true,false,false}};
            case 'L': return new boolean[][]{{true,false,false},{true,false,false},{true,false,false},{true,false,false},{true,true,true}};
            case 'H': return new boolean[][]{{true,false,true},{true,false,true},{true,true,true},{true,false,true},{true,false,true}};
            case 'D': return new boolean[][]{{true,true,false},{true,false,true},{true,false,true},{true,false,true},{true,true,false}};
            case 'S': return new boolean[][]{{false,true,true},{true,false,false},{false,true,false},{false,false,true},{true,true,false}};
            case 'B': return new boolean[][]{{true,true,false},{true,false,true},{true,true,false},{true,false,true},{true,true,false}};
            case 'K': return new boolean[][]{{true,false,true},{true,true,false},{true,true,false},{true,false,true},{true,false,true}};
            case 'C': return new boolean[][]{{false,true,true},{true,false,false},{true,false,false},{true,false,false},{false,true,true}};
            case 'W': return new boolean[][]{{true,false,true},{true,false,true},{true,false,true},{true,true,true},{false,true,false}};
            case 'R': return new boolean[][]{{true,true,false},{true,false,true},{true,true,false},{true,false,true},{true,false,true}};
            case 'A': return new boolean[][]{{false,true,false},{true,false,true},{true,true,true},{true,false,true},{true,false,true}};
            case 'N': return new boolean[][]{{true,false,true},{true,true,true},{true,true,true},{true,false,true},{true,false,true}};
            case 'G': return new boolean[][]{{false,true,true},{true,false,false},{true,false,true},{true,false,true},{false,true,true}};
            case 'T': return new boolean[][]{{true,true,true},{false,true,false},{false,true,false},{false,true,false},{false,true,false}};
            case 'P': return new boolean[][]{{true,true,false},{true,false,true},{true,true,false},{true,false,false},{true,false,false}};
            case 'E': return new boolean[][]{{true,true,true},{true,false,false},{true,true,false},{true,false,false},{true,true,true}};
            case 'I': return new boolean[][]{{true,true,true},{false,true,false},{false,true,false},{false,true,false},{true,true,true}};
            case 'O': return new boolean[][]{{false,true,false},{true,false,true},{true,false,true},{true,false,true},{false,true,false}};
            case 'Q': return new boolean[][]{{false,true,false},{true,false,true},{true,false,true},{true,true,true},{false,true,true}};
            case 'Y': return new boolean[][]{{true,false,true},{true,false,true},{false,true,false},{false,true,false},{false,true,false}};
            case 'U': return new boolean[][]{{true,false,true},{true,false,true},{true,false,true},{true,false,true},{false,true,false}};
            case 'J': return new boolean[][]{{false,false,true},{false,false,true},{false,false,true},{true,false,true},{false,true,false}};
            case 'Z': return new boolean[][]{{true,true,true},{false,false,true},{false,true,false},{true,false,false},{true,true,true}};
            case 'X': return new boolean[][]{{true,false,true},{true,false,true},{false,true,false},{true,false,true},{true,false,true}};
            case 'V': return new boolean[][]{{true,false,true},{true,false,true},{true,false,true},{true,false,true},{false,true,false}};
            default:  return new boolean[][]{{true,true,true},{true,false,true},{true,false,true},{true,false,true},{true,true,true}};
        }
    }

    private static Color toColor(int rgba) {
        float r = ((rgba >> 24) & 0xFF) / 255f;
        float g = ((rgba >> 16) & 0xFF) / 255f;
        float b = ((rgba >> 8)  & 0xFF) / 255f;
        float a = ( rgba        & 0xFF) / 255f;
        return new Color(r, g, b, a);
    }
}
