/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.model;

import com.survey.complaint.tableClasses.ComplaintRegister;
import com.survey.mail.SendMailModel;
import com.survey.mail.SmsBean;
import com.survey.mail.MailBean;
import com.survey.util.SendMailSmsModel;
import com.survey.util.MailSmsCredentialModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.SQLException;
//import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Shruti
 */
public class ComplaintAssingedForModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private String message;
    private String msgBgColor;
    private String sms_mail_message;
    private String sms_mail_msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setDriverClass(String driverclass) {
        this.driverClass = driverclass;
    }

    public void setConnectionString(String connectionstring) {
        this.connectionString = connectionstring;
    }

    public void setDb_userName(String username) {
        this.db_userName = username;
    }

    public void setDb_userPasswrod(String pass) {
        this.db_userPasswrod = pass;
    }
    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public String getSmsMailMessage() {
        return sms_mail_message;
    }

    public String getSmsMailMsgBgColor() {
        return sms_mail_msgBgColor;
    }
    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPasswrod);
        } catch (Exception e) {
            System.out.println("AdAssoSiteDetailsModel setConnection() Error: " + e);
        }
    }

    /*public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("ComplaintRegisterModel setConnection() Error: " + e);
        }
    }*/

    public Map getStatuslist() {

        Map<Integer, String> statuslist = new HashMap<Integer, String>();
        try {

            String query = " select * from complaint_status";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                statuslist.put(rst.getInt("complaint_status_id"), rst.getString("complaint_status"));
            }


        } catch (Exception e) {
        }
        return statuslist;
    }

    public Map getComplintTypelist() {
        Map<Integer, String> complaintTypelist = new HashMap<Integer, String>();
        try {
            String query = " select * from complaint_type  ct ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                complaintTypelist.put(rst.getInt("complaint_type_id"), rst.getString("complaint_type"));
            }


        } catch (Exception e) {
        }
        return complaintTypelist;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ComplaintRegisterModel CloseConnection() Error: " + e);
        }
    }


public int complaintAssingedFor(String keyPersonName) {
        int key_person_id = 0;
        String query = "SELECT key_person_id FROM key_person k where key_person_name=?";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, keyPersonName);
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                key_person_id = rset.getInt("key_person_id");
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel " + e);
        }
        return key_person_id;
    }

  /*  public int reviseRecord(ComplaintRegister com_reg) {
        int rowsAffected = 0;
       //  int complaint_status_id=2;
        String query = null;
        query = " insert into complaint_register( complaint_type_id,"
                + " complaint_status_id, posted_by, posted_date, complaint_date, assinged_for, "
                + " remark , complition_date , complaint_register_id , complaint_revision_no,sub_site_id) "
                + " values(?, ?, ?, current_date(), ?, ?, ?, ADDDATE(? ,1) , ? , "
                + "  ( select max(c.complaint_revision_no)+1 from complaint_register  c "
                + " where c.complaint_register_id = ?),? )";
        String[] complaint_reg_no = com_reg.getComplaint_register_no();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < complaint_reg_no.length; i++) {

                pstmt = connection.prepareStatement(query);
                //pstmt.setInt(1, getsite_id(com_reg.getSite_name()[i]));
                pstmt.setInt(1, Integer.parseInt(com_reg.getComplaint_type()[i].trim()));
                pstmt.setInt(2, Integer.parseInt(com_reg.getStatus()[i].trim()));
             //   pstmt.setInt(3,complaint_status_id);
                pstmt.setInt(3, getkey_person_id(com_reg.getPosted_by()[i]));
                pstmt.setDate(4, convertToSqlDate(com_reg.getComplaint_date()[i]));
                if (com_reg.getAssigned_for()[i] != null && !com_reg.getAssigned_for()[i].isEmpty()) {
                    pstmt.setInt(5, getkey_person_id(com_reg.getAssigned_for()[i]));
                } else {
                    pstmt.setNull(5, java.sql.Types.INTEGER);
                }
                pstmt.setString(6, com_reg.getRemark()[i]);
                pstmt.setDate(7, convertToSqlDate(com_reg.getComplaint_date()[i]));
                pstmt.setInt(8, Integer.parseInt(complaint_reg_no[i].trim()));
                pstmt.setInt(9, Integer.parseInt(complaint_reg_no[i].trim()));
                pstmt.setInt(10,Integer.parseInt(com_reg.getAd_sub_site_name()[i]));
                rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    String updatequery = " update complaint_register set active='N',complaint_status_id=3"
                            + " where complaint_register_id =?   and "
                            + " complaint_revision_no = ?  ";
                    pstmt = connection.prepareStatement(updatequery);
                    pstmt.setInt(1, Integer.parseInt(complaint_reg_no[i].trim()));
                    pstmt.setInt(2, com_reg.getCompalint_rev_no());
                  rowsAffected=  pstmt.executeUpdate();

                }

            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
            e.printStackTrace();
        }
        if (rowsAffected > 0) {
            message = "Record Revised successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot Revise the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
*/

public  int  complaintAssingedFor(String[] com_reg_id,String[] compalint_rev_no,String[] assinged_for,String[] check_print) throws SQLException{
       int rowsAffected = 0;
       ComplaintRegisterModel crModel = new ComplaintRegisterModel();
       crModel.setConnection(connection);
       PreparedStatement pstmt = null, pst = null ;
       int count = 0;
        boolean autocommit = false;
        connection.setAutoCommit(false);
        String messages=" ";
        String complaint_type=" ";
        String mob_no=" ", email_id = "";
        try {

     //String query= "update   complaint_register  set assinged_for=?,complaint_status_id=2 "
                   //+ " where complaint_register_id=? and complaint_revision_no=? ";

       String selctQuery = "SELECT complaint_sub_type_id, complaint_status_id, posted_by, posted_date, complaint_date, remark, complition_date, pole_id FROM complaint_register WHERE complaint_register_id=? AND active = 'Y';";

       String query = " insert into complaint_register( complaint_sub_type_id,"
                + " complaint_status_id, posted_by, posted_date, complaint_date, assinged_for, "
                + " remark , complition_date , complaint_register_id , complaint_revision_no,pole_id, assign_date) "
                + " values(?, 2, ?, ?, ?, ?, ?, ?, ?, "
                + "  ( select max(c.complaint_revision_no)+1 from complaint_register  c "
                + " where c.complaint_register_id = ?),?, current_date())";
            
          //  ComplaintRegister com_reg = new ComplaintRegister();
               autocommit = connection.getAutoCommit();
            for (int i = 0; i <assinged_for.length; i++) {

                if (check_print[i].equals("1")) {
                    pst = connection.prepareStatement(selctQuery);
                    pst.setInt(1, Integer.parseInt(com_reg_id[i]));
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()){
                        pstmt = connection.prepareStatement(query);
                        pstmt.setInt(1, rs.getInt("complaint_sub_type_id"));
                        pstmt.setInt(2, rs.getInt("posted_by"));
                        pstmt.setDate(3, rs.getDate("posted_date"));
                        pstmt.setDate(4, rs.getDate("complaint_date"));
                        pstmt.setInt(5,complaintAssingedFor(assinged_for[i]));
                        pstmt.setString(6, rs.getString("remark"));
                        pstmt.setDate(7, rs.getDate("complition_date"));
                        pstmt.setInt(8,Integer.parseInt(com_reg_id[i]));
                        pstmt.setInt(9,Integer.parseInt(com_reg_id[i]));
                        pstmt.setInt(10, rs.getInt("pole_id"));
                        //pstmt.setInt(3,Integer.parseInt(compalint_rev_no[i]));
                        rowsAffected = pstmt.executeUpdate();

                 if(rowsAffected > 0){
                    rowsAffected = cancelRecord(Integer.parseInt(com_reg_id[i]), Integer.parseInt(compalint_rev_no[i]));
                 }
                 if(rowsAffected>0) {
              String query1= "select cr.assinged_for, complaint_register_id,pole_no, kp.mobile_no1,ct.complaint_type, cst.complaint_sub_type, kp.email_id1 "

                  + " from complaint_register cr,key_person kp,complaint_type ct, complaint_sub_type cst, pole p "
                  + " where  cr.complaint_register_id=? and cr.active='Y' AND p.pole_id=cr.pole_id AND p.active='Y' AND cr.active='Y'"
                  //+ " and cr.complaint_revision_no=? "
                  + " and kp.key_person_name= ?  and cr.complaint_sub_type_id=cst.complaint_sub_type_id and ct.complaint_type_id=cst.complaint_type_id ";
                   pstmt = connection.prepareStatement(query1);
                   pstmt.setInt(1,Integer.parseInt(com_reg_id[i]));
                   //pstmt.setInt(2,Integer.parseInt(compalint_rev_no[i]));
                   pstmt.setString(2,assinged_for[i]);
                   ResultSet rset = pstmt.executeQuery();
                  if(rset.next()) {
                        mob_no =rset.getString("mobile_no1");
                        email_id = rset.getString("email_id1");
                        complaint_type = rset.getString("complaint_sub_type");
                       if(complaint_type==null)
                        complaint_type="";


                        messages ="You have assinged "
                            + "\n  Complaint Type- " + complaint_type
                            + "\n  Complaint no-  " +rset.getInt("complaint_register_id")
                            + "\n  Site Name- " + rset.getString("pole_no");
                        String subject = "Complaint";
                        String mail_direction = "outgoing";
                        int assigned_for = rset.getInt("assinged_for");

                      sendSmsToAssignedFor(mob_no,messages);
                      //sendMail_Posted_by(messages, subject, email_id);
                      //insertMailRecords(rset.getInt("key_person_id"),rset.getInt("key_person_id"), mail_direction,rset.getInt("complaint_register_id") + "");
                      crModel.sendMail_Assigned_For(messages, subject, email_id);
                      crModel.insertMailRecords(assigned_for,assigned_for, mail_direction, rset.getInt("complaint_register_id") + "");
                        // }
                    } //end  inner if  loop
                    } //end if condition
                 }  //end of outer if
                }
           count++;
            }// end of for loop
           if (rowsAffected > 0 && count ==assinged_for.length) {
                connection.commit();
                rowsAffected = 1;
                connection.setAutoCommit(autocommit);
                message = "Complaint assingedFor  saved successfully.";
                 msgBgColor = COLOR_OK;
        } else {
           rowsAffected= 0;
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        } catch (Exception e) {

            rowsAffected = 0;
            connection.rollback();
            connection.setAutoCommit(autocommit);
            System.out.println("Error in  complaint Assinged model" + e);
        }

        return rowsAffected;
   }

   public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
        String result = "";
        try {

            String query = "SELECT si.user_name, si.user_password, si.sender_id FROM sms_info si ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            String host_url = "http://api.mVaayoo.com/mvaayooapi/MessageCompose?";
            String user_name = rset.getString("user_name");        // e.g. "jpss1277@gmail.com"
            String user_password = rset.getString("user_password"); // e.g. "8826887606"
            String sender_id = java.net.URLEncoder.encode(rset.getString("sender_id"), "UTF-8");         // e.g. "TEST+SMS"

            System.out.println("messageStr1 is =" + messageStr1);
            messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");

            String queryString = "user=" + user_name + ":" + user_password + "&senderID=" + sender_id + "&receipientno=" + numberStr1 + "&msgtxt=" + messageStr1;
            String url = host_url + queryString;
            result = callURL(url);
            System.out.println("SMS URL: " + url);
        } catch (Exception e) {
            result = e.toString();
            System.out.println("SMSModel sendSMS() Error: " + e);
        }
        return result;
    }

    private String callURL(String strURL) {
        String status = "";
        try {
            java.net.URL obj = new java.net.URL(strURL);
            HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
            httpReq.setDoOutput(true);
            httpReq.setInstanceFollowRedirects(true);
            httpReq.setRequestMethod("GET");
            status = httpReq.getResponseMessage();
        } catch (MalformedURLException me) {
            status = me.toString();
        } catch (IOException ioe) {
            status = ioe.toString();
        } catch (Exception e) {
            status = e.toString();
        }
        return status;
    }

    public int cancelRecord(int complaint_register_id, int rev_no ) {
        //String query = "DELETE FROM complaint_register WHERE complaint_register_id = " + complaint_register_id;
        String query = " update complaint_register set active='N',complaint_status_id=3"
                            + " where complaint_register_id =?   and "
                            + " complaint_revision_no = ?  ";
        int rowsAffected = 0;
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, complaint_register_id);
            pst.setInt(2, rev_no);
            rowsAffected = pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: ComplaintAssignedForModel" + e);
        }
        if (rowsAffected > 0) {
            message = "Record Save successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot Save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

public int getNoOfRows(String search_assigned_for,int search_type_of_complaint_no ) {
        int noOfRows = 0;

      
        try {
           /* String query = "select count(*)"
                    + "   from complaint_register cr "                
                    + "   , complaint_status cs ,complaint_type ac, ad_site  ad, "
                    + "  key_person k1 "
                    + "      where cr.site_id= ad.site_id "
                    + "  and cs.complaint_status_id= cr.complaint_status_id and cr.complaint_type_id ="
                    + "    ac.complaint_type_id and k1.key_person_id= cr.posted_by "
                    + "   and  cr.active ='Y' ";  */

        String query = "select   count(*)"
       
                + "  from complaint_register cr "
                + "  left join( key_person k2 ) on k2.key_person_id = cr.assinged_for  "
                + "  ,complaint_status cs ,complaint_type ct, complaint_sub_type cst, pole p, "
                + "  key_person k1 "//, advertising_sub_type2 ad2 , advertising_sub_type1  ad1,ad_mounting am
                + "  where cr.pole_id=p.pole_id AND p.active = 'Y' "// and ad.advertising_sub_type2_id = ad2.advertising_sub_type2_id "
                //+ "  and  ad1.advertising_sub_type1_id = ad2.advertising_sub_type1_id "
                + "  and cs.complaint_status_id= cr.complaint_status_id and cr.complaint_sub_type_id = cst.complaint_sub_type_id AND "
                + "  ct.complaint_type_id = cst.complaint_type_id and k1.key_person_id= cr.posted_by "// and ss.mounting_id=am.mounting_id"
                + "  and cr.active ='Y' "
            //    + "  if('" + posted_by + "'='',k1.key_person_name like'%', k1.key_person_name='" + posted_by + "') "
                + "  and if('" + search_assigned_for + "'='',(k2.key_person_name like'%' or k2.key_person_name is null), k2.key_person_name='" + search_assigned_for + "') "
                + "  and "
                 + "  if(" + search_type_of_complaint_no + "=0,cr.complaint_register_id  like'%',cr.complaint_register_id =" + search_type_of_complaint_no + " ) ";

            PreparedStatement pst = connection.prepareStatement(query);
            // pst.setString(1, searchPrintMedia);
        //    pst.setDate(1, search_complaint_date_from);
         //   pst.setDate(2, search_complaint_date_to);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error:ComplaintRegisterModel " + e);
        }
        return noOfRows;
    }
    public List<String> getComplaintNoList(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        // String query = "SELECT complaint_no FROM complaint_feedback ";
        String query = " select complaint_register_id from complaint_register where active ='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                int complaint_register_id = rset.getInt("complaint_register_id");

                String complaint_no_list = Integer.toString(complaint_register_id);
                //  if (feedback_type.toUpperCase().startsWith(q.toUpperCase())) {
                list.add(complaint_no_list);
                count++;
                //    }
            }
            if (count == 0) {
                list.add("No Such Type Of Compalint Number  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintAssingedForModel --getComplaintNoList-- " + e);
        }
        return list;
    }

     public List<String> getAssignedForSearchlist(String q) {

        List<String> assigned_for_list = new ArrayList<String>();
         q = q.trim();
        int count = 0;
        try {
             String query =" SELECT key_person_name "
                     + "  FROM key_person k, org_office o,organisation_name oc, organisation_map om,organisation_type ot,designation ds"
                     + " where k.org_office_id=o.org_office_id "
                     + " and o.organisation_id= oc.organisation_id  and oc.organisation_id= om.organisation_id "
                     +"  and om.organisation_type_id=ot.organisation_type_id and  org_type_name='Home' and k.designation_id=ds.designation_id and ds.designation='Technician' ORDER BY key_person_name ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                assigned_for_list.add(rst.getString("key_person_name"));
                 count++;
            }
             if (count == 0) {
                assigned_for_list.add("No Such Assigned_for  exists.....");
            }
     } catch (Exception e) {
        }
        return assigned_for_list;
    }
     public List<String> getAssignedForlist(String q) {

        List<String> assigned_for_list = new ArrayList<String>();
         q = q.trim();
        int count = 0;
        try {
             String query =" SELECT key_person_name "
                     + "  FROM key_person k, org_office o,organisation_name oc, organisation_map om,organisation_type ot,designation ds"
                     + " where k.org_office_id=o.org_office_id "
                     + " and o.organisation_id= oc.organisation_id  and oc.organisation_id= om.organisation_id "
                     +"  and om.organisation_type_id=ot.organisation_type_id and  org_type_name='Home' and k.designation_id=ds.designation_id and ds.designation='Technician' ORDER BY key_person_name ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                assigned_for_list.add(rst.getString("key_person_name"));
                 count++;
            }
             if (count == 0) {
                assigned_for_list.add("No Such Assigned_for  exists.....");
            }
     } catch (Exception e) {
        }
        return assigned_for_list;
    }

    public List<ComplaintRegister> showData(int lowerLimit, int noOfRowsToDisplay,String search_assigned_for,int search_type_of_complaint_no) {
        List<ComplaintRegister> list = new ArrayList<ComplaintRegister>();
     
        String query = "select  complaint_revision_no ,concat_ws(' ',p.pole_no,', ', spd.switching_point_name) as pole_no, p.pole_id,  complaint_status , complaint_type, complaint_sub_type,"
                + "  date_format(complaint_date,'%d-%m-%Y') as complaint_date , "
                + "  date_format(complition_date,'%d-%m-%Y') as complition_date , "
                + "  complaint_register_id ,"
                + "  k1. key_person_name as posted_person,k2.key_person_name as assigned_person, "
                + "  cr.complaint_sub_type_id, cr.complaint_status_id, posted_by, posted_date, complaint_date, "
                + "  cr.remark, complition_date "
                + "  from complaint_register cr "
                + "  left join( key_person k2 ) on k2.key_person_id = cr.assinged_for  "
                + "  ,complaint_status cs ,complaint_type ct, complaint_sub_type cst, pole p, "
                + "  key_person k1, switching_point_detail spd " //, advertising_sub_type2 ad2 , advertising_sub_type1  ad1,ad_mounting am "
                + "  where spd.switching_point_detail_id = p.switching_point_detail_id AND spd.active='Y' AND p.pole_id = cr.pole_id AND p.active='Y' "
                //+ "  and  ad1.advertising_sub_type1_id = ad2.advertising_sub_type1_id "
                + "  and cs.complaint_status_id= cr.complaint_status_id and cr.complaint_sub_type_id = cst.complaint_sub_type_id "
                + "  AND ct.complaint_type_id = cst.complaint_type_id and k1.key_person_id= cr.posted_by "
                + "  and cr.active ='Y' "          
            //    + "  if('" + posted_by + "'='',k1.key_person_name like'%', k1.key_person_name='" + posted_by + "') "
                + "  and if('" + search_assigned_for + "'='',(k2.key_person_name like'%' or k2.key_person_name is null), k2.key_person_name='" + search_assigned_for + "') "                  
                + "  and "
                 + "  if(" + search_type_of_complaint_no + "=0,cr.complaint_register_id  like'%',cr.complaint_register_id =" + search_type_of_complaint_no + " ) "
                 + " Order by cr.complaint_register_id desc"
                 + "  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pst = connection.prepareStatement(query);
//            pst.setDate(1, search_complaint_date_from);
//            pst.setDate(2, search_complaint_date_to);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                ComplaintRegister com_reg = new ComplaintRegister();
                com_reg.setComplaint_reg_no(rset.getInt("complaint_register_id"));               
                com_reg.setSite_nameM(rset.getString("pole_no"));
                com_reg.setPosted_byM(rset.getString("posted_person"));
                com_reg.setAssigned_forM(rset.getString("assigned_person"));
                com_reg.setStatusM(rset.getString("complaint_status"));
                com_reg.setComplaint_typeM(rset.getString("complaint_type"));
                com_reg.setComplaint_sub_typeM(rset.getString("complaint_sub_type"));
                com_reg.setComplaint_dateM(rset.getString("complaint_date"));
                com_reg.setComplition_date(rset.getString("complition_date"));
                com_reg.setRemarkM(rset.getString("remark"));
                //com_reg.setAd_sub_type1(rset.getString("advertising_sub_type1_name"));
                //com_reg.setAd_sub_type2(rset.getString("advertising_sub_type2_name"));
                com_reg.setCompalint_rev_no(rset.getInt("complaint_revision_no"));
                //com_reg.setSub_site_name(rset.getString("name"));
                list.add(com_reg);
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
        }
        return list;
    }
  
}
