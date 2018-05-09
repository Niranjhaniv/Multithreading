package com;

import java.util.List;

/**
 * This file needs to hold your solver to be tested. 
 * You can alter the class to extend any class that extends PuzzleSolver.
 * It must have a constructor that takes in a Puzzle.
 * It must have a solve() method that returns the datatype List<Direction>
 *   which will either be a reference to a list of steps to take or will
 *   be null if the puzzle cannot be solved.
 */
/*
 *@Invariant 1)Puzzle must return atmost one solution
 *			 2)Puzzle should not be more than 20,000 x 20,000 cells
 *			 3)Puzzles should not contain cycles and/or loops
 * 
 */
public class StudentMultiPuzzleSolver extends SkippingPuzzleSolver
{
    public StudentMultiPuzzleSolver(Puzzle puzzle)
    {
        super(puzzle);
    }
    /*
     *@Pre-Condition : Puzzle should not be null
     *@Post-Condition : Returns the atmost one solution for the puzzle
     *@Exception : Null pointer exception
     *			   Interrupted exception if the thread is interrupted in the middle
     */

    public List<Direction> solve()
    {
        // TODO: Implement your code here
    	/*
    	 * Approach
    	 * ******************************************************************
    	 * Here I am going to use Depth first search(Parallel processing)to find the shortest path.
    	 * Both BFS and DFS takes same time and have same efficiency to compute the path. 
    	 * The difference is that BFS returns shortest path whereas the DFS returns just a path 
    	 * and also memory consumed by DFS is comparatively less than BFS
    	 * 
    	 * Loop through all the choices and call follow method - it follows the point until it reaches a choice point
    	 * Call DFS on this current choice in parallel way using java.util.concurrent classes (Executors)
    	 * Get the path returned by multiple DFS call  using futures
    	 * 
    	 * 
    	 */
    	
    	/* PSEUDOCODE
    	 * ************************************************************************
    	 * 
    	 * Step 1: Create a LinkedList for path
    	 * Step 2: Create a LinkedList for choiceStack
    	 * Step 3: get the List of choices from firstChoice(puzzle.getStart()) and push it in the choiceStack
    	 * Step 4: Create a executor service.
    	 * Step 5: Loop the choices 
    	 * 				get the presentChoice
    	 * 				invoke DFS on presentChoice add them to the list pathToRun 
    	 * 		   End Loop
    	 * Step 6 : invoke all the choices in the pathToRun list parallely
    	 * Step 7 : Get the path obtained using future
    	 * Step 8 : DFS is implements the Callable function 
    	 * 			try starts
    	 * 				check the  choicePosition and path 
    	 * 				Loop till choiceStack is empty
    	 *             		 if Deadend reached 
    	 * 						backtrack the path(throws solution found exception)
    	 * 					else
    	 * 						get the new path choices from the current position
    	 * 				End Loop
    	 * 			try ends
    	 * 		 
    	 * 		catch the solution found exception 
    	 * 			return path found
    	 * 
    	 * 	 return null if no solution found
    	 * 
    	 */
        throw new RuntimeException("Not yet implemented!");
    }
}
