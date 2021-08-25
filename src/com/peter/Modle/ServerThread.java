package com.peter.Modle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.peter.Service.ShowOnline;

public class ServerThread extends Thread{
	private Socket socket;
	private String UID;
	//�ڷ������˹�����̣߳�ÿ���߳��ж�Ӧ��UID
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
		//ʹ��ѭ����ʹ��socket�߳�һֱ���ּ���״̬����û�����ݷ��������������ڴ�
		while(true) {
			Message message;
			try {
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				message = (Message)input.readObject();
				
				//�жϷ��͹���������
				if(message.getMT()==MessageType.allUsers) { //�������������û�
					System.out.println(UID+"����������������û�");
					message.setUsers(ShowOnline.getAllIP(UID));
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					output.writeObject(message);
					
				}
				
				else if(message.getMT() == MessageType.chat) {
					String receiver = message.getReceiver();
					ServerThread receiverThread = ManageClientThreads.getThread(receiver);
					System.out.println(message.getSender()+"��"+receiver+"����:\n"+message.getWords());
					if(receiverThread!=null) {
						
						ObjectOutputStream output = new ObjectOutputStream(receiverThread.getSocket().getOutputStream());
						output.writeObject(message);
					}else {
						
					}
					
				}
				else if(message.getMT()==MessageType.exit) { //�����˳�
					//�˳�ʱ���̳߳���ɾ������socketͨ���ر�
					ManageClientThreads.delThread(UID);
					socket.close();
					System.out.println(message.getSender()+"�˳�");
					break; //��ֹѭ��
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
