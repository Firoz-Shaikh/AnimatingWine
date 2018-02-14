package com.example.firozshaikh.myapplication;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView wineBottle;
    private ObjectAnimator animation, secondAnimation;
    private TextView lblDegree;
    private AnimatorSet animatorSet = new AnimatorSet();
    private AnimatorSet secondAnimatorSet = new AnimatorSet();
    private float currentValue = 0;
    private boolean displayButtonIsPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wineBottle = findViewById(R.id.wineBottle);
        lblDegree = findViewById(R.id.lblDegree);
    }

    public void rotateWine(View view) {

        final View v = view;
        if(animation==null  || !animation.isRunning())
        {
            if(currentValue == 0)
            {
                clearAnimation();
                animation = ObjectAnimator.ofFloat(wineBottle, "rotation", 0, 360);
                animation.setRepeatCount(ObjectAnimator.INFINITE);
                animation.setDuration(1500);
                animation.setInterpolator(new LinearInterpolator());
                animatorSet.play(animation);
                animatorSet.start();
            }
            else
            {
                clearAnimation();
                displayButtonIsPressed = false;
                secondAnimation = ObjectAnimator.ofFloat(wineBottle, "rotation",currentValue,360);
                secondAnimation.setRepeatCount(0);
                secondAnimation.setDuration(1500);
                secondAnimation.setInterpolator(new LinearInterpolator());
                secondAnimatorSet.play(secondAnimation);
                secondAnimatorSet.start();
                secondAnimatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {}

                    @Override
                    public void onAnimationEnd(Animator animator)
                    {
                        currentValue = displayButtonIsPressed ? currentValue : 0;
                        secondAnimatorSet.removeAllListeners();
                        clearAnimation();
                        if(!displayButtonIsPressed)
                        {
                            rotateWine(v);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {}

                    @Override
                    public void onAnimationRepeat(Animator animator) {}
                });
            }
        }
    }

    public void displayWineDegree(View view)
    {
        displayButtonIsPressed = true;
        currentValue = + wineBottle.getRotation();
        lblDegree.setText("Rotation:- "+currentValue);
        clearAnimation();
    }

    public void clearAnimation()
    {
        wineBottle.clearAnimation();
        if(animatorSet!=null)animatorSet.cancel();
        if(secondAnimatorSet!=null)secondAnimatorSet.cancel();
    }
}