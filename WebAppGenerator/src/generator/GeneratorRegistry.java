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
		generators.add(new ApplicationPropertiesGenerator());
		generators.add(new ModelBaseGenerator());
		generators.add(new ModelGenerator());
		generators.add(new RepositoryBaseGenerator());
		generators.add(new RepositoryGenerator());
		generators.add(new PomXmlGenerator());
		generators.add(new ControllerBaseGenerator());
		generators.add(new ControllerGenerator());
		generators.add(new ServiceBaseGenerator());
		generators.add(new ServiceGenerator());
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

		// create the project structure for generated files
		GeneratorUtils.createDirectory(projectNamePath + File.separator + "src-gen");
		GeneratorUtils.createDirectory(projectNamePath + File.separator + "src-gen" + File.separator + "main");
		GeneratorUtils.createDirectory(
				projectNamePath + File.separator + "src-gen" + File.separator + "main" + File.separator + "resources");
		GeneratorUtils.createDirectory(
				projectNamePath + File.separator + "src-gen" + File.separator + "main" + File.separator + "java");

		String[] packageGenStrings = projectInfo.getProjectPackage().split("\\.");
		String baseGenPath = projectNamePath + File.separator + "src-gen" + File.separator + "main" + File.separator
				+ "java";
		for (String packageString : packageGenStrings) {
			baseGenPath += File.separator + packageString;
			GeneratorUtils.createDirectory(baseGenPath);
		}
		GeneratorUtils.createDirectory(baseGenPath + File.separator + "model");
		GeneratorUtils.createDirectory(baseGenPath + File.separator + "controllers");
		GeneratorUtils.createDirectory(baseGenPath + File.separator + "services");
		GeneratorUtils.createDirectory(baseGenPath + File.separator + "repositories");

		projectInfo.setBaseGeneratedFilesPath(baseGenPath);

		// create the project structure for handwritten files
		GeneratorUtils.createDirectory(projectNamePath + File.separator + "src");
		GeneratorUtils.createDirectory(projectNamePath + File.separator + "src" + File.separator + "main");
		GeneratorUtils.createDirectory(
				projectNamePath + File.separator + "src" + File.separator + "main" + File.separator + "resources");
		GeneratorUtils.createDirectory(
				projectNamePath + File.separator + "src" + File.separator + "main" + File.separator + "java");

		String[] packageStrings = projectInfo.getProjectPackage().split("\\.");
		String basePath = projectNamePath + File.separator + "src" + File.separator + "main" + File.separator + "java";
		for (String packageString : packageStrings) {
			basePath += File.separator + packageString;
			GeneratorUtils.createDirectory(basePath);
		}

		projectInfo.setBaseHandwrittenFilesPath(basePath);
	}
}
