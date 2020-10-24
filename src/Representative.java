import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Representative
{
	private Integer RepID;
	private SimpleStringProperty FirstName;
	private SimpleStringProperty LastName;
	private Integer CmpID;
	private SimpleStringProperty CntNO;

	Representative()
	{
		RepID=0;
		CmpID=0;
		FirstName=new SimpleStringProperty();
		LastName=new SimpleStringProperty();
		CntNO=new SimpleStringProperty();
	}

	Representative(String RepID,String FirstName,String LastName,String CmpID,String CntNO)
	{
		this.RepID=Integer.valueOf(RepID);
		this.FirstName=new SimpleStringProperty(FirstName);
		this.LastName=new SimpleStringProperty(LastName);
		this.CmpID=Integer.valueOf(CmpID);
		this.CntNO=new SimpleStringProperty(CntNO);

	}

	public Integer getRepID()
	{
		return RepID;
	}

	public void setRepID(Integer r)
	{
		RepID=r;
	}

	public String getFirstName()
	{
		return FirstName.get();
	}

	public void setFirstName(String firstName)
	{
		this.FirstName.set(firstName);
	}

	public String getLastName()
	{
		return LastName.get();
	}

	public void setLastName(String lastName)
	{
		this.LastName.set(lastName);
	}

	public Integer getCmpID()
	{
		return CmpID;
	}

	public void setCmpID(Integer c)
	{
		CmpID=c;
	}

	public String getCntNO()
	{
		return CntNO.get();
	}

	public void setCntNO(String cntNO)
	{
		this.CntNO.set(cntNO);
	}

	private String getSqlAdd()
	{
		String str="";
		str+=RepID.toString()+",";
		str+="'"+FirstName.get()+"',";
		str+="'"+LastName.get()+"',";
		str+=CmpID.toString()+",";
		str+="'"+CntNO.get()+"')";
		return str;
	}

	public static List<List<String>> getAllRepresentatives(Connection con)
	{
		String sql = "SELECT * FROM Representative";
		List<List<String>> resultList = new ArrayList<>();
		try{
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next())
			{
				List<String> row = new ArrayList<>();
				row.add(rs.getString("RepID"));
				row.add(rs.getString("FirstName"));
				row.add(rs.getString("LastName"));
				row.add(rs.getString("CmpId"));
				row.add(rs.getString("CntNO"));
				resultList.add(row);
			}
			pst.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultList;
	}

	public void insertIntoDatabase(Connection con) throws SQLException
	{
		Statement st=null;
		String sql="INSERT INTO REPRESENTATIVE(REPID,FIRSTNAME,LASTNAME,CMPID,CNTNO) " +
				"VALUES("+getSqlAdd();
		st=con.createStatement();
		st.executeUpdate(sql);
	}

	public void deleteFromDatabase(Connection con)
	{
		Statement st=null;
		String sql="DELETE FROM REPRESENTATIVE WHERE REPID="+RepID.toString();
		try
		{
			st=con.createStatement();
			st.executeUpdate(sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
