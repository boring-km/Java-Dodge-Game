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
	
	private ImageIcon BigScore;		// ��ư �׸�
	private ImageIcon Lines;
	private ImageIcon ScoreIntro;
	private ImageIcon Nquit;		
	private ImageIcon Main;
	private ImageIcon Restart;
	private ImageIcon DeleteData;
	
	private ArrayList<Object> Score;
	
	private JLabel scoreLabel;	// ������
	private JPanel scoreback;
	
	private String date;		// �ҷ��� �����͵�
	private String nickname;
	private int score, stage;
	
	private JButton mainmenu;	// ��ư
	private JButton restart;
	private JButton quit;
	private JButton deleteData;
	
	private FirstFrame FF;		// ��ư���� �������� ������
	private Back newgame;
	
	public GameScore(String nickname, int score, int stage, String date) {
		
		this.nickname = nickname;
		this.score = score;
		this.stage = stage;
		this.date = date;
		
		scoreback = new JPanel();
		
		setLayout(null);
		setBounds(100,100,1600,900);		// ������ ������
		
		BigScore = new ImageIcon("src/File/Scores.png");
		Lines = new ImageIcon("src/File/2Lines.png");
		ScoreIntro = new ImageIcon("src/File/scoreintro.png");
		
		
		System.out.println("�г��� : " + nickname + " ���ھ� : " + score + " �������� : " + stage + " ��¥ : " + date);
		
		try {
			texting();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("������ �����ϴ�!");
		}
		
		Main = new ImageIcon("src/File/Main2.png");
		mainmenu = new JButton(Main);
		mainmenu.setBorderPainted(false);
		mainmenu.setBounds(800,50,150,90);
		mainmenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				FF = new FirstFrame();
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
		scoreback.setBackground(new Color(0,0,0,100));		// ����ȭ���� ���?
		scoreback.setSize(1600,900);				// ��� ������
		
		add(restart);
		add(mainmenu);
		add(quit);
		add(scoreback);					// �����ӿ� ����߰�
		
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Cursor ScoreCursor = tk.createCustomCursor(tk.createImage("src/File/Yellow.png"), new Point(), null);
	    setCursor(ScoreCursor);
		getGlassPane().setVisible(true);
		
		setUndecorated(true);
		setBackground(new Color(0,0,0,100));
		setVisible(true);
		
	}
	// text���Ͽ� �Է��ϰ� �ҷ��� �޼ҵ� ����
	// ����ó��
	public void texting() throws IOException {			
		
		// �ؽ�Ʈ ���Ϸ� ������ ����� �ҷ�����
		// ���� 765������
		
		PrintWriter write_text = null;
		
		int i = 0;
		double j = 0;
		
		Score = new ArrayList<Object>();
		if(nickname.isEmpty() == true) {		// �̸��� �Է����� �ʾ��� �� ó��
			nickname = "�̸�����";
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
		add(deleteData);		// �����ӿ� �߰�
		// �ʱ�ȭ���� ���ؼ�...
		if(stage != 0) {	// ������ �÷������� �ʾ��� ��
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
				if(j == 1) {		// �����ϰ� �������� �׸��� ���� ��ġ
					j += 0.3;
				}
				scoreLabel.setBounds(100 + (int)(350*j), 350 + 50*(i/4), 500, 50);	// ���������� ���ھ�, ��¥�� ������ �й�
				scoreback.add(scoreLabel);
				if(j == 1.3) {		// �������
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
		if (write_text != null)			// ������
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
