package id.moxspoy.mnurilmanbaehaqi.smsdong.utility;

import android.content.Context;
import android.content.SharedPreferences;


import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class Numbers {
    private Context context;
    private static final String SP_NAME = "SP_NUMBERS";
    private static final String SP_GET_NUMBER = "GET_NUMBERS";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;

    private Set<String> numbers = new HashSet<>();

    public Numbers(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
    }

    public void addNumber(String number) {
        edit = sharedPreferences.edit();
        edit.putStringSet(number, numbers);
        edit.apply();
    }


    public Set<String> getAllNumber() {
        return sharedPreferences.getStringSet(SP_GET_NUMBER, numbers);
    }
}
