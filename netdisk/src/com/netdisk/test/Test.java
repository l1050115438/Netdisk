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
		
		// 测试首页展示///////////////////////////////////////////////////////////////
		NetDiskService service = null;
		// service目前为null，具体的实现或者是实例化，web框架中，spring框架思想。接口编程。
		
		service = new NetDiskServiceImpl();// 多态的体现。向上转型。
		
		List<FileInfo> fileList = service.getFileList("/");
		
		System.out.println(fileList.size());
		//////////////////////////////////////////////////////////////////////////////
		
		
	}
}
