package com.mycompany.myfirstapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ApiCaller {
    public String url;
    public Map<String, String> parameters;
    public final static Integer CONNECTION_TIMEOUT = 10000;
    public final static Integer DATARETRIEVAL_TIMEOUT = 10000;
    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';

    public ApiCaller(String myUrl) {
        url = myUrl;
        parameters = new HashMap<String, String>();
    }

    public ApiCaller(String myUrl, HashMap<String, String> myParameters) {
        url = myUrl;
        parameters = myParameters;
    }

    public JSONObject makePostRequest() {
        HttpURLConnection urlConnection = null;
        String postParameterString = createQueryStringForParameters(parameters);

        try {
            URL urlToRequest = new URL(url);
            urlConnection = (HttpURLConnection)urlToRequest.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);

            urlConnection.setFixedLengthStreamingMode(
                    postParameterString.getBytes().length);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(postParameterString);
            out.close();


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

    public JSONObject makeGetRequest() {
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

    public static String createQueryStringForParameters(Map<String, String> parameters) {
        StringBuilder parametersAsQueryString = new StringBuilder();

        try {
            if (parameters != null) {
                boolean firstParameter = true;

                for (String parameterName : parameters.keySet()) {
                    if (!firstParameter) {
                        parametersAsQueryString.append(PARAMETER_DELIMITER);
                    }

                    parametersAsQueryString.append(parameterName)
                            .append(PARAMETER_EQUALS_CHAR)
                            .append(URLEncoder.encode(
                                    parameters.get(parameterName), "UTF-8"));

                    firstParameter = false;
                }
            }
        } catch (UnsupportedEncodingException e) {
        }

        return parametersAsQueryString.toString();
    }

    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }
}
