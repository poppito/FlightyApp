package io.embry.flighty.presentation.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.embry.flighty.R;
import io.embry.flighty.app.FlightyApp;
import io.embry.flighty.data.FlightData;
import io.embry.flighty.injection.ActivityComponent;
import io.embry.flighty.injection.ActivityModule;
import io.embry.flighty.injection.DaggerActivityComponent;
import io.embry.flighty.presentation.presenters.MainPresenter;
import io.embry.flighty.presentation.presenters.MainPresenterContract;

import javax.inject.Inject;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MainPresenter.ViewSurface,
        TextWatcher,
        CustomDatePickerDialog.DateSetListener {

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
    MainPresenterContract presenter;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindView(R.id.recycler_flight_list)
    RecyclerView flightList;

    @BindView(R.id.layout_query_container)
    LinearLayout queryContainer;

    @BindView(R.id.layout_flight_list_container)
    RelativeLayout flightContainer;

    @BindView(R.id.btn_search_again)
    Button btnSearchAgain;

    private AlertDialog cachedInformationDialog;

    private static final String TAG_RETURN_DATE = "returnDate";
    private static final String TAG_DEPARTURE_DATE = "departureDate";

    private Loader loader;


    //region lifecycle
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loader = new Loader(this);
        inject();
        ButterKnife.bind(this);
        presenter.onStart(this);

        departureDate.setOnClickListener(view -> {
            presenter.handleDepartureDateClick();
        });

        returnDate.setOnClickListener(view -> {
            presenter.handleReturnDateClick();
        });

        btnInfoArrival.setOnClickListener(view -> {
            getInfoDialog();
        });

        btnInfoDeparture.setOnClickListener(view -> {
            getInfoDialog();
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.handleSubmitClicked();
            }
        });

        btnSearchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryContainer.setVisibility(View.VISIBLE);
                flightContainer.setVisibility(View.GONE);
            }
        });

        departureAirport.addTextChangedListener(this);
        arrivalAirport.addTextChangedListener(this);
    }
    //endregion

    //region view surface
    @Override
    public void showLoader() {
        loader.show();
    }

    @Override
    public void hideLoader() {
        loader.hide();
    }

    @Override
    public void setDepartureDate(String date) {
        departureDate.setText(date);
    }

    @Override
    public void setReturnDate(String date) {
        returnDate.setText(date);
    }

    @Override
    public void handleDepartureDateChoices() {
        CustomDatePickerDialog dialog = new CustomDatePickerDialog(this, this, TAG_DEPARTURE_DATE);
        dialog.show();
    }

    @Override
    public void handleReturnDateChoices() {
        CustomDatePickerDialog dialog = new CustomDatePickerDialog(this, this, TAG_RETURN_DATE);
        dialog.show();
    }

    @Override
    public void showDateError() {
        new AlertDialog.Builder(this)
                .setIcon(getDrawable(R.mipmap.ic_launcher))
                .setTitle(getString(R.string.txt_title_error))
                .setMessage(getString(R.string.txt_date_error))
                .setPositiveButton(getString(R.string.txt_btn_ok), null)
                .show();
    }

    //endregion

    //region DateSetListener
    @Override
    public void onDateSet(int year, int month, int dayOfTheMonth, String tag) {
        if (tag.equalsIgnoreCase(TAG_DEPARTURE_DATE)) {
            presenter.handleDepartureDateSet(year, month, dayOfTheMonth);
        } else if (tag.equalsIgnoreCase(TAG_RETURN_DATE)) {
            presenter.handleReturnDateSet(year, month, dayOfTheMonth);
        }
    }
    //endregion

    //region textwatcher
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.hashCode() == arrivalAirport.getText().hashCode()) {
            presenter.handleArrivalAirportInput(s.toString());
        } else if (s.hashCode() == departureAirport.getText().hashCode()) {
            presenter.handleDepartureAirportInput(s.toString());
        }
    }

    @Override
    public void showDepartureAirportError() {
        showError(getString(R.string.txt_departure_airport_error));
    }

    @Override
    public void showArrivalAirportError() {
        showError(getString(R.string.txt_arrival_airport_error));
    }

    @Override
    public void showErrorResponse(String error) {
        showError(error);
    }

    @Override
    public void showFlightData(List<FlightData> flightDataList) {
        queryContainer.setVisibility(View.GONE);
        FlightDataAdapter dataAdapter = new FlightDataAdapter(flightDataList, this);
        flightContainer.setVisibility(View.VISIBLE);
        flightList.setAdapter(dataAdapter);
        flightList.setLayoutManager(new LinearLayoutManager(this));
    }

    //endregion


    //region private
    private void getInfoDialog() {
        if (cachedInformationDialog == null) {
            cachedInformationDialog = new AlertDialog.Builder(this)
                    .setIcon(getDrawable(R.mipmap.ic_launcher))
                    .setMessage(getString(R.string.txt_info_validation))
                    .setTitle(getString(R.string.txt_title_airport_validation))
                    .setPositiveButton(getString(R.string.txt_btn_ok), null)
                    .create();
        }
        cachedInformationDialog.show();
    }

    private void showError(String error) {
        new AlertDialog.Builder(this)
                .setIcon(getDrawable(R.mipmap.ic_launcher))
                .setMessage(error)
                .setTitle(getString(R.string.txt_title_error))
                .setPositiveButton(getString(R.string.txt_btn_ok), null)
                .create()
                .show();
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
