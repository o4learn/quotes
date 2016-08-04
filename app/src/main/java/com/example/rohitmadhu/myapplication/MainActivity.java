package com.example.rohitmadhu.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * Created by Kiran Anto aka RazorSharp on 1/26/2016.
 * For more Info Contact
 * Kirananto@gmail.com
 * 9495333724
 * All Copyrights Reserved 2016
 *
 */

public class MainActivity extends AppCompatActivity{


    private List<gallery> listGallery;

    private ProgressDialog pDialog;
    private static final String TAG_IMAGE_URL = "url";
    private static final String TAG_TITLE = "title";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        listGallery = new ArrayList<>();
        new fetch().execute();
    }

    public class fetch {
        void execute(){
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setTitle(getString(R.string.loading));
            pDialog.setMessage(getString(R.string.loading));
            pDialog.setCancelable(false);
            pDialog.show();
            String IMG_URL = "https://raw.githubusercontent.com/o4learn/quotes/master/rohit.json";
            Volley.newRequestQueue(MainActivity.this).
                    add(new CustomJsonRequestMain(IMG_URL, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse (JSONArray response){
                                        hidePDialog();

                                        // Parsing json
                                        for (int i = 0; i < response.length(); i++) {
                                            gallery gallerypic = new gallery();
                                            try {
                                                JSONObject obj = response.getJSONObject(i);
                                                gallerypic.setImageUrl(obj.getString(TAG_IMAGE_URL));
                                                gallerypic.setName(" " + obj.getString(TAG_TITLE));
                                                Log.i("GALLERY :","\nIMGURL :"+ gallerypic.getImageUrl() +"\n TITLE : " + gallerypic.getName());
                                                //
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            listGallery.add(gallerypic);

                                        }
                                        mAdapter = new gallerycardadapter(listGallery, getApplicationContext());

                                        //Adding adapter to recyclerview
                                        mRecyclerView.setAdapter(mAdapter);

                                    }
                                }

                                ,new Response.ErrorListener()

                        {
                            @Override
                            public void onErrorResponse (VolleyError error){
                                VolleyLog.d("Error: " + error.getMessage());
                                //error.printStackTrace();
                                hidePDialog();
                            }
                        }

                        )

                        {


                            @Override
                            protected Response<JSONArray> parseNetworkResponse (
                                    NetworkResponse response){
                                try {
                                    String jsonString = new String(response.data,
                                            HttpHeaderParser
                                                    .parseCharset(response.headers));
                                    return Response.success(new JSONArray(jsonString),
                                            HttpHeaderParser
                                                    .parseCacheHeaders(response));
                                } catch (UnsupportedEncodingException e) {
                                    return Response.error(new ParseError(e));
                                } catch (JSONException je) {
                                    return Response.error(new ParseError(je));
                                }
                            }
                        }

                    );
        }

    }

    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


}