package dhcc.com.cn.test_mqtt;

import java.util.List;

/**
 * @author denghang
 * @version V1.0
 * @Package dhcc.com.cn.test_mqtt
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/26 10
 */
public class DeviceUsersResponse {

    /**
     * cmd : get_device_user_resp
     * device_id : 3715d7d7-b59f-48a2-9fb2-441a8ea0c917
     * error_msg : OK
     * error_no : 0
     * from_id :
     * from_type : server_user
     * to_id : 6c06784f-253a-40c3-8d28-99c676385519
     * to_type : user
     * user_list : [{"is_owner":"1","user_id":"6c06784f-253a-40c3-8d28-99c676385519","user_name":"niko"}]
     */

    public String cmd;
    public String device_id;
    public String error_msg;
    public String error_no;
    public String from_id;
    public String from_type;
    public String to_id;
    public String to_type;
    public List<UserListBean> user_list;

    public static class UserListBean {
        /**
         * is_owner : 1
         * user_id : 6c06784f-253a-40c3-8d28-99c676385519
         * user_name : niko
         */

        public String is_owner;
        public String user_id;
        public String user_name;
    }
}
