package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class TodayMatchPojo {

    /**
     * status : 1
     * data : [{"id":"5","name":"Match 5","win_price":"800","per_kill":"20","entry_fee":"40","type":"Duo","version":"TPP","map":"Erangel","detail":"Room id and password will share before 15 minute of match start","status":"0","total_spot":"100","match_time":"2019-04-05 18:30:00","count":"0"},{"id":"6","name":"Match 6","win_price":"800","per_kill":"20","entry_fee":"40","type":"Duo","version":"TPP","map":"Erangel","detail":"Room id and password will share before 15 minute of match start","status":"0","total_spot":"100","match_time":"2019-04-05 19:00:00","count":"0"}]
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

    public class DataBean {
        /**
         * id : 5
         * name : Match 5
         * win_price : 800
         * per_kill : 20
         * entry_fee : 40
         * type : Duo
         * version : TPP
         * map : Erangel
         * detail : Room id and password will share before 15 minute of match start
         * status : 0
         * total_spot : 100
         * match_time : 2019-04-05 18:30:00
         * count : 0
         * "is_joined": 1
         */

        private String id;
        private String name;
        private String win_price;
        private String per_kill;
        private String entry_fee;
        private String type;
        private String version;
        private String map;
        private String detail;
        private int status;
        private int total_spot;
        private String match_time;
        private int count;
        private String entry_fee_coin;
        private int is_joined;

        private long countDown;
        private long endTime;

        public int getIs_joined() {
            return is_joined;
        }

        public void setIs_joined(int is_joined) {
            this.is_joined = is_joined;
        }

        public String getEntry_fee_coin() {
            return entry_fee_coin;
        }

        public void setEntry_fee_coin(String entry_fee_coin) {
            this.entry_fee_coin = entry_fee_coin;
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

        public String getWin_price() {
            return win_price;
        }

        public void setWin_price(String win_price) {
            this.win_price = win_price;
        }

        public String getPer_kill() {
            return per_kill;
        }

        public void setPer_kill(String per_kill) {
            this.per_kill = per_kill;
        }

        public String getEntry_fee() {
            return entry_fee;
        }

        public void setEntry_fee(String entry_fee) {
            this.entry_fee = entry_fee;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getMap() {
            return map;
        }

        public void setMap(String map) {
            this.map = map;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTotal_spot() {
            return total_spot;
        }

        public void setTotal_spot(int total_spot) {
            this.total_spot = total_spot;
        }

        public String getMatch_time() {
            return match_time;
        }

        public void setMatch_time(String match_time) {
            this.match_time = match_time;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        //CountDown Timer

        public long getCountDown() {
            return countDown;
        }

        public void setCountDown(long countDown) {
            this.countDown = countDown;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
    }
}
