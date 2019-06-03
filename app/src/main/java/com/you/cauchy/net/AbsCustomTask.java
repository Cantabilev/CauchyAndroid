package com.you.cauchy.net;

public abstract class AbsCustomTask {

    //protected String URL = "http://106.91.31.178:8080/";
    //protected String URL = "http://10.30.6.39:8080/";
    //protected String URL = "http://123.207.120.145:8080/";
    //protected String URL = "http://192.168.43.90:8080/";
    //protected String URL = "http://192.168.155.2:8080/";
    protected String URL = "http://169.254.21.235:8080/";
    protected RestCallback restCallback;

    protected String CAUCHYSSO = "CAUCHYSSO";

    public void setRestCallback(RestCallback restCallback) {
        this.restCallback = restCallback;
    }

    public abstract <Params> void execute(Params params);
    public abstract <Params> void execute(Params... params);

}
