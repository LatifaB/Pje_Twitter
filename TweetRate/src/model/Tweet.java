package model;

public class Tweet {
	private long id;
	private String tweet;
	private String username;
	private int rate;
	private String date;

	public Tweet(long id, String username, String tweet, String date, int rate) {
		this.id = id;
		this.tweet = tweet;
		this.username = username;
		this.date = date;
		this.rate = rate;
	}

	public long getId(){return id;}
	public String getTweet(){return tweet;}
	public String getUser(){return username;}
	public int getNote(){return rate;}
	public void setNote(int note){this.rate = note;}
	public String getDate(){return date;}
	
	public String toString() {
		return "Tweet [id=" + id + ", tweet=" + tweet + ", username=" + username + ", rate=" + rate + ", date=" + date
				+ "]";
	}
	
}
