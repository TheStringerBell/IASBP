package com.example.soram.iasbp;

import org.magiclen.magiccrypt.MagicCrypt;

/**
 * Created by sOram on 25. 11. 2017.
 */

public class EncriptedKey {
    String key;
    String data;
    public EncriptedKey(String key, String data){
        this.key = key;
        this.data = data;
    }
    public String getMagicCrypt(){
        return (new MagicCrypt(key, 256).encrypt(data));
    }

}
