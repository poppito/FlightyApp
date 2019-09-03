package io.embry.flighty.presentation;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.embry.flighty.R;
import io.embry.flighty.app.FlightyApp;
import io.embry.flighty.injection.ActivityComponent;
import io.embry.flighty.injection.ActivityModule;
import io.embry.flighty.injection.DaggerActivityComponent;
import io.embry.flighty.presentation.presenters.MainPresenter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txt_arrival_airport)
    EditText arrivalAirport;

    @BindView(R.id.txt_departure_airport)
    EditText departureAirport;

    @BindView(R.id.txt_departure_date)
    TextView departureDate;

    @BindView(R.id.txt_return_date)
    TextView returnDate;

    @BindView(R.id.btn_info_arrival)
    ImageView btnInfoArrival;

    @BindView(R.id.btn_info_departure)
    ImageView btnInfoDeparture;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inject();
        ButterKnife.bind(this);
        //setDepartureDate();
        //setReturnDate();

        departureDate.setOnClickListener(view -> {
            //onClick
        });

        returnDate.setOnClickListener(view -> {
            //onClick
        });

        btnInfoArrival.setOnClickListener(view -> {
            getInfoDialog().show();
        });

        btnInfoDeparture.setOnClickListener(view -> {
            getInfoDialog().show();
        });

        presenter.onStart();
    }


    //region private
    private AlertDialog getInfoDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(getDrawable(R.mipmap.ic_launcher))
                .setMessage(getString(R.string.txt_info_validation))
                .setTitle(getString(R.string.txt_title_airport_validation))
                .setPositiveButton(getString(R.string.txt_btn_ok), ((it, which) -> {
                    it.dismiss();
                }))
                .create();
        return dialog;
    }

    private void inject() {
        FlightyApp app = (FlightyApp) getApplication();
        ActivityComponent component = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(app.appComponent)
                .build();
        component.inject(this);
    }
    //endregion
}
