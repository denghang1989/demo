package dhcc.com.cn.test_mqtt;

/**
 * @author denghang
 * @version V1.0
 * @Package dhcc.com.cn.test_mqtt
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/26 10
 */
public class DeviceUsersRequest {

    /**
     * cmd : get_device_user
     * device_id : 3715d7d7-b59f-48a2-9fb2-441a8ea0c917
     * from_id : 6c06784f-253a-40c3-8d28-99c676385519
     * from_type : user
     * to_id :
     * to_type : server_user
     * user_id : 6c06784f-253a-40c3-8d28-99c676385519
     */

    public String cmd;
    public String device_id;
    public String from_id;
    public String from_type;
    public String to_id;
    public String to_type;
    public String user_id;
}
