/*
 * Controller managing the choice of the algorithm for the classification
 */

package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.Tweet;
import model.TweetAction;

public class RateMethodController implements ActionListener {
	TweetAction model;

	public RateMethodController(TweetAction model) {
		this.model = model;
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String method = button.getText();
		List<Tweet> list = TweetAction.getListCleanTweets();

		String text = "";
		System.out.println(list);
		if (list == null || list.isEmpty()) text += "Do a research before.\n";
		if (TweetAction.getBase() == null || TweetAction.getBase().isEmpty()) text += "The tweet base is empty.";

		if (text.equals("")){
			try {
				model.rateTweets(list, method);
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
		else {
			JOptionPane.showMessageDialog(null, text);
		}
	}
}
