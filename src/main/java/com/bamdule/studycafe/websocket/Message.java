package com.bamdule.studycafe.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Message {

    public Message(MessageType messageType, Object message) {
        this.type = messageType;
        this.message = message;
    }

    private MessageType type;

    private Object message;
}
