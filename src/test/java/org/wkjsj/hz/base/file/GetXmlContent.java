package org.wkjsj.hz.base.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class GetXmlContent {
	// 3
	public String parserIptXml(String path, int i) {
		InputStream is;
		String text = "";
		try {
			is = new FileInputStream(path);
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(is, "utf-8"));
				SAXBuilder builder = new SAXBuilder();
				Document doc;
				try {
					doc = builder.build(br);
					Element root = doc.getRootElement();// 获得根节点
					List<Element> itemList = root.getChildren("item");

					if (itemList.size() > 0) {
						Element itemElement = (Element) itemList.get(0);

						switch (i) {
						case 0:
							text = itemElement.getChildText("title");
							break;
						case 1:
							text = itemElement.getChildText("time_limit");
							break;
						case 2:
							text = itemElement.getChildText("memory_limit");
							break;
						default:
							;

						}
					}

				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return text;
	}

	// 4
	public String parserCkXml(String path, int i) {
		InputStream is;
		String text = "";
		try {
			is = new FileInputStream(path);

			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(is, "utf-8"));
				SAXBuilder builder = new SAXBuilder();
				Document doc;
				try {
					doc = builder.build(br);
					Element root = doc.getRootElement();// 获得根节点
					List<Element> itemList = root.getChildren("item");

					if (itemList.size() > 0) {
						Element itemElement = (Element) itemList.get(0);

						switch (i) {
						case 0:
							text = itemElement.getChildText("description");
							break;
						case 1:
							text = itemElement.getChildText("input");
							break;
						case 2:
							text = itemElement.getChildText("output");
							break;
						case 3:
							text = itemElement.getChildText("hint");
							break;
						default:
							;

						}
					}

				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return text;
	}

	// 5
	public String parserAreaXml(String path, int i) {
		InputStream is;
		String text = "";
		try {
			is = new FileInputStream(path);

			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(is, "utf-8"));
				SAXBuilder builder = new SAXBuilder();
				Document doc;
				try {
					doc = builder.build(br);
					Element root = doc.getRootElement();// 获得根节点
					List<Element> itemList = root.getChildren("item");

					if (itemList.size() > 0) {
						Element itemElement = (Element) itemList.get(0);

						switch (i) {
						case 0:
							text = itemElement.getChildText("sample_input");
							break;
						case 1:
							text = itemElement.getChildText("sample_output");
							break;
						// 如果有多个同标签的数据将只会读取第一个标签的数据，这是个bug优先级放在后面
						case 2:
							text = itemElement.getChildText("test_input");
							break;
						case 3:
							text = itemElement.getChildText("test_output");
							break;
						case 4:
							text = itemElement.getChildText("source");
							break;
						default:
							;
						}
					}

				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return text;
	}
}