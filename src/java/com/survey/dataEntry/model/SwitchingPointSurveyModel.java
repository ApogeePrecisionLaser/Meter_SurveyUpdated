/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import java.sql.Connection;
import com.survey.tableClasses.SwitchingPointSurveyBean;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author JPSS
 */
public class SwitchingPointSurveyModel {

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
        //String query = " SELECT zone FROM switching_point_detail GROUP BY zone ORDER BY zone ";
        String query = " SELECT z.zone FROM switching_point_detail as t"
                + " left join feeder as fe on fe.feeder_id=t.feeder_id"
                + "left join zone as z on fe.zone_id=z.zone_id"
                + "GROUP BY zone ORDER BY zone";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone = rset.getString("zone");
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

    public List<String> getZone(String q, String division) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT zone FROM zone z"
                + "  left join  division d on z.division_id=d.division_id WHERE IF('" + division + "' = '', division_name LIKE '%%',division_name=? )"
                //  + "WHERE division_id = (SELECT division_id FROM division WHERE division_name = ?) "
                + "ORDER BY zone ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, division);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone = rset.getString("zone");
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
        String query = "  SELECT fd.feeder_name FROM   feeder fd"
                + ""
                + "  left join  zone z on z.zone_id=fd.zone_id WHERE IF('" + zone + "' = '', zone LIKE '%%',zone =? )"
                //   + " one = ?) "
                + " ORDER BY feeder_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, zone);
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

    public List<String> getWard_No(String q, String city) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ward_no FROM ward WHERE city_id = (SELECT city_id FROM city WHERE city_name = ?) ORDER BY ward_no ";
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

    public List<String> getPoleNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " Select pole_no from pole GROUP BY pole_no";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no = rset.getString("pole_no");
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
        String query = " SELECT switching_point_name FROM switching_point_detail GROUP BY switching_point_name ORDER BY switching_point_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no = rset.getString("switching_point_name");
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
        String query = " SELECT pole_no_s FROM switching_point_detail GROUP BY pole_no_s ORDER BY pole_no_s ";
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

    public List<SwitchingPointSurveyBean> showAllData(String searchPoleNoSwitch, String searchPoleType, String searchPoleNo, String searchAreaName, String searchRoadName, String service_conn_no_Search, String searchSwitchingPtName, String zone) {
        List<SwitchingPointSurveyBean> listAll = new ArrayList<SwitchingPointSurveyBean>();
        String query = "SELECT d.division_name, z.zone, s.measured_load ,cty.city_name,w.ward_no, s.longitude, s.lattitude ,"
                + " s.switching_point_detail_id, s.switching_point_name, fd.feeder_name as feeder , "
                + " s.pole_no_s, s.gps_code_s, s.is_working,  s.active, s.remark, s.service_conn_no, s.ivrs_no, "
                + " DATE_FORMAT(s.created_date,'%d-%m-%Y') AS created_date, s.switching_rev_no, s.meter_no_s, s.ph,s.created_by, "
                //+ " s.controller_model, s.mobile_no, s.sim_no, s.imei_no, s.panel_file_no, s.panel_transferred_status, "
                + " p.pole_no,p.pole_span,p.pole_height,p.gps_code,  r.road_name,a.area_name,t.traffic_type,c.control_mechanism_type, s.fuse, "
                + " s.contacter, s.mccb, f.fuse_type, ct.contacter_type, mcb.mccb_type, s.timer, tm.timer_type, s.fuse_quantity, s.contacter_quantity, "
                + " s.mccb_quantity, s.timer_quantity, p.avg_lux_level,p.standard_lux_level,p.max_avg_lux_level,p.min_avg_lux_level, "
                + " p.mounting_height, "
                + "  (SELECT GROUP_CONCAT(CAST((concat(spltm.switching_point_light_type_id,'-',spltm.light_type_id)) AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
                + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w "
                + " WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id "
                + " AND s.switching_rev_no = spltm.switching_rev_no  AND lt.wattage_id=w.wattage_id AND lt.source_id=l.source_type_id)AS map_id, "
                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.total_fittings)) AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
                + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w "
                + " WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id AND s.switching_rev_no = spltm.switching_rev_no "
                + " AND lt.wattage_id=w.wattage_id AND lt.source_id=l.source_type_id)AS source_wattage, "
                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.no_of_working,'/',spltm.no_of_not_working)) "
                + " AS CHAR CHARACTER SET utf8) SEPARATOR '__') FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l, "
                + " wattage AS w WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id AND s.switching_rev_no = spltm.switching_rev_no AND "
                + " lt.wattage_id=w.wattage_id AND lt.source_id=l.source_type_id)AS Isworking, s.no_of_poles, "
                + " (select SUM(spm.total_fittings * w.wattage_value) from wattage w, light_type lt, switching_point_light_type_mapping spm "
                + " where spm.light_type_id = lt.light_type_id and  lt.wattage_id = w.wattage_id AND "
                + " spm.switching_point_detail_id = s.switching_point_detail_id) as wattage, ru.road_use, rc.category_name, s.isOnPole "
                + " FROM "
                + "   switching_point_detail s LEFT JOIN pole p "
                + "  ON s.switching_point_detail_id = p.switching_point_detail_id AND s.switching_rev_no = p.switching_rev_no and p.isSwitchingPoint = 'Yes' and p.active = 'Y' "
                //     + "  left join area a on  s.area_id = a.area_id  "
                + "  left join area a on IF (s.isOnPole = 'N', s.area_id = a.area_id , p.area_id = a.area_id ) "
                + " left join ward w on a.ward_id = w.ward_id "
                + " left join city cty on w.city_id = cty.city_id "
                //    + "  left join road r on  s.road_id = r.road_id AND s.road_rev_no = r.road_rev_no "
                + "  left join road r on IF (s.isOnPole = 'N', s.road_id = r.road_id AND s.road_rev_no = r.road_rev_no, p.road_id = r.road_id AND p.road_rev_no = r.road_rev_no) "
                + " left join road_category rc on r.category_id = rc.category_id "
                + " left join road_use ru on r.road_use_id = ru.road_use_id "
                //      + " left join traffic_type t on  s.traffic_type_id = t.traffic_type_id "
                + " left join traffic_type t on IF (s.isOnPole = 'N', s.traffic_type_id = t.traffic_type_id, p.traffic_type_id = t.traffic_type_id) "
                + " left join feeder fd on s.feeder_id = fd.feeder_id "
                + " left join  zone z on fd.zone_id = z.zone_id "
                + " left join division d on z.division_id = d.division_id "
                + " ,control_mechanism c,fuse f, contacter ct, timer tm, mccb mcb "
                + " WHERE "
                + " s.control_mechanism_id = c.control_mechanism_id and "
                + " s.fuse_id = f.fuse_id and "
                + " s.contacter_id = ct.contacter_id and "
                + " s.timer_id = tm.timer_id   and "
                + " s.mccb_id = mcb.mccb_id and s.active = 'Y' "
                + " AND IF( ? = '', s.service_conn_no LIKE '%%', s.zone =? ) "
                + " AND IF( ? = '', s.switching_point_name LIKE '%%', s.switching_point_name =? ) "
                + " AND IF( ? = '', s.service_conn_no LIKE '%%', p.pole_no =? ) "
                + " AND IF( ? = '', s.service_conn_no LIKE '%%', s.service_conn_no =? ) order by s.switching_point_name ";

//        String query = " SELECT s.switching_point_detail_id, s.switching_point_name, s.feeder ,s.pole_no_s, s.gps_code_s, s.is_working,"
//                + "  s.active, s.remark,"
//                //+ " s.controller_model, s.mobile_no, s.sim_no, s.imei_no, s.panel_file_no, s.panel_transferred_status, "
//                + " DATE_FORMAT(s.created_date,'%d-%m-%Y') AS created_date,"
//                + " s.switching_rev_no, s.meter_no_s, s.ph,s.created_by, p.pole_no,p.pole_span,p.pole_height,p.gps_code,"
//                + "  r.road_name,a.area_name,t.traffic_type,c.control_mechanism_type,"
//                + " s.fuse, s.contacter, s.mccb, f.fuse_type, ct.contacter_type, mcb.mccb_type,"
//                + " s.timer, tm.timer_type, s.fuse_quantity, s.contacter_quantity, s.mccb_quantity, s.timer_quantity,"
//                + " p.avg_lux_level,p.standard_lux_level,p.max_avg_lux_level,p.min_avg_lux_level,"
//                + " m.mounting_type, p.mounting_height, pt.pole_type,"
//                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.total_fittings))"
//                + " AS CHAR CHARACTER SET utf8) SEPARATOR '__')"
//                + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w"
//                + " WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id"
//                + " AND lt.wattage_id=w.wattage_id"
//                + " AND lt.source_id=l.source_type_id)AS source_wattage,"
//                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.no_of_working,'/',spltm.no_of_not_working))"
//                + " AS CHAR CHARACTER SET utf8) SEPARATOR '__')"
//                + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w"
//                + " WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id"
//                + " AND lt.wattage_id=w.wattage_id"
//                + " AND lt.source_id=l.source_type_id)AS Isworking, s.no_of_poles, "
//                + " (select SUM(spm.total_fittings * w.wattage_value) from wattage w, light_type lt, switching_point_light_type_mapping spm "
//                + " where spm.light_type_id = lt.light_type_id and "
//                + " lt.wattage_id = w.wattage_id AND spm.switching_point_detail_id = s.switching_point_detail_id) as wattage "
//                + " FROM switching_point_detail s,pole p, pole_type pt, area a, road r, traffic_type t, mounting_type m,"
//                + " control_mechanism c,"
//                + " fuse f, contacter ct, timer tm, mccb mcb"
//                + " WHERE"
//                + " s.pole_id = p.pole_id"
//                + " and p.pole_type_id = pt.pole_type_id"
//                + " AND p.mounting_type_id = m.mounting_type_id"
//                + " AND s.area_id = a.area_id"
//                + " And s.road_id = r.road_id"
//                + " And s.fuse_id = f.fuse_id"
//                + " And s.contacter_id = ct.contacter_id"
//                + " And s.timer_id = tm.timer_id"
//                + " And s.mccb_id = mcb.mccb_id"
//                + " AND s.traffic_type_id = t.traffic_type_id"
//                + " AND s.control_mechanism_id = c.control_mechanism_id"
//                /*         + " AND IF('" + searchPoleNoSwitch + "' = '', s.pole_no_s LIKE '%%', s.pole_no_s =? ) "
//                + " AND IF('" + searchPoleType + "' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "
//                + " AND IF('" + searchPoleNo + "' = '', p.pole_no LIKE '%%', p.pole_no =? )"
//                + " AND IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =? ) "
//                + " AND IF('" + searchRoadName + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) " */
//                + " order by s.pole_no_s ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, zone);
            pstmt.setString(2, zone);
            pstmt.setString(3, searchSwitchingPtName);
            pstmt.setString(4, searchSwitchingPtName);
            pstmt.setString(5, searchPoleNo);
            pstmt.setString(6, searchPoleNo);
            pstmt.setString(7, service_conn_no_Search);
            pstmt.setString(8, service_conn_no_Search);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SwitchingPointSurveyBean surveyType = new SwitchingPointSurveyBean();
                //           surveyType.setSurvey_id(rset.getInt("switching_point_detail_id"));
                //           surveyType.setSurvey_rev_no(rset.getInt("switching_rev_no"));
                surveyType.setPole_no_s(rset.getString("pole_no_s"));
                surveyType.setSwitching_point_name(rset.getString("switching_point_name"));
                surveyType.setFeeder(rset.getString("feeder"));
                //           surveyType.setGps_code_s(rset.getString("gps_code_s"));
                surveyType.setMeter_no_s(rset.getString("meter_no_s"));
                //            surveyType.setPhase(rset.getString("ph"));
                // surveyType.setSurvey_date(rset.getString("survey_date"));
                //            surveyType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                surveyType.setArea_name(rset.getString("area_name"));
                surveyType.setRoad_name(rset.getString("road_name"));
                //            surveyType.setTraffic_type(rset.getString("traffic_type"));
                //            surveyType.setPole_type(rset.getString("pole_type"));
                //            surveyType.setPole_span(rset.getString("pole_span"));
                //            surveyType.setPole_height(rset.getString("pole_height"));
                //            surveyType.setMounting_height(rset.getString("mounting_height"));
                //            surveyType.setCreated_date(rset.getString("created_date"));
                //             surveyType.setCreated_by(rset.getString("created_by"));
                surveyType.setRemark(rset.getString("remark"));
                //             surveyType.setMounting_type(rset.getString("mounting_type"));
                //             surveyType.setActive(rset.getString("active"));
                surveyType.setPole_no(rset.getString("pole_no"));
                //             surveyType.setGps_code(rset.getString("gps_code"));
                //            surveyType.setMax_avg_lux_level(rset.getString("max_avg_lux_level"));
                //            surveyType.setMin_avg_lux_level(rset.getString("min_avg_lux_level"));
                //            surveyType.setAvg_lux_level(rset.getString("avg_lux_level"));
                //             surveyType.setStandard_avg_lux_level(rset.getString("standard_lux_level"));
                //             surveyType.setIs_working(rset.getString("is_working"));
                surveyType.setSource_wattage(rset.getString("source_wattage"));
                surveyType.setIsworking(rset.getString("Isworking"));   // number & type of bulbs working / not working
                /*             surveyType.setFuse(rset.getString("fuse"));
                surveyType.setFuse_type(rset.getString("fuse_type"));
                surveyType.setFuse_quantity(rset.getString("fuse_quantity"));
                surveyType.setContacter(rset.getString("contacter"));
                surveyType.setContacter_type(rset.getString("contacter_type"));
                surveyType.setContacter_quantity(rset.getString("contacter_quantity"));
                surveyType.setTimer(rset.getString("timer"));
                surveyType.setTimer_type(rset.getString("timer_type"));
                surveyType.setTimer_quantity(rset.getString("timer_quantity"));
                surveyType.setMccb(rset.getString("mccb"));
                surveyType.setMccb_type(rset.getString("mccb_type"));
                surveyType.setMccb_quantity(rset.getString("mccb_quantity"));    */
                surveyType.setNo_of_poles(rset.getInt("no_of_poles"));
                surveyType.setSwitching_load(rset.getInt("wattage"));

                /*surveyType.setController_model(rset.getString("controller_model"));
                surveyType.setMobile_no(rset.getString("mobile_no"));
                surveyType.setSim_no(rset.getString("sim_no"));
                surveyType.setImei_no(rset.getString("imei_no"));
                surveyType.setPanel_file_no(rset.getString("panel_file_no"));
                surveyType.setPanel_transferred_status(rset.getString("panel_transferred_status"));
                 */
                listAll.add(surveyType);
            }
        } catch (Exception e) {
            System.out.println("Error in  showAllData of Switching point detail is : " + e);
        }
        return listAll;
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<SwitchingPointSurveyBean> listAll) {
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
        String query = " SELECT area_name FROM area WHERE ward_id = (select ward_id from ward where ward_no = ?) GROUP BY area_name ORDER BY area_name ";
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

    public List<String> getservice_conn_no_Search(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT service_conn_no FROM switching_point_detail group by service_conn_no";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String service_conn_no = rset.getString("service_conn_no");
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

    public List<String> getRoadUse(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT ru.road_use FROM road_use ru, road r WHERE r.road_use_id = ru.road_use_id order by ru.road_use ";
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
        String query = "SELECT c.category_name FROM road_category c, road r WHERE r.category_id = c.category_id order by c.category_name ";
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
        String query = " SELECT switching_point_name FROM switching_point_detail GROUP BY switching_point_name ORDER BY switching_point_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switch_name = rset.getString("switching_point_name");
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

    public List<String> getContacterType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT contacter_type FROM contacter GROUP BY contacter_type ORDER BY contacter_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String contacter_type = rset.getString("contacter_type");
                if (contacter_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(contacter_type);
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

    public List<String> getMccbType(String q) {
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

    public List<String> getEnclosureType(String q) {
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

    public int getFuseId(String fuse_type) {
        int fuse_id = 0;
        String query = " SELECT fuse_id FROM fuse WHERE fuse_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, fuse_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            fuse_id = rset.getInt("fuse_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getFuseId() Error: " + e);
        }
        return fuse_id;
    }

    public int getContacterId(String contacter_type) {
        int contacter_id = 0;
        String query = " SELECT contacter_id FROM contacter WHERE contacter_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, contacter_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            contacter_id = rset.getInt("contacter_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getContacterId() Error: " + e);
        }
        return contacter_id;
    }

    public int getMccbId(String mccb_type) {
        int mccb_id = 0;
        String query = " SELECT mccb_id FROM mccb WHERE mccb_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mccb_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            mccb_id = rset.getInt("mccb_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getMccbId() Error: " + e);
        }
        return mccb_id;
    }

    public int getTimerId(String timer_type) {
        int timer_id = 0;
        String query = " SELECT timer_id FROM timer WHERE timer_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, timer_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            timer_id = rset.getInt("timer_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getTimerId() Error: " + e);
        }
        return timer_id;
    }

    public int getPoleId(String pole_no) {
        shouldPoleEnter = "Yes";
        int pole_type_id = 0;
        String query = " SELECT pole_id FROM pole WHERE pole_no = ? group by pole_id";
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

    public List<String> searchPoleNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " Select pole_no_s from switching_point_detail "
                + " where active= 'Y' GROUP BY pole_no_s ";
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
        String query = " SELECT enclosure_type_id FROM enclosure_type WHERE enclosure_type='" + switch_type + "'"
                + "group by enclosure_type_id";
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

    public int getAreaId(String city, String ward_no, String area_name) {
        int area_id = 0;
        String query = " SELECT area_id FROM area WHERE area_name like  ? "
                + "AND  ward_id = (SELECT ward_id FROM ward WHERE ward_no = ? AND city_id = (SELECT city_id FROM city WHERE city_name = ?))";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, "%" + area_name);
            pstmt.setString(2, ward_no);
            pstmt.setString(3, city);
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
        String query = " SELECT feeder_id FROM feeder WHERE feeder_name = ? AND zone_id = (Select zone_id FROM zone WHERE zone = ? AND division_id = (SELECT division_id FROM division WHERE division_name = ?))";
        try {
            if (!zone.isEmpty() && !division.isEmpty()) {
                final_query = query;
            } else {
                final_query = query1;
            }
            java.sql.PreparedStatement pstmt = connection.prepareStatement(final_query);

            if (zone.isEmpty() && division.isEmpty()) {
                pstmt.setString(1, feeder);
            } else {
                pstmt.setString(1, feeder);
                pstmt.setString(2, zone);
                pstmt.setString(3, division);
            }
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

    public int getSwitchingPointId(String switching_point_name, int feeder_id, String switching_pt_code) {
        int switch_id = 0;
        String query = " SELECT switching_point_id FROM switching_point WHERE switching_point = ? AND feeder_id = ? AND switching_pt_code = ?";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, switching_point_name);
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
            String query = "select max(switching_point_detail_id) from switching_point_detail ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                survey_id = rs.getInt("max(switching_point_detail_id)");
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
        String query = "select count(*) from switching_point_detail where service_conn_no ='" + serv_conn + "' and ivrs_no = '" + ivrs_no + "' and active = 'Y'";
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

    public boolean validationCheckforRevise(String serv_conn, String ivrs_no, String pole_no, int switching_point_detail_id, int swithcing_point_revNo, String isOnPole) {
        int i = 0;
        boolean isPoleNoExists;
        String query = "select count(*) from switching_point_detail where service_conn_no ='" + serv_conn + "' and active = 'Y' AND ivrs_no ='" + ivrs_no + "' AND switching_point_detail_id !=" + switching_point_detail_id;
        String isPoleExistsInPole = "select count(*) from pole where pole_no = ? and switching_point_detail_id != ?  AND switching_rev_no != ?  AND active = 'Y' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                i = rs.getInt("count(*)");
            }
            if (i <= 0 && isOnPole.equals("Y")) {
                isPoleNoExists = checkIfPoleAlreadyExists(isPoleExistsInPole, pole_no, switching_point_detail_id, swithcing_point_revNo);
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

    private String getMeterNameAuto(String ivrs_no) {
        String query = "select meter_name_auto from switching_point_detail where active = 'Y' AND ivrs_no ='" + ivrs_no + "' ";
        String meter_name_auto = "";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                meter_name_auto = rs.getString("meter_name_auto");
            }
        } catch (Exception e) {
        }
        return meter_name_auto;
    }

    public int insertRecord(SwitchingPointSurveyBean surveyTypeBean) {
        /*  String query1 = "INSERT INTO switching_point_detail (switching_point_name, pole_no_s, gps_code_s, pole_id, area_id, road_id, is_working, "
        + " traffic_type_id, created_by, remark, meter_no_s, ph, "
        + " control_mechanism_id, fuse, contacter, mccb, fuse_id, "
        + " contacter_id, mccb_id, timer, timer_id, "
        + " fuse_quantity, contacter_quantity, mccb_quantity, timer_quantity ) "
        + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) "; */
 /*     String query = "INSERT INTO switching_point_detail ("
        + "switching_point_name, pole_no_s, GPS_code_s, area_id, road_id, is_working, traffic_type_id, "
        + " created_by, remark, meter_no_s, ph, control_mechanism_id, fuse, "
        + " contacter, mccb, fuse_id, contacter_id, mccb_id, timer, timer_id, fuse_quantity, "
        + " contacter_quantity, mccb_quantity, timer_quantity, no_of_poles, "
        + " longitude, lattitude, service_conn_no, ivrs_no, measured_load, feeder_id, pole_id)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";  */

 /*String query1 = "INSERT INTO switching_point_detail ("
                + "switching_point_name, pole_no_s, GPS_code_s, area_id, road_id, is_working, traffic_type_id, "
                + " created_by, remark, meter_no_s, ph, control_mechanism_id, fuse, "
                + " contacter, mccb, fuse_quantity, "
                + "  mccb_quantity, no_of_poles, "
                + " longitude, lattitude, service_conn_no, ivrs_no, measured_load, feeder_id, isOnPole, road_rev_no,fuse1,fuse2,fuse3,"
                + "mccb1,mccb2,mccb3,fuse_id1,fuse_id2,"
                + "fuse_id3,mccb_id1,mccb_id2,mccb_id3,contacter_id,contacter_capacity,contacter_make,"
                + "auto_switch_type_id,main_switch_type_id,main_switch_reading,enclosure_type_id) "
                //+ " controller_model, mobile_no, sim_no, imei_no, panel_file_no, road_rev_no )"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?, ?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        /*     String query1 = " INSERT INTO switching_point_detail "
        + "( switching_point_id, meter_id, pole_no_s, pole_id, area_id, road_id, "
        + " traffic_type_id,control_mechanism_id,timer_id,contacter_id,mccb_id,fuse_id, "
        + " ph, fuse, contacter, mccb, timer, fuse_quantity, contacter_quantity, mccb_quantity, timer_quantity, "
        + " no_of_poles, longitude, lattitude, measured_load, is_working, "
        + " created_by, remark )"
        + " VALUES (?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? ,?, ?, ?, ?)";  */
        String query1 = "INSERT INTO switching_point_detail ("
                + "switching_point_name, pole_no_s, GPS_code_s, area_id, road_id, is_working, traffic_type_id, "
                + " created_by, remark, meter_no_s, ph, control_mechanism_id, fuse, "
                + " contacter, mccb, fuse_quantity, "
                + "  mccb_quantity, no_of_poles , "
                + " longitude, lattitude, service_conn_no, ivrs_no, measured_load, feeder_id, isOnPole, road_rev_no,fuse1,fuse2,fuse3,"
                + "mccb1,mccb2,mccb3,fuse_id1,fuse_id2,"
                + "fuse_id3,mccb_id1,mccb_id2,mccb_id3,contacter_id,contacter_capacity,cotracter_make,"
                + "auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id,switching_rev_no,switching_point_detail_id,meter_name_auto)"
                //+ " controller_model, mobile_no, sim_no, imei_no, panel_file_no, road_rev_no )"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?, ?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,? )";
        String query2 = "INSERT INTO switching_point_light_type_mapping (switching_point_detail_id,light_type_id , no_of_working , no_of_not_working, total_fittings,switching_rev_no)"
                + " VALUES (?, ?, ?, ?, ?,?)";

        String query3 = "INSERT INTO pole "
                + " (created_by, pole_no, switching_point_detail_id, switching_rev_no, isSwitchingPoint, area_id, road_id, road_rev_no, traffic_type_id) "
                + " VALUES "
                + " (?, ?, ?, 0, 'Yes', ?,?,?,?) ";
        //   String query4 = "UPDATE switching_point_detail SET pole_id = ?, pole_rev_no = 0 WHERE switching_point_detail_id = ? AND switching_rev_no = 0";
        //   String query5 = "INSERT INTO pole_light_type_mapping (light_type_id, pole_id, pole_rev_no, quantity) VALUES (?,?,?,?)";
        java.sql.Date s_date = null;
        ResultSet rs = null;
        int switching_point_detail_id = 0;
        int switching_rev_no = 0;
        int pole_id = 0;
        int survey_id = getSurveyId();
        int rowsAffected = 0;
        String final_query;

        java.sql.PreparedStatement pstmt = null;
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
 

  
 if(surveyTypeBean.getSwitching_point_name()==null){
   pstmt.setString(1, "Yes");
 }else{
  pstmt.setString(1, surveyTypeBean.getSwitching_point_name());
 }
          
            pstmt.setString(2, surveyTypeBean.getPole_no_s());
            pstmt.setString(3, surveyTypeBean.getGps_code_s());
            if (surveyTypeBean.getIsCheckedTrue().equals("Y")) {
                pstmt.setNull(4, java.sql.Types.INTEGER);
                pstmt.setNull(5, java.sql.Types.INTEGER);
                pstmt.setNull(7, java.sql.Types.INTEGER);
                pstmt.setNull(26, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(4, surveyTypeBean.getArea_id());
                pstmt.setInt(5, surveyTypeBean.getRoad_id());
                pstmt.setInt(7, surveyTypeBean.getTraffic_type_id());
                pstmt.setInt(26, surveyTypeBean.getRoad_rev_no());
            }
            pstmt.setString(6, surveyTypeBean.getIs_working());
            pstmt.setString(8, surveyTypeBean.getCreated_by());
            pstmt.setString(9, surveyTypeBean.getRemark());
            pstmt.setString(10, surveyTypeBean.getMeter_no_s());
            pstmt.setInt(11, surveyTypeBean.getPhase());
            pstmt.setInt(12, surveyTypeBean.getControl_mechanism_id());
            pstmt.setString(13, surveyTypeBean.getFuse());
            if ("Y".equals(surveyTypeBean.getFuse())) {
                pstmt.setInt(33, surveyTypeBean.getFuse_id1());
                pstmt.setInt(34, surveyTypeBean.getFuse_id2());
                pstmt.setInt(35, surveyTypeBean.getFuse_id3());
            } else {
                pstmt.setNull(33, java.sql.Types.INTEGER);
                pstmt.setNull(34, java.sql.Types.INTEGER);
                pstmt.setNull(35, java.sql.Types.INTEGER);
            }
            pstmt.setString(14, surveyTypeBean.getContacter());
            if ("Y".equals(surveyTypeBean.getContacter())) {
                pstmt.setInt(39, surveyTypeBean.getContacter_id());

            } else {
                pstmt.setNull(39, java.sql.Types.INTEGER);

            }
            pstmt.setString(15, surveyTypeBean.getMccb());
            if ("Y".equals(surveyTypeBean.getMccb())) {
                pstmt.setInt(36, surveyTypeBean.getMccb_id1());
                pstmt.setInt(37, surveyTypeBean.getMccb_id2());
                pstmt.setInt(38, surveyTypeBean.getMccb_id3());

            } else {
                pstmt.setNull(36, java.sql.Types.INTEGER);
                pstmt.setNull(37, java.sql.Types.INTEGER);
                pstmt.setNull(38, java.sql.Types.INTEGER);
            }
            pstmt.setString(16, surveyTypeBean.getFuse_quantity());
            pstmt.setString(17, surveyTypeBean.getMccb_quantity());
            pstmt.setInt(18, surveyTypeBean.getNo_of_poles());
            pstmt.setDouble(19, surveyTypeBean.getLongitude());
            pstmt.setDouble(20, surveyTypeBean.getLattitude());
            pstmt.setString(21, surveyTypeBean.getService_conn_no());
            if(surveyTypeBean.getIvrs_no()==null){
   pstmt.setString(22, "0");
 }else{
  pstmt.setString(22, surveyTypeBean.getIvrs_no());
 }
           
            pstmt.setDouble(23, surveyTypeBean.getMeasured_load());
            pstmt.setInt(24, surveyTypeBean.getFeeder_id());
            pstmt.setString(25, surveyTypeBean.getIsCheckedTrue());
            pstmt.setString(27, surveyTypeBean.getFuse1());
            pstmt.setString(28, surveyTypeBean.getFuse2());
            pstmt.setString(29, surveyTypeBean.getFuse3());
            pstmt.setString(30, surveyTypeBean.getMccb1());
            pstmt.setString(31, surveyTypeBean.getMccb2());
            pstmt.setString(32, surveyTypeBean.getMccb3());

            pstmt.setString(40, surveyTypeBean.getContacter_capacity());
            pstmt.setString(41, surveyTypeBean.getContacter_make());
            pstmt.setInt(42, surveyTypeBean.getAuto_switch_type_id());
            pstmt.setInt(43, surveyTypeBean.getMain_switch_type_id());
            pstmt.setString(44, surveyTypeBean.getMain_switch_reading());
            pstmt.setInt(45, surveyTypeBean.getEnclosure_type_id());
            pstmt.setInt(46, surveyTypeBean.getSwitching_rev_no());
            switching_rev_no = surveyTypeBean.getSwitching_rev_no();
            pstmt.setInt(47, surveyTypeBean.getSwitching_point_detail_id());
            pstmt.setString(48, getMeterNameAuto(surveyTypeBean.getIvrs_no()));
            
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    switching_point_detail_id = rs.getInt(1);
                }
                rowsAffected = 0;
                pstmt.close();
                pstmt = connection.prepareStatement(query2);
                int light_type_idArr[] = surveyTypeBean.getLight_type_id_M();
                String working[] = surveyTypeBean.getWorking();
                String n_working[] = surveyTypeBean.getN_working();
                String quantity[] = surveyTypeBean.getQuantity();
               
                if(light_type_idArr!=null){
                for (int i = 0; i < light_type_idArr.length; i++) {
                     if (light_type_idArr[i] > 0) {
                    if (light_type_idArr[i] != 0) {
                        pstmt.setInt(1, switching_point_detail_id);
                        pstmt.setInt(2, light_type_idArr[i]);
                        pstmt.setInt(3, Integer.parseInt(working[i].trim()));
                        pstmt.setInt(4, Integer.parseInt(n_working[i].trim()));
                        pstmt.setInt(5, Integer.parseInt(quantity[i].trim()));
                        pstmt.setInt(6, switching_rev_no);
                        rowsAffected = pstmt.executeUpdate();
                    }
                     }else {
                         System.out.println("no light type arr");
                        break;
                    }
                }
                }else{
                
                  rowsAffected=1;
                }
                if (rowsAffected > 0) {
                    if (!surveyTypeBean.getPole_no().equals("NoPoleEntry")) {
                        pstmt = connection.prepareStatement(query3);
                        pstmt.setInt(1, 1);
                        pstmt.setString(2, surveyTypeBean.getPole_no());
                        pstmt.setInt(3, switching_point_detail_id);
                        pstmt.setInt(4, surveyTypeBean.getArea_id());
                        pstmt.setInt(5, surveyTypeBean.getRoad_id());
                        pstmt.setInt(6, surveyTypeBean.getRoad_rev_no());
                        pstmt.setInt(7, surveyTypeBean.getTraffic_type_id());

                        rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            connection.commit();
                            /*      rs = pstmt.getGeneratedKeys();
                            while (rs.next()) {
                            pole_id = rs.getInt(1);
                            }
                            rowsAffected = 0;
                            pstmt.close();
                            pstmt = connection.prepareStatement(query4);
                            pstmt.setInt(1, pole_id);
                            pstmt.setInt(2, switching_point_detail_id);
                            rowsAffected = pstmt.executeUpdate();
                            if (rowsAffected > 0) {
                            connection.commit();
                            } else {
                            connection.rollback();
                            }  */
                        } else {
                            connection.rollback();
                        }
                    } else {
                        connection.commit();
                    }
                } else {
                    connection.rollback();
                }

            } else {
                connection.rollback();
            }

        } catch (Exception e) {
            System.out.println("exception occured during inserting in switching point detail :  " + e);
            try {
                connection.rollback();
            } catch (SQLException sqlE) {
                System.out.println("SQL Exception Occured while inserting data in Switching point detail:" + sqlE);
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

    public int getContracterId(String contracter_type) {
        int contracter_id = 0;
        String query = "select contracter_id from contracter where contracter_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, contracter_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                contracter_id = rset.getInt("contracter_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return contracter_id;
    }

    public int updateIVRSRecord(SwitchingPointSurveyBean surveyTypeBean) {
        int rowsAffected = 0;
        String[] switching_point_id = surveyTypeBean.getSwitching_point_id_for_update();
        String[] hidden_field = surveyTypeBean.getHidden_field();
        String[] ivrs_no = surveyTypeBean.getIvrs_no_edit();

        String query = "update switching_point_detail set ivrs_no = ? where switching_point_detail_id = ?";
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

    public int updateRecord(SwitchingPointSurveyBean surveyTypeBean) {
        int rowsAffected = 0;

        String updateSPD = "UPDATE switching_point_detail SET pole_no_s=?, gps_code_s=?, area_id=?, road_id=?, is_working=?, "
                + " traffic_type_id=? , created_by=? , remark=? , meter_no_s=? , ph=?, control_mechanism_id=? , fuse=? , contacter=? , mccb=?  "
                + " contacter_id=? , fuse_quantity=? , mccb_quantity=? , longitude=? , lattitude=? , measured_load=? , feeder_id=? , isOnPole=? , "
                + " no_of_poles=? , service_conn_no=? , ivrs_no=? , switching_point_name=?,fuse1=?,fuse2=?,fuse3=?,mccb1=?,mccb2=?,mccb3=?,fuse_id1=?,fuse_id2=?,"
                + "fuse_id3=?,mccb_id1=?,mccb_id2=?,mccb_id3=?,contacter_capacity=?,contacter_make=?,auto_switch_type_id=?,main_switch_type_id=?"
                + ",main_switch_reading=?,enclosure_type_id=?  WHERE  switching_point_detail_id = ? AND switching_rev_no = ?";
        String updateMapping = "UPDATE switching_point_light_type_mapping SET light_type_id=?, no_of_working=?, no_of_not_working=?, total_fittings=?"
                + " WHERE switching_point_detail_id = ? AND switching_rev_no = ? AND light_type_id = ?";
        String insertIntoMap = "INSERT INTO  switching_point_light_type_mapping (light_type_id, no_of_working, no_of_not_working, total_fittings, switching_point_detail_id, switching_rev_no)"
                + " VALUES (?,?,?,?,?,?)";
        String checkPole = "SELECT count(*) FROM pole WHERE switching_point_detail_id = ? AND switching_rev_no = ? AND isSwitchingPoint = 'Yes' AND active = 'Y'";
        String updatePole1 = " UPDATE pole SET  pole_no = ? , isSwitchingPoint = 'No' WHERE switching_point_detail_id = ? AND switching_rev_no = ? AND isSwitchingPoint = 'Yes'";
        String updatePole2 = " UPDATE pole SET pole_no = ?  WHERE switching_point_detail_id = ? AND switching_rev_no = ?  AND isSwitchingPoint = 'Yes'";
        String insertPole1 = "INSERT INTO pole "
                + " (created_by, pole_no, switching_point_detail_id, switching_rev_no, isSwitchingPoint, area_id, road_id, road_rev_no, traffic_type_id) "
                + " VALUES "
                + " (?, ?, ?, 0, 'Yes', ?,?,?,?) ";
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(updateSPD);
            pstmt.setString(1, surveyTypeBean.getPole_no_s());
            pstmt.setString(2, surveyTypeBean.getGps_code_s());
            pstmt.setInt(3, surveyTypeBean.getArea_id());
            pstmt.setInt(4, surveyTypeBean.getRoad_id());
            pstmt.setString(5, surveyTypeBean.getIs_working());
            pstmt.setInt(6, surveyTypeBean.getTraffic_type_id());
            pstmt.setString(7, surveyTypeBean.getCreated_by());
            pstmt.setString(8, surveyTypeBean.getRemark());
            pstmt.setString(9, surveyTypeBean.getMeter_no_s());
            pstmt.setInt(10, surveyTypeBean.getPhase());
            pstmt.setInt(11, surveyTypeBean.getControl_mechanism_id());
            pstmt.setString(12, surveyTypeBean.getFuse());
            pstmt.setString(13, surveyTypeBean.getContacter());
            pstmt.setString(14, surveyTypeBean.getMccb());
            pstmt.setInt(15, surveyTypeBean.getContacter_id());
            pstmt.setString(16, surveyTypeBean.getFuse_quantity());
            pstmt.setString(17, surveyTypeBean.getMccb_quantity());
            pstmt.setDouble(18, surveyTypeBean.getLongitude());
            pstmt.setDouble(19, surveyTypeBean.getLattitude());
            pstmt.setDouble(20, surveyTypeBean.getMeasured_load());
            pstmt.setInt(21, surveyTypeBean.getFeeder_id());
            pstmt.setString(22, surveyTypeBean.getIsCheckedTrue());
            pstmt.setInt(23, surveyTypeBean.getNo_of_poles());
            pstmt.setString(24, surveyTypeBean.getService_conn_no());
            pstmt.setString(25, surveyTypeBean.getIvrs_no());
            pstmt.setString(26, surveyTypeBean.getSwitching_point_name());
            pstmt.setString(27, surveyTypeBean.getFuse1());
            pstmt.setString(28, surveyTypeBean.getFuse2());
            pstmt.setString(29, surveyTypeBean.getFuse3());
            pstmt.setString(30, surveyTypeBean.getMccb1());
            pstmt.setString(31, surveyTypeBean.getMccb2());
            pstmt.setString(32, surveyTypeBean.getMccb3());
            pstmt.setInt(33, surveyTypeBean.getFuse_id1());
            pstmt.setInt(34, surveyTypeBean.getFuse_id2());
            pstmt.setInt(35, surveyTypeBean.getFuse_id3());
            pstmt.setInt(36, surveyTypeBean.getMccb_id1());
            pstmt.setInt(37, surveyTypeBean.getMccb_id2());
            pstmt.setInt(38, surveyTypeBean.getMccb_id3());
            pstmt.setString(39, surveyTypeBean.getContacter_capacity());
            pstmt.setString(40, surveyTypeBean.getContacter_make());
            pstmt.setInt(41, surveyTypeBean.getAuto_switch_type_id());
            pstmt.setInt(42, surveyTypeBean.getMain_switch_type_id());
            pstmt.setString(43, surveyTypeBean.getMain_switch_reading());
            pstmt.setInt(44, surveyTypeBean.getEnclosure_type_id());
            pstmt.setInt(45, surveyTypeBean.getSwitching_point_detail_id());
            pstmt.setInt(46, surveyTypeBean.getSwitching_rev_no());

            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                pstmt.close();
                rowsAffected = 0;
                int loop = 0;
                int flag = 0;
                pstmt = connection.prepareStatement(updateMapping);
                int light_type_idArr[] = surveyTypeBean.getLight_type_id_M();  // currently entered light types
                String working[] = surveyTypeBean.getWorking();
                String n_working[] = surveyTypeBean.getN_working();
                String quantity[] = surveyTypeBean.getQuantity();
                String old_light_type_ids[] = surveyTypeBean.getLight_type_ids();  // previously entered light types
                for (int i = 0; i < light_type_idArr.length; i++) {
                    if (light_type_idArr[i] != 0) {
                        loop = i + 1;
                        if (Integer.parseInt(old_light_type_ids[i]) != 0) {
                            pstmt.setInt(1, light_type_idArr[i]);
                            pstmt.setInt(2, Integer.parseInt(working[i].trim()));
                            pstmt.setInt(3, Integer.parseInt(n_working[i].trim()));
                            pstmt.setInt(4, Integer.parseInt(quantity[i].trim()));
                            pstmt.setInt(5, surveyTypeBean.getSwitching_point_detail_id());
                            pstmt.setInt(6, surveyTypeBean.getSwitching_rev_no());
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
                            pstmt.setInt(5, surveyTypeBean.getSwitching_point_detail_id());
                            pstmt.setInt(6, surveyTypeBean.getSwitching_rev_no());
                            rowsAffected = pstmt.executeUpdate();
                            flag = flag + rowsAffected;
                        }

                    } else {
                        break;
                    }
                }
                if (flag == loop) { // ---

                    pstmt.close();
                    //rowsAffected = 0;
                    int pole_count = 0;
                    pstmt = connection.prepareStatement(checkPole);
                    pstmt.setInt(1, surveyTypeBean.getSwitching_point_detail_id());
                    pstmt.setInt(2, surveyTypeBean.getSwitching_rev_no());
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        pole_count = rs.getInt("count(*)");
                    }
                    if (pole_count == 0) {
                        pstmt.close();
                        if (!surveyTypeBean.getPole_no().equals("NoPoleEntry")) {    // if formerly switching point was on wall but not it has been mounted on pole
                            rowsAffected = 0;
                            pstmt = connection.prepareStatement(insertPole1);
                            pstmt.setInt(1, 1);
                            pstmt.setString(2, surveyTypeBean.getPole_no());
                            pstmt.setInt(3, surveyTypeBean.getSwitching_point_detail_id());
                            pstmt.setInt(4, surveyTypeBean.getArea_id());
                            pstmt.setInt(5, surveyTypeBean.getRoad_id());
                            pstmt.setInt(6, surveyTypeBean.getRoad_rev_no());
                            pstmt.setInt(7, surveyTypeBean.getTraffic_type_id());
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
                        if (surveyTypeBean.getIsCheckedTrue().equals("Y")) {  // if u change da pole no of the switching point
                            pstmt = connection.prepareStatement(updatePole2);
                            pstmt.setString(1, surveyTypeBean.getPole_no());
                            pstmt.setInt(2, surveyTypeBean.getSwitching_point_detail_id());
                            pstmt.setInt(3, surveyTypeBean.getSwitching_rev_no());
                            rowsAffected = pstmt.executeUpdate();
                        } else {
                            pstmt = connection.prepareStatement(updatePole1);  // if u remove switching point from a pole
                            pstmt.setString(1, surveyTypeBean.getPole_no());
                            pstmt.setInt(2, surveyTypeBean.getSwitching_point_detail_id());
                            pstmt.setInt(3, surveyTypeBean.getSwitching_rev_no());
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
            } catch (SQLException sq) {
                System.out.println("Error while updating record...." + sq);
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

    public int reviseRecord(SwitchingPointSurveyBean surveyTypeBean) {
        String query1 = "INSERT INTO switching_point_detail ("
                + "switching_point_name, pole_no_s, GPS_code_s, area_id, road_id, is_working, traffic_type_id, "
                + " created_by, remark, meter_no_s, ph, control_mechanism_id, fuse, "
                + " contacter, mccb, fuse_quantity, "
                + "  mccb_quantity, no_of_poles , "
                + " longitude, lattitude, service_conn_no, ivrs_no, measured_load, feeder_id, isOnPole, road_rev_no,fuse1,fuse2,fuse3,"
                + "mccb1,mccb2,mccb3,fuse_id1,fuse_id2,"
                + "fuse_id3,mccb_id1,mccb_id2,mccb_id3,contacter_id,contacter_capacity,cotracter_make,"
                + "auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id,switching_rev_no,switching_point_detail_id)"
                //+ " controller_model, mobile_no, sim_no, imei_no, panel_file_no, road_rev_no )"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?, ?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,? )";
        String query2 = " UPDATE switching_point_detail SET active='N'"
                + " WHERE switching_point_detail_id = ? and switching_rev_no = ? ";
        String query33 = "INSERT INTO switching_point_light_type_mapping( switching_point_detail_id, light_type_id, no_of_working, no_of_not_working, total_fittings,"
                + " switching_rev_no ) "
                + " VALUES (?,?,?,?,?,?) ";
        String query3 = "UPDATE switching_point_light_type_mapping  SET active = 'N' "
                + " WHERE switching_point_detail_id = ?  ";
        String query4 = " UPDATE pole SET active = 'N', pole_no = ? ,switching_point_detail_id = ?, switching_rev_no = ? , isSwitchingPoint = 'No' WHERE switching_point_detail_id = ?  AND switching_rev_no = ? AND isSwitchingPoint = 'Yes'";
        String query5 = " UPDATE pole SET pole_no = ?, switching_point_detail_id = ? ,switching_rev_no = ?, area_id=? , road_id= ?, road_rev_no=?, traffic_type_id=?  "
                + " WHERE switching_point_detail_id = ?  AND switching_rev_no = ?  AND isSwitchingPoint = 'Yes'";
        String query6 = "SELECT count(*) FROM pole WHERE switching_point_detail_id = ? AND switching_rev_no = ? AND isSwitchingPoint = 'Yes' AND active = 'Y'";
        String query7 = "UPDATE junction SET switching_rev_no = ? WHERE switching_point_detail_id = ? AND active = 'Y'";
        String isPoleExistsInPole = "select count(*) from pole where pole_no = ? and switching_point_detail_id != ?  AND switching_rev_no != ?  AND active = 'Y' ";

        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query1);
            /*pstmt.setInt(1, surveyTypeBean.getSwitching_point_detail_id());

            pstmt.setString(2, surveyTypeBean.getPole_no_s());
            pstmt.setString(3, surveyTypeBean.getGps_code_s());
            if (surveyTypeBean.getIsCheckedTrue().equals("Y")) {
                pstmt.setNull(4, java.sql.Types.INTEGER);
                pstmt.setNull(5, java.sql.Types.INTEGER);
                pstmt.setNull(7, java.sql.Types.INTEGER);
                pstmt.setNull(35, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(4, surveyTypeBean.getArea_id());
                pstmt.setInt(5, surveyTypeBean.getRoad_id());
                pstmt.setInt(7, surveyTypeBean.getTraffic_type_id());
                pstmt.setInt(35, surveyTypeBean.getRoad_rev_no());
            }

            
            //   pstmt.setInt(4, surveyTypeBean.getArea_id());
            
            //   pstmt.setInt(5, surveyTypeBean.getRoad_id());
            pstmt.setString(6, surveyTypeBean.getIs_working());
            //   pstmt.setInt(7, surveyTypeBean.getTraffic_type_id());
            pstmt.setString(8, surveyTypeBean.getCreated_by());
            pstmt.setString(9, surveyTypeBean.getRemark());
            pstmt.setInt(10, surveyTypeBean.getSwitching_rev_no() + 1);
            pstmt.setString(11, surveyTypeBean.getMeter_no_s());
            pstmt.setInt(12, surveyTypeBean.getPhase());
            pstmt.setInt(13, surveyTypeBean.getControl_mechanism_id());
            pstmt.setString(14, surveyTypeBean.getFuse());
            pstmt.setString(15, surveyTypeBean.getContacter());
            pstmt.setString(16, surveyTypeBean.getMccb());
            pstmt.setInt(17, surveyTypeBean.getFuse_id());
            pstmt.setInt(18, surveyTypeBean.getContacter_id());
            pstmt.setInt(19, surveyTypeBean.getMccb_id());
            pstmt.setString(20, surveyTypeBean.getTimer());
            pstmt.setInt(21, surveyTypeBean.getTimer_id());
            pstmt.setString(22, surveyTypeBean.getFuse_quantity());
            //   pstmt.setString(23, surveyTypeBean.getContacter_quantity());
            pstmt.setString(24, surveyTypeBean.getMccb_quantity());
            // pstmt.setString(25, surveyTypeBean.getTimer_quantity());

            pstmt.setDouble(26, surveyTypeBean.getLongitude());
            pstmt.setDouble(27, surveyTypeBean.getLattitude());
            pstmt.setDouble(28, surveyTypeBean.getMeasured_load());
            pstmt.setInt(29, surveyTypeBean.getFeeder_id());
            pstmt.setString(30, surveyTypeBean.getIsCheckedTrue());
            //   pstmt.setString(32, surveyTypeBean.getPole_no());
       /*     if (surveyTypeBean.getIsCheckedTrue().equals("Y") && surveyTypeBean.getPole_id() != 0) {
            pstmt.setInt(32, surveyTypeBean.getPole_rev_no());
            } else {
            pstmt.setNull(32, java.sql.Types.INTEGER);
            }   */
 /* pstmt.setInt(31, surveyTypeBean.getNo_of_poles());
            pstmt.setString(32, surveyTypeBean.getService_conn_no());
            pstmt.setString(33, surveyTypeBean.getIvrs_no());
            pstmt.setString(34, surveyTypeBean.getSwitching_point_name());*/

 /*pstmt.setString(35, surveyTypeBean.getController_model());
            pstmt.setString(36, surveyTypeBean.getMobile_no());
            pstmt.setString(37, surveyTypeBean.getSim_no());
            pstmt.setString(38, surveyTypeBean.getImei_no());
            pstmt.setString(39, surveyTypeBean.getPanel_file_no());*/
            //     pstmt.setInt(40, surveyTypeBean.getRoad_rev_no());
            pstmt.setString(1, surveyTypeBean.getSwitching_point_name());
            pstmt.setString(2, surveyTypeBean.getPole_no_s());
            pstmt.setString(3, surveyTypeBean.getGps_code_s());
            if (surveyTypeBean.getIsCheckedTrue().equals("Y")) {
                pstmt.setNull(4, java.sql.Types.INTEGER);
                pstmt.setNull(5, java.sql.Types.INTEGER);
                pstmt.setNull(7, java.sql.Types.INTEGER);
                pstmt.setNull(26, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(4, surveyTypeBean.getArea_id());
                pstmt.setInt(5, surveyTypeBean.getRoad_id());
                pstmt.setInt(7, surveyTypeBean.getTraffic_type_id());
                pstmt.setInt(26, surveyTypeBean.getRoad_rev_no());
            }
            pstmt.setString(6, surveyTypeBean.getIs_working());
            pstmt.setString(8, surveyTypeBean.getCreated_by());
            pstmt.setString(9, surveyTypeBean.getRemark());
            pstmt.setString(10, surveyTypeBean.getMeter_no_s());
            pstmt.setInt(11, surveyTypeBean.getPhase());
            pstmt.setInt(12, surveyTypeBean.getControl_mechanism_id());
            pstmt.setString(13, surveyTypeBean.getFuse());
            pstmt.setString(14, surveyTypeBean.getContacter());
            pstmt.setString(15, surveyTypeBean.getMccb());
            pstmt.setString(16, surveyTypeBean.getFuse_quantity());
            pstmt.setString(17, surveyTypeBean.getMccb_quantity());
            pstmt.setInt(18, surveyTypeBean.getNo_of_poles());
            pstmt.setDouble(19, surveyTypeBean.getLongitude());
            pstmt.setDouble(20, surveyTypeBean.getLattitude());
            pstmt.setString(21, surveyTypeBean.getService_conn_no());
            pstmt.setString(22, surveyTypeBean.getIvrs_no());
            pstmt.setDouble(23, surveyTypeBean.getMeasured_load());
            pstmt.setInt(24, surveyTypeBean.getFeeder_id());
            pstmt.setString(25, surveyTypeBean.getIsCheckedTrue());
            pstmt.setString(27, surveyTypeBean.getFuse1());
            pstmt.setString(28, surveyTypeBean.getFuse2());
            pstmt.setString(29, surveyTypeBean.getFuse3());
            pstmt.setString(30, surveyTypeBean.getMccb1());
            pstmt.setString(31, surveyTypeBean.getMccb2());
            pstmt.setString(32, surveyTypeBean.getMccb3());
            pstmt.setInt(33, surveyTypeBean.getFuse_id1());
            pstmt.setInt(34, surveyTypeBean.getFuse_id2());
            pstmt.setInt(35, surveyTypeBean.getFuse_id3());
            pstmt.setInt(36, surveyTypeBean.getMccb_id1());
            pstmt.setInt(37, surveyTypeBean.getMccb_id2());
            pstmt.setInt(38, surveyTypeBean.getMccb_id3());
            pstmt.setInt(39, surveyTypeBean.getContacter_id());
            pstmt.setString(40, surveyTypeBean.getContacter_capacity());
            pstmt.setString(41, surveyTypeBean.getContacter_make());
            pstmt.setInt(42, surveyTypeBean.getAuto_switch_type_id());
            pstmt.setInt(43, surveyTypeBean.getMain_switch_type_id());
            pstmt.setString(44, surveyTypeBean.getMain_switch_reading());
            pstmt.setInt(45, surveyTypeBean.getEnclosure_type_id());
            pstmt.setInt(46, surveyTypeBean.getSwitching_rev_no() + 1);
            pstmt.setInt(47, surveyTypeBean.getSwitching_point_detail_id());
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                pstmt.close();
                rowsAffected = 0;
                pstmt = connection.prepareStatement(query2);
                pstmt.setInt(1, surveyTypeBean.getSwitching_point_detail_id());
                pstmt.setInt(2, surveyTypeBean.getSwitching_rev_no());
                rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    pstmt.close();
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(query3);
                    pstmt.setInt(1, surveyTypeBean.getSwitching_point_detail_id());
                    rowsAffected = pstmt.executeUpdate();
//                    if (rowsAffected > 0) {
//                        pstmt.close();
//                        rowsAffected = 0;
//                        pstmt = connection.prepareStatement(query7);
//                        pstmt.setInt(1, surveyTypeBean.getSwitching_rev_no() + 1);
//                        pstmt.setInt(2, surveyTypeBean.getSwitching_point_detail_id());
//                        rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        pstmt.close();
                        rowsAffected = 0;
                        int loop = 0;
                        int flag = 0;
                        pstmt = connection.prepareStatement(query33);
                        int light_type_idArr[] = surveyTypeBean.getLight_type_id_M();
                        String working[] = surveyTypeBean.getWorking();
                        String n_working[] = surveyTypeBean.getN_working();
                        String quantity[] = surveyTypeBean.getQuantity();
                        for (int i = 0; i < light_type_idArr.length; i++) {

                            if (light_type_idArr[i] != 0) {
                                loop = i + 1;
                                pstmt.setInt(1, surveyTypeBean.getSwitching_point_detail_id());
                                pstmt.setInt(2, light_type_idArr[i]);
                                pstmt.setInt(3, Integer.parseInt(working[i].trim()));
                                pstmt.setInt(4, Integer.parseInt(n_working[i].trim()));
                                pstmt.setInt(5, Integer.parseInt(quantity[i].trim()));
                                pstmt.setInt(6, surveyTypeBean.getSwitching_rev_no() + 1);

                                rowsAffected = pstmt.executeUpdate();
                                flag = flag + rowsAffected;
                            } else {

                                break;
                            }
                        }
                        if (flag == loop) {
                            pstmt.close();
                            //rowsAffected = 0;
                            int localcount = 0;
                            pstmt = connection.prepareStatement(query6);
                            pstmt.setInt(1, surveyTypeBean.getSwitching_point_detail_id());
                            pstmt.setInt(2, surveyTypeBean.getSwitching_rev_no());
                            ResultSet rs = pstmt.executeQuery();
                            if (rs.next()) {
                                localcount = rs.getInt("count(*)");
                            }
                            if (localcount == 0) {
                                connection.commit();
                                rowsAffected = 1;
                            } else {
                                pstmt.close();
                                localcount = 0;
                                if (surveyTypeBean.getIsCheckedTrue().equals("Y")) {
                                    if (checkIfPoleAlreadyExists(isPoleExistsInPole, surveyTypeBean.getPole_no(), surveyTypeBean.getSwitching_point_detail_id(), surveyTypeBean.getSwitching_rev_no())) {
                                        pstmt = connection.prepareStatement(query5);
                                        pstmt.setString(1, surveyTypeBean.getPole_no());
                                        pstmt.setInt(2, surveyTypeBean.getSwitching_point_detail_id());
                                        pstmt.setInt(3, surveyTypeBean.getSwitching_rev_no() + 1);
                                        pstmt.setInt(4, surveyTypeBean.getArea_id());
                                        pstmt.setInt(5, surveyTypeBean.getRoad_id());
                                        pstmt.setInt(6, surveyTypeBean.getRoad_rev_no());
                                        pstmt.setInt(7, surveyTypeBean.getTraffic_type_id());
                                        pstmt.setInt(8, surveyTypeBean.getSwitching_point_detail_id());
                                        pstmt.setInt(9, surveyTypeBean.getSwitching_rev_no());
                                        localcount = pstmt.executeUpdate();
                                    } else {
                                        localcount = -2;
                                    }
                                } else {
                                    pstmt = connection.prepareStatement(query4);
                                    pstmt.setString(1, surveyTypeBean.getPole_no());
                                    pstmt.setInt(2, surveyTypeBean.getSwitching_point_detail_id());
                                    pstmt.setInt(3, surveyTypeBean.getSwitching_rev_no() + 1);
                                    pstmt.setInt(4, surveyTypeBean.getSwitching_point_detail_id());
                                    pstmt.setInt(5, surveyTypeBean.getSwitching_rev_no());
                                    localcount = pstmt.executeUpdate();
                                }
                                if (rowsAffected > 0) {
                                    connection.commit();
                                } else {
                                    connection.rollback();
                                }
                            }
                        } else {
                            connection.rollback();
                        }
                    } else {
                        connection.rollback();
                    }
//                    } else {
//                        connection.rollback();
//                    }
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException sqlE) {
                System.out.println("SQL exception while revising record........." + e);
            }
            System.out.println("error while updating record........." + e);
        } finally {
            try {
                connection.setAutoCommit(true);

            } catch (SQLException exp) {
                System.out.println("SQL exception while revising record........." + exp);
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

    public boolean checkIfPoleAlreadyExists(String q, String pole_no, int switching_point_detail_id, int switching_point_detail_revNo) throws Exception {
        int count = 0;
        String query = q;
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, pole_no);
        stmt.setInt(2, switching_point_detail_id);
        stmt.setInt(3, switching_point_detail_revNo);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        count = Integer.parseInt(rs.getString(1));
        return count > 0 ? false : true;
    }

    public int getNoOfRows(String searchPoleNoSwitch, String searchPoleType, String searchPoleNo, String searchAreaName, String searchRoadName, String service_conn_no_Search, String switching_pt_name_search, String zone, String service_conn_no, String meter_name_auto) {
        /*  String query = "SELECT count(*) "
        + "FROM switching_point_detail s,pole p, pole_type pt, area a, road r, traffic_type t, mounting_type m, "
        + "control_mechanism c,"
        + "fuse f, contacter ct, timer tm, mccb mcb "
        + "WHERE "
        + "  s.pole_id = p.pole_id "
        + "AND p.pole_type_id = pt.pole_type_id "
        + "AND p.mounting_type_id = m.mounting_type_id "
        + "AND s.road_id = r.road_id "
        + "AND s.area_id = a.area_id "
        + "AND s.fuse_id = f.fuse_id "
        + "AND s.contacter_id = ct.contacter_id "
        + "AND s.mccb_id = mcb.mccb_id "
        + "AND s.timer_id = tm.timer_id "
        + "AND s.traffic_type_id = t.traffic_type_id "
        + "AND s.control_mechanism_id = c.control_mechanism_id "
        //     + "AND (p.active='Cancelled'||p.active='Active') "
        + "AND IF('" + searchPoleNoSwitch + "' = '', s.pole_no_s LIKE '%%', s.pole_no_s =? ) "
        + "AND IF('" + searchPoleType + "' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "
        + "AND IF('" + searchPoleNo + "' = '', p.pole_no LIKE '%%', p.pole_no =? )"
        + "AND IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =? ) "
        + "AND IF('" + searchRoadName + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
        + "order by s.pole_no_s   */
//        String query = " SELECT count(*)"
//                + " FROM switching_point_detail s,pole p, area a, traffic_type t, mounting_type m,"
//                + " control_mechanism c,"
//                + " fuse f, contacter ct, timer tm, mccb mcb"
//                + " WHERE"
//                + " s.pole_id = p.pole_id"
//
//                + " AND p.mounting_type_id = m.mounting_type_id"
//                + " AND s.area_id = a.area_id"
//
//                + " And s.fuse_id = f.fuse_id"
//                + " And s.contacter_id = ct.contacter_id"
//                + " And s.timer_id = tm.timer_id"
//                + " And s.mccb_id = mcb.mccb_id"
//                + " AND s.traffic_type_id = t.traffic_type_id"
//                + " AND s.control_mechanism_id = c.control_mechanism_id"
//           //     + " AND IF('" + searchPoleNoSwitch + "' = '', s.pole_no_s LIKE '%%', s.pole_no_s =? ) "
//
//          //      + " AND IF('" + searchPoleNo + "' = '', p.pole_no LIKE '%%', p.pole_no =? )"
//          //      + " AND IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =? ) "
//           //     + " AND IF('" + searchRoadName + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
//                + " order by s.pole_no_s ";
        /*     String query = " SELECT count(*)"
        + " FROM switching_point_detail s,pole p, pole_type pt, area a, road r, traffic_type t, mounting_type m,"
        + " control_mechanism c,"
        + " fuse f, contacter ct, timer tm, mccb mcb, road_category rc, road_use ru"
        + " WHERE"
        + " s.pole_id = p.pole_id"
        + " and p.pole_type_id = pt.pole_type_id"
        + " AND p.mounting_type_id = m.mounting_type_id"
        + " AND s.area_id = a.area_id"
        + " And s.road_id = r.road_id"
        + " And r.road_use_id = ru.road_use_id"
        + " And r.category_id = rc.category_id"
        + " And s.fuse_id = f.fuse_id"
        + " And s.contacter_id = ct.contacter_id"
        + " And s.timer_id = tm.timer_id"
        + " And s.mccb_id = mcb.mccb_id"
        + " AND s.traffic_type_id = t.traffic_type_id"
        + " AND s.control_mechanism_id = c.control_mechanism_id"
        + " AND IF('" + searchPoleNoSwitch + "' = '', s.pole_no_s LIKE '%%', s.pole_no_s =? ) "
        + " AND IF('" + searchPoleType + "' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "
        + " AND IF('" + searchPoleNo + "' = '', p.pole_no LIKE '%%', p.pole_no =? )"
        + " AND IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =? ) "
        + " AND IF('" + searchRoadName + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
        + " AND IF('" + service_conn_no_Search + "' = '', s.service_conn_no LIKE '%%', s.service_conn_no =? ) "
        + " order by s.pole_no_s ";  */
//        String query = "SELECT count(*)"
//                + " FROM "
//                + " switching_point_detail s LEFT JOIN pole p "
//                + " ON s.switching_point_detail_id = p.switching_point_detail_id AND s.switching_rev_no = p.switching_rev_no and p.isSwitchingPoint = 'Yes' and p.active = 'Y' , "
//                + " area a, road r, traffic_type t,control_mechanism c, fuse f, "
//                + " contacter ct, timer tm, mccb mcb, road_category rc, road_use ru, ward w, city cty, feeder fd, zone z, division d "
//                + " WHERE "
//                + " IF (s.isOnPole = 'N', s.area_id = a.area_id , p.area_id = a.area_id ) And "
//                + " a.ward_id = w.ward_id AND "
//                + " w.city_id = cty.city_id AND "
//                + " s.feeder_id = fd.feeder_id AND "
//                + " fd.zone_id = z.zone_id AND "
//                + " z.division_id = d.division_id AND "
//                + " IF (s.isOnPole = 'N', s.road_id = r.road_id AND s.road_rev_no = r.road_rev_no, p.road_id = r.road_id AND p.road_rev_no = r.road_rev_no) And "
//                + " r.road_use_id = ru.road_use_id And "
//                + " r.category_id = rc.category_id And "
//                + " s.fuse_id = f.fuse_id And "
//                + " s.contacter_id = ct.contacter_id And "
//                + " s.timer_id = tm.timer_id And "
//                + " s.mccb_id = mcb.mccb_id AND "
//                + " s.active = 'Y' AND "
//                + " IF (s.isOnPole = 'N', s.traffic_type_id = t.traffic_type_id, p.traffic_type_id = t.traffic_type_id) AND "
//                + " s.control_mechanism_id = c.control_mechanism_id "
//                + " AND IF( ? = '', s.switching_point_name LIKE '%%', s.switching_point_name =? ) "
//                + " AND IF( ? = '', s.service_conn_no LIKE '%%', p.pole_no =? ) "
//                + " AND IF( ? = '', s.service_conn_no LIKE '%%', s.service_conn_no =? )  order by s.switching_point_name  ";
        /*String query = "SELECT count(*) "
                + "   FROM "
                + "   switching_point_detail s LEFT JOIN pole p "
                + "  ON s.switching_point_detail_id = p.switching_point_detail_id AND s.switching_rev_no = p.switching_rev_no and p.isSwitchingPoint = 'Yes' and p.active = 'Y' "
                //         + "  left join area a on  s.area_id = a.area_id  "
                + "  left join area a on IF (s.isOnPole = 'N', s.area_id = a.area_id , p.area_id = a.area_id ) "
                + " left join ward w on a.ward_id = w.ward_id "
                + " left join city cty on w.city_id = cty.city_id "
                //        + "  left join road r on  s.road_id = r.road_id AND s.road_rev_no = r.road_rev_no "
                + "  left join road r on IF (s.isOnPole = 'N', s.road_id = r.road_id AND s.road_rev_no = r.road_rev_no, p.road_id = r.road_id AND p.road_rev_no = r.road_rev_no) "
                + " left join road_category rc on r.category_id = rc.category_id "
                + " left join road_use ru on r.road_use_id = ru.road_use_id "
                //      + " left join traffic_type t on s.traffic_type_id = t.traffic_type_id "
                + " left join traffic_type t on IF (s.isOnPole = 'N', s.traffic_type_id = t.traffic_type_id, p.traffic_type_id = t.traffic_type_id) "
                + " left join feeder fd on s.feeder_id = fd.feeder_id "
                + " left join  zone z on fd.zone_id = z.zone_id "
                + " left join division d on z.division_id = d.division_id "
                + " ,control_mechanism c,fuse f, contacter ct, mccb mcb "//timer tm,
                + " WHERE  s.control_mechanism_id = c.control_mechanism_id and "
                + " s.fuse_id1 = f.fuse_id and "
                + " s.contacter_id = ct.contacter_id and "
               // + " s.timer_id1 = tm.timer_id   and "
                + " s.mccb_id1 = mcb.mccb_id and s.active = 'Y' "
                + " AND IF( ? = '', s.switching_point_name LIKE '%%', s.switching_point_name =? ) "
                + " AND IF( ? = '', s.service_conn_no LIKE '%%', p.pole_no =? ) "
                + " AND IF( ? = '', s.service_conn_no LIKE '%%', s.service_conn_no =? )  "
                + " AND IF( ? = '', s.service_conn_no LIKE '%%', s.zone =? )  "
                + " order by s.switching_point_name  ";

         */ String add = "";
        if (service_conn_no_Search.isEmpty()) {
            add = service_conn_no;
        } else {
            add = service_conn_no_Search;
        }
        String query = "select count(*) from switching_point_detail as t "
                + "left join fuse as f on t.fuse_id1=f.fuse_id "
                + "left join fuse as f1 on t.fuse_id2=f1.fuse_id "
                + "left join fuse as f2 on t.fuse_id3=f2.fuse_id "
                + "left join mccb as m on t.mccb_id1=m.mccb_id "
                + "left join mccb as m1 on t.mccb_id1=m1.mccb_id "
                + "left join mccb as m2 on t.mccb_id1=m2.mccb_id "
                + "left join pole as p on p.pole_id=t.pole_id "
                + "left join switch_type as s on t.auto_switch_type_id=s.switch_type_id "
                + "left join switch_type as s1 on t.main_switch_type_id=s1.switch_type_id "
                + "left join enclosure_type as e on e.enclosure_type_id=t.enclosure_type_id "
                + "left join area as a on t.area_id=a.area_id "
                + "left join type_of_premises as pt on pt.type_of_premises_id=t.type_of_premises_id "
                + "left join road as r on r.road_id=t.road_id "
                + "left join traffic_type as tt on tt.traffic_type_id=t.traffic_type_id "
                + "left join feeder as fe on fe.feeder_id=t.feeder_id "
                + "left join road_category as rc on rc.category_id=r.category_id "
                + "left join road_use as ru on ru.road_use_id=r.road_use_id "
                + "left join zone as z on fe.zone_id=z.zone_id "
                + "left join division as d on d.division_id = z.division_id AND d.active='Y' "
                + "left join ward as w on w.ward_id = a.ward_id "
                + "left join city as cy on cy.city_id = w.city_id "
                + "where t.active='Y' "
                + "AND IF( '" + meter_name_auto + "' = '', t.meter_name_auto LIKE '%%', t.meter_name_auto ='" + meter_name_auto + "' )"
                + " AND IF( '" + add + "' = '', t.service_conn_no LIKE '%%', t.service_conn_no ='" + add + "' )"
                + " AND IF( '" + searchPoleNo + "' = '', t.pole_no_s LIKE '%%', t.pole_no_s ='" + searchPoleNo + "' )"
                + " AND IF( '" + switching_pt_name_search + "' = '', t.switching_point_name LIKE '%%', t.switching_point_name ='" + switching_pt_name_search + "' )"
                + " AND IF( '" + zone + "' = '', z.zone LIKE '%%', z.zone ='" + zone + "' )"
                + "order by t.switching_point_detail_id";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows switching point detail model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<SwitchingPointSurveyBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchPoleNoSwitch, String searchPoleType, String searchPoleNo, String searchAreaName, String searchRoadName, String service_conn_no_Search, String searchSwitchingPtName, String zone, String service_conn_no, String meter_name_auto) {
        List<SwitchingPointSurveyBean> list = new ArrayList<SwitchingPointSurveyBean>();

        /*   String query = " SELECT s.switching_point_detail_id, s.pole_no_s, s.gps_code_s, s.is_working, s.active, s.remark, DATE_FORMAT(s.created_date,'%d-%m-%Y') AS created_date, "
        + " s.switching_rev_no, s.meter_no_s, s.ph,s.created_by, p.pole_no,p.pole_span,p.pole_height,p.gps_code, "
        + " r.road_name,a.area_name,t.traffic_type,c.control_mechanism_type, "
        + " s.fuse, s.contacter, s.mccb, f.fuse_type, ct.contacter_type, mcb.mccb_type, "
        + " s.timer, tm.timer_type, s.fuse_quantity, s.contacter_quantity, s.mccb_quantity, s.timer_quantity, "
        + " p.avg_lux_level,p.standard_lux_level,p.gps_code,p.max_avg_lux_level,p.min_avg_lux_level,"
        + " m.mounting_type, p.mounting_height, pt.pole_type,"
        + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',pltm.quantity)) "
        + " AS CHAR CHARACTER SET utf8) SEPARATOR '%%') "
        + " FROM pole_light_type_mapping AS pltm,light_type lt,light_source_type AS l,wattage AS w "
        + " WHERE pltm.light_type_id=lt.light_type_id AND p.pole_id=pltm.pole_id "
        + " AND lt.wattage_id=w.wattage_id "
        + " AND lt.source_id=l.source_type_id)AS source_wattage "
        + " FROM switching_point_detail s,pole p, pole_type pt, area a, road r, traffic_type t, mounting_type m, "
        + " control_mechanism c, "
        + " fuse f, contacter ct, timer tm, mccb mcb "
        + " WHERE  "
        + "  s.pole_id = p.pole_id "
        + " and p.pole_type_id = pt.pole_type_id "
        + " AND p.mounting_type_id = m.mounting_type_id "
        + " AND s.area_id = a.area_id "
        + " And s.road_id = r.road_id "
        + " And s.fuse_id = f.fuse_id "
        + " And s.contacter_id = ct.contacter_id "
        + " And s.timer_id = tm.timer_id "
        + " And s.mccb_id = mcb.mccb_id"

        + " AND s.traffic_type_id = t.traffic_type_id "
        + " AND s.control_mechanism_id = c.control_mechanism_id "
        //        + " AND (p.active='Cancelled'||p.active='Active') "
        + " AND IF('" + searchPoleNoSwitch + "' = '', s.pole_no_s LIKE '%%', s.pole_no_s =? ) "
        + " AND IF('" + searchPoleType + "' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "
        + " AND IF('" + searchPoleNo + "' = '', p.pole_no LIKE '%%', p.pole_no =? )"
        + " AND IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =? ) "
        + " AND IF('" + searchRoadName + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
        + " order by s.pole_no_s ";  */
//        String query = " SELECT s.longitude, s.lattitude ,s.switching_point_detail_id, s.switching_point_name, s.feeder ,s.pole_no_s, s.gps_code_s, s.is_working,"
//                + "  s.active, s.remark,"
//                + " DATE_FORMAT(s.created_date,'%d-%m-%Y') AS created_date,"
//                + " s.switching_rev_no, s.meter_no_s, s.ph,s.created_by, p.pole_no,p.pole_span,p.pole_height,p.gps_code,"
//                + "  a.area_name,t.traffic_type,c.control_mechanism_type,"
//                + " s.fuse, s.contacter, s.mccb, f.fuse_type, ct.contacter_type, mcb.mccb_type,"
//                + " s.timer, tm.timer_type, s.fuse_quantity, s.contacter_quantity, s.mccb_quantity, s.timer_quantity,"
//                + " p.avg_lux_level,p.standard_lux_level,p.max_avg_lux_level,p.min_avg_lux_level,"
//                + " m.mounting_type, p.mounting_height, "
//                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.total_fittings))"
//                + " AS CHAR CHARACTER SET utf8) SEPARATOR '__')"
//                + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w"
//                + " WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id"
//                + " AND lt.wattage_id=w.wattage_id"
//                + " AND lt.source_id=l.source_type_id)AS source_wattage,"
//                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.no_of_working,'/',spltm.no_of_not_working))"
//                + " AS CHAR CHARACTER SET utf8) SEPARATOR '__')"
//                + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w"
//                + " WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id"
//                + " AND lt.wattage_id=w.wattage_id"
//                + " AND lt.source_id=l.source_type_id)AS Isworking, s.no_of_poles, "
//                + " (select SUM(spm.total_fittings * w.wattage_value) from wattage w, light_type lt, switching_point_light_type_mapping spm "
//                + " where spm.light_type_id = lt.light_type_id and "
//                + " lt.wattage_id = w.wattage_id AND spm.switching_point_detail_id = s.switching_point_detail_id) as wattage "
//                + " FROM switching_point_detail s,pole p, area a,  traffic_type t, mounting_type m,"
//                + " control_mechanism c,"
//                + " fuse f, contacter ct, timer tm, mccb mcb"
//                + " WHERE"
//                + " s.pole_id = p.pole_id"
//
//                + " AND p.mounting_type_id = m.mounting_type_id"
//                + " AND s.area_id = a.area_id"
//
//                + " And s.fuse_id = f.fuse_id"
//                + " And s.contacter_id = ct.contacter_id"
//                + " And s.timer_id = tm.timer_id"
//                + " And s.mccb_id = mcb.mccb_id"
//                + " AND s.traffic_type_id = t.traffic_type_id"
//                + " AND s.control_mechanism_id = c.control_mechanism_id"
//
//           //     + " AND IF('" + searchPoleNoSwitch + "' = '', s.pole_no_s LIKE '%%', s.pole_no_s =? ) "
//
//         //       + " AND IF('" + searchPoleNo + "' = '', p.pole_no LIKE '%%', p.pole_no =? )"
//           //     + " AND IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =? ) "
//           //     + " AND IF('" + searchRoadName + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
//                + " order by s.pole_no_s "
//                 + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        /*   String query = " SELECT s.measured_load ,s.city, s.longitude, s.lattitude ,s.switching_point_detail_id, s.switching_point_name, s.feeder ,s.pole_no_s, s.gps_code_s, s.is_working,"
        + "  s.active, s.remark, s.service_conn_no, s.ivrs_no, "
        + " DATE_FORMAT(s.created_date,'%d-%m-%Y') AS created_date,"
        + " s.switching_rev_no, s.meter_no_s, s.ph,s.created_by, p.pole_no,p.pole_span,p.pole_height,p.gps_code,"
        + "  r.road_name,a.area_name,t.traffic_type,c.control_mechanism_type,"
        + " s.fuse, s.contacter, s.mccb, f.fuse_type, ct.contacter_type, mcb.mccb_type,"
        + " s.timer, tm.timer_type, s.fuse_quantity, s.contacter_quantity, s.mccb_quantity, s.timer_quantity,"
        + " p.avg_lux_level,p.standard_lux_level,p.max_avg_lux_level,p.min_avg_lux_level,"
        + " m.mounting_type, p.mounting_height, pt.pole_type,"
        + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.total_fittings))"
        + " AS CHAR CHARACTER SET utf8) SEPARATOR '__')"
        + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w"
        + " WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id"
        + " AND lt.wattage_id=w.wattage_id"
        + " AND lt.source_id=l.source_type_id)AS source_wattage,"
        + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.no_of_working,'/',spltm.no_of_not_working))"
        + " AS CHAR CHARACTER SET utf8) SEPARATOR '__')"
        + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w"
        + " WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id"
        + " AND lt.wattage_id=w.wattage_id"
        + " AND lt.source_id=l.source_type_id)AS Isworking, s.no_of_poles, "
        + " (select SUM(spm.total_fittings * w.wattage_value) from wattage w, light_type lt, switching_point_light_type_mapping spm "
        + " where spm.light_type_id = lt.light_type_id and "
        + " lt.wattage_id = w.wattage_id AND spm.switching_point_detail_id = s.switching_point_detail_id) as wattage, ru.road_use, rc.category_name "
        + " FROM switching_point_detail s,pole p, pole_type pt, area a, road r, traffic_type t, mounting_type m,"
        + " control_mechanism c,"
        + " fuse f, contacter ct, timer tm, mccb mcb, road_category rc, road_use ru"
        + " WHERE"
        + " s.pole_id = p.pole_id"
        + " and p.pole_type_id = pt.pole_type_id"
        + " AND p.mounting_type_id = m.mounting_type_id"
        + " AND s.area_id = a.area_id"
        + " And s.road_id = r.road_id"
        + " And r.road_use_id = ru.road_use_id"
        + " And r.category_id = rc.category_id"
        + " And s.fuse_id = f.fuse_id"
        + " And s.contacter_id = ct.contacter_id"
        + " And s.timer_id = tm.timer_id"
        + " And s.mccb_id = mcb.mccb_id"
        + " AND s.traffic_type_id = t.traffic_type_id"
        + " AND s.control_mechanism_id = c.control_mechanism_id"
        + " AND IF('" + searchPoleNoSwitch + "' = '', s.pole_no_s LIKE '%%', s.pole_no_s =? ) "
        + " AND IF('" + searchPoleType + "' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "
        + " AND IF('" + searchPoleNo + "' = '', p.pole_no LIKE '%%', p.pole_no =? )"
        + " AND IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =? ) "
        + " AND IF('" + searchRoadName + "' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
        + " AND IF('" + service_conn_no_Search + "' = '', s.service_conn_no LIKE '%%', s.service_conn_no =? ) "
        + " order by s.pole_no_s "
        + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;  */
 /*String query = "SELECT d.division_name, z.zone, s.measured_load ,cty.city_name,w.ward_no, s.longitude, s.lattitude ,"
                + " s.switching_point_detail_id, s.switching_point_name, fd.feeder_name , "
                + " s.pole_no_s, s.gps_code_s, s.is_working,  s.active, s.remark, s.service_conn_no, s.ivrs_no, "
                + " DATE_FORMAT(s.created_date,'%d-%m-%Y') AS created_date, s.switching_rev_no, s.meter_no_s, s.ph,s.created_by, "
                //+ " s.controller_model, s.mobile_no, s.sim_no, s.imei_no, s.panel_file_no, s.panel_transferred_status, "
                + " p.pole_no,p.pole_span,p.pole_height,p.gps_code,  r.road_name,a.area_name,t.traffic_type,c.control_mechanism_type, s.fuse, "
                + " s.contacter, s.mccb, f.fuse_type, ct.contacter_type, mcb.mccb_type, s.fuse_quantity, "//s.contacter_quantity, "s.timer,tm.timer_type,
                + " s.mccb_quantity, p.avg_lux_level,p.standard_lux_level,p.max_avg_lux_level,p.min_avg_lux_level, "//s.timer_quantity, 
                + " p.mounting_height, "
                + "  (SELECT GROUP_CONCAT(CAST((concat(spltm.switching_point_light_type_id,'-',spltm.light_type_id)) AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
                + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w "
                + " WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id "
                + " AND s.switching_rev_no = spltm.switching_rev_no  AND lt.wattage_id=w.wattage_id AND lt.source_id=l.source_type_id)AS map_id, "
                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.total_fittings)) AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
                + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w "
                + " WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id AND s.switching_rev_no = spltm.switching_rev_no "
                + " AND lt.wattage_id=w.wattage_id AND lt.source_id=l.source_type_id)AS source_wattage, "
                + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.no_of_working,'/',spltm.no_of_not_working)) "
                + " AS CHAR CHARACTER SET utf8) SEPARATOR '__') FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l, "
                + " wattage AS w WHERE spltm.light_type_id=lt.light_type_id AND s.switching_point_detail_id = spltm.switching_point_detail_id AND s.switching_rev_no = spltm.switching_rev_no AND "
                + " lt.wattage_id=w.wattage_id AND lt.source_id=l.source_type_id)AS Isworking, s.no_of_poles, "
                + " (select SUM(spm.total_fittings * w.wattage_value) from wattage w, light_type lt, switching_point_light_type_mapping spm "
                + " where spm.light_type_id = lt.light_type_id and  lt.wattage_id = w.wattage_id AND "
                + " spm.switching_point_detail_id = s.switching_point_detail_id) as wattage, ru.road_use, rc.category_name, s.isOnPole "
                + " FROM "
                + "   switching_point_detail s LEFT JOIN pole p "
                + "  ON s.switching_point_detail_id = p.switching_point_detail_id AND s.switching_rev_no = p.switching_rev_no and p.isSwitchingPoint = 'Yes' and p.active = 'Y' "
                //     + "  left join area a on  s.area_id = a.area_id  "
                + "  left join area a on IF (s.isOnPole = 'N', s.area_id = a.area_id , p.area_id = a.area_id ) "
                + " left join ward w on a.ward_id = w.ward_id "
                + " left join city cty on w.city_id = cty.city_id "
                //    + "  left join road r on  s.road_id = r.road_id AND s.road_rev_no = r.road_rev_no "
                + "  left join road r on IF (s.isOnPole = 'N', s.road_id = r.road_id AND s.road_rev_no = r.road_rev_no, p.road_id = r.road_id AND p.road_rev_no = r.road_rev_no) "
                + " left join road_category rc on r.category_id = rc.category_id "
                + " left join road_use ru on r.road_use_id = ru.road_use_id "
                //      + " left join traffic_type t on  s.traffic_type_id = t.traffic_type_id "
                + " left join traffic_type t on IF (s.isOnPole = 'N', s.traffic_type_id = t.traffic_type_id, p.traffic_type_id = t.traffic_type_id) "
                + " left join feeder fd on s.feeder_id = fd.feeder_id "
                + " left join  zone z on fd.zone_id = z.zone_id "
                + " left join division d on z.division_id = d.division_id "
                + " ,control_mechanism c,fuse f, fuse f2, fuse f3, contacter ct,  mccb mcb "//timer tm,
                + " WHERE "
                + " s.control_mechanism_id = c.control_mechanism_id and "
                + " s.fuse_id1 = f.fuse_id and "
                + " s.fuse_id2 = f2.fuse_id and "
                + " s.fuse_id3 = f3.fuse_id and "
                + " s.contacter_id = ct.contacter_id and "
                //+ " s.timer_id1 = tm.timer_id   and "
                + " s.mccb_id1 = mcb.mccb_id and s.active = 'Y' "
                + " AND IF( ? = '', s.service_conn_no LIKE '%%', s.zone =? ) "
                + " AND IF( ? = '', s.switching_point_name LIKE '%%', s.switching_point_name =? ) "
                + " AND IF( ? = '', s.service_conn_no LIKE '%%', p.pole_no =? ) "
                + " AND IF( ? = '', s.service_conn_no LIKE '%%', s.service_conn_no =? ) order by s.switching_point_name  LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
//                + " switching_point_detail s LEFT JOIN pole p "
//                + " ON s.switching_point_detail_id = p.switching_point_detail_id AND s.switching_rev_no = p.switching_rev_no and p.isSwitchingPoint = 'Yes' and p.active = 'Y' , "
//                + " area a, road r, traffic_type t,control_mechanism c, fuse f, "
//                + " contacter ct, timer tm, mccb mcb, road_category rc, road_use ru, ward w, city cty, feeder fd, zone z, division d "
//                + " WHERE "
//                + " IF (s.isOnPole = 'N', s.area_id = a.area_id , p.area_id = a.area_id ) And "
//                + " a.ward_id = w.ward_id AND "
//                + " w.city_id = cty.city_id AND "
//                + " s.feeder_id = fd.feeder_id AND "
//                + " fd.zone_id = z.zone_id AND "
//                + " z.division_id = d.division_id AND "
//                + " IF (s.isOnPole = 'N', s.road_id = r.road_id AND s.road_rev_no = r.road_rev_no, p.road_id = r.road_id AND p.road_rev_no = r.road_rev_no) And "
//                + " r.road_use_id = ru.road_use_id And "
//                + " r.category_id = rc.category_id And "
//                + " s.fuse_id = f.fuse_id And "
//                + " s.contacter_id = ct.contacter_id And "
//                + " s.timer_id = tm.timer_id And "
//                + " s.mccb_id = mcb.mccb_id AND "
//                + " s.active = 'Y' AND "
//                + " IF (s.isOnPole = 'N', s.traffic_type_id = t.traffic_type_id, p.traffic_type_id = t.traffic_type_id) AND "
//                + " s.control_mechanism_id = c.control_mechanism_id "
//                + " AND IF( ? = '', s.switching_point_name LIKE '%%', s.switching_point_name =? ) "
//                + " AND IF( ? = '', s.service_conn_no  LIKE '%%', p.pole_no =? ) "
//                + " AND IF( ? = '', s.service_conn_no LIKE '%%', s.service_conn_no =? )  order by s.switching_point_name  LIMIT 0,10 ";  // original end
        /*         + " AND IF('' = '', s.pole_no_s LIKE '%%', s.pole_no_s =? )  AND IF('' = '', pt.pole_type LIKE '%%', pt.pole_type =? ) "

        + " AND IF('' = '', m.mounting_type LIKE '%%', m.mounting_type =? ) "
         */

 /*try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, zone);
            pstmt.setString(2, zone);
            pstmt.setString(3, searchSwitchingPtName);
            pstmt.setString(4, searchSwitchingPtName);
            pstmt.setString(5, searchPoleNo);
            pstmt.setString(6, searchPoleNo);
            pstmt.setString(7, service_conn_no_Search);
            pstmt.setString(8, service_conn_no_Search);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SwitchingPointSurveyBean surveyType = new SwitchingPointSurveyBean();
                surveyType.setSwitching_point_detail_id(rset.getInt("switching_point_detail_id"));
                surveyType.setSurvey_rev_no(rset.getInt("switching_rev_no"));
                surveyType.setPole_no_s(rset.getString("pole_no_s"));
                surveyType.setSwitching_point_name(rset.getString("switching_point_name"));
                surveyType.setFeeder(rset.getString("feeder_name"));
                surveyType.setGps_code_s(rset.getString("gps_code_s"));
                surveyType.setMeter_no_s(rset.getString("meter_no_s"));
                surveyType.setPhase(rset.getInt("ph"));
                // surveyType.setSurvey_date(rset.getString("survey_date"));
                surveyType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                surveyType.setArea_name(rset.getString("area_name"));
                surveyType.setRoad_name(rset.getString("road_name"));
                surveyType.setTraffic_type(rset.getString("traffic_type"));
                //      surveyType.setPole_type(rset.getString("pole_type"));
                surveyType.setPole_span(rset.getString("pole_span"));
                surveyType.setPole_height(rset.getString("pole_height"));
                surveyType.setMounting_height(rset.getString("mounting_height"));
                surveyType.setCreated_date(rset.getString("created_date"));
                surveyType.setCreated_by(rset.getString("created_by"));
                surveyType.setRemark(rset.getString("remark"));
                //      surveyType.setMounting_type(rset.getString("mounting_type"));
                surveyType.setActive(rset.getString("active"));
                surveyType.setPole_no(rset.getString("pole_no"));
                surveyType.setGps_code(rset.getString("gps_code"));
                surveyType.setMax_avg_lux_level(rset.getString("max_avg_lux_level"));
                surveyType.setMin_avg_lux_level(rset.getString("min_avg_lux_level"));
                surveyType.setAvg_lux_level(rset.getString("avg_lux_level"));
                surveyType.setStandard_avg_lux_level(rset.getString("standard_lux_level"));
                surveyType.setIs_working(rset.getString("is_working"));
                surveyType.setSource_wattage(rset.getString("source_wattage"));
                surveyType.setIsworking(rset.getString("Isworking"));   // number & type of bulbs working / not working
                surveyType.setFuse(rset.getString("fuse"));
                surveyType.setFuse_type(rset.getString("fuse_type"));
                surveyType.setFuse_quantity(rset.getString("fuse_quantity"));
                surveyType.setContacter(rset.getString("contacter"));
                surveyType.setContacter_type(rset.getString("contacter_type"));
//                surveyType.setContacter_capacity(rset.getString("contacter_capacity"));
                //   surveyType.setTimer(rset.getString("timer"));
                //  surveyType.setTimer_type(rset.getString("timer_type"));
                //surveyType.setTimer_quantity(rset.getString("timer_quantity"));
                surveyType.setMccb(rset.getString("mccb"));
                surveyType.setMccb_type(rset.getString("mccb_type"));
                surveyType.setMccb_quantity(rset.getString("mccb_quantity"));
                surveyType.setNo_of_poles(rset.getInt("no_of_poles"));
                surveyType.setSwitching_load(rset.getInt("wattage"));
                surveyType.setLongitude(rset.getDouble("longitude"));
                surveyType.setLattitude(rset.getDouble("lattitude"));
                surveyType.setService_conn_no(rset.getString("service_conn_no"));
                surveyType.setIvrs_no(rset.getString("ivrs_no"));
                surveyType.setRoad_use(rset.getString("road_use"));
                surveyType.setRoad_category(rset.getString("category_name"));
                surveyType.setCity(rset.getString("city_name"));
                surveyType.setMeasured_load(rset.getDouble("measured_load"));
                surveyType.setDivision(rset.getString("division_name"));
                surveyType.setZone(rset.getString("zone"));
                surveyType.setWard_no(rset.getString("ward_no"));
                surveyType.setIsCheckedTrue(rset.getString("isOnPole"));
                //         surveyType.setPole_id(rset.getInt("pole_id"));
                //         surveyType.setPole_rev_no(rset.getInt("pole_rev_no"));
                surveyType.setMap_id(rset.getString("map_id"));

                /*surveyType.setController_model(rset.getString("controller_model"));
                surveyType.setMobile_no(rset.getString("mobile_no"));
                surveyType.setSim_no(rset.getString("sim_no"));
                surveyType.setImei_no(rset.getString("imei_no"));
                surveyType.setPanel_file_no(rset.getString("panel_file_no"));
                surveyType.setPanel_transferred_status(rset.getString("panel_transferred_status"));
                 
                list.add(surveyType);

            }
        } catch (Exception e) {
            System.out.println("Error in Show data of switching point detail is : " + e);
        }*/
        String add = "";
        if (service_conn_no_Search.isEmpty()) {
            add = service_conn_no;
        } else {
            add = service_conn_no_Search;
        }
        String query = "select z.zone,d.division_name,w.ward_no,cy.city_name, f.fuse_type as fuse_type1,f1.fuse_type as "
                + " fuse_type2,f2.fuse_type as fuse_type3,s.switch_type as auto_switch_type,s1.switch_type as main_switch_type,t.switching_point_detail_id ,"
                + "  t.pole_no_s, t.GPS_code_s, a.area_name,  tt.traffic_type, t.active,t.switching_rev_no, t.meter_no_s, ph,p.pole_no "
                + " ,m.mccb_type as mccb_type1, fuse_quantity, mccb_quantity, meter_no_s,t.created_date,t.created_by,t.remark, "
                + " t.longitude,t.lattitude, t.service_conn_no,t.ivrs_no,measured_load,  isOnPole,t.pole_id,fe.feeder_name, pt.type_of_premsis, fuse1,"
                + "  t.fuse2, t.fuse3,t.mccb1, t.mccb2, t.mccb3,  m1.mccb_type  as mccb_type2, m2.mccb_type as  mccb_type3, t.main_switch_rating,t.no_of_poles,"
                + "   e.enclosure_type, t.mccb, t.fuse,rc.category_name,ru.road_use, t.is_working, r.road_name,t.switching_point_name,t.contacter,t.contacter_capacity,"
                + "  (SELECT GROUP_CONCAT(CAST((concat(spltm.switching_point_light_type_id,'-',spltm.light_type_id)) AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
                + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w "
                + " WHERE spltm.light_type_id=lt.light_type_id AND t.switching_point_detail_id = spltm.switching_point_detail_id "
                + " AND t.switching_rev_no = spltm.switching_rev_no  AND lt.wattage_id=w.wattage_id AND lt.source_id=l.source_type_id)AS map_id ,"
                + "(SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.total_fittings)) AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
                + "FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w"
                + " WHERE spltm.light_type_id=lt.light_type_id AND t.switching_point_detail_id = spltm.switching_point_detail_id AND t.switching_rev_no = spltm.switching_rev_no"
                + " AND lt.wattage_id=w.wattage_id AND lt.source_id=l.source_type_id)AS source_wattage "
                + "  from switching_point_detail as t "
                + "left join fuse as f on t.fuse_id1=f.fuse_id "
                + "left join fuse as f1 on t.fuse_id2=f1.fuse_id "
                + "left join fuse as f2 on t.fuse_id3=f2.fuse_id "
                + "left join mccb as m on t.mccb_id1=m.mccb_id "
                + "left join mccb as m1 on t.mccb_id1=m1.mccb_id "
                + "left join mccb as m2 on t.mccb_id1=m2.mccb_id "
                + "left join pole as p on p.pole_id=t.pole_id "
                + "left join switch_type as s on t.auto_switch_type_id=s.switch_type_id "
                + "left join switch_type as s1 on t.main_switch_type_id=s1.switch_type_id "
                + "left join enclosure_type as e on e.enclosure_type_id=t.enclosure_type_id "
                + "left join area as a on t.area_id=a.area_id "
                + "left join type_of_premises as pt on pt.type_of_premises_id=t.type_of_premises_id "
                + "left join road as r on r.road_id=t.road_id "
                + "left join traffic_type as tt on tt.traffic_type_id=t.traffic_type_id "
                + "left join feeder as fe on fe.feeder_id=t.feeder_id "
                + "left join road_category as rc on rc.category_id=r.category_id "
                + "left join road_use as ru on ru.road_use_id=r.road_use_id "
                + "left join zone as z on fe.zone_id=z.zone_id "
                + "left join division as d on d.division_id = z.division_id AND d.active='Y' "
                + "left join ward as w on w.ward_id = a.ward_id_m "
                + " left join city as cy on cy.city_id = w.city_id "
                + " where t.active='Y' ";
//                + "AND IF( '" + meter_name_auto + "' = '', t.meter_name_auto LIKE '%%', t.meter_name_auto ='" + meter_name_auto + "' ) "
//                + "AND IF( '" + add + "' = '', t.service_conn_no LIKE '%%', t.service_conn_no ='" + add + "' ) "
//                + " AND IF( '" + searchPoleNo + "' = '', t.pole_no_s LIKE '%%', t.pole_no_s ='" + searchPoleNo + "' ) "
//                + "AND IF( '" + searchSwitchingPtName + "' = '', t.switching_point_name LIKE '%%', t.switching_point_name ='" + searchSwitchingPtName + "' ) "
//                + "AND IF( '" + zone + "' = '', z.zone LIKE '%%', z.zone ='" + zone + "' ) "
//                + "order by t.switching_point_detail_id ";
if(zone!=""){
        query=query+"and z.zone='"+zone+"'";
        }
if(searchPoleNo!=""){
        query=query+"and t.pole_no_s='"+searchPoleNo+"'";
        }
         if(searchSwitchingPtName!=""){
        query+=" and  t.switching_point_name='"+searchSwitchingPtName+"'";
        }
if(meter_name_auto!=""){
        query=query+"and t.meter_name_auto='"+meter_name_auto+"'";
        }
         if(add!=""){
        query+=" and t.service_conn_no='"+add+"'";
        }
              query+= "order by t.switching_point_detail_id ";      
        int rowsAffected = -1;
        try {
            connection.setAutoCommit(false);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                SwitchingPointSurveyBean surveyType = new SwitchingPointSurveyBean();
                surveyType.setSwitching_point_detail_id(rset.getInt("switching_point_detail_id"));
                surveyType.setSurvey_rev_no(rset.getInt("switching_rev_no"));
                surveyType.setPole_no_s(rset.getString("pole_no_s"));
                surveyType.setSwitching_point_name(rset.getString("switching_point_name"));
                surveyType.setFeeder(rset.getString("feeder_name"));
                surveyType.setGps_code_s(rset.getString("gps_code_s"));
                surveyType.setMeter_no_s(rset.getString("meter_no_s"));
                surveyType.setPhase(rset.getInt("ph"));
                // surveyType.setSurvey_date(rset.getString("survey_date"));
                //surveyType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                surveyType.setArea_name(rset.getString("area_name"));
                surveyType.setRoad_name(rset.getString("road_name"));
                surveyType.setTraffic_type(rset.getString("traffic_type"));
                //      surveyType.setPole_type(rset.getString("pole_type"));
                // surveyType.setPole_span(rset.getString("pole_span"));
                ///surveyType.setPole_height(rset.getString("pole_height"));
                //surveyType.setMounting_height(rset.getString("mounting_height"));
                surveyType.setCreated_date(rset.getString("created_date"));
                surveyType.setCreated_by(rset.getString("created_by"));
                surveyType.setRemark(rset.getString("remark"));
                //      surveyType.setMounting_type(rset.getString("mounting_type"));
                surveyType.setActive(rset.getString("active"));
                //surveyType.setPole_no(rset.getString("pole_no"));
                //surveyType.setGps_code(rset.getString("gps_code"));

                surveyType.setIs_working(rset.getString("is_working"));
                surveyType.setSource_wattage(rset.getString("source_wattage"));

                surveyType.setFuse(rset.getString("fuse"));
                surveyType.setFuse_type(rset.getString("fuse_type1"));
                surveyType.setFuse_type1(rset.getString("fuse_type2"));
                surveyType.setFuse_type2(rset.getString("fuse_type3"));
                //surveyType.setFuse_quantity(rset.getString("fuse_quantity"));
                surveyType.setContacter(rset.getString("contacter"));
                //surveyType.setContacter_type(rset.getString("contacter_type"));
//                surveyType.setContacter_capacity(rset.getString("contacter_capacity"));
                //   surveyType.setTimer(rset.getString("timer"));
                //  surveyType.setTimer_type(rset.getString("timer_type"));
                //surveyType.setTimer_quantity(rset.getString("timer_quantity"));
                surveyType.setMccb(rset.getString("mccb"));
                surveyType.setMccb_type(rset.getString("mccb_type1"));
                surveyType.setMccb_type1(rset.getString("mccb_type2"));
                surveyType.setMccb_type2(rset.getString("mccb_type3"));
                surveyType.setMccb_quantity(rset.getString("mccb_quantity"));
                surveyType.setNo_of_poles(rset.getInt("no_of_poles"));
                //surveyType.setSwitching_load(rset.getInt("wattage"));
                surveyType.setLongitude(rset.getDouble("longitude"));
                surveyType.setLattitude(rset.getDouble("lattitude"));
                surveyType.setService_conn_no(rset.getString("service_conn_no"));
                surveyType.setIvrs_no(rset.getString("ivrs_no"));
                surveyType.setRoad_use(rset.getString("road_use"));
                surveyType.setRoad_category(rset.getString("category_name"));
                surveyType.setCity(rset.getString("city_name"));
                surveyType.setMeasured_load(rset.getDouble("measured_load"));
                surveyType.setDivision(rset.getString("division_name"));
                surveyType.setZone(rset.getString("zone"));
                surveyType.setWard_no(rset.getString("ward_no"));
                surveyType.setIsCheckedTrue(rset.getString("isOnPole"));
                //         surveyType.setPole_id(rset.getInt("pole_id"));
                //         surveyType.setPole_rev_no(rset.getInt("pole_rev_no"));
                surveyType.setMap_id(rset.getString("map_id"));
                surveyType.setPole_no(rset.getString("pole_no"));
                /*surveyType.setController_model(rset.getString("controller_model"));
                surveyType.setMobile_no(rset.getString("mobile_no"));
                surveyType.setSim_no(rset.getString("sim_no"));
                surveyType.setImei_no(rset.getString("imei_no"));
                surveyType.setPanel_file_no(rset.getString("panel_file_no"));
                surveyType.setPanel_transferred_status(rset.getString("panel_transferred_status"));
                 */
                list.add(surveyType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    public JSONArray showJsonData()
//        {
//        JSONArray rowData = new JSONArray();
//        String query = null;
//        query = "SELECT f.feeder_name,top.type_of_premsis,t.meter_name_auto,s.general_image_details_id,s.image_name,switching_point_survey_id,s.survey_id,t.switching_point_survey_rev_no,s.survey_rev_no, "
//                + " meter_no,t.created_by,t.active, meter_functional,r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, "
//                + " mccb_functional,   switching_point_survey_rev_no, reason_id, no_of_phase, fuse1, fuse2, "
//                + " fuse3,t.fuse_id1, t.fuse_id2, t.fuse_id3, mccb1, mccb2, mccb3,t.mccb_id1, t.mccb_id2, t.mccb_id3, "
//                + " contacter_capacity, if(t.contacter_id is null,null,sr.contacter_type) as contacter_type ,meter_phase, "
//                + " meter_reading, if(t.contacter_make_id is null,null,sm.contacter_make) as contacter_make, "
//                + " auto_switch_type_id, main_switch_type_id, main_switch_rating, enclosure_type_id ,survey_file_no, "
//                + " DATE_FORMAT(survey_date, '%d-%m-%Y') AS survey_date, survey_page_no, mobile_no, pole_no, pole_rev_no, survey_type, status, image_name, "
//                + " s.lattitude, s.longitude, image_date_time,  data_entry_type_id, video_name, survey_pole_no, "
//                + " t.meter_name_auto, t.service_conn_no, t.previous_reading, t.consume_unit, t.amount "
//                + " FROM switching_point_survey as t "
//                +" LEFT JOIN(contacter as sr,contacter_make as sm) ON sr.contacter_id=t.contacter_id AND sm.contacter_make_id=t.contacter_make_id "
//                +" left join meters as m ON m.meter_name_auto=t.meter_name_auto AND m.final_revision='VALID' "
//                +" left join feeder as f ON f.feeder_id=m.feeder_id "
//                +" left join (premises_tariff_map as ptm, type_of_premises as top) ON m.premises_tariff_map_id=ptm.premises_tariff_map_id "
//                +" AND top.type_of_premises_id=ptm.type_of_premises_id "
//                + " ,survey as s "//,contacter as sr,contacter_make as sm "
//                + " where t.switching_point_survey_id=s.survey_id  "
//                + " and t.active='Y' and s.status='Y' "
//                + "  order by switching_point_survey_id ";
//
//        String query = "select z.zone,d.division_name,w.ward_no,cy.city_name, f.fuse_type as fuse_type1,f1.fuse_type as "
//   +" fuse_type2,f2.fuse_type as fuse_type3,s.switch_type as auto_switch_type,s1.switch_type as main_switch_type,t.switching_point_detail_id ,"
//  +"  t.pole_no_s, t.GPS_code_s, a.area_name,  tt.traffic_type, t.active,t.switching_rev_no, t.meter_no_s, ph,p.pole_no "
//   +" ,m.mccb_type as mccb_type1, fuse_quantity, mccb_quantity, meter_no_s,t.created_date,t.created_by,t.remark, "
//   +" t.longitude,t.lattitude, t.service_conn_no,t.ivrs_no,measured_load,  isOnPole,t.pole_id,fe.feeder_name, pt.type_of_premsis, fuse1,"
//  +"  t.fuse2, t.fuse3,t.mccb1, t.mccb2, t.mccb3,  m1.mccb_type  as mccb_type2, m2.mccb_type as  mccb_type3, t.main_switch_rating,t.no_of_poles,"
// +"   e.enclosure_type, t.mccb, t.fuse,rc.category_name,ru.road_use, t.is_working, r.road_name,t.switching_point_name,t.contacter,t.contacter_capacity,"
//  + "  (SELECT GROUP_CONCAT(CAST((concat(spltm.switching_point_light_type_id,'-',spltm.light_type_id)) AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
//  + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w "
//  + " WHERE spltm.light_type_id=lt.light_type_id AND t.switching_point_detail_id = spltm.switching_point_detail_id "
//    + " AND t.switching_rev_no = spltm.switching_rev_no  AND lt.wattage_id=w.wattage_id AND lt.source_id=l.source_type_id)AS map_id ,"
//    + "(SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.total_fittings)) AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
//    + "FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w"
//     +" WHERE spltm.light_type_id=lt.light_type_id AND t.switching_point_detail_id = spltm.switching_point_detail_id AND t.switching_rev_no = spltm.switching_rev_no"
//    + " AND lt.wattage_id=w.wattage_id AND lt.source_id=l.source_type_id)AS source_wattage "
//  +"  from switching_point_detail as t "
//  +"left join fuse as f on t.fuse_id1=f.fuse_id "
//  +"left join fuse as f1 on t.fuse_id2=f1.fuse_id "
//  +"left join fuse as f2 on t.fuse_id3=f2.fuse_id "
//  +"left join mccb as m on t.mccb_id1=m.mccb_id "
//  +"left join mccb as m1 on t.mccb_id1=m1.mccb_id "
//  +"left join mccb as m2 on t.mccb_id1=m2.mccb_id "
//  +"left join pole as p on p.pole_id=t.pole_id "
//  +"left join switch_type as s on t.auto_switch_type_id=s.switch_type_id "
//  +"left join switch_type as s1 on t.main_switch_type_id=s1.switch_type_id "
//  +"left join enclosure_type as e on e.enclosure_type_id=t.enclosure_type_id "
//  +"left join area as a on t.area_id=a.area_id "
//  +"left join type_of_premises as pt on pt.type_of_premises_id=t.type_of_premises_id "
//  +"left join road as r on r.road_id=t.road_id "
//  +"left join traffic_type as tt on tt.traffic_type_id=t.traffic_type_id "
//  +"left join feeder as fe on fe.feeder_id=t.feeder_id "
//  +"left join road_category as rc on rc.category_id=r.category_id "
//  +"left join road_use as ru on ru.road_use_id=r.road_use_id "
//  +"left join zone as z on fe.zone_id=z.zone_id "
//  +"left join division as d on d.division_id = z.division_id AND d.active='Y' "
//  +"left join ward as w on w.ward_id = a.ward_id "
//  +" left join city as cy on cy.city_id = w.city_id "
//  +" where t.active='Y' "
//  +" order by t.switching_point_detail_id ";
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                JSONObject obj = new JSONObject();
//                    obj.put("pole_no", rset.getString("pole_no"));
//                    obj.put("poll_no", rset.getString("survey_pole_no"));
//                    obj.put("file_no", rset.getString("survey_file_no"));
//                    obj.put("service_conn_no", rset.getString("service_conn_no"));
//
//                    obj.put("page_no", rset.getString("survey_page_no"));
//                    obj.put("survey_by", rset.getString("mobile_no"));
//                    obj.put("survey_type", rset.getString("survey_type"));
//                    obj.put("survey_date", rset.getString("survey_date"));
//                    obj.put("survey_remark", "");
//                    obj.put("meter_name_auto", rset.getString("meter_name_auto"));
//                    obj.put("lattitude", rset.getString("lattitude"));
//                    obj.put("longitude", rset.getString("longitude"));
//
//                    obj.put("img_name", rset.getString("image_name"));
//                    obj.put("vid_name", "");
//                    obj.put("survey_with_name", "");
//                    obj.put("survey_with", "");
//                    obj.put("meter_status", "");
//                    obj.put("meter_no", rset.getString("meter_no"));
//                    obj.put("phase", rset.getString("meter_phase"));
//                    obj.put("meter_reading", rset.getString("meter_reading"));
//                    obj.put("meter_functional", rset.getString("meter_functional"));
//                    obj.put("meter_address", "");
//                    obj.put("meter_remark", "");
//                    obj.put("fuse_status", rset.getString("fuse_functional"));
//                    obj.put("fuse1", rset.getString("fuse1"));
//                    obj.put("fuse1_type", getFuseTYpe(rset.getInt("fuse_id1")));
//                    obj.put("fuse2", rset.getString("fuse2"));
//                    obj.put("fuse2_type", getFuseTYpe(rset.getInt("fuse_id2")));
//                    obj.put("fuse3", rset.getString("fuse3"));
//                    obj.put("fuse3_type", getFuseTYpe(rset.getInt("fuse_id3")));
//                    obj.put("mccb_status", rset.getString("mccb_functional"));
//                    obj.put("mccb1", rset.getString("mccb1"));
//                    obj.put("mccb1_type", getMccbType(rset.getInt("mccb_id1")));
//                    obj.put("mccb2", rset.getString("mccb2"));
//                    obj.put("mccb2_type", getMccbType(rset.getInt("mccb_id2")));
//                    obj.put("mccb3", rset.getString("mccb3"));
//                    obj.put("mccb3_type", getMccbType(rset.getInt("mccb_id3")));
//                    obj.put("contacter_status", rset.getString("contacter_functional"));
//                    obj.put("contacter_type", rset.getString("contacter_type"));
//                    obj.put("contacter_capacity", rset.getString("contacter_capacity"));
//                    obj.put("contacter_make", rset.getString("contacter_make"));
//                    obj.put("b_phase", rset.getString("b_phase"));
//                    obj.put("r_phase", rset.getString("r_phase"));
//                    obj.put("y_phase", rset.getString("y_phase"));
//                    obj.put("power", rset.getString("power"));
//                    obj.put("auto_switch", getSwitchType(rset.getInt("auto_switch_type_id")));
//                    obj.put("main_switch", getSwitchType(rset.getInt("main_switch_type_id")));
//                    obj.put("main_switch_rating", rset.getString("main_switch_rating"));
//                    obj.put("enclosure", getEnclosureType(rset.getInt("enclosure_type_id")));
//                    obj.put("sp_remark", "");
//                    rowData.add(obj);
//           }
//        } catch (Exception e) {
//            System.out.println("Error inside show data of survey: " + e);
//        }
//        return rowData;
//    }
    public int cancelRecord(int switching_point_detail_id, int switching_rev_no) {

        String query1 = "UPDATE switching_point_detail SET active='N' WHERE switching_point_detail_id = " + switching_point_detail_id
                + " and switching_rev_no = " + switching_rev_no;
        String query2 = "UPDATE pole SET active = 'N' WHERE switching_point_detail_id =" + switching_point_detail_id + " AND switching_rev_no=" + switching_rev_no;
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
            } catch (SQLException q) {
                System.out.println("Error inside CANCEL Switching point detail:" + q);
            }
            System.out.println("Error: " + e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException q) {
                System.out.println("Error inside CANCEL Switching point detail:" + q);
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
