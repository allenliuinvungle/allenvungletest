package com.example.allenvungletest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class SecondActivity extends Activity {
    Button button;
    public final String  TAG="allentest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
//        View decorView = getWindow().getDecorView();
////
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//        decorView.setSystemUiVisibility(uiOptions);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitActivity();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        makeToast("onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        makeToast("onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        makeToast("onStop");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        makeToast("onNewIntent");

    }
    private void makeToast(String message) {
        Log.e(TAG,message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        makeToast("onSaveInstanceState");

    }
    private void exitActivity(){
        this.finish();
    }
}
