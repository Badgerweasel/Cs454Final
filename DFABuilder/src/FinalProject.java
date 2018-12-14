import java.math.BigInteger;
import java.util.ArrayList;

public class FinalProject {
	int[][][][] M;
	int[][] dfa;
	boolean[] accepting;
	int size;
	int length;
	BigInteger score;
	boolean changed;
	
	FinalProject(int size, int length)
	{
		//M = new int[size][size][length][2*length];
		
		//dfa = new int[size][2];
		
		DfaBuilder temp = new DfaBuilder(size);
		
		//dfa = temp.getDFA();
		//this.size = size;
		//this.length = length;
		//accepting = temp.getAccepting();
		dfa = new int[2][2]; //[number of states] [number of inputs]
		dfa[0][0] = 0; //State 0, input 0, =0 so go to state 0.
		dfa[0][1] = 1; //State 0, input 1, =1 so go to state 1.
		dfa[1][0] = 1; //State 1, input 0, =1 so go to state 1.
		dfa[1][1] = 0; //State 1, input 1, =0 so go to state 0.
		accepting = new boolean[2]; //Value of 2 since that is our total number of states. 
		accepting[0] = true; //State 0 is accepting.
		accepting[1] = false; //State 1 is not accepting. 
		this.size = 2;
		this.length = 3;
		M = new int[this.size][this.size][this.length][2*this.length]; //[2 states: 0,1] [2 possible inputs: {0,1}] [???] [???]
	}
	
	public void run()
	{
		System.out.println("-----------Populating M---------------");
		populateM();
		System.out.println("--------------Done populating M, now computing the sum() for \'a\'--------------");
		score = sum();
		changed = true;
		System.out.println("---------------\'a\' completed sum, now running print2D--------------");
		print2D();
		System.out.println("-----------Done running print2D. Now printing a.toString-------------------");
		System.out.println(score.toString());
		System.out.println("------------Done printing a.toString. Now mutating--------------------");
		while(changed)
		{
			changed = false;
			mutate();
		}
		System.out.println("--------------Mutation Complete, now running printDFA(dfa, accepting)-----------------");
		System.out.println(printDFA(dfa, accepting));
		System.out.println("-------------Done running printDFA, program complete.----------------");
	}
	
	protected void populateM()
	{
		//States of M
		for(int i = 0; i < M.length; i++)
		{
			//Possible inputs of M
			for(int j = 0; j < M[i].length; j++)
			{
				M[i][j][1][length] = 0;
				
				//If state i (state from the input DFA), input 1 = j (j=input), we set transition to state 1. 
				if(dfa[i][1] == j)
				{
					M[i][j][1][length + 1] = 1;
				}
				else //Otherwise its transition goes to state 0. 
				{
					M[i][j][1][length + 1] = 0;
				}
				//Repeat for other input. 
				if(dfa[i][0] == j)
				{
					M[i][j][1][length - 1] = 1;
				}
				{
					M[i][j][1][length - 1] = 0;
				}
				
			}
		}
		System.out.println("--------------Printing current M------------");
		print2D();
		System.out.println("--------------Done Printing current M------------");
		for(int i = 0; i < M.length; i++)
		{
			for(int j = 0; j < M[i].length; j++)
			{
				for(int p = 2; p < M[i][j].length; p++)
				{
					for(int q = M[i][j].length - p; q < M[i][j].length + p; q++)
					{
						M[i][j][p][q] = M[ dfa[i][0] ][j][p-1][q+1] + M[ dfa[i][1] ][j][p-1][q-1];
					}
				}
			}
		}
		
		
	}
	
	protected BigInteger sum()
	{
		BigInteger sum = new BigInteger("0");
		int field1 = 0; //State: 0, Input: 0
		int field2 = 0; //State: 0, Input: 1
		int field3 = 0; //State: 1, Input: 0
		int field4 = 0; //State: 1, Input: 1
		
		//For each s.
		for(int j = 0; j < M[0].length; j++)
		{
			//For each k value (k=p)
			for(int k = 0; k < M[0][j].length; k++)
			{
				//For each q value
				for(int q = 0; q < M[0][j][k].length; q++)
				{
					if(accepting[j] && q > M[0][j].length) //Its a bit dangerous to run accepting[input] when accepting is based on States!
																	//(it just so happens #states (0,1) = #inputs {0,1}
					{
						field1 += M[0][j][k][q];
					}
					else if(!accepting[j] && q < M[0][j].length && q > 0)
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
		
		//Sum is merely the total amount of accepting states from the accepting DFA.
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
		for(int j = 0; j < children.size(); j++) {
			int addOrRemove = (int)(Math.random() * 3);
			
			if(addOrRemove == 0) //add accepting state
			{
				childAccepting.add(new boolean[accepting.length]);
				ArrayList<Integer> addsPossible = new ArrayList<Integer>();
				ArrayList<Integer> acceptingStates = new ArrayList<Integer>();
				for(int i = 0; i < accepting.length; i++)
				{
					if(accepting[i])
					{
						childAccepting.get(childAccepting.size() - 1)[i] = true;
						acceptingStates.add(i);
					}
					else
					{
						addsPossible.add(i);
					}
				}
				//Check if we can add a new accepting state and still have a fail state
				if(addsPossible.size() > 1)
				{
					childAccepting.get(childAccepting.size() - 1)[addsPossible.get((int)(Math.random() * addsPossible.size()))] = true;
				}
				else
				{
					childAccepting.get(childAccepting.size() - 1)[addsPossible.get(0)] = true;
					childAccepting.get(childAccepting.size() - 1)[acceptingStates.get((int)(Math.random() * acceptingStates.size()))] = false;
				}
			}
			else if(addOrRemove == 1) //remove accepting state
			{
				childAccepting.add(new boolean[accepting.length]);
				ArrayList<Integer> removePossible = new ArrayList<Integer>();
				for(int i = 0; i < accepting.length; i++)
				{
					if(accepting[i])
					{
						childAccepting.get(childAccepting.size() - 1)[i] = true;
						removePossible.add(i);
					}
				}
				//Check if we can add a new accepting state and still have a fail state
				if(removePossible.size() > 1)
				{
					childAccepting.get(childAccepting.size() - 1)[removePossible.get((int)(Math.random() * removePossible.size()))] = false;
				}
			}
			else
			{
				childAccepting.add(new boolean[accepting.length]);
				for(int i = 0; i < accepting.length; i++)
				{
					if(accepting[i])
					{
						childAccepting.get(childAccepting.size() - 1)[i] = true;
					}
				}
			}
			
			
		}
		
		int[][] tempDfa = copy();
		boolean[] tempAccepting = copyAccepting(accepting);

		for(int i = 0; i < children.size(); i++)
		{
			dfa = children.get(i);
			accepting = childAccepting.get(i);
			populateM();
			BigInteger a = sum();
			if(a.compareTo(score) ==  1)
			{
				tempDfa = copy();
				tempAccepting = copyAccepting(accepting);
				score = a;
				changed = true;
			}
		}
		
		dfa = copy(tempDfa);
		accepting = copyAccepting(tempAccepting);
		
		for(int i = 0; i < children.size(); i++)
		{
			System.out.println(printDFA(children.get(i), childAccepting.get(i)));
		}
		/*
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
		*/
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
	
	protected int[][] copy(int[][] c)
	{
		int[][] tempdfa = new int[c.length][c[0].length];
		
		for(int i = 0; i < c.length; i++)
		{
			for(int j = 0; j < c[i].length; j++)
			{
				tempdfa[i][j] = c[i][j];
			}
		}
		return tempdfa;
	}
	
	protected boolean[] copyAccepting(boolean[] a)
	{
		boolean[] temp = new boolean[a.length];
		for(int i = 0; i < a.length; i++)
		{
			if(a[i])
			{
				temp[i] = true;
			}
		}
		
		return temp;
	}
	
	public void print2D()
	{
		//For each State, we print out its respective M
		for(int i = 0; i < M.length; i++)
		{
			System.out.println("Current State: " + i);
			
			System.out.println("{");
			
			//For each possible input.
			for(int j = 0; j < M[i].length; j++)
			{
				System.out.println("Current Input: " + j);
				
				System.out.print("[");
				
				//For each p val???
				for(int p = 0; p < M[i][j].length; p++)
				{
					System.out.print("(");
					
					//For each q val???
					for(int q = 0; q < M[i][j].length * 2; q++)
					{
						System.out.print(M[i][j][p][q] + ", ");
					}
					System.out.print("), ");
				}
				System.out.print("],\n");
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
	

	public BigInteger GetNumberOfPermutations(int stringSize) {
		BigInteger big1, big2, bigComplete;
		big1 = new BigInteger("2");
		big2 = new BigInteger("0");
		bigComplete = new BigInteger("0");
		for (int i=stringSize; i>0;i--) {
			big2 = big1.pow(i);
			bigComplete = bigComplete.add(big2);
		}
		return bigComplete;
	}
}
