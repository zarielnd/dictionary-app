package com.PRM391.dictionaryapp.Model;

import com.google.gson.annotations.SerializedName;


public class Phonetics {

    @SerializedName("text")
    String text;

    @SerializedName("audio")
    String audio;


    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
    public String getAudio() {
        return audio;
    }

}