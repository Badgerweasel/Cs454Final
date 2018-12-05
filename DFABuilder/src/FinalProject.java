import java.math.BigInteger;

public class FinalProject {
	int[][][][] M;
	int[][] dfa;
	boolean[] accepting;
	int size;
	int length;
	
	FinalProject(int size, int length)
	{
		M = new int[size][size][length][2*length];
		dfa = new int[size][2];
		
		DfaBuilder temp = new DfaBuilder(size);
		
		dfa = temp.getDFA();
		this.size = size;
		this.length = length;
		accepting = temp.getAccepting();
	}
	
	public void run()
	{
		populateM();
		
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
					for(int q = M[i][j].length; q < M[i][j][p].length - 1; q++)
					{
						M[i][j][p][q] = M[dfa[i][0]][j][p-1][q+1] + M[dfa[i][1]][j][p-1][q-1];
					}
					for(int q = M[i][j].length; q > 0; q--)
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
				}
			}
		}
		
		
		return sum;
	}
}
