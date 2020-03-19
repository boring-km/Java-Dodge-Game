import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameOver extends JFrame{

	private ImageIcon gameoverimage;

	public GameOver() {

		setLayout(null);
		setBounds(121,450,1329,193);			// 이미지 사이즈만큼
		setAlwaysOnTop(true);					// 계속 위에 뜨게한다.
		gameoverimage = new ImageIcon("src/File/GameOver.png");
		JPanel panel = new JPanel() {			// 패널에 바로 그린다.
			public void paintComponent(Graphics g) {

				g.drawImage(gameoverimage.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		panel.setLayout(null);
		panel.setSize(1329,193);
		add(panel);

		setUndecorated(true);
		setVisible(true);

	}
}