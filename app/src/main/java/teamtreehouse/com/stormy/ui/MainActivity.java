package teamtreehouse.com.stormy.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import teamtreehouse.com.stormy.ForecastApplication;
import teamtreehouse.com.stormy.R;

public class MainActivity extends AppCompatActivity {

    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ForecastApplication forecastApplication = (ForecastApplication) getApplication();
        forecastApplication.setActivity(this);
        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), this);

        mTabLayout = findViewById(R.id.tabLayout);

        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
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
