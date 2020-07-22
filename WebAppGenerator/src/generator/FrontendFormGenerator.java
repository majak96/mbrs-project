package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import freemarker.template.Template;
import model.FMEntity;
import model.FMModel;
import utils.ProjectInfo;

public class FrontendFormGenerator extends AbstractGenerator {

	private List<FMEntity> entities;
	private List<String> entityLabels;

	@Override
	public void init() {
		FMModel parsedModel = FMModel.getInstance();
		entities = parsedModel.getEntities();

		entityLabels = new ArrayList<String>();
		for (FMEntity entity : entities) {
			entityLabels.add(entity.getLabel());
		}
		model.put("entities", entityLabels);
	}

	@Override
	public void generate() {
		// generate a html form file for every entity
		for (FMEntity entity : entities) {

			model.put("entity", entity);

			try {
				Template temp = generatorInfo.getConfiguration().getTemplate("html_form.ftl");

				String entityName = entity.getName().toLowerCase();
				File file = new File(ProjectInfo.getInstance().getFrontendPath() + File.separator + "src-gen" + File.separatorChar + entityName + "Form.html");
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
