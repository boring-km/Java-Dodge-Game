import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameOver extends JFrame{
	
	private ImageIcon gameoverimage;
	
	public GameOver() {
		
		setLayout(null);
		setBounds(121,450,1329,193);			// �̹��� �����ŭ
		setAlwaysOnTop(true);					// ��� ���� �߰��Ѵ�.
		gameoverimage = new ImageIcon("src/File/GameOver.png");
		JPanel panel = new JPanel() {			// �гο� �ٷ� �׸���.
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
