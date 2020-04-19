package org.schemaspygui.schemaspygui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultCaret;

import com.alee.laf.WebLookAndFeel;

public class SchemaspyGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JToolBar toolBar;
	private JTabbedPane tabbedPane;
	private JTextField tfDbHost,tfDbPort,tfDbUserName,tfDbName,tfDbPassword,tfSchema;
	private JTextField tfOutputFolder,tfSchemaspyPath,tfDriverPath;
	
	private JComboBox<String> cbDbType;
	private JTextArea taOutput;
	private JButton btnLoad,btnSave,btnClear,btnStart,btnShowOutput,btnAbout,btnExit;
	private JButton btnBrowseDriver,btnBrowseSchemaspy,btnBrowseOutputFolder;
	private JLabel lblProcessing;
	private Properties paramProperties;
	
	SchemaspyGui(){
		setUi();
		initComponents();
		paramProperties = new Properties();
		//initTestData();
	}
	
	private void setUi() {
		setIconImage(new ImageIcon("resources/logo.png").getImage());
		setTitle("Schemaspy GUI ");
		setResizable(true);
		setSize(700, 600);
		
		try {
			if(Config.enableLookAndFeel) {
				UIManager.setLookAndFeel ( new WebLookAndFeel () );
			}else {
				UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName());
			}
		} catch(Exception e){
		e.printStackTrace();
		} 
	}
	
	private void initComponents() {
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		btnLoad = new JButton("Load");
		btnLoad.addActionListener(new OpenEventListener());
		btnLoad.setIcon(ImageUtil.getResizedIcon("resources/open.png", 16));
		toolBar.add(btnLoad);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new SaveEventListener());
		btnSave.setIcon(ImageUtil.getResizedIcon("resources/save.png", 16));
		toolBar.add(btnSave);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ClearBtnEventListener());
		btnClear.setIcon(ImageUtil.getResizedIcon("resources/clear.png", 16));
		toolBar.add(btnClear);
		
		btnStart = new JButton("Start");
		//btnStart.setEnabled(false);
		btnStart.setIcon(ImageUtil.getResizedIcon("resources/start.png", 16));
		btnStart.addActionListener(new StartBtnEventListener());
		toolBar.add(btnStart);
		
		btnShowOutput = new JButton("Output");
		btnShowOutput.setEnabled(false);
		btnShowOutput.addActionListener(new OutputActionListener());
		btnShowOutput.setIcon(ImageUtil.getResizedIcon("resources/output.png", 16));
		toolBar.add(btnShowOutput);
		
		btnAbout = new JButton("About");
		btnAbout.addActionListener(new AboutEventListener());
		btnAbout.setIcon(ImageUtil.getResizedIcon("resources/info.png", 16));
		toolBar.add(btnAbout);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setIcon(ImageUtil.getResizedIcon("resources/exit.png", 16));
		toolBar.add(btnExit);
		
		lblProcessing = new JLabel("");
		lblProcessing.setVisible(false);
		lblProcessing.setToolTipText("Running command..");
		lblProcessing.setIcon(ImageUtil.getIcon("resources/loading1.gif"));
		toolBar.add(lblProcessing);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel outputPanel = new JPanel();
		
		outputPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane opScrollPane = new JScrollPane();
		outputPanel.add(opScrollPane, BorderLayout.CENTER);
		
		taOutput = new JTextArea();
		taOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
		taOutput.setEditable(false);
		DefaultCaret caret = (DefaultCaret) taOutput.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		opScrollPane.setViewportView(taOutput);
		JPanel optionsPanel = new JPanel();
		tabbedPane.addTab("Parameters", null, optionsPanel, null);
		tabbedPane.addTab("Output", null, outputPanel, null);
		optionsPanel.setLayout(null);
		
		
		
		cbDbType = new JComboBox<String>();
		cbDbType.setModel(Config.getDatabaseTypes());
		cbDbType.setBounds(124, 34, 528, 31);
		optionsPanel.add(cbDbType);
		
		tfDbHost = new JTextField();
		tfDbHost.setBounds(124, 79, 200, 31);
		optionsPanel.add(tfDbHost);
		tfDbHost.setColumns(10);
		
		tfDbPort = new JTextField();
		tfDbPort.setBounds(452, 79, 200, 31);
		optionsPanel.add(tfDbPort);
		tfDbPort.setColumns(10);
		
		tfDbUserName = new JTextField();
		tfDbUserName.setBounds(124, 126, 200, 31);
		optionsPanel.add(tfDbUserName);
		tfDbUserName.setColumns(10);
		
		tfDbName = new JTextField();
		tfDbName.setBounds(124, 173, 200, 31);
		optionsPanel.add(tfDbName);
		tfDbName.setColumns(10);
		
		tfDbPassword = new JPasswordField();
		tfDbPassword.setBounds(452, 126, 200, 31);
		optionsPanel.add(tfDbPassword);
		tfDbPassword.setColumns(10);
		
		JLabel lblDatabaseType = new JLabel("Database Type");
		lblDatabaseType.setBounds(19, 34, 111, 23);
		optionsPanel.add(lblDatabaseType);
		
		JLabel lblDatabaseHost = new JLabel("Database Host");
		lblDatabaseHost.setBounds(19, 87, 111, 14);
		optionsPanel.add(lblDatabaseHost);
		
		JLabel lblDatabaseUsername = new JLabel("Database Username");
		lblDatabaseUsername.setBounds(19, 134, 111, 14);
		optionsPanel.add(lblDatabaseUsername);
		
		JLabel lblDatabasePassword = new JLabel("Database Password");
		lblDatabasePassword.setBounds(351, 136, 111, 14);
		optionsPanel.add(lblDatabasePassword);
		
		JLabel lblDatabaseName = new JLabel("Database Name");
		lblDatabaseName.setBounds(19, 181, 111, 14);
		optionsPanel.add(lblDatabaseName);
		
		JLabel lblDbPort = new JLabel("Database Port");
		lblDbPort.setBounds(351, 89, 97, 14);
		optionsPanel.add(lblDbPort);
		
		tfDriverPath = new JTextField();
		tfDriverPath.setBounds(126, 223, 427, 31);
		tfDriverPath.setEditable(false);
		optionsPanel.add(tfDriverPath);
		tfDriverPath.setColumns(10);
		
		JLabel lblDriver = new JLabel("DB Driver Path");
		lblDriver.setBounds(19, 231, 97, 14);
		optionsPanel.add(lblDriver);
		
		btnBrowseDriver = new JButton("Choose");
		btnBrowseDriver.addActionListener(new DbDriverFileChooserEvent());
		btnBrowseDriver.setBounds(563, 223, 89, 31);
		optionsPanel.add(btnBrowseDriver);
		
		JLabel lblSchemaspypath = new JLabel("Schemaspy Path");
		lblSchemaspypath.setBounds(19, 276, 97, 14);
		optionsPanel.add(lblSchemaspypath);
		
		tfSchemaspyPath = new JTextField();
		tfSchemaspyPath.setColumns(10);
		tfSchemaspyPath.setEditable(false);
		tfSchemaspyPath.setBounds(126, 268, 427, 31);
		optionsPanel.add(tfSchemaspyPath);
		
		btnBrowseSchemaspy = new JButton("Choose");
		btnBrowseSchemaspy.addActionListener(new SchemaspyFileChooserEvent());
		btnBrowseSchemaspy.setBounds(563, 268, 89, 31);
		optionsPanel.add(btnBrowseSchemaspy);
		
		JLabel lblOutput = new JLabel("Output Directory");
		lblOutput.setBounds(19, 316, 97, 14);
		optionsPanel.add(lblOutput);
		
		tfOutputFolder = new JTextField();
		tfOutputFolder.setColumns(10);
		tfOutputFolder.setBounds(126, 309, 427, 31);
		tfOutputFolder.setEditable(false);
		optionsPanel.add(tfOutputFolder);
		
		btnBrowseOutputFolder = new JButton("Choose");
		btnBrowseOutputFolder.addActionListener(new OutputFolderChooserEvent());
		btnBrowseOutputFolder.setBounds(563, 309, 89, 31);
		optionsPanel.add(btnBrowseOutputFolder);
		
		JPanel flagsPanel = new JPanel();
		flagsPanel.setBorder(new TitledBorder(null, "Flags", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		flagsPanel.setBounds(19, 362, 633, 107);
		optionsPanel.add(flagsPanel);
		
		JLabel lblSchema = new JLabel("Schema");
		lblSchema.setBounds(351, 183, 111, 14);
		optionsPanel.add(lblSchema);
		
		tfSchema = new JTextField();
		tfSchema.setText("user1");
		tfSchema.setColumns(10);
		tfSchema.setBounds(452, 173, 200, 31);
		optionsPanel.add(tfSchema);
	}
	
	private boolean validateInput() {
		boolean status = true;
		if(tfDbHost.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Enter Database Host", "Error", JOptionPane.ERROR_MESSAGE, null);
			return false;
		}
		return status;
	}
	
	private String buildCommand() {
		StringBuilder sb = new StringBuilder();
		sb.append("java -jar");
		sb.append(" "+getQuotedString(paramProperties.getProperty(Parameters.SCHEMASPY_PATH)));
		sb.append(" -dp "+getQuotedString(paramProperties.getProperty(Parameters.DRIVER_PATH)));
		sb.append(" -t "+cbDbType.getSelectedItem().toString().toLowerCase());
		sb.append(" -db "+tfDbName.getText());
		sb.append(" -s "+tfSchema.getText());
		sb.append(" -host "+tfDbHost.getText());
		sb.append(" -port "+tfDbPort.getText());
		sb.append(" -u "+tfDbUserName.getText());
		sb.append(" -p "+tfDbPassword.getText());
		sb.append(" -o "+getQuotedString(paramProperties.getProperty(Parameters.OUTPUT_DIR)));
		sb.append(" -vizjs ");
		return sb.toString();
	}
	
	private void clear() {
		tfDbHost.setText("");
		tfDbName.setText("");
		tfDbPort.setText("");
		tfDbPassword.setText("");
		tfDriverPath.setText("");
		tfDbUserName.setText("");
		tfOutputFolder.setText("");
		tfSchema.setText("");
		tfSchemaspyPath.setText("");
		paramProperties.clear();
		taOutput.setText("");
		cbDbType.setSelectedIndex(0);
		
	}
	
	private void reload() {
		tfDbHost.setText(paramProperties.getProperty(Parameters.DB_HOST));
		tfDbName.setText(paramProperties.getProperty(Parameters.DB_NAME));
		tfDbPort.setText(paramProperties.getProperty(Parameters.DB_PORT));
		tfDbPassword.setText(paramProperties.getProperty(Parameters.DB_PASSWORD));
		tfDriverPath.setText(paramProperties.getProperty(Parameters.DRIVER_PATH));
		tfDbUserName.setText(paramProperties.getProperty(Parameters.DB_USERNAME));
		tfOutputFolder.setText(paramProperties.getProperty(Parameters.OUTPUT_DIR));
		tfSchema.setText(paramProperties.getProperty(Parameters.DB_SCHEMA));
		tfSchemaspyPath.setText(paramProperties.getProperty(Parameters.SCHEMASPY_PATH));
		cbDbType.setSelectedItem(paramProperties.getProperty(Parameters.DB_TYPE));
	}
	
	//Inner classes
	
	class RunCommand implements  Runnable{
		private String cmd;
		RunCommand(String s){
			cmd = s;
		}
		
		public void run() {
			lblProcessing.setVisible(true);
			taOutput.setText("");
			Runtime runtime = Runtime.getRuntime();
		    Process process = null;
		    BufferedReader is;
		    BufferedReader es;
		    String line;
		    try {
				process = runtime.exec(cmd);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		    System.out.println("In Main after exec");
		    is = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    es = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		    try {
				while ((line = is.readLine()) != null) {
				  System.out.println(line);
				  taOutput.append(line+"\n");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		    
		    try {
				while ((line = es.readLine()) != null) {
				  System.out.println(line);
				  taOutput.append(line+"\n");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		    
		    System.out.println("In Main after EOF");
		    System.out.flush();
		    try {
		      process.waitFor();  // wait for process to complete
		    } catch (InterruptedException e) {
		      System.err.println(e);
		      return;
		    }
		    lblProcessing.setVisible(false);
		    int statusCode = process.exitValue();
		    if(statusCode == 0) {
		    	btnShowOutput.setEnabled(true);
		    	int option = JOptionPane.showConfirmDialog(null, "Export completed.\n Do you want to browse output?", "Message", JOptionPane.YES_NO_OPTION);
		    	if(option == JOptionPane.YES_OPTION) {
		    		String url = paramProperties.getProperty(Parameters.OUTPUT_DIR)+File.separator+"index.html";
					Util.openUrl(url);
		    	}
		    }
		    System.err.println("Process done, exit status was " + statusCode);
		    return;
			
		}
	    
	  }
		
	
	private String getQuotedString(String s) {
		return "\""+s+"\"";
	}
	
	
	class DbDriverFileChooserEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(".");
			jfc.setDialogTitle("Choose Database Driver");
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setMultiSelectionEnabled(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Jar files", "jar");
			//jfc.addChoosableFileFilter(filter);
			jfc.setFileFilter(filter);
			
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				paramProperties.setProperty(Parameters.DRIVER_PATH, path);
				tfDriverPath.setText(path);
			
			}
		}
	}
	
	class SchemaspyFileChooserEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(".");
			jfc.setDialogTitle("Choose Schemaspy Jar file");
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setMultiSelectionEnabled(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Jar files", "jar");
			jfc.addChoosableFileFilter(filter);
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				paramProperties.setProperty(Parameters.SCHEMASPY_PATH,path);
				tfSchemaspyPath.setText(path);
			
			}
		}
	}
	
	class OutputFolderChooserEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(".");
			jfc.setDialogTitle("Choose Output Folder");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				String path =  selectedFile.getAbsolutePath();
				paramProperties.setProperty(Parameters.OUTPUT_DIR,path);
				tfOutputFolder.setText(path);
			
			}
		}
	}
	
	class StartBtnEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (validateInput()) {
				String cmd = buildCommand();
				// System.out.println("Command:"+cmd);
				tabbedPane.setSelectedIndex(1);
				try {
					RunCommand runner = new RunCommand(cmd);
					Thread t1 = new Thread(runner);
					t1.start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	class ClearBtnEventListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			clear();
		}
	}
	
	class OutputActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String url = paramProperties.getProperty(Parameters.OUTPUT_DIR)+File.separator+"index.html";
			Util.openUrl(url);
		}
	}
	
	class AboutEventListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			new About();
		}
	}
	
	class SaveEventListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(".");
			jfc.setDialogTitle("Save Parameters");
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setMultiSelectionEnabled(false);
			jfc.setSelectedFile(new File("schemaspygui.ssg"));
			jfc.setFileFilter(new javax.swing.filechooser.FileFilter()
			   {
			        public boolean accept(final File f)
			        {
			            return f.isDirectory()|| f.getAbsolutePath().endsWith(".ssg");
			        }

			        public String getDescription()
			        {
			            return "SchemaSpy GUI Session (*.ssg)";
			        }
			  });
			
			int returnValue = jfc.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				paramProperties.setProperty(Parameters.DB_HOST, tfDbHost.getText());
				paramProperties.setProperty(Parameters.DB_PORT, tfDbPort.getText());
				paramProperties.setProperty(Parameters.DB_PASSWORD, tfDbPassword.getText());
				paramProperties.setProperty(Parameters.DB_USERNAME, tfDbUserName.getText());
				paramProperties.setProperty(Parameters.DB_NAME, tfDbName.getText());
				paramProperties.setProperty(Parameters.DB_SCHEMA, tfSchema.getText());
				paramProperties.setProperty(Parameters.DB_TYPE, cbDbType.getSelectedItem().toString());
				Util.putProperties(paramProperties, path);
			}
		}
	}
	
	class OpenEventListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(".");
			jfc.setDialogTitle("Load Parameters");
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setMultiSelectionEnabled(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("SchemaSpy GUI Session", "ssg");
			//jfc.addChoosableFileFilter(filter);
			jfc.setSelectedFile(new File("schemaspygui.ssg"));
			jfc.setFileFilter(new javax.swing.filechooser.FileFilter()
			   {
			        public boolean accept(final File f)
			        {
			            return f.isDirectory()|| f.getAbsolutePath().endsWith(".ssg");
			        }

			        public String getDescription()
			        {
			            return "SchemaSpy GUI Session (*.ssg)";
			        }
			  });
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				paramProperties = Util.getProperties(path);
				reload();
			
			}
		}
	}
}
