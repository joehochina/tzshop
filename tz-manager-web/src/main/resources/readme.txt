图片服务器 客户端  在MAVAEN中央仓库 没有依赖包，需 手动添加到 本地 仓库 
此时需将fastdfs_client maven项目 导入
执行 maven install 将jar包安装 在 本地 仓库 
然后将依赖配置在要是用的项目pom文件中 ，此处添加到 tz-mananger-web 的 pom
		<dependency>
		  <groupId>fastdfs_client</groupId>
		  <artifactId>fastdfs_client</artifactId>
		  <version>1.25</version>
		</dependency> 
		
创建图片服务器配置文件 config/fastDfsClient.conf 
     tracker_server=192.168.25.133:22122 
     
将图片上传服务器访问路径设置到properties文件
    config/resouce.properties
