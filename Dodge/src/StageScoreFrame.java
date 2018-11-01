import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StageScoreFrame extends JFrame {
	
	private JLabel score = new JLabel();			// ������
	private JLabel stage = new JLabel();			// ��������
	private JPanel gameback = new JPanel();			// ����г�	
	private JLabel start = new JLabel();
	private JLabel ready = new JLabel();
	private JLabel life = new JLabel();

	private ArrayList<Integer> userscore = null;
	
	protected int count = 0;
	protected boolean pause = false;
	protected boolean resume = false;
	
	protected int gamepoint, stagepoint;				// ���� ���ھ�� ��������
	
	Runnable task = () -> {
		while (true) {
			
			if(pause == false || resume == true) {
				count += 1;
				
				if ( count >= 1 && count <= 15) {
					start.setVisible(false);				// ���� �ȳ� ������
					
					ready.setText("������ �� ���۵˴ϴ�!");
					ready.setBounds(500,20,600,30);
					ready.setFont(start.getFont().deriveFont(30.0f));
					ready.setForeground(Color.ORANGE);
				}
				
				if( count > 15) {
					
					ready.setVisible(false);				// �غ� �ȳ� ������
					
					gamepoint = count - 16;					// ���� ����Ʈ
					stagepoint = (count - 16) / 100 + 1;	// �������� ����Ʈ�� 1���� 100������ ���������� ����ϰ� ��
					
					score.setText("SCORE : " + gamepoint);
					score.setBounds(50,20,300,30);
					score.setForeground(Color.YELLOW);
					score.setFont(score.getFont().deriveFont(30.0f));
						
					stage.setText("STAGE : " + stagepoint);		// �������� 1����
					stage.setBounds(1300,20,200,30);
					stage.setForeground(Color.GREEN);
					stage.setFont(stage.getFont().deriveFont(30.0f));
				}
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	public StageScoreFrame() {
		
		setLayout(null);
		setUndecorated(true);
		setBounds(50,100,1500,50);
		setVisible(true);
		setAlwaysOnTop(true);
		gameback.setLayout(null);
		gameback.setBackground(Color.BLACK);
		gameback.setSize(1500,50);
		
		userscore = new ArrayList<Integer>();
		
		gameback.add(life);
		gameback.add(start);
		gameback.add(ready);
		gameback.add(score);	// �гο� �߰�
		gameback.add(stage);	// �гο� �߰�
		add(gameback);			// frame�� �߰�
		
		Thread t = new Thread(task);
		t.start();
		
		// Ŀ��
	    Toolkit tk = Toolkit.getDefaultToolkit();
	    Cursor GameCursor = tk.createCustomCursor(tk.createImage("src/File/Yellow.png"), new Point(), null);
	    setCursor(GameCursor);
		getGlassPane().setVisible(true);
		// Ŀ���κ�
		
	}
}
