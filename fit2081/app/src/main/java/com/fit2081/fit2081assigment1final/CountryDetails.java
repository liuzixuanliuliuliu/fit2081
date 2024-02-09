package com.fit2081.fit2081assigment1final;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class CountryDetails extends AppCompatActivity {

    private TextView nameTV;
    private TextView capitalTV;
    private TextView codeTV;
    private TextView populationTV;
    private TextView areaTV;

    private RequestQueue requestQueue;
    ExecutorService executor;
    Handler uiHandler;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        getSupportActionBar().setTitle(R.string.title_activity_country_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String selectedCountry = getIntent().getStringExtra("country");

        nameTV = findViewById(R.id.label_country_name);
        capitalTV = findViewById(R.id.value_capital);
        codeTV = findViewById(R.id.label_country_code);
        populationTV = findViewById(R.id.label_population);
        areaTV = findViewById(R.id.label_area);

        requestQueue = Volley.newRequestQueue(this);




        executor = Executors.newSingleThreadExecutor();
        //Executor handler = ContextCompat.getMainExecutor(this);
        uiHandler = new Handler(Looper.getMainLooper());
        jsonParse(selectedCountry);
    }


    private void jsonParse(String selectCountry) {
        String url = "https://restcountries.com/v2/name/" + selectCountry;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject countryObj = response.getJSONObject(0);
                        String name = countryObj.getString("name");
                        String capital = countryObj.getString("capital");
                        String code = countryObj.getString("alpha2Code");
                        int population = countryObj.getInt("population");
                        double area = countryObj.getDouble("area");

                        nameTV.setText(name);
                        capitalTV.setText(capital);
                        codeTV.setText(code);
                        populationTV.setText(String.format(Locale.getDefault(), "%,d", population));
                        areaTV.setText(String.format(Locale.getDefault(), "%,.2f km²", area));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CountryDetails.this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(CountryDetails.this, "Error retrieving data", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }

}
