package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class CompletedMatchPojo {


    /**
     * status : 1
     * data : [{"id":"3","game_id":"1","name":"Pubg squad","win_price":"200","per_kill":"4","entry_fee":"50","entry_fee_coin":"100","type":"Squad","version":"TPP","map":"Erangel","detail":"Room detail","status":"3","total_spot":"100","match_time":"2020-07-04 15:50:00","rules_id":"1","count":"8","is_joined":1,"game_details":{"id":"1","title":"PUBG Mobile","image":"https://test5.prisminfoways.com/multi_game/assets/images/646f5f10dab0bf658a86d2b41f60c0ea.png","icon":"https://test5.prisminfoways.com/multi_game/assets/images/40444799caa8b017300e8ae4898fdf47.png","playstore_url":"https://play.google.com/store/apps/details?id=com.tencent.ig&hl=en_US","bundle_id":"com.tencent.ig"},"price_pool":[{"id":"7","rank":"1","amount":"100"},{"id":"8","rank":"2","amount":"75"},{"id":"9","rank":"3","amount":"50"}]}]
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
         * game_id : 1
         * name : Pubg squad
         * win_price : 200
         * per_kill : 4
         * entry_fee : 50
         * entry_fee_coin : 100
         * type : Squad
         * version : TPP
         * map : Erangel
         * detail : Room detail
         * status : 3
         * total_spot : 100
         * match_time : 2020-07-04 15:50:00
         * rules_id : 1
         * count : 8
         * is_joined : 1
         * game_details : {"id":"1","title":"PUBG Mobile","image":"https://test5.prisminfoways.com/multi_game/assets/images/646f5f10dab0bf658a86d2b41f60c0ea.png","icon":"https://test5.prisminfoways.com/multi_game/assets/images/40444799caa8b017300e8ae4898fdf47.png","playstore_url":"https://play.google.com/store/apps/details?id=com.tencent.ig&hl=en_US","bundle_id":"com.tencent.ig"}
         * price_pool : [{"id":"7","rank":"1","amount":"100"},{"id":"8","rank":"2","amount":"75"},{"id":"9","rank":"3","amount":"50"}]
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
        private int is_joined;
        private GameDetailsBean game_details;
        private List<PricePoolBean> price_pool;

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

        public int getIs_joined() {
            return is_joined;
        }

        public void setIs_joined(int is_joined) {
            this.is_joined = is_joined;
        }

        public GameDetailsBean getGame_details() {
            return game_details;
        }

        public void setGame_details(GameDetailsBean game_details) {
            this.game_details = game_details;
        }

        public List<PricePoolBean> getPrice_pool() {
            return price_pool;
        }

        public void setPrice_pool(List<PricePoolBean> price_pool) {
            this.price_pool = price_pool;
        }

        public static class GameDetailsBean {
            /**
             * id : 1
             * title : PUBG Mobile
             * image : https://test5.prisminfoways.com/multi_game/assets/images/646f5f10dab0bf658a86d2b41f60c0ea.png
             * icon : https://test5.prisminfoways.com/multi_game/assets/images/40444799caa8b017300e8ae4898fdf47.png
             * playstore_url : https://play.google.com/store/apps/details?id=com.tencent.ig&hl=en_US
             * bundle_id : com.tencent.ig
             */

            private String id;
            private String title;
            private String image;
            private String icon;
            private String playstore_url;
            private String bundle_id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getPlaystore_url() {
                return playstore_url;
            }

            public void setPlaystore_url(String playstore_url) {
                this.playstore_url = playstore_url;
            }

            public String getBundle_id() {
                return bundle_id;
            }

            public void setBundle_id(String bundle_id) {
                this.bundle_id = bundle_id;
            }
        }

        public static class PricePoolBean {
            /**
             * id : 7
             * rank : 1
             * amount : 100
             */

            private String id;
            private String rank;
            private String amount;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }
        }
    }
}
