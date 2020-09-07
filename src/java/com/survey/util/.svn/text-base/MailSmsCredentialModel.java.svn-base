/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.util;

import com.survey.mail.MailBean;
import com.survey.mail.SmsBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Ritesh
 */
public class MailSmsCredentialModel {

    static Connection connection;
    static String userName = "", userPassword = "";
    static String smsUserName = "", smsUserPassword = "", smsSenderId = "", smsHost = "http://api.mVaayoo.com/mvaayooapi/MessageCompose?";
    int state = 4;
    static String host = "smtp.gmail.com", port = "465";
    MailBean mailBean;
    SmsBean smsBean;

    public MailSmsCredentialModel(Connection conn) {
        connection = conn;
    }

    public MailBean getMailCredentialUser() {
        try {
            String query = "SELECT mi.user_name, mi.user_password FROM mail_info AS mi ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                userName = rset.getString("user_name");
                userPassword = rset.getString("user_password");
            }
        } catch (Exception e) {
            System.out.println("MaiCredentialModel getCredentials Bugg: " + e);
        }
        mailBean = new MailBean();
        mailBean.setUserName(userName);
        mailBean.setUserPassword(userPassword);
        mailBean.setHost(host);
        mailBean.setPort(port);
        return mailBean;
    }

    public SmsBean getSmsCredentialUser() {
        try {
            String query = "SELECT si.user_name, si.user_password, si.sender_id FROM sms_info si ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                smsUserName = rset.getString("user_name");
                smsUserPassword = rset.getString("user_password");
                smsSenderId = java.net.URLEncoder.encode(rset.getString("sender_id"), "UTF-8");
            }
        } catch (Exception e) {
            System.out.println("MaiCredentialModel getCredentials Bugg: " + e);
        }
        smsBean = new SmsBean();
        smsBean.setSmsUserName(smsUserName);
        smsBean.setSmsUserPassword(smsUserPassword);
        smsBean.setSmsHost(smsHost);
        smsBean.setSmsSenderId(smsSenderId);
        smsBean.setState(state);
        return smsBean;
    }
}
