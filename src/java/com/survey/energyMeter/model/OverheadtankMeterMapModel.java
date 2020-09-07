/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.model;

import com.survey.energyMeter.tableClasses.OverheadtankMeterMapBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class OverheadtankMeterMapModel {
private  Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
       private String msgBgColor;

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
    private final String COLOR_OK = "yellow";
   private final String COLOR_ERROR = "red";


//       public void setConnection() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//           // connection= (Connection) DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8",db_username,db_password);
//            connection= (Connection) DriverManager.getConnection(connectionString,db_username,db_password);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }


    public List getOverheadTankName(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = "select o.name from smart_meter_survey.overheadtank o order by o.name ";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next())
            {
                list.add(rs.getString("name"));
                count++;
            }
            if (count == 0) {
                list.add("No such Overheadtank name exists.");
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR inside OverheadtankMeterMapModel getOverheadTankName method - " + e);
        }
        return list;
    }
    public List getMeterName(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = "select meter_name from meters "
                + " where active ='Y' ";
                //+ " order by meter_name ";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next())
            {
                list.add(rs.getString("meter_name"));
                count++;
            }
            if (count == 0) {
                list.add("No such Meter name exists.");
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR inside OverheadtankMeterMapModel getMeterName method - " + e);
        }
        return list;
    }
     public List getSearchOverheadTankName(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = "select name from smart_meter_survey.overheadtank o, overheadtank_meter_map ohtmmap "
                + " where o.overheadtank_id=ohtmmap.overheadtank_id " ;
                //+ " order by meter_name ";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next())
            {
                list.add(rs.getString("name"));
                count++;
            }
            if (count == 0) {
                list.add("No such Overhead Tank name exists.");
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR inside OverheadtankMeterMapModel getSearchOverheadTankName method - " + e);
        }
        return list;
    }
      public List getSearchMeterName(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = "select m.meter_name from meters m,overheadtank_meter_map ohtmmap"
                + " where m.meter_id=ohtmmap.meter_id "
                + " and m.active='Y' ";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next())
            {
                list.add(rs.getString("meter_name"));
                count++;
            }
            if (count == 0) {
                list.add("No such Meter name exists.");
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR inside OverheadtankMeterMapModel getSearchMeterName method - " + e);
        }
        return list;
    }
    public int getOverheadTank_id(String xyz){
        int overheadtank_id=0;

        String query = " select overheadtank_id from smart_meter_survey.overheadtank o "
                      +" where name='"+xyz+"'";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rs.next())
            {
                overheadtank_id=rs.getInt("overheadtank_id");
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR inside OverheadtankMeterMapModel getOverheadTank_id method - " + e);
        }
        return overheadtank_id;
    }
    public int getMeter_id(String xyz){
        int meter_id=0;
        String query = " select meter_id from meter_survey.meters m "
                      +" where meter_name='"+xyz+"'"
                      +" and active='Y'";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                meter_id=rs.getInt("meter_id");
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR inside OverheadtankMeterMapModel getMeter_id method - " + e);
        }
        return meter_id;
    }
    public List<OverheadtankMeterMapBean> ShowData(int lowerLimit, int noOfRowsToDisplay,String searchOverheadtank_Name,String searchMeter_Name)
    {
        List<OverheadtankMeterMapBean> list = new ArrayList<OverheadtankMeterMapBean>();
        String query = "select ohtmmap.overheadtank_meter_map_id,o.name,m.meter_name,ohtmmap.remark "
                      +" from smart_meter_survey.overheadtank o,meter_survey.meters m,meter_survey.overheadtank_meter_map ohtmmap "
                      +" where m.meter_id=ohtmmap.meter_id "
                      +" and o.overheadtank_id=ohtmmap.overheadtank_id "
                      +" and m.active='Y'"
                      + " and if('"+searchOverheadtank_Name+"'='', o.name LIKE '%%', o.name='"+searchOverheadtank_Name+"')"
                      + " and if('"+searchMeter_Name+"'='', m.meter_name LIKE '%%', m.meter_name='"+searchMeter_Name+"')";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                OverheadtankMeterMapBean itemNameBean=new OverheadtankMeterMapBean();
                itemNameBean.setOverheadtank_metermap_id(rs.getString("overheadtank_meter_map_id"));
                
                itemNameBean.setOverheadtank_name(rs.getString("name"));
                itemNameBean.setMeter_name(rs.getString("meter_name"));
                itemNameBean.setRemark(rs.getString("remark"));
               
                    list.add(itemNameBean);
            }
        } catch (Exception e)
        {
            System.out.println("ERROR inside OverheadtankMeterMapModel ShowData method - " + e);
        }
        return list;
       
    }
    public int getNoOfRows(String searchOverheadtank_Name,String searchMeter_Name)
    {
        int count=0;
       String query = "select count(*) "
                      +" from smart_meter_survey.overheadtank o,meter_survey.meters m,meter_survey.overheadtank_meter_map ohtmmap "
                      +" where m.meter_id=ohtmmap.meter_id "
                      +" and o.overheadtank_id=ohtmmap.overheadtank_id "
                      +" and m.active='Y'"
                     + " and if('"+searchOverheadtank_Name+"'='', o.name LIKE '%%', o.name='"+searchOverheadtank_Name+"')"
                      + " and if('"+searchMeter_Name+"'='', m.meter_name LIKE '%%', m.meter_name='"+searchMeter_Name+"')";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                count=rs.getInt(1);

            }
        } catch (Exception e) {
            System.out.println("ERROR inside OverheadtankMeterMapModel getNoOfRows method - " + e);
        }
        return count;
    }
    public boolean insertRecord(OverheadtankMeterMapBean itemNameBean) {
    boolean b=false;

    int overheadtank_id=getOverheadTank_id(itemNameBean.getOverheadtank_name());
    int meter_id=getMeter_id(itemNameBean.getMeter_name());

        String query = "insert into overheadtank_meter_map (overheadtank_id,meter_id,remark) values (?,?,?)";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1,overheadtank_id);
             ps.setInt(2,meter_id);
             ps.setString(3,itemNameBean.getRemark());
             int rowsAffected=ps.executeUpdate();
             if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        } catch (Exception e) {
             message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("ERROR inside OverheadtankMeterMapModel insertRecord method - " + e);
        }
        return b;
    }
    public boolean UpdateRecord(OverheadtankMeterMapBean itemNameBean)
    {
        int overheadtank_id=getOverheadTank_id(itemNameBean.getOverheadtank_name());
    int meter_id=getMeter_id(itemNameBean.getMeter_name());

        String query = "update overheadtank_meter_map set overheadtank_id="+overheadtank_id+",meter_id="+meter_id+",remark='"+itemNameBean.getRemark()+"' where overheadtank_meter_map_id="+itemNameBean.getOverheadtank_metermap_id();
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             int rowsAffected=ps.executeUpdate();
             if (rowsAffected > 0) {
            message = "Record Update successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        } catch (Exception e) {
             message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("ERROR inside OverheadtankMeterMapModel UpdateRecord method - " + e);
        }
        boolean b=false;
        return b;
    }
    public boolean deleteRecord(OverheadtankMeterMapBean itemNameBean) {
        boolean b=false;
        String query = "DELETE FROM overheadtank_meter_map where overheadtank_meter_map_id=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1,Integer.parseInt(itemNameBean.getOverheadtank_metermap_id()));
             int rowsAffected=ps.executeUpdate();
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }

        } catch (Exception e) {
             message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("ERROR inside OverheadtankMeterMapModel deleteRecord method - " + e);
        }
         return b;
    }


        public Connection getConnection() {
        return connection;
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
// public Connection getConnection() {
//        return connection;
//    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("HealthStatusMapModel closeConnection() Error: " + e);
        }
    }

}
