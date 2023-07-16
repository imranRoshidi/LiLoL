package com.example.lilol;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("chat")
    Call<ChatResponse> chatWithTheBit(@Field("chatInput") String chatText);
}

class ChatResponse {
    public String chatBotReply;

    public ChatResponse(String chatBotReply) {
        this.chatBotReply = chatBotReply;
    }

    public String getChatBotReply() {
        return chatBotReply;
    }
}
