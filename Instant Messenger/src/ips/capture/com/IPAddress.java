package ips.capture.com;

import java.net.*;
import java.util.*;
import java.io.*;
import java.nio.*;
 
public interface IPAddress{
class IPAddresss {
	String clientipdetect;
public void IPAddresss(){
	IPAddres();
}
 public void  getInterfaces (){
      try {
         Enumeration e = NetworkInterface.getNetworkInterfaces();
 
         while(e.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) e.nextElement();
         //   System.out.println("Net interface: "+ni.getName());
 
            Enumeration e2 = ni.getInetAddresses();
 
            while (e2.hasMoreElements()){
               InetAddress ip = (InetAddress) e2.nextElement();
               clientipdetect = ip.getHostAddress();
               System.out.println("asli: "+ clientipdetect);
               
            }
         }
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
 
   public static void IPAddres()  {
    IPAddresss ip = new IPAddresss();
    ip.getInterfaces();
    
   }
}
}