package com.openrules.tools;

import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class XmlComputerReader extends XmlReader {
	
	public XmlComputerReader(String fileName) {
		super(fileName);
	}

	public Object mapXmlToJava(Node node) {
		CloudComputer computer = new CloudComputer();
		Element element = (Element) node;
		computer.setId(getTagValue("id", element));
		computer.setCpuPower(getIntValue("cpuPower", element));
		computer.setMemory(getIntValue("memory", element));
		computer.setNetworkBandwidth(getIntValue(
				"networkBandwidth", element));
		computer.setCost(getIntValue("cost", element));
		
		return computer;
	}
}
