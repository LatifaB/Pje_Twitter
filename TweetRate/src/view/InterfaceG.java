/*
 * General view of the application
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controler.DeleteBaseController;
import controler.KeyController;
import controler.ProxyController;
import controler.RateController;
import controler.RateMethodController;
import controler.SaveController;
import controler.ScrollController;
import controler.SearchController;
import controler.StatsController;
import model.Tweet;
import model.TweetAction;
import twitter4j.Twitter;

public class InterfaceG extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel generalPanel;
	public JTextField searchField, idTweet, usernameTweet;
	public JLabel limitLabel;
	public JTextArea tweetText;
	public JComboBox<String> rateComboBox;
	public JList<String> list;
	public static Twitter twitter;
	TweetAction model;


	public InterfaceG(TweetAction m) {
		model = m; //makes the link between 
		model.addObserver(this); //view and model
		TweetAction.loadBaseTweet(); //loads the tweet base
		setResizable(false); //unresizable

		setTitle("MyTweetRate"); //title of the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 520);

		//General panel 
		generalPanel = new JPanel();
		generalPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
		setContentPane(generalPanel);
		generalPanel.setLayout(null);
		generalPanel.setBackground(new Color(0, 102, 153));

		//Button load base
		JButton btnLoadBase = new JButton("Load");
		btnLoadBase.setFont(new Font(Font.DIALOG, Font.BOLD, 11));
		btnLoadBase.setBounds(10, 10, 70, 29);
		btnLoadBase.setBackground(new Color(102, 51, 51));
		btnLoadBase.setForeground(Color.white);
		btnLoadBase.setToolTipText("Load the tweetsBase.csv file");
		generalPanel.add(btnLoadBase);

		//Button del base
		JButton btnDelBase = new JButton("Delete");
		btnDelBase.setFont(new Font(Font.DIALOG, Font.BOLD, 11));
		btnDelBase.setBounds(80, 10, 70, 29);
		btnDelBase.setBackground(new Color(182, 51, 51));
		btnDelBase.setForeground(Color.white);
		btnDelBase.setToolTipText("Delete the tweetsBase.csv file");
		generalPanel.add(btnDelBase);

		//Search label
		JLabel searchLabel = new JLabel("Enter research :");
		searchLabel.setFont(new Font("Arial", Font.BOLD, 13));
		searchLabel.setBounds(165, 10, 120, 20);
		searchLabel.setForeground(Color.white);
		generalPanel.add(searchLabel);

		//Search field
		searchField = new JTextField();
		searchField.setBounds(280, 5, 300, 30);
		searchField.setBackground(new Color(0, 102, 153));
		searchField.setForeground(Color.white);
		generalPanel.add(searchField);

		//Proxy checkbox
		JCheckBox proxy = new JCheckBox("Proxy");
		proxy.setBounds(600, 5, 120, 30);
		proxy.setBackground(new Color(0, 102, 153));
		proxy.setForeground(Color.white);
		generalPanel.add(proxy);

		//Panel with tweets details
		JPanel panelTweet = new JPanel();
		panelTweet.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		panelTweet.setBounds(160, 45, 535, 425);
		generalPanel.add(panelTweet);
		panelTweet.setLayout(null);
		panelTweet.setBackground(new Color(0, 153, 153));

		//ID label
		JLabel idLabel = new JLabel("ID :");
		idLabel.setFont(new Font("Arial", Font.BOLD, 13));
		idLabel.setBounds(20, 30, 50, 25);
		idLabel.setForeground(Color.white);
		panelTweet.add(idLabel);

		//ID tweet
		idTweet = new JTextField();
		idTweet.setEditable(false);
		idTweet.setBounds(140, 30, 200, 25);
		panelTweet.add(idTweet);

		//Username label
		JLabel userLabel = new JLabel("Username :");
		userLabel.setFont(new Font("Arial", Font.BOLD, 13));
		userLabel.setBounds(20, 80, 72, 25);
		userLabel.setForeground(Color.white);
		panelTweet.add(userLabel);

		//Username tweet
		usernameTweet = new JTextField();
		usernameTweet.setEditable(false);
		usernameTweet.setBounds(140, 80, 200, 25);
		panelTweet.add(usernameTweet);

		//Text label
		JLabel labelText = new JLabel("Text :");
		labelText.setFont(new Font("Arial", Font.BOLD, 13));
		labelText.setBounds(20, 130, 60, 25);
		labelText.setForeground(Color.white);
		panelTweet.add(labelText);

		//Text tweet
		tweetText = new JTextArea();
		tweetText.setWrapStyleWord(true);
		tweetText.setEditable(false);
		tweetText.setLineWrap(true);
		tweetText.setFont(new Font("Arial", Font.ITALIC, 13));
		tweetText.setBounds(140, 130, 300, 90);
		panelTweet.add(tweetText);

		//Rate label
		JLabel ratingLabel = new JLabel("Rating :");
		ratingLabel.setFont(new Font("Arial", Font.BOLD, 13));
		ratingLabel.setBounds(20, 250, 50, 25);
		ratingLabel.setForeground(Color.white);
		panelTweet.add(ratingLabel);

		//Rate combobox
		rateComboBox = new JComboBox<String>();
		rateComboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "0", "2", "4" }));
		rateComboBox.setBounds(140, 250, 50, 25);
		panelTweet.add(rateComboBox);

		//Ranking method label
		JLabel labelRank = new JLabel("Ranking method :");
		labelRank.setFont(new Font("Ariana", Font.BOLD, 13));
		labelRank.setBounds(20, 300, 150, 25);
		labelRank.setForeground(Color.white);
		panelTweet.add(labelRank);

		//Button keyword
		JButton btnKeyword = new JButton("Keyword");
		btnKeyword.setBounds(140, 300, 125, 25);
		btnKeyword.setBackground(new Color(51, 102, 102));
		btnKeyword.setForeground(Color.white);
		btnKeyword.setToolTipText("Ranking with the keyword method");
		panelTweet.add(btnKeyword);

		//Button knn 
		JButton btnKnn = new JButton("Knn");
		btnKnn.setBounds(270, 300, 125, 25);
		btnKnn.setBackground(new Color(51, 102, 102));
		btnKnn.setForeground(Color.white);
		btnKnn.setToolTipText("Ranking with the knn method");
		panelTweet.add(btnKnn);

		//Button bayesUniPres
		JButton btnBayesUniPres = new JButton("BayesUniPres");
		btnBayesUniPres.setBounds(400, 300, 125, 25);
		btnBayesUniPres.setBackground(new Color(51, 102, 102));
		btnBayesUniPres.setForeground(Color.white);
		btnBayesUniPres.setToolTipText("Ranking with the bayes presence method");
		panelTweet.add(btnBayesUniPres);

		//Button bayesUniFreq
		JButton btnBayesUniFreq = new JButton("BayesUniFreq");
		btnBayesUniFreq.setBounds(140, 330, 125, 25);
		btnBayesUniFreq.setBackground(new Color(51, 102, 102));
		btnBayesUniFreq.setForeground(Color.white);
		btnBayesUniFreq.setToolTipText("Ranking with the bayes frequence method");
		panelTweet.add(btnBayesUniFreq);

		//Buton bayesBigPres
		JButton btnBayesBigPres = new JButton("BayesBigPres");
		btnBayesBigPres.setBounds(270, 330, 125, 25);
		btnBayesBigPres.setBackground(new Color(51, 102, 102));
		btnBayesBigPres.setForeground(Color.white);
		btnBayesBigPres.setToolTipText("Ranking with the bayes bigram presence method");
		panelTweet.add(btnBayesBigPres);

		//Button bayesBigFreq
		JButton btnBayesBigFreq = new JButton("BayesBigFreq");
		btnBayesBigFreq.setBounds(400, 330, 125, 25);
		btnBayesBigFreq.setBackground(new Color(51, 102, 102));
		btnBayesBigFreq.setForeground(Color.white);
		btnBayesBigFreq.setToolTipText("Ranking with the bayes bigram frequence method");
		panelTweet.add(btnBayesBigFreq);

		//Button statistics
		JButton btnStats = new JButton("Statistics");
		btnStats.setBounds(225, 380, 140, 35);
		btnStats.setBackground(new Color(255, 153, 51));
		btnStats.setForeground(Color.white);
		btnStats.setToolTipText("Press to print different charts with the statistics");
		panelTweet.add(btnStats);

		//Button save in base
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(370, 380, 140, 35);
		btnSave.setToolTipText("Press to add this tweets to the database");
		btnSave.setBackground(new Color(0, 53, 0));
		btnSave.setForeground(Color.white);
		panelTweet.add(btnSave);

		//Model List
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		List<Tweet> baseTweet = TweetAction.getBase();
		if (!baseTweet.isEmpty()) {
			int i = 1;
			for (@SuppressWarnings("unused") Tweet tweet : baseTweet)
				listModel.addElement("Tweet nº" + i++);
		}
		else {
			listModel.addElement("No database.");
			listModel.addElement(" ");
			listModel.addElement("Make a research,");
			listModel.addElement("rate the tweets,");
			listModel.addElement("and press \"Save\".");
		}

		//List of tweets scrollpane
		list = new JList<String>(listModel);
		JScrollPane scrollTweets = new JScrollPane();
		scrollTweets.setBounds(8, 45, 145, 425);
		scrollTweets.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		scrollTweets.getViewport().setView(list);
		generalPanel.add(scrollTweets);

		//Key listener
		KeyController keyControl = new KeyController(this);
		list.addKeyListener(keyControl);
		
		//Initialize the controllers
		SearchController searchControl = new SearchController(model, this);
		RateMethodController ratingMethodControl = new RateMethodController(model);
		SaveController saveControl = new SaveController(model);
		DeleteBaseController deleteBaseControl = new DeleteBaseController(model, this);
		ScrollController scrollControl = new ScrollController(model, this);
		RateController rateControl = new RateController(model, this);
		ProxyController proxyControl = new ProxyController(model, proxy);
		StatsController statsControl = new StatsController();

		//Controller for the JScrollPane
		list.addListSelectionListener(scrollControl);

		//Controller for the search field
		searchField.addActionListener(searchControl);

		//Controller for the save base button
		btnSave.addActionListener(saveControl);

		//Controller for the load base button
		btnLoadBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setListDirtyTweets(new ArrayList<Tweet>());
				model.setListCleanTweets(TweetAction.getBase());
				update(model, new Object());
			}
		});

		//Controller for the delete base button
		btnDelBase.addActionListener(deleteBaseControl);

		//Controllers for the rating method buttons
		btnKnn.addActionListener(ratingMethodControl);
		btnKeyword.addActionListener(ratingMethodControl);
		btnBayesUniPres.addActionListener(ratingMethodControl);
		btnBayesUniFreq.addActionListener(ratingMethodControl);
		btnBayesBigFreq.addActionListener(ratingMethodControl);
		btnBayesBigPres.addActionListener(ratingMethodControl);

		//Controller for the rate combobox
		rateComboBox.addItemListener(rateControl);

		//Controller for the proxy checkbox
		proxy.addItemListener(proxyControl);

		//Controller for the statistic button
		btnStats.addActionListener(statsControl);

		setVisible(true);
		model.initConfig();
	}

	//Update the view when there are changes
	public void update(Observable o, Object arg) {
		List<Tweet> listTweet = TweetAction.getListCleanTweets();
		DefaultListModel<String> modelTmp = (DefaultListModel<String>) list.getModel();
		modelTmp.clear();

		if (listTweet != null){
			for (int i = 1; i < listTweet.size(); i++) {
				modelTmp.addElement("Tweet nº" + i);
			}
		}

		list.setModel(modelTmp);
	}
}
