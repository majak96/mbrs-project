package application;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;
import generator.GeneratorRegistry;
import utils.Handler;
import utils.ProjectInfo;

public class Application {

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
			SwingUtilities.updateComponentTreeUI(new JFrame());
			UIManager.put("Synthetica.tabbedPane.keepOpacity", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ProjectInfo.getInstance().getMainFrame().setVisible(true);

	}
	
	public static void generate() {
		ProjectInfo projectInfo = ProjectInfo.getInstance();
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
			File inputFile = projectInfo.getResourceFile();
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
