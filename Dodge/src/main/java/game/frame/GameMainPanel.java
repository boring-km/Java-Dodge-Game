package game.frame;

import game.pattern.Diagonal;
import game.pattern.Garo;
import game.pattern.Garo2;
import game.pattern.Sero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameMainPanel extends JPanel implements Runnable, KeyListener{

	private BufferedImage image;		// 화면 프레임 전체 이미지를 담을 것
	private boolean left = false, right = false, up = false, down = false;

	private static final int WIDTH = 1500; 			// 전체 폭
	private static final int HEIGHT = 800; 			// 전체 높이
	private int x = 750, y = 400;			// 움직일 구체 초기 위치(구체 좌표)
	private final int SIZE = 20;					// 구체 사이즈
	private int PlayerSpeed = 4;				// 플레이어 속도
	private int life = 8;						// 플레이어 목숨 8개
	private final int SPACEBALL = 120;					// 우주배경 구체 수
	private final int SPACEBALLSIZE = 3;

	private ImageIcon heart;
	private ImageIcon showlife;

	protected boolean gameisover = false;				// 게임오버 창을 한번만 만들기 위한 변수를 하나 생성
	private int stagecounting = 0;
	private int ball_count = 0;						// 세로로 나오는 구체 수 통제

	private int[] spicy = new int[4];		// 난이도 조절용 변수

	private ArrayList<Object> garoList;		// 가로로 나오는 구체 리스트
	private ArrayList<Object> garo2List;		// 가로로 나오는 구체 리스트
	private ArrayList<Object> spaceList;		// 가로로 나오는 우주 배경 리스트
	private ArrayList<Object> seroList;		// 세로로 나오는 구체 리스트
	private ArrayList<Object> diaList;

	protected String date;
	protected StageScoreFrame SSF = new StageScoreFrame();
	protected GameOver gameover;				// 게임오버 화면 출력
	protected Nickname nk;					// 게임오버 시 닉네임 입력

	public GameMainPanel() {

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);		// 가로,세로와,정수형타입의 RGB

		this.setLayout(null);						// 좌표를 내마음대로

		garoList = new ArrayList<>();
		garo2List = new ArrayList<>();
		spaceList = new ArrayList<>();
		seroList = new ArrayList<>();
		diaList = new ArrayList<>();

		heart = new ImageIcon("files/heart.png");
		showlife = new ImageIcon("files/Life.png");

		addKeyListener(this);
		this.requestFocus();		// 패널은 그냥 포커스를 받을수가 없다
		setFocusable(true);

	}

	@Override
	public void run() {							// Runnable 인터페이스를 사용해서 필수적인 오버라이드!

		// TODO Auto-generated method stub
		try {
			while(true) {
				Thread.sleep(8);
				spaceCreate(); 			// 우주 배경
				losing_life();

				// 스테이지 1
				if( ball_count > 100 && SSF.getStagepoint() == 1) {

					spicy[0] = 10;

					leftCreate(); rightCreate(); upCreate();

					ball_count = 0;
					stagecounting = SSF.getStagepoint();				// 스테이지를 기억해줌 stagecounting = 1;
				}

				// 스테이지가 바뀔 때마다 life를 하나씩 준다.
				if( stagecounting < SSF.getStagepoint()) {
					life++;
					stagecounting = SSF.getStagepoint();
				}

				// 스테이지 2
				if(ball_count > 150 && SSF.getStagepoint() == 2) {

					spicy[0] = 10;		spicy[1] = 10;

					leftCreate(); 		rightCreate();
					ball_count = 0;
					stagecounting = SSF.getStagepoint();
				}
				// 스테이지 3
				if( ball_count > 250 && SSF.getStagepoint() == 3) {		// 곳곳에서 팡팡 터진다!!!!

					spicy[3] = 10;

					for(int i = 0; i < 6; i++)
						diaCreate();
					ball_count = 0;
					stagecounting = SSF.getStagepoint();
				}
				// 스테이지 4
				if( ball_count > 300 && SSF.getStagepoint() == 4) {

					spicy[0] = 10;
					spicy[1] = 10;

					rightCreate(); 	leftCreate();

					spicy[3] = 10;
					for(int i = 0; i < 5; i++)
						diaCreate();
					ball_count = 0;
					stagecounting = SSF.getStagepoint();
				}
				// 스테이지 5
				if( ball_count > 300 && SSF.getStagepoint() == 5) {

					for(int i = 0; i < 3; i++)
						spicy[i] = (i*10) + 5;

					upCreate();		rightCreate();		leftCreate();

					for(int i = 0; i < 5; i++)
						diaCreate();

					ball_count = 0;
					stagecounting = SSF.getStagepoint();
				}
				// 스테이지 6
				if( ball_count > 250 && SSF.getStagepoint() == 6) {

					for(int i = 0; i < 3; i++)
						spicy[i] = (i*10) + 2;

					upCreate();		rightCreate();		leftCreate();

					for(int i = 0; i < 10; i++)
						diaCreate();
					ball_count = 0;
					stagecounting = SSF.getStagepoint();
				}

				// 스테이지 7 이후
				if( ball_count > 200 && SSF.getStagepoint() >= 7) {

					for(int i = 0; i < 3; i++)
						spicy[i] = (int)(Math.random() * 15) + 4;

					upCreate();		rightCreate();		leftCreate();
					for(int i = 0; i < 15; i++)
						diaCreate();
					ball_count = 0;
				}

				ball_count++;
				draw();
				keyControl();

				if(life <= 0 && !gameisover) {
					Thread.interrupted();
					gameisover = true;
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
		for(int i = 0; i < spicy[0]; i++) {
			double xc = (Math.random() * 60) - 200;		// 시작 위치
			double yc = Math.random() * HEIGHT;	// 시작 위치
			Garo garo = new Garo();
			garo.setX((int)xc);
			garo.setY((int)yc);
			garoList.add(garo);
		}
	}
	// 가로로 오른쪽에서 나오는 구체 생성
	public void rightCreate() {
		for(int i = 0; i < spicy[1]; i++) {
			double xc = WIDTH + 200 - (Math.random() * 60);		// 시작 위치
			double yc = Math.random() * HEIGHT;	// 시작 위치
			Garo2 garo2 = new Garo2();
			garo2.setX((int)xc);
			garo2.setY((int)yc);
			garo2List.add(garo2);
		}
	}
	// 세로로 나오는 구체 생성
	public void upCreate() {
		for(int i = 0; i < spicy[2]; i++) {
			double xc = Math.random() * WIDTH;
			double yc = (Math.random() * 100) - HEIGHT;	// 시작 위치
			Sero sero = new Sero();
			sero.setX((int)xc);
			sero.setY((int)yc);
			seroList.add(sero);
		}
	}
	// 대각선으로 나오는 구체 생성
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

		Graphics g = image.getGraphics();						// 이미지 버퍼를 생성하여 이 안에 객체들을 그려넣고 g2에서 계속해서 전체를 이미지로 그려낸다
		// 				이렇게 해야 유저를 움직이는 모션이 부드러워졌다!
		g.setColor(Color.BLACK);								// 검은 바탕
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(new Color((int)(Math.random() * 255.0), (int)(Math.random() * 255.0),
				(int)(Math.random() * 255.0) ));								// 유저 모양과 색
		g.fillOval(x, y, SIZE, SIZE);

		g.setColor(Color.YELLOW);								// 왼쪽에서 오른쪽
		for(int i = 0; i < garoList.size(); i++) {
			Garo garo = (Garo)garoList.get(i);				// 형변환
			g.fillOval(garo.getX(), garo.getY(), garo.getRad(), garo.getRad());
			if(garo.getX() > WIDTH)								// 화면넘어가면 삭제
				garoList.remove(i);
			garo.move();
		}

		g.setColor(Color.YELLOW);								// 위에서 아래로
		for(int i = 0; i < seroList.size(); i++) {
			Sero sero = (Sero)seroList.get(i);				// 형변환
			g.fillOval(sero.getX(), sero.getY(), sero.getRad(), sero.getRad());
			if(sero.getX() > WIDTH)								// // 화면넘어가면 삭제
				seroList.remove(i);
			sero.move();
		}

		g.setColor(Color.YELLOW);								// 오른쪽 가로
		for(int i = 0; i < garo2List.size(); i++) {
			Garo2 garo2 = (Garo2)garo2List.get(i);				// 형변환
			g.fillOval(garo2.getX(), garo2.getY(), garo2.getRad(), garo2.getRad());
			if(garo2.getX() < 0)									// 화면넘어가면 삭제
				garo2List.remove(i);
			garo2.move();
		}

		g.setColor(Color.YELLOW);								// 대각선 3->7
		for(int i = 0; i < diaList.size(); i++) {
			Diagonal dia = (Diagonal)diaList.get(i);				// 형변환
			g.fillOval(dia.getX(), dia.getY(), dia.getRad(), dia.getRad());

			if(dia.getX() < 0) {									// 화면넘어가면 삭제

				diaList.remove(i);
			}
			dia.move();
		}

		g.setColor(Color.WHITE);								// 우주같은 배경
		for(int i = 0; i < SPACEBALL; i++) {
			Garo space = (Garo)spaceList.get(i);
			g.fillOval(space.getX(), space.getY(), SPACEBALLSIZE, SPACEBALLSIZE);				// 조그마한 흰색 점을 많이 찍어서 우주처럼 연출시킬 예정
			if(space.getX() > WIDTH)
				spaceList.remove(i);
			space.move();
		}

		g.drawImage(showlife.getImage(), 0, 690, null);		// Life :
		for(int i = 0; i < life; i++) {

			g.drawImage(heart.getImage(), 160 + 80*i, 700, null);		// ♥♥♥ 하트갯수
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

	public void losing_life() {

		Polygon poly = null;							// 다각형 그리기

		for(int i = 0; i < garoList.size(); i++) {

			Garo ga = (Garo)garoList.get(i);

			int[] xpoint = {x, (x + ga.getRad()), (x + ga.getRad()), x};		// 사각형 4개의 점의 x의 좌표를 그려준다.
			int[] ypoint = {y, y, (y + ga.getRad()), (y + ga.getRad())};		// 사각형 4개의 점의 y의 좌표를 그려준다.
			poly = new Polygon(xpoint, ypoint, 4);
			if(poly.intersects((double)ga.getX(), (double)ga.getY(), (double)ga.getRad(), (double)ga.getRad())) {
				garoList.remove(i);		// 플레이어의 다각형과 가로 구체들 간의 충돌 시
				life -= 1;
			}
		}

		for(int i = 0; i < garo2List.size(); i++) {

			Garo2 ga2 = (Garo2)garo2List.get(i);

			int[] xpoint = {x, (x + ga2.getRad()), (x + ga2.getRad()), x};		// 사각형 4개의 점의 x의 좌표를 그려준다.
			int[] ypoint = {y, y, (y + ga2.getRad()), (y + ga2.getRad())};		// 사각형 4개의 점의 y의 좌표를 그려준다.
			poly = new Polygon(xpoint, ypoint, 4);
			if(poly.intersects((double)ga2.getX(), (double)ga2.getY(), (double)ga2.getRad(), (double)ga2.getRad())) {
				garo2List.remove(i);		// 플레이어의 다각형과 가로 구체들 간의 충돌 시
				life -= 1;
			}
		}

		for(int i = 0; i < seroList.size(); i++) {

			Sero se = (Sero)seroList.get(i);

			int[] xpoint = {x, (x + SIZE), (x + SIZE), x};
			int[] ypoint = {y, y, (y + SIZE), (y + SIZE)};
			poly = new Polygon(xpoint, ypoint, 4);
			if(poly.intersects((double)se.getX(), (double)se.getY(), (double)se.getRad(), (double)se.getRad())) {
				seroList.remove(i);		// 플레이어의 다각형과 세로 구체들 간의 충돌 시
				life -= 1;
			}
		}

		for(int i = 0; i < diaList.size(); i++) {

			Diagonal dia = (Diagonal)diaList.get(i);

			int[] xpoint = {x, (x + SIZE), (x + SIZE), x};		// 사각형 4개의 점의 x의 좌표를 그려준다.
			int[] ypoint = {y, y, (y + SIZE), (y + SIZE)};		// 사각형 4개의 점의 y의 좌표를 그려준다.
			poly = new Polygon(xpoint, ypoint, 4);
			if(poly.intersects((double)dia.getX(), (double)dia.getY(), (double)dia.getRad(), (double)dia.getRad())) {
				diaList.remove(i);		// 플레이어의 다각형과 대각선 구체들 간의 충돌 시
				life -= 1;
			}
		}
	}
}