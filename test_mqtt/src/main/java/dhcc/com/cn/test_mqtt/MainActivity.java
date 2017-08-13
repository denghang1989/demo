package dhcc.com.cn.test_mqtt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mqttretrofit.Callback;
import com.example.mqttretrofit.MqttRetrofit;
import com.example.mqttretrofit.mqtt.ClientMqttClient;
import com.example.mqttretrofit.mqtt.MqttConnectionOption;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String USER_ID = "C110A1B700045";
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

        final DeviceUsersRequest request = new DeviceUsersRequest();
        request.cmd = "get_device_user";
        request.from_id = "C110A1B700045";
        request.to_id = "";
        request.from_type = "device";
        request.to_type = "server_user";
        request.user_id = "26f62e7ac8a34b208cdf0be6040d2209";
        request.device_id = "C110A1B700045";

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttApi.getDeviceUsers(request).enqueue(new Callback<Response>() {
                    @Override
                    public void onSuccess(Response response) {
                        Log.d(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });
            }
        });

    }
}
