package com.pairme;


import java.util.ArrayList;
import java.util.Random;
  
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity{
	Player[] players;
	int numPlayers, idClickSound, idRemoveSound, idVictorySound;
	TurnController turn; 
	View toUpdate;
	SoundPool soundPool;
	boolean musicON;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		initializeAudio(); 
		initializeComponents();

	}

	private void initializeAudio() {
		SharedPreferences pref = getSharedPreferences("com.pairme_preferences",MODE_PRIVATE);
		musicON = pref.getBoolean("musica", false);
		if(musicON){
			soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
			idClickSound = soundPool.load(this, R.raw.putdown, 0);
			idRemoveSound = soundPool.load(this, R.raw.drawstri,0);
			idVictorySound = soundPool.load(this, R.raw.ff5victo,0);
		}
	}

	private void initializeComponents() {
		initiatePlayers(2);
		initializeTurnController();
		initializeBoard();
	}

	private void initializeBoard() {
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new CardAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	if(musicON)soundPool.play(idClickSound, 1, 1, 1, 0, 1);
	        	//((CardView)v).flipView();
	        	turn.current = v;
        		turn.newMovement(v);
	        	 
  	        }
	    });
 	}

	private void initializeTurnController() {
		turn = new TurnController();
		
	}

	private void initiatePlayers(int num) {
		SharedPreferences pref = getSharedPreferences("com.pairme_preferences",MODE_PRIVATE);
		numPlayers = num;
		players = new Player[num];
		for(int i = 0; i<num;i++)
		{
			players[i] = new Player(); 
		} 
		players[0].setNameView((TextView)findViewById(R.id.player1));
		players[0].setScoreView((TextView)findViewById(R.id.player1score));
		players[1].setNameView((TextView)findViewById(R.id.player2));
		players[1].setScoreView((TextView)findViewById(R.id.player2score)); 
		
		players[0].getNameView().setText(pref.getString("player1name", "Jugador 1"));
		players[1].getNameView().setText(pref.getString("player2name", "Jugador 2"));
	}
	
	private class TurnController {
		Player currentPlayer;
		ArrayList<View> selectedCards;
		View current;
		int deletedCards; 
		int pos; 
		
		public TurnController() {
			Random dice = new Random();
			pos = dice.nextInt(numPlayers-1);
			setCurrentPlayer(pos); 
			selectedCards = new ArrayList<View>();
			deletedCards = 0;
		}
		
		public void nextTurn(){
			pos = (pos+1)%numPlayers;
			setCurrentPlayer(pos);
			selectedCards.clear();
			Toast t = Toast.makeText(getApplicationContext(),R.string.next_turn,Toast.LENGTH_SHORT);
			t.setGravity(Gravity.BOTTOM, 0, 0);
			t.show();
		}
		
		public void setCurrentPlayer(int pos)
		{
			currentPlayer = players[pos];
			updatePlayerView(pos);
		}
		
		private void updatePlayerView(int pos) {
			TextView name, old_name;
			TextView score, old_score;
			if(pos == 0){
				name = (TextView)findViewById(R.id.player1);
				score = (TextView)findViewById(R.id.player1score);
				old_name = (TextView)findViewById(R.id.player2);
				old_score = (TextView)findViewById(R.id.player2score);
			}
			else{
				name = (TextView)findViewById(R.id.player2);
				score = (TextView)findViewById(R.id.player2score);
				old_name = (TextView)findViewById(R.id.player1);
				old_score = (TextView)findViewById(R.id.player1score);
			}
			updateScore(score, getCurrentPlayer().getScore());
			flashPlayer(name,score,true);
			flashPlayer(old_name,old_score,false);
		}

		private void flashPlayer(TextView name, TextView score, boolean b) {
			if(b){
				name.setTextColor(Color.YELLOW);
				score.setTextColor(Color.YELLOW);
			}
			else{
				name.setTextColor(Color.WHITE);
				score.setTextColor(Color.WHITE);
			}
			
		}

		private void updateScore(TextView scoreView, int score ) {
			scoreView.setText(String.valueOf(score));
			
		}

		public Player getCurrentPlayer(){

			return currentPlayer;
		}
		
		public void newMovement(View v){
			CheckTurntask task = new CheckTurntask();
			task.execute(v);
		}
		
		public Integer newMovementTask(View v){  
			Integer result = -1;
			turn.selectedCards.add(v);
			if(selectedCards.size() == 2){
				if(checkSuccess()){ 
					if(checkEnd()){
						result = 2;  
					}
					else{
						result = 1; 
					}
				}
				else{ 
					result = 0; 
				}
				
			}
			return result;

		}
 
		
 

		private boolean checkEnd() {
			GridView gridview = (GridView) findViewById(R.id.gridview);
			return gridview.getCount() == (deletedCards+2);
		}

		private boolean checkSuccess() {
			return(!sameCard(selectedCards) && sameImage(selectedCards)); 
		}

		private boolean sameCard(ArrayList<View> selectedCards) {
			return selectedCards.get(0).equals(selectedCards.get(1));
		}

		private void updateScore() {
			Toast t = Toast.makeText(getApplicationContext(),R.string.new_score,Toast.LENGTH_SHORT);
			t.setGravity(Gravity.BOTTOM, 0, 0);
			t.show();
			Player current = getCurrentPlayer();
			current.setScore(current.getScore() + 1); 
			updateScore(current.getScoreView(), current.getScore());
		}

		private void initializeMovements() {
			selectedCards.clear();
			
		}

		private void deleteCards(ArrayList<View> selectedCards2) {
			((CardView)selectedCards.get(0)).deleteCard();
			((CardView)selectedCards.get(1)).deleteCard();
			deletedCards += 2;
			if(musicON)soundPool.play(idRemoveSound, 1, 1, 1, 0, 1);
			
		}

		private void flipCards() {
			((CardView)selectedCards.get(0)).flipView();
			((CardView)selectedCards.get(1)).flipView();
		}

		private boolean sameImage(ArrayList<View> selectedCards) {
			Integer id1 = ((CardView)selectedCards.get(0)).getImageID();
			Integer id2 = ((CardView)selectedCards.get(1)).getImageID();
			return id1.equals(id2);
		}
		class CheckTurntask extends AsyncTask<View, Void, Integer>{

			@Override 
			protected void onPreExecute(){
				((CardView)current).flipView();
			}
			@Override
			protected Integer doInBackground(View... params) {
				return turn.newMovementTask(params[0]);
 
			}
			
			@Override
			protected void onPostExecute(Integer result){
				if(result.equals(0)){
					 synchronized (this) {
 						 try {
								wait(400);
						 } catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
						 }
						 flipCards();
						 nextTurn();
					} 
				}
				if(result.equals(1)){
					deleteCards(selectedCards);
					updateScore();
					initializeMovements();
				}
				if(result.equals(2)){
					deleteCards(selectedCards);
					updateScore();
					Toast.makeText(getApplicationContext(), R.string.win, Toast.LENGTH_SHORT).show(); 
					end();
				}
			}
			 
		}
		
 	}

	public void end() { 
		   SharedPreferences pref = getSharedPreferences("com.pairme_preferences",MODE_PRIVATE);
	       Bundle bundle = new Bundle();
	       bundle.putInt("puntj1", this.players[0].getScore());
	       bundle.putInt("puntj2", this.players[1].getScore());
	       
	       bundle.putString("nomj1", pref.getString("player1name", "Jugador 1"));
	       bundle.putString("nomj2", pref.getString("player2name", "Jugador 2"));
	       Intent intent = new Intent();
	       intent.putExtras(bundle);
	       setResult(Activity.RESULT_OK, intent);
	       finish(); 
	} 
  
}
