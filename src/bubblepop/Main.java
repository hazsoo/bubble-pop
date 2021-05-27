package bubblepop;

/**
 * 메인 클래스.
 * @author Jisoo Ha
 *
 */
public class Main {
	/**
	 * 게임 제목
	 */
	public static final String GAMETITLE = "Bubble Pop";
	
	/**
	 * 음악 시작 시간
	 */
	public static final int START_TIME = 4050;
	
	/**
	 * 게임 화면 너비
	 */
	public static final int SCREEN_WIDTH = 1280;
	/**
	 * 게임 화면 높이
	 */
	public static final int SCREEN_HEIGHT = 720;
	
	/**
	 * 노트 속도1
	 */
	public static final int NOTE_SPEED1 = 25;
	/**
	 * 노트 속도2
	 */
	public static final int NOTE_SPEED2 = 10;
	
	/**
	 * 파란노트 x 좌표
	 */
	public static final int BLUE_X = 205;
	/**
	 * 파란노트 y 좌표
	 */
	public static final int BLUE_Y = 250;
	/**
	 * 초록노트 x 좌표
	 */
	public static final int GREEN_X = 440;
	/**
	 * 초록노트 y 좌표
	 */
	public static final int GREEN_Y = 400;
	/**
	 * 빨간노트 x 좌표
	 */
	public static final int RED_X = 760;
	/**
	 * 빨간노트 y 좌표
	 */
	public static final int RED_Y = 400;
	/**
	 * 노란노트 x 좌표
	 */
	public static final int YELLOW_X = 1000;
	/**
	 * 노란노트 y 좌표
	 */
	public static final int YELLOW_Y = 250;
	
	/**
	 * perfect 점수
	 */
	public static final int PERFECT_SCORE = 2000;
	/**
	 * great 점수
	 */
	public static final int GREAT_SCORE = 1600;
	/**
	 * good 점수
	 */
	public static final int GOOD_SCORE = 1000;
	/**
	 * bad 점수
	 */
	public static final int BAD_SCORE = 500;
	
	/**
	 * 메인 메서드
	 * @param args 메인
	 */
	public static void main(String[] args) {
		
		new Menu();
		
	}
	
}
