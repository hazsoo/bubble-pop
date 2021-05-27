package bubblepop;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 게임오버 클래스.<br/>
 * 주요 기능
 * <ol>
 *  <li>게임 오버 화면 초기화</li>
 *  <li>점수 구현</li>
 *  <li>데이터베이스 연결</li>
 *  <li>게임 종료</li>
 *  <li>데이터 저장 이벤트 키, 마우스 리스닝</li>
 * </ol>
 * @author Jisoo Ha
 *
 */
public class GameOver extends JFrame {
	
	private JFrame frame = new JFrame();
	private JButton nullBtn = new JButton(); // 가짜 버튼
	
	private JLabel gameOverLb = new JLabel("GAME OVER"); // 게임오버글자
	private JLabel gradeLb = new JLabel(); // 등급 SS, S, A, B, C, D, F
	private JLabel finalScoreLb = new JLabel(); // 최종 점수
	private JButton newGameBtn = new JButton("NEW GAME"); // 게임 다시 버튼
	private JButton endGameBtn = new JButton("END GAME"); // 게임 끄기 버튼
	
	private JLabel maxScoreLb = new JLabel(); // 최고 점수 목록 표시
	private JLabel newRecordLb = new JLabel("NEW RECORD!"); // 최고 점수 달성 안내
	private JTextField setID = new JTextField(); // 최고점수 달성시 이름 입력
	
	private final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306/testDB?"
								+ "useUnicode=true"
								+ "&characterEncoding=utf8";
	private final String USERNAME = "root";
	private final String PASSWORD = "1234";
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public String grade = "F";
	public void setGrade() { // score에 따른 grade 설정
		if(Game.score > 100000) {
			grade = "SS";
		} else if(Game.score > 60000) {
			grade = "S";
		} else if(Game.score > 50000) {
			grade = "A";
		} else if(Game.score > 20000) {
			grade = "B";
		} else if(Game.score > 4000) {
			grade = "C";
		} else if(Game.score > 1) {
			grade = "D";
		}
	}
	
	// 게임 재시작, 종료 버튼 리스너
	private ActionListener neworendGameListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton jb = (JButton) e.getSource();
			if(jb.getText() == "NEW GAME") {
				frame.dispose();
				new Menu();
			} else if(jb.getText() == "END GAME") {
				System.exit(0); // 프로그램 종료
			}
		}
	};
	
	// 마우스 리스너
	private MouseListener removeTextListener = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			setID.setText(""); // 이름 입력창 클릭시 텍스트 삭제
		}
	};
	
	private KeyListener inputNameListener = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				try {
					conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
					if(setID.getText().length() == 3) { // 입력 문자길이가 3일때
						setMaxScore(); // 최고 점수 저장
						maxScoreLb.setText(getMaxScore()); // 최고 점수 목록 불러오기
						newRecordLb.setVisible(false);
						setID.setVisible(false);
						maxScoreLb.setVisible(true);
					} else {
						setID.setText(""); // 다시 입력
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					try {
					if (rs != null) {
						rs.close();
					}
					if (ps != null) {
						ps.close();
					}
					if (conn != null) {
						conn.close();
					}
					} catch (Exception e2) {
					e2.printStackTrace();
					}
				}
				
			}
		}
	};
	
	
	public GameOver() {
		
		createTable();
		setGrade();
		
		gameOverLb.setHorizontalAlignment(JLabel.CENTER);
		gameOverLb.setBounds(140, 20, 1000, 200);
		gameOverLb.setForeground(new Color(250, 80, 80));
		gameOverLb.setFont(new Font("Gill sans", Font.BOLD, 130));
		gameOverLb.setVisible(true);
		frame.add(gameOverLb);
		
		gradeLb.setHorizontalAlignment(JLabel.CENTER);
		gradeLb.setBounds(320, 220, 220, 150);
		gradeLb.setText(grade);
		gradeLb.setForeground(new Color(235, 255, 77));
		gradeLb.setFont(new Font("Gill sans", Font.BOLD, 150));
		gradeLb.setVisible(true);
		frame.add(gradeLb);
		
		finalScoreLb.setHorizontalAlignment(JLabel.CENTER);
		finalScoreLb.setBounds(310, 370, 220, 30);
		finalScoreLb.setText(Game.score + " 점");
		finalScoreLb.setForeground(Color.WHITE);
		finalScoreLb.setFont(new Font("Gill sans", Font.BOLD, 30));
		finalScoreLb.setVisible(true);
		frame.add(finalScoreLb);
		
		
		newGameBtn.setBounds(770, 240, 200, 70);
		newGameBtn.setFont(new Font("Gill sans", Font.BOLD, 25));
		newGameBtn.setForeground(Color.WHITE);
		newGameBtn.setBorderPainted(false);
		newGameBtn.setContentAreaFilled(false);
		newGameBtn.setFocusPainted(false);
		newGameBtn.addActionListener(neworendGameListener);
		newGameBtn.setVisible(true);
		frame.add(newGameBtn);
		
		endGameBtn.setBounds(770, 320, 200, 70);
		endGameBtn.setFont(new Font("Gill sans", Font.BOLD, 25));
		endGameBtn.setForeground(Color.WHITE);
		endGameBtn.setBorderPainted(false);
		endGameBtn.setContentAreaFilled(false);
		endGameBtn.setFocusPainted(false);
		endGameBtn.addActionListener(neworendGameListener);
		endGameBtn.setVisible(true);
		frame.add(endGameBtn);
		
		
		maxScoreLb.setVerticalAlignment(JLabel.TOP);;
		maxScoreLb.setHorizontalAlignment(JLabel.CENTER);
		maxScoreLb.setBounds(390, 420, 500, 220);
		maxScoreLb.setForeground(Color.WHITE);
		maxScoreLb.setFont(new Font("Gill sans", Font.BOLD, 15));
		maxScoreLb.setVisible(false);
		frame.add(maxScoreLb);
		
		newRecordLb.setHorizontalAlignment(JLabel.CENTER);
		newRecordLb.setBounds(390, 300, 500, 20);
		newRecordLb.setForeground(Color.WHITE);
		newRecordLb.setFont(new Font("Gill sans", Font.BOLD, 20));
		newRecordLb.setVisible(true);
		frame.add(newRecordLb);
		
		setID.setHorizontalAlignment(JLabel.CENTER);
		setID.setBounds(615, 350, 50, 20);
		setID.setText("AAA");
		setID.addMouseListener(removeTextListener);
		setID.addKeyListener(inputNameListener);
		setID.setForeground(Color.GRAY);
		setID.setFont(new Font("Gill sans", Font.BOLD, 15));
		setID.requestFocus();
		setID.setVisible(true);
		frame.add(setID);
		
		
		nullBtn.setBounds(0, 0, 0, 0);
		nullBtn.setBorderPainted(false);
		nullBtn.setContentAreaFilled(false);
		nullBtn.setFocusPainted(false);
		nullBtn.setVisible(true);
		frame.add(nullBtn);
		
		
		frame.setTitle(Main.GAMETITLE);
		frame.setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setVisible(true);
		
		
	}		
	
	/**
	 * 관계형 데이터베이스 연결
	 */
	private void createTable() { // 점수 저장할 테이블 생성
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("CREATE TABLE if not exists scorelist(")
					.append("id INT AUTO_INCREMENT PRIMARY KEY, ")
					.append("regdate DATETIME DEFAULT CURRENT_TIMESTAMP, ")
					.append("name varchar(3) check(0 < length(name) and length(name) <=3), ") 
					.append("score INT NOT NULL")
					.append(")").toString();
			
			ps = conn.prepareStatement(sql);
			ps.execute();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setMaxScore() throws SQLException { // 최고 점수 저장
		String sql = "INSERT INTO scorelist(name, score) VALUES(?, ?)";
		
		String name = setID.getText();
		int score = Game.score;
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, name);
		ps.setInt(2, score);
		ps.execute();
	}
	
	private String getMaxScore() throws SQLException { // 최고 점수 추출
		String sql = "SELECT regdate, name, score FROM scorelist ORDER BY score DESC";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		String message = "";
		while(rs.next()) {
			message += rs.getString(1) 
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
					+ rs.getString(2) 
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
					+ rs.getInt(3) + "<br>"; 
		}
		return "<html><body>" + message + "<body><html>";
	}
	
}


