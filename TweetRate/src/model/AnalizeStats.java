package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classification.BayesBiGrammeMethod;
import classification.BayesMethod;
import classification.KnnMethod;
import classification.KeywordMethod;
import model.TweetAction.Rate;
import view.BarChart;
import view.PieChart;

public class AnalizeStats {
	List<List<Tweet>> set;
	Map<Tweet, Integer> ref;
	Map<String, Float> dataBase;
	Map<String, Integer> dataBayes;
	Map<String, Integer> dataKnnKeyword;
	private static int nbSubset = 10;


	public AnalizeStats() {
		dataBase = new HashMap<String, Float>();
		dataKnnKeyword = new HashMap<String, Integer>();
		dataBayes = new HashMap<String, Integer>();
		TweetAction.loadBaseTweet();

		try {
			calcErrorRate();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("AnalizeStats:construc");
		}

		createRatioBase();

		new PieChart("Ratio Tweets Base", dataBase);
		new BarChart("Statistiques Knn & KeyWord", dataKnnKeyword);
		new BarChart("Statistiques Bayes", dataBayes);
	}


	public void createSubsets() throws IOException {
		List<Tweet> base = TweetAction.getBase();
		int neg = 0, pos = 0, neu = 0;

		set = new ArrayList<List<Tweet>>(nbSubset);
		ref = new HashMap<Tweet, Integer>();
		for (int i = 0; i < nbSubset; i++) {
			set.add(new ArrayList<Tweet>());
		}

		for (Tweet tweet : base) {

			switch (tweet.getNote()) {
			case 0:
				if (neg >= nbSubset)
					neg = 0;
				ref.put(tweet, tweet.getNote());
				set.get(neg).add(tweet);
				neg++;
				break;
			case 2:
				if (neu >= nbSubset)
					neu = 0;
				ref.put(tweet, tweet.getNote());
				set.get(neu).add(tweet);
				neu++;
				break;
			case 4:
				if (pos >= nbSubset)
					pos = 0;
				ref.put(tweet, tweet.getNote());
				set.get(pos).add(tweet);
				pos++;
				break;
			}
		}
	}

	/**
	 * Calculate the % error rate of each algo 
	 * @throws IOException
	 */
	void calcErrorRate() throws IOException{
		int classeKnn, classeBayesUniFreq, classeBayesUniPres, classeBayesBiGFreq, classeBayesBiGPres, classePosNeg;
		int errKnn, errBayesUniFreq, errBayesUniPres, errBayesBigFreq, errBayesBigPres, errPosNeg;
		errBayesUniFreq = errBayesBigFreq = errBayesBigPres = errBayesUniPres = errKnn = errPosNeg = 0;

		try {
			createSubsets();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Analize:calcErrorRate:IOExcep");
		}

		for (int i = 0; i < nbSubset; i++) {
			List<Tweet> baseCalcul = concatSet(i);

			for (Tweet tweetcourrant : set.get(i)) {
				classeKnn = KnnMethod.knn(tweetcourrant.getTweet(), 30,	baseCalcul);
				classePosNeg = KeywordMethod.getClassePosNeg(tweetcourrant.getTweet());
				classeBayesUniPres = BayesMethod.rankBayes(baseCalcul, tweetcourrant.getTweet(), 0);
				classeBayesUniFreq = BayesMethod.rankBayes(baseCalcul, tweetcourrant.getTweet(), 1);
				classeBayesBiGPres = BayesBiGrammeMethod .rankBigramBayes(baseCalcul, tweetcourrant.getTweet(), 0);
				classeBayesBiGFreq = BayesBiGrammeMethod .rankBigramBayes(baseCalcul, tweetcourrant.getTweet(), 1);

				// Verification de la notation de chaque classifieur
				if (classeKnn != ref.get(tweetcourrant)) errKnn++;
				if (classePosNeg != ref.get(tweetcourrant)) errPosNeg++;
				if (classeBayesUniPres != ref.get(tweetcourrant)) errBayesUniPres++;
				if (classeBayesUniFreq != ref.get(tweetcourrant)) errBayesUniFreq++;
				if (classeBayesBiGFreq != ref.get(tweetcourrant)) errBayesBigFreq++;
				if (classeBayesBiGPres != ref.get(tweetcourrant)) errBayesBigPres++; 
			}
		}

		dataBayes.put("UnigrammeFreq", (int) (((float) errBayesUniFreq / ref.size()) * 100));
		dataBayes.put("UnigrammePres", (int) (((float) errBayesUniPres / ref.size()) * 100));
		dataBayes.put("BigrammeFreq", (int) (((float) errBayesBigFreq / ref.size()) * 100));
		dataBayes.put("BigrammePres", (int) (((float) errBayesBigPres / ref.size()) * 100));

		dataKnnKeyword.put("Knn", (int) (((float) errKnn / ref.size()) * 100));
		dataKnnKeyword.put("Pos/Neg", (int) (((float) errPosNeg / ref.size()) * 100));
	}


	public List<Tweet> concatSet(int exception) {
		List<Tweet> concat = new ArrayList<Tweet>();
		
		for (int i = 0; i < set.size(); i++) 
			if (i != exception)	concat.addAll(set.get(i));
		
		return concat;
	}

	/**
	 * Calculate % pos/neg/neu tweets in the base
	 */
	public void createRatioBase() {
		float pos, neg, neu;
		int size = TweetAction.getBase().size();

		pos = ((float) TweetAction.getTweetByClasse(Rate.POSITIVE).size() / size) * 100;
		neg = ((float) TweetAction.getTweetByClasse(Rate.NEGATIVE).size() / size) * 100;
		neu = ((float) TweetAction.getTweetByClasse(Rate.NEUTRAL).size() / size) * 100;
		dataBase.put("Positif", pos);
		dataBase.put("Negatif", neg);
		dataBase.put("Neutre", neu);
	}

}
