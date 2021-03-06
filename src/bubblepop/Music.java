package bubblepop;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * 음악 클래스. 재생시키거나 멈춤.
 * @author Jisoo Ha
 *
 */
public class Music extends Thread {
	/**
	 * 음악 폴더 경로
	 */
	private String musicFolder = "E:/_00_java_project/beatgame/src/musics";
	/**
	 * 음악 클립 객체 생성
	 */
	private Clip clip;
	
	/**
	 * 음악 정지 메서드
	 */
	public void stopMusic() {
		if (clip != null) {
			clip.stop();
		}
	}
	
	/**
	 * 음악 재생 메서드
	 * @param musicName 음악 제목
	 */
	public void playMusic(String musicName) {
		stopMusic(); // 실행중이던 음악 정지
		try {
			File musicPath = new File(musicFolder + "/" + musicName);
			if(musicPath.exists()) {
				AudioInputStream musicinput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(musicinput);
				clip.start();
			}
			else {
				System.out.println("음악파일이 없음");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
	}
}
