import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class BackMusic extends Thread{
	
	private Player player;				// ���̺귯��
	private boolean isLoop;			
	private File music;
	private FileInputStream music_input;
	private BufferedInputStream music_buffer;
	
	public BackMusic(String name, boolean isLoop) {			// ���� �̸��� ����ġ
		
		try {
			this.isLoop = isLoop;
			music = new File("src/File/Toru - Life.mp3");				// �������� -> non-copyrighted music
			music_input = new FileInputStream(music);				// ������ ��Ʈ����
			music_buffer = new BufferedInputStream(music_input);
			player = new Player(music_buffer);						// ���̺귯�� �̿�
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			do {
				player.play();								// �÷��� �Ѵ�!
				music_input = new FileInputStream(music);
				music_buffer = new BufferedInputStream(music_input);
				player = new Player(music_buffer);
			} while(isLoop);				// ��� ����ġ����
			
		}catch(Exception e) {
			e.printStackTrace();

		}
	}
}
