package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class LuckyDrawParticipatePojo {


    /**
     * status : 1
     * data : [{"id":"3","lucky_draw_id":"3","user_id":"2","is_winner":"0","created_date":"2020-06-30 17:20:55","name":"hardik"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * lucky_draw_id : 3
         * user_id : 2
         * is_winner : 0
         * created_date : 2020-06-30 17:20:55
         * name : hardik
         */

        private String id;
        private String lucky_draw_id;
        private String user_id;
        private String is_winner;
        private String created_date;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLucky_draw_id() {
            return lucky_draw_id;
        }

        public void setLucky_draw_id(String lucky_draw_id) {
            this.lucky_draw_id = lucky_draw_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getIs_winner() {
            return is_winner;
        }

        public void setIs_winner(String is_winner) {
            this.is_winner = is_winner;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
