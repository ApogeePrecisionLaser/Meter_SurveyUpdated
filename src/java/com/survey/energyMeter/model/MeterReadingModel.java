/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.model;

import com.survey.energyMeter.tableClasses.Readings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Pooja
 */
public class MeterReadingModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPassword;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPassword);
        } catch (Exception e) {
            System.out.println("MeterReadingModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String searchJunction,String searchIvrs_no, String searchReadingDateFrom, String searchReadingDateTo, String searchVoltage, String searchVoltageValue, String searchCurrent, String searchCurrentValue, String searchConnectedLoad, String  timeFromHour, String timeFromMin, String  timeToHour, String timeToMin) {
        int noOfRows = 0;
        String timeFrom = "";
        String timeTo = "";
        if(!timeFromHour.isEmpty() || !timeFromMin.isEmpty())
            timeFrom = (timeFromHour.isEmpty()?"00":timeFromHour) + ":" + (timeFromMin.isEmpty()?"00":timeFromMin) + ":00";
        if(!timeToHour.isEmpty() || !timeToMin.isEmpty())
            timeTo = (timeToHour.isEmpty()?"00":timeToHour) + ":" + (timeToMin.isEmpty()?"00":timeToMin) + ":00";
        String addQuery1 = " AND IF('" + searchVoltageValue + "'= '', "+ searchVoltage +" LIKE '%%' , "+ searchVoltage +" > '" + searchVoltageValue + "') ";
        String addQuery2 = " AND IF('" + searchCurrentValue + "'= '', "+ searchCurrent +" LIKE '%%' , "+ searchCurrent +" > '" + searchCurrentValue + "') ";
        if(searchVoltage.isEmpty())
            addQuery1 = "";
        if(searchCurrent.isEmpty())
            addQuery2 = "";
        String addQuery = addQuery1 + addQuery2;
        try {
            String query = "SELECT COUNT(*)"
                    + " from meter_readings AS mr , switching_point_detail AS spd, junction AS j, unit AS v, unit AS c "
                    + " WHERE j.junction_id=mr.junction_id AND mr.voltage_unit_id = v.unit_id AND j.active='Y'"
                    + " AND mr.current_unit_id = c.unit_id AND spd.switching_point_detail_id=j.switching_point_detail_id AND spd.active='Y' "
//                    + " AND str_to_date(DATE_FORMAT(reading_date,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date(IF('"+searchReadingDateFrom+"'='',DATE_FORMAT(reading_date,'%d-%m-%Y'),'"+searchReadingDateFrom+"'),'%d-%m-%Y') AND str_to_date('"+searchReadingDateTo+"','%d-%m-%Y')"
//                    + " AND IF('"+ timeFrom +"'='', reading_time LIKE '%%',reading_time >= '"+ timeFrom +"')"
//                    + " AND IF('"+ timeTo +"'='', reading_time LIKE '%%',reading_time <= '"+ timeTo +"')"
                    + " AND str_to_date(DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'%d-%m-%Y %H:%i')"
                    + " between str_to_date(IF('"+searchReadingDateFrom+"'='',DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'"+searchReadingDateFrom + " " + timeFrom +"'),'%d-%m-%Y %H:%i')"
                    + " AND str_to_date(IF('"+searchReadingDateTo+"'='',DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'"+searchReadingDateTo + " " + timeTo +"'),'%d-%m-%Y %H:%i')"
                    + " AND IF('" + searchJunction + "'= '', switching_point_name LIKE '%%' , switching_point_name = '" + searchJunction + "')"
                    + " AND IF('" + searchIvrs_no + "'= '', ivrs_no LIKE '%%' , ivrs_no = '" + searchIvrs_no + "') "

                    + " AND IF('" + searchConnectedLoad + "'= '', mr.connected_load LIKE '%%' , mr.connected_load > '" + searchConnectedLoad + "')"
                    + addQuery;

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                noOfRows = rset.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error: MeterReadingModel-getNoOfRows--- " + e);
        }
        return noOfRows;
    }

    public List<Readings> showData(String searchJunction,String searchIvrs_no, String searchReadingDateFrom, String searchReadingDateTo, int lowerLimit, int noOfRowsToDisplay, String searchVoltage, String searchVoltageValue, String searchCurrent, String searchCurrentValue, String searchConnectedLoad, String  timeFromHour, String timeFromMin, String  timeToHour, String timeToMin) {
        List<Readings> list = new ArrayList<Readings>();
        String timeFrom = "";
        String timeTo = "";
        if(!timeFromHour.isEmpty() || !timeFromMin.isEmpty())
            timeFrom = (timeFromHour.isEmpty()?"00":timeFromHour) + ":" + (timeFromMin.isEmpty()?"00":timeFromMin) + ":00";
        if(!timeToHour.isEmpty() || !timeToMin.isEmpty())
            timeTo = (timeToHour.isEmpty()?"00":timeToHour) + ":" + (timeToMin.isEmpty()?"00":timeToMin) + ":00";
        String addQuery1 = " AND IF('" + searchVoltageValue + "'= '', "+ searchVoltage +" LIKE '%%' , "+ searchVoltage +" > '" + searchVoltageValue + "') ";
        String addQuery2 = " AND IF('" + searchCurrentValue + "'= '', "+ searchCurrent +" LIKE '%%' , "+ searchCurrent +" > '" + searchCurrentValue + "') ";
        if(searchVoltage.isEmpty())
            addQuery1 = "";
        if(searchCurrent.isEmpty())
            addQuery2 = "";
        String addQuery = addQuery1 + addQuery2;
        String addOrderQuery = " AND IF('" + searchJunction + "'= '', switching_point_name LIKE '%%' , switching_point_name = '" + searchJunction + "') ORDER BY meter_readings_id DESC LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if(lowerLimit == 0 && noOfRowsToDisplay == 1)
            addOrderQuery = " AND meter_readings_id IN (SELECT max(meter_readings_id) FROM meter_readings AS mr INNER JOIN junction AS j ON j.junction_id=mr.junction_id AND j.active='Y'"
                    + " INNER JOIN switching_point_detail AS spd ON spd.switching_point_detail_id=j.switching_point_detail_id"
                    + " AND spd.active='Y' WHERE IF('" + searchJunction + "'= '', switching_point_name LIKE '%%' , switching_point_name = '" + searchJunction + "')) ";
        try {
            /*String query = "SELECT j.junction_id, j.program_version_no, switching_point_name, voltage1, voltage2, voltage3, current1, current2, current3, power_factor, consumed_unit, "
                    + " v.unit AS voltage_unit, c.unit AS current_unit, DATE_FORMAT(reading_date, '%d-%m-%Y') AS reading_date, reading_time, mr.remark, mr.connected_load, "
                    + " mr.on_time_hr, mr.on_time_min, mr.off_time_hr, mr.off_time_min "
                    + " from meter_readings AS mr , switching_point_detail AS spd, junction AS j, unit AS v, unit AS c "
                    + " WHERE j.junction_id=mr.junction_id AND mr.voltage_unit_id = v.unit_id  AND j.active='Y' "
                    + " AND mr.current_unit_id = c.unit_id AND spd.switching_point_detail_id=j.switching_point_detail_id AND spd.active='Y' "
                    //+ " AND str_to_date(DATE_FORMAT(reading_date,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date(IF('"+searchReadingDateFrom+"'='',DATE_FORMAT(reading_date,'%d-%m-%Y'),'"+searchReadingDateFrom+"'),'%d-%m-%Y') AND str_to_date('"+searchReadingDateTo+"','%d-%m-%Y')"
//                    + " AND str_to_date(DATE_FORMAT(reading_date,'%d-%m-%Y'),'%d-%m-%Y') between str_to_date(IF('"+searchReadingDateFrom+"'='',DATE_FORMAT(reading_date,'%d-%m-%Y'),'"+searchReadingDateFrom+"'),'%d-%m-%Y') AND str_to_date(IF('"+searchReadingDateTo+"'='',DATE_FORMAT(reading_date,'%d-%m-%Y'),'"+searchReadingDateTo+"'),'%d-%m-%Y')"
                    + " AND str_to_date(DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'%d-%m-%Y %H:%i')"
                    + " between str_to_date(IF('"+searchReadingDateFrom+"'='',DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'"+searchReadingDateFrom + " " + timeFrom +"'),'%d-%m-%Y %H:%i')"
                    + " AND str_to_date(IF('"+searchReadingDateTo+"'='',DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'"+searchReadingDateTo + " " + timeTo +"'),'%d-%m-%Y %H:%i')"
                    //+ " AND IF('"+ timeFrom +"'='', reading_time LIKE '%%',reading_time >= '"+ timeFrom +"')"
                    //+ " AND IF('"+ timeTo +"'='', reading_time LIKE '%%',reading_time <= '"+ timeTo +"')"
                    + " AND IF('" + searchJunction + "'= '', switching_point_name LIKE '%%' , switching_point_name = '" + searchJunction + "') "
                    + " AND IF('" + searchConnectedLoad + "'= '', mr.connected_load LIKE '%%' , mr.connected_load > '" + searchConnectedLoad + "')"
                    + addQuery
                    + addOrderQuery//+ " ORDER BY meter_readings_id DESC "
                    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;*/

            String query = "SELECT j.junction_id, j.program_version_no, switching_point_name,spd.ivrs_no, voltage1, voltage2, voltage3, current1, current2, current3, power_factor, consumed_unit, "
                    + " v.unit AS voltage_unit, c.unit AS current_unit, DATE_FORMAT(reading_date, '%d-%m-%Y') AS reading_date, reading_time, mr.remark, mr.connected_load, "
                    + " mr.on_time_hr, mr.on_time_min, mr.off_time_hr, mr.off_time_min "
                    + " FROM meter_readings AS mr INNER JOIN junction AS j ON j.junction_id=mr.junction_id AND j.active='Y'"
                    + " INNER JOIN switching_point_detail AS spd ON spd.switching_point_detail_id=j.switching_point_detail_id AND spd.active='Y'"
                    + " INNER JOIN unit AS v ON mr.voltage_unit_id = v.unit_id INNER JOIN unit AS c ON mr.current_unit_id = c.unit_id"
                    + " WHERE "
                    + " str_to_date(DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'%d-%m-%Y %H:%i')"
                    + " between str_to_date(IF('"+searchReadingDateFrom+"'='',DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'"+searchReadingDateFrom + " " + timeFrom +"'),'%d-%m-%Y %H:%i')"
                    + " AND str_to_date(IF('"+searchReadingDateTo+"'='',DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'"+searchReadingDateTo + " " + timeTo +"'),'%d-%m-%Y %H:%i')"
                    //+ " AND IF('" + searchJunction + "'= '', switching_point_name LIKE '%%' , switching_point_name = '" + searchJunction + "') "
                    + " AND IF('" + searchIvrs_no + "'= '', ivrs_no LIKE '%%' , ivrs_no = '" + searchIvrs_no + "') "
                    + " AND IF('" + searchConnectedLoad + "'= '', mr.connected_load LIKE '%%' , mr.connected_load > '" + searchConnectedLoad + "')"
                    + addQuery
                    + addOrderQuery;//+ " ORDER BY meter_readings_id DESC "
                   // + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            //AND str_to_date(DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'%d-%m-%Y %H:%i')
//       between str_to_date(IF('29-06-2016'='',DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'29-06-2016 04:00'),'%d-%m-%Y %H:%i')
//       AND str_to_date(IF('30-09-2016'='',DATE_FORMAT(concat(reading_date, concat(' ', reading_time)),'%d-%m-%Y %H:%i'),'30-06-2016 12:40'),'%d-%m-%Y %H:%i')

            //System.out.println(query);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
           while (rset.next()) {
                Readings readings = new Readings();
                readings.setJunction_id(rset.getInt("junction_id"));
                readings.setProgram_version_no(rset.getInt("program_version_no"));
                readings.setJunction_name(rset.getString("switching_point_name"));
                readings.setIvrs_no(rset.getString("ivrs_no"));
                readings.setVoltage1(rset.getLong("voltage1"));
                readings.setVoltage2(rset.getLong("voltage2"));
                readings.setVoltage3(rset.getLong("voltage3"));
                readings.setCurrent1(rset.getLong("current1"));
                readings.setCurrent2(rset.getLong("current2"));
                readings.setCurrent3(rset.getLong("current3"));
                readings.setConnected_load(rset.getLong("connected_load"));
                readings.setPower_factor(rset.getLong("power_factor"));
                readings.setConsumed_unit(rset.getLong("consumed_unit"));
                readings.setVoltage_unit(rset.getString("voltage_unit"));
                readings.setCurrent_unit(rset.getString("current_unit"));
                readings.setReading_date(rset.getString("reading_date"));
                readings.setReading_time(rset.getString("reading_time"));
                readings.setRemark(rset.getString("remark"));
                readings.setOn_time_hr(rset.getInt("on_time_hr"));
                readings.setOn_time_min(rset.getInt("on_time_min"));
                readings.setOff_time_hr(rset.getInt("off_time_hr"));
                readings.setOff_time_min(rset.getInt("off_time_min"));
                list.add(readings);
            }

        } catch (Exception e) {
            System.out.println("Error: MeterReadingModel-showData--- " + e);
        }
        return list;
    }

     public List<String> getJunctionList(String q) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = " SELECT spd.switching_point_name "
                + " FROM switching_point_detail spd WHERE spd.active='Y' ORDER BY spd.switching_point_name; ";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String junction_name = rset.getString("switching_point_name");
                if (junction_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(junction_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such junction_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

     public List<String> getIvrsNo(String q) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = " select ivrs_no "
                     +" from junction j,switching_point_detail spd,meter_readings mr "
                     +" where j.junction_id=mr.junction_id AND j.active='Y' "
                     +" and spd.switching_point_detail_id=j.switching_point_detail_id AND spd.active='Y' "
                     +" group by ivrs_no ORDER BY ivrs_no  ";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
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
                list.add("No such junction_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }


    public byte[] generatePdf(String jrxmlFilePath, String searchJunction, String searchReadingDateFrom, String searchReadingDateTo, List datalist) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        mymap.put("junctionName", searchJunction.isEmpty() ? "All" : searchJunction);
        mymap.put("reading_date", searchReadingDateFrom.equals(searchReadingDateTo) ? searchReadingDateFrom : searchReadingDateFrom+" to "+searchReadingDateTo);

        try {
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(datalist);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, beanColDataSource);

        } catch (Exception e) {
            System.out.println("Error: in MeterReadingModel generatePdf() JRException: " + e);
        }
        return reportInbytes;
    }

    public java.sql.Date getSqDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        if (date != null && !date.isEmpty()) {
            String strD = date;
            String[] str1 = strD.split("-");
            strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
            finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        }
        return finalDate;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnection(Connection con) {
        this.connection = con;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_userName() {
        return db_userName;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public String getDb_userPassword() {
        return db_userPassword;
    }

    public void setDb_userPassword(String db_userPassword) {
        this.db_userPassword = db_userPassword;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("MeterReadingModel closeConnection() Error: " + e);
        }
    }
}
