package com.example.officeutils.https;

import android.util.Base64;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author FC
 */
public class PdfConvertHelp {

    private static final String TAG = "PdfConvertHelp";

    public static final String secretId = "AKID7KgduqScdf0kqc75jqn98p1Si0hkdyp73p7G";
    //云市场分配的密钥Key
    public static final String secretKey = "lq8P1959iVGX9C2zo33kIG5VoO01P5jht7hO2ai";
    public static final String source = "market";
    public static final String url = "https://service-jnj94q93-1255058406.sh.apigw.tencentcs.com/release/v2/jobs";
    public static final String he = "data:application/pdf;base64,";

    public static String calcAuthorization(String source, String secretId, String secretKey, String datetime)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));
        String sig = Base64.encodeToString(hash,2);
        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
        Log.i(TAG, "calcAuthorization: "+auth);
        return auth;
    }

    public static String GetGMT(){
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String datetime = sdf.format(cd.getTime());
        Log.i(TAG, "GetGMT: "+datetime);
        return datetime;
    }
}
