package ua.com.company.component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonToObject {
	
	String jsonString = "";
	byte [] buffer = new byte[1024];
	
	public String getJsonasString() {
		File file = new File("D:\\aaImgs\\weatherJson.txt");
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String result = "";
			
			while((result = reader.readLine())!= null) {
				jsonString += result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		System.out.println(jsonString);
		
		
		
		return jsonString;
	}

}
