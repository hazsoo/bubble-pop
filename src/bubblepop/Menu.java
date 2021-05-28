package bubblepop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 게임 메뉴 클래스.<br/>
 * 주요 기능
 * <ol>
 *  <li>곡 선택 화면 초기화</li>
 *  <li>화살표, 시작 이벤트 키 리스닝</li>
 * </ol>
 * @author Jisoo Ha
 *
 */
public class Menu extends JFrame implements KeyListener {

	/**
	 * 메뉴 창 메인 패널
	 */
	private JPanel bigPanel = new JPanel();

	/**
	 * 게임로고 아이콘
	 */
	private ImageIcon gameLogo = new ImageIcon("E:/_00_java_project/beatgame/src/images/gamelogo.png");
	/**
	 * 게임로고 아이콘에서 불러온 이미지
	 */
	private Image logoimg = gameLogo.getImage();
	/**
	 * 바뀐 스케일의 게임로고 이미지
	 */
	private Image changelogoImage = logoimg.getScaledInstance(1000, 500, Image.SCALE_SMOOTH);
	/**
	 * 바뀐 스케일의 게임로고 아이콘
	 */
	private ImageIcon changelogoIcon = new ImageIcon(changelogoImage);
	/**
	 * 게임로고 라벨
	 */
	private JLabel gameLogoLb = new JLabel(changelogoIcon);
	
	/**
	 * Select Music 텍스트 라벨
	 */
	private JLabel selectLb = new JLabel("Select Music");
	/**
	 * 게임 제목 표시 라벨
	 */
	private JLabel musicTitleLb = new JLabel("RetroVision - Campfire");
	/**
	 * 게임 시작 버튼
	 */
	private JButton startBtn = new JButton("START");

	/**
	 * 왼쪽 화살표 아이콘
	 */
	private ImageIcon leftImage = 
			new ImageIcon("E:/_00_java_project/beatgame/src/images/leftimage.png");
	/**
	 * 왼쪽 화살표 아이콘에서 불러온 이미지
	 */
	private Image img = leftImage.getImage();
	/**
	 * 바뀐 스케일의 왼쪽 화살표 이미지
	 */
	private Image changeleftImage = img.getScaledInstance(100, 75, Image.SCALE_SMOOTH);
	/**
	 * 바뀐 스케일의 왼쪽 화살표 아이콘
	 */
	private ImageIcon changeleftIcon = new ImageIcon(changeleftImage);
	/**
	 * 왼쪽 화살표 버튼
	 */
	private JButton leftBtn = new JButton(changeleftIcon);

	/**
	 * 오른쪽 화살표 아이콘
	 */
	private ImageIcon rightImage = 
			new ImageIcon("E:/_00_java_project/beatgame/src/images/rightimage.png");
	/**
	 * 오른쪽 화살표 아이콘에서 불러온 이미지
	 */
	private Image img2 = rightImage.getImage();
	/**
	 * 바뀐 스케일의 오른쪽 화살표 이미지
	 */
	private Image changerightImage = img2.getScaledInstance(100, 75, Image.SCALE_SMOOTH);
	/**
	 * 바뀐 스케일의 오른쪽 화살표 아이콘
	 */
	private ImageIcon changerightIcon = new ImageIcon(changerightImage);
	/**
	 * 오른쪽 화살표 버튼
	 */
	private JButton rightBtn = new JButton(changerightIcon);
	
	/**
	 * 에프터 이펙트 이미지 아이콘
	 */
	private ImageIcon songhzImage = 
			new ImageIcon("E:/_00_java_project/beatgame/src/images/song1hz640.gif");
	/**
	 * 에프터 이펙트 이미지 라벨
	 */
	private JLabel hzLabel = new JLabel(songhzImage);
	
	/**
	 * 음악을 재생하기 위한 객체 생성
	 */
	private Music music = new Music();
	/**
	 * 게임 객체 생성
	 */
	public Game game;
	
	
	/**
	 * 왼쪽 화살표 버튼 리스너
	 */
	private ActionListener leftListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			music.playMusic("RetroVision_Campfire_trimed.wav");
			gameLogoLb.setVisible(false);
			selectLb.setVisible(false);
			hzLabel.setVisible(true);
			musicTitleLb.setVisible(true);
		}
	};
	
	/**
	 * 오른쪽 화살표 버튼 리스너
	 */
	private ActionListener rightListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			music.playMusic("RetroVision_Campfire_trimed.wav");
			gameLogoLb.setVisible(false);
			selectLb.setVisible(false);
			hzLabel.setVisible(true);
			musicTitleLb.setVisible(true);
		}
	};
	
	/**
	 * START 버튼 리스너
	 */
	private ActionListener startListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			music.stopMusic();
			dispose(); // menu화면 끄기
			if(game != null)
				game.stopTimer();
			game = new Game(); // game화면 보이기
		}
	};
	
	
	/**
	 * 메뉴 창 생성자
	 */
	public Menu() {
		
		gameLogoLb.setHorizontalAlignment(JLabel.CENTER);
		gameLogoLb.setBounds(140, 100, 1000, 200);
		gameLogoLb.setForeground(new Color(13, 214, 130));
		gameLogoLb.setFont(new Font("Gill sans", Font.BOLD, 130));
		gameLogoLb.setVisible(true);
		add(gameLogoLb);
		
		selectLb.setHorizontalAlignment(JLabel.CENTER);
		selectLb.setBounds(390, 400, 500, 50);
		selectLb.setForeground(new Color(122, 122, 122));
		selectLb.setFont(new Font("Gill sans", Font.BOLD, 40));
		selectLb.setVisible(true);
		add(selectLb);
		
		musicTitleLb.setHorizontalAlignment(JLabel.CENTER);
		musicTitleLb.setBounds(390, 450, 500, 30);
		musicTitleLb.setForeground(new Color(245, 103, 233));
		musicTitleLb.setFont(new Font("Gill sans", Font.BOLD, 30));
		musicTitleLb.setVisible(false);
		add(musicTitleLb);
		
		hzLabel.setBounds(320, 90, 640, 360);
		hzLabel.setVisible(false);
		add(hzLabel);
		
		startBtn.setFont(new Font("Gill sans", Font.BOLD, 50));
		startBtn.setBounds(530, 500, 210, 70);
		startBtn.setBackground(Color.BLACK);
		startBtn.setForeground(Color.WHITE);
		startBtn.setBorderPainted(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setFocusPainted(false);
		startBtn.addActionListener(startListener);
		add(startBtn);

		leftBtn.setBounds(350, 500, 100, 75);
		leftBtn.setBackground(Color.BLACK);
		leftBtn.setBorderPainted(false);
		leftBtn.setContentAreaFilled(false);
		leftBtn.setFocusPainted(false);
		leftBtn.addActionListener(leftListener);
		add(leftBtn);

		rightBtn.setBounds(830, 500, 100, 75);
		rightBtn.setBackground(Color.BLACK);
		rightBtn.setBorderPainted(false);
		rightBtn.setContentAreaFilled(false);
		rightBtn.setFocusPainted(false);
		rightBtn.addActionListener(rightListener);
		add(rightBtn);
		
		bigPanel.setBackground(Color.BLACK);
		add(bigPanel);
		
		addKeyListener(this);
		setFocusable(true);
		
		setTitle(Main.GAMETITLE);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}



	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) { // 마우스 클릭 외 키보드리스너도 넣음
		if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
			music.stopMusic();
			dispose(); // 현재 화면 끄기
			game = new Game(); // game 화면 보이기
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			music.playMusic("RetroVision_Campfire_trimed.wav");
			gameLogoLb.setVisible(false);
			selectLb.setVisible(false);
			hzLabel.setVisible(true);
			musicTitleLb.setVisible(true);
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			music.playMusic("RetroVision_Campfire_trimed.wav");
			gameLogoLb.setVisible(false);
			selectLb.setVisible(false);
			hzLabel.setVisible(true);
			musicTitleLb.setVisible(true);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
}
