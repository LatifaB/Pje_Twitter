/*
 * Controller managing the saving base button 
 */

package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.TweetAction;

public class SaveController implements ActionListener {
	private TweetAction model;

	public SaveController(TweetAction model) {
		this.model = model;
	}

	public void actionPerformed(ActionEvent e) {
		if (!TweetAction.getListCleanTweets().isEmpty() && TweetAction.getListCleanTweets() != null){
			int choice = JOptionPane.showConfirmDialog(null, 
					"Save these tweets in the base ?", 
					"Add tweets to base", 
					JOptionPane.YES_NO_OPTION); 
			if (choice == 0){
				model.saveBase();	
			}
		}
	}
}
