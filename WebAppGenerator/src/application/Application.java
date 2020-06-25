package application;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import generator.GeneratorRegistry;
import utils.Handler;
import utils.ProjectInfo;

public class Application {

	public static void main(String[] args) {

		ProjectInfo projectInfo = ProjectInfo.getInstance();
		projectInfo.setProjectName("example-project");
		projectInfo.setProjectPackage("com.example");
		projectInfo.setProjectPath("C:\\Users\\Marijana\\Desktop\\MBRS gen");
		projectInfo.setDatabaseUrl("jdbc:postgresql://localhost:5432/example-database");
		projectInfo.setDatabaseUsername("postgres");
		projectInfo.setDatabasePassword("root");

		String[] nameStrings = projectInfo.getProjectName().replace("-", " ").replace("_", " ").split(" ");

		StringBuilder pascalCaseBuilder = new StringBuilder();
		for (String word : nameStrings) {
			if (!word.isEmpty()) {
				pascalCaseBuilder.append(Character.toUpperCase(word.charAt(0)));
				pascalCaseBuilder.append(word.substring(1).toLowerCase());
			}
		}

		projectInfo.setApplicationName(pascalCaseBuilder.toString() + "Application");
		
		try {
			File inputFile = new File("resources/example.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			Handler handler = new Handler();
			saxParser.parse(inputFile, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GeneratorRegistry generatorRegistry = new GeneratorRegistry();
		generatorRegistry.registerGenerators();
		generatorRegistry.generate();

	}

}
