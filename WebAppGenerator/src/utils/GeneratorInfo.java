package utils;

import java.io.File;
import java.io.IOException;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class GeneratorInfo {

	private static GeneratorInfo generatorInfo;

	private Configuration configuration;

	@SuppressWarnings("deprecation")
	private GeneratorInfo() {
		configuration = new Configuration();

		try {
			configuration.setTemplateLoader(new FileTemplateLoader(new File("resources/templates")));
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		} catch (IOException e) {

		}
	}

	public static GeneratorInfo getInstance() {
		if (generatorInfo == null) {
			generatorInfo = new GeneratorInfo();
		}

		return generatorInfo;
	}

	public static GeneratorInfo getGeneratorInfo() {
		return generatorInfo;
	}

	public static void setGeneratorInfo(GeneratorInfo generatorInfo) {
		GeneratorInfo.generatorInfo = generatorInfo;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}
