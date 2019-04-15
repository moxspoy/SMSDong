package id.moxspoy.mnurilmanbaehaqi.smsdong.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class Numbers {
    private Context context;
    private static final String SP_NAME = "SP_NUMBERS";
    private static final String SP_GET_NUMBER = "GET_NUMBERS";
    private static final String SP_GET_TIME = "GET_SMS_INTERVAL";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;

    private Set<String> numbers = new HashSet<>();

    public Numbers(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
    }

    public void addNumber(String number) {
        Set<String> set = sharedPreferences.getStringSet(SP_GET_NUMBER, new HashSet<>());
        Set<String> in = new HashSet<>(set);
        in.add(number);

        edit = sharedPreferences.edit();

        edit.putStringSet(SP_GET_NUMBER, in);
        Log.d("onSharedPref", "number size " + in.size());
        edit.apply();
    }


    public Set<String> getAllNumber() {
        return sharedPreferences.getStringSet(SP_GET_NUMBER, new HashSet<>());
    }

    public void setIntervalTime(long time) {
        edit = sharedPreferences.edit();
        edit.putLong(SP_GET_TIME, time);
        edit.apply();
    }

    public long getIntervalTime() {
        return sharedPreferences.getLong(SP_GET_TIME, 2000L);
    }

    public void deleteNumber() {
        edit = sharedPreferences.edit();
        edit.remove(SP_GET_NUMBER);
        edit.apply();
    }
}
