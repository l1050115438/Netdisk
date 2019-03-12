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
 * ���̿���
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/loginServlet")  // ��������ע����һ��servlet���񣬷�������·��/loginServlet
public class LoginServlet extends HttpServlet {

	private LoginServcie servcie = new LoginServcieImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1����ȡ�û��ύ�Ĳ���ֵ  get��������   
		String parameter1 = request.getParameter("username");
		String parameter2 = request.getParameter("pwd");
		
		// 2��ҵ����   admin admin
		boolean flag = servcie.login(parameter1, parameter2);

		// 3��������Ӧ   �ض��򣨿ͻ��˵���Ϊ����ת�����������ڲ�����Ϊ��
		if(flag) {
			// ʹ��ת�� success.jsp
			// ��ȡת���ĵ�ַ
			// ʹ��request��ֵ
			request.setAttribute("username", parameter1);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("success.jsp");
			requestDispatcher.forward(request, response);
			//response.getWriter().println("ok����¼�ɹ���");
		}else {
			// �ض��� ��������
			//response.sendRedirect("http://localhost:9999/netdisk/error.jsp");// ����� �ͻ���
			response.sendRedirect(request.getContextPath() + "/error.jsp");// /disk
			
			//response.getWriter().println("error,ʧ�ܣ�");
		}		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post ��������
		this.doGet(request, response);
	}

}
