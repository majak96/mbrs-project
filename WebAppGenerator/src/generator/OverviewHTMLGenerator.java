package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import freemarker.template.Template;
import model.FMEntity;
import model.FMModel;
import utils.ProjectInfo;

public class OverviewHTMLGenerator  extends AbstractGenerator {

	private List<FMEntity> entities;

	@Override
	public void init() {
		FMModel parsedModel = FMModel.getInstance();
		entities = parsedModel.getEntities();
		model.put("groups", parsedModel.getGroups());
		model.put("application_name", projectInfo.getApplicationFrontendName());
	}

	@Override
	public void generate() {
		// generate a html form file for every entity
		for (FMEntity entity : entities) {

			model.put("entity", entity);

			try {
				Template temp = generatorInfo.getConfiguration().getTemplate("html_overview.ftl");

				String entityName = entity.getName().toLowerCase();
				File file = new File(ProjectInfo.getInstance().getFrontendPath() + File.separator + "src-gen" + File.separatorChar + entityName + ".html");
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
