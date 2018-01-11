package server.window.com;

import javax.swing.*;
import javax.swing.LayoutStyle.*;
import javax.swing.GroupLayout.*;

import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import server.sound.com.*;
import server.sound.com.SoundUtil.*;

import javax.swing.*;
import javax.swing.border.*;

public class Server extends JFrame implements SoundUtil {
	
	public ObjectOutputStream output;
	public JTextArea allmsg;
	public ObjectInputStream input;
	public ServerSocket server;
	public String myrealip="null";
	public Socket connection;
	public JTextField myip;
	public JTextField clientip;
	public JTextField mymsg;
	public String getclientip;
	private JTextField status;
	private String ipname;
	private String clientipstore;
	
	


// Creating Constructor
	public Server() {
		
// Title Heading
		super("Instant Messenger (Server)");
		
// To Close window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
//Top Heading ( Server Chat Window )
		JLabel serverchatwindow = new JLabel("Server Chat Window");
		
//Enter Your IP Address Label	
		JLabel entermyip = new JLabel("Your Username/IP Address :-");
		
//Enter Client IP Address Label			
		JLabel enterclientip = new JLabel("Client IP Address :-");
		
		myip = new JTextField();
		myip.setColumns(10);
		myip.setEditable(false);												
		getContentPane().add(myip);

		
		clientip = new JTextField();
		clientip.setHorizontalAlignment(SwingConstants.CENTER);
		clientip.setEditable(false);
		clientip.setColumns(10);
			clientip.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent eventss){
						eventss.equals(connection.getInetAddress().getHostName());	
						clientipstore =	connection.getInetAddress().getHostAddress();
						
					}
				}
			);
		getContentPane().add(clientip);				

//Enter Your Messages Label			
		JLabel entermymsg = new JLabel("Enter Your Message Here :-");
		
		mymsg = new JTextField();
		mymsg.setForeground(Color.BLUE);
		mymsg.setColumns(10);
		mymsg.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						try {
							sendMessage(event.getActionCommand());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mymsg.setText("");
					}
				}
			);
		getContentPane().add(mymsg);
		setSize(500,500);
		setVisible(true);
//Shows Messages Label	
		JLabel showallmsg = new JLabel("Chat Messages :-");
		
		allmsg = new JTextArea();
		//add(new JScrollPane(allmsg));
		allmsg.setForeground(Color.RED);
		setVisible(true);
		allmsg.setEditable(false);

//Shows Status of Connection With Client
		status = new JTextField();
		status.setEditable(false);
		status.setHorizontalAlignment(SwingConstants.CENTER);
		status.setBackground(new Color(0, 206, 209));
		
		status.setText(" Not Connected");
		status.setColumns(9);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(allmsg, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(entermyip)
								.addComponent(enterclientip))
							.addGap(14)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(serverchatwindow)
								.addComponent(myip, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
								.addComponent(clientip))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(entermymsg)
						.addComponent(mymsg, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addComponent(showallmsg))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(serverchatwindow)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(entermyip)
						.addComponent(myip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(enterclientip)
						.addComponent(clientip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(entermymsg)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(mymsg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(showallmsg)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(allmsg, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	
	}
	
	
// Sound Comes When Message received
	
	
	Sound sound1 = new Sound();
	
	
// Setup & Execute The Server
	
		public void startRunning() throws Exception{
			try{
				server = new ServerSocket(8080,100);
				while(true){
					try{
						myIp();
						
						waitingConnection();
						setupStreams();
						status.setBackground(new Color(0, 100, 0));
						status.setForeground(Color.YELLOW);
						status.setText("Connected");
						
						whileChatting();
						
						// Connect & Conversation Start
						
						}catch(EOFException eofException){
							status.setBackground(new Color(139, 0, 0));
							status.setForeground(Color.YELLOW);
							status.setText("Disconnected");
							allmsg.setForeground(Color.BLUE);
							showMessage("\n SERVER Has Terminated The Program ... :(");
						}finally{
							
							closeChat();
						}
					}
				}catch(IOException ioException){
					status.setBackground(new Color(139, 0, 0));
					status.setForeground(Color.YELLOW);
					status.setText("Disconnected");
					allmsg.setForeground(Color.BLUE);
					ioException.printStackTrace();
				}
			}
		
//Waiting For Connection, And Then Displaying Connection Information
		
		private void waitingConnection() throws IOException{
			showMessage("Waiting For Someone Special To Connect.... :) ,\nPlease Wait SABAR KA FAL MEETHA HE HOTA HAI..... ;)\n");
			connection = server.accept();
			showMessage("Got Connected To "+connection.getInetAddress().getHostName());
			showMessage(".... :)");
			
			clientip.setText(clientipstore);	
		}
		
		void myIp() throws UnknownHostException{
			InetAddress IP=InetAddress.getLocalHost();
			myrealip = IP.toString();
			ipname = IP.getHostName();
			
			myip.setText(myrealip);	
			myip.setForeground(new Color(128, 0, 0));
		}
		
// Setting Up The Streams To Send & Receive Messages Between Server & Client
		
		private void setupStreams() throws Exception{
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			showMessage("\nStreams Are Ready To Setup! Please Wait.... :)\n");
		}
		
// During The Chat Conversation
			
		private void whileChatting() throws Exception{
			String message = "You Are Now Connected To Someone Special! \nSay Hie... :)\n\n";
			sendMessage(message);
			ableToType(true);
			do{
				try{
					message = (String) input.readObject();
					showMessage("\n" + message);
					}catch(ClassNotFoundException classNotFoundException){
						status.setBackground(new Color(139, 0, 0));
						status.setForeground(Color.YELLOW);
						status.setText("Disconnected");
						allmsg.setForeground(Color.BLUE);
						showMessage("\nDue To Some Technical Problem We Are Unable To This Task :( ,\nPlease Try Again :) \n");
					}
				}while(!message.equals("CLIENT Has Terminated The Program :o ,\n Tell Him To Start The Program Again Otherwise Don't Waste My Time.... :o ,\n You Also Close The Program :x \n"));
			}
		
		
// Closing Streams & Sockets After Chatting Is Done
		
		private void closeChat(){
			
			status.setBackground(new Color(139, 0, 0));
			status.setForeground(Color.YELLOW);
			status.setText("Disconnected");
			allmsg.setForeground(Color.BLUE);
			showMessage("\n\nClosing The Connection .... :( \n\n*************** BYE BYE :) *****************\n");
			ableToType(false);
			try{
				output.close();
				input.close();
				connection.close();
			}catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
		
// Sending A Message To The Client
		
		private void sendMessage(String message) throws Exception{
			try{
				output.writeObject(ipname+ "  -  "+message);
				output.flush();	
				
				showMessage("\n");
				
				
				
				showMessage(ipname+ "  -  "+ message);
				sound1.SoundUtils();
			}catch(IOException ioException){
				allmsg.append("\nDue To Some Technical Problem We Are Unable To Send This Message :( , \nPlease Try Again :)\n");	
			}
		}
		
// Updates ChatWindow Everytime
		
		private void showMessage(final String text){
			SwingUtilities.invokeLater(
					new Runnable(){
						public void run(){
							allmsg.append(text);
						}
					}
				);
			}
		
// Message Typed By The User In TextBox
		
		private void ableToType(final boolean tof){
			SwingUtilities.invokeLater(
					new Runnable(){
						public void run(){
							mymsg.setEditable(tof);
						}
					}
				);
		}	
		
		
}
