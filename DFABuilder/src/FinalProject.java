import java.math.BigInteger;
import java.util.ArrayList;

public class FinalProject {
	int[][][][] M;
	int[][] dfa;
	boolean[] accepting;
	int size;
	int length;
	
	FinalProject(int size, int length)
	{
		//M = new int[size][size][length][2*length];
		
		//dfa = new int[size][2];
		
		DfaBuilder temp = new DfaBuilder(size);
		
		//dfa = temp.getDFA();
		//this.size = size;
		//this.length = length;
		//accepting = temp.getAccepting();
		dfa = new int[2][2];
		dfa[0][0] = 0;
		dfa[0][1] = 1;
		dfa[1][0] = 1;
		dfa[1][1] = 0;
		accepting = new boolean[2];
		accepting[0] = true;
		accepting[1] = false;
		this.size = 2;
		this.length = 3;
		M = new int[this.size][this.size][this.length][2*this.length];
	}
	
	public void run()
	{
		populateM();
		BigInteger a = sum();
		print2D();
		System.out.println(a.toString());
		
		mutate();
		System.out.println(printDFA(dfa, accepting));
	}
	
	protected void populateM()
	{
		
		for(int i = 0; i < M.length; i++)
		{
			for(int j = 0; j < M[i].length; j++)
			{
				M[i][j][1][length] = 0;
				if(dfa[i][1] == j)
				{
					M[i][j][1][length + 1] = 1;
				}
				else
				{
					M[i][j][1][length + 1] = 0;
				}
				if(dfa[i][0] == j)
				{
					M[i][j][1][length - 1] = 1;
				}
				{
					M[i][j][1][length - 1] = 0;
				}
				
			}
		}
		print2D();
		
		for(int i = 0; i < M.length; i++)
		{
			for(int j = 0; j < M[i].length; j++)
			{
				for(int p = 2; p < M[i][j].length; p++)
				{
					for(int q = M[i][j].length - p; q < M[i][j].length + p; q++)
					{
						M[i][j][p][q] = M[dfa[i][0]][j][p-1][q+1] + M[dfa[i][1]][j][p-1][q-1];
					}
				}
			}
		}
		
		
	}
	
	protected BigInteger sum()
	{
		BigInteger sum = new BigInteger("0");
		int field1 = 0;
		int field2 = 0;
		int field3 = 0;
		int field4 = 0;
		
		for(int j = 0; j < M[0].length; j++)
		{
			for(int k = 0; k < M[0][j].length; k++)
			{
				for(int q = 0; q < M[0][j][k].length; q++)
				{
					if(accepting[j] && q < M[0][j].length && q > 0)
					{
						field1 += M[0][j][k][q];
					}
					else if(!accepting[j] && q > M[0][j].length)
					{
						field2 += M[0][j][k][q];
					}
					else if(accepting[j] && q == 0)
					{
						field3 += M[dfa[0][1]][j][k][q];
					}
					else if(!accepting[j] && q == 0)
					{
						field4 += M[dfa[0][0]][j][k][q];
					}
				}
			}
		}
		System.out.println("field1: " + field1 + "\nfield2: " + field2 + "\nfield3: " + field3 + "\nfield4: " + field4);
		
		sum = sum.add(new BigInteger(String.valueOf(field1)));
		sum = sum.add(new BigInteger(String.valueOf(field2)));
		sum = sum.add(new BigInteger(String.valueOf(field3)));
		sum = sum.add(new BigInteger(String.valueOf(field4)));
		return sum;
	}
	
	protected void mutate()
	{
		ArrayList<int[][]> children = new ArrayList<int[][]>();
		ArrayList<boolean[]> childAccepting = new ArrayList<boolean[]>();
		
		//Creating child dfas
		for (int i = 0; i < dfa.length; i ++)
		{
			for(int j = 0; j < dfa[i].length; j++)
			{
				children.add(copy());
				children.get(children.size() - 1)[i][j] = (int)(Math.random() * dfa.length);
			}
		}
		//create accepting states
		for(int i = 0; i < accepting.length * 2; i ++)
		{
			childAccepting.add(new boolean[accepting.length]);
			int numAccepting = (int)(Math.random() * accepting.length - 1);
			if(numAccepting <= 0)
			{
				numAccepting = 1;
			}
			for(int j = 0; j < numAccepting; j++)
			{
				childAccepting.get(childAccepting.size() - 1)[(int)(Math.random() * accepting.length)] = true;			
			}
		}
		
		for(int i = 0; i < children.size(); i++)
		{
			System.out.println(printDFA(children.get(i), childAccepting.get(i)));
		}
		
	}
	
	protected int[][] copy()
	{
		int[][] tempdfa = new int[dfa.length][dfa[0].length];
		
		for(int i = 0; i < dfa.length; i++)
		{
			for(int j = 0; j < dfa[i].length; j++)
			{
				tempdfa[i][j] = dfa[i][j];
			}
		}
		return tempdfa;
	}
	
	public void print2D()
	{
		for(int i = 0; i < M.length; i++)
		{
			System.out.println("{");
			for(int j = 0; j < M[i].length; j++)
			{
				System.out.print("[");
				for(int p = 0; p < M[i][j].length; p++)
				{
					System.out.print("(");
					for(int q = 0; q < M[i][j].length * 2; q++)
					{
						System.out.print(M[i][j][p][q] + ", ");
					}
					System.out.print("), ");
				}
				System.out.print("], ");
			}
			System.out.println("}\n");
		}
	}
	
	public String printDFA(int[][] dfa, boolean[] accepting)
	{
		String DfaString = "";
		
		for(int i = 0; i < dfa.length; i++)
		{
			for(int j = 0; j < dfa[i].length; j++)
			{
				DfaString += dfa[i][j] + " ";
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
