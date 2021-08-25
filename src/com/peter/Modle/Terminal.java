package com.peter.Modle;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import com.peter.Service.AllIP;
/*
 * 服务器类
 * 
 * */
public class Terminal {
	private ServerSocket listener; //用于监听端口
	public Terminal(int port){
		try {
			System.out.println("服务器Start");
			listener = new ServerSocket(port); //确认端口
			Socket accept; //连接的Socket通道
			while(true) {
				accept = listener.accept(); //建立通道连接
				//获取连接后将此socket通道放入线程和线程池中进行管理
				ObjectInputStream input = new ObjectInputStream(accept.getInputStream());
				Message message = (Message)input.readObject();
				if(message.getMT() == MessageType.connect) {
					ServerThread thread = new ServerThread(accept, message.getSender());
					System.out.println(message.getSender()+"上线了");
					thread.start();
					ManageClientThreads.addThread(message.getSender(), thread);
				}
				
				
			}
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Map<String, String> GetAllIP() {
		return AllIP.GetALLIP();
	}
}
