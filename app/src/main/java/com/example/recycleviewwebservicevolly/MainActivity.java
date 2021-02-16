package com.example.recycleviewwebservicevolly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
   EditText userid,name,sort,status,title,description,keyword,date;
   ImageView imageView;
   String imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      userid=findViewById(R.id.UserId);
      name=findViewById(R.id.Name);
      sort=findViewById(R.id.Sort);
      status=findViewById(R.id.Status);
      title=findViewById(R.id.Title);
      description=findViewById(R.id.Description);
      keyword=findViewById(R.id.Keyword);
      date=findViewById(R.id.Datee);
      imageView=findViewById(R.id.Imageview);


    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://192.168.100.65:8080/Course/rest/service/fetch", new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);

                    Log.d("data","=============================================="+jsonObject.getString("id"));
                    Log.d("data","=============================================="+jsonObject.getString("name"));
                    Log.d("data","=============================================="+jsonObject.getString("sort"));
                    Log.d("data","=============================================="+jsonObject.getString("status"));
                    Log.d("data","=============================================="+jsonObject.getString("title"));
                    Log.d("data","=============================================="+jsonObject.getString("description"));
                    Log.d("data","=============================================="+jsonObject.getString("keyword"));
                    Log.d("data","=============================================="+jsonObject.getString("url"));
                   Log.d("data","=============================================="+jsonObject.getString("date"));

              userid.setText(jsonObject.getString("id"));
              name.setText(jsonObject.getString("name"));
              sort.setText(jsonObject.getString("sort"));
              status.setText(jsonObject.getString("status"));
              title.setText(jsonObject.getString("title"));
              description.setText(jsonObject.getString("description"));
              keyword.setText(jsonObject.getString("keyword"));
             date.setText(jsonObject.getString("date"));
              imageurl=jsonObject.getString("url");
             Log.d("image url","======================================"+imageurl);

                    Glide.with(MainActivity.this)
//                            .load("https://rukminim1.flixcart.com/image/880/1056/k66sh3k0/shoe/e/j/f/5510123-6-bonexy-black-original-imafny9bzhubzbat.jpeg?q=50")
                            .load("http://192.168.100.65:8080/Course/"+imageurl)
                            .centerCrop()
                            .into(imageView);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

        }
    },new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(MainActivity.this, "volly error"+error, Toast.LENGTH_SHORT).show();
            Log.d("msg","==================================="+error);

        }
    });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
}

}