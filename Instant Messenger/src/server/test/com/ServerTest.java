package server.test.com;


import javax.swing.JFrame;
import server.window.com.*;

// Testing The Server Program

public class ServerTest {
	public static void main(String[] args) throws Exception{
		Server main = new Server();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.startRunning();
	}
}
