package com.example.hieult.datphongkhachsan;

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
}
