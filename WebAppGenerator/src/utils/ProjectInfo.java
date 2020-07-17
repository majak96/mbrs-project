package utils;

import java.io.File;

import ui.MainFrame;

public class ProjectInfo {

	private static ProjectInfo projectInfo;
	
	private MainFrame mainFrame;

	private String projectPath;
	private String projectName;
	private String projectPackage;

	private String applicationName;
	private String basePath;

	private String applicationPort;

	private String databaseUrl;
	private String databaseUsername;
	private String databasePassword;
	
	private File resourceFile;

	private ProjectInfo() {
		mainFrame = new MainFrame();
	}

	public static ProjectInfo getInstance() {
		if (projectInfo == null) {
			projectInfo = new ProjectInfo();
		}

		return projectInfo;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectPackage() {
		return projectPackage;
	}

	public void setProjectPackage(String projectPackage) {
		this.projectPackage = projectPackage;
	}

	public String getApplicationPort() {
		return applicationPort;
	}

	public void setApplicationPort(String applicationPort) {
		this.applicationPort = applicationPort;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getDatabaseUsername() {
		return databaseUsername;
	}

	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public File getResourceFile() {
		return resourceFile;
	}

	public void setResourceFile(File resourceFile) {
		this.resourceFile = resourceFile;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

}
