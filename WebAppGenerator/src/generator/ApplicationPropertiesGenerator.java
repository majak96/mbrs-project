package generator;

import java.io.File;
import java.io.IOException;

import utils.ProjectInfo;

public class ApplicationPropertiesGenerator extends AbstractGenerator {

	@Override
	public void init() {

	}

	@Override
	public void generate() {
		try {
			String propertiesPath = ProjectInfo.getInstance().getProjectPath() + File.separatorChar
					+ ProjectInfo.getInstance().getProjectName() + File.separatorChar + "src" + File.separatorChar
					+ "main" + File.separatorChar + "resources";
			File file = new File(propertiesPath + File.separatorChar + "application.properties");
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
