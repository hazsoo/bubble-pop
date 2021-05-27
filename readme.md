# Bubble Pop

흘러나오는 음악에 맞춰 버튼을 누르는 흔한 리듬게임이다.

하지만 조금 다른 방식의 스타일로 진행되는 게임에 새로움을 느낄 수 있다.

최고 점수를 기록하여 사용자의 승부욕을 고취시킬 수 있다.


<img width="30%" src="https://user-images.githubusercontent.com/81146582/119824191-99868f00-bf30-11eb-8d12-7200675a064e.JPG"/> <img width="30%" src="https://user-images.githubusercontent.com/81146582/119824346-c3d84c80-bf30-11eb-9c73-884b7658cd4f.JPG"/> <img width="30%" src="https://user-images.githubusercontent.com/81146582/119824439-dfdbee00-bf30-11eb-9932-ca0512f551a3.JPG"/>





## 개발환경

> JDK 11.0.9
>
> IDE : Eclipse
>
> DB : MariaDB 10.5.6



## 주요기능

- 키보드로 조작
- 남은 기회 소멸 시 게임 오버
- 일시정지 후 재시작 및 초기화면 이동
- 점수대별 최종 성적 표시 (SS, S, A, B, C, D, F)
- 점수 DB에 입력
- DB에 있는 최고 점수 추출



## 프로그램의 특징

- 여타 리듬게임과는 다른 방식으로 만들고자 하여 Note가 위에서 아래로 내려와 특정 위치에서 판정하는 방식이 아닌, 원 형태의 Note가 설정한 속도로 줄어들어 특정 지름에서 판정하는 방식으로 만들었다.

- 원 형태의 Note를 어떻게 그릴 지 많이 고심했다. Graphics 클래스와 Timer를 사용해 줄어드는 원을 구현하였다.

  ```java
  public void paint(Graphics g) {
  	radius -= Main.NOTE_SPEED1; // 지름 계속 줄이기
  	g.setColor(color);
  	g.fillOval(centerX - radius / 2, centerY - radius / 2, radius, radius);
  }
  ```

  ```java
  private int nowRadius; // 줄어드는 지름
  private String nowNoteType; // 지금의 노트
  private boolean isRunning = true; // 노트 정지를 위한 논리값
  	
  public synchronized void noteCircles() { // 노트 그리기
  	for(int i = 0; i < beats.length; ++i) {
  		final int ii = i;
  		final NoteBorder border = new NoteBorder(beats[ii].getTime(),beats[ii].getNoteType());
  		NoteBorder.setNew(true);
  		border.setVisible(false);
  		borderList.add(border);
  		
  		// 노트 생성 시 딜레이 넣는 일회성 타이머
  		Timer timer = new Timer(beats[ii].getTime(), new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				if(!isRunning) { // false 시 노트 정지
  					((Timer)e.getSource()).stop();
  				} else {
  					border.setVisible(true);
  					
  					// 노트 사이즈 줄이는 반복 타이머
  					Timer t = new Timer(Main.NOTE_SPEED2, new ActionListener() { 
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
  					t.start(); // 반복
  				} // if else
  			}
  		} );
  		timer.setRepeats(false); // 한번만 동작
  		timer.start();
  	}
  }
  ```




- KeyListener에 HashSet을 사용하여 키보드 동시 입력이 가능하게 만들었다. 리듬게임에는 분명 두 개 혹은 두 개 이상의 노트를 동시에 누르는 경우도 있다. 하지만 일반적인 KeyListener는 이러한 동시 입력을 제대로 받지 못해서 Set의 HashSet에  KeyPressed 때 추가, KeyReleased 때 삭제 하며 입력한 키가 Set에 있는 경우 계속 실행되도록 하여 노트의 흐름과 난이도를 다양하게 바꿀 수 있다.

  ```java
  private final HashSet<Integer> pressedKeys = new HashSet<>();
  public synchronized void keyPressed(KeyEvent e) {
  	pressedKeys.add(e.getKeyCode()); // 키 입력값 저장
  	if(!pressedKeys.isEmpty()) {
  		for(Iterator<Integer> it = pressedKeys.iterator(); it.hasNext();) {
  			switch (it.next()) {
  			case KeyEvent.VK_A:
                      break;
              case KeyEvent.VK_S:
                      break;
  			case KeyEvent.VK_K:
                      break;
  			case KeyEvent.VK_L:
  					break;
  		}
  	}
  }
      
  public synchronized void keyReleased(KeyEvent e) {
      pressedKeys.remove(e.getKeyCode()); // 키 입력값 삭제
  }
  ```

  

- 관계형 데이터베이스를 활용해 순위를 조회하고 기록을 갱신한다.

  ```java
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
  ```

  

  ```java
  private String getMaxScore() throws SQLException { // 순위 조회
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
  ```

  ```java
  private void setMaxScore() throws SQLException { // 기록 갱신
  	String sql = "INSERT INTO scorelist(name, score) VALUES(?, ?)";
  	
  	String name = setID.getText();
  	int score = Game.score;
  	
  	ps = conn.prepareStatement(sql);
  	ps.setString(1, name);
  	ps.setInt(2, score);
  	ps.execute();
  }
  ```



## 힘들었던 점

#### 1. Graphics로 원을 구현하는 문제

BufferedImage를 이용해 원 모양의 이미지를 줄이는 방법도 있었다. 하지만 PhotoShop 프로그램이 없어서 반투명한 원의 이미지를 그리는 데에 어려움도 있었을 뿐더러, 좀 더 자연스러운 구현을 위해 Graphics를 쓰고 싶었다. Graphics를 처음 사용하기 때문에 우선은 paint()에 대한 이해가 필요했으며 Frame 혹은 Panel에 그려내는 데에 충돌이 생겨 보이지 않는 문제가 있었다. 그래서 여러 swing 객체들을 한 화면에서 원근감있게 표현할 수 있는 JLayerPane이라는 클래스를 이용하여 해결할 수 있었다.

그리고 fillOval()라는 메서드는 원을 그릴 때 센터좌표를 중심으로 그리지 않고 사각형을 그리듯이 왼쪽 위의 좌표에서 원의 너비 즉 지름만큼의 사각형에 맞는 원을 그린다. 줄어드는 Note는 항상 그 중심이 같아야하므로 이를 해결하기 위해 변하는 지름에 맞게 x, y 좌표도 실시간으로 변하도록 만들어 주었다.



#### 2. 원이 생성되는 시점과 줄어드는 속도를 설정하는 문제

paint()는 해당 클래스가 시작될 때 자동으로 제일 먼저 실행된다. 그래서 실행이 되기 전 delay를 주기 위해 swing.Timer 를 한번만 실행되도록 했고, 그 내부에 paint()와 repaint()를 지름이 마이너스가 될 때까지 반복하는 Timer 클래스 한번 더 사용했다.



#### 3. Button으로 Note를 원하는 시점에 멈추는 문제

Timer로 구현해놓은 Note를 멈추기 위해 처음에는 synchronized를 이용해 stop() 혹은 wait()으로 계속 시도를 했다. 하지만 스레드를 멈추면 Note생성 메서드 뿐 아니라 Frame 전체가 멈추거나 에러가 먹는 현상이 발생했다. 그래서 isRunning이라는 boolean 인스턴스 변수를 만들어 리스너에서 그 값을 변경할 수 있게 만들고, Timer내 actionPerformed의 실행 조건으로 설정했다.



## 앞으로 개선할 것들

#### 1. 곡 종류 및 난이도 추가

본 프로그램은 한 곡만 이용해서 코딩했다. 하지만 사용자가 다양한 난이도를 즐길 수 있도록 곡 종류와 각 곡의 난이도를 추가하면 좋을 것 같다.



#### 2. 콤보 수와 점수, 노트 판정에 시각적 효과 추가

Label을 사용하여 매 Key 입력에 콤보 수, 점수, 등의 게임적 요소가 변하게 구현했지만, 포토샵을 활용한 Image를 추가하여 좀 더 타격감 넘치는 프로그램을 만들 수 있다.



#### 3. 일시정지 및 계속하기 기능 추가

Button을 누르면 Thread가 일시정지 되었다가, 한 번 더 누르면 멈췄던 부분부터 다시 실행되는 기능을 추가하고 싶었으나, 오디오 파일을 멈춘 부분부터 다시 재생하는 것은 해결하지 못했다. 자바의 여러 가지 오디오 재생 클래스 중 멈춘 부분의 시점이나 byte를 기억하는 방법을 찾아 개선할 것이다.





## 시연영상 및 Javadoc 링크

<div>
	<a href="https://www.youtube.com/watch?v=비디오id" target="_blank"><image src = "https://img.youtube.com/vi/비디오id/mqdefault.jpg"></a>	

</div>
  
  [Javadoc 링크](https://hazsoo.github.io/bubble-pop/blob/master/doc/index.html)
