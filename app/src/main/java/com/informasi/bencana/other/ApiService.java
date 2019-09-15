package com.informasi.bencana.other;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.util.Patterns;

import com.informasi.bencana.app.DashboardActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiService {
    private Activity activity;
    private FunctionHelper functionHelper;
    private ProgressDialog pDialog;

    public ApiService(Activity activity, ProgressDialog pDialog) {
        this.activity   = activity;
        functionHelper  = new FunctionHelper(activity);
        this.pDialog    = pDialog;

        Ion.getDefault(activity).getConscryptMiddleware().enable(false);
        Ion.getDefault(activity).configure().setLogging("LOG GET API", Log.DEBUG);
    }

    // Fungsi ini digunakan untuk mengambil data hashmap dari reponse API
    public interface hashMapListener {
        String getHashMap(Map<String, String> hashMap);
    }

    // Fungsi ini digunakan untuk melakukan login ke aplikasi dengan token facebook atau token google
    public void login(final String url, final String username, final String password)
    {
        try {
            functionHelper.showProgressDialog(pDialog, true);
            if (Patterns.WEB_URL.matcher(url).matches()) {
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(5000)
                        .setBodyParameter("username", username)
                        .setBodyParameter("password", password)
                        .asString()
                        .withResponse()
                        .setCallback(new FutureCallback<Response<String>>() {
                            @Override
                            public void onCompleted(Exception e, Response<String> result) {
                                try {
                                    if (e == null) {
                                        if (result.getHeaders().code() == 200) {
                                            JSONObject results      = new JSONObject(result.getResult());

                                            boolean status          = results.getBoolean("status");
                                            String message          = results.getString("message");

                                            if (status) {
                                                JSONArray profile           = results.getJSONArray("result");
                                                JSONObject detailProfile    = profile.getJSONObject(0);

                                                functionHelper.saveSession("id", detailProfile.getString("id"));
                                                functionHelper.saveSession("name", detailProfile.getString("name"));
                                                functionHelper.saveSession("email", detailProfile.getString("email"));
                                                functionHelper.saveSession("phone", detailProfile.getString("phone"));

                                                functionHelper.startIntent(DashboardActivity.class, true, true,
                                                        null);
                                            } else {
                                                functionHelper.popupDialog("Oops !", message, false);
                                            }
                                        } else {
                                            functionHelper.popupDialog("Oops !", result.getHeaders().message(), false);
                                        }
                                    } else {
                                        functionHelper.popupDialog("Oops !", e.getLocalizedMessage(), false);
                                    }
                                    functionHelper.showProgressDialog(pDialog, false);
                                } catch (Exception ex) {
                                    functionHelper.showProgressDialog(pDialog, false);
                                    functionHelper.popupDialog("Oops !", "Login has failed, try again later !", false);
                                    Log.e("Exception Login", "exception", ex);
                                }
                            }
                        });
            } else {
                functionHelper.showProgressDialog(pDialog, false);
                functionHelper.popupDialog("Oops", "Your URL Address is not valid !", false);
            }
        } catch (Exception e) {
            functionHelper.showProgressDialog(pDialog, false);
            functionHelper.popupDialog("Oops", "Login has failed, try again later !", false);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk mengambil data yang akan ditampilkan dimenu dashboard
    public void getData(final String url, String type, boolean showLoading,
                        final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                if (showLoading)
                    functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .setTimeout(5000)
                        .setLogging("FETCH DATA", Log.DEBUG)
                        .noCache()
                        .asString()
                        .withResponse()
                        .setCallback(new FutureCallback<Response<String>>() {
                            @Override
                            public void onCompleted(Exception e, Response<String> result) {
                                if (showLoading)
                                    functionHelper.showProgressDialog(pDialog, false);
                                try {
                                    if (e == null) {
                                        if (result.getHeaders().code() == 200) {
                                            if (type.equals("array")) {
                                                JSONArray results = new JSONArray(result.getResult());
                                                hash.put("success", "1");
                                                hash.put("result", results.toString());
                                            } else {
                                                JSONObject results = new JSONObject(result.getResult());
                                                hash.put("success", "1");
                                                hash.put("result", results.toString());
                                            }

                                            listener.getHashMap(hash);
                                        } else {
                                            hash.put("success", "0");
                                            hash.put("message", "Fetch Data : " + result.getHeaders().message());
                                            listener.getHashMap(hash);
                                        }
                                    } else {
                                        hash.put("success", "0");
                                        hash.put("message", "Fetch Data : " + e.getLocalizedMessage());
                                        listener.getHashMap(hash);
                                    }
                                } catch (Exception ex) {
                                    hash.put("success", "0");
                                    hash.put("message", "Fetching data has failed, please try again later !");
                                    listener.getHashMap(hash);
                                    Log.e("Exception FETCH DATA", "exception", ex);
                                }
                            }
                        });
            } else {
                hash.put("success", "0");
                hash.put("message", "Your URL Address is not valid !");
                listener.getHashMap(hash);
            }
        } catch (Exception e) {
            hash.put("success", "0");
            hash.put("message", "Fetching data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception FETCH DATA", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk melakukan ubah password
    public void changePassword(final String url, final Map<String, String> param,
                               final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(5000)
                        .setBodyParameter("id", param.get("id"))
                        .setBodyParameter("password", param.get("password"))
                        .setBodyParameter("new_password", param.get("newPassword"))
                        .setBodyParameter("retype_password", param.get("confirmPassword"))
                        .asString()
                        .withResponse()
                        .setCallback(new FutureCallback<Response<String>>() {
                            @Override
                            public void onCompleted(Exception e, Response<String> result) {
                                try {
                                    functionHelper.showProgressDialog(pDialog, false);
                                    if (e == null) {
                                        if (result.getHeaders().code() == 200) {
                                            JSONObject results      = new JSONObject(result.getResult());

                                            boolean status          = results.getBoolean("status");
                                            String message          = results.getString("message");

                                            if (status) {
                                                hash.put("success", "1");
                                                hash.put("message", message);
                                                listener.getHashMap(hash);
                                            } else {
                                                hash.put("success", "0");
                                                hash.put("message", message);
                                                listener.getHashMap(hash);
                                            }
                                        } else {
                                            hash.put("success", "0");
                                            hash.put("message", result.getHeaders().message());
                                            listener.getHashMap(hash);
                                        }
                                    } else {
                                        hash.put("success", "0");
                                        hash.put("message", e.getLocalizedMessage());
                                        listener.getHashMap(hash);
                                    }
                                } catch (Exception ex) {
                                    hash.put("success", "0");
                                    hash.put("message", "Change password has failed, please try again later !");
                                    listener.getHashMap(hash);
                                    Log.e("Exception Login", "exception", ex);
                                }
                            }
                        });
            } else {
                hash.put("success", "0");
                hash.put("message", "Your URL Address is not valid !");
                listener.getHashMap(hash);
            }
        } catch (Exception e) {
            hash.put("success", "0");
            hash.put("message", "Change password has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }
}
