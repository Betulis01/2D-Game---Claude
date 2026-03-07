# SpriteSheetSlicer

**File:** `core/.../engine/utils/SpriteSheetSlicer.java`
**Role:** Slices a sprite sheet texture into a 2D array of TextureRegions indexed by [frameIndex][row].

## Construction
```java
new SpriteSheetSlicer(Texture sheet, int frameWidth, int frameHeight)
// Divides sheet into grid: cols = sheet.width/frameWidth, rows = sheet.height/frameHeight
// Result: frames[col][row]
```

## Key Method
- `getFrame(int frameIndex, int row)` — returns the TextureRegion at that column/row

## Usage
- Used by AnimationClip to extract frame ranges from a sheet:
  ```java
  new AnimationClip(slicer, frameBegin, frameEnd, rowBegin, rowEnd, duration)
  ```

## Rules
- Assumes uniform grid layout — all frames same width/height
- Frame index = column index on the sprite sheet
- Row index = horizontal strip (direction, state, etc.)
- Does not own the Texture — caller manages lifetime
