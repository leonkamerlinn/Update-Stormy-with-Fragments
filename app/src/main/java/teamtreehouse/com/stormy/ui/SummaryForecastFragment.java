package teamtreehouse.com.stormy.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamtreehouse.com.stormy.ForecastApplication;
import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.weather.Current;
import teamtreehouse.com.stormy.weather.Day;
import teamtreehouse.com.stormy.weather.Forecast;
import teamtreehouse.com.stormy.weather.Hour;


public class SummaryForecastFragment extends Fragment implements ForecastApplication.NetworkListener {

    public static final String TAG = SummaryForecastFragment.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";


    @BindView(R.id.timeLabel) TextView mTimeLabel;
    @BindView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @BindView(R.id.humidityValue) TextView mHumidityValue;
    @BindView(R.id.precipValue) TextView mPrecipValue;
    @BindView(R.id.summaryLabel) TextView mSummaryLabel;
    @BindView(R.id.iconImageView) ImageView mIconImageView;
    @BindView(R.id.refreshImageView) ImageView mRefreshImageView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    private ForecastApplication mForecastApplication;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_summary_forecast, container, false);
        ButterKnife.bind(this, rootView);
        mForecastApplication = (ForecastApplication)getActivity().getApplication();
        mForecastApplication.setNetworkListener(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        final double latitude = 37.8267;
        final double longitude = -122.423;

        mRefreshImageView.setOnClickListener(v -> mForecastApplication.getForecast(latitude, longitude));
        toggleRefresh();
        updateDisplay();

        Log.d(TAG, "Main UI code is running!");
        return rootView;
    }


    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateDisplay() {
        if (mForecastApplication.getForecast() != null) {
            Current current = mForecastApplication.getForecast().getCurrent();

            mTemperatureLabel.setText(current.getTemperature() + "");
            mTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
            mHumidityValue.setText(current.getHumidity() + "");
            mPrecipValue.setText(current.getPrecipChance() + "%");
            mSummaryLabel.setText(current.getSummary());

            Drawable drawable = getResources().getDrawable(current.getIconId());
            mIconImageView.setImageDrawable(drawable);

            toggleRefresh();
            if (getActivity() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setBackground(current.getTemperature());
            }
        }

    }


    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getActivity().getFragmentManager(), "error_dialog");
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPreResponse() {
        toggleRefresh();
    }

    @Override
    public void onFailure() {
        getActivity().runOnUiThread(this::toggleRefresh);
        alertUserAboutError();
    }

    @Override
    public void onResponse(boolean success) {
        if (success) {
            getActivity().runOnUiThread(this::updateDisplay);
        } else {
            alertUserAboutError();
        }



    }



}














