package game.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Nickname extends JFrame implements KeyListener{
	
	private JTextField input = new JTextField();
	private JLabel nick = new JLabel();
	private JPanel background = new JPanel();
	private JButton score;
	private ImageIcon inputscore;
	protected String nickname;
	
	public Nickname() {
		
		setLayout(null);
		setBounds(550, 650, 500, 60);			
		setAlwaysOnTop(true);
		background.setSize(500, 60);
		background.setBackground(Color.BLACK);
		
		nick.setBounds(0, 5, 200, 50);
		nick.setForeground(Color.WHITE);
		nick.setFont(nick.getFont().deriveFont(30.0f));
		nick.setText(" NickName : ");
		
		input.setBounds(200, 5, 200, 50);
		input.setBackground(Color.BLACK);
		input.setForeground(Color.ORANGE);
		input.addKeyListener(this);
		input.setFont(input.getFont().deriveFont(30.0f));
		
		inputscore = new ImageIcon("files/inputscore.png");
		score = new JButton(inputscore);
		score.setBorderPainted(false);
		score.setBounds(400, 1, 95, 45);
		score.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nickname = input.getText();
				dispose();
			}
		});
		
		add(score);
		add(input);
		add(nick);
		add(background);
		setUndecorated(true);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		
		super.paint(g);
		g.setColor(Color.YELLOW);
		g.drawRect(0, 0, 499, 59);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		
		switch(keycode){
		case KeyEvent.VK_ENTER : 
			nickname = input.getText();
			dispose();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
