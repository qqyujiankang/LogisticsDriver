package com.cn.android.bean;

import java.util.List;

public class CityAreaBean {

    /**
     * secondList : [{"name":"瓯海区","id":"030EBCF804F992ADAF8C316D47320090"},{"name":"永嘉区","id":"09901CE35A91ED1C349DA3A4CDD99BC3"},{"name":"平阳区","id":"20A855771376A0BA5FEA050E003DA8AA"},{"name":"苍南区","id":"3923BA73729A11201F5147B1B75565C3"},{"name":"文成区","id":"7EF968547143D2434370D03AFD857237"},{"name":"瑞安区","id":"81C1359EE71F766C95DCCDAD329E4B17"},{"name":"鹿城区","id":"919D663BE7129CB5E7CEC950059A06BC"},{"name":"泰顺区","id":"91BB80DFDBF911729057B3AF04EC7937"},{"name":"龙湾区","id":"B7B969612FB9A3BC64222EA68FD3AD36"},{"name":"洞头区","id":"C04477FFF212E6EA0087DA242A10DDDE"},{"name":"乐清区","id":"CE6FCBA238C68377FBD882B2562D6916"}]
     * name : 温州市
     * id : 0EDF25C7DD1AD695CCA580DBCA933BB5
     */

    private String name;
    private String id;
    private List<SecondListBean> secondList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SecondListBean> getSecondList() {
        return secondList;
    }

    public void setSecondList(List<SecondListBean> secondList) {
        this.secondList = secondList;
    }

    public static class SecondListBean {
        /**
         * name : 瓯海区
         * id : 030EBCF804F992ADAF8C316D47320090
         */

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
