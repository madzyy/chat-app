import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.*;
import java.awt.BorderLayout;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;


public class client extends JFrame{
	private JTextField userText;
	private JTextArea chatWindow;
	private String serverIP;
	private String message = "";
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public client(String host){
		super("Instant messenger app Client");
		serverIP = host;
		userText = new JTextField();
		userText.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					sendMessage(event.getActionCommand());
					userText.setText("");
				}
			}
		);
		userText.setEditable(false);
		add(userText, BorderLayout.NORTH);

		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(300, 150);
		setVisible(true);


	}


	public void startRunning(){
		try{
			
			while(true){
				try{
					connectToServer();
					setupStreams();
					whileChatting();


				}catch(EOFException eofException){
					showMessage("server ended connection");

					}finally{
						closeCrap();
						}
				
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
			}

	}
	public void connectToServer() throws IOException{

		showMessage("\nattempting to connect server\n");
		connection = new Socket(InetAddress.getByName(serverIP), 1234);
		showMessage("\nconnected to " + connection.getInetAddress().getHostName());

	}

	public void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\nstreams are ready\n");
	}

	public void whileChatting() throws IOException{
		String message = "now can type";
		sendMessage(message);
		ableToType(true);
		do{
			try{
				message = (String) input.readObject(); 
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("idk what you sent");
				}
		}while(!message.equals("CLIENT - END"));



	}

	public void closeCrap(){
		showMessage("\nclosing connections");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
			}


	}

	public void sendMessage(String message){
		try{
			output.writeObject("\nCLIENT - "+message);
			output.flush();
			showMessage("\nCLIENT - "+message);
		}catch(IOException ioException){
			chatWindow.append("\ncant send message\n");
			}

	}
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(text);
				}
			}
		);

	}

	private void ableToType(final Boolean tof){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userText.setEditable(tof);
				}
			}
		);

	}

}