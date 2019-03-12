package com.netdisk.service;

import java.util.List;

import com.netdisk.pojo.FileInfo;

/**
 * 云盘业务接口类
 * @author yaohuicheng
 * @date 2019-01-09
 */
public interface NetDiskService {
	
	/**
	 * 获取指定目录下所有文件列表的方法
	 * @param  String类型的 path
	 * @return  容器（列表 list）
	 */
	List<FileInfo> getFileList(String path) throws Exception;
	
	/**
	 * 创建文件目录
	 * @param String类型的path
	 * @return void
	 */
	void createDir(String path) throws Exception;
	
	/**
	 * 浏览查看文件
	 * @param path
	 * @return String
	 */
	String viewFile(String path) throws Exception;
	
	/**
	 * 删除文件
	 * @param path
	 * @return void
	 */
	void del(String path) throws Exception;
	
	/**
	 * 创建新的文件
	 * @param path
	 * @return void
	 */
	void createNewFile(String path) throws Exception;
	
	/**
	 * 重命名
	 * @param path1原文件名   path2修改后的文件名
	 * @return void 
	 */
	void rename(String path1 ,String path2) throws Exception;
	
	/**
	 * 追加文件，修改文件
	 * @param {String path ,String centext}
	 * @return void
	 */
	void appendFile(String path ,String centext) throws Exception;
	
	/**
	 * 修改文件。覆盖写。
	 * 
	 */
	void writeFile(String path,String centext) throws Exception;
	
}
