package com.shanghai.jingzhi.model;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class RespData {

    /**
     * code : 9000
     * respMsg : 登录成功
     * respData : {"sessionid":"1464589874579","username":"123","userlogo":"http://192.168.1.7:8080/IISService_2/UserLogoImages/123.jpg","nickname":"dsjhksdsd"}
     */

    private int code;
    private String respMsg;
    /**
     * sessionid : 1464589874579
     * username : 123
     * userlogo : http://192.168.1.7:8080/IISService_2/UserLogoImages/123.jpg
     * nickname : dsjhksdsd
     */

    private RespDataBean respData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public RespDataBean getRespData() {
        return respData;
    }

    public void setRespData(RespDataBean respData) {
        this.respData = respData;
    }

    public static class RespDataBean {
        private String sessionid;
        private String username;
        private String userlogo;
        private String nickname;

        public String getSessionid() {
            return sessionid;
        }

        public void setSessionid(String sessionid) {
            this.sessionid = sessionid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserlogo() {
            return userlogo;
        }

        public void setUserlogo(String userlogo) {
            this.userlogo = userlogo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
