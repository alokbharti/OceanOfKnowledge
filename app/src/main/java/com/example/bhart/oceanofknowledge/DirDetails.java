package com.example.bhart.oceanofknowledge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DirDetails extends AppCompatActivity {

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dir_detail);
        setTitle("Details of modules");

        String moddirect=getIntent().getStringExtra("directory");

        Log.e("Url",moddirect);
        String url="http://oer2go.org/cgi/json_api_v2.pl?moddir="+moddirect;



        final TextView title=(TextView)findViewById(R.id.dir_title);
        final TextView desc=(TextView)findViewById(R.id.desc);
        final TextView sourceUrl=(TextView)findViewById(R.id.sourceUrl);
        final TextView download = (TextView)findViewById(R.id.download);
        final TextView s=(TextView)findViewById(R.id.s);
        final TextView d=(TextView)findViewById(R.id.d);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data....");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{

                    JSONObject jsonObject=new JSONObject(response);
                    title.setText(jsonObject.getString("title"));
                    desc.setText(jsonObject.getString("description"));
                    s.setText("Source Link");
                    sourceUrl.setText(jsonObject.getString("source_url"));
                    d.setText("Download Link");
                    download.setText(jsonObject.getString("kiwix_url"));


                }catch (JSONException e){
                    Toast.makeText(DirDetails.this,"Failed to load, try again",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        sourceUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl.getText().toString())));
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(download.getText().toString())));
            }
        });
    }
}
