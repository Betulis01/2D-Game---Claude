package com.Betulis.Game2D.engine.audio;

public class SoundDef {
    public String id;
    public String path;
    public String category;       // "MUSIC" | "GAMEPLAY" | "MENU"
    public float baseVolume = 1f;
    public float maxDistance = 400f;  // GAMEPLAY only
}
