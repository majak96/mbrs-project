package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.FMEntity;
import model.FMModel;
import model.FMPersistentProperty;
import utils.ProjectInfo;

public class RepositoryBaseGenerator extends AbstractGenerator {

	@Override
	public void init() {
		model.put("project_name", projectInfo.getProjectName());
		model.put("project_package", projectInfo.getProjectPackage());
	}

	@Override
	public void generate() {
		Template template = null;

		try {
			template = generatorInfo.getConfiguration().getTemplate("repository_base.ftl");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String path = ProjectInfo.getInstance().getBaseGeneratedFilesPath() + File.separatorChar + "repositories";

		for (FMEntity entity : FMModel.getInstance().getEntities()) {

			String entityName = entity.getName().substring(0, 1).toUpperCase() + entity.getName().substring(1);
			model.put("class_name", entityName);
			model.put("package", entity.getTypePackage());

			for (FMPersistentProperty property : entity.getPersistentProperties()) {
				if (property.getId()) {
					model.put("id_class", property.getType().getName());
					break;
				}
			}

			File file = new File(path + File.separatorChar + entityName + "BaseRepository.java");

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
