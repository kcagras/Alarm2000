package com.alarmcompany.alarm2000_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


/**
 * Created by Kawa on 24.03.2016.
 */
public class LiveActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = LiveActivity.class.getSimpleName();
    WebView liveWebView;
    SharedPreferences sharedPreferences;
    static final String SHARED_PREF = "SHARED_PREF";
    static final String SHARED_PREF_ENABLEBTN = "SHARED_PREF_ENABLEBTN";
    Button enableBtn;
    Boolean alarmEnabled;
    Context mContext;
    String webServerUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        webServerUrl = "http://" +sharedPreferences.getString("stg_key_ip", "0.0.0.0") + ":"+sharedPreferences.getInt("stg_key_port", 8080);
        Log.d(TAG, "webServerUrl: "+webServerUrl);
        enableBtn = (Button) findViewById(R.id.enablebtn);
        if (sharedPreferences.getBoolean(SHARED_PREF_ENABLEBTN, false) == false) {
            alarmEnabled = false;
            enableBtn.setText("Enable Alarm");
        }
        else {
            alarmEnabled = true;
            enableBtn.setText("Disable Alarm");
        }
        enableBtn.setOnClickListener(this);
        String frameVideo = "<html><body><iframe width=\"350\" height=\"206\" src=\"https://www.youtube.com/embed/sFukyIIM1XI\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        liveWebView = (WebView) findViewById(R.id.livecam_webview);
        liveWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = liveWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        liveWebView.loadData(frameVideo, "text/html", "utf-8");

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.enablebtn) {

            try {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String URL = "http://192.168.189.32:8080";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("Title", "Android Volley Demo");
                jsonBody.put("Author", "BNK");
                final String mRequestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                    mRequestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                requestQueue.add(stringRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}