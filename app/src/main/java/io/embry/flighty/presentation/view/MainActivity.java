package io.embry.flighty.presentation.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
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
import io.embry.flighty.presentation.viewmodels.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
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

    @BindView(R.id.view_progressbar)
    ProgressBar progressBar;

    private AlertDialog cachedInformationDialog;

    private static final String TAG_RETURN_DATE = "returnDate";
    private static final String TAG_DEPARTURE_DATE = "departureDate";

    private MainViewModel viewModel;

    //region lifecycle
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        inject();

        departureDate.setOnClickListener(view -> {
            showDepartureDateDialog();
        });

        returnDate.setOnClickListener(view -> {
            showReturnDateDialog();
        });

        btnInfoArrival.setOnClickListener(view -> {
            getInfoDialog();
        });

        btnInfoDeparture.setOnClickListener(view -> {
            getInfoDialog();
        });

        btnSubmit.setOnClickListener(view -> {
            handleSubmitClicked();
        });

        btnSearchAgain.setOnClickListener(view -> {
                queryContainer.setVisibility(View.VISIBLE);
                flightContainer.setVisibility(View.GONE);
        });

        departureAirport.addTextChangedListener(this);
        arrivalAirport.addTextChangedListener(this);

        returnDate.setText(viewModel.getInitialReturnDate());
        departureDate.setText(viewModel.getInitialDepartureDate());
    }

    //endregion

    //region private

    public void handleSubmitClicked() {
        if (viewModel.isQueryDataComplete()) {
            if (viewModel.validateReturnDate()) {
                showLoader();
                viewModel.getFlightData().observe(this, this::showFlightData);
            } else {
                showDateError();
            }
        } else {
            if (viewModel.getArrivalCode() == null || viewModel.getArrivalCode().isEmpty()) {
                showArrivalAirportError();
            } else if (viewModel.getDepartureCode() == null || viewModel.getDepartureCode().isEmpty()) {
                showDepartureAirportError();
            }
        }
    }

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
        viewModel.setFlightService(app.service);
    }

    private void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }


    private void hideLoader() {
        progressBar.setVisibility(View.GONE);
    }


    private void showDepartureDateDialog() {
        CustomDatePickerDialog dialog = new CustomDatePickerDialog(this, this, TAG_DEPARTURE_DATE);
        dialog.show();
    }


    private void showReturnDateDialog() {
        CustomDatePickerDialog dialog = new CustomDatePickerDialog(this, this, TAG_RETURN_DATE);
        dialog.show();
    }


    private void showDateError() {
        new AlertDialog.Builder(this)
                .setIcon(getDrawable(R.mipmap.ic_launcher))
                .setTitle(getString(R.string.txt_title_error))
                .setMessage(getString(R.string.txt_date_error))
                .setPositiveButton(getString(R.string.txt_btn_ok), null)
                .show();
    }

    private void showDepartureAirportError() {
        showError(getString(R.string.txt_departure_airport_error));
    }


    private void showArrivalAirportError() {
        showError(getString(R.string.txt_arrival_airport_error));
    }


    private void showErrorResponse(String error) {
        showError(error);
    }


    private void showFlightData(List<FlightData> flightDataList) {
        hideLoader();
        queryContainer.setVisibility(View.GONE);
        FlightDataAdapter dataAdapter = new FlightDataAdapter(flightDataList, this);
        flightContainer.setVisibility(View.VISIBLE);
        flightList.setAdapter(dataAdapter);
        flightList.setLayoutManager(new LinearLayoutManager(this));
    }


    //endregion

    //region DateSetListener
    @Override
    public void onDateSet(int year, int month, int dayOfTheMonth, String tag) {
        if (tag.equalsIgnoreCase(TAG_DEPARTURE_DATE)) {
            viewModel.setDepartureDate(year, month, dayOfTheMonth);
        } else if (tag.equalsIgnoreCase(TAG_RETURN_DATE)) {
            viewModel.setReturnDate(year, month, dayOfTheMonth);
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
            viewModel.setArrivalCode(arrivalAirport.getText().toString());
        } else if (s.hashCode() == departureAirport.getText().hashCode()) {
            viewModel.setDepartureCode(departureAirport.getText().toString());
        }
    }
    //endregion
}
