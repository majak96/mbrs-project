package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import freemarker.template.Template;
import model.FMEntity;
import model.FMModel;
import utils.ProjectInfo;

public class ControllerGenerator extends AbstractGenerator {

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
			Template temp = generatorInfo.getConfiguration().getTemplate("controller.ftl");

			String controllerPath = ProjectInfo.getInstance().getProjectPath() + "\\"
					+ ProjectInfo.getInstance().getProjectName() + "\\src\\main\\java\\";

			String[] packageStrings = projectInfo.getProjectPackage().split("\\.");
			for (String packageString : packageStrings) {
				controllerPath += "\\" + packageString;
			}
			controllerPath += "\\controllers";

			for (FMEntity entity : entities) {

				model.put("class_name", entity.getName());
				model.put("package", entity.getTypePackage());

				File file = new File(controllerPath + "\\" + entity.getName().substring(0, 1).toUpperCase()
						+ entity.getName().substring(1) + "Controller.java");
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
