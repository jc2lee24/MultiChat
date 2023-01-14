import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class ClientScreen extends JPanel implements ActionListener {
    private JTextField inputField;
    private ArrayList<Message> chatMessages;
    private ScrollableClientScreen messageContainer;
    private JTextField usernameField;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String username = "";
    private URL image;
    private JScrollPane scrollPane;
    
    public ClientScreen() {
        this.setLayout(null);
        chatMessages = new ArrayList<Message>();


        //TextField
        inputField = new JTextField(20);
        inputField.setBounds(0,250, 400, 50);
        this.add(inputField);

        usernameField = new JTextField(20);
        usernameField.setBounds(0,250, 400, 50);
        this.add(usernameField);
        usernameField.setText("Username");


        this.setFocusable(true);
        inputField.addActionListener(this);
        usernameField.addActionListener(this);


        messageContainer = new ScrollableClientScreen();
        scrollPane = new JScrollPane(messageContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 0, 400, 250);
        this.add(scrollPane);
        messageContainer.update(chatMessages);

        inputField.setVisible(false);
        scrollPane.setVisible(false);

    }

    public Dimension getPreferredSize() {
        //Sets the size of the panel
        return new Dimension(400,300);
    }

    public void paintComponent(Graphics g) {
		super.paintComponent(g);
        //draw background
        g.setColor(Color.white);
        g.fillRect(0,0,800,400);

        //draw instructions
        g.setColor(Color.black);
        if(username.equals("")){
            g.drawString("To see messages, enter a username", 50, 50);
        }
        else if(image == null){
            g.drawString("To see messages, enter a url for a profile pic", 50, 50);

        }

        String toPrint = "";
        for(int i = 0;i<chatMessages.size();i++){
            toPrint += chatMessages.get(i).toString()+"\n";
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inputField) {
            if(inputField.getText().equals("")){
                return;
            }
            String input = inputField.getText();
            try {
                Message chat = new Message(input, username, image);
                out.writeObject(chat);
                out.reset();
                inputField.setText("");


            } catch (IOException f) {
                System.out.println(f);
            }
        }
        if(e.getSource() == usernameField && username.equals("")){
            this.username = usernameField.getText();
            usernameField.setText("URL to Profile Pic");

        }
        else if(e.getSource()==usernameField && !username.equals("")){
            String input = usernameField.getText();
            try {
                image = new URL(input);

                usernameField.setVisible(false);
                inputField.setVisible(true);
                scrollPane.setVisible(true);
                
            } catch (MalformedURLException l) {
                System.out.println(l);
            }

        }
        repaint();
    }
    public void poll() throws IOException{

		String hostName = "10.210.71.146"; 
		int portNumber = 1024;
		Socket serverSocket = new Socket(hostName, portNumber);
        out = new ObjectOutputStream(serverSocket.getOutputStream());
        in = new ObjectInputStream(serverSocket.getInputStream());

		repaint();

		//listens for inputs
		try {

			while (true) {
                try {
                    Object obj = in.readObject();
                    if(obj instanceof ArrayList){
                        chatMessages = (ArrayList)obj;
                        messageContainer.update(chatMessages);
                    }
                    if(obj instanceof Message){
                        chatMessages.add((Message)obj);
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println(e);
                }
                repaint();

			}
		} catch (UnknownHostException e) {
			System.err.println("Host unkown: " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
        serverSocket.close();
	}

    





}