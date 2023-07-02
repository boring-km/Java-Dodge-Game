package game.frame;

import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StageScoreFrame extends JFrame {

	private JLabel score = new JLabel();			// 점수판
	private JLabel stage = new JLabel();			// 스테이지
	private JPanel gameback = new JPanel();			// 배경패널
	private JLabel start = new JLabel();
	private JLabel ready = new JLabel();
	private JLabel life = new JLabel();

	private ArrayList<Integer> userscore = null;

	@Getter private int count = 0;
	@Getter @Setter private boolean pause = false;
	@Getter @Setter private boolean resume = false;

	@Getter private int gamepoint, stagepoint;				// 게임 스코어와 스테이지

	Runnable task = () -> {
		while (true) {

			if(pause == false || resume == true) {
				count += 1;

				if ( count >= 1 && count <= 15) {
					start.setVisible(false);				// 시작 안내 가리기

					ready.setText("게임이 곧 시작됩니다!");
					ready.setBounds(500,20,600,30);
					ready.setFont(start.getFont().deriveFont(30.0f));
					ready.setForeground(Color.ORANGE);
				}

				if( count > 15) {

					ready.setVisible(false);				// 준비 안내 가리기

					gamepoint = count - 16;					// 게임 포인트
					stagepoint = (count - 16) / 100 + 1;	// 스테이지 포인트를 1부터 100점마다 스테이지를 상승하게 함

					score.setText("SCORE : " + gamepoint);
					score.setBounds(50,20,300,30);
					score.setForeground(Color.YELLOW);
					score.setFont(score.getFont().deriveFont(30.0f));

					stage.setText("STAGE : " + stagepoint);		// 스테이지 1부터
					stage.setBounds(1300,20,200,30);
					stage.setForeground(Color.GREEN);
					stage.setFont(stage.getFont().deriveFont(30.0f));
				}
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	public StageScoreFrame() {

		setLayout(null);
		setUndecorated(true);
		setBounds(0,100,1500,50);
		setVisible(true);
		setAlwaysOnTop(true);
		gameback.setLayout(null);
		gameback.setBackground(Color.BLACK);
		gameback.setSize(1500,50);

		userscore = new ArrayList<Integer>();

		gameback.add(life);
		gameback.add(start);
		gameback.add(ready);
		gameback.add(score);	// 패널에 추가
		gameback.add(stage);	// 패널에 추가
		add(gameback);			// frame에 추가

		Thread t = new Thread(task);
		t.start();

		// 커서
		Toolkit tk = Toolkit.getDefaultToolkit();
		Cursor GameCursor = tk.createCustomCursor(tk.createImage("files/Yellow.png"), new Point(), null);
		setCursor(GameCursor);
		getGlassPane().setVisible(true);
		// 커서부분

	}
}