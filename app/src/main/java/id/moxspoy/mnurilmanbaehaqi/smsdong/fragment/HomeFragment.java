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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import id.moxspoy.mnurilmanbaehaqi.smsdong.utility.Sender;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final String MOXSPOY_NUMBER = "085920039600";
    private static final String defaultSmsBody = "Assalamualaikum wr wb" +
            "\ndalam menghadapi pemilu 2019" +
            "\nelemen guru ayo gunakan hak pilihnya" +
            "\ningat\n\n" +
            "Dr. Didi Suprijadi MM (Ketua PB PGRI)";
    public long SMS_TIME_INTERVAL = 2000L;
    private String PERMISSION_TEXT = "permission is ";

    @BindView(R.id.sms_body)
    EditText smsBody;
    @BindView(R.id.sms_send)
    Button sendSmsButton;
    @BindView(R.id.sms_loading)
    ProgressBar loading;
    @BindView(R.id.option_time)
    RadioGroup radioGroup;
    @BindView(R.id.option_2_second)
    RadioButton option2;
    @BindView(R.id.option_5_second)
    RadioButton option5;
    @BindView(R.id.option_10_second)
    RadioButton option10;
    @BindView(R.id.option_21_second)
    RadioButton option21;

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

        checkPermission();

        smsManager = SmsManager.getDefault();
        initNumbersFromSharedPref();
        initDefaultSMSBody();
        return view;
    }

    private void initDefaultSMSBody() {
        smsBody.setText(defaultSmsBody);
    }

    private void checkPermission() {
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
        }
    }

    private void initNumbersFromSharedPref() {
        numberList = new ArrayList<>();
        spNumbers = new Numbers(getContext());
        numbers = spNumbers.getAllNumber();

        for (String num : numbers) {
            numberList.add(num);
        }

        Log.d(TAG, "numbers size: " + numbers.size() + ", numberlsit size: " + numberList.size());
    }

    @OnClick(R.id.sms_send)
    void sendSMS(){
        String smsBodyText = smsBody.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = getView().findViewById(selectedId);
        switch (radioButton.getText().toString()) {
            case "2":
                spNumbers.setIntervalTime(2000L);
                break;
            case "5":
                spNumbers.setIntervalTime(5000L);
                break;
            case "10":
                spNumbers.setIntervalTime(10000L);
                break;
            case "21":
                spNumbers.setIntervalTime(21000L);
                break;
        }
        SMS_TIME_INTERVAL = spNumbers.getIntervalTime();
        Log.d(TAG, radioButton.getText().toString()  + " SMS INTERVAL: " + SMS_TIME_INTERVAL);

        if (smsBodyText.isEmpty()) {
            Snackbar.make(getView(), "Fill this message, cannot create sms!",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (numberList.isEmpty()) {
            /*//TODO comment add funtion and uncomment snackbat
            numbers.add("has");
            numbers.add("crot");
            numbers.add("ahh");*/
            Snackbar.make(getView(), "Phone number is empty. Please add first in setting menu",
                    Snackbar.LENGTH_SHORT).show();
        } else {
            Sender sender = new Sender(getView(), numberList, loading);
            sender.execute(smsBodyText);
            smsBody.setText(defaultSmsBody);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 3:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "you granted permission", Toast.LENGTH_SHORT).show();
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
