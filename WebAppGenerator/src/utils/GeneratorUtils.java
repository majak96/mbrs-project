package utils;

import java.io.File;

public class GeneratorUtils {

	public static void createDirectory(String name) throws SecurityException {
		File file = new File(name);

		if (!file.exists()) {
			try {
				file.mkdir();
			} catch (SecurityException se) {
				throw se;
			}
		}
	}

}
