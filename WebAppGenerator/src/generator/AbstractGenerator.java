package generator;

import java.util.HashMap;
import java.util.Map;

import utils.GeneratorInfo;
import utils.ProjectInfo;

public abstract class AbstractGenerator {

	protected GeneratorInfo generatorInfo;
	protected ProjectInfo projectInfo;
	protected Map<String, Object> model;

	public AbstractGenerator() {
		generatorInfo = GeneratorInfo.getInstance();
		projectInfo = ProjectInfo.getInstance();
		model = new HashMap<String, Object>();
	}

	public abstract void init();

	public abstract void generate();

}
