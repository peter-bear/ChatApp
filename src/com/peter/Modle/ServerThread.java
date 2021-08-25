package com.peter.Modle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.peter.Service.ShowOnline;

public class ServerThread extends Thread{
	private Socket socket;
	private String UID;
	//在服务器端管理的线程，每个线程有对应的UID
	public ServerThread(Socket s, String id) {
		socket = s;
		UID =id;
	}
	
	public String getUID() {
		return UID;
	}
	
	public Socket getSocket() {
		return socket;
	}


	@Override
	public void run() {
		//使用循环，使此socket线程一直保持监听状态，若没有内容发过来，则阻塞在此
		while(true) {
			Message message;
			try {
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				message = (Message)input.readObject();
				
				//判断发送过来的内容
				if(message.getMT()==MessageType.allUsers) { //请求所有在线用户
					System.out.println(UID+"向服务器请求在线用户");
					message.setUsers(ShowOnline.getAllIP(UID));
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					output.writeObject(message);
					
				}
				
				else if(message.getMT() == MessageType.chat) {
					String receiver = message.getReceiver();
					ServerThread receiverThread = ManageClientThreads.getThread(receiver);
					System.out.println(message.getSender()+"向"+receiver+"发送:\n"+message.getWords());
					if(receiverThread!=null) {
						
						ObjectOutputStream output = new ObjectOutputStream(receiverThread.getSocket().getOutputStream());
						output.writeObject(message);
					}else {
						
					}
					
				}
				else if(message.getMT()==MessageType.exit) { //请求退出
					//退出时从线程池中删除，将socket通道关闭
					ManageClientThreads.delThread(UID);
					socket.close();
					System.out.println(message.getSender()+"退出");
					break; //终止循环
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
