/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.controller;

import com.survey.complaint.model.AutoComplaintDelayModel;
//import com.ssadvt.tableClasses.ComplaintRegister;
//import com.ssadvt.associate.Model.InstallmentModel;
//import com.ssadvt.general.model.UpdateDbServerModel;
//import com.ssadvt.booking.model.AutoBookingUpdateModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Timer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SimpleScheduleBuilder;
//import org.quartz.Trigger;
//import org.quartz.TriggerBuilder;
//import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Shruti
 */
public class AutoComplaintDelayController extends HttpServlet {
   HttpSession session;

    @Override
    public void init() throws ServletException {
        ServletContext ctx = getServletContext();

        AutoComplaintDelayModel autoComplaintDelayModel = new AutoComplaintDelayModel();
        autoComplaintDelayModel.setDriverClass(ctx.getInitParameter("driverClass"));
        autoComplaintDelayModel.setConnectionString(ctx.getInitParameter("connectionString"));
        autoComplaintDelayModel.setDb_userName(ctx.getInitParameter("db_username"));
        autoComplaintDelayModel.setDb_userPasswrod(ctx.getInitParameter("db_password"));
      //  autoComplaintDelayModel.setSsadvtRepositoryPath(ctx.getInitParameter("ssadvtRepositoryPath"));
     //   bk_update.setReportPathName(ctx.getRealPath("/report/bookedSiteList.jrxml"));
/*
        InstallmentModel installment = new InstallmentModel();
        installment.setDriverClass(ctx.getInitParameter("driverClass"));
        installment.setConnectionString(ctx.getInitParameter("connectionString"));
        installment.setDb_userName(ctx.getInitParameter("db_userName"));
        installment.setDb_userPasswrod(ctx.getInitParameter("db_userPasswrod"));
*/
 //        bk_update.setConnection();
        Timer timer = new Timer();
     //   Calendar date = Calendar.getInstance();
        Calendar date1 = Calendar.getInstance();
        autoComplaintDelayModel.setConnection();
        if (autoComplaintDelayModel.getComplaintSendSmsMailStatus()) {
        } else {
            date1.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println("date or calender " + date1.getTime());
        }
        date1.set(Calendar.HOUR_OF_DAY, 0);
        date1.set(Calendar.MINUTE, 0);
        date1.set(Calendar.SECOND, 0);
        date1.set(Calendar.MILLISECOND, 0);
        System.out.println("calender date or calender" + date1.getTime());
        timer.schedule(autoComplaintDelayModel, date1.getTime(), 1000 * 60 * 60 * 24);

     /*   // If entry NOT found
        if (bk_update.getBookingUpdateStatus()) {
        } else {
            date.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println("date or calender" + date.getTime());
        }
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        System.out.println("date or calender" + date.getTime());
        timer.schedule(bk_update, date.getTime(), 1000 * 60 * 60 * 24);
        try {
            JobDetail job = JobBuilder.newJob(UpdateDbServerModel.class).withIdentity("dummyJobName", "group1").build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1).repeatForever()).build();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
        }
   */
      }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        session = request.getSession();
        String role = (String) session.getAttribute("user_role");
        //((Integer)session.getAttribute("user_id")).intValue();
        try {
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
