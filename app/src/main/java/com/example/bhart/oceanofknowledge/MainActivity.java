package com.example.bhart.oceanofknowledge;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<module> list;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        list=new ArrayList<>();
        mAdapter = new ModuleAdapter(this,list);
        recyclerView.setAdapter(mAdapter);

        //Initialising dialog box
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data....");
        progressDialog.show();

        module_lang_search(0);
        mAdapter.notifyDataSetChanged();

        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,
                R.array.lang_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                module_lang_search(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                module_lang_search(0);
                mAdapter.notifyDataSetChanged();

            }
        });

        final EditText editText=(EditText)findViewById(R.id.editext);
        TextView textView=(TextView)findViewById(R.id.search);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=editText.getText().toString();
                if (!s.isEmpty()){
                    module_search(s);
                }
                else{
                    Toast.makeText(MainActivity.this,"Enter any module to search",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public void module_search(final String s){
        String url="http://oer2go.org/cgi/json_api_v2.pl";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{
                    //JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonArray = new JSONObject(response);

                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);

                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);

                            String module_language=object.getString("lang");
                            String real_name="";

                            if (module_language.equals("en")){
                                real_name="English";
                            }
                            else if(module_language.equals("es")){
                                real_name = "Spanish";
                            }
                            else if(module_language.equals("pt")){
                                real_name = "Portuguese";
                            }
                            else if(module_language.equals("hi")){
                                real_name = "Hindi";
                            }
                            else if(module_language.equals("fr")){
                                real_name = "French";
                            }
                            else if(module_language.equals("de")){
                                real_name = "German";
                            }
                            else if(module_language.equals("multi")){
                                real_name = "Multilingual";
                            }
                            else if(module_language.equals("ar")){
                                real_name = "Arabic";
                            }
                            else if(module_language.equals("kn")){
                                real_name = "Kannada";
                            }
                            else if(module_language.equals("id")){
                                real_name = "Indonesian";
                            }

                            if (object.getString("title").contains(s)){
                                module item = new module(object.getString("title"),real_name
                                        ,object.getString("logofilename"),object.getString("moddir"),size);
                                list.add(item);
                                mAdapter.notifyDataSetChanged();
                            }

                        }

                }catch(JSONException e){
                    //progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void module_lang_search(final int position){
        String url="http://oer2go.org/cgi/json_api_v2.pl";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{
                    JSONObject jsonArray = new JSONObject(response);

                    //Log.e("inside try block","reached");


                    if (position==0){
                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);

                            String module_language=object.getString("lang");
                            String real_name="";

                            if (module_language.equals("en")){
                                real_name="English";
                            }
                            else if(module_language.equals("es")){
                                real_name = "Spanish";
                            }
                            else if(module_language.equals("pt")){
                                real_name = "Portuguese";
                            }
                            else if(module_language.equals("hi")){
                                real_name = "Hindi";
                            }
                            else if(module_language.equals("fr")){
                                real_name = "French";
                            }
                            else if(module_language.equals("de")){
                                real_name = "German";
                            }
                            else if(module_language.equals("multi")){
                                real_name = "Multilingual";
                            }
                            else if(module_language.equals("ar")){
                                real_name = "Arabic";
                            }
                            else if(module_language.equals("kn")){
                                real_name = "Kannada";
                            }
                            else if(module_language.equals("id")){
                                real_name = "Indonesian";
                            }

                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);
                            module item = new module(object.getString("title"),real_name
                                    ,object.getString("logofilename"),object.getString("moddir"),size);
                            list.add(item);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    else if (position==1){
                         list.clear();
                         Iterator<String> keys = jsonArray.keys();

                         while (keys.hasNext())
                         {
                             // Get the key
                             String key = keys.next();

                             // Get the value
                             JSONObject object = jsonArray.getJSONObject(key);

                             String size=object.getString("ksize");
                             float kilobit= Float.valueOf(size);
                             kilobit=kilobit/1000000;
                             size=String.valueOf(kilobit);

                             if (object.getString("lang").equals("en")){
                                 module item = new module(object.getString("title"),"English"
                                 ,object.getString("logofilename"),object.getString("moddir"),size);
                                 list.add(item);
                                 mAdapter.notifyDataSetChanged();
                             }
                         }
                    }


                    else if (position==2){
                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);

                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);

                            String language= object.getString("lang");
                            if (language.equals("es")){
                                module item = new module(object.getString("title"),"Spanish"
                                        ,object.getString("logofilename"),object.getString("moddir"),size);
                                list.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    else if (position==3){
                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);

                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);

                            String language= object.getString("lang");
                            if (language.equals("pt")){
                                module item = new module(object.getString("title"),"Portuguese"
                                        ,object.getString("logofilename"),object.getString("moddir"),size);
                                list.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    else if (position==4){
                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);
                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);

                            String language= object.getString("lang");
                            if (language.equals("hi")){
                                module item = new module(object.getString("title"),"Hindi"
                                        ,object.getString("logofilename"),object.getString("moddir"),size);
                                list.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    else if (position==5){
                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);
                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);

                            String language= object.getString("lang");
                            if (language.equals("fr")){
                                module item = new module(object.getString("title"),"French"
                                        ,object.getString("logofilename"),object.getString("moddir"),size);
                                list.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    else if (position==6){
                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);

                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);
                            String language= object.getString("lang");
                            if (language.equals("de")){
                                module item = new module(object.getString("title"),"German"
                                        ,object.getString("logofilename"),object.getString("moddir"),size);
                                list.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    else if (position==7){
                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);
                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);

                            String language= object.getString("lang");
                            if (language.equals("ar")){
                                module item = new module(object.getString("title"),"Arabic"
                                        ,object.getString("logofilename"),object.getString("moddir"),size);
                                list.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    else if (position==8){
                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);
                            String language= object.getString("lang");
                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);

                            if (language.equals("kn")){
                                module item = new module(object.getString("title"),"Kannada"
                                        ,object.getString("logofilename"),object.getString("moddir"),size);
                                list.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    else if (position==9){
                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);
                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);

                            String language= object.getString("lang");
                            if (language.equals("id")){
                                module item = new module(object.getString("title"),"Indonesian"
                                        ,object.getString("logofilename"),object.getString("moddir"),size);
                                list.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    else if (position==10){
                        list.clear();
                        Iterator<String> keys = jsonArray.keys();

                        while (keys.hasNext())
                        {
                            // Get the key
                            String key = keys.next();

                            // Get the value
                            JSONObject object = jsonArray.getJSONObject(key);

                            String size=object.getString("ksize");
                            float kilobit= Float.valueOf(size);
                            kilobit=kilobit/1000000;
                            size=String.valueOf(kilobit);
                            String language= object.getString("lang");
                            if (language.equals("multi")){
                                module item = new module(object.getString("title"),"Multilingual"
                                        ,object.getString("logofilename"),object.getString("moddir"),size);
                                list.add(item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }



                }catch(JSONException e){
                    //progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
