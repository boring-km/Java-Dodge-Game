import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class BackMusic extends Thread{
	
	private Player player;				// 라이브러리
	private boolean isLoop;			
	private File music;
	private FileInputStream music_input;
	private BufferedInputStream music_buffer;
	
	public BackMusic(String name, boolean isLoop) {			// 파일 이름과 스위치
		
		try {
			this.isLoop = isLoop;
			music = new File("src/File/Toru - Life.mp3");				// 음악파일 -> non-copyrighted music
			music_input = new FileInputStream(music);				// 파일을 스트림에
			music_buffer = new BufferedInputStream(music_input);
			player = new Player(music_buffer);						// 라이브러리 이용
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			do {
				player.play();								// 플레이 한다!
				music_input = new FileInputStream(music);
				music_buffer = new BufferedInputStream(music_input);
				player = new Player(music_buffer);
			} while(isLoop);				// 재생 스위치역할
			
		}catch(Exception e) {
			e.printStackTrace();

		}
	}
}
