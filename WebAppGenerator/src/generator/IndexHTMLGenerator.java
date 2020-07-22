package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import freemarker.template.Template;
import model.FMModel;
import utils.ProjectInfo;

public class IndexHTMLGenerator extends AbstractGenerator {

	@Override
	public void init() {
		FMModel parsedModel = FMModel.getInstance();
		model.put("groups", parsedModel.getGroups());
		model.put("application_name", projectInfo.getApplicationFrontendName());
	}

	@Override
	public void generate() {

		try {
			Template temp = generatorInfo.getConfiguration().getTemplate("html_index.ftl");

			File file = new File(ProjectInfo.getInstance().getFrontendPath() + File.separatorChar + "index.html");
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
