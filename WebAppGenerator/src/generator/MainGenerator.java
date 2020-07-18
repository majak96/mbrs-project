package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

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
		String mainPath = ProjectInfo.getInstance().getBaseHandwrittenFilesPath() + File.separatorChar
				+ projectInfo.getApplicationName() + ".java";
		
		if (Files.notExists(Paths.get(mainPath))) {
			try {
				Template temp = generatorInfo.getConfiguration().getTemplate("main.ftl");

				File file = new File(mainPath);
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
}
