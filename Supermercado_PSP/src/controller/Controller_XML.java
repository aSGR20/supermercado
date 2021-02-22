package controller;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Controller_XML {
	public Controller_XML() { }
	
	public String getCorreo() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		String email = "";
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("App.config"));
			NodeList listEmail = document.getElementsByTagName("AppConfig");
			for(int i = 0; i < listEmail.getLength(); i++) {
				Node node = listEmail.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					NodeList nodeList = element.getChildNodes();
					for(int o = 0; o < nodeList.getLength(); o++) {
						Node nodeChild = nodeList.item(o);
						if(nodeChild.getNodeType() == Node.ELEMENT_NODE) {
							email = nodeChild.getTextContent();
						}
					}
				}
			}
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return email;
	}
}
