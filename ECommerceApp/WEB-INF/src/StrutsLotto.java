

public class StrutsLotto {

	private String num1, num2, num3, num4;
	private String fixedMessage;
	
	public StrutsLotto() {
		
	}
	
	public String play() {
		//check the numbers are present and between 1-42
		fixedMessage = "Congrats, you played Lotto";
		return "Fine";
	}

	public String getNum1() {
		return num1;
	}

	public void setNum1(String num1) {
		this.num1 = num1;
	}

	public String getNum2() {
		return num2;
	}

	public void setNum2(String num2) {
		this.num2 = num2;
	}

	public String getNum3() {
		return num3;
	}

	public void setNum3(String num3) {
		this.num3 = num3;
	}

	public String getNum4() {
		return num4;
	}

	public void setNum4(String num4) {
		this.num4 = num4;
	}

	public String getFixedMessage() {
		return fixedMessage;
	}

	public void setFixedMessage(String fixedMessage) {
		this.fixedMessage = fixedMessage;
	}
	
	

}
