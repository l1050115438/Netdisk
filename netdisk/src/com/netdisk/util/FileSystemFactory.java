package com.netdisk.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

/**
 * FileSystem ������ ����ģʽ
 * @author yaohuicheng
 *
 */
public class FileSystemFactory {
	
	public static FileSystem fileSystem;
	
	static {
		try {
			fileSystem = FileSystem.get(new Configuration());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private FileSystemFactory(){}
	
	public static FileSystem getFileSystem() {
		if(fileSystem == null) {
			try {
				fileSystem = FileSystem.get(new Configuration());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return fileSystem;
	}
	
}
