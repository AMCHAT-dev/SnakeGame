package pt.amchat.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    private Clip clip;
    private URL soundURL;

    private SoundSettings state;

    public Sound() {
        soundURL = getClass().getResource("/snake_game.wav");
        setFile();
    }

    private void setFile() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        state = SoundSettings.ON;
        clip.start();
    }

    public void loop() {
        state = SoundSettings.ON;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        state = SoundSettings.OFF;
        clip.stop();
    }

    public void switchStates() {
        if (state == SoundSettings.ON) {
            stop();
        } else {
            loop();
        }
    }

    public SoundSettings state() {
        return clip.isActive() ? SoundSettings.ON : SoundSettings.OFF;
    }
}
