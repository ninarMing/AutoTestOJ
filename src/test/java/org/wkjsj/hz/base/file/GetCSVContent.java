package org.wkjsj.hz.base.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetCSVContent {
	/*
	 * public static void main(String args[]){
	 * getLinkName("src/main/resources/fixedData/LinkData.csv", 8); }
	 */

	/*---读取文件---------*/
	public static String[] getLinkName(String path, int num) {

		File linkData = new File(path);
		String[] str = new String[num];
		try {

			// UTF-8还是乱码，应该用GBK,
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					linkData), "gbk");
			BufferedReader br = new BufferedReader(isr);
			String line = "";

			int i = 0;
			while ((line = br.readLine()) != null) {
				str[i] = line;
			//	 System.out.println(str[i]+i);
				i++;
			}
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;
	}

}
