# ItemDefinition

**File:** `core/.../game/items/ItemDefinition.java`
**Extends/Type:** Plain class
**Role:** Runtime item descriptor. Holds the loaded icon texture, item type, and parsed config for one item type. Immutable after construction.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `name` | `String` | Display name |
| `icon` | `Texture` | Rendered icon (from Assets) |
| `itemType` | `ItemType` | WEAPON / ARMOR / ACCESSORY / CONSUMABLE |
| `config` | `ItemConfig` | Full stat data |

## ItemType Enum
`WEAPON, ARMOR, ACCESSORY, CONSUMABLE`

## Key Methods
- `getName()`, `getIcon()`, `getItemType()`, `getConfig()` — simple getters

## Rules
- Instances are created by `LootDropper.start()` using texture from Assets + JSON config
- One `ItemDefinition` instance is shared across `LootDropper` → `WorldItem` → `Inventory` → `ItemSlot`
- Icon texture is owned by Assets, not by ItemDefinition — do not dispose it here
