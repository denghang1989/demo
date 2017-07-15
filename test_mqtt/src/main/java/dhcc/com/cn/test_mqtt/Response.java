package dhcc.com.cn.test_mqtt;

import java.util.List;

/**
 * @author denghang
 * @version V1.0
 * @Package dhcc.com.cn.test_mqtt
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/14 17
 */
public class Response {

    public List<DeviceListBean> device_list;

    public static class DeviceListBean {
        /**
         * config_url :
         * connect_type : 1
         * control_url :
         * control_version :
         * device_type_id : b5dc9ac05ae54eb4b17e3fb308558504
         * device_type_name : 神灯1.0
         * is_parent_device : 0
         * module : FG-SDV1.0
         * picture : http://adsys.cloudring.net/55191a114bd547529863b3a418d7f944.jpg
         * device_version : 11
         */

        public String config_url;
        public int connect_type;
        public String control_url;
        public String control_version;
        public String device_type_id;
        public String device_type_name;
        public int is_parent_device;
        public String module;
        public String picture;
        public String device_version;

        @Override
        public String toString() {
            return "DeviceListBean{" +
                    "config_url='" + config_url + '\'' +
                    ", connect_type=" + connect_type +
                    ", control_url='" + control_url + '\'' +
                    ", control_version='" + control_version + '\'' +
                    ", device_type_id='" + device_type_id + '\'' +
                    ", device_type_name='" + device_type_name + '\'' +
                    ", is_parent_device=" + is_parent_device +
                    ", module='" + module + '\'' +
                    ", picture='" + picture + '\'' +
                    ", device_version='" + device_version + '\'' +
                    '}';
        }
    }

}
