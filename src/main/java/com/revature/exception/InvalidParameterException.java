package com.revature.exception;

import java.util.ArrayList;

public class InvalidParameterException extends Exception {

    private ArrayList<String> messages;

    public InvalidParameterException() {
        messages = new ArrayList<>();
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public boolean containsMessage() {
        return !this.messages.isEmpty();
    }

}
