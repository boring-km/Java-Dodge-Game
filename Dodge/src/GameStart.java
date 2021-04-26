import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GameStart extends JFrame{

	private BufferedImage img;
	private GameScore GSC;
	private Garo spaceEffect;		// 우주효과
	private Back back;				// 게임 플레이화면

	public void paint(Graphics g) {

		super.paint(g);
		Image img = Toolkit.getDefaultToolkit().getImage("src/File/Main1.png");
		g.drawImage(img, 297, 300, this);

	}
	public GameStart() {

		setLayout(null);
		setBounds(50,100,1600,900);
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));

		ImageIcon Nstart = new ImageIcon("src/File/start.png");			// 게임시작 버튼
		ImageIcon Rstart = new ImageIcon("src/File/start1.png");
		ImageIcon Pstart = new ImageIcon("src/File/start2.png");

		JButton start = new JButton(Nstart);
		start.setPressedIcon(Pstart);
		start.setRolloverIcon(Rstart);
		start.setBorderPainted(false);
		start.setBounds(160,650,427,140);

		ImageIcon Nscore = new ImageIcon("src/File/score.png");			// 점수보기 버튼
		ImageIcon Rscore = new ImageIcon("src/File/score1.png");
		ImageIcon Pscore = new ImageIcon("src/File/score2.png");

		JButton score = new JButton(Nscore);
		score.setPressedIcon(Pscore);
		score.setRolloverIcon(Rscore);
		score.setBorderPainted(false);
		score.setBounds(587,650,427,140);

		ImageIcon Nquit = new ImageIcon("src/File/quit.png");			// 종료버튼
		ImageIcon Rquit = new ImageIcon("src/File/quit1.png");
		ImageIcon Pquit = new ImageIcon("src/File/quit2.png");

		JButton quit = new JButton(Nquit);
		quit.setPressedIcon(Pquit);
		quit.setRolloverIcon(Rquit);
		quit.setBorderPainted(false);
		quit.setBounds(1014,650,427,140);
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
				back = new Back();

			}
		});
		score.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
				GSC = new GameScore("", 0, 0, null);

			}
		});
		//마우스 커서
		Toolkit tk = Toolkit.getDefaultToolkit();
		Cursor invisCursor = tk.createCustomCursor(tk.createImage("src/File/Yellow.png"), new Point(), null);
		setCursor(invisCursor);
		getGlassPane().setVisible(true);
		//마우스 커서

		add(start);
		add(score);
		add(quit);
		setVisible(true);
	}

}