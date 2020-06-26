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
import model.FMProperty;
import utils.ProjectInfo;

/**
 * @author Vesna Milic
 *
 */
public class ServiceGenerator extends AbstractGenerator {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		model.put("project_name", projectInfo.getProjectName());
		model.put("project_package", projectInfo.getProjectPackage());
	}

	@Override
	public void generate() {
		// TODO Auto-generated method stub

		Template template = null;

		try {
			template = generatorInfo.getConfiguration().getTemplate("service.ftl");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String path = ProjectInfo.getInstance().getProjectPath() + File.separatorChar
				+ ProjectInfo.getInstance().getProjectName() + "\\src\\main\\java\\";

		String[] packageStrings = projectInfo.getProjectPackage().split("\\.");

		for (String packageString : packageStrings) {
			path += File.separatorChar + packageString;
		}
		path += File.separatorChar + "services";

		for (FMEntity entity : FMModel.getInstance().getEntities()) {

			String entityName = entity.getName().substring(0, 1).toUpperCase() + entity.getName().substring(1);
			List<String> properties = (entity.getProperties()).stream().map(p -> p.getName())
					.collect(Collectors.toList());
			this.model.put("class_name", entityName);
			this.model.put("package", entity.getTypePackage());

			this.model.put("properties", properties);

			for (FMProperty property : entity.getProperties()) {
				if (property instanceof FMPersistentProperty) {
					FMPersistentProperty persistentPropery = (FMPersistentProperty) property;
					if (persistentPropery.getId()) {
						this.model.put("id_class", persistentPropery.getType().getName());
						break;
					}
				}

			}

			File file = new File(path + File.separatorChar + entityName + "Service.java");
			System.out.println(path + File.separatorChar + entityName + "Service.java");
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
