package com.informasi.bencana.app;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.informasi.bencana.other.ApiService;
import com.informasi.bencana.other.FunctionHelper;

public class MasterActivity extends AppCompatActivity {
    String currentTime = String.valueOf(System.currentTimeMillis()/1000);
    // Attribute Url API
    private final String baseUrl = "http://api.atp-sb-medis.com/public/";
    public String urlLogin = baseUrl + "api/v1/auth/login?v=" + currentTime;
    public String urlRegister = baseUrl + "api/v1/auth/register?v=" + currentTime;
    public String urlChangePassword = baseUrl + "api/v1/auth/changePassword?v=" + currentTime;
    public String urlProfile = baseUrl + "api/v1/auth/profile?v=" + currentTime + "&id=";
    public String urlLogout = baseUrl + "api/v1/auth/logout?v=" + currentTime;
    public String urlPatientCountries = baseUrl + "api/v1/dashboard/getPatientCountries?v=" + currentTime;
    public String urlNews = baseUrl + "api/v1/dashboard/getNews?v=" + currentTime;
    public String urlUserGuide = baseUrl + "api/v1/dashboard/listUserGuide?v=" + currentTime;
    public String urlContactUs = baseUrl + "api/v1/dashboard/contactUs?v=" + currentTime;
    public String listPatient = baseUrl + "api/v1/patient/listPatient?v=" + currentTime;
    public String insertPatient = baseUrl + "api/v1/patient/insert?v=" + currentTime;
    public String updatePatient = baseUrl + "api/v1/patient/update?v=" + currentTime;
    public String deletePatient = baseUrl + "api/v1/patient/delete?v=" + currentTime;
    public String listCountries = baseUrl + "api/v1/master/listCountries?v=" + currentTime;
    public String dataMaster = baseUrl + "api/v1/master/dataMasterStatic?v=" + currentTime + "&type=";
    public String historyPatient = baseUrl + "api/v1/patient/getHistory?v=" + currentTime + "&id=";
    public String saveHistoryPatient = baseUrl + "api/v1/patient/saveHistory?v=" + currentTime;
    public String insertProgressPatient = baseUrl + "api/v1/ProgressPatient/insertProgress?v=" + currentTime;
    public String updateProgressPatient = baseUrl + "api/v1/ProgressPatient/updateProgress?v=" + currentTime;
    public String deleteProgressPatient = baseUrl + "api/v1/ProgressPatient/deleteProgress?v=" + currentTime;
    public String listProgressPatient = baseUrl + "api/v1/ProgressPatient/getProgress?v=" + currentTime;
    public String listProgressPatientDetail = baseUrl + "api/v1/ProgressPatient/getProgressDetail?v=" + currentTime + "&patientId=";
    public String listMonitoring = baseUrl + "api/v1/MonitoringPatient/getMonitoring?v=" + currentTime;
    public String listMonitoringDetail = baseUrl + "api/v1/MonitoringPatient/getMonitoringDetail?v=" + currentTime + "&patientId=";
    public String insertMonitoring = baseUrl + "api/v1/MonitoringPatient/insertMonitoring?v=" + currentTime;
    public String updateMonitoring = baseUrl + "api/v1/MonitoringPatient/updateMonitoring?v=" + currentTime;
    public String deleteMonitoring = baseUrl + "api/v1/MonitoringPatient/deleteMonitoring?v=" + currentTime;
    public String listCollabortaionPatient = baseUrl + "api/v1/CollaborationPatient/getCollaboration?v=" + currentTime;
    public String listCollabortaionPatientDetail = baseUrl + "api/v1/CollaborationPatient/getCollaborationDetail?v=" + currentTime + "&patientId=";
    public String insertCollaboratationPatient = baseUrl + "api/v1/CollaborationPatient/insertCollaboration?v=" + currentTime;
    public String updateCollaborationPatient = baseUrl + "api/v1/CollaborationPatient/updateCollaboration?v=" + currentTime;
    public String deleteCollaborationPatient = baseUrl + "api/v1/CollaborationPatient/deleteCollaboration?v=" + currentTime;
    public String listReport = baseUrl + "api/v1/Report/getReportProgress?v=" + currentTime;
    public String listNeedAssesmen = baseUrl + "api/v1/AssessmentNeed/listData?v=" + currentTime;
    public String saveNeedAssesmen = baseUrl + "api/v1/AssessmentNeed/saveData?v=" + currentTime;
    public String deleteNeedAssesmen = baseUrl + "api/v1/AssessmentNeed/deleteData?v=" + currentTime;
    public String urlDisaster = baseUrl + "api/v1/Dashboard/prepareDisaster?v=" + currentTime;
    public String urlNaturalDisaster = baseUrl + "welcome/naturalDisaster?v=" + currentTime;
    public String urlTechDisaster = baseUrl + "welcome/technologyDisaster?v=" + currentTime;
    public String urlComplexDisaster = baseUrl + "welcome/complexDisaster?v=" + currentTime;
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
