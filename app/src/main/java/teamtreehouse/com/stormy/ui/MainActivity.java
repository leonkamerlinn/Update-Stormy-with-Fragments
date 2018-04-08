package teamtreehouse.com.stormy.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import teamtreehouse.com.stormy.ForecastApplication;
import teamtreehouse.com.stormy.R;

public class MainActivity extends AppCompatActivity {

    private static final String SUMMARY_FORECAST_TAG = "summary_forecast_tag";
    private static final String HOURLY_FORECAST_TAG = "hourly_forecast_tag";
    private static final String DAILY_FORECAST_TAG = "daily_forecast_tag";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ForecastApplication forecastApplication = (ForecastApplication) getApplication();
        forecastApplication.setActivity(this);



        mTabLayout = findViewById(R.id.tabLayout);
        if (mTabLayout != null) {
            mViewPager = findViewById(R.id.pager);
            mViewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(), this));
            mTabLayout.setupWithViewPager(mViewPager);
        } else {

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.left, new SummaryForecastFragment(), SUMMARY_FORECAST_TAG)
                        .add(R.id.center, new HourlyForecastFragment(), HOURLY_FORECAST_TAG)
                        .add(R.id.right, new DailyForecastFragment(), DAILY_FORECAST_TAG)
                        .commit();


        }



    }

    public void setBackground(int temperature) {
          if (temperature > 70) {
                //hot
              findViewById(R.id.root).setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_hot));
          } else if(temperature > 50 && temperature <= 70) {
                //cool
              findViewById(R.id.root).setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_cool));
          } else {
                //cold
              findViewById(R.id.root).setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_cold));
          }

    }


    class CustomPagerAdapter extends FragmentPagerAdapter {
        Context mContext;
        public CustomPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new SummaryForecastFragment();
            } else if (position == 1) {
                return new HourlyForecastFragment();
            } else if (position == 2) {
                return new DailyForecastFragment();
            }
            return new Fragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.summary);
                case 1:
                    return getString(R.string.hourly);
                case 2:
                    return getString(R.string.daily);
                default:
                    return null;
            }
        }
    }


}
