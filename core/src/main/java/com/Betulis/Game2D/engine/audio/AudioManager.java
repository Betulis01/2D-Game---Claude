package com.Betulis.Game2D.engine.audio;

import com.Betulis.Game2D.engine.camera.Camera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {

    private static final String SOUNDS_JSON = "data/config/audio/sounds.json";

    private final Map<String, SoundDef> defs = new HashMap<>();
    private final Map<String, Sound> sounds = new HashMap<>();
    private final Map<String, Music> music = new HashMap<>();

    private final Map<SoundCategory, Float> categoryVolumes = new HashMap<>();

    private Music currentMusic;

    public AudioManager() {
        for (SoundCategory cat : SoundCategory.values()) {
            categoryVolumes.put(cat, 1f);
        }
    }

    public void load() {
        if (!Gdx.files.internal(SOUNDS_JSON).exists()) {
            Gdx.app.log("AudioManager", "sounds.json not found at: " + SOUNDS_JSON);
            return;
        }

        Json json = new Json();
        SoundDef[] entries = json.fromJson(SoundDef[].class, Gdx.files.internal(SOUNDS_JSON));
        if (entries == null) return;

        for (SoundDef def : entries) {
            defs.put(def.id, def);
            SoundCategory cat = SoundCategory.valueOf(def.category);
            if (cat == SoundCategory.MUSIC) {
                Music m = Gdx.audio.newMusic(Gdx.files.internal(def.path));
                m.setLooping(true);
                music.put(def.id, m);
            } else {
                sounds.put(def.id, Gdx.audio.newSound(Gdx.files.internal(def.path)));
            }
        }
    }

    /** Play a GAMEPLAY (spatial) or MENU (flat) sound. */
    public void play(String id, float x, float y, Camera cam) {
        SoundDef def = defs.get(id);
        Sound sound = sounds.get(id);
        if (def == null || sound == null) return;

        SoundCategory cat = SoundCategory.valueOf(def.category);
        float catVol = categoryVolumes.getOrDefault(cat, 1f);

        if (cat == SoundCategory.GAMEPLAY && cam != null) {
            float cx = cam.getGameObject().getTransform().getWorldX();
            float cy = cam.getGameObject().getTransform().getWorldY();
            float dx = x - cx;
            float dy = y - cy;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            float vol = Math.max(0f, 1f - dist / def.maxDistance) * def.baseVolume * catVol;
            if (vol <= 0f) return;

            float pan = Math.max(-1f, Math.min(dx / (def.maxDistance * 0.5f), 1f));
            sound.play(vol, 1f, pan);
        } else {
            sound.play(def.baseVolume * catVol);
        }
    }

    public void playMusic(String id) {
        stopMusic();
        Music m = music.get(id);
        if (m == null) return;
        SoundDef def = defs.get(id);
        float catVol = categoryVolumes.getOrDefault(SoundCategory.MUSIC, 1f);
        m.setVolume(def != null ? def.baseVolume * catVol : catVol);
        m.play();
        currentMusic = m;
    }

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic = null;
        }
    }

    public void setCategoryVolume(SoundCategory cat, float vol) {
        categoryVolumes.put(cat, vol);
    }

    public void dispose() {
        for (Sound s : sounds.values()) s.dispose();
        for (Music m : music.values()) m.dispose();
        sounds.clear();
        music.clear();
        defs.clear();
    }
}
