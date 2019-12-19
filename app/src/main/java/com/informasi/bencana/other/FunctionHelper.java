package com.informasi.bencana.other;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.informasi.bencana.R;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class FunctionHelper {
    private Activity activity;
    private Context context;

    public FunctionHelper(){}
    public FunctionHelper(Activity activity) {
        this.activity = activity;
    }
    public FunctionHelper(Context context) {
        this.context = context;
    }

    // Fungsi ini digunakan untuk berpindah ke activity lain / page lain
    public void startIntent(Class destination, boolean clearIntent, boolean finish,
                            Map<String, String> paramList)

    {
        Intent intent   = new Intent(activity, destination);
        if (clearIntent)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        else
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (paramList != null) {
            for (Map.Entry<String, String> data : paramList.entrySet()) {
                String key      = data.getKey();
                String value    = data.getValue();

                intent.putExtra(key, value);
            }
        }

        activity.startActivity(intent);
        if (finish)
            activity.finish();
    }

    // Fungsi ini digunakan untuk berpindah ke activity lain namun saat kembali ke activity sebelumnya
    // memberikan suatu nilai
    public void startIntentForResult(Class destination, Map<String, String> paramList, int code)
    {
        Intent intent = new Intent(activity, destination);

        if (paramList != null) {
            for (Map.Entry<String, String> data : paramList.entrySet()) {
                String key      = data.getKey();
                String value    = data.getValue();

                intent.putExtra(key, value);
            }
        }
        activity.startActivityForResult(intent, code);
    }

    // Fungsi ini digunakan untuk menyimpan session sesuai dengan key dan value yang diinginkan
    public void saveSession(String name, String value){
        PreferenceManager.getDefaultSharedPreferences(activity).edit().putString(name, value).apply();
    }

    // Fungsi ini digunakan untuk mengambil data session sesuai dengan key yang diinginkan
    public String getSession(String key) {
        return PreferenceManager.getDefaultSharedPreferences(activity).getString(key, null);
    }

    // Fungsi ini digunakan untuk membersihkan session / proses logout aplikasi
    public void clearSession()
    {
        SharedPreferences sharedPreferences         = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor             = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    // Fungsi ini digunakan untuk mengenkripsi base 64 suatu String
    public String encryptString(String text){
        byte[] data;
        String base64   = "";
        try {
            data            = text.getBytes(StandardCharsets.UTF_8);
            base64          = Base64.encodeToString(data, Base64.NO_WRAP);
        } catch (Exception e) {
        }
        return base64;
    }

    // Fungsi ini digunakan untuk convert format waktu menjadi jam, menit, detik
    public float convertTime(final String input, final String type)
    {
        String[] parts  = input.split ( ":" );
        float tempHasil = 0;

        if (type.equalsIgnoreCase("hour")) {
            float hour    = Float.parseFloat(parts[0]);
            float minute  = Float.parseFloat(parts[1]) / 60;
            float second  = Float.parseFloat(parts[2]) / 3600;

            tempHasil   = hour + minute + second;
        } else if (type.equalsIgnoreCase("minute")) {
            float hour    = Float.parseFloat(parts[0]) * 60;
            float minute  = Float.parseFloat(parts[1]);
            float second  = Float.parseFloat(parts[2]) / 60;

            tempHasil   = hour + minute + second;
        } else if (type.equalsIgnoreCase("second")) {
            float hour    = Float.parseFloat(parts[0]) * 3600;
            float minute  = Float.parseFloat(parts[1]) * 60;
            float second  = Float.parseFloat(parts[2]);

            tempHasil   = hour + minute + second;
        }

        return tempHasil;
    }

    // Fungsi ini digunakan untuk mengubah format tanggal sesuai dengan keinginan
    public String formattingDate(String oldFormat, String format, String time){
        Date date           = null;
        String tempFormat   = "dd MMMM yyyy";
        String fromFormat   = "yyyy-MM-dd HH:mm:ss";
        String newFormat    = "";
        try {
            if (!format.isEmpty())
                tempFormat  = format;
            if (!oldFormat.isEmpty())
                fromFormat  = oldFormat;

            if (time != null && !time.isEmpty()) {
                date = new SimpleDateFormat(fromFormat).parse(time);
                newFormat = new SimpleDateFormat(tempFormat).format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return newFormat;
    }

    // Fungsi ini digunakan untuk menampilkan atau menutup loading dari SwipeRefreshLayout
    public void showLoadingSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout, boolean isShow)
    {
        if (isShow) {
            if (!swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(true);
            }
        } else {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    // Fungsi ini digunakan untuk melakukan pengecekan apakah posisi listview berada di atas
    public void setEnabledSwipeRefreshListView(final SwipeRefreshLayout swipeRefreshLayout,
                                               final ListView listView, final String type)   {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (listView.getChildAt(0) != null) {
                    boolean isTop = false;
                    if(listView.getChildCount() == 0)
                        isTop = true;
                    else if (type.equalsIgnoreCase("collapseToolbar"))
                        isTop = listView.getChildAt(0).getTop() >= 0 && listView.getChildAt(0).getTop() >= 20;
                    else
                        isTop = listView.getChildAt(0).getTop() >= 0;

                    swipeRefreshLayout.setEnabled(isTop);
                }
            }
        });
    }

    // Fungsi ini digunakan untuk melakukan pengecekan apakah posisi scrollview berada di atas
    public void setEnabledSwipeRefreshScrollView(final SwipeRefreshLayout swipeRefreshLayout,
                                                 final ScrollView scrollView)   {
        final ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = new
                ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getScrollY() == 0) {
                            swipeRefreshLayout.setEnabled(true);
                        } else
                            swipeRefreshLayout.setEnabled(false);
                    }
                };

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            private ViewTreeObserver observer;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (observer == null) {
                    observer = scrollView.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                } else if (!observer.isAlive()) {
                    observer.removeOnScrollChangedListener(onScrollChangedListener);
                    observer = scrollView.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                }
                return false;
            }
        });
    }

    // Fungsi ini digunakan untuk menampilkan popup biasa yang berisi judul dan deskripsi
    public void popupDialog(String title, String message, final boolean isFinishActivity) {
        try {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case DialogInterface.BUTTON_NEGATIVE:
                            dialogInterface.dismiss();
                            if (isFinishActivity)
                                activity.finish();
                            return;
                        default:
                            return;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle((CharSequence) title);
            builder.setCancelable(false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setMessage(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
            } else {
                builder.setMessage(Html.fromHtml(message));
            }

            builder.setNegativeButton((CharSequence) "Tutup", dialogClickListener);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    Fungsi ini digunakan untuk menampilkan popup konfirmasi
    public void popupConfirm(String title, DialogInterface.OnClickListener dialogClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle((CharSequence) "Peringatan ! ");
        builder.setCancelable(false);
        builder.setMessage((CharSequence) title)
                .setPositiveButton((CharSequence) "Ya", dialogClickListener)
                .setNegativeButton((CharSequence) "Tidak", dialogClickListener).show();
    }

    public void popupNotification(Dialog builder, int image, String textTitle,
                                  String textDescription, View.OnClickListener positiveListener,
                                  View.OnClickListener negativeListener, boolean allowClose) {
        if (allowClose) {
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //nothing;
                }
            });
        }

        View customView         = activity.getLayoutInflater().inflate(R.layout.popup_notification, null);
        FancyButton btnPositive = (FancyButton) customView.findViewById(R.id.btnPositive);
        FancyButton btnNegative = (FancyButton) customView.findViewById(R.id.btnNegative);
        TextView title          = (TextView) customView.findViewById(R.id.title);
        TextView description    = (TextView) customView.findViewById(R.id.description);
        ImageView icon          = (ImageView) customView.findViewById(R.id.icon);

        icon.setImageResource(image);
        title.setText(textTitle);
        description.setText(textDescription);
        if (positiveListener != null) {
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setOnClickListener(positiveListener);
        }

        if (negativeListener != null) {
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setOnClickListener(negativeListener);
        }

        builder.setContentView(customView);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.show();
    }

    // Fungsi ini digunakan untuk membuat height listview menjadi dinamis
    public void setListViewHeightBasedOnChildren(final ListView listView) {
        listView.post(new Runnable() {
            @Override
            public void run() {
                ListAdapter listAdapter = listView.getAdapter();
                if (listAdapter == null) {
                    return;
                }
                int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
                int listWidth = listView.getMeasuredWidth();
                for (int i = 0; i < listAdapter.getCount(); i++) {
                    View listItem = listAdapter.getView(i, null, listView);
                    listItem.measure(
                            View.MeasureSpec.makeMeasureSpec(listWidth, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    totalHeight += listItem.getMeasuredHeight();
                    Log.d("listItemHeight" + listItem.getMeasuredHeight(), "___________");
                }
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = (int) ((totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))));
                listView.setLayoutParams(params);
                listView.requestLayout();
            }
        });
    }

    // Fungsi ini digunakan untuk setup progress dialog / loading sesuai dengan context
    public void setupProgressDialog(ProgressDialog pDialog, String title)
    {
        pDialog.setMessage(title);
        pDialog.setCancelable(false);
    }

    // Fungsi ini digunakan untuk menampilkan progress dialog / loading
    public void showProgressDialog(ProgressDialog pDialog, boolean show)
    {
        if (show) {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        } else {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    // Fungsi ini digunakan untuk menampilkan pesan berupa toast
    public void showToast(String message, int duration)
    {
        Toast.makeText(activity.getApplicationContext(), message, duration).show();
    }

    // Fungsi ini  digunakan untuk mengecek apakah device terkoneksi dengan internet atau tidak
    public boolean isNetworkAvailable()
    {
        ConnectivityManager manager =
                (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    public float convertDpToPixel(float dp){
        return dp * ((float) activity.getResources().getDisplayMetrics().densityDpi
                / DisplayMetrics.DENSITY_DEFAULT);
    }

    public float convertPixelsToDp(float px){
        return px / ((float) activity.getResources().getDisplayMetrics().densityDpi
                / DisplayMetrics.DENSITY_DEFAULT);
    }

    // Fungsi ini digunakan untuk membuat height pada grid view menyesuaikan dengan value nya
    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns)
    {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if( items > columns ){
            x = items/columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }

    // Fungsi ini digunakan untuk mengkonversi waktu hh:mm:ss string ke millisecond
    public long timeStringToMilliseconds(String timeString){
        long millis = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(timeString);
            millis = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return millis;
    }

    // Fungsi ini digunakan untuk mengkonversi dari tanggal tipe string ke tanggal tipe date
    // Setelah menjadi tipe date maka akan dikonversi lagi menjadi waktu hh:mm:ss
    public String convertDateToTime(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dfCurrentTime = new SimpleDateFormat("hh:mm:ss");
        String strDateCurrentTime  = "";
        Date convertedDate  = new Date();
        try {
            convertedDate       = dateFormat.parse(dateString);
            strDateCurrentTime  = dfCurrentTime.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strDateCurrentTime;
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteCache(File pathDir) {
        try {
            File dir = activity.getCacheDir();
            if (pathDir != null)
                dir = pathDir;

            if (dir != null && dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteCache(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
                return dir.delete();
            } else if(dir!= null && dir.isFile()) {
                return dir.delete();
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fungsi ini digunakan untuk setup webview yang memiliki konten text
    public void formatIsText(final WebView webView, final String urlContent, final String textColor)
    {
        webView.clearCache(true);
        webView.clearHistory();
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportZoom(true);
        webView.setLongClickable(false);
        webView.setHapticFeedbackEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(WebView.LAYER_TYPE_NONE, null);
        if (urlContent.contains("{") && urlContent.contains("}") || urlContent.contains("\\(")) {
            webView.loadDataWithBaseURL("http://bar",
                    "<head><style type='text/css'>body{color: " + textColor
                            + "; font-size: 14px}</style></head><script type='text/javascript' "
                            + "src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-MML-AM_CHTML'>"
                            + "</script><math>" + urlContent + "</math>",
                    "text/html", "utf-8", "");
        } else {
            String text = "<html><head>"
                    + "<style type='text/css'>body{color: " + textColor + ";font-size: 14px;}"
                    + "</style></head>"
                    + "<body>"
                    + urlContent
                    + "</body></html>";
            webView.loadData(text, "text/html", null);
        }
    }

    // Fungsi ini digunakan untuk menampilkan konten doc, ppt, serta pdf pada webview
    public void formatIsEmbed(final ProgressDialog pDialog, WebView webView, String url){
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)(RelativeLayout
                .LayoutParams.MATCH_PARENT), RelativeLayout.LayoutParams.MATCH_PARENT);

        showProgressDialog(pDialog, true);
        webView.setLayoutParams(lp);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                showProgressDialog(pDialog, false);
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onPageFinished(final WebView view, final String url) {
                showProgressDialog(pDialog, false);
            }
        });
        webView.loadUrl(url);
    }

    public String intToMonth(int month) {
        switch (month) {
            case 1 : return "Januari";
            case 2 : return "Febuari";
            case 3 : return "Maret";
            case 4 : return "April";
            case 5 : return "Mei";
            case 6 : return "Juni";
            case 7 : return "Juli";
            case 8 : return "Agustus";
            case 9 : return "September";
            case 10 : return "Oktober";
            case 11 : return "November";
            case 12 : return "Desember";
            default: return "-";
        }
    }

    public void showDatePicker(EditText date) {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dates = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                date.setText(sdf.format(myCalendar.getTime()));
            }
        };
        new DatePickerDialog(activity, dates, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public View inflateView(int layout) {
        View view;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        return view;
    }

    public boolean isPackageInstalled(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}