package com.tengzhuo.freemark;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkTest {

	@Test
	public void testFreeMark() throws Exception{
		//先创建一个模板对象
		//在创建configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		
		//设置模板文件保存路径
		configuration.setDirectoryForTemplateLoading(new File("D:/m2/workspace/tz-item-web/src/main/webapp/WEB-INF/ftl"));
		//设置模板文件字符集编码
		configuration.setDefaultEncoding("utf-8");
		//通过configuration创建模板对象
		Template template = configuration.getTemplate("hello.ftl");
		//创建一个数据集，可以是pojo,也可以使map
		Map map = new HashMap<>();
		map.put("hello", "this is the first freemark template");
		//创建一个Student pojo对象
		Student student = new Student();
		//创建一个writer 对象，指定输出文件路径及文件名
		Writer writer = new FileWriter(new File("D:/m2/workspace/tz-item-web/src/main/webapp/WEB-INF/ftl/hello.txt"));
		template.process(map, writer);
		//关闭流
		writer.close();
	}
}
