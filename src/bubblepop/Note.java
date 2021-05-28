package bubblepop;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 판정 원 클래스.
 * @author Jisoo Ha
 *
 */
public class Note extends JButton {
	
	/**
	 * 판정 원의 이미지를 담는 메서드
	 * @param image 이미지
	 */
	public Note(ImageIcon image) {
		super(image);
	}
	
	/**
	 * 프레임 좌표로 부터 원의 중심 구하는 메서드
	 * @param x x 좌표
	 * @param y y 좌표
	 * @param r 지름
	 * @return centerX, centerY 중심 x, y 좌표
	 */
	public static int[] getCenter(int x, int y, int r) {
		return new int[] {x + r / 2, y + r / 2};
	}
}
