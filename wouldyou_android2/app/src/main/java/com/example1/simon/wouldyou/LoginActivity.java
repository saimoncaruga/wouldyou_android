package com.example1.simon.wouldyou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //String URL = "http://lalalaravel.test/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailInput = findViewById(R.id.editTextMail);
        final EditText passwordInput = findViewById(R.id.editTextPassword);
        final Button loginBtn = findViewById(R.id.buttonLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailInput.getText().toString();
                final String password = passwordInput.getText().toString();

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST, "http://dashboard.test/api/login", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("VOLLEY", "RESPONSE: " + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("VOLLEY", "JSONOBJECT: " + obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY", "ERROR: " + error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                /*StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s.equals("true")) {
                            Log.d("VOLLEY", "Response: " + s);
                        } else {
                            Log.d("VOLLEY", "Incorrect details");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY", "Error: " + error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("email", email);
                        parameters.put("password", password);
                        parameters.put("password_confirmation", confirmPassword);
                        return parameters;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(request);*/

                /*try {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String URL = "http://www.lalalaravel.test/api/login";
                    JSONObject jsonBody = new JSONObject();

                    jsonBody.put("email", email);
                    jsonBody.put("password", password);

                    Log.d("LoginActivity", jsonBody.toString());

                    JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("VOLLEY", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("VOLLEY", error.toString());
                        }

                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            final Map<String, String> headers = new HashMap<>();
                            headers.put("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjM4MmM1OTc5YzFhN2Y1ZDczMjFjMWQwMThhYzBmMTdkZWI1MzlkMjIxN2M2YWRhMDUxNzhlMTcyNmVmOWQwZDM3ZGY2ZTdlNDIxOGUxNmQ5In0.eyJhdWQiOiIxIiwianRpIjoiMzgyYzU5NzljMWE3ZjVkNzMyMWMxZDAxOGFjMGYxN2RlYjUzOWQyMjE3YzZhZGEwNTE3OGUxNzI2ZWY5ZDBkMzdkZjZlN2U0MjE4ZTE2ZDkiLCJpYXQiOjE1MjkyNTg2OTAsIm5iZiI6MTUyOTI1ODY5MCwiZXhwIjoxNTYwNzk0NjkwLCJzdWIiOiIyMSIsInNjb3BlcyI6W119.hyjXMXwRb3GLVJ7w5KBmzwVDzoPcztjXKHOx8fqwN6jOvTfgki_HngAIB3JaG5Xhb7w8mFBX03fUDwzOc2S108SiBTjZ7B14GtYmlC8OgxVkKXcCqJ_0UOZBsgCYo7p7f4HBIgIyZ8StrsNlNbuj_4W5Pz2YY4WitbvDX9LsZFIbdz3vhVlmtR2zKA23hyRGUsWZniB7zLGEvsUt5XA9NNAy9XWySaw7JuRZ4uyYm0bXHrJKHSTmfBd9KOJSdpnWs_9miNvh5g_DJo39L7awAlG7-cb67Ih00bhrmgGAWp6VCEd5h-EhIc-l1qfYT56osDgnYIqMk3cAgIWrYrhrs6-mZ1miXqipk9dClliUO7DBylZW8cRbQ5PQ5xisE_wCMTSUr7VYE_7RyjMzDaZuctf8kHQ_W27ED9tq14NhgvJs4hkpO6Gzr32zyMj-KRan2Fc3O1CYgyJx0OR2LhvXrxp2PxfUSIzR8eGHOGypLicU_suG3Dsz_UG7Kp5EdhP8Ma9eDyWObfU64iFYZsuz58TOBOH2ssH7_PpKHBGmchxQSjpKsIwsjeYpdbVmzXG7cZe2_AOEZpXmlwkaAZLow3_kDPfvuoCIWVsEKfgRs72t3B8fIwSt-vWaXpGYKjGOjf9ZOXpiKjG1XHaur0vLR1eiIXKVFSyn6y42FIlbSqk");
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }
                    };

                    queue.add(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                /*StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, "http://lalalaravel.test/api/login", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("VOLLEY", "Response: " + response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY", "Error: " + error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> postParams = new HashMap<String, String>();
                        postParams.put("email", email);
                        postParams.put("password", password);
                        postParams.put("password_confirmation", confirmPassword);

                        return postParams;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(jsonObjectRequest);*/

                /*final HashMap<String, String> postParams = new HashMap<String, String>();
                postParams.put("email", email);
                postParams.put("password", password);
                postParams.put("password_confirmation", confirmPassword);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, "http://lalalaravel.test/api/login", new JSONObject(postParams), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("VOLLEY", "Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("VOLLEY", "Error: " + error.toString());
                            }
                        });

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(jsonObjectRequest);*/


               /* //sovrascrittura delle Shared Preferences
                SharedPreferences newSharedPref = LoginActivity.this.getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = newSharedPref.edit();
                editor.putString(getString(R.string.token), usernameField.getText().toString());
                Log.d("provaEditor", usernameField.getText().toString());
                editor.commit();

                SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
                String username = sharedPref.getString(getString(R.string.username), "");
                Log.d("usernameLogin", username);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);*/
            }
        });
    }
}