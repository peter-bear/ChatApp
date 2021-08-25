package com.peter.Modle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


//用户线程类，用于接收服务器发送过来的信息
public class UserThread extends Thread{
	private Socket UserSocket=null;
	private static ArrayList<String> allUsers=null;
	private static ArrayList<String> ChatContent=new ArrayList<>();
	
	public UserThread(Socket us) {
		UserSocket = us;
	}
	
	public Socket getUserSocket() {
		return UserSocket;
	}

	public ArrayList<String> getAllUsers() {
		return allUsers;
	}
	
	public ArrayList<String> getContent(){
		return ChatContent;
	}


	@Override
	public void run() {
		while(true) {
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(UserSocket.getInputStream());
				Message mms = (Message)ois.readObject(); //如果服务器没有发送数据，通道会一直阻塞
				
				if(mms.getMT() == MessageType.allUsers) {
					UserThread.allUsers = mms.getUsers();
//					System.out.println("hello");
				}else if(mms.getMT() == MessageType.chat) {
					ChatContent.add(mms.getSender()+" 对 "+mms.getReceiver()+" 说:\n "+mms.getWords());
					System.out.println("过往信息：");
					//显示过往消息信息
					for(String chatContent:ChatContent)
						System.out.println(chatContent);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
		}
	}
	
}
