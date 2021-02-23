package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class MatchDetailPojo {

    /**
     * status : 1
     * data : {"id":"13","game_id":"1","name":"Piyush copy","win_price":"10","per_kill":"2","entry_fee":"10","entry_fee_coin":"50","type":"Duo","version":"TPP","map":"Erangel","detail":"sam match","status":"0","total_spot":"100","match_time":"2020-07-02 17:45:00","rules_id":"1","count":"2","rules":"<ul>\r\n\t<li>You must be 16/18/21 years of age or older to buy Lottery tickets and claim prizes.<\/li>\r\n\t<li>Players are responsible for checking their tickets before leaving the Retailer location.<\/li>\r\n\t<li>A ticket is void if stolen, unissued, mutilated, illegible, tampered with or altered in any way, defective or incomplete.<\/li>\r\n\t<li>The Lottery is not responsible for lost or stolen tickets.<\/li>\r\n\t<li>A Lotto/Lottery ticket may be cancelled only if presented before the drawing to the Retailer where it was originally purchased. Powerball tickets cannot be cancelled.<\/li>\r\n\t<li>Multi-drawing bets on the same number(s) may be made in advance for all games. Ask Retailer for details.<\/li>\r\n\t<li>You must fill in your name, address and phone number on the back of your winning ticket before you file a prize claim.<\/li>\r\n\t<li>All winning tickets are subject to validation by the Lottery.<\/li>\r\n\t<li>All winning tickets are bearer instruments.<\/li>\r\n\t<li>All prizes must be claimed within 6 months/one year from date of drawing.<\/li>\r\n<\/ul>\r\n","users":[{"id":"14","user_id":"2","pubg_name":"youcaption"},{"id":"15","user_id":"2","pubg_name":"nilesh"},{"id":"25","user_id":"14","pubg_name":"Tarun"}]}
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
         * id : 13
         * game_id : 1
         * name : Piyush copy
         * win_price : 10
         * per_kill : 2
         * entry_fee : 10
         * entry_fee_coin : 50
         * type : Duo
         * version : TPP
         * map : Erangel
         * detail : sam match
         * status : 0
         * total_spot : 100
         * match_time : 2020-07-02 17:45:00
         * rules_id : 1
         * count : 2
         * rules : <ul>
         <li>You must be 16/18/21 years of age or older to buy Lottery tickets and claim prizes.</li>
         <li>Players are responsible for checking their tickets before leaving the Retailer location.</li>
         <li>A ticket is void if stolen, unissued, mutilated, illegible, tampered with or altered in any way, defective or incomplete.</li>
         <li>The Lottery is not responsible for lost or stolen tickets.</li>
         <li>A Lotto/Lottery ticket may be cancelled only if presented before the drawing to the Retailer where it was originally purchased. Powerball tickets cannot be cancelled.</li>
         <li>Multi-drawing bets on the same number(s) may be made in advance for all games. Ask Retailer for details.</li>
         <li>You must fill in your name, address and phone number on the back of your winning ticket before you file a prize claim.</li>
         <li>All winning tickets are subject to validation by the Lottery.</li>
         <li>All winning tickets are bearer instruments.</li>
         <li>All prizes must be claimed within 6 months/one year from date of drawing.</li>
         </ul>
         * users : [{"id":"14","user_id":"2","pubg_name":"youcaption"},{"id":"15","user_id":"2","pubg_name":"nilesh"},{"id":"25","user_id":"14","pubg_name":"Tarun"}]
         */

        private String id;
        private String game_id;
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
        private String rules_id;
        private String count;
        private String rules;
        private List<UsersBean> users;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGame_id() {
            return game_id;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
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

        public String getRules_id() {
            return rules_id;
        }

        public void setRules_id(String rules_id) {
            this.rules_id = rules_id;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getRules() {
            return rules;
        }

        public void setRules(String rules) {
            this.rules = rules;
        }

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class UsersBean {
            /**
             * id : 14
             * user_id : 2
             * pubg_name : youcaption
             */

            private String id;
            private String user_id;
            private String pubg_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getPubg_name() {
                return pubg_name;
            }

            public void setPubg_name(String pubg_name) {
                this.pubg_name = pubg_name;
            }
        }
    }
}
