package com.example.mynote.Adapters;

public class Model {
    String mHeader,mDescribtion,mKey;

    public Model() {
    }

    public Model(String mHeader, String mDescribtion, String mKey) {
        this.mHeader = mHeader;
        this.mDescribtion = mDescribtion;
        this.mKey = mKey;
    }

    public String getmHeader() {
        return mHeader;
    }

    public String getmDescribtion() {
        return mDescribtion;
    }

    public String getmKey() {
        return mKey;
    }
}
