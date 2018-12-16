
/*
 * CS 454 Final Project, Problem #3
 * 
 * Group Members: Sean Smith, Trenton Rackerby and Grant Young
 */

import java.util.Scanner;

public class main {
	
	public static void main(String [ ] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Enter number of states for the DFA: ");
		int size = input.nextInt();
		System.out.println("Enter max length of the string: ");
		int length = input.nextInt();
		
		FinalProject f = new FinalProject(size, length);
		
		f.run();
		
		/*StringRandomizer sr = new StringRandomizer(5);
		System.out.println(sr.GetString() + ", isValid: " + sr.IsValid() + ", size: " + sr.GetStringSize());
		
		int max=25, min=1;
		for(int i=0;i<10;i++) {
			System.out.println(sr.GenerateNewString((int)(Math.random() * ((max - min) + 1)) + min) 
					+ ", isValid: " + sr.IsValid() + ", size: " + sr.GetStringSize());
		}*/
		
	}

}
