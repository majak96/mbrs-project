package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import freemarker.template.Template;
import model.FMEnumeration;
import model.FMModel;
import utils.ProjectInfo;

public class EnumGenerator extends AbstractGenerator {

	private List<FMEnumeration> enumerations;

	public EnumGenerator() {
		// TODO Auto-generated constructor stub
		FMModel parsedModel = FMModel.getInstance();
		enumerations = parsedModel.getEnumerations();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void generate() {
		// generate a file for every entity
		for (FMEnumeration enumeration : this.enumerations) {

			model.put("enum", enumeration);

			try {
				Template temp = generatorInfo.getConfiguration().getTemplate("enumeration.ftl");
				String enumerationName = enumeration.getName().substring(0, 1).toUpperCase()
						+ enumeration.getName().substring(1);
				File file = new File(ProjectInfo.getInstance().getBaseGeneratedFilesPath() + File.separatorChar
						+ "model" + File.separatorChar + enumerationName + ".java");
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
