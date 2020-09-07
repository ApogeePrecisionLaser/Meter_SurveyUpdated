/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.general.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;

/**
 *
 * @author jpss
 */
public class JFreeChartModel {

    private Connection connection;

    private String MeterRepositoryPath;

  
public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
          System.out.println("JFreeChartModel setConnection() Error: " + e);
        }
    }
    public org.jfree.chart.JFreeChart generateBarChart(String parameter, int junction_id, String date) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        //IntervalCategoryDataset dataSet1 = new Category();
        org.jfree.chart.JFreeChart chart = null;
        String column_name = "";
        if (parameter.equals("Consumed Unit")) {
            column_name = "consumed_unit";
        } else if (parameter.equals("Current Consumed Unit")) {
            column_name = "total_consumed_unit";
        } else if (parameter.equals("Total Amount")) {
            column_name = "total_amount";
        } else if (parameter.equals("Due Date Amount")) {
            column_name = "due_date_amount";
        } else if(parameter.equals("Previous Due")){
            column_name = "previous_due";
        }
        String[] split = date.split("-", date.length());
        try {
            String query = " SELECT " + column_name + ", mb.bill_month, "
                    + " CASE "
                    + " WHEN mb.bill_month = 'Jan-"+split[1]+"' THEN 01 "
                    + " WHEN mb.bill_month = 'Feb-"+split[1]+"' THEN 02 "
                    + " WHEN mb.bill_month = 'Mar-"+split[1]+"' THEN 03 "
                    + " WHEN mb.bill_month = 'Apr-"+split[1]+"' THEN 04 "
                    + " WHEN mb.bill_month = 'May-"+split[1]+"' THEN 05 "
                    + " WHEN mb.bill_month = 'Jun-"+split[1]+"' THEN 06 "
                    + " WHEN mb.bill_month = 'Jul-"+split[1]+"' THEN 07 "
                    + " WHEN mb.bill_month = 'Aug-"+split[1]+"' THEN 08 "
                    + " WHEN mb.bill_month = 'Sep-"+split[1]+"' THEN 09 "
                    + " WHEN mb.bill_month = 'Oct-"+split[1]+"' THEN 10 "
                    + " WHEN mb.bill_month = 'Nov-"+split[1]+"' THEN 11 "
                    + " WHEN mb.bill_month = 'Dec-"+split[1]+"' THEN 12 "
                    + " END AS 'month_int' "
                    + " FROM meter_bill AS mb "
                    + " WHERE mb.final_revision = 'VALID' AND SUBSTRING_INDEX(mb.bill_month, '-', -1) = '" + split[1]
                    + "' AND meter_id = " + junction_id
                    + " ORDER BY month_int ASC ";
            query = "SELECT "+column_name+", date_format(mb.reading_time, '%H:%i') AS reading_time"
                    + " FROM meter_readings AS mb"
                    + " WHERE reading_date=str_to_date('"+ date +"', '%d-%m-%Y') AND junction_id = "+ junction_id +" GROUP BY "+column_name+" ASC "
                    + " LIMIT 0, 10";

            PreparedStatement pstmt;
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            String initialValue = "";
            int count = 1;
            while (rset.next()) {
                if(initialValue.isEmpty())
                    initialValue = rset.getString(column_name).substring(0, 1) + "_ _ _";
                dataSet.setValue(Integer.parseInt(rset.getString(column_name).substring(1)), parameter, rset.getString("reading_time"));
            }
            chart = ChartFactory.createLineChart(
                    date + " " + parameter + " Representation ", "Time", initialValue + parameter,
                    dataSet, PlotOrientation.VERTICAL, false, true, false);

        } catch (Exception e) {
            System.out.println("JFreeChartModel generateBarChart() Error: " + e);
        }
        return chart;
    }

     public int getCompanyCityCredentials(int meter_id) {
        PreparedStatement pstmt = null;
        int company_id = 0;
        String query = "select c.company_id, c.company_name, c.acronym, cty.city_name, cty.city_name_hindi, cty.city_in_kruti_dev  from meters m "
                + " left join feeder f on f.feeder_id = m.feeder_id "
                + " left join zone z on z.zone_id = f.zone_id "
                + " left join division d on d.division_id = z.division_id AND d.active = 'Y' "
                + " left join circle cir on cir.circle_id = d.circle_id "
                + " left join company c on c.company_id = cir.company_id "
                + " left join city cty on m.city_id = cty.city_id "
                + " WHERE m.meter_id = ? and m.active = 'Y' ";
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, meter_id);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                company_id = rset.getInt("company_id");
            }

        } catch (Exception e) {
            System.out.println("Error occured inside getCompanyCityCredentials is: " + e);
        }
        return company_id;
    }

    public String getMeterRepositoryPath() {
        return MeterRepositoryPath;
    }

    public void setMeterRepositoryPath(String MeterRepositoryPath) {
        this.MeterRepositoryPath = MeterRepositoryPath;
    }

    public Connection getConnection() {
        return connection;
    }

}
