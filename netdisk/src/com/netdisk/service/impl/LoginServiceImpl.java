package com.netdisk.service.impl;

import org.springframework.stereotype.Service;

import com.netdisk.service.LoginService;
@Service
public class LoginServiceImpl implements LoginService {

	@Override
	public boolean login(String username, String pwd) {
		// TODO Auto-generated method stub
		return username.equals("admin")&&pwd.equals("admin");
	}

}
