package com.peter.Service;

import java.util.ArrayList;
import java.util.Set;

import com.peter.Modle.ManageClientThreads;

public class ShowOnline {
	//根据线程池中的所有线程，获取所有UID，即在线的用户
	public static ArrayList<String> getAllIP(String UID){
		ArrayList<String> ips = new ArrayList<>(ManageClientThreads.threads.keySet());
		ips.remove(UID); //在线用户查看时，排除用户自己
		return ips;
	}
}
