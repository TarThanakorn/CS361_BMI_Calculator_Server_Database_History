package com.example.assignment1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {
    DecimalFormat formatter = new DecimalFormat("#,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final ListView listView = (ListView)findViewById(R.id.listView);
        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
        String url = "http://10.0.2.2:80/getData.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            HashMap<String, String> map;
                            JSONArray data = new JSONArray(response);
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject c = data.getJSONObject(i);
                                map = new HashMap<String, String>();
                                map.put("id", c.getString("id"));
                                map.put("date", changeDateFormat(c.getString("date")));
                                map.put("weight", c.getString("weight"));
                                map.put("height", c.getString("height"));
                                map.put("bmi", c.getString("bmi"));
                                map.put("status", c.getString("status"));
                                MyArrList.add(map);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        SimpleAdapter simpleAdapter = new SimpleAdapter(ListActivity.this, MyArrList, R.layout.activity_column,
                                new String[]{"id", "date", "weight", "height", "bmi", "status"},
                                new int[]{R.id.col_id, R.id.col_date, R.id.col_weight, R.id.col_height, R.id.col_bmi, R.id.col_status});
                        listView.setAdapter(simpleAdapter);

                        final AlertDialog.Builder viewDetail = new AlertDialog.Builder(ListActivity.this);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> myAdapter, View myView,
                                                    int position, long id) {
                                String sID = MyArrList.get(position).get("id");
                                String sDate = MyArrList.get(position).get("date");
                                String sWeight = MyArrList.get(position).get("weight");
                                String sHeight = MyArrList.get(position).get("height");
                                String sBmi = MyArrList.get(position).get("bmi");
                                String sStatus = MyArrList.get(position).get("status");
                                viewDetail.setIcon(android.R.drawable.btn_star_big_on);
                                viewDetail.setTitle("Order");
                                viewDetail.setMessage("ID : " + sID + "\n"
                                        + "Date : " + sDate + "\n" + "Weight : " + sWeight + "\n"
                                        + "Height : " + sHeight + "\n"
                                        + "BMI : " + sBmi + "\n" + "Status : " + sStatus);
                                viewDetail.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                viewDetail.show();
                            }//end onItemClicked
                        });//end listView.setOnItemClickListener
                    }//end onResponse
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Log", "Volley::onErrorResponse():" + error.getMessage());
                    }//end onErrorResponse
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private String changeDateFormat (String date){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Parse the input string to Calendar object
            Calendar calendar = Calendar.getInstance();
            Date inputDate = inputFormat.parse(date);
            calendar.setTime(inputDate);

            // Add 543 to the year
            int originalYear = calendar.get(Calendar.YEAR);
            int newYear = originalYear + 543;
            calendar.set(Calendar.YEAR, newYear);

            // Format the modified Calendar object to the desired output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            String outputDateString = outputFormat.format(calendar.getTime());

            // Print the result
            System.out.println("Input Date: " + date);
            System.out.println("Modified Date: " + outputDateString);

            return outputDateString;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date);
    }
}