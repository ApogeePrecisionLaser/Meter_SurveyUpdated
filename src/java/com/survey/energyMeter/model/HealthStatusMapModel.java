/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.model;

import com.survey.energyMeter.tableClasses.HealthStatusMapBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author jpss
 */
public class HealthStatusMapModel {
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
            Class.forName("com.mysql.jdbc.Driver");
            connection= (Connection) DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8",db_username,db_password);
           // System.out.println("connected - "+connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

       public byte[] generateRecordList(String jrxmlFilePath,List list, String searchSwitchingPointName, String searchHealthStatus, String searchReadingDateFrom, String searchReadingDateTo,
               String searchReadingTimeFrom, String searchReadingTimeTo,String searchP1HealthStatus,String searchP2HealthStatus,String searchP3HealthStatus,String searchP1vcHealthStatus,String searchP2vcHealthStatus,String searchP3vcHealthStatus) {
                byte[] reportInbytes = null;
                HashMap mymap = new HashMap();
                String searchReadingDate = "";
                String searchReadingTime = "";
                try {
                    if(!searchReadingDateFrom.isEmpty() && searchReadingDateFrom.equals(searchReadingDateTo)){
                        searchReadingDate = searchReadingDateFrom;
                    }else{
                        searchReadingDate = searchReadingDateFrom.equals("")?"All":searchReadingDateFrom+" To "+searchReadingDateTo;
                    }
                    if(!searchReadingTimeFrom.isEmpty() && searchReadingTimeFrom.equals(searchReadingTimeTo)){
                        searchReadingTime = searchReadingTimeFrom;
                    }else{
                        searchReadingTime = searchReadingTimeFrom.equals("")?"All":searchReadingTimeFrom+" To "+searchReadingTimeTo;
                    }
                    mymap.put("searchSwitchingPointName", searchSwitchingPointName.equals("")?"All":searchSwitchingPointName);
                    mymap.put("searchHealthStatus", searchHealthStatus.equals("")?"All":searchHealthStatus);
                    mymap.put("searchDate", searchReadingDate);
                    mymap.put("searchTime", searchReadingTime);
                    mymap.put("searchP1HealthStatus", searchP1HealthStatus.equals("")?"All":searchP1HealthStatus);
                    mymap.put("searchP2HealthStatus", searchP2HealthStatus.equals("")?"All":searchP2HealthStatus);
                    mymap.put("searchP3HealthStatus", searchP3HealthStatus.equals("")?"All":searchP3HealthStatus);
                    mymap.put("searchP3vcHealthStatus", searchP3vcHealthStatus.equals("")?"All":searchP3vcHealthStatus);
                    mymap.put("searchP2vcHealthStatus", searchP2vcHealthStatus.equals("")?"All":searchP2vcHealthStatus);
                    mymap.put("searchP1vcHealthStatus", searchP1vcHealthStatus.equals("")?"All":searchP1vcHealthStatus);
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, jrBean);
                } catch (Exception e) {
                    System.out.println("HealthStatusMapModel generatReport() JRException: " + e);
                }
                return reportInbytes;
            }    

    public List<HealthStatusMapBean> showData(int lowerLimit, int noOfRowsToDisplay , String searchReadingDateFrom, String searchReadingDateTo, String searchReadingTimeFrom, String searchReadingTimeTo, 
            String searchSwitchingPointName, String searchHealthStatus,String searchP1HealthStatus,String searchP2HealthStatus,String searchP3HealthStatus,String searchP1vcHealthStatus,String searchP2vcHealthStatus,String searchP3vcHealthStatus) {
        List<HealthStatusMapBean> list = new ArrayList<HealthStatusMapBean>();
        String addLimit = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if(lowerLimit == -1)
            addLimit = "";

        String query = " SELECT hm.health_status_map_id, hm.health_status_id, hm.junction_id, hm.reading_date,hm.program_version_no, hm.reading_time,"
                + " hm.phase1_status_id, hm.phase2_status_id, hm.phase3_status_id, phase1_vc_status_id, phase2_vc_status_id, phase3_vc_status_id, s.switching_point_name, s.ph, h.health_status, "
                + " hp1.health_status AS phase1_status, hp2.health_status AS phase2_status, hp3.health_status AS phase3_status, "
                + " hpv1.health_status AS phase1_vc_status, hpv2.health_status AS phase2_vc_status, hpv3.health_status AS phase3_vc_status "
                + " FROM health_status_map hm, switching_point_detail s, health_status h, junction j, "
                + " health_status hp1, health_status hp2, health_status hp3, health_status hpv1, health_status hpv2, health_status hpv3"
		+ " WHERE s.active='Y' AND s.switching_point_detail_id=j.switching_point_detail_id  AND j.active='Y' "
                + " AND j.junction_id=hm.junction_id AND h.health_status_id=hm.health_status_id "
                + " AND hp1.health_status_id=hm.phase1_status_id AND hp2.health_status_id=hm.phase2_status_id AND hp3.health_status_id=hm.phase3_status_id "
                + " AND hpv1.health_status_id=hm.phase1_vc_status_id AND hpv2.health_status_id=hm.phase2_vc_status_id AND hpv3.health_status_id=hm.phase3_vc_status_id "
                + " AND IF('"+searchReadingDateFrom+"'='',str_to_date(DATE_FORMAT(hm.reading_date,'%d-%m-%Y'),'%d-%m-%Y') LIKE '%%',(str_to_date(DATE_FORMAT(hm.reading_date,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date('"+searchReadingDateFrom+"','%d-%m-%Y') AND str_to_date('"+searchReadingDateTo+"','%d-%m-%Y')))"
                + " AND if('"+searchReadingTimeFrom+"'='', DATE_FORMAT(hm.reading_time, '%h:%m') LIKE '%%', hm.reading_time BETWEEN '"+searchReadingTimeFrom+"' AND '"+searchReadingTimeTo+"')"
                + " AND IF('"+searchHealthStatus+"' = '',h.health_status LIKE '%%',h.health_status ='"+searchHealthStatus+"')"
                + " AND IF('"+searchP1HealthStatus+"' = '',hp1.health_status LIKE '%%',hp1.health_status ='"+searchP1HealthStatus+"')"
                + " AND IF('"+searchP2HealthStatus+"' = '',hp2.health_status LIKE '%%',hp2.health_status ='"+searchP2HealthStatus+"')"
                + " AND IF('"+searchP3HealthStatus+"' = '',hp3.health_status LIKE '%%',hp3.health_status ='"+searchP3HealthStatus+"')"
                + " AND IF('"+searchP1vcHealthStatus+"' = '',hpv1.health_status LIKE '%%',hpv1.health_status ='"+searchP1vcHealthStatus+"')"
                + " AND IF('"+searchP2vcHealthStatus+"' = '',hpv2.health_status LIKE '%%',hpv2.health_status ='"+searchP2vcHealthStatus+"')"
                + " AND IF('"+searchP3vcHealthStatus+"' = '',hpv3.health_status LIKE '%%',hpv3.health_status ='"+searchP3vcHealthStatus+"')"
                + " AND IF('"+searchSwitchingPointName+"' = '', s.switching_point_name LIKE '%%', s.switching_point_name='"+searchSwitchingPointName+"')"
                + " ORDER BY health_status_map_id DESC " + addLimit;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                HealthStatusMapBean healthStatusMapBean = new HealthStatusMapBean();
                healthStatusMapBean.setHealth_status_map_id(rset.getInt("health_status_map_id"));
                healthStatusMapBean.setHealth_status_id(rset.getInt("health_status_id"));
                healthStatusMapBean.setHealth_status(rset.getString("health_status"));
                healthStatusMapBean.setSwitching_point_detail_id(rset.getInt("junction_id"));
                healthStatusMapBean.setSwitching_rev_no(rset.getInt("program_version_no"));
                healthStatusMapBean.setSwitching_point_name(rset.getString("switching_point_name"));
                healthStatusMapBean.setReading_date(rset.getString("reading_date"));
                healthStatusMapBean.setReading_time(rset.getString("reading_time"));
                healthStatusMapBean.setPhase(rset.getInt("ph"));
                healthStatusMapBean.setPhase1_status_id(rset.getInt("phase1_status_id"));
                healthStatusMapBean.setPhase1_status(rset.getString("phase1_status"));
                healthStatusMapBean.setPhase2_status_id(rset.getInt("phase2_status_id"));
                healthStatusMapBean.setPhase2_status(rset.getString("phase2_status"));
                healthStatusMapBean.setPhase3_status_id(rset.getInt("phase3_status_id"));
                healthStatusMapBean.setPhase3_status(rset.getString("phase3_status"));
                healthStatusMapBean.setPhase1_vc_status_id(rset.getInt("phase1_vc_status_id"));
                healthStatusMapBean.setPhase1_vc_status(rset.getString("phase1_vc_status"));
                healthStatusMapBean.setPhase2_vc_status_id(rset.getInt("phase2_vc_status_id"));
                healthStatusMapBean.setPhase2_vc_status(rset.getString("phase2_vc_status"));
                healthStatusMapBean.setPhase3_vc_status_id(rset.getInt("phase3_vc_status_id"));
                healthStatusMapBean.setPhase3_vc_status(rset.getString("phase3_vc_status"));
                list.add(healthStatusMapBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
        public List<HealthStatusMapBean> showAllData() {
        List<HealthStatusMapBean> list = new ArrayList<HealthStatusMapBean>();
         String query = "  SELECT hm.health_status_map_id, hm.health_status_id, hm.junction_id, hm.reading_date,hm.program_version_no, hm.reading_time,"
                + " hm.phase1_status_id, hm.phase2_status_id, hm.phase3_status_id, phase1_vc_status_id, phase2_vc_status_id, phase3_vc_status_id, s.switching_point_name, s.ph, h.health_status, "
                + " hp1.health_status AS phase1_status, hp2.health_status AS phase2_status, hp3.health_status AS phase3_status, "
                + " hpv1.health_status AS phase1_vc_status, hpv2.health_status AS phase2_vc_status, hpv3.health_status AS phase3_vc_status "
                + " FROM health_status_map hm, switching_point_detail s, health_status h, junction j, "
                + " health_status hp1, health_status hp2, health_status hp3, health_status hpv1, health_status hpv2, health_status hpv3"
		+ " WHERE s.active='Y' AND s.switching_point_detail_id=j.switching_point_detail_id "
                + " AND j.junction_id=hm.junction_id AND h.health_status_id=hm.health_status_id "
                + " AND hp1.health_status_id=hm.phase1_status_id AND hp2.health_status_id=hm.phase2_status_id AND hp3.health_status_id=hm.phase3_status_id "
                + " AND hpv1.health_status_id=hm.phase1_vc_status_id AND hpv2.health_status_id=hm.phase2_vc_status_id AND hpv3.health_status_id=hm.phase3_vc_status_id ";
                //+ " WHERE IF('" + searchHealthStatus + "' = '', health_status LIKE '%%', health_status=?) "
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                HealthStatusMapBean healthStatusMapBean = new HealthStatusMapBean();
                healthStatusMapBean.setHealth_status_map_id(rset.getInt("health_status_map_id"));
                healthStatusMapBean.setHealth_status_id(rset.getInt("health_status_id"));
                healthStatusMapBean.setHealth_status(rset.getString("health_status"));
                healthStatusMapBean.setSwitching_point_detail_id(rset.getInt("junction_id"));
                healthStatusMapBean.setSwitching_rev_no(rset.getInt("program_version_no"));
                healthStatusMapBean.setSwitching_point_name(rset.getString("switching_point_name"));
                healthStatusMapBean.setReading_date(rset.getString("reading_date"));
                healthStatusMapBean.setReading_time(rset.getString("reading_time"));
                healthStatusMapBean.setPhase(rset.getInt("ph"));
                healthStatusMapBean.setPhase1_status_id(rset.getInt("phase1_status_id"));
                healthStatusMapBean.setPhase1_status(rset.getString("phase1_status"));
                healthStatusMapBean.setPhase2_status_id(rset.getInt("phase2_status_id"));
                healthStatusMapBean.setPhase2_status(rset.getString("phase2_status"));
                healthStatusMapBean.setPhase3_status_id(rset.getInt("phase3_status_id"));
                healthStatusMapBean.setPhase3_status(rset.getString("phase3_status"));
                healthStatusMapBean.setPhase1_vc_status_id(rset.getInt("phase1_vc_status_id"));
                healthStatusMapBean.setPhase1_vc_status(rset.getString("phase1_vc_status"));
                healthStatusMapBean.setPhase2_vc_status_id(rset.getInt("phase2_vc_status_id"));
                healthStatusMapBean.setPhase2_vc_status(rset.getString("phase2_vc_status"));
                healthStatusMapBean.setPhase3_vc_status_id(rset.getInt("phase3_vc_status_id"));
                healthStatusMapBean.setPhase3_vc_status(rset.getString("phase3_vc_status"));
                list.add(healthStatusMapBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchReadingDateFrom, String searchReadingDateTo, String searchReadingTimeFrom, String searchReadingTimeTo, String searchSwitchingPointName, 
            String searchHealthStatus,String searchP1HealthStatus,String searchP2HealthStatus,String searchP3HealthStatus,String searchP1vcHealthStatus,String searchP2vcHealthStatus,String searchP3vcHealthStatus) {
        String query = " SELECT count(*) "
                + " FROM health_status_map hm, switching_point_detail s, health_status h, junction j, "
                + " health_status hp1, health_status hp2, health_status hp3, health_status hpv1, health_status hpv2, health_status hpv3"
		+ " WHERE s.active='Y' AND s.switching_point_detail_id=j.switching_point_detail_id  AND j.active='Y' "
                + " AND j.junction_id=hm.junction_id AND h.health_status_id=hm.health_status_id "
                + " AND hp1.health_status_id=hm.phase1_status_id AND hp2.health_status_id=hm.phase2_status_id AND hp3.health_status_id=hm.phase3_status_id "
                + " AND hpv1.health_status_id=hm.phase1_vc_status_id AND hpv2.health_status_id=hm.phase2_vc_status_id AND hpv3.health_status_id=hm.phase3_vc_status_id "
                + " AND IF('"+searchReadingDateFrom+"'='',str_to_date(DATE_FORMAT(hm.reading_date,'%d-%m-%Y'),'%d-%m-%Y') LIKE '%%',(str_to_date(DATE_FORMAT(hm.reading_date,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date('"+searchReadingDateFrom+"','%d-%m-%Y') AND str_to_date('"+searchReadingDateTo+"','%d-%m-%Y')))"
                + " AND if('"+searchReadingTimeFrom+"'='', DATE_FORMAT(hm.reading_time, '%h:%m') LIKE '%%', hm.reading_time BETWEEN '"+searchReadingTimeFrom+"' AND '"+searchReadingTimeTo+"')"
                + " AND IF('"+searchHealthStatus+"' = '',h.health_status LIKE '%%',h.health_status ='"+searchHealthStatus+"')"
                + " AND IF('"+searchP1HealthStatus+"' = '',hp1.health_status LIKE '%%',hp1.health_status ='"+searchP1HealthStatus+"')"
                + " AND IF('"+searchP2HealthStatus+"' = '',hp2.health_status LIKE '%%',hp2.health_status ='"+searchP2HealthStatus+"')"
                + " AND IF('"+searchP3HealthStatus+"' = '',hp3.health_status LIKE '%%',hp3.health_status ='"+searchP3HealthStatus+"')"
                + " AND IF('"+searchP1vcHealthStatus+"' = '',hpv1.health_status LIKE '%%',hpv1.health_status ='"+searchP1vcHealthStatus+"')"
                + " AND IF('"+searchP2vcHealthStatus+"' = '',hpv2.health_status LIKE '%%',hpv2.health_status ='"+searchP2vcHealthStatus+"')"
                + " AND IF('"+searchP3vcHealthStatus+"' = '',hpv3.health_status LIKE '%%',hpv3.health_status ='"+searchP3vcHealthStatus+"')"
                + " AND IF('"+searchSwitchingPointName+"' = '', s.switching_point_name LIKE '%%', s.switching_point_name='"+searchSwitchingPointName+"')";
               // + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows HealthStatusMapModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public List<String> getHealthStatus(String q) {
        List<String> list = new ArrayList<String>();
        String query = " Select h.health_status FROM health_status h"
			+ " ORDER BY h.health_status;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String health_status = rset.getString("health_status");
                if (health_status.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(health_status);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Health Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getHealthStatus ERROR inside HealthStatusMapModel - " + e);
        }
        return list;
    }

    public List<String> getSwitchingPointName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " Select s.switching_point_name FROM switching_point_detail s, junction j "
			+ " WHERE s.active='Y' AND s.switching_point_detail_id=j.switching_point_detail_id ; ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switching_point_name = rset.getString("switching_point_name");
                if (switching_point_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(switching_point_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Health Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getHealthStatus ERROR inside HealthStatusMapModel - " + e);
        }
        return list;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("HealthStatusMapModel closeConnection() Error: " + e);
        }
    }


}
