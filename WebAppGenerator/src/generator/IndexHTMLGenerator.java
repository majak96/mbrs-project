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

public class IndexHTMLGenerator extends AbstractGenerator {

	@Override
	public void init() {
		FMModel parsedModel = FMModel.getInstance();
		List<FMEntity> entities = parsedModel.getEntities();

		List<String> entityLabels = new ArrayList<String>();
		for (FMEntity entity : entities) {
			entityLabels.add(entity.getLabel());
		}
		model.put("entities", entityLabels);
		model.put("application_name", projectInfo.getApplicationName());
	}

	@Override
	public void generate() {

		try {
			Template temp = generatorInfo.getConfiguration().getTemplate("html_index.ftl");

			File file = new File(ProjectInfo.getInstance().getFrontendPath() + File.separator + "src-gen" + File.separatorChar + "index.html");
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
