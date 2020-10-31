package com.example.financialhospital.object;

public class ChatObj {
    String chat, timestamp;

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ChatObj(String chat, String timestamp) {
        this.chat = chat;
        this.timestamp = timestamp;
    }
}
