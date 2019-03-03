package com.example.a85161.weibo;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegisterHttpUtil {

    private static final String TAG = "FullTask";
    private static final String SUCCESS = "success";
    private Boolean b;

    private static byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ":with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();

            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public static String getStringwithoutParams(String baseUrl) throws IOException {
        return new String(getUrlBytes(baseUrl));
    }

    public static byte[] getBytes(String baseUrl, Map<String, String> parameter) throws IOException {
        Uri.Builder uri = Uri.parse(baseUrl).buildUpon();
        for (Map.Entry<String, String> entry : parameter.entrySet()) {
            uri.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        String url = uri.build().toString();

        return getUrlBytes(url);
    }

    public static String getString(String baseUrl, Map<String, String> parameter) throws IOException {
        return new String(getBytes(baseUrl, parameter));
    }

    public static String getResult(String url, HashMap<String, String> parameter, String select) {
//        HashMap<String, String> parameter = new HashMap<>();
//        parameter.put("name", name);
//        parameter.put("password", password);
        try {
            url = url + select;
            String result = getString(url, parameter);
            return result;
        } catch (IOException ex) {
            Log.e(TAG, ex.toString(), ex);
        }
        return "fail";
    }

    public static String getResult1(String url, HashMap<String, String> parameter) {
//        HashMap<String, String> parameter = new HashMap<>();
//        parameter.put("name", name);
//        parameter.put("password", password);
        try {
            url = url;
            String result = getString(url, parameter);
            return result;
        } catch (IOException ex) {
            Log.e(TAG, ex.toString(), ex);
        }
        return "fail";
    }
}
