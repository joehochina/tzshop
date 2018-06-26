package com.tengzhuo.fast;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.fasterxml.jackson.core.json.UTF8StreamJsonParser;
import com.tengzhuo.utils.FastDFSClient;

public class FastDFSTest {
	
	@Test
	public void testUpload()throws Exception {
		//要想使用图片服务器，首先要创建一个配置文件。文件名任意
		//内容就是tracker服务器地址,使用全局对象加载文件
		ClientGlobal.init("D:/m2/workspace/tz-manager-web/src/main/resources/config/fastDfsClient.conf");
		
		//创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		
		//通过TrackerClient获得一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个StorageServer的引用 ，可以是Null
		StorageServer storageServer=null;		
		//创建 一个StorageClient,参数需要TrackerServer和StrorageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//使用StorageClient上传文件
		String[] upload_file = storageClient.upload_file("C:/Users/Public/Pictures/Sample Pictures/Penguins.jpg", 
				"jpg", null);
		for (int i = 0; i < upload_file.length; i++) {
			String string = upload_file[i];
			System.out.println(string);
			
		}
	}

	@Test
	public void testFastDFSClient()throws Exception{
		String conf="D:/m2/workspace/tz-manager-web/src/main/resources/config/fastDfsClient.conf";
		FastDFSClient fastDFSClient = new FastDFSClient(conf);
		String uploadFile = fastDFSClient.uploadFile("C:/Users/Public/Pictures/Sample Pictures/Chrysanthemum.jpg");
		System.out.println(uploadFile);
	}
}
