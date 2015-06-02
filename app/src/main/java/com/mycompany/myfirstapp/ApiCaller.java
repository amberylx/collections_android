package com.mycompany.myfirstapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

public class ApiCaller {
    public String url;
    public final static Integer CONNECTION_TIMEOUT = 10000;
    public final static Integer DATARETRIEVAL_TIMEOUT = 10000;

    public ApiCaller(String myUrl) {
        url = myUrl;
    }

    public JSONObject callAPI() {
        HttpURLConnection urlConnection = null;

        try {
            URL urlToRequest = new URL(url);
            urlConnection = (HttpURLConnection)urlToRequest.openConnection();
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);

            int statusCode = urlConnection.getResponseCode();
            System.out.println(statusCode);
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
            }

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return new JSONObject(getResponseText(in));
        } catch (MalformedURLException e) {
            System.out.println(e);
        } catch (SocketTimeoutException e) {
            System.out.println(e);
        } catch (JSONException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }
}
