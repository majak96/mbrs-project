package generator;

import java.util.ArrayList;
import java.util.List;

public class GeneratorRegistry {

	private List<AbstractGenerator> generators = new ArrayList<AbstractGenerator>();
	
	public void registerGenerators() {
		generators.add(new RepositoryGenerator());
		//TODO: add other generators
	}
	
	public void generate() {
		
		for(AbstractGenerator generator : generators) {
			generator.init();
			generator.generate();
		}
		
	}
}
