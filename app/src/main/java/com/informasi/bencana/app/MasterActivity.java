package com.informasi.bencana.app;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.informasi.bencana.other.ApiService;
import com.informasi.bencana.other.FunctionHelper;

public class MasterActivity extends AppCompatActivity {
    // Attribute Url API
    public String urlLogin = "http://128.199.219.185/api-simsb/public/api/v1//auth/login";
    public String urlRegister = "http://128.199.219.185/api-simsb/public/api/v1//auth/register";
    public String urlChangePassword = "http://128.199.219.185/api-simsb/public/api/v1//auth/changePassword";
    public String urlProfile = "http://128.199.219.185/api-simsb/public/api/v1//auth/profile?id=";
    public String urlLogout = "http://128.199.219.185/api-simsb/public/api/v1//auth/logout";
    public String urlPatientCountries = "http://128.199.219.185/api-simsb/public/api/v1//dashboard/getPatientCountries";
    public String urlNews = "http://128.199.219.185/api-simsb/public/api/v1//dashboard/getNews";
    public String urlUserGuide = "http://128.199.219.185/api-simsb/public/api/v1//dashboard/listUserGuide";
    public String urlContactUs = "http://128.199.219.185/api-simsb/public/api/v1//dashboard/contactUs";
    public String listPatient = "http://128.199.219.185/api-simsb/public/api/v1//patient/listPatient";
    public String insertPatient = "http://128.199.219.185/api-simsb/public/api/v1//patient/insert";
    public String updatePatient = "http://128.199.219.185/api-simsb/public/api/v1//patient/update";
    public String deletePatient = "http://128.199.219.185/api-simsb/public/api/v1//patient/delete";
    public String listCountries = "http://128.199.219.185/api-simsb/public/api/v1//patient/listCountries";
    public String urlGoogleDoc   = "https://view.officeapps.live.com/op/embed.aspx?src=";

    // Attribute view
    protected FunctionHelper helper;
    protected ApiService clientApiService;
    protected ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pDialog             = new ProgressDialog(this);
        clientApiService    = new ApiService(this, pDialog);
        helper              = new FunctionHelper(this);
    }
}