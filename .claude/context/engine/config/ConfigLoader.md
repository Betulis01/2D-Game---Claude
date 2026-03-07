# ConfigLoader

**File:** `core/.../engine/config/ConfigLoader.java`
**Role:** Utility class. Loads and deserializes EntityConfig JSON files using LibGDX's Json library.

## Key Method
```java
EntityConfig load(String path)
// Strips leading '/' from path, reads file, deserializes into EntityConfig
```

## Dependencies
- LibGDX `Json` — deserializer
- LibGDX `Gdx.files.internal()` — file access

## Rules
- Path is relative to assets root
- Returns a new EntityConfig instance per call — not cached
- All required fields must be present in JSON — no validation currently
