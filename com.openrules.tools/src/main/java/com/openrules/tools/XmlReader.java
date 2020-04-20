package com.openrules.tools;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

abstract public class XmlReader {
	String fileName;
	File xmlFile;
	Document doc;
	
	public XmlReader(String fileName) {
		this.fileName = fileName;
		try {
			xmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}


	public Object[] readArray(String arrayName) {
		System.out.println("Read objects of the type " + arrayName + " from <" + fileName + "> (root "
				+ doc.getDocumentElement().getNodeName() + ")");
		NodeList nodeList = doc.getElementsByTagName(arrayName);
		System.out.println("-----------------------");
		Object[] objects = new Object[nodeList.getLength()];
		for (int i = 0; i < objects.length; i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				objects[i] = mapXmlToJava(node);
//				System.out.println(objects[i]);
			}
		}
		return objects;
	}
	
	abstract public Object mapXmlToJava(Node node);
	
	protected String getTagValue(String tag, Element element) {
		NodeList nlList = element.getElementsByTagName(tag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	protected int getIntValue(String tag, Element element) {

		return Integer.parseInt(getTagValue(tag, element));
	}
	
	protected double getDoubleValue(String tag, Element element) {

		return Double.parseDouble(getTagValue(tag, element));
	}
}
