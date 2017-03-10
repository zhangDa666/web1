package com.zd.test;

import java.util.List;

import com.google.common.collect.Lists;

public class Test1 {
	public static void main(String[] args) {
		
		List<String> list = Lists.newCopyOnWriteArrayList();
		list.add("qq");  list.add("qq");  list.add("qq");
		
	}
}
