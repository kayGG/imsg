package client.window.com;

import java.awt.BorderLayout.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import server.sound.com.*;
import server.sound.com.SoundUtil.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.GroupLayout.*;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.*;


//import chat.client.com.SoundUtils;


public class Client extends JFrame {
	public JTextField myip;
	public JTextArea allmsg;
	public JTextField serverip;
	public String getserverip;
	public JTextField mymsg;
	public ObjectOutputStream output;
	public ObjectInputStream input;
	public String message = "";
	public String serverIP;
	public String ipname;
	public Socket connection;
	public String myrealip;
	private JTextField status;
	private String serveripwrite;

// Creating Constructor
	public Client(String host) {
		
// Title Heading
				super("Instant Messenger (Client)");
				serverIP = host;
				
// To Close window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(850, 100, 450, 300);
		
//Top Heading ( Client Chat Window ) Label
		JLabel clientchatwindow = new JLabel("Client Chat Window");
		
//Enter Your IP Address Label
		JLabel entermyip = new JLabel("Your Username/IP Address :-");
		
//Enter Server IP Address Label		
		JLabel enterserverip = new JLabel("Server IP Address :-");
		
//Enter Your Messages Label		
		JLabel entermymsg = new JLabel("Enter Your Message Here :-");
		
//Shows Messages Label	
		JLabel showallmsg = new JLabel("Chat Messages :-");
		
		myip = new JTextField();
		myip.setColumns(10);
		getContentPane().add(myip);
		myip.setEditable(false);
		myip.setForeground(new Color(128, 0, 0));

		serverip = new JTextField();
		serverip.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent take){
						
						take.equals(serveripwrite);
						}
					}
				);
		
		serverip.setColumns(10);
			serverip.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent events){
						
					events.equals(getserverip);
					}
				}
			);

		
		JButton connect = new JButton("Connect");
		connect.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent eventss){
						//if(eventss.equals(serveripwrite) == eventss.equals(clientipdetect))
					//	{
					//		System.out.println("ahsdga");
					//	}
					//	else{
					//		System.out.println("elsre");
					//	}
					}
				}
			);
		getContentPane().add(connect);
		
		mymsg = new JTextField();
		mymsg.setForeground(Color.RED);
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
		allmsg = new JTextArea();
		//add(new JScrollPane(allmsg));
		allmsg.setForeground(Color.BLUE);
		setVisible(true);
		allmsg.setEditable(false);
		
		status = new JTextField();
		status.setBackground(new Color(0, 206, 209));
		status.setHorizontalAlignment(SwingConstants.CENTER);
		
		status.setText("Not Connected");
		status.setEditable(false);
		status.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(entermyip)
								.addComponent(enterserverip))
							.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(serverip)
								.addComponent(myip, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
							.addGap(18))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(entermymsg)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(connect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(91))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(169)
					.addComponent(clientchatwindow)
					.addContainerGap(221, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(mymsg, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(showallmsg)
					.addContainerGap(390, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(allmsg, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(clientchatwindow)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(entermyip)
						.addComponent(myip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(connect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(enterserverip)
								.addComponent(serverip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(entermymsg))
						.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(mymsg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(showallmsg)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(allmsg, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}

	
//Getting IP of Own System
	void myIp() throws UnknownHostException{
		InetAddress IP=InetAddress.getLocalHost();
		myrealip = IP.toString();
		ipname = IP.getHostName();
		myip.setText(myrealip);	
	}

	
// Sound Comes When Message received
	
	
	Sound sound1 = new Sound();
	

// Connecting To The Server
	
	public void startRunning(){
		
		try{
			myIp();
			connectToServer();
			setupStreams();
			status.setBackground(new Color(0, 100, 0));
			status.setForeground(Color.YELLOW);
			status.setText("Connected");
			
			whileChatting();
		}catch(EOFException eofException){
			status.setBackground(new Color(139, 0, 0));
			status.setForeground(Color.YELLOW);
			allmsg.setForeground(Color.RED);
			status.setText("Disconnected");
			showMessage("\n CLIENT Has Terminated The Program.... :( \n");
		}catch(IOException ioException){
			status.setBackground(new Color(139, 0, 0));
			allmsg.setForeground(Color.RED);
			status.setForeground(Color.YELLOW);
			status.setText("Disconnected");
			ioException.printStackTrace();
		}finally{
			closeChat();
		}
	}
	
// Connected To The Server
	
	private void connectToServer() throws IOException{
		showMessage("Connecting To The SERVER Please Wait ...... :) \n");
		connection = new Socket(InetAddress.getByName(serverIP), 8080);
		showMessage("Got Connected To : " + connection.getInetAddress().getHostName());
		showMessage(".... :)");
	}
	
// Setting Up The Streams To Send & Receive Messages Between Server & Client
	
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\nStreams Are Ready To Setup! Please Wait.... :)\n");
	}

// While Chatting With Server
	
	private void whileChatting() throws IOException{
		ableToType(true);
		do{
			try{
				message = (String) input.readObject();
				showMessage("\n");
				showMessage(message);
			}catch(ClassNotFoundException classNotFoundException){
				status.setBackground(new Color(139, 0, 0));
				allmsg.setForeground(Color.RED);
				status.setForeground(Color.YELLOW);
				status.setText("Disconnected");
				showMessage("\nDue To Some Technical Problem We Are Unable To This Task :( ,\nPlease Try Again :) \n");
			}
		}while(!message.equals("\nSERVER Has Terminated The Program :o ,\n Tell Him To Start The Program Again Otherwise Don't Waste My Time.... :o ,\n You Also Close The Program :x \n"));
	}
	
// Closing The Streams & Sockets
	
	private void closeChat(){
		showMessage("\n\nClosing The Connection .... :(\n\n*************** BYE BYE :) *****************\n");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			status.setBackground(new Color(139, 0, 0));
			status.setForeground(Color.YELLOW);
			allmsg.setForeground(Color.RED);
			status.setText("Disconnected");
			ioException.printStackTrace();
		}
	}
	
// Sending Messages To The Server
	
	private void sendMessage(String message) throws Exception{
		try{
			output.writeObject(ipname+ "  -  "+message);
			output.flush();
			showMessage("\n");
			
			sound1.SoundUtils();
			
			showMessage(ipname);
			showMessage("  -  "+ message);
			
		}catch(IOException ioException){
			status.setBackground(new Color(139, 0, 0));
			allmsg.setForeground(Color.RED);
			status.setText("Disconnected");
			allmsg.append("\nDue To Some Technical Problem We Are Unable To Send This Message :( , \nPlease Try Again :)\n");
		}
	}

// Updating The ChatWindow Everytime
	
	private void showMessage(final String m){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						allmsg.append(m);
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

