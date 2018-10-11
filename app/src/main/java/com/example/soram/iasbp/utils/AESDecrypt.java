package com.example.soram.iasbp.utils;
import android.util.Base64;
import android.util.Log;

import com.example.soram.iasbp.ApiKeys;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;



public class AESDecrypt {
    String keygen = new ApiKeys().getKeygen();

    public String Decrypt(String response) throws Exception{
        byte[] bytes = keygen.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, "AES");
//        Key key = new SecretKeySpec(bytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] bts = Base64.decode(response, Base64.DEFAULT);
        byte[] bts2 = cipher.doFinal(bts);
        return new String(bts2);

    }

}
