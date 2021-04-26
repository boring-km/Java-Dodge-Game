import javax.swing.*;
import java.awt.*;

public class GameStart extends JFrame {

	@Override
    public void paint(Graphics g) {
        super.paint(g);
        Image img = Toolkit.getDefaultToolkit().getImage("src/File/Main1.png");
        g.drawImage(img, 297, 300, this);
    }

    public GameStart() {
        setLayout(null);
        setBounds(50, 100, 1600, 900);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        ImageIcon normalStartIcon = new ImageIcon("src/File/start.png");            // 게임시작 버튼
        ImageIcon rolloverStartIcon = new ImageIcon("src/File/start1.png");
        ImageIcon pressedStartIcon = new ImageIcon("src/File/start2.png");

        JButton start = new JButton(normalStartIcon);
        start.setPressedIcon(pressedStartIcon);
        start.setRolloverIcon(rolloverStartIcon);
        start.setBorderPainted(false);
        start.setBounds(160, 650, 427, 140);
		start.addActionListener(e -> {
			dispose();
			new Back();
		});

        ImageIcon normalScoreIcon = new ImageIcon("src/File/score.png");            // 점수보기 버튼
        ImageIcon rolloverScoreIcon = new ImageIcon("src/File/score1.png");
        ImageIcon pressedScoreIcon = new ImageIcon("src/File/score2.png");

        JButton score = new JButton(normalScoreIcon);
        score.setPressedIcon(pressedScoreIcon);
        score.setRolloverIcon(rolloverScoreIcon);
        score.setBorderPainted(false);
        score.setBounds(587, 650, 427, 140);
		score.addActionListener(e -> {
			dispose();
			new GameScore("", 0, 0, null);
		});

        ImageIcon normalQuitIcon = new ImageIcon("src/File/quit.png");            // 종료버튼
        ImageIcon rolloverQuitIcon = new ImageIcon("src/File/quit1.png");
        ImageIcon pressedQuitIcon = new ImageIcon("src/File/quit2.png");

        JButton quit = new JButton(normalQuitIcon);
        quit.setPressedIcon(pressedQuitIcon);
        quit.setRolloverIcon(rolloverQuitIcon);
        quit.setBorderPainted(false);
        quit.setBounds(1014, 650, 427, 140);
        quit.addActionListener(e -> System.exit(0));

        //마우스 커서
        Toolkit tk = Toolkit.getDefaultToolkit();
        Cursor invisibleCursor = tk.createCustomCursor(tk.createImage("src/File/Yellow.png"), new Point(), null);
        setCursor(invisibleCursor);
        getGlassPane().setVisible(true);

        // 버튼 추가
        add(start);
        add(score);
        add(quit);
        setVisible(true);
    }
}