/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.general.controller;

import com.itextpdf.text.Document;

import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.FontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Table;
import com.itextpdf.text.pdf.PdfPTable;
import com.survey.general.model.JFreeChartModel;
import com.survey.dbcon.DBConnection;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.design.JasperDesign;
import com.survey.util.RepositryPath;

/**
 *
 * @author jpss
 */
public class JFreeChart extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        JFreeChartModel jFreeChartModel = new JFreeChartModel();
        HttpSession session = request.getSession(false);
//        if (session == null || (String) session.getAttribute("user_name") == null) {
//            response.sendRedirect("beforeLoginPage");
//            return;
//        }
        try {
            jFreeChartModel.setConnection(DBConnection.getConnection(ctx));
        } catch (Exception e) {
            System.out.println("error in JFreeChart setConnection() calling try block" + e);
        }
        try {
            String requestprinrt = request.getParameter("requestprinrt");
            if (requestprinrt.equals("VIEW_GRAPH")) {
                String parameter = request.getParameter("parameter");
                String date = request.getParameter("date");
                int junction_id = Integer.parseInt(request.getParameter("junction_id"));
                jFreeChartModel.setMeterRepositoryPath(RepositryPath.getRepPath(jFreeChartModel.getConnection()));//jFreeChartModel.getCompanyCityCredentials(meter_id)
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                String str_month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("en", "US"));
                int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
                String folder_path = jFreeChartModel.getMeterRepositoryPath() + "\\ProgressChart\\";
                File file = new File(folder_path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String path = folder_path + year + "_" + str_month + "_" + day_of_month + ".pdf";
                boolean writeChartToPDF = writeChartToPDF(jFreeChartModel.generateBarChart(parameter, junction_id, date), response, 600, 700, path);
                if (writeChartToPDF == true) {
                    System.out.println("path " + path + " received.");
                    try {
                        OutputStream os = response.getOutputStream();
                        File f = new File(path);
                        InputStream is = new FileInputStream(f);
                        byte[] buf = new byte[32 * 1024];
                        int nRead = 0;
                        while ((nRead = is.read(buf)) != -1) {
                            os.write(buf, 0, nRead);
                        }
                        os.flush();
                        os.close();
                        return;
                    } catch (Exception e) {
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("JFreeChart main thread " + e);
        }
    }

    public static boolean writeChartToPDF(org.jfree.chart.JFreeChart chart, HttpServletResponse response, int width, int height, String fileName) {
        boolean isChartGenerated = false;
        PdfWriter writer = null;

        Document document = new Document();

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = template.createGraphics(width, height, (FontMapper) new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
                    height);

            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);

            Table t = new Table(10, 20);
            com.lowagie.text.pdf.PdfPTable pt = t.createPdfPTable();
            JasperDesign jp = new JasperDesign();
            // JRBand jr= new J
            //jp.setTitle();
        } catch (Exception e) {
            System.out.println("JFreeeChart writeChartToPDF - " + e);
        }
        document.close();
        isChartGenerated = true;
        return isChartGenerated;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
