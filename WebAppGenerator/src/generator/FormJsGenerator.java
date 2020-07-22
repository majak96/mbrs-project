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

public class FormJsGenerator extends AbstractGenerator {

	@Override
	public void init() {
		model.put("port", projectInfo.getApplicationPort());
	}

	@Override
	public void generate() {
		String overviewPath = ProjectInfo.getInstance().getFrontendPath() + File.separator + "src-gen" + File.separator + "js";
		GeneratorUtils.createDirectory(overviewPath);
		FMModel modelMap = FMModel.getInstance();
		List<FMEntity> entities = modelMap.getEntities();

		try {
			Template temp = generatorInfo.getConfiguration().getTemplate("form_js.ftl");

			for (FMEntity entity : entities) {

				model.put("class_name", entity.getName());
				model.put("entity", entity);

				String entityName = entity.getName().substring(0, 1).toLowerCase() + entity.getName().substring(1);
				File baseFile = new File(overviewPath + File.separatorChar + entityName + "-form-base.js");
				baseFile.createNewFile();
				
				Writer fileWriter = new FileWriter(baseFile);

				temp.process(model, fileWriter);

				fileWriter.flush();
				fileWriter.close();
				
				if(Files.notExists(Paths.get(projectInfo.getFrontendPath() + File.separator + "src" + File.separator + "js" + File.separator + entityName + "-form.js"))){
					File file = new File(projectInfo.getFrontendPath() + File.separator + "src" + File.separator + "js" + File.separator + entityName + "-form.js");
					file.createNewFile();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
