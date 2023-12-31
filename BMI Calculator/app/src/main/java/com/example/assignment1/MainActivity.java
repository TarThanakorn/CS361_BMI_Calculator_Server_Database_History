package com.example.assignment1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DecimalFormat formatter = new DecimalFormat("#,###.##");
        EditText edit_text1 = findViewById(R.id.editText1);
        EditText edit_text2 = findViewById(R.id.editText2);
        TextView show_bmi = findViewById(R.id.show_bmi);
        TextView show_status = findViewById(R.id.show_status);
        ImageView image = findViewById(R.id.imageView);

        image.setImageResource(R.drawable.ws0);
        edit_text1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(6, 2)});
        edit_text2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(6, 2)});

        final Button calculate = findViewById(R.id.button);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edit_text1.getText().toString().isEmpty() && !edit_text2.getText().toString().isEmpty() && Double.parseDouble(edit_text2.getText().toString()) > 0){
                    hideKeyboard();
                    Double bmiCal = Double.parseDouble(edit_text1.getText().toString())/(Double.parseDouble(edit_text2.getText().toString())/100 * Double.parseDouble(edit_text2.getText().toString())/100);
                    String bmiFormat = formatter.format(bmiCal);
                    String statusCal;
                    show_status.setTextColor(getResources().getColor(R.color.black));
                    if(bmiCal < 18.5){
                        statusCal = getString(R.string.ws1);
                        show_status.setBackgroundColor(getResources().getColor(R.color.ws1));
                        image.setImageResource(R.drawable.ws1);
                    }else if(bmiCal < 23) {
                        statusCal = getString(R.string.ws2);
                        show_status.setBackgroundColor(getResources().getColor(R.color.ws2));
                        image.setImageResource(R.drawable.ws2);
                    }else if(bmiCal < 25) {
                        statusCal = getString(R.string.ws3);
                        show_status.setBackgroundColor(getResources().getColor(R.color.ws3));
                        image.setImageResource(R.drawable.ws3);
                    }else if(bmiCal < 30) {
                        statusCal = getString(R.string.ws4);
                        show_status.setBackgroundColor(getResources().getColor(R.color.ws4));
                        image.setImageResource(R.drawable.ws4);
                    }else if(bmiCal >= 30) {
                        statusCal = getString(R.string.ws5);
                        show_status.setBackgroundColor(getResources().getColor(R.color.ws5));
                        image.setImageResource(R.drawable.ws5);
                    }else{
                        statusCal = getString(R.string.error);
                    }
                    show_bmi.setText(bmiFormat);
                    show_status.setText(statusCal);
                    if(saveData()){
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            final EditText txtMsg = (EditText) findViewById(R.id.editText2);
                            txtMsg.requestFocus();
                        }
                    }
                }
            }
        });

        edit_text1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN) {
                    if (i == KeyEvent.KEYCODE_ENTER) {
                        if (!edit_text1.getText().toString().isEmpty() && !edit_text2.getText().toString().isEmpty() && Double.parseDouble(edit_text2.getText().toString()) > 0) {
                            hideKeyboard();
                            Double bmiCal = Double.parseDouble(edit_text1.getText().toString()) / (Double.parseDouble(edit_text2.getText().toString()) / 100 * Double.parseDouble(edit_text2.getText().toString()) / 100);
                            String bmiFormat = formatter.format(bmiCal);
                            String statusCal;
                            show_status.setTextColor(getResources().getColor(R.color.black));
                            if (bmiCal < 18.5) {
                                statusCal = getString(R.string.ws1);
                                show_status.setBackgroundColor(getResources().getColor(R.color.ws1));
                                image.setImageResource(R.drawable.ws1);
                            } else if (bmiCal < 23) {
                                statusCal = getString(R.string.ws2);
                                show_status.setBackgroundColor(getResources().getColor(R.color.ws2));
                                image.setImageResource(R.drawable.ws2);
                            } else if (bmiCal < 25) {
                                statusCal = getString(R.string.ws3);
                                show_status.setBackgroundColor(getResources().getColor(R.color.ws3));
                                image.setImageResource(R.drawable.ws3);
                            } else if (bmiCal < 30) {
                                statusCal = getString(R.string.ws4);
                                show_status.setBackgroundColor(getResources().getColor(R.color.ws4));
                                image.setImageResource(R.drawable.ws4);
                            } else if (bmiCal >= 30) {
                                statusCal = getString(R.string.ws5);
                                show_status.setBackgroundColor(getResources().getColor(R.color.ws5));
                                image.setImageResource(R.drawable.ws5);
                            } else {
                                statusCal = getString(R.string.error);
                            }
                            show_bmi.setText(bmiFormat);
                            show_status.setText(statusCal);
                            if(saveData()){
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager)
                                            getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                    final EditText txtMsg = (EditText) findViewById(R.id.editText2);
                                    txtMsg.requestFocus();
                                }
                            }
                        }
                    }
                }
                return false;
            }
        });

        edit_text2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN) {
                    if (i == KeyEvent.KEYCODE_ENTER) {
                        if (!edit_text1.getText().toString().isEmpty() && !edit_text2.getText().toString().isEmpty() && Double.parseDouble(edit_text2.getText().toString()) > 0) {
                            hideKeyboard();
                            Double bmiCal = Double.parseDouble(edit_text1.getText().toString()) / (Double.parseDouble(edit_text2.getText().toString()) / 100 * Double.parseDouble(edit_text2.getText().toString()) / 100);
                            String bmiFormat = formatter.format(bmiCal);
                            String statusCal;
                            show_status.setTextColor(getResources().getColor(R.color.black));
                            if (bmiCal < 18.5) {
                                statusCal = getString(R.string.ws1);
                                show_status.setBackgroundColor(getResources().getColor(R.color.ws1));
                                image.setImageResource(R.drawable.ws1);
                            } else if (bmiCal < 23) {
                                statusCal = getString(R.string.ws2);
                                show_status.setBackgroundColor(getResources().getColor(R.color.ws2));
                                image.setImageResource(R.drawable.ws2);
                            } else if (bmiCal < 25) {
                                statusCal = getString(R.string.ws3);
                                show_status.setBackgroundColor(getResources().getColor(R.color.ws3));
                                image.setImageResource(R.drawable.ws3);
                            } else if (bmiCal < 30) {
                                statusCal = getString(R.string.ws4);
                                show_status.setBackgroundColor(getResources().getColor(R.color.ws4));
                                image.setImageResource(R.drawable.ws4);
                            } else if (bmiCal >= 30) {
                                statusCal = getString(R.string.ws5);
                                show_status.setBackgroundColor(getResources().getColor(R.color.ws5));
                                image.setImageResource(R.drawable.ws5);
                            } else {
                                statusCal = getString(R.string.error);
                            }
                            show_bmi.setText(bmiFormat);
                            show_status.setText(statusCal);
                            System.out.println("pass");
                            if(saveData()){
                                System.out.println("pass2");
                                if (view != null) {
                                    System.out.println("pass3");
                                    InputMethodManager imm = (InputMethodManager)
                                            getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                    final EditText txtMsg = (EditText) findViewById(R.id.editText2);
                                    txtMsg.requestFocus();
                                }
                            }
                        }
                    }
                }
                return false;
            }
        });
    }

    public boolean saveData(){
        final EditText txtWeight = (EditText)findViewById(R.id.editText1);
        final EditText txtHeight = (EditText)findViewById(R.id.editText2);
        final TextView txtBMI = (TextView)findViewById(R.id.show_bmi);
        final TextView txtStatus = (TextView)findViewById(R.id.show_status);
        final ImageView image = (ImageView)findViewById(R.id.imageView);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setPositiveButton("Close", null);
        String url = "http://10.0.2.2:80/uploadData.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(String response) {
                Log.i("DbVolley", response);
                try {
                    String strStatusID = "0";
                    String strError = "Unknown Status!";
                    JSONObject c;

                    JSONArray data = new JSONArray("["+response.toString()+"]");
                    for(int i = 0; i < data.length(); i++){
                        System.out.println(data);
                        c = data.getJSONObject(i);
                        strStatusID = c.getString("StatusID");
                        strError = c.getString("Error");
                    }
                    if(strStatusID.equals("0")){
                        dialog.setMessage(strError);
                        dialog.show();
                    } else {
                        dialog.setTitle(R.string.submit_title);
                        dialog.setMessage(R.string.submit_result);
                        dialog.show();
                        txtWeight.setText("");
                        txtHeight.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Submission Error!" ,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("DbVolley", "Volley::onErrorResponse():"+error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("sWeight",txtWeight.getText().toString());
                params.put("sHeight",txtHeight.getText().toString());
                params.put("sBMI",txtBMI.getText().toString());
                params.put("sStatus",txtStatus.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final TextView title = findViewById(R.id.title);
        final TextView title2 = findViewById(R.id.title2);
        final TextView weight = findViewById(R.id.weight);
        final TextView height = findViewById(R.id.height);
        final TextView bmi = findViewById(R.id.bmi);
        final TextView status = findViewById(R.id.status);
        final EditText edit_text1 = findViewById(R.id.editText1);
        final EditText edit_text2 = findViewById(R.id.editText2);
        final TextView show_bmi = findViewById(R.id.show_bmi);
        final TextView show_status = findViewById(R.id.show_status);
        final Button button = findViewById(R.id.button);

        title.setTextSize(newConfig.fontScale*16);
        title2.setTextSize(newConfig.fontScale*16);
        weight.setTextSize(newConfig.fontScale*12);
        height.setTextSize(newConfig.fontScale*12);
        bmi.setTextSize(newConfig.fontScale*12);
        status.setTextSize(newConfig.fontScale*12);
        edit_text1.setTextSize(newConfig.fontScale*12);
        edit_text2.setTextSize(newConfig.fontScale*12);
        show_bmi.setTextSize(newConfig.fontScale*12);
        show_status.setTextSize(newConfig.fontScale*12);
        button.setTextSize(newConfig.fontScale*12);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.view_record) {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
    private void hideKeyboard(){
        View view = getCurrentFocus(); // returns the view that has focus or null
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

class DecimalDigitsInputFilter implements InputFilter {
    private final Pattern mPattern;
    DecimalDigitsInputFilter(int digits, int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digits - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) +
                "})?)||(\\.)?");
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches())
            return "";
        return null;
    }
}