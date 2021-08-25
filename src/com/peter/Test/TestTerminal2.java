package com.peter.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.peter.Modle.Message;

public class TestTerminal2 {
	public static void main(String[] args) {
		ServerSocket listener;
		try {
			System.out.println("Server Start");
			listener = new ServerSocket(10086);
			Socket accept = listener.accept();
			
			ObjectOutputStream oop = new ObjectOutputStream(accept.getOutputStream());
			Message message = new Message();
			message.setWords("Hello World");
			oop.writeObject(message);
			
			oop.close();
			accept.close();
			System.out.println("Server Close");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
