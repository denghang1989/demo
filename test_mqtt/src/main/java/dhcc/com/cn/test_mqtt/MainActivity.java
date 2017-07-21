package dhcc.com.cn.test_mqtt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.mqttretrofit.Callback;
import com.example.mqttretrofit.MqttRetrofit;
import com.example.mqttretrofit.mqtt.ClientMqttClient;
import com.example.mqttretrofit.mqtt.MqttConnectionOption;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String USER_ID = "1ce9dcf2d2df41cda8b6bab28934293b";
    private static final String MOBILE = "13632932016";
    private static final String PASSWORD = "677b1f01bc36bde2e2e802277411354f";
    private static final String serverUrl = "tcp://iot.cloudring.net:1885";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
        MqttConnectionOption option = new MqttConnectionOption.Builder().userId(USER_ID).baseUrl(serverUrl).mobile(MOBILE).build();
        ClientMqttClient client = new ClientMqttClient(option);
        client.connect();

        MqttRetrofit mqttRetrofit = new MqttRetrofit.Builder().setMqttClient(client).build();
        final MqttApi mqttApi = mqttRetrofit.create(MqttApi.class);

        final Request request = new Request();
        request.cmd = "get_system_device_list";
        request.from_id = "1ce9dcf2d2df41cda8b6bab28934293b";
        request.to_id = "";
        request.from_type = "user";
        request.to_type = "server_user";
        request.user_id = "1ce9dcf2d2df41cda8b6bab28934293b";
//        final String toJson = gson.toJson(request);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttApi.getSystemSupportDevice(request).enqueue(new Callback<Response>() {
                    @Override
                    public void onSuccess(Response response) {
                        Logger.d(response.device_list.toString());
                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });
            }
        });
    }
}
