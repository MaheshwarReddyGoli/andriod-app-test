package com.example.covidforecasting;

public class DataModel {

    String tv1,tv11,tv2,tv22,tv3;


    public DataModel(String tv1, String tv11, String tv2, String tv22,String tv3 ) {
        this.tv1=tv1;
        this.tv11=tv11;
        this.tv2=tv2;
        this.tv22=tv22;
        this.tv3=tv3;
    }

    public String getTv1() {
        return tv1;
    }

    public String getTv11() {
        return tv11;
    }

    public String getTv2() {
        return tv2;
    }

    public String getTv22() {
        return tv22;
    }
    public String getTv3() {
        return tv3;
    }

}
