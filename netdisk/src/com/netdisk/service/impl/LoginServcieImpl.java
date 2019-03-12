package com.netdisk.service.impl;

import org.springframework.stereotype.Service;

import com.netdisk.service.LoginServcie;

@Service  //把 当前类的实例，放到 spring容器中，名字=loginServiceImpl
public class LoginServcieImpl implements LoginServcie {

	@Override
	public boolean login(String username, String pwd) {
		return username.equals("admin") && pwd.equals("admin");
	}

}
