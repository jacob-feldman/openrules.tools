package com.openrules.tools;

public class XmlReaderTest {
	
	public static void main(String argv[]) {
		
		XmlComputerReader reader = new XmlComputerReader("./test/resources/cb-0004comp-0012proc.xml");
		Object[] computers = reader.readArray("CloudComputer");
		System.out.println("Read " + computers.length + " processes");
		for (int i = 0; i < computers.length; i++) {
			CloudComputer computer = (CloudComputer) computers[i];
			System.out.println(computer);
		}
		
		XmlProcessReader reader2 = new XmlProcessReader("./test/resources/cb-0004comp-0012proc.xml");
		Object[] processes = reader2.readArray("CloudProcess");
		System.out.println("Read " + processes.length + " processes");
		for (int i = 0; i < processes.length; i++) {
			CloudProcess process = (CloudProcess) processes[i];
			System.out.println(process);
		}
	}

}
