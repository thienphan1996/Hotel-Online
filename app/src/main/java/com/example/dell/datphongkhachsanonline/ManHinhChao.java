package com.example.dell.datphongkhachsanonline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

/**
 * Created by Dell on 1/14/2018.
 */

public class ManHinhChao extends Activity {

    LinearLayout lnManHinhChao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_manhinhchao);

        lnManHinhChao = findViewById(R.id.lnManHinhChao);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.in);
        lnManHinhChao.setAnimation(animation);

        CountDownTimer timer = new CountDownTimer(1500,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        };
        timer.start();
    }
}
