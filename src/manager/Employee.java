package manager;

import javafx.beans.property.SimpleStringProperty;
import utilities.OracleDBMS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Employee
{
    private SimpleStringProperty name;
    private SimpleStringProperty doj;
    private SimpleStringProperty cntNo;
    private Integer salary;
    
    public Employee(String name,String doj,String cntNo,Integer salary)
    {
        this.name=new SimpleStringProperty(name);
        this.doj=new SimpleStringProperty(doj);
        this.cntNo=new SimpleStringProperty(cntNo);
        this.salary=salary;
    }
    
    public String getName()
    {
        return name.get();
    }
    
    public SimpleStringProperty nameProperty()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name.set(name);
    }
    
    public String getDoj()
    {
        return doj.get();
    }
    
    public SimpleStringProperty dojProperty()
    {
        return doj;
    }
    
    public void setDoj(String doj)
    {
        this.doj.set(doj);
    }
    
    public String getCntNo()
    {
        return cntNo.get();
    }
    
    public SimpleStringProperty cntNoProperty()
    {
        return cntNo;
    }
    
    public void setCntNo(String cntNo)
    {
        this.cntNo.set(cntNo);
    }
    
    public Integer getSalary()
    {
        return salary;
    }
    
    public void setSalary(Integer salary)
    {
        this.salary=salary;
    }
    
    public static List<List<String>> getAllEmployees(OracleDBMS con,Integer managerId)
    {
        String sql="SELECT FIRSTNAME||' '||LASTNAME NAME,DOJ,CNTNO,SALARY " +
                "FROM EMPLOYEE WHERE MNGID=?";
        List<List<String>> resultList=new ArrayList<>();
        try
        {
            PreparedStatement pst=con.getConnection().prepareStatement(sql);
            pst.setString(1,String.valueOf(managerId));
            ResultSet rs=pst.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            
            while(rs.next())
            {
                List<String> row=new ArrayList<>();
                row.add(rs.getString("Name"));
                row.add(rs.getString("DoJ"));
                row.add(rs.getString("CntNo"));
                row.add(rs.getString("Salary"));
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
}
