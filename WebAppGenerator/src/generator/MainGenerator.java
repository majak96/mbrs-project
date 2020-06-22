package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import freemarker.template.Template;
import utils.ProjectInfo;

public class MainGenerator extends AbstractGenerator {

	@Override
	public void init() {

		String projectPackage = projectInfo.getProjectPackage();
		String projectName = projectInfo.getApplicationName();

		model.put("package", projectPackage);
		model.put("project_name", projectName);
	}

	@Override
	public void generate() {

		try {
			Template temp = generatorInfo.getConfiguration().getTemplate("main.ftl");

			File file = new File(ProjectInfo.getInstance().getBasePath() + "//" + projectInfo.getApplicationName() + ".java");
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
