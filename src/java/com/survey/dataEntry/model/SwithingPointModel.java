/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.survey.tableClasses.SwitchingPoint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author JPSS
 */
public class SwithingPointModel {

    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
   
    private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
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
    public int getNoOfRows(String switching_point, String location, String feeder_filter, String zone_filter, String switching_pt_no_filter, String switching_point_type_filter, String division_filter) {
        int noOfRows = 0;
        try {
            if (switching_point_type_filter.equals("Switching Point Type")) {
                switching_point_type_filter = "";
            }
            String query = "select count(*) from switching_point s, feeder f, zone z, division d, switching_point_type st ,meters m"
                    + " where s.switching_point_id = m.switching_point_id AND m.active='Y' and s.feeder_id= f.feeder_id and f.zone_id = z.zone_id AND z.division_id = d.division_id  "
                    + " AND  if( '" + switching_point + "'  = '' , s.switching_point like '%' , s.switching_point = '" + switching_point + "' ) "
                    + " AND  if( '" + location + "'  = '' ,  s.address1 like '%' , s.address1 = '" + location + "' ) "
                    + " AND  if( '" + feeder_filter + "'  = '' , f.feeder_name like '%' , f.feeder_name = '" + feeder_filter + "' ) "
                    + " AND  if( '" + zone_filter + "'  = '' , z.zone like '%' , z.zone = '" + zone_filter + "' ) "
                    + " AND  if( '" + division_filter + "'  = '' , d.division_name like '%' , d.division_name = '" + division_filter + "' ) "
                    + " AND  if( '" + switching_pt_no_filter + "'  = '' , s.switching_pt_code like '%' , s.switching_pt_code = '" + switching_pt_no_filter + "' ) "
                    + " AND  if( '" + switching_point_type_filter + "'  = '' , st.switching_point_type like '%' , st.switching_point_type = '" + switching_point_type_filter + "' ) ";
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error:SwithingPointModel-getNoOfRows-- " + e);
        }
        return noOfRows;
    }

    public List<SwitchingPoint> showData(int lowerLimit, int noOfRowsToDisplay, String switching_point, String location, String feeder_filter, String zone_filter, String switching_pt_no_filter, String switching_point_type_filter, String division_filter) {
        PreparedStatement pstmt = null;
        String query;
        List<SwitchingPoint> list = new ArrayList<SwitchingPoint>();
        try {
            /*if ((switching_point == null || switching_point.isEmpty())
                    && (location == null || location.isEmpty())
                    && (feeder_filter == null || feeder_filter.isEmpty())
                    && (zone_filter == null || zone_filter.isEmpty())
                    && (division_filter == null || division_filter.isEmpty())
                    && (switching_pt_no_filter == null || switching_pt_no_filter.isEmpty())
                    && (switching_point_type_filter == null || switching_point_type_filter.isEmpty())) {
                query = "select s.switching_point_id, d.division_name, s.switching_point,  f.feeder_name, s.auto_switching_point, z.zone ,"
                        + " m.meter_name, m.sanctioned_load_kw, s.address1, s.address2, s.address3, s.description, s.active, s.switching_pt_code "
                        + "from switching_point s, meters m, feeder f, zone z, division d, switching_point_type st where  s.feeder_id= f.feeder_id  AND s.active='Y' "
                        + "  and s.switching_point_id = m.switching_point_id "
                        + " and f.zone_id = z.zone_id AND z.division_id = d.division_id  "
                        + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
                pstmt = connection.prepareStatement(query);
            } else {*/
                if (switching_point_type_filter.equals("Switching Point Type")) 
                    switching_point_type_filter = "";
               /// }
                query = "select s.switching_point_id,  d.division_name, s.switching_point,  f.feeder_name, s.auto_switching_point, z.zone ,"
                        + " m.meter_name, m.sanctioned_load_kw, s.address1, s.address2, s.address3, s.description, s.active, s.switching_pt_code  "
                        + " from switching_point s, meters m, feeder f, zone z, division d, switching_point_type st where m.active='y' and s.feeder_id= f.feeder_id "
                        + "  and s.switching_point_id = m.switching_point_id "
                        + " and f.zone_id = z.zone_id AND z.division_id = d.division_id  "
                        + " AND  if( '" + switching_point + "'  = '' , s.switching_point like '%' , s.switching_point = '" + switching_point + "' ) "
                        + " AND  if( '" + location + "'  = '' , s.address1 like '%' , s.address1 = '" + location + "' ) "
                        + " AND  if( '" + feeder_filter + "'  = '' , f.feeder_name like '%' , f.feeder_name = '" + feeder_filter + "' ) "
                        + " AND  if( '" + zone_filter + "'  = '' , z.zone like '%' , z.zone = '" + zone_filter + "' ) "
                        + " AND  if( '" + division_filter + "'  = '' , d.division_name like '%' , d.division_name = '" + division_filter + "' ) "
                        + " AND  if( '" + switching_pt_no_filter + "'  = '' , s.switching_pt_code like '%' , s.switching_pt_code = '" + switching_pt_no_filter + "' ) "
                        + " AND  if( '" + switching_point_type_filter + "'  = '' , st.switching_point_type like '%' , st.switching_point_type = '" + switching_point_type_filter + "' ) "
                        + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
                pstmt = connection.prepareStatement(query);
           // }
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SwitchingPoint switchingPoint = new SwitchingPoint();
                switchingPoint.setMeter_name(rset.getString("meter_name"));
                switchingPoint.setSanctioned_load_kw(rset.getString("sanctioned_load_kw"));
                switchingPoint.setZone(rset.getString("zone"));
                switchingPoint.setDivision_name(rset.getString("division_name"));
                switchingPoint.setSwitching_point_id(rset.getInt("switching_point_id"));
                switchingPoint.setSwitching_point(rset.getString("switching_point"));
                switchingPoint.setAuto_switching_point(rset.getString("auto_switching_point"));
                switchingPoint.setFeeder_name(rset.getString("feeder_name"));
                switchingPoint.setAddress1(rset.getString("address1"));
                switchingPoint.setAddress2(rset.getString("address2"));
                switchingPoint.setAddress3(rset.getString("address3"));
                switchingPoint.setDescription(rset.getString("description"));
                switchingPoint.setActive(rset.getString("active"));
                switchingPoint.setSwitching_point_code(rset.getString("switching_pt_code"));
                
                list.add(switchingPoint);
            }
        } catch (Exception e) {
            System.out.println("Error:--SwithingPointModel--- showData--" + e);
        }
        return list;
    }

    public int InsertSwitchpoint(SwitchingPoint switchingpoint) {
        int row_affected = 0;
        try {
            String query = "insert into switching_point (switching_point , address1, address2, address3,"
                    + " feeder_id  ,description , active, auto_switching_point ,switching_pt_code ) values( ?,?,?,?,?,? ,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, switchingpoint.getSwitching_point());
            pstmt.setString(2, switchingpoint.getAddress1());
            pstmt.setString(3, switchingpoint.getAddress2());
            pstmt.setString(4, switchingpoint.getAddress3());
            pstmt.setInt(5, switchingpoint.getFeeder_id());
            pstmt.setString(6, switchingpoint.getDescription());
            pstmt.setString(7, switchingpoint.getActive());
            pstmt.setString(8, switchingpoint.getAuto_switching_point());
            //pstmt.setInt(9, Integer.parseInt(switchingpoint.getSwitching_point_type()));
            String switchingPointTypeName = getSwitchingPointTypeName(Integer.parseInt(switchingpoint.getSwitching_point_type()));
            String cutAdName = cutAdName(switchingPointTypeName);
            int latestSwitchingPointCode = getLatestSwitchingPointCode(cutAdName);
            latestSwitchingPointCode++;
            pstmt.setString(9, cutAdName + "_" + latestSwitchingPointCode);
            row_affected = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error SwithingPointModel InsertFeeder" + e);
        }
        if (row_affected > 0) {
            message = "Record Saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot Save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return row_affected;
    }

    public int updateSwitchpoint(SwitchingPoint switchingpoint) {
        int row_affected = 0;
        try {
            String query = "update switching_point set switching_point =? , address1 =?, address2=?, "
                    + " address3 =?, feeder_id =?, description=? , active=?, auto_switching_point = ?, "
                    + "  switching_pt_code = ?  "
                    + " where switching_point_id = ? ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, switchingpoint.getSwitching_point());
            pstmt.setString(2, switchingpoint.getAddress1());
            pstmt.setString(3, switchingpoint.getAddress2());
            pstmt.setString(4, switchingpoint.getAddress3());
            pstmt.setInt(5, switchingpoint.getFeeder_id());
            pstmt.setString(6, switchingpoint.getDescription());
            pstmt.setString(7, switchingpoint.getActive());
            pstmt.setString(8, switchingpoint.getAuto_switching_point());
            //pstmt.setInt(9, Integer.parseInt(switchingpoint.getSwitching_point_type()));
            int switching_point_type_id = Integer.parseInt(switchingpoint.getSwitching_point_type());
            int switching_point_type_old_id = getSwitchingPointTypeID(switchingpoint.getSwitching_point_type_old());
            if (switching_point_type_old_id == switching_point_type_id) {
                pstmt.setString(9, switchingpoint.getSwitching_point_code());
            } else {
                String switchingPointTypeName = getSwitchingPointTypeName(Integer.parseInt(switchingpoint.getSwitching_point_type()));
                String cutAdName = cutAdName(switchingPointTypeName);
                int latestSwitchingPointCode = getLatestSwitchingPointCode(cutAdName);
                latestSwitchingPointCode++;
                pstmt.setString(9, cutAdName + "_" + latestSwitchingPointCode);
            }
            pstmt.setInt(10, switchingpoint.getSwitching_point_id());
            row_affected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error SwithingPointModel updateFeeder" + e);
        }
        if (row_affected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return row_affected;
    }

    public int deleteRecord(int switching_point_id) {
        String query = "DELETE FROM switching_point WHERE switching_point_id=" + switching_point_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:SwithingPointModel-deleteRecord-- " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public String cutAdName(String adName) {
        String[] ad;
        String name = null;
        String cutName = null;
        int l = adName.length();
        ad = adName.split("\\s+");
        int count = ad.length;
        if (count == 1) {
            name = ad[0].trim();
            cutName = name.substring(0, 1);
            cutName.toUpperCase();
            cutName = cutName + name.substring(1, 2);
        } else if (count == 2) {
            name = ad[0].trim();
            cutName = name.substring(0, 1);
            name = ad[1].trim();
            cutName = cutName + name.substring(0, 1);
            cutName.toUpperCase();
        } else if (count == 3 || count > 3) {
            name = ad[0].trim();
            cutName = name.substring(0, 1);
            name = ad[1].trim();
            cutName = cutName + name.substring(0, 1);
            name = ad[2].trim();
            cutName = cutName + name.substring(0, 1);
            cutName.toUpperCase();
        }
        return cutName;
    }

    public String getSwitchingPointTypeName(int switching_point_type_id) {
        String switching_point_type = "";
        String query = " SELECT switching_point_type FROM switching_point_type WHERE switching_point_type_id = ? ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, switching_point_type_id);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                switching_point_type = rset.getString("switching_point_type");
            }
        } catch (Exception e) {
            System.out.println("getSwitchingPointTypeName - ERROR : " + e);
        }
        return switching_point_type;
    }

    public int getSwitchingPointTypeID(String switching_point_type) {
        int switching_point_type_id = 0;
        String query = " SELECT switching_point_type_id FROM switching_point_type WHERE switching_point_type = ? ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, switching_point_type);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                switching_point_type_id = rset.getInt("switching_point_type_id");
            }
        } catch (Exception e) {
            System.out.println("getSwitchingPointTypeID - ERROR : " + e);
        }
        return switching_point_type_id;
    }

    public int getLatestSwitchingPointCode(String switching_pt_name) {
        int switching_pt_code = 0;
        String query = " SELECT SUBSTRING_INDEX(switching_pt_code, '_', -1) FROM switching_point WHERE "
                + " SUBSTRING_INDEX(switching_pt_code, '_', 1) = ? ORDER BY CAST(SUBSTRING_INDEX(switching_pt_code, '_', -1) AS SIGNED) DESC LIMIT 1 ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, switching_pt_name);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                switching_pt_code = Integer.parseInt(rset.getString("SUBSTRING_INDEX(switching_pt_code, '_', -1)"));
            }
        } catch (Exception e) {
            System.out.println("getLatestSwitchingPointCode - ERROR : " + e);
        }
        return switching_pt_code;
    }

    public Map<Integer, String> getSwitchingPointType() {
        Map<Integer, String> mymap = new LinkedHashMap<Integer, String>();
        String query = " SELECT switching_point_type_id, switching_point_type FROM switching_point_type ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                int switching_point_type_id = rset.getInt("switching_point_type_id");
                String switching_point_type = rset.getString("switching_point_type");
                mymap.put(switching_point_type_id, switching_point_type);
            }
        } catch (Exception e) {
            System.out.println("getSwitchingPointType - ERROR : " + e);
        }
        return mymap;
    }

    public byte[] generateSWPointList(String jrxmlFilePath, String swtch_point, String loc, String feeder, String zone, String switching_pt_no, String switching_point_type, String division) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        String zone_param = zone, division_param = division;
        try {
            if (switching_point_type.equals("Switching Point Type")) {
                switching_point_type = "";
            }
            mymap.put("switching_point", swtch_point);
            mymap.put("location", loc);
            mymap.put("feeder", feeder);
            mymap.put("zone", zone);
            mymap.put("switching_pt_no", switching_pt_no);
            mymap.put("switching_point_type", switching_point_type);
            mymap.put("division", division);
            if (division.equals("")) {
                division_param = "ALL";
            }
            if (zone.equals("")) {
                zone_param = "ALL";
            }
            mymap.put("division_param", division_param);
            mymap.put("zone_param", zone_param);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, connection);
        } catch (Exception e) {
            System.out.println("MeterModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public int getmeter_id(String meter_service_num) {
        String query = "SELECT meter_id FROM meters WHERE meter_service_number  = ? ";
        int meter_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, meter_service_num);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {    // move cursor from BOR to valid record.
                meter_id = rset.getInt("meter_id");
            }
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel  getmeter_id" + e);
        }
        return meter_id;
    }

    public int getfeeder_id(String feeder_name) {
        String query = "SELECT feeder_id FROM feeder WHERE feeder_name = ? ";
        int feeder_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, feeder_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            feeder_id = rset.getInt("feeder_id");
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel getfeeder_id" + e);
        }
        return feeder_id;
    }

    public int getfeeder_id(int switching_pont_id) {
        String query = "SELECT feeder_id FROM switching_point WHERE switching_point_id = ? ";
        int feeder_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, switching_pont_id);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                feeder_id = rset.getInt("feeder_id");
            }
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel getfeeder_id" + e);
        }
        return feeder_id;
    }

    public String getSwitchingPoint(String address, String feeder, int switching_pont_id) {
        String switching_point = "swp";
        int count = 0;
        try {
            int feeder_id = getfeeder_id(feeder);
            if (feeder_id > 0) {
                String query = "select count(*) from switching_point where feeder_id= " + feeder_id;
                PreparedStatement psmt = connection.prepareStatement(query);
                ResultSet rst = psmt.executeQuery();
                if (rst.next()) {
                    count = rst.getInt("count(*)");
                }
                int av_feeder_id = getfeeder_id(switching_pont_id);
                if (feeder_id == av_feeder_id) {
                    // in this case cont woulde not be incremented
                } else {
                    count = count + 1;
                }


                switching_point = feeder + "_" + switching_point + count + "_" + address;
            }
        } catch (Exception e) {
            System.out.println("Error in SwithingPointModel getSwitchingPoint" + e);
        }
        return switching_point;
    }

    public List<String> getmeter_srv_numList() {
        List<String> list = new ArrayList<String>();
        String query = " select meter_service_number from meters where active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("meter_service_number"));
            }
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel  getmeter_srv_numList" + e);
        }
        return list;
    }

    public List<String> getLocation(String q) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = " SELECT address1 FROM switching_point where active='Y' ";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String address1 = rset.getString("address1");
                if (address1.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(address1);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such address exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public String getSwitchingPointNo(String feederName) {
        List<String> swtPt = new ArrayList<String>();
        String switchingPointNo = "";
        String switchPt = "";
        PreparedStatement pstmt;
        int suffix = 0;
        String query = " SELECT SUBSTRING_INDEX(auto_switching_point, '_', -1) FROM switching_point WHERE "
                + " SUBSTRING_INDEX(auto_switching_point, '_', 1) = ? ORDER BY switching_point_id DESC LIMIT 1 ";
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, feederName);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                switchingPointNo = rset.getString("SUBSTRING_INDEX(auto_switching_point, '_', -1)");
                suffix = Integer.parseInt(switchingPointNo.substring(switchingPointNo.length() - 1, switchingPointNo.length()));
            }
            suffix++;
            switchPt = "swp" + suffix;
            swtPt.add(switchPt);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return switchPt;
    }

    public List<String> getSwitchingPoint(String q) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = " SELECT switching_point FROM switching_point s where active = 'Y' ";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
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
                list.add("No such switching_point exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getSwitchingPoint(String q, String feeder_name) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = " SELECT switching_point FROM switching_point s, feeder f where s.feeder_id = f.feeder_id "
                + " AND f.feeder_name = ? AND s.active = 'Y' ";
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, feeder_name);
            ResultSet rset = pstmt.executeQuery();
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
                list.add("No such switching_point exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getfeederList() {
        List<String> list = new ArrayList<String>();
        String query = "SELECT feeder_name FROM feeder where active='Y' ORDER BY feeder_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("feeder_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel  getfeederList" + e);
        }
        return list;
    }

    public boolean chkmeterServiceNumEntery(int meter_id) {
        int count = 0;
        String switching_point = null, meter_service_num = null;
        try {
            String query = " select count(*), switching_point, meter_service_number from switching_point  s, "
                    + " meters m where s.meter_id = ?  and s.active='Y' and s.meter_id= m.meter_id ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setInt(1, meter_id);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                count = rst.getInt("count(*)");
                switching_point = rst.getString("switching_point");
                meter_service_num = rst.getString("meter_service_number");

            }

        } catch (Exception e) {
            System.out.println("Error in meter Model chkmeterServiceNumEntery" + e);
        }
        if (count > 0) {
            message = switching_point + ": is alredy avilable at this Service number :" + meter_service_num;
            msgBgColor = COLOR_ERROR;
        }

        return count == 0 ? true : false;
    }

    public boolean chkmeterServiceNumEntery(int switching_point_id, int meter_id) {
        int count = 0;
        String switching_point = null, meter_service_num = null;
        try {
            String query = " select count(*) , switching_point , meter_service_number  from switching_point s , meters m "
                    + " where  switching_point_id !=?  and s.meter_id =?  and s.active ='Y' and m.meter_id= s.meter_id ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setInt(1, switching_point_id);
            psmt.setInt(2, meter_id);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                count = rst.getInt("count(*)");
                switching_point = rst.getString("switching_point");
                meter_service_num = rst.getString("meter_service_number");

            }

        } catch (Exception e) {
            System.out.println("Error in meter Model chkmeterServiceNumEntery" + e);
        }
        if (count > 0) {
            message = " This " + meter_service_num + " : Service number is alredy avilable at  this switching point :" + switching_point;
            msgBgColor = COLOR_ERROR;
        }
        return count == 0 ? true : false;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("PmViewExistingQtListModel closeConnection() Error: " + e);
        }
    }

   

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
