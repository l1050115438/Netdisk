package com.netdisk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import com.netdisk.pojo.FileInfo;
import com.netdisk.service.NetDiskService;
import com.netdisk.util.FileSystemFactory;
import com.netdisk.util.Tools;

/**
 * 云盘业务实现类
 * @author yaohuicheng
 * @version v1.0
 * 
 */
@Service
public class NetDiskServiceImpl implements NetDiskService {
	
	// 单例的工厂类 FileSystemFactory
	private FileSystem fs = FileSystemFactory.getFileSystem();
	
	@Override
	public List<FileInfo> getFileList(String path) throws Exception {
		
		FileStatus[] listStatus = fs.listStatus(new Path(path));
		
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>(); // 有序可重复的容器（集合框架）
		
		for (FileStatus fileStatus : listStatus) {
			
			FileInfo tmpFileInfo = new FileInfo();
			
			// 把tmpFileInfo展开  ，把fileStatus的值填充到tmpFIleinfo中。
			String tmp = fileStatus.getPath().toString().substring(fileStatus.getPath().toString().indexOf("//") + 2);
			String absolutePath = tmp.substring(tmp.indexOf("/"));
			tmpFileInfo.setAbsolutePath(fileStatus.isDirectory() ? absolutePath : absolutePath); 
			//   /usr/test/    /user/hello.txt
			
			tmpFileInfo.setAccess_time(fileStatus.getAccessTime());//
			tmpFileInfo.setBlock_replication(fileStatus.getReplication());// 备份因子
			tmpFileInfo.setBlocksize(fileStatus.getBlockSize());
			tmpFileInfo.setGroup(fileStatus.getGroup());
			tmpFileInfo.setIsdir(fileStatus.isDirectory());
			tmpFileInfo.setLength(fileStatus.getLen());
			
			long time = fileStatus.getModificationTime();
			tmpFileInfo.setModification_time(Tools.getStringDate(time, null));
			tmpFileInfo.setName(fileStatus.getPath().getName());
			tmpFileInfo.setOwner(fileStatus.getOwner());
			
			//
			fileInfoList.add(tmpFileInfo);
		}
		// 文件排序
		fileInfoList.sort(new FileInfo());
		
		return fileInfoList;
	}

	@Override
	public void createDir(String p) throws Exception {
		Path path = new Path(p);
		// 判断文件夹是否存在，如果存在，提示已经存在。
		if(fs.exists(path)) {
			throw new Exception(p + "已经存在！");
		}
		
		if(fs.mkdirs(path)) {
			return;  // break;
		}
		
		throw new Exception("创建失败！");
		
	}

	@Override
	public String viewFile(String p) throws Exception {
		
		Path path = new Path(p);
		
		// hdfs中的文件 通过IO写入到内存中。  在内存中就可以直接显示。
		FSDataInputStream open = fs.open(path);
		
		// 读取的缓冲区 1024
		byte[] b = new byte[1024];
		
		// 第一次读
		int flag = open.read(b);
		
		StringBuffer sbuf = new StringBuffer();
		
		while(flag != -1) {
			// 第一次写 
			sbuf.append(new String(b,"utf-8"));
			
			// 洗一次读
			flag = open.read(b);
		}
		
		// inputStream输入流关闭
		open.close();
		
		return sbuf.toString();
	}

	@Override
	public void del(String p) throws Exception {
		Path path = new Path(p);
		// 判断文件夹是否存在，如果存在，提示已经存在。
		if(!fs.exists(path)) {
			throw new Exception(p + "不存在！");
		}
		
		if(fs.delete(path,true)) {
			return;  // break;
		}
		
		throw new Exception("删除失败！");
	}

	@Override
	public void createNewFile(String p) throws Exception {
		Path path = new Path(p);
		// 判断文件夹是否存在，如果存在，提示已经存在。
		if(fs.exists(path)) {
			throw new Exception(p + "文件已存在！");
		}
		
		if(fs.createNewFile(path)) {
			return;  // break;
		}
		
		throw new Exception("创建文件失败！");
	}

	@Override
	public void rename(String path1, String path2) throws Exception {
		// 	path1 path2
		Path p1 = new Path(path1);
		Path p2 = new Path(path2);
		if(!fs.exists(p1)) {
			throw new Exception("源文件不存在！");
		}
		if(fs.exists(p2)) {
			throw new Exception("修改的文件名已存在！");
		}
		
		if(fs.rename(p1, p2)) {
			return ;
		}
		
		throw new Exception("重命名错误！");
	}

	@Override
	public void appendFile(String path, String centext) throws Exception {
		Path p = new Path(path);
		FSDataOutputStream append = fs.append(p);
		append.write(centext.getBytes());	
		append.flush();
		append.close();
	}

	@Override
	public void writeFile(String path, String centext) throws Exception {
		Path p = new Path(path);
		FSDataOutputStream append = fs.create(p);
		append.write(centext.getBytes());	
		append.flush();
		append.close();
	}

}
