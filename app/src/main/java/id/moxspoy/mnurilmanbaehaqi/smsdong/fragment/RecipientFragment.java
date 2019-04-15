package id.moxspoy.mnurilmanbaehaqi.smsdong.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.moxspoy.mnurilmanbaehaqi.smsdong.MainActivity;
import id.moxspoy.mnurilmanbaehaqi.smsdong.R;
import id.moxspoy.mnurilmanbaehaqi.smsdong.utility.Numbers;
import ir.sohreco.androidfilechooser.ExternalStorageNotAvailableException;
import ir.sohreco.androidfilechooser.FileChooser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipientFragment extends Fragment implements FileChooser.ChooserListener {

    private static final String TAG = RecipientFragment.class.getSimpleName();
    private ArrayList<String> numberList;
    private Set<String> numbers;
    private Numbers spNumbers;

    @BindView(R.id.list_number)
    ListView listNumberView;
    @BindView(R.id.recipient_text)
    TextView tvRecipientTitle;
    @BindView(R.id.btn_add_recipient)
    FloatingActionButton addBtn;
    @BindView(R.id.btn_delete_recipient)
    FloatingActionButton deleteBtn;

    public RecipientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipient, container, false);
        ButterKnife.bind(this, view);
        //sendSms();
        checkPermission();
        initNumbersFromSharedPref();
        initList();
        return view;

    }

    private void checkPermission() {
        //check permision
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PackageManager.PERMISSION_GRANTED);
            }
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
                    Toast.makeText(getContext(), "you denied permission. cannot read storage. please try again",
                            Toast.LENGTH_SHORT).show();
                }
                break;
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

    private void initList() {
        if (!numberList.isEmpty()) {
            deleteBtn.setVisibility(View.VISIBLE);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                R.layout.item_number_list, R.id.text_view_number, numberList);
        listNumberView.setAdapter(arrayAdapter);

        String textTitleInRecipient = getString(R.string.daftar_nomor_hape) + " (" + numberList.size() + ")";
        tvRecipientTitle.setText(textTitleInRecipient);
    }


    private void showSnackbar(String message) {
        Snackbar.make(getView(), message,
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSelect(String path) {
        writeDataToCSV(path);
        toMainActivity();

    }

    private void writeDataToCSV(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                String number = line.replace(",","").replace(" ", "").trim();
                Log.d(TAG, "read: " + number);
                if (!number.isEmpty()) {
                    spNumbers.addNumber(number);
                }
                line = reader.readLine();
            }
            reader.close();
            fileReader.close();
        } catch (IOException e) {
            showSnackbar("error: " + e.getMessage());
        }
        showSnackbar("Success add data. Refresh this page");
    }

    private void toMainActivity() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    @OnClick(R.id.btn_add_recipient)
    void addRecipient() {
        FileChooser.Builder builder = new FileChooser.Builder(FileChooser.ChooserType.FILE_CHOOSER,
                this)
                .setMultipleFileSelectionEnabled(false)
                .setFileFormats(new String[]{".csv", ".txt", ".xlsx", ".xls"});

        try {
            FileChooser fileChooser = builder.build();
            getFragmentManager().beginTransaction().add(R.id.frame_choose_file, fileChooser).commit();
        } catch (ExternalStorageNotAvailableException e) {
            showSnackbar(e.getMessage());
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_delete_recipient)
    void deleteRecipient() {
        spNumbers.deleteNumber();
        toMainActivity();
    }

}
