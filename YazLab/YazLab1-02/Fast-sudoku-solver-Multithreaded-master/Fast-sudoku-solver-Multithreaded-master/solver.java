import java.util.*;
public class solver implements Runnable {

	
	static boolean finished;
	static int r=1;
	static int s=1;
	static int t=0;

	public void run() {
		int[][] board = setUpBoard(1);					
		int i, j, value;								
		long start, end;
		Random generator = new Random(System.currentTimeMillis());
			i = generator.nextInt(3);
			j = generator.nextInt(3);
			value = generator.nextInt(3)+1;
			printinitial(i,j,value);

			start = System.currentTimeMillis();
	
			if (solve(i, j, board, 0, value)){
			
				printBoard(board);
				end = System.currentTimeMillis();
				printime(start,end);
				if(!finished){
				finished = true;
				}
				Thread.currentThread().interrupt();
			}
			else
				System.out.println("No answers for this puzzle!");
	}

    

	public static void main(String[] args) {
		/**System.out.println("Using 1 thread");
			finished = false;
			solver solve=new solver();
			Thread thread1=new Thread(solve);
		
			thread1.start();
			while(!finished);
			
			try
        		{
				thread1.interrupt();
        		}

        		catch(Exception ex)
        		{
            			System.out.println("Exception has " +
                                "been caught" + ex);
        		}
		
		
			System.out.println("Using two threads");		
		
			finished = false;
			solver solve=new solver();
			Thread thread1=new Thread(solve);
			Thread thread2=new Thread(solve);
			
			

			thread1.start();

			thread2.start();
			
			while(!finished);
			
			 try{
				
				thread1.interrupt();
				thread2.interrupt();
			}
        
        		catch(Exception ex)
        		{
            			System.out.println("Exception has " +
                                "been caught" + ex);
        		}
			
		/**
			System.out.println("Using 3 threads");
			finished = false;
			
			solver solve=new solver();
			Thread thread1=new Thread(solve);
			Thread thread2=new Thread(solve);
			Thread thread3=new Thread(solve);
			


			thread1.start();
			thread2.start();
			thread3.start();

			
			while(!finished);
			
			try
        		{
           
				thread1.interrupt();
				thread2.interrupt();
				thread3.interrupt();
        		}

        		catch(Exception ex)
        		{
            			System.out.println("Exception has " +
                                "been caught" + ex);
        		}
			

	
		
		System.out.println("Using four threads");		
		
			finished = false;
			solver solve=new solver();
			Thread thread1=new Thread(solve);
			Thread thread2=new Thread(solve);
			Thread thread3=new Thread(solve);
			Thread thread4=new Thread(solve);
			
			thread1.start();
			
			thread2.start();
			
			thread3.start();
			
			thread4.start();
		
			while(!finished);
			
			try
        		{
           
				thread1.interrupt();
				thread2.interrupt();
				thread3.interrupt();
				thread4.interrupt();
			
			
        		}

        		catch(Exception ex)
        		{
            			System.out.println("Exception has " +
                                "been caught" + ex);
        		}
		**/
	
		System.out.println("Using five threads ");		
	

			finished = false;
			solver solve=new solver();
			Thread thread1=new Thread(solve);
			Thread thread2=new Thread(solve);
			Thread thread3=new Thread(solve);
			Thread thread4=new Thread(solve);
			Thread thread5=new Thread(solve);
		
			thread1.start();
			thread2.start();
			thread3.start();
			thread4.start();
			thread5.start();
			
			while(!finished);
			
			try
        		{
           
				thread1.interrupt();
				thread2.interrupt();
				thread3.interrupt();
				thread4.interrupt();
				thread5.interrupt();


        		}

        		catch(Exception ex)
        		{
            			System.out.println("Exception has " +
                                "been caught" + ex);
        		}
			
		
	}
	
	
    static int[][] setUpBoard(int puzzleChoise) {
 
    	
    	int[][] newBoard = new int[4][4];
    	int i,j, n=0;
        
    	
        int[] puzzle = {4,0,0,2,0,3,0,4,3,0,0,0,0,4,0,0};
        for(i = 0; i < 4; i++)
        	for(j= 0; j < 4; j++)
    			newBoard[i][j] = puzzle[n++];

       
        return newBoard;

    }

	
	static boolean solve(int row, int col, int[][] board,int xTimes, int startV) {
		
		if(xTimes == 16) return true;
		int f=1;
		for(int p=0;p<=3;p++){
			for(int q=0;q<=3;q++){
				if(board[p][q]==0){ f=0;}	
			}
		}
		if(f==1)return true;


		
        if (++col == 4){
        	col = 0;
        	if(++row == 4)
        		row = 0;
        }
       
		
        if (board[row][col] != 0){
       		return solve(row ,col, board, xTimes+1, startV);
        }

        for (int val = 1; val <= 4; ++val) {
        	if(++startV == 5) startV = 1;
        	
            if (allowedHere(row,col,startV,board)) {
                board[row][col] = startV;   
        		if(solve(row ,col, board, xTimes+1, startV))
        			return true;
            }
        }
        
        board[row][col] = 0;
        return false;
    }

    static boolean allowedHere(int row, int col, int value, int[][] board) {
    	int i;
    	
    	
    	for(i = 0; i < 4; i++){
    		
     		if(board[row][i] == value)
     			return false;
     		
     		if(board[i][col] == value)
     			return false;
     		
     		if (board[row/2*2+i%2][col/2*2+i/2] == value)
     			return false;
    	}
        return true;
    }

    static synchronized void printBoard(int[][] boardToPrint) {
    	int i, j;
    	
    	if(r==1){
    		r=0;

        for (i = 0; i < 4; i++) {
            if( i%2 == 0)
                System.out.println(" ------------");
            
            for (j = 0; j < 4; j++) {
                if (j%2 == 0) System.out.print("| ");
                if(boardToPrint[i][j] == 0)
                	System.out.print("* ");
                else
                	System.out.print(Integer.toString(boardToPrint[i][j])+ " ");
            }
            System.out.println("|");
        }
        System.out.println(" ------------");
	

	System.out.println("Thread that solved first:"+Thread.currentThread().getName());
    	}
    }
    static synchronized void printime(long start,long end){
    	if(s==1){
    		s=0;
	
    	System.out.print("Time taken:"+(end-start)+"\n");
    	}
    }

	static synchronized void printinitial(int i,int j,int value){
	System.out.println("random row:"+i+" "+"random Col:"+j+" "+"random value:"+value+" "+Thread.currentThread().getName());


	}
}


