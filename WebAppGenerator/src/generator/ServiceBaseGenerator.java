/**
 * 
 */
package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.FMEntity;
import model.FMModel;
import model.FMPersistentProperty;
import utils.ProjectInfo;

/**
 * @author Vesna Milic
 *
 */
public class ServiceBaseGenerator extends AbstractGenerator {

	@Override
	public void init() {
		this.model.put("project_name", projectInfo.getProjectName());
		this.model.put("project_package", projectInfo.getProjectPackage());
	}

	@Override
	public void generate() {
		Template template = null;

		try {
			template = generatorInfo.getConfiguration().getTemplate("service_base.ftl");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String path = ProjectInfo.getInstance().getBaseGeneratedFilesPath() + File.separatorChar + "services";

		for (FMEntity entity : FMModel.getInstance().getEntities()) {

			String entityName = entity.getName().substring(0, 1).toUpperCase() + entity.getName().substring(1);
			List<String> properties = (entity.getPersistentProperties()).stream().map(p -> p.getName())
					.collect(Collectors.toList());
			List<String> linkedPr = (entity.getLinkedProperties()).stream().map(p -> p.getName())
					.collect(Collectors.toList());
			properties.addAll(linkedPr);
			this.model.put("class_name", entityName);
			this.model.put("package", entity.getTypePackage());
			this.model.put("properties", properties);
			this.model.put("linkedProperties", entity.getLinkedProperties());

			for (FMPersistentProperty property : entity.getPersistentProperties()) {
				if (property.getId()) {
					model.put("id_class", property.getType().getName());
					break;
				}
			}

			File file = new File(path + File.separatorChar + entityName + "BaseService.java");
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
