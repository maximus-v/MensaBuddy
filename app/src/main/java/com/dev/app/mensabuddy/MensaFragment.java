package com.dev.app.mensabuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Max on 08.12.2016.
 */

public class MensaFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_CONTENT = "ARG_CONTENT";

    private int mPage;
    private String mContent;

    private String fragmentText = "Text";
    private Context context;
    private ProgressDialog progressDialog;
    private String jsonResponse;
    private static String TAG = StartActivity.class.getSimpleName();
    private String urlJsonObject;

    public static MensaFragment newInstance(int page, String content) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_CONTENT, content);
        MensaFragment fragment = new MensaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        mContent = getArguments().getString(ARG_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mensa_fragment_page, container, false);
        TextView textView = (TextView) view;
        //textView.setText(mContent);
        textView.setText(makeJsonObjectRequest(mContent));
        return view;
    }


    //----------------------------------------------------------------------------------------------
    private String makeJsonObjectRequest(String urlJsonObject) {

        /*progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Laden ... ");
        progressDialog.setCancelable(false);

        showProgessDialog();*/

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJsonObject, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String erstesMenue = response.getString("erstesMenue");
                    String zweitesMenue = response.getString("zweitesMenue");
                    String drittesMenue = response.getString("drittesMenue");


                    jsonResponse = erstesMenue + "\n\n";
                    jsonResponse += zweitesMenue + "\n\n";
                    jsonResponse += drittesMenue + "\n\n";
                } catch (JSONException e) {
                    e.printStackTrace();
                    jsonResponse = e.getMessage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonResponse = error.getMessage();
            }
        });
        //hideProgressDialog();
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        return jsonResponse;
    }

    private void showProgessDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
