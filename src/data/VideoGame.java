package data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class VideoGame implements Node {
	
	private enum videoKey{
		USERSCORE,
		CRITICSCORE,
		COMPLETIONTIME,
		RELEASEYEAR;
	}
	
	private String name;
	private double userScore;
	private double criticScore;
	private double completionTime;
	private double releaseYear;
	private double key;
	
	public VideoGame(String name, double userScore, double criticScore, double completionTime, double releaseYear) {
		this.name = name;
		this.userScore = userScore;
		this.criticScore = criticScore;
		this.completionTime = completionTime;
		this.releaseYear = releaseYear;
		
		this.key = this.releaseYear;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void keySelector(videoKey vk) {
		switch(vk) {
		case USERSCORE:
			this.key=this.userScore;
			break;
		case CRITICSCORE:
			this.key=this.criticScore;
			break;
		case RELEASEYEAR:
			this.key=this.releaseYear;
			break;
		case COMPLETIONTIME:
			this.key=this.completionTime;
			break;
		default:
			System.out.println("Invalid key selector");
			break;
		}
	}
	
	@Override
	public double getKey() {
		return this.key;
	}
	
	public static List<Node> videoGameGenerator(){
		List<Node> videoGames = new ArrayList<>();
		for(int i=0; i<100; i++) {
			VideoGame tempVideoGame = new VideoGame("Dark Souls 2", 10, 10, 70, (int)(Math.random()*100));
			videoGames.add(i, tempVideoGame);
		}
		return videoGames;
	}

}
