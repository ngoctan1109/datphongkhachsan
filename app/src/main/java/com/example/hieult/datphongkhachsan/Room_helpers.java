package com.example.hieult.datphongkhachsan;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by HieuLT on 27/05/2015.
 */
public class Room_helpers {
    public static String getRoomType(int type){
        String rs = null;
        switch (type){
            case 1:
                rs = "Standard";
                break;
            case 2:
                rs = "Super";
                break;
            case 3:
                rs = "Vip";
                break;
        }
        return rs;
    }

    public static int getRoomImage(int type){
        int rs = 0;
        switch (type){
            case 1:
                rs = R.drawable.standards;
                break;
            case 2:
                rs = R.drawable.supers;
                break;
            case 3:
                rs = R.drawable.vips;
                break;
        }
        return rs;
    }
}
