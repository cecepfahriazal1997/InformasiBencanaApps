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

    // Fungsi ini digunakan untuk insert data pasien
    public void insertPatient(final String url, final Map<String, String> param,
                               final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("location", param.get("location"))
                        .setBodyParameter("date", param.get("date"))
                        .setBodyParameter("number", param.get("number"))
                        .setBodyParameter("name", param.get("name"))
                        .setBodyParameter("gender", param.get("gender"))
                        .setBodyParameter("age", param.get("age"))
                        .setBodyParameter("weaknessCondition", param.get("weaknessCondition"))
                        .setBodyParameter("threadCondition", param.get("threadCondition"))
                        .setBodyParameter("doctorName", param.get("doctorName"))
                        .setBodyParameter("nurseName", param.get("nurseName"))
                        .setBodyParameter("supportName", param.get("supportName"))
                        .setBodyParameter("remark", param.get("remark"))
                        .setBodyParameter("userInput", param.get("userInput"))
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Insert data has failed, please try again later !");
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
            hash.put("message", "Insert data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk update data pasien
    public void updatePatient(final String url, final Map<String, String> param,
                               final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("patientId", param.get("patientId"))
                        .setBodyParameter("location", param.get("location"))
                        .setBodyParameter("date", param.get("date"))
                        .setBodyParameter("name", param.get("name"))
                        .setBodyParameter("gender", param.get("gender"))
                        .setBodyParameter("age", param.get("age"))
                        .setBodyParameter("weaknessCondition", param.get("weaknessCondition"))
                        .setBodyParameter("threadCondition", param.get("threadCondition"))
                        .setBodyParameter("doctorName", param.get("doctorName"))
                        .setBodyParameter("nurseName", param.get("nurseName"))
                        .setBodyParameter("supportName", param.get("supportName"))
                        .setBodyParameter("remark", param.get("remark"))
                        .setBodyParameter("userInput", param.get("userInput"))
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Insert data has failed, please try again later !");
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
            hash.put("message", "Insert data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk update data pasien
    public void deletePatient(final String url, final String id, final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("patientId", id)
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Insert data has failed, please try again later !");
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
            hash.put("message", "Insert data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk insert data pasien
    public void savePatientHistory(final String url, final Map<String, String> param,
                              final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("patientId", param.get("patientId"))
                        .setBodyParameter("typeRecord", param.get("typeRecord"))
                        .setBodyParameter("symptom", param.get("symptom"))
                        .setBodyParameter("specific", param.get("specific"))
                        .setBodyParameter("relationship", param.get("relationship"))
                        .setBodyParameter("condition", param.get("condition"))
                        .setBodyParameter("specificCondition", param.get("specificCondition"))
                        .setBodyParameter("lab", param.get("lab"))
                        .setBodyParameter("radiologi", param.get("radiologi"))
                        .setBodyParameter("diagnostic", param.get("diagnostic"))
                        .setBodyParameter("therapy", param.get("therapy"))
                        .setBodyParameter("rehability", param.get("rehability"))
                        .setBodyParameter("remark", param.get("remark"))
                        .setBodyParameter("userInput", param.get("userInput"))
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Insert data has failed, please try again later !");
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
            hash.put("message", "Insert data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk insert data pasien
    public void insertPatientProgress(final String url, final Map<String, String> param,
                              final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("patientId", param.get("patientId"))
                        .setBodyParameter("year", param.get("year"))
                        .setBodyParameter("month", param.get("month"))
                        .setBodyParameter("week", param.get("week"))
                        .setBodyParameter("day", param.get("day"))
                        .setBodyParameter("complication", param.get("complication"))
                        .setBodyParameter("complicationDetail", param.get("complicationDetail"))
                        .setBodyParameter("progress", param.get("progress"))
                        .setBodyParameter("status", param.get("status"))
                        .setBodyParameter("remark", param.get("remark"))
                        .setBodyParameter("userInput", param.get("userInput"))
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Insert data has failed, please try again later !");
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
            hash.put("message", "Insert data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk insert data pasien
    public void updatePatientProgress(final String url, final Map<String, String> param,
                              final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("id", param.get("id"))
                        .setBodyParameter("patientId", param.get("patientId"))
                        .setBodyParameter("year", param.get("year"))
                        .setBodyParameter("month", param.get("month"))
                        .setBodyParameter("week", param.get("week"))
                        .setBodyParameter("day", param.get("day"))
                        .setBodyParameter("complication", param.get("complication"))
                        .setBodyParameter("complicationDetail", param.get("complicationDetail"))
                        .setBodyParameter("progress", param.get("progress"))
                        .setBodyParameter("status", param.get("status"))
                        .setBodyParameter("remark", param.get("remark"))
                        .setBodyParameter("userInput", param.get("userInput"))
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Insert data has failed, please try again later !");
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
            hash.put("message", "Insert data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk update data pasien
    public void deleteProgress(final String url, final String id, final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("id", id)
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Delete data has failed, please try again later !");
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
            hash.put("message", "Delete data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk insert data pasien
    public void insertPatientMonitoring(final String url, final Map<String, String> param,
                              final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("patientId", param.get("patientId"))
                        .setBodyParameter("year", param.get("year"))
                        .setBodyParameter("month", param.get("month"))
                        .setBodyParameter("week", param.get("week"))
                        .setBodyParameter("day", param.get("day"))
                        .setBodyParameter("fact", param.get("fact"))
                        .setBodyParameter("problem", param.get("problem"))
                        .setBodyParameter("userInput", param.get("userInput"))
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Insert data has failed, please try again later !");
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
            hash.put("message", "Insert data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk insert data pasien
    public void updatePatientMonitoring(final String url, final Map<String, String> param,
                              final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("id", param.get("id"))
                        .setBodyParameter("patientId", param.get("patientId"))
                        .setBodyParameter("year", param.get("year"))
                        .setBodyParameter("month", param.get("month"))
                        .setBodyParameter("week", param.get("week"))
                        .setBodyParameter("day", param.get("day"))
                        .setBodyParameter("fact", param.get("fact"))
                        .setBodyParameter("problem", param.get("problem"))
                        .setBodyParameter("userInput", param.get("userInput"))
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Insert data has failed, please try again later !");
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
            hash.put("message", "Insert data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk update data pasien
    public void deleteMonitoring(final String url, final String id, final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("id", id)
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Delete data has failed, please try again later !");
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
            hash.put("message", "Delete data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }
    // Fungsi ini digunakan untuk insert data pasien
    public void insertPatientCollab(final String url, final Map<String, String> param,
                              final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("patientId", param.get("patientId"))
                        .setBodyParameter("year", param.get("year"))
                        .setBodyParameter("month", param.get("month"))
                        .setBodyParameter("week", param.get("week"))
                        .setBodyParameter("day", param.get("day"))
                        .setBodyParameter("problem", param.get("problem"))
                        .setBodyParameter("recommend", param.get("recommend"))
                        .setBodyParameter("collaborativeC", param.get("collaborativeC"))
                        .setBodyParameter("collaborativeD", param.get("collaborativeD"))
                        .setBodyParameter("feedback", param.get("feedback"))
                        .setBodyParameter("userInput", param.get("userInput"))
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Insert data has failed, please try again later !");
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
            hash.put("message", "Insert data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk insert data pasien
    public void updatePatientCollab(final String url, final Map<String, String> param,
                              final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("id", param.get("id"))
                        .setBodyParameter("detailId", param.get("detailId"))
                        .setBodyParameter("patientId", param.get("patientId"))
                        .setBodyParameter("year", param.get("year"))
                        .setBodyParameter("month", param.get("month"))
                        .setBodyParameter("week", param.get("week"))
                        .setBodyParameter("day", param.get("day"))
                        .setBodyParameter("problem", param.get("problem"))
                        .setBodyParameter("recommend", param.get("recommend"))
                        .setBodyParameter("collaborativeC", param.get("collaborativeC"))
                        .setBodyParameter("collaborativeD", param.get("collaborativeD"))
                        .setBodyParameter("feedback", param.get("feedback"))
                        .setBodyParameter("userInput", param.get("userInput"))
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Insert data has failed, please try again later !");
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
            hash.put("message", "Insert data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }

    // Fungsi ini digunakan untuk update data pasien
    public void deleteCollab(final String url, final String id,
                             final String detailId, final hashMapListener listener) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.clear();
        try {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                functionHelper.showProgressDialog(pDialog, true);
                Ion.with(activity).load(url)
                        .noCache()
                        .setTimeout(6000)
                        .setBodyParameter("id", id)
                        .setBodyParameter("detailId", detailId)
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
                                            String status           = results.getString("status");
                                            String message          = results.getString("message");

                                            if (status.equals("1")) {
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
                                    hash.put("message", "Delete data has failed, please try again later !");
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
            hash.put("message", "Delete data has failed, please try again later !");
            listener.getHashMap(hash);
            Log.e("Exception Login", "Exception", e);
        }
    }
}
