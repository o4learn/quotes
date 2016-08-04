package com.example.rohitmadhu.myapplication;



import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * Created by Kiran Anto aka RazorSharp on 1/26/2016.
 * For more Info Contact
 * Kirananto@gmail.com
 * 9495333724
 * All Copyrights Reserved 2016
 *
 */
class CustomJsonRequestMain extends Request {

    private final Map<String, String> params;
    private final Response.Listener listener;

    public CustomJsonRequestMain(String url, Map<String, String> params,
                                 Response.Listener responseListener, Response.ErrorListener errorListener) {

        super(Method.GET, url, errorListener);


        params = new HashMap<>();
        params.put("Content-Type", "application/json; charset=utf-8");
        params.put("User-agent", "My useragent");
        this.params = params;
        this.listener = responseListener;
    }

    @Override
    protected void deliverResponse(Object response) {
        listener.onResponse(response);

    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {

        return params;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
