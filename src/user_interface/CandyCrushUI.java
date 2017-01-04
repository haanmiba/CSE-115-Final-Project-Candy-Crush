package user_interface;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import code.CandyCrush;

public class CandyCrushUI implements Runnable {

	private JFrame frame;
	private CandyCrush _candyCrush;
	
	private ArrayList<ArrayList<JButton>> buttons;
	private int numClicks;
	
	public void run() {
		_candyCrush = new CandyCrush(this);
		frame = new JFrame("Hans Bas's Lab 11");
		frame.getContentPane().setLayout(new GridLayout(0, 5));
		numClicks = 0;
		
		buttons = new ArrayList<ArrayList<JButton>>();
		for (int i = 0; i < 5; i++) {
			ArrayList<JButton> buttonRow = new ArrayList<JButton>();
			for (int j = 0; j < 5; j++) {
				JButton button = new JButton();
				button.setIcon(new ImageIcon("Images/Tile-" + _candyCrush.getImages(i, j) + ".png"));
				button.setEnabled(true);
				button.addActionListener(new EventHandler(_candyCrush, this));
				buttonRow.add(button);
				frame.add(button);
			}
			buttons.add(buttonRow);
		}
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
	}
	
	public ArrayList<ArrayList<JButton>> getButtons() {
		return buttons;
	}
	
	public void updateDisplay(int row, int column, int possibleRow, int possibleColumn) {
		numClicks++;
		buttons.get(row).get(column).setOpaque(true);
		buttons.get(row).get(column).setBackground(Color.RED);
		buttons.get(possibleRow).get(possibleColumn).setOpaque(true);
		buttons.get(possibleRow).get(possibleColumn).setBackground(Color.YELLOW);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				buttons.get(i).get(j).setIcon(new ImageIcon("Images/Tile-" + _candyCrush.getImages(i, j) + ".png"));
			}
		}
		
		if (numClicks == 2) {
			removeBorder();
			numClicks = 0;
		}
		
		frame.repaint();
		
	}
	
	public void removeBorder() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				buttons.get(i).get(j).setOpaque(false);
			}
		}
	}
	
}
