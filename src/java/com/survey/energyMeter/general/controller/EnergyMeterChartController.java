/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.general.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.jdbc.JDBCPieDataset;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class EnergyMeterChartController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try {
                connection =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/meter_survey?user=root&password=root&useUnicode=true&characterEncoding=utf-8");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        JDBCPieDataset dataset = new JDBCPieDataset(connection);
        try {
            dataset.executeQuery("Select city_name, city_id FROM city ");

            JFreeChart chart = ChartFactory.createPieChart("City Chart", dataset, true, true, false);
            chart.setBorderPaint(Color.black);
            chart.setBorderStroke(new BasicStroke(10.0f));
            chart.setBorderVisible(true);
            if (chart != null) {
                int width = 500;
                int height = 350;
                final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
                response.setContentType("image/png");
                OutputStream out = response.getOutputStream();
                ChartUtilities.writeChartAsPNG(out, chart, width, height, info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// TODO Auto-generated method stub
    }
}
