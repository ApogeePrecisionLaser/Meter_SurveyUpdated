/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.survey.tableClasses.PoleDetailTypeBean;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author JPSS
 */
public class PoleDetailTypeModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    private String messagee;
    private String msgBgColorr;

    public String getMessagee() {
        return messagee;
    }

    public void setMessagee(String messagee) {
        this.messagee = messagee;
    }

    public String getMsgBgColorr() {
        return msgBgColorr;
    }

    public void setMsgBgColorr(String msgBgColorr) {
        this.msgBgColorr = msgBgColorr;
    }

    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<PoleDetailTypeBean> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        //    HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in TrafficMapModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<String> getPoleType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT pole_type FROM pole_type GROUP BY pole_type ORDER BY pole_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_type = rset.getString("pole_type");
                if (pole_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Pole Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleType ERROR inside PoleDetailModel - " + e);
        }
        return list;
    }

    public List<String> getTrafficType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT traffic_type FROM traffic_type GROUP BY traffic_type ORDER BY traffic_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String traffic_type = rset.getString("traffic_type");
                if (traffic_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(traffic_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Ward exists.");
            }
        } catch (Exception e) {
            System.out.println("getWard ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getAreaName(String q, String ward_no) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT area_name FROM area,ward WHERE ward_id = (select ward_id from ward where ward_no = ?) GROUP BY area_name ORDER BY area_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ward_no);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String area_name = rset.getString("area_name");
                if (area_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(area_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Area exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getWard_No(String q, String city) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT w.ward_no FROM ward w,city WHERE w.city_id = (SELECT city_id FROM city WHERE city_name = ?) ORDER BY w.ward_no ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_no = rset.getString("ward_no");
                if (ward_no.startsWith(q)) {
                    list.add(ward_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Ward No. Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getRoadUse(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT ru.road_use FROM road_use ru, road r WHERE r.road_use_id = ru.road_use_id  GROUP BY  ru.road_use  order by ru.road_use  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String road_use = rset.getString("road_use");
                if (road_use.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(road_use);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Road Use Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getRoad_Category(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT c.category_name FROM road_category c, road r WHERE r.category_id = c.category_id     group by c.category_name order by c.category_name   ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String category_name = rset.getString("category_name");
                if (category_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(category_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Road Category Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getRoadName(String q, String road_category, String road_use) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT road_name FROM road WHERE category_id = (SELECT category_id FROM road_category WHERE category_name = ?) "
                + " AND road_use_id = (SELECT road_use_id FROM road_use WHERE road_use = ?) GROUP BY road_name ORDER BY road_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, road_category);
            pstmt.setString(2, road_use);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String road_name = rset.getString("road_name");
                if (road_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(road_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Road exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getCity(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT city_name FROM city  ORDER BY city_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("city_name");
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such City Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public int getAreaId(String city, String ward_no, String area_name) {
        int area_id = 0;
        String query = " SELECT area_id FROM area,ward WHERE area_name = ? "
                + "AND  ward_id = (SELECT ward_id FROM ward WHERE ward_no = ? AND city_id = (SELECT city_id FROM city WHERE city_name = ?))";
        if (ward_no.isEmpty() || ward_no == null) {
            query = "SELECT area_id FROM area WHERE area_name = ? ";
        }
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            if (ward_no.isEmpty() || ward_no == null) {
                pstmt.setString(1, area_name);
            } else {
                pstmt.setString(1, area_name);
                pstmt.setString(2, ward_no);
                pstmt.setString(3, city);
            }
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            area_id = rset.getInt("area_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getAreaId() Error: " + e);
        }
        return area_id;
    }

    public String getRoadId(String road_name, String road_category, String road_use) {
        String road_id = "";
        String query = " SELECT road_id, road_rev_no FROM road WHERE road_name = ? AND "
                + " category_id = (SELECT category_id FROM road_category WHERE category_name = ? ) AND "
                + " road_use_id = (SELECT road_use_id FROM road_use WHERE road_use =?) ";
        if (road_use.isEmpty() || road_category.isEmpty() || road_category == null || road_use == null) {
            query = " SELECT road_id, road_rev_no FROM road WHERE road_name = ?";
        }
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            if (road_use.isEmpty() || road_category.isEmpty() || road_category == null || road_use == null) {
                pstmt.setString(1, road_name);
            } else {
                pstmt.setString(1, road_name);
                pstmt.setString(2, road_category);
                pstmt.setString(3, road_use);
            }
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            road_id = rset.getInt("road_id") + "_" + rset.getInt("road_rev_no");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getRoadId() Error: " + e);
        }
        return road_id;
    }

    public int getTrafficId(String traffic_type) {
        int taffic_id = 0;
        String query = " SELECT traffic_type_id FROM traffic_type WHERE traffic_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, traffic_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            taffic_id = rset.getInt("traffic_type_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getTrafficId() Error: " + e);
        }
        return taffic_id;
    }

    public List<String> getSwitchingPointName(String q, String feeder_name) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT  spd.switching_point_name FROM switching_point_detail AS spd ,feeder AS f "
                + " WHERE spd.feeder_id =f.feeder_id  and feeder_name=? and spd.active = 'Y' group by  switching_point_name order by switching_point_name";
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, feeder_name);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String ward_no = rset.getString("switching_point_name");
                if (ward_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such switching point name exists.");
            }
        } catch (Exception e) {
            System.out.println("getSwitchingPointName ERROR inside WardTypeModel - " + e);
        }
        return list;
    }
    //getCircuitName
   public List<String> getCircuitName(String q, String switching_point_name) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT  c.circuit_name FROM switching_point_detail AS spd ,circuit AS c "
                + " WHERE spd.switching_point_detail_id =c.switching_point_detail_id  and spd.switching_point_name=? and spd.active = 'Y' group by  circuit_name order by circuit_name";
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, switching_point_name);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String ward_no = rset.getString("circuit_name");
                if (ward_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such switching point name exists.");
            }
        } catch (Exception e) {
            System.out.println("getSwitchingPointName ERROR inside WardTypeModel - " + e);
        }
        return list;
    }
    public List<String> getMountingType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT mounting_type FROM mounting_type GROUP BY mounting_type ORDER BY mounting_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String mounting_type = rset.getString("mounting_type");
                if (mounting_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(mounting_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such mounting Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getMountingType ERROR inside PoleDEtailTypeModel - " + e);
        }
        return list;
    }

    public List<String> getSourceWattageType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT concat (l.source_type,'-',w.wattage_value) AS source_wattage "
                + "FROM light_type lt,light_source_type AS l,wattage AS w "
                + "where lt.wattage_id=w.wattage_id "
                + "AND lt.source_id=l.source_type_id "
                + "GROUP BY source_wattage ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String source_type = rset.getString("source_wattage");
                if (source_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(source_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such mounting Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getSourceWattageType ERROR inside PoleDEtailTypeModel - " + e);
        }
        return list;
    }

    public List<String> getSwitchingFeederName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT  f.feeder_name FROM switching_point_detail AS spd ,feeder AS f "
                + " WHERE spd.feeder_id =f.feeder_id group by  f.feeder_name order by f.feeder_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String source_type = rset.getString("feeder_name");
                if (source_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(source_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such feeder name exists.");
            }
        } catch (Exception e) {
            System.out.println("getSwitchingFeederName ERROR inside PoleDEtailTypeModel - " + e);
        }
        return list;
    }

    public List<String> getSearchSwitchingPointName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT  spd.switching_point_name FROM pole p LEFT JOIN switching_point_detail AS spd ON"
                + " p.switching_point_detail_id = spd.switching_point_detail_id AND p.switching_rev_no = spd.switching_rev_no group by  spd.switching_point_name order by switching_point_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String source_type = rset.getString("switching_point_name");
                if (source_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(source_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Switching Point exists with Pole");
            }
        } catch (Exception e) {
            System.out.println("getSwitchingFeederName ERROR inside PoleDEtailTypeModel - " + e);
        }
        return list;
    }

    public String getSwitchingPointID_Rev(String spn) {
        String switching_id_rev_no = "";
        String query = "select switching_point_detail_id, switching_rev_no  from switching_point_detail where switching_point_name = ? and active = 'Y' ";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, spn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                switching_id_rev_no = rs.getInt("switching_point_detail_id") + "_" + rs.getInt("switching_rev_no");
            }
        } catch (Exception e) {
            System.out.println("Exception occured inside getSwitchingPointID_Rev is " + e);
        }
        return switching_id_rev_no;
    }

    public List<String> getSearchPoleNo(String q, String switching_pt_name) {
        int switching_id = 0;
        int switching_rev_no = 0;
        if (!switching_pt_name.isEmpty()) {
            String switching_id_rev_no = getSwitchingPointID_Rev(switching_pt_name);
            switching_id = Integer.parseInt(switching_id_rev_no.split("_")[0]);
            switching_rev_no = Integer.parseInt(switching_id_rev_no.split("_")[1]);
        }
        List<String> list = new ArrayList<String>();
        String query = " SELECT  p.pole_no FROM pole p LEFT JOIN switching_point_detail AS spd ON"
                + " p.switching_point_detail_id = spd.switching_point_detail_id AND p.switching_rev_no = spd.switching_rev_no "
                + " WHERE IF (? = '', p.pole_no LIKE '%%', p.switching_point_detail_id = ? AND p.switching_rev_no = ?)"
                + " GROUP BY p.pole_no ORDER by p.pole_no ";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, switching_pt_name);
            stmt.setInt(2, switching_id);
            stmt.setInt(3, switching_rev_no);
            ResultSet rset = stmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String source_type = rset.getString("pole_no");
                if (source_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(source_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Pole Exists");
            }
        } catch (Exception e) {
            System.out.println("getSwitchingFeederName ERROR inside PoleDEtailTypeModel - " + e);
        }
        return list;
    }

    public int getPoleId(String Pole_type) {
        int pole_type_id = 0;
        String query = " SELECT pole_type_id FROM pole_type WHERE pole_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, Pole_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            pole_type_id = rset.getInt("pole_type_id");
        } catch (Exception e) {
            System.out.println("PoleDEtailTypeModel getPoleId() Error: " + e);
        }
        return pole_type_id;
    }

    public int getMountingId(String mounting_type) {
        int mounting_type_id = 0;
        String query = " SELECT mounting_type_id FROM mounting_type WHERE mounting_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mounting_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            mounting_type_id = rset.getInt("mounting_type_id");
        } catch (Exception e) {
            System.out.println("PoleDEtailTypeModel getMountingId() Error: " + e);
        }
        return mounting_type_id;
    }

    public int getSourceWattageId(String source_wattage) {
        int light_type_id = 0;
        String query = " SELECT light_type_id "
                + " FROM light_type lt,light_source_type lst,wattage w "
                + " WHERE concat (lst.source_type,'-',w.wattage_value)=? "
                + " AND lt.wattage_id=w.wattage_id "
                + " AND lt.source_id=lst.source_type_id; ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, source_wattage);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            light_type_id = rset.getInt("light_type_id");
        } catch (Exception e) {
            System.out.println("PoleDEtailTypeModel getSourceWattageId() Error: " + e);
        }
        return light_type_id;
    }

    public int getFeederId(String feeder_name) {
        int light_type_id = 0;
        String query = "SELECT fd.feeder_id FROM feeder as  fd,switching_point_detail as spd where spd.feeder_id=fd.feeder_id  and fd.feeder_name=? group by feeder_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, feeder_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            light_type_id = rset.getInt("feeder_id");
        } catch (Exception e) {
            System.out.println("PoleDEtailTypeModel getFeederId() Error: " + e);
        }
        return light_type_id;
    }

    public String getSwitchingPointDetailId(String switchPointName, int feeder_id) {
        String switching_id_rev_no = "";
        String query = " SELECT switching_point_detail_id, switching_rev_no FROM switching_point_detail spd ,feeder fd where spd.feeder_id=fd.feeder_id  and  switching_point_name=?"
                + " and fd.feeder_id=? group by switching_point_detail_id ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, switchPointName);
            pstmt.setInt(2, feeder_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            switching_id_rev_no = rset.getInt("switching_point_detail_id") + "_" + rset.getInt("switching_rev_no");
        } catch (Exception e) {
            System.out.println("PoleDEtailTypeModel getSourceWattageId() Error: " + e);
        }
        return switching_id_rev_no;
    }
       public int getCircuitDetailId(String circuitName, int feeder_id,int switching_point_id) {
      int  circuit_id_rev_no = 0;
//        String query = " SELECT switching_point_detail_id, switching_rev_no FROM switching_point_detail spd ,feeder fd where spd.feeder_id=fd.feeder_id  and  switching_point_name=?"
//                + " and fd.feeder_id=? group by switching_point_detail_id ";
 String query = " SELECT id FROM circuit where circuit_name=?"
                + " and switching_point_detail_id=? group by id ";        
try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, circuitName);
            pstmt.setInt(2, switching_point_id);
//            pstmt.setInt(2, feeder_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            circuit_id_rev_no = rset.getInt("id");
        } catch (Exception e) {
            System.out.println("PoleDEtailTypeModel getSourceWattageId() Error: " + e);
        }
        return circuit_id_rev_no;
    }

    public int getPoleId() {
        int pole_id = 0;
        try {
            String query = "select max(pole_id) from pole ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pole_id = rs.getInt("max(pole_id)");
            }

        } catch (Exception e) {
            System.out.println("Error: PoleTypeModel getpoleId():" + e);
        }
        return pole_id;
    }

    private int getRevNo(int pole_id) {
        int pole_rev_no = 0;
        String query = "SELECT max(pole_rev_no) as pole_rev_no FROM pole where pole_id=" + pole_id;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                pole_rev_no = rset.getInt("pole_rev_no");
            }
        } catch (Exception e) {
            System.out.println("Error: getRevNo():" + e);
        }
        return pole_rev_no;
    }

    public int insertRecord(PoleDetailTypeBean poleTypeBean) {

//        String query = "INSERT INTO pole (pole_id, pole_type_id, pole_span, pole_height, mounting_height, created_by, remark, mounting_type_id, "
//                + " pole_no, gps_code, max_avg_lux_level, min_avg_lux_level, avg_lux_level, standard_lux_level, "
//                + " is_working,switching_point_detail_id,isSwitchingPoint, area_id, road_id, road_rev_no, traffic_type_id, switching_rev_no) "
//                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?) ";
//        String query2 = "INSERT INTO pole_light_type_mapping (pole_id, light_type_id, quantity)"
//                + " VALUES (?,?,?) ";
   String query = "INSERT INTO pole (pole_id, pole_type_id, pole_span, pole_height, mounting_height, created_by, remark, mounting_type_id, "
                + " pole_no, gps_code, max_avg_lux_level, min_avg_lux_level, avg_lux_level, standard_lux_level, "
                + " is_working,switching_point_detail_id,isSwitchingPoint, area_id, road_id, road_rev_no, traffic_type_id, switching_rev_no,circuit_id) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?) ";
        String query2 = "INSERT INTO pole_light_type_mapping (pole_id, light_type_id, quantity)"
                + " VALUES (?,?,?) ";
        int pole_id = getPoleId();



        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, pole_id + 1);
            pstmt.setInt(2, poleTypeBean.getPole_type_id());
            pstmt.setString(3, poleTypeBean.getPole_span());
            pstmt.setString(4, poleTypeBean.getPole_height());
            pstmt.setString(5, poleTypeBean.getMounting_height());
            pstmt.setString(6, poleTypeBean.getCreated_by());
            pstmt.setString(7, poleTypeBean.getRemark());
            pstmt.setInt(8, poleTypeBean.getMounting_type_id());
            // pstmt.setString(9, poleTypeBean.getActive());
            // pstmt.setInt(9, poleTypeBean.getLight_type_id());
            pstmt.setString(9, poleTypeBean.getPole_no());
            pstmt.setString(10, poleTypeBean.getGps_code());
            pstmt.setString(11, poleTypeBean.getMax_avg_lux_level());
            pstmt.setString(12, poleTypeBean.getMin_avg_lux_level());
            pstmt.setString(13, poleTypeBean.getAvg_lux_level());
            pstmt.setString(14, poleTypeBean.getStandard_lux_level());
            pstmt.setString(15, poleTypeBean.getIs_working());
            pstmt.setInt(16, poleTypeBean.getSwitch_point_detail_id());
            pstmt.setString(17, poleTypeBean.getIs_switch_point());

            pstmt.setInt(18, poleTypeBean.getArea_id());
            pstmt.setInt(19, poleTypeBean.getRoad_id());
            pstmt.setInt(20, poleTypeBean.getRoad_rev_no());
            pstmt.setInt(21, poleTypeBean.getTraffic_type_id());
            pstmt.setInt(22, poleTypeBean.getSwitching_rev_no());
            pstmt.setInt(23, poleTypeBean.getCircuit_id());
            //pstmt.setString(23, poleTypeBean.getPole_no_client());
            rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                //   int max_pole_id = getPoleId();
                ResultSet rs1 = pstmt.getGeneratedKeys();
                if (rs1.next()) {
                    rowsAffected = 0;
                    int max_pole_id = rs1.getInt(1);
                    pstmt.close();
                    //   int  max_pole_id = rs1.getInt(1);
                    // int pole_rev_no = getRevNo(max_pole_id);
                    String[] source_wattage = poleTypeBean.getSources_wattage_M();

                    for (int i = 0; i < source_wattage.length; i++) {
                        if (!source_wattage[i].isEmpty()) {

                            int light_type_id = getSourceWattageId(source_wattage[i]);

                            pstmt = connection.prepareStatement(query2,Statement.RETURN_GENERATED_KEYS);
                            pstmt.setInt(1, max_pole_id);
                            pstmt.setInt(2, light_type_id);
                            pstmt.setInt(3, Integer.parseInt(poleTypeBean.getQuantity()[i]));
                            //     pstmt.setInt(4, pole_rev_no);
                            rowsAffected = pstmt.executeUpdate();
                        }
                    }
                    if (rowsAffected > 0) {
                        connection.commit();
                        //  rowsAffected++;
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PoleDetailTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(PoleDetailTypeBean poleTypeBean) {

        String query = "update  pole set  pole_type_id=?, pole_span=?, pole_height=?, mounting_height=?, created_by=?, remark=?, mounting_type_id=?, "
                + " pole_no=?, gps_code=?, max_avg_lux_level=?, min_avg_lux_level=?, avg_lux_level=?, standard_lux_level=?, "
                + " is_working=?,switching_point_detail_id=?,isSwitchingPoint=? where pole_id=?  and pole_rev_no=?";//pole_no_client=?

        String updateMapping = "update pole_light_type_mapping set quantity=?,light_type_id=? where pole_id=? and light_type_id=? and pole_rev_no=? ";
        String insertIntoMap = "INSERT INTO pole_light_type_mapping (pole_id, light_type_id, quantity,pole_rev_no )"
                + " VALUES (?,?,?,?) ";

      //      String query3 = "update switching_point_detail set pole_id=?,pole_rev_no=? where switching_point_detail_id=?";
        //  String updateMapping = "UPDATE pole_light_type_mapping SET light_type_id=?, quantity=? "
         //       + " WHERE pole_id = ? AND pole_rev_no = ? AND light_type_id = ?";
     //   String insertIntoMap = "INSERT INTO  pole_light_type_mapping (pole_id, light_type_id, quantity, pole_rev_no)"
     //           + " VALUES (?,?,?,?)";

        int pole_id =poleTypeBean.getPole_id();



        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            //     pstmt.setInt(1, pole_id + 1);
            pstmt.setInt(1, poleTypeBean.getPole_type_id());
            pstmt.setString(2, poleTypeBean.getPole_span());
            pstmt.setString(3, poleTypeBean.getPole_height());
            pstmt.setString(4, poleTypeBean.getMounting_height());
            pstmt.setString(5, poleTypeBean.getCreated_by());
            pstmt.setString(6, poleTypeBean.getRemark());
            pstmt.setInt(7, poleTypeBean.getMounting_type_id());
            // pstmt.setString(9, poleTypeBean.getActive());
            // pstmt.setInt(9, poleTypeBean.getLight_type_id());
            pstmt.setString(8, poleTypeBean.getPole_no());
            pstmt.setString(9, poleTypeBean.getGps_code());
            pstmt.setString(10, poleTypeBean.getMax_avg_lux_level());
            pstmt.setString(11, poleTypeBean.getMin_avg_lux_level());
            pstmt.setString(12, poleTypeBean.getAvg_lux_level());
            pstmt.setString(13, poleTypeBean.getStandard_lux_level());
            pstmt.setString(14, poleTypeBean.getIs_working());
            pstmt.setInt(15, poleTypeBean.getSwitch_point_detail_id());
            pstmt.setString(16, poleTypeBean.getIs_switch_point());            
            
            //pstmt.setString(17, poleTypeBean.getPole_no_client());

            pstmt.setInt(17, poleTypeBean.getPole_id());
            pstmt.setInt(18, poleTypeBean.getPole_rev_no());

            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {               
                rowsAffected = 0;               
                int pole_rev_no = getRevNo(pole_id);
                String[] source_wattage = poleTypeBean.getSources_wattage_M();
                String[] old_mapping_id = poleTypeBean.getMapp_id();
                int add_row_count = poleTypeBean.getAddRowCount();
                for (int i = 0; i < source_wattage.length; i++) {                 
                    if (source_wattage[i].equals("")) {
                        continue;
                    }
                    int light_type_id = getSourceWattageId(source_wattage[i]);
                    if (old_mapping_id[i].equals("") && source_wattage[i] != null && !source_wattage[i].isEmpty()) {
                        pstmt = connection.prepareStatement(insertIntoMap);
                        pstmt.setInt(1, pole_id);
                        pstmt.setInt(2, light_type_id);
                        pstmt.setInt(3, Integer.parseInt(poleTypeBean.getQuantity()[i]));
                        pstmt.setInt(4, pole_rev_no);
                        rowsAffected = pstmt.executeUpdate();
                    } else {
                        //  int light_type_id = getSourceWattageId(source_wattage[i]);
                        pstmt = connection.prepareStatement(updateMapping);
                        pstmt.setInt(1, Integer.parseInt(poleTypeBean.getQuantity()[i]));
                        pstmt.setInt(2, light_type_id);
                        pstmt.setInt(3, pole_id);
                        pstmt.setInt(4, Integer.parseInt(poleTypeBean.getMap_light_type_id()[i]));
                        pstmt.setInt(5, pole_rev_no);
                        // pstmt.setInt(5, Integer.parseInt(poleTypeBean.getMapp_id()[i]));
                        rowsAffected = pstmt.executeUpdate();
                    }
                }  
                if (rowsAffected > 0) {
                    connection.commit();
                    //  rowsAffected++;
                } else {
                    connection.rollback();
                }
                //   }
            } else {
                connection.rollback();
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PoleDetailTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rowsAffected > 0) {
            message = "Record update successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int reviseRecord(PoleDetailTypeBean lightTypeBean) {

        String query = "INSERT INTO pole (pole_id, pole_type_id, pole_span, pole_height, mounting_height, created_by, remark, mounting_type_id, "
                + " pole_no, gps_code, max_avg_lux_level, min_avg_lux_level, avg_lux_level, standard_lux_level, "
                + " is_working, pole_rev_no,switching_point_detail_id,isSwitchingPoint, area_id, road_id, road_rev_no, traffic_type_id, switching_rev_no)"//pole_no_client) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?) ";

        String query2 = " UPDATE pole SET active='N'"
                + " WHERE pole_id = ? and pole_rev_no = ? ";
        String query3 = "INSERT INTO pole_light_type_mapping (pole_id, light_type_id, quantity, pole_rev_no)"
                + " VALUES (?,?,?,?) ";
        String query4 = "Update pole_light_type_mapping SET active='N' WHERE pole_id = ? and pole_rev_no = ? ";

        
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, lightTypeBean.getPole_id());
            pstmt.setInt(2, lightTypeBean.getPole_type_id());
            pstmt.setString(3, lightTypeBean.getPole_span());
            pstmt.setString(4, lightTypeBean.getPole_height());
            pstmt.setString(5, lightTypeBean.getMounting_height());
            pstmt.setString(6, lightTypeBean.getCreated_by());
            pstmt.setString(7, lightTypeBean.getRemark());
            pstmt.setInt(8, lightTypeBean.getMounting_type_id());
            pstmt.setString(9, lightTypeBean.getPole_no());
            pstmt.setString(10, lightTypeBean.getGps_code());
            pstmt.setString(11, lightTypeBean.getMax_avg_lux_level());
            pstmt.setString(12, lightTypeBean.getMin_avg_lux_level());
            pstmt.setString(13, lightTypeBean.getAvg_lux_level());
            pstmt.setString(14, lightTypeBean.getStandard_lux_level());
            pstmt.setString(15, lightTypeBean.getIs_working());
            pstmt.setInt(16, lightTypeBean.getPole_rev_no() + 1);
            pstmt.setInt(17, lightTypeBean.getSwitch_point_detail_id());
            pstmt.setString(18, lightTypeBean.getIs_switch_point());

            pstmt.setInt(19, lightTypeBean.getArea_id());
            pstmt.setInt(20, lightTypeBean.getRoad_id());
            pstmt.setInt(21, lightTypeBean.getRoad_rev_no());
            pstmt.setInt(22, lightTypeBean.getTraffic_type_id());
            pstmt.setInt(23, lightTypeBean.getSwitching_rev_no());
            //pstmt.setString(24, lightTypeBean.getPole_no_client());
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rowsAffected = 0;
                pstmt = connection.prepareStatement(query2);
                pstmt.setInt(1, lightTypeBean.getPole_id());
                pstmt.setInt(2, lightTypeBean.getPole_rev_no());
                rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    rowsAffected = 0;

                    String[] source_wattage = lightTypeBean.getSources_wattage_M();
                    String[] quanity = lightTypeBean.getQuantity();
                    int add_row_count = lightTypeBean.getAddRowCount();
                    for (int i = 0; i < source_wattage.length; i++) {

                        if (!source_wattage[i].isEmpty()) {

                            int light_type_id = getSourceWattageId(source_wattage[i]);
                            pstmt = connection.prepareStatement(query3);
                            pstmt.setInt(1, lightTypeBean.getPole_id());
                            pstmt.setInt(2, light_type_id);
                            pstmt.setInt(3, Integer.parseInt(lightTypeBean.getQuantity()[i]));
                            pstmt.setInt(4, lightTypeBean.getPole_rev_no() + 1);
                            rowsAffected = pstmt.executeUpdate();
                        }
                    }
                }
                if (rowsAffected > 0) {
                    rowsAffected = 0;

                    if (!(lightTypeBean.getIs_switch_point().equals("Yes") && lightTypeBean.getPole_rev_no() == 0)) { // check if revision is of pole with switching point (1st time entry in mapping)
                        pstmt = connection.prepareStatement(query4);
                        pstmt.setInt(1, lightTypeBean.getPole_id());
                        pstmt.setInt(2, lightTypeBean.getPole_rev_no());
                        rowsAffected = pstmt.executeUpdate();
                    } else {
                        rowsAffected = 1;
                        connection.commit();
                    }
                }

                if (rowsAffected > 0) {
                    connection.commit();

                } else {
                    connection.rollback();
                }

            } else {
                connection.rollback();
            }
        } catch (Exception e) {
            System.out.println("Error while revised record...." + e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PoleDetailTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (rowsAffected > 0) {
            message = "Record Revised successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot revised the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public boolean validationCheckforRevise(String pole_no, String pole_no_client, int pole_id) {
        int i = 0;
        String query = "select count(*) from pole where pole_no ='" + pole_no + "' and active = 'Y' AND pole_id !=" + pole_id;// OR  pole_no_client ='" + pole_no_client + "' 
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                i = rs.getInt("count(*)");
            }
        } catch (Exception e) {
            System.out.println("Error : inside Switching point validation check of insert is " + e);
        }
        if (i > 0) {
            message = "This Pole No. Already Exists";
            msgBgColor = COLOR_ERROR;
        }
        return i > 0 ? false : true;
    }

    public boolean validationCheck(String pole_no, String pole_no_client) {
        int i = 0;
        String query = "select count(*) from pole where pole_no ='" + pole_no + "' and active = 'Y'";//OR pole_no_client ='" + pole_no_client + "'
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                i = rs.getInt("count(*)");
            }
        } catch (Exception e) {
            System.out.println("Error : inside Switching point validation check of insert is " + e);
        }
        if (i > 0) {
            message = "This Pole No. Already Exists";
            msgBgColor = COLOR_ERROR;
        }
        return i > 0 ? false : true;
    }

    public int updatePoloNoClient(PoleDetailTypeBean bean) {
        int rowsAffected = 0;
        String[] pole_id_showData = bean.getPole_id_showData();
        String[] client_pole_no_id = bean.getClient_pole_no_id();
        String[] client_pole_no_edit = bean.getClient_pole_no_edit();

        String query = "update pole set pole_no_client = ? where pole_id = ?";
        for (int i = 0; i < pole_id_showData.length; i++) {
            if (client_pole_no_id[i].equals("1")) {
                try {
                    java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, client_pole_no_edit[i].trim());
                    pstmt.setInt(2, Integer.parseInt(pole_id_showData[i]));
                    rowsAffected = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Error while updatePoloNoClient updating record...." + e);
                }

            }
        }
        if (rowsAffected > 0) {
            messagee = "Record Updated Successfully.";
            msgBgColorr = COLOR_OK;
        } else {
            messagee = "Cannot update the record, some error.";
            msgBgColorr = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public boolean checkOnRevision(int pole_id) {
        int no_of_rows = 0;
        String query = "select count(*) from pole_light_type_mapping where pole_id =" + pole_id;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                no_of_rows = rs.getInt("count(*)");
            }
        } catch (Exception e) {
            System.out.println("Exception occured inside checOnRevision is " + e);
        }
        return no_of_rows > 0 ? true : false;
    }

    public int getNoOfRows(String searchPoleType, String searchMountingType, String searchSwitchingPoint, String searchPole_no) {
//        String query = " SELECT Count(*) "
//                + " FROM pole p,  mounting_type m "
//                // + " light_type lt,light_source_type AS l,wattage AS w "
//                + "  WHERE "
//                + " p.mounting_type_id = m.mounting_type_id ";
//                // + " AND p.light_type_id=lt.light_type_id "
//                //+ " AND lt.wattage_id=w.wattage_id "
//                //+ " AND lt.source_id=l.source_type_id "


        String query = /*" SELECT Count(*) "
                + " FROM pole p, pole_type pt, mounting_type m, switching_point_detail spd "
                // + " light_type lt,light_source_type AS l,wattage AS w "
                + "  WHERE p.pole_type_id = pt.pole_type_id "
                + " AND p.mounting_type_id = m.mounting_type_id "
                + " AND p.switching_point_detail_id = spd.switching_point_detail_id "
                // + " AND p.light_type_id=lt.light_type_id "
                //+ " AND lt.wattage_id=w.wattage_id "
                //+ " AND lt.source_id=l.source_type_id "
                + " AND IF('" + searchPoleType + "' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "
                + " AND IF('" + searchMountingType + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
                + " ORDER BY pt.pole_type"; */
                /*  " SELECT COUNT(*) "
                + " FROM pole p "
                + "  LEFT JOIN pole_type pt ON p.pole_type_id = pt.pole_type_id "
                + " LEFT JOIN mounting_type m ON p.mounting_type_id = m.mounting_type_id "
                + " LEFT JOIN switching_point_detail spd ON p.switching_point_detail_id = spd.switching_point_detail_id AND p.switching_rev_no = spd.switching_rev_no "
                + " left join feeder f  on spd.feeder_id=f.feeder_id "

                + " AND IF('" + searchPoleType + "' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "
                + " AND IF('" + searchMountingType + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
                + " ORDER BY p.switching_point_detail_id "; */
                " SELECT  count(*) "
                + "  FROM pole p "
                + " LEFT JOIN pole_type pt ON p.pole_type_id = pt.pole_type_id "
                + " LEFT JOIN mounting_type m ON p.mounting_type_id = m.mounting_type_id "
                + "    LEFT JOIN switching_point_detail spd ON p.switching_point_detail_id = "
                + "  spd.switching_point_detail_id AND spd.active='Y' "
                + " left join feeder f  on spd.feeder_id=f.feeder_id, "
                + " area a, road r, traffic_type t, road_category rc, road_use ru, ward w, city cty "
               // + " area a, road r, traffic_type t, road_category rc, road_use ru, ward_m w, city cty "
                + " where p.active = 'Y' AND  "
                + " p.area_id = a.area_id And "
               + " a.ward_id = w.ward_id AND "
                // + " a.ward_id_m = w.ward_id_m AND "
               + " w.city_id = cty.city_id AND "
                + " p.road_id = r.road_id And "
                + " p.road_rev_no = r.road_rev_no And "
                + " r.road_use_id = ru.road_use_id And "
                + " r.category_id = rc.category_id And "
                + " p.traffic_type_id = t.traffic_type_id AND "
                + " IF('" + searchPoleType + "' = '', p.pole_id LIKE '%%', pt.pole_type =? ) "
                + " AND IF('" + searchMountingType + "' = '', p.pole_id LIKE '%%', m.mounting_type =? ) "
                + " AND IF('" + searchSwitchingPoint + "' = '', spd.switching_point_name LIKE '%%', spd.switching_point_name =? ) "
                + " AND IF('" + searchPole_no + "' = '', p.pole_no LIKE '%%', p.pole_no =? ) "
                + " ORDER BY p.switching_point_detail_id ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchPoleType);
            stmt.setString(2, searchMountingType);
            stmt.setString(3, searchSwitchingPoint);
            stmt.setString(4, searchPole_no);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Light model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<PoleDetailTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchPoleType, String searchMountingType, String searchSwitchingPoint, String searchPoleNo) {
         List<PoleDetailTypeBean> list = new ArrayList<PoleDetailTypeBean>();

        String query = /*"SELECT concat (l.source_type,'-',w.wattage_value) AS source_wattage,p.isSwitchingPoint as is_switch_point,f.feeder_name,p.latitude ,p.longitude,pt.pole_type,p.pole_rev_no,p.pole_span,p.pole_height,p.mounting_height, DATE_FORMAT(p.created_date,'%d-%m-%Y') AS created_date,"
                + " p.created_by, p.remark,p.pole_no,p.gps_code,p.max_avg_lux_level,p.min_avg_lux_level,"
                + " p.avg_lux_level,p.standard_lux_level,p.is_working,"
                + " m.mounting_type,p.active, "
                + " p.pole_id, spd.switching_point_name, "
                + " '' AS source_wattage, "

                + " '' AS switching_load"

                + " FROM pole p, pole_type pt, mounting_type m, switching_point_detail spd,feeder f ,light_type lt,light_source_type AS l,wattage AS w"
                + " WHERE p.pole_type_id = pt.pole_type_id "
                + " AND p.mounting_type_id = m.mounting_type_id "
                + " AND p.switching_point_detail_id = spd.switching_point_detail_id and spd.feeder_id=f.feeder_id and spd.feeder_id=f.feeder_id and lt.wattage_id=w.wattage_id "  */
                " SELECT a.area_name, w.ward_no, r.road_name, t.traffic_type, ru.road_use, rc.category_name, cty.city_name , "
                + "    (SELECT GROUP_CONCAT(CAST((concat(spltm.mapping_type_id,'-',spltm.light_type_id))   AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
                + " FROM pole_light_type_mapping AS spltm,light_type lt,light_source_type   AS l,wattage AS w  WHERE spltm.light_type_id=lt.light_type_id "
                + " AND p.pole_id = spltm.pole_id and  p.pole_rev_no = spltm.pole_rev_no  AND lt.wattage_id=w.wattage_id "
                + " AND   lt.source_id=l.source_type_id)AS map_id, "
                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.quantity)) "
                + "  AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
                + " FROM pole_light_type_mapping AS spltm,light_type lt,light_source_type "
                + "  AS l,wattage AS w  WHERE spltm.light_type_id=lt.light_type_id "
                + "  AND p.pole_id = spltm.pole_id and  p.pole_rev_no = spltm.pole_rev_no "
                + " AND lt.wattage_id=w.wattage_id AND "
                + "  lt.source_id=l.source_type_id)AS source_wattage,p.latitude "
                + " ,p.longitude,pt.pole_type,p.pole_rev_no,p.pole_span,p.pole_height,p.mounting_height, "
                + " DATE_FORMAT(p.created_date,'%d-%m-%Y') AS "
                + "  created_date,f.feeder_name,p.isSwitchingPoint as is_switch_point, "
                + " p.created_by, p.remark,p.pole_no, "//p.pole_no_client, "
                + " p.gps_code,p.max_avg_lux_level,p.min_avg_lux_level, "
                + "  p.avg_lux_level,p.standard_lux_level,p.is_working, "
                + " m.mounting_type,p.active,  p.pole_id, "
                + "   spd.switching_point_name,   '' AS switching_load "
                + "  FROM pole p "
                + " LEFT JOIN pole_type pt ON p.pole_type_id = pt.pole_type_id "
                + " LEFT JOIN mounting_type m ON p.mounting_type_id = m.mounting_type_id "
                + "    LEFT JOIN switching_point_detail spd ON p.switching_point_detail_id = "
                + "  spd.switching_point_detail_id AND spd.active='Y' "
                + " left join feeder f  on spd.feeder_id=f.feeder_id, "
//                + " area a, road r, traffic_type t, road_category rc, road_use ru, ward w, city cty "
                   + " area a, road r, traffic_type t, road_category rc, road_use ru, ward w, city cty "
                + " where p.active = 'Y' AND "
                + " p.area_id = a.area_id And "
                + " a.ward_id = w.ward_id AND "
             //     + " a.ward_id_m = w.ward_id_m AND "
                + " w.city_id = cty.city_id AND "
                + " p.road_id = r.road_id And "
                + " p.road_rev_no = r.road_rev_no And "
                + " r.road_use_id = ru.road_use_id And "
                + " r.category_id = rc.category_id And "
                + " p.traffic_type_id = t.traffic_type_id AND "
                + " IF('" + searchPoleType + "' = '', p.pole_id LIKE '%%', pt.pole_type =? ) "
                + " AND IF('" + searchMountingType + "' = '', p.pole_id LIKE '%%', m.mounting_type =? ) "
                + " AND IF('" + searchSwitchingPoint + "' = '', spd.switching_point_name LIKE '%%', spd.switching_point_name =? ) "
                + " AND IF('" + searchPoleNo + "' = '', p.pole_no LIKE '%%', p.pole_no =? ) "
                + " ORDER BY p.switching_point_detail_id desc"
                + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;

        /*" SELECT p.pole_id, pt.pole_type,p.pole_span,p.pole_height,p.mounting_height, DATE_FORMAT(p.created_date,'%d-%m-%Y') AS created_date, "
        + " p.created_by, p.remark,p.pole_no,p.gps_code,p.max_avg_lux_level,p.min_avg_lux_level, "
        + " p.avg_lux_level,p.standard_lux_level,p.is_working, "
        + " m.mounting_type,p.active, "
        + " concat (l.source_type,'-',w.wattage_value) AS source_wattage "
        + " FROM pole p, pole_type pt, mounting_type m, "
        + " light_type lt,light_source_type AS l,wattage AS w "
        + "  WHERE p.pole_type_id = pt.pole_type_id "
        + " AND p.mounting_type_id = m.mounting_type_id "
        + " AND p.light_type_id=lt.light_type_id "
        + " AND lt.wattage_id=w.wattage_id "
        + " AND lt.source_id=l.source_type_id "
        + " AND IF('" + searchPoleType + "' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "
        + " AND IF('" + searchMountingType + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
        + " ORDER BY pt.pole_type" ;*/
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchPoleType);
            pstmt.setString(2, searchMountingType);
            pstmt.setString(3, searchSwitchingPoint);
            pstmt.setString(4, searchPoleNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PoleDetailTypeBean sourceType = new PoleDetailTypeBean();
                sourceType.setPole_id(rset.getInt("pole_id"));
                sourceType.setPole_rev_no(rset.getInt("pole_rev_no"));
                sourceType.setPole_type(rset.getString("pole_type"));
                sourceType.setPole_span(rset.getString("pole_span"));
                sourceType.setPole_height(rset.getString("pole_height"));
                sourceType.setMounting_height(rset.getString("mounting_height"));
                sourceType.setCreated_date(rset.getString("created_date"));
                sourceType.setCreated_by(rset.getString("created_by"));
                sourceType.setRemark(rset.getString("remark"));
                sourceType.setMounting_type(rset.getString("mounting_type"));
                sourceType.setActive(rset.getString("active"));
                sourceType.setPole_no(rset.getString("pole_no"));
                //sourceType.setPole_no_client(rset.getString("pole_no_client"));
                sourceType.setGps_code(rset.getString("gps_code"));
                sourceType.setMax_avg_lux_level(rset.getString("max_avg_lux_level"));
                sourceType.setMin_avg_lux_level(rset.getString("min_avg_lux_level"));
                sourceType.setAvg_lux_level(rset.getString("avg_lux_level"));
                sourceType.setStandard_lux_level(rset.getString("standard_lux_level"));
                sourceType.setIs_working(rset.getString("is_working"));
                sourceType.setSource_wattage(rset.getString("source_wattage"));
                sourceType.setSwitching_point_name(rset.getString("switching_point_name"));
                sourceType.setSwitching_load(rset.getInt("switching_load"));
                sourceType.setLatitude(rset.getDouble("latitude"));
                sourceType.setLongitude(rset.getDouble("longitude"));
                sourceType.setFeeder_name(rset.getString("feeder_name"));
                sourceType.setSource_wattage(rset.getString("source_wattage"));
                sourceType.setIs_switch_point(rset.getString("is_switch_point"));
                sourceType.setMap_id(rset.getString("map_id"));


                sourceType.setArea_name(rset.getString("area_name"));
                sourceType.setRoad_name(rset.getString("road_name"));
                sourceType.setTraffic_type(rset.getString("traffic_type"));
                sourceType.setRoad_use(rset.getString("road_use"));
                sourceType.setRoad_category(rset.getString("category_name"));
                sourceType.setCity(rset.getString("city_name"));
                sourceType.setWard_no(rset.getString("ward_no"));
                list.add(sourceType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public boolean generateExcelReport(String jrxmlFilePath, List siteList, HashMap mymap, String searchSwitchingPoint, HttpServletResponse response) {
        byte[] reportInbytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String fileName = searchSwitchingPoint.replaceAll(" ", "_");
        try {
            JRBeanCollectionDataSource jrbean = new JRBeanCollectionDataSource(siteList);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jp = JasperFillManager.fillReport(compiledReport, mymap, jrbean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            //    exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, repository + "/" + excelFileName);
            exporter.exportReport();

            ServletOutputStream servletOutputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            response.setContentLength(baos.size());
            servletOutputStream.write(baos.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();

            //  reportInbytes = baos.toByteArray();
        } catch (Exception e) {
            System.out.println("COULDN'T GENERATE EXCEL FILE INSIDE IS " + e);
        }
        return true;
    }

    public List<PoleDetailTypeBean> showAllData(String searchPoleType, String searchMountingType, String searchSwitchingPoint,String searchPoleNo) {
        List<PoleDetailTypeBean> listAll = new ArrayList<PoleDetailTypeBean>();
        String query = " SELECT a.area_name, w.ward_no, r.road_name, t.traffic_type, ru.road_use, rc.category_name, cty.city_name , "
                + "    (SELECT GROUP_CONCAT(CAST((concat(spltm.mapping_type_id,'-',spltm.light_type_id))   AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
                + " FROM pole_light_type_mapping AS spltm,light_type lt,light_source_type   AS l,wattage AS w  WHERE spltm.light_type_id=lt.light_type_id "
                + " AND p.pole_id = spltm.pole_id and  p.pole_rev_no = spltm.pole_rev_no  AND lt.wattage_id=w.wattage_id "
                + " AND   lt.source_id=l.source_type_id)AS map_id, "
                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.quantity)) "
                + "  AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
                + " FROM pole_light_type_mapping AS spltm,light_type lt,light_source_type "
                + "  AS l,wattage AS w  WHERE spltm.light_type_id=lt.light_type_id "
                + "  AND p.pole_id = spltm.pole_id and  p.pole_rev_no = spltm.pole_rev_no "
                + " AND lt.wattage_id=w.wattage_id AND "
                + "  lt.source_id=l.source_type_id)AS source_wattage,p.latitude "
                + " ,p.longitude,pt.pole_type,p.pole_rev_no,p.pole_span,p.pole_height,p.mounting_height, "
                + " DATE_FORMAT(p.created_date,'%d-%m-%Y') AS "
                + "  created_date,f.feeder_name,p.isSwitchingPoint as is_switch_point, "
                + " p.created_by, p.remark,p.pole_no, "
                + " p.gps_code,p.max_avg_lux_level,p.min_avg_lux_level, "
                + "  p.avg_lux_level,p.standard_lux_level,p.is_working, "
                + " m.mounting_type,p.active,  p.pole_id, "
                + "   spd.switching_point_name,   '' AS switching_load "
                + "  FROM pole p "
                + " LEFT JOIN pole_type pt ON p.pole_type_id = pt.pole_type_id "
                + " LEFT JOIN mounting_type m ON p.mounting_type_id = m.mounting_type_id "
                + "    LEFT JOIN switching_point_detail spd ON p.switching_point_detail_id = "
                + "  spd.switching_point_detail_id AND p.switching_rev_no = spd.switching_rev_no "
                + " left join feeder f  on spd.feeder_id=f.feeder_id, "
              + " area a, road r, traffic_type t, road_category rc, road_use ru, ward w, city cty "
               //   + " area a, road r, traffic_type t, road_category rc, road_use ru, ward_m w, city cty "
                + " where p.active = 'Y' AND "
                + " p.area_id = a.area_id And "
               + " a.ward_id = w.ward_id AND "
             //   + " a.ward_id_m = w.ward_id_m AND "
                + " w.city_id = cty.city_id AND "
                + " p.road_id = r.road_id And "
                + " p.road_rev_no = r.road_rev_no And "
                + " r.road_use_id = ru.road_use_id And "
                + " r.category_id = rc.category_id And "
                + " p.traffic_type_id = t.traffic_type_id AND "
                + " IF('" + searchPoleType + "' = '', p.pole_id LIKE '%%', pt.pole_type =? ) "
                + " AND IF('" + searchMountingType + "' = '', p.pole_id LIKE '%%', m.mounting_type =? ) "
                + " AND IF('" + searchSwitchingPoint + "' = '', spd.switching_point_name LIKE '%%', spd.switching_point_name =? ) "
                + " AND IF('" + searchPoleNo + "' = '', p.pole_no LIKE '%%', p.pole_no ='"+searchPoleNo+"' ) "
                + " ORDER BY p.switching_point_detail_id ";
//        String query = "SELECT pt.pole_type,p.pole_rev_no,p.pole_span,p.pole_height,p.mounting_height, DATE_FORMAT(p.created_date,'%d-%m-%Y') AS created_date,"
//                + " p.created_by, p.remark,p.pole_no,p.gps_code,p.max_avg_lux_level,p.min_avg_lux_level,"
//                + " p.avg_lux_level,p.standard_lux_level,p.is_working,"
//                + " m.mounting_type,p.active, "
//                + " p.pole_id, spd.switching_point_name, "
//                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',pltm.quantity)) "
//                + " AS CHAR CHARACTER SET utf8) SEPARATOR '%%') "
//                + " FROM pole_light_type_mapping AS pltm,light_type lt,light_source_type AS l,wattage AS w "
//                + " WHERE pltm.light_type_id=lt.light_type_id AND p.pole_id=pltm.pole_id AND p.pole_rev_no = pltm.pole_rev_no "
//                + " AND lt.wattage_id=w.wattage_id "
//                + " AND lt.source_id=l.source_type_id)AS source_wattage, "
//                + "(select SUM(pltm.quantity * w.wattage_value)  from wattage w, light_type lt, pole_light_type_mapping pltm, pole pp "
//                + " where pltm.light_type_id = lt.light_type_id and "
//                + " lt.wattage_id = w.wattage_id AND pltm.pole_id = pp.pole_id and pp.switching_point_detail_id = p.switching_point_detail_id) AS switching_load"
//                + " FROM pole p, pole_type pt, mounting_type m, switching_point_detail spd "
//                + " WHERE p.pole_type_id = pt.pole_type_id "
//                + " AND p.mounting_type_id = m.mounting_type_id "
//                + " AND p.switching_point_detail_id = spd.switching_point_detail_id "
//                + " AND IF('" + searchPoleType + "' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "
//                + " AND IF('" + searchMountingType + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
//                + " ORDER BY p.switching_point_detail_id ";
//       String query = "SELECT pt.pole_type,p.pole_rev_no,p.pole_span,p.pole_height,p.mounting_height, DATE_FORMAT(p.created_date,'%d-%m-%Y') AS created_date,"
//                + " p.created_by, p.remark,p.pole_no,p.gps_code,p.max_avg_lux_level,p.min_avg_lux_level,"
//                + " p.avg_lux_level,p.standard_lux_level,p.is_working,"
//                + " m.mounting_type,p.active, "
//                + " p.pole_id, spd.switching_point_name, "
//                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',pltm.quantity)) "
//                + " AS CHAR CHARACTER SET utf8) SEPARATOR '%%') "
//                + " FROM pole_light_type_mapping AS pltm,light_type lt,light_source_type AS l,wattage AS w "
//                + " WHERE pltm.light_type_id=lt.light_type_id AND p.pole_id=pltm.pole_id AND p.pole_rev_no = pltm.pole_rev_no "
//                + " AND lt.wattage_id=w.wattage_id "
//                + " AND lt.source_id=l.source_type_id)AS source_wattage, "
//
//                + "(select SUM(pltm.quantity * w.wattage_value)  from wattage w, light_type lt, pole_light_type_mapping pltm, pole pp "
//                + " where pltm.light_type_id = lt.light_type_id and "
//                + " lt.wattage_id = w.wattage_id AND pltm.pole_id = pp.pole_id and pp.switching_point_detail_id = p.switching_point_detail_id) AS switching_load"
//
//                + " FROM pole p, pole_type pt, mounting_type m, switching_point_detail spd "
//                + " WHERE p.pole_type_id = pt.pole_type_id "
//                + " AND p.mounting_type_id = m.mounting_type_id "
//                + " AND p.switching_point_detail_id = spd.switching_point_detail_id "
//                + " AND IF('" + searchPoleType + "' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "
//                + " AND IF('" + searchMountingType + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
//                + " ORDER BY p.switching_point_detail_id ";
        // + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchPoleType);
            pstmt.setString(2, searchMountingType);
            pstmt.setString(3, searchSwitchingPoint);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PoleDetailTypeBean sourceType = new PoleDetailTypeBean();
                sourceType.setPole_id(rset.getInt("pole_id"));
                sourceType.setPole_type(rset.getString("pole_type"));
                sourceType.setPole_no(rset.getString("pole_no"));
                sourceType.setPole_span(rset.getString("pole_span"));
                sourceType.setPole_height(rset.getString("pole_height"));
                sourceType.setMounting_height(rset.getString("Mounting_height"));
                sourceType.setCreated_date(rset.getString("created_date"));
                sourceType.setCreated_by(rset.getString("created_by"));
                sourceType.setRemark(rset.getString("remark"));
                sourceType.setMounting_type(rset.getString("mounting_type"));
                sourceType.setActive(rset.getString("active"));
                sourceType.setSource_wattage(rset.getString("source_wattage"));
                sourceType.setSwitching_point_name(rset.getString("switching_point_name"));
                sourceType.setSwitching_load(rset.getInt("switching_load"));
                sourceType.setArea_name(rset.getString("area_name"));
                sourceType.setRoad_name(rset.getString("road_name"));
                listAll.add(sourceType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

    public int cancelRecord(int pole_id, int pole_rev_no) {
        String query = "UPDATE pole SET active='N' WHERE pole_id = " + pole_id
                + " and pole_rev_no = " + pole_rev_no;
        String query1 = "UPDATE pole_light_type_mapping SET active='N' WHERE pole_id = " + pole_id
                + " and pole_rev_no = " + pole_rev_no;

        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            rowsAffected = connection.prepareStatement(query).executeUpdate();

            if (rowsAffected > 0) {
                rowsAffected = 0;
                rowsAffected = connection.prepareStatement(query1).executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception ee) {
                System.out.println("Exception Occured inside pole_detail_modelccc is " + ee);
            }
            System.out.println("Error: " + e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Exception Occured inside pole_detail_model is " + e);
            }

        }
        if (rowsAffected > 0) {
            message = "Record cancelled successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    /* public int deleteRecord(int pole_type_id) {
    String query = "DELETE FROM pole WHERE pole_id=" + pole_type_id;

    String query1 = "DELETE FROM pole_light_type_mapping where pole_id=" + pole_type_id;
    int rowsAffected = 0;
    try {
    java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
    rowsAffected = pstmt.executeUpdate();
    if(rowsAffected>0){
    pstmt = connection.prepareStatement(query1);
    rowsAffected = pstmt.executeUpdate();
    }
    } catch (Exception e) {
    System.out.println("Error: " + e);
    }
    if (rowsAffected > 0) {
    message = "Record deleted successfully......";
    msgBgColor = COLOR_OK;
    } else {
    message = "Error Record cannot be deleted.....";
    msgBgColor = COLOR_ERROR;
    }
    return rowsAffected;
    }*/
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection TrafficTypeModel:" + e);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
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
