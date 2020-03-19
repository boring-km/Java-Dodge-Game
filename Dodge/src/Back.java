import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Back extends JFrame {

	private GameMainFrame game;
	private FirstFrame FF;
	private GameScore GS;
	private boolean music = true;

	public Back() {

		setLayout(null);
		setBounds(50,100,1800,850);

		JPanel background = new JPanel();
		background.setBackground(Color.BLACK);
		background.setBounds(1500,0,300,850);

		ImageIcon Menu = new ImageIcon("src/File/Menu1.png");
		JButton menu = new JButton(Menu);
		ImageIcon Pmenu = new ImageIcon("src/File/Menu3.png");

		menu.setBorderPainted(false);
		menu.setPressedIcon(Pmenu);
		menu.setRolloverIcon(Pmenu);
		menu.setBackground(Color.BLACK);
		menu.setBounds(1580,50,125,63);

		ImageIcon Menu2 = new ImageIcon("src/File/Menu1.png");
		JButton menu2 = new JButton(Menu2);

		menu2.setBorderPainted(false);
		menu2.setPressedIcon(Pmenu);
		menu2.setRolloverIcon(Pmenu);
		menu2.setBackground(Color.BLACK);
		menu2.setBounds(1580,50,125,63);

		ImageIcon Main = new ImageIcon("src/File/Main.png");
		JButton mainmenu = new JButton(Main);
		mainmenu.setBorderPainted(false);
		mainmenu.setBounds(1580,100,100,50);

		ImageIcon Pause = new ImageIcon("src/File/Pause.png");
		JButton pause = new JButton(Pause);
		ImageIcon Pause2 = new ImageIcon("src/File/Pause2.png");
		JButton pause2 = new JButton(Pause2);

		pause.setBorderPainted(false);
		pause.setBounds(1580,150,100,50);
		pause2.setBorderPainted(false);
		pause2.setBounds(1580,150,150,50);

		ImageIcon Score = new ImageIcon("src/File/gameScore.png");
		JButton score = new JButton(Score);
		score.setBorderPainted(false);
		score.setBackground(Color.BLACK);
		score.setBounds(1580,200,126,73);

		ImageIcon Restart = new ImageIcon("src/File/Restart.png");
		JButton restart = new JButton(Restart);
		restart.setBorderPainted(false);
		restart.setBounds(1580,250,100,50);

		ImageIcon Quit = new ImageIcon("src/File/Q.png");
		JButton quit = new JButton(Quit);
		quit.setBounds(1600,300,100,50);
		quit.setBorderPainted(false);
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// 일단 가려놓음
		pause.setVisible(false);
		pause2.setVisible(false);
		restart.setVisible(false);
		mainmenu.setVisible(false);
		score.setVisible(false);
		quit.setVisible(false);
		menu2.setVisible(false);

		add(restart);
		add(score);
		add(pause);
		add(pause2);
		add(mainmenu);
		add(menu2);
		add(menu);
		add(quit);
		add(background);

		// 메뉴를 누르면 버튼이 등장
		menu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				menu.setVisible(false);
				menu2.setVisible(true);
				pause.setVisible(true);
				pause2.setVisible(true);
				restart.setVisible(true);
				score.setVisible(true);
				mainmenu.setVisible(true);
				quit.setVisible(true);
			}

		});
		// 메뉴를 닫으면 버튼이 가려짐
		menu2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				menu.setVisible(true);
				menu2.setVisible(false);
				pause.setVisible(false);
				pause2.setVisible(false);
				restart.setVisible(false);
				score.setVisible(false);
				mainmenu.setVisible(false);
				quit.setVisible(false);
			}
		});

		game = new GameMainFrame();
		Thread t = new Thread(game);		// 게임프레임의 쓰레드를 생성
		t.start();							// 쓰레드 시작
		game.setBounds(0,50,1500,800);

		add(game);
		setUndecorated(true);
		setVisible(true);

		BackMusic introBackMusic = new BackMusic("src/File/Toru - Life.mp3", music);		// 음악재생!
		introBackMusic.start();

		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	// 멈추기
				introBackMusic.suspend();
				t.suspend();
				game.SSF.pause = true;
				game.SSF.resume = false;
			}
		});
		pause2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	// 멈춤 풀기
				introBackMusic.resume();
				t.resume();
				game.addKeyListener(game);
				game.setFocusable(true);
				game.requestFocus();
				if(game.gameisover == false) {				// 게임이 안 끝났을 때에만 점수를 멈춘다
					game.SSF.resume = true;
					game.SSF.pause = false;
				}
			}
		});
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	// 다시 시작 버튼

				t.stop();
				introBackMusic.stop();
				dispose();					// 메뉴화면 종료
				game.SSF.dispose();				// 점수화면 종료
				if(game.gameisover == true) {
					game.gameover.dispose();	// 게임오버화면도 종료
					game.nk.dispose();
				}
				Back restartgame = new Back();
			}
		});
		score.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.suspend();
				introBackMusic.suspend();
				dispose();
				game.SSF.dispose();
				if(game.gameisover == true) {

					if(game.nk.nickname != null)
						GS = new GameScore(game.nk.nickname, game.SSF.gamepoint, game.SSF.stagepoint, game.date);
					else if(game.nk.nickname == null && game.SSF.gamepoint == 0)
						GS = new GameScore("", game.SSF.gamepoint, game.SSF.stagepoint, game.date);
					else
						GS = new GameScore("", 0, 0, null);

					game.gameover.dispose();	// 게임오버화면도 종료
					game.nk.dispose();

				}else
					GS = new GameScore("", 0, 0, null);

			}
		});
		mainmenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.stop();
				introBackMusic.stop();
				dispose();
				game.SSF.dispose();
				if(game.gameisover == true) {
					game.gameover.dispose();	// 게임오버화면도 종료
					game.nk.dispose();
				}
				FF = new FirstFrame();
			}
		});
		// 커서
		Toolkit tk = Toolkit.getDefaultToolkit();
		Cursor GameCursor = tk.createCustomCursor(tk.createImage("src/File/Yellow.png"), new Point(), null);
		setCursor(GameCursor);
		getGlassPane().setVisible(true);
	}
	public void paint(Graphics g) {

		super.paint(g);
		Image img = Toolkit.getDefaultToolkit().getImage("src/File/intro.png");
		g.drawImage(img, 1550, 350, this);
		g.setColor(Color.WHITE);
		g.drawString("Ver 1.0", 1750, 830);
	}
}