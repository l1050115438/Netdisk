package com.netdisk.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netdisk.service.LoginServcie;
import com.sun.org.glassfish.gmbal.ParameterNames;

/**
 * ��¼ҵ�������
 * 1���������Ӧ �� 2��spring IOC ע�� 
 * @author yaohuicheng
 *
 */
@Controller  // loginController
public class LoginController {
	
	// ע��ҵ����
	@Autowired  // ��spring������loginServiceImplָ�� servcie
	private LoginServcie service = null;
	
	@RequestMapping("{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		return  module + "/" + url + ".html";
	}
	@RequestMapping("{url}.html")
	public String url(@PathVariable("url") String url){
		return url + ".html";
	}
	@RequestMapping("/")
	public String index(){
		return "redirect:index.html";
	}
	
	/**
	 * ��¼�ķ��� 
	 */
	@RequestMapping("loginServlet")
	public String abc(String username,String pwd,Map reMap) {// spring��������
		//1����ȡ�û��ύ�Ĳ���ֵ 
		// servlet ͨ��request��ȡ��springmvc ͨ�������б�����ʵ��
		System.out.println("ok");
		
		//2�� ����ҵ�����еķ���  // new LoginServer��ʵ���ࣻ springmvc����У�ͨ��iocע��ķ�ʽ����@Autowired @Service��
		boolean flag = service.login(username, pwd); // ��ָ���쳣
		
		// 3\�ض����ת���Ŀ���   // ת����������request �ض���response
		if(flag == true) {
			// ��ֵ���������������� Model
			reMap.put("username", username);
			return  "success.html";// FreeMarker ��ͼ������  // ת��	
		}else {
			return "redirect:error.html";
		}
		
	}
}
