package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class MatchResultPojo {


    /**
     * status : 1
     * data : {"id":"6","name":"Match 6","win_price":"800","per_kill":"20","entry_fee":"40","entry_fee_coin":"400","type":"Duo","version":"TPP","map":"Erangel","detail":"Room id and password will share before 15 minute of match start","status":"2","total_spot":"100","match_time":"2019-04-10 19:00:00","count":"0","winner":[{"pubg_name":"XXX","kill_count":"15","win_amount":800}],"result":[{"pubg_name":"VV","kill_count":"13","win_amount":260},{"pubg_name":"AAA","kill_count":"11","win_amount":220},{"pubg_name":"ASD","kill_count":"7","win_amount":140},{"pubg_name":"Rembo","kill_count":"6","win_amount":120},{"pubg_name":"Himanshu","kill_count":"0","win_amount":0},{"pubg_name":"XXX","kill_count":"15","win_amount":300}]}
     */

    private String status;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * name : Match 6
         * win_price : 800
         * per_kill : 20
         * entry_fee : 40
         * entry_fee_coin : 400
         * type : Duo
         * version : TPP
         * map : Erangel
         * detail : Room id and password will share before 15 minute of match start
         * status : 2
         * total_spot : 100
         * match_time : 2019-04-10 19:00:00
         * count : 0
         * winner : [{"pubg_name":"XXX","kill_count":"15","win_amount":800}]
         * result : [{"pubg_name":"VV","kill_count":"13","win_amount":260},{"pubg_name":"AAA","kill_count":"11","win_amount":220},{"pubg_name":"ASD","kill_count":"7","win_amount":140},{"pubg_name":"Rembo","kill_count":"6","win_amount":120},{"pubg_name":"Himanshu","kill_count":"0","win_amount":0},{"pubg_name":"XXX","kill_count":"15","win_amount":300}]
         */

        private String id;
        private String name;
        private String win_price;
        private String per_kill;
        private String entry_fee;
        private String entry_fee_coin;
        private String type;
        private String version;
        private String map;
        private String detail;
        private String status;
        private String total_spot;
        private String match_time;
        private String count;
        private List<WinnerBean> winner;
        private List<ResultBean> result;

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

        public String getEntry_fee_coin() {
            return entry_fee_coin;
        }

        public void setEntry_fee_coin(String entry_fee_coin) {
            this.entry_fee_coin = entry_fee_coin;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTotal_spot() {
            return total_spot;
        }

        public void setTotal_spot(String total_spot) {
            this.total_spot = total_spot;
        }

        public String getMatch_time() {
            return match_time;
        }

        public void setMatch_time(String match_time) {
            this.match_time = match_time;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<WinnerBean> getWinner() {
            return winner;
        }

        public void setWinner(List<WinnerBean> winner) {
            this.winner = winner;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class WinnerBean {
            /**
             * pubg_name : XXX
             * kill_count : 15
             * win_amount : 800
             */

            private String pubg_name;
            private String kill_count;
            private int win_amount;

            public String getPubg_name() {
                return pubg_name;
            }

            public void setPubg_name(String pubg_name) {
                this.pubg_name = pubg_name;
            }

            public String getKill_count() {
                return kill_count;
            }

            public void setKill_count(String kill_count) {
                this.kill_count = kill_count;
            }

            public int getWin_amount() {
                return win_amount;
            }

            public void setWin_amount(int win_amount) {
                this.win_amount = win_amount;
            }
        }

        public static class ResultBean {
            /**
             * pubg_name : VV
             * kill_count : 13
             * win_amount : 260
             */

            private String pubg_name;
            private String kill_count;
            private int win_amount;

            public String getPubg_name() {
                return pubg_name;
            }

            public void setPubg_name(String pubg_name) {
                this.pubg_name = pubg_name;
            }

            public String getKill_count() {
                return kill_count;
            }

            public void setKill_count(String kill_count) {
                this.kill_count = kill_count;
            }

            public int getWin_amount() {
                return win_amount;
            }

            public void setWin_amount(int win_amount) {
                this.win_amount = win_amount;
            }
        }
    }
}
