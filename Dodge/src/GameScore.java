import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameScore extends JFrame{

	private ImageIcon BigScore;		// 버튼 그림
	private ImageIcon Lines;
	private ImageIcon ScoreIntro;
	private ImageIcon Nquit;
	private ImageIcon Main;
	private ImageIcon Restart;
	private ImageIcon DeleteData;

	private ArrayList<Object> Score;

	private JLabel scoreLabel;	// 점수판
	private JPanel scoreback;

	private String date;		// 불러올 데이터들
	private String nickname;
	private int score, stage;

	private JButton mainmenu;	// 버튼
	private JButton restart;
	private JButton quit;
	private JButton deleteData;

	private GameStart FF;		// 버튼에서 실행해줄 프레임
	private Back newgame;

	public GameScore(String nickname, int score, int stage, String date) {

		this.nickname = nickname;
		this.score = score;
		this.stage = stage;
		this.date = date;

		scoreback = new JPanel();

		setLayout(null);
		setBounds(100,100,1600,900);		// 프레임 사이즈

		BigScore = new ImageIcon("src/File/Scores.png");
		Lines = new ImageIcon("src/File/2Lines.png");
		ScoreIntro = new ImageIcon("src/File/scoreintro.png");


		System.out.println("닉네임 : " + nickname + " 스코어 : " + score + " 스테이지 : " + stage + " 날짜 : " + date);

		try {
			texting();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("파일이 없습니다!");
		}

		Main = new ImageIcon("src/File/Main2.png");
		mainmenu = new JButton(Main);
		mainmenu.setBorderPainted(false);
		mainmenu.setBounds(800,50,150,90);
		mainmenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				FF = new GameStart();
			}
		});

		Restart = new ImageIcon("src/File/restart2.png");
		restart = new JButton(Restart);
		restart.setBorderPainted(false);
		restart.setBounds(1200,50,150,100);
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				newgame = new Back();
			}
		});

		Nquit = new ImageIcon("src/File/exit.png");
		quit = new JButton(Nquit);
		quit.setBorderPainted(false);
		quit.setBounds(1400,50,150,100);
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		scoreback.setLayout(null);
		scoreback.setBackground(new Color(0,0,0,100));		// 점수화면은 흰색?
		scoreback.setSize(1600,900);				// 배경 사이즈

		add(restart);
		add(mainmenu);
		add(quit);
		add(scoreback);					// 프레임에 배경추가

		Toolkit tk = Toolkit.getDefaultToolkit();
		Cursor ScoreCursor = tk.createCustomCursor(tk.createImage("src/File/Yellow.png"), new Point(), null);
		setCursor(ScoreCursor);
		getGlassPane().setVisible(true);

		setUndecorated(true);
		setBackground(new Color(0,0,0,100));
		setVisible(true);

	}
	// text파일에 입력하고 불러올 메소드 생성
	// 예외처리
	public void texting() throws IOException {

		// 텍스트 파일로 데이터 만들고 불러오기
		// 교재 765페이지

		PrintWriter write_text = null;

		int i = 0;
		double j = 0;

		Score = new ArrayList<Object>();
		if(nickname.isEmpty() == true) {		// 이름을 입력하지 않았을 때 처리
			nickname = "이름없음";
		}
		DeleteData = new ImageIcon("src/File/DeleteData.png");
		deleteData = new JButton(DeleteData);
		deleteData.setBorderPainted(false);
		deleteData.setBounds(1000,50,150,100);
		deleteData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = "src/File/score.text";
				File file = new File(data);
				file.delete();
				dispose();
				GameScore GS = new GameScore("",0,0,null);
			}
		});
		add(deleteData);		// 프레임에 추가
		// 초기화면을 위해서...
		if(stage != 0) {	// 게임을 플레이하지 않았을 때
			write_text = new PrintWriter(new FileWriter("src/File/score.text", true));

			write_text.println(nickname + " " + stage + " " + score + " " + date + " ");
			write_text.flush();
		}
		Scanner s = new Scanner(new BufferedReader(new FileReader("src/File/score.text")));

		while(s.hasNext()) {

			if(s.hasNextLine()) {

				Score.add(i, s.next());
				String str = (String) Score.get(i);
				scoreLabel = new JLabel((String)Score.get(i));
				scoreLabel.setForeground(Color.ORANGE);
				scoreLabel.setFont(scoreLabel.getFont().deriveFont(40.0f));
				if(j == 1) {		// 적절하게 점수판을 그리기 위한 장치
					j += 0.3;
				}
				scoreLabel.setBounds(100 + (int)(350*j), 350 + 50*(i/4), 500, 50);	// 스테이지와 스코어, 날짜를 적절히 분배
				scoreback.add(scoreLabel);
				if(j == 1.3) {		// 원래대로
					j -= 0.3;
				}
				i++;
				j++;
				if(j == 4) {
					j = 0;
				}
			} else {
				s.next();
			}
		}
		if (write_text != null)			// 끝까지
			write_text.close();
		if (s != null)
			s.close();

	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(BigScore.getImage(), 20, 30, null);
		g.drawImage(Lines.getImage(), 30, 180, null);
		g.drawImage(ScoreIntro.getImage(), 30, 195, null);
		g.drawImage(Lines.getImage(), 30, 280, null);
	}
}