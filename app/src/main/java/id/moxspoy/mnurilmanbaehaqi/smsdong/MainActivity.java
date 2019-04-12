package id.moxspoy.mnurilmanbaehaqi.smsdong;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.moxspoy.mnurilmanbaehaqi.smsdong.fragment.HomeFragment;
import id.moxspoy.mnurilmanbaehaqi.smsdong.fragment.RecipientFragment;
import id.moxspoy.mnurilmanbaehaqi.smsdong.fragment.SettingFragment;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private int currentFragment;

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private HomeFragment homeFragment = new HomeFragment();
    private RecipientFragment recipientFragment = new RecipientFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupBottomBar();
    }

    private void setupBottomBar() {
        navigation.setOnNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            switchFragments(homeFragment);
                            currentFragment = R.id.navigation_home;
                            invalidateOptionsMenu();
                            return true;
                        case R.id.navigation_recipient:
                            switchFragments(recipientFragment);
                            currentFragment = R.id.navigation_recipient;
                            invalidateOptionsMenu();
                            return true;
                        case R.id.navigation_exit:
                            showExitDialog();
                            return true;
                    }
                    return false;
                }
        );
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    private void switchFragments(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment curFrag = fragmentManager.getPrimaryNavigationFragment();
        if (curFrag != null) {
            transaction.detach(curFrag);
        }
        String tag = fragment.getClass().getSimpleName();
        Fragment fragment1 = fragmentManager.findFragmentByTag(tag);
        if (fragment1 == null) {
            fragment1 = fragment;
            transaction.add(fragmentContainer.getId(), fragment1, tag);
        } else {
            transaction.attach(fragment1);
        }
        transaction.setPrimaryNavigationFragment(fragment1);
        transaction.setReorderingAllowed(true);
        transaction.commitNowAllowingStateLoss();
    }


    private void showExitDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(
                new ContextThemeWrapper(this, R.style.Theme_MaterialComponents_Light_Dialog)
        );
        dialog.setMessage("Are you sure you want to exit from this appication?");
        dialog.setTitle("Exit");
        dialog.setPositiveButton("Exit",
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    finish();
                    System.exit(0);
                });
        dialog.setNegativeButton("Cancel",
                (dialogInterface, i) -> Log.d(TAG, "user cancel logout acount"));
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

}
