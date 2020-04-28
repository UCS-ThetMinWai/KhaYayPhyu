package com.kayayphyu.applilcation.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;


public class PropertiesLoader extends PropertyPlaceholderConfigurer {
	private Properties properties;
	private String originalText;
	private static Logger logger = Logger.getLogger(PropertiesLoader.class);

	public PropertiesLoader(String location) {
		super();
		setFileEncoding("UTF-8");
		setLocation(new ClassPathResource(location));
		ClassPathResource classPath = new ClassPathResource(location);
		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(classPath.getInputStream(), "UTF-8"));
			String temp;
			originalText = "";
			while ((temp = buffer.readLine()) != null) {
				originalText += temp + "\n";
			}
			this.properties = mergeProperties();
		} catch (IOException e) {
			logger.error("Can't read file", e);
		}
	}

	public void merge(PropertiesLoader properties) {
		if (properties == null)
			return;
		originalText = originalText + properties.originalText;
		this.properties.putAll(properties.getHashMap());

	}

	public String getJSONString() {
		JSONParser parser = new JSONParser();
		try {
			String text = "";
			for (String temp : getOriginalText().split("\n")) {
				text += temp.trim();
			}
			JSONObject json = (JSONObject) parser.parse(text);
			return json.toJSONString();
		} catch (ParseException e) {
			logger.error("Can't parse json", e);
			return "{}";
		}
	}

	public HashMap<String, String> getHashMap() {
		HashMap<String, String> map = new HashMap<>();
		for (Object key : properties.keySet()) {
			map.put((String) key, (String) properties.get(key));
		}
		return map;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public Properties getPerperties() {
		return properties;
	}

//	public String composeJson() {
//		Json json = new Json();
//		Set<?> keys = properties.keySet();
//		for (Object key : keys) {
//			json.put((String) key, (String) properties.getProperty((String) key));
//		}
//		return json.toJSONString();
//	}

	public String getOriginalText() {
		return this.originalText;
	}

	public void add(String key, String value) {
		originalText += "key =" + value;
		properties.setProperty(key, value);
	}
}
