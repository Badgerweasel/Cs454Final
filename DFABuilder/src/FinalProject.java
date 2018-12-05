import java.math.BigInteger;

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
		for(int i = 0; i < M.length; i++)
		{
			for(int j = 0; j < M[i].length; j++)
			{
				for(int p = 0; p < M[i][j].length; p++)
				{
					for(int q = 0; q < M[i][j][p].length - 1; q++)
					{
						System.out.println(M[i][j][p][q]);
					}

				}
			}
		}
		BigInteger a = sum();
		System.out.println(a.toString());
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
		
		for(int i = 0; i < M.length; i++)
		{
			for(int j = 0; j < M[i].length; j++)
			{
				for(int p = 2; p < M[i][j].length; p++)
				{
					for(int q = 1; q < M[i][j][p].length - 1; q++)
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
		
		for(int i = 0; i < M[0].length; i++)
		{
			for(int k = 0; k < M[0][i].length; k++)
			{
				for(int q = 0; q < M[0][i][k].length; q++)
				{
					if(accepting[i] && q > length)
					{
						field1 += M[0][i][k][q];
					}
					else if(!accepting[i] && q < length)
					{
						field2 += M[0][i][k][q];
					}
					else if(accepting[i] && q == length)
					{
						field3 += M[dfa[0][1]][i][k][q];
					}
					else if(!accepting[i] && q == length)
					{
						field4 += M[dfa[0][0]][i][k][q];
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
}
