import java.awt.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

public class ScrollableClientScreen extends JPanel {

    private HashMap<URL, MyImage> images;
    private ArrayList<Message> messages;



    public ScrollableClientScreen() {
        images = new HashMap<URL, MyImage>();
        this.setLayout(null);
    }

    public Dimension getPreferredSize() {
        //Sets the size of the panel
        return new Dimension(400,600);
    }

    public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
        //draw background
        g.setColor(Color.WHITE);
        g.fillRect(0,0,400,800);

        // DISPLAY ALL MESSAGES
        int y = 50;
        g.setFont(new Font("Sans-Serif", Font.PLAIN,15));
        g.setColor(Color.BLACK);
        for(int i = 0; i < messages.size(); i++){
            if(images.containsKey(messages.get(i).getURL())){
                images.get(messages.get(i).getURL()).drawMe(g, 5, y - 17);
            }else{
                images.put(messages.get(i).getURL(),new MyImage(messages.get(i).getURL()));
                images.get(messages.get(i).getURL()).drawMe(g, 5, y - 17);
            }
            
            g.drawString(messages.get(i).toString() + "\n", 35, y);
            y+=30;
        }
    }
    public void update(ArrayList<Message> messages){
        this.messages = messages;
        repaint();
    }


}