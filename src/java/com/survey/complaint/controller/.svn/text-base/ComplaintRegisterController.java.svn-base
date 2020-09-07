/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.controller;

import com.survey.complaint.model.AutoComplaintDelayModel;
import com.survey.complaint.model.ComplaintRegisterModel;
import com.survey.complaint.tableClasses.ComplaintRegister;
import com.survey.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.survey.dbcon.DBConnection;
import java.io.ByteArrayOutputStream;
import java.util.jar.Attributes.Name;

/**
 *
 * @author Shruti
 */
public class ComplaintRegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        try {
            String task = null;
            List list = null;
            String input = null;
            String param = null;
            int i = 0;
            HttpSession session = request.getSession(false);
            /*if (session == null || session.getAttribute("user_name") == null) {
                response.sendRedirect("beforelogin.jsp");
                return;
            }*/
            ComplaintRegisterModel complregisterModel = new ComplaintRegisterModel();
          //  AutoComplaintDelayModel  complregisterModel = new AutoComplaintDelayModel();
            ServletContext ctx = getServletContext();
            complregisterModel.setDriverClass(ctx.getInitParameter("driverClass"));
            complregisterModel.setConnectionString(ctx.getInitParameter("connectionString"));
            complregisterModel.setDb_userName(ctx.getInitParameter("db_username"));
            complregisterModel.setDb_userPasswrod(ctx.getInitParameter("db_password"));
            complregisterModel.setConnection();
            /*try {
                complregisterModel.setConnection(DBConnection.getConnectionForUtf(ctx, session));
            } catch (Exception e) {
                System.out.println("error in ComplaintRegisterController setConnection() calling try block" + e);
            }*/
            String delayed_work = "";
            String jqstring = request.getParameter("action");
            String q = request.getParameter("q");
            String response_ad_sub_site_name;
            String response_ad_sub_site_data;
            String response_site_name;
            String site_name;
            String complaintDateFrom = null, complaintDateTo = null;
            response.setContentType("text/html");
            String cmpl_type = request.getParameter("action1");
            if (jqstring != null) {
                PrintWriter out = response.getWriter();             
                input = request.getParameter("q").trim();
               // if (jqstring != null) {
                //    param = request.getParameter("Param");
                /*if (jqstring.equals("siteList")) {
                    String ad_sub_type2 = request.getParameter("add_sub_type2");
                    list = complregisterModel.getsiteList(ad_sub_type2);
                    i = 1;
                  }*/
                if (jqstring.equals("getPole_No")) {
                    String sp = request.getParameter("action2");
                    if (cmpl_type.equals("Pole")) {
                        list = complregisterModel.getPoleNo(q, sp);
                    }else if(cmpl_type.equals("All")){
                        list = complregisterModel.getAllPoleNo(q, sp);
                    }else {
                        list = complregisterModel.getSwitchingPoleNo(q, sp);
                    }
                }
                 else if (jqstring.equals("getPole_No_Id")) {
                    String pole_no = request.getParameter("pole_no");
                        String a = complregisterModel.getSPIDNameByPole(pole_no);
                        out.println(a);
                        return;
                }
                else if (jqstring.equals("getSwitchingPoint")) {
                        list = complregisterModel.getSwitchingPoint(q, cmpl_type);
                        //out.println(a);
                        //return;
                }else if (jqstring.equals("getSp_Id")) {
                    String sp_name = request.getParameter("sp_name");
                        String a = complregisterModel.getSwitchingPointID(sp_name);
                        out.println(a);
                        return;
                }
                  else if (jqstring.equals("PostedBy")) {
                  list = complregisterModel.getPostedBy();
               }
                 else if (jqstring.equals("AssingedFor")) {
                  list = complregisterModel.getAssingedFor();                                                                                                                   
               }
                else if (jqstring.equals("ComplaintNo")) {
                  list = complregisterModel.getComplaintNo(input);
               }
                complregisterModel.closeConnection();
                Iterator itr = list.iterator();
                int count = 0;
                while (itr.hasNext()) {
               String   data = (String) itr.next();
                    if (data.toUpperCase().startsWith(input.toUpperCase())) {
                        out.println(data);
                        count++;
                    }
                }

                if (count == 0 && i == 1) {
                    out.print("No Such Site Exists.");

                }

                return;
            } else {
                task = request.getParameter("task");
                if (task == null) {
                    task = "";
                }
                if (request.getParameter("search_compalint_date_from") != null && !request.getParameter("search_compalint_date_from").isEmpty()) {
                    complaintDateFrom = request.getParameter("search_compalint_date_from");
                    System.out.println("cotrller page search_compalint_date_from " + complaintDateFrom);
                }
                if (request.getParameter("search_compalint_date_to") != null && !request.getParameter("search_compalint_date_to").isEmpty()) {
                    complaintDateTo = request.getParameter("search_compalint_date_to");
                    //    System.out.println("cotrller page search_compalint_date_to "+complaintDateTo);
                }

         //  if(jqstring1.equals("getAddSubSite")){
        //    site_name=request.getParameter("site_name");
        //    response_ad_sub_site="hello world";
         //   return;
         //  }

                //  String checked="";
                //  checked=request.getParameter("sms");
                //  System.out.println(checked);

                if (task.equals("compliantTypeList")) {
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out = response.getWriter();
                    Map map = complregisterModel.getComplintTypelist();
                    String response_data = map.toString();
                    out.println(response_data);
                    return;


                }
              //
              else  if (task.equals("getSiteName")) {
                       response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out = response.getWriter(); 
                  site_name =request.getParameter("site_name");
                       System.out.println(site_name);
                        response_site_name=complregisterModel.getSiteName(site_name);
                     //   response_data= "hello world";
                        // response_data = compFeedbackModel.getComplaintNoDetails(com);

                       out.println(response_site_name );
                   
                
                    
                    return ;
                }

                //
                else if (task.equals("getAddSubSiteName")) {
                  response.setContentType("text/html");
                  PrintWriter out = response.getWriter();
                  out = response.getWriter();
                //  site_name=request.getParameter("site_name");
               //   Map map=complregisterModel.getAdSubSite("site_name");
                //  response_ad_sub_site =map.toString();
            //    if(request.getParameter("site_name") != null && !request.getParameter("site_name").isEmpty()) {
                    response_ad_sub_site_name=request.getParameter(" sub_site_name");
                   response_ad_sub_site_name=complregisterModel.getAdSubSiteName(response_ad_sub_site_name);
               //  Map map=complregisterModel.getAdSubSite(site_name);
                 // response_ad_sub_site_name =map.toString();
                     // response_ad_sub_site=site_name;
               //System.out.println("response_ad_sub_site_name="+response_ad_sub_site_name);
                   out.println(response_ad_sub_site_name);
                  return ;
              //      }
              }   

              else if (task.equals("getAddSubSite")) {
                  response.setContentType("text/html");
                  PrintWriter out = response.getWriter();
                  out = response.getWriter();
          
                    site_name=request.getParameter("site_name");
                   response_ad_sub_site_name=complregisterModel.getAdSubSite(site_name);              
                   out.println(response_ad_sub_site_name);
                  return ;
              //      }
              }

              else if (task.equals("getComplaintStatus")) {
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out = response.getWriter();
                    Map map = complregisterModel.getComplaintStatusList();
                    String response_data = map.toString();
                    out.println(response_data);
                    return;
              }

              else if(task.equals("Delay Complaint")){
            //   AutoComplaintDelayModel  autoComplaintDelayModel =new AutoComplaintDelayModel();

            //   autoComplaintDelayModel.autoSendSmsAndMail();
              complregisterModel.autoSendSmsAndMail();
              }
                else if (task.equals("Send")) {
                    //ComplaintRegister complaint_regst = new ComplaintRegister();

                    //   complaint_regst.setComplaint_register_no(request.getParameter("is_check"));
                    //      complregisterModel.sendSmsMail(Integer.parseInt(request.getParameter("complaint_reg_no")));
                    String[] complaint_reg = request.getParameterValues("com_reg_id");
                    String sms_check = request.getParameter("sms_check");
                    String mail_check = request.getParameter("mail_check");
                    String[] check_print = request.getParameterValues("is_check");

                    complregisterModel.sendSmsMail(check_print, complaint_reg, sms_check, mail_check);
                    //             complaint_regst.setComplaint_register_no(check_print);
        

                } else if (task.equals("Delete")) {
                    complregisterModel.cancelRecord(Integer.parseInt(request.getParameter("complaint_reg_no")));  // Pretty sure that meter_id will be available.
                } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Add All Records") || task.equals("Revise")||task.equals("Update")) {
                    try {
                        ComplaintRegister complaint_reg = new ComplaintRegister();
                        String complaint_reg_no[] = new String[1];
                        try {
                            complaint_reg_no[0] = request.getParameter("complaint_reg_no");            // meter_id may or may NOT be available i.e. it can be update or new record.
                        } catch (Exception e) {
                            complaint_reg_no[0] = "0";
                        }
                        if (task.equals("Save AS New")) {
                            complaint_reg_no[0] = "0";
                            String complaint_reg_no1[] = {"1"};
                            complaint_reg.setComplaint_register_no(complaint_reg_no1);
                        } else if (task.equals("Save")) {
                            complaint_reg.setComplaint_register_no(complaint_reg_no);
                        }
                        complaint_reg.setSite_name(request.getParameterValues("site_name"));
                       
                        complaint_reg.setAd_sub_site_name(request.getParameterValues("pole_no"));
                         String ss= request.getParameter("ad_sub_site_name");
                        System.out.println("ad_sub_site_name=" +ss);

                        complaint_reg.setComplaint_type(request.getParameterValues("complaint_sub_type"));
                        complaint_reg.setStatus(request.getParameterValues("status"));
                        complaint_reg.setComplaint_date(request.getParameterValues("complaint_date"));
                        complaint_reg.setPosted_by(request.getParameterValues("posted_by"));
                        complaint_reg.setAssigned_for((request.getParameterValues("assigned_for")));
                        complaint_reg.setRemark(request.getParameterValues("remark"));



                        if (task.equals("Add All Records")) {
                            complaint_reg.setComplaint_register_no(request.getParameterValues("complaint_reg_no"));
                        }
                        if (complaint_reg_no[0].equals("0") || task.equals("Add All Records")) {
                            complregisterModel.insertRecord(complaint_reg);
                        } else if (task.equals("Revise")) {
                            complaint_reg.setCompalint_rev_no(Integer.parseInt(request.getParameter("compalint_rev_no")));
                            complaint_reg.setComplaint_register_no(complaint_reg_no);
                            complregisterModel.reviseRecord(complaint_reg);
                        } /*else {
                            complaint_reg.setCompalint_rev_no(Integer.parseInt(request.getParameter("compalint_rev_no")));// update existing record.
                            complaint_reg.setComplaint_register_no(complaint_reg_no);
                            complregisterModel.updateRecord(complaint_reg);
                        }*/
                    } catch (Exception e) {
                        System.out.println("Error - " + e);
                    }
                } else if (task.equals("Show All Records")) {
                    // searchPrintMedia = "";
                }
            }
      //if (request.getAttribute("isSelectPriv").equals("Y")) {
            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            }
            if (task.equals("Search") || task.equals("clear")) {

                lowerLimit = 0;
            }

            int s_status = 0;
            String s_posted_by = "", s_assigned_for = "";
             int s_complaint_no=0;            
            if (request.getParameter("s_posted_by") != null && !request.getParameter("s_posted_by").isEmpty()) {
                s_posted_by = request.getParameter("s_posted_by");
                request.setAttribute("s_posted_by", s_posted_by);
            }
            if (request.getParameter("s_status") != null && !request.getParameter("s_status").isEmpty()) {
                s_status = Integer.parseInt(request.getParameter("s_status"));
                request.setAttribute("s_status", s_status);
            }
            if (request.getParameter("s_assigned_for") != null && !request.getParameter("s_assigned_for").isEmpty()) {
                s_assigned_for = request.getParameter("s_assigned_for");
                request.setAttribute("s_assigned_for", s_assigned_for);
            }
            if(request.getParameter("s_complaint_no")!=null && !request.getParameter("s_complaint_no").isEmpty()){
              s_complaint_no=Integer.parseInt(request.getParameter("s_complaint_no"));
              if(s_complaint_no!=0){
              request.setAttribute("s_complaint_no", s_complaint_no);
            }
            }
             if(request.getParameter("delayed_comp")!=null && !request.getParameter("delayed_comp").isEmpty()){
              delayed_work = "Y";
            }else delayed_work = "N";
             
              if (task.equals("PRINTComplaintReport")) {
                String jrxmlFilePath;
                response.setHeader("Content-Type", "text/plain; charset=UTF-8");
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/complaint/complaintReport.jrxml");
                delayed_work = request.getParameter("delayed_work");
                byte[] reportInbytes = complregisterModel.generateComplaintReportPdf(jrxmlFilePath, s_posted_by, s_assigned_for, s_status, delayed_work);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                complregisterModel.closeConnection();
                return;

            }
           //  if(){
                 
         //    }
             if (task.equals("PRINTComplaintExcelReport")) {
                String jrxmlFilePath;
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/complaint/complaintReport.jrxml");
                        delayed_work = request.getParameter("delayed_work");
                        ByteArrayOutputStream reportInbytes = complregisterModel.generateComplaintReportExcel(jrxmlFilePath, s_posted_by, s_assigned_for, s_status,delayed_work);
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment;filename=ComplaintReport.xls");
                        response.setContentLength(reportInbytes.size());
                        servletOutputStream.write(reportInbytes.toByteArray());
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        complregisterModel.closeConnection();
                        return;
            }

            noOfRowsInTable = complregisterModel.getNoOfRows(s_status, s_posted_by, s_assigned_for, complaintDateFrom, complaintDateTo,s_complaint_no, delayed_work);                  // get the number of records (rows) in the table.
            if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
            else if (buttonAction.equals("Previous")) {
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0) {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else {
                    lowerLimit = temp;
                }
            } else if (buttonAction.equals("First")) {
                lowerLimit = 0;
            } else if (buttonAction.equals("Last")) {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Add All Records") || task.equals("Send")||task.equals("Revise")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }
            // Logic to show data in the table.
            List<ComplaintRegister> ComplaintRegisterList = complregisterModel.showData(lowerLimit, noOfRowsToDisplay, s_status, s_posted_by, s_assigned_for, complaintDateFrom, complaintDateTo,s_complaint_no, delayed_work);
            lowerLimit = lowerLimit + ComplaintRegisterList.size();
            noOfRowsTraversed = ComplaintRegisterList.size();


            // Now set request scoped attributes, and then forward the request to view.
            // Following request scoped attributes NAME will remain constant from module to module.
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("ComplaintRegisterList", ComplaintRegisterList);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
      //}//isPrevle
            request.setAttribute("complaintTypeList", complregisterModel.complaintTypeList());
            request.setAttribute("complaintSubTypeList", complregisterModel.complaintSubTypeList());
            request.setAttribute("divisionList", complregisterModel.divisionList());
            request.setAttribute("zoneList", complregisterModel.zoneList());
            request.setAttribute("feederList", complregisterModel.feederList());
            request.setAttribute("switchingPointList", complregisterModel.switchingPointList());
            request.setAttribute("search_compalint_date_from", complaintDateFrom);
            request.setAttribute("search_compalint_date_to", complaintDateTo);
         //   request.setAttribute("lowerLimit", lowerLimit);
         //   request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("sms_mail_message", complregisterModel.getSmsMailMessage());
            // request.setAttribute("searchPrintMedia", searchPrintMedia);
            request.setAttribute("sms_mail_msgBgColor", complregisterModel.getSmsMailMsgBgColor());
            request.setAttribute("message", complregisterModel.getMessage());
            // request.setAttribute("searchPrintMedia", searchPrintMedia);
            request.setAttribute("msgBgColor", complregisterModel.getMsgBgColor());
            // Following request scoped attributes NAME will change from module to module.
         //   request.setAttribute("ComplaintRegisterList", ComplaintRegisterList);

            request.setAttribute("statuslist", complregisterModel.getStatuslist());
            // request.setAttribute("complaintTypelist", complregisterModel.getComplintTypelist());
            complregisterModel.closeConnection();
            request.getRequestDispatcher("complaintregister_view").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error int ComplaintReisterController" + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }
}
