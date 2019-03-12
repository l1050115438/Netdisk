package com.netdisk.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netdisk.pojo.FileInfo;
import com.netdisk.service.NetDiskService;
import com.netdisk.util.FileSystemFactory;

@Controller
public class NetDiskController {
	
	@Autowired
	private NetDiskService netDiskService;
	
	/**
	 * 文件列表展示控制类
	 * @param path
	 * @return
	 */
	@RequestMapping(value = "getFileList")
	@ResponseBody
	public Map<String,Object> getFileList(String path){
		if(path == null) {
			path = "/";
		}
				
		List<FileInfo> fileList = null;
		Map<String,Object> r = new HashMap<String,Object>();
		
		try {
			fileList = netDiskService.getFileList(path);
			r.put("code",200);
			r.put("fileList",fileList);
		} catch (Exception e) {
			r.put("code",500);
			r.put("msg","文件不存在!");
		} 
		
		return r;
	}
	/**
	 * 创建目录 ,新建文件夹 
	 * 参数同 getFileList 字符串, 内容为绝对路径 例如 "/etc/hosts"
	 * @param path
	 * @return 返回json对象 
	 * 	code 封装了状态码
	 * 	msg 为返回信息,可以在前台展示 
	 */
	@RequestMapping(value = "createDir",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> createDir(String path){
		Map<String,Object> r = new HashMap<String,Object>();

		try {
			netDiskService.createDir(path); 
			r.put("msg","创建成功！");
			r.put("code",200);
		} catch (Exception e) {
            r.put("code",500);
			r.put("msg",e.getMessage());
		}
		return r;
	}
	
	/**
	 * 重命名  POST请求
	 * 注意此处两个都需要绝对路径
	 * @param 
	 * 	src:资源路径
	 * 	dest 目标路径
	 * @return 返回json对象 
	 * 	code 封装了状态码
	 * 	msg 为返回信息,可以在前台展示 
	 */
	@RequestMapping(value = "renameFile",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> renameFile(String src, String dest){
		Map<String,Object> r = new HashMap<String,Object>();

		try {
			netDiskService.rename(src, dest);
			r.put("msg","重命名成功!");
			r.put("code",200);
		} catch (Exception e) {
            r.put("code",500);
			r.put("msg","重命名失败!");
		}
		return r;
	}
	/**
	 * 在HDFS创建文件
	 * @param path 目标路径
	 * @return 返回json对象 
	 * 	code 封装了状态码
	 * 	msg 为返回信息,可以在前台展示 
	 */
	@RequestMapping(value = "createNewFile",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> createNewFile(String path){
		Map<String,Object> r = new HashMap<String,Object>();

		try {
			netDiskService.createNewFile(path);
			r.put("msg","创建成功!");
			r.put("code",200);
		} catch (Exception e) {   
			r.put("code",500);
			r.put("msg",e.getMessage());
		}
		return r;
	}
	/**
     * 追加或重写内容  
     * @param filePath 目标路径 content内容 bool是否为重写: true 重写
     * @return 返回json对象 
     */
    @RequestMapping(value = "/appendToHDFSFile",method=RequestMethod.POST)
    @ResponseBody //String filePath, String content,boolean bool
    public Map<String,Object> appendToHDFSFile(@RequestParam Map<String,Object> params){
        String filePath = (String) params.get("filePath");
        String content = (String) params.get("content");
        String app =  (String) params.get("bool");
        Boolean bool = false;
        if(app.equals("追加")){
            bool = true;
        }else{//覆盖
            bool = false;
        }
        //System.out.println(filePath);
        FSDataOutputStream append =null;
        
		Map<String,Object> r = new HashMap<String,Object>();

        try {
            if(bool){//追加文件内容
            	netDiskService.appendFile(filePath, content);
            }else{
            	netDiskService.writeFile(filePath, content);
            }
            r.put("code",200);
        } catch (Exception e) {
            e.printStackTrace();
            r.put("code",500);
            r.put("msg","写入失败!");
        }
        return r;
    }
    
	/**
	 * 读文件内容  
	 * @param filePath 目标路径 
	 * @return 返回json对象 
	 * 	code 封装了状态码
	 * 	msg 为返回信息,可以在前台展示 
	 * content 返回内容
	 * @throws IOException 
	 */
	@RequestMapping(value = "/readFromHDFSFile",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> readFromHDFSFile(String filePath){
		String content = "";
		Map<String,Object> r = new HashMap<String,Object>();

		try {
			content = netDiskService.viewFile(filePath);
			r.put("content", content);
			r.put("code",200);
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code",500);
			r.put("msg","查看错误！");
		}
		return r;
	}
	/**
	 * 删除文件
	 * @param filePath 目标路径 
	 * @return 返回json对象 
	 */
	@RequestMapping(value = "delectFromHDFS",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delectFromHDFS(String path){
		Map<String,Object> r = new HashMap<String,Object>();

		try {
			netDiskService.del(path);
			r.put("code",200);
			r.put("msg","删除成功!");
		} catch (Exception e) {
			r.put("code",500);
			r.put("msg","删除失败!");
		} 
		
		return r;
	}
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 上传 文件
	 * @param src:资源路径,本地
	 * dest 目标路径
	 * @return 返回json对象 
	 * 	code 封装了状态码
	 * 	msg 为返回信息,可以在前台展示 
	 * @throws FileUploadException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "uploadFile",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadFileToHDFS(String path,HttpServletRequest request) throws FileUploadException, IOException{
		// hdfs系统目录
		if(path == null || path.equals("")) {
			path = "/";
		}
		String parameter = request.getParameter("path");
		// 1.创建DiskFileItemFactory
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2.创建
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 底层通过request获取数据，进行解析，将解析的数据封装到List<FileItem>
		List<FileItem> items = upload.parseRequest(request);
		// 3.遍历集合,   临时处理只能上传一个文件
		FileItem item = items.get(0);
		// item获取文件名 
		String name = item.getName();
		// 创建输入流，写入内存
		InputStream in = new BufferedInputStream(item.getInputStream());
		
		Map<String,Object> r = new HashMap<String,Object>();
		
		try { 
			// 创建操作HDFS的对象
			FileSystem fs = FileSystemFactory.getFileSystem();
			FSDataOutputStream out = fs.create(new Path(path + name));
			in = new BufferedInputStream(item.getInputStream());
			IOUtils.copyBytes(in, out, 4096, false);
			out.hsync();
			out.close();
			r.put("code",200);
			r.put("msg","上传成功!");
		} catch (IOException e) {
			r.put("code",500);
			r.put("msg","上传失败!");
		}
		return r;
	}
	/**
	 * 下载 
	 * @param src:资源路径,HDFS
	 * dest 目标路径,本地
	 * @return 返回json对象 
	 */
	@RequestMapping(value = "downloadFile")
	public  void downLoadFileFromHDFS(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> r = new HashMap<String,Object>();
		
		try {
			FileSystem fs  = FileSystemFactory.getFileSystem();

			String src = request.getParameter("src");
			
			if(src.endsWith("/")){
				 //目测是个目录
				 return;
			}
			String fileName = src.substring(src.lastIndexOf("/")+1);
	        
	        //设置响应头，控制浏览器下载该文件
	        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
	        //读取要下载的文件，保存到文件输入流
	        FSDataInputStream in = fs.open(new Path(src));
	        
	        //创建输出流
	        OutputStream out = response.getOutputStream();
	        //创建缓冲区
	        byte buffer[] = new byte[1024];
	        int len = 0;
	        //循环将输入流中的内容读取到缓冲区当中
	        while((len=in.read(buffer))>0){
	            //输出缓冲区的内容到浏览器，实现文件下载
	            out.write(buffer, 0, len);
	        }
	        //关闭文件输入流
	        in.close();
	        //关闭输出流
	        out.close();
	        //fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
