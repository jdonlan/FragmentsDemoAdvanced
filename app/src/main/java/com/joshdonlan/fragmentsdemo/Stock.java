package com.joshdonlan.fragmentsdemo;

import android.util.Log;

import org.json.JSONObject;

public class Stock {

    final String TAG = "STOCK CLASS";

    private String mSymbol;
    private String mPrice;
    private String mDate;
    private String mHigh;
    private String mLow;
    private String mChange;
    private String mOpen;
    private String mVolume;
    private String mPercent;
    
    public Stock(){}
    
    public Stock(String symbol, String price, String date, String high, String low, String change, String open, String volume, String percent){
        mSymbol = symbol;
        mPrice = price;
        mDate = date;
        mHigh = high;
        mLow = low;
        mChange = change;
        mOpen = open;
        mVolume = volume;
        mPercent = percent;
    }
    
    public Stock(JSONObject stockData){
        try {
            mSymbol = stockData.getString("symbol");
            mPrice = stockData.getString("price");
            mDate = stockData.getString("date") + " " + stockData.getString("time");
            mHigh = stockData.getString("high");
            mLow = stockData.getString("low");
            mChange = stockData.getString("change");
            mOpen = stockData.getString("open");
            mVolume = stockData.getString("volume");
            mPercent = stockData.getString("chgpct");
        } catch (Exception e) {
            Log.e(TAG, "Error updating display");
        }       
    }

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String mSymbol) {
        this.mSymbol = mSymbol;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getHigh() {
        return mHigh;
    }

    public void setHigh(String mHigh) {
        this.mHigh = mHigh;
    }

    public String getLow() {
        return mLow;
    }

    public void setLow(String mLow) {
        this.mLow = mLow;
    }

    public String getChange() {
        return mChange;
    }

    public void setChange(String mChange) {
        this.mChange = mChange;
    }

    public String getOpen() {
        return mOpen;
    }

    public void setOpen(String mOpen) {
        this.mOpen = mOpen;
    }

    public String getVolume() {
        return mVolume;
    }

    public void setVolume(String mVolume) {
        this.mVolume = mVolume;
    }

    public String getPercent() {
        return mPercent;
    }

    public void setPercent(String mPercent) {
        this.mPercent = mPercent;
    }
}
