package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import freemarker.template.Template;
import utils.ProjectInfo;

public class PomXmlGenerator extends AbstractGenerator {

	@Override
	public void init() {

		model.put("project_name", projectInfo.getProjectName());
		model.put("project_package", projectInfo.getProjectPackage());
	}

	@Override
	public void generate() {

		try {
			Template temp = generatorInfo.getConfiguration().getTemplate("pom_xml.ftl");

			String propertiesPath = ProjectInfo.getInstance().getProjectPath() + "//"
					+ ProjectInfo.getInstance().getProjectName();
			File file = new File(propertiesPath + "//pom.xml");
			file.createNewFile();

			Writer fileWriter = new FileWriter(file);

			temp.process(model, fileWriter);

			fileWriter.flush();
			fileWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
