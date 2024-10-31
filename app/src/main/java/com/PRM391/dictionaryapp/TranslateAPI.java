package com.PRM391.dictionaryapp;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslateAPI {
    private static final String SUBSCRIPTION_KEY = "8rGbuCMgFHIXTxyR3YCjF8RChl4FH8s5Cx6RcubYzUvesdCeZ8gAJQQJ99AJACqBBLyXJ3w3AAAbACOGTpxD";
    private static final String SUBSCRIPTION_REGION = "southeastasia";
    private static final String ENDPOINT = "https://api.cognitive.microsofttranslator.com/translate";

    public static void translate(String textToTranslate, String targetLang, TranslationCallback callback) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = HttpUrl.parse(ENDPOINT).newBuilder()
                .addQueryParameter("api-version", "3.0")
                .addQueryParameter("to", targetLang)
                .build();

        String escapedText = textToTranslate.replace("\"", "\\\"");
        String content = "[{\"Text\": \"" + escapedText + "\"}]";

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                content
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY)
                .addHeader("Ocp-Apim-Subscription-Region", SUBSCRIPTION_REGION)
                .addHeader("Content-type", "application/json")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonResponse = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonResponse);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        JSONArray translations = jsonObject.getJSONArray("translations");
                        String translatedText = translations.getJSONObject(0).getString("text");
                        callback.onSuccess(translatedText);
                    } catch (Exception e) {
                        callback.onError(e.getMessage());
                    }
                } else {
                    callback.onError("Translation failed: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                callback.onError(e.getMessage());
            }
        });
    }

    public interface TranslationCallback {
        void onSuccess(String translatedText);
        void onError(String error);
    }
}
