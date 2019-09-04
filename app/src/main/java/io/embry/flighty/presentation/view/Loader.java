package io.embry.flighty.presentation.view;

import android.app.Activity;
import android.app.Dialog;
import android.widget.ProgressBar;
import io.embry.flighty.R;

public class Loader {

    ProgressBar bar;

     Dialog dialog;

    public Loader(Activity activity) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_loader);
    }

    public void show() {
        dialog.setCancelable(false);
        dialog.show();
    }

    public void hide() {
        dialog.dismiss();
    }
}