package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import freemarker.template.Template;
import model.FMEntity;
import model.FMModel;
import utils.GeneratorUtils;
import utils.ProjectInfo;

public class ControllerGenerator extends AbstractGenerator {

	@Override
	public void init() {
		model.put("project_package", projectInfo.getProjectPackage());
	}

	@Override
	public void generate() {
		String controllerPath = ProjectInfo.getInstance().getBaseHandwrittenFilesPath() + File.separator
				+ "controllers";
		if (Files.notExists(Paths.get(controllerPath))) {
			GeneratorUtils.createDirectory(controllerPath);
			FMModel modelMap = FMModel.getInstance();
			List<FMEntity> entities = modelMap.getEntities();

			try {
				Template temp = generatorInfo.getConfiguration().getTemplate("controller.ftl");

				for (FMEntity entity : entities) {

					model.put("class_name", entity.getName());

					String entityName = entity.getName().substring(0, 1).toUpperCase() + entity.getName().substring(1);
					File file = new File(controllerPath + File.separatorChar + entityName + "Controller.java");
					file.createNewFile();

					Writer fileWriter = new FileWriter(file);

					temp.process(model, fileWriter);

					fileWriter.flush();
					fileWriter.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
