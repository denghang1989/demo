package dhcc.com.cn.scroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mScroll;
    private Button mRelease;
    private TextView mTextView;
    private float translation = 20.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScroll = (Button) findViewById(R.id.button2);
        mRelease = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.textView);

        mScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mTextView.setTranslationX(translation);
                translation += 20f;*/

            }
        });

        mRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mTextView.setTranslationX(0f);
                translation = 20.0f;*/
            }
        });
    }
}
