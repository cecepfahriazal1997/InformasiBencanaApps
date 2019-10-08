package com.informasi.bencana.app;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.informasi.bencana.other.ApiService;
import com.informasi.bencana.other.FunctionHelper;

public class MasterActivity extends AppCompatActivity {
    // Attribute Url API
    public String urlLogin = "http://128.199.219.185/api-simsb/public/api/v1/auth/login";
    public String urlRegister = "http://128.199.219.185/api-simsb/public/api/v1/auth/register";
    public String urlChangePassword = "http://128.199.219.185/api-simsb/public/api/v1/auth/changePassword";
    public String urlProfile = "http://128.199.219.185/api-simsb/public/api/v1/auth/profile?id=";
    public String urlLogout = "http://128.199.219.185/api-simsb/public/api/v1/auth/logout";
    public String urlPatientCountries = "http://128.199.219.185/api-simsb/public/api/v1/dashboard/getPatientCountries";
    public String urlNews = "http://128.199.219.185/api-simsb/public/api/v1/dashboard/getNews";
    public String urlUserGuide = "http://128.199.219.185/api-simsb/public/api/v1/dashboard/listUserGuide";
    public String urlContactUs = "http://128.199.219.185/api-simsb/public/api/v1/dashboard/contactUs";
    public String listPatient = "http://128.199.219.185/api-simsb/public/api/v1/patient/listPatient";
    public String insertPatient = "http://128.199.219.185/api-simsb/public/api/v1/patient/insert";
    public String updatePatient = "http://128.199.219.185/api-simsb/public/api/v1/patient/update";
    public String deletePatient = "http://128.199.219.185/api-simsb/public/api/v1/patient/delete";
    public String listCountries = "http://128.199.219.185/api-simsb/public/api/v1/master/listCountries";
    public String dataMaster = "http://128.199.219.185/api-simsb/public/api/v1/master/dataMasterStatic?type=";
    public String historyPatient = "http://128.199.219.185/api-simsb/public/api/v1/patient/getHistory?id=";
    public String saveHistoryPatient = "http://128.199.219.185/api-simsb/public/api/v1/patient/saveHistory";
    public String insertProgressPatient = "http://128.199.219.185/api-simsb/public/api/v1/ProgressPatient/insertProgress";
    public String updateProgressPatient = "http://128.199.219.185/api-simsb/public/api/v1/ProgressPatient/updateProgress";
    public String deleteProgressPatient = "http://128.199.219.185/api-simsb/public/api/v1/ProgressPatient/deleteProgress";
    public String listProgressPatient = "http://128.199.219.185/api-simsb/public/api/v1/ProgressPatient/getProgress";
    public String listProgressPatientDetail = "http://128.199.219.185/api-simsb/public/api/v1/ProgressPatient/getProgressDetail?patientId=";
    public String listMonitoring = "http://128.199.219.185/api-simsb/public/api/v1/MonitoringPatient/getMonitoring";
    public String listMonitoringDetail = "http://128.199.219.185/api-simsb/public/api/v1/MonitoringPatient/getMonitoringDetail?patientId=";
    public String insertMonitoring = "http://128.199.219.185/api-simsb/public/api/v1/MonitoringPatient/insertMonitoring";
    public String updateMonitoring = "http://128.199.219.185/api-simsb/public/api/v1/MonitoringPatient/updateMonitoring";
    public String deleteMonitoring = "http://128.199.219.185/api-simsb/public/api/v1/MonitoringPatient/deleteMonitoring";
    public String listCollabortaionPatient = "http://128.199.219.185/api-simsb/public/api/v1/CollaborationPatient/getCollaboration";
    public String listCollabortaionPatientDetail = "http://128.199.219.185/api-simsb/public/api/v1/CollaborationPatient/getCollaborationDetail?patientId=";
    public String insertCollaboratationPatient = "http://128.199.219.185/api-simsb/public/api/v1/CollaborationPatient/insertCollaboration";
    public String updateCollaborationPatient = "http://128.199.219.185/api-simsb/public/api/v1/CollaborationPatient/updateCollaboration";
    public String deleteCollaborationPatient = "http://128.199.219.185/api-simsb/public/api/v1/CollaborationPatient/deleteCollaboration";
    public String listReport = "http://128.199.219.185/api-simsb/public/api/v1/Report/getReportProgress";
    public String listNeedAssesmen = "http://128.199.219.185/api-simsb/public/api/v1/AssessmentNeed/listData";
    public String saveNeedAssesmen = "http://128.199.219.185/api-simsb/public/api/v1/AssessmentNeed/saveData";
    public String deleteNeedAssesmen = "http://128.199.219.185/api-simsb/public/api/v1/AssessmentNeed/deleteData";
    public String urlGoogleDoc   = "https://view.officeapps.live.com/op/embed.aspx?src=";

    // Attribute view
    public FunctionHelper helper;
    public ApiService clientApiService;
    public ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pDialog             = new ProgressDialog(this);
        clientApiService    = new ApiService(this, pDialog);
        helper              = new FunctionHelper(this);
    }
}