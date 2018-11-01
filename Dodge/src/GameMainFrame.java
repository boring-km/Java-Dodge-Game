import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameMainFrame extends JPanel implements Runnable, KeyListener{
	
	private BufferedImage image = null;		// ȭ�� ������ ��ü �̹����� ���� ��
	private boolean left = false, right = false, up = false, down = false;

	private static final int WIDTH = 1500; 			// ��ü ��
	private static final int HEIGHT = 800; 			// ��ü ����
	private int x = 750, y = 400;			// ������ ��ü �ʱ� ��ġ(��ü ��ǥ)
	private final int SIZE = 20;					// ��ü ������
	private int PlayerSpeed = 4;				// �÷��̾� �ӵ�
	private int life = 3;						// �÷��̾� ��� 3��
	private final int SPACEBALL = 150;					// ���ֹ�� ��ü ��
	private final int SPACEBALLSIZE = 3;
	
	private ImageIcon heart;
	private ImageIcon showlife;
	
	protected boolean gameisover = false;				// ���ӿ��� â�� �ѹ��� ����� ���� ������ �ϳ� ����
	private int stagecounting = 0;
	private int ball_count = 0;						// ���η� ������ ��ü �� ����
	
	private int[] spicy = new int[4];		// ���̵� ������ ����
	
	private ArrayList<Object> garoList = null;		// ���η� ������ ��ü ����Ʈ
	private ArrayList<Object> garo2List = null;		// ���η� ������ ��ü ����Ʈ
	private ArrayList<Object> spaceList = null;		// ���η� ������ ���� ��� ����Ʈ
	private ArrayList<Object> seroList = null;		// ���η� ������ ��ü ����Ʈ
	private ArrayList<Object> diaList = null;
	
	protected String date;
	protected StageScoreFrame SSF = new StageScoreFrame();
	protected GameOver gameover;				// ���ӿ��� ȭ�� ���
	protected Nickname nk;					// ���ӿ��� �� �г��� �Է�
	
	public GameMainFrame() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);		// ����,���ο�,������Ÿ���� RGB

		this.setLayout(null);						// ��ǥ�� ���������
		
		garoList = new ArrayList<Object>();
		garo2List = new ArrayList<Object>();
		spaceList = new ArrayList<Object>();
		seroList = new ArrayList<Object>();
		diaList = new ArrayList<Object>();
		
		heart = new ImageIcon("src/File/heart.png");
		showlife = new ImageIcon("src/File/Life.png");
		
		addKeyListener(this);
		this.requestFocus();		// �г��� �׳� ��Ŀ���� �������� ����
		setFocusable(true);
		
	}
		
	@Override
	public void run() {							// Runnable �������̽��� ����ؼ� �ʼ����� �������̵�!

		// TODO Auto-generated method stub
		try {
			while(true) {
				Thread.sleep(8);
				spaceCreate(); 			// ���� ���
				losing_life();
				
				// �������� 1
			    if( ball_count > 100 && SSF.stagepoint == 1) {
			    	
			    	spicy[0] = 40;
			    	
			    	leftCreate(); rightCreate(); upCreate();
			    	
			    	ball_count = 0;
			    	stagecounting = SSF.stagepoint;				// ���������� ������� stagecounting = 1;
			    }
			    
			    // ���������� �ٲ� ������ life�� �ϳ��� �ش�.
				if( stagecounting < SSF.stagepoint) {
			    	life++;
			    	stagecounting = SSF.stagepoint;
			    }
			    
			    // �������� 2
			    if(ball_count > 150 && SSF.stagepoint == 2) {
			    	
			    	spicy[0] = 40;		spicy[1] = 40;
			    	
			    	leftCreate(); 		rightCreate();
			    	ball_count = 0;
			    	stagecounting = SSF.stagepoint;
			    }
			    // �������� 3
			    if( ball_count > 250 && SSF.stagepoint == 3) {		// �������� ���� ������!!!!
			    	
			    	spicy[3] = 40;
			    	
			    	for(int i = 0; i < 12; i++)
			    		diaCreate();
			    	ball_count = 0;
			    	stagecounting = SSF.stagepoint;	
			    }
			    // �������� 4
			    if( ball_count > 300 && SSF.stagepoint == 4) {
			    	
			    	spicy[0] = 30;
			    	spicy[1] = 30;
			    	
			    	rightCreate(); 	leftCreate();
			    	
			    	spicy[3] = 30;
			    	for(int i = 0; i < 5; i++)
			    		diaCreate();
			    	ball_count = 0;
			    	stagecounting = SSF.stagepoint;	
			    }
			    // �������� 5
			    if( ball_count > 300 && SSF.stagepoint == 5) {
			    	
			    	for(int i = 0; i < 3; i++) 
			    		spicy[i] = (i*10) + 5;
			    	
			    	upCreate();		rightCreate();		leftCreate();
			    	
			    	for(int i = 0; i < 5; i++)
			    		diaCreate();
			    	
			    	ball_count = 0;
			    	stagecounting = SSF.stagepoint;	
			    }
			    // �������� 6
			    if( ball_count > 250 && SSF.stagepoint == 6) {
			    	
			    	for(int i = 0; i < 3; i++) 
			    		spicy[i] = (i*10) + 5;
			    	
			    	upCreate();		rightCreate();		leftCreate();
			    	
			    	for(int i = 0; i < 10; i++)
			    		diaCreate();
			    	ball_count = 0;
			    	stagecounting = SSF.stagepoint;	
			    }
			    
			    // �������� 7 ����
			    if( ball_count > 200 && SSF.stagepoint >= 7) {
			    	
			    	for(int i = 0; i < 3; i++) 
			    		spicy[i] = (int)(Math.random() * 15) + 10;
			    	
			    	upCreate();		rightCreate();		leftCreate();
			    	for(int i = 0; i < 15; i++)
			    		diaCreate();
			    	ball_count = 0;
			    }
			    	
			    ball_count++;
			    draw();
			    keyControl();
			    
				if(life <= 0 && gameisover == false) {
					
					Thread.interrupted();
					gameisover = true;
					PlayerSpeed = 0;
					SSF.pause = true;
					SSF.resume = false;
					gameover = new GameOver();
					nk = new Nickname();
					date = new java.text.SimpleDateFormat("MM/dd_HH:mm:ss").format(new java.util.Date());
				}
			  
			}
		} catch(Exception e) {
			   System.out.println("�������� �����Դϴ�.");
		}
	};
	
	// ȭ��ǥ Ű�� �޾Ƽ� ������ ���� �޴� �޼ҵ�
	public void keyControl() {
		
		if(0 < x) {
			if(left) x -= PlayerSpeed;
		}
		if(WIDTH > x + SIZE) {
			if(right) x += PlayerSpeed;
		}
		if(0 < y) {
			if(up) y -= PlayerSpeed;
		}
		if(HEIGHT > y + SIZE) {
			if(down) y += PlayerSpeed;
		}
	}
	// ��ü �̵� �޼ҵ�
	// ���η� ���ʿ��� ������ ��ü ����
	public void leftCreate() {
		for(int i = 0; i < spicy[0]; i++) {
			double xc = (Math.random() * 60) - 200;		// ���� ��ġ
			double yc = Math.random() * HEIGHT;	// ���� ��ġ
			Garo garo = new Garo();
			garo.setX((int)xc);
			garo.setY((int)yc);
			garoList.add(garo);
		}
	}
	// ���η� �����ʿ��� ������ ��ü ����
	public void rightCreate() {
		for(int i = 0; i < spicy[1]; i++) {
			double xc = WIDTH + 200 - (Math.random() * 60);		// ���� ��ġ
			double yc = Math.random() * HEIGHT;	// ���� ��ġ
			Garo2 garo2 = new Garo2();
			garo2.setX((int)xc);
			garo2.setY((int)yc);
			garo2List.add(garo2);
		}
	}	
	// ���η� ������ ��ü ����
	public void upCreate() {
		for(int i = 0; i < spicy[2]; i++) {
			double xc = Math.random() * WIDTH;
			double yc = (Math.random() * 100) - HEIGHT;	// ���� ��ġ
			Sero sero = new Sero();
			sero.setX((int)xc);
			sero.setY((int)yc);
			seroList.add(sero);
		}
	}
	// �밢������ ������ ��ü ����
	public void diaCreate() {
		
		double xdia = Math.random() * WIDTH;
		double ydia = Math.random() * HEIGHT;
		
		for(int i = 1; i < spicy[3]; i++) {
			
			Diagonal dia = new Diagonal();
			dia.setX((int)xdia);
			dia.setY((int)ydia);
			diaList.add(dia);
		}
	}
	public void spaceCreate() {
		for(int i = 0; i < SPACEBALL; i++) {
			double xs = Math.random() * WIDTH;
			double ys = Math.random() * HEIGHT;
			Garo space = new Garo();
			space.setX((int)xs);
			space.setY((int)ys);
			spaceList.add(space);
		}
	}
	
	public void draw() {
		
		  Graphics g = image.getGraphics();						// �̹��� ���۸� �����Ͽ� �� �ȿ� ��ü���� �׷��ְ� g2���� ����ؼ� ��ü�� �̹����� �׷�����
		  														// 				�̷��� �ؾ� ������ �����̴� ����� �ε巯������!
		  g.setColor(Color.BLACK);								// ���� ����
		  g.fillRect(0, 0, WIDTH, HEIGHT);
		  
		  g.setColor(new Color((int)(Math.random() * 255.0), (int)(Math.random() * 255.0),
				  (int)(Math.random() * 255.0) ));								// ���� ���� ��
		  g.fillOval(x, y, SIZE, SIZE);
		  
		  g.setColor(Color.YELLOW);								// ���ʿ��� ������
		  for(int i = 0; i < garoList.size(); i++) {
			  Garo garo = (Garo)garoList.get(i);				// ����ȯ
			  g.fillOval(garo.getX(), garo.getY(), garo.getRad(), garo.getRad());
			  if(garo.getX() > WIDTH)								// ȭ��Ѿ�� ����
				  garoList.remove(i);
			  garo.move();
		  }
		  
		  g.setColor(Color.YELLOW);								// ������ �Ʒ���
		  for(int i = 0; i < seroList.size(); i++) {
			  Sero sero = (Sero)seroList.get(i);				// ����ȯ
			  g.fillOval(sero.getX(), sero.getY(), sero.getRad(), sero.getRad());
			  if(sero.getX() > WIDTH)								// // ȭ��Ѿ�� ����
				  seroList.remove(i);
			  sero.move();
		  }
		  
		  g.setColor(Color.YELLOW);								// ������ ����
		  for(int i = 0; i < garo2List.size(); i++) {
			  Garo2 garo2 = (Garo2)garo2List.get(i);				// ����ȯ
			  g.fillOval(garo2.getX(), garo2.getY(), garo2.getRad(), garo2.getRad());
			  if(garo2.getX() < 0)									// ȭ��Ѿ�� ����
				  garo2List.remove(i);
			  garo2.move();
		  }
		  
		  g.setColor(Color.YELLOW);								// �밢�� 3->7
		  for(int i = 0; i < diaList.size(); i++) {
			  Diagonal dia = (Diagonal)diaList.get(i);				// ����ȯ
			  g.fillOval(dia.getX(), dia.getY(), dia.getRad(), dia.getRad());
			  
			  if(dia.getX() < 0) {									// ȭ��Ѿ�� ����
				  
				  diaList.remove(i);
			  }
			  dia.move();
		  }
		  
		  g.setColor(Color.WHITE);								// ���ְ��� ���
		  for(int i = 0; i < SPACEBALL; i++) {
			  Garo space = (Garo)spaceList.get(i);
			  g.fillOval(space.getX(), space.getY(), SPACEBALLSIZE, SPACEBALLSIZE);				// ���׸��� ��� ���� ���� �� ����ó�� �����ų ����
			  if(space.getX() > WIDTH)
				  spaceList.remove(i);
			  space.move();
		  }
		  
		  g.drawImage(showlife.getImage(), 0, 690, null);		// Life :
		  for(int i = 0; i < life; i++) {
			  
			  g.drawImage(heart.getImage(), 160 + 80*i, 700, null);		// ������ ��Ʈ����
		  }
		  
		  Graphics g2 = this.getGraphics();						// ������ ��ü�� �̹������·� �׸��� -> �����带 ���ؼ� ��� �ҷ����� ����
		  g2.drawImage(image, 0, 0, WIDTH, HEIGHT, this);
		  
	}
	
	public void keyPressed(KeyEvent e){
		
		int keycode = e.getKeyCode();
		
		switch(keycode){
		case KeyEvent.VK_LEFT : 
			left = true;
			break;
		case KeyEvent.VK_RIGHT :
			right = true;
			break;
		case KeyEvent.VK_UP :
			up = true;
			break;
		case KeyEvent.VK_DOWN : 
			down = true;
			break;
		case KeyEvent.VK_ESCAPE :	// ESCŰ�� ������ ���ᰡ �� �� �ֵ��� �Ѵ�.
			System.exit(0);
			break;
		case KeyEvent.VK_F11:		// �׽�Ʈ�� ���� �ѹ��� ���ӿ��� �Ǵ� ��Ȳ�� Ű�� �����صд�.
			life = 0;
			break;
		case KeyEvent.VK_F12:		// �׽�Ʈ�� ���ؼ� ���Ƿ� ����� �÷��ִ� Ű�� �����صд� 
			life += 1;
			break;
		default:
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		int keycode = e.getKeyCode();
		switch(keycode){
		case KeyEvent.VK_LEFT : 
			left = false;
			break;
		case KeyEvent.VK_RIGHT :
			right = false;
			break;
		case KeyEvent.VK_UP :
			up = false;
			break;
		case KeyEvent.VK_DOWN : 
			down = false;
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void losing_life() {
		
		Polygon poly = null;							// �ٰ��� �׸���
		
		for(int i = 0; i < garoList.size(); i++) {
			
			Garo ga = (Garo)garoList.get(i);
			
			int[] xpoint = {x, (x + ga.getRad()), (x + ga.getRad()), x};		// �簢�� 4���� ���� x�� ��ǥ�� �׷��ش�.
			int[] ypoint = {y, y, (y + ga.getRad()), (y + ga.getRad())};		// �簢�� 4���� ���� y�� ��ǥ�� �׷��ش�.
			poly = new Polygon(xpoint, ypoint, 4);
			if(poly.intersects((double)ga.getX(), (double)ga.getY(), (double)ga.getRad(), (double)ga.getRad())) {
				garoList.remove(i);		// �÷��̾��� �ٰ����� ���� ��ü�� ���� �浹 ��
				life -= 1;
			}
		}
		
		for(int i = 0; i < garo2List.size(); i++) {
			
			Garo2 ga2 = (Garo2)garo2List.get(i);
			
			int[] xpoint = {x, (x + ga2.getRad()), (x + ga2.getRad()), x};		// �簢�� 4���� ���� x�� ��ǥ�� �׷��ش�.
			int[] ypoint = {y, y, (y + ga2.getRad()), (y + ga2.getRad())};		// �簢�� 4���� ���� y�� ��ǥ�� �׷��ش�.
			poly = new Polygon(xpoint, ypoint, 4);
			if(poly.intersects((double)ga2.getX(), (double)ga2.getY(), (double)ga2.getRad(), (double)ga2.getRad())) {
				garo2List.remove(i);		// �÷��̾��� �ٰ����� ���� ��ü�� ���� �浹 ��
				life -= 1;
			}
		}
		
		for(int i = 0; i < seroList.size(); i++) {
			
			Sero se = (Sero)seroList.get(i);
			
			int[] xpoint = {x, (x + SIZE), (x + SIZE), x};
			int[] ypoint = {y, y, (y + SIZE), (y + SIZE)};
			poly = new Polygon(xpoint, ypoint, 4);
			if(poly.intersects((double)se.getX(), (double)se.getY(), (double)se.getRad(), (double)se.getRad())) {
				seroList.remove(i);		// �÷��̾��� �ٰ����� ���� ��ü�� ���� �浹 ��
				life -= 1;
			}
		}
		
		for(int i = 0; i < diaList.size(); i++) {
			
			Diagonal dia = (Diagonal)diaList.get(i);
			
			int[] xpoint = {x, (x + SIZE), (x + SIZE), x};		// �簢�� 4���� ���� x�� ��ǥ�� �׷��ش�.
			int[] ypoint = {y, y, (y + SIZE), (y + SIZE)};		// �簢�� 4���� ���� y�� ��ǥ�� �׷��ش�.
			poly = new Polygon(xpoint, ypoint, 4);
			if(poly.intersects((double)dia.getX(), (double)dia.getY(), (double)dia.getRad(), (double)dia.getRad())) {
				diaList.remove(i);		// �÷��̾��� �ٰ����� �밢�� ��ü�� ���� �浹 ��
				life -= 1;
			}
		}
	}
}