package com.peter.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.peter.Service.UserConnectService;

import java.util.Scanner;
import java.util.Set;

public class Menus {
	public static void Start() {
		FirstMenu();
	}
	
	private static void FirstMenu() {
		boolean open =true;
		Scanner input =null;
		UserConnectService.ConnectToServer();
		while(open) {
			System.out.println("\n\n====聊天一级菜单====");
			System.out.println("\t1. 聊天面版");
			System.out.println("\t2. 传输文件");
			System.out.println("\t3. 接收文件");
			System.out.println("\t4.退出");
			System.out.print("   选择：");
			input = new Scanner(System.in);
			switch (input.next()) {
			case "1":
				Chatting(input);
//				open = false;
				break;
				
			case "4":
				UserConnectService.Exit();
				open = false;
				break;
			default:
				break;
			}
		}
		
		input.close();
	

		
		
	}
	
	private static void Chatting(Scanner input) {
		boolean open = true;
		while(open) {
			
			System.out.println("\n\n====聊天二级菜单(聊天面板)====");
			System.out.println("在线联系人如下：");
			ArrayList<String> tmpSet = UserConnectService.GetAllIP();
			
			if(tmpSet == null) {
				System.out.println("暂时无上线用户");
				open = false;
			}else {
				for(String tmp: tmpSet) {
					System.out.println(tmp);
				}
				
				System.out.println("\n\t1.选择人聊天");
				System.out.println("\t2.退出");
				System.out.print("你的选择：");
				String choice = input.next();
				input.reset();
				
				switch (choice) {
				case "1":
					
					System.out.print("聊天对象的IP：");
					String receiver = input.next();
					input.reset();
					System.out.print("想说的话：");
					input.nextLine();
					String words = input.nextLine();
					input.reset();
					System.out.println(UserConnectService.SendChatContent(receiver, words));
					break;
					
				case "2":
					open = false;
					break;
				default:
					break;
				}
			}
		}
		
	}
	
//	private static Map<String, String> getAllIP(){
//		Map<String, String> tmpMap = new HashMap();
//		tmpMap.put("peter", "192.168.0.1");
//		tmpMap.put("tom", "192.168.0.1");
//		return tmpMap;
//	}
}
