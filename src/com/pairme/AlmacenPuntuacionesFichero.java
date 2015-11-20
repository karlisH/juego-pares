package com.pairme;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

public class AlmacenPuntuacionesFichero  {
	private static String FICHERO = "puntuaciones.txt";
	private Context context; 
	
	public AlmacenPuntuacionesFichero(Context context){
		this.context = context;
	}

 
	public void guardarPuntuacion(int puntosj1, String nombrej1,int puntosj2, String nombrej2, long fecha) {
		try{
			FileOutputStream f = context.openFileOutput(FICHERO, Context.MODE_APPEND);
			String texto = puntosj1 + " " + nombrej1 + " - " + puntosj2 + " " + nombrej2 + "\n";
			f.write(texto.getBytes());
			f.close();
		} catch(Exception e){
			Log.e("Asteroides", e.getMessage(),e);
		}

	}

 
	public Vector<String> listaPuntuaciones(int cantidad) {
		Vector<String> result = new Vector<String>();
		try{
			FileInputStream f = context.openFileInput(FICHERO);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(f));
			int n=0;
			String linea;
			do{
				linea = entrada.readLine();
				if(linea !=null){
					result.add(linea);
					n++;
				}
			}while(n<cantidad && linea!=null);
			f.close();
		}catch(Exception e){
			Log.e("Asteroides",e.getMessage(),e);
		}
		return result;
	}

}
