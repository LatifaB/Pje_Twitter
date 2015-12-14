/*
 * Controller allowing to note the tweets with the keypad
 */

package controler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.InterfaceG;

public class KeyController implements KeyListener {
	private InterfaceG view;
	
	
	public KeyController(InterfaceG view){
		this.view = view;
	}
	
	public void keyPressed(KeyEvent evt) {}
	public void keyReleased(KeyEvent evt) {}

	public void keyTyped(KeyEvent evt) {
		if (evt.getKeyChar() == '0'){
			view.rateComboBox.setSelectedIndex(0);
		}
		else if (evt.getKeyChar() == '2'){
			view.rateComboBox.setSelectedIndex(1);
		}
		else if (evt.getKeyChar() == '4'){
			view.rateComboBox.setSelectedIndex(2);
		}
	}

}
