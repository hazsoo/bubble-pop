package bubblepop;

/**
 * 노트 생성 클래스. 생성 시간과 노트 타입을 받음 <br/>
 * @author Jisoo Ha
 *
 */
public class Beat {

	/**
	 * 노트 생성 시간
	 */
	private int time;
	/**
	 * 노트 종류
	 */
	private String noteType;
	
	/**
	 * 노트 생성 시간을 얻음
	 * @return 시간
	 */
	public int getTime() {
		return time;
	}
	/**
	 * 노트 생성 시간 설정
	 * @param time 시간
	 */
	public void setTime(int time) {
		this.time = time;
	}
	/**
	 * 노트 종류 얻음
	 * @return 노트 종류
	 */
	public String getNoteType() {
		return noteType;
	}
	/**
	 * 노트 종류 설정
	 * @param noteType 노트 종류
	 */
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	
	/**
	 * 생성자
	 * @param time 노트 생성 시간
	 * @param noteType 노트 종류
	 */
	public Beat(int time, String noteType) {
		super();
		this.time = time;
		this.noteType = noteType;
	}
	
	
}
