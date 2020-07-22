package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import freemarker.template.Template;
import model.FMEntity;
import model.FMEnumeration;
import model.FMModel;
import model.FMPersistentProperty;
import model.FMType;
import utils.ProjectInfo;

public class ModelBaseGenerator extends AbstractGenerator {

	private List<FMEntity> entities;

	@Override
	public void init() {
		FMModel parsedModel = FMModel.getInstance();
		entities = parsedModel.getEntities();
	}

	@Override
	public void generate() {
		// generate a model file for every entity
		for (FMEntity entity : entities) {

			// sort imports alphabetically
			List<FMType> imports = new ArrayList<FMType>(entity.getImportedPackages());
			Collections.sort(imports, new Comparator<FMType>() {
				@Override
				public int compare(final FMType object1, final FMType object2) {
					return (object1.getTypePackage() + "." + object1.getName())
							.compareTo(object2.getTypePackage() + "." + object2.getName());
				}
			});

			model.put("entity", entity);
			model.put("imports", imports);

			List<FMPersistentProperty> persistentProperties = new ArrayList<FMPersistentProperty>();
			List<FMPersistentProperty> enumerations = new ArrayList<FMPersistentProperty>();
			for (FMPersistentProperty property : entity.getPersistentProperties()) {
				if (property.getType() instanceof FMEnumeration) {
					enumerations.add(property);
				} else {
					persistentProperties.add(property);
				}
			}
			
			model.put("persistent_properties", persistentProperties);
			model.put("enumeration_properties", enumerations);

			try {
				Template temp = generatorInfo.getConfiguration().getTemplate("model_base.ftl");

				String entityName = entity.getName().substring(0, 1).toUpperCase() + entity.getName().substring(1);
				File file = new File(ProjectInfo.getInstance().getBaseGeneratedFilesPath() + File.separatorChar
						+ "model" + File.separatorChar + "Base" + entityName + ".java");
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
