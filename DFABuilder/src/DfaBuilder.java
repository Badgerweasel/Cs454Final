import java.util.ArrayList;


public class DfaBuilder {
	
	protected int[][] DFA;
	protected boolean[] accepting;
	
	DfaBuilder(int size)
	{
		DFA = new int[size][2];
		accepting = new boolean[size];
		
		//Add Random transitions to DFA
		for(int i = 0; i < DFA.length; i++)
		{
			for(int j = 0; j < DFA[i].length; j++)
			{
				DFA[i][j] = (int)(Math.random() * (size));
			}
		}
		
		findAndFixUnconnected();
		boolean oneAcceptingState = false;
		
		for(int i = 0; i < accepting.length; i++)
		{
			if((int)(Math.random() * 3) == 2)
			{
				accepting[i] = true;
				oneAcceptingState = true;
			}
			else 
			{
				accepting[i] = false;
			}
				
		}
		
		if(!oneAcceptingState)
		{
			System.out.println("Adding accepting");
			accepting[(int)(Math.random() * size)] = true;
		}
		
		
		
	}
	
	private void findAndFixUnconnected()
	{
		int[] pointedAt = new int[DFA.length];
		int[][] pointingTo = new int[DFA.length][DFA.length];
		
		for(int i = 0; i < DFA.length; i++)
		{
			for(int j = 0; j < DFA[i].length; j++)
			{
				pointedAt[DFA[i][j]]++;
				pointingTo[DFA[i][j]][i]++;
			}
		}
		
		for(int i = 0; i < DFA.length; i++)
		{
			if(pointedAt[i] == 0)
			{
				int maxIndex = max(pointedAt);
				for(int j = 0; j < pointingTo[maxIndex].length; j++)
				{
					if(pointingTo[maxIndex][j] != 0)
					{
						if(DFA[j][0] == maxIndex)
						{
							DFA[j][0] = i;
						}
						else
						{
							DFA[j][1] = i;	
						}
						pointedAt[i]++;
						pointedAt[maxIndex]-= 1;
						pointingTo[maxIndex][j] -= 1;
						break;
					}
				}
			}
		}
	}
	
	public static int[][] findAndFixUnconnected(int[][] DFA)
	{
		int[] pointedAt = new int[DFA.length];
		int[][] pointingTo = new int[DFA.length][DFA.length];
		
		for(int i = 0; i < DFA.length; i++)
		{
			for(int j = 0; j < DFA[i].length; j++)
			{
				pointedAt[DFA[i][j]]++;
				pointingTo[DFA[i][j]][i]++;
			}
		}
		
		for(int i = 0; i < DFA.length; i++)
		{
			if(pointedAt[i] == 0)
			{
				int maxIndex = max(pointedAt);
				for(int j = 0; j < pointingTo[maxIndex].length; j++)
				{
					if(pointingTo[maxIndex][j] != 0)
					{
						if(DFA[j][0] == maxIndex)
						{
							DFA[j][0] = i;
						}
						else
						{
							DFA[j][1] = i;	
						}
						pointedAt[i]++;
						pointedAt[maxIndex]-= 1;
						pointingTo[maxIndex][j] -= 1;
						break;
					}
				}
			}
		}
		
		return DFA;
	}
	
	public static int max(int[] list)
	{
		int max = 0;
		int maxValue = 0;
		for(int i = 0; i < list.length; i++)
		{
			if(list[i] > maxValue)
			{
				max = i;
				maxValue = list[i];
			}
		}
		return max;
	}
	
	public int[][] getDFA()
	{
		return DFA;
	}
	
	public boolean[] getAccepting()
	{
		return accepting;
	}
	public String toString()
	{
		String DfaString = "";
		
		for(int i = 0; i < DFA.length; i++)
		{
			for(int j = 0; j < DFA[i].length; j++)
			{
				DfaString += DFA[i][j] + " ";
			}
			DfaString += "\n";
		}
		
		System.out.print("Accepting states are: ");
		
		for( int i = 0; i < accepting.length; i ++)
		{
			if(accepting[i])
			{
				System.out.print(i + " ");
			}
		}
		System.out.println("\n");
		
		return DfaString;
	}
}
