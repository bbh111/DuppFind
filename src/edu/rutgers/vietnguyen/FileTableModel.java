package edu.rutgers.vietnguyen;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class FileTableModel extends AbstractTableModel	{
	private String[] columnNames = {"Checked", "Full path", "File type"};

	private Vector<RowData> rows_data = new Vector<RowData>();
	
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return rows_data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
    
	public Object getValueAt(int row, int col)
	{
		return rows_data.get(row).getValueAtColumn(col);
	}
	
	public Class getColumnClass(int c)
	{
		return getValueAt(0,c).getClass();
	}
	
	public boolean isCellEditable(int row, int col)
	{
		if(col == 0)
			return true;
		else
			return false;
	}
	
	public void setValueAt(Object value, int row, int col)
	{
		rows_data.get(row).setValueAtColumn(col, value);
		fireTableCellUpdated(row, col);
	}
	
	public void insertEmptyRow()
	{ 
		RowData row = new RowData();
		rows_data.addElement(row) ; 
		fireTableChanged(null); 
	} 
	
	public void insertRow(RowData row)
	{
		rows_data.addElement(row);
		fireTableChanged(null); 
	}
	
	public void deleteRow(int index)
	{
		rows_data.remove(index);
		fireTableChanged(null);
	}
	
	public void deleteAllRows()
	{
		rows_data.removeAllElements();
		fireTableChanged(null);
	}
	
}

