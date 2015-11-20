package com.pairme;

import android.widget.TextView;

public class Player {
	private int score;
 
	private TextView nameView;


	private TextView scoreView;
	
	public Player(){
		score = 0; 
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	public TextView getNameView() {
		return nameView;
	}
	public void setNameView(TextView nameView) {
		this.nameView = nameView;
	}
	public TextView getScoreView() {
		return scoreView;
	}
	public void setScoreView(TextView scoreView) {
		this.scoreView = scoreView;
	}

}
