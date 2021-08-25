package com.peter.Service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.peter.Modle.Message;
import com.peter.Modle.MessageType;
import com.peter.Modle.User;
import com.peter.Modle.UserThread;

public class UserConnectService {
	private static Socket UserSocket = null;
	private static User user= new User();
	private static UserThread userThread;
	public static void ConnectToServer() {
		try {
			UserSocket = new Socket("137.112.237.25", 10086);
			Message message = new Message();
			message.setMT(MessageType.connect);
			message.setSender(user.getUname()+":"+user.getUID());
			
			
			ObjectOutputStream output = new ObjectOutputStream(UserSocket.getOutputStream());
			output.writeObject(message);
			
			userThread = new UserThread(UserSocket);
			userThread.start();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//获取在线用户IP
	public static ArrayList<String> GetAllIP() {

		
		Message message = new Message();
		message.setMT(MessageType.allUsers);
		message.setSender(user.getUname()+":"+user.getUID());
		try {
			ObjectOutputStream output = new ObjectOutputStream(userThread.getUserSocket().getOutputStream());
			output.writeObject(message);
			//等待发送IP
			if(userThread.getAllUsers() == null) {
				Thread.sleep(5000);
			}
			if(userThread.getAllUsers() == null)
				return null;
			return userThread.getAllUsers();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String SendChatContent(String receiver, String words){
		Message message =  new Message();
		message.setMT(MessageType.chat);
		message.setSender(user.getUname()+":"+user.getUID());
		message.setReceiver(receiver);
		Date date = new Date();
		message.setWords(words+"\t"+date.toString());
		
		try {
			ObjectOutputStream output = new ObjectOutputStream(userThread.getUserSocket().getOutputStream());
			output.writeObject(message);
			String words1=message.getSender()+" 对 "+message.getReceiver()+" 说:\n "+message.getWords();
			userThread.getContent().add(words1);
			return words1;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "连接超时，信息未发送";
	} 
	
	//退出服务
	public static void Exit() {
		//向服务器发送退出指令
		Message message = new Message();
		message.setMT(MessageType.exit);
		message.setSender(user.getUname()+":"+user.getUID());
		
		try {
			ObjectOutputStream output = new ObjectOutputStream(userThread.getUserSocket().getOutputStream());
			output.writeObject(message);
			System.exit(0);//系统退出
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
