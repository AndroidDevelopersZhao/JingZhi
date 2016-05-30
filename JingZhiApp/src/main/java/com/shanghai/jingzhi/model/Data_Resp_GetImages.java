package com.shanghai.jingzhi.model;

import java.util.List;

/**
 * Created by zhaowenyun on 16/5/30.
 */
public class Data_Resp_GetImages {

    /**
     * code : 9000
     * respMsg : 索引成功
     * respData : {"images":[{"username":"123","city":"上海市","lat":"31.302773","lnt":"121.328148","likeNumbers":"0","likeUsers":"null","url":"http://192.168.1.7:8080/IISService_2/Images/1464369422915.jpg","userlogo":"http://q.qlogo.cn/qqapp/1104950333/B0EAC5ED9B0D11672A385C91B9BB0DD9/100","nickName":"系统管理员"},{"username":"123","city":"上海市","lat":"31.302744","lnt":"121.328124","likeNumbers":"0","likeUsers":"null","url":"http://192.168.1.7:8080/IISService_2/Images/1464444514957.jpg","userlogo":"http://q.qlogo.cn/qqapp/1104950333/B0EAC5ED9B0D11672A385C91B9BB0DD9/100","nickName":"系统管理员"},{"username":"123","city":"上海市","lat":"31.302744","lnt":"121.328124","likeNumbers":"0","likeUsers":"null","url":"http://192.168.1.7:8080/IISService_2/Images/1464444561051.jpg","userlogo":"http://q.qlogo.cn/qqapp/1104950333/B0EAC5ED9B0D11672A385C91B9BB0DD9/100","nickName":"系统管理员"},{"username":"123","city":"上海市","lat":"31.302744","lnt":"121.328124","likeNumbers":"0","likeUsers":"null","url":"http://192.168.1.7:8080/IISService_2/Images/1464444581453.jpg","userlogo":"http://q.qlogo.cn/qqapp/1104950333/B0EAC5ED9B0D11672A385C91B9BB0DD9/100","nickName":"系统管理员"},{"username":"123","city":"上海市","lat":"31.302787","lnt":"121.328255","likeNumbers":"0","likeUsers":"null","url":"http://192.168.1.7:8080/IISService_2/Images/1464451147138.jpg","userlogo":"http://q.qlogo.cn/qqapp/1104950333/B0EAC5ED9B0D11672A385C91B9BB0DD9/100","nickName":"系统管理员"},{"username":"123","city":"上海市","lat":"31.302648","lnt":"121.328169","likeNumbers":"0","likeUsers":"null","url":"http://192.168.1.7:8080/IISService_2/Images/1464456374145.jpg","userlogo":"http://q.qlogo.cn/qqapp/1104950333/B0EAC5ED9B0D11672A385C91B9BB0DD9/100","nickName":"系统管理员"},{"username":"123","city":"上海市","lat":"31.302817","lnt":"121.328256","likeNumbers":"0","likeUsers":"null","url":"http://192.168.1.7:8080/IISService_2/Images/1464462457473.jpg","userlogo":"http://q.qlogo.cn/qqapp/1104950333/B0EAC5ED9B0D11672A385C91B9BB0DD9/100","nickName":"系统管理员"},{"username":"123","city":"上海市","lat":"31.302811","lnt":"121.328258","likeNumbers":"0","likeUsers":"null","url":"http://192.168.1.7:8080/IISService_2/Images/1464491909107.jpg","userlogo":"http://q.qlogo.cn/qqapp/1104950333/B0EAC5ED9B0D11672A385C91B9BB0DD9/100","nickName":"系统管理员"}]}
     */

    private int code;
    private String respMsg;
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
        /**
         * username : 123
         * city : 上海市
         * lat : 31.302773
         * lnt : 121.328148
         * likeNumbers : 0
         * likeUsers : null
         * url : http://192.168.1.7:8080/IISService_2/Images/1464369422915.jpg
         * userlogo : http://q.qlogo.cn/qqapp/1104950333/B0EAC5ED9B0D11672A385C91B9BB0DD9/100
         * nickName : 系统管理员
         */

        private List<ImagesBean> images;

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            private String username;
            private String city;
            private String lat;
            private String lnt;
            private String likeNumbers;
            private String likeUsers;
            private String url;
            private String userlogo;
            private String nickName;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLnt() {
                return lnt;
            }

            public void setLnt(String lnt) {
                this.lnt = lnt;
            }

            public String getLikeNumbers() {
                return likeNumbers;
            }

            public void setLikeNumbers(String likeNumbers) {
                this.likeNumbers = likeNumbers;
            }

            public String getLikeUsers() {
                return likeUsers;
            }

            public void setLikeUsers(String likeUsers) {
                this.likeUsers = likeUsers;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUserlogo() {
                return userlogo;
            }

            public void setUserlogo(String userlogo) {
                this.userlogo = userlogo;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }
        }
    }
}
