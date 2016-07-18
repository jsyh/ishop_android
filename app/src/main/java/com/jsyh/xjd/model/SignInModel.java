package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by Su on 2016/6/21.
 */
public class SignInModel extends BaseModel{


    /**
     * points : 10
     * record : [{"from":"1","id":"1","name":"签到","points":"10","state":"1","time":"1466478184","userid":"5"},{"from":"1","id":"8","name":"签到","points":"10","state":"1","time":"1466488779","userid":"5"}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String points;
        /**
         * from : 1
         * id : 1
         * name : 签到
         * points : 10
         * state : 1
         * time : 1466478184
         * userid : 5
         */

        private List<RecordBean> record;

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public List<RecordBean> getRecord() {
            return record;
        }

        public void setRecord(List<RecordBean> record) {
            this.record = record;
        }

        public static class RecordBean {
            private String from;
            private String id;
            private String name;
            private String points;
            private String state;
            private String time;
            private String userid;

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }
        }
    }
}
