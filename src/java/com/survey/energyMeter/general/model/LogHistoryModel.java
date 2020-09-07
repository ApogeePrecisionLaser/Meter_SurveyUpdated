/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.general.model;


import com.survey.energyMeter.general.tableClasses.History;
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
public class LogHistoryModel {
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
            System.out.println("connected - "+connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

       public byte[] generateRecordList(String jrxmlFilePath,List list, String searchError_state, String searchLoginDate, String searchLogoutDate, String searchLoginTime, String searchLogoutTime) {
                byte[] reportInbytes = null;
                HashMap mymap = new HashMap();
                String searchReadingDate = "";
                String searchReadingTime = "";
                try {
                    if(!searchLoginDate.isEmpty() && searchLoginDate.equals(searchLogoutDate)){
                        searchReadingDate = searchLoginDate;
                    }else{
                        searchReadingDate = searchLoginDate.equals("")?"All":searchLoginDate+" To "+searchLogoutDate;
                    }
                    if(!searchLoginTime.isEmpty() && searchLoginTime.equals(searchLogoutTime)){
                        searchReadingTime = searchLoginTime;
                    }else{
                        searchReadingTime = searchLoginTime.equals("")?"All":searchLoginTime+" To "+searchLogoutTime;
                    }
                    mymap.put("searchSwitchingPointName", searchError_state.equals("")?"All":searchError_state);
                    mymap.put("searchDate", searchReadingDate);
                    mymap.put("searchTime", searchReadingTime);
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, jrBean);
                } catch (Exception e) {
                    System.out.println("LogHistoryModel generatReport() JRException: " + e);
                }
                return reportInbytes;
            }

    public List<History> showData(int lowerLimit, int noOfRowsToDisplay , String searchLoginDateFrom, String searchLoginDateTo, String searchLogoutDateFrom, String searchLogoutDateTo, String searchLoginTimeFrom, String searchLoginTimeTo,String searchLogoutTimeFrom,String searchLogoutTimeTo, String searchError_state) {
        List<History> list = new ArrayList<History>();
        String query = " SELECT j.junction_id, j.program_version_no, spd.switching_point_name, IF(isOnPole = 'Y' ,c.city_name, sc.city_name) AS city_name, l.ip_address, l.port, l.login_timestamp_date, l.login_timestamp_time, l.logout_timestamp_date, l.logout_timestamp_time, l.status, l.error_state, l.timestamp"
               + " FROM switching_point_detail spd LEFT JOIN( pole AS p,city AS c, ward AS w, area AS a) ON "
                + "(p.area_id = a.area_id AND a.ward_id = w.ward_id  AND w.city_id=c.city_id AND w.active='Active' AND isSwitchingPoint='Yes') "
                + ", junction j, log_history as l,city AS sc, ward AS sw, area AS sa "
                + " WHERE spd.switching_point_detail_id = p.switching_point_detail_id  AND spd.active = 'Y' AND p.active='Y' "
                + " AND spd.area_id = sa.area_id AND sa.ward_id = sw.ward_id AND sw.city_id=sc.city_id AND sw.active='Active' "
                + " AND spd.switching_point_detail_id=j.switching_point_detail_id AND j.active='Y'  AND j.junction_id=l.junction_id"
                + " AND IF('"+searchLoginDateFrom+"'!='' && '"+searchLoginDateTo+"'!='',"
                + " (str_to_date(DATE_FORMAT(l.login_timestamp_date,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date('"+searchLoginDateFrom+"','%d-%m-%Y') AND str_to_date('"+searchLoginDateTo+"','%d-%m-%Y')),"
                + " IF ('"+searchLoginDateFrom+"'='',str_to_date(DATE_FORMAT(l.login_timestamp_date,'%d-%m-%Y'),'%d-%m-%Y') LIKE '%%', l.login_timestamp_date=str_to_date('"+searchLoginDateFrom+"','%d-%m-%Y')))"
                + " AND IF('"+searchLogoutDateFrom+"'!='' && '"+searchLogoutDateTo+"'!='',"
                + " (str_to_date(DATE_FORMAT(l.logout_timestamp_date,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date('"+searchLogoutDateFrom+"','%d-%m-%Y') AND str_to_date('"+searchLogoutDateTo+"','%d-%m-%Y')),"
                + " IF ('"+searchLogoutDateFrom+"'='',str_to_date(DATE_FORMAT(l.logout_timestamp_date,'%d-%m-%Y'),'%d-%m-%Y') LIKE '%%', l.logout_timestamp_date=str_to_date('"+searchLogoutDateFrom+"','%d-%m-%Y')))"
                + " AND if('"+searchLoginTimeFrom+"'!='' && '"+searchLoginTimeTo+"'!='',"
                + " CAST(DATE_FORMAT(l.login_timestamp_time, '%h:%i')AS time) BETWEEN CAST('"+searchLoginTimeFrom+"' AS time)  AND CAST('"+searchLoginTimeTo+"' AS time),"
                + " If('"+searchLoginTimeFrom+"'='',DATE_FORMAT(l.login_timestamp_time, '%h:%i') LIKE '%%',CAST(DATE_FORMAT(l.login_timestamp_time, '%h:%i')AS time)=CAST('"+searchLoginTimeFrom+"' AS time)))"
                + " AND if('"+searchLogoutTimeFrom+"'!='' && '"+searchLogoutTimeTo+"'!='',"
                + " CAST(DATE_FORMAT(l.logout_timestamp_time, '%h:%i')AS time) BETWEEN CAST('"+searchLogoutTimeFrom+"' AS time)  AND CAST('"+searchLogoutTimeTo+"' AS time),"
                + " If('"+searchLogoutTimeFrom+"'='',DATE_FORMAT(l.logout_timestamp_time, '%h:%i') LIKE '%%',CAST(DATE_FORMAT(l.logout_timestamp_time, '%h:%i')AS time)=CAST('"+searchLogoutTimeFrom+"' AS time)))"
                + " AND IF(? = '', l.error_state LIKE '%%', l.error_state=?) ORDER BY l.timestamp desc"
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            if(searchError_state.equals("Yes")){
                searchError_state = "Y";
            }else if(searchError_state.equals("No")){
                searchError_state = "N";
            }else searchError_state = "";
            pstmt.setString(1, searchError_state);
            pstmt.setString(2, searchError_state);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                History history = new History();
                history.setIp_address(rset.getString("ip_address"));
                history.setPort(rset.getInt("port"));
                history.setJunction_id(rset.getInt("junction_id"));
                history.setProgram_version_no(rset.getInt("program_version_no"));
                history.setJunction_name(rset.getString("switching_point_name"));
                history.setCity_name(rset.getString("city_name"));
                history.setLogin_timestamp_date(rset.getString("login_timestamp_date"));
                history.setLogin_timestamp_time(rset.getString("login_timestamp_time"));
                history.setLogout_timestamp_date(rset.getString("logout_timestamp_date"));
                history.setLogout_timestamp_time(rset.getString("logout_timestamp_time"));
                history.setStatus(rset.getString("status").equals("Y")?true:false);
                history.setError_state(rset.getString("error_state"));
                list.add(history);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
        
    public int getNoOfRows(String searchLoginDateFrom, String searchLoginDateTo, String searchLogoutDateFrom, String searchLogoutDateTo, String searchLoginTimeFrom, String searchLoginTimeTo,String searchLogoutTimeFrom,String searchLogoutTimeTo, String searchError_state) {
        String query = " SELECT count(*)"
                + " FROM switching_point_detail spd LEFT JOIN( pole AS p,city AS c, ward AS w, area AS a) ON "
                + "(p.area_id = a.area_id AND a.ward_id = w.ward_id  AND w.city_id=c.city_id AND w.active='Active' AND isSwitchingPoint='Yes') "
                + ", junction j, log_history as l,city AS sc, ward AS sw, area AS sa "
                + " WHERE spd.switching_point_detail_id = p.switching_point_detail_id  AND spd.active = 'Y' AND p.active='Y' "
                + " AND spd.area_id = sa.area_id AND sa.ward_id = sw.ward_id AND sw.city_id=sc.city_id AND sw.active='Active' "
                + " AND spd.switching_point_detail_id=j.switching_point_detail_id AND j.active='Y'  AND j.junction_id=l.junction_id"
                + " AND IF('"+searchLoginDateFrom+"'!='' && '"+searchLoginDateTo+"'!='',"
                + " (str_to_date(DATE_FORMAT(l.login_timestamp_date,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date('"+searchLoginDateFrom+"','%d-%m-%Y') AND str_to_date('"+searchLoginDateTo+"','%d-%m-%Y')),"
                + " IF ('"+searchLoginDateFrom+"'='',str_to_date(DATE_FORMAT(l.login_timestamp_date,'%d-%m-%Y'),'%d-%m-%Y') LIKE '%%', l.login_timestamp_date=str_to_date('"+searchLoginDateFrom+"','%d-%m-%Y')))"
                + " AND IF('"+searchLogoutDateFrom+"'!='' && '"+searchLogoutDateTo+"'!='',"
                + " (str_to_date(DATE_FORMAT(l.logout_timestamp_date,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date('"+searchLogoutDateFrom+"','%d-%m-%Y') AND str_to_date('"+searchLogoutDateTo+"','%d-%m-%Y')),"
                + " IF ('"+searchLogoutDateFrom+"'='',str_to_date(DATE_FORMAT(l.logout_timestamp_date,'%d-%m-%Y'),'%d-%m-%Y') LIKE '%%', l.logout_timestamp_date=str_to_date('"+searchLogoutDateFrom+"','%d-%m-%Y')))"
                + " AND if('"+searchLoginTimeFrom+"'!='' && '"+searchLoginTimeTo+"'!='',"
                + " CAST(DATE_FORMAT(l.login_timestamp_time, '%h:%i')AS time) BETWEEN CAST('"+searchLoginTimeFrom+"' AS time)  AND CAST('"+searchLoginTimeTo+"' AS time),"
                + " If('"+searchLoginTimeFrom+"'='',DATE_FORMAT(l.login_timestamp_time, '%h:%i') LIKE '%%',CAST(DATE_FORMAT(l.login_timestamp_time, '%h:%i')AS time)=CAST('"+searchLoginTimeFrom+"' AS time)))"
                + "AND if('"+searchLogoutTimeFrom+"'!='' && '"+searchLogoutTimeTo+"'!='',"
                + "CAST(DATE_FORMAT(l.logout_timestamp_time, '%h:%i')AS time) BETWEEN CAST('"+searchLogoutTimeFrom+"' AS time)  AND CAST('"+searchLogoutTimeTo+"' AS time),"
                + "If('"+searchLogoutTimeFrom+"'='',DATE_FORMAT(l.logout_timestamp_time, '%h:%i') LIKE '%%',CAST(DATE_FORMAT(l.logout_timestamp_time, '%h:%i')AS time)=CAST('"+searchLogoutTimeFrom+"' AS time)))"
                + " AND IF(? = '', l.error_state LIKE '%%', l.error_state=?);";
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        int noOfRows = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            if(searchError_state.equals("Yes")){
                searchError_state = "Y";
            }else if(searchError_state.equals("No")){
                searchError_state = "N";
            }else searchError_state = "";
            pstmt.setString(1, searchError_state);
            pstmt.setString(2, searchError_state);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows LogHistoryModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
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
            System.out.println("LogHistoryModel closeConnection() Error: " + e);
        }
    }
}
