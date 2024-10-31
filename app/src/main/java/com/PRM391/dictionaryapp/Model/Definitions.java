package com.PRM391.dictionaryapp.Model;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Definitions {

    @SerializedName("definition")
    String definition;

    @SerializedName("example")
    String example;

    @SerializedName("synonyms")
    List<String> synonyms;

    @SerializedName("antonyms")
    List<String> antonyms;


    public void setDefinition(String definition) {
        this.definition = definition;
    }
    public String getDefinition() {
        return definition;
    }

    public void setExample(String example) {
        this.example = example;
    }
    public String getExample() {
        return example;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }
    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }
    public List<String> getAntonyms() {
        return antonyms;
    }

}