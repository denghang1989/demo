package dhcc.com.cn.test_mqtt;

import com.example.mqttretrofit.Call;
import com.example.mqttretrofit.annotation.Cmd;
import com.example.mqttretrofit.annotation.QueryMap;
import com.example.mqttretrofit.annotation.Topic;

import java.util.Map;

/**
 * @date 2017/7/14 14
 */
public interface MqttApi {

    @Cmd("get_system_device_list")
    @Topic("cloudring/server/user/1.0/")
    Call<Response> getSystemSupportDevice(@QueryMap Map<Integer,String> map);

}
