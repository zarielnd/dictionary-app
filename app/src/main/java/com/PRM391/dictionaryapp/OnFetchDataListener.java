package com.PRM391.dictionaryapp;

import com.PRM391.dictionaryapp.Model.APIResponse;

public interface OnFetchDataListener {
    void onFetchData(APIResponse apiResponse, String message);
    void onError(String message);
}
