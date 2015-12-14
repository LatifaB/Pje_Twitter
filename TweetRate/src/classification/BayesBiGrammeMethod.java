/*
 * Ranking method using bigram bayes
 */

package classification;

import java.util.List;

import model.Tweet;
import model.TweetAction;
import model.TweetAction.Rate;


public class BayesBiGrammeMethod {



	private static int[] nbTweetsMoodBiGramme(List<Tweet> listMood) {
		int[] res = new int[2];
		int cpt = 0;
		res[0] = listMood.size();
		for (int i = 0; i < listMood.size(); i++) {
			cpt += listMood.get(i).getTweet().length() - 1;
		}
		res[1] = cpt;
		return res;
	}


	private static String[] tabMotsMoodBiGramme(List<Tweet> listMood) {
		int tab[] = nbTweetsMoodBiGramme(listMood);
		String resTmp[] = new String[tab[1]];
		int cpt = 0;
		int i, j;
		for (i = 0; i < listMood.size(); i++) {
			String[] tmp = listMood.get(i).getTweet().split(" ");
			for (j = 0; j < tmp.length - 1; j++) {
				resTmp[cpt] = tmp[j] + " " + tmp[j + 1];
				cpt++;
			}
		}
		int cpt2 = 1;
		for (i = 1; i < resTmp.length; i++) {
			int alreadyPresent = 0;
			for (j = 0; j < i; j++) {
				if (resTmp[i] == resTmp[j])
					alreadyPresent = 1;
			}
			if (alreadyPresent == 0)
				cpt2++;
		}
		String res[] = new String[cpt2];
		int tmp = 1;
		res[0] = resTmp[0];
		for (i = 0; i < res.length; i++) {
			int alreadyPresent = 0;
			for (j = 0; j < i; j++) {
				if ((resTmp[i] == resTmp[j]))
					alreadyPresent = 1;
			}
			if ((alreadyPresent == 0) && (tmp < cpt2)) {
				res[tmp] = resTmp[i];
				tmp++;
			}
		}
		return res;
	}


	private static int[] nbMotsBiGramme(String[] mots, List<Tweet> listMood) {
		int nbMots[] = new int[mots.length];
		int i, j, k;
		for (i = 0; i < mots.length; i++) {
			nbMots[i] = 0;
		}
		for (i = 0; i < listMood.size(); i++) {
			String tmp[] = listMood.get(i).getTweet().split(" ");
			for (j = 0; j < tmp.length - 1; j++) {
				for (k = 0; k < mots.length; k++) {
					if ((tmp[j] + " " + tmp[j + 1]).equals(mots[k])) {
						nbMots[k]++;
					}
				}
			}
		}
		return nbMots;
	}

	public static int nbMotsTotalMoodBiGramme(int[] nbMots) {
		int nbTot = 0;
		for (int nbMot : nbMots) {
			nbTot += nbMot;
		}
		return nbTot;
	}


	private static String[] tweetToTabBiGramme(String tweet) {
		String[] tab = tweet.split(" ");
		String[] biGramme = new String[tab.length - 1];
		if (tweet.length() == 0) {
			return tab;
		}
		int cpt = 1;
		int tmp;
		int pos = 1;
		biGramme[0] = tab[0] + " " + tab[1];
		for (int i = 1; i < tab.length - 1; i++) {
			tmp = 0;
			biGramme[i] = tab[i] + " " + tab[i + 1];
			for (int j = 0; j < i; j++) {
				if (biGramme[i].equals(biGramme[j])) {
					tmp = 1;
				}
			}
			if (tmp == 0) {
				cpt++;
			}
		}
		String motsTweetBiGramme[] = new String[cpt];
		motsTweetBiGramme[0] = biGramme[0];
		for (int k = 1; k < biGramme.length; k++) {
			tmp = 0;
			for (int l = 0; l < pos; l++) {
				if (motsTweetBiGramme[l].equals(biGramme[k])) {
					tmp = 1;
				}
			}
			if (tmp == 0) {
				motsTweetBiGramme[pos] = biGramme[k];
				pos++;
			}
		}
		return motsTweetBiGramme;
	}

	
	private static int[] nbMotstweetBiGramme(String tweet) {
		int cpt;
		String[] motsTweet = tweetToTabBiGramme(tweet);
		int[] nbMots = new int[motsTweet.length];
		String[] sp = tweet.split(" ");
		String[] biGrammeEntier = new String[sp.length - 1];
		for (int j = 0; j < biGrammeEntier.length; j++) {
			biGrammeEntier[j] = sp[j] + " " + sp[j + 1];
		}
		for (int i = 0; i < motsTweet.length; i++) {
			cpt = 0;
			for (String element : biGrammeEntier) {
				if (element.equals(motsTweet[i])) {
					cpt += 1.0;
				}
			}
			nbMots[i] = cpt;
		}
		return nbMots;
	}

	private static float probaTweetMoodBiGramme(List<Tweet> listTweets,
			List<Tweet> listMood, List<Tweet> secondList,
			List<Tweet> thirdList, String tweet, int classif) {
		int nbTweetsMood[] = nbTweetsMoodBiGramme(listTweets);
		int nbTweets = listTweets.size();

		String[] motsMood = tabMotsMoodBiGramme(listMood);
		String[] secondMotsMood = tabMotsMoodBiGramme(secondList);
		String[] thirdMotsMood = tabMotsMoodBiGramme(thirdList);

		int nbMotsMood[] = nbMotsBiGramme(motsMood, listMood);
		int nbMotsMoodSecond[] = nbMotsBiGramme(secondMotsMood, secondList);
		int nbMotsMoodThird[] = nbMotsBiGramme(thirdMotsMood, thirdList);

		int nbMotsTotMood = nbMotsTotalMoodBiGramme(nbMotsMood);
		int nbMotsTotSecond = nbMotsTotalMoodBiGramme(nbMotsMoodSecond);
		int nbMotsTotThird = nbMotsTotalMoodBiGramme(nbMotsMoodThird);

		int nbTotal = nbMotsTotMood + nbMotsTotSecond + nbMotsTotThird;

		String[] motsTweet = tweetToTabBiGramme(tweet);
		int[] nbMotsTweet = nbMotstweetBiGramme(tweet);
		float probaMood = 0;
		probaMood = nbTweetsMood[0] / nbTweets;
		float proba = 0;
		for (int i = 0; i < motsTweet.length; i++) {
			if (motsTweet[i].length() > 3) {
				for (int j = 0; j < motsMood.length; j++) {
					float probaMot = 0;
					if (motsMood[j].equals(motsTweet[i])) {
						if (motsMood[j].length() > 3) {
							if (proba == 0) {
								proba = 1;
							}
							if (classif == 0) {
								//Presence
								probaMot = ((float) (nbMotsMood[j] + 1))
										/ ((float) (nbMotsTotMood + nbTotal));
							} else {
								//Frequence 
								probaMot = (float) Math.pow(
										(((double) (nbMotsMood[j] + 1)) / ((double) (nbMotsTotMood + nbTotal))),
										nbMotsTweet[i]);
							}
						}
					}
					if (probaMot != 0) {
						proba *= probaMot;
					}
				}
			}
		}
		proba *= probaMood;
		return proba;
	}

	
	/**
	 * classif = 0 for presence 
	 * 	       = 1 for frequence
	 */
	public static int rankBigramBayes(List<Tweet> listTweets,
			String tweet, int classif) {
		List<Tweet> listNeg, listPos, listNeutre;
		listNeg = TweetAction.getTweetByClasse(Rate.NEGATIVE);
		listNeutre = TweetAction.getTweetByClasse(Rate.NEUTRAL);
		listPos = TweetAction.getTweetByClasse(Rate.POSITIVE);

		float probaPos = probaTweetMoodBiGramme(listTweets, listPos, listNeg, listNeutre, tweet, classif);
		float probaNeg = probaTweetMoodBiGramme(listTweets, listNeg, listPos, listNeutre, tweet, classif);
		float probaNeu = probaTweetMoodBiGramme(listTweets, listNeutre, listPos, listNeg, tweet, classif);
		
		float max = Math.max(probaPos, Math.max(probaNeg, probaNeu));
		return (max == probaPos) ? 4 : ((max == probaNeu) ? 2 : 0);
	}
}
