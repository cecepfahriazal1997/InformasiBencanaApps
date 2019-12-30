package com.informasi.bencana.app;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.informasi.bencana.other.ApiService;
import com.informasi.bencana.other.FunctionHelper;

public class MasterActivity extends AppCompatActivity {
    // Attribute Url API
    public String urlLogin = "http://api.atp-sb-medis.com/public/api/v1/auth/login";
    public String urlRegister = "http://api.atp-sb-medis.com/public/api/v1/auth/register";
    public String urlChangePassword = "http://api.atp-sb-medis.com/public/api/v1/auth/changePassword";
    public String urlProfile = "http://api.atp-sb-medis.com/public/api/v1/auth/profile?id=";
    public String urlLogout = "http://api.atp-sb-medis.com/public/api/v1/auth/logout";
    public String urlPatientCountries = "http://api.atp-sb-medis.com/public/api/v1/dashboard/getPatientCountries";
    public String urlNews = "http://api.atp-sb-medis.com/public/api/v1/dashboard/getNews";
    public String urlUserGuide = "http://api.atp-sb-medis.com/public/api/v1/dashboard/listUserGuide";
    public String urlContactUs = "http://api.atp-sb-medis.com/public/api/v1/dashboard/contactUs";
    public String listPatient = "http://api.atp-sb-medis.com/public/api/v1/patient/listPatient";
    public String insertPatient = "http://api.atp-sb-medis.com/public/api/v1/patient/insert";
    public String updatePatient = "http://api.atp-sb-medis.com/public/api/v1/patient/update";
    public String deletePatient = "http://api.atp-sb-medis.com/public/api/v1/patient/delete";
    public String listCountries = "http://api.atp-sb-medis.com/public/api/v1/master/listCountries";
    public String dataMaster = "http://api.atp-sb-medis.com/public/api/v1/master/dataMasterStatic?type=";
    public String historyPatient = "http://api.atp-sb-medis.com/public/api/v1/patient/getHistory?id=";
    public String saveHistoryPatient = "http://api.atp-sb-medis.com/public/api/v1/patient/saveHistory";
    public String insertProgressPatient = "http://api.atp-sb-medis.com/public/api/v1/ProgressPatient/insertProgress";
    public String updateProgressPatient = "http://api.atp-sb-medis.com/public/api/v1/ProgressPatient/updateProgress";
    public String deleteProgressPatient = "http://api.atp-sb-medis.com/public/api/v1/ProgressPatient/deleteProgress";
    public String listProgressPatient = "http://api.atp-sb-medis.com/public/api/v1/ProgressPatient/getProgress";
    public String listProgressPatientDetail = "http://api.atp-sb-medis.com/public/api/v1/ProgressPatient/getProgressDetail?patientId=";
    public String listMonitoring = "http://api.atp-sb-medis.com/public/api/v1/MonitoringPatient/getMonitoring";
    public String listMonitoringDetail = "http://api.atp-sb-medis.com/public/api/v1/MonitoringPatient/getMonitoringDetail?patientId=";
    public String insertMonitoring = "http://api.atp-sb-medis.com/public/api/v1/MonitoringPatient/insertMonitoring";
    public String updateMonitoring = "http://api.atp-sb-medis.com/public/api/v1/MonitoringPatient/updateMonitoring";
    public String deleteMonitoring = "http://api.atp-sb-medis.com/public/api/v1/MonitoringPatient/deleteMonitoring";
    public String listCollabortaionPatient = "http://api.atp-sb-medis.com/public/api/v1/CollaborationPatient/getCollaboration";
    public String listCollabortaionPatientDetail = "http://api.atp-sb-medis.com/public/api/v1/CollaborationPatient/getCollaborationDetail?patientId=";
    public String insertCollaboratationPatient = "http://api.atp-sb-medis.com/public/api/v1/CollaborationPatient/insertCollaboration";
    public String updateCollaborationPatient = "http://api.atp-sb-medis.com/public/api/v1/CollaborationPatient/updateCollaboration";
    public String deleteCollaborationPatient = "http://api.atp-sb-medis.com/public/api/v1/CollaborationPatient/deleteCollaboration";
    public String listReport = "http://api.atp-sb-medis.com/public/api/v1/Report/getReportProgress";
    public String listNeedAssesmen = "http://api.atp-sb-medis.com/public/api/v1/AssessmentNeed/listData";
    public String saveNeedAssesmen = "http://api.atp-sb-medis.com/public/api/v1/AssessmentNeed/saveData";
    public String deleteNeedAssesmen = "http://api.atp-sb-medis.com/public/api/v1/AssessmentNeed/deleteData";
    public String urlDisaster = "http://api.atp-sb-medis.com/public/api/v1/Dashboard/prepareDisaster";
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
