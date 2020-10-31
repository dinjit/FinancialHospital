package com.example.financialhospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.financialhospital.fragments.OtherFragments;
import com.example.financialhospital.fragments.YourInfoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int NUM_PAGES = 5;
    private FrameLayout viewPager;

    CardView cvYourInfo, cvKnowYouBetter, cvKnowYourRisk, cvYourFamily;
    private FragmentStateAdapter pagerAdapter;
    ImageView ivPrevious, ivNext;
    int pageNo = 1;
    FloatingActionButton contactNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        cvYourInfo = findViewById(R.id.cvYourInfo);
        cvKnowYouBetter = findViewById(R.id.cvKnowYouBetter);
        cvKnowYourRisk = findViewById(R.id.cvKnowYourRisk);
        cvYourFamily = findViewById(R.id.cvYourFamily);
        viewPager = findViewById(R.id.pager);
        ivPrevious = findViewById(R.id.ivPrevious);
        ivNext = findViewById(R.id.ivNext);
        contactNow = (FloatingActionButton) findViewById(R.id.contactNow);
        cvYourInfo.setOnClickListener(this);
        cvKnowYouBetter.setOnClickListener(this);
        cvKnowYourRisk.setOnClickListener(this);
        cvYourFamily.setOnClickListener(this);
        ivPrevious.setOnClickListener(this);
        ivNext.setOnClickListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.pager, new YourInfoFragment()).commit();

        resetBackgroud();
        navigateToFragment(pageNo);
        contactNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvYourInfo:
                pageNo = 1;
                break;

            case R.id.cvKnowYouBetter:
                pageNo = 2;
                break;

            case R.id.cvKnowYourRisk:
                pageNo = 3;
                break;

            case R.id.cvYourFamily:
                pageNo = 4;
                break;

            case R.id.ivPrevious:
                pageNo--;
                break;

            case R.id.ivNext:
                pageNo++;
                break;

        }
        resetBackgroud();
        navigateToFragment(pageNo);

    }

    private void resetBackgroud() {
        cvYourInfo.setBackgroundColor(getResources().getColor(R.color.colorAccentLight));
        cvKnowYouBetter.setBackgroundColor(getResources().getColor(R.color.colorAccentLight));
        cvKnowYourRisk.setBackgroundColor(getResources().getColor(R.color.colorAccentLight));
        cvYourFamily.setBackgroundColor(getResources().getColor(R.color.colorAccentLight));

    }

    public void navigateToFragment(int pageNo) {
        switch (pageNo) {
            case 1:
                showFragment(new YourInfoFragment());
                cvYourInfo.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 2:
                OtherFragments otherFragments = new OtherFragments();
                Bundle bundle = new Bundle();
                bundle.putString("fragmentName", "Know You Better");
                otherFragments.setArguments(bundle);
                showFragment(otherFragments);
                cvKnowYouBetter.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                break;
            case 3:
                OtherFragments otherFragments1 = new OtherFragments();
                Bundle bundle1 = new Bundle();
                bundle1.putString("fragmentName", "Know Your Risk");
                otherFragments1.setArguments(bundle1);
                showFragment(otherFragments1);
                cvKnowYourRisk.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                break;
            case 4:

                OtherFragments otherFragments2 = new OtherFragments();
                Bundle bundle2 = new Bundle();
                bundle2.putString("fragmentName", "Your Family");
                otherFragments2.setArguments(bundle2);
                showFragment(otherFragments2);
                cvYourFamily.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                break;
        }
    }

    public void showFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.pager, fragment).commit();
    }
}