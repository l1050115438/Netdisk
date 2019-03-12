package com.netdisk.pojo;

import java.util.Comparator;

/**
 * ʵ���� �ļ���Ϣ��װ��
 * @author yaohuicheng
 *
 */
public class FileInfo implements Comparator<FileInfo>{
	
	/**
	 * �ļ���Ŀ¼������
	 */
	private String name;
	
	/**
	 * ����·��
	 */
	private String absolutePath;
	
	/**
	 * ��С
	 */
	private long length;
	
	/**
	 * �Ƿ�ΪĿ¼ ,trueΪĿ¼
	 */
	private boolean isdir;
	
	/**
	 * ������
	 */
	private short block_replication;
	
	/**
	 * block��С
	 */
	private long blocksize;
	
	/**
	 * �޸�ʱ��
	 */
	private String modification_time;
	/**
	 * ����ʱ��
	 */
	private long access_time;
	
	/**
	 * ����
	 */
	private String owner;
	/**
	 * ����
	 */
	private String group;
	
	/**
	 * �Ƿ�Ϊ�޸�״̬
	 */
	private boolean rename = false;

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAbsolutePath() {
		return absolutePath;
	}


	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}


	public long getLength() {
		return length;
	}


	public void setLength(long length) {
		this.length = length;
	}


	public boolean isIsdir() {
		return isdir;
	}


	public void setIsdir(boolean isdir) {
		this.isdir = isdir;
	}


	public short getBlock_replication() {
		return block_replication;
	}


	public void setBlock_replication(short block_replication) {
		this.block_replication = block_replication;
	}


	public long getBlocksize() {
		return blocksize;
	}


	public void setBlocksize(long blocksize) {
		this.blocksize = blocksize;
	}


	public String getModification_time() {
		return modification_time;
	}


	public void setModification_time(String modification_time) {
		
		this.modification_time = modification_time;
	}


	public long getAccess_time() {
		return access_time;
	}


	public void setAccess_time(long access_time) {
		this.access_time = access_time;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getGroup() {
		return group;
	}


	public void setGroup(String group) {
		this.group = group;
	}
	public boolean isRename() {
		return rename;
	}
	public void setRename(boolean rename) {
		this.rename = rename;
	}
	

	@Override
	public String toString() {
		return "FileInfo [name=" + name + ", absolutePath=" + absolutePath + ", length=" + length + ", isdir=" + isdir
				+ ", block_replication=" + block_replication + ", blocksize=" + blocksize + ", modification_time="
				+ modification_time + ", access_time=" + access_time + ", owner=" + owner + ", group=" + group + "]";
	}

	/**
	 * �ļ��Ƚ�
	 */
	@Override
	public int compare(FileInfo o1, FileInfo o2) {
		// �ļ�����ǰ
		if(!o1.isIsdir() && o2.isIsdir()){
			return 1;
		}else if(o1.isIsdir() && !o2.isIsdir()){
			return -1;
		}
		
		if (o1.getName().compareToIgnoreCase(o2.getName())>0) {
            return 1;
        } else if (o1.getName().compareToIgnoreCase(o2.getName())==0) {
            return 0;
        } else {
            return -1;
        }
	}
	
}
