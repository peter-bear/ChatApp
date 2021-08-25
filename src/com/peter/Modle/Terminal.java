package com.peter.Modle;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import com.peter.Service.AllIP;
/*
 * ��������
 * 
 * */
public class Terminal {
	private ServerSocket listener; //���ڼ����˿�
	public Terminal(int port){
		try {
			System.out.println("������Start");
			listener = new ServerSocket(port); //ȷ�϶˿�
			Socket accept; //���ӵ�Socketͨ��
			while(true) {
				accept = listener.accept(); //����ͨ������
				//��ȡ���Ӻ󽫴�socketͨ�������̺߳��̳߳��н��й���
				ObjectInputStream input = new ObjectInputStream(accept.getInputStream());
				Message message = (Message)input.readObject();
				if(message.getMT() == MessageType.connect) {
					ServerThread thread = new ServerThread(accept, message.getSender());
					System.out.println(message.getSender()+"������");
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
