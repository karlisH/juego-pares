package com.pairme;

import java.util.Random;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private cardController cardControl;

    public CardAdapter(Context c) {
        mContext = c;
        if(cardControl == null) cardControl = new cardController(); 
    }

    public int getCount() {
        return cardControl.getnumCards();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        CardView card;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            card = new CardView(mContext);
            card.setCardImages(cardControl.getCard(),cardControl.getCardBack());
            card.setLayoutParams(new GridView.LayoutParams(90, 90));
            card.setScaleType(ImageView.ScaleType.CENTER_CROP);
            card.setPadding(8, 8, 8, 8);
        } else {
            card = (CardView) convertView;
            card.setCardImages(cardControl.getCard(), cardControl.getCardBack());
        }
        card.actualizarView();
        return card;
    }
 

    // references to our images
   
    
 
    private class cardController{
    	private Integer[] cards;
    	private Integer cardBack = R.drawable.carta_back;
    	int index;
    	public cardController( ) {
    		index = 0;
    		cards  = new Integer[]  {
    				R.drawable.carta_c_b, R.drawable.carta_c_r,R.drawable.carta_c_v,
		            R.drawable.carta_p_m, R.drawable.carta_p_rs,R.drawable.carta_p_v, 
		            R.drawable.carta_c_b, R.drawable.carta_c_r,R.drawable.carta_c_v,
		            R.drawable.carta_p_m, R.drawable.carta_p_rs,R.drawable.carta_p_v,
		            R.drawable.carta_cr_b,R.drawable.carta_cr_g,R.drawable.carta_cr_mo,R.drawable.carta_cr_n,
		            R.drawable.carta_cr_b,R.drawable.carta_cr_g,R.drawable.carta_cr_mo,R.drawable.carta_cr_n
            };
    		Random rgen = new Random();
    		for (int i=0; i<cards.length; i++) {
    		    int randomPosition = rgen.nextInt(cards.length);
    		    int temp = cards[i];
    		    cards[i] = cards[randomPosition];
    		    cards[randomPosition] = temp;
    		}
		}
		public  Integer getCard() {
			if(index<cards.length){
				Integer result = cards[index];
				index++;
				return result;
			}
			else{
				Integer result = cards[0];
				index=1; 
				return result;
			}
		}
		public Integer getCardBack(){
			return cardBack;
		}
		public int getnumCards(){
			return cards.length;
		}
    }
}
