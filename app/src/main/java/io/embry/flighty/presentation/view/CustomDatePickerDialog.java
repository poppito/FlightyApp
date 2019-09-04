package io.embry.flighty.presentation.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import io.embry.flighty.R;

public class CustomDatePickerDialog {

    private Dialog dialog;
    private DateSetListener listener;
    private String tag;


    DatePicker picker;
    Button btnDone;

    public CustomDatePickerDialog(Activity activity, DateSetListener listener, String tag) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_date_picker);
        this.listener = listener;
        this.tag = tag;
        picker = dialog.findViewById(R.id.view_date_picker);
        btnDone = dialog.findViewById(R.id.btn_done);
    }

    public void show() {
        dialog.setCancelable(false);
        dialog.show();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDateSet(picker.getYear(), picker.getMonth(), picker.getDayOfMonth(), tag);
                dialog.dismiss();
            }
        });
    }

    public interface DateSetListener {
        void onDateSet(int year, int month, int dayOfTheMonth, String tag);
    }
}