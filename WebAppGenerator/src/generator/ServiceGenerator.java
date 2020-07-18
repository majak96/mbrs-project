package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.FMEntity;
import model.FMModel;
import utils.GeneratorUtils;
import utils.ProjectInfo;

public class ServiceGenerator extends AbstractGenerator {

	@Override
	public void init() {
		this.model.put("project_package", projectInfo.getProjectPackage());
	}

	@Override
	public void generate() {
		String servicePath = ProjectInfo.getInstance().getBaseHandwrittenFilesPath() + File.separator + "services";
		if (Files.notExists(Paths.get(servicePath))) {
			GeneratorUtils.createDirectory(servicePath);

			Template template = null;

			try {
				template = generatorInfo.getConfiguration().getTemplate("service.ftl");
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

			for (FMEntity entity : FMModel.getInstance().getEntities()) {

				String entityName = entity.getName().substring(0, 1).toUpperCase() + entity.getName().substring(1);
				this.model.put("class_name", entityName);

				File file = new File(servicePath + File.separatorChar + entityName + "Service.java");
				try {
					file.createNewFile();

					Writer fileWriter = new FileWriter(file);

					template.process(model, fileWriter);

					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TemplateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
