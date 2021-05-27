package bubblepop;

/**
 * 노트 생성 클래스. 생성 시간과 노트 타입을 받음 <br/>
 * @author Jisoo Ha
 *
 */
public class Beat {

	private int time;
	private String noteType;
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	
	public Beat(int time, String noteType) {
		super();
		this.time = time;
		this.noteType = noteType;
	}
	
	
}
