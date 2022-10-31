package com.example.officeutils.ui;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.example.officeutils.bean.FileNameAndBase64;
import com.example.officeutils.bean.ResultBean;
import com.example.officeutils.utils.FileToBase64;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Java_Windows {

    private static final String TAG = "Java_Windows";

    public static String calcAuthorization(String source, String secretId, String secretKey, String datetime)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));
        String sig =  new Base64Encoder().encode(hash);
        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
        return auth;
    }

    public static String urlencode(Map<?, ?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static FileNameAndBase64 mains(String file, Context context) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        //云市场分配的密钥Id
        String secretId = "AKID7KgduqScdf0kqc75jqn98p1Si0hkdyp73p7G";
        //云市场分配的密钥Key
        String secretKey = "lq8P1959iVGX9C2zo33kIG5VoO01P5jht7hO2ai";

        String source = "market";
        String result = "";
        FileNameAndBase64 fileNameAndBase64 = null;

        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String datetime = sdf.format(cd.getTime());
        // 签名
        String auth = calcAuthorization(source, secretId, secretKey, datetime);
        // 请求方法
        String method = "POST";
        // 请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Source", source);
        headers.put("X-Date", datetime);
        headers.put("Authorization", auth);

        if (file == null){
            return null;
        }
        FileInputStream fis = new FileInputStream(file);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        for (int len = 0; (len = fis.read(buf)) != -1; ) {
            bos.write(buf, 0, len);
        }
        bos.flush();
        bos.close();
        fis.close();
        byte[] encode = Base64.getEncoder().encode(bos.toByteArray());
        String str = new String(encode);
        String he = "application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,";// docx
        // 查询参数
        Map<String, String> queryParams = new HashMap<String, String>();
        // body参数
        Map<String, String> bodyParams = new HashMap<String, String>();

        bodyParams.put("body","{\"tasks\":{\"JsonRootBean\":{\"input\":[\"ImportFile\"],\"output_format\":\"pdf\",\"operation\":\"convert\"},\"ImportFile\":{\"url\":\""+he+str+"\",\"operation\":\"import/url\"},\"ExportResult\":{\"input\":[\"JsonRootBean\"],\"operation\":\"export/url\"}},\"webHook\":\"\",\"tag\":\"\"}");
        // url参数拼接
        String url = "https://service-1odt5k7b-1255058406.sh.apigw.tencentcs.com/release/v2/jobs"; //word post


        if (!queryParams.isEmpty()) {
            url += "?" + urlencode(queryParams);
        }

        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(200000);
            conn.setReadTimeout(200000);
            conn.setRequestMethod(method);

            // request headers
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            // request body
            Map<String, Boolean> methods = new HashMap<>();
            methods.put("POST", true);
            methods.put("PUT", true);
            methods.put("PATCH", true);
            Boolean hasBody = methods.get(method);
            if (hasBody != null) {
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(urlencode(bodyParams));
                out.flush();
                out.close();
            }

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;




            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
            JSONObject jsonObject = new JSONObject(result);
            JSONObject now = jsonObject.optJSONObject("data");
            assert now != null;


            JSONArray tasks = now.optJSONArray("tasks");
            JSONObject the = (JSONObject) tasks.get(0);
            JSONArray thethe = the.optJSONArray("urlArray");

            Log.e("base64,====",thethe.toString());
            String filesName = file.substring(42,file.length()-5);
            String sb = thethe.toString().substring(81);
             fileNameAndBase64 = new FileNameAndBase64(sb,filesName);
            Log.i(TAG, "mains: 原生请求成功"+fileNameAndBase64);

        } catch (Exception e) {
            System.out.println(e);
            Log.e(TAG, "mains: 原生请求失败"+e );
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return fileNameAndBase64;
    }


    public static class Base64Encoder {
        private static final char last2byte = (char) Integer
                .parseInt("00000011", 2);
        private static final char last4byte = (char) Integer
                .parseInt("00001111", 2);
        private static final char last6byte = (char) Integer
                .parseInt("00111111", 2);
        private static final char lead6byte = (char) Integer
                .parseInt("11111100", 2);
        private static final char lead4byte = (char) Integer
                .parseInt("11110000", 2);
        private static final char lead2byte = (char) Integer
                .parseInt("11000000", 2);
        private static final char[] encodeTable = new char[] { 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
                '4', '5', '6', '7', '8', '9', '+', '/' };

        /**
         * Base64 encoding.
         *
         * @param from
         *            The src data.
         * @return
         */
        public static String encode(byte[] from) {
            StringBuffer to = new StringBuffer((int) (from.length * 1.34) + 3);
            int num = 0;
            char currentByte = 0;
            for (int i = 0; i < from.length; i++) {
                num = num % 8;
                while (num < 8) {
                    switch (num) {
                        case 0:
                            currentByte = (char) (from[i] & lead6byte);
                            currentByte = (char) (currentByte >>> 2);
                            break;
                        case 2:
                            currentByte = (char) (from[i] & last6byte);
                            break;
                        case 4:
                            currentByte = (char) (from[i] & last4byte);
                            currentByte = (char) (currentByte << 2);
                            if ((i + 1) < from.length) {
                                currentByte |= (from[i + 1] & lead2byte) >>> 6;
                            }
                            break;
                        case 6:
                            currentByte = (char) (from[i] & last2byte);
                            currentByte = (char) (currentByte << 4);
                            if ((i + 1) < from.length) {
                                currentByte |= (from[i + 1] & lead4byte) >>> 4;
                            }
                            break;
                    }
                    to.append(encodeTable[currentByte]);
                    num += 6;
                }
            }
            if (to.length() % 4 != 0) {
                for (int i = 4 - to.length() % 4; i > 0; i--) {
                    to.append("=");
                }
            }
            return to.toString();
        }
    }
}
