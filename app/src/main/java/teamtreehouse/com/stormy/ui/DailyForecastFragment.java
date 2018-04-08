package teamtreehouse.com.stormy.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamtreehouse.com.stormy.ForecastApplication;
import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.adapters.DayAdapter;
import teamtreehouse.com.stormy.weather.Day;

public class DailyForecastFragment extends Fragment {

    private Day[] mDays;

    @BindView(android.R.id.list) ListView mListView;
    @BindView(android.R.id.empty) TextView mEmptyTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily_forecast, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {

        ForecastApplication forecastApplication = (ForecastApplication)getActivity().getApplication();
        mDays = forecastApplication.getForecast().getDailyForecast();

        mListView.setAdapter(forecastApplication.getDayAdapter());
        mListView.setEmptyView(mEmptyTextView);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            String dayOfTheWeek = mDays[position].getDayOfTheWeek();
            String conditions = mDays[position].getSummary();
            String highTemp = mDays[position].getTemperatureMax() + "";
            String message = String.format("On %s the high will be %s and it will be %s",
                    dayOfTheWeek,
                    highTemp,
                    conditions);

            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        });
    }
}









