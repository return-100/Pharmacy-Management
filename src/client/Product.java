package client;

import javafx.beans.property.SimpleStringProperty;
import utilities.OracleDBMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class Product
{
    private SimpleStringProperty name;
    private Integer price;
    private SimpleStringProperty type;
    private SimpleStringProperty cmpName;
    
    Product()
    {
        price=0;
        name=new SimpleStringProperty();
        type=new SimpleStringProperty();
        cmpName=new SimpleStringProperty();
    }
    
    Product(String name,Integer price,String type,String cmpName)
    {
        this.name=new SimpleStringProperty(name);
        this.type=new SimpleStringProperty(type);
        this.cmpName=new SimpleStringProperty(cmpName);
        this.price=price;
        
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
    
    public Integer getPrice()
    {
        return price;
    }
    
    public void setPrice(Integer price)
    {
        this.price=price;
    }
    
    public String getType()
    {
        return type.get();
    }
    
    public SimpleStringProperty typeProperty()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type.set(type);
    }
    
    public String getCmpName()
    {
        return cmpName.get();
    }
    
    public SimpleStringProperty cmpNameProperty()
    {
        return cmpName;
    }
    
    public void setCmpName(String cmpName)
    {
        this.cmpName.set(cmpName);
    }
    
    public static List<List<String>> getAllProducts(OracleDBMS con)
    {
        String sql="SELECT P.NAME,P.PRICE,P.TYPE,C.NAME "+"FROM PRODUCT P JOIN COMPANY C ON P.CMPID=C.CMPID";
        List<List<String>> resultList=new ArrayList<>();
        try
        {
            PreparedStatement pst=con.getConnection().prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            
            while(rs.next())
            {
                List<String> row=new ArrayList<>();
                row.add(rs.getString("Name"));
                row.add(rs.getString("Price"));
                row.add(rs.getString("Type"));
                row.add(rs.getString("Name"));
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
