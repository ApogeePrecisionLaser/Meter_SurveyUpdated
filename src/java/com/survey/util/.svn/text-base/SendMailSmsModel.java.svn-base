/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.util;

import com.survey.mail.MailBean;
import com.survey.mail.SendMailModel;
import com.survey.mail.SmsBean;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 *
 * @author Ritesh
 */
public class SendMailSmsModel {

    public String sendSMS(SmsBean smsBean) {
        String result = "";
        try {
            //strMsg = java.net.URLEncoder.encode(strMsg, "UTF-8");
            String url = smsBean.getSmsHost() + "user=" + smsBean.getSmsUserName() + ":" + smsBean.getSmsUserPassword() + "&senderID=" + smsBean.getSmsSenderId()
                    + "&receipientno=" + smsBean.getRecipientNo() + "&msgtxt=" + smsBean.getMsgText() + "&state=" + smsBean.getState();
            java.net.URL obj = new java.net.URL(url);
            HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
            httpReq.setDoOutput(true);
            httpReq.setInstanceFollowRedirects(true);
            httpReq.setRequestMethod("GET");
            result = httpReq.getResponseMessage();
            System.out.println("SMS URL: " + url);
        } catch (MalformedURLException me) {
            result = me.toString();
        } catch (IOException ioe) {
            result = ioe.toString();
        } catch (Exception e) {
            result = e.toString();
            System.out.println("TempQuotationModel sendSMS() Error: " + e);
        }
        return result;
    }

    public String sendMail(MailBean mailBean) {
        SendMailModel sendMailModel = new SendMailModel();
        sendMailModel.setHost(mailBean.getHost());
        sendMailModel.setPort(mailBean.getPort());
        sendMailModel.setUser_name(mailBean.getUserName());
        sendMailModel.setUser_password(mailBean.getUserPassword());
        sendMailModel.setTo(mailBean.getToEmailList());
        sendMailModel.setBcc(mailBean.getBccEmailList());
        sendMailModel.setCc(mailBean.getCcEmailList());
        sendMailModel.setSubject(mailBean.getSubject());
        sendMailModel.setBodyText(mailBean.getBodyText());
        sendMailModel.setFileName(mailBean.getAttachmentPath());
        return sendMailModel.sendMail();    // return message wether mail was send successfully or not.
    }
}
