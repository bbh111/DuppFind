package edu.rutgers.vietnguyen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class DuppFindFrame extends JFrame implements ActionListener{
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	
	JFileChooser fc;
	JButton findButton, openButton, deleteButton;
	JLabel label;
	JTextField field;
	JPanel top, bottom;
	JTable fileTable;
	
	File folderToScan;
	
	public DuppFindFrame()
	{	
		setTitle("Dupplicate File Finder! @Viet Nguyen");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		//--File chooser--
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		//--TOP--
		findButton = new JButton("Find");
		findButton.addActionListener(this);
		openButton = new JButton("Choose dir...");
		openButton.addActionListener(this);
		label = new JLabel("Path: ", JLabel.RIGHT);
		field = new JTextField("", 40);
		field.setEnabled(false);
		top = new JPanel();
		top.add(label,BorderLayout.WEST);
		top.add(field,BorderLayout.CENTER);
		top.add(openButton, BorderLayout.EAST);
		top.add(findButton, BorderLayout.EAST);
		add(top, BorderLayout.NORTH);
		
		//--BOTTOM--
		bottom = new JPanel();

		fileTable = new JTable(new FileTableModel());
		fileTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		fileTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		fileTable.getColumnModel().getColumn(2).setPreferredWidth(20);
		fileTable.doLayout();
		JScrollPane scrollPane = new JScrollPane(fileTable);
		fileTable.setFillsViewportHeight(true);
		
		((FileTableModel)(fileTable.getModel())).insertEmptyRow();
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		bottom.add(scrollPane);
		bottom.add(deleteButton);
		add(bottom);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Handle open button action
		if(e.getSource() == openButton)
		{
			int returnVal = fc.showOpenDialog(DuppFindFrame.this);
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				folderToScan = fc.getSelectedFile();
				field.setText(folderToScan.getAbsolutePath());
				
			}
			else 
			{
				System.out.println("Open command cancelled!");
			}
		}
		
		//Handle find button action
		if(e.getSource() == findButton)
		{
			DuppFind df = new DuppFind(folderToScan.getAbsolutePath(), (FileTableModel)(fileTable.getModel()));
			findButton.setEnabled(false);
			openButton.setEnabled(false);
			deleteButton.setEnabled(false);
			df.run();
			
			findButton.setEnabled(true);
			openButton.setEnabled(true);
			deleteButton.setEnabled(true);
			//new Thread(df).start();
		}
		
		//Handle delete button action
		if(e.getSource() == deleteButton)
		{
			List<Integer> delFileIndex = new ArrayList<Integer>();
			FileTableModel fileTableModel = (FileTableModel) (fileTable.getModel());
			int rowCount = fileTableModel.getRowCount();
			for(int row = 0; row < rowCount; row++)
			{
				if((Boolean)(fileTableModel.getValueAt(row, 0))==true)
				{
					delFileIndex.add(row);
					String filePath = (String)(fileTableModel.getValueAt(row,1));
					if(FileUtils.deleteFile(filePath))
					{
						System.out.println("    Deleted file: " + filePath );
					}
				}
			}
			
			//clean the table
			for(int i:delFileIndex)
			{
				fileTableModel.deleteRow(i);
			}
		}
	}
}


