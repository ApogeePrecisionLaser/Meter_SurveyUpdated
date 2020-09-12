/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.model;

import com.survey.energyMeter.tableClasses.JunctionBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author jpss
 */
public class JunctionModel {

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
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            System.out.println("connected - " + connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<String> getJunctionName1(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT s.switching_point_name "
                       +" FROM switching_point_detail s "
                       +" where s.active='Y' "
                       +" GROUP BY s.switching_point_name "
                       +" Order BY s.switching_point_name ";
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
                list.add("No such Junction Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("getJunctionName ERROR inside JunctionModel - " + e);
        }
        return list;
    }

    public List<JunctionBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchJunctionName, String searchIvrsNo) {
        List<JunctionBean> list = new ArrayList<JunctionBean>();
        String query1 = "SELECT spd.switching_point_detail_id, spd.switching_rev_no, spd.switching_point_name, spd.ivrs_no, spd.ph, spd.isOnPole, j.* FROM switching_point_detail spd, junction j "
                + " WHERE spd.active='Y' AND spd.switching_point_detail_id=j.switching_point_detail_id AND j.active='Y' "
                + " AND IF('" + searchJunctionName + "' = '', spd.switching_point_name LIKE '%%', spd.switching_point_name=?) "
                + " AND IF('" + searchIvrsNo + "' = '', spd.ivrs_no LIKE '%%', spd.ivrs_no=?)"
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

        String query2 = "SELECT rd.road_name, a.area_name, w.ward_no, c.city_name FROM road rd, switching_point_detail spd, area a, ward w, city c "
                + " WHERE rd.road_id=spd.road_id AND rd.active='Active' AND c.city_id=w.city_id AND w.ward_id=a.ward_id AND w.active='Y' "
                + " AND a.area_id=spd.area_id AND spd.switching_point_detail_id=? AND spd.active = 'Y' ;";

        String query3 = "SELECT rd.road_name, a.area_name, w.ward_no, c.city_name FROM road rd, area a, ward w, city c, pole p "
                + " WHERE rd.road_id=p.road_id AND rd.active='Active' AND c.city_id=w.city_id AND w.ward_id=a.ward_id AND w.active='Y' "
                + " AND a.area_id=p.area_id AND p.switching_point_detail_id=? AND p.active='Y' AND isSwitchingPoint='Yes' ;";

        try {
            //System.out.println(query);
            PreparedStatement pstSpd = connection.prepareStatement(query1);
            pstSpd.setString(1, searchJunctionName);
            pstSpd.setString(2, searchIvrsNo);
            ResultSet rsSj = pstSpd.executeQuery();
            PreparedStatement pstOnPole;
            while (rsSj.next()) {
                if (rsSj.getString("isOnPole").equals("N")) {
                    pstOnPole = connection.prepareStatement(query2);
                } else {
                    pstOnPole = connection.prepareStatement(query3);
                }
                pstOnPole.setInt(1, rsSj.getInt("switching_point_detail_id"));
                //pstOnPole.setInt(2, rsSj.getInt("switching_rev_no"));
                ResultSet rset = pstOnPole.executeQuery();
                if (rset.next()) {
                    JunctionBean junctionBean = new JunctionBean();
                    junctionBean.setJunction_id(rsSj.getInt("junction_id"));
                    junctionBean.setProgram_version_no(rsSj.getInt("program_version_no"));
                    junctionBean.setSwitching_point_name(rsSj.getString("switching_point_name"));
                    junctionBean.setRoad(rset.getString("road_name"));
                    junctionBean.setArea(rset.getString("area_name"));
                    junctionBean.setWard(rset.getString("ward_no"));
                    junctionBean.setCity(rset.getString("city_name"));
                    junctionBean.setIvrs_no(rsSj.getString("ivrs_no"));
                    junctionBean.setPhase(rsSj.getInt("ph"));
                    junctionBean.setController_model(rsSj.getString("controller_model"));
                    junctionBean.setMobile_no(rsSj.getString("mobile_no"));
                    junctionBean.setSim_no(rsSj.getString("sim_no"));
                    junctionBean.setImei_no(rsSj.getString("imei_no"));
                    junctionBean.setPanel_file_no(rsSj.getString("panel_file_no"));
                    String status = rsSj.getString("panel_transferred_status");
                    if (status.equals("Y")) {
                        junctionBean.setPanel_transferred_status("Yes");
                    } else {
                        junctionBean.setPanel_transferred_status("No");
                    }
                    junctionBean.setEnergy_meter_no(rsSj.getString("energy_meter_no"));
                    junctionBean.setSanctioned_load(rsSj.getDouble("sanctioned_load"));
                    junctionBean.setConnected_load(rsSj.getDouble("connected_load"));
                    junctionBean.setPhase1_healthy_voltage(rsSj.getDouble("phase1_healthy_voltage"));
                    junctionBean.setPhase2_healthy_voltage(rsSj.getDouble("phase2_healthy_voltage"));
                    junctionBean.setPhase3_healthy_voltage(rsSj.getDouble("phase3_healthy_voltage"));
                    junctionBean.setPhase1_healthy_current(rsSj.getDouble("phase1_healthy_current"));
                    junctionBean.setPhase2_healthy_current(rsSj.getDouble("phase2_healthy_current"));
                    junctionBean.setPhase3_healthy_current(rsSj.getDouble("phase3_healthy_current"));
                    junctionBean.setRemark(rsSj.getString("remark"));
                    String active = rsSj.getString("active");
                    if (active.equals("Y")) {
                        junctionBean.setActive("Yes");
                    } else {
                        junctionBean.setActive("No");
                    }
                    list.add(junctionBean);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public List<JunctionBean> showAllData() {
        List<JunctionBean> list = new ArrayList<JunctionBean>();
        String query1 = "SELECT spd.switching_point_detail_id, spd.switching_rev_no, spd.switching_point_name, spd.ivrs_no, spd.ph, spd.isOnPole, "
                + " j.* FROM switching_point_detail spd, junction j "
                + " WHERE spd.active='Y' AND spd.switching_point_detail_id=j.switching_point_detail_id  AND j.active='Y' ";
        //+ " AND IF('"+searchJunctionName+"' = '', spd.switching_point_name LIKE '%%', spd.switching_point_name=?) "
        //+ " AND IF('"+searchIvrsNo+"' = '', spd.ivrs_no LIKE '%%', spd.ivrs_no=?)"
        //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        String query2 = "SELECT rd.road_name, a.area_name, w.ward_no, c.city_name FROM road rd, switching_point_detail spd, area a, ward w, city c "
                + " WHERE rd.road_id=spd.road_id AND rd.active='Active' AND c.city_id=w.city_id AND w.ward_id=a.ward_id AND w.active='Y' "
                + " AND a.area_id=spd.area_id AND spd.switching_point_detail_id=? AND spd.active='Y';";

        String query3 = "SELECT rd.road_name, a.area_name, w.ward_no, c.city_name FROM road rd, switching_point_detail spd, area a, ward w, city c, pole p "
                + " WHERE rd.road_id=p.road_id AND rd.active='Active' AND c.city_id=w.city_id AND w.ward_id=a.ward_id AND w.active='Y' "
                + " AND a.area_id=p.area_id AND p.switching_point_detail_id=? AND p.active='Y' AND isSwitchingPoint='Yes' ;";

        try {
            //System.out.println(query);
            PreparedStatement pstSpd = connection.prepareStatement(query1);
            ResultSet rsSj = pstSpd.executeQuery();
            PreparedStatement pstOnPole;
            while (rsSj.next()) {
                if (rsSj.getString("isOnPole").equals("N")) {
                    pstOnPole = connection.prepareStatement(query2);
                } else {
                    pstOnPole = connection.prepareStatement(query3);
                }
                pstOnPole.setInt(1, rsSj.getInt("switching_point_detail_id"));
                //pstOnPole.setInt(2, rsSj.getInt("switching_rev_no"));
                ResultSet rset = pstOnPole.executeQuery();
                if (rset.next()) {
                    JunctionBean junctionBean = new JunctionBean();
                    junctionBean.setJunction_id(rsSj.getInt("junction_id"));
                    junctionBean.setProgram_version_no(rsSj.getInt("program_version_no"));
                    junctionBean.setSwitching_point_name(rsSj.getString("switching_point_name"));
                    junctionBean.setRoad(rset.getString("road_name"));
                    junctionBean.setArea(rset.getString("area_name"));
                    junctionBean.setWard(rset.getString("ward_no"));
                    junctionBean.setCity(rset.getString("city_name"));
                    junctionBean.setIvrs_no(rsSj.getString("ivrs_no"));
                    junctionBean.setPhase(rsSj.getInt("ph"));
                    junctionBean.setController_model(rsSj.getString("controller_model"));
                    junctionBean.setMobile_no(rsSj.getString("mobile_no"));
                    junctionBean.setSim_no(rsSj.getString("sim_no"));
                    junctionBean.setImei_no(rsSj.getString("imei_no"));
                    junctionBean.setPanel_file_no(rsSj.getString("panel_file_no"));
                    String status = rsSj.getString("panel_transferred_status");
                    if (status.equals("Y")) {
                        junctionBean.setPanel_transferred_status("Yes");
                    } else {
                        junctionBean.setPanel_transferred_status("No");
                    }
                    junctionBean.setEnergy_meter_no(rsSj.getString("energy_meter_no"));
                    junctionBean.setSanctioned_load(rsSj.getDouble("sanctioned_load"));
                    junctionBean.setConnected_load(rsSj.getDouble("connected_load"));
                    junctionBean.setPhase1_healthy_voltage(rsSj.getDouble("phase1_healthy_voltage"));
                    junctionBean.setPhase2_healthy_voltage(rsSj.getDouble("phase2_healthy_voltage"));
                    junctionBean.setPhase3_healthy_voltage(rsSj.getDouble("phase3_healthy_voltage"));
                    junctionBean.setPhase1_healthy_current(rsSj.getDouble("phase1_healthy_current"));
                    junctionBean.setPhase2_healthy_current(rsSj.getDouble("phase2_healthy_current"));
                    junctionBean.setPhase3_healthy_current(rsSj.getDouble("phase3_healthy_current"));
                    junctionBean.setRemark(rsSj.getString("remark"));
                    String active = rsSj.getString("active");
                    if (active.equals("Y")) {
                        junctionBean.setActive("Yes");
                    } else {
                        junctionBean.setActive("No");
                    }
                    list.add(junctionBean);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData()....: " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchJunctionName, String searchIvrsNo) {
        String query = "SELECT count(*) FROM switching_point_detail spd, junction j WHERE spd.active='Y' "
                + " AND spd.switching_point_detail_id=j.switching_point_detail_id AND spd.active='Y' AND j.active='Y' "
                + " AND IF('" + searchJunctionName + "' = '', spd.switching_point_name LIKE '%%', spd.switching_point_name=?) "
                + " AND IF('" + searchIvrsNo + "' = '', spd.ivrs_no LIKE '%%', spd.ivrs_no=?)";
        //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        int noOfRows = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchJunctionName);
            pstmt.setString(2, searchIvrsNo);
            //pstmt.setString(3, searchIs_bill_old);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows JunctionModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public byte[] generateRecordList(String jrxmlFilePath, List list, String searchJunctionName, String searchIvrsNo) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            mymap.put("switching_point_name", searchJunctionName.equals("") ? "All" : searchJunctionName);
            mymap.put("ivrs_no", searchIvrsNo.equals("") ? "All" : searchIvrsNo);
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, jrBean);
        } catch (Exception e) {
            System.out.println("JunctionModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public int cancelRecord(int junction_id, int program_version_no) {
        String query = " UPDATE junction set active='N' WHERE junction_id = " + junction_id + " && program_version_no=" + program_version_no;
        int rowsAffected = 0;
        try {
            //  rowsAffected = connection.prepareStatement(query1).executeUpdate();
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("JunctionModel cancelRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Cancel successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot Cancel the record, it is used in another GUI.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int reviseRecord(JunctionBean junctionBean) {
        int rowsAffected = 0;
        try {
            rowsAffected = insertRecord(junctionBean);
            if (rowsAffected > 0) {
                rowsAffected = cancelRecord(junctionBean.getJunction_id(), junctionBean.getProgram_version_no());
            }
        } catch (Exception e) {
            System.out.println("JunctionModel reviseRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Revise successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot Revise the record, some error......";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int insertRecord(JunctionBean junctionBean) {
        int rowsAffected = 0, verify = 0;
        String query = "INSERT INTO junction (junction_id, program_version_no, switching_point_detail_id, switching_rev_no, controller_model, mobile_no, sim_no, imei_no, panel_file_no, energy_meter_no, "
                + "sanctioned_load, connected_load, phase1_healthy_voltage, phase2_healthy_voltage, phase3_healthy_voltage, phase1_healthy_current, phase2_healthy_current, phase3_healthy_current, remark) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        String querySwitching_point_detail = " SELECT switching_point_detail_id, switching_rev_no FROM switching_point_detail WHERE active='Y' AND ivrs_no=?";

        String updateQuery="update switching_point_detail spd set switching_point_name=? "
                           +" where switching_point_detail_id=? "
                           +" and spd.active='Y' ";


        try {
            PreparedStatement pstSpd = connection.prepareStatement(querySwitching_point_detail);
            pstSpd.setString(1, junctionBean.getIvrs_no());
            ResultSet rsSpd = pstSpd.executeQuery();
            if (rsSpd.next()) {
                PreparedStatement pstmt = connection.prepareStatement(query);
                int i = junctionBean.getJunction_id();
                int junction_id;
                int program_version_no;
                if (i == 0) {
                    String queryPCM = "SELECT max(junction_id) junction_id FROM junction; ";
                    ResultSet rs = connection.prepareStatement(queryPCM).executeQuery();
                    if (rs.next()) {
                        junction_id = rs.getInt("junction_id") + 1;
                        program_version_no = 0;
                    } else {
                        junction_id = 1;
                        program_version_no = 1;
                    }
                    verify = verifyMsi(junctionBean.getIvrs_no(), junctionBean.getMobile_no(), junctionBean.getSim_no(), junctionBean.getImei_no());
                } else {
                    junction_id = i;
                    program_version_no = junctionBean.getProgram_version_no() + 1;
                }
                if (verify == 0) {
                    pstmt.setInt(1, junction_id);
                    pstmt.setInt(2, program_version_no);
                    pstmt.setInt(3, rsSpd.getInt("switching_point_detail_id"));
                    pstmt.setInt(4, rsSpd.getInt("switching_rev_no"));
                    pstmt.setString(5, junctionBean.getController_model());
                    pstmt.setString(6, junctionBean.getMobile_no());
                    pstmt.setString(7, junctionBean.getSim_no());
                    pstmt.setString(8, junctionBean.getImei_no());
                    pstmt.setString(9, junctionBean.getPanel_file_no());
                    pstmt.setString(10, junctionBean.getEnergy_meter_no());
                    pstmt.setDouble(11, junctionBean.getSanctioned_load());
                    pstmt.setDouble(12, junctionBean.getConnected_load());
                    pstmt.setDouble(13, junctionBean.getPhase1_healthy_voltage());
                    pstmt.setDouble(14, junctionBean.getPhase2_healthy_voltage());
                    pstmt.setDouble(15, junctionBean.getPhase3_healthy_voltage());
                    pstmt.setDouble(16, junctionBean.getPhase1_healthy_current());
                    pstmt.setDouble(17, junctionBean.getPhase2_healthy_current());
                    pstmt.setDouble(18, junctionBean.getPhase3_healthy_current());
                    pstmt.setString(19, junctionBean.getRemark());

                    rowsAffected = pstmt.executeUpdate();
                }
            }

            PreparedStatement pstmt1 = connection.prepareStatement(updateQuery);
            pstmt1.setString(1,junctionBean.getJunction_name());
            pstmt1.setInt(2,rsSpd.getInt("switching_point_detail_id"));

            rowsAffected = pstmt1.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error: Record inserting: " + e);
            message = "Record is allready exist......";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
            message = "Record is Not saved......";
            msgBgColor = COLOR_ERROR;
            System.out.println("not inserted");
        }
        if (verify > 0) {
            message = "IVRS no, Mobile no, Sim no and imei no must be different......";
            msgBgColor = COLOR_ERROR;
            System.out.println("not inserted");
        }
        return rowsAffected;
    }

    public int verifyMsi(String ivrs_no, String mobile_no, String sim_no, String imei_no) {
        int i = 0;
        String queryIvrs_no = "SELECT * FROM switching_point_detail s, junction j "
                + " WHERE s.switching_point_detail_id=j.switching_point_detail_id AND s.active='Y' And s.ivrs_no=" + ivrs_no + " ";
        String queryMsi1 = "SELECT * FROM junction WHERE "
                + "IF('" + mobile_no + "' = '', mobile_no LIKE '%%', mobile_no=?) AND active='Y' ";
        //+ "AND IF('"+sim_no+"' = '', sim_no LIKE '%%', sim_no=?) "
        //+ "AND IF('"+imei_no+"' = '', imei_no LIKE '%%', imei_no=?)";
        String queryMsi2 = "SELECT * FROM junction WHERE "
                //+ "IF('"+mobile_no+"' = '', mobile_no LIKE '%%', mobile_no=?) "
                + " IF('" + sim_no + "' = '', sim_no LIKE '%%', sim_no=?) AND active='Y'  ";
        //+ "AND IF('"+imei_no+"' = '', imei_no LIKE '%%', imei_no=?)";
        String queryMsi3 = "SELECT * FROM junction WHERE "
                //+ "IF('"+mobile_no+"' = '', mobile_no LIKE '%%', mobile_no=?) "
                //+ "AND IF('"+sim_no+"' = '', sim_no LIKE '%%', sim_no=?) "
                + "IF('" + imei_no + "' = '', imei_no LIKE '%%', imei_no=?)  AND active='Y' ";
        try {
            PreparedStatement pstIvrs_no = connection.prepareStatement(queryIvrs_no);
            ResultSet rsIvrs_no = pstIvrs_no.executeQuery();
            if (rsIvrs_no.next()) {
                i++;
            }
            PreparedStatement pstMo = connection.prepareStatement(queryMsi1);
            PreparedStatement pstSim = connection.prepareStatement(queryMsi2);
            PreparedStatement pstImei = connection.prepareStatement(queryMsi3);
            pstMo.setString(1, mobile_no);
            pstSim.setString(1, sim_no);
            pstImei.setString(1, imei_no);
            ResultSet rsMo = pstMo.executeQuery();
            ResultSet rsSim = pstSim.executeQuery();
            ResultSet rsImei = pstImei.executeQuery();
            if (rsMo.next()) {
                i++;
            }
            if (rsSim.next()) {
                i++;
            }
            if (rsImei.next()) {
                i++;
            }
        } catch (Exception e) {
            System.out.println("Error: in verify " + e);
            return 0;
        }
        return i;
    }

    public Map<Integer, JunctionBean> getJunctionList() {
        Map<Integer, JunctionBean> junctionList = new HashMap<Integer, JunctionBean>();
        try {
            String query = "SELECT junction_id, switching_point_name, controller_model, mobile_no, sim_no, imei_no, "
                    + " program_version_no, panel_file_no, panel_transferred_status "
                    + " FROM junction AS j, switching_point_detail AS spd "
                    + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id "
                    + "  AND spd.active = 'Y' AND j.active='Y' ";

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                JunctionBean junction = new JunctionBean();
                int junction_id = rset.getInt("junction_id");
                int program_version_no = rset.getInt("program_version_no");
                junction.setSwitching_point_detail_id(junction_id);
                junction.setSwitching_rev_no(program_version_no);
                junction.setSwitching_point_name(rset.getString("switching_point_name"));
                junction.setController_model(rset.getString("controller_model"));
                junction.setMobile_no(rset.getString("mobile_no"));
                junction.setSim_no(rset.getString("mobile_no"));
                junction.setImei_no(rset.getString("imei_no"));
                junction.setPanel_file_no(rset.getString("panel_file_no"));
                junction.setPanel_transferred_status(rset.getString("panel_transferred_status"));
                junctionList.put(junction_id, junction);
            }
        } catch (Exception e) {
            System.out.println("Error:junctionModel-getJunctionList--- " + e);
        }
        return junctionList;
    }

    public List<String> getIvrs_no(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ivrs_no FROM switching_point_detail s WHERE active='Y' GROUP BY ivrs_no Order BY ivrs_no; ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ivrs_no = rset.getString("ivrs_no");
                if (ivrs_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ivrs_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such IVRS No exists.......");
            }
        } catch (Exception e) {
            System.out.println("getIvrs_no ERROR inside JunctionModel - " + e);
        }
        return list;
    }

    public List<String> getJunctionName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT s.switching_point_name FROM switching_point_detail s, junction j WHERE s.switching_point_detail_id=j.switching_point_detail_id "
                + " AND s.active='Y' GROUP BY s.switching_point_name Order BY s.switching_point_name; ";
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
                list.add("No such Junction Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("getJunctionName ERROR inside JunctionModel - " + e);
        }
        return list;
    }

    public List<String> getIvrsNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT s.ivrs_no FROM switching_point_detail s, junction j WHERE s.switching_point_detail_id=j.switching_point_detail_id "
                + " AND s.active='Y' GROUP BY ivrs_no Order BY ivrs_no; ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ivrs_no = rset.getString("ivrs_no");
                if (ivrs_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ivrs_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such IVRS No exists.......");
            }
        } catch (Exception e) {
            System.out.println("getIvrsNo ERROR inside JunctionModel - " + e);
        }
        return list;
    }

    public int getPhase(String ivrs_no) {
        int phase = 0;//new ArrayList<String>();
        String query = "  SELECT ph FROM switching_point_detail s WHERE ivrs_no='" + ivrs_no + "'; ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {    // move cursor from BOR to valid record.
                phase = rset.getInt("ph");
            }
        } catch (Exception e) {
            System.out.println("getPhase ERROR inside JunctionModel - " + e);
        }
        return phase;
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
            System.out.println("JunctionModel closeConnection() Error: " + e);
        }
    }
}
