package com.peter.Modle;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class User {
	private String UID = "127.0.0.1";
	private String Uname=null;
	
	public User(){
/*
 * 考虑到我的电脑安装了虚拟机，需要检查具体的IP，所以下面的程序用于扫描本机所有网络端口，根据需要选择相应的ip
 * */
		try {
			Uname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Enumeration<NetworkInterface> i =null;
		try {
			i = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		  ArrayList<String> IPlist = new ArrayList<>();  
		  while(i.hasMoreElements())
		    {
		        NetworkInterface n = i.nextElement();
		        Enumeration<InetAddress> ea = n.getInetAddresses();
		        
		        while(ea.hasMoreElements())
		        {	
		            InetAddress a = ea.nextElement();
		            if(a.getHostAddress().toCharArray()[3] =='.') {
		            	IPlist.add(a.getHostAddress());
		            }
		        }
		    }
		    
//选取对应的IP
		  for(String ip:IPlist) {
			  if(ip.indexOf("137.")!=-1) {
				  UID = ip;
				  break;
			  }
			  if(ip.indexOf("192.")!=-1) {
				  UID = ip;
			  }
			  
			  
		  }


	}
	
	public String getUID() {
		return UID;
	}

	public String getUname() {
		return Uname;
	}

	
	

}
