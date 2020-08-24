package com.yc.tomcat.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseUrlPattern {
	private String basePath = TomcatConstants.BASE_PATH;
	private  static Map<String,String> parameter = new HashMap<String,String>();

	public ParseUrlPattern() {
		parse();
	}

	private void parse() {
		File[] files = new File(basePath).listFiles();
		if(files == null || files.length <= 0 ) {//没有项目
			return;
		}
		String projectName;
		File childFile;
		for(File f : files) {
			projectName = f.getName();
			
			if(!f.isDirectory()) {//如果不是文件夹就进行下一个
				continue;
			}
			childFile = new File(f,"web.xml");
			
			if(!childFile.exists()) {//不存在web。xml
				continue;
			}
			
			parseXml(projectName,childFile);
		}
	}

	private void parseXml(String projectName, File childFile) {
		SAXReader read = new SAXReader();
		Document doc = null;
		
		try {
			doc = read.read(childFile);
			List<Element> list = doc.selectNodes("//servlet");
			
			for(Element el : list) {
				parameter.put("/" + projectName +el.selectSingleNode("url-pattern").getText().trim(), el.selectSingleNode("servlet-class").getText().trim());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}

	public static String getClass(String key) {
		return parameter.getOrDefault(key, null);
	}
	public static Map<String,String> getPattern(){
		return parameter;
	}


}
