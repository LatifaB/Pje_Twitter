/*
 * Ranking with the positives and negatives words in
 * the .txt files
 */

package classification;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class KeywordMethod {


	public static int getClassePosNeg(String tweet) {
		int pos = nbPosWords(tweet); //number of positive words in the tweet
		int neg = nbNegWords(tweet); //number of negative words in the tweet

		int result = pos - neg;

		if (result > 0) 
			return 4;
		else if (result < 0)
			return 0;
		else 
			return 2;
	}


	private static int nbPosWords(String tweet) {
		int pos = 0;

		String fileName = "";
		try {
			fileName = new java.io.File(".").getCanonicalPath()
					+ "/tweets/positive.txt";
		} catch (IOException e1) {
			System.out.println("keyWord:nbPos:excFilename");
			System.out.println(e1.getMessage());
		}

		CSVReader reader = null;
		String [] words;

		try {
			reader = new CSVReader(new FileReader(fileName), ',');

			try {
				words = reader.readNext();
				for (int cpt = 0; cpt < words.length-1; cpt++){
					if (tweet.contains(words[cpt])) pos++;
				}
			} catch (NumberFormatException | IOException e) {
				System.out.println("keyWord:nbPos:exc1");
				System.out.println(e.getMessage());
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("keyWord:nbPos:FileNotFound");
			System.out.println(e.getMessage());
		}
		return pos;
	}


	private static int nbNegWords(String tweet) {
		int neg = 0;

		String fileName = "";
		try {
			fileName = new java.io.File(".").getCanonicalPath()
					+ "/tweets/negative.txt";
		} catch (IOException e1) {
			System.out.println("keyWord:nbNeg:excFilename");
			System.out.println(e1.getMessage());
		}

		CSVReader reader = null;
		String [] words;

		try {
			reader = new CSVReader(new FileReader(fileName), ',');

			try {
				words = reader.readNext();
				for (int cpt = 0; cpt < words.length-1; cpt++){
					if (tweet.contains(words[cpt])) neg++;
				}
			} catch (NumberFormatException | IOException e) {
				System.out.println("keyWord:nbPos:exc1");
				System.out.println(e.getMessage());
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("keyWord:nbPos:FileNotFound");
			System.out.println(e.getMessage());
		}
		return neg;
	}

}
