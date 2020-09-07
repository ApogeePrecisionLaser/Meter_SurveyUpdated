package com.survey.waterworks.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.survey.tableClasses.OHLevel;
import java.text.DateFormat;
import java.text.ParseException;

public class OHLevelModel {
    private Connection connection;
    private String driver,url,user,password;
    private String message,messageBGColor;
 private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
 public void setConnection() {
        try {
            Class.forName(driver);
           // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }
     public byte[] generateMapReport(String jrxmlFilePath,List<OHLevel> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in OHLevelModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }


    public List<OHLevel> showAllData(String waterTreatmentPlantName,String overHeadTankName)
    {
      ArrayList<OHLevel> list = new ArrayList<OHLevel>();
        String query =  "SELECT wtp.name,oht.name,le.level,le.date_time,le.remark, level_datetime "
                        +"FROM ohlevel AS le "
                        + "LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id "
                        + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                        + "WHERE IF('"+overHeadTankName+"'='',oht.name LIKE '%%',oht.name=?) "
                        + "AND IF('"+waterTreatmentPlantName+"'='',wtp.name LIKE '%%',wtp.name=?) "
                        +"ORDER BY oht.name ";
       try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, overHeadTankName);
            pstmt.setString(2, waterTreatmentPlantName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OHLevel ohLevelBean = new OHLevel();
                ohLevelBean.setWaterTreatmentPlantName(rset.getString(1));
                ohLevelBean.setOverHeadTankName(rset.getString(2));
                ohLevelBean.setLevel(rset.getFloat("level"));
                ohLevelBean.setDateTime(rset.getString("date_time"));
                ohLevelBean.setRemark(rset.getString("remark"));
                ohLevelBean.setLevel_datetime(rset.getString("level_datetime"));                

                list.add(ohLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in showAllRecrod -- OHLevelModel : " + e);
            message="Something going wrong";
            messageBGColor="red";
        }
        return list;
    }

     public List<String> getOverHeadTank(String q,String waterTreatmentPlantName) {
        List<String> list = new ArrayList<String>();
        String query =  "SELECT oht.name FROM overheadtank AS oht "
                        +"LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id =  wtp.watertreatmentplant_id "
                        +"WHERE IF('"+waterTreatmentPlantName+"'='',wtp.name LIKE '%%',wtp.name='"+waterTreatmentPlantName+"') "
                        +"ORDER BY oht.name";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String overHeadTank_type = rset.getString("name");
                if (overHeadTank_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(overHeadTank_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Overhead Tank exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error in getOverHeadTank - OHLevelModel - " + e);
            message="Something going wrong";
            messageBGColor="red";
        }
        return list;
    }

    public int getOverHeadTankId(String overHeanTankName) {
        String query =  "SELECT overheadtank_id FROM overheadtank WHERE name='"+overHeanTankName+"'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if(rset.next())
                return rset.getInt(1);
            else
                return 0;
            } catch (Exception e) {
            System.out.println("Error in getOverHeadTank - OHLevelModel - " + e);
            message="Something going wrong";
            messageBGColor="red";
            return 0;
        }
    }

    public List<String> getWaterTreatmentPlant(String q) {
        List<String> list = new ArrayList<String>();
        String query =  "SELECT name FROM watertreatmentplant ORDER BY name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String watertreatmentplant_type = rset.getString("name");
                if (watertreatmentplant_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(watertreatmentplant_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Water Treatment Plant exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error in getWaterTreatmentPlant - OHLevelModel - " + e);
            message="Something going wrong";
            messageBGColor="red";
        }
        return list;
    }

public void updateRecord(OHLevel ohLevelBean)
{
      String query = "UPDATE ohlevel SET overheadtank_id=?,level=?,remark=?, level_datetime=? WHERE ohlevel_id=?";
      try
      {
        PreparedStatement presta = connection.prepareStatement(query);
        presta.setInt(1, ohLevelBean.getOverHeadTankId());
        presta.setFloat(2, ohLevelBean.getLevel());
        presta.setString(3, ohLevelBean.getRemark());
        presta.setString(4, convertToSqlDate(ohLevelBean.getLevel_datetime()).toString() + " " + ohLevelBean.getHours() + ":" + ohLevelBean.getMinutes());
        presta.setInt(5, ohLevelBean.getOhLevelId());
        int i = presta.executeUpdate();
        if(i>0)
        {
            message="Record updated successfully";
            messageBGColor="yellow";
        }else
        {
            message="Record not updated successfully";
            messageBGColor="red";
        }
      }catch(Exception e)
      {
          System.out.println("Error in updateRecord - OHLevelModel : "+e);
          message="Something going wrong";
          messageBGColor="red";
      }
}
    public void deleteRecord(int ohLevelId)
    {
        PreparedStatement presta=null;
        try
        {
            presta = connection.prepareStatement("DELETE FROM ohlevel WHERE ohlevel_id=?");
            presta.setInt(1, ohLevelId);
            int i = presta.executeUpdate();
            if(i>0)
            {
                message="Record deleted successfully......";
                messageBGColor="yellow";
            }
            else
            {
                message="Record not deleted successfully......";
                messageBGColor="red";
            }
        }catch(Exception e)
        {
            System.out.println("Error in deleteRecord ---- OHLevelModel : "+e);
            message="Something going wrong";
            messageBGColor="red";
        }
    }
    public ArrayList<OHLevel> getAllRecords(int lowerLimit,int noOfRowsToDisplay,String waterTreatmentPlantName,String overHeadTankName)
    {
        ArrayList<OHLevel> list = new ArrayList<OHLevel>();
        String query =  "SELECT wtp.name,oht.name,le.ohlevel_id,le.level,le.date_time,le.remark, date_format(level_datetime, '%d-%m-%Y %h:%i') AS level_datetime "
                        +"FROM ohlevel AS le "
                        + "LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id "
                        + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                        + "WHERE IF('"+overHeadTankName+"'='',oht.name LIKE '%%',oht.name=?) "
                        + "AND IF('"+waterTreatmentPlantName+"'='',wtp.name LIKE '%%',wtp.name=?) "
                        +"ORDER BY oht.name "
                        +"LIMIT "+lowerLimit+","+noOfRowsToDisplay;
       try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, overHeadTankName);
            pstmt.setString(2, waterTreatmentPlantName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OHLevel ohLevelBean = new OHLevel();
                ohLevelBean.setWaterTreatmentPlantName(rset.getString(1));
                ohLevelBean.setOverHeadTankName(rset.getString(2));
                ohLevelBean.setOhLevelId(rset.getInt("ohlevel_id"));
                ohLevelBean.setLevel(rset.getFloat("level"));
                ohLevelBean.setDateTime(rset.getString("date_time"));
                ohLevelBean.setRemark(rset.getString("remark"));
                ohLevelBean.setLevel_datetime(rset.getString("level_datetime"));                

                list.add(ohLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OHLevelModel : " + e);
            message="Something going wrong";
            messageBGColor="red";
        }
        return list;
    }


    public int getTotalRowsInTable(String waterTreatmentName,String overHeadTankName)
    {
         String query = " SELECT count(*) "
                + " FROM ohlevel AS le "
                + "LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "WHERE IF('"+overHeadTankName+"'='',oht.name LIKE '%%',oht.name=?) "
                + "AND IF('"+waterTreatmentName+"'='',wtp.name LIKE '%%',wtp.name=?) ";
        int noOfRows = 0;
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, overHeadTankName);
            presta.setString(2, waterTreatmentName);
            ResultSet rs = presta.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - OHLevelModel : " + e);
            message="Something going wrong";
            messageBGColor="red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public void insertRecord(OHLevel ohLevelBean)
    {
        String query = "INSERT INTO ohlevel(overheadtank_id,level,remark, level_datetime) VALUES(?,?,?,?)";
        try
        {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setInt(1, ohLevelBean.getOverHeadTankId());
            presta.setFloat(2, ohLevelBean.getLevel());
            presta.setString(3, ohLevelBean.getRemark());
            presta.setString(4, convertToSqlDate(ohLevelBean.getLevel_datetime()).toString() + " " + ohLevelBean.getHours() + ":" + ohLevelBean.getMinutes());
            int i = presta.executeUpdate();
            if(i>=0)
            {
                message="Record inserted succesfully";
                messageBGColor="Yellow";
            }else
            {
                message="Record not inserted succesfully";
                messageBGColor="red";
            }
        }catch(Exception e)
        {
            System.out.println("Error in insertRecord - OHLevelModel : "+e);
            message="Something going wrong";
            messageBGColor="red";
        }
    }

    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public void closeConnection()
    {
        try
        {
            connection.close();
        }catch(Exception e)
        {
            System.out.println("Error in closeConnection -- OHLevelModel : "+e);
        }
    }
  public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
          System.out.println("PaymentStatusModel setConnection() Error: " + e);
        }
    }
    public Connection getConnection()
    {
        return connection;
    }
    public void setDriver(String driver)
    {
        this.driver = driver;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
    public void setUser(String user)
    {
        this.user = user;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getDriver()
    {
        return driver;
    }

    public String getUrl()
    {
        return url;
    }
    public String getUser()
    {
        return user;
    }
    public String getPassword()
    {
        return password;
    }
    public String getMessage()
    {
        return message;
    }
    public String getMessageBGColor()
    {
        return messageBGColor;
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

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

}