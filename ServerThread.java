import java.util.*;
import java.io.*;
import java.net.*;

public class ServerThread implements Runnable{
    private ArrayList<Message> messages;
    private Manager manager;
    private Socket clientSocket;
    private ObjectOutputStream outObject;
    private ObjectInputStream inObject;

    
    public ServerThread(Manager manager, Socket clientSocket){
        this.manager = manager;
        this.messages = manager.getMessages();
        this.clientSocket = clientSocket;
        try {
            outObject = new ObjectOutputStream(clientSocket.getOutputStream());
            inObject = new ObjectInputStream(clientSocket.getInputStream());

            outObject.writeObject(messages);
        } catch (IOException e) {
            System.out.println(e);
            //TODO: handle exception
        }
       
    }
    public void run(){
        //listen for messages
        //update when new message
        while(true){

            try {
                
                Object obj = inObject.readObject();
                if(obj instanceof Message){
                    manager.add((Message)obj);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void update(ArrayList<Message> messages){
        this.messages = messages;
        try {
            outObject.writeObject(messages.get(messages.size()-1)); 
            outObject.reset();

        } catch (IOException e) {
            System.out.println(e);
        }
        
        // send this to the client
        // send the arraylist of messages
        
    }
    
    public String toString(){
        String returnString = "";
        for(int i = 0; i < messages.size(); i++){
            returnString += messages.get(i) + "\n";
        }
        return returnString;
    }
    
}
