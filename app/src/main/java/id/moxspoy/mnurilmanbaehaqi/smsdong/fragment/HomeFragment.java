package id.moxspoy.mnurilmanbaehaqi.smsdong.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.moxspoy.mnurilmanbaehaqi.smsdong.R;
import id.moxspoy.mnurilmanbaehaqi.smsdong.utility.Numbers;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final String MOXSPOY_NUMBER = "085920039600";
    public static final long SMS_TIME_INTERVAL = 1300L;
    private String PERMISSION_TEXT = "permission is ";

    @BindView(R.id.sms_body)
    EditText smsBody;
    @BindView(R.id.sms_send)
    Button sendSmsButton;
    @BindView(R.id.sms_loading)
    ProgressBar loading;

    private SmsManager smsManager;
    private ArrayList<String> numberList;
    private Set<String> numbers;
    private Numbers spNumbers;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        smsManager = SmsManager.getDefault();
        initNumbersFromSharedPref();

        return view;
    }

    private void initNumbersFromSharedPref() {
        numberList = new ArrayList<>();
        spNumbers = new Numbers(getContext());
        numbers = spNumbers.getAllNumber();
        numbers.addAll(numberList);
    }

    @OnClick(R.id.sms_send)
    void sendSMS(){

        //check permision
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.SEND_SMS},
                        PackageManager.PERMISSION_GRANTED);
            }
        } else {
            executeSendingSms();
        }
    }

    private void executeSendingSms() {
        String smsBodyText = smsBody.getText().toString();
        int totalNumber = numberList.size();

        if (numberList.isEmpty()) {
            //showSnackbar("Phone number is empty. Please add first in setting menu");
            numberList.add(MOXSPOY_NUMBER);
        }

        loading.setVisibility(View.VISIBLE);
        //execute
        for (int i = 0; i < totalNumber; i++) {
            Log.d(TAG, "step " + i + ", sending sms to " + numberList.get(i));
            smsManager.sendTextMessage(numberList.get(i), null, smsBodyText, null, null);
            delay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                loading.setProgress(i, true);
            }
        }
        loading.setVisibility(View.GONE);
    }

    private void delay() {
        try {
            Thread.sleep(SMS_TIME_INTERVAL);
        } catch (InterruptedException e) {
            showSnackbar("error while using thread when delay waiting sms");
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(getView(), message,
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 3:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "you granted permission", Toast.LENGTH_SHORT).show();
                    executeSendingSms();
                } else {
                    Toast.makeText(getContext(), "you denied permission. cannot send sms. please try again",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
