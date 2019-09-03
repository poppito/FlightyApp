package io.embry.flighty.presentation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;

public class AnimationUtil {

    public static void scaleUpAnimation(ImageView view, int duration, float fromX, float fromY) {
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", fromX);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", fromY);

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f);

        scaleUpX.setDuration(0);
        scaleUpY.setDuration(0);
        scaleDownX.setDuration(duration);
        scaleDownY.setDuration(duration);
        AnimatorSet scaleUp = new AnimatorSet();
        AnimatorSet scaleDown = new AnimatorSet();
        AnimatorSet scale = new AnimatorSet();
        scaleUp.play(scaleUpX).with(scaleUpY);
        scaleDown.play(scaleDownX).with(scaleDownY);
        scale.playSequentially(scaleUp, scaleDown);
        scale.start();
    }

    public static void translateAnimation(ImageView view, int duration, float fromTx, float fromTy) {
        ObjectAnimator endTx = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, fromTx, 0f);
        ObjectAnimator endTy = ObjectAnimator.ofFloat(view , View.TRANSLATION_Y, fromTy, 0f);

        endTx.setDuration(duration);
        endTy.setDuration(duration);

        AnimatorSet end = new AnimatorSet();
        end.play(endTx).with(endTy);
        end.start();
    }
}
