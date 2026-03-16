package com.Betulis.Game2D.engine.audio;

import com.Betulis.Game2D.engine.system.Component;

public class AudioPlayer extends Component {

    private final String soundId;

    public AudioPlayer(String soundId) {
        this.soundId = soundId;
    }

    @Override
    public void start() {
        getScene().getGame().getAudioManager()
            .play(soundId, (float) transform.getWorldX(), (float) transform.getWorldY());
        setEnabled(false);
    }
}
