package id.moxspoy.mnurilmanbaehaqi.smsdong.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static id.moxspoy.mnurilmanbaehaqi.smsdong.fragment.HomeFragment.SMS_TIME_INTERVAL;

public class Sender extends AsyncTask<String, Integer, String> {

    private static final String TAG = "SenderAsynTask";

    private View view;
    private List<String> numbers;
    private ProgressBar loading;
    private SmsManager smsManager;
    private ProgressDialog progressDialog;

    public Sender(View view, List<String> numbers, ProgressBar loding) {
        this.view = view;
        this.numbers = numbers;
        this.loading = loding;
        smsManager = SmsManager.getDefault();
        progressDialog = new ProgressDialog(view.getContext());
    }

    @Override
    protected String doInBackground(String... smsBody) {
        int totalNumber = numbers.size();

        Log.d(TAG, "start executing..");
        //execute
        for (int i = 0; i < totalNumber; i++) {
            Log.d(TAG, "step " + i + ", sending sms to " + numbers.get(i));
            try {
                Thread.sleep(SMS_TIME_INTERVAL);
                publishProgress(i);
            } catch (InterruptedException e) {
                return "error while using thread when delay waiting sms";
            }

            //Log.d(TAG, smsBody[0]);
            smsManager.sendTextMessage(numbers.get(i), null, smsBody[0], null, null);
        }
        return "Selesai";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
        loading.setVisibility(View.GONE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        showSnackbar(s);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setMessage("sending.. " + (values[0] + 1) + "/" + numbers.size());
    }

    private void showSnackbar(String message) {
        Snackbar.make(view, message,
                Snackbar.LENGTH_SHORT).show();
    }

}
