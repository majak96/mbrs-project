package application;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import utils.Handler;

public class Application {

	public static void main(String[] args) {

		System.out.println("Starting app");

		try {
			File inputFile = new File("resources/example.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			Handler handler = new Handler();
			saxParser.parse(inputFile, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
