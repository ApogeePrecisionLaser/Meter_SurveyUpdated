/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;


import com.survey.tableClasses.ErrorMessageBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Navitus1
 */
public class ErrorMessageModel {
  private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }
     public void setConnection() {
        try {
            Class.forName(driverClass);
           // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("ErrorTypeModel setConnection() Error: " + e);
        }
    }
     public List<String> getAutoSearch(String q)
     {
         List<String> list=new ArrayList<String>();
         int count=0;
        try
        {
            q=q.trim();
          String searchMessage="select message from error_message order by message";
          PreparedStatement pstm=connection.prepareStatement(searchMessage);
          ResultSet  rs=pstm.executeQuery();              
          while(rs.next())
          {
              String errorMesssage=rs.getString("message");
              if(errorMesssage.toUpperCase().startsWith(q.toUpperCase()))
              {
               list.add(errorMesssage);
               count++;
             }
          }
        }
        catch(Exception e)
        {
            System.out.println("ErrorMessageModel getAutoSearch() Error : -" +e);
        }
      if(count==0)
      {
          list.add("No Such Data Exist !!!");
      }
         return list;
     }
     public int getNumberOfRow(String searchMessage)
    {
         int totalRow=0;
         try
         {
          String getRows="select count(*) count from error_message where if('"+searchMessage+"'='',message like '%%',message=?)";
          PreparedStatement pstm=connection.prepareStatement(getRows);
          pstm.setString(1, searchMessage);
          ResultSet rs = pstm.executeQuery();
          rs.next();
          totalRow=rs.getInt(1);
         }
         catch(Exception e)
         {
          System.out.println("ErrorMessageBean numberOfRow() Error : - " +e);
         }
         return totalRow;
     }
     public List<ErrorMessageBean> showAllData(int lowerLimit,int noOfRowsToDisplay,String searchMessage)
      {
         List<ErrorMessageBean> list=new ArrayList<ErrorMessageBean>();
     try
     {
      String showData="SELECT em.error_message_id,em.message,em.remark,em.timestamp,em.created_by,em.active from error_message em "
                      +" where if('"+searchMessage+"'='',message like '%%', message=?)"
                      +" order by em.timestamp LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
      PreparedStatement pstm=connection.prepareStatement(showData);
        pstm.setString(1, searchMessage);
      ResultSet rs=pstm.executeQuery();
      while(rs.next())
      {
          ErrorMessageBean  obj=new ErrorMessageBean();
          obj.setError_message_id(rs.getInt("error_message_id"));
          obj.setMessage(rs.getString("message"));
          obj.setRemark(rs.getString("remark"));
          obj.setTimestamp(rs.getString("timestamp"));
          obj.setCreatedBy(rs.getString("created_by"));
          obj.setActive(rs.getString("active"));
          list.add(obj);
      }   }
     catch(Exception e)
     {
      System.out.println("showAllData  Error : - " +e);  }
         return list;  
     }
   public void getNewInsert(ErrorMessageBean obj)
    {
   int value=0;
    String insert="insert into error_message(message,remark,created_by,active) values(?,?,?,?)";
    try
    {
    PreparedStatement pstm=connection.prepareStatement(insert);
   pstm.setString(1, obj.getMessage());
   pstm.setString(2,obj.getRemark());
  pstm.setString(3, obj.getCreatedBy());
  pstm.setString(4, obj.getActive());
  value=pstm.executeUpdate();

if(value>0)
{
    message="New Record Successfully Inserted!!";
}else{
        message="Not Inserted!!";}

        }
  catch(Exception e)
    {
      System.out.println("Error  :-"  +e);
  }

    }
    public void getUpdated(ErrorMessageBean obj)
    {
        String updated="update error_message set message=?,remark=?,created_by=?,active=? where error_message_id=?";
        try
        {
        PreparedStatement pstm=connection.prepareStatement(updated);
       pstm.setString(1, obj.getMessage());
       pstm.setString(2,obj.getRemark());
       pstm.setString(3, obj.getCreatedBy());
       pstm.setString(4, obj.getActive());
       pstm.setInt(5,obj.getError_message_id());
     int value=pstm.executeUpdate();
    if(value>0)
      {
    message="Data Successfully updated!!!";
     }else
       {
    message="Sorry,Information didn't update!!";
        }
        }catch(Exception e)
        {
             System.out.println(" getUdated Error :- "+e);
        }

    }


    public void getDelete(int id)
    {
        String delete="delete from error_message where error_message_id=?";
        try
        {
          PreparedStatement pstm1=connection.prepareStatement(delete);
          pstm1.setInt(1, id);
          int x=pstm1.executeUpdate();
          if(x>0)
          {
              message="Data Successfully deleted!!!!!!!!";
          }else
          {
              message="Data didn't delete!!";
          }
        }catch(Exception e)
        {
    System.out.println("Error :-" +e);
        }

    }
 public void getInsert(ErrorMessageBean obj)
     {
        int value=0;
    String insert="insert into error_message(message,remark,created_by,active) values(?,?,?,?)";
    try
     {
        for(int i=0;i<obj.getMessage1().length;i++)
        {
            if(obj.getMessage_id()[i].equals("1"))
            {
    PreparedStatement pstm=connection.prepareStatement(insert);
    pstm.setString(1, obj.getMessage1()[i]);
    pstm.setString(2,obj.getRemark1()[i]);
    pstm.setString(3, obj.getCretedBy1()[i]);
    pstm.setString(4, obj.getActive1()[i]);
    value=pstm.executeUpdate();
            }
        }
          if(value>0)
              {
                  message="Successfully Inserted!!";
          } else {
                   message="Not Inserted!!";}

      }
     catch(Exception e)
        {
      System.out.println("Error  :-"  +e);
       }

   }


}
