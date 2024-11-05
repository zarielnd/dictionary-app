package com.PRM391.dictionaryapp.Model;

public class SavedWord {
    private String id;
    private String englishWord;
    private String vietnameseWord;
    private long timestamp;

    public SavedWord() {} // Required for Firestore

    public SavedWord(String englishWord, String vietnameseWord) {
        this.englishWord = englishWord;
        this.vietnameseWord = vietnameseWord;
        this.timestamp = System.currentTimeMillis();
    }

    // Add getters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getVietnameseWord() {
        return vietnameseWord;
    }

    public void setVietnameseWord(String vietnameseWord) {
        this.vietnameseWord = vietnameseWord;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
