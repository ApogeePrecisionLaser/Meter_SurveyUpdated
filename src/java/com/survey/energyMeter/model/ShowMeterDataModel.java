/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.model;


import com.survey.energyMeter.tableClasses.ShowMeterDataBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class ShowMeterDataModel {


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
            System.out.println("AraeTYpeTypeModel setConnection() Error: " + e);
        }
    }

     public int getNoOfRows() {
        int noOfRows = 0;
        try {
            String query = "select count(*) "
                     +" from meters m "
                     +" left join city as c on c.city_id=m.city_id "
                     +" left join organisation_name as org_name on org_name.organisation_id=m.organisation_id "
                     +" left join org_office as office on office.org_office_id=m.org_office_id "
                     +" left join switching_point as sw_point on sw_point.switching_point_id=m.switching_point_id "
                     +" left join feeder as f on f.feeder_id=m.feeder_id ";
            java.sql.PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = rset.getInt(1);
        } catch (Exception e) {
            System.out.println("Error in getRows() of AreaTypeModel...." + e);
        }
        return noOfRows;
    }

     public List<ShowMeterDataBean> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<ShowMeterDataBean> list = new ArrayList<ShowMeterDataBean>();
        //PreparedStatement pstmt = null;

        String query = "select meter_id,meter_name,security_deposit,sd_receipt_no,date,initial_reading,city_name, "
                     +"  meter_service_number,poll_no,organisation_name,org_office_code,switching_point,"
                     +" feeder_name,sanctioned_load_kw,reason,phase,accessed_load,effective_date, "
                     +" updated_date,calculated_load,m.description,tariff_code,msn_first_part,msn_sec_part, "
                     +" msn_third_part,msn_fourth_part,ivrs_no,file_no,calculated_security_deposit, "
                     +" meter_name_auto,ward_no,bill_sanction_load "
                     +" from meters m "
                     +" left join city as c on c.city_id=m.city_id "
                     +" left join organisation_name as org_name on org_name.organisation_id=m.organisation_id "
                     +" left join org_office as office on office.org_office_id=m.org_office_id "
                     +" left join switching_point as sw_point on sw_point.switching_point_id=m.switching_point_id "
                     +" left join feeder as f on f.feeder_id=m.feeder_id "
                     + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ShowMeterDataBean bean = new ShowMeterDataBean();
             bean.setMeter_id(rset.getInt("meter_id"));
             bean.setMeter_name(rset.getString("meter_name"));
             bean.setSecurity_deposit(rset.getString("security_deposit"));
             bean.setSd_receipt_no(rset.getString("sd_receipt_no"));
             bean.setDate(rset.getString("date"));
             bean.setInitial_reading(rset.getString("initial_reading"));
             bean.setCity_name(rset.getString("city_name"));
             bean.setMeter_service_number(rset.getString("meter_service_number"));
             bean.setPoll_no(rset.getString("poll_no"));
             bean.setOrganisation_name(rset.getString("organisation_name"));
             bean.setOrg_office_code(rset.getString("org_office_code"));
             bean.setSwitching_point(rset.getString("switching_point"));
             bean.setFeeder_name(rset.getString("feeder_name"));
             bean.setSanctioned_load_kw(rset.getString("sanctioned_load_kw"));
             bean.setReason(rset.getString("reason"));
             bean.setPhase(rset.getString("phase"));
             bean.setAccessed_load(rset.getString("accessed_load"));
             bean.setEffective_date(rset.getString("effective_date"));
             bean.setUpdated_date(rset.getString("updated_date"));
             bean.setCalculated_load(rset.getString("calculated_load"));
             bean.setDescription(rset.getString("description"));
             bean.setTariff_code(rset.getString("tariff_code"));
             bean.setMsn_first_part(rset.getString("msn_first_part"));
             bean.setMsn_sec_part(rset.getString("msn_sec_part"));
             bean.setMsn_third_part(rset.getString("msn_third_part"));
             bean.setMsn_fourth_part(rset.getString("msn_fourth_part"));
             bean.setIvrs_no(rset.getString("ivrs_no"));
             bean.setFile_no(rset.getString("file_no"));
             bean.setCalculated_security_deposit(rset.getString("calculated_security_deposit"));
             bean.setMeter_name_auto(rset.getString("meter_name_auto"));
             bean.setWard_no(rset.getString("ward_no"));
             bean.setBill_sanction_load(rset.getString("bill_sanction_load"));


                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }


public int insertRecord(String meter_id[]) {
    String meter_id2="";
    String feeder_id="";
    String ivrs_no="";
    int rowsAffected=0;
    for(int i=0;i<meter_id.length;i++){
        int meter_id1=Integer.parseInt(meter_id[i]);
        meter_id2=meter_id[i];
        String query1=" select feeder_id,ivrs_no "
                     +" from meters where meter_id="+meter_id1;

        try{

            java.sql.PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
               feeder_id=rset.getString("feeder_id");
               ivrs_no=rset.getString("ivrs_no");

               String query2="insert into tube_well_detail(meter_id,feeder_id,ivrs_no) values(?,?,?)";
               try{
               java.sql.PreparedStatement pstmt1 = connection.prepareStatement(query2);
               pstmt1.setInt(1,Integer.parseInt(meter_id2));
               pstmt1.setInt(2,Integer.parseInt(feeder_id));
               pstmt1.setString(3,ivrs_no);
               rowsAffected = pstmt1.executeUpdate();
                if (rowsAffected > 0) {
                message = " All Records saved successfully.";
                 msgBgColor = COLOR_OK;
               } else {
                  message = "Cannot save the record, some error.";
                 msgBgColor = COLOR_ERROR;
                }

                }catch(Exception e){
                System.out.println("Error in inserting basic tube well records ShowMeterDataModel insertRecords() "+e);
                }
            }

        }catch(Exception e){
            System.out.println("Error in select meter data ShowMeterDataModel insertRecords() "+e);
        }


    }

       return rowsAffected;

    }

// switching point table populate
public int insertRecordswitching_point_detail() {

    int rowsAffected=0;
    int count=0;


        String query1="select meter_id,feeder_id,ivrs_no,poll_no " +
"                     from meters " +
"                      where final_revision='VALID' and tariff_code in('LV3.2.A','LV3.2','LV3.2.B','LV3.2.C')";

        try{

            java.sql.PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

               String query2="insert into switching_point_detail(meter_id,feeder_id,ivrs_no,pole_no_s) values(?,?,?,?)";
               try{
               java.sql.PreparedStatement pstmt1 = connection.prepareStatement(query2);
               pstmt1.setInt(1,Integer.parseInt(rset.getString("meter_id")));
               pstmt1.setInt(2,Integer.parseInt(rset.getString("feeder_id")));
               pstmt1.setString(3,rset.getString("ivrs_no"));
               pstmt1.setString(4,rset.getString("poll_no"));
               rowsAffected = pstmt1.executeUpdate();
               count=count+rowsAffected;
                if (rowsAffected > 0) {
                message = count+" Records saved successfully.";
                 msgBgColor = COLOR_OK;
               } else {
                  message = "Cannot save the record, some error.";
                 msgBgColor = COLOR_ERROR;
                }

                }catch(Exception e){
                System.out.println("Error in inserting basic tube well records ShowMeterDataModel insertRecords() "+e);
                }
            }

        }catch(Exception e){
            System.out.println("Error in select meter data ShowMeterDataModel insertRecords() "+e);
        }




       return rowsAffected;

    }

// switching point table populate end
public int insertRecord1() {

    int rowsAffected=0;
    int count=0;


        String query1=" select meter_id,feeder_id,ivrs_no,poll_no "
                     +" from meters  "
                     +" where final_revision='VALID' and tariff_code in('LV3.1.A','LV3.1','LV3.1.B','LV3.1A.T','LV3.1T','LV3.1C')";

        try{

            java.sql.PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

               String query2="insert into tube_well_detail(meter_id,feeder_id,ivrs_no,pole_no_s) values(?,?,?,?)";
               try{
               java.sql.PreparedStatement pstmt1 = connection.prepareStatement(query2);
               pstmt1.setInt(1,Integer.parseInt(rset.getString("meter_id")));
               pstmt1.setInt(2,Integer.parseInt(rset.getString("feeder_id")));
               pstmt1.setString(3,rset.getString("ivrs_no"));
                pstmt1.setString(4,rset.getString("poll_no"));
               rowsAffected = pstmt1.executeUpdate();
               count=count+rowsAffected;
                if (rowsAffected > 0) {
                message = count+" Records saved successfully.";
                 msgBgColor = COLOR_OK;
               } else {
                  message = "Cannot save the record, some error.";
                 msgBgColor = COLOR_ERROR;
                }

                }catch(Exception e){
                System.out.println("Error in inserting basic tube well records ShowMeterDataModel insertRecords() "+e);
                }
            }

        }catch(Exception e){
            System.out.println("Error in select meter data ShowMeterDataModel insertRecords() "+e);
        }




       return rowsAffected;

    }

public int insertRecord2() {

    int rowsAffected=0;
    int count=0;


        String query1=" select mail_record_id,mail_type_value,division_keyperson_id,created_date, "
                       +" created_by,mail_status_id,mail_type_id,bill_month,mail_revision_no, "
                       +" active,mail_direction,type_of_transaction,description, "
                       +" key_person_id "
                       +" from   meter_jabalpur.mail_records m "
                       +" where m.mail_record_id > 42128 ";
                       
                      
        try{

            java.sql.PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

            String query2=" insert into meter_survey.mail_records(mail_record_id,mail_type_value,division_keyperson_id,created_date, "
                    + "  created_by,mail_status_id,mail_type_id,bill_month,mail_revision_no, "
                    + " active,mail_direction,type_of_transaction,description, "
                    + " key_person_id ) "
                    +" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

               try{
               java.sql.PreparedStatement pstmt1 = connection.prepareStatement(query2);
              pstmt1.setString(1,rset.getString("mail_record_id"));
              pstmt1.setString(2,rset.getString("mail_type_value"));
              pstmt1.setString(3,rset.getString("division_keyperson_id"));
              pstmt1.setString(4,rset.getString("created_date"));
              pstmt1.setString(5,rset.getString("created_by"));
              pstmt1.setString(6,rset.getString("mail_status_id"));
              pstmt1.setString(7,rset.getString("mail_type_id"));
              pstmt1.setString(8,rset.getString("bill_month"));
              pstmt1.setString(9,rset.getString("mail_revision_no"));
              pstmt1.setString(10,rset.getString("active"));
              pstmt1.setString(11,rset.getString("mail_direction"));
             // pstmt1.setString(12,rset.getString("mail_date_time"));
              pstmt1.setString(12,rset.getString("type_of_transaction"));
              pstmt1.setString(13,rset.getString("description"));
              pstmt1.setString(14,rset.getString("key_person_id"));
            
                         
               rowsAffected = pstmt1.executeUpdate();
               count=count+rowsAffected;
                if (rowsAffected > 0) {
                message = count+" Records saved successfully.";
                 msgBgColor = COLOR_OK;
               } else {
                  message = "Cannot save the record, some error.";
                 msgBgColor = COLOR_ERROR;
                }

                }catch(Exception e){
                System.out.println("Error in inserting basic tube well records ShowMeterDataModel insertRecords() "+e);
                }
            }
            System.out.println(count+" records inserted");
        }catch(Exception e){
            System.out.println("Error in select meter data ShowMeterDataModel insertRecords() "+e);
        }
       return rowsAffected;
    }



     public int insertInToMeters() {
        int rowsAffected=0;
        int count=0;
        String query="select meter_id,revision,meter_name,security_deposit,sd_receipt_no,date,initial_reading, "
                             +" city_id,meter_service_number,poll_no,active,organisation_id,org_office_id, "
                             +" switching_point_id,feeder_id,sanctioned_load_kw,reason,final_revision,phase, "
                             +" accessed_load,effective_date,updated_date,calculated_load,description,tariff_code, "
                             +" msn_first_part,msn_sec_part,msn_third_part,msn_fourth_part,ivrs_no,file_no, "
                             +" calculated_security_deposit,meter_name_auto,sanct_load_unit_id,latitude,longitude, "
                             +" ward_no,bill_sanction_load,premises_tariff_map_id,premises_tariff_map_rev, "
                             +" address_asper_Bill,general_img_details_id "
                             +" from meter_jabalpur.meters ";

        String query1="Insert into meter_survey.meters(meter_id,revision,meter_name,security_deposit,sd_receipt_no,date,initial_reading,"
                + "city_id,meter_service_number,poll_no,active,organisation_id,org_office_id,"
                + "switching_point_id,feeder_id,sanctioned_load_kw,reason,final_revision,phase,"
                + " accessed_load,effective_date,updated_date,calculated_load,description,tariff_code,"
                + "msn_first_part,msn_sec_part,msn_third_part,msn_fourth_part,ivrs_no,file_no,"
                + "calculated_security_deposit,meter_name_auto,sanct_load_unit_id,latitude,longitude,"
                + "ward_no,bill_sanction_load,premises_tariff_map_id,premises_tariff_map_rev,"
                + "address_asper_Bill,general_img_details_id)"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

        try{
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rset = pst.executeQuery();
         while(rset.next()){
            // MeterBean mBean = new MeterBean();
             PreparedStatement pst1 = connection.prepareStatement(query1);
            // mBean.setMeter_id(rset.getString("meter_id"));
             pst1.setString(1,rset.getString("meter_id"));
             pst1.setString(2,rset.getString("revision"));
             pst1.setString(3,rset.getString("meter_name"));
             pst1.setString(4,rset.getString("security_deposit"));
             pst1.setString(5,rset.getString("sd_receipt_no"));
             pst1.setString(6,rset.getString("date"));
             pst1.setString(7,rset.getString("initial_reading"));
             pst1.setString(8,rset.getString("city_id"));
             pst1.setString(9,rset.getString("meter_service_number"));

             pst1.setString(10,rset.getString("poll_no"));
             pst1.setString(11,rset.getString("active"));
             pst1.setString(12,rset.getString("organisation_id"));
             pst1.setString(13,rset.getString("org_office_id"));
             pst1.setString(14,rset.getString("switching_point_id"));
             pst1.setString(15,rset.getString("feeder_id"));
             pst1.setString(16,rset.getString("sanctioned_load_kw"));

             pst1.setString(17,rset.getString("reason"));
             pst1.setString(18,rset.getString("final_revision"));
             pst1.setString(19,rset.getString("phase"));
             pst1.setString(20,rset.getString("accessed_load"));
             pst1.setString(21,rset.getString("effective_date"));
             pst1.setString(22,rset.getString("updated_date"));
             pst1.setString(23,rset.getString("calculated_load"));
             pst1.setString(24,rset.getString("description"));
             pst1.setString(25,rset.getString("tariff_code"));
             pst1.setString(26,rset.getString("msn_first_part"));
             pst1.setString(27,rset.getString("msn_sec_part"));
             pst1.setString(28,rset.getString("msn_third_part"));
             pst1.setString(29,rset.getString("msn_fourth_part"));
             pst1.setString(30,rset.getString("ivrs_no"));
             pst1.setString(31,rset.getString("file_no"));
             pst1.setString(32,rset.getString("calculated_security_deposit"));
             pst1.setString(33,rset.getString("meter_name_auto"));
             pst1.setString(34,rset.getString("sanct_load_unit_id"));
             pst1.setString(35,rset.getString("latitude"));
             pst1.setString(36,rset.getString("longitude"));
             pst1.setString(37,rset.getString("ward_no"));
             pst1.setString(38,rset.getString("bill_sanction_load"));
             pst1.setString(39,rset.getString("premises_tariff_map_id"));
             pst1.setString(40,rset.getString("premises_tariff_map_rev"));
             pst1.setString(41,rset.getString("address_asper_Bill"));
             pst1.setString(42,rset.getString("general_img_details_id"));

             rowsAffected=pst1.executeUpdate();
             count= count+rowsAffected;
             System.out.println("rowsAffected="+rowsAffected);


         }
         }catch(Exception e){
             System.out.println(e);
         }

       return rowsAffected;

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





}
