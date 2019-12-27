
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



@SuppressWarnings("unused")
public class ChatClient implements ChatClientInterface {
	
	private BufferedReader in;
	private PrintWriter out;
	private JFrame chatBox = new JFrame("Chat Room");
	private JTextField messageTF =  new JTextField(40);
	private JTextArea messageBox = new JTextArea(15,50);
	private Socket clientSocket;
	private static int PORT;
	
	/**
	 * Default constructor
	 */
	 public ChatClient()
	    {
	    	// I just choose a random port number of 9001 to start the server in.
	    	PORT = 9001;	
	    	
	    	// The ChatClient class file provided for this assignment had the Java Swing package imported, so I will be
	        // using Java Swing to create the chat box and textfields for the application.
	    	
	    	chatBox.setVisible(true);
	    	messageTF.setEditable(false);
	    	messageBox.setEditable(false);
	        chatBox.getContentPane().add(messageTF, "North");
	        chatBox.getContentPane().add(new JScrollPane(messageBox), "Center");
	        chatBox.pack();

	        // Add Listeners
	        messageTF.addActionListener(new ActionListener()
	        {

	            public void actionPerformed(ActionEvent e)
	            {
	                out.println(messageTF.getText());
	                messageTF.setText("");
	            }
	        });
	    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			clientSocket = new Socket ("localhost",getServerPort());
			
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			while (true)
			{
				String line = in.readLine();
				if(line.startsWith("SUBMITNAME"))
				{
					out.println(getName());
				}
				else if(line.startsWith("NAMEACCEPTED"))
				{
					messageTF.setEditable(true);
				}
				else if(line.startsWith("MESSAGE"))
				{
					messageBox.append(line.substring(8)+ "\n");
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
/**
 * getName method
 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return JOptionPane.showInputDialog(chatBox,"Choose a screen name:","Screen name selection",JOptionPane.PLAIN_MESSAGE);
	}
/**
 * getServerPort method
 */
	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return PORT;
	}
	

}

