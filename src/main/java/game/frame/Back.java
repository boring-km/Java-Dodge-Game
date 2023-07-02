package game.frame;

import game.music.BackMusic;

import javax.swing.*;
import java.awt.*;

public class Back extends JFrame {

	private final GameMainPanel game;

	public Back() {

		setLayout(null);
		setBounds(0,100,1800,850);

		JPanel background = new JPanel();
		background.setBackground(Color.BLACK);
		background.setBounds(1500,0,300,850);

		ImageIcon Menu = new ImageIcon("files/Menu1.png");
		JButton menu = new JButton(Menu);
		ImageIcon Pmenu = new ImageIcon("files/Menu3.png");

		menu.setBorderPainted(false);
		menu.setPressedIcon(Pmenu);
		menu.setRolloverIcon(Pmenu);
		menu.setBackground(Color.BLACK);
		menu.setBounds(1580,50,125,63);

		ImageIcon Menu2 = new ImageIcon("files/Menu1.png");
		JButton menu2 = new JButton(Menu2);

		menu2.setBorderPainted(false);
		menu2.setPressedIcon(Pmenu);
		menu2.setRolloverIcon(Pmenu);
		menu2.setBackground(Color.BLACK);
		menu2.setBounds(1580,50,125,63);

		ImageIcon Main = new ImageIcon("files/Main.png");
		JButton mainmenu = new JButton(Main);
		mainmenu.setBorderPainted(false);
		mainmenu.setBounds(1580,100,100,50);

		ImageIcon Pause = new ImageIcon("files/Pause.png");
		JButton pause = new JButton(Pause);
		ImageIcon Pause2 = new ImageIcon("files/Pause2.png");
		JButton pause2 = new JButton(Pause2);

		pause.setBorderPainted(false);
		pause.setBounds(1580,150,100,50);
		pause2.setBorderPainted(false);
		pause2.setBounds(1580,150,150,50);

		ImageIcon Score = new ImageIcon("files/gameScore.png");
		JButton score = new JButton(Score);
		score.setBorderPainted(false);
		score.setBackground(Color.BLACK);
		score.setBounds(1580,200,126,73);

		ImageIcon Restart = new ImageIcon("files/Restart.png");
		JButton restart = new JButton(Restart);
		restart.setBorderPainted(false);
		restart.setBounds(1580,250,100,50);

		ImageIcon Quit = new ImageIcon("files/Q.png");
		JButton quit = new JButton(Quit);
		quit.setBounds(1600,300,100,50);
		quit.setBorderPainted(false);
		quit.addActionListener(e -> System.exit(0));
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
		menu.addActionListener(e -> {
			menu.setVisible(false);
			menu2.setVisible(true);
			pause.setVisible(true);
			pause2.setVisible(true);
			restart.setVisible(true);
			score.setVisible(true);
			mainmenu.setVisible(true);
			quit.setVisible(true);
		});
		// 메뉴를 닫으면 버튼이 가려짐
		menu2.addActionListener(e -> {
			menu.setVisible(true);
			menu2.setVisible(false);
			pause.setVisible(false);
			pause2.setVisible(false);
			restart.setVisible(false);
			score.setVisible(false);
			mainmenu.setVisible(false);
			quit.setVisible(false);
		});

		game = new GameMainPanel();
		Thread t = new Thread(game);		// 게임프레임의 쓰레드를 생성
		t.start();							// 쓰레드 시작
		game.setBounds(0,50,1500,800);

		add(game);
		setUndecorated(true);
		setVisible(true);

		BackMusic introBackMusic = new BackMusic("files/Toru - Life.mp3", true);		// 음악재생!
		introBackMusic.start();

		pause.addActionListener(e -> {	// 멈추기
			introBackMusic.suspend();
			t.suspend();
			game.SSF.setPause(true);
			game.SSF.setResume(false);
		});
		pause2.addActionListener(e -> {	// 멈춤 풀기
			introBackMusic.resume();
			t.resume();
			game.addKeyListener(game);
			game.setFocusable(true);
			game.requestFocus();
			if(!game.gameisover) {				// 게임이 안 끝났을 때에만 점수를 멈춘다
				game.SSF.setResume(true);
				game.SSF.setPause(false);
			}
		});
		restart.addActionListener(e -> {	// 다시 시작 버튼
			t.stop();
			introBackMusic.stop();
			dispose();					// 메뉴화면 종료
			game.SSF.dispose();				// 점수화면 종료
			if(game.gameisover) {
				game.gameover.dispose();	// 게임오버화면도 종료
				game.nk.dispose();
			}
			new Back();
		});
		score.addActionListener(e -> {
			t.suspend();
			introBackMusic.suspend();
			dispose();
			game.SSF.dispose();
			if(game.gameisover) {
				if(game.nk.nickname != null)
					new GameScore(game.nk.nickname, game.SSF.getGamepoint(), game.SSF.getStagepoint(), game.date);
				else if(game.SSF.getGamepoint() == 0)
					new GameScore("", game.SSF.getGamepoint(), game.SSF.getStagepoint(), game.date);
				else
					new GameScore("", 0, 0, null);

				game.gameover.dispose();	// 게임오버화면도 종료
				game.nk.dispose();

			} else new GameScore("", 0, 0, null);

		});
		mainmenu.addActionListener(e -> {
			t.stop();
			introBackMusic.stop();
			dispose();
			game.SSF.dispose();
			if(game.gameisover) {
				game.gameover.dispose();	// 게임오버화면도 종료
				game.nk.dispose();
			}
			new GameStart();
		});
		// 커서
		Toolkit tk = Toolkit.getDefaultToolkit();
		Cursor GameCursor = tk.createCustomCursor(tk.createImage("files/Yellow.png"), new Point(), null);
		setCursor(GameCursor);
		getGlassPane().setVisible(true);
	}
	public void paint(Graphics g) {

		super.paint(g);
		Image img = Toolkit.getDefaultToolkit().getImage("files/intro.png");
		g.drawImage(img, 1550, 350, this);
		g.setColor(Color.WHITE);
		g.drawString("Ver 1.0", 1750, 830);
	}
}