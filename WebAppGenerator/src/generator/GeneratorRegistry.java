package generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.GeneratorUtils;
import utils.ProjectInfo;

public class GeneratorRegistry {

	private List<AbstractGenerator> generators = new ArrayList<AbstractGenerator>();

	public void registerGenerators() {
		generators.add(new MainGenerator());
		generators.add(new ApplicationYmlGenerator());
		generators.add(new RepositoryGenerator());
		generators.add(new PomXmlGenerator());
		generators.add(new ControllerGenerator());
		generators.add(new ServiceGenerator());

		// TODO: add other generators
	}

	public void generate() {

		createProjectStructure();

		for (AbstractGenerator generator : generators) {
			generator.init();
			generator.generate();
		}

	}

	private void createProjectStructure() {
		ProjectInfo projectInfo = ProjectInfo.getInstance();

		String projectNamePath = projectInfo.getProjectPath() + File.separator + projectInfo.getProjectName();

		GeneratorUtils.createDirectory(projectNamePath);
		GeneratorUtils.createDirectory(projectNamePath + File.separator + "src");
		GeneratorUtils.createDirectory(projectNamePath + File.separator + "src" + File.separator + "main");
		GeneratorUtils.createDirectory(projectNamePath + File.separator + "src" + File.separator + "main" + File.separator + "resources");
		GeneratorUtils.createDirectory(projectNamePath + File.separator + "src" + File.separator + "main" + File.separator + "java");

		String[] packageStrings = projectInfo.getProjectPackage().split("\\.");
		String basePath = projectNamePath + File.separator + "src" + File.separator + "main" + File.separator + "java";
		for (String packageString : packageStrings) {
			basePath += File.separator + packageString;
			GeneratorUtils.createDirectory(basePath);
		}
		GeneratorUtils.createDirectory(basePath + File.separator + "model");
		GeneratorUtils.createDirectory(basePath + File.separator + "controllers");
		GeneratorUtils.createDirectory(basePath + File.separator + "services");
		GeneratorUtils.createDirectory(basePath + File.separator + "repositories");
		
		projectInfo.setBasePath(basePath);
	}
}
