package game.frame;

import game.pattern.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameMainPanel extends JPanel implements Runnable, KeyListener{

	private final BufferedImage image;		// 화면 프레임 전체 이미지를 담을 것
	private boolean left = false, right = false, up = false, down = false;

	private static final int WIDTH = 1500; 			// 전체 폭
	private static final int HEIGHT = 800; 			// 전체 높이
	private int x = 750, y = 400;			// 움직일 구체 초기 위치(구체 좌표)
	private final int SIZE = 20;					// 구체 사이즈
	private int PlayerSpeed = 4;				// 플레이어 속도
	private int life = 8;						// 플레이어 목숨 8개
	private final static int SPACE_BALL = 120;					// 우주배경 구체 수
	private final static int SPACE_BALL_SIZE = 3;

	private final ImageIcon heartIcon;
	private final ImageIcon showLifeIcon;

	protected boolean isGameOver = false;				// 게임오버 창을 한번만 만들기 위한 변수를 하나 생성
	private int stageNumber = 0;
	private int ballCount = 0;						// 세로로 나오는 구체 수 통제

	private final int[] difficulty = new int[4];		// 난이도 조절용 변수

	private final ArrayList<Ball> horizontalList;		// 가로로 나오는 구체 리스트
	private final ArrayList<Ball> reverseHorizontalList;		// 가로로 나오는 구체 리스트
	private final ArrayList<Ball> spaceList;		// 가로로 나오는 우주 배경 리스트
	private final ArrayList<Ball> verticalList;		// 세로로 나오는 구체 리스트
	private final ArrayList<Ball> diagonalList;

	protected String date;
	protected StageScoreFrame SSF = new StageScoreFrame();
	protected GameOver gameover;				// 게임오버 화면 출력
	protected Nickname nk;					// 게임오버 시 닉네임 입력

	public GameMainPanel() {

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);		// 가로,세로와,정수형타입의 RGB

		this.setLayout(null);						// 좌표를 내마음대로

		horizontalList = new ArrayList<>();
		reverseHorizontalList = new ArrayList<>();
		spaceList = new ArrayList<>();
		verticalList = new ArrayList<>();
		diagonalList = new ArrayList<>();

		heartIcon = new ImageIcon("files/heart.png");
		showLifeIcon = new ImageIcon("files/Life.png");

		addKeyListener(this);
		this.requestFocus();		// 패널은 그냥 포커스를 받을수가 없다
		setFocusable(true);

	}

	@Override
	public void run() {							// Runnable 인터페이스를 사용해서 필수적인 오버라이드!

		// TODO Auto-generated method stub
		try {
			while(true) {
				//noinspection BusyWait
				Thread.sleep(8);
				spaceCreate(); 			// 우주 배경
				loseLifePoint();

				// 스테이지 1
				if( ballCount > 100 && SSF.getStagepoint() == 1) {

					difficulty[0] = 10;

					leftCreate(); rightCreate(); upCreate();

					ballCount = 0;
					stageNumber = SSF.getStagepoint();				// 스테이지를 기억해줌 stagecounting = 1;
				}

				// 스테이지가 바뀔 때마다 life를 하나씩 준다.
				if( stageNumber < SSF.getStagepoint()) {
					life++;
					stageNumber = SSF.getStagepoint();
				}

				// 스테이지 2
				if(ballCount > 150 && SSF.getStagepoint() == 2) {

					difficulty[0] = 10;		difficulty[1] = 10;

					leftCreate(); 		rightCreate();
					ballCount = 0;
					stageNumber = SSF.getStagepoint();
				}
				// 스테이지 3
				if( ballCount > 250 && SSF.getStagepoint() == 3) {		// 곳곳에서 팡팡 터진다!!!!

					difficulty[3] = 10;

					for(int i = 0; i < 6; i++)
						diaCreate();
					ballCount = 0;
					stageNumber = SSF.getStagepoint();
				}
				// 스테이지 4
				if( ballCount > 300 && SSF.getStagepoint() == 4) {

					difficulty[0] = 10;
					difficulty[1] = 10;

					rightCreate(); 	leftCreate();

					difficulty[3] = 10;
					for(int i = 0; i < 5; i++)
						diaCreate();
					ballCount = 0;
					stageNumber = SSF.getStagepoint();
				}
				// 스테이지 5
				if( ballCount > 300 && SSF.getStagepoint() == 5) {

					for(int i = 0; i < 3; i++)
						difficulty[i] = (i*10) + 5;

					upCreate();		rightCreate();		leftCreate();

					for(int i = 0; i < 5; i++)
						diaCreate();

					ballCount = 0;
					stageNumber = SSF.getStagepoint();
				}
				// 스테이지 6
				if( ballCount > 250 && SSF.getStagepoint() == 6) {

					for(int i = 0; i < 3; i++)
						difficulty[i] = (i*10) + 2;

					upCreate();		rightCreate();		leftCreate();

					for(int i = 0; i < 10; i++)
						diaCreate();
					ballCount = 0;
					stageNumber = SSF.getStagepoint();
				}

				// 스테이지 7 이후
				if( ballCount > 200 && SSF.getStagepoint() >= 7) {

					for(int i = 0; i < 3; i++)
						difficulty[i] = (int)(Math.random() * 15) + 4;

					upCreate();		rightCreate();		leftCreate();
					for(int i = 0; i < 15; i++)
						diaCreate();
					ballCount = 0;
				}

				ballCount++;
				draw();
				keyControl();

				if(life <= 0 && !isGameOver) {
					//noinspection ResultOfMethodCallIgnored
					Thread.interrupted();
					isGameOver = true;
					PlayerSpeed = 0;
					SSF.setPause(true);
					SSF.setResume(false);
					gameover = new GameOver();
					nk = new Nickname();
					date = new java.text.SimpleDateFormat("MM/dd_HH:mm:ss").format(new java.util.Date());
					break;
				}

			}
		} catch(Exception e) {
			System.out.println("정상적인 오류입니다.");
		}
	}

	// 화살표 키를 받아서 실제로 값을 받는 메소드
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
	// 구체 이동 메소드
	// 가로로 왼쪽에서 나오는 구체 생성
	public void leftCreate() {
		for(int i = 0; i < difficulty[0]; i++) {
			double xc = (Math.random() * 60) - 200;		// 시작 위치
			double yc = Math.random() * HEIGHT;	// 시작 위치
			HorizontalBall horizontalBall = new HorizontalBall();
			horizontalBall.setX((int)xc);
			horizontalBall.setY((int)yc);
			horizontalList.add(horizontalBall);
		}
	}
	// 가로로 오른쪽에서 나오는 구체 생성
	public void rightCreate() {
		for(int i = 0; i < difficulty[1]; i++) {
			double xc = WIDTH + 200 - (Math.random() * 60);		// 시작 위치
			double yc = Math.random() * HEIGHT;	// 시작 위치
			ReverseHorizontalBall reverseHorizontalBall = new ReverseHorizontalBall();
			reverseHorizontalBall.setX((int)xc);
			reverseHorizontalBall.setY((int)yc);
			reverseHorizontalList.add(reverseHorizontalBall);
		}
	}
	// 세로로 나오는 구체 생성
	public void upCreate() {
		for(int i = 0; i < difficulty[2]; i++) {
			double xc = Math.random() * WIDTH;
			double yc = (Math.random() * 100) - HEIGHT;	// 시작 위치
			VerticalBall verticalBall = new VerticalBall();
			verticalBall.setX((int)xc);
			verticalBall.setY((int)yc);
			verticalList.add(verticalBall);
		}
	}
	// 대각선으로 나오는 구체 생성
	public void diaCreate() {

		double xdia = Math.random() * WIDTH;
		double ydia = Math.random() * HEIGHT;

		for(int i = 1; i < difficulty[3]; i++) {

			DiagonalBall dia = new DiagonalBall();
			dia.setX((int)xdia);
			dia.setY((int)ydia);
			diagonalList.add(dia);
		}
	}
	public void spaceCreate() {
		for(int i = 0; i < SPACE_BALL; i++) {
			double xs = Math.random() * WIDTH;
			double ys = Math.random() * HEIGHT;
			HorizontalBall space = new HorizontalBall();
			space.setX((int)xs);
			space.setY((int)ys);
			spaceList.add(space);
		}
	}

	public void draw() {

		Graphics g = image.getGraphics();						// 이미지 버퍼를 생성하여 이 안에 객체들을 그려넣고 g2에서 계속해서 전체를 이미지로 그려낸다
		// 				이렇게 해야 유저를 움직이는 모션이 부드러워졌다!
		g.setColor(Color.BLACK);								// 검은 바탕
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(new Color((int)(Math.random() * 255.0), (int)(Math.random() * 255.0),
				(int)(Math.random() * 255.0) ));								// 유저 모양과 색
		g.fillOval(x, y, SIZE, SIZE);

		g.setColor(Color.YELLOW);								// 왼쪽에서 오른쪽
		// TODO: Loop에서 remove하는 연산은 개선이 필요하다
		for(int i = 0; i < horizontalList.size(); i++) {
			HorizontalBall horizontalBall = (HorizontalBall) horizontalList.get(i);				// 형변환
			g.fillOval(horizontalBall.getX(), horizontalBall.getY(), horizontalBall.getRad(), horizontalBall.getRad());
			if(horizontalBall.getX() > WIDTH)								// 화면넘어가면 삭제
				horizontalList.remove(i);
			horizontalBall.move();
		}

		g.setColor(Color.YELLOW);								// 위에서 아래로
		for(int i = 0; i < verticalList.size(); i++) {
			VerticalBall verticalBall = (VerticalBall) verticalList.get(i);				// 형변환
			g.fillOval(verticalBall.getX(), verticalBall.getY(), verticalBall.getRad(), verticalBall.getRad());
			if(verticalBall.getX() > WIDTH)								// // 화면넘어가면 삭제
				verticalList.remove(i);
			verticalBall.move();
		}

		g.setColor(Color.YELLOW);								// 오른쪽 가로
		for(int i = 0; i < reverseHorizontalList.size(); i++) {
			ReverseHorizontalBall reverseHorizontalBall = (ReverseHorizontalBall) reverseHorizontalList.get(i);				// 형변환
			g.fillOval(reverseHorizontalBall.getX(), reverseHorizontalBall.getY(), reverseHorizontalBall.getRad(), reverseHorizontalBall.getRad());
			if(reverseHorizontalBall.getX() < 0)									// 화면넘어가면 삭제
				reverseHorizontalList.remove(i);
			reverseHorizontalBall.move();
		}

		g.setColor(Color.YELLOW);								// 대각선 3->7
		for(int i = 0; i < diagonalList.size(); i++) {
			DiagonalBall dia = (DiagonalBall) diagonalList.get(i);				// 형변환
			g.fillOval(dia.getX(), dia.getY(), dia.getRad(), dia.getRad());

			if(dia.getX() < 0) {									// 화면넘어가면 삭제

				diagonalList.remove(i);
			}
			dia.move();
		}

		g.setColor(Color.WHITE);								// 우주같은 배경
		for(int i = 0; i < SPACE_BALL; i++) {
			HorizontalBall space = (HorizontalBall)spaceList.get(i);
			g.fillOval(space.getX(), space.getY(), SPACE_BALL_SIZE, SPACE_BALL_SIZE);				// 조그마한 흰색 점을 많이 찍어서 우주처럼 연출시킬 예정
			if(space.getX() > WIDTH)
				spaceList.remove(i);
			space.move();
		}

		g.drawImage(showLifeIcon.getImage(), 0, 690, null);		// Life :
		for(int i = 0; i < life; i++) {

			g.drawImage(heartIcon.getImage(), 160 + 80*i, 700, null);		// ♥♥♥ 하트갯수
		}

		Graphics g2 = this.getGraphics();						// 프레임 전체를 이미지형태로 그린다 -> 쓰레드를 통해서 계속 불러오기 위함
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
			case KeyEvent.VK_ESCAPE :	// ESC키를 누르면 종료가 될 수 있도록 한다.
				System.exit(0);
				break;
			case KeyEvent.VK_F11:		// 테스트를 위해 한번에 게임오버 되는 상황을 키로 설정해둔다.
				life = 0;
				break;
			case KeyEvent.VK_F12:		// 테스트를 위해서 임의로 목숨을 늘려주는 키를 설정해둔다
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

	private void loseLifePoint() {
		collide(x, y, horizontalList);
		collide(x, y, reverseHorizontalList);
		collide(x, y, verticalList);
		collide(x, y, diagonalList);
	}

	private void collide(int x, int y, ArrayList<Ball> ballList) {
		for (int i = 0, len = ballList.size(); i < len; i++) {
			Ball ball = ballList.get(i);
			int rad = ball.getRad();
			int[] xPoint = {x, x + rad, x + rad, x};
			int[] yPoint = {y, y + rad, y + rad, y};
			Polygon polygon = new Polygon(xPoint, yPoint, 4);
			if (polygon.intersects(ball.getX(), ball.getY(), rad, rad)) {
				horizontalList.remove(i);
				life -= 1;
			}
		}
	}
}