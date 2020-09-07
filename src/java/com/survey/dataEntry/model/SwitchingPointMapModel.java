/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.survey.tableClasses.SwitchingPointMapBean;
import com.survey.tableClasses.SwitchingPointSurveyBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.jasper.tagplugins.jstl.core.Catch;

/**
 *
 * @author Administrator
 */
public class SwitchingPointMapModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "LightGreen";
    private final String COLOR_ALREADY_EXISTS = "yellow";
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

    public List<String> getPoleNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT pole_no FROM pole GROUP BY pole_no ORDER BY pole_no ";
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

    public List<SwitchingPointMapBean> showAllData(String searchIVRSNo, String searchZone) {
        List<SwitchingPointMapBean> listAll = new ArrayList<SwitchingPointMapBean>();


//        String query = " SELECT s.switching_point_detail_id, s.switching_point_name, s.feeder ,s.pole_no_s, s.gps_code_s, s.is_working,"
//                + "  s.active, s.remark,"
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
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            /*   pstmt.setString(1, searchPoleNoSwitch);
//            pstmt.setString(2, searchPoleType);
//            pstmt.setString(3, searchPoleNo);
//            pstmt.setString(4, searchAreaName);
//            pstmt.setString(5, searchRoadName); */
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                SwitchingPointSurveyBean surveyType = new SwitchingPointSurveyBean();
//                //           surveyType.setSurvey_id(rset.getInt("switching_point_detail_id"));
//                //           surveyType.setSurvey_rev_no(rset.getInt("switching_rev_no"));
//                surveyType.setPole_no_s(rset.getString("pole_no_s"));
//                surveyType.setSwitching_point_name(rset.getString("switching_point_name"));
//                surveyType.setFeeder(rset.getString("feeder"));
//                //           surveyType.setGps_code_s(rset.getString("gps_code_s"));
//                surveyType.setMeter_no_s(rset.getString("meter_no_s"));
//                //            surveyType.setPhase(rset.getString("ph"));
//                // surveyType.setSurvey_date(rset.getString("survey_date"));
//                //            surveyType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
//                surveyType.setArea_name(rset.getString("area_name"));
//                surveyType.setRoad_name(rset.getString("road_name"));
//                //            surveyType.setTraffic_type(rset.getString("traffic_type"));
//                //            surveyType.setPole_type(rset.getString("pole_type"));
//                //            surveyType.setPole_span(rset.getString("pole_span"));
//                //            surveyType.setPole_height(rset.getString("pole_height"));
//                //            surveyType.setMounting_height(rset.getString("mounting_height"));
//                //            surveyType.setCreated_date(rset.getString("created_date"));
//                //             surveyType.setCreated_by(rset.getString("created_by"));
//                surveyType.setRemark(rset.getString("remark"));
//                //             surveyType.setMounting_type(rset.getString("mounting_type"));
//                //             surveyType.setActive(rset.getString("active"));
//                surveyType.setPole_no(rset.getString("pole_no"));
//                //             surveyType.setGps_code(rset.getString("gps_code"));
//                //            surveyType.setMax_avg_lux_level(rset.getString("max_avg_lux_level"));
//                //            surveyType.setMin_avg_lux_level(rset.getString("min_avg_lux_level"));
//                //            surveyType.setAvg_lux_level(rset.getString("avg_lux_level"));
//                //             surveyType.setStandard_avg_lux_level(rset.getString("standard_lux_level"));
//                //             surveyType.setIs_working(rset.getString("is_working"));
//                surveyType.setSource_wattage(rset.getString("source_wattage"));
//                surveyType.setIsworking(rset.getString("Isworking"));   // number & type of bulbs working / not working
//   /*             surveyType.setFuse(rset.getString("fuse"));
//                surveyType.setFuse_type(rset.getString("fuse_type"));
//                surveyType.setFuse_quantity(rset.getString("fuse_quantity"));
//                surveyType.setContacter(rset.getString("contacter"));
//                surveyType.setContacter_type(rset.getString("contacter_type"));
//                surveyType.setContacter_quantity(rset.getString("contacter_quantity"));
//                surveyType.setTimer(rset.getString("timer"));
//                surveyType.setTimer_type(rset.getString("timer_type"));
//                surveyType.setTimer_quantity(rset.getString("timer_quantity"));
//                surveyType.setMccb(rset.getString("mccb"));
//                surveyType.setMccb_type(rset.getString("mccb_type"));
//                surveyType.setMccb_quantity(rset.getString("mccb_quantity"));    */
//                surveyType.setNo_of_poles(rset.getInt("no_of_poles"));
//                surveyType.setSwitching_load(rset.getInt("wattage"));
//                listAll.add(surveyType);
//            }
   /*     String query =
                " select"
                + " spd.service_conn_no as SCNo_Switching, m.meter_service_number as SCNo_Meter,"
                + " spd.ivrs_no as IVRS_Switching, m.ivrs_no as IVRS_Meter,"
                + "  spd.switching_point_name as Switching_SPN, sp.switching_point as Meter_SPN,"
                + " spd.city as Switching_Zone, z.zone as Meter_Zone,"
                + " spd.feeder as Switching_Feeder, f.feeder_name as Meter_Feeder,"
                + " concat_ws(' , ',r.road_name,a.area_name) as Address_Switching,"
                + " concat_ws(' , ',sp.address1,sp.address2,sp.address3) as Address_Meter"
                + " from switching_point_map spm, meters m, switching_point_detail spd, switching_point sp, feeder f, zone z, road r, area a "
                + " where "
                + " spm.switching_point_detail_id = spd.switching_point_detail_id and "
                + " spm.meter_id = m.meter_id "
                + " and "
                + " sp.switching_point_id = m.switching_point_id"
                + " and "
                + " m.feeder_id = f.feeder_id "
                + " and "
                + " f.zone_id = z.zone_id"
                + " and"
                + " spd.road_id = r.road_id "
                + " and "
                + " spd.area_id = a.area_id "
                + " and m.final_revision='VALID' group by m.ivrs_no ";  */
        String query = "select spm.switching_point_map_id,spd.service_conn_no as SCNo_Switching, m.meter_service_number as SCNo_Meter, "
        + " spd.ivrs_no as IVRS_Switching, m.ivrs_no as IVRS_Meter,spd.switching_point_name as Switching_SPN, sp.switching_point as Meter_SPN, "
       // + " spd.city as Switching_Zone, "
        + " spd.zone as Switching_Zone , "
        + " z.zone as Meter_Zone,spd.feeder as Switching_Feeder, f.feeder_name as Meter_Feeder, "
        + " spd.measured_load , m.sanctioned_load_kw as sanctioned_load,concat_ws(' , ',r.road_name,a.area_name) as Address_Switching, "
        + " concat_ws(' , ',sp.address1,sp.address2,sp.address3) as Address_Meter "
        + " from switching_point_map spm left join meters m on spm.meter_id = m.meter_id and m.final_revision='VALID' and  m.active='Y'"
        + " left join feeder f on m.feeder_id = f.feeder_id "
        + " left join switching_point sp on sp.switching_point_id = m.switching_point_id "
        + " left join zone z on f.zone_id = z.zone_id "
        + " ,switching_point_detail spd,  road r, area a "
        + " where spm.switching_point_detail_id = spd.switching_point_detail_id and  spd.active='Y' and spd.road_id = r.road_id and spd.area_id = a.area_id "
        + " AND IF('" + searchIVRSNo + "' = '', spd.ivrs_no LIKE '%%', spd.ivrs_no =?) "
       // + " AND IF('" + searchService_Conn_No + "' = '', spd.service_conn_no LIKE '%%', spd.service_conn_no =?) "
        + " AND IF('" + searchZone + "' = '', spd.zone LIKE '%%', spd.zone =?) GROUP BY m.ivrs_no ORDER BY spd.zone";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchIVRSNo);
            pstmt.setString(2, searchZone);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SwitchingPointMapBean bean = new SwitchingPointMapBean();
                bean.setService_conn_no(rset.getString("SCNo_Switching"));
                bean.setSevice_conn_no_meter(rset.getString("SCNo_Meter"));
                bean.setIvrs_no(rset.getString("IVRS_Switching"));
                bean.setIvrs_no_meter(rset.getString("IVRS_Meter"));
                bean.setSwitching_point_name(rset.getString("Switching_SPN"));
                bean.setSwitching_point_name_meter(rset.getString("Meter_SPN"));
                bean.setZone(rset.getString("Switching_Zone"));
                bean.setZone_meter(rset.getString("Meter_Zone"));
                bean.setFeeder(rset.getString("Switching_Feeder"));
                bean.setFeeder_meter(rset.getString("Meter_Feeder"));
                bean.setAddress(rset.getString("Address_Switching"));
                bean.setAddress_meter(rset.getString("Address_Meter"));
                listAll.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in  showAllData of Switching-Meter Map detail is : " + e);
        }
        return listAll;
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<SwitchingPointMapBean> listAll) {
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

    public List<String> getsearchIVRSNo(String q) {
        List<String> list = new ArrayList<String>();
        //  String query = "select ivrs_no from switching_point_detail WHERE ivrs_no != 'No Data'";
        String query = "select "
                + " spd.ivrs_no "
                + " from switching_point_map spm left join switching_point_detail spd "
                + " on spm.switching_point_detail_id = spd.switching_point_detail_id ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ivrs_no = rset.getString("ivrs_no");
                if (ivrs_no.startsWith(q)) {
                    list.add(ivrs_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such IVRS exists in Map.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside Switching point Map Model - " + e);
        }
        return list;
    }
    public List<String> getIVRSNo(String q) {
        List<String> list = new ArrayList<String>();
          String query = "select ivrs_no from switching_point_detail WHERE ivrs_no != 'No Data'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ivrs_no = rset.getString("ivrs_no");
                if (ivrs_no.startsWith(q)) {
                    list.add(ivrs_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such IVRS exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside Switching point Map Model - " + e);
        }
        return list;
    }

    public List<String> getsearchService_Conn_No(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select service_conn_no from switching_point_detail ";
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
                list.add("No such Service Conn. No. exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside getFeder_switch model of switching point Map - " + e);
        }
        return list;
    }

    public List<String> getsearchZone(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select zone from switching_point_detail group by zone order by zone";
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
                list.add("No such Zone exists.");
            }


        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside getFeder_switch model of switching point Map - " + e);
        }
        return list;
    }

    public List<String> getivrs_meter(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select ivrs_no from meters WHERE final_revision='VALID' group by ivrs_no";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ivrs_no = rset.getString("ivrs_no");
                if (ivrs_no.startsWith(q)) {
                    list.add(ivrs_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such IVRS No. exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside getFeder_switch model of switching point Map - " + e);
        }
        return list;
    }

    public List<String> getMeter_detail(String q, String feeder) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT meter_no_s  FROM switching_point_detail WHERE feeder = ? GROUP BY meter_no_s";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, feeder);

            ResultSet rset = stm.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String meter_no_s = rset.getString("meter_no_s");
                if (meter_no_s.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(meter_no_s);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Meter No. exists.");
            }
        } catch (Exception e) {
            System.out.println("getWard ERROR inside mere no of switching point map - " + e);
        }
        return list;
    }

    public List<String> getMeter_meter(String q, String feeder) {
        List<String> list = new ArrayList<String>();
        String query = "select m.meter_name  FROM  meters m , feeder f WHERE m.feeder_id = f.feeder_id AND f.feeder_name=? GROUP BY m.meter_name";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, feeder);
            ResultSet rset = stm.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String meter_name = rset.getString("m.meter_name");
                if (meter_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(meter_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Meter No. exists.");
            }
        } catch (Exception e) {
            System.out.println("getWard ERROR inside get Meter in Switching Point Map - " + e);
        }
        return list;
    }

    public List<String> getSwitching_pt_detail(String q, String meterNo) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT switching_point_name FROM switching_point_detail WHERE meter_no_s = ? ";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, meterNo);
            ResultSet rset = stm.executeQuery();
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
                list.add("No such Switching point exists.");
            }
        } catch (Exception e) {
            System.out.println("getWard ERROR inside Switching point Map- " + e);
        }
        return list;
    }

    public List<String> getSwitching_pt_meter(String q, String meterNo) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT sp.switching_point FROM  switching_point sp, meters m  WHERE m.switching_point_id = sp.switching_point_id AND m.meter_name=? GROUP BY switching_point ";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, meterNo);
            ResultSet rset = stm.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switching_point = rset.getString("switching_point");
                if (switching_point.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(switching_point);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Switching point exists.");
            }
        } catch (Exception e) {
            System.out.println("getWard ERROR inside Switching point Map- " + e);
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

    public List<String> getTimerType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT timer_type FROM timer GROUP BY timer_type ORDER BY timer_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String timer_type = rset.getString("timer_type");
                if (timer_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(timer_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Timer Type exists.");
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
        int pole_type_id = 0;
        String query = " SELECT pole_id FROM pole WHERE pole_no = ? group by pole_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, pole_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            pole_type_id = rset.getInt("pole_id");
        } catch (Exception e) {
            System.out.println("SurveyModel getPoleId() Error: " + e);
        }
        return pole_type_id;
    }

    public int getAreaId(String area_name) {
        int area_id = 0;
        String query = " SELECT area_id FROM area WHERE area_name = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, area_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            area_id = rset.getInt("area_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getAreaId() Error: " + e);
        }
        return area_id;
    }

    public int getRoadId(String road_name) {
        int road_id = 0;
        String query = " SELECT road_id FROM road WHERE road_name = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, road_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            road_id = rset.getInt("road_id");
        } catch (Exception e) {
            System.out.println("SwitchSuerveyModel getRoadId() Error: " + e);
        }
        return road_id;
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

    public int getSwitchingPointId(String switching_point_name) {
        int switch_id = 0;
        String query = " SELECT switching_point_id FROM switching_point WHERE switching_point = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, switching_point_name);
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

    public int isIVRSAlreadyExists(String ivrs_no) throws Exception {
        int rowsAffected = 0;
        String query = "SELECT count(*) FROM switching_point_map WHERE switching_point_detail_id =(SELECT switching_point_detail_id  FROM switching_point_detail WHERE ivrs_no = ?)";

        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, ivrs_no);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            rowsAffected = rs.getInt("count(*)");
        }

        return rowsAffected;
    }

    public int getMeterID(String ivrs_no_meter) {
        int meter_id = 0;
        String query = "SELECT meter_id FROM  meters WHERE ivrs_no = ? AND final_revision='VALID' GROUP BY ivrs_no";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ivrs_no_meter.trim());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                meter_id = rs.getInt("meter_id");
            } else {
                meter_id = 0;
            }
        } catch (Exception e) {
        }
        return meter_id;
    }

   public int getSwitchingPointDetailId(String ivrs_no_survey) {
        int switching_point_detail_id = 0;
        String query = "SELECT switching_point_detail_id FROM  switching_point_detail WHERE ivrs_no = ? and active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ivrs_no_survey.trim());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                switching_point_detail_id = rs.getInt("switching_point_detail_id");
            } else {
                switching_point_detail_id = 0;
            }
        } catch (Exception e) {
        }
        return switching_point_detail_id;
    }

public String getMeterSanctionedLoad(String ivrs_no_survey) {
        String  meter_sanction_load ="";
        String query = "SELECT sanctioned_load_kw FROM  meters WHERE ivrs_no = ? AND final_revision='VALID' and  active='Y' GROUP BY ivrs_no";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ivrs_no_survey.trim());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                meter_sanction_load = rs.getString("sanctioned_load_kw");
            } else {
                meter_sanction_load ="";
            }
        } catch (Exception e) {
        }
        return meter_sanction_load;
    }


   public String getSwitchingPointMeasuredLoad(String ivrs_no_survey) {
        String measured_load ="";
        
        String query = "SELECT measured_load FROM  switching_point_detail WHERE ivrs_no = ? and active='Y'";
    
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ivrs_no_survey.trim());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                measured_load = rs.getString("measured_load");
            } else {
                measured_load ="";
            }
        } catch (Exception e) {
        }
        return measured_load;
    }


public int insertRecord(String ivrs_no_survey, String ivrs_no_meter) {

        String query = "INSERT INTO switching_point_map (switching_point_detail_id, meter_id,measured_load,sanctioned_load) values "
                + " ( ?,?,?,?)";

        int rowsAffected = 0;
        int meter_id = 0;
        int switching_point_detail_id=0;
        String  measured_load="";
        String  sanctioned_load="";
        int isExist = 0;
        try {
            isExist = isIVRSAlreadyExists(ivrs_no_survey);
            if (isExist == 0) {
                PreparedStatement pstmt = connection.prepareStatement(query);
                
                switching_point_detail_id = getSwitchingPointDetailId(ivrs_no_survey);

                measured_load = getSwitchingPointMeasuredLoad(ivrs_no_meter);
                sanctioned_load = getMeterSanctionedLoad(ivrs_no_survey);


                pstmt.setInt(1, switching_point_detail_id);
                meter_id = getMeterID(ivrs_no_meter);
                pstmt.setInt(2, meter_id);               
                pstmt.setString(3, measured_load);
                pstmt.setString(4, sanctioned_load);


                rowsAffected = pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            if (isExist != 0) {
                message = "Record Already Exists.";
                msgBgColor = COLOR_ALREADY_EXISTS;
            } else {
                message = "Cannot save the record, some error.";
                msgBgColor = COLOR_ERROR;
            }

        }
        return rowsAffected;
    }
    /*
    public int reviseRecord(SwitchingPointSurveyBean surveyTypeBean) {
    String query1 = "INSERT INTO switching_point_detail (switching_point_detail_id, switching_point_id, pole_no_s, gps_code_s, pole_id, area_id, road_id, is_working, "
    + " traffic_type_id, created_by, remark, switching_rev_no, meter_no_s, ph, "
    + " control_mechanism_id, fuse, contacter, mccb, fuse_id, "
    + " contacter_id, mccb_id, timer, timer_id, "
    + " fuse_quantity, contacter_quantity, mccb_quantity, timer_quantity ) "
    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) ";
    String query2 = " UPDATE switching_point_detail SET active='Revised'"
    + " WHERE switching_point_detail_id = ? and switching_rev_no = ? ";

    int rowsAffected = 0;
    try {
    PreparedStatement pstmt = null;
    pstmt = connection.prepareStatement(query1);
    pstmt.setInt(1, surveyTypeBean.getSurvey_id());
    pstmt.setInt(2, surveyTypeBean.getSwitching_point_id());
    //  pstmt.setString(3, surveyTypeBean.getSurvey_date());
    pstmt.setString(3, surveyTypeBean.getPole_no_s());
    pstmt.setString(4, surveyTypeBean.getGps_code_s());
    pstmt.setInt(5, surveyTypeBean.getPole_type_id());
    pstmt.setInt(6, surveyTypeBean.getArea_id());
    pstmt.setInt(7, surveyTypeBean.getRoad_id());
    pstmt.setString(8, surveyTypeBean.getIs_working());
    pstmt.setInt(9, surveyTypeBean.getTraffic_type_id());
    pstmt.setString(10, surveyTypeBean.getCreated_by());
    pstmt.setString(11, surveyTypeBean.getRemark());
    pstmt.setInt(12, surveyTypeBean.getSurvey_rev_no() + 1);
    pstmt.setString(13, surveyTypeBean.getMeter_no_s());
    pstmt.setString(14, surveyTypeBean.getPhase());
    pstmt.setInt(15, surveyTypeBean.getControl_mechanism_id());
    pstmt.setString(16, surveyTypeBean.getFuse());
    pstmt.setString(17, surveyTypeBean.getContacter());
    pstmt.setString(18, surveyTypeBean.getMccb());
    pstmt.setInt(19, surveyTypeBean.getFuse_id());
    pstmt.setInt(20, surveyTypeBean.getContacter_id());
    pstmt.setInt(21, surveyTypeBean.getMccb_id());
    pstmt.setString(22, surveyTypeBean.getTimer());
    pstmt.setInt(23, surveyTypeBean.getTimer_id());
    pstmt.setString(24, surveyTypeBean.getFuse_quantity());
    pstmt.setString(25, surveyTypeBean.getContacter_quantity());
    pstmt.setString(26, surveyTypeBean.getMccb_quantity());
    pstmt.setString(27, surveyTypeBean.getTimer_quantity());

    rowsAffected = pstmt.executeUpdate();
    if (rowsAffected > 0) {

    pstmt = connection.prepareStatement(query2);
    pstmt.setInt(1, surveyTypeBean.getSurvey_id());
    pstmt.setInt(2, surveyTypeBean.getSurvey_rev_no());
    rowsAffected = pstmt.executeUpdate();
    }
    } catch (Exception e) {
    System.out.println("error while updating record........." + e);
    }
    if (rowsAffected > 0) {

    message = "Record Revised successfully.";
    msgBgColor = COLOR_OK;
    } else {
    message = "Cannot revise the record, some error.";
    msgBgColor = COLOR_ERROR;
    }
    return rowsAffected;
    }  */

    public int getNoOfRows(String searchIVRSNo, String searchService_Conn_No, String searchZone) {
        int noOfRows = 0;
        try {
            String query = " SELECT count(*) FROM ("
                    + "select count(*) "
                    + " from switching_point_map spm left join meters m on spm.meter_id = m.meter_id and m.final_revision='VALID' "
                    + " left join feeder f on m.feeder_id = f.feeder_id "
                    + " left join switching_point sp on sp.switching_point_id = m.switching_point_id "
                    + " left join zone z on f.zone_id = z.zone_id "
                    + " ,switching_point_detail spd,  road r, area a "
                    + " where spm.switching_point_detail_id = spd.switching_point_detail_id and spd.road_id = r.road_id and spd.area_id = a.area_id "
                    + " AND IF('" + searchIVRSNo + "' = '', spd.ivrs_no LIKE '%%', spd.ivrs_no =?) "
                    //     + " AND IF('" + searchService_Conn_No + "' = '', spd.service_conn_no LIKE '%%', spd.service_conn_no =?) "
                    + " AND IF('" + searchZone + "' = '', spd.zone LIKE '%%', spd.zone=?) "
                    + ""
                    + "  GROUP BY m.ivrs_no ORDER BY spd.zone"
                    + " ) AS total_count";

            /*    String query = "SELECT count(*) FROM ("
            + " select count(*)"
            + " from switching_point_map spm, meters m, switching_point_detail spd, switching_point sp, feeder f, zone z, road r, area a "
            + " where "
            + " spm.switching_point_detail_id = spd.switching_point_detail_id and "
            + " spm.meter_id = m.meter_id "
            + " and "
            + " sp.switching_point_id = m.switching_point_id"
            + " and "
            + " m.feeder_id = f.feeder_id "
            + " and "
            + " f.zone_id = z.zone_id"
            + " and"
            + " spd.road_id = r.road_id "
            + " and "
            + " spd.area_id = a.area_id "
            + " and m.final_revision='VALID' "
            + " AND IF('" + searchIVRSNo + "' = '', spd.ivrs_no LIKE '%%', spd.ivrs_no =?) "
            + " AND IF('" + searchService_Conn_No + "' = '', spd.service_conn_no LIKE '%%', spd.service_conn_no =?) "
            + " AND IF('" + searchZone + "' = '', spd.city LIKE '%%', spd.city =?) "
            + " GROUP BY m.ivrs_no "
            + " ) AS total_count"; */
//            String query = "SELECT count(*) FROM ("
//                    + " select count(*)"
//                    + " from meters m, switching_point_detail spd, switching_point sp, feeder f, zone z, road r, area a "
//                    + " where "
//                    + " m.ivrs_no = spd.ivrs_no"
//                    + " and "
//                    + " sp.switching_point_id = m.switching_point_id"
//                    + " and "
//                    + " m.feeder_id = f.feeder_id "
//                    + " and "
//                    + " f.zone_id = z.zone_id"
//                    + " and"
//                    + " spd.road_id = r.road_id "
//                    + " and "
//                    + " spd.area_id = a.area_id "
//                    + " and m.final_revision='VALID' group by m.ivrs_no "
//                    + " ) AS total_count";
            /*    String query = " SELECT  count(*)"
            + " FROM "
            + " switching_point_detail spd, "
            + " pole p, pole_type pt, "
            + " area a, road r, traffic_type t, mounting_type mt, "
            + " control_mechanism c, "
            + " fuse ff, contacter ct, timer tm, mccb mcb, switching_point_map spm, "
            + " switching_point s, meters m, feeder f, zone z, division d, switching_point_type st "
            + " WHERE "
            + " s.switching_point_id = spm.switching_point_id "
            + "  AND s.switching_point_type_id = st.switching_point_type_id and "
            + " spd.pole_id = p.pole_id "
            + " and p.pole_type_id = pt.pole_type_id "
            + " AND p.mounting_type_id = mt.mounting_type_id "
            + " AND spd.area_id = a.area_id "
            + " And spd.road_id = r.road_id "
            + " And spd.fuse_id = ff.fuse_id "
            + " And spd.contacter_id = ct.contacter_id "
            + " And spd.timer_id = tm.timer_id "
            + " And spd.mccb_id = mcb.mccb_id "
            + " AND spd.traffic_type_id = t.traffic_type_id "
            + " AND spm.switching_point_detail_id  =  spd.switching_point_detail_id "
            + " AND spd.control_mechanism_id = c.control_mechanism_id "
            + " and s.feeder_id= f.feeder_id "
            + "  and s.switching_point_id = m.switching_point_id "
            + "  and f.zone_id = z.zone_id AND z.division_id = d.division_id "
            + " group by switching_point_id  ";   */


            /*       String query1 = "select count(*) from switching_point s, feeder f, zone z, division d, switching_point_type st , switching_point_map spm"
            + " where  s.feeder_id= f.feeder_id and f.zone_id = z.zone_id"
            + " AND s.switching_point_id = spm.switching_point_id"
            + " AND z.division_id = d.division_id AND s.switching_point_type_id = st.switching_point_type_id  GROUP BY  switching_point ";
            String query2 = " SELECT  count(*)"
            + " FROM switching_point_detail s,pole p, pole_type pt, area a, road r, traffic_type t, mounting_type m,"
            + " control_mechanism c,"
            + " fuse f, contacter ct, timer tm, mccb mcb, switching_point_map spm"
            + " WHERE"
            + " s.pole_id = p.pole_id"
            + " and p.pole_type_id = pt.pole_type_id"
            + " AND p.mounting_type_id = m.mounting_type_id"
            + " AND s.area_id = a.area_id"
            + " And s.road_id = r.road_id"
            + " And s.fuse_id = f.fuse_id"
            + " And s.contacter_id = ct.contacter_id"
            + " And s.timer_id = tm.timer_id"
            + " And s.mccb_id = mcb.mccb_id"
            + " AND s.traffic_type_id = t.traffic_type_id"
            + " AND spm.switching_point_detail_id  =  s.switching_point_detail_id"
            + " AND s.control_mechanism_id = c.control_mechanism_id";

            String  query = "select @r:=(" + query1 + ") as d , @s:=(" + query2 + ")as t , @r+@S as total_count from (select @r:=0)as r , (select @s:=0)as s";
             */
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchIVRSNo);
            //    pstmt.setString(2, searchService_Conn_No);
            pstmt.setString(2, searchZone);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString("count(*)"));
        } catch (Exception e) {
            System.out.println("Error:SwithingPointModel-getNoOfRows-- " + e);
        }
        return noOfRows;
    }

    public List<SwitchingPointMapBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchIVRSNo, String searchService_Conn_No, String searchZone) {
        List<SwitchingPointMapBean> list = new ArrayList<SwitchingPointMapBean>();

        PreparedStatement pstmt;
        /*     String query1 = "select s.switching_point_id,  d.division_name, s.switching_point,  f.feeder_name, s.auto_switching_point, z.zone ,"
        + " m.meter_name, m.sanctioned_load_kw, s.address1, s.address2, s.address3, s.description, s.active, s.switching_pt_code, st.switching_point_type, "
        + " '' as  switching_point_detail_id, '' as switching_point_name, '' as feeder , '' as pole_no_s, '' as gps_code_s, '' as is_working, "
        + " ''AS created_date,"
        + " '' as switching_rev_no, '' as meter_no_s, '' as ph, '' as created_by, '' as pole_no, '' as pole_span, '' as pole_height,'' as gps_code,"
        + "  '' as road_name, '' as area_name, '' as traffic_type, '' as control_mechanism_type,"
        + " '' as fuse, '' as contacter, '' as mccb, '' as fuse_type, '' as contacter_type, '' as mccb_type,"
        + " '' as timer, '' as timer_type, '' as fuse_quantity, '' as contacter_quantity, '' as mccb_quantity, '' as timer_quantity,"
        + " '' as avg_lux_level, '' as standard_lux_level, '' as max_avg_lux_level, '' as min_avg_lux_level,"
        + " '' as mounting_type, '' as mounting_height, '' as pole_type,"
        + " '' AS source_wattage,"
        + " '' AS Isworking"
        + " from switching_point s, meters m, feeder f, zone z, division d, switching_point_type st, switching_point_map spm where  s.feeder_id= f.feeder_id "
        + " and s.switching_point_id = m.switching_point_id "
        + " and f.zone_id = z.zone_id AND z.division_id = d.division_id AND s.switching_point_type_id = st.switching_point_type_id "
        + " AND s.switching_point_id = spm.switching_point_id GROUP BY  switching_point";

        String query2 = " SELECT "
        + " '' as switching_point_id,  '' as division_name, '' as switching_point,  '' as  feeder_name, '' as auto_switching_point, '' as zone ,"
        + " '' as meter_name, '' as sanctioned_load_kw, '' as address1, '' as address2, '' as address3, '' as description, '' as active, '' as switching_pt_code, '' as switching_point_type, "
        + " s.switching_point_detail_id, s.switching_point_name, s.feeder ,s.pole_no_s, s.gps_code_s, s.is_working,"
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
        + " AND lt.source_id=l.source_type_id)AS Isworking "
        + " FROM switching_point_detail s,pole p, pole_type pt, area a, road r, traffic_type t, mounting_type m,"
        + " control_mechanism c,"
        + " fuse f, contacter ct, timer tm, mccb mcb, switching_point_map spm"
        + " WHERE"
        + " s.pole_id = p.pole_id"
        + " and p.pole_type_id = pt.pole_type_id"
        + " AND p.mounting_type_id = m.mounting_type_id"
        + " AND s.area_id = a.area_id"
        + " And s.road_id = r.road_id"
        + " And s.fuse_id = f.fuse_id"
        + " And s.contacter_id = ct.contacter_id"
        + " And s.timer_id = tm.timer_id"
        + " And s.mccb_id = mcb.mccb_id"
        + " AND s.traffic_type_id = t.traffic_type_id"
        + " AND spm.switching_point_detail_id  =  s.switching_point_detail_id"
        + " AND s.control_mechanism_id = c.control_mechanism_id ";
        String query = query1 + " union " + query2 + "  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;  */

        /*
        String query = " SELECT "
        + " s.switching_point_id,  d.division_name, s.switching_point,  f.feeder_name, s.auto_switching_point, z.zone , "
        + " m.meter_name, m.sanctioned_load_kw, s.address1, s.address2, "
        + " s.address3, s.description, s.active, s.switching_pt_code, st.switching_point_type, "
        + " spd.switching_point_detail_id, spd.switching_point_name, spd.feeder , spd.pole_no_s, spd.gps_code_s, spd.is_working, "
        + " DATE_FORMAT(spd.created_date,'%d-%m-%Y') AS created_date, "
        + " spd.switching_rev_no, spd.meter_no_s, spd.ph, spd.created_by, p.pole_no,p.pole_span,p.pole_height,p.gps_code, "
        + " r.road_name,a.area_name,t.traffic_type,c.control_mechanism_type, "
        + " spd.fuse, spd.contacter, spd.mccb, ff.fuse_type, ct.contacter_type, mcb.mccb_type, "
        + " spd.timer, tm.timer_type, spd.fuse_quantity, spd.contacter_quantity, spd.mccb_quantity, spd.timer_quantity, "
        + " p.avg_lux_level,p.standard_lux_level,p.max_avg_lux_level,p.min_avg_lux_level, "
        + " mt.mounting_type, p.mounting_height, pt.pole_type, "
        + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.total_fittings)) "
        + " AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
        + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w "
        + " WHERE spltm.light_type_id=lt.light_type_id AND spd.switching_point_detail_id = spltm.switching_point_detail_id "
        + " AND lt.wattage_id=w.wattage_id "
        + " AND lt.source_id=l.source_type_id)AS source_wattage, "
        + " (SELECT GROUP_CONCAT(CAST((concat(l.source_type,'-',w.wattage_value,',',spltm.no_of_working,'/',spltm.no_of_not_working)) "
        + " AS CHAR CHARACTER SET utf8) SEPARATOR '__') "
        + " FROM switching_point_light_type_mapping AS spltm,light_type lt,light_source_type AS l,wattage AS w "
        + " WHERE spltm.light_type_id=lt.light_type_id AND spd.switching_point_detail_id = spltm.switching_point_detail_id "
        + " AND lt.wattage_id=w.wattage_id "
        + " AND lt.source_id=l.source_type_id)AS Isworking "
        + " FROM "
        + " switching_point_detail spd, "
        + " pole p, pole_type pt, "
        + " area a, road r, traffic_type t, mounting_type mt, "
        + " control_mechanism c, "
        + " fuse ff, contacter ct, timer tm, mccb mcb, switching_point_map spm, "
        + " switching_point s, meters m, feeder f, zone z, division d, switching_point_type st "
        + " WHERE "
        + " s.switching_point_id = spm.switching_point_id "
        + "  AND s.switching_point_type_id = st.switching_point_type_id and "
        + " spd.pole_id = p.pole_id "
        + " and p.pole_type_id = pt.pole_type_id "
        + " AND p.mounting_type_id = mt.mounting_type_id "
        + " AND spd.area_id = a.area_id "
        + " And spd.road_id = r.road_id "
        + " And spd.fuse_id = ff.fuse_id "
        + " And spd.contacter_id = ct.contacter_id "
        + " And spd.timer_id = tm.timer_id "
        + " And spd.mccb_id = mcb.mccb_id "
        + " AND spd.traffic_type_id = t.traffic_type_id "
        + " AND spm.switching_point_detail_id  =  spd.switching_point_detail_id "
        + " AND spd.control_mechanism_id = c.control_mechanism_id "
        + " and s.feeder_id= f.feeder_id "
        + "  and s.switching_point_id = m.switching_point_id "
        + "  and f.zone_id = z.zone_id AND z.division_id = d.division_id "
        + " group by switching_point_id  "
        + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
         */



        /*      try {
        pstmt = connection.prepareStatement(query);
        ResultSet rset = pstmt.executeQuery();
        while (rset.next()) {
        SwitchingPointMapBean bean = new SwitchingPointMapBean();
        bean.setMeter_name(rset.getString("meter_name"));
        bean.setSanctioned_load_kw(rset.getString("sanctioned_load_kw"));
        bean.setZone(rset.getString("zone"));
        bean.setDivision_name(rset.getString("division_name"));
        bean.setSwitching_point_id(rset.getInt("switching_point_id"));
        bean.setSwitching_pt_meter(rset.getString("switching_point"));
        bean.setAuto_switching_point(rset.getString("auto_switching_point"));
        bean.setFeeder_name(rset.getString("feeder_name"));
        bean.setAddress1(rset.getString("address1"));
        bean.setAddress2(rset.getString("address2"));
        bean.setAddress3(rset.getString("address3"));
        bean.setDescription(rset.getString("description"));
        bean.setActive(rset.getString("active"));
        bean.setSwitching_point_code(rset.getString("switching_pt_code"));
        bean.setSwitching_point_type(rset.getString("switching_point_type"));


        bean.setSwitching_point_detail_id(rset.getInt("switching_point_detail_id"));
        bean.setSurvey_rev_no(rset.getInt("switching_rev_no"));
        bean.setPole_no_s(rset.getString("pole_no_s"));
        bean.setSwitching_pt_detail(rset.getString("switching_point_name"));
        bean.setFeeder(rset.getString("feeder"));
        bean.setGps_code_s(rset.getString("gps_code_s"));
        bean.setMeter_no_s(rset.getString("meter_no_s"));
        bean.setPhase(rset.getString("ph"));
        bean.setControl_mechanism_type(rset.getString("control_mechanism_type"));
        bean.setArea_name(rset.getString("area_name"));
        bean.setRoad_name(rset.getString("road_name"));
        bean.setTraffic_type(rset.getString("traffic_type"));
        bean.setPole_type(rset.getString("pole_type"));
        bean.setPole_span(rset.getString("pole_span"));
        bean.setPole_height(rset.getString("pole_height"));
        bean.setMounting_height(rset.getString("mounting_height"));
        bean.setCreated_date(rset.getString("created_date"));
        bean.setCreated_by(rset.getString("created_by"));
        //  bean.setRemark(rset.getString("remark"));
        bean.setMounting_type(rset.getString("mounting_type"));
        bean.setActive(rset.getString("active"));
        bean.setPole_no(rset.getString("pole_no"));
        bean.setGps_code(rset.getString("gps_code"));
        bean.setMax_avg_lux_level(rset.getString("max_avg_lux_level"));
        bean.setMin_avg_lux_level(rset.getString("min_avg_lux_level"));
        bean.setAvg_lux_level(rset.getString("avg_lux_level"));
        bean.setStandard_avg_lux_level(rset.getString("standard_lux_level"));
        bean.setIs_working(rset.getString("is_working"));
        bean.setSource_wattage(rset.getString("source_wattage"));
        bean.setIsworking(rset.getString("Isworking"));   // number & type of bulbs working / not working
        bean.setFuse(rset.getString("fuse"));
        bean.setFuse_type(rset.getString("fuse_type"));
        bean.setFuse_quantity(rset.getString("fuse_quantity"));
        bean.setContacter(rset.getString("contacter"));
        bean.setContacter_type(rset.getString("contacter_type"));
        bean.setContacter_quantity(rset.getString("contacter_quantity"));
        bean.setTimer(rset.getString("timer"));
        bean.setTimer_type(rset.getString("timer_type"));
        bean.setTimer_quantity(rset.getString("timer_quantity"));
        bean.setMccb(rset.getString("mccb"));
        bean.setMccb_type(rset.getString("mccb_type"));
        bean.setMccb_quantity(rset.getString("mccb_quantity"));
        //      bean.setNo_of_poles(rset.getInt("no_of_poles"));
        //      bean.setSwitching_load(rset.getInt("wattage"));
        list.add(bean);

        }
        } catch (Exception e) {
        System.out.println("Error in Show data of switching point detail is : " + e);
        }
        System.out.println(list); */

//        String query = " select"
//                + " spd.service_conn_no as SCNo_Switching, m.meter_service_number as SCNo_Meter,"
//                + " spd.ivrs_no as IVRS_Switching, m.ivrs_no as IVRS_Meter,"
//                + "  spd.switching_point_name as Switching_SPN, sp.switching_point as Meter_SPN,"
//                + " spd.city as Switching_Zone, z.zone as Meter_Zone,"
//                + " spd.feeder as Switching_Feeder, f.feeder_name as Meter_Feeder,"
//                + " concat_ws(' , ',r.road_name,a.area_name) as Address_Switching,"
//                + " concat_ws(' , ',sp.address1,sp.address2,sp.address3) as Address_Meter"
//                + " from meters m, switching_point_detail spd, switching_point sp, feeder f, zone z, road r, area a "
//                + " where "
//                + " m.ivrs_no = spd.ivrs_no"
//                + " and "
//                + " sp.switching_point_id = m.switching_point_id"
//                + " and "
//                + " m.feeder_id = f.feeder_id "
//                + " and "
//                + " f.zone_id = z.zone_id"
//                + " and"
//                + " spd.road_id = r.road_id "
//                + " and "
//                + " spd.area_id = a.area_id "
//                + " and m.final_revision='VALID' group by m.ivrs_no "
//                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
    /*    String query = " select spm.switching_point_map_id,"
        + " spd.service_conn_no as SCNo_Switching, m.meter_service_number as SCNo_Meter,"
        + " spd.ivrs_no as IVRS_Switching, m.ivrs_no as IVRS_Meter,"
        + " spd.switching_point_name as Switching_SPN, sp.switching_point as Meter_SPN,"
        + " spd.city as Switching_Zone, z.zone as Meter_Zone,"
        + " spd.feeder as Switching_Feeder, f.feeder_name as Meter_Feeder,"
        + " spd.measured_load , m.sanctioned_load_kw as sanctioned_load, "
        + " concat_ws(' , ',r.road_name,a.area_name) as Address_Switching,"
        + " concat_ws(' , ',sp.address1,sp.address2,sp.address3) as Address_Meter"
        + " from switching_point_map spm, meters m, switching_point_detail spd, switching_point sp, feeder f, zone z, road r, area a "
        + " where "
        + " spm.switching_point_detail_id = spd.switching_point_detail_id and "
        + " spm.meter_id = m.meter_id "
        + " and "
        + " sp.switching_point_id = m.switching_point_id"
        + " and "
        + " m.feeder_id = f.feeder_id "
        + " and "
        + " f.zone_id = z.zone_id"
        + " and"
        + " spd.road_id = r.road_id "
        + " and "
        + " spd.area_id = a.area_id "
        + " and m.final_revision='VALID' "
        + " AND IF('" + searchIVRSNo + "' = '', spd.ivrs_no LIKE '%%', spd.ivrs_no =?) "
        + " AND IF('" + searchService_Conn_No + "' = '', spd.service_conn_no LIKE '%%', spd.service_conn_no =?) "
        + " AND IF('" + searchZone + "' = '', spd.city LIKE '%%', spd.city =?) GROUP BY m.ivrs_no "
        + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;  */

        /*  String query = "select spm.switching_point_map_id,spd.service_conn_no as SCNo_Switching, m.meter_service_number as SCNo_Meter, "
        + " spd.ivrs_no as IVRS_Switching, m.ivrs_no as IVRS_Meter,spd.switching_point_name as Switching_SPN, sp.switching_point as Meter_SPN, "
        + " spd.city as Switching_Zone, z.zone as Meter_Zone,spd.feeder as Switching_Feeder, f.feeder_name as Meter_Feeder, "
        + " spd.measured_load , m.sanctioned_load_kw as sanctioned_load,concat_ws(' , ',r.road_name,a.area_name) as Address_Switching, "
        + " concat_ws(' , ',sp.address1,sp.address2,sp.address3) as Address_Meter "
        + " from switching_point_map spm left join meters m on spm.meter_id = m.meter_id and m.final_revision='VALID' "
        + " left join feeder f on m.feeder_id = f.feeder_id "
        + " left join switching_point sp on sp.switching_point_id = m.switching_point_id "
        + " left join zone z on f.zone_id = z.zone_id "
        + " ,switching_point_detail spd,  road r, area a "
        + " where spm.switching_point_detail_id = spd.switching_point_detail_id and spd.road_id = r.road_id and spd.area_id = a.area_id "
        + " AND IF('" + searchIVRSNo + "' = '', spd.ivrs_no LIKE '%%', spd.ivrs_no =?) "
        + " AND IF('" + searchService_Conn_No + "' = '', spd.service_conn_no LIKE '%%', spd.service_conn_no =?) "
        + " AND IF('" + searchZone + "' = '', spd.city LIKE '%%', spd.city =?) GROUP BY m.ivrs_no ORDER BY spd.city"
        + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;  */
        String query = "select spm.switching_point_map_id, "
                + " spd.ivrs_no as IVRS_Switching, m.ivrs_no as IVRS_Meter, "
             //   + " spd.city as Switching_Zone , "
                + " spd.zone as Switching_Zone , "
                + "  z.zone as Meter_Zone,"
                + " spd.measured_load , m.sanctioned_load_kw as sanctioned_load "
                + " from switching_point_map spm left join meters m on spm.meter_id = m.meter_id and m.final_revision='VALID' and  m.active='Y' "
                + " left join feeder f on m.feeder_id = f.feeder_id "
                + " left join switching_point sp on sp.switching_point_id = m.switching_point_id "
                + " left join zone z on f.zone_id = z.zone_id "
                + " ,switching_point_detail spd,  road r, area a "
                + " where spm.switching_point_detail_id = spd.switching_point_detail_id and spd.road_id = r.road_id and spd.area_id = a.area_id  and  spd.active='Y'"
                + " AND IF('" + searchIVRSNo + "' = '', spd.ivrs_no LIKE '%%', spd.ivrs_no =?) "
                //    + " AND IF('" + searchService_Conn_No + "' = '', spd.service_conn_no LIKE '%%', spd.service_conn_no =?) "
                + " AND IF('" + searchZone + "' = '', spd.zone LIKE '%%', spd.zone =?) GROUP BY m.ivrs_no ORDER BY spd.zone"
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchIVRSNo);
            //    pstmt.setString(2, searchService_Conn_No);
            pstmt.setString(2, searchZone);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SwitchingPointMapBean bean = new SwitchingPointMapBean();
                bean.setSwitching_point_map_id(rset.getInt("switching_point_map_id"));
                //       bean.setService_conn_no(rset.getString("SCNo_Switching"));
                //       bean.setSevice_conn_no_meter(rset.getString("SCNo_Meter"));
                bean.setIvrs_no(rset.getString("IVRS_Switching"));
                bean.setIvrs_no_meter(rset.getString("IVRS_Meter"));
                //       bean.setSwitching_point_name(rset.getString("Switching_SPN"));
                //       bean.setSwitching_point_name_meter(rset.getString("Meter_SPN"));
                bean.setZone(rset.getString("Switching_Zone"));
                bean.setZone_meter(rset.getString("Meter_Zone"));
                //       bean.setFeeder(rset.getString("Switching_Feeder"));
                //       bean.setFeeder_meter(rset.getString("Meter_Feeder"));
                //       bean.setAddress(rset.getString("Address_Switching"));
                //       bean.setAddress_meter(rset.getString("Address_Meter"));
                bean.setMeasured_load(rset.getDouble("measured_load"));
                bean.setSanctioned_load(rset.getDouble("sanctioned_load"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data is" + e);
        }

        return list;
    }

    public int deleteRecord(int id) {
        String query = "DELETE FROM switching_point_map WHERE switching_point_map_id = ?";
        int rowsAffected = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            rowsAffected = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while deleting record: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(int switching_point_map_id, String ivrs_no_survey, String ivrs_no_meter) {
        String query = "UPDATE switching_point_map SET "
                + " switching_point_detail_id =(SELECT switching_point_detail_id FROM switching_point_detail WHERE  active='Y' and ivrs_no= ?), "
                + " meter_id=(SELECT meter_id FROM meters WHERE ivrs_no= ? AND final_revision='VALID' and active='Y' group by ivrs_no )"
                + " WHERE switching_point_map_id = ?";
        int rowsAffected = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, ivrs_no_survey);
            stmt.setString(2, ivrs_no_meter);
            stmt.setInt(3, switching_point_map_id);
            rowsAffected = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while updating record in switching point map: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be updated.....";
            msgBgColor = COLOR_ERROR;
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
}
