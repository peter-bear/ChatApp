package com.peter.Service;

import java.util.ArrayList;
import java.util.Set;

import com.peter.Modle.ManageClientThreads;

public class ShowOnline {
	//�����̳߳��е������̣߳���ȡ����UID�������ߵ��û�
	public static ArrayList<String> getAllIP(String UID){
		ArrayList<String> ips = new ArrayList<>(ManageClientThreads.threads.keySet());
		ips.remove(UID); //�����û��鿴ʱ���ų��û��Լ�
		return ips;
	}
}
