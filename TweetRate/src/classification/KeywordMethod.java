package classification;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class KeywordMethod {

	/* CLASSIFICATION PAR RAPPORT AUX FICHIER .TXT (MOTS POSITIFS/NEGATIFS) */
	/**
	 * Retourne la classe d'un tweet en fonction du nb de mot positif/négatif
	 *
	 * @param tweet
	 *            Le tweet à classer
	 * @return la classe du tweet
	 */
	public static int getClassePosNeg(String tweet) {
		/* Annotation */
		int positif = motsPositifs(tweet);
		int negatif = motsNegatifs(tweet);

		int result = positif - negatif;
		if (result > 0) {
			return 4;
		} else {
			if (result < 0) {
				return 0;
			} else {
				return 2;
			}
		}
	}

	/**
	 * Calcule le nombre de mots positifs d'un tweet
	 *
	 * @param tweet
	 *            le tweet à tester
	 *
	 * @return le nombre de mots positifs du tweet
	 */
	private static int motsPositifs(String tweet) {
		int positif = 0;
		try {
			String fileName = new java.io.File(".").getCanonicalPath()
					+ "/tweets/positive.txt";
			System.out.print("1");
			InputStream ips = new FileInputStream(fileName);
			System.out.print("2");
			InputStreamReader ipsr = new InputStreamReader(ips);
			System.out.print("3");
			BufferedReader br = new BufferedReader(ipsr);
			System.out.print("4");
			String ligne;
			String[] mots = null;

			while ((ligne = br.readLine()) != null) {
				mots = ligne.split(", ");

				for (String mot : mots) {

					if (tweet.contains(mot)) {
						positif++;
					}
				}
			}
			ips.close();
			br.close();
			ipsr.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return positif;
	}

	/**
	 * Calcule le nombre de mots négatifs d'un tweet
	 *
	 * @param tweet
	 *            le tweet à tester
	 * @return le nombre de mots négatifs de ce tweet
	 */
	private static int motsNegatifs(String tweet) {
		int negatif = 0;
		try {
			String fileName = new java.io.File(".").getCanonicalPath()
					+ "/tweets/negative.txt";
			InputStream ips = new FileInputStream(fileName);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			String[] mots = null;

			while ((ligne = br.readLine()) != null) {
				mots = ligne.split(", ");
				for (String mot : mots) {
					if (tweet.contains(mot)) {
						negatif++;
					}
				}
				ips.close();
				br.close();
				ipsr.close();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return negatif;
	}

}
