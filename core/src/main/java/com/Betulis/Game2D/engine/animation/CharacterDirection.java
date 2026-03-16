package com.Betulis.Game2D.engine.animation;

/** 8-way character direction. Enum order MUST remain N, NE, E, SE, S, SW, W, NW. */
public enum CharacterDirection {
    N, NE, E, SE, S, SW, W, NW;

    /**
     * Derives direction from a movement vector.
     * Uses compass bearing: 0=N, 90=E, 180=S, 270=W.
     */
    public static CharacterDirection fromVector(float dx, float dy) {
        double angle = Math.toDegrees(Math.atan2(dy, dx));
        if (angle < 0) angle += 360;
        double bearing = (90 - angle + 360) % 360;
        int sector = (int) ((bearing + 22.5) / 45) % 8;
        return values()[sector];
    }

    /** Returns the drawn direction (maps mirrored left-side dirs to their right-side equivalents). */
    public CharacterDirection getDrawnDir() {
        switch (this) {
            case SW: return SE;
            case W:  return E;
            case NW: return NE;
            default: return this;
        }
    }

    /** True if this direction uses a horizontally flipped sprite. */
    public boolean isFlipped() {
        return this == SW || this == W || this == NW;
    }

    /** Atlas region suffix for the five drawn directions (N, NE, E, SE, S). */
    public String getAtlasSuffix() {
        switch (getDrawnDir()) {
            case N:  return "n";
            case NE: return "ne";
            case E:  return "e";
            case SE: return "se";
            case S:  return "s";
            default: return "s";
        }
    }
}
