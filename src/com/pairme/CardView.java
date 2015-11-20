package com.pairme;

import android.content.Context;
 
import android.widget.ImageView;

public class CardView extends ImageView {

	private int status; 
	private Integer imageID;
	private Integer imagebackID;
	
	public CardView(Context context) {
		super(context);
		this.status = 0;
	}
	public void flipView(){
	    	if(status == 1) status = 0;
	    	else status = 1;
	    	actualizarView();
	}
   	public void actualizarView(){
   		if(status == 1) this.setImageResource(imageID);
    	else if(status == 0) this.setImageResource(imagebackID);
    	else this.setVisibility(INVISIBLE);
   	}
   	public void setCardImages(Integer image, Integer imageBack){
   		this.imagebackID = imageBack;
   		this.imageID = image;
   	}
   	public Integer getImageID(){
   		return imageID;
   	}
   	public int getStatus(){
   		return status;
   	}
   	public void deleteCard(){
   		status = 2;
   		actualizarView();
   	}
}
