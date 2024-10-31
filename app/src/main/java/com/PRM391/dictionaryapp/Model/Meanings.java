package com.PRM391.dictionaryapp.Model;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Meanings {

    @SerializedName("partOfSpeech")
    String partOfSpeech;

    @SerializedName("definitions")
    List<Definitions> definitions;


    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setDefinitions(List<Definitions> definitions) {
        this.definitions = definitions;
    }
    public List<Definitions> getDefinitions() {
        return definitions;
    }

}