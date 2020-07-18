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

public class ModelGenerator extends AbstractGenerator {

	private List<FMEntity> entities;

	@Override
	public void init() {

		FMModel parsedModel = FMModel.getInstance();
		entities = parsedModel.getEntities();
	}

	@Override
	public void generate() {
		String modelPath = ProjectInfo.getInstance().getBaseHandwrittenFilesPath() + File.separator + "model";
		if (Files.notExists(Paths.get(modelPath))) {
			GeneratorUtils.createDirectory(modelPath);

			// generate a file for every entity
			for (FMEntity entity : entities) {

				model.put("entity", entity);

				try {
					Template temp = generatorInfo.getConfiguration().getTemplate("model.ftl");

					String entityName = entity.getName().substring(0, 1).toUpperCase() + entity.getName().substring(1);
					File file = new File(modelPath + File.separatorChar + entityName + ".java");
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

}
