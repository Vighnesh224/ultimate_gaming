package com.prisminfoways.ultimate.pojo;

public class SettingPojo {

    /**
     * status : 1
     * data : {"telegram":"","about_us":"https://test5.prisminfoways.com/multi_game/extra/about_us.html","customer_support":"https://test5.prisminfoways.com/multi_game/extra/customer_support.html","privacy_policy":"https://test5.prisminfoways.com/multi_game/extra/privacy_policy.html","terms_and_conditions":"https://test5.prisminfoways.com/multi_game/extra/terms_and_conditions.html","faq":"https://test5.prisminfoways.com/multi_game/extra/faq.html","website":"http://pbgzone.com/","how_it_work":"https://www.youtube.com/","how_to_join_room":"http://pbgzone.com/screen_shot_and_steps","date":"2020-06-30","task":{"click":"20","web_click":"40","msg":"Click on this ads to earn free coins."},"info_msg":"\n\u2022 No new PUBG accounts allowed. PUBG Accounts with level less than 25 are not allowed to participate. Those participate will be kicked from room and not given any refund.\n\n\n\u2022 If in anyway you fail to join the room by the match start time then we are\u2019t responsible for it. Refund in such cases won\u2019t be processed. So make sure to join on time.\n\n\n\u2022 Do not share the Room ID & Password with anyone who has not joined the match. If you are found doing so, your account may be  get terminated and all the winnings will be lost.\n\n\n\u2022 Grieving and Teaming is against the game rules. Any participant found doing so we will be disqualified and their prize will be lost.\n\n\n\u2022 Room ID and Password will be shared in the app before 15 minutes of match start time.\n\n\n\u2022 Match will start after 15 minutes of Sharing Room ID and Password.\n\n\n\u2022 Make sure to grab ID and Password before the Match Start Time.\n\n\n\u2022 Make sure you join the Match Room ASAP before the match starts.\n\n\n\u2022 This match is Paid Match. To participate you have to pay the entry fee amount. There are total 100 spots available. Join it  before all are filled.\n\n\n\u2022 Please note that the listed entry fee is per individual and not the Squad/Duo team.\n\n\n\u2022 Each member of team (Squad or Duo) has to pay the entry fee and register individually for the match or tournament.\n\n\n\u2022 Once you join the match custom room, Do not keep changing your position . If you do so you may get kicked from the room.\n\n\n\u2022 Spot are given on the First come First serve basis.\n\n\n\u2022 Last standing man gets the Chicken Dinner Award.\n\n\n\u2022 You will also rewarded for each kill, Check match detail.\n\n\n\u2022 Use only mobile Device to join match. Do not use any Hacks or Emulators.\n\n\n\u2022 If anyone found violating these rules then immediate action will be taken and respective accounts may suspend and all rewards may abandoned.\n\n\n\u2022 If you have any query please contact us on.","update":{"version":"4","message":"Update this version to work smoothly.","skip":"1","link":"http://pbgzone.com/download_step"}}
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
         * telegram :
         * about_us : https://test5.prisminfoways.com/multi_game/extra/about_us.html
         * customer_support : https://test5.prisminfoways.com/multi_game/extra/customer_support.html
         * privacy_policy : https://test5.prisminfoways.com/multi_game/extra/privacy_policy.html
         * terms_and_conditions : https://test5.prisminfoways.com/multi_game/extra/terms_and_conditions.html
         * faq : https://test5.prisminfoways.com/multi_game/extra/faq.html
         * website : http://pbgzone.com/
         * how_it_work : https://www.youtube.com/
         * how_to_join_room : http://pbgzone.com/screen_shot_and_steps
         * date : 2020-06-30
         * task : {"click":"20","web_click":"40","msg":"Click on this ads to earn free coins."}
         * info_msg :
         • No new PUBG accounts allowed. PUBG Accounts with level less than 25 are not allowed to participate. Those participate will be kicked from room and not given any refund.


         • If in anyway you fail to join the room by the match start time then we are’t responsible for it. Refund in such cases won’t be processed. So make sure to join on time.


         • Do not share the Room ID & Password with anyone who has not joined the match. If you are found doing so, your account may be  get terminated and all the winnings will be lost.


         • Grieving and Teaming is against the game rules. Any participant found doing so we will be disqualified and their prize will be lost.


         • Room ID and Password will be shared in the app before 15 minutes of match start time.


         • Match will start after 15 minutes of Sharing Room ID and Password.


         • Make sure to grab ID and Password before the Match Start Time.


         • Make sure you join the Match Room ASAP before the match starts.


         • This match is Paid Match. To participate you have to pay the entry fee amount. There are total 100 spots available. Join it  before all are filled.


         • Please note that the listed entry fee is per individual and not the Squad/Duo team.


         • Each member of team (Squad or Duo) has to pay the entry fee and register individually for the match or tournament.


         • Once you join the match custom room, Do not keep changing your position . If you do so you may get kicked from the room.


         • Spot are given on the First come First serve basis.


         • Last standing man gets the Chicken Dinner Award.


         • You will also rewarded for each kill, Check match detail.


         • Use only mobile Device to join match. Do not use any Hacks or Emulators.


         • If anyone found violating these rules then immediate action will be taken and respective accounts may suspend and all rewards may abandoned.


         • If you have any query please contact us on.
         * update : {"version":"4","message":"Update this version to work smoothly.","skip":"1","link":"http://pbgzone.com/download_step"}
         */

        private String telegram;
        private String about_us;
        private String customer_support;
        private String privacy_policy;
        private String terms_and_conditions;
        private String faq;
        private String website;
        private String how_it_work;
        private String how_to_join_room;
        private String date;
        private TaskBean task;
        private String info_msg;
        private UpdateBean update;

        public String getTelegram() {
            return telegram;
        }

        public void setTelegram(String telegram) {
            this.telegram = telegram;
        }

        public String getAbout_us() {
            return about_us;
        }

        public void setAbout_us(String about_us) {
            this.about_us = about_us;
        }

        public String getCustomer_support() {
            return customer_support;
        }

        public void setCustomer_support(String customer_support) {
            this.customer_support = customer_support;
        }

        public String getPrivacy_policy() {
            return privacy_policy;
        }

        public void setPrivacy_policy(String privacy_policy) {
            this.privacy_policy = privacy_policy;
        }

        public String getTerms_and_conditions() {
            return terms_and_conditions;
        }

        public void setTerms_and_conditions(String terms_and_conditions) {
            this.terms_and_conditions = terms_and_conditions;
        }

        public String getFaq() {
            return faq;
        }

        public void setFaq(String faq) {
            this.faq = faq;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getHow_it_work() {
            return how_it_work;
        }

        public void setHow_it_work(String how_it_work) {
            this.how_it_work = how_it_work;
        }

        public String getHow_to_join_room() {
            return how_to_join_room;
        }

        public void setHow_to_join_room(String how_to_join_room) {
            this.how_to_join_room = how_to_join_room;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public TaskBean getTask() {
            return task;
        }

        public void setTask(TaskBean task) {
            this.task = task;
        }

        public String getInfo_msg() {
            return info_msg;
        }

        public void setInfo_msg(String info_msg) {
            this.info_msg = info_msg;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public static class TaskBean {
            /**
             * click : 20
             * web_click : 40
             * msg : Click on this ads to earn free coins.
             */

            private String click;
            private String web_click;
            private String msg;

            public String getClick() {
                return click;
            }

            public void setClick(String click) {
                this.click = click;
            }

            public String getWeb_click() {
                return web_click;
            }

            public void setWeb_click(String web_click) {
                this.web_click = web_click;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }
        }

        public static class UpdateBean {
            /**
             * version : 4
             * message : Update this version to work smoothly.
             * skip : 1
             * link : http://pbgzone.com/download_step
             */

            private int version;
            private String message;
            private String skip;
            private String link;

            public int getVersion() {
                return version;
            }

            public void setVersion(int version) {
                this.version = version;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getSkip() {
                return skip;
            }

            public void setSkip(String skip) {
                this.skip = skip;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
}
