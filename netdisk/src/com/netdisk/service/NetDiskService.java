package com.netdisk.service;

import java.util.List;

import com.netdisk.pojo.FileInfo;

/**
 * ����ҵ��ӿ���
 * @author yaohuicheng
 * @date 2019-01-09
 */
public interface NetDiskService {
	
	/**
	 * ��ȡָ��Ŀ¼�������ļ��б�ķ���
	 * @param  String���͵� path
	 * @return  �������б� list��
	 */
	List<FileInfo> getFileList(String path) throws Exception;
	
	/**
	 * �����ļ�Ŀ¼
	 * @param String���͵�path
	 * @return void
	 */
	void createDir(String path) throws Exception;
	
	/**
	 * ����鿴�ļ�
	 * @param path
	 * @return String
	 */
	String viewFile(String path) throws Exception;
	
	/**
	 * ɾ���ļ�
	 * @param path
	 * @return void
	 */
	void del(String path) throws Exception;
	
	/**
	 * �����µ��ļ�
	 * @param path
	 * @return void
	 */
	void createNewFile(String path) throws Exception;
	
	/**
	 * ������
	 * @param path1ԭ�ļ���   path2�޸ĺ���ļ���
	 * @return void 
	 */
	void rename(String path1 ,String path2) throws Exception;
	
	/**
	 * ׷���ļ����޸��ļ�
	 * @param {String path ,String centext}
	 * @return void
	 */
	void appendFile(String path ,String centext) throws Exception;
	
	/**
	 * �޸��ļ�������д��
	 * 
	 */
	void writeFile(String path,String centext) throws Exception;
	
}
