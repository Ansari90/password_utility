package operator;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import security.SecurityData;
import data_io.Block;

public class GUI_Operator {
	public int blockIndex;
	public static GUI_Operator localOperator;
	
	private LogicDriver logicDriver;
	private ArrayList<Block> allInformation;
	private TextArea textArea;
	private JTextField nameField;

	public GUI_Operator()
	{
		blockIndex = -1;
		textArea = null;
		nameField = null;
		logicDriver = new LogicDriver();
		
		allInformation = logicDriver.getInformation();		
		if(allInformation == null)
		{
			allInformation = new ArrayList<Block>(); 
		}
	}
	
	public static void setupFrame()
	{
		localOperator = new GUI_Operator();
		
		JFrame mainFrame = new JFrame(SecurityData.TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		mainFrame.add(localOperator.set_GUI_Components());
		
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	public JPanel set_GUI_Components()
	{
		JPanel optionsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		
		JLabel nameLabel = new JLabel(SecurityData.ENTRY_NAME);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		optionsPanel.add(nameLabel, gbConstraints);

		nameField = new JTextField(SecurityData.DEFUALT_COLUMNS * 5);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		optionsPanel.add(nameField, gbConstraints);
		
		JButton searchButton = new JButton(SecurityData.SEARCH_FOR);
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 0;
		optionsPanel.add(searchButton, gbConstraints);
		
		JPanel dataPanel1 = new JPanel();
		dataPanel1.setLayout(new BoxLayout(dataPanel1, BoxLayout.PAGE_AXIS));
		
		JButton newButton = new JButton(SecurityData.NEW_ENTRY);
		newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		newButton.setBorder(BorderFactory.createEmptyBorder(10,21,10,21));
		dataPanel1.add(newButton);
		
		JButton saveButton = new JButton(SecurityData.SAVE_CHANGES);
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		dataPanel1.add(saveButton);
		
		JButton deleteButton = new JButton(SecurityData.DELETE_ENTRY);
		deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		deleteButton.setBorder(BorderFactory.createEmptyBorder(10,17,10,17));
		dataPanel1.add(deleteButton);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		optionsPanel.add(dataPanel1, gbConstraints);
		
		textArea = new TextArea(SecurityData.DEFUALT_COLUMNS, SecurityData.DEFUALT_COLUMNS * 10);
		textArea.setEditable(true);
		JScrollPane scrollPane = new JScrollPane(localOperator.textArea);		
		JPanel infoPanel = new JPanel();
		infoPanel.add(scrollPane);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		optionsPanel.add(infoPanel, gbConstraints);
		
		JPanel dataPanel2 = new JPanel();
		dataPanel2.setLayout(new BoxLayout(dataPanel2, BoxLayout.PAGE_AXIS));
		
		JButton commitButton = new JButton(SecurityData.COMMIT_CHANGES);
		commitButton.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
		commitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		dataPanel2.add(commitButton, gbConstraints);
		
		JButton exportButton = new JButton(SecurityData.EXPORT_TO_TEXT);
		exportButton.setBorder(BorderFactory.createEmptyBorder(10,21,10,21));
		exportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		dataPanel2.add(exportButton, gbConstraints);
		
		JButton importButton = new JButton(SecurityData.IMPORT_FROM_TEXT);
		importButton.setBorder(BorderFactory.createEmptyBorder(10,26,7,26));
		importButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		dataPanel2.add(importButton, gbConstraints);
		
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 1;
		optionsPanel.add(dataPanel2, gbConstraints);
		
		JPanel dataPanel3 = new JPanel();
		dataPanel3.setLayout(new BoxLayout(dataPanel3, BoxLayout.LINE_AXIS));
		
		JButton backwardButton = new JButton(SecurityData.BACKWARD);
		exportButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		dataPanel3.add(backwardButton, gbConstraints);
		
		JButton forwardButton = new JButton(SecurityData.FORWARD);
		exportButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		dataPanel3.add(forwardButton, gbConstraints);
		
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 2;
		optionsPanel.add(dataPanel3, gbConstraints);
		
		//Add Event Listeners
		addSearchButtonListener(searchButton);
		addNewButtonListener(newButton);
		addSaveButtonListener(saveButton);
		addCommitButtonListener(commitButton);
		addDeleteButtonListener(deleteButton);
		addExportButtonListener(exportButton);
		addImportButtonListener(importButton);
		addForwardButtonListener(forwardButton);
		addBackwardButtonListener(backwardButton);
		
		return optionsPanel;
	}
	
	//Event Listeners added in seperate functions, to prevent the setOptinsPanel function from becoming too crowded
	public void addSearchButtonListener(JButton searchButton)
	{
		class searchActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) {
				boolean found = false;
				blockIndex = 0;
				CharSequence cs = nameField.getText().toLowerCase();
				
				for(Block block : allInformation)
				{
					String lowerCaseName = block.getName().toLowerCase();
					if(lowerCaseName.contains(cs))
					{
						nameField.setText(block.getName());
						textArea.setText(block.getInformation());
						found = true;
						break;			//Stops at first match
					}
					else
					{
						blockIndex++;
					}
				}
				
				if(!found)
				{
					nameField.setText("");
					textArea.setText(SecurityData.NO_RESULT);
				}
			}			
		}
		
		searchButton.addActionListener(new searchActionListener());
	}
	
	public void addNewButtonListener(JButton newButton)
	{
		class newActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				allInformation.add(new Block(""));
				nameField.setText("");
				textArea.setText("");
				blockIndex = allInformation.size() - 1;				
			}			
		}
		
		newButton.addActionListener(new newActionListener());
	}
	
	public void addSaveButtonListener(JButton saveButton)
	{
		class saveActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(blockIndex != -1)
				{
					Block tempBlock = allInformation.get(blockIndex);
					tempBlock.setName(nameField.getText());
					tempBlock.setInformation(textArea.getText());
				}
			}
		}
		
		saveButton.addActionListener(new saveActionListener());
	}
	
	public void addCommitButtonListener(JButton commitButton)
	{
		class commitActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				logicDriver.saveInformation(allInformation);
			}
		}
		
		commitButton.addActionListener(new commitActionListener());
	}
	
	public void addDeleteButtonListener(JButton deleteButton)
	{
		class deleteActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(blockIndex != -1)
				allInformation.remove(blockIndex);
				nameField.setText("");
				textArea.setText("");
			}
		}
		
		deleteButton.addActionListener(new deleteActionListener());
	}
	
	public void addForwardButtonListener(JButton forwardButton)
	{
		class forwardActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(blockIndex < (allInformation.size() - 1))
				{
					Block tempBlock = allInformation.get(++blockIndex);
					nameField.setText(tempBlock.getName());
					textArea.setText(tempBlock.getInformation());
				}
			}
		}
		
		forwardButton.addActionListener(new forwardActionListener());
	}
	
	public void addBackwardButtonListener(JButton forwardButton)
	{
		class backwardActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(blockIndex > 0)
				{
					Block tempBlock = allInformation.get(--blockIndex);
					nameField.setText(tempBlock.getName());
					textArea.setText(tempBlock.getInformation());
				}
			}
		}
		
		forwardButton.addActionListener(new backwardActionListener());
	}
	
	public void addExportButtonListener(JButton exportButton)
	{
		class exportActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) {
				logicDriver.backupInformation(allInformation);
			}	
		}
		
		exportButton.addActionListener(new exportActionListener());
	}
	
	public void addImportButtonListener(JButton importButton)
	{
		class importActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) {
				allInformation = logicDriver.importInformation();
			}			
		}
		
		importButton.addActionListener(new importActionListener());
	}
	
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setupFrame();
            }
        });		
		
	}
}