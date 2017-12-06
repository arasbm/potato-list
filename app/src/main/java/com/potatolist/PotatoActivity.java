package com.potatolist;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class PotatoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potato);

        final PotatoAdapter myAdapter = new PotatoAdapter(this);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(myAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(PotatoActivity.this, "" + position + " " + myAdapter.potatoInfo.get(position).get("name"),
                        Toast.LENGTH_SHORT).show();
            }
        });

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.optJSONArray("potatoes");
            HashMap<String, String> p;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String p_name = jo_inside.getString("name");
                String p_description = jo_inside.getString("description");
                String p_icon = jo_inside.getString("icon");

                Log.d("name-->", jo_inside.getString("name"));
                Log.d("icon-->", jo_inside.getString("icon"));

                //Add your values in your `ArrayList` as below:
                p = new HashMap<String, String>();
                p.put("name", p_name);
                p.put("description", p_description);

                myAdapter.potatoInfo.add(p);

                Resources resources = getResources();
                int pIconId = resources.getIdentifier(p_icon, "drawable", getPackageName());
                myAdapter.potatoIcons.add(pIconId);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("potato.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
