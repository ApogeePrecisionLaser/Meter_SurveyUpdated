/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;


import com.survey.tableClasses.Meters;
import com.survey.tableClasses.SwitchingPointSurveyBean;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.*;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Soft_Tech
 */
public class MeterModelABC1 {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
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

    public int getNoOfRows(String con, int switching_point_id, int organisation_id, int org_office_id, String active1, String zone_filter, String date_filter, String feeder_filter, String mete_ser_num_filter, String division_filter, String meter_no, String file_no, String premises_type, String switching_point_sub_type, String premises_type_details, int company_id, String circle_name) {
        int noOfRows = 0;
        String add_query = " where ";
        String searchTypeOfPremises = premises_type;
        int orgOfficeId = 0;
        if(switching_point_sub_type == null || (switching_point_sub_type.equals("0") || switching_point_sub_type.equals(""))){
            searchTypeOfPremises = premises_type;
        }else{
            if(switching_point_sub_type.indexOf('Y') > -1){
                searchTypeOfPremises = getPremisesTypeFromSubType(switching_point_sub_type);
            }else{
                orgOfficeId = getOrgOffice_idByOfficeType(switching_point_sub_type);
            }

        }
        if (premises_type_details.equals("ALL")) {
           add_query = " WHERE IF(" + orgOfficeId + "= 0 , m.org_office_id like '%%' OR m.org_office_id is null, m.org_office_id = " + orgOfficeId + " ) AND ";
        }
        else if (premises_type_details.equals("Y")) {
            add_query = " , switching_point  s where m.switching_point_id = s.switching_point_id and "
                    + " if(" + switching_point_id + "= 0 , m.switching_point_id  like '%%', s.switching_point_id =" + switching_point_id + " ) and  ";
        } 
        else
        {
            add_query = " , org_office  s where m.org_office_id = s.org_office_id and "
                   // + " if(" + org_office_id + "= 0 , m.org_office_id IS null, m.org_office_id = " + org_office_id + " ) and   ";
                  + " if(" + org_office_id + "= 0 , m.org_office_id  like '%%', s.org_office_id = " + org_office_id + " ) and   ";
        }
        try {
            String query = " select count(*) "
                    + " FROM meters as m,city c , organisation_name o, type_of_premises tp , feeder f, zone z, division d, tarrif_gen_details t, premises_tariff_map ptm, company cy, circle ci"
                    + add_query + "  m.city_id = c.city_id  and m.organisation_id= o.organisation_id and m.final_revision='VALID' and "
                    
                    + "  cy.company_id = ci.company_id and ci.circle_id = d.circle_id and "
                    + " f.zone_id = z.zone_id and z.division_id = d.division_id AND d.active = 'Y' and m.feeder_id = f.feeder_id "
                    + "and if(" + organisation_id + " = 0 , m.organisation_id  like '%%' , m.organisation_id = ? ) "
                    + " and if(  '" + active1 + "'='All',m.active like'%%'  , m.active= ? ) and "
//                    + " m.premises_tariff_map_id = ptm.premises_tariff_map_id AND m.premises_tariff_map_rev = ptm.revision and "
                    + " m.premises_tariff_map_id = ptm.premises_tariff_map_id AND ptm.active = 'Y' and "
                    + "  ptm.type_of_premises_id = tp.type_of_premises_id and "
                    + "  ptm.tarrif_gen_details_id = t.tarrif_gen_details_id and "
//                    + "  ptm.tarrif_revision = t.revision and t.active = 'Y' "
                    + "  t.active = 'Y' "
                    + " AND  if(" + company_id + "=0 , cy.company_id like '%%' , cy.company_id=" + company_id + ")"
                    + " AND  if('" + circle_name + "'='' , ci.circle_name like '%%' , ci.circle_name like '" + circle_name + "')"
                    + "  and if(  '" + division_filter + "'='',d.division_name like'%%'  , d.division_name like '" + division_filter + '%' + "' ) "
                    + "  and if(  '" + zone_filter + "'='',z.zone like'%%'  , z.zone like '" + zone_filter + '%' + "' ) "
                    + " and if(  '" + date_filter + "'='',DATE_FORMAT(m.date, '%d-%m-%Y') like'%%'  , DATE_FORMAT(m.date, '%d-%m-%Y') like '" + date_filter + '%' + "' )"
                    + " and if(  '" + feeder_filter + "'='',f.feeder_name like'%%'  , f.feeder_name like '" + feeder_filter + '%' + "'  ) "
                    + " and if(  '" + mete_ser_num_filter + "'='',m.meter_service_number like'%%'  , m.meter_service_number like '" + mete_ser_num_filter + '%' + "' ) "
                    + " and if(  '" + meter_no + "'='',m.meter_name like'%%'  , m.meter_name like '" + meter_no + '%' + "'  ) "
                    + " AND IF ('" + file_no + "'='' ,file_no LIKE '%%' ,file_no = '" + file_no + "') "
                    + " AND IF ('" + searchTypeOfPremises + "'='ALL' ,tp.type_of_premsis LIKE '%%' ,tp.type_of_premsis = '" + searchTypeOfPremises + "') ";
                    
            /*  String query = "select count(*) from meters as m,city c , organisation_name o, type_of_premises tp , feeder f, zone z, division d  "
            + add_query + "  m.city_id = c.city_id  and m.organisation_id= o.organisation_id and m.final_revision='VALID' and f.zone_id = z.zone_id and z.division_id = d.division_id and m.feeder_id = f.feeder_id "
            + "and if(" + organisation_id + " = 0 , m.organisation_id  like '%%' , m.organisation_id = ? ) "
            + " and if(  '" + active1 + "'='All',m.active like'%%'  , m.active= ? ) "
            + " and m.type_of_premises_id= tp.type_of_premises_id   "
            + " and m.feeder_id= f.feeder_id "
            + "  and if(  '" + division_filter + "'='',d.division_name like'%%'  , d.division_name like '" + division_filter + '%' + "' ) "
            + "  and if(  '" + zone_filter + "'='',z.zone like'%%'  , z.zone like '" + zone_filter + '%' + "' ) "
            + " and if(  '" + date_filter + "'='',DATE_FORMAT(m.date, '%d-%m-%Y') like'%%'  , DATE_FORMAT(m.date, '%d-%m-%Y') like '" + date_filter + '%' + "' )"
            + " and if(  '" + feeder_filter + "'='',f.feeder_name like'%%'  , f.feeder_name like '" + feeder_filter + '%' + "'  ) "
            + " and if(  '" + meter_no + "'='',m.meter_name like'%%'  , m.meter_name like '" + meter_no + '%' + "'  ) "
            + " and if(  '" + mete_ser_num_filter + "'='',m.meter_service_number like'%%'  , m.meter_service_number like '" + mete_ser_num_filter + '%' + "' ) "
            + " AND IF ('" + file_no + "'='' ,file_no LIKE '%%' ,file_no = '" + file_no + "') "
            + " AND IF ('" + premises_type + "'='ALL' ,tp.type_of_premsis LIKE '%%' ,tp.type_of_premsis = '" + premises_type + "') ";   */
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setInt(1, organisation_id);
            psmt.setString(2, active1);
            ResultSet rset = psmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error:meterModel getNoOfRows" + e);
        }
        return noOfRows;
    }

    public List showData(int lowerLimit, int noOfRowsToDisplay, String con, int switching_point_id, int organisation_id, int org_office_id, String active1, String zone_filter, String date_filter, String feeder_filter, String mete_ser_num_filter, String division_filter, String meter_no, String file_no, String premises_type, String switching_point_sub_type, String premises_type_details, int company_id, String circle_name,List SurveyTypeList,int countnum,String meter_name_auto,String ivrs_no) {
        List list = new ArrayList();
        
        String query;
        String add_query = " where ";
        String searchTypeOfPremises = premises_type;
        int orgOfficeId = 0;
        if(switching_point_sub_type == null || (switching_point_sub_type.equals("0") || switching_point_sub_type.equals(""))){
            searchTypeOfPremises = premises_type;
        }else{
            if(switching_point_sub_type.indexOf('Y') > -1)
            {
                searchTypeOfPremises = getPremisesTypeFromSubType(switching_point_sub_type);
            }else{
                orgOfficeId = getOrgOffice_idByOfficeType(switching_point_sub_type);
            }
            
        }
        if (premises_type_details.equals("ALL"))
        {
            add_query = " WHERE IF(" + orgOfficeId + "= 0 , m.org_office_id like '%%' OR m.org_office_id is null, m.org_office_id = " + orgOfficeId + " ) AND ";
        } else if (premises_type_details.equals("Y")) {
            add_query = " , switching_point  s where m.switching_point_id = s.switching_point_id and "
                    + " if(" + switching_point_id + "= 0 , m.switching_point_id  like '%%', s.switching_point_id =" + switching_point_id + " ) and  ";
        } else {
            add_query = " , org_office  s where m.org_office_id = s.org_office_id and "
                   // + " if(" + org_office_id + "= 0 , m.org_office_id IS null, s.org_office_id = " + org_office_id + " ) and   ";
                    + " if(" + org_office_id + "= 0 , m.org_office_id  like '%%', s.org_office_id = " + org_office_id + " ) and   ";
        }
        try {
            PreparedStatement psmt;
            
            query = " select cy.company_id,ci.circle_id,cy.company_name,ci.circle_name, m.meter_id, msn_first_part, msn_sec_part, msn_third_part , msn_fourth_part  , premises_individual_detail, ivrs_no, file_no, t.tarrif_no, m.calculated_load, "
                    + " m.accessed_load,if(latitude is null,'',latitude) AS latitude ,if(longitude is null,'',longitude) AS longitude ,if(ward_no is null,'',ward_no) AS ward_no , "
                    + " DATE_FORMAT(m.effective_date, '%d-%m-%Y') AS effective_date, z.zone, f.feeder_name, m.revision ,m.meter_service_number , m.poll_no "
                    + " , d.division_name , m.active, m.org_office_id , m.meter_name, m.security_deposit, m.switching_point_id , f.feeder_name , "
                    + "m.sd_receipt_no, DATE_FORMAT(m.date, '%d-%m-%Y') AS date, m.initial_reading, m.phase, m.sanctioned_load_kw, o.organisation_name ,"
                    + " tp.type_of_premsis,tp.premises_individual_detail,"
                    + " c.city_name , m.reason, m.description, m.meter_name_auto, m.bill_sanction_load  "
                    + " FROM meters as m,city c , organisation_name o, type_of_premises tp , feeder f, zone z, division d, tarrif_gen_details t, premises_tariff_map ptm, company cy, circle ci "
                    + add_query + "  m.city_id = c.city_id  and m.organisation_id= o.organisation_id and m.final_revision='VALID' and "
                    
                    + "  cy.company_id = ci.company_id and ci.circle_id = d.circle_id and "
                    + " f.zone_id = z.zone_id and z.division_id = d.division_id AND d.active = 'Y' and m.feeder_id = f.feeder_id "
                    + "and if(" + organisation_id + " = 0 , m.organisation_id  like '%%' , m.organisation_id = ? ) "
                    + " and if(  '" + active1 + "'='All',m.active like'%%'  , m.active= ? ) and "
//                    + " m.premises_tariff_map_id = ptm.premises_tariff_map_id AND m.premises_tariff_map_rev = ptm.revision  and ptm.active='Y' and  "
                    + " m.premises_tariff_map_id = ptm.premises_tariff_map_id AND ptm.active='Y' and  "
                    + "  ptm.type_of_premises_id = tp.type_of_premises_id and "
                    + "  ptm.tarrif_gen_details_id = t.tarrif_gen_details_id and "
//                    + "  ptm.tarrif_revision = t.revision and t.active = 'Y' "
                    + "  t.active = 'Y' "
                    + " AND  if(" + company_id + "=0 , cy.company_id like '%%' , cy.company_id=" + company_id + ")"
                    + " AND  if('" + circle_name + "'='' , ci.circle_name like '%%' , ci.circle_name like '" + circle_name + "')"
                    + "  and if(  '" + division_filter + "'='',d.division_name like'%%'  , d.division_name like '" + division_filter + '%' + "' ) "
                    + "  and if(  '" + zone_filter + "'='',z.zone like'%%'  , z.zone like '" + zone_filter + '%' + "' ) "
                    + " and if(  '" + date_filter + "'='',DATE_FORMAT(m.date, '%d-%m-%Y') like'%%'  , DATE_FORMAT(m.date, '%d-%m-%Y') like '" + date_filter + '%' + "' )"
                    + " and if(  '" + feeder_filter + "'='',f.feeder_name like'%%'  , f.feeder_name like '" + feeder_filter + '%' + "'  ) "
                    + " and if(  '" + mete_ser_num_filter + "'='',m.meter_service_number like'%%'  , m.meter_service_number like '" + mete_ser_num_filter + '%' + "' ) "
                    + " and if(  '" + meter_no + "'='',m.meter_name like'%%'  , m.meter_name like '" + meter_no + '%' + "'  ) "
                    + " AND IF ('" + file_no + "'='' ,file_no LIKE '%%' ,file_no = '" + file_no + "') "
                    + " AND IF ('" + searchTypeOfPremises + "'='ALL' ,tp.type_of_premsis LIKE '%%' ,tp.type_of_premsis = '" + searchTypeOfPremises + "') "
                    + " AND IF ('" + meter_name_auto + "'='' ,m.meter_name_auto LIKE '%%' ,m.meter_name_auto = '" + meter_name_auto + "') "
                    + " AND IF ('" + ivrs_no + "'='' ,m.ivrs_no LIKE '%%' ,m.ivrs_no = '" + ivrs_no + "') "
                    //+ " AND IF(" + orgOfficeId + "= 0 , m.org_office_id IS null, m.org_office_id = " + orgOfficeId + " )"
                    + " ORDER BY m.meter_name LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            psmt = connection.prepareStatement(query);
            psmt.setInt(1, organisation_id);
            psmt.setString(2, active1);
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {
                if(countnum==1)
                {
                    Iterator itr=SurveyTypeList.iterator();
                    SwitchingPointSurveyBean meter = new SwitchingPointSurveyBean();
                    if(itr.hasNext())
                    {
                    meter=(SwitchingPointSurveyBean)itr.next();
                    meter.setLattitude(rset.getDouble("latitude"));
                    meter.setLongitude(rset.getDouble("longitude"));
                    meter.setWard_no(rset.getString("ward_no"));
                    meter.setPhase_no(rset.getString("phase"));
                    meter.setCity(rset.getString("city_name"));
                    meter.setZone(rset.getString("zone"));
                    meter.setDivision(rset.getString("division_name"));
                    meter.setFeeder(rset.getString("feeder_name"));
                    list.add(meter);
                    }
                }
                else
                {
                Meters meter = new Meters();
                meter.setDescription(rset.getString("description"));
                meter.setTariff_code(rset.getString("tarrif_no"));
                meter.setCalculated_load(rset.getDouble("calculated_load"));
                meter.setDivision_name(rset.getString("division_name"));
                meter.setZone(rset.getString("zone"));
                meter.setAccessed_load_kw(Double.parseDouble(rset.getString("accessed_load")));
                meter.setEffective_date(rset.getString("effective_date"));
                meter.setFeeder(rset.getString("feeder_name"));
                meter.setMeter_id(rset.getInt("meter_id"));
                meter.setMeter_service_num(rset.getString("meter_service_number"));
                meter.setPoll_num(rset.getString("poll_no"));
                meter.setMeter_name(rset.getString("meter_name"));
                meter.setSecurity_deposit(rset.getDouble("security_deposit"));
                meter.setSd_receipt_no(rset.getString("sd_receipt_no"));
                String strDate = rset.getDate("date").toString();
//                String[] str = strDate.split("-");
//                strDate = str[2] + "-" + str[1] + "-" + str[0];
                meter.setDate(rset.getString("date"));
                meter.setInitial_reading(rset.getInt("initial_reading"));
                meter.setSanctioned_load_kw(rset.getDouble("bill_sanction_load"));
                //meter.setBill_sanction_load(rset.getDouble("bill_sanction_load"));
                meter.setCity_name(rset.getString("city_name"));
                meter.setOrganisation(rset.getString("organisation_name"));
                String type_of_premises = rset.getString("premises_individual_detail");
                meter.setType_of_premises(rset.getString("type_of_premsis"));
                meter.setPremises_individual_detail(rset.getString("premises_individual_detail"));
                meter.setFeeder_name(rset.getString("feeder_name"));
                meter.setReason(rset.getString("reason"));
                meter.setRevision(rset.getInt("revision"));
                meter.setPhase(rset.getString("phase"));
                meter.setIvrs_no(rset.getString("ivrs_no"));
                meter.setFile_no(rset.getString("file_no"));
                meter.setMeter_name_auto(rset.getString("meter_name_auto"));
                meter.setMsn_part1(rset.getString("msn_first_part"));
                meter.setMsn_part2(rset.getString("msn_sec_part"));
                meter.setMsn_part3(rset.getString("msn_third_part"));
                meter.setMsn_part4(rset.getString("msn_fourth_part"));
                //meter.setLoad_unit(rset.getString("sacntion_load_unit"));
                meter.setLatitude(rset.getString("latitude"));
                meter.setLongitude(rset.getString("longitude"));
                meter.setWard_no(rset.getString("ward_no"));

                meter.setCompany_name(rset.getString("company_name"));
                meter.setCircle_name(rset.getString("circle_name"));

                if (type_of_premises.equals("Y")) {
                    meter.setSwitching_point(getSwitcing_pointName(rset.getInt("switching_point_id")));
                } else {
                    meter.setOrg_office(getorg_office_name(rset.getInt("org_office_id")));
                    meter.setOffice_type(getOffice_type(rset.getInt("org_office_id")));
                }
                String active = rset.getString("active");
                if (active.equalsIgnoreCase("y")) {
                    meter.setActive("yes");
                } else {
                    meter.setActive("No");
                }
                list.add(meter);
            }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public String getOffice_type(int org_office_id) {
        String office_type = null;
        try {
            String query = " select office_type from  org_office_type "
                    + "where office_type_id =( select office_type_id from "
                    + "org_office   where  org_office_id = " + org_office_id + ")";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            if (rst.next()) {
                office_type = rst.getString("office_type");
            }
        } catch (Exception e) {
            System.out.println("Error in meter  Controller" + e);
        }
        return office_type;
    }

    public Map<Integer, String> getTarrifDetails() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        String query = " SELECT tarrif_gen_details_id, tarrif_no "
                + " FROM tarrif_gen_details WHERE active = 'Y' ";
        map.put(0, "Tarrif code");
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                map.put(rset.getInt("tarrif_gen_details_id"), rset.getString("tarrif_no"));
            }
        } catch (Exception e) {
            System.out.println("Error in meterModel getTarrifDetails()" + e);
        }
        return map;
    }

    public int getmeter_id(String service_connection) {
        int count = 0, meter_id = 0;
        try {
            String query = " Select count(*) from meters where meter_service_number =?  limit 1 ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, service_connection);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                count = rst.getInt("count(*)");
            }
            psmt.close();
            rst.close();
            if (count == 0) {
                String query1 = "select meter_id from meters order by meter_id  desc limit 1 ";
                psmt = connection.prepareStatement(query1);
                rst = psmt.executeQuery();
                if (rst.next()) {
                    meter_id = rst.getInt("meter_id");
                }
                meter_id = meter_id + 1;

            }
        } catch (Exception e) {
            System.out.println("Error in meterModel " + e);
        }
        return meter_id;

    }

    public double getCalculatedLoad(int meter_id, int revison) {
        double calculated_load = 0;
        try {
            String query = " select calculated_load  from meters where  meter_id =? and revision =? ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, meter_id);
            pstmt.setInt(2, revison);
            ResultSet rst = pstmt.executeQuery();
            if (rst.next()) {
                calculated_load = rst.getDouble("calculated_load");
            }
        } catch (Exception e) {
            System.out.println("Error in getCalculatedLoad" + e);
        }
        return calculated_load;
    }

    public int insertRecord(Meters meter, String task) {
        int rowsAffected = 0;
        String query = null;
        String type_value;
        boolean autoCommit = false;
        try {
            autoCommit = connection.getAutoCommit();
            PreparedStatement pstmt = null;
            connection.setAutoCommit(false);
            if (task.equals("Revise")) {
                String update_query = "update meters set final_revision ='EXPIRED', active ='N' where meter_id= ? ";
                pstmt = connection.prepareStatement(update_query);
                pstmt.setInt(1, meter.getMeter_id());
                rowsAffected = pstmt.executeUpdate();
            } else {
                rowsAffected = 1;
            }
            if (rowsAffected > 0) {
                if (task.equals("Revise")) {
                    pstmt.close();
                }

                if (meter.getType_of_premises().equals("Y") || meter.getType_of_premises().equals("Tube well")) {
                    type_value = "switching_point_id";
                } else {
                    type_value = "org_office_id";
                }

                /*   query = "INSERT INTO "
                + " meters(meter_name , meter_service_number, poll_no , security_deposit , sd_receipt_no , date ,  "
                + " initial_reading , sanctioned_load_kw ,city_id, active , "
                + "organisation_id , type_of_premises_id , feeder_id ,   revision , reason , phase , meter_id, "
                + " accessed_load, effective_date ," + type_value + ", description, tarrif_gen_details_id, "
                + " msn_first_part, msn_sec_part, msn_third_part, msn_fourth_part, ivrs_no, file_no, meter_name_auto , sanct_load_unit,latitude,longitude,ward_no )"
                //+ "  calculated_load ) "
                + " VALUES(?, ?,?,? ,?, ?, ?, ?, ?, ?,?,? ,? , ? ,? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,?)"; */
                query = "INSERT INTO "
                        + " meters(meter_name , meter_service_number, poll_no , security_deposit , sd_receipt_no , date ,  "
                        + " initial_reading , sanctioned_load_kw ,city_id, active , "
                        + "organisation_id , feeder_id ,   revision , reason , phase , meter_id, "
                        + " accessed_load, effective_date ," + type_value + ", description, premises_tariff_map_id, "
                        + " msn_first_part, msn_sec_part, msn_third_part, msn_fourth_part, ivrs_no, file_no, meter_name_auto , sanct_load_unit_id,latitude,longitude,ward_no ,premises_tariff_map_rev, bill_sanction_load)"
                        //+ "  calculated_load ) "
                        + " VALUES(?, ?,?,? ,?, ?, ?, ?, ?, ?,?,? ,? , ? ,? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,?,?,?)";
                pstmt = connection.prepareStatement(query);
                pstmt.setString(1, meter.getMeter_name());
                pstmt.setString(2, meter.getMeter_service_num());
                String meter_service_num = meter.getMeter_service_num();
                String[] split = meter_service_num.split("-", meter_service_num.length());
                pstmt.setString(3, meter.getPoll_num());
                pstmt.setDouble(4, meter.getSecurity_deposit());
                pstmt.setString(5, meter.getSd_receipt_no());
                String strD = meter.getDate();
                String[] str1 = strD.split("-");
                strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
                // Format: mm/dd/yy
                java.sql.Date date = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
                pstmt.setDate(6, date);
                pstmt.setDouble(7, meter.getInitial_reading());
                pstmt.setDouble(8, meter.getSanctioned_load_kw());
                pstmt.setInt(9, meter.getCity_id());
                pstmt.setString(10, meter.getActive());
                pstmt.setInt(11, meter.getOrganisation_id());
                //    pstmt.setInt(12, meter.getType_of_premises_id());
                pstmt.setInt(12, meter.getFeeder_id());
                pstmt.setInt(13, meter.getRevision());
                pstmt.setString(14, meter.getReason());
                pstmt.setString(15, meter.getPhase());
                pstmt.setInt(16, meter.getMeter_id());
                pstmt.setDouble(17, meter.getAccessed_load_kw());
                String effective_date = meter.getEffective_date();
                String[] str2 = effective_date.split("-");
                effective_date = str2[1] + "/" + str2[0] + "/" + str2[2]; // Format: mm/dd/yy
                pstmt.setDate(18, new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(effective_date).getTime()));
                if (type_value.equals("switching_point_id")) {
                    pstmt.setInt(19, meter.getSwitching_point_id());
                } else {
                    pstmt.setInt(19, meter.getOrg_office_id());
                }
                pstmt.setString(20, meter.getDescription());
                pstmt.setInt(21, meter.getPremises_tariff_map_id());
                String ivrs_no = meter.getMeter_service_num();
//                if (split.length == 1) {
//                    ivrs_no = split[0];
//                    pstmt.setString(23, split[0]);
//                    pstmt.setString(24, "");
//                    pstmt.setString(25, "");
//                    pstmt.setString(26, "");
//                }
//                if (split.length == 2) {
//                    ivrs_no = split[0];
//                    pstmt.setString(23, split[0]);
//                    pstmt.setString(24, split[1]);
//                    pstmt.setString(25, "");
//                    pstmt.setString(26, "");
//                }
//                if (split.length == 3) {
//                    ivrs_no = split[0];
//                    pstmt.setString(23, split[0]);
//                    pstmt.setString(24, split[1]);
//                    pstmt.setString(25, split[2]);
//                    pstmt.setString(26, "");
//                }
//                if (split.length == 4) {
//                    ivrs_no = split[0] + split[3];
//                    pstmt.setString(23, split[0]);
//                    pstmt.setString(24, split[1]);
//                    pstmt.setString(25, split[2]);
//                    pstmt.setString(26, split[3]);
//                }
                pstmt.setString(22, meter.getMsn_part1());
                pstmt.setString(23, meter.getMsn_part2());
                pstmt.setString(24, meter.getMsn_part3());
                pstmt.setString(25, meter.getMsn_part4());
                pstmt.setString(26, ivrs_no);
                pstmt.setString(27, meter.getFile_no());
                pstmt.setString(28, meter.getMeter_name_auto());
                pstmt.setInt(29, Integer.parseInt(meter.getLoad_unit()));
                pstmt.setString(30, meter.getLatitude());
                pstmt.setString(31, meter.getLongitude());
                pstmt.setString(32, meter.getWard_no());
                pstmt.setInt(33, meter.getPremises_tariff_map_rev());
                pstmt.setDouble(34, meter.getBill_sanction_load());
                rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    if (task.equals("Revise")) {
                        connection.commit();
                    } else {
                        int meterId = getMeterId(meter);
                        boolean isInsertedIntoMeterBillStatus = insertIntoMeterBillStatus(meterId, date);
                        if (isInsertedIntoMeterBillStatus == true) {
                            connection.commit();
                        } else {
                            message = "Could not save the record.";
                            msgBgColor = COLOR_ERROR;
                            throw new Exception("Could NOT save Data into METERBILLSTATUS properly");
                        }
                    }
                    if (task.equals("Revise")) {
                        message = "Connection Revised successfully.";
                    } else {
                        message = "Record saved successfully.";
                    }
                    msgBgColor = COLOR_OK;
                } else {
                    message = "Could not save the record.";
                    msgBgColor = COLOR_ERROR;
                    throw new Exception("Could NOT save  Data properly");
                }
            } else {
                message = "Could not save the record.";
                msgBgColor = COLOR_ERROR;
                throw new Exception("Could NOT save  Data properly");
            }
        } catch (Exception e) {
            System.out.println("Error: meter model insertRecord " + e);
        }
        try {
            connection.setAutoCommit(autoCommit);
        } catch (Exception e) {
        }

        return rowsAffected;
    }

    public boolean insertIntoMeterBillStatus(int meterId, java.sql.Date date) {
        boolean isInserted = false;
        String query = " INSERT INTO meter_bill_status(meter_id, date_from, date_to) VALUES(?, ?, ?) ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, meterId);
            pstmt.setDate(2, date);
            pstmt.setDate(3, date);
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                isInserted = true;
            }
        } catch (Exception e) {
            System.out.println("insertIntoMeterBillStatus() ERROR - " + e);
        }
        return isInserted;
    }

    public int getMeterId(Meters meter) {
        int meterId = 0;
        String query = " SELECT meter_id FROM meters WHERE meter_service_number = ? ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, meter.getMeter_service_num());
            rset = pstmt.executeQuery();
            if (rset.next()) {
                meterId = rset.getInt("meter_id");
            }
        } catch (Exception e) {
            System.out.println("getMeterId() ERROR - " + e);
        }
        return meterId;
    }

    public int updateRecord(Meters meter) {
        String query = null;
        int rowsAffected = 0;
        int place = 17;
        String type_value;
        String type_change;

        if (meter.getType_of_premises().equals("Y")) {
            type_value = "switching_point_id";
            type_change = "org_office_id";

        } else {
            type_value = "org_office_id";
            type_change = "switching_point_id";
        }

        /*     query = " UPDATE meters  "
        + " SET meter_name = ?,meter_service_number =? ,poll_no =? , security_deposit= ?,"
        + " sd_receipt_no= ?, date= ?, initial_reading =?, sanctioned_load_kw= ?, "
        + "  city_id = ?, active =? , organisation_id =? , phase = ?, "
        + " type_of_premises_id = ? , feeder_id =? , accessed_load = ?, effective_date= ?, "
        + " tarrif_gen_details_id = ?, description = ?, " + type_value + " = ?," + type_change + " =null, "
        + " msn_first_part = ?, msn_sec_part = ?, msn_third_part =?, msn_fourth_part = ?, ivrs_no = ?, file_no = ?, meter_name_auto= ? , sanct_load_unit =?"
        + " ,latitude= ?,longitude = ?,ward_no= ? "
        + " WHERE meter_id = ? and revision = ? ";  */
        query = " UPDATE meters  "
                + " SET meter_name = ?,meter_service_number =? ,poll_no =? , security_deposit= ?,"
                + " sd_receipt_no= ?, date= ?, initial_reading =?, sanctioned_load_kw= ?, "
                + " city_id = ?, active =? , organisation_id =? , phase = ?, "
                + " feeder_id =? , accessed_load = ?, effective_date= ?, "
                + " premises_tariff_map_id = ?, description = ?, " + type_value + " = ?," + type_change + " =null, "
                + " msn_first_part = ?, msn_sec_part = ?, msn_third_part =?, msn_fourth_part = ?, ivrs_no = ?, file_no = ?, meter_name_auto= ? , sanct_load_unit_id =?"
                + " ,latitude= ?,longitude = ?,ward_no= ?, bill_sanction_load=? "
                + " WHERE meter_id = ? and revision = ? ";
        //  place = 15;

        try {

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, meter.getMeter_name());
            pstmt.setString(2, meter.getMeter_service_num());
            String meter_service_num = meter.getMeter_service_num();
            String[] split = meter_service_num.split("-", meter_service_num.length());
            pstmt.setString(3, meter.getPoll_num());
            pstmt.setDouble(4, meter.getSecurity_deposit());
            pstmt.setString(5, meter.getSd_receipt_no());
            String strD = meter.getDate();
            String[] str1 = strD.split("-");
            strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
            pstmt.setDate(6, new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime()));
            pstmt.setDouble(7, meter.getInitial_reading());
            pstmt.setDouble(8, meter.getSanctioned_load_kw());
            pstmt.setInt(9, meter.getCity_id());
            pstmt.setString(10, meter.getActive());
            pstmt.setInt(11, meter.getOrganisation_id());
            pstmt.setString(12, meter.getPhase());
            //    pstmt.setInt(13, meter.getType_of_premises_id());
            pstmt.setInt(13, meter.getFeeder_id());
            pstmt.setDouble(14, meter.getAccessed_load_kw());
            String effective_date = meter.getEffective_date();
            String[] str2 = effective_date.split("-");
            effective_date = str2[1] + "/" + str2[0] + "/" + str2[2]; // Format: mm/dd/yy
            pstmt.setDate(15, new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(effective_date).getTime()));
            pstmt.setInt(16, meter.getPremises_tariff_map_id());
            pstmt.setString(17, meter.getDescription());
            if (type_value.equals("switching_point_id")) {
                pstmt.setInt(18, meter.getSwitching_point_id());
            } else {
                pstmt.setInt(18, meter.getOrg_office_id());
            }
            String ivrs_no = meter.getMeter_service_num();
//            if (split.length == 1) {
//                ivrs_no = split[0];
//                pstmt.setString(20, split[0]);
//                pstmt.setString(21, "");
//                pstmt.setString(22, "");
//                pstmt.setString(23, "");
//            }
//            if (split.length == 2) {
//                ivrs_no = split[0];
//                pstmt.setString(20, split[0]);
//                pstmt.setString(21, split[1]);
//                pstmt.setString(22, "");
//                pstmt.setString(23, "");
//            }
//            if (split.length == 3) {
//                ivrs_no = split[0];
//                pstmt.setString(20, split[0]);
//                pstmt.setString(21, split[1]);
//                pstmt.setString(22, split[2]);
//                pstmt.setString(23, "");
//            }
//            if (split.length == 4) {
//                ivrs_no = split[0] + split[3];
//                pstmt.setString(20, split[0]);
//                pstmt.setString(21, split[1]);
//                pstmt.setString(22, split[2]);
//                pstmt.setString(23, split[3]);
//            }
            pstmt.setString(19, meter.getMsn_part1());
            pstmt.setString(20, meter.getMsn_part2());
            pstmt.setString(21, meter.getMsn_part3());
            pstmt.setString(22, meter.getMsn_part4());
            pstmt.setString(23, ivrs_no);
            pstmt.setString(24, meter.getFile_no());
            pstmt.setString(25, meter.getMeter_name_auto());
            pstmt.setInt(26, Integer.parseInt(meter.getLoad_unit()));
            pstmt.setString(27, meter.getLatitude());
            pstmt.setString(28, meter.getLongitude());
            pstmt.setString(29, meter.getWard_no());
            pstmt.setDouble(30, meter.getBill_sanction_load());
            pstmt.setInt(31, meter.getMeter_id());
            pstmt.setInt(32, meter.getRevision());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: metermodel  updateRecord" + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int getrevision_no(int meter_id) {
        int revision_no = 0;
        try {
            String query = " select revision from meters where meter_id= ? order by revision desc limit 1  ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setInt(1, meter_id);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                revision_no = rst.getInt("revision");
            }
            revision_no = revision_no + 1;

        } catch (Exception e) {
            System.out.println("Error MeterBill Controller getrevision_no" + e);
        }
        return revision_no;
    }

    public byte[] generateMeterList(String jrxmlFilePath, int organisation_id, String con, String status, String conn_type, String zone_filter1, String date_filter1, String feeder_filter, String mete_ser_num_filter, String division_filter, String file_no, String conn_value, String company_name, String circle_name) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        int org_office_id = 0, switching_point_id = 0;
        String division_param = "", zone_param = "", feeder_param = "";
        try {

            if (conn_value.equals("Y")) {
                if (conn_type != null && !conn_type.isEmpty()) {
                    int[] site = getSite_id(conn_type);
                    switching_point_id = site[0];
                }
            } else {
                if (conn_type != null && !conn_type.isEmpty()) {
                    org_office_id = getOrgOffice_id(organisation_id, conn_type);
                }
            }
            mymap.put("organisation_id", organisation_id);
            if (status.equals("Active")) {
                status = "Y";
            }
            if (status.equals("InActive")) {
                status = "N";
            }
            if (status.equals("All")) {
                status = "";
            }
            if (division_filter.equals("")) {
                division_param = "ALL";
            } else {
                division_param = division_filter;
            }
            if (zone_filter1.equals("")) {
                zone_param = "ALL";
            } else {
                zone_param = zone_filter1;
            }
            if (feeder_filter.equals("")) {
                feeder_param = "ALL";
            } else {
                feeder_param = feeder_filter;
            }
            mymap.put("status", status);
            mymap.put("switching_point_id", switching_point_id);
            mymap.put("org_office_id", org_office_id);
            mymap.put("con_type", con);
            mymap.put("zone", zone_filter1);
            mymap.put("date", date_filter1);
            mymap.put("feeder", feeder_filter);
            mymap.put("service_conn", mete_ser_num_filter);
            mymap.put("division", division_filter);
            mymap.put("division_param", division_param);
            mymap.put("zone_param", zone_param);
            mymap.put("feeder_param", feeder_param);
            mymap.put("file_no", file_no);
            mymap.put("conn_value", conn_value);
            mymap.put("company_name", company_name);
            mymap.put("circle_name", circle_name);

            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, connection);
        } catch (Exception e) {
            System.out.println("MeterModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public byte[] generateIncorrectMeterList(String jrxmlFilePath, int organisation_id, String con, String status, String conn_type, String zone_filter1, String date_filter1, String feeder_filter, String mete_ser_num_filter, String division_filter, String company_name, String circle_name) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        int org_office_id = 0, switching_point_id = 0;
        String division_param = "", zone_param = "", feeder_param = "";
        String query = "";
        String add_query = " where ";
        try {
            if (conn_type.equals("ALL")) {
            } else if (con.equals("Y")) {
                if (conn_type != null && !conn_type.isEmpty()) {
                    int[] site = getSite_id(conn_type);
                    switching_point_id = site[0];
                    add_query = " , switching_point  s where m.switching_point_id = s.switching_point_id and "
                            + " if(" + switching_point_id + "= 0 , m.switching_point_id  like '%%', s.switching_point_id =" + switching_point_id + " ) and  ";
                }
            } else {
                if (conn_type != null && !conn_type.isEmpty()) {
                    org_office_id = getOrgOffice_id(organisation_id, conn_type);
                    add_query = " , org_office  s where m.org_office_id = s.org_office_id and "
                            + " if(" + org_office_id + "= 0 , m.org_office_id  like '%%', s.org_office_id = " + org_office_id + " ) and   ";
                }
            }
            mymap.put("organisation_id", organisation_id);
            if (status.equals("Active")) {
                status = "Y";
            }
            if (status.equals("InActive")) {
                status = "N";
            }
            if (division_filter.equals("")) {
                division_param = "ALL";
            } else {
                division_param = division_filter;
            }
            if (zone_filter1.equals("")) {
                zone_param = "ALL";
            } else {
                zone_param = zone_filter1;
            }
            if (feeder_filter.equals("")) {
                feeder_param = "ALL";
            } else {
                feeder_param = feeder_filter;
            }
            query = /*" select  d.division_name, z.zone, f.feeder_name, m.meter_service_number, feeder_no, "
                    + " m.meter_name , tp.type_of_premsis "
                    + " from meters as m,city c , organisation_name o, type_of_premises tp , feeder f , zone z, division d "
                    + add_query + " m.city_id = c.city_id  and m.organisation_id= o.organisation_id AND f.zone_id = z.zone_id "
                    + " and z.division_id = d.division_id "
                    + " and if( " + organisation_id + "  = 0 , m.organisation_id  like '%%' , m.organisation_id = " + organisation_id + " ) "
                    + " and if(  '" + status + "'='',m.active like'%%'  , m.active= '" + status + "' ) "
                    + " and if( '" + division_filter + "' ='',d.division_name like'%%'  , d.division_name = '" + division_filter + "' ) "
                    + " and if( '" + zone_filter1 + "' ='',z.zone like'%%'  , z.zone like '" + zone_filter1 + "' ) "
                    + " and if( '" + date_filter1 + "' ='',DATE_FORMAT(m.date, '%d-%m-%Y') like'%%'  , DATE_FORMAT(m.date,  '%d-%m-%Y') like '" + date_filter1 + "') "
                    + " and if( '" + feeder_filter + "' ='',f.feeder_name like'%%'  , f.feeder_name like '" + feeder_filter + "' ) "
                    + " and if( '" + mete_ser_num_filter + "' ='',m.meter_service_number like'%%'  , m.meter_service_number like '" + mete_ser_num_filter + "' ) "
                    + " AND IF ('" + con + "'='ALL' ,tp.type_of_premsis LIKE '%%' ,tp.type_of_premsis = '" + con + "') "
                    + " and m.type_of_premises_id= tp.type_of_premises_id "
                    + " and SUBSTRING(SUBSTRING_INDEX(meter_service_number, '-', 1), 6, 7) != feeder_no and m.feeder_id= f.feeder_id "
                    + " GROUP BY meter_service_number ORDER BY f.feeder_name, meter_service_number ";  */
                    "  select cy.company_id,ci.circle_id,cy.company_name,ci.circle_name, slu.sacntion_load_unit , m.meter_id, msn_first_part, msn_sec_part, msn_third_part , msn_fourth_part  , premises_individual_detail, ivrs_no, file_no, t.tarrif_no, m.calculated_load,feeder_no, "
                    + " m.accessed_load,if(latitude is null,'',latitude) AS latitude ,if(longitude is null,'',longitude) AS longitude ,if(ward_no is null,'',ward_no) AS ward_no , "
                    + " DATE_FORMAT(m.effective_date, '%d-%m-%Y') AS effective_date, z.zone, f.feeder_name, m.revision ,m.meter_service_number , m.poll_no "
                    + " , d.division_name , m.active, m.org_office_id , m.meter_name, m.security_deposit, m.switching_point_id , f.feeder_name , "
                    + "m.sd_receipt_no, DATE_FORMAT(m.date, '%d-%m-%Y') AS date, m.initial_reading, m.phase, m.sanctioned_load_kw, o.organisation_name ,"
                    + " tp.type_of_premsis,tp.premises_individual_detail,"
                    + " c.city_name , m.reason, m.description, m.meter_name_auto, m.bill_sanction_load "
                    + " FROM meters as m,city c , organisation_name o, type_of_premises tp , feeder f, zone z, division d, tarrif_gen_details t, premises_tariff_map ptm, company cy, circle ci, sanct_load_unit slu  "
                    + add_query + "  m.city_id = c.city_id  and m.organisation_id= o.organisation_id  and "
                    + " slu.sanct_load_unit_id=m.sanct_load_unit_id AND slu.active='Y' AND "
                    + "  cy.company_id = ci.company_id and ci.circle_id = d.circle_id and "
                    + " f.zone_id = z.zone_id and z.division_id = d.division_id AND d.active = 'Y' and m.feeder_id = f.feeder_id "
                    + "and if(" + organisation_id + " = 0 , m.organisation_id  like '%%' , m.organisation_id = " + organisation_id + " ) "
                    + " and if(  '" + status + "'='All',m.active like'%%'  , m.active= '" + status + "') and "
//                    + " m.premises_tariff_map_id = ptm.premises_tariff_map_id AND m.premises_tariff_map_rev = ptm.revision and ptm.active='Y' and  "
                    + " m.premises_tariff_map_id = ptm.premises_tariff_map_id AND ptm.active='Y' and  "
                    + "  ptm.type_of_premises_id = tp.type_of_premises_id and "
                    + "  ptm.tarrif_gen_details_id = t.tarrif_gen_details_id and "
//                    + "  ptm.tarrif_revision = t.revision and t.active = 'Y' "
                    + "  t.active = 'Y' "
                    + " AND  if('" + company_name + "'='' , cy.company_name like '%%' , cy.company_name= '" + company_name + "')"
                    + " AND  if('" + circle_name + "'='' , ci.circle_name like '%%' , ci.circle_name like '" + circle_name + "')"
                    + "  and if(  '" + division_filter + "'='',d.division_name like'%%'  , d.division_name like '" + division_filter + '%' + "' ) "
                    + "  and if(  '" + zone_filter1 + "'='',z.zone like'%%'  , z.zone like '" + zone_filter1 + '%' + "' ) "
                    + " and if(  '" + date_filter1 + "'='',DATE_FORMAT(m.date, '%d-%m-%Y') like'%%'  , DATE_FORMAT(m.date, '%d-%m-%Y') like '" + date_filter1 + '%' + "' )"
                    + " and if(  '" + feeder_filter + "'='',f.feeder_name like'%%'  , f.feeder_name like '" + feeder_filter + '%' + "'  ) "
                    + " and if(  '" + mete_ser_num_filter + "'='',m.meter_service_number like'%%'  , m.meter_service_number like '" + mete_ser_num_filter + '%' + "' ) "
                    //  + " and if(  '" + meter_no + "'='',m.meter_name like'%%'  , m.meter_name like '" + meter_no + '%' + "'  ) "
                    //   + " AND IF ('" + file_no + "'='' ,file_no LIKE '%%' ,file_no = '" + file_no + "') "
                    + " AND IF ('" + con + "'='ALL' ,tp.type_of_premsis LIKE '%%' ,tp.type_of_premsis = '" + con + "') ";







            mymap.put("status", status);
            mymap.put("switching_point_id", switching_point_id);
            mymap.put("org_office_id", org_office_id);
            mymap.put("con_type", con);
            mymap.put("zone", zone_filter1);
            mymap.put("date", date_filter1);
            mymap.put("feeder", feeder_filter);
            mymap.put("service_conn", mete_ser_num_filter);
            mymap.put("division", division_filter);
            mymap.put("division_param", division_param);
            mymap.put("zone_param", zone_param);
            mymap.put("feeder_param", feeder_param);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(query);

            JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFilePath);
            Map<String, JRDataset> datasetMap = jasperDesign.getDatasetMap();
            JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("MainDataset");
            subDataset.setQuery(newQuery);
            JasperReport compiledReport = JasperCompileManager.compileReport(jasperDesign);

            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, connection);
        } catch (Exception e) {
            System.out.println("MeterModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }
    /*
    public JasperPrint generateMeterExcelList(String jrxmlFilePath, int organisation_id, String con, String status, String conn_type, String zone_filter1, String date_filter1, String feeder_filter, String mete_ser_num_filter, String division_filter, String file_no, String conn_value) {
    JasperPrint jplist = null;
    HashMap mymap = new HashMap();
    int org_office_id = 0, switching_point_id = 0;
    String division_param ="" , zone_param ="" , feeder_param ="";
    try {

    if (conn_value.equals("Y")) {
    if (conn_type != null && !conn_type.isEmpty()) {
    int[] site = getSite_id(conn_type);
    switching_point_id = site[0];
    }
    } else {
    if (conn_type != null && !conn_type.isEmpty()) {
    org_office_id = getOrgOffice_id(organisation_id, conn_type);
    }
    }
    mymap.put(organisation_id, organisation_id);
    if (status.equals("Active")) {
    status ="Y";
    }
    if (status.equals("InActive")) {
    status ="N";
    }
    if (division_filter.equals("")) {
    division_param ="ALL";
    } else {
    division_param = division_filter;
    }
    if (zone_filter1.equals("")) {
    zone_param = "ALL";
    } else {
    zone_param = zone_filter1;
    }
    if (feeder_filter.equals("")) {
    feeder_param ="ALL";
    } else {
    feeder_param = feeder_filter;
    }
    mymap.put("status", status);
    mymap.put("switching_point_id", switching_point_id);
    mymap.put("org_office_id", org_office_id);
    mymap.put("con_type", con);
    mymap.put("zone", zone_filter1);
    mymap.put("date", date_filter1);
    mymap.put("feeder", feeder_filter);
    mymap.put("service_conn", mete_ser_num_filter);
    mymap.put("division", division_filter);
    mymap.put("division_param", division_param);
    mymap.put("zone_param", zone_param);
    mymap.put("feeder_param", feeder_param);
    mymap.put("file_no", file_no);
    mymap.put("conn_value", conn_value);
    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, connection);
    jplist = jasperPrint;
    } catch (Exception e) {
    System.out.println("MeterModel generatReport() JRException " + e);
    }
    return jplist;
    }
     */

    public List<Meters> excelReport(String premises, String division, String company, String circle) {
        List<Meters> list = new ArrayList<Meters>();
        String query;

        try {
            PreparedStatement stmt;

            query = " select cy.company_id,ci.circle_id,cy.company_name,ci.circle_name,m.general_img_details_id, slu.sacntion_load_unit , m.meter_id, msn_first_part, msn_sec_part, msn_third_part , msn_fourth_part  , "
                    + " premises_individual_detail, ivrs_no, file_no, t.tarrif_no, m.calculated_load, "
                    + " m.accessed_load,if(latitude is null,'',latitude) AS latitude ,if(longitude is null,'',longitude) AS longitude ,if(ward_no is null,'',ward_no) AS ward_no,image_name as image_path,"
                    + " DATE_FORMAT(m.effective_date, '%d-%m-%Y') AS effective_date, z.zone, f.feeder_name, m.revision ,m.meter_service_number , m.poll_no "
                    + " , d.division_name , m.active, m.org_office_id , m.meter_name, m.security_deposit, m.switching_point_id , f.feeder_name , "
                    + " m.sd_receipt_no, DATE_FORMAT(m.date, '%d-%m-%Y') AS date, m.initial_reading, m.phase, m.sanctioned_load_kw, o.organisation_name ,"
                    + " tp.type_of_premsis,tp.premises_individual_detail,"
                    + " c.city_name , m.reason, m.description, m.meter_name_auto, m.bill_sanction_load "
                    + " from meters as m,city c , organisation_name o, feeder f, zone z, division d,company cy, circle ci,general_image_details as gim, sanct_load_unit slu, "
                    + " premises_tariff_map ptm  LEFT JOIN tarrif_gen_details t ON ptm.tarrif_gen_details_id = t.tarrif_gen_details_id "
//                    + " AND t.revision = ptm.tarrif_revision "
                    + " AND t.active = 'Y' "
                    + " LEFT JOIN type_of_premises tp ON ptm.type_of_premises_id = tp.type_of_premises_id"
                    + " where slu.sanct_load_unit_id=m.sanct_load_unit_id AND slu.active='Y' AND "
                    + "  m.city_id = c.city_id  and m.organisation_id= o.organisation_id and m.final_revision='VALID' and f.zone_id = z.zone_id and z.division_id = d.division_id AND d.active = 'Y' "
                    + " and m.feeder_id = f.feeder_id and cy.company_id = ci.company_id and ci.circle_id = d.circle_id  "
                    + "  and m.premises_tariff_map_id=ptm.premises_tariff_map_id  and ptm.active='Y' and gim.general_image_details_id=m.general_img_details_id "
                    + " AND IF( ?='', cy.company_name Like '%%',cy.company_name = ?)"
                    + " AND IF( ?='', ci.circle_name Like '%%',ci.circle_name = ?)"
                    + " AND IF( ?='', d.division_name Like '%%',d.division_name = ?)"
                    + " AND IF( ?='', tp.type_of_premsis Like '%%',tp.type_of_premsis = ?)";


//                    + " AND    cy.company_name ='"+ company_name +"'"
//                    + " AND    ci.circle_name ='"+ circle_name +"'"
//                    + " AND    d.division_name ='"+ division_filter +"'";
            //   + " AND    tp.type_of_premsis ='"+ conn +"'";

            stmt = connection.prepareStatement(query);
            stmt.setString(1, company);
            stmt.setString(2, company);
            stmt.setString(3, circle);
            stmt.setString(4, circle);
            stmt.setString(5, division);
            stmt.setString(6, division);
            stmt.setString(7, premises);
            stmt.setString(8, premises);

            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                Meters meter = new Meters();
                //  meter.setGeneral_img_details_id(rset.getInt("general_img_details_id"));
                ///    meter.setDescription(rset.getString("description"));
                //   meter.setTariff_code(rset.getString("tarrif_no"));
                //   meter.setCalculated_load(rset.getDouble("calculated_load"));
                //    meter.setDivision_name(rset.getString("division_name"));
                //     meter.setZone(rset.getString("zone"));
                //   meter.setAccessed_load_kw(Double.parseDouble(rset.getString("accessed_load")));
                //  meter.setEffective_date(rset.getString("effective_date"));
                // meter.setFeeder(rset.getString("feeder_name"));
                meter.setMeter_id(rset.getInt("meter_id"));
                meter.setMeter_service_num(rset.getString("meter_service_number"));
                meter.setPoll_num(rset.getString("poll_no"));
                meter.setMeter_name(rset.getString("meter_name"));
                /*     meter.setSecurity_deposit(rset.getDouble("security_deposit"));
                meter.setSd_receipt_no(rset.getString("sd_receipt_no"));
                String strDate = rset.getDate("date").toString(); */
//                String[] str = strDate.split("-");
//                strDate = str[2] + "-" + str[1] + "-" + str[0];
             /*   meter.setDate(rset.getString("date"));
                meter.setInitial_reading(rset.getInt("initial_reading"));
                meter.setSanctioned_load_kw(rset.getDouble("sanctioned_load_kw")); */
                meter.setCity_name(rset.getString("city_name"));
                meter.setOrganisation(rset.getString("organisation_name"));
                String type_of_premises = rset.getString("premises_individual_detail");
                meter.setType_of_premises(rset.getString("type_of_premsis"));
                meter.setPremises_individual_detail(rset.getString("premises_individual_detail"));
                meter.setImage_path(rset.getString("image_path"));
                /*     meter.setFeeder_name(rset.getString("feeder_name"));
                meter.setReason(rset.getString("reason"));
                meter.setRevision(rset.getInt("revision"));
                meter.setPhase(rset.getString("phase"));
                meter.setIvrs_no(rset.getString("ivrs_no"));
                meter.setFile_no(rset.getString("file_no"));
                meter.setMeter_name_auto(rset.getString("meter_name_auto"));
                meter.setMsn_part1(rset.getString("msn_first_part"));
                meter.setMsn_part2(rset.getString("msn_sec_part"));
                meter.setMsn_part3(rset.getString("msn_third_part"));
                meter.setMsn_part4(rset.getString("msn_fourth_part"));
                meter.setLoad_unit(rset.getString("sanct_load_unit"));
                meter.setLatitude(rset.getString("latitude"));
                meter.setLongitude(rset.getString("longitude"));
                meter.setWard_no(rset.getString("ward_no")); */

                if (type_of_premises.equals("Y")) {
                    meter.setSwitching_point(getSwitcing_pointName(rset.getInt("switching_point_id")));
                    //   meter.setSwitching_offcie_name(getSwitcing_pointName(rse));
                } else {
                    meter.setOrg_office(getorg_office_name(rset.getInt("org_office_id")));
                    meter.setOffice_type(getOffice_type(rset.getInt("org_office_id")));
                }
                /*  String active = rset.getString("active");
                if (active.equalsIgnoreCase("y")) {
                meter.setActive("yes");
                } else {
                meter.setActive("No");
                } */
                list.add(meter);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }




        return list;
    }

    public byte[] generateExcelReport(String jrxmlFilePath, List siteList, HashMap mymap) {
        byte[] reportInbytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JRBeanCollectionDataSource jrbean = new JRBeanCollectionDataSource(siteList);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jp = JasperFillManager.fillReport(compiledReport, mymap, jrbean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            reportInbytes = baos.toByteArray();
        } catch (Exception e) {
            System.out.println("Error: in AdsitelistModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    /* public JasperPrint generateMeterExcelList(String jrxmlFilePath, String con, String division_filter,String company_name,String circle_name) {
    JasperPrint jplist = null;
    HashMap mymap = new HashMap();
    int org_office_id = 0, switching_point_id = 0;
    String division_param = "", zone_param = "", feeder_param = "";
    try {


    mymap.put("status", status);
    mymap.put("switching_point_id", switching_point_id);
    mymap.put("org_office_id", org_office_id);
    mymap.put("con_type", con);
    mymap.put("zone", zone_filter1);
    mymap.put("date", date_filter1);
    mymap.put("feeder", feeder_filter);
    mymap.put("service_conn", mete_ser_num_filter);
    mymap.put("division", division_filter);
    mymap.put("division_param", division_param);
    mymap.put("zone_param", zone_param);
    mymap.put("feeder_param", feeder_param);
    mymap.put("file_no", file_no);
    mymap.put("conn_value", conn_value);
    mymap.put("company_name", company_name);
    mymap.put("circle_name", circle_name);
    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, connection);
    jplist = jasperPrint;
    } catch (Exception e) {
    System.out.println("MeterModel generatReport() JRException: " + e);
    }
    return jplist;
    } */
    public byte[] generateDeactiveMeterList(String jrxmlFilePath, int organisation_id, String con, String status, String conn_type, String zone_filter1, String date_filter1, String feeder_filter, String mete_ser_num_filter, String division_filter, String company_name, String circle_name) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        int org_office_id = 0, switching_point_id = 0;
        String division_param = "", zone_param = "", feeder_param = "";
        String query = "SELECT division_name,zone,feeder_name,type_of_premsis,meter_service_number,DATE_FORMAT(updated_date,'%d-%m-%Y') AS deactive_date "
                + " from meters as m,city c , organisation_name o, feeder f, zone z, division d,company cy, circle ci,general_image_details as gim, "
                + " premises_tariff_map ptm  LEFT JOIN tarrif_gen_details t ON ptm.tarrif_gen_details_id = t.tarrif_gen_details_id "
//                + " AND t.revision = ptm.tarrif_revision "
                + " AND t.active = 'Y' "
                + " LEFT JOIN type_of_premises tp ON ptm.type_of_premises_id = tp.type_of_premises_id"
                + " where "
                + "  m.city_id = c.city_id  and m.organisation_id= o.organisation_id and m.active='N' and f.zone_id = z.zone_id and z.division_id = d.division_id AND d.active = 'Y' and m.feeder_id = f.feeder_id and cy.company_id = ci.company_id and ci.circle_id = d.circle_id  "
                + "  and m.premises_tariff_map_id=ptm.premises_tariff_map_id  and ptm.active='Y' and gim.general_image_details_id=m.general_img_details_id "
                + " and if( " + organisation_id + "  = 0 , m.organisation_id  like '%%' , m.organisation_id = " + organisation_id + " ) "
                + " and if('" + company_name + "' ='',cy.company_name like'%%'  ,cy.company_name like '" + company_name + "') "
                + " and if( '" + circle_name + "' ='',ci.circle_name like'%%'  , ci.circle_name= '" + circle_name + "' ) "
                + " and if( '" + division_filter + "' ='',d.division_name like'%%'  , d.division_name = '" + division_filter + "' ) "
                + " and if( '" + zone_filter1 + "' ='',z.zone like'%%'  , z.zone like '" + zone_filter1 + "' ) "
                + " and if( '" + date_filter1 + "' ='',DATE_FORMAT(m.date, '%d-%m-%Y') like'%%'  , DATE_FORMAT(m.date,  '%d-%m-%Y') like '" + date_filter1 + "') "
                + " and if( '" + feeder_filter + "' ='',f.feeder_name like'%%'  , f.feeder_name like '" + feeder_filter + "' ) "
                + " and if( '" + mete_ser_num_filter + "' ='',m.meter_service_number like'%%'  , m.meter_service_number like '" + mete_ser_num_filter + "' ) "
                + " AND IF ('" + con + "'='ALL' ,tp.type_of_premsis LIKE '%%' ,tp.type_of_premsis = '" + con + "') "
                + " order by updated_date DESC ";

        try {

            mymap.put("organisation_id", organisation_id);
            if (division_filter.equals("")) {
                division_param = "ALL";
            } else {
                division_param = division_filter;
            }
            if (zone_filter1.equals("")) {
                zone_param = "ALL";
            } else {
                zone_param = zone_filter1;
            }
            if (feeder_filter.equals("")) {
                feeder_param = "ALL";
            } else {
                feeder_param = feeder_filter;
            }
            mymap.put("status", status);
            mymap.put("switching_point_id", switching_point_id);
            mymap.put("org_office_id", org_office_id);
            mymap.put("con_type", con);
            mymap.put("zone", zone_filter1);
            mymap.put("date", date_filter1);
            mymap.put("feeder", feeder_filter);
            mymap.put("service_conn", mete_ser_num_filter);
            mymap.put("division", division_filter);
            mymap.put("division_param", division_param);
            mymap.put("zone_param", zone_param);
            mymap.put("feeder_param", feeder_param);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(query);

            JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFilePath);
            Map<String, JRDataset> datasetMap = jasperDesign.getDatasetMap();
            JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("MainDataset");
            subDataset.setQuery(newQuery);
            JasperReport compiledReport = JasperCompileManager.compileReport(jasperDesign);

            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, connection);
        } catch (Exception e) {
            System.out.println("MeterModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public int deleteRecord(int meter_id) {
        String query = "DELETE FROM meters WHERE meter_id = " + meter_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
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

    public Integer getLocation_id(String location_name) {
        String query = "SELECT id FROM location3 WHERE location_name = ?";
        Integer location_id = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, location_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            location_id = rset.getInt("id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return location_id;
    }

    public List<String> getLocationNameList() {
        List<String> list = new ArrayList<String>();
        String query = "SELECT location_name FROM location3 ORDER BY location_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("location_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int getTariff_premises_map_id(String tariff_key, int premises_id) {
        String query = "SELECT premises_tariff_map_id FROM premises_tariff_map WHERE type_of_premises_id = ? AND tarrif_gen_details_id = ? AND tarrif_revision = ? AND active='Y'";
        String[] tariff_data = tariff_key.split("=");
        int tariff_id = Integer.parseInt(tariff_data[0]);
        int tariff_rev = Integer.parseInt(tariff_data[1]);
        int premises_tariff_map_id = 0;
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setInt(1, premises_id);
            psmt.setInt(2, tariff_id);
            psmt.setInt(3, tariff_rev);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                premises_tariff_map_id = rst.getInt("premises_tariff_map_id");
            }
        } catch (Exception e) {
            System.out.println("Error inside tarrif premises maip id of MeterModelABC :" + e);
        }
        return premises_tariff_map_id;
    }

    public int getRevisionNo(int tarrif_map_id) {
        int revision_no = 0;
        String query = " SELECT revision FROM premises_tariff_map "
                + " WHERE premises_tariff_map_id = ? AND active = 'Y' ORDER BY revision DESC LIMIT 1 ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, tarrif_map_id);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                revision_no = rset.getInt("revision");
            }
            // revision_no++;
        } catch (Exception e) {
            System.out.println("zone Model getRevisionNo() Error: " + e);
        }
        return revision_no;
    }

    public Meters showMeterServiceconnection(int meter_id, int revision_no) {
        //    List<MeterBill> list = new ArrayList<MeterBill>();
        Meters meter = new Meters();
        String query;

        try {
            PreparedStatement psmt;

            query = "select cy.company_id,ci.circle_id,cy.company_name,ci.circle_name, slu.sacntion_load_unit , m.meter_id, msn_first_part, msn_sec_part, msn_third_part , msn_fourth_part  , premises_individual_detail, ivrs_no, file_no, t.tarrif_no, m.calculated_load,  m.accessed_load,"
                    + " if(latitude is null,'',latitude) AS latitude ,if(longitude is null,'',longitude)"
                    + " AS longitude ,if(ward_no is null,'',ward_no) AS ward_no ,  DATE_FORMAT(m.effective_date, '%d-%m-%Y') AS effective_date, z.zone,"
                    + "f.feeder_name, m.revision ,m.meter_service_number , m.poll_no  , d.division_name , m.active, m.org_office_id , m.meter_name, m.security_deposit, m.switching_point_id , f.feeder_name , m.sd_receipt_no, DATE_FORMAT(m.date, '%d-%m-%Y')"
                    + " AS date, m.initial_reading, m.phase, m.sanctioned_load_kw, o.organisation_name , tp.type_of_premsis,tp.premises_individual_detail,"
                    + " c.city_name , m.reason, m.description, m.meter_name_auto, m.bill_sanction_load  FROM meters as m,city c , organisation_name o, type_of_premises tp ,"
                    + "  feeder f, zone z, division d, tarrif_gen_details t, premises_tariff_map ptm, company cy, circle ci, sanct_load_unit slu"
                    + " where slu.sanct_load_unit_id=m.sanct_load_unit_id AND slu.active='Y' AND  m.city_id = c.city_id  and m.organisation_id= o.organisation_id and m.final_revision='VALID'"
                    + "and   cy.company_id = ci.company_id and ci.circle_id = d.circle_id and  f.zone_id = z.zone_id"
                    + " and z.division_id = d.division_id AND d.active = 'Y' and m.feeder_id = f.feeder_id"
//                    + " and  m.premises_tariff_map_id = ptm.premises_tariff_map_id AND m.premises_tariff_map_rev = ptm.revision"
                    + " and  m.premises_tariff_map_id = ptm.premises_tariff_map_id AND ptm.active = 'Y'"
                    + " and   ptm.type_of_premises_id = tp.type_of_premises_id"
                    + " and   ptm.tarrif_gen_details_id = t.tarrif_gen_details_id"
//                    + "  and   ptm.tarrif_revision = t.revision and t.active = 'Y'"
                    + "  and   t.active = 'Y'"
                    + " AND    m.meter_id =" + meter_id + " and m.revision=" + revision_no + " ";

            psmt = connection.prepareStatement(query);
            //    psmt.setInt(1, organisation_id);

            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {
                //    Meters meter = new Meters();
                meter.setDescription(rset.getString("description"));
                meter.setTariff_code(rset.getString("tarrif_no"));
                meter.setCalculated_load(rset.getDouble("calculated_load"));
                meter.setDivision_name(rset.getString("division_name"));
                meter.setZone(rset.getString("zone"));
                meter.setAccessed_load_kw(Double.parseDouble(rset.getString("accessed_load")));
                meter.setEffective_date(rset.getString("effective_date"));
                meter.setFeeder(rset.getString("feeder_name"));
                meter.setMeter_id(rset.getInt("meter_id"));
                meter.setMeter_service_num(rset.getString("meter_service_number"));
                meter.setPoll_num(rset.getString("poll_no"));
                meter.setMeter_name(rset.getString("meter_name"));
                meter.setSecurity_deposit(rset.getDouble("security_deposit"));
                meter.setSd_receipt_no(rset.getString("sd_receipt_no"));
                String strDate = rset.getDate("date").toString();
//                String[] str = strDate.split("-");
//                strDate = str[2] + "-" + str[1] + "-" + str[0];
                meter.setDate(rset.getString("date"));
                meter.setInitial_reading(rset.getInt("initial_reading"));
                meter.setSanctioned_load_kw(rset.getDouble("bill_sanction_load"));
                meter.setCity_name(rset.getString("city_name"));
                meter.setOrganisation(rset.getString("organisation_name"));
                String type_of_premises = rset.getString("premises_individual_detail");
                meter.setType_of_premises(rset.getString("type_of_premsis"));
                meter.setPremises_individual_detail(rset.getString("premises_individual_detail"));
                meter.setFeeder_name(rset.getString("feeder_name"));
                meter.setReason(rset.getString("reason"));
                meter.setRevision(rset.getInt("revision"));
                meter.setPhase(rset.getString("phase"));
                meter.setIvrs_no(rset.getString("ivrs_no"));
                meter.setFile_no(rset.getString("file_no"));
                meter.setMeter_name_auto(rset.getString("meter_name_auto"));
                meter.setMsn_part1(rset.getString("msn_first_part"));
                meter.setMsn_part2(rset.getString("msn_sec_part"));
                meter.setMsn_part3(rset.getString("msn_third_part"));
                meter.setMsn_part4(rset.getString("msn_fourth_part"));
                meter.setLoad_unit(rset.getString("sacntion_load_unit"));
                meter.setLatitude(rset.getString("latitude"));
                meter.setLongitude(rset.getString("longitude"));
                meter.setWard_no(rset.getString("ward_no"));
                meter.setTarrif_gen_details_id(rset.getInt("tarrif_gen_details_id"));
                meter.setType_of_premises_id(rset.getInt("type_of_premises_id"));
                meter.setCompany_name(rset.getString("company_name"));
                meter.setCircle_name(rset.getString("circle_name"));
                if (type_of_premises.equals("Y")) {
                    meter.setSwitching_point(getSwitcing_pointName(rset.getInt("switching_point_id")));
                } else {
                    meter.setOrg_office(getorg_office_name(rset.getInt("org_office_id")));
                    // meter.setOffice_type(getOffice_type(rset.getInt("org_office_id")));
                }
                String active = rset.getString("active");
                if (active.equalsIgnoreCase("y")) {
                    meter.setActive("yes");
                } else {
                    meter.setActive("No");
                }
                //    meter.add(meter);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }




        return meter;
    }

    public List<String> getofficetypeList() {
        List<String> list = new ArrayList<String>();
        String query = " select office_type from org_office_type ot , org_office o , "
                + " organisation_name om "
                + " where  ot.office_type_id = o.office_type_id  and "
                + " om.organisation_id = o.organisation_id and om.organisation_name  != 'Electricity Board'  group by office_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("office_type"));
            }
        } catch (Exception e) {
            System.out.println("Error: getofficetypeList" + e);
        }
        return list;
    }

    public Map<String, String> getpremisesList() {
        Map<String, String> map = new HashMap<String, String>();
        String query = "SELECT top.type_of_premsis, top.premises_individual_detail FROM premises_tariff_map ptm "
                    + " LEFT JOIN tarrif_gen_details tgd ON ptm.tarrif_gen_details_id = tgd.tarrif_gen_details_id "
//                    + " AND tgd.revision = ptm.tarrif_revision "
                    + " AND tgd.active = 'Y' "
                    + " LEFT JOIN type_of_premises top ON ptm.type_of_premises_id = top.type_of_premises_id "
                    + " LEFT JOIN company c on c.company_id = tgd.company_id WHERE tgd.company_id =1 ";
                    //+ " WHERE IF("+company_id+"=, tgd.company_id LIKE '%%', tgd.company_id = "+company_id+") ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                map.put(rset.getString("type_of_premsis"), rset.getString("premises_individual_detail"));
            }
        } catch (Exception e) {
            System.out.println("Error: getpremisesList" + e);
        }
        return map;
    }

    public Map<String, String> getpremisesList(int company_id) {
        Map<String, String> map = new HashMap<String, String>();
        //     String query = "SELECT tp.type_of_premsis,  tp.premises_individual_detail FROM type_of_premises tp";
        String query = "SELECT top.type_of_premsis, top.premises_individual_detail FROM premises_tariff_map ptm "
                + " LEFT JOIN tarrif_gen_details tgd ON ptm.tarrif_gen_details_id = tgd.tarrif_gen_details_id "
//                + " AND tgd.revision = ptm.tarrif_revision "
                + " AND tgd.active = 'Y' "
                + " LEFT JOIN type_of_premises top ON ptm.type_of_premises_id = top.type_of_premises_id "
                + " LEFT JOIN company c on c.company_id = tgd.company_id "
                //+ "WHERE tgd.company_id =" + company_id;
                + " WHERE IF("+company_id+"=0, tgd.company_id LIKE '%%', tgd.company_id = "+company_id+") ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                map.put(rset.getString("type_of_premsis"), rset.getString("premises_individual_detail"));
            }
        } catch (Exception e) {
            System.out.println("Error: getpremisesList" + e);
        }
        return map;
    }

    public boolean chkmeterServiceNumEntery(String meter_service_neum) {
        int count = 0;
        try {
            String query = " select count(*) from meters where meter_service_number =?  and active='Y' ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, meter_service_neum);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                count = rst.getInt("count(*)");
            }

        } catch (Exception e) {
            System.out.println("Error in meter Model chkmeterServiceNumEntery" + e);
        }
        return count == 0 ? true : false;
    }

    public boolean chkmeterServiceNumEntery(String meter_service_neum, int meter_id) {
        int count = 0;
        try {
            String query = " select count(*) from meters where meter_service_number =? and   meter_id !=? and active='Y' ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, meter_service_neum);
            psmt.setInt(2, meter_id);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                count = rst.getInt("count(*)");
            }

        } catch (Exception e) {
            System.out.println("Error in meter Model chkmeterServiceNumEntery" + e);
        }
        return count == 0 ? true : false;
    }

    public boolean chkmeterServiceNumEntery(int meter_id, String meter_num) {
        int count = 0;
        try {
            String query = " select count(*) from meters where meter_id != ?  and meter_name = ? and active='Y' ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setInt(1, meter_id);
            psmt.setString(2, meter_num);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                count = rst.getInt("count(*)");
            }

        } catch (Exception e) {
            System.out.println("Error in meter Model chkmeterServiceNumEntery" + e);
        }
        return count == 0 ? true : false;
    }

    public boolean chkmeterServiceNumEntery(String meter_service_neum, String meter_num, int meter_id) {
        int count = 0;
        try {
            String query = " select count(*) from meters where meter_service_number = ?  and meter_name = ?  and meter_id !=? and active='Y' ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, meter_service_neum);
            psmt.setString(2, meter_num);
            psmt.setInt(3, meter_id);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                count = rst.getInt("count(*)");
            }

        } catch (Exception e) {
            System.out.println("Error in meter Model chkmeterServiceNumEntery" + e);
        }
        return count == 0 ? true : false;
    }

    public String getmeterName(String meter_service_num) {

        try {
            String query = "select meter_name from meters where meter_service_number = ? and active='Y'";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, meter_service_num);
            ResultSet rst = psmt.executeQuery();
            while (rst.next()) {
                message = rst.getString("meter_name");
            }
            String msg = "Meter Number " + message + "is Allready Stand on the that Service_number";
            message = msg;
        } catch (Exception e) {
            System.out.println("Error in getmeterName" + e);
        }
        return message;

    }

    public String getSwitchingOffcieName(String type_of_conn) {

        String query = "select premises_individual_detail from type_of_premises where type_of_premsis = ? ";
        String switching_offcie_name = "";
        String premises_individual_detail = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, type_of_conn);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            premises_individual_detail = rset.getString("premises_individual_detail");
        } catch (Exception e) {
            System.out.println("Error: MeterModel gettype_of_premises_id " + e);
        }
        if (premises_individual_detail.equals("Y")) {
            switching_offcie_name = "Switching Point";
        } else {
            switching_offcie_name = "Office Location";
        }
        return switching_offcie_name;
    }

    public Integer getOgranisation_id(String organisation) {
        String query = "select organisation_id from organisation_name where organisation_name = ? ";
        Integer location_id = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            location_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: MeterModel getOgranisation_id " + e);
        }
        return location_id;
    }

    public int gettype_of_premises_id(String type_of_premises) {
        String query = "select type_of_premises_id from type_of_premises where type_of_premsis = ? ";
        int type_of_premises_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, type_of_premises);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            type_of_premises_id = rset.getInt("type_of_premises_id");
        } catch (Exception e) {
            System.out.println("Error: MeterModel gettype_of_premises_id " + e);
        }
        return type_of_premises_id;
    }

    public int[] getSite_id(int company_id, int circle_id, String switching_point) {
        int switching_point_id = 0;
        int feeder_id = 0;
        int[] site = {switching_point_id, feeder_id};
        //   String query = "select switching_point_id, feeder_id from switching_point where switching_point= ? ";
        String query = "SELECT sp.switching_point_id, sp.feeder_id "
                + " FROM  switching_point sp "
                + " LEFT JOIN feeder f ON sp.feeder_id = f.feeder_id "
                + " LEFT JOIN zone z ON z.zone_id = f.zone_id "
                + " LEFT JOIN division d ON z.division_id = d.division_id AND d.active = 'Y' "
                + " LEFT JOIN circle c ON d.circle_id = c.circle_id "
                + " LEFT JOIN company cy ON c.company_id = cy.company_id "
                + " WHERE d.circle_id = ? AND c.company_id = ? AND switching_point = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, circle_id);
            pstmt.setInt(2, company_id);
            pstmt.setString(3, switching_point);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                switching_point_id = rset.getInt("switching_point_id");
                feeder_id = rset.getInt("feeder_id");
            }
            site[0] = switching_point_id;
            site[1] = feeder_id;

        } catch (Exception e) {
            System.out.println("Error: MeterModel getSite_id " + e);
        }
        return site;
    }

    public int[] getSite_id(String switching_point) {
        int switching_point_id = 0;
        int feeder_id = 0;
        int[] site = {switching_point_id, feeder_id};
        String query = "select switching_point_id, feeder_id from switching_point where switching_point= ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, switching_point);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                switching_point_id = rset.getInt("switching_point_id");
                feeder_id = rset.getInt("feeder_id");
            }
            site[0] = switching_point_id;
            site[1] = feeder_id;

        } catch (Exception e) {
            System.out.println("Error: MeterModel getSite_id " + e);
        }
        return site;
    }

    public int getfeeder_id(int company_id, int circle_id, String feeder_name) {
        String query = "SELECT f.feeder_id "
                + " FROM feeder f "
                + " LEFT JOIN zone z ON z.zone_id = f.zone_id "
                + " LEFT JOIN division d ON z.division_id = d.division_id AND d.active = 'Y' "
                + " LEFT JOIN circle c ON d.circle_id = c.circle_id "
                + " LEFT JOIN company cy ON c.company_id = cy.company_id "
                + " WHERE d.circle_id = ? AND c.company_id = ? AND f.feeder_name = ?";
        int feeder_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, circle_id);
            pstmt.setInt(2, company_id);
            pstmt.setString(3, feeder_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            feeder_id = rset.getInt("feeder_id");
        } catch (Exception e) {
            System.out.println("Error: MeterModel getfeeder_id" + e);
        }
        return feeder_id;
    }

    public Map<Integer, String> getCompanyNameMap() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        String query = "SELECT company_id, company_name FROM company ";
       // map.put(0, "Company Name..");
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                map.put(rset.getInt("company_id"), rset.getString("company_name"));
            }
        } catch (Exception e) {
            System.out.println("Error in meterModel getTarrifDetails()" + e);
        }
        return map;
    }
public Map<Integer, String> getcircleName() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        String query ="SELECT c.circle_name , c.circle_id FROM circle c LEFT JOIN company cy"
                    + " ON c.company_id = cy.company_id WHERE c.company_id = 1 ";
      //  map.put(0, "Company Name..");
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                map.put(rset.getInt("circle_id"), rset.getString("circle_name"));
            }
        } catch (Exception e) {
            System.out.println("Error in fileDeatilModel getcircleName()" + e);
        }
        return map;
    }
    public List<String> getfeederList(int company_id, int circle_id) {
        List<String> list = new ArrayList<String>();
        //String query = "SELECT feeder_name FROM feeder where active='Y' ORDER BY feeder_name ";
        String query = "select feeder_name "
                + " FROM feeder f "
                + " LEFT JOIN zone z  ON f.zone_id = z.zone_id "
                + " LEFT JOIN division d ON d.division_id = z.division_id "
                + " LEFT JOIN circle c ON c.circle_id = d.circle_id "
                + " LEFT JOIN company cy ON c.company_id = cy.company_id "
                + " WHERE d.active = 'Y' AND c.company_id =? AND d.circle_id=? AND f.active='Y' ORDER BY feeder_name";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, company_id);
            stmt.setInt(2, circle_id);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("feeder_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel  getfeederList" + e);
        }
        return list;
    }

    public List<String> getMeterList(String feeder_name) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT meter_service_number FROM  meters m, feeder f where f.active='Y' and final_revision ='VALID' "
                + " and m.feeder_id = f.feeder_id and f.feeder_name = ? ORDER BY meter_service_number ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, feeder_name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("meter_service_number"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getMeterList() {
        List<String> list = new ArrayList<String>();
        String query = " SELECT meter_service_number FROM  meters m WHERE final_revision ='VALID' "
                + "  ORDER BY meter_service_number ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("meter_service_number"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getfeederList(String zone) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT feeder_name FROM feeder f, zone z where active='Y' AND "
                + " f.zone_id = z.zone_id AND z.zone = ? ORDER BY feeder_name; ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, zone);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("feeder_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel  getfeederList" + e);
        }
        return list;
    }

    public List<String> getSwitchingPoint(int company_id, int circle_id) {
        List<String> list = new ArrayList<String>();
        //   String query = "SELECT switching_point FROM switching_point where active='Y' ";
        String query = "select switching_point "
                + " FROM switching_point sp "
                + " LEFT JOIN feeder f ON sp.feeder_id = f.feeder_id "
                + " LEFT JOIN zone z  ON f.zone_id = z.zone_id "
                + " LEFT JOIN division d ON d.division_id = z.division_id "
                + " LEFT JOIN circle c ON c.circle_id = d.circle_id "
                + " LEFT JOIN company cy ON c.company_id = cy.company_id "
                + " WHERE d.active = 'Y' AND c.company_id =? AND d.circle_id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, company_id);
            stmt.setInt(2, circle_id);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("switching_point"));
            }
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel  getfeederList" + e);
        }
        return list;
    }

      public List<String> getSwitchingPointList(int company_id, String   circle_name ,String type_of_premsis, String switching_point_sub_type) {
        List<String> list = new ArrayList<String>();
        //   String query = "SELECT switching_point FROM switching_point where active='Y' ";
        /*String query = "select switching_point "
                + " FROM switching_point sp "
                + " LEFT JOIN feeder f ON sp.feeder_id = f.feeder_id "
                + " LEFT JOIN zone z  ON f.zone_id = z.zone_id "
                + " LEFT JOIN division d ON d.division_id = z.division_id "
                + " LEFT JOIN circle c ON c.circle_id = d.circle_id "
                + " LEFT JOIN company cy ON c.company_id = cy.company_id "
                + " LEFT JOIN type_of_premises t ON t.type_of_premises_id = sp.type_of_premises_id "
             //   + " WHERE cy.company_id =? AND c.circle_name=?   and  t.type_of_premsis= ?"
                + "  WHERE   if(" + company_id + "=0 , cy.company_id like '%%' , cy.company_id=" + company_id + ")"
                + " AND  if('" + circle_name + "'='' , c.circle_name like '%%' , c.circle_name = '" + circle_name + "')"
                 + " AND  if('" + type_of_premsis + "'='' , t.type_of_premsis like '%%' , t.type_of_premsis= '" + type_of_premsis + "')" ;*/
        String query = "select switching_point  "
                + " FROM switching_point sp  LEFT JOIN feeder f ON sp.feeder_id = f.feeder_id "
                + " LEFT JOIN zone z  ON f.zone_id = z.zone_id  LEFT JOIN division d ON d.division_id = z.division_id AND d.active = 'Y'  "
                + " LEFT JOIN circle c ON c.circle_id = d.circle_id  LEFT JOIN company cy ON c.company_id = cy.company_id "
                + " LEFT JOIN switching_point_sub_type_premises_map spm "
                + " ON spm.switching_point_sub_type_premises_map_id = sp.switching_point_sub_type_premises_map_id, type_of_premises t, switching_point_sub_type spt "
                + " WHERE spm.switching_point_sub_type_premises_map_id = sp.switching_point_sub_type_premises_map_id "
                + " and spm.type_of_premises_id=t.type_of_premises_id and spt.switching_point_sub_type_id=spm.switching_point_sub_type_id "
                + " and if(" + company_id + "=0 , cy.company_id like '%%' , cy.company_id=" + company_id + ") "
                + " AND  if('" + circle_name + "'='' , c.circle_name like '%%' , c.circle_name = '" + circle_name + "') "
                + " AND if('" + type_of_premsis + "'='' , t.type_of_premsis like '%%' , t.type_of_premsis= '" + type_of_premsis + "') "
                + " AND if('"+switching_point_sub_type+"'='' , spt.switching_point_sub_type like '%%' , spt.switching_point_sub_type= '"+switching_point_sub_type+"');";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
          //  stmt.setInt(1, company_id);
         //   stmt.setString(2, circle_name);
         //   stmt.setString(3, type_of_premsis);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("switching_point"));
            }
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel  getfeederList" + e);
        }
        return list;
    }

      public int getOrgOffice_idByOfficeType(String officeType) {
        int officeTypeId = Integer.parseInt(officeType.split("N")[0]);
        String query = "select org_office_id  from org_office where office_type_id="+officeTypeId;
        int org_office_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, org_office);
            //pstmt.setInt(2, organisation_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            org_office_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error: meter Model getOrgOffice_id " + e);
        }
        return org_office_id;
    }

      public Map<String, String> getSwitchingPointSubType() {
        Map<String, String> mymap = new LinkedHashMap<String, String>();
//        String query = " SELECT type_of_premises_id,type_of_premsis FROM type_of_premises where premises_individual_detail='Y' ";
        String query = "SELECT spm.switching_point_sub_type_id, switching_point_sub_type "
                + " FROM switching_point_sub_type_premises_map spm, switching_point_sub_type sst "
                + " WHERE sst.switching_point_sub_type_id=spm.switching_point_sub_type_id;";
        String officeTypeQuery ="SELECT office_type_id, office_type FROM org_office_type o ORDER BY office_type;";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                int switching_point_type_id = rset.getInt("switching_point_sub_type_id");
                String switching_point_type = rset.getString("switching_point_sub_type");
                mymap.put(switching_point_type_id+"Y", switching_point_type);
            }
            pstmt = connection.prepareStatement(officeTypeQuery);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                int office_type_id = rset.getInt("office_type_id");
                String office_type = rset.getString("office_type");
                mymap.put(office_type_id+"N", office_type);
            }
        } catch (Exception e) {
            System.out.println("getSwitchingPointSubType - ERROR : " + e);
        }
        return mymap;
    }

      public String getSwitchingPointSubType(String switchingPointType){
        String str = "";
        //String spt = "";
        Map<String, String> mymap = new LinkedHashMap<String, String>();
        String switchingPointSubTypequery = "";
        String officeTypeQuery = "";
        String premisesQuery = "";
        if(switchingPointType.equals("ALL")){
           /* premisesQuery = "SELECT type_of_premises_id, type_of_premsis, premises_individual_detail FROM type_of_premises";
            switchingPointSubTypequery = "SELECT spm.switching_point_sub_type_id, switching_point_sub_type "
                + " FROM switching_point_sub_type_premises_map spm, switching_point_sub_type sst "
                + " WHERE sst.switching_point_sub_type_id=spm.switching_point_sub_type_id;";
        officeTypeQuery ="SELECT office_type_id, office_type FROM org_office_type o ORDER BY office_type;";*/
            mymap = getSwitchingPointSubType();
            Set st = mymap.entrySet();
            Iterator itr = st.iterator();
            while(itr.hasNext()){
                Map.Entry entry = (Map.Entry) itr.next();
                str = str + entry.getKey() + "#" + entry.getValue() + "_";
            }
        }else{
        premisesQuery = "SELECT type_of_premises_id, type_of_premsis, premises_individual_detail FROM type_of_premises WHERE type_of_premsis='"+ switchingPointType +"';";
        switchingPointSubTypequery = "SELECT spm.switching_point_sub_type_id, switching_point_sub_type "
                + " FROM switching_point_sub_type_premises_map spm, switching_point_sub_type sst "
                + " WHERE sst.switching_point_sub_type_id=spm.switching_point_sub_type_id "
                + " AND IF('"+switchingPointType+"'='', spm.type_of_premises_id LIKE '%%',spm.type_of_premises_id=?);";
        officeTypeQuery ="SELECT office_type_id, office_type FROM org_office_type o ORDER BY office_type;";
          
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(premisesQuery);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                if(rset.getString("premises_individual_detail").equals("Y")){
                    pstmt = connection.prepareStatement(switchingPointSubTypequery);
                    pstmt.setInt(1, rset.getInt("type_of_premises_id"));
                    rset = pstmt.executeQuery();
                    while (rset.next()) {
                    int switching_point__sub_type_id = rset.getInt("switching_point_sub_type_id");
                    String switching_point_sub_type = rset.getString("switching_point_sub_type");
                    str = str + switching_point__sub_type_id + "Y" + "#" + switching_point_sub_type + "_";
                   }
                }else{
                    pstmt = connection.prepareStatement(officeTypeQuery);
                    rset = pstmt.executeQuery();
                    while (rset.next()) {
                        int office_type_id = rset.getInt("office_type_id");
                        String office_type = rset.getString("office_type");
                        str = str + office_type_id + "N" + "#" + office_type + "_";
                    }
                }
            }            
        } catch (Exception e) {
            System.out.println("getSwitchingPointType - ERROR : " + e);
        }
          }
        return str;
    }

    public String getPremisesTypeFromSubType(String switching_point_sub_type){
        String premisesType = "";
        int switchingPointSubTypeId = Integer.parseInt(switching_point_sub_type.split("Y")[0]);
        String query = "SELECT tp.type_of_premises_id, type_of_premsis "
                + " FROM switching_point_sub_type_premises_map spm, type_of_premises tp "
                + " WHERE tp.type_of_premises_id=spm.type_of_premises_id AND spm.switching_point_sub_type_id="+ switchingPointSubTypeId +";";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                //int switching_point_type_id = rset.getInt("type_of_premises_id");
                premisesType = rset.getString("type_of_premsis");
                //str = str + switching_point_type_id + "#" + switching_point_type + "_";
            }
        } catch (Exception e) {
            System.out.println("getPremisesTypeFromSubType - ERROR : " + e);
        }

        return premisesType;
    }

    public List getofficelist(String organisation, String office_type) {
        List list = new ArrayList();
        try {
            String query = "SELECT oo.location_name "
                    + "FROM org_office AS oo, organisation_name AS orgN, "
                    + "organisation_map AS om, organisation_sub_type AS ost, organisation_type AS ot , org_office_type otf "
                    + "WHERE oo.organisation_id = orgN.organisation_id AND "
                    + "orgN.organisation_id = om.organisation_id AND "
                    + "om.organisation_sub_type_id = ost.organisation_sub_type_id AND "
                    + "ost.organisation_type_id = ot.organisation_type_id AND oo.office_type_id = otf.office_type_id and "
                    + "orgN.organisation_name = ? and  office_type =? "
                    + " group by oo.location_name ORDER BY oo.location_name  ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation);
            pstmt.setString(2, office_type);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("location_name") + "&#;");
                count++;
            }
            if (count == 0) {
                //out.println("No Such Organisation Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error metermodel getofficelist" + e);
        }
        return list;
    }

    public List getcirclelist(int company_id) {
        List list = new ArrayList();
        try {
            String query = "SELECT c.circle_name , c.circle_id FROM circle c LEFT JOIN company cy"
                    + " ON c.company_id = cy.company_id WHERE c.company_id = ? ORDER BY c.circle_name";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, company_id);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            while (rset.next()) {
                list.add(rset.getString("circle_name"));
                list.add(",");
                list.add(rset.getInt("circle_id"));
                list.add("&#;");
                count++;
            }
            if (count == 0) {
                //out.println("No Such Organisation Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error metermodel getofficelist" + e);
        }
        return list;
    }

    public List getSearchCircleList(int company_id) {
        List list = new ArrayList();
        try {
            String query = "SELECT c.circle_name , c.circle_id FROM circle c LEFT JOIN company cy"
                    + " ON c.company_id = cy.company_id WHERE c.company_id = ? ORDER BY c.circle_name";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, company_id);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            while (rset.next()) {
                list.add(rset.getString("circle_name"));

                count++;
            }
            if (count == 0) {
                //out.println("No Such Organisation Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error metermodel getofficelist" + e);
        }
        return list;
    }

    public List getSearchCircleList() {
        List list = new ArrayList();
        try {
            String query = "SELECT c.circle_name ,c.circle_id FROM circle c ORDER BY c.circle_name";
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setInt(1, company_id);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            while (rset.next()) {
                list.add(rset.getString("circle_name"));

                count++;
            }
            if (count == 0) {
                //out.println("No Such Organisation Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error metermodel getofficelist" + e);
        }
        return list;
    }

    public List getTariffCode(int company_id, String premises) {
        List list = new ArrayList();
        try {
            String query = "SELECT tgd.tarrif_no, CONCAT_WS('=',ptm.tarrif_gen_details_id, ptm.tarrif_revision) as tarrif_gen "
                    + " FROM premises_tariff_map ptm "
                    + " LEFT JOIN tarrif_gen_details tgd ON ptm.tarrif_gen_details_id = tgd.tarrif_gen_details_id "
//                    + " AND tgd.revision = ptm.tarrif_revision "
                    + " AND tgd.active = 'Y' "
                    + " LEFT JOIN type_of_premises top ON ptm.type_of_premises_id = top.type_of_premises_id "
                    + " LEFT JOIN company c on c.company_id = tgd.company_id WHERE tgd.company_id = ? "
                    + " AND top.type_of_premsis = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, company_id);
            pstmt.setString(2, premises);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            while (rset.next()) {
                list.add(rset.getString("tarrif_no") + "," + rset.getString("tarrif_gen") + "&#;");
                count++;
            }
            if (count == 0) {
                //out.println("No Such Organisation Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error metermodel getofficelist" + e);
        }
        return list;
    }

    public List getPremisesList(int company_id) {
        List list = new ArrayList();
        try {
            String query = "SELECT top.type_of_premsis, top.premises_individual_detail FROM premises_tariff_map ptm "
                    + " LEFT JOIN tarrif_gen_details tgd ON ptm.tarrif_gen_details_id = tgd.tarrif_gen_details_id "
//                    + " AND tgd.revision = ptm.tarrif_revision "
                    + " AND tgd.active = 'Y' "
                    + " LEFT JOIN type_of_premises top ON ptm.type_of_premises_id = top.type_of_premises_id "
                    + " LEFT JOIN company c on c.company_id = tgd.company_id WHERE tgd.company_id =?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, company_id);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            while (rset.next()) {
                list.add(rset.getString("type_of_premsis"));
                list.add(",");
                list.add(rset.getString("premises_individual_detail"));
                list.add("&#;");
                count++;
            }
            if (count == 0) {
                //out.println("No Such Organisation Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error metermodel getofficelist" + e);
        }
        return list;
    }

    public List<String> getZoneList() {
        List<String> list = new ArrayList<String>();
        String zone;
        String query = "select zone from zone ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                zone = rset.getString("zone");
                list.add(zone);
            }
        } catch (Exception e) {
            System.out.println("Error:FeederModel   getZoneList" + e);
        }
        return list;
    }

    public List officelist(String organisation, String premisees_type, String subType) {
        List list = new ArrayList();
        int orgOfficeId = 0;
        /*if(subType == null || (subType.equals("0") || subType.equals(""))){
            //orgOfficeId = premises_type;
        }else{
            if(subType.indexOf('Y') > -1){
                //subType = getPremisesTypeFromSubType(subType);
            }else{
                orgOfficeId = getOrgOffice_idByOfficeType(subType);
            }

        }*/
        try {
            String query = " select location_name from org_office of ,org_office_type  ot, "
                           + " organisation_name om where om.organisation_id= of.organisation_id and ot.office_type_id=of.office_type_id "
                            + " and om.organisation_name=? "
                            + " and if('"+ subType +"'='', office_type LIKE '%%', office_type='"+ subType +"')"
                            //+ " and office_type=?"
                            + " group by location_name ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation);
//            pstmt.setString(2, premisees_type);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                list.add(rset.getString("location_name"));
            }

        } catch (Exception e) {
            System.out.println("Error metermodel officelist" + e);
        }
        return list;
    }

    public String getSwitcing_pointName(int switching_point_id) {
        String query = "select switching_point from switching_point where switching_point_id = ? ";
        String switching_point = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, switching_point_id);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {    // move cursor from BOR to valid record.
                switching_point = rset.getString("switching_point");
            }
        } catch (Exception e) {
            System.out.println("Error: MeterModel getSwitcing_point " + e);
        }
        return switching_point;
    }

    public String getorg_office_name(int org_office_id) {
        String query = "select location_name FROM org_office o where org_office_id= ? ";
        String org_office = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_office_id);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {    // move cursor from BOR to valid record.
                org_office = rset.getString("location_name");
            }
        } catch (Exception e) {
            System.out.println("Error: MeterModel getorg_office_name " + e);
        }
        return org_office;
    }

    public int getOrgOffice_id(int organisation_id, String org_office, String org_office_code) {
        int org_office_id = 0;
        PreparedStatement pstmt = null;
        try {
            String query = "";
            if (org_office != null) {
                if (org_office.equals("Select")) {
                    org_office = "";
                }
                if (!org_office.equals("")) {
                    query = "select org_office_id  from org_office where location_name= ? and organisation_id= ? ";
                    pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, org_office);
                    pstmt.setInt(2, organisation_id);
                }
            }
            if (org_office_code != null) {
                if (!org_office_code.equals("")) {
                    query = "select org_office_id  from org_office where org_office_code= ? and organisation_id= ? ";
                    pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, org_office_code);
                    pstmt.setInt(2, organisation_id);
                }
            }

            System.out.println(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            org_office_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error: meter Model getOrgOffice_id " + e);
        }
        return org_office_id;
    }

    public int getOrgOffice_id(int organisation_id, String org_office) {
        String query = "select org_office_id  from org_office where location_name= ? and organisation_id= ? ";
        int org_office_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_office);
            pstmt.setInt(2, organisation_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            org_office_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error: meter Model getOrgOffice_id " + e);
        }
        return org_office_id;
    }

    public List<String> getOrganisationList() {
        List<String> list = new ArrayList<String>();
        String query = "select organisation_name from organisation_name om , organisation_map omp, organisation_type otp "
                + " where  om.organisation_id= omp.organisation_id and omp.organisation_type_id =otp.organisation_type_id "
                + "and   otp.org_type_name ='Home' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("organisation_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: in MeterModel getOrganisationList" + e);
        }
        return list;
    }

    public List<String> getSiteList() {
        List<String> list = new ArrayList<String>();
        String query = "SELECT site_name FROM ad_site   ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("site_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: in MeterModel getSiteList" + e);
        }
        return list;
    }

    public List<String> getSanctloadList() {
        List<String> list = new ArrayList<String>();
        String query = " SELECT sacntion_load_unit FROM sanct_load_unit s  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("sacntion_load_unit"));
            }
        } catch (Exception e) {
            System.out.println("Error: in MeterModel getSanctloadList" + e);
        }
        return list;
    }

    public Map<String, String> getSanctLoadUnitList() {
        //List<String> list = new ArrayList<String>();
        Map<String, String> mapList = new HashMap<String, String>();
        String query = " SELECT sanct_load_unit_id, multiplying_factor, sacntion_load_unit FROM sanct_load_unit s WHERE active = 'Y'  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                mapList.put(rset.getInt("sanct_load_unit_id") + "#" + rset.getDouble("multiplying_factor"), rset.getString("sacntion_load_unit"));
            }
        } catch (Exception e) {
            System.out.println("Error: in MeterModel getSanctloadList" + e);
        }
        return mapList;
    }

    public List<String> getAddressList() {
        List<String> list = new ArrayList<String>();
        String query = " SELECT concat_ws( ',' ,address1, address2, address3,   '.')  as location FROM meters ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("location"));
            }
        } catch (Exception e) {
            System.out.println("Error: in MeterModel getOrganisationList" + e);
        }
        return list;
    }

    public int getMpeb_je_id(int junoir_eng_id) {
        String query = "SELECT mpeb_je_id FROM mpeb_je_office WHERE org_office_id = ? ";
        int mpeb_je_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junoir_eng_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            mpeb_je_id = rset.getInt("mpeb_je_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return mpeb_je_id;
    }

    public int getJEOffice_id(Meters meter) {
        int je_office_id = 0;
        String query = "select  of.org_office_id from org_office of, organisation_name o, org_office_type ot "
                + " where of.organisation_id= o.organisation_id and organisation_name='Electricity Board' "
                + " and of.office_type_id=ot.office_type_id  and of.location_name = ?  "
                + " and address_line1 =? and address_line2  =? or address_line3 =? ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, meter.getJunior_engineer());
            psmt.setString(2, meter.getJe_ddress1());
            if (meter.getJe_ddress2() == null) {
                psmt.setString(3, "");
            } else {
                psmt.setString(3, meter.getJe_ddress2());
            }
            if (meter.getJe_ddress3() == null) {
                psmt.setString(4, "");
            } else {
                psmt.setString(4, meter.getJe_ddress3());
            }
            ResultSet rset = psmt.executeQuery();
            rset.next();
            je_office_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return je_office_id;
    }

    public List<String> getCityList() {
        List<String> list = new ArrayList<String>();
        String query = "SELECT city_name FROM city  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("city_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int getCity_id(String city_name) {
        String query = "SELECT city_id FROM city WHERE city_name = ?";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("city_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return city_id;
    }

    public int getLastRecordAuto() {
        int auto = 0;
        String meter_name_auto = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT meter_name_auto FROM meters ORDER BY meter_id DESC LIMIT 1;");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            meter_name_auto = rset.getString(1);
            auto = (Integer.parseInt(meter_name_auto.split("_")[1])) + 1;
        } catch (Exception e) {
            System.out.println("Error in getLastRecordAuto : " + e);
        }
        return auto;
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

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMessage(String message) {
        this.message = message;
    }

     public List<String> getZoneList(String q) {
        List<String> list = new ArrayList<String>();
        String zone;
        int count = 0;
        q = q.trim();
        String query = "select distinct(zone) as zone  from zone ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                zone = rset.getString("zone");
                if (zone.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such zone exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:FeederModel   getZoneList" + e);
        }
        return list;
    }
     public List<String> getDivisionList(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct(division_name) as division_name FROM division order by division_name";
        int count = 0;
        q = q.trim();
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String division_name = rset.getString("division_name");
                if (division_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such type_of_file_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel  getfeederList" + e);
        }
        return list;
    }
     public List<String> getDivisionList(String q, String circle_name) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct(division_name) as division_name  FROM division d,circle c where d.circle_id =c.circle_id  and circle_name='"+circle_name+"' order by division_name";
        int count = 0;
        q = q.trim();
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String division_name = rset.getString("division_name");
                if (division_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such type_of_file_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: SwithingPointModel  getfeederList" + e);
        }
        return list;
    }

     public List<String> getFileNoList(String msn, String premisesType) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT DISTINCT m.file_no FROM meters m, type_of_premises t,  premises_tariff_map ptm "
                + "WHERE"
                + " m.premises_tariff_map_id = ptm.premises_tariff_map_id and "
                + " ptm.type_of_premises_id = t.type_of_premises_id "
                + "AND IF(?='',m.meter_service_number LIKE '%%',m.meter_service_number=?) "
                + "AND IF(?='',t.type_of_premsis LIKE '%%',t.type_of_premsis=?) "
                + "ORDER BY file_no ";
        //  String query = "select  o.organisation_name FROM organisation_name AS o ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, msn);
            psmt.setString(2, msn);
            psmt.setString(3, premisesType);
            psmt.setString(4, premisesType);
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("file_no"));
            }
        } catch (Exception e) {
            System.out.println("Error: in NotesheetModel getFileNoList" + e);
        }
        return list;
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


/*SELECT * FROM meters m where m.meter_name_auto='meter225';

select cy.company_id,ci.circle_id,cy.company_name,ci.circle_name,m.meter_id, msn_first_part, msn_sec_part, msn_third_part ,
       msn_fourth_part  , premises_individual_detail, ivrs_no, file_no, t.tarrif_no, m.calculated_load,  m.accessed_load,
       if(latitude is null,'',latitude) AS latitude ,if(longitude is null,'',longitude) AS longitude ,
       if(ward_no is null,'',ward_no) AS ward_no ,  DATE_FORMAT(m.effective_date, '%d-%m-%Y') AS effective_date, z.zone, f.feeder_name,
        m.revision ,m.meter_service_number , m.poll_no  , d.division_name , m.active, m.org_office_id , m.meter_name, m.security_deposit,
        m.switching_point_id , f.feeder_name , m.sd_receipt_no, DATE_FORMAT(m.date, '%d-%m-%Y') AS date, m.initial_reading, m.phase,
         m.sanctioned_load_kw, o.organisation_name , tp.type_of_premsis,tp.premises_individual_detail, c.city_name , m.reason,
         m.description, m.meter_name_auto, m.bill_sanction_load
         FROM meters as m,city c , organisation_name o, type_of_premises tp ,
         feeder f, zone z, division d, tarrif_gen_details t, premises_tariff_map ptm, company cy, circle ci  , org_office  s
         where m.org_office_id = s.org_office_id
          AND IF ('meter225'='' ,m.meter_name_auto LIKE '%%' ,m.meter_name_auto='meter225');
         and  if(0= 0 , m.org_office_id  like '%%', s.org_office_id = 0 )
         and     m.city_id = c.city_id
         and m.organisation_id= o.organisation_id and m.final_revision='VALID'
         and   cy.company_id = ci.company_id and ci.circle_id = d.circle_id and  f.zone_id = z.zone_id and z.division_id = d.division_id
         AND d.active = 'Y' and m.feeder_id = f.feeder_id
         and if(0 = 0 , m.organisation_id  like '%%' , m.organisation_id = 0 )
         and if(  'ALL'='All',m.active like'%%'  , m.active= 'Y' ) and  m.premises_tariff_map_id = ptm.premises_tariff_map_id
         AND ptm.active='Y' and    ptm.type_of_premises_id = tp.type_of_premises_id and   ptm.tarrif_gen_details_id = t.tarrif_gen_details_id
         and   t.active = 'Y'           AND  if(0=0 , cy.company_id like '%%' , cy.company_id=0)
         AND  if(''='' , ci.circle_name like '%%' , ci.circle_name like '')
         and if(  ''='',d.division_name like'%%'  , d.division_name like '%' )
         and if(  ''='',z.zone like'%%'  , z.zone like '%' )
         and if(  ''='',DATE_FORMAT(m.date, '%d-%m-%Y') like'%%'  , DATE_FORMAT(m.date, '%d-%m-%Y') like '%' )
         and if(  ''='',f.feeder_name like'%%'  , f.feeder_name like '%'  )
         and if(  ''='',m.meter_service_number like'%%'  , m.meter_service_number like '%' )
         and if(  ''='',m.meter_name like'%%'  , m.meter_name like '%'  )
         AND IF (''='' ,file_no LIKE '%%' ,file_no = '')
         AND IF ('ALL'='ALL' ,tp.type_of_premsis LIKE '%%' ,tp.type_of_premsis = 'ALL')

        AND IF (''='' ,m.ivrs_no LIKE '%%' ,m.ivrs_no = '')
          ORDER BY m.meter_name;*/