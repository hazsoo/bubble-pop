package bubblepop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

/**
 * 노트 그래픽 클래스.<br/>
 * 주요 기능
 * <ol>
 *  <li>노트 그리기</li>
 *  <li>지름 줄어든 노트 그리기</li>
 * </ol>
 * @author Jisoo Ha
 *
 */
public class NoteBorder extends JComponent implements Runnable {
	
	/**
	 * 노트 최대 지름
	 */
	private static final int MAX_RADIUS = 1280;
	
	/**
	 * 노트 센터 좌표
	 */
	private int centerX, centerY;
	/**
	 * 노트 지름
	 */
	public  int radius = MAX_RADIUS;
	/**
	 * 노트 생성 시간
	 */
	private int t;
	
	/**
	 * 노트 색깔
	 */
	private Color color;
	
	/**
	 * 스레드 동작 메서드
	 */
	public void run() {
		while(!Thread.interrupted()) {
			synchronized (this) {
				try {
					wait();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Beat 클래스의 배열의 노트를 입력받아 각 노트의 센터 좌표를 구하는 메서드
	 * @param t 노트 생성 시간
	 * @param noteType 노트 종류
	 */
	public NoteBorder(int t, String noteType) {
		this.t = t;
		if(noteType.equals("A")) {
			centerX = Note.getCenter(Main.BLUE_X, Main.BLUE_Y, 70)[0];
			centerY = Note.getCenter(Main.BLUE_X, Main.BLUE_Y, 70)[1];
			color = new Color(20, 120, 179, 100);
		} else if(noteType.equals("S")) {
			centerX = Note.getCenter(Main.GREEN_X, Main.GREEN_Y, 70)[0];
			centerY = Note.getCenter(Main.GREEN_X, Main.GREEN_Y, 70)[1];
			color = new Color(81, 204, 59, 100);
		} else if(noteType.equals("K")) {
			centerX = Note.getCenter(Main.RED_X, Main.RED_Y, 70)[0];
			centerY = Note.getCenter(Main.RED_X, Main.RED_Y, 70)[1];
			color = new Color(232, 98, 49, 100);
		} else if(noteType.equals("L")) {
			centerX = Note.getCenter(Main.YELLOW_X, Main.YELLOW_Y, 70)[0];
			centerY = Note.getCenter(Main.YELLOW_X, Main.YELLOW_Y, 70)[1];
			color = new Color(247, 250, 65, 100);
		}
		setBounds(0, 0, 3000, 3000);
	}
	
	/**
	 * Graphics로 노트를 그림
	 */
	public void paint(Graphics g) {
		radius -= Main.NOTE_SPEED1; // 지름 계속 줄이기
		g.setColor(color);
		g.fillOval(centerX - radius / 2, centerY - radius / 2, radius, radius);
	}
	
	/**
	 * paint()를 다시 실행
	 */
	public void resizeCircle() {
		repaint();
	}
	
	/**
	 * 노트의 지름을 받음
	 * @return 지름
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * 노트의 지름을 설정
	 * @param radius 지름
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}
}
