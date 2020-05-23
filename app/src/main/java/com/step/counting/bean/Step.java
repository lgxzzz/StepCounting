package com.step.counting.bean;

public class Step {

    String STEP_ID;
    String DATE;
    String STEP_NUM;
    String LOCATIONS;

    public String getSTEP_ID() {
        return STEP_ID;
    }

    public void setSTEP_ID(String STEP_ID) {
        this.STEP_ID = STEP_ID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getSTEP_NUM() {
        return STEP_NUM;
    }

    public void setSTEP_NUM(String STEP_NUM) {
        this.STEP_NUM = STEP_NUM;
    }

    public String getLOCATIONS() {
        return LOCATIONS;
    }

    public void setLOCATIONS(String LOCATIONS) {
        this.LOCATIONS = LOCATIONS;
    }

    public Step(){

    }

    public Step(String STEP_ID,String DATE,String STEP_NUM,String LOCATIONS){
        this.setSTEP_ID(STEP_ID);
        this.setDATE(DATE);
        this.setSTEP_NUM(STEP_NUM);
        this.setLOCATIONS(LOCATIONS);
    }
}
