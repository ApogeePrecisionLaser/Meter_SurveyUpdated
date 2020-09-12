/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;



import com.survey.tableClasses.LogErrorBean;
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
public class LogErrorModel
{
 private Connection connection;
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
     public void setConnection() {
        try {
            Class.forName(driverClass);
           // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("ContacterTypeModel setConnection() Error: " + e);
        }
    }
public List<String> getDataJuction(String q)
    {
    List<String> list=new ArrayList<String>();
    String search= "SELECT switching_point_name "
     + " FROM switching_point_detail  order by switching_point_name";
     try
     {
     PreparedStatement pstm=connection.prepareStatement(search);
     ResultSet rs=pstm.executeQuery();
     q=q.trim();
     int count=0;
     while(rs.next())
     {
         String juc_name=rs.getString("switching_point_name");
         if(juc_name.toUpperCase().startsWith(q.toUpperCase()))
         {
             list.add(juc_name);
             count++;
         }
     }
   if(count==0)
   {
       list.add("Sorry,No Data Exit!!!");
   }
     }
     catch(Exception e)
     {
         System.out.println("Error : -" +e);
     }
    return list;
   }
public List<String> getDataErrorMsg(String q)
    {
    List<String> list=new ArrayList<String>();
    String search= "SELECT message FROM error_message where active='Y' order by message";
     try
     {
     PreparedStatement pstm=connection.prepareStatement(search);
     ResultSet rs=pstm.executeQuery();
     q=q.trim();
     int count=0;
     while(rs.next())
     {
         String err_message=rs.getString("message");
         if(err_message.toUpperCase().startsWith(q.toUpperCase()))
         {
             list.add(err_message);
             count++;
         }
     }
   if(count==0)
   {
       list.add("Sorry,Data Not Exit!!!");
   }
     }
     catch(Exception e)
     {
         System.out.println("Error : -" +e);
     }
    return list;
   }
public int getNoOfRows(String searchReadingDateFrom, String searchReadingDateTo,String searchJuction,String searchMesssage,String searchReadingTimeFrom, String searchReadingTimeTo,String searchDate) {

//     String rowcount="SELECT Count(*) as count"
//     +  " FROM switching_point_detail spd, junction j, error_log el, error_message em"
//     + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id AND spd.active = 'Y'"
//     + " AND str_to_date(DATE_FORMAT(el.timestamp,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date(IF('"+searchReadingDateFrom+"'='',DATE_FORMAT(el.timestamp,'%d-%m-%Y'),'"+searchReadingDateFrom+"'),'%d-%m-%Y') AND str_to_date('"+searchReadingDateTo+"','%d-%m-%Y') "
//     + " AND if('"+searchJuction+"'='',switching_point_name like '%%',switching_point_name=?) "
//     + " AND if('"+searchMesssage+"'='',message like '%%',message=?) "
//     + " AND if('"+searchReadingTimeFrom+"'='', DATE_FORMAT(el.timestamp, '%H:%i') LIKE '%%', DATE_FORMAT(el.timestamp, '%H:%i') BETWEEN '"+searchReadingTimeFrom+"' AND '"+searchReadingTimeTo+"') "
//     + " AND if('"+searchDate+"'='',el.timestamp LIKE '%%',DATE_FORMAT(el.timestamp,'%d-%m-%Y') ='"+searchDate+"') "
//     + " AND el.junction_id = j.junction_id AND j.active = 'Y' AND el.error_id = em.error_message_id order by el.timestamp";

      String rowcount="Select Count(*) as count "
              + "FROM  error_log el left join (junction j,switching_point_detail spd)"
              + "  on el.junction_id=j.junction_id and j.active='Y' AND spd.switching_point_detail_id=j.switching_point_detail_id and spd.active='Y',"
              + " error_message em  where el.error_id = em.error_message_id "
              + "  AND str_to_date(DATE_FORMAT(el.timestamp,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date(IF('"+searchReadingDateFrom+"'='',DATE_FORMAT(el.timestamp,'%d-%m-%Y'),'"+searchReadingDateFrom+"'),'%d-%m-%Y')"
              + " AND str_to_date('"+searchReadingDateTo+"','%d-%m-%Y')"
              + " AND if('"+searchReadingTimeFrom+"'='', DATE_FORMAT(el.timestamp, '%H:%i') LIKE '%%', DATE_FORMAT(el.timestamp, '%H:%i') BETWEEN '"+searchReadingTimeFrom+"' AND '"+searchReadingTimeTo+"') "
              + " AND if('"+searchDate+"'='',el.timestamp LIKE '%%',DATE_FORMAT(el.timestamp,'%d-%m-%Y') ='"+searchDate+"')"
              + " AND if('"+searchJuction+"'='',switching_point_name like '%%' OR switching_point_name is null,switching_point_name=?)"
              + " AND if('"+searchMesssage+"'='',message like '%%',message=?) "
              + " order by el.timestamp DESC ";
             // + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;"
    int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(rowcount);
            stmt.setString(1,searchJuction);
            stmt.setString(2, searchMesssage);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows State type model" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public List<LogErrorBean> showAllData(int lowerLimit,int noOfRowsToDisplay,String searchJuction,String searchErrorMessage,String searchReadingDateFrom,String searchReadingDateTo,String searchReadingTimeFrom, String searchReadingTimeTo,String searchDate)
    {
      List<LogErrorBean> list=new ArrayList<LogErrorBean>();
//       String showData="SELECT spd.switching_point_detail_id, spd.switching_point_name,j.junction_id,j.program_version_no,el.received_data, el.sent_data, el.timestamp AS timestamp, em.message"
//     + " FROM switching_point_detail spd, junction j, error_log el, error_message em"
//     + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id AND spd.active = 'Y'"
//     + " AND el.junction_id = j.junction_id AND j.active = 'Y' AND el.error_id = em.error_message_id "
//     + "  AND str_to_date(DATE_FORMAT(el.timestamp,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date(IF('"+searchReadingDateFrom+"'='',DATE_FORMAT(el.timestamp,'%d-%m-%Y'),'"+searchReadingDateFrom+"'),'%d-%m-%Y')"
//     + " AND str_to_date('"+searchReadingDateTo+"','%d-%m-%Y')"
//     + " AND if('"+searchReadingTimeFrom+"'='', DATE_FORMAT(el.timestamp, '%H:%i') LIKE '%%', DATE_FORMAT(el.timestamp, '%H:%i') BETWEEN '"+searchReadingTimeFrom+"' AND '"+searchReadingTimeTo+"') "
//
//     + " AND if('"+searchDate+"'='',el.timestamp LIKE '%%',DATE_FORMAT(el.timestamp,'%d-%m-%Y') ='"+searchDate+"')"
//     + " AND if('"+searchJuction+"'='',switching_point_name like '%%',switching_point_name=?) "
//     + " AND if('"+searchErrorMessage+"'='',message like '%%',message=?) "
//     + " order by el.timestamp DESC "
//     + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

      String showData="SELECT spd.switching_point_detail_id, spd.switching_point_name,j.junction_id,"
              + "j.program_version_no,el.received_data, el.sent_data, el.timestamp AS timestamp,"
              + " em.message "
              + "FROM  error_log el left join (junction j,switching_point_detail spd)"
              + "  on el.junction_id=j.junction_id and j.active='Y' AND spd.switching_point_detail_id=j.switching_point_detail_id and spd.active='Y',"
              + " error_message em  where el.error_id = em.error_message_id "
              + "  AND str_to_date(DATE_FORMAT(el.timestamp,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date(IF('"+searchReadingDateFrom+"'='',DATE_FORMAT(el.timestamp,'%d-%m-%Y'),'"+searchReadingDateFrom+"'),'%d-%m-%Y')"
              + " AND str_to_date('"+searchReadingDateTo+"','%d-%m-%Y')"
              + " AND if('"+searchReadingTimeFrom+"'='', DATE_FORMAT(el.timestamp, '%H:%i') LIKE '%%', DATE_FORMAT(el.timestamp, '%H:%i') BETWEEN '"+searchReadingTimeFrom+"' AND '"+searchReadingTimeTo+"') "
              + " AND if('"+searchDate+"'='',el.timestamp LIKE '%%',DATE_FORMAT(el.timestamp,'%d-%m-%Y') ='"+searchDate+"')"
              + " AND if('"+searchJuction+"'='',switching_point_name like '%%' OR switching_point_name is null,switching_point_name=?)"
              + " AND if('"+searchErrorMessage+"'='',message like '%%',message=?) "
              + " order by el.timestamp DESC "
              + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

      try
       {   int count=0;String msname="";
           PreparedStatement pstm=connection.prepareStatement(showData);
           pstm.setString(1, searchJuction);
           pstm.setString(2, searchErrorMessage);
           ResultSet rs=pstm.executeQuery();
           while(rs.next())
           {
               LogErrorBean obj=new LogErrorBean();
               obj.setJunction_id(rs.getInt("junction_id"));
               obj.setRecieved_data(rs.getString("received_data"));
               obj.setSent_data(rs.getString("sent_data"));
               obj.setTimestamp(rs.getString("timestamp"));
               obj.setSwitching_name(rs.getString("switching_point_name"));
               obj.setPro_ver_no(rs.getInt("program_version_no"));
               obj.setError_mess(rs.getString("message"));
               if(searchErrorMessage.equals("imei_no not matched")||searchErrorMessage.equals("energyMeter_no not matched"))
               {
               if((rs.getString("message")).equals("imei_no not matched")||(rs.getString("message")).equals("energyMeter_no not matched"))
               {
                 LogErrorBean.setStatusofMessage("true");
               }
               }
                else{LogErrorBean.setStatusofMessage("false");}
               if(count==0)
                 {
                   msname=rs.getString("message");
                   if(msname!=null)
                   {
                   obj.setStatus("true");
                    count++;
                   }
               }else if(rs.getString("message")==null||count==1 && msname.equals(rs.getString("message")))
               {
                   obj.setStatus("false");
               }else{
                   obj.setStatus("true");}
         list.add(obj);
           }
       }
       catch(Exception e)
       {
           System.out.println("Error :- "+e);
       }
        return list;
    }
 public int revisedRecord(LogErrorBean leb) {
  int rowsAffected = 0, rowsAffected1 = 0;
  String j_id[]=leb.getJunction_id1();
  String emno[]=leb.getEmno();
  String imeno[]=leb.getImeino();
     String query0 = " SELECT max(program_version_no) program_version_no,switching_point_detail_id,switching_rev_no,controller_model,mobile_no,sim_no,imei_no,panel_file_no,"
                    +" panel_transferred_status,sanctioned_load,connected_load,phase1_healthy_voltage,energy_meter_no,"
                    +" phase2_healthy_voltage,phase3_healthy_voltage,phase1_healthy_current,phase2_healthy_current,phase3_healthy_current,created_date,remark from junction where junction_id=? and active='Y'";
     String query1 =" INSERT INTO junction (junction_id, program_version_no, switching_point_detail_id, switching_rev_no, controller_model, mobile_no, sim_no, imei_no, panel_file_no,panel_transferred_status,energy_meter_no, "
                    +" sanctioned_load, connected_load, phase1_healthy_voltage, phase2_healthy_voltage, phase3_healthy_voltage, phase1_healthy_current, phase2_healthy_current, phase3_healthy_current, remark,active) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     String query2 =" update junction set active=? where junction_id=? and active='Y'and program_version_no=?";
       try {             
             int p_v_number=0;
             PreparedStatement pstmt = connection.prepareStatement(query0);
          for(int i=0;i<j_id.length;i++)
              {              
             pstmt.setInt(1,Integer.parseInt(j_id[i]));
             ResultSet rs=pstmt.executeQuery();
             PreparedStatement pstmt1 = connection.prepareStatement(query1);
             while(rs.next())
                 { p_v_number= rs.getInt("program_version_no");
                    pstmt1.setInt(1, Integer.parseInt(j_id[i]));
                    pstmt1.setInt(2, (p_v_number+1));
                    pstmt1.setInt(3, rs.getInt("switching_point_detail_id"));
                    pstmt1.setInt(4, rs.getInt("switching_rev_no"));
                    pstmt1.setString(5, rs.getString("controller_model"));
                    pstmt1.setString(6, rs.getString("mobile_no"));
                    pstmt1.setString(7,rs.getString("sim_no"));
                    pstmt1.setString(8,imeno[i]);
                    pstmt1.setString(9, rs.getString("panel_file_no"));
                    pstmt1.setString(10,rs.getString("panel_transferred_status"));
                    pstmt1.setString(11, emno[i]);
                    pstmt1.setDouble(12, rs.getDouble("sanctioned_load"));
                    pstmt1.setDouble(13, rs.getDouble("connected_load"));
                    pstmt1.setDouble(14, rs.getDouble("phase1_healthy_voltage"));
                    pstmt1.setDouble(15, rs.getDouble("phase2_healthy_voltage"));
                    pstmt1.setDouble(16,rs.getDouble("phase3_healthy_voltage"));
                    pstmt1.setDouble(17, rs.getDouble("phase1_healthy_current"));
                    pstmt1.setDouble(18, rs.getDouble("phase2_healthy_current"));
                    pstmt1.setDouble(19, rs.getDouble("phase3_healthy_current"));
                    pstmt1.setString(20, rs.getString("remark"));
                    pstmt1.setString(21, "Y");
                    rowsAffected1 = pstmt1.executeUpdate();               
             }
           }
             if(rowsAffected1>0)
             {
             PreparedStatement pstSpd = connection.prepareStatement(query2);
           for(int i=0;i<j_id.length;i++)
         {
         pstSpd.setString(1,"N");
         pstSpd.setInt(2,Integer.parseInt(j_id[i]));
         pstSpd.setInt(3,p_v_number);
         rowsAffected = pstSpd.executeUpdate();
         }
             if(rowsAffected>0)
             {
                 System.out.println("Successfully Updated!!!!!");
             }
             }
     } catch (Exception e) {
           System.out.println("Error: Record inserting: " + e);
       }
        if (rowsAffected1 > 0) 
        {           
            message = "Record Revised successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Revised");
        } else {
            message = "Record is Not saved......";
            msgBgColor = COLOR_OK;
            System.out.println("not Revised");
        }

      return rowsAffected;
  }
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

   

}
