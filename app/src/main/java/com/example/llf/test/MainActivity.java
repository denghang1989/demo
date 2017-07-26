package com.example.llf.test;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);

        Observable<String> stringObservable = Async.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                return "haha";
            }
        });
        stringObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: "+s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button30:
                startJumpActivity(HLinearLayoutActivity.class);
                break;
            case R.id.button31:
                startJumpActivity(VLinearLayoutActivity.class);
                break;
            case R.id.button32:
                startJumpActivity(BaselineActivity.class);
                break;
            case R.id.button33:
                startJumpActivity(BiasActivity.class);
                break;
            case R.id.button34:
                startJumpActivity(ChainsActivity.class);
                break;
            case R.id.button35:
                startJumpActivity(GoneActivity.class);
                break;
            case R.id.button36:
                startJumpActivity(GuidelineActivity.class);
                break;
            case R.id.button37:
                startJumpActivity(RelativeLayoutActivity.class);
                break;
            case R.id.button38:
                break;
            case R.id.button39:
                break;
            case R.id.button40:
                break;
            case R.id.button41:
                break;
            case R.id.button42:
                break;
            case R.id.button43:
                break;
            case R.id.button44:
                break;
            case R.id.button45:
                break;
            case R.id.button46:
                break;
            case R.id.button47:
                break;
        }
    }

    private void startJumpActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + this.getClassLoader());
        Log.d(TAG, "onResume: " + getApplicationContext().getClassLoader());
        Log.d(TAG, "onResume: " + List.class.getClassLoader());
        Log.d(TAG, "onResume: " + ListView.class.getClassLoader());
        Log.d(TAG, "onResume: " + TextUtils.class.getClassLoader());
        Log.d(TAG, "onResume: " + Activity.class.getClassLoader());
        Log.d(TAG, "onResume: " + BroadcastReceiver.class.getClassLoader());
        Log.d(TAG, "onResume: " + Service.class.getClassLoader());
        Log.d(TAG, "onResume: " + ContentProvider.class.getClassLoader());
    }
}
