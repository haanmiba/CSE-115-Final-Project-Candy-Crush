package user_interface;

import java.awt.event.*;

import javax.swing.JButton;

import code.CandyCrush;
import user_interface.CandyCrushUI;

public class EventHandler implements ActionListener {

	private CandyCrush _candyCrush;
	private CandyCrushUI _ui;
	
	public EventHandler(CandyCrush c, CandyCrushUI ui) {
		_candyCrush = c;
		_ui = ui;
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton pressedButton = (JButton) e.getSource();
		
		int rowIndex = -1;
		int columnIndex = -1;
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (_ui.getButtons().get(i).get(j) == pressedButton) {
					rowIndex = i;
					columnIndex = j;
				}
			}
		}
		
		_candyCrush.buttonPressed(rowIndex, columnIndex);
	}
	
}
