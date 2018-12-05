import java.util.ArrayList;

public class StringRandomizer {
	private int stringSize = 5;
	private String inputString = "";
	private boolean isStringValid = false;
	
	public int GetStringSize() {
		return stringSize;
	}
	public String GetString() {
		return inputString;
	}
	public boolean IsValid() {
		return isStringValid;
	}
	
	StringRandomizer(int _stringSize){
		GenerateNewString(_stringSize);
	}
	
	public String GenerateNewString(int _stringSize) {
		stringSize = _stringSize;
		inputString = "";
		isStringValid = false;
		
		int oneCount = 0;
		int zeroCount = 0;
		boolean firstInput1 = false;
		
		for(int i=0;i<_stringSize;i++) {
			int val = TrueOrFalse() ? 1 : 0;
			inputString += val;
			if(i == 0 && val == 1) firstInput1 = true;
			if(val == 1) oneCount ++;
			else zeroCount++;
		}
		
		if(oneCount > zeroCount) isStringValid = true;
		else if (oneCount == zeroCount && firstInput1) isStringValid = true;
		else isStringValid = false;
		
		return inputString;
	}
	
	private boolean TrueOrFalse() {
		//(int)(Math.random() * ((max - min) + 1)) + min
		int rand = (int)(Math.random() * ((1 - 0) + 1)) + 0;
		
		if(rand == 1) return true;
		else return false;
	}
}
