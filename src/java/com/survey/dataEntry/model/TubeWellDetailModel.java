/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import java.sql.Connection;
import com.survey.tableClasses.TubeWellDetailBean;
import com.survey.tableClasses.TubeWellSurveyBean;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author JPSS
 */
public class TubeWellDetailModel {

    private String shouldPoleEnter;
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private String messagee;
    private String msgBgColorr;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
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
            System.out.println("getPoleType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getZoneSearch(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select zone_m "
                       +" from zone_m zm,ward_m wm,area a,tube_well_detail twd "
                       +" where twd.area_id = a.area_id "
                       +" and a.ward_id_m = wm.ward_id_m "
                       +" and wm.zone_id_m = zm.zone_id_m "
                       +" and twd.active='Y' "
                       +" group by zone_m order by zone_m ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone = rset.getString("zone_m");
                if (zone.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Zone Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getDivision(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT division_name FROM division ORDER BY division_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String division_name = rset.getString("division_name");
                if (division_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Division Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getZone(String q) {
        List<String> list = new ArrayList<String>();
//        String query = " SELECT zone FROM zone z"
//                + "  left join  division d on z.division_id=d.division_id WHERE IF('" + division + "' = '', division_name LIKE '%%',division_name=? )"
//                //  + "WHERE division_id = (SELECT division_id FROM division WHERE division_name = ?) "
//                + "ORDER BY zone ";
        String query="select zone_m "
                     +" from zone_m zm";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone = rset.getString("zone_m");
                if (zone.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Zone Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getFeeder(String q, String zone) {
        List<String> list = new ArrayList<String>();
        String query = "  select feeder_name "
                       +" from feeder f "
                      +" where f.zone_id=(select zone_id from zone where zone='"+zone+"') ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String feeder_name = rset.getString("feeder_name");
                if (feeder_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(feeder_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Feeder Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }
    public List<String> getFeederZoneName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "  select zone "
                       +" from zone";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String feeder_name = rset.getString("zone");
                if (feeder_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(feeder_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Feeder Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }


    public List<String> getWard_No(String q, String zone) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ward_no_m FROM ward_m WHERE zone_id_m = (SELECT zone_id_m FROM zone_m zm WHERE zm.zone_m = ?) ORDER BY ward_no_m ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, zone);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_no_m = rset.getString("ward_no_m");
                if (ward_no_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no_m);
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

    public List<String> getPoleNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT pole_no_s FROM tube_well_detail twd "
                       +" where twd.active='Y' "
                       +" GROUP BY pole_no_s ORDER BY pole_no_s ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no = rset.getString("pole_no_s");
                if (pole_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Pole No exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getSwitchingPtName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT tube_well_name FROM tube_well_detail GROUP BY tube_well_name ORDER BY tube_well_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no = rset.getString("tube_well_name");
                if (pole_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Switching Point exists.");
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

    public List<String> getPoleNoSwitch(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT pole_no_s FROM tube_well_detail GROUP BY pole_no_s ORDER BY pole_no_s ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no_s = rset.getString("pole_no_s");
                if (pole_no_s.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_no_s);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Switch Pole exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<TubeWellDetailBean> showAllData(String searchPoleNoSwitch, String searchPoleType, String searchPoleNo, String searchAreaName, String searchRoadName, String service_conn_no_Search, String searchSwitchingPtName, String zone) {
        List<TubeWellDetailBean> listAll = new ArrayList<TubeWellDetailBean>();
        String query = "select f.fuse_type as fuse_type1,f1.fuse_type as fuse_type2,f2.fuse_type as fuse_type3,s.switch_type as auto_switch_type,s1.switch_type as main_switch_type,tube_well_detail_id,"
                + "pole_no_s, GPS_code_s, a.area_name,  tt.traffic_type, t.active,tube_well_rev_no, meter_no_s, ph,"
                + " c.control_mechanism_type,st.starter_type,m.mccb_type as mccb_type1, fuse_quantity, starter_capacity, mccb_quantity,"
                + " tube_well_name, no_of_users, t.longitude,t.lattitude, service_conn_no,t.ivrs_no,measured_load,  "
                + "isOnPole,pole_id,fe.feeder_name, zone,pt.type_of_premsis, fuse1, fuse2, fuse3, mccb1, mccb2, mccb3,"
                + "sm.starter_make,  m1.mccb_type as as mccb_type2, m2.mccb_type as as mccb_type3, main_switch_rating,e.enclosure_type, mccb,"
                + " fuse,rc.category_name,ru.road_use,starter, is_working, me.meter_name from tube_well_detail as t "
                + "left join fuse as f on t.fuse_id1=f.fuse_id  left join fuse as f1 on t.fuse_id2=f1.fuse_id"
                + " left join fuse as f2 on t.fuse_id3=f2.fuse_id left join mccb as m on t.mccb_id1=m.mccb_id"
                + " left join mccb as m1 on t.mccb_id1=m1.mccb_id left join mccb as m2 on t.mccb_id1=m2.mccb_id"
                + " left join switch_type as s on t.auto_switch_type_id=s.switch_type_id left join switch_type as s1 on "
                + "t.main_switch_type_id=s1.switch_type_id left join starter_make as sm on sm.starter_make_id=t.starter_make_id"
                + " left join starter as st on st.starter_id=t.starter_id left join enclosure_type as e on e.enclosure_type_id=t.enclosure_type_id"
                + " left join area as a on t.area_id=a.area_id left join type_of_premises as pt on pt.type_of_premises_id=t.type_of_premises_id"
                + " left join meters as me on me.meter_id=t.meter_id and me.final_revision='VAlID' left join road as r on r.road_id=t.road_id"
                + "  left join traffic_type as tt on tt.traffic_type_id=t.traffic_type_id left join control_mechanism as c on"
                + " c.control_mechanism_id=t.control_mechanism_id left join feeder as fe on fe.feeder_id=t.feeder_id "
                + "left join road_category as rc on rc.category_id=r.category_id"
                + " left join road_use as ru on ru.road_use_id=r.road_use_id and  "
                + "left join zone as z on fe.zone_id=z.zone_id"
                + "  left join division as d on d.division_id = z.division_id"
                + "  left join ward as w on w.ward_id = a.ward_id"
                + "  left join city as cy on cy.city_id = w.city_id and t.active='Y' ";
        //  + "order by s.tube_well_name LIMIT " + lowerLimit + "," + noOfRowsToDisplay;


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, zone);
//            pstmt.setString(2, zone);
//            pstmt.setString(3, searchSwitchingPtName);
//            pstmt.setString(4, searchSwitchingPtName);
//            pstmt.setString(5, searchPoleNo);
//            pstmt.setString(6, searchPoleNo);
//            pstmt.setString(7, service_conn_no_Search);
//            pstmt.setString(8, service_conn_no_Search);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TubeWellDetailBean surveyType = new TubeWellDetailBean();

                surveyType.setDivision(rset.getString("division_name"));
                surveyType.setZone(rset.getString("zone"));
                surveyType.setWard_no(rset.getString("ward_no"));
                surveyType.setMeasured_load(rset.getString("measured_load"));
                surveyType.setCity(rset.getString("city_name"));
                surveyType.setLongitude(rset.getString("longitude"));
                surveyType.setLattitude(rset.getString("lattitude"));
                surveyType.setService_conn_no(rset.getString("service_conn_no"));
                surveyType.setTube_well_detail_id(rset.getInt("tube_well_detail_id"));
                surveyType.setTube_well_name(rset.getString("tube_well_name"));
                surveyType.setFeeder(rset.getString("feeder_name"));
                surveyType.setPole_no_s(rset.getString("pole_no_s"));
                surveyType.setGps_code_s(rset.getString("gps_code_s"));
                surveyType.setIs_working(rset.getString("is_working"));
                surveyType.setActive(rset.getString("active"));
                surveyType.setRemark(rset.getString("remark"));
                surveyType.setIvrs_no(rset.getString("ivrs_no"));
//                surveyType.setCreated_date(rset.getString("created_date"));
//                surveyType.setCreated_by(rset.getString("created_by"));
                surveyType.setSurvey_rev_no(rset.getInt("tube_well_rev_no"));
                surveyType.setMeter_no_s(rset.getString("meter_no_s"));
                surveyType.setPhase(rset.getInt("ph"));
                surveyType.setPole_no(rset.getString("pole_no"));
                // surveyType.setPole_span(rset.getString("pole_span"));
                //  surveyType.setPole_height(rset.getString("pole_height"));
                surveyType.setGps_code(rset.getString("gps_code"));
                surveyType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                surveyType.setArea_name(rset.getString("area_name"));
                surveyType.setRoad_name(rset.getString("road_name"));
                surveyType.setTraffic_type(rset.getString("traffic_type"));
                surveyType.setFuse(rset.getString("fuse"));
                surveyType.setStarter_type(rset.getString("starter"));
                surveyType.setMccb(rset.getString("mccb"));
                surveyType.setFuse_type1(rset.getString("fuse_type1"));
                surveyType.setFuse_type2(rset.getString("fuse_type2"));
                surveyType.setFuse_type3(rset.getString("fuse_type3"));
                surveyType.setMccb_type1(rset.getString("mccb_type1"));
                surveyType.setMccb_type1(rset.getString("mccb_type2"));
                surveyType.setMccb_type1(rset.getString("mccb_type3"));
                surveyType.setFuse1(rset.getString("fuse1"));
                surveyType.setFuse2(rset.getString("fuse2"));
                surveyType.setFuse3(rset.getString("fuse3"));
                surveyType.setMccb1(rset.getString("mccb1"));
                surveyType.setMccb2(rset.getString("mccb2"));
                surveyType.setMccb3(rset.getString("mccb3"));
                surveyType.setFuse_quantity(rset.getString("fuse_quantity"));
                surveyType.setStarter_type(rset.getString("starter_type"));
                surveyType.setStarter_capacity(rset.getString("starter_capacity"));
                surveyType.setMccb_quantity(rset.getString("mccb_quantity"));
                surveyType.setAuto_switch_type(rset.getString("auto_switch_type"));
                surveyType.setMain_switch_type(rset.getString("main_switch_type"));
                surveyType.setMain_switch_reading(rset.getString("main_switch_rating"));
                surveyType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                surveyType.setEnclosure_type(rset.getString("enclosure_type"));

                listAll.add(surveyType);
            }
        } catch (Exception e) {
            System.out.println("Error in  showAllData of Switching point detail is : " + e);
        }
        return listAll;
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<TubeWellDetailBean> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in Switching Point Detail generateMapReport() JRException: " + e);
        }
        return reportInbytes;
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
                list.add("No such Light Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getSourceWattageType ERROR inside SurveyTypeModel - " + e);
        }
        return list;
    }

    public List<String> getAreaName(String q, String ward_no) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT area_name FROM area WHERE ward_id_m = (select ward_id_m from ward_m where ward_no_m = ?) GROUP BY area_name ORDER BY area_name ";
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
            System.out.println("getAreaName ERROR inside SurveyModel - " + e);
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
            System.out.println("getRoadName ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getIvrsNoSearch(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT ivrs_no "
                       +" FROM tube_well_detail twd "
                       +" where twd.active='Y' "
                       +" group by ivrs_no order by ivrs_no";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String service_conn_no = rset.getString("ivrs_no");
                if (service_conn_no.startsWith(q)) {
                    list.add(service_conn_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Service Conn. exists.");
            }
        } catch (Exception e) {
            System.out.println("getservice_conn_no_Search ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getIVRSNo(String q, String pole_no) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT meter_service_number  FROM meters where poll_no='" + pole_no + "' and final_revision='VALID'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String service_conn_no = rset.getString("meter_service_number");
                if (service_conn_no.startsWith(q)) {
                    list.add(service_conn_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Service Conn. exists.");
            }
        } catch (Exception e) {
            System.out.println("getIVRSNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getPole_No(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select pole_no from pole where active='Y'";// "select poll_no from meters as m,org_office as of where final_revision='VALID' and of.org_office_id=m.org_office_id ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String service_conn_no = rset.getString("pole_no");
                if (service_conn_no.startsWith(q)) {
                    list.add(service_conn_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Service Conn. exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getMeterNo(String q, String pole_no) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT meter_name  FROM meters where poll_no='" + pole_no + "' and final_revision='VALID'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String service_conn_no = rset.getString("meter_name");
                if (service_conn_no.startsWith(q)) {
                    list.add(service_conn_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Service Conn. exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getRoadUse(String q,String road_category) {
        List<String> list = new ArrayList<String>();
        //String query = "SELECT ru.road_use FROM road_use ru, road r WHERE r.road_use_id = ru.road_use_id group by ru.road_use order by ru.road_use ";

        String query1="SELECT ru.road_use "
                      +" FROM road_use ru, road r "
                      +" WHERE r.road_use_id = ru.road_use_id "
                      +" and r.category_id=(SELECT category_id FROM road_category WHERE category_name = '"+road_category+"') "
                      +" group by ru.road_use order by ru.road_use ";
        try {
            ResultSet rset = connection.prepareStatement(query1).executeQuery();
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
        String query = "SELECT c.category_name FROM road_category c, road r WHERE r.category_id = c.category_id  group by c.category_name order by c.category_name ";
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

    public List<String> getWardNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ward_no FROM ward GROUP BY ward_no ORDER BY ward_no ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_no = rset.getString("ward_no");
                if (ward_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no);
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

    public List<String> getSwitchingName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT tube_well_name FROM tube_well_detail GROUP BY tube_well_name ORDER BY tube_well_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switch_name = rset.getString("tube_well_name");
                if (switch_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(switch_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("New Switching Point.");
            }
        } catch (Exception e) {
            System.out.println("getWard ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getControlType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT control_mechanism_type FROM control_mechanism GROUP BY control_mechanism_type ORDER BY control_mechanism_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String control_type = rset.getString("control_mechanism_type");
                if (control_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(control_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Control Mechanism exists.");
            }
        } catch (Exception e) {
            System.out.println("getControlNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getLightType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select concat(lst.source_type,'-',w.wattage_value) as light_type from light_type lt, wattage w, light_source_type lst "
                + " where lt.wattage_id = w.wattage_id and lt.source_id = lst.source_type_id ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String light_type = rset.getString("light_type");
                if (light_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(light_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Light Type Exists.");
            }
        } catch (Exception e) {
            System.out.println("getControlNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getFuseType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT fuse_type FROM fuse GROUP BY fuse_type ORDER BY fuse_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String fuse_type = rset.getString("fuse_type");
                if (fuse_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(fuse_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getStarterMake(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT starter_make FROM starter_make GROUP BY starter_make ORDER BY starter_make ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String starter_make = rset.getString("starter_make");
                if (starter_make.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(starter_make);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Contacter Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getContacterType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getMccbType1(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT mccb_type FROM mccb GROUP BY mccb_type ORDER BY mccb_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String mccb_type = rset.getString("mccb_type");
                if (mccb_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(mccb_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Mccb Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getMccbType ERROR inside SurveyModel - " + e);
        }
        return list;
    }
    public String getMccbType(int mccb_id) {
        String mccb_type = "";
        String query = "select mccb_type from mccb where mccb_id='" + mccb_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, mccb_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                mccb_type = rset.getString("mccb_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getMccbType() in surveyModel" + e);
        }
        return mccb_type;
    }

    public List<String> getStarterType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT starter_type FROM starter GROUP BY starter_type ORDER BY starter_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String starter_type = rset.getString("starter_type");
                if (starter_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(starter_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Contacter Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getContacterType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getAutoSwitchType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT st.switch_type FROM switch_type as st,switch as s where s.switch_id-st.switch_id"
                + " and s.switch='auto_switch' GROUP BY switch_type ORDER BY switch_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switch_type = rset.getString("switch_type");
                if (switch_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(switch_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Auto Switch Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getTimerType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getMainSwitchType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT st.switch_type FROM switch_type as st,switch as s where s.switch_id-st.switch_id"
                + " and s.switch='main_switch' GROUP BY switch_type ORDER BY switch_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switch_type = rset.getString("switch_type");
                if (switch_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(switch_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Auto Switch Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getTimerType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getEnclosureType1(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT enclosure_type FROM enclosure_type "
                + "  GROUP BY enclosure_type ORDER BY enclosure_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String enclosure_type = rset.getString("enclosure_type");
                if (enclosure_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(enclosure_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Auto Switch Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getTimerType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public String getEnclosureType(int enclosure_type_id) {
        String switch_type = "";
        String query = "select enclosure_type from enclosure_type where enclosure_type_id='" + enclosure_type_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, mccb_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                switch_type = rset.getString("enclosure_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getMccbType() in surveyModel" + e);
        }
        return switch_type;
    }

    public int getFuseId(String fuse_type) {
        int fuse_id = 0;
        String query = " SELECT fuse_id FROM fuse WHERE fuse_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, fuse_type);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                fuse_id = rset.getInt("fuse_id");
            }    // move cursor from BOR to valid record.

        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getFuseId() Error: " + e);
        }
        return fuse_id;
    }

    public int getContacterId(String starter_type) {
        int starter_id = 0;
        String query = " SELECT starter_id FROM starter WHERE starter_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, starter_type);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next())// move cursor from BOR to valid record.
            {
                starter_id = rset.getInt("starter_id");
            }

        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getContacterId() Error: " + e);
        }
        return starter_id;
    }

    public int getMccbId(String mccb_type) {
        int mccb_id = 0;
        String query = " SELECT mccb_id FROM mccb WHERE mccb_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mccb_type);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                mccb_id = rset.getInt("mccb_id");
            }    // move cursor from BOR to valid record.

        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getMccbId() Error: " + e);
        }
        return mccb_id;
    }

    public int getMeterId(String ivrs_no) {
        int meter_id = 0;
        String query = " SELECT meter_id FROM meters WHERE meter_service_number = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ivrs_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            meter_id = rset.getInt("meter_id");
        } catch (Exception e) {
            System.out.println("TubeWellDetailModel getMeterId() Error: " + e);
        }
        return meter_id;
    }

    public int getPoleId(String pole_no) {
        shouldPoleEnter = "Yes";
        int pole_type_id = 0;
        String query = " SELECT pole_id FROM pole WHERE pole_no = ? and active='Y'";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, pole_no);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                pole_type_id = rset.getInt("pole_id");
            } else {
                shouldPoleEnter = "No";
                pole_type_id = 0;
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getPoleId() Error: " + e);
        }
        return pole_type_id;
    }

    public int getSwitchId(String switch_type) {

        int switch_type_id = 0;
        String query = " SELECT st.switch_type_id FROM switch_type as st,switch as s WHERE  "
                + "s.switch_id=st.switch_id and st.switch_type='" + switch_type + "' group by switch_type_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                switch_type_id = rset.getInt("switch_type_id");
            } else {
                switch_type_id = 0;
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getSwitchId() Error: " + e);
        }
        return switch_type_id;
    }

    public int getEnclosureTypeId(String switch_type) {

        int enclosure_type_id = 0;
        String query = " SELECT enclosure_type_id FROM enclosure_type "
                      +" where enclosure_type='"+switch_type+"'";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                enclosure_type_id = rset.getInt("enclosure_type_id");
            } else {
                enclosure_type_id = 0;
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
        }
        return enclosure_type_id;
    }

    public int getAreaId(String zone, String ward_no, String area_name) {
        int area_id = 0;
//        String query = " SELECT area_id FROM area WHERE area_name like  ? "
//                + "AND  ward_id = (SELECT ward_id FROM ward WHERE ward_no = ? AND city_id = (SELECT city_id FROM city WHERE city_name = ?))";
//
        String query="select area_id "
                     +" from area a,ward_m wm,zone_m zm "
                     +" where a.ward_id_m=wm.ward_id_m "
                     +" and wm.zone_id_m=zm.zone_id_m "
                     +" and zm.zone_m='"+zone+"'"
                     +" and wm.ward_no_m='"+ward_no+"'"
                     +" and a.area_name='"+area_name+"'";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, "%" + area_name);
            //pstmt.setString(2, ward_no);
            //pstmt.setString(3, city);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            area_id = rset.getInt("area_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getAreaId() Error: " + e);
        }
        return area_id;
    }

    public String getRoadId_Rev(String road_name, String road_category, String road_use) {
        String road_id = "";
        String query = " SELECT road_id, road_rev_no FROM road WHERE road_name like ? AND "
                + " category_id = (SELECT category_id FROM road_category WHERE category_name = ? ) AND "
                + " road_use_id = (SELECT road_use_id FROM road_use WHERE road_use =?) ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, "%" + road_name);
            pstmt.setString(2, road_category);
            pstmt.setString(3, road_use);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            road_id = rset.getInt("road_id") + "_" + rset.getInt("road_rev_no");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getRoadId() Error: " + e);
        }
        return road_id;
    }

    public int getFeeder_id(String feeder, String zone, String division) {
        int feeder_id = 0;
        String final_query = "";
        String query1 = "SELECT feeder_id FROM feeder WHERE feeder_name = ?";
        //String query = " SELECT feeder_id FROM feeder WHERE feeder_name = ? AND zone_id = (Select zone_id FROM zone WHERE zone = ? AND division_id = (SELECT division_id FROM division WHERE division_name = ?))";
        try {
//            if (zone.isEmpty() && division.isEmpty()) {
//                final_query = query1;
//            } else {
//                final_query = query;
//            }
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query1);

//            if (zone.isEmpty() || division.isEmpty()) {
//                pstmt.setString(1, feeder);
//            } else {
//                pstmt.setString(1, feeder);
//                pstmt.setString(2, zone);
//                pstmt.setString(3, division);
//            }
            pstmt.setString(1, feeder);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            feeder_id = rset.getInt("feeder_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel feeder_id() Error: " + e);
        }
        return feeder_id;
    }

    public int getRoadId(String road_name, String road_category, String road_use) {
        int road_id = 0;
        String query = " SELECT road_id FROM road WHERE road_name = ? AND "
                + " category_id = (SELECT category_id FROM road_category WHERE category_name = ? ) AND "
                + " road_use_id = (SELECT road_use_id FROM road_use WHERE road_use =?) ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, road_name);
            pstmt.setString(2, road_category);
            pstmt.setString(3, road_use);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            road_id = rset.getInt("road_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getRoadId() Error: " + e);
        }
        return road_id;
    }

    public int getlightTypeID(String bulb_type, String wattage) {
        int light_type_id = 0;
        String query = " SELECT light_type_id  FROM light_type WHERE  "
                + " source_id = (SELECT source_type_id FROM light_source_type WHERE source_type = ? ) AND "
                + " wattage_id = (SELECT wattage_id FROM wattage WHERE wattage_value =?) ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, bulb_type);
            pstmt.setString(2, wattage);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {    // move cursor from BOR to valid record.
                light_type_id = rset.getInt("light_type_id");
            }
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getLightTypeId() Error: " + e);
        }
        return light_type_id;
    }

    public int getMeterID(String ivrs_no, String meter_name, String meter_service_number) {
        int meter_id = 0;
        String query = " SELECT meter_id  FROM meters WHERE  "
                + " ivrs_no = ?  OR meter_name = ? OR meter_service_number = ? GROUP BY meter_id ";

        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ivrs_no);
            pstmt.setString(2, meter_name);
            pstmt.setString(3, meter_service_number);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            meter_id = rset.getInt("meter_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getMeter_ID() Error: " + e);
        }
        return meter_id;
    }

    public int getControlId(String mechanism_name) {
        int control_id = 0;
        String query = " SELECT control_mechanism_id FROM control_mechanism WHERE control_mechanism_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mechanism_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            control_id = rset.getInt("control_mechanism_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getControlId() Error: " + e);
        }
        return control_id;
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

    public int getSwitchingPointId(String tube_well_name, int feeder_id, String switching_pt_code) {
        int switch_id = 0;
        String query = " SELECT switching_point_id FROM switching_point WHERE switching_point = ? AND feeder_id = ? AND switching_pt_code = ?";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, tube_well_name);
            pstmt.setInt(2, feeder_id);
            pstmt.setString(3, switching_pt_code);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            switch_id = rset.getInt("switching_point_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getControlId() Error: " + e);
        }
        return switch_id;
    }

    public int getSurveyId() {
        int survey_id = 0;
        try {
            String query = "select max(tube_well_detail_id) from tube_well_detail ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                survey_id = rs.getInt("max(tube_well_detail_id)");
            }

        } catch (Exception e) {
            System.out.println("Error: SwitchingPointTypeModel getsurveyId():" + e);
        }
        return survey_id;
    }

    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public boolean validationCheck(String serv_conn, String ivrs_no, String pole_no, int swithcing_point_id, int swithcing_point_revNo, String isOnPole) {
        int i = 0;
        boolean isPoleNoExists;
        String query = "select count(*) from tube_well_detail where service_conn_no ='" + serv_conn + "' and ivrs_no = '" + ivrs_no + "' and active = 'Y'";
        String isPoleExistsInPole = "select count(*) from pole where pole_no = ? AND active = 'Y' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                i = rs.getInt("count(*)");
            }
            if (i <= 0 && isOnPole.equals("Y")) {
                isPoleNoExists = checkIfPoleAlreadyExists(isPoleExistsInPole, pole_no, swithcing_point_id, swithcing_point_revNo);
                if (!isPoleNoExists) {
                    i = -2;
                }
            }
        } catch (Exception e) {
            System.out.println("Error : inside Switching point validation check of insert is " + e);
        }
        if (i > 0) {
            if (i == -2) {
                message = "This Pole No. Already Exists";
            } else {
                message = "This Service Conn. Already Exists";
            }
            msgBgColor = COLOR_ERROR;
        }
        return i > 0 ? false : true;
    }

    public boolean validationCheckforRevise(String serv_conn, String ivrs_no, String pole_no, int tube_well_detail_id, int swithcing_point_revNo, String isOnPole) {
        int i = 0;
        boolean isPoleNoExists;
        String query = "select count(*) from tube_well_detail where service_conn_no ='" + serv_conn + "' and active = 'Y' AND ivrs_no ='" + ivrs_no + "' AND tube_well_detail_id !=" + tube_well_detail_id;
        String isPoleExistsInPole = "select count(*) from pole where pole_no = ? and tube_well_detail_id != ?  AND tube_well_rev_no != ?  AND active = 'Y' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                i = rs.getInt("count(*)");
            }
            if (i <= 0 && isOnPole.equals("Y")) {
                isPoleNoExists = checkIfPoleAlreadyExists(isPoleExistsInPole, pole_no, tube_well_detail_id, swithcing_point_revNo);
                if (!isPoleNoExists) {
                    i = -2;
                }
            }
        } catch (Exception e) {
            System.out.println("Error : inside Switching point validation check of insert is " + e);
        }
        if (i > 0) {
            if (i == -2) {
                message = "This Pole No. Already Exists";
            } else {
                message = "This Service Conn. Already Exists";
            }
            msgBgColor = COLOR_ERROR;
        }
        return i > 0 ? false : true;
    }

    public int getTubeWellDetailId() {
        String query;
        int survey_id = 0;
        query = "select MAX(tube_well_detail_id) as id from tube_well_detail where active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                survey_id = rset.getInt("id");
                survey_id = survey_id + 1;
            }

        } catch (Exception ex) {
            System.out.println("Error: getSurveyId() " + ex);
        }
        return survey_id;
    }

    public int insertRecord(TubeWellDetailBean tubeWellTypeBean) {


        String query1 = "INSERT INTO tube_well_detail ("
                + "tube_well_name, pole_no_s, GPS_code_s, area_id, road_id, is_working, traffic_type_id, "
                + " created_by, remark, meter_no_s, ph, control_mechanism_id, fuse,starter, mccb, fuse_quantity, "
                + "  mccb_quantity, no_of_users,longitude, lattitude, service_conn_no, ivrs_no, measured_load, "
                + "feeder_id, isOnPole, road_rev_no,fuse1,fuse2,fuse3,mccb1,mccb2,mccb3,fuse_id1,fuse_id2,fuse_id3,"
                + "starter_id,starter_capacity,starter_make_id,mccb_id1,mccb_id2,mccb_id3,"
                + "auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id,meter_id,tube_well_detail_id,r_phase,b_phase,y_phase,pole_id) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?, ?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";


        String query2 = "INSERT INTO switching_point_light_type_mapping (tube_well_detail_id , light_type_id , no_of_working , no_of_not_working, total_fittings)"
                + " VALUES (?, ?, ?, ?, ?)";

        String query3 = "INSERT INTO pole "
                + " (created_by, pole_no, tube_well_detail_id, tube_well_rev_no, isSwitchingPoint, area_id, road_id, road_rev_no, traffic_type_id) "
                + " VALUES "
                + " (?, ?, ?, 0, 'Yes', ?,?,?,?) ";

        java.sql.Date s_date = null;
        ResultSet rs = null;
        int tube_well_detail_id = getTubeWellDetailId();
        int pole_id = 0;
        int survey_id = getSurveyId();
        int rowsAffected = 0;
        String final_query;

        java.sql.PreparedStatement pstmt = null;
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query1);
//            pstmt.setInt(1, survey_id + 1);
            pstmt.setString(1, tubeWellTypeBean.getTube_well_name());
            pstmt.setString(2, tubeWellTypeBean.getPole_no_s());
            pstmt.setString(3, tubeWellTypeBean.getGps_code_s());
//            if (tubeWellTypeBean.getIsCheckedTrue().equals("Y")) {
//                pstmt.setNull(4, java.sql.Types.INTEGER);
//                pstmt.setNull(5, java.sql.Types.INTEGER);
//                pstmt.setNull(7, java.sql.Types.INTEGER);
//                pstmt.setNull(26, java.sql.Types.INTEGER);
//            } else {
            pstmt.setInt(4, tubeWellTypeBean.getArea_id());
            pstmt.setInt(5, tubeWellTypeBean.getRoad_id());
            pstmt.setInt(7, tubeWellTypeBean.getTraffic_type_id());
            pstmt.setInt(26, tubeWellTypeBean.getRoad_rev_no());
            // }
            pstmt.setString(6, tubeWellTypeBean.getIs_working());
            pstmt.setString(8, tubeWellTypeBean.getCreated_by());
            pstmt.setString(9, tubeWellTypeBean.getRemark());
            if (!tubeWellTypeBean.getMeter_no_s().isEmpty()) {
                pstmt.setString(10, tubeWellTypeBean.getMeter_no_s());
            } else {
                pstmt.setNull(10, java.sql.Types.NULL);
            }
            if (tubeWellTypeBean.getPhase() != 0) {
                pstmt.setInt(11, tubeWellTypeBean.getPhase());
            } else {
                pstmt.setNull(11, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getControl_mechanism_id() != 0) {
                pstmt.setInt(12, tubeWellTypeBean.getControl_mechanism_id());
            } else {
                pstmt.setNull(12, java.sql.Types.INTEGER);
            }
            if (!tubeWellTypeBean.getFuse().isEmpty()) {
                pstmt.setString(13, tubeWellTypeBean.getFuse());
            } else {
                pstmt.setNull(13, java.sql.Types.INTEGER);
            }
            if (!tubeWellTypeBean.getStarter().isEmpty()) {
                pstmt.setString(14, tubeWellTypeBean.getStarter());
            } else {
                pstmt.setNull(13, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMccb().isEmpty()) {
                pstmt.setString(15, tubeWellTypeBean.getMccb());
            } else {
                pstmt.setNull(15, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getFuse_quantity().isEmpty()) {
                pstmt.setString(16, tubeWellTypeBean.getFuse_quantity());
            } else {
                pstmt.setNull(16, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMccb_quantity().isEmpty()) {
                pstmt.setString(17, tubeWellTypeBean.getMccb_quantity());
            } else {
                pstmt.setNull(17, java.sql.Types.NULL);
            }
            if (tubeWellTypeBean.getNo_of_poles() != 0) {
                pstmt.setInt(18, tubeWellTypeBean.getNo_of_poles());
            } else {
                pstmt.setNull(18, java.sql.Types.INTEGER);
            }
            if (!tubeWellTypeBean.getLongitude().isEmpty()) {
                pstmt.setString(19, tubeWellTypeBean.getLongitude());
            } else {
                pstmt.setNull(19, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getLattitude().isEmpty()) {
                pstmt.setString(20, tubeWellTypeBean.getLattitude());
            } else {
                pstmt.setNull(20, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getService_conn_no().isEmpty()) {
                pstmt.setString(21, tubeWellTypeBean.getService_conn_no());
            } else {
                pstmt.setNull(21, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getIvrs_no().isEmpty()) {
                pstmt.setString(22, tubeWellTypeBean.getIvrs_no());
            } else {
                pstmt.setNull(22, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMeasured_load().isEmpty()) {
                pstmt.setString(23, tubeWellTypeBean.getMeasured_load());
            } else {
                pstmt.setNull(23, java.sql.Types.NULL);
            }

            pstmt.setInt(24, tubeWellTypeBean.getFeeder_id());
            pstmt.setString(25, tubeWellTypeBean.getIsCheckedTrue());
            if (!tubeWellTypeBean.getFuse1().isEmpty()) {
                pstmt.setString(27, tubeWellTypeBean.getFuse1());
            } else {
                pstmt.setNull(27, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getFuse2().isEmpty()) {
                pstmt.setString(28, tubeWellTypeBean.getFuse2());
            } else {
                pstmt.setNull(28, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getFuse3().isEmpty()) {
                pstmt.setString(29, tubeWellTypeBean.getFuse3());
            } else {
                pstmt.setNull(29, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMccb1().isEmpty()) {
                pstmt.setString(30, tubeWellTypeBean.getMccb1());
            } else {
                pstmt.setNull(30, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMccb2().isEmpty()) {
                pstmt.setString(31, tubeWellTypeBean.getMccb2());
            } else {
                pstmt.setNull(31, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMccb3().isEmpty()) {
                pstmt.setString(32, tubeWellTypeBean.getMccb3());
            } else {
                pstmt.setNull(32, java.sql.Types.NULL);
            }
            if (tubeWellTypeBean.getFuse_id1() != 0) {
                pstmt.setInt(33, tubeWellTypeBean.getFuse_id1());
            } else {
                pstmt.setNull(33, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getFuse_id2() != 0) {
                pstmt.setInt(34, tubeWellTypeBean.getFuse_id2());
            } else {
                pstmt.setNull(34, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getFuse_id3() != 0) {
                pstmt.setInt(35, tubeWellTypeBean.getFuse_id3());
            } else {
                pstmt.setNull(35, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getStarter_id() != 0) {
                pstmt.setInt(36, tubeWellTypeBean.getStarter_id());
            } else {
                pstmt.setNull(36, java.sql.Types.INTEGER);
            }
            if (!tubeWellTypeBean.getStarter_capacity().isEmpty()) {
                pstmt.setString(37, tubeWellTypeBean.getStarter_capacity());
            } else {
                pstmt.setNull(37, java.sql.Types.NULL);
            }
            if (tubeWellTypeBean.getStarter_make_id() != 0) {
                pstmt.setInt(38, tubeWellTypeBean.getStarter_make_id());
            } else {
                pstmt.setNull(38, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getMccb_id1() != 0) {
                pstmt.setInt(39, tubeWellTypeBean.getMccb_id1());
            } else {
                pstmt.setNull(39, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getMccb_id2() != 0) {
                pstmt.setInt(40, tubeWellTypeBean.getMccb_id2());
            } else {
                pstmt.setNull(40, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getMccb_id3() != 0) {
                pstmt.setInt(41, tubeWellTypeBean.getMccb_id3());
            } else {
                pstmt.setNull(41, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getAuto_switch_type_id() != 0) {
                pstmt.setInt(42, tubeWellTypeBean.getAuto_switch_type_id());
            } else {
                pstmt.setNull(42, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getMain_switch_type_id() != 0) {
                pstmt.setInt(43, tubeWellTypeBean.getMain_switch_type_id());
            } else {
                pstmt.setNull(43, java.sql.Types.INTEGER);
            }
            if (!tubeWellTypeBean.getMain_switch_reading().isEmpty()) {
                pstmt.setString(44, tubeWellTypeBean.getMain_switch_reading());
            } else {
                pstmt.setNull(44, java.sql.Types.NULL);
            }
            if (tubeWellTypeBean.getEnclosure_type_id() != 0) {
                pstmt.setInt(45, tubeWellTypeBean.getEnclosure_type_id());
            } else {
                pstmt.setNull(45, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getMeter_id() != 0) {
                pstmt.setInt(46, tubeWellTypeBean.getMeter_id());
            } else {
                pstmt.setNull(46, java.sql.Types.INTEGER);
            }
            pstmt.setInt(47, tube_well_detail_id);
            pstmt.setString(48, tubeWellTypeBean.getR_phase());
            pstmt.setString(49, tubeWellTypeBean.getB_phase());
            pstmt.setString(50, tubeWellTypeBean.getY_phase());
            pstmt.setInt(51, tubeWellTypeBean.getPole_id());
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
//                rs = pstmt.getGeneratedKeys();
//                while (rs.next()) {
//                    tube_well_detail_id = rs.getInt(1);
//                }
//                // rowsAffected = 0;
//                pstmt.close();
//                pstmt = connection.prepareStatement(query2);
//                int light_type_idArr[] = tubeWellTypeBean.getLight_type_id_M();
//                String working[] = tubeWellTypeBean.getWorking();
//                String n_working[] = tubeWellTypeBean.getN_working();
//                String quantity[] = tubeWellTypeBean.getQuantity();
////                for (int i = 0; i < light_type_idArr.length; i++) {
////
////                    if (light_type_idArr[i] != 0) {
////                        pstmt.setInt(1, tube_well_detail_id);
////                        pstmt.setInt(2, light_type_idArr[i]);
////                        pstmt.setInt(3, Integer.parseInt(working[i].trim()));
////                        pstmt.setInt(4, Integer.parseInt(n_working[i].trim()));
////                        pstmt.setInt(5, Integer.parseInt(quantity[i].trim()));
////                        rowsAffected = pstmt.executeUpdate();
////                    } else {
////
////                        break;
////                    }
////                }
//                if (rowsAffected > 0) {
//                    if (!tubeWellTypeBean.getPole_no().equals("NoPoleEntry")) {
//                        pstmt = connection.prepareStatement(query3);
//                        pstmt.setInt(1, 1);
//                        pstmt.setString(2, tubeWellTypeBean.getPole_no());
//                        pstmt.setInt(3, tube_well_detail_id);
//                        pstmt.setInt(4, tubeWellTypeBean.getArea_id());
//                        pstmt.setInt(5, tubeWellTypeBean.getRoad_id());
//                        pstmt.setInt(6, tubeWellTypeBean.getRoad_rev_no());
//                        pstmt.setInt(7, tubeWellTypeBean.getTraffic_type_id());
//
//                        rowsAffected = pstmt.executeUpdate();
//                        if (rowsAffected > 0) {
//                            connection.commit();
//                            /*      rs = pstmt.getGeneratedKeys();
//                            while (rs.next()) {
//                            pole_id = rs.getInt(1);
//                            }
//                            rowsAffected = 0;
//                            pstmt.close();
//                            pstmt = connection.prepareStatement(query4);
//                            pstmt.setInt(1, pole_id);
//                            pstmt.setInt(2, tube_well_detail_id);
//                            rowsAffected = pstmt.executeUpdate();
//                            if (rowsAffected > 0) {
//                            connection.commit();
//                            } else {
//                            connection.rollback();
//                            }  */
//                        } else {
//                            connection.rollback();
//                        }
//                    } else {
//                        connection.commit();
//                    }
//                } else {
//                    connection.rollback();
//                }
                connection.commit();
            } else {
                connection.rollback();
            }

        } catch (Exception e) {

            try {
                System.out.println("exception occured during inserting in switching point detail :  " + e);
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(TubeWellDetailModel.class.getName()).log(Level.SEVERE, null, ex);
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

    public int getStarterId(String starter_type) {
        int starter_id = 0;
        String query = "select starter_id from starter where starter_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, starter_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                starter_id = rset.getInt("starter_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return starter_id;
    }

    public int updateIVRSRecord(TubeWellDetailBean tubeWellTypeBean) {
        int rowsAffected = 0;
        String[] switching_point_id = tubeWellTypeBean.getSwitching_point_id_for_update();
        String[] hidden_field = tubeWellTypeBean.getHidden_field();
        String[] ivrs_no = tubeWellTypeBean.getIvrs_no_edit();

        String query = "update tube_well_detail set ivrs_no = ? where tube_well_detail_id = ?";
        for (int i = 0; i < switching_point_id.length; i++) {
            if (hidden_field[i].equals("1")) {
                try {
                    java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, ivrs_no[i].trim());
                    pstmt.setInt(2, Integer.parseInt(switching_point_id[i]));
                    rowsAffected = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Error while IVRS updating record...." + e);
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

    public int updateRecord(TubeWellDetailBean tubeWellTypeBean) {
        int rowsAffected = 0;


        String updateSPD = "UPDATE tube_well_detail SET pole_no_s=?, gps_code_s=?, area_id=?, road_id=?, is_working=?, "
                + " traffic_type_id=? , created_by=? , remark=? , meter_no_s=? , ph=?, control_mechanism_id=? , fuse=? , starter=? , mccb=?  "
                + " starter_id=? , fuse_quantity=? , mccb_quantity=? , longitude=? , lattitude=? , measured_load=? , feeder_id=? , isOnPole=? , "
                + " no_of_poles=? , service_conn_no=? , ivrs_no=? , tube_well_name=?,fuse1=?,fuse2=?,fuse3=?,mccb1=?,mccb2=?,mccb3=?,fuse_id1=?,fuse_id2=?,"
                + "fuse_id3=?,mccb_id1=?,mccb_id2=?,mccb_id3=?,starter_capacity=?,starter_make=?,auto_switch_type_id=?,main_switch_type_id=?"
                + ",main_switch_reading=?,enclosure_type_id=?  WHERE  tube_well_detail_id = ? AND tube_well_rev_no = ?";
        String updateMapping = "UPDATE switching_point_light_type_mapping SET light_type_id=?, no_of_working=?, no_of_not_working=?, total_fittings=?"
                + " WHERE tube_well_detail_id = ? AND tube_well_rev_no = ? AND light_type_id = ?";
        String insertIntoMap = "INSERT INTO  switching_point_light_type_mapping (light_type_id, no_of_working, no_of_not_working, total_fittings, tube_well_detail_id, tube_well_rev_no)"
                + " VALUES (?,?,?,?,?,?)";
        String checkPole = "SELECT count(*) FROM pole WHERE tube_well_detail_id = ? AND tube_well_rev_no = ? AND isSwitchingPoint = 'Yes' AND active = 'Y'";
        String updatePole1 = " UPDATE pole SET  pole_no = ? , isSwitchingPoint = 'No' WHERE tube_well_detail_id = ? AND tube_well_rev_no = ? AND isSwitchingPoint = 'Yes'";
        String updatePole2 = " UPDATE pole SET pole_no = ?  WHERE tube_well_detail_id = ? AND tube_well_rev_no = ?  AND isSwitchingPoint = 'Yes'";
        String insertPole1 = "INSERT INTO pole "
                + " (created_by, pole_no, tube_well_detail_id, tube_well_rev_no, isSwitchingPoint, area_id, road_id, road_rev_no, traffic_type_id) "
                + " VALUES "
                + " (?, ?, ?, 0, 'Yes', ?,?,?,?) ";
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(updateSPD);
            pstmt.setString(1, tubeWellTypeBean.getPole_no_s());
            pstmt.setString(2, tubeWellTypeBean.getGps_code_s());
            pstmt.setInt(3, tubeWellTypeBean.getArea_id());
            pstmt.setInt(4, tubeWellTypeBean.getRoad_id());
            pstmt.setString(5, tubeWellTypeBean.getIs_working());
            pstmt.setInt(6, tubeWellTypeBean.getTraffic_type_id());
            pstmt.setString(7, tubeWellTypeBean.getCreated_by());
            pstmt.setString(8, tubeWellTypeBean.getRemark());
            pstmt.setString(9, tubeWellTypeBean.getMeter_no_s());
            pstmt.setInt(10, tubeWellTypeBean.getPhase());
            pstmt.setInt(11, tubeWellTypeBean.getControl_mechanism_id());
            pstmt.setString(12, tubeWellTypeBean.getFuse());
            pstmt.setString(13, tubeWellTypeBean.getStarter_type());
            pstmt.setString(14, tubeWellTypeBean.getMccb());
            pstmt.setInt(15, tubeWellTypeBean.getStarter_id());
            pstmt.setString(16, tubeWellTypeBean.getFuse_quantity());
            pstmt.setString(17, tubeWellTypeBean.getMccb_quantity());
            pstmt.setString(18, tubeWellTypeBean.getLongitude());
            pstmt.setString(19, tubeWellTypeBean.getLattitude());
            pstmt.setString(20, tubeWellTypeBean.getMeasured_load());
            pstmt.setInt(21, tubeWellTypeBean.getFeeder_id());
            pstmt.setString(22, tubeWellTypeBean.getIsCheckedTrue());
            pstmt.setInt(23, tubeWellTypeBean.getNo_of_poles());
            pstmt.setString(24, tubeWellTypeBean.getService_conn_no());
            pstmt.setString(25, tubeWellTypeBean.getIvrs_no());
            pstmt.setString(26, tubeWellTypeBean.getTube_well_name());
            pstmt.setString(27, tubeWellTypeBean.getFuse1());
            pstmt.setString(28, tubeWellTypeBean.getFuse2());
            pstmt.setString(29, tubeWellTypeBean.getFuse3());
            pstmt.setString(30, tubeWellTypeBean.getMccb1());
            pstmt.setString(31, tubeWellTypeBean.getMccb2());
            pstmt.setString(32, tubeWellTypeBean.getMccb3());
            pstmt.setInt(33, tubeWellTypeBean.getFuse_id1());
            pstmt.setInt(34, tubeWellTypeBean.getFuse_id2());
            pstmt.setInt(35, tubeWellTypeBean.getFuse_id3());
            pstmt.setInt(36, tubeWellTypeBean.getMccb_id1());
            pstmt.setInt(37, tubeWellTypeBean.getMccb_id2());
            pstmt.setInt(38, tubeWellTypeBean.getMccb_id3());
            pstmt.setString(39, tubeWellTypeBean.getStarter_capacity());
            pstmt.setString(40, tubeWellTypeBean.getStarter_make());
            pstmt.setInt(41, tubeWellTypeBean.getAuto_switch_type_id());
            pstmt.setInt(42, tubeWellTypeBean.getMain_switch_type_id());
            pstmt.setString(43, tubeWellTypeBean.getMain_switch_reading());
            pstmt.setInt(44, tubeWellTypeBean.getEnclosure_type_id());
            pstmt.setInt(45, tubeWellTypeBean.getTube_well_detail_id());
            pstmt.setInt(46, tubeWellTypeBean.getTube_well_rev_no());

            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                pstmt.close();
                rowsAffected = 0;
                int loop = 0;
                int flag = 0;
                pstmt = connection.prepareStatement(updateMapping);
                int light_type_idArr[] = tubeWellTypeBean.getLight_type_id_M();  // currently entered light types
                String working[] = tubeWellTypeBean.getWorking();
                String n_working[] = tubeWellTypeBean.getN_working();
                String quantity[] = tubeWellTypeBean.getQuantity();
                String old_light_type_ids[] = tubeWellTypeBean.getLight_type_ids();  // previously entered light types
                for (int i = 0; i < light_type_idArr.length; i++) {
                    if (light_type_idArr[i] != 0) {
                        loop = i + 1;
                        if (Integer.parseInt(old_light_type_ids[i]) != 0) {
                            pstmt.setInt(1, light_type_idArr[i]);
                            pstmt.setInt(2, Integer.parseInt(working[i].trim()));
                            pstmt.setInt(3, Integer.parseInt(n_working[i].trim()));
                            pstmt.setInt(4, Integer.parseInt(quantity[i].trim()));
                            pstmt.setInt(5, tubeWellTypeBean.getTube_well_detail_id());
                            pstmt.setInt(6, tubeWellTypeBean.getTube_well_rev_no());
                            pstmt.setInt(7, Integer.parseInt(old_light_type_ids[i]));
                            rowsAffected = pstmt.executeUpdate();
                            flag = flag + rowsAffected;
                        } else {
                            rowsAffected = 0;
                            pstmt.close();
                            pstmt = connection.prepareStatement(insertIntoMap);
                            pstmt.setInt(1, light_type_idArr[i]);
                            pstmt.setInt(2, Integer.parseInt(working[i].trim()));
                            pstmt.setInt(3, Integer.parseInt(n_working[i].trim()));
                            pstmt.setInt(4, Integer.parseInt(quantity[i].trim()));
                            pstmt.setInt(5, tubeWellTypeBean.getTube_well_detail_id());
                            pstmt.setInt(6, tubeWellTypeBean.getTube_well_rev_no());
                            rowsAffected = pstmt.executeUpdate();
                            flag = flag + rowsAffected;
                        }

                    } else {
                        break;
                    }
                }
                if (flag == loop) { // ---

                    pstmt.close();
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(checkPole);
                    pstmt.setInt(1, tubeWellTypeBean.getTube_well_detail_id());
                    pstmt.setInt(2, tubeWellTypeBean.getTube_well_rev_no());
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        rowsAffected = rs.getInt("count(*)");
                    }
                    if (rowsAffected == 0) {
                        pstmt.close();
                        rowsAffected = 0;
                        if (!tubeWellTypeBean.getPole_no().equals("NoPoleEntry")) {    // if formerly switching point was on wall but not it has been mounted on pole
                            pstmt = connection.prepareStatement(insertPole1);
                            pstmt.setInt(1, 1);
                            pstmt.setString(2, tubeWellTypeBean.getPole_no());
                            pstmt.setInt(3, tubeWellTypeBean.getTube_well_detail_id());
                            pstmt.setInt(4, tubeWellTypeBean.getArea_id());
                            pstmt.setInt(5, tubeWellTypeBean.getRoad_id());
                            pstmt.setInt(6, tubeWellTypeBean.getRoad_rev_no());
                            pstmt.setInt(7, tubeWellTypeBean.getTraffic_type_id());
                            rowsAffected = pstmt.executeUpdate();
                            if (rowsAffected > 0) {
                                connection.commit();
                            } else {
                                connection.rollback();
                            }
                        }
                    } else {
                        pstmt.close();
                        rowsAffected = 0;
                        if (tubeWellTypeBean.getIsCheckedTrue().equals("Y")) {  // if u change da pole no of the switching point
                            pstmt = connection.prepareStatement(updatePole2);
                            pstmt.setString(1, tubeWellTypeBean.getPole_no());
                            pstmt.setInt(2, tubeWellTypeBean.getTube_well_detail_id());
                            pstmt.setInt(3, tubeWellTypeBean.getTube_well_rev_no());
                            rowsAffected = pstmt.executeUpdate();
                        } else {
                            pstmt = connection.prepareStatement(updatePole1);  // if u remove switching point from a pole
                            pstmt.setString(1, tubeWellTypeBean.getPole_no());
                            pstmt.setInt(2, tubeWellTypeBean.getTube_well_detail_id());
                            pstmt.setInt(3, tubeWellTypeBean.getTube_well_rev_no());
                            rowsAffected = pstmt.executeUpdate();
                        }
                        if (rowsAffected > 0) {
                            connection.commit();
                        } else {
                            connection.rollback();
                        }
                    }

                } else { //---
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (Exception e) {
            System.out.println("Error while updating record...." + e);
            try {
                connection.rollback();
            } catch (Exception ee) {
                System.out.println("Error while updating record...." + ee);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(TubeWellDetailModel.class.getName()).log(Level.SEVERE, null, ex);
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

    public int reviseRecord(TubeWellDetailBean tubeWellTypeBean) {
//        String query1 = " INSERT INTO tube_well_detail ("
//                + "tube_well_name, pole_no_s, GPS_code_s, area_id, road_id, is_working, traffic_type_id, "
//                + " created_by, remark, meter_no_s, ph, control_mechanism_id, fuse,starter, mccb, fuse_quantity, "
//                + "  mccb_quantity, no_of_users,longitude, lattitude, service_conn_no, ivrs_no, measured_load, "
//                + "feeder_id, isOnPole, road_rev_no,fuse1,fuse2,fuse3,mccb1,mccb2,mccb3,fuse_id1,fuse_id2,fuse_id3,"
//                + "starter_id,starter_capacity,starter_make_id,mccb_id1,mccb_id2,mccb_id3,"
//                + "auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id,meter_id,tube_well_detail_id,tube_well_rev_no) "
//                + " Select tube_well_detail_id ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?, ?,"
//                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,"
//                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,tube_well_detail_id from tube_well_detail where tube_well_detail_id=? and tube_well_rev_no=? ";

        String query1 = "INSERT INTO  tube_well_detail ("
                + "tube_well_name, pole_no_s, GPS_code_s, area_id, road_id, is_working, traffic_type_id, "
                + " created_by, remark, meter_no_s, ph, control_mechanism_id, fuse,starter, mccb, fuse_quantity, "
                + "  mccb_quantity, no_of_users,longitude, lattitude, service_conn_no, ivrs_no, measured_load, "
                + "feeder_id, isOnPole, road_rev_no,fuse1,fuse2,fuse3,mccb1,mccb2,mccb3,fuse_id1,fuse_id2,fuse_id3,"
                + "starter_id,starter_capacity,starter_make_id,mccb_id1,mccb_id2,mccb_id3,"
                + "auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id,meter_id,tube_well_detail_id,tube_well_rev_no,r_phase,b_phase,y_phase,meter_status,meter_address) "
                + " values(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?, ?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?) ";


        String query2 = " UPDATE tube_well_detail SET active='N'"
                + " WHERE tube_well_detail_id = ? and tube_well_rev_no = ? ";
        String query33 = "INSERT INTO switching_point_light_type_mapping( tube_well_detail_id, light_type_id, no_of_working, no_of_not_working, total_fittings,"
                + " tube_well_rev_no ) "
                + " VALUES (?,?,?,?,?,?) ";
        String query3 = "UPDATE switching_point_light_type_mapping  SET active = 'N' "
                + " WHERE tube_well_detail_id = ?  ";
        String query4 = " UPDATE pole SET active = 'N', pole_no = ? ,tube_well_detail_id = ?, tube_well_rev_no = ? , isSwitchingPoint = 'No' WHERE tube_well_detail_id = ?  AND tube_well_rev_no = ? AND isSwitchingPoint = 'Yes'";
        String query5 = " UPDATE pole SET pole_no = ?, tube_well_detail_id = ? ,tube_well_rev_no = ?, area_id=? , road_id= ?, road_rev_no=?, traffic_type_id=?  "
                + " WHERE tube_well_detail_id = ?  AND tube_well_rev_no = ?  AND isSwitchingPoint = 'Yes'";
        String query6 = "SELECT count(*) FROM pole WHERE tube_well_detail_id = ? AND tube_well_rev_no = ? AND isSwitchingPoint = 'Yes' AND active = 'Y'";
        String query7 = "UPDATE junction SET tube_well_rev_no = ? WHERE tube_well_detail_id = ? AND active = 'Y'";
        String isPoleExistsInPole = "select count(*) from pole where pole_no = ? and tube_well_detail_id != ?  AND tube_well_rev_no != ?  AND active = 'Y' ";

        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, tubeWellTypeBean.getTube_well_name());
            pstmt.setString(2, tubeWellTypeBean.getPole_no_s());
            pstmt.setString(3, tubeWellTypeBean.getGps_code_s());
//            if (tubeWellTypeBean.getIsCheckedTrue().equals("Y")) {
//                pstmt.setNull(4, java.sql.Types.INTEGER);
//                pstmt.setNull(5, java.sql.Types.INTEGER);
//                pstmt.setNull(7, java.sql.Types.INTEGER);
//                pstmt.setNull(26, java.sql.Types.INTEGER);
//            } else {
            pstmt.setInt(4, tubeWellTypeBean.getArea_id());
            pstmt.setInt(5, tubeWellTypeBean.getRoad_id());
            pstmt.setInt(7, tubeWellTypeBean.getTraffic_type_id());
            pstmt.setInt(26, tubeWellTypeBean.getRoad_rev_no());
            //   }
            pstmt.setString(6, tubeWellTypeBean.getIs_working());
            pstmt.setString(8, tubeWellTypeBean.getCreated_by());
            pstmt.setString(9, tubeWellTypeBean.getRemark());
            if (!tubeWellTypeBean.getMeter_no_s().isEmpty()) {
                pstmt.setString(10, tubeWellTypeBean.getMeter_no_s());
            } else {
                pstmt.setNull(10, java.sql.Types.NULL);
            }
            if (tubeWellTypeBean.getPhase() != 0) {
                pstmt.setInt(11, tubeWellTypeBean.getPhase());
            } else {
                pstmt.setNull(11, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getControl_mechanism_id() != 0) {
                pstmt.setInt(12, tubeWellTypeBean.getControl_mechanism_id());
            } else {
                pstmt.setNull(12, java.sql.Types.INTEGER);
            }
            if (!tubeWellTypeBean.getFuse().isEmpty()) {
                pstmt.setString(13, tubeWellTypeBean.getFuse());
            } else {
                pstmt.setNull(13, java.sql.Types.INTEGER);
            }
            if (!tubeWellTypeBean.getStarter().isEmpty()) {
                pstmt.setString(14, tubeWellTypeBean.getStarter());
            } else {
                pstmt.setNull(13, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMccb().isEmpty()) {
                pstmt.setString(15, tubeWellTypeBean.getMccb());
            } else {
                pstmt.setNull(15, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getFuse_quantity().isEmpty()) {
                pstmt.setString(16, tubeWellTypeBean.getFuse_quantity());
            } else {
                pstmt.setNull(16, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMccb_quantity().isEmpty()) {
                pstmt.setString(17, tubeWellTypeBean.getMccb_quantity());
            } else {
                pstmt.setNull(17, java.sql.Types.NULL);
            }
            if (tubeWellTypeBean.getNo_of_poles() != 0) {
                pstmt.setInt(18, tubeWellTypeBean.getNo_of_poles());
            } else {
                pstmt.setNull(18, java.sql.Types.INTEGER);
            }
            if (!tubeWellTypeBean.getLongitude().isEmpty()) {
                pstmt.setString(19, tubeWellTypeBean.getLongitude());
            } else {
                pstmt.setNull(19, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getLattitude().isEmpty()) {
                pstmt.setString(20, tubeWellTypeBean.getLattitude());
            } else {
                pstmt.setNull(20, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getService_conn_no().isEmpty()) {
                pstmt.setString(21, tubeWellTypeBean.getService_conn_no());
            } else {
                pstmt.setNull(21, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getIvrs_no().isEmpty()) {
                pstmt.setString(22, tubeWellTypeBean.getIvrs_no());
            } else {
                pstmt.setNull(22, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMeasured_load().isEmpty()) {
                pstmt.setString(23, tubeWellTypeBean.getMeasured_load());
            } else {
                pstmt.setNull(23, java.sql.Types.NULL);
            }

            pstmt.setInt(24, tubeWellTypeBean.getFeeder_id());////////////////////////////
            pstmt.setString(25, tubeWellTypeBean.getIsCheckedTrue());
            if (!tubeWellTypeBean.getFuse1().isEmpty()) {
                pstmt.setString(27, tubeWellTypeBean.getFuse1());
            } else {
                pstmt.setNull(27, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getFuse2().isEmpty()) {
                pstmt.setString(28, tubeWellTypeBean.getFuse2());
            } else {
                pstmt.setNull(28, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getFuse3().isEmpty()) {
                pstmt.setString(29, tubeWellTypeBean.getFuse3());
            } else {
                pstmt.setNull(29, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMccb1().isEmpty()) {
                pstmt.setString(30, tubeWellTypeBean.getMccb1());
            } else {
                pstmt.setNull(30, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMccb2().isEmpty()) {
                pstmt.setString(31, tubeWellTypeBean.getMccb2());
            } else {
                pstmt.setNull(31, java.sql.Types.NULL);
            }
            if (!tubeWellTypeBean.getMccb3().isEmpty()) {
                pstmt.setString(32, tubeWellTypeBean.getMccb3());
            } else {
                pstmt.setNull(32, java.sql.Types.NULL);
            }
            if (tubeWellTypeBean.getFuse_id1() != 0) {
                pstmt.setInt(33, tubeWellTypeBean.getFuse_id1());
            } else {
                pstmt.setNull(33, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getFuse_id2() != 0) {
                pstmt.setInt(34, tubeWellTypeBean.getFuse_id2());
            } else {
                pstmt.setNull(34, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getFuse_id3() != 0) {
                pstmt.setInt(35, tubeWellTypeBean.getFuse_id3());
            } else {
                pstmt.setNull(35, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getStarter_id() != 0) {
                pstmt.setInt(36, tubeWellTypeBean.getStarter_id());
            } else {
                pstmt.setNull(36, java.sql.Types.INTEGER);
            }
            if (!tubeWellTypeBean.getStarter_capacity().isEmpty()) {
                pstmt.setString(37, tubeWellTypeBean.getStarter_capacity());
            } else {
                pstmt.setNull(37, java.sql.Types.NULL);
            }
            if (tubeWellTypeBean.getStarter_make_id() != 0) {
                pstmt.setInt(38, tubeWellTypeBean.getStarter_make_id());
            } else {
                pstmt.setNull(38, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getMccb_id1() != 0) {
                pstmt.setInt(39, tubeWellTypeBean.getMccb_id1());
            } else {
                pstmt.setNull(39, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getMccb_id2() != 0) {
                pstmt.setInt(40, tubeWellTypeBean.getMccb_id2());
            } else {
                pstmt.setNull(40, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getMccb_id3() != 0) {
                pstmt.setInt(41, tubeWellTypeBean.getMccb_id3());
            } else {
                pstmt.setNull(41, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getAuto_switch_type_id() != 0) {
                pstmt.setInt(42, tubeWellTypeBean.getAuto_switch_type_id());
            } else {
                pstmt.setNull(42, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getMain_switch_type_id() != 0) {
                pstmt.setInt(43, tubeWellTypeBean.getMain_switch_type_id());
            } else {
                pstmt.setNull(43, java.sql.Types.INTEGER);
            }
            if (!tubeWellTypeBean.getMain_switch_reading().isEmpty()) {
                pstmt.setString(44, tubeWellTypeBean.getMain_switch_reading());
            } else {
                pstmt.setNull(44, java.sql.Types.NULL);
            }
            if (tubeWellTypeBean.getEnclosure_type_id() != 0) {
                pstmt.setInt(45, tubeWellTypeBean.getEnclosure_type_id());
            } else {
                pstmt.setNull(45, java.sql.Types.INTEGER);
            }
            if (tubeWellTypeBean.getMeter_id() != 0) {
                pstmt.setInt(46, tubeWellTypeBean.getMeter_id());
            } else {
                pstmt.setNull(46, java.sql.Types.INTEGER);
            }
            //pstmt.setInt(47, tubeWellTypeBean.getTube_well_rev_no() + 1);
            pstmt.setInt(47, tubeWellTypeBean.getTube_well_detail_id());
            pstmt.setInt(48, tubeWellTypeBean.getTube_well_rev_no()+1);

            pstmt.setString(49, tubeWellTypeBean.getR_phase());
            pstmt.setString(50, tubeWellTypeBean.getB_phase());
            pstmt.setString(51, tubeWellTypeBean.getY_phase());
            pstmt.setString(52, tubeWellTypeBean.getMeter_status());

            pstmt.setString(53,tubeWellTypeBean.getMeter_address());


            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }
            if (rowsAffected > 0) {
                pstmt.close();
                rowsAffected = 0;
                pstmt = connection.prepareStatement(query2);
                pstmt.setInt(1, tubeWellTypeBean.getTube_well_detail_id());
                pstmt.setInt(2, tubeWellTypeBean.getTube_well_rev_no());
                rowsAffected = pstmt.executeUpdate();
            }
//                if(rowsAffected > 0){}
//                if (rowsAffected > 0) {
//                    pstmt.close();
//                    rowsAffected = 0;
//                    pstmt = connection.prepareStatement(query3);
//                    pstmt.setInt(1, tubeWellTypeBean.getTube_well_detail_id());
//                    rowsAffected = pstmt.executeUpdate();
////                    if (rowsAffected > 0) {
////                        pstmt.close();
////                        rowsAffected = 0;
////                        pstmt = connection.prepareStatement(query7);
////                        pstmt.setInt(1, tubeWellTypeBean.getTube_well_rev_no() + 1);
////                        pstmt.setInt(2, tubeWellTypeBean.getTube_well_detail_id());
////                        rowsAffected = pstmt.executeUpdate();
//
//                    if (rowsAffected > 0) {
//                        pstmt.close();
//                        rowsAffected = 0;
//                        int loop = 0;
//                        int flag = 0;
//                        pstmt = connection.prepareStatement(query33);
//                        int light_type_idArr[] = tubeWellTypeBean.getLight_type_id_M();
//                        String working[] = tubeWellTypeBean.getWorking();
//                        String n_working[] = tubeWellTypeBean.getN_working();
//                        String quantity[] = tubeWellTypeBean.getQuantity();
//                        for (int i = 0; i < light_type_idArr.length; i++) {
//
//                            if (light_type_idArr[i] != 0) {
//                                loop = i + 1;
//                                pstmt.setInt(1, tubeWellTypeBean.getTube_well_detail_id());
//                                pstmt.setInt(2, light_type_idArr[i]);
//                                pstmt.setInt(3, Integer.parseInt(working[i].trim()));
//                                pstmt.setInt(4, Integer.parseInt(n_working[i].trim()));
//                                pstmt.setInt(5, Integer.parseInt(quantity[i].trim()));
//                                pstmt.setInt(6, tubeWellTypeBean.getTube_well_rev_no() + 1);
//
//                                rowsAffected = pstmt.executeUpdate();
//                                flag = flag + rowsAffected;
//                            } else {
//
//                                break;
//                            }
//                        }
//                        if (flag == loop) {
//                            pstmt.close();
//                            rowsAffected = 0;
//                            pstmt = connection.prepareStatement(query6);
//                            pstmt.setInt(1, tubeWellTypeBean.getTube_well_detail_id());
//                            pstmt.setInt(2, tubeWellTypeBean.getTube_well_rev_no());
//                            ResultSet rs = pstmt.executeQuery();
//                            if (rs.next()) {
//                                rowsAffected = rs.getInt("count(*)");
//                            }
//                            if (rowsAffected == 0) {
//                                connection.commit();
//                                rowsAffected = 1;
//                            } else {
//                                pstmt.close();
//                                rowsAffected = 0;
//                                if (tubeWellTypeBean.getIsCheckedTrue().equals("Y")) {
//                                    if (checkIfPoleAlreadyExists(isPoleExistsInPole, tubeWellTypeBean.getPole_no(), tubeWellTypeBean.getTube_well_detail_id(), tubeWellTypeBean.getTube_well_rev_no())) {
//                                        pstmt = connection.prepareStatement(query5);
//                                        pstmt.setString(1, tubeWellTypeBean.getPole_no());
//                                        pstmt.setInt(2, tubeWellTypeBean.getTube_well_detail_id());
//                                        pstmt.setInt(3, tubeWellTypeBean.getTube_well_rev_no() + 1);
//                                        pstmt.setInt(4, tubeWellTypeBean.getArea_id());
//                                        pstmt.setInt(5, tubeWellTypeBean.getRoad_id());
//                                        pstmt.setInt(6, tubeWellTypeBean.getRoad_rev_no());
//                                        pstmt.setInt(7, tubeWellTypeBean.getTraffic_type_id());
//                                        pstmt.setInt(8, tubeWellTypeBean.getTube_well_detail_id());
//                                        pstmt.setInt(9, tubeWellTypeBean.getTube_well_rev_no());
//                                        rowsAffected = pstmt.executeUpdate();
//                                    } else {
//                                        rowsAffected = -2;
//                                    }
//                                } else {
//                                    pstmt = connection.prepareStatement(query4);
//                                    pstmt.setString(1, tubeWellTypeBean.getPole_no());
//                                    pstmt.setInt(2, tubeWellTypeBean.getTube_well_detail_id());
//                                    pstmt.setInt(3, tubeWellTypeBean.getTube_well_rev_no() + 1);
//                                    pstmt.setInt(4, tubeWellTypeBean.getTube_well_detail_id());
//                                    pstmt.setInt(5, tubeWellTypeBean.getTube_well_rev_no());
//                                    rowsAffected = pstmt.executeUpdate();
//                                }
//                                if (rowsAffected > 0) {
//                                    connection.commit();
//                                } else {
//                                    connection.rollback();
//                                }
//                            }
//                        } else {
//                            connection.rollback();
//                        }
//                    } else {
//                        connection.rollback();
//                    }
////                    } else {
////                        connection.rollback();
////                    }
//                } else {
//                    connection.rollback();
//                }
//            } else {
//                connection.rollback();
//            }
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(TubeWellDetailModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("error while updating record........." + e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(TubeWellDetailModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rowsAffected > 0) {
            message = "Record Revised successfully.";
            msgBgColor = COLOR_OK;
        } else {
            if (rowsAffected == -2) {
                message = "This Pole No. Already Exists In Pole Data";
            } else {
                message = "Cannot revise the record, some error.";
            }
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
    public boolean checkIfPoleAlreadyExists(String q, String pole_no, int tube_well_detail_id, int switching_point_detail_revNo) throws Exception {
        int count = 0;
        String query = q;
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, pole_no);
        // stmt.setInt(2, tube_well_detail_id);
        // stmt.setInt(3, switching_point_detail_revNo);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        count = Integer.parseInt(rs.getString(1));
        return count > 0 ? false : true;
    }

    public int getNoOfRows(String searchPoleNo, String service_conn_no_Search, String searchSwitchingPtName,String zone_search) {

        String query = " select count(*)  "
                +" from tube_well_detail as t "
             +" left join fuse as f on t.fuse_id1=f.fuse_id  left join fuse as f1 on t.fuse_id2=f1.fuse_id "
             +" left join fuse as f2 on t.fuse_id3=f2.fuse_id left join mccb as m on t.mccb_id1=m.mccb_id "
             +" left join mccb as m1 on t.mccb_id2=m1.mccb_id left join mccb as m2 on t.mccb_id3=m2.mccb_id "
             +" left join switch_type as s on t.auto_switch_type_id=s.switch_type_id left join switch_type as s1 on "
             +" t.main_switch_type_id=s1.switch_type_id left join starter_make as sm on sm.starter_make_id=t.starter_make_id "
             +" left join starter as st on st.starter_id=t.starter_id left join enclosure_type as e on e.enclosure_type_id=t.enclosure_type_id "
             +" left join type_of_premises as pt on pt.type_of_premises_id=t.type_of_premises_id "
             +" left join meters as me on me.meter_id=t.meter_id and me.final_revision='VAlID' left join road as r on r.road_id=t.road_id "
             +" left join traffic_type as tt on tt.traffic_type_id=t.traffic_type_id left join control_mechanism as c on "
             +" c.control_mechanism_id=t.control_mechanism_id left join feeder as fe on fe.feeder_id=t.feeder_id "
             +" left join zone as fed_zone on fe.zone_id = fed_zone.zone_id "
             +" left join road_category as rc on rc.category_id=r.category_id "
             +" left join road_use as ru on ru.road_use_id=r.road_use_id "
             +" left join area as a on t.area_id=a.area_id "
             +" left join ward_m as w on a.ward_id_m = w.ward_id_m "
             +" left join zone_m as z on w.zone_id_m=z.zone_id_m "
             +" where t.active='Y' "

             + " AND IF ( '" + zone_search + "'  = '' , z.zone_m like '%%' , z.zone_m = ? ) "
             + " AND IF ( '" + service_conn_no_Search + "'  = '' , t.ivrs_no like '%%' , t.ivrs_no = ? ) "
             + " AND IF ( '" + searchSwitchingPtName + "'  = '' , t.tube_well_name like '%%' , t.tube_well_name = ? ) "
             + " AND IF ( '" + searchPoleNo + "'  = '' , t.pole_no_s like '%%' , t.pole_no_s = ? ) ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, zone_search);
            stmt.setString(2, service_conn_no_Search);
            stmt.setString(3, searchSwitchingPtName);
            stmt.setString(4, searchPoleNo);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows switching point detail model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<TubeWellDetailBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchPoleNo, String service_conn_no_Search, String searchSwitchingPtName, String zone_search) {
        List<TubeWellDetailBean> list = new ArrayList<TubeWellDetailBean>();


//        String query = "select t.r_phase,t.b_phase,t.y_phase, z.zone,d.division_name,w.ward_no,cy.city_name, f.fuse_type as fuse_type1,f1.fuse_type as fuse_type2,"
//                + "f2.fuse_type as fuse_type3,s.switch_type as auto_switch_type,s1.switch_type as main_switch_type,tube_well_detail_id,"
//                + "pole_no_s, GPS_code_s, a.area_name,  tt.traffic_type, t.active,tube_well_rev_no, meter_no_s, ph,"
//                + " c.control_mechanism_type,st.starter_type,m.mccb_type as mccb_type1, fuse_quantity, starter_capacity, mccb_quantity,"
//                + " tube_well_name, no_of_users, t.longitude,t.lattitude, service_conn_no,t.ivrs_no,measured_load,  "
//                + "isOnPole,pole_id,fe.feeder_name, pt.type_of_premsis, fuse1, fuse2, fuse3, mccb1, mccb2, mccb3,"
//                + "sm.starter_make,  m1.mccb_type  as mccb_type2, m2.mccb_type as  mccb_type3, main_switch_rating,e.enclosure_type, mccb,"
//                + " fuse,rc.category_name,ru.road_use,starter, is_working, me.meter_name,r.road_name from tube_well_detail as t "
//                + "left join fuse as f on t.fuse_id1=f.fuse_id  left join fuse as f1 on t.fuse_id2=f1.fuse_id"
//                + " left join fuse as f2 on t.fuse_id3=f2.fuse_id left join mccb as m on t.mccb_id1=m.mccb_id"
//                + " left join mccb as m1 on t.mccb_id1=m1.mccb_id left join mccb as m2 on t.mccb_id1=m2.mccb_id"
//                + " left join switch_type as s on t.auto_switch_type_id=s.switch_type_id left join switch_type as s1 on "
//                + "t.main_switch_type_id=s1.switch_type_id left join starter_make as sm on sm.starter_make_id=t.starter_make_id"
//                + " left join starter as st on st.starter_id=t.starter_id left join enclosure_type as e on e.enclosure_type_id=t.enclosure_type_id"
//                + " left join area as a on t.area_id=a.area_id left join type_of_premises as pt on pt.type_of_premises_id=t.type_of_premises_id"
//                + " left join meters as me on me.meter_id=t.meter_id and me.final_revision='VAlID' left join road as r on r.road_id=t.road_id"
//                + "  left join traffic_type as tt on tt.traffic_type_id=t.traffic_type_id left join control_mechanism as c on"
//                + " c.control_mechanism_id=t.control_mechanism_id left join feeder as fe on fe.feeder_id=t.feeder_id "
//                + "left join road_category as rc on rc.category_id=r.category_id"
//                + " left join road_use as ru on ru.road_use_id=r.road_use_id "
//                + "left join zone as z on fe.zone_id=z.zone_id"
//                + "  left join division as d on d.division_id = z.division_id"
//                + "  left join ward as w on w.ward_id = a.ward_id"
//                + "  left join city as cy on cy.city_id = w.city_id where t.active='Y' "
//                + "order by t.tube_well_detail_id LIMIT " + lowerLimit + "," + noOfRowsToDisplay;

        String query="select t.r_phase,t.b_phase,t.y_phase, z.zone_m,w.ward_no_m,w.ward_name,a.area_name, f.fuse_type as fuse_type1,f1.fuse_type as fuse_type2, "
             +" f2.fuse_type as fuse_type3,s.switch_type as auto_switch_type,s1.switch_type as main_switch_type,tube_well_detail_id, "
             +" pole_no_s, GPS_code_s, a.area_name,  tt.traffic_type, t.active,tube_well_rev_no, meter_no_s, ph, "
             +" c.control_mechanism_type,st.starter_type,m.mccb_type as mccb_type1, fuse_quantity, starter_capacity, mccb_quantity, "
             +" tube_well_name, no_of_users, t.longitude,t.lattitude, service_conn_no,t.ivrs_no,measured_load, "
             +" isOnPole,pole_id,fed_zone.zone as feeder_zone,fe.feeder_name, pt.type_of_premsis, fuse1, fuse2, fuse3, mccb1, mccb2, mccb3, "
             +" sm.starter_make,  m1.mccb_type  as mccb_type2, m2.mccb_type as  mccb_type3, main_switch_rating,e.enclosure_type, mccb, "
             +" fuse,rc.category_name,ru.road_use,starter, is_working, me.meter_name,r.road_name,t.meter_address "
             +" from tube_well_detail as t "
             +" left join fuse as f on t.fuse_id1=f.fuse_id  left join fuse as f1 on t.fuse_id2=f1.fuse_id "
             +" left join fuse as f2 on t.fuse_id3=f2.fuse_id left join mccb as m on t.mccb_id1=m.mccb_id "
             +" left join mccb as m1 on t.mccb_id2=m1.mccb_id left join mccb as m2 on t.mccb_id3=m2.mccb_id "
             +" left join switch_type as s on t.auto_switch_type_id=s.switch_type_id left join switch_type as s1 on "
             +" t.main_switch_type_id=s1.switch_type_id left join starter_make as sm on sm.starter_make_id=t.starter_make_id "
             +" left join starter as st on st.starter_id=t.starter_id left join enclosure_type as e on e.enclosure_type_id=t.enclosure_type_id "
             +" left join type_of_premises as pt on pt.type_of_premises_id=t.type_of_premises_id "
             +" left join meters as me on me.meter_id=t.meter_id and me.final_revision='VAlID' left join road as r on r.road_id=t.road_id "
             +" left join traffic_type as tt on tt.traffic_type_id=t.traffic_type_id left join control_mechanism as c on "
             +" c.control_mechanism_id=t.control_mechanism_id left join feeder as fe on fe.feeder_id=t.feeder_id "
             +" left join zone as fed_zone on fe.zone_id = fed_zone.zone_id "
             +" left join road_category as rc on rc.category_id=r.category_id "
             +" left join road_use as ru on ru.road_use_id=r.road_use_id "
             +" left join area as a on t.area_id=a.area_id "
             +" left join ward_m as w on a.ward_id_m = w.ward_id_m "
             +" left join zone_m as z on w.zone_id_m=z.zone_id_m "
             +" where t.active='Y' "

             + " AND IF ( '" + zone_search + "'  = '' , z.zone_m like '%%' , z.zone_m = ? ) "
             + " AND IF ( '" + service_conn_no_Search + "'  = '' , t.ivrs_no like '%%' , t.ivrs_no = ? ) "
             + " AND IF ( '" + searchSwitchingPtName + "'  = '' , t.tube_well_name like '%%' , t.tube_well_name = ? ) "
             + " AND IF ( '" + searchPoleNo + "'  = '' , t.pole_no_s like '%%' , t.pole_no_s = ? ) "

             +" order by t.tube_well_detail_id LIMIT " + lowerLimit + "," + noOfRowsToDisplay;


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, zone_search);
             pstmt.setString(2, service_conn_no_Search);

            pstmt.setString(3, searchSwitchingPtName);

            pstmt.setString(4, searchPoleNo);
           


            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TubeWellDetailBean surveyType = new TubeWellDetailBean();
                surveyType.setR_phase(rset.getString("r_phase"));
                surveyType.setB_phase(rset.getString("b_phase"));
                surveyType.setY_phase(rset.getString("y_phase"));
                //surveyType.setDivision(rset.getString("division_name"));
                surveyType.setZone(rset.getString("zone_m"));
                surveyType.setWard_no(rset.getString("ward_no_m"));
                surveyType.setWard_name(rset.getString("ward_name"));
                surveyType.setMeasured_load(rset.getString("measured_load"));
                //surveyType.setCity(rset.getString("city_name"));
                surveyType.setLongitude(rset.getString("longitude"));
                surveyType.setLattitude(rset.getString("lattitude"));
                surveyType.setService_conn_no(rset.getString("service_conn_no"));
                surveyType.setTube_well_detail_id(rset.getInt("tube_well_detail_id"));
                surveyType.setTube_well_name(rset.getString("tube_well_name"));

                surveyType.setFeeder_zone(rset.getString("feeder_zone"));
                surveyType.setFeeder(rset.getString("feeder_name"));
                surveyType.setPole_no_s(rset.getString("pole_no_s"));
                surveyType.setGps_code_s(rset.getString("gps_code_s"));
                surveyType.setIs_working(rset.getString("is_working"));
                surveyType.setActive(rset.getString("active"));
                // surveyType.setRemark(rset.getString("remark"));
                surveyType.setIvrs_no(rset.getString("ivrs_no"));
//                surveyType.setCreated_date(rset.getString("created_date"));
//                surveyType.setCreated_by(rset.getString("created_by"));
                surveyType.setSurvey_rev_no(rset.getInt("tube_well_rev_no"));

                
                surveyType.setTube_well_rev_no(rset.getInt("tube_well_rev_no"));
                surveyType.setMeter_no_s(rset.getString("meter_no_s"));
                surveyType.setPhase(rset.getInt("ph"));
                //   surveyType.setPole_no(rset.getString("pole_no"));
                // surveyType.setPole_span(rset.getString("pole_span"));
                //  surveyType.setPole_height(rset.getString("pole_height"));
                surveyType.setGps_code(rset.getString("GPS_code_s"));
                surveyType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                surveyType.setArea_name(rset.getString("area_name"));
                surveyType.setRoad_name(rset.getString("road_name"));
                surveyType.setTraffic_type(rset.getString("traffic_type"));
                surveyType.setFuse(rset.getString("fuse"));
                surveyType.setStarter_type(rset.getString("starter"));
                surveyType.setMccb(rset.getString("mccb"));
                surveyType.setFuse_type1(rset.getString("fuse_type1"));
                surveyType.setFuse_type2(rset.getString("fuse_type2"));
                surveyType.setFuse_type3(rset.getString("fuse_type3"));
                surveyType.setMccb_type1(rset.getString("mccb_type1"));
                surveyType.setMccb_type2(rset.getString("mccb_type2"));
                surveyType.setMccb_type3(rset.getString("mccb_type3"));
                surveyType.setFuse1(rset.getString("fuse1"));
                surveyType.setFuse2(rset.getString("fuse2"));
                surveyType.setFuse3(rset.getString("fuse3"));
                surveyType.setMccb1(rset.getString("mccb1"));
                surveyType.setMccb2(rset.getString("mccb2"));
                surveyType.setMccb3(rset.getString("mccb3"));
                surveyType.setFuse_quantity(rset.getString("fuse_quantity"));
                surveyType.setStarter_type(rset.getString("starter_type"));
                surveyType.setStarter_capacity(rset.getString("starter_capacity"));
                surveyType.setMccb_quantity(rset.getString("mccb_quantity"));
                surveyType.setAuto_switch_type(rset.getString("auto_switch_type"));
                surveyType.setMain_switch_type(rset.getString("main_switch_type"));
                surveyType.setMain_switch_reading(rset.getString("main_switch_rating"));
                surveyType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                surveyType.setEnclosure_type(rset.getString("enclosure_type"));
                surveyType.setType_of_premises(rset.getString("type_of_premsis"));
                surveyType.setRoad_name(rset.getString("road_name"));

                surveyType.setMeter_address(rset.getString("meter_address"));



                //  surveyType.setMax_avg_lux_level(rset.getString("max_avg_lux_level"));
                // surveyType.setMin_avg_lux_level(rset.getString("min_avg_lux_level"));
                //  surveyType.setAvg_lux_level(rset.getString("avg_lux_level"));
                //  surveyType.setStandard_avg_lux_level(rset.getString("standard_lux_level"));
                //    surveyType.setMounting_height(rset.getString("mounting_height"));
                //    surveyType.setMap_id(rset.getString("map_id"));

                //  surveyType.setSource_wattage(rset.getString("source_wattage"));
                //  surveyType.setIsworking(rset.getString("Isworking"));
                //   surveyType.setMounting_type(rset.getString("mounting_type"));

                surveyType.setNo_of_poles(rset.getInt("no_of_users"));
                surveyType.setRoad_use(rset.getString("road_use"));
                surveyType.setRoad_category(rset.getString("category_name"));
                surveyType.setIsCheckedTrue(rset.getString("isOnPole"));

                list.add(surveyType);

            }
        } catch (Exception e) {
            System.out.println("Error in Show data of switching point detail is : " + e);
        }
        return list;
    }

    public TubeWellDetailBean showDetailData(String ivrs_no) {
        TubeWellDetailBean surveyType = new TubeWellDetailBean();
//        String query = "select t.r_phase,t.b_phase,t.y_phase, z.zone,d.division_name,w.ward_no,cy.city_name, f.fuse_type as fuse_type1,f1.fuse_type as fuse_type2,"
//                + "f2.fuse_type as fuse_type3,s.switch_type as auto_switch_type,s1.switch_type as main_switch_type,tube_well_detail_id,"
//                + "pole_no_s, GPS_code_s, a.area_name,  tt.traffic_type, t.active,tube_well_rev_no, meter_no_s, ph,"
//                + " c.control_mechanism_type,st.starter_type,m.mccb_type as mccb_type1, fuse_quantity, starter_capacity, mccb_quantity,"
//                + " tube_well_name, no_of_users, t.longitude,t.lattitude, service_conn_no,t.ivrs_no,measured_load,  "
//                + "isOnPole,pole_id,fe.feeder_name, pt.type_of_premsis, fuse1, fuse2, fuse3, mccb1, mccb2, mccb3,"
//                + "sm.starter_make,  m1.mccb_type  as mccb_type2, m2.mccb_type as  mccb_type3, main_switch_rating,e.enclosure_type, mccb,"
//                + " fuse,rc.category_name,ru.road_use,starter, is_working, me.meter_name,r.road_name from tube_well_detail as t "
//                + "left join fuse as f on t.fuse_id1=f.fuse_id  left join fuse as f1 on t.fuse_id2=f1.fuse_id"
//                + " left join fuse as f2 on t.fuse_id3=f2.fuse_id left join mccb as m on t.mccb_id1=m.mccb_id"
//                + " left join mccb as m1 on t.mccb_id1=m1.mccb_id left join mccb as m2 on t.mccb_id1=m2.mccb_id"
//                + " left join switch_type as s on t.auto_switch_type_id=s.switch_type_id left join switch_type as s1 on "
//                + "t.main_switch_type_id=s1.switch_type_id left join starter_make as sm on sm.starter_make_id=t.starter_make_id"
//                + " left join starter as st on st.starter_id=t.starter_id left join enclosure_type as e on e.enclosure_type_id=t.enclosure_type_id"
//                + " left join area as a on t.area_id=a.area_id left join type_of_premises as pt on pt.type_of_premises_id=t.type_of_premises_id"
//                + " left join meters as me on me.meter_id=t.meter_id and me.final_revision='VAlID' left join road as r on r.road_id=t.road_id"
//                + "  left join traffic_type as tt on tt.traffic_type_id=t.traffic_type_id left join control_mechanism as c on"
//                + " c.control_mechanism_id=t.control_mechanism_id left join feeder as fe on fe.feeder_id=t.feeder_id "
//                + "left join road_category as rc on rc.category_id=r.category_id"
//                + " left join road_use as ru on ru.road_use_id=r.road_use_id "
//                + "left join zone as z on fe.zone_id=z.zone_id"
//                + "  left join division as d on d.division_id = z.division_id"
//                + "  left join ward as w on w.ward_id = a.ward_id"
//                + "  left join city as cy on cy.city_id = w.city_id  where t.ivrs_no='"+ivrs_no+"' and t.active='Y' LIMIT 1";

                String query="select t.r_phase,t.b_phase,t.y_phase,meter_status,z.zone_m,w.ward_no_m,w.ward_name,a.area_name, f.fuse_type as fuse_type1,f1.fuse_type as fuse_type2, "
                   +" f2.fuse_type as fuse_type3,s.switch_type as auto_switch_type,s1.switch_type as main_switch_type,tube_well_detail_id, "
                   +" pole_no_s, GPS_code_s, a.area_name,  tt.traffic_type, t.active,tube_well_rev_no, meter_no_s, ph, "
                   +" c.control_mechanism_type,st.starter_type,m.mccb_type as mccb_type1, fuse_quantity, starter_capacity, mccb_quantity, "
                   +" tube_well_name, no_of_users, t.longitude,t.lattitude, service_conn_no,t.ivrs_no,measured_load, "
                   +" isOnPole,pole_id,fed_zone.zone as feeder_zone,fe.feeder_name, pt.type_of_premsis, fuse1, fuse2, fuse3, mccb1, mccb2, mccb3, "
                   +" sm.starter_make,  m1.mccb_type  as mccb_type2, m2.mccb_type as  mccb_type3, main_switch_rating,e.enclosure_type, mccb, "
                   +" fuse,rc.category_name,ru.road_use,starter, is_working, me.meter_name,r.road_name,t.meter_address "
                   +" from tube_well_detail as t "
                   +" left join fuse as f on t.fuse_id1=f.fuse_id  left join fuse as f1 on t.fuse_id2=f1.fuse_id "
                   +" left join fuse as f2 on t.fuse_id3=f2.fuse_id left join mccb as m on t.mccb_id1=m.mccb_id "
                   +" left join mccb as m1 on t.mccb_id2=m1.mccb_id left join mccb as m2 on t.mccb_id3=m2.mccb_id "
                   +" left join switch_type as s on t.auto_switch_type_id=s.switch_type_id left join switch_type as s1 on "
                   +" t.main_switch_type_id=s1.switch_type_id left join starter_make as sm on sm.starter_make_id=t.starter_make_id "
                   +" left join starter as st on st.starter_id=t.starter_id left join enclosure_type as e on e.enclosure_type_id=t.enclosure_type_id "
                   +" left join type_of_premises as pt on pt.type_of_premises_id=t.type_of_premises_id "
                   +" left join meters as me on me.meter_id=t.meter_id and me.final_revision='VAlID' left join road as r on r.road_id=t.road_id "
                   +" left join traffic_type as tt on tt.traffic_type_id=t.traffic_type_id left join control_mechanism as c on "
                   +" c.control_mechanism_id=t.control_mechanism_id left join feeder as fe on fe.feeder_id=t.feeder_id "
                   +" left join zone as fed_zone on fe.zone_id = fed_zone.zone_id "
                   +" left join road_category as rc on rc.category_id=r.category_id "
                   +" left join road_use as ru on ru.road_use_id=r.road_use_id "

                   +" left join area as a on t.area_id=a.area_id "
                   +" left join ward_m as w on a.ward_id_m = w.ward_id_m "
                   +" left join zone_m as z on w.zone_id_m=z.zone_id_m "
                   +" where t.ivrs_no='"+ivrs_no+"' and t.active='Y' LIMIT 1;";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                surveyType.setR_phase(rset.getString("r_phase"));
                surveyType.setB_phase(rset.getString("b_phase"));
                surveyType.setY_phase(rset.getString("y_phase"));

                surveyType.setMeter_status(rset.getString("meter_status"));
                //surveyType.setDivision(rset.getString("division_name"));
                surveyType.setZone(rset.getString("zone_m"));
                surveyType.setWard_no(rset.getString("ward_no_m"));
                surveyType.setMeasured_load(rset.getString("measured_load"));
                //surveyType.setCity(rset.getString("city_name"));
                surveyType.setLongitude(rset.getString("longitude"));
                surveyType.setLattitude(rset.getString("lattitude"));
                surveyType.setService_conn_no(rset.getString("service_conn_no"));
                surveyType.setTube_well_detail_id(rset.getInt("tube_well_detail_id"));
                surveyType.setTube_well_name(rset.getString("tube_well_name"));

                surveyType.setFeeder_zone(rset.getString("feeder_zone"));
                surveyType.setFeeder(rset.getString("feeder_name"));
                surveyType.setPole_no_s(rset.getString("pole_no_s"));
                surveyType.setGps_code_s(rset.getString("gps_code_s"));
                surveyType.setIs_working(rset.getString("is_working"));
                surveyType.setActive(rset.getString("active"));
                // surveyType.setRemark(rset.getString("remark"));
                surveyType.setIvrs_no(rset.getString("ivrs_no"));
//                surveyType.setCreated_date(rset.getString("created_date"));
//                surveyType.setCreated_by(rset.getString("created_by"));
                surveyType.setSurvey_rev_no(rset.getInt("tube_well_rev_no"));
                surveyType.setTube_well_rev_no(rset.getInt("tube_well_rev_no"));
                surveyType.setMeter_no_s(rset.getString("meter_no_s"));
                surveyType.setPhase(rset.getInt("ph"));
                //   surveyType.setPole_no(rset.getString("pole_no"));
                // surveyType.setPole_span(rset.getString("pole_span"));
                //  surveyType.setPole_height(rset.getString("pole_height"));
                surveyType.setGps_code(rset.getString("GPS_code_s"));
                surveyType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                surveyType.setArea_name(rset.getString("area_name"));
                surveyType.setRoad_name(rset.getString("road_name"));
                surveyType.setTraffic_type(rset.getString("traffic_type"));
                surveyType.setFuse(rset.getString("fuse"));
                surveyType.setStarter_type(rset.getString("starter"));
                surveyType.setMccb(rset.getString("mccb"));
                surveyType.setFuse_type1(rset.getString("fuse_type1"));
                surveyType.setFuse_type2(rset.getString("fuse_type2"));
                surveyType.setFuse_type3(rset.getString("fuse_type3"));
                surveyType.setMccb_type1(rset.getString("mccb_type1"));
                surveyType.setMccb_type2(rset.getString("mccb_type2"));
                surveyType.setMccb_type3(rset.getString("mccb_type3"));
                surveyType.setFuse1(rset.getString("fuse1"));
                surveyType.setFuse2(rset.getString("fuse2"));
                surveyType.setFuse3(rset.getString("fuse3"));
                surveyType.setMccb1(rset.getString("mccb1"));
                surveyType.setMccb2(rset.getString("mccb2"));
                surveyType.setMccb3(rset.getString("mccb3"));
                surveyType.setFuse_quantity(rset.getString("fuse_quantity"));
                surveyType.setStarter_type(rset.getString("starter_type"));
                surveyType.setStarter_capacity(rset.getString("starter_capacity"));
                surveyType.setMccb_quantity(rset.getString("mccb_quantity"));
                surveyType.setAuto_switch_type(rset.getString("auto_switch_type"));
                surveyType.setMain_switch_type(rset.getString("main_switch_type"));
                surveyType.setMain_switch_reading(rset.getString("main_switch_rating"));
                surveyType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                surveyType.setEnclosure_type(rset.getString("enclosure_type"));
                surveyType.setType_of_premises(rset.getString("type_of_premsis"));
                surveyType.setRoad_name(rset.getString("road_name"));

                surveyType.setMeter_address(rset.getString("meter_address"));

                surveyType.setStarter_make(rset.getString("starter_make"));


                surveyType.setNo_of_poles(rset.getInt("no_of_users"));
                surveyType.setRoad_use(rset.getString("road_use"));
                surveyType.setRoad_category(rset.getString("category_name"));
                surveyType.setIsCheckedTrue(rset.getString("isOnPole"));



            }
        } catch (Exception e) {
            System.out.println("Error in Show data of switching point detail is : " + e);
        }
        return surveyType;
    }

    public TubeWellSurveyBean getTubeWellSurveyData(String service_conn_no) {
        TubeWellSurveyBean surveyType = new TubeWellSurveyBean();


        String query = null;



//          query = "SELECT s.general_image_details_id,tube_well_survey_id,s.survey_id,t.revison_no,s.survey_rev_no,  meter_no, meter_functional,r_phase, "
//                + "y_phase, b_phase, power, fuse_functional, starter_functional, mccb_functional,   tube_well_survey_rev_no, reason_id, no_of_phase, "
//                + "fuse1, fuse2, fuse3,t.fuse_id1, t.fuse_id2, t.fuse_id3, mccb1, mccb2, mccb3,t.mccb_id1, t.mccb_id2, t.mccb_id3, starter_capacity,"
//                + " if(t.starter_id is null,null,sr.starter_type) as starter_type ,meter_phase, meter_reading,if(t.starter_make_id is null,null,sm.starter_make) as starter_make, "
//                + " auto_switch_type_id, main_switch_type_id, main_switch_rating, enclosure_type_id ,survey_file_no, "
//                 + " s.survey_date as survey_date, survey_page_no,"
//                + " mobile_no, pole_no, pole_rev_no, survey_type, status, image_name, s.lattitude, s.longitude, image_date_time,  data_entry_type_id, video_name, survey_pole_no, "
//                + " t.meter_name_auto, t.service_conn_no, previous_reading, consume_unit, amount "
//                + " FROM tube_well_survey as t "
//                +" left join meters as m ON m.meter_name_auto=t.meter_name_auto AND m.final_revision='VALID' "
//                +" left join feeder as f ON f.feeder_id=m.feeder_id "
//                +" left join (premises_tariff_map as ptm, type_of_premises as top) ON m.premises_tariff_map_id=ptm.premises_tariff_map_id "
//                +" AND top.type_of_premises_id=ptm.type_of_premises_id "
//                +" ,survey as s,starter as sr,starter_make as sm "
//                +" where t.tube_well_survey_id=s.survey_id  and sr.starter_id=t.starter_id and sm.starter_make_id=t.starter_make_id and t.active='Y' and s.status='Y' "
//                +" and t.service_conn_no="+service_conn_no
//                + "  order by tube_well_survey_id desc LIMIT 1";

          query="SELECT s.general_image_details_id,zone_m,ward_no_m,ward_name,area_name,tube_well_survey_id,s.survey_id,t.revison_no,s.survey_rev_no,  meter_no,meter_status, meter_functional,r_phase, "
               +" y_phase, b_phase, power, fuse_functional, starter_functional, mccb_functional,   tube_well_survey_rev_no, reason_id, no_of_phase, "
               +" fuse1, fuse2, fuse3,t.fuse_id1, t.fuse_id2, t.fuse_id3, mccb1, mccb2, mccb3,t.mccb_id1, t.mccb_id2, t.mccb_id3, starter_capacity, "
               +" if(t.starter_id is null,null,sr.starter_type) as starter_type ,meter_phase, meter_reading,if(t.starter_make_id is null,null,sm.starter_make) as starter_make, "
               +" auto_switch_type_id, main_switch_type_id, main_switch_rating, enclosure_type_id ,survey_file_no, "
               +" s.survey_date as survey_date, survey_page_no, "
               +" mobile_no, pole_no, pole_rev_no, survey_type, status, image_name, s.lattitude, s.longitude, image_date_time,  data_entry_type_id, video_name, survey_pole_no, "
               +" t.meter_name_auto, t.service_conn_no, previous_reading, consume_unit, amount,t.meter_address "
               +" FROM tube_well_survey as t "
               +" left join meters as m ON m.meter_name_auto=t.meter_name_auto AND m.final_revision='VALID' "
               +" left join feeder as f ON f.feeder_id=m.feeder_id "

               +" left join area as a on t.area_id=a.area_id "
               +" left join ward_m as w on a.ward_id_m = w.ward_id_m "
               +" left join zone_m as z on w.zone_id_m=z.zone_id_m "

               +" left join (premises_tariff_map as ptm, type_of_premises as top) ON m.premises_tariff_map_id=ptm.premises_tariff_map_id "
               +" AND top.type_of_premises_id=ptm.type_of_premises_id "
               +" ,survey as s,starter as sr,starter_make as sm "
               +" where t.tube_well_survey_id=s.survey_id  and sr.starter_id=t.starter_id and sm.starter_make_id=t.starter_make_id and t.active='Y' and s.status='Y' "
               +" and t.service_conn_no='"+service_conn_no+"' "
               +" order by tube_well_survey_id desc LIMIT 1";



        try {
            PreparedStatement pstmt = connection.prepareStatement(query);


            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                //TubeWellSurveyBean surveyType = new TubeWellSurveyBean();
                //surveyType.setSurvey_meter_no(rset.getString("survey_meter_no"));
                surveyType.setGeneral_image_detials_id(rset.getInt("general_image_details_id"));

                surveyType.setArea_name(rset.getString("area_name"));
                surveyType.setWard_name(rset.getString("ward_name"));
                surveyType.setWard_no_m(rset.getString("ward_no_m"));
                surveyType.setZone_m(rset.getString("zone_m"));

                surveyType.setSurvey_id(rset.getInt("survey_id"));
                surveyType.setTube_well_survey_id(rset.getInt("tube_well_survey_id"));
                surveyType.setTube_well_survey_rev_no(rset.getInt("tube_well_survey_rev_no"));
                surveyType.setSurvey_file_no(rset.getString("survey_file_no"));
                surveyType.setSurvey_date(rset.getString("survey_date"));
                surveyType.setSurvey_page_no(rset.getString("survey_page_no"));
                surveyType.setSurvey_by(rset.getString("mobile_no"));
                surveyType.setPole_no(rset.getString("pole_no"));
                surveyType.setPole_rev_no(rset.getInt("pole_rev_no"));
                surveyType.setSurvey_type(rset.getString("survey_type"));
                // surveyType.setCreated_date(rset.getString("created_date"));
                surveyType.setStatus(rset.getString("status"));
                //   surveyType.setRemark(rset.getString("remark"));
                surveyType.setSurvey_rev_no(rset.getInt("survey_rev_no"));

                surveyType.setNo_of_phase(rset.getInt("no_of_phase"));

                //surveyType.setTube_well_survey_id(rset.getInt("tube_well_survey_id"));
                //  surveyType.setSurvey_id(rset.getInt("survey_id"));
                surveyType.setMeter_no(rset.getString("meter_no"));

                surveyType.setMeter_status(rset.getString("meter_status"));
                surveyType.setMeter_functional(rset.getString("meter_functional"));
                surveyType.setR_phase(rset.getString("r_phase"));
                surveyType.setY_phase(rset.getString("y_phase"));
                surveyType.setB_phase(rset.getString("b_phase"));
                surveyType.setPower(rset.getString("power"));
                surveyType.setFuse_functional(rset.getString("fuse_functional"));
                surveyType.setStarter_functional(rset.getString("starter_functional"));
                surveyType.setMccb_functional(rset.getString("mccb_functional"));
                /// surveyType.setTimer_functional(rset.getString("timer_functional"));
                // surveyType.setCreated_date(rset.getString("created_date"));
                //surveyType.setActive(rset.getString("active"));
                surveyType.setTube_well_survey_rev_no(rset.getInt("tube_well_survey_rev_no"));
                //    surveyType.setCreated_by(rset.getString("created_by"));

                surveyType.setFuse1(rset.getString("fuse1"));
                surveyType.setFuse2(rset.getString("fuse2"));
                surveyType.setFuse3(rset.getString("fuse3"));
                surveyType.setMccb1(rset.getString("mccb1"));
                surveyType.setMccb2(rset.getString("mccb2"));
                surveyType.setMccb3(rset.getString("mccb3"));
                surveyType.setFuse_type1(getFuseTYpe(rset.getInt("fuse_id1")));
                surveyType.setFuse_type2(getFuseTYpe(rset.getInt("fuse_id2")));
                surveyType.setFuse_type3(getFuseTYpe(rset.getInt("fuse_id3")));
                surveyType.setMccb_type1(getMccbType(rset.getInt("mccb_id1")));
                surveyType.setMccb_type2(getMccbType(rset.getInt("mccb_id2")));
                surveyType.setMccb_type3(getMccbType(rset.getInt("mccb_id3")));
                surveyType.setStarter_capacity(rset.getString("starter_capacity"));
                surveyType.setStarter_type(rset.getString("starter_type"));
                surveyType.setStarter_make(rset.getString("starter_make"));
                surveyType.setLatitude(rset.getString("lattitude"));
                surveyType.setLongitude(rset.getString("longitude"));
                surveyType.setImage_name(rset.getString("image_name"));
                surveyType.setSurvey_pole_no(rset.getString("survey_pole_no"));
                surveyType.setAuto_switch_type(getSwitchType(rset.getInt("auto_switch_type_id")));
                surveyType.setMain_switch_type(getSwitchType(rset.getInt("main_switch_type_id")));
                surveyType.setEnclosure_type(getEnclosureType(rset.getInt("enclosure_type_id")));
                surveyType.setMain_switch_rating(rset.getString("main_switch_rating"));
                surveyType.setMeter_reading(rset.getString("meter_reading"));
                surveyType.setMeter_phase(rset.getInt("meter_phase"));
                surveyType.setService_conn_no(rset.getString("service_conn_no"));
                surveyType.setMeter_name_auto(rset.getString("meter_name_auto"));
                surveyType.setPrevious_reading(rset.getDouble("previous_reading"));
                surveyType.setConsume_unit(rset.getDouble("consume_unit"));
                surveyType.setAmount(rset.getDouble("amount"));

                surveyType.setNo_of_phase(rset.getInt("no_of_phase"));

                surveyType.setMeter_address(rset.getString("meter_address"));

//                if (rset.getString("survey_type").equals("pole_type_survey")) {
//                    surveyType.setPole_no(getPole_No(rset.getInt("pole_id"), rset.getInt("pole_rev_no")));
//                } else {
//                    surveyType.setPole_no(getSwitchingTypePole_No(rset.getInt("tube_well_detail_id"), rset.getInt("tube_well_rev_no")));
//                }


                double r_phase = rset.getDouble("r_phase");
                double y_phase = rset.getDouble("y_phase");
                double b_phase = rset.getDouble("b_phase");

                double calculated_power = ((r_phase + y_phase + b_phase) * 220) / 1000;
                double projected_consumption = calculated_power * 12 * 30;//10 is average daily consumption and 30 is days in month
                double c_p = Double.valueOf(new DecimalFormat("#.##").format(calculated_power));
                double p_c = Double.valueOf(new DecimalFormat("#.##").format(projected_consumption));

                surveyType.setCalculated_power(c_p);
                surveyType.setProjected_consumption(p_c);
//                surveyType.setPole_no(rset.getString("pole_no"));
//                surveyType.setSwitching_point_name(rset.getString("pole_no_s"));
//                surveyType.setActive(rset.getString("active"));
//                surveyType.setPole_no(rset.getString("pole_no"));
//                surveyType.setContacter_funactional(rset.getString("starter_functional"));


            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return surveyType;
    }
    public String getFuseTYpe(int fuse_id) {
        String fuse_type = "";
        String query = "select fuse_type from fuse where fuse_id='" + fuse_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, fuse_type);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                fuse_type = rset.getString("fuse_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getFuseTYpe() in surveyModel" + e);
        }
        return fuse_type;
    }

    public String getSwitchType(int switch_id) {
        String switch_type = "";
        String query = "select switch_type from switch_type where switch_type_id='" + switch_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, mccb_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                switch_type = rset.getString("switch_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getMccbType() in surveyModel" + e);
        }
        return switch_type;
    }


    public int getStarterMakeId(String starter_make) {

        int starter_make_id = 0;
        String query = " SELECT starter_make_id FROM starter_make WHERE starter_make='" + starter_make + "'  "
                + "group by starter_make_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                starter_make_id = rset.getInt("starter_make_id");
            } else {
                starter_make_id = 0;
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
        }
        return starter_make_id;
    }

    public int cancelRecord(int tube_well_detail_id, int tube_well_rev_no) {

        String query1 = "UPDATE tube_well_detail SET active='N' WHERE tube_well_detail_id = " + tube_well_detail_id
                + " and tube_well_rev_no = " + tube_well_rev_no;
        String query2 = "UPDATE pole SET active = 'N' WHERE tube_well_detail_id =" + tube_well_detail_id + " AND tube_well_rev_no=" + tube_well_rev_no;
        int rowsAffected = -1;
        try {
            connection.setAutoCommit(false);
            rowsAffected = connection.prepareStatement(query1).executeUpdate();
            if (rowsAffected > 0) {
                rowsAffected = 0;
                rowsAffected = connection.prepareStatement(query2).executeUpdate();
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (Exception e) {
            try {
                connection.rollback();
                System.out.println("Error: " + e);
            } catch (SQLException ex) {
                Logger.getLogger(TubeWellDetailModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(TubeWellDetailModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rowsAffected < 0) {
            message = "Error Record cannot be Cancelled.....";
            msgBgColor = COLOR_ERROR;
        } else {
            message = "Record Cancelled Successfully......";
            msgBgColor = COLOR_OK;
        }
        return rowsAffected;
    }

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
}
