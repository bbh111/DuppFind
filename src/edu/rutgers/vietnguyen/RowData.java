package edu.rutgers.vietnguyen;

public class RowData
{
	public boolean checked;
	public String fullPath;
	public String fileType;
	
	public RowData()
	{
		this.checked =false;
		this.fullPath = "";
		this.fileType = "";
	}
	public RowData(boolean checked, String fullPath, String fileType)
	{
		this.checked = checked;
		this.fullPath = fullPath;
		this.fileType = fileType;
	}
	
	public Object getValueAtColumn(int i)
	{
		if(i == 0)
			return checked;
		if(i == 1)
			return fullPath;
		else
			return fileType;
	}
	
	public void setValueAtColumn(int i, Object value)
	{
		if(i == 0)
			checked = (Boolean) value;
		else if(i == 1)
			fullPath = (String) value;
		else
			fileType = (String) value;
	}
}