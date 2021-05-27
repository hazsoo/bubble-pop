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
 * @author Jisoo Ha
 *
 */
public class NoteBorder extends JComponent implements Runnable {
	
	private static final int MAX_RADIUS = 1280;
	
	private int centerX, centerY; // 노트 센터 좌표
	public  int radius = MAX_RADIUS; // 최대 지름
	private int t; // 노트 생성 시간
	
	private Color color;
	
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
	
	
	public void paint(Graphics g) {
		radius -= Main.NOTE_SPEED1; // 지름 계속 줄이기
		g.setColor(color);
		g.fillOval(centerX - radius / 2, centerY - radius / 2, radius, radius);
	}
	
	public void resizeCircle() {
		repaint();
	}
	
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}
