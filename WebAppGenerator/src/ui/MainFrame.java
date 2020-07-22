package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import application.Application;
import utils.ProjectInfo;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 2454480143973598574L;
	private JPanel centerPanel;
	private String projectPathField;
	private File resourceFilePath;
	private JTextField projectNameField;
	private JTextField projectPackageField;
	private JTextField applicationNameField;
	private JTextField applicationPortField;
	private JTextField databaseUrlField;
	private JTextField databaseUsernameField;
	
	private JTextField projectPathFieldSelected;
	private JTextField resourceFilePathField;
	private JPasswordField databasePasswordField;
	private List<JComponent> comp;

	public MainFrame() {
		this.setSize(600, 500);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("Configure generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		this.setIconImage((new ImageIcon("resources/code.png")).getImage());
		

		comp = new ArrayList<>();
		comp.add(projectNameField);
		comp.add(projectPackageField);
		comp.add(applicationNameField);
		comp.add(applicationPortField);
		comp.add(databaseUrlField);
		comp.add(databaseUsernameField);
		comp.add(databasePasswordField);

		// panel init
		this.centerPanel = new JPanel();

		setUpCenterPanel();

		this.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JButton submitButton = new JButton("Generate project");
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean valid = true;
				for (JComponent component : comp) {
					if (component instanceof JTextField) {
						if (((JTextField) component).getText() == null) {
							valid = false;
							break;
						}
					} else if (component instanceof JPasswordField) {
						if (((JPasswordField) component).getPassword() == null) {
							valid = false;
							break;
						}
					}
				}

				if (valid && resourceFilePath != null && projectPathField != null) {
					ProjectInfo projectInfo = ProjectInfo.getInstance();
					projectInfo.setProjectName(projectNameField.getText().trim());
					projectInfo.setProjectPackage(projectPackageField.getText().trim());
					projectInfo.setProjectPath(projectPathField);
					projectInfo.setDatabaseUrl(databaseUrlField.getText().trim());
					projectInfo.setDatabaseUsername(databaseUsernameField.getText().trim());
					projectInfo.setDatabasePassword(new String(databasePasswordField.getPassword()));
					projectInfo.setResourceFile(resourceFilePath);
					projectInfo.setApplicationFrontendName(applicationNameField.getText().trim());
					projectInfo.setApplicationPort(applicationPortField.getText().trim());
					Application.generate();
					JOptionPane.showMessageDialog(MainFrame.this,
						    "Generation completed");
				}
			}
		});
		submitButton.setBackground(new Color(64, 128, 243));
		panel.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
		panel.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
		panel.add(submitButton, BorderLayout.CENTER);
		panel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
		panel.add(Box.createVerticalStrut(20), BorderLayout.NORTH);
		
		JButton addInputButton = new JButton();
		addInputButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				applicationNameField.setText("Name of the App");
				applicationPortField.setText("8080");
				projectNameField.setText("example-project");
				projectPackageField.setText("com.example");
				projectPathField = "C:\\Users\\Marijana\\Desktop\\MBRS gen";
				databaseUrlField.setText("jdbc:postgresql://localhost:5432/example-database");
				databaseUsernameField.setText("postgres");
				databasePasswordField.setText("root");
				resourceFilePath = new File("resources/example.xml");
				resourceFilePathField.setText("resources/example.xml");
				projectPathFieldSelected.setText("C:\\Users\\Marijana\\Desktop\\MBRS gen");
			}
		});

		this.add(addInputButton,BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
		this.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
		this.add(panel, BorderLayout.SOUTH);

		Container c = getContentPane();
		JScrollPane scroll = new JScrollPane(c);
		setContentPane(scroll);
	}

	public void setUpCenterPanel() {

		this.centerPanel.setLayout(new GridLayout(0, 1, 4, 4));
		JPanel projectInfoPanel = setUpProjectInfPanel();
		JPanel appInfoPanel = setUpApplicationPanel();
		JPanel dbInfoPanel = setUpDBPanel();

		centerPanel.add(projectInfoPanel);
		centerPanel.add(appInfoPanel);
		centerPanel.add(dbInfoPanel);

	}

	private JPanel setUpApplicationPanel() {
		JPanel appPanel = new JPanel();
		applicationNameField = new JTextField();
		applicationPortField = new JTextField();
		appPanel.setLayout(new GridLayout(0, 1));
		appPanel.setBorder(BorderFactory.createTitledBorder("Application information"));
		JLabel applicationNameLabel = new JLabel("Name");
		JLabel applicationPortLabel = new JLabel("Port");
		appPanel.add(applicationNameLabel);
		appPanel.add(applicationNameField);
		appPanel.add(applicationPortLabel);
		appPanel.add(applicationPortField);
		appPanel.add(new JPanel());
		appPanel.add(new JPanel());

		return appPanel;

	}

	private JPanel setUpProjectInfPanel() {
		JPanel projectInfoPanel = new JPanel();
		JPanel pathPanel = new JPanel();
		JPanel pathResourcePanel = new JPanel();
		projectInfoPanel.setLayout(new GridLayout(0, 1));
		projectInfoPanel.setBorder(BorderFactory.createTitledBorder("Project information"));
		JLabel projectPathLabel = new JLabel("Path");
		JLabel resourcePathLabel = new JLabel("Resource file");
		JLabel projectNameLabel = new JLabel("Name");
		JLabel projectPackageLabel = new JLabel("Package");
		projectNameField = new JTextField();
		projectPackageField = new JTextField();
		projectPathFieldSelected = new JTextField();
		resourceFilePathField = new JTextField();
		
		projectPathFieldSelected.setEditable(false);
		resourceFilePathField.setEditable(false);
		
		JButton selectProjectPathButton = new JButton("...");
		selectProjectPathButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser projectPathChooser = new JFileChooser();
				projectPathChooser.setCurrentDirectory(new java.io.File("."));
				projectPathChooser.setDialogTitle("Choose project path");
				projectPathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				projectPathChooser.setAcceptAllFileFilterUsed(false);
				if (projectPathChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getCurrentDirectory(): " + projectPathChooser.getCurrentDirectory());
					System.out.println("getSelectedFile() : " + projectPathChooser.getSelectedFile());
					projectPathField = projectPathChooser.getSelectedFile().toString();
					projectPathFieldSelected.setText(projectPathChooser.getSelectedFile().toString());
					
				} else {
					System.out.println("No Selection ");
				}
			}

		});

		JButton button1 = new JButton("...");
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser projectPathChooser = new JFileChooser();
				projectPathChooser.setCurrentDirectory(new java.io.File("."));
				projectPathChooser.setDialogTitle("Choose resource file");
				projectPathChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
				projectPathChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				projectPathChooser.setAcceptAllFileFilterUsed(false);
				if (projectPathChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getCurrentDirectory(): " + projectPathChooser.getCurrentDirectory());
					System.out.println("getSelectedFile() : " + projectPathChooser.getSelectedFile());
					resourceFilePath = projectPathChooser.getSelectedFile();
					resourceFilePathField.setText(projectPathChooser.getSelectedFile().toString());
				} else {
					System.out.println("No Selection ");
				}
			}

		});

		pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.X_AXIS));
		pathPanel.add(projectPathLabel);
		pathPanel.add(Box.createHorizontalStrut(5));
		pathPanel.add(projectPathFieldSelected);
		pathPanel.add(Box.createHorizontalStrut(5));
		pathPanel.add(selectProjectPathButton);

		pathResourcePanel.setLayout(new BoxLayout(pathResourcePanel, BoxLayout.X_AXIS));
		pathResourcePanel.add(resourcePathLabel);
		pathResourcePanel.add(Box.createHorizontalStrut(5));
		pathResourcePanel.add(resourceFilePathField);
		pathResourcePanel.add(Box.createHorizontalStrut(5));
		pathResourcePanel.add(button1);

		projectInfoPanel.add(pathPanel);
		projectInfoPanel.add(pathResourcePanel);
		projectInfoPanel.add(projectNameLabel);
		projectInfoPanel.add(projectNameField);
		projectInfoPanel.add(projectPackageLabel);
		projectInfoPanel.add(projectPackageField);

		return projectInfoPanel;
	}

	private JPanel setUpDBPanel() {
		JPanel dbInfoPanel = new JPanel();
		dbInfoPanel.setLayout(new GridLayout(0, 1));
		dbInfoPanel.setBorder(BorderFactory.createTitledBorder("Database information"));

		JLabel databasePasswordFieldLabel = new JLabel("Password");
		JLabel databaseUrlFieldLabel = new JLabel("URL");
		JLabel databaseUsernameFieldLabel = new JLabel("Username");

		databasePasswordField = new JPasswordField();
		databaseUrlField = new JTextField();
		databaseUsernameField = new JTextField();

		dbInfoPanel.add(databaseUrlFieldLabel);
		dbInfoPanel.add(databaseUrlField);
		dbInfoPanel.add(databaseUsernameFieldLabel);
		dbInfoPanel.add(databaseUsernameField);
		dbInfoPanel.add(databasePasswordFieldLabel);
		dbInfoPanel.add(databasePasswordField);

		return dbInfoPanel;
	}

}
