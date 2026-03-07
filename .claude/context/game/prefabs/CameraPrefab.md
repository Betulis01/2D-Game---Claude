# CameraPrefab

**File:** `core/.../game/prefabs/camera/CameraPrefab.java`
**Role:** Factory that creates the camera GameObject. Wraps Camera and CameraZoom into a single object.

## Produced GameObject: "Camera"
| Component | Purpose |
|-----------|---------|
| `Transform` | Required by GameObject (camera position is managed internally by Camera component) |
| `Camera` | Follow + zoom + coordinate conversion |
| `CameraZoom` | Scroll input → zoom delta |

## Construction
```java
create(Transform target, int screenWidth, int screenHeight)
```
- `target` = Transform to follow (player's transform)
- Zoom and world bounds configured after creation in scene `onLoad()`

## Rules
- One CameraPrefab per scene
- World bounds and initial zoom set by the scene after prefab creation:
  ```java
  camera.setWorldBounds(mapPixelW, mapPixelH)
  camera.setZoom(3)
  scene.setCamera(camera)
  ```
