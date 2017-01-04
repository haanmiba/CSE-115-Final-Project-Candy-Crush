package code;

import user_interface.CandyCrushUI;
import java.util.*;


public class CandyCrush {

	private CandyCrushUI _ui;
	private ArrayList<ArrayList<Integer>> buttons;
	private int numClicks, points, button1row, button1column, 
		button2row, button2column, possibleMoveRow, possibleMoveColumn;
	private boolean validSecondSelection, match, availableMatches;
	
	public CandyCrush(CandyCrushUI candyCrushUI) {
		_ui = candyCrushUI;
		buttons = new ArrayList<ArrayList<Integer>>();
		numClicks = 0;
		validSecondSelection = false;
		match = false;
		points = 0;
		randomizeButtonsAtBeginning();
		availableMatches = availableMoves();
	}
		
	public void randomizeButtonsAtBeginning() {
		for (int i = 0; i < 5; i++) {
			ArrayList<Integer> ai = new ArrayList<Integer>();
			buttons.add(ai);
			for (int j = 0; j < 5; j++) {
				ai.add(-1);
				ai.set(j, (int)(Math.random() * 6));
			}
		}
		guaranteeNoInitialMatches();
	}
	
	public void guaranteeNoInitialMatches() {
		
		int a1 = -1;
		int a2 = -1;
		int a3 = -1;
		
		for(int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				a1 = buttons.get(i).get(j);
				a2 = buttons.get(i).get(j+1);
				a3 = buttons.get(i).get(j+2);
				
				if ((a1 == a2) && (a2 == a3)) {
					int randomNumGen = (int)(Math.random()*6);
					while (randomNumGen == a2) {
						randomNumGen = (int)(Math.random()*6);
					}
					buttons.get(i).set(j+1, randomNumGen);					
				}
				
			}
		}
		
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				a1 = buttons.get(i).get(j);
				a2 = buttons.get(i+1).get(j);
				a3 = buttons.get(i+2).get(j);
				
				if ((a1 == a2) && (a2 == a3)) {
					int randomNumGen = (int)(Math.random()*6);
					while (randomNumGen == a2) {
						randomNumGen = (int)(Math.random()*6);
					}
					buttons.get(i+1).set(j, randomNumGen);					
				}
				
			}
		}
		
	}

	public int getImages(int i, int j) {
		return buttons.get(i).get(j);
	}

	public void buttonPressed(int row, int column) {
		
		numClicks++;
		
		if (numClicks == 1) {
			button1row = row;
			button1column = column;
		}
		
		else if (numClicks == 2) {
			button2row = row;
			button2column = column;
			
			validSecondSelection = checkValidSecondSelection(button1row, button1column, button2row, button2column);
			
			if (validSecondSelection) {
				swapButtons(button1row, button1column, button2row, button2column);
			}
			
			scanForMatches(button1row, button1column, button2row, button2column);
			
			numClicks = 0;
			match = false;
			validSecondSelection = false;
		}

		availableMatches = availableMoves();
		
		if (!availableMatches) {
			System.out.println("No more available moves.");
			System.out.println("Score: " + points);
			System.exit(0);
		}
		
		_ui.updateDisplay(row, column, possibleMoveRow, possibleMoveColumn);
		
	}
		
	public boolean checkValidSecondSelection(int b1r, int b1c, int b2r, int b2c) {
		return Math.abs(b1r - b2r) + Math.abs(b1c - b2c) == 1;
	}

	public void swapButtons(int b1r, int b1c, int b2r, int b2c) {
		int temp = buttons.get(b1r).get(b1c);
		buttons.get(b1r).set(b1c, buttons.get(b2r).get(b2c));
		buttons.get(b2r).set(b2c, temp);
	}
		
	public void scanForMatches(int b1r, int b1c, int b2r, int b2c) {
		
		int a1 = -1;
		int a2 = -1;
		int a3 = -1;
		
		for (int j = 0; j < 3; j++) {
			a1 = buttons.get(b1r).get(j);
			a2 = buttons.get(b1r).get(j+1);
			a3 = buttons.get(b1r).get(j+2);
			
			if (checkSame(a1, a2, a3)) {
				match = true;
				points += 3;
				System.out.println("The board has a match.");
				buttons.get(b1r).set(j, -1);
				buttons.get(b1r).set(j+1, -1);
				buttons.get(b1r).set(j+2, -1);
				shiftButtonsDown();
			}
			
		} 
		
		for (int j = 0; j < 3; j++) {
			a1 = buttons.get(b2r).get(j);
			a2 = buttons.get(b2r).get(j+1);
			a3 = buttons.get(b2r).get(j+2);
			
			if (checkSame(a1, a2, a3)) {
				match = true;
				points += 3;
				System.out.println("The board has a match.");
				buttons.get(b2r).set(j, -1);
				buttons.get(b2r).set(j+1, -1);
				buttons.get(b2r).set(j+2, -1);
				shiftButtonsDown();
			}
			
		}

		for (int i = 0; i < 3; i++) {
			a1 = buttons.get(i).get(b1c);
			a2 = buttons.get(i+1).get(b1c);
			a3 = buttons.get(i+2).get(b1c);
			
			if (checkSame(a1, a2, a3)) {
				match = true;
				points += 3;
				System.out.println("The board has a match.");
				buttons.get(i).set(b1c, -1);
				buttons.get(i+1).set(b1c, -1);
				buttons.get(i+2).set(b1c, -1);
				shiftButtonsDown();
			}

		}
		
		for (int i = 0; i < 3; i++) {
			a1 = buttons.get(i).get(b2c);
			a2 = buttons.get(i+1).get(b2c);
			a3 = buttons.get(i+2).get(b2c);
			
			if (checkSame(a1, a2, a3)) {
				match = true;
				points += 3;
				System.out.println("The board has a match.");
				buttons.get(i).set(b2c, -1);
				buttons.get(i+1).set(b2c, -1);
				buttons.get(i+2).set(b2c, -1);
				shiftButtonsDown();
			}

		}
				
		if (!match && validSecondSelection) {
			System.out.println("The board has no match.");
		}
		
	}
	
	public boolean checkSame(int a1, int a2, int a3) {
		return ((a1 == a2) && (a2 == a3));
	}

	public void shiftButtonsDown() {
		
		for (int i = 4; i > 0; i--) {
			for (int j = 0; j < 5; j++) {
				if (buttons.get(i).get(j) == -1) {
					int aboveIndex = 1;
					int buttonAbove = buttons.get(i-aboveIndex).get(j);
					if (buttonAbove != -1) {
						int temp = buttons.get(i).get(j);
						buttons.get(i).set(j, buttonAbove);
						buttons.get(i-1).set(j, temp);
					}
				}
			}
		}

		fillBlanks();
		
	}
		
	public void fillBlanks() {
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (buttons.get(i).get(j) == -1) {
					buttons.get(i).set(j, (int)(Math.random() * 6));
				}
			}
		}
		
	}
	
	public boolean availableMoves() {
		
		int a1 = -1;
		int a2 = -1;
		int p1 = -1;
		int p2 = -1;
		int p3 = -1;
		int p4 = -1;
		int p5 = -1;
		int p6 = -1;
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				a1 = buttons.get(i).get(j);
				a2 = buttons.get(i).get(j+1);
				
				if (a1 == a2) {

					if (i-1 > 0 && j-1 > 0) {
						p1 = buttons.get(i-1).get(j-1);	
						if (p1 == a1) {
							possibleMoveRow = i-1;
							possibleMoveColumn = j-1;
							return true;
						}
					}
					
					if (j-2 > 0) {
						p2 = buttons.get(i).get(j-2);
						if (p2 == a1) {
							possibleMoveRow = i;
							possibleMoveColumn = j-2;
							return true;
						}
					}
					
					if (i+1 < 5 && j-1 > 0) {
						p3 = buttons.get(i+1).get(j-1);
						if (p3 == a1) {
							possibleMoveRow = i+1;
							possibleMoveColumn = j-1;
							return true;
						}
					}
					
					if (i-1 > 0 && j+2 < 5) {
						p4 = buttons.get(i-1).get(j+2);
						if (p4 == a1) {
							possibleMoveRow = i-1;
							possibleMoveColumn = j+2;
							return true;
						}
					}
					
					if (j+3 < 5) {
						p5 = buttons.get(i).get(j+3);
						if (p5 == a1) {
							possibleMoveRow = i;
							possibleMoveColumn = j+3;
							return true;
						}
					}
						
					if (i+1 < 5 && j+2 < 5) {
						p6 = buttons.get(i+1).get(j+2);
						if (p6 == a1) {
							possibleMoveRow = i+1;
							possibleMoveColumn = j+2;
							return true;
						}
					}

				}
								
				}
			}
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				a1 = buttons.get(i).get(j);
				a2 = buttons.get(i+1).get(j);
				
				if (a1 == a2) {
					
	 				if (i-2 > 0) {
						p1 = buttons.get(i-2).get(j);
						if (p1 == a1) {
							possibleMoveRow = i-2;
							possibleMoveColumn = j;
							return true;
						}
					}
					if (i-1 > 0 && j-1 > 0) {
						p2 = buttons.get(i-1).get(j-1);
						if (p2 == a1) {
							possibleMoveRow = i-1;
							possibleMoveColumn = j-1;
							return true;
						}
					}
					if (i-1 > 0 && j+1 < 5) {
						p3 = buttons.get(i-1).get(j+1);
						if (p3 == a1) {
							possibleMoveRow = i-1;
							possibleMoveColumn = j+1;
							return true;
						}
					}
					if (i+2 < 5 && j-1 > 0) {
						p4 = buttons.get(i+2).get(j-1);
						if (p4 == a1) {
							possibleMoveRow = i+2;
							possibleMoveColumn = j-1;
							return true;
						}
					}
					if (i+2 < 5 && j+1 < 5) {
						p5 = buttons.get(i+2).get(j+1);
						if (p5 == a1) {
							possibleMoveRow = i+2;
							possibleMoveColumn = j+1;
							return true;
						}
					}
					if (i+3 < 5) {
						p6 = buttons.get(i+3).get(j);
						if (p6 == a1) {
							possibleMoveRow = i+3;
							possibleMoveColumn = j;
							return true;
						}
					}

				}
								
			}
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				a1 = buttons.get(i).get(j);
				a2 = buttons.get(i).get(j+2);
				
				if (a1 == a2) {
					
					if (i-1 > 0) {
						p1 = buttons.get(i-1).get(j+1);
						if (p1 == a1) {
							possibleMoveRow = i-1;
							possibleMoveColumn = j+1;
							return true;
						}
					}
					
					if (i+1 < 5) {
						p2 = buttons.get(i+1).get(j+1);
						if (p2 == a1) {
							possibleMoveRow = i+1;
							possibleMoveColumn = j+1;
							return true;
						}
					}

				}
								
			}
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				a1 = buttons.get(i).get(j);
				a2 = buttons.get(i+2).get(j);
				
				if (a1 == a2) {
					
					if (j-1 > 0) {
						p1 = buttons.get(i+1).get(j-1);					
						if (p1 == a1) {
							possibleMoveRow = i+1;
							possibleMoveColumn = j-1;
							return true;
						}
					}
					
					if (j+1 < 5) {
						p2 = buttons.get(i+1).get(j+1);					
						if (p2 == a1) {
							possibleMoveRow = i+1;
							possibleMoveColumn = j+1;
							return true;
						}
					}

				}
				
			}
		}

		return false;
		
	}
	
}
