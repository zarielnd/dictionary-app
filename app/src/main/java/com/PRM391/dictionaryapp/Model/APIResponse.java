package com.PRM391.dictionaryapp.Model;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class APIResponse {

    @SerializedName("word")
    String word;

    @SerializedName("phonetic")
    String phonetic;

    @SerializedName("phonetics")
    List<Phonetics> phonetics;

    @SerializedName("origin")
    String origin;

    @SerializedName("meanings")
    List<Meanings> meanings;


    public void setWord(String word) {
        this.word = word;
    }
    public String getWord() {
        return word;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }
    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetics(List<Phonetics> phonetics) {
        this.phonetics = phonetics;
    }
    public List<Phonetics> getPhonetics() {
        return phonetics;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getOrigin() {
        return origin;
    }

    public void setMeanings(List<Meanings> meanings) {
        this.meanings = meanings;
    }
    public List<Meanings> getMeanings() {
        return meanings;
    }

}