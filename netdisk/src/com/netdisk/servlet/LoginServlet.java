package com.netdisk.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netdisk.service.LoginServcie;
import com.netdisk.service.impl.LoginServcieImpl;

/**
 * 流程控制
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/loginServlet")  // 在容器中注册了一个servlet服务，服务请求路径/loginServlet
public class LoginServlet extends HttpServlet {

	private LoginServcie servcie = new LoginServcieImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1、获取用户提交的参数值  get请求类型   
		String parameter1 = request.getParameter("username");
		String parameter2 = request.getParameter("pwd");
		
		// 2、业务处理   admin admin
		boolean flag = servcie.login(parameter1, parameter2);

		// 3、做出响应   重定向（客户端的行为）和转发（服务器内部的行为）
		if(flag) {
			// 使用转发 success.jsp
			// 获取转发的地址
			// 使用request传值
			request.setAttribute("username", parameter1);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("success.jsp");
			requestDispatcher.forward(request, response);
			//response.getWriter().println("ok，登录成功！");
		}else {
			// 重定向 二次请求
			//response.sendRedirect("http://localhost:9999/netdisk/error.jsp");// 浏览器 客户端
			response.sendRedirect(request.getContextPath() + "/error.jsp");// /disk
			
			//response.getWriter().println("error,失败！");
		}		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post 请求类型
		this.doGet(request, response);
	}

}
