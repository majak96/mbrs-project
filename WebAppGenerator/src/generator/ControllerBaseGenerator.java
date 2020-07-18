package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import freemarker.template.Template;
import model.FMEntity;
import model.FMModel;
import utils.ProjectInfo;

public class ControllerBaseGenerator extends AbstractGenerator {

	@Override
	public void init() {
		model.put("project_name", projectInfo.getProjectName());
		model.put("project_package", projectInfo.getProjectPackage());
	}

	@Override
	public void generate() {
		FMModel modelMap = FMModel.getInstance();
		List<FMEntity> entities = modelMap.getEntities();

		try {
			Template temp = generatorInfo.getConfiguration().getTemplate("controller_base.ftl");

			String controllerPath = ProjectInfo.getInstance().getBaseGeneratedFilesPath() + File.separatorChar
					+ "controllers";

			for (FMEntity entity : entities) {
				model.put("class_name", entity.getName());
				model.put("package", entity.getTypePackage());

				String entityName = entity.getName().substring(0, 1).toUpperCase() + entity.getName().substring(1);
				File file = new File(controllerPath + File.separatorChar + entityName + "BaseController.java");
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
