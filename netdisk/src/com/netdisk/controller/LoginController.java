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
 * 登录业务控制类
 * 1、请求和响应 ； 2、spring IOC 注入 
 * @author yaohuicheng
 *
 */
@Controller  // loginController
public class LoginController {
	
	// 注入业务类
	@Autowired  // 把spring容器中loginServiceImpl指向 servcie
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
	 * 登录的方法 
	 */
	@RequestMapping("loginServlet")
	public String abc(String username,String pwd,Map reMap) {// spring拦截器，
		//1、获取用户提交的参数值 
		// servlet 通过request获取，springmvc 通过参数列表拦截实现
		System.out.println("ok");
		
		//2、 调用业务类中的方法  // new LoginServer的实现类； springmvc框架中，通过ioc注入的方式。（@Autowired @Service）
		boolean flag = service.login(username, pwd); // 空指针异常
		
		// 3\重定向和转发的控制   // 转发服务器，request 重定向，response
		if(flag == true) {
			// 传值？？？？？？？？ Model
			reMap.put("username", username);
			return  "success.html";// FreeMarker 视图解析器  // 转发	
		}else {
			return "redirect:error.html";
		}
		
	}
}
