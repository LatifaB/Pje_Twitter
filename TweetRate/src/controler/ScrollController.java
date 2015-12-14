/*
 * Controller managing the scrollPane printing the list of tweets
 */

package controler;

import java.awt.Color;
import java.util.List;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Tweet;
import model.TweetAction;
import view.InterfaceG;

public class ScrollController implements ListSelectionListener {
	@SuppressWarnings("unused")
	private TweetAction model;
	InterfaceG view;

	public ScrollController(TweetAction model, InterfaceG view) {
		this.model = model;
		this.view = view;
	}

	public void valueChanged(ListSelectionEvent e) {
		try {
			List<Tweet> tweets = TweetAction.getListDirtyTweets();
			int index = ((JList<?>) e.getSource()).getSelectedIndex();

			if (tweets == null) tweets = TweetAction.getListCleanTweets();

			if (!e.getValueIsAdjusting()) {
				//Printing the tweet details in the tweet panel
				view.idTweet.setText(""+tweets.get(index).getId());
				view.usernameTweet.setText(tweets.get(index).getUser());
				view.tweetText.setText(tweets.get(index).getTweet());
				int mark = TweetAction.getListCleanTweets().get(index).getNote();

				//Changes the mark of the tweet and the background color
				switch(mark){
				case 0:
					view.rateComboBox.setSelectedIndex(0);
					view.tweetText.setBackground(new Color(246, 51, 51));
					break;
				case 2:
					view.rateComboBox.setSelectedIndex(1);
					view.tweetText.setBackground(new Color(110, 139, 235));
					break;
				case 4:
					view.rateComboBox.setSelectedIndex(2);
					view.tweetText.setBackground(new Color(84, 218, 69));
					break;
				default:
					view.rateComboBox.setSelectedIndex(-1);
					view.tweetText.setBackground(Color.GRAY);
					break;
				}
			}	
		}
		catch(Exception exc){
			System.out.println("ScrollControl:Exc");
			System.out.println(exc.getMessage());
		}
	}
}
