package com.hust.su.enity;

import java.util.Date;

public class WordFreq {
    private String word;
    private int freq;
    private Date date; //以月为单位，比如2017-05-01

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
