package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import freemarker.template.Template;
import model.FMEntity;
import model.FMModel;
import utils.GeneratorUtils;
import utils.ProjectInfo;

public class FormJsGenerator extends AbstractGenerator {

	@Override
	public void init() {
		model.put("port", projectInfo.getApplicationPort());
	}

	@Override
	public void generate() {
		String overviewPath = ProjectInfo.getInstance().getFrontendPath() + File.separator + "js";
		GeneratorUtils.createDirectory(overviewPath);
		FMModel modelMap = FMModel.getInstance();
		List<FMEntity> entities = modelMap.getEntities();

		try {
			Template temp = generatorInfo.getConfiguration().getTemplate("form_js.ftl");

			for (FMEntity entity : entities) {

				model.put("class_name", entity.getName());
				model.put("entity", entity);

				String entityName = entity.getName().substring(0, 1).toLowerCase() + entity.getName().substring(1);
				File file = new File(overviewPath + File.separatorChar + entityName + "-form.js");
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
