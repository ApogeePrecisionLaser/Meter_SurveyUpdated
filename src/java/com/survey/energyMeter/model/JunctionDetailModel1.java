/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.model;

import java.sql.PreparedStatement;
import com.survey.energyMeter.tableClasses.HealthStatusMapBean;
import com.survey.energyMeter.tableClasses.PhaseBean;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;




/**
 *
 * @author jpss
 */
public class JunctionDetailModel1 {

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
    DecimalFormat df =new DecimalFormat("0.00");

    public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            System.out.println("connected - " + connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public HealthStatusMapBean getJunctionHealthBitData(HealthStatusMapBean bean, int junction_id){
        EnergyMeterWebServiceModel energeyMeterModel = new EnergyMeterWebServiceModel();
        //HealthStatusMapBean bean = new HealthStatusMapBean();
        String query = "SELECT junction_phase_health_id, junction_id, phase1_bits, phase2_bits, phase3_bits, contactor_status_bits, command "
                + " FROM junction_phase_health "
                + " WHERE junction_id=" + junction_id + " ORDER BY junction_phase_health_id DESC LIMIT 0, 1 ";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                String phase1 = rs.getString("phase1_bits");
                String phase2 = rs.getString("phase2_bits");
                String phase3 = rs.getString("phase3_bits");
                bean.setPhase1Bean(new PhaseBean());
                bean.setPhase2Bean(new PhaseBean());
                bean.setPhase3Bean(new PhaseBean());
                energeyMeterModel.getHealthStatus_1(phase1.split(""), bean.getPhase1Bean());
                energeyMeterModel.getHealthStatus_1(phase2.split(""), bean.getPhase2Bean());
                energeyMeterModel.getHealthStatus_1(phase3.split(""), bean.getPhase3Bean());

                String[] contactor_status = rs.getString("contactor_status_bits").split("");
                int a = contactor_status.length - 1;
                if(a != -1)
                    if(contactor_status[a].equals("0"))
                        bean.setContactor_status("OFF");
                    else
                        bean.setContactor_status("ON");

                int command = rs.getInt("command");
                if(command == 0)
                    bean.setContactor_command("OFF");
                else
                    bean.setContactor_command("ON");
            }
        }catch(Exception ex){
            System.out.println("ERROR : in getJunctionHealthBitData() in JunctionDetailModel : " + ex);
        }
        return bean;
    }

    public JSONArray getAllRecords() //ServletContext ctx
    {
     //   ArrayList<OHLevel> list = new ArrayList<OHLevel>();
        JSONArray arrayObj = new JSONArray();
        byte[] twoByteData = new byte[2];
        String query = " select * from (SELECT wtp.name as name1,oht.name,le.ohlevel_id,le.overheadtank_id,"
                + " le.level_a,le.level_b,le.date_time,le.remark,"
                + " date_format(level_datetime, '%d-%m-%Y %h:%i') AS level_datetime,step,level1,level2,level3,level4,oht.capacity_height "
                + "FROM smart_meter_survey.ohlevel AS le "
                + "LEFT JOIN smart_meter_survey.overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id "
                + "LEFT JOIN smart_meter_survey.watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
//                + "WHERE IF('" + overHeadTankName + "'='',oht.name LIKE '%%',oht.name=?) "
//                + "AND IF('" + waterTreatmentPlantName + "'='',wtp.name LIKE '%%',wtp.name=?) "
                + "  ORDER BY ohlevel_id desc )  as a group by a.name order by name1,date_time desc";
               // + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
//            pstmt.setString(1, overHeadTankName);
//            pstmt.setString(2, waterTreatmentPlantName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {              
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("WaterTreatmentPlantName",rset.getString(1));
                jsonObj.put("OverHeadTankName",rset.getString(2));
                jsonObj.put("OhLevelId",rset.getInt("ohlevel_id"));
                jsonObj.put("Level_a",rset.getByte("level_a"));
                jsonObj.put("Level_b",rset.getString("level_b"));
                jsonObj.put("Value_of_ab",rset.getString("remark"));
                jsonObj.put("DateTime",rset.getString("date_time"));
                jsonObj.put("remark","");
                jsonObj.put("Level_datetime",rset.getString("date_time"));
                jsonObj.put("Step",rset.getByte("step"));
                jsonObj.put("Level1",rset.getByte("level1"));
                jsonObj.put("Level2",rset.getByte("level2"));
                jsonObj.put("Level3",rset.getByte("level3"));
                jsonObj.put("Level4",rset.getByte("level4"));               
                twoByteData[0] = rset.getByte("level3");
                twoByteData[1] = rset.getByte("level4");
                long voltage1 = (new BigInteger(twoByteData).longValue());
                jsonObj.put("Value_of_34",("" + voltage1).trim());
                jsonObj.put("Level_of_34",("" + df.format((voltage1 / (rset.getDouble("capacity_height") * 1000)) * 100)));              
//                String command = rset.getString("overheadtank_id");
//                String command_value = "",feedback="";
//                try {
//                    command_value =(String) ctx.getAttribute(command);
//                    feedback =(String) ctx.getAttribute("feedback_"+command);
//                    if(feedback==null)
//                        feedback=(""+0).trim();
//                    if(command_value==null)
//                        command_value=(""+0).trim();
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//                jsonObj.put("Command",command_value);
//                jsonObj.put("Feedback",feedback);
                arrayObj.add(jsonObj);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OHLevelModel : " + e);
            message = "Something going wrong";
          //  messageBGColor = "red";
        }
        return arrayObj;
    }

    public int getTotalRowsInTable(String waterTreatmentName, String overHeadTankName) {
        String query = "select count(*) from ( SELECT ohlevel_id "
                + " FROM ohlevel AS le "
                + "LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "WHERE IF('" + overHeadTankName + "'='',oht.name LIKE '%%',oht.name=?) "
                + "AND IF('" + waterTreatmentName + "'='',wtp.name LIKE '%%',wtp.name=?) group by oht.name ) as a";
        int noOfRows = 0;
        try {
            PreparedStatement presta = (PreparedStatement) connection.prepareStatement(query);
            presta.setString(1, overHeadTankName);
            presta.setString(2, waterTreatmentName);
            ResultSet rs = presta.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - OHLevelModel : " + e);
            message = "Something going wrong";
           // messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }



    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection con) {
        this.connection = con;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("JunctionModel closeConnection() Error: " + e);
        }
    }
}
