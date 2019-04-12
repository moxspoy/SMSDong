package id.moxspoy.mnurilmanbaehaqi.smsdong.fragment;


import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import id.moxspoy.mnurilmanbaehaqi.smsdong.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipientFragment extends Fragment {

    private static final String TAG = RecipientFragment.class.getSimpleName();
    public RecipientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipient, container, false);
        //sendSms();
        return view;

    }

    private void sendSms() {
        SmsManager smsManager = SmsManager.getDefault();
        List<String> numberList = new ArrayList<>();
        numberList.add("085920039600");
        numberList.add("085741161292");
        String smsBodyText = "Assalamualaykum. Coblos Aan Yusufianto (Caleg DPRD Purwokerto, " +
                "Partai Koncet, No Urut 12";
        int totalNumber = numberList.size();

        for (int i = 0; i < totalNumber; i++) {
            Log.d(TAG, "step " + i + ", sending sms to " + numberList.get(i));
            smsManager.sendTextMessage(numberList.get(i), null, smsBodyText, null, null);
            delay();
        }
    }

    private void delay() {
        try {
            Thread.sleep(HomeFragment.SMS_TIME_INTERVAL);
        } catch (InterruptedException e) {
            showSnackbar("error while using thread when delay waiting sms");
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(getView(), message,
                Snackbar.LENGTH_SHORT).show();
    }

}
