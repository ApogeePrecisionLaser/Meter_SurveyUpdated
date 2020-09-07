/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.survey.tableClasses.TubeWellBoreTypeBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Administrator
 */
public class TubeWellBoreDataModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<TubeWellBoreTypeBean> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in FuseTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public int insertRecord(TubeWellBoreTypeBean tubeWellBoreTypeBean) {

        String query = "INSERT INTO tubewell_bore_data ( depth, bore_diameter, bore_casing_type_id, motore_capacity, "
                + " motor_type_id, suction_diameter, delivery_diameter, discharge_capacity, remark,"
                + " tube_well_detail_id,date_of_installation,contact_person_name,contact_person_address,contact_person_mobile_no,"
                + " operated_by,type_of_use_id,operator_name,operator_mobile_no,ward_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, tubeWellBoreTypeBean.getDepth());
            pstmt.setString(2, tubeWellBoreTypeBean.getBore_diameter());
            int casing_type_id=getCasingTypeId(tubeWellBoreTypeBean.getBore_casing_type());
            pstmt.setInt(3,casing_type_id);
            pstmt.setString(4, tubeWellBoreTypeBean.getMotore_capacity());
            int motor_type_id=getMotorTypeId(tubeWellBoreTypeBean.getMotor_type());
            pstmt.setInt(5,motor_type_id);
            pstmt.setString(6, tubeWellBoreTypeBean.getSuction_diameter());
            pstmt.setString(7, tubeWellBoreTypeBean.getDelivery_diameter());
            pstmt.setString(8, tubeWellBoreTypeBean.getDischarge_capacity());
            pstmt.setString(9, tubeWellBoreTypeBean.getRemark());
            pstmt.setInt(10, tubeWellBoreTypeBean.getTube_well_detail_id());
           
            pstmt.setString(11, tubeWellBoreTypeBean.getDate_of_installation());
            pstmt.setString(12,tubeWellBoreTypeBean.getContact_person_name());
            pstmt.setString(13,tubeWellBoreTypeBean.getContact_person_address());
            pstmt.setString(14,tubeWellBoreTypeBean.getContact_person_mobile_no());
            pstmt.setString(15,tubeWellBoreTypeBean.getOperated_by());
            int type_of_use_id=getTypeOfUseId(tubeWellBoreTypeBean.getUsed_for());

            pstmt.setInt(16,type_of_use_id);
            pstmt.setString(17,tubeWellBoreTypeBean.getOperator_name());
            pstmt.setString(18,tubeWellBoreTypeBean.getOperator_mobile_no());
            pstmt.setInt(19, tubeWellBoreTypeBean.getWard_id());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
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

    public int updateRecord(TubeWellBoreTypeBean tubeWellBoreTypeBean) {
        int rowsAffected=0;

        String query1 = "SELECT max(rev_no) rev_no FROM tubewell_bore_data WHERE tubewell_bore_data_id = "+tubeWellBoreTypeBean.getTubewell_bore_data_id()+" && active='Y' ORDER BY rev_no DESC";
      String query2 = " UPDATE tubewell_bore_data SET active=? WHERE tubewell_bore_data_id = ? && rev_no = ? ";
      String query3 = "INSERT INTO tubewell_bore_data (tubewell_bore_data_id,depth, bore_diameter, bore_casing_type, motore_capacity, "
                + " motor_type, suction_diameter, delivery_diameter, discharge_capacity, remark,"
                + " tube_well_detail_id,date_of_installation,contact_person_name,contact_person_address,contact_person_mobile_no,"
                + " operated_by,used_for,operator_name,operator_mobile_no,ward_id,rev_no, active) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,tubeWellBoreTypeBean.getTubewell_bore_data_id());
           pst.setInt(3, rs.getInt("rev_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("rev_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1, tubeWellBoreTypeBean.getTubewell_bore_data_id());
             psmt.setString(2, tubeWellBoreTypeBean.getDepth());
            psmt.setString(3, tubeWellBoreTypeBean.getBore_diameter());

            int casing_type_id=getCasingTypeId(tubeWellBoreTypeBean.getBore_casing_type());
            psmt.setInt(4,casing_type_id);
            psmt.setString(5, tubeWellBoreTypeBean.getMotore_capacity());

            int motor_type_id=getMotorTypeId(tubeWellBoreTypeBean.getMotor_type());
            psmt.setInt(6,motor_type_id);
            psmt.setString(7, tubeWellBoreTypeBean.getSuction_diameter());
            psmt.setString(8, tubeWellBoreTypeBean.getDelivery_diameter());
            psmt.setString(9, tubeWellBoreTypeBean.getDischarge_capacity());
            psmt.setString(10, tubeWellBoreTypeBean.getRemark());
            psmt.setInt(11, tubeWellBoreTypeBean.getTube_well_detail_id());

            psmt.setString(12, tubeWellBoreTypeBean.getDate_of_installation());
            psmt.setString(13,tubeWellBoreTypeBean.getContact_person_name());
            psmt.setString(14,tubeWellBoreTypeBean.getContact_person_address());
            psmt.setString(15,tubeWellBoreTypeBean.getContact_person_mobile_no());
            psmt.setString(16,tubeWellBoreTypeBean.getOperated_by());

            int type_of_use_id=getTypeOfUseId(tubeWellBoreTypeBean.getUsed_for());
            psmt.setInt(17,type_of_use_id);
            psmt.setString(18,tubeWellBoreTypeBean.getOperator_name());
            psmt.setString(19,tubeWellBoreTypeBean.getOperator_mobile_no());
            psmt.setInt(20, tubeWellBoreTypeBean.getWard_id());
             psmt.setInt(21, rev);
             psmt.setString(22, "Y");
             int a = psmt.executeUpdate();
              if(a > 0)
              rowsAffected=a;
             }
           }

          } catch (Exception e)
             {
              System.out.println("CityDAOClass reviseRecord() Error: " + e);
             }


//        String query = "UPDATE tubewell_bore_data SET depth=?, bore_diameter=?, bore_casing_type=?, motore_capacity=?, "
//                + "motor_type=?, suction_diameter=?, delivery_diameter=?, discharge_capacity=?, remark=?,"
//                + " tube_well_detail_id=?, tube_well_rev_no=?,date_of_installation=? WHERE tubewell_bore_data_id=? ";
//        int rowsAffected = 0;
//        try {
//            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, tubeWellBoreTypeBean.getDepth());
//            pstmt.setString(2, tubeWellBoreTypeBean.getBore_diameter());
//            pstmt.setString(3, tubeWellBoreTypeBean.getBore_casing_type());
//            pstmt.setString(4, tubeWellBoreTypeBean.getMotore_capacity());
//            pstmt.setString(5, tubeWellBoreTypeBean.getMotor_type());
//            pstmt.setString(6, tubeWellBoreTypeBean.getSuction_diameter());
//            pstmt.setString(7, tubeWellBoreTypeBean.getDelivery_diameter());
//            pstmt.setString(8, tubeWellBoreTypeBean.getDischarge_capacity());
//            pstmt.setString(9, tubeWellBoreTypeBean.getRemark());
//            pstmt.setInt(10, tubeWellBoreTypeBean.getTube_well_detail_id());
//            pstmt.setInt(11, tubeWellBoreTypeBean.getTube_well_rev_no());
//             pstmt.setString(12, tubeWellBoreTypeBean.getDate_of_installation());
//            pstmt.setInt(13, tubeWellBoreTypeBean.getTube_well_detail_id());
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("error while updating record........." + e);
//        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
    
    public List<String> getTUbeWellNameByIvrsNo(String q,String ivrs_no) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT td.tube_well_name "
                      +" FROM tube_well_detail td "
                      +" where td.ivrs_no='"+ivrs_no+"'"
                      +" and td.active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            //q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String tube_well_name = rset.getString("tube_well_name");
                //if (tube_well_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(tube_well_name);
                    count++;
               // }
            }
            if (count == 0) {
                list.add("No such TubeWell Name exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }

    public int getCasingTypeId(String casing_type_name) {
        String query = " select bore_casing_type_id "
                       +" from bore_casing_type "
                       +" where bore_casing_type_name='"+casing_type_name+"'";
        int bore_casing_type_id = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bore_casing_type_id = rs.getInt("bore_casing_type_id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getDivisonId division type model" + e);
        }

        return bore_casing_type_id;
    }


    public int getMotorTypeId(String motor_type_name) {
        String query = " select motor_type_id "
                       +" from meter_survey.motor_type "
                       +" where motor_type_name='"+motor_type_name+"'";
        int motor_type_id = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                motor_type_id = rs.getInt("motor_type_id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getDivisonId division type model" + e);
        }

        return motor_type_id;
    }
            public int getTypeOfUseId(String type_of_use_name) {
        String query = " select type_of_use_id "
                       +" from meter_survey.type_of_use "
                       +" where type_of_use_name='"+type_of_use_name+"'";
        int type_of_use_id = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                type_of_use_id = rs.getInt("type_of_use_id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getDivisonId division type model" + e);
        }

        return type_of_use_id;
    }
    public int getTubeWellDetailId(String ivrs_no) {
        String query = " select tube_well_detail_id from tube_well_detail where ivrs_no='" + ivrs_no + "'";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                noOfRows = rs.getInt("tube_well_detail_id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getDivisonId division type model" + e);
        }

        return noOfRows;
    }
    public int getWardId(String ward_no) {
        String query = " select ward_id from ward where ward_no='" + ward_no + "'";
        int ward_id = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ward_id = rs.getInt("ward_id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getDivisonId division type model" + e);
        }

        return ward_id;
    }

    public int getTubeWellRevNo(String tubewell_name) {
        String query = " select tube_well_rev_no from tube_well_detail where tube_well_name='" + tubewell_name + "'";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                noOfRows = rs.getInt("tube_well_rev_no");
            }
        } catch (Exception e) {
            System.out.println("Error inside getDivisonId division type model" + e);
        }

        return noOfRows;
    }

    public List<String> getTUbeWellName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT tube_well_name FROM tube_well_detail GROUP BY tube_well_name ORDER BY tube_well_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String tube_well_name = rset.getString("tube_well_name");
                if (tube_well_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(tube_well_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }
    public List<String> getIVRS(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select ivrs_no "
                      +" from meter_survey.tube_well_detail t "
                      +" where t.active='Y'";
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
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }
    public List<String> getUsed_for_name(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select type_of_use_name "
                      +" from meter_survey.type_of_use t ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String type_of_use_name = rset.getString("type_of_use_name");
                if (type_of_use_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(type_of_use_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }
    public List<String> getIvrsNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT td.ivrs_no "
                      +" from tubewell_bore_data as t ,tube_well_detail as td "
                      +" where t.tube_well_detail_id=td.tube_well_detail_id "
                      +" and t.active='Y'";
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
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }
    public List<String> getWardName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select  ward_no "
                      +" from meter_survey.ward w "
                      +" where w.active='Y'";
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
                list.add("No such Ward_no exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }

    public List<String> getBoreCasingTypeName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select bore_casing_type_name "
                       +" from meter_survey.bore_casing_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String bore_casing_type_name = rset.getString("bore_casing_type_name");
                if (bore_casing_type_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(bore_casing_type_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such bore_casing_type_name exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }

    public List<String> getMotorTypeName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select motor_type_name "
                       +" from meter_survey.motor_type";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String motor_type_name = rset.getString("motor_type_name");
                if (motor_type_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(motor_type_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such motor_type_name exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchTubeWellName,String searchIvrsNo) {
        String query = "SELECT count(*) "
                +" FROM tubewell_bore_data as t ,tube_well_detail as td,ward w, "
                    +" bore_casing_type bct,motor_type mt,type_of_use tou "
                    +" where t.tube_well_detail_id=td.tube_well_detail_id "
                    +" and t.ward_id=w.ward_id "
                    +" and t.bore_casing_type_id=bct.bore_casing_type_id "
                    +" and t.motor_type_id=mt.motor_type_id "
                    +" and t.type_of_use_id=tou.type_of_use_id "
                    +" and t.active='Y' "
                    +" and td.active='Y' "
                    +" and w.active='Y' "
                + " AND IF('" + searchTubeWellName + "' = '', td.tube_well_name  LIKE '%%', td.tube_well_name ='" + searchTubeWellName + "') "
                + " AND IF('" + searchIvrsNo + "' = '', td.ivrs_no  LIKE '%%', td.ivrs_no ='" + searchIvrsNo + "') "
                +" ORDER BY  td.tube_well_name";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
//            stmt.setString(1, searchTubeWellName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows fuse type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }



    public List<TubeWellBoreTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchTubeWellName,String searchIvrsNo) {
        List<TubeWellBoreTypeBean> list = new ArrayList<TubeWellBoreTypeBean>();

        String addLimit = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
       if(lowerLimit == -1)
           addLimit = "";
        String query="SELECT tubewell_bore_data_id,td.ivrs_no,w.ward_no,td.tube_well_name,depth, bore_diameter, bct.bore_casing_type_name, motore_capacity, "
                    +" mt.motor_type_name, suction_diameter, delivery_diameter, discharge_capacity, t.remark,t.created_date, "
                    +" t.tube_well_detail_id,date_of_installation,contact_person_name,contact_person_address,contact_person_mobile_no, "
                    +" operated_by,type_of_use_name,operator_name,operator_mobile_no "
                    +" FROM tubewell_bore_data as t ,tube_well_detail as td,ward w, "
                    +" bore_casing_type bct,motor_type mt,type_of_use tou "
                    +" where t.tube_well_detail_id=td.tube_well_detail_id "
                    +" and t.ward_id=w.ward_id "
                    +" and t.bore_casing_type_id=bct.bore_casing_type_id "
                    +" and t.motor_type_id=mt.motor_type_id "
                    +" and t.type_of_use_id=tou.type_of_use_id "
                    +" and t.active='Y' "
                    +" and td.active='Y' "
                   +" and w.active='Y' "
                + " AND IF('" + searchTubeWellName + "' = '', td.tube_well_name  LIKE '%%', td.tube_well_name ='" + searchTubeWellName + "') "
                + " AND IF('" + searchIvrsNo + "' = '', td.ivrs_no  LIKE '%%', td.ivrs_no ='" + searchIvrsNo + "') "
                +" ORDER BY  td.tube_well_name"
                +  addLimit;
        try {
            System.out.println(query);
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, searchTubeWellName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TubeWellBoreTypeBean tubeWellBoreType = new TubeWellBoreTypeBean();
                tubeWellBoreType.setTubewell_bore_data_id(rset.getInt("tubewell_bore_data_id"));
                tubeWellBoreType.setIvrs_no(rset.getString("ivrs_no"));
                tubeWellBoreType.setWard_no(rset.getString("ward_no"));
                tubeWellBoreType.setTubewell_name(rset.getString("tube_well_name"));
                tubeWellBoreType.setDepth(rset.getString("depth"));
                tubeWellBoreType.setBore_diameter(rset.getString("bore_diameter"));
                tubeWellBoreType.setBore_casing_type(rset.getString("bore_casing_type_name"));
                tubeWellBoreType.setMotore_capacity(rset.getString("motore_capacity"));
                tubeWellBoreType.setMotor_type(rset.getString("motor_type_name"));
                tubeWellBoreType.setSuction_diameter(rset.getString("suction_diameter"));
                tubeWellBoreType.setDelivery_diameter(rset.getString("delivery_diameter"));
                tubeWellBoreType.setDischarge_capacity(rset.getString("discharge_capacity"));
                tubeWellBoreType.setRemark(rset.getString("remark"));
                tubeWellBoreType.setDate_of_installation(rset.getString("date_of_installation"));
                tubeWellBoreType.setContact_person_name(rset.getString("contact_person_name"));
                tubeWellBoreType.setContact_person_address(rset.getString("contact_person_address"));
                tubeWellBoreType.setContact_person_mobile_no(rset.getString("contact_person_mobile_no"));
                tubeWellBoreType.setOperated_by(rset.getString("operated_by"));
                tubeWellBoreType.setUsed_for(rset.getString("type_of_use_name"));
                tubeWellBoreType.setOperator_name(rset.getString("operator_name"));
                tubeWellBoreType.setOperator_mobile_no(rset.getString("operator_mobile_no"));
                tubeWellBoreType.setCreated_date(rset.getString("created_date"));


                list.add(tubeWellBoreType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<TubeWellBoreTypeBean> showAllData(String searchTubeWellName,String searchIvrsNo) {
        List<TubeWellBoreTypeBean> listAll = new ArrayList<TubeWellBoreTypeBean>();

//        String query = " SELECT tubewell_bore_data_id, depth, bore_diameter, bore_casing_type, motore_capacity, motor_type, suction_diameter,"
//                + " delivery_diameter, discharge_capacity, t.remark, td.tube_well_name "
//                + " FROM tubewell_bore_data as t ,tube_well_detail as td where t.tube_well_detail_id=td.tube_well_detail_id"
//                + " AND IF('" + searchTubeWellName + "' = '', td.tube_well_name  LIKE '%%', td.tube_well_name =?) "
//                + " ORDER BY  td.tube_well_name ";
        String query="SELECT tubewell_bore_data_id,td.ivrs_no,w.ward_no,td.tube_well_name,depth, bore_diameter, bore_casing_type, motore_capacity, "
                 +" motor_type, suction_diameter, delivery_diameter, discharge_capacity, t.remark,t.created_date, "
                 +" t.tube_well_detail_id,date_of_installation,contact_person_name,contact_person_address,contact_person_mobile_no, "
                 +" operated_by,used_for,operator_name,operator_mobile_no "
                 +" FROM tubewell_bore_data as t ,tube_well_detail as td,ward w "
                 +" where t.tube_well_detail_id=td.tube_well_detail_id "
                 +" and t.ward_id=w.ward_id "
                 +" and t.active='Y' "
                + " AND IF('" + searchTubeWellName + "' = '', td.tube_well_name  LIKE '%%', td.tube_well_name ='" + searchTubeWellName + "') "
                + " AND IF('" + searchIvrsNo + "' = '', td.ivrs_no  LIKE '%%', td.ivrs_no ='" + searchIvrsNo + "') "
                +" ORDER BY  td.tube_well_name";
        try {
            System.out.println(query);
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, searchTubeWellName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TubeWellBoreTypeBean tubeWellBoreType = new TubeWellBoreTypeBean();
                tubeWellBoreType.setTubewell_bore_data_id(rset.getInt("tubewell_bore_data_id"));
                tubeWellBoreType.setIvrs_no(rset.getString("ivrs_no"));
                tubeWellBoreType.setWard_no(rset.getString("ward_no"));
                tubeWellBoreType.setTubewell_name(rset.getString("tube_well_name"));
                tubeWellBoreType.setDepth(rset.getString("depth"));
                tubeWellBoreType.setBore_diameter(rset.getString("bore_diameter"));
                tubeWellBoreType.setBore_casing_type(rset.getString("bore_casing_type"));
                tubeWellBoreType.setMotore_capacity(rset.getString("motore_capacity"));
                tubeWellBoreType.setMotor_type(rset.getString("motor_type"));
                tubeWellBoreType.setSuction_diameter(rset.getString("suction_diameter"));
                tubeWellBoreType.setDelivery_diameter(rset.getString("delivery_diameter"));
                tubeWellBoreType.setDischarge_capacity(rset.getString("discharge_capacity"));
                tubeWellBoreType.setRemark(rset.getString("remark"));
                tubeWellBoreType.setDate_of_installation(rset.getString("date_of_installation"));
                tubeWellBoreType.setContact_person_name(rset.getString("contact_person_name"));
                tubeWellBoreType.setContact_person_address(rset.getString("contact_person_address"));
                tubeWellBoreType.setContact_person_mobile_no(rset.getString("contact_person_mobile_no"));
                tubeWellBoreType.setOperated_by(rset.getString("operated_by"));
                tubeWellBoreType.setUsed_for(rset.getString("used_for"));
                tubeWellBoreType.setOperator_name(rset.getString("operator_name"));
                tubeWellBoreType.setOperator_mobile_no(rset.getString("operator_mobile_no"));
                tubeWellBoreType.setCreated_date(rset.getString("created_date"));


                listAll.add(tubeWellBoreType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

    public int deleteRecord(int tubewell_bore_data_id) {
        String query = "UPDATE tubewell_bore_data set active='N' "
                + " WHERE tubewell_bore_data_id=" + tubewell_bore_data_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
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
