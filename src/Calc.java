import java.io.Serializable;

public class Calc implements Serializable{

	private String a;
	private String b;
	
	public Calc(String a, String b) {
		this.a = a;
		this.b = b;
	}
	
	public int add() {
		int x = Integer.parseInt(this.a);
		int y = Integer.parseInt(this.b);
		return x + y;
	}
}
