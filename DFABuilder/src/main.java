
public class main {
	
	public static void main(String [ ] args)
	{
		DfaBuilder dfa = new DfaBuilder(10);
		
		System.out.println(dfa.toString());
		
		/*StringRandomizer sr = new StringRandomizer(5);
		System.out.println(sr.GetString() + ", isValid: " + sr.IsValid() + ", size: " + sr.GetStringSize());
		
		int max=25, min=1;
		for(int i=0;i<10;i++) {
			System.out.println(sr.GenerateNewString((int)(Math.random() * ((max - min) + 1)) + min) 
					+ ", isValid: " + sr.IsValid() + ", size: " + sr.GetStringSize());
		}*/
		
	}

}
