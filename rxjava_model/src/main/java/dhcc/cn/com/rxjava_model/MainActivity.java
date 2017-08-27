package dhcc.cn.com.rxjava_model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import dhcc.cn.com.rxjava_model.rxjava.Observable;
import dhcc.cn.com.rxjava_model.rxjava.OnSubscrible;
import dhcc.cn.com.rxjava_model.rxjava.Subscriber;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onClick(view);
            }
        });
    }

    public void onClick(View view) {
        Observable.create(new OnSubscrible<String>() {
            @Override
            public void call(Subscriber<String> subscriber) {
                subscriber.onNext("hehe");
            }
        }).subscrible(new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }
        });
    }
}
