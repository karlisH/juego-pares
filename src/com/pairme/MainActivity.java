package com.pairme;
 
import android.app.Activity;
import android.content.Intent; 
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button; 
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button playButton, configButton, aboutButton, scoreButton;
	public static AlmacenPuntuacionesFichero storage; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setStartButton();
        setPreferenceButton();
        setScoreButton();
        setAboutButton();
        inicializarAlmacen();
    }


	private void setAboutButton() {
		aboutButton = (Button)findViewById(R.id.buttonAbout);
        aboutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAbout();
				
			}
		});
	}


	protected void showAbout() {
		Toast.makeText(this, R.string.about_string, Toast.LENGTH_LONG).show();
		
	}


	private void setStartButton() {
		playButton = (Button)findViewById(R.id.buttonPlay);
        playButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startGame();
				
			}
		});
	}
	
	private void setPreferenceButton() {
		configButton = (Button)findViewById(R.id.buttonConfig);
        configButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPreferences();
				
			}
		});
	}
	
	private void setScoreButton() {
		scoreButton = (Button)findViewById(R.id.buttonPuntuacion);
        scoreButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showScore();
				
			}
		});
	}


	protected void showScore() { 
		Intent i = new Intent(this, ScoreList.class);
		startActivity(i);
	}

	private void inicializarAlmacen() {
		storage = new AlmacenPuntuacionesFichero(this);
  	}

	protected void startGame() {
		Intent game = new Intent(this, Game.class);
		startActivityForResult(game, 1234);
	}
	protected void showPreferences() {
		Intent i = new Intent(this, Configuration.class);
		startActivity(i);
	}
	
	@Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
          super.onActivityResult(requestCode, resultCode, data);
          if (requestCode==1234 & resultCode==RESULT_OK & data!=null) {
                 Bundle extras = data.getExtras();
                 int puntj1 =  extras.getInt("puntj1");
                 String nomj1 = extras.getString("nomj1");
                 int puntj2 = extras.getInt("puntj2");
                 String nomj2 = extras.getString("nomj2");
  
                 storage.guardarPuntuacion(puntj1,nomj1, puntj2,nomj2, System.currentTimeMillis());
                 showScore();
          }
	}
}