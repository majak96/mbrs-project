package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import freemarker.template.Template;
import utils.ProjectInfo;

public class ApplicationYmlGenerator extends AbstractGenerator {

	@Override
	public void init() {

		model.put("project_name", projectInfo.getProjectName());
		model.put("database_url", projectInfo.getDatabaseUrl());
		model.put("database_username", projectInfo.getDatabaseUsername());
		model.put("database_password", projectInfo.getDatabasePassword());
	}

	@Override
	public void generate() {

		try {
			Template temp = generatorInfo.getConfiguration().getTemplate("application_yml.ftl");

			String propertiesPath = ProjectInfo.getInstance().getProjectPath() + "//"
					+ ProjectInfo.getInstance().getProjectName() + "//src//main//resources";
			File file = new File(propertiesPath + "//application.yml");
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
