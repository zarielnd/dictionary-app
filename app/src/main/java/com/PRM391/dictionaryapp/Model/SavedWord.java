package com.PRM391.dictionaryapp.Model;

public class SavedWord {
    private String englishWord;
    private String vietnameseWord;
    private long timestamp;

    public SavedWord(String englishWord, String vietnameseWord) {
        this.englishWord = englishWord;
        this.vietnameseWord = vietnameseWord;
        this.timestamp = System.currentTimeMillis();
    }

    // Add getters
    public String getEnglishWord() { return englishWord; }
    public String getVietnameseWord() { return vietnameseWord; }
    public long getTimestamp() { return timestamp; }
}
