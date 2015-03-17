package org.wkjsj.hz.base.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetTestedCode {

	/*
	 * 获取测试代码
	 * @param 一个测试目录地址
	 * */ 
	/*public void getTestedData(String path) {
		String pathD, pathDF;
		List<String> list = new ArrayList<String>();
		List<String> listFile = new ArrayList<String>();
		list = this.getDirList(path);
		for (int i = 0; i < list.size(); i++) {
			
			pathD = path + "/" + list.get(i);
			// System.out.println(pathDF);
			listFile = this.getFileList(pathD);
			
			for (int j = 0; j < listFile.size(); j++) {
				pathDF = pathD + "/" + listFile.get(j);
				// System.out.println(pathDF);
			//	System.out.println(this.getText(pathDF));
			//	System.out.println("************" + j);
			}
		}

	}*/

	// 获取大文件夹下的各个小的路径
	public List<String> getDirList(String path) {
		File file = new File(path);
		List<String> result = new ArrayList<String>();
		if (!file.isDirectory()) {
			// System.out.println(file.getName()+"一个文件夹，请检查路径是否有误！！");
			// 到时候写入日志;
		} else {
			File[] directoryList = file.listFiles();
			for (int i = 0; i < directoryList.length; i++) {
				if (directoryList[i].isDirectory()) {
					result.add(directoryList[i].getName());
				//	 System.out.println((String) directoryList[i].getPath());
				}
			}
		}
		return result;
	}

	// 获取Text文件路径
	public List<String> getFileList(String path) {
		File file = new File(path);
		List<String> result = new ArrayList<String>();
		// System.out.println(file.getName()+"fileName");
		// 判断传入对象是否为一个文件夹对象
		if (!file.isDirectory()) {
			// System.out.println(file.getName()+"一个文件夹，请检查路径是否有误！！");
			result.add(file.getAbsolutePath());
		} else {
			File[] directoryList = file.listFiles(new FileFilter() {
				public boolean accept(File file) {
					if (file.isFile() && file.getName().indexOf("txt") > -1) {
						return true;
					} else {
						return false;
					}
				}
			});
			for (int i = 0; i < directoryList.length; i++) {
				result.add(directoryList[i].getName());
			}
		}

		return result;
	}

	// 获取text内容
	public String getText(String path) {
		Scanner in = null;
		String text = "";
		try {
			in = new Scanner(new FileInputStream(path));
		} catch (Exception e) {
			in = new Scanner(System.in);
		}
		while (in.hasNextLine()) {
			text = text + in.nextLine() + "\n";

		}
		return text;

	}

}
