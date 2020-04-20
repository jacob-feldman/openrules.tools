package com.openrules.tools;

import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class XmlProcessReader extends XmlReader {
	
	public XmlProcessReader(String fileName) {
		super(fileName);
	}

	public Object mapXmlToJava(Node node) {
		CloudProcess process = new CloudProcess();
		Element element = (Element) node;
		process.setId(getTagValue("id", element));
		process.setRequiredCpuPower(getIntValue("requiredCpuPower", element));
		process.setRequiredMemory(getIntValue("requiredMemory", element));
		process.setRequiredNetworkBandwidth(getIntValue(
				"requiredNetworkBandwidth", element));
		return process;
	}

}
