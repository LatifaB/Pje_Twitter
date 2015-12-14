/*
 * Controller managing the saving base button 
 */

package controler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.TweetAction;
import view.InterfaceG;

public class DeleteBaseController implements ActionListener {
	private TweetAction model;
	private InterfaceG view;

	public DeleteBaseController(TweetAction model, InterfaceG view) {
		this.model = model;
		this.view = view;
	}

	public void actionPerformed(ActionEvent e) {
		int choice = JOptionPane.showConfirmDialog(null, 
				"Are you sur you want to delete the tweet base file ?", 
				"Delete base", 
				JOptionPane.YES_NO_OPTION); 
		if (choice == 0){
			if (model.deleteBase()){
				view.idTweet.setText("");
				view.usernameTweet.setText("");
				view.tweetText.setText("");
				view.rateComboBox.setSelectedIndex(-1);
				view.tweetText.setBackground(Color.white);
			}
		}
	}
}
