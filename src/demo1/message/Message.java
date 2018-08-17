package demo1.message;

import java.io.Serializable;

public class Message implements Serializable {

    public String username;
    public enum MessageType {LOGIN, SYSTEM, CHAT, GAME_ACTION, RESULT}
    public String module = "battleship1";
    public MessageType messageType;

    public void setUsername(String username) {

        this.username = username;
    }

    public String getUsername() {

        return username;
    }

    protected Message(String username) {

        setUsername(username);
    }

    public String getModule() {

        return module;
    }

    public MessageType getMessageType() {
        return null;
    }

    @Override
    public String toString() {
        return "Message{" +
                "username='" + username + '\'' +
                ", module='" + module + '\'' +
                ", messageType=" + getMessageType() +
                '}';
    }
}
