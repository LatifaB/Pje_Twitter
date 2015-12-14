/*
 * Controller managing the proxy checkbox
 */

package controler;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JCheckBox;

import model.TweetAction;

public class ProxyController implements ItemListener {
	TweetAction model;
	JCheckBox check;

	public ProxyController(TweetAction model, JCheckBox check) {
		this.model = model;
		this.check = check;
	}

	public void itemStateChanged(ItemEvent e) {
		try {
			if (check.isSelected()) {
				model.setProxy(true);
			} else {
				model.setProxy(false);
			}

		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}

	}
}
