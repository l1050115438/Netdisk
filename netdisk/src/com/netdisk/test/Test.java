package com.netdisk.test;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import com.netdisk.pojo.FileInfo;
import com.netdisk.service.NetDiskService;
import com.netdisk.service.impl.NetDiskServiceImpl;

public class Test {
	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
		Configuration configuration = new Configuration();
		FileSystem fs  = FileSystem.get(configuration);
		System.out.println(fs);
		
		// ������ҳչʾ///////////////////////////////////////////////////////////////
		NetDiskService service = null;
		// serviceĿǰΪnull�������ʵ�ֻ�����ʵ������web����У�spring���˼�롣�ӿڱ�̡�
		
		service = new NetDiskServiceImpl();// ��̬�����֡�����ת�͡�
		
		List<FileInfo> fileList = service.getFileList("/");
		
		System.out.println(fileList.size());
		//////////////////////////////////////////////////////////////////////////////
		
		
	}
}
