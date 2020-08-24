package com.yc.tomcat.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseXML {
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	public ParseXML() {
		SAXReader read = new SAXReader();
		Document doc = null;
		try {
			doc = read.read(this.getClass().getClassLoader().getResourceAsStream("web.xml"));
			List<Element> list = doc.selectNodes("//mime-mapping");//两斜杠表示xml中的任何地方
			
			for(Element el : list) {
				map.put(el.selectSingleNode("extension").getText().trim(), el.selectSingleNode("mime-type").getText().trim());
			}
		
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	
	public static String getCotentType(String key) {
		return map.getOrDefault(key, "text/html;charset=utf-8");
	}
}
