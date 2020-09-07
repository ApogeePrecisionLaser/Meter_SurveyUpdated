/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author Navitus1
 */
public class LogErrorBean
{
private String sent_data,recieved_data,error_mess, timestamp,switching_name,status;
private int junction_id,err_mess_id,err_log_id,pro_ver_no;
private static String statusofMessage="false";
private String junction_id1[],imeino[],emno[];

    public String[] getEmno() {
        return emno;
    }

    public void setEmno(String[] emno) {
        this.emno = emno;
    }

    public String[] getImeino() {
        return imeino;
    }

    public void setImeino(String[] imeino) {
        this.imeino = imeino;
    }   
    public String getStatus() {
        return status;
    }

    public String[] getJunction_id1() {
        return junction_id1;
    }

    public static String getStatusofMessage() {
        return statusofMessage;
    }

    public static void setStatusofMessage(String statusofMessage) {
        LogErrorBean.statusofMessage = statusofMessage;
    }
   

    public void setJunction_id1(String[] junction_id1) {
        this.junction_id1 = junction_id1;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getErr_log_id() {
        return err_log_id;
    }

    public void setErr_log_id(int err_log_id) {
        this.err_log_id = err_log_id;
    }

    public int getErr_mess_id() {
        return err_mess_id;
    }

    public void setErr_mess_id(int err_mess_id) {
        this.err_mess_id = err_mess_id;
    }

    public String getError_mess() {
        return error_mess;
    }

    public void setError_mess(String error_mess) {
        this.error_mess = error_mess;
    }

    public int getJunction_id() {
        return junction_id;
    }

    public void setJunction_id(int junction_id) {
        this.junction_id = junction_id;
    }

    public int getPro_ver_no() {
        return pro_ver_no;
    }

    public void setPro_ver_no(int pro_ver_no) {
        this.pro_ver_no = pro_ver_no;
    }

    public String getRecieved_data() {
        return recieved_data;
    }

    public void setRecieved_data(String recieved_data) {
        this.recieved_data = recieved_data;
    }

    public String getSent_data() {
        return sent_data;
    }

    public void setSent_data(String sent_data) {
        this.sent_data = sent_data;
    }

    public String getSwitching_name() {
        return switching_name;
    }

    public void setSwitching_name(String switching_name) {
        this.switching_name = switching_name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
