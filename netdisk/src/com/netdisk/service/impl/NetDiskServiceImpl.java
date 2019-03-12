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
 * ����ҵ��ʵ����
 * @author yaohuicheng
 * @version v1.0
 * 
 */
@Service
public class NetDiskServiceImpl implements NetDiskService {
	
	// �����Ĺ����� FileSystemFactory
	private FileSystem fs = FileSystemFactory.getFileSystem();
	
	@Override
	public List<FileInfo> getFileList(String path) throws Exception {
		
		FileStatus[] listStatus = fs.listStatus(new Path(path));
		
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>(); // ������ظ������������Ͽ�ܣ�
		
		for (FileStatus fileStatus : listStatus) {
			
			FileInfo tmpFileInfo = new FileInfo();
			
			// ��tmpFileInfoչ��  ����fileStatus��ֵ��䵽tmpFIleinfo�С�
			String tmp = fileStatus.getPath().toString().substring(fileStatus.getPath().toString().indexOf("//") + 2);
			String absolutePath = tmp.substring(tmp.indexOf("/"));
			tmpFileInfo.setAbsolutePath(fileStatus.isDirectory() ? absolutePath : absolutePath); 
			//   /usr/test/    /user/hello.txt
			
			tmpFileInfo.setAccess_time(fileStatus.getAccessTime());//
			tmpFileInfo.setBlock_replication(fileStatus.getReplication());// ��������
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
		// �ļ�����
		fileInfoList.sort(new FileInfo());
		
		return fileInfoList;
	}

	@Override
	public void createDir(String p) throws Exception {
		Path path = new Path(p);
		// �ж��ļ����Ƿ���ڣ�������ڣ���ʾ�Ѿ����ڡ�
		if(fs.exists(path)) {
			throw new Exception(p + "�Ѿ����ڣ�");
		}
		
		if(fs.mkdirs(path)) {
			return;  // break;
		}
		
		throw new Exception("����ʧ�ܣ�");
		
	}

	@Override
	public String viewFile(String p) throws Exception {
		
		Path path = new Path(p);
		
		// hdfs�е��ļ� ͨ��IOд�뵽�ڴ��С�  ���ڴ��оͿ���ֱ����ʾ��
		FSDataInputStream open = fs.open(path);
		
		// ��ȡ�Ļ����� 1024
		byte[] b = new byte[1024];
		
		// ��һ�ζ�
		int flag = open.read(b);
		
		StringBuffer sbuf = new StringBuffer();
		
		while(flag != -1) {
			// ��һ��д 
			sbuf.append(new String(b,"utf-8"));
			
			// ϴһ�ζ�
			flag = open.read(b);
		}
		
		// inputStream�������ر�
		open.close();
		
		return sbuf.toString();
	}

	@Override
	public void del(String p) throws Exception {
		Path path = new Path(p);
		// �ж��ļ����Ƿ���ڣ�������ڣ���ʾ�Ѿ����ڡ�
		if(!fs.exists(path)) {
			throw new Exception(p + "�����ڣ�");
		}
		
		if(fs.delete(path,true)) {
			return;  // break;
		}
		
		throw new Exception("ɾ��ʧ�ܣ�");
	}

	@Override
	public void createNewFile(String p) throws Exception {
		Path path = new Path(p);
		// �ж��ļ����Ƿ���ڣ�������ڣ���ʾ�Ѿ����ڡ�
		if(fs.exists(path)) {
			throw new Exception(p + "�ļ��Ѵ��ڣ�");
		}
		
		if(fs.createNewFile(path)) {
			return;  // break;
		}
		
		throw new Exception("�����ļ�ʧ�ܣ�");
	}

	@Override
	public void rename(String path1, String path2) throws Exception {
		// 	path1 path2
		Path p1 = new Path(path1);
		Path p2 = new Path(path2);
		if(!fs.exists(p1)) {
			throw new Exception("Դ�ļ������ڣ�");
		}
		if(fs.exists(p2)) {
			throw new Exception("�޸ĵ��ļ����Ѵ��ڣ�");
		}
		
		if(fs.rename(p1, p2)) {
			return ;
		}
		
		throw new Exception("����������");
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
