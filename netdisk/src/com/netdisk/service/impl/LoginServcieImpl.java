package com.netdisk.service.impl;

import org.springframework.stereotype.Service;

import com.netdisk.service.LoginServcie;

@Service  //�� ��ǰ���ʵ�����ŵ� spring�����У�����=loginServiceImpl
public class LoginServcieImpl implements LoginServcie {

	@Override
	public boolean login(String username, String pwd) {
		return username.equals("admin") && pwd.equals("admin");
	}

}
