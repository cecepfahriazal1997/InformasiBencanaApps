package com.informasi.bencana.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.informasi.bencana.R;
import com.informasi.bencana.app.DashboardActivity;
import com.informasi.bencana.app.ProfileActivity;
import com.informasi.bencana.app.UserGuideActivity;
import com.informasi.bencana.app.ViewHtmlActivity;
import com.informasi.bencana.app.WalkthroughActivity;
import com.informasi.bencana.other.ApiService;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

public class AccountFragment extends Fragment {
    private DashboardActivity parent;
    private CardView cardUserGuide, cardChangePassword, cardContact, cardPrivacy, cardTermService;
    private FancyButton btnLogout;
    private TextView detailProfile, name, email;
    private CircleImageView imageProfile;

    public AccountFragment() {
        parent          = (DashboardActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent      = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView       = inflater.inflate(R.layout.fragment_account, container, false);

        cardUserGuide       = rootView.findViewById(R.id.cardUserGuide);
        cardChangePassword  = rootView.findViewById(R.id.cardChangePassword);
        cardContact         = rootView.findViewById(R.id.cardContact);
        cardPrivacy         = rootView.findViewById(R.id.cardPrivacy);
        cardTermService     = rootView.findViewById(R.id.cardTermService);
        btnLogout           = rootView.findViewById(R.id.btnLogout);
        detailProfile       = rootView.findViewById(R.id.detailProfile);
        name                = rootView.findViewById(R.id.name);
        email               = rootView.findViewById(R.id.email);
        imageProfile        = rootView.findViewById(R.id.avatar);

        initial();
        return rootView;
    }

    private void initial() {
        name.setText("" + parent.functionHelper.getSession("name"));
        email.setText("" + parent.functionHelper.getSession("email"));
        parent.apiService.getImageOnlineImageViewCircle(parent.functionHelper.getSession("image"), imageProfile);

        detailProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.functionHelper.startIntent(ProfileActivity.class, false, false, null);
            }
        });

        cardChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        cardContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactUs();
            }
        });

        cardUserGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.functionHelper.startIntent(UserGuideActivity.class, false, false, null);
            }
        });

        cardPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> param = new HashMap<>();
                param.put("title", "Privacy Police");
                param.put("file", "privacy_policy.html");
                parent.functionHelper.startIntent(ViewHtmlActivity.class, false, false, param);
            }
        });

        cardTermService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> param = new HashMap<>();
                param.put("title", "Term of Service");
                param.put("file", "term_of_service.html");
                parent.functionHelper.startIntent(ViewHtmlActivity.class, false, false, param);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog   = new Dialog(parent);
                String title    = "Are you sure ?";
                String message  = "You will logout and exit from application !";
                View.OnClickListener positive = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        parent.functionHelper.clearSession();
                        parent.functionHelper.startIntent(WalkthroughActivity.class, true, true, null);
                    }
                };

                View.OnClickListener negative = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                };

                parent.functionHelper.popupNotification(dialog, R.drawable.confirmation,
                        title, message, positive, negative, false);
            }
        });
    }

    private void processChangePassword(Dialog builder, String password, String newPass, String confirmPass) {
        Map<String, String> param = new HashMap<>();
        param.put("id", parent.functionHelper.getSession("id"));
        param.put("password", password);
        param.put("newPassword", newPass);
        param.put("confirmPassword", confirmPass);
        parent.apiService.changePassword(parent.urlChangePassword, param, new ApiService.hashMapListener() {
            @Override
            public String getHashMap(Map<String, String> hashMap) {
                parent.functionHelper.showToast(hashMap.get("message"), 0);
                if (hashMap.get("status").equals("1"))
                    builder.dismiss();
                return null;
            }
        });
    }

    private void changePassword() {
        final Dialog builder = new Dialog(parent);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        View customView = getLayoutInflater().inflate(R.layout.popup_change_password, null);
        TextView close              = (TextView) customView.findViewById(R.id.close);
        FancyButton submit          = (FancyButton) customView.findViewById(R.id.btnSubmit);
        EditText oldPassword        = (EditText) customView.findViewById(R.id.oldPassword);
        EditText newPassword        = (EditText) customView.findViewById(R.id.newPassword);
        EditText confirmPassword    = (EditText) customView.findViewById(R.id.confirmPassword);
        TextInputLayout contentPassword             = (TextInputLayout) customView.findViewById(R.id.contentPassword);
        TextInputLayout contentNewPassword          = (TextInputLayout) customView.findViewById(R.id.contentNewPassword);
        TextInputLayout contentConfirmPassword      = (TextInputLayout) customView.findViewById(R.id.contentConfirmPassword);

        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                oldPassword.setError(null);
                contentPassword.setPasswordVisibilityToggleEnabled(true);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPassword.setError(null);
                contentNewPassword.setPasswordVisibilityToggleEnabled(true);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmPassword.setError(null);
                contentConfirmPassword.setPasswordVisibilityToggleEnabled(true);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldPassword.getText().toString().isEmpty()) {
                    contentPassword.setPasswordVisibilityToggleEnabled(false);
                    oldPassword.setError("Enter your old password !");
                } if (newPassword.getText().toString().isEmpty()) {
                    contentNewPassword.setPasswordVisibilityToggleEnabled(false);
                    newPassword.setError("Enter your new password !");
                } if (confirmPassword.getText().toString().isEmpty()) {
                    contentConfirmPassword.setPasswordVisibilityToggleEnabled(false);
                    confirmPassword.setError("Enter your confirmation password !");
                } else {
                    if (!confirmPassword.getText().toString().equals(newPassword.getText().toString())) {
                        contentConfirmPassword.setPasswordVisibilityToggleEnabled(false);
                        confirmPassword.setError("Your confirmation password is not match !");
                    } else {
                        processChangePassword(builder, oldPassword.getText().toString(),
                                newPassword.getText().toString(), confirmPassword.getText().toString());
                    }
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        builder.setContentView(customView);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.show();
    }

    private void showContactUs() {
        final Dialog builder = new Dialog(parent);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        View customView = getLayoutInflater().inflate(R.layout.popup_contact, null);
        TextView close              = (TextView) customView.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        builder.setContentView(customView);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.show();
    }
}
