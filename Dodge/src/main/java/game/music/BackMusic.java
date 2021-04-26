package game.music;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class BackMusic extends Thread {

    private Player player;
    private boolean isLoop;
    private File music;
    private FileInputStream music_input;
    private BufferedInputStream music_buffer;

    // isLoop: 스위치
    public BackMusic(boolean isLoop) {

        try {
            this.isLoop = isLoop;
            music = new File("files/Toru - Life.mp3");      // 음악파일 -> non-copyrighted music
            music_input = new FileInputStream(music);
            music_buffer = new BufferedInputStream(music_input);
            player = new Player(music_buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            do {
                player.play();
                music_input = new FileInputStream(music);
                music_buffer = new BufferedInputStream(music_input);
                player = new Player(music_buffer);
            } while (isLoop);                // 재생 스위치역할

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}