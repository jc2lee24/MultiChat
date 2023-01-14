import java.io.*;
import java.net.*;

public class Message implements Serializable{
    private String message;
    private String username;
    private URL url;
    public Message(String message, String username, URL url){
        this.message = message;
        this.username = username;
        this.url = url;
    }


    public String toString(){
        return username + ": " + message;
    }

    public URL getURL(){
        return url;
    }
    
}
