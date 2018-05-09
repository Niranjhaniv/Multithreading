package com;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import com.SkippingPuzzleSolver.SolutionFound;

public class DepFirsSearch implements Callable<List<Direction>> {
		private Choice head;
		private Direction choiceDir;
		public long count = 0;
		StudentMultiPuzzleSolver stmp;
		private Puzzle puzzle;

		public DepFirsSearch(Choice head, Direction choiceDir,Puzzle puzzle, StudentMultiPuzzleSolver stmp) {
			this.head = head;
			this.choiceDir = choiceDir;
			this.puzzle = puzzle;
			this.stmp=stmp;

		}

		@Override
		public List<Direction> call() {
			LinkedList<Choice> choiceStack = new LinkedList<Choice>();
			Choice currentChoice;
			try {
				choiceStack.push(this.head);
				while (!choiceStack.isEmpty()) {
					currentChoice = choiceStack.peek();
					count++;
					if (currentChoice.isDeadend()) {
						choiceStack.pop();
						if (!choiceStack.isEmpty()) {
							Choice newChoice = choiceStack.peek();
							newChoice.choices.pop();
						}
						continue;
					}
					Choice nextChoice = stmp.follow(currentChoice.at, currentChoice.choices.peek());
					choiceStack.push(nextChoice);
				}
				return null;
			} catch (SolutionFound e) {
				Iterator<Choice> iterator = choiceStack.iterator();
				LinkedList<Direction> solutionPath = new LinkedList<Direction>();
				while (iterator.hasNext()) {
					currentChoice = iterator.next();
					solutionPath.push(currentChoice.choices.peek());
				}
				solutionPath.push(choiceDir);
				if (puzzle.display != null) {
					puzzle.display.updateDisplay();
				}
				return stmp.pathToFullPath(solutionPath);
			}
		}
	}