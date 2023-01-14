import java.util.*;

public class Manager {
    
    public ArrayList<ServerThread> threadList;
    public ArrayList<Message> messages;
    
    public Manager(){
        threadList = new ArrayList<ServerThread>();
        messages = new ArrayList<Message>();
    }

    public void add(ServerThread thread){
        threadList.add(thread);
    }
    public void add(Message message){
        messages.add(message);
        System.out.println("Trying to add a message");
        System.out.println(messages);
        for(int i = 0;i<threadList.size();i++){
            threadList.get(i).update(messages);
        }
    }
    public ArrayList<Message> getMessages(){
        return this.messages;
    }

    

}
