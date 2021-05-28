package bubblepop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

/**
 * 게임 클래스.<br/>
 * 주요 기능
 * <ol>
 *  <li>게임 실행 화면 초기화</li>
 *  <li>노트 생성</li>
 *  <li>오디오 재생</li>
 *  <li>게임 일시정지</li>
 *  <li>노트 판정 이벤트 키 리스닝</li>
 * </ol>
 * @author Jisoo Ha
 *
 */
public class Game extends JFrame implements KeyListener{

	/**
	 * 게임 실행창의 메인 프레임
	 */
	private JFrame frame = new JFrame();
	
	/**
	 * 일시정지 아이콘
	 */
	private ImageIcon pause = 
			new ImageIcon("E:/_00_java_project/beatgame/src/images/pauseBtn.png");
	/**
	 * 일시정지 아이콘에서 불러온 이미지
	 */
	private Image img0 = pause.getImage();
	/**
	 * 바뀐 스케일의 일시정지 이미지
	 */
	private Image changepauseImage = img0.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	/**
	 * 바뀐 스케일의 일시정지 아이콘
	 */
	private ImageIcon changepauseIcon = new ImageIcon(changepauseImage);
	/**
	 * 일시정지 버튼
	 */
	private JButton pauseBtn = new JButton(changepauseIcon);
	
	/**
	 * 파란색 KeyPressed 판정 아이콘
	 */
	private ImageIcon pressedblueIcon = 
			new ImageIcon("E:/_00_java_project/beatgame/src/images/pressedbluecircle.png");
	/**
	 * 파란색 KeyPressed 판정 아이콘에서 불러온 이미지
	 */
	private Image img = pressedblueIcon.getImage();
	/**
	 * 바뀐 스케일의 파란색 KeyPressed 판정 이미지
	 */
	private Image changepressedblueImage = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	
	/**
	 * 초록색 KeyPressed 판정 아이콘
	 */
	private ImageIcon pressedgreenIcon = 
			new ImageIcon("E:/_00_java_project/beatgame/src/images/pressedgreencircle.png");
	/**
	 * 초록색 KeyPressed 판정 아이콘에서 불러온 이미지
	 */
	private Image img2 = pressedgreenIcon.getImage();
	/**
	 * 바뀐 스케일의 초록색 KeyPressed 판정 이미지
	 */
	private Image changepressedgreenImage = img2.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	
	/**
	 * 빨간색 KeyPressed 판정 아이콘
	 */
	private ImageIcon pressedredIcon = 
			new ImageIcon("E:/_00_java_project/beatgame/src/images/pressedredcircle.png");
	/**
	 * 빨간색 KeyPressed 판정 아이콘에서 불러온 이미지
	 */
	private Image img3 = pressedredIcon.getImage();
	/**
	 * 바뀐 스케일의 빨간색 KeyPressed 판정 이미지
	 */
	private Image changepressedredImage = img3.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	
	/**
	 * 노란색 KeyPressed 판정 아이콘
	 */
	private ImageIcon pressedyellowIcon = 
			new ImageIcon("E:/_00_java_project/beatgame/src/images/pressedyellowcircle.png");
	/**
	 * 노란색 KeyPressed 판정 아이콘에서 불러온 이미지
	 */
	private Image img4 = pressedyellowIcon.getImage();
	/**
	 * 바뀐 스케일의 노란색 KeyPressed 판정 이미지
	 */
	private Image changepressedyellowImage = img4.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	
	/**
	 * 남은 목숨 라벨
	 */
	private JLabel lifeLb = new JLabel();
	/**
	 * perfect, great, miss 등 표시 라벨
	 */
	private JLabel perfectLb = new JLabel();
	/**
	 * 콤보 숫자 라벨
	 */
	private JLabel comboLb = new JLabel();
	/**
	 * 점수 라벨
	 */
	private JLabel scoreLb = new JLabel();
	
	/**
	 * 판정 원 리스트
	 */
	private ArrayList<Note> noteList = new ArrayList<Note>();
	/**
	 * keyPressed 판정 원 리스트
	 */
	private ArrayList<Note> pressednoteList = new ArrayList<Note>();
	/**
	 * 노트 리스트
	 */
	private ArrayList<NoteBorder> borderList = new ArrayList<NoteBorder>();
	
	/**
	 * 게임을 다시 시작하는 버튼
	 */
	private JButton restartBtn = new JButton("RESTART");
	/**
	 * 게임의 메뉴로 가는 버튼
	 */
	private JButton goMenuBtn = new JButton("MENU");
	
	/**
	 * 음악을 재생하기 위한 객체 생성
	 */
	private Music music = new Music();
	
	/**
	 * 판정 원 이미지 추가 메서드
	 */
	private void initCircles() {
		ImageIcon[] circleImageIcons = {
				new ImageIcon("E:/_00_java_project/beatgame/src/images/bluecircle.png"),
				new ImageIcon("E:/_00_java_project/beatgame/src/images/greencircle.png"),
				new ImageIcon("E:/_00_java_project/beatgame/src/images/redcircle.png"),
				new ImageIcon("E:/_00_java_project/beatgame/src/images/yellowcircle.png")
		};
		Rectangle[] rects = {
				new Rectangle(Main.BLUE_X, Main.BLUE_Y, 70, 70),
				new Rectangle(Main.GREEN_X, Main.GREEN_Y, 70, 70),
				new Rectangle(Main.RED_X, Main.RED_Y, 70, 70),
				new Rectangle(Main.YELLOW_X, Main.YELLOW_Y, 70, 70),
		};
		for(int i = 0; i < circleImageIcons.length; ++i) {
			Note note = new Note(circleImageIcons[i]);
			note.setBounds(rects[i]);
			note.setBorderPainted(false);
			note.setContentAreaFilled(false);
			note.setFocusPainted(false);
			note.setVisible(true);
			noteList.add(note);
		}
	}
	
	/**
	 * keyPressed 판정 원 이미지 추가 메서드
	 */
	private void pressedCircles() { 
		ImageIcon[] pressedcircleImageIcons = {
				new ImageIcon(changepressedblueImage),
				new ImageIcon(changepressedgreenImage),
				new ImageIcon(changepressedredImage),
				new ImageIcon(changepressedyellowImage)
		};
		Rectangle[] rects = {
				new Rectangle(Main.BLUE_X - 15, Main.BLUE_Y - 15, 100, 100),
				new Rectangle(Main.GREEN_X - 15, Main.GREEN_Y - 15, 100, 100),
				new Rectangle(Main.RED_X - 15, Main.RED_Y - 15, 100, 100),
				new Rectangle(Main.YELLOW_X - 15, Main.YELLOW_Y - 15, 100, 100),
		};
		for(int i = 0; i < pressedcircleImageIcons.length; ++i) {
			final Note note = new Note(pressedcircleImageIcons[i]);
			note.setBounds(rects[i]);
			note.setBorderPainted(false);
			note.setContentAreaFilled(false);
			note.setFocusPainted(false);
			note.setVisible(false);
			pressednoteList.add(note);
		}
	}

	/**
	 * 판정 원 보이기 혹은 가리기 메서드. 일시정지 화면 전환 시 필요.
	 * @param visible 보이면 true, 안보이면 false
	 */
	private void showCircles(boolean visible) {
		noteList.stream().forEach(n -> n.setVisible(visible));
	}
	
	/**
	 * 노트 생성 시간 및 노트 종류 배열
	 */
	private Beat[] beats = new Beat[] { 
			new Beat(Main.START_TIME + 940, "A"),
			new Beat(Main.START_TIME + 1880, "S"),
			new Beat(Main.START_TIME + 2820, "K"),
			new Beat(Main.START_TIME + 3760, "L"),
			
			new Beat(Main.START_TIME + 4700, "L"),
			new Beat(Main.START_TIME + 5640, "K"),
			new Beat(Main.START_TIME + 6580, "S"),
			new Beat(Main.START_TIME + 7520, "A"),
			
			new Beat(Main.START_TIME + 8460, "A"),
			new Beat(Main.START_TIME + 9400, "L"),
			new Beat(Main.START_TIME + 10340, "S"),
			new Beat(Main.START_TIME + 11280, "K"),
			
			new Beat(Main.START_TIME + 12220, "K"),
			new Beat(Main.START_TIME + 13160, "S"),
			new Beat(Main.START_TIME + 14100, "K"),
			// <<
			new Beat(Main.START_TIME + 14700, "A"),
			new Beat(Main.START_TIME + 14700, "L"),
			new Beat(Main.START_TIME + 15500, "S"),
			new Beat(Main.START_TIME + 16700, "S"),
			new Beat(Main.START_TIME + 16700, "K"),
			new Beat(Main.START_TIME + 17600, "L"),
			
			new Beat(Main.START_TIME + 18700, "S"),
			new Beat(Main.START_TIME + 19500, "K"),
			new Beat(Main.START_TIME + 20500, "A"),
			new Beat(Main.START_TIME + 21200, "L"),
			new Beat(Main.START_TIME + 21200, "A"),
			
			new Beat(Main.START_TIME + 22400, "L"),
			new Beat(Main.START_TIME + 23200, "K"),
			new Beat(Main.START_TIME + 24300, "S"),
			new Beat(Main.START_TIME + 25200, "A"),
			
			new Beat(Main.START_TIME + 26300, "S"),
			new Beat(Main.START_TIME + 27000, "S"),
			new Beat(Main.START_TIME + 28200, "K"),
			new Beat(Main.START_TIME + 29100, "K"),
			};
	
	/**
	 * 노트 생성 시 딜레이 넣는 일회성 타이머
	 */
	private Timer timer;
	/**
	 * 노트 사이즈 줄이는 반복 타이머
	 */
	private Timer t;
	/**
	 * 줄어드는 지름
	 */
	private int nowRadius;
	/**
	 * 지금의 노트 종류
	 */
	private String nowNoteType;
	/**
	 * 노트 정지를 위한 논리 변수
	 */
	private boolean isRunning = true;
	
	/**
	 * 타이머 스레드 종료 메서드
	 */
	public synchronized void stopTimer() {
		timer.stop();
		timer = null;
	}
	
	/**
	 * 노트 그리기 메서드
	 */
	public synchronized void noteCircles() {
		for(int i = 0; i < beats.length; ++i) {
			final int ii = i;
			final NoteBorder border = new NoteBorder(beats[ii].getTime(),beats[ii].getNoteType());
			border.setVisible(false);
			borderList.add(border);
		
			timer = new Timer(beats[ii].getTime(), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!isRunning) {
						((Timer)e.getSource()).stop();
					} else {
						border.setVisible(true);
						t = new Timer(Main.NOTE_SPEED2, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e1) {
								if(border.getRadius() < 0) {
									((Timer)e1.getSource()).stop();
									return;
								}
								border.resizeCircle();
								nowRadius = border.getRadius();
								nowNoteType = beats[ii].getNoteType();
							}
						});
						t.start();
					}
				}
			} );
			timer.setRepeats(false);
			timer.start();
		}
	}
	
	/**
	 * 일시정지 버튼 리스너<br>
	 * 게임 화면에서 restart와 menu버튼만 있는 화면으로 전환한다.<br>
	 * 음악이 정지되고 노트도 정지된다.
	 */
	private ActionListener pauseListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// 화면 전환
			pauseBtn.setVisible(false);
			lifeLb.setVisible(false);
			perfectLb.setVisible(false);
			comboLb.setVisible(false);
			scoreLb.setVisible(false);
			showCircles(false);
			restartBtn.setVisible(true);
			goMenuBtn.setVisible(true);
			
			music.stopMusic(); // 음악 정지
			isRunning = false; // 노트 정지
		}
	};
	
	/**
	 * 게임클래스 객체
	 */
	private Game game;
	/**
	 * restart, menu 마우스 리스너
	 */
	private ActionListener selectListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton jb = (JButton) e.getSource();
			if(jb.getText() == "RESTART") {
				frame.dispose();
				if(game != null)
					game.stopTimer();
				game = new Game();
			} else if(jb.getText() == "MENU") {
				frame.dispose();
				new Menu();
			}
		}
	};
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	/**
	 * 점수
	 */
	public static int score;
	/**
	 * perfect, great, good, bad, miss
	 */
	public static String perfect;
	/**
	 * 콤보 수
	 */
	public static int combo;
	/**
	 * 남은 목숨 수
	 */
	public static int life;
	/**
	 * 키보드 동시 입력을 위한 set
	 */
	private final HashSet<Integer> pressedKeys = new HashSet<>();
	
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { // ESC 누르면 창 닫기
			music.stopMusic();
			frame.dispose();
		}
		
		pressedKeys.add(e.getKeyCode()); // 키 입력값 저장
		
		if(!pressedKeys.isEmpty()) {
			for(Iterator<Integer> it = pressedKeys.iterator(); it.hasNext();) {
				switch (it.next()) {
				case KeyEvent.VK_A:
					pressednoteList.get(0).setVisible(true);
					if(nowNoteType == "A" && nowRadius >= 55 && nowRadius <= 180) {
						perfect = "PERFECT";
						score += Main.PERFECT_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "A" && nowRadius >= 30 && nowRadius <= 205) {
						perfect = "GREAT";
						score += Main.GREAT_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "A" && nowRadius != 5 && nowRadius <= 255) {
						perfect = "GOOD";
						score += Main.GOOD_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "A" && nowRadius != 5 && nowRadius <= 380) {
						perfect = "BAD";
						score += Main.BAD_SCORE;
						combo = 0;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType != "A" || nowNoteType == "A" && nowRadius == 5 && nowRadius >= 380){
						frame.setBackground(new Color(143, 145, 148));
						perfect = "miss";
						combo = 0;
						life -= 1;
						if(life < 0) {
							music.stopMusic();
							frame.dispose();
							new GameOver();
						}
						lifeLb.setText("LIFE : " + Integer.toString(life));
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} break;
				case KeyEvent.VK_S:
					pressednoteList.get(1).setVisible(true);
					if(nowNoteType == "S" && nowRadius >= 55 && nowRadius <= 180) {
						perfect = "PERFECT";
						score += Main.PERFECT_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "S" && nowRadius >= 30 && nowRadius <= 205) {
						perfect = "GREAT";
						score += Main.GREAT_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "S" && nowRadius != 5 && nowRadius <= 255) {
						perfect = "GOOD";
						score += Main.GOOD_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "S" && nowRadius != 5 && nowRadius <= 380) {
						perfect = "BAD";
						score += Main.BAD_SCORE;
						combo = 0;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType != "S" || nowNoteType == "S" && nowRadius == 5 && nowRadius >= 380){
						frame.setBackground(new Color(143, 145, 148));
						perfect = "miss";
						combo = 0;
						life -= 1;
						if(life < 0) {
							music.stopMusic();
							frame.dispose();
							new GameOver();
						}
						lifeLb.setText("LIFE : " + Integer.toString(life));
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} break;
				case KeyEvent.VK_K:
					pressednoteList.get(2).setVisible(true);
					if(nowNoteType == "K" && nowRadius >= 55 && nowRadius <= 180) {
						perfect = "PERFECT";
						score += Main.PERFECT_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "K" && nowRadius >= 30 && nowRadius <= 205) {
						perfect = "GREAT";
						score += Main.GREAT_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "K" && nowRadius != 5 && nowRadius <= 255) {
						perfect = "GOOD";
						score += Main.GOOD_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "K" && nowRadius != 5 && nowRadius <= 305) {
						perfect = "BAD";
						score += Main.BAD_SCORE;
						combo = 0;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType != "K" || nowNoteType == "K" && nowRadius == 5 && nowRadius >= 380){
						frame.setBackground(new Color(143, 145, 148));
						perfect = "miss";
						combo = 0;
						life -= 1;
						if(life < 0) {
							music.stopMusic();
							frame.dispose();
							new GameOver();
						}
						lifeLb.setText("LIFE : " + Integer.toString(life));
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} break;
				case KeyEvent.VK_L:
					pressednoteList.get(3).setVisible(true);
					if(nowNoteType == "L" && nowRadius >= 55 && nowRadius <= 180) {
						perfect = "PERFECT";
						score += Main.PERFECT_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "L" && nowRadius >= 30 && nowRadius <= 205) {
						perfect = "GREAT";
						score += Main.GREAT_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "L" && nowRadius != 5 && nowRadius <= 255) {
						perfect = "GOOD";
						score += Main.GOOD_SCORE;
						combo += 1;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType == "L" && nowRadius != 5 && nowRadius <= 305) {
						perfect = "BAD";
						score += Main.BAD_SCORE;
						combo = 0;
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} else if(nowNoteType != "L" || nowNoteType == "L" && nowRadius == 5 && nowRadius >= 380){
						frame.setBackground(new Color(143, 145, 148));
						perfect = "miss";
						combo = 0;
						life -= 1;
						if(life < 0) {
							music.stopMusic();
							frame.dispose();
							new GameOver();
						}
						lifeLb.setText("LIFE : " + Integer.toString(life));
						perfectLb.setText(perfect);
						comboLb.setText(Integer.toString(combo));
						scoreLb.setText(Integer.toString(score));
					} break;
				}
			}
		}
	}
	
	@Override
	public synchronized void keyReleased(KeyEvent e) {
		
		if(e.getSource() != null) {
			frame.setBackground(Color.BLACK);
		}
		
		pressedKeys.remove(e.getKeyCode()); // 키 입력값 삭제
		
		if(e.getKeyCode() == KeyEvent.VK_A) {
			pressednoteList.get(0).setVisible(false);
		} else if(e.getKeyCode() == KeyEvent.VK_S) {
			noteList.get(1).setVisible(true);
			pressednoteList.get(1).setVisible(false);
		} else if(e.getKeyCode() == KeyEvent.VK_K) {
			noteList.get(2).setVisible(true);
			pressednoteList.get(2).setVisible(false);
		} else if(e.getKeyCode() == KeyEvent.VK_L) {
			noteList.get(3).setVisible(true);
			pressednoteList.get(3).setVisible(false);
		}
	}
	
	/**
	 * 게임 화면 생성자. 게임 화면을 초기화한다.
	 */
	public Game() {
		
		// 게임 재시작 시 초기화
		score = 0;
		perfect = "";
		combo = 0;
		life = 7;
		
		music.playMusic("RetroVision_Campfire-cut2.wav");
		JLayeredPane pane = new JLayeredPane();
		frame.setBackground(Color.BLACK);
		pane.setLayout(null);
		pane.addKeyListener(this);
		pane.setFocusable(true); // 리스너 포커스
		
		initCircles();
		noteCircles();
		pressedCircles();
		noteList.stream().forEach(e ->pane.add(e, Integer.valueOf(1))); // 가장 앞에 보여야 함
		borderList.stream().forEach(e ->pane.add(e));
		pressednoteList.stream().forEach(e ->pane.add(e));
		
		pauseBtn.setBounds(1200, 20, 50, 50);
		pauseBtn.setBackground(Color.BLACK);
		pauseBtn.setBorderPainted(false);
		pauseBtn.setContentAreaFilled(false);
		pauseBtn.setFocusPainted(false);
		pauseBtn.addActionListener(pauseListener);
		pauseBtn.setVisible(true);
		pane.add(pauseBtn);
		
		lifeLb.setHorizontalAlignment(JLabel.LEFT);
		lifeLb.setBounds(30, 20, 200, 50);
		lifeLb.setText("LIFE : 7");
		lifeLb.setForeground(Color.WHITE);
		lifeLb.setFont(new Font("Gill sans", Font.BOLD, 30));
		lifeLb.setVisible(true);
		pane.add(lifeLb);

		perfectLb.setHorizontalAlignment(JLabel.CENTER);
		perfectLb.setBounds(545, 30, 180, 70);
		perfectLb.setForeground(Color.WHITE);
		perfectLb.setFont(new Font("Gill sans", Font.BOLD, 30));
		perfectLb.setVisible(true);
		pane.add(perfectLb);
		
		comboLb.setHorizontalAlignment(JLabel.CENTER);
		comboLb.setBounds(525, 110, 220, 100);
		comboLb.setText("0");
		comboLb.setForeground(Color.WHITE);
		comboLb.setFont(new Font("Gill sans", Font.BOLD, 100));
		comboLb.setVisible(true);
		pane.add(comboLb);
		
		scoreLb.setHorizontalAlignment(JLabel.RIGHT);
		scoreLb.setBounds(660, 25, 500, 50);
		scoreLb.setText("0");
		scoreLb.setForeground(Color.WHITE);
		scoreLb.setFont(new Font("Gill sans", Font.BOLD, 30));
		scoreLb.setVisible(true);
		pane.add(scoreLb);
		
		restartBtn.setBounds(565, 250, 150, 70);
		restartBtn.setFont(new Font("Gill sans", Font.BOLD, 20));
		restartBtn.setForeground(Color.WHITE);
		restartBtn.setBorderPainted(false);
		restartBtn.setContentAreaFilled(false);
		restartBtn.setFocusPainted(false);
		restartBtn.addActionListener(selectListener);
		restartBtn.setVisible(false);
		pane.add(restartBtn);
		
		goMenuBtn.setBounds(565, 340, 150, 70);
		goMenuBtn.setFont(new Font("Gill sans", Font.BOLD, 20));
		goMenuBtn.setForeground(Color.WHITE);
		goMenuBtn.setBorderPainted(false);
		goMenuBtn.setContentAreaFilled(false);
		goMenuBtn.setFocusPainted(false);
		goMenuBtn.addActionListener(selectListener);
		goMenuBtn.setVisible(false);
		pane.add(goMenuBtn);

		frame.setLayeredPane(pane);
		
		frame.setTitle(Main.GAMETITLE);
		frame.setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
