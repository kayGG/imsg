package client.test.com;

import javax.swing.JFrame;
import client.window.com.*;
import ips.capture.com.*;

public class ClientTest implements IPAddress {

	public static void main(String[] args)
	{
		IPAddresss ipdetect = new IPAddresss() ;
		
		ipdetect.IPAddresss();
		
		Client kay;
		kay = new Client("127.0.0.1");
		kay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		kay.startRunning();
		
	}
}