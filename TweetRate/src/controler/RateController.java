/*
 * Controller managing the rating combobox 
 */

package controler;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;

import model.Tweet;
import model.TweetAction;
import view.InterfaceG;


public class RateController implements ItemListener {
	@SuppressWarnings("unused")
	private TweetAction model;
	JList<String> listTweets;
	JComboBox<Integer> ratingComboBox;
	InterfaceG view;


	public RateController(TweetAction model, InterfaceG view) {
		this.model = model;
		this.view = view;
	}


	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			int mark = Integer.parseInt(((String) e.getItem()));
			List<Tweet> list = TweetAction.getListCleanTweets();
			if (list != null && !list.isEmpty()){
				int index = view.list.getSelectedIndex();
				if (index != -1){
					Tweet t = list.get(view.list.getSelectedIndex());
					t.setNote(mark);
					switch(mark){
					case 0:
						view.tweetText.setBackground(new Color(246, 51, 51));
						break;
					case 2:
						view.tweetText.setBackground(new Color(110, 139, 235));
						break;
					case 4:
						view.tweetText.setBackground(new Color(84, 218, 69));
						break;
					default:
						view.tweetText.setBackground(Color.GRAY);
						break;
					}
				}
			}
		}
	}
}
