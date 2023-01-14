import java.awt.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;

public class MyImage implements Serializable{

    private URL link;
    private Image picture;
    
    public MyImage(URL link){
        this.link = link;
    }

    public void drawMe(Graphics g, int x, int y){
        try{
            this.picture = ImageIO.read(link);
            Image scaledPicture = picture.getScaledInstance(25, 25, Image.SCALE_DEFAULT);            
            g.drawImage(scaledPicture, x, y, null);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }


    public String getURL(){
        String temp = link.toString();
        return temp;
    }

    @Override
    public String toString(){
        return link.toString();
    }
}
