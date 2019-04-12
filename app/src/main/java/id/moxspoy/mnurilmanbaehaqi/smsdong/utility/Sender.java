package id.moxspoy.mnurilmanbaehaqi.smsdong.utility;

import android.content.Context;
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

    private Context context;
    private List<String> numbers;
    private ProgressBar loading;
    private SmsManager smsManager;

    public Sender(Context context, List<String> numbers, ProgressBar loding) {
        this.context = context;
        this.numbers = numbers;
        this.loading = loding;
        smsManager = SmsManager.getDefault();
    }

    @Override
    protected String doInBackground(String... smsBody) {
        int totalNumber = numbers.size();

        //execute
        for (int i = 0; i < totalNumber; i++) {
            Log.d(TAG, "step " + i + ", sending sms to " + numbers.get(i));
            publishProgress(i);
            try {
                Thread.sleep(SMS_TIME_INTERVAL);
            } catch (InterruptedException e) {
                return "error while using thread when delay waiting sms";
            }

            //smsManager.sendTextMessage(numbers.get(i), null, smsBody, null, null);
        }
        return "Selesai";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading.setMax(numbers.size());
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        loading.setVisibility(View.GONE);
        showSnackbar(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        loading.setProgress(values[0]);
    }

    private void showSnackbar(String message) {
        Snackbar.make(context, message,
                Snackbar.LENGTH_SHORT).show();
    }

}
