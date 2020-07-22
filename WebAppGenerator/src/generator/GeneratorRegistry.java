package generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
		generators.add(new OvirviewJsGenerator());
		generators.add(new FormJsGenerator());
		generators.add(new FrontendFormGenerator());
		generators.add(new IndexHTMLGenerator());
		generators.add(new OverviewHTMLGenerator());
		generators.add(new EnumGenerator());
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

		String projectPath = projectInfo.getProjectPath() + File.separator + "Generated Application";
		projectInfo.setProjectPath(projectPath);
		GeneratorUtils.createDirectory(projectPath);

		String backendPath = projectPath + File.separator + projectInfo.getProjectName();
		projectInfo.setBackendPath(backendPath);
		GeneratorUtils.createDirectory(backendPath);

		String frontendPath = projectPath + File.separator + projectInfo.getProjectName() + "_frontend";
		projectInfo.setFrontendPath(frontendPath);

		// create the project structure for frontend generated files
		GeneratorUtils.createDirectory(frontendPath);
		GeneratorUtils.createDirectory(frontendPath + File.separator + "css");
		GeneratorUtils.createDirectory(frontendPath + File.separator + "js");

		if(Files.notExists(Paths.get(frontendPath + File.separator + "css/index.css"))){
			try {
				Files.copy(Paths.get("resources/index.css"), Paths.get(frontendPath + File.separator + "css/index.css"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// create the project structure for generated files
		GeneratorUtils.createDirectory(backendPath + File.separator + "src-gen");
		GeneratorUtils.createDirectory(backendPath + File.separator + "src-gen" + File.separator + "main");
		GeneratorUtils.createDirectory(
				backendPath + File.separator + "src-gen" + File.separator + "main" + File.separator + "resources");
		GeneratorUtils.createDirectory(
				backendPath + File.separator + "src-gen" + File.separator + "main" + File.separator + "java");

		String[] packageGenStrings = projectInfo.getProjectPackage().split("\\.");
		String baseGenPath = backendPath + File.separator + "src-gen" + File.separator + "main" + File.separator
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
		GeneratorUtils.createDirectory(backendPath + File.separator + "src");
		GeneratorUtils.createDirectory(backendPath + File.separator + "src" + File.separator + "main");
		GeneratorUtils.createDirectory(
				backendPath + File.separator + "src" + File.separator + "main" + File.separator + "resources");
		GeneratorUtils.createDirectory(
				backendPath + File.separator + "src" + File.separator + "main" + File.separator + "java");

		String[] packageStrings = projectInfo.getProjectPackage().split("\\.");
		String basePath = backendPath + File.separator + "src" + File.separator + "main" + File.separator + "java";
		for (String packageString : packageStrings) {
			basePath += File.separator + packageString;
			GeneratorUtils.createDirectory(basePath);
		}

		projectInfo.setBaseHandwrittenFilesPath(basePath);
	}
}
