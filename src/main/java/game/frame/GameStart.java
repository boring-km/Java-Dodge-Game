package game.frame;

import game.buttons.ImageButton;

import javax.swing.*;
import java.awt.*;

public class GameStart extends JFrame {

    public GameStart() {
        setLayout(null);
        setBounds(0, 100, 1600, 900);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        JButton start = ImageButton.builder()
                .normalStartIcon(new ImageIcon("files/start.png"))
                .rolloverStartIcon(new ImageIcon("files/start1.png"))
                .pressedStartIcon(new ImageIcon("files/start2.png"))
                .borderPainted(false)
                .bounds(new Rectangle(160, 650, 427, 140))
                .listener(e -> {
                    dispose();
                    new Back();
                })
                .build()
                .make();

        JButton score = ImageButton.builder()
                .normalStartIcon(new ImageIcon("files/score.png"))
                .rolloverStartIcon(new ImageIcon("files/score1.png"))
                .pressedStartIcon(new ImageIcon("files/score2.png"))
                .borderPainted(false)
                .bounds(new Rectangle(587, 650, 427, 140))
                .listener(e -> {
                    dispose();
                    new GameScore("", 0, 0, null);
                })
                .build()
                .make();

        JButton quit = ImageButton.builder()
                .normalStartIcon(new ImageIcon("files/quit.png"))
                .rolloverStartIcon(new ImageIcon("files/quit1.png"))
                .pressedStartIcon(new ImageIcon("files/quit2.png"))
                .borderPainted(false)
                .bounds(new Rectangle(1014, 650, 427, 140))
                .listener(e -> System.exit(0))
                .build()
                .make();

        //마우스 커서
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image cursorImage = tk.createImage("files/Yellow.png");
        Cursor invisibleCursor = tk.createCustomCursor(cursorImage, new Point(), null);
        setCursor(invisibleCursor);
        getGlassPane().setVisible(true);

        // 버튼 추가
        add(start);
        add(score);
        add(quit);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Image img = Toolkit.getDefaultToolkit().getImage("files/Main1.png");
        g.drawImage(img, 297, 300, this);
    }
}