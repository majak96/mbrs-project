package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import freemarker.template.Template;
import model.FMEntity;
import model.FMEnumeration;
import model.FMModel;
import model.FMPersistentProperty;
import utils.ProjectInfo;

public class FrontendFormGenerator extends AbstractGenerator {

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

			List<FMPersistentProperty> persistentProperties = new ArrayList<FMPersistentProperty>();
			List<FMPersistentProperty> enumerations = new ArrayList<FMPersistentProperty>();
			for (FMPersistentProperty property : entity.getPersistentProperties()) {
				if (property.getType() instanceof FMEnumeration) {
					enumerations.add(property);
				} else {
					persistentProperties.add(property);
				}
			}
			
			model.put("persistentProperties", persistentProperties);
			model.put("enumerations", enumerations);

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
