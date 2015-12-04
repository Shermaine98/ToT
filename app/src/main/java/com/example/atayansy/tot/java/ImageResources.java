package com.example.atayansy.tot.java;

import android.content.Context;

/**
 * Created by Dindin on 12/4/2015.
 */
public class ImageResources {

    public int getImage(int number, Context c){

        String imageText ="";
        if(number>=0 && number<=90)
            imageText = "p"+number;
        else if(number>=91 && number<=108)
            imageText = "p"+90;
        else if(number>=109 && number <=113)
            imageText = "p"+number;
        else if(number>=114 && number <=132)
            imageText = "p"+113;
        else if(number>=133 && number <=137)
            imageText = "p"+number;
        else if(number>=138 && number <=173)
            imageText = "p"+137;
        else if(number>=174 && number <=178)
            imageText = "p"+number;
        else
            imageText = "p"+178;

        int id = c.getResources().getIdentifier(imageText, "drawable", c.getPackageName());
        return id;

    }

}
