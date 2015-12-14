/*
 * Controller managing the search textfield
 */

package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import model.TweetAction;

public class SearchController implements ActionListener {
	private TweetAction model;

	public SearchController(TweetAction model) {
		this.model = model;
	}

	public void actionPerformed(ActionEvent e) {
		String word = ((JTextField) e.getSource()).getText();

		if (!word.equals("")) {
			model.recherche = word;
			model.doSearch(word);
		}
	}
}
