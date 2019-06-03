package com.you.cauchy.activity.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.you.cauchy.R;
import com.you.cauchy.activity.BaseFragment;
import com.you.cauchy.activity.fragment.AccountFra;
import com.you.cauchy.activity.fragment.CourseFra;
import com.you.cauchy.activity.fragment.StudyFra;

public class HomeAct extends AppCompatActivity {
    private BaseFragment[] fragments;
    private int lastIndex = -1;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(0);
                    return true;
                case R.id.navigation_course:
                    switchFragment(1);
                    return true;
                case R.id.navigation_account:
                    switchFragment(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragment();
        switchFragment(0);
    }

    private void initFragment() {
        fragments = new BaseFragment[3];
        fragments[0] = new CourseFra();
        fragments[1] = new StudyFra();
        fragments[2] = new AccountFra();
    }


    // 切换Fragment
    private void switchFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (lastIndex == index){
            return ;
        }
        transaction.replace(R.id.frameLayout, fragments[index]);
        transaction.addToBackStack(null);
        transaction.commit();
        lastIndex = index;
    }

    public static void startHomeActivity(Context context){
        Intent intent = new Intent(context, HomeAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
}
