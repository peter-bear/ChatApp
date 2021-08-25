package com.peter.Modle;

import java.util.HashMap;
//�����̵߳��࣬�̳߳�
public class ManageClientThreads {
	public static HashMap<String, ServerThread> threads = new HashMap<>();
	//UIDΪ�߳�Key��socketΪvalue
	public static void addThread(String UID, ServerThread s ) {
		threads.put(UID, s);
		
	}
	//ɾ���߳�
	public static void delThread(String UID) {
		threads.remove(UID);
	}
	//��ȡ��Ӧ�߳�
	public static ServerThread getThread(String UID) {
		for(String id:threads.keySet()) {
			if(KMP(id,UID,MatchMap(UID))!=-1) {
//				System.out.println("Find "+UID);
				return threads.get(id);
			}
				
		}
		return null;
	}
	
	
//	public static void main(String[] args) {
//		System.out.println(KMP("peterbear:127.0.0.1","127.0.0.1",MatchMap("127.0.0.1")));
//	}
	
	
	private static int[] MatchMap(String dest) {
		int[] map = new int[dest.length()];
		map[0]=0;
		for(int i=0,j=0;i<dest.length();i++) {
			while(j>0&&dest.charAt(i)!=dest.charAt(j))
				j = map[j-1];
			if(dest.charAt(i)==dest.charAt(j))
				j++;
			map[i] = j;
			
		}
		return map;
	}
	
	private static int KMP(String st1, String st2, int[] map) {
		for(int i=0, j=0;i<st1.length();i++) {
			while(j>0&&st1.charAt(i)!=st2.charAt(j))
				j = map[j-1];
			if(st1.charAt(i)==st2.charAt(j))
				j++;
			if(j>=st2.length())
				return i-j+1;
		}
		return -1;
	}
	
}
