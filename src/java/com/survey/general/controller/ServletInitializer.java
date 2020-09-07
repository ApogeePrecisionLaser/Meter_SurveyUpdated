/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.general.controller;

import com.survey.energyMeter.tcpServer.ClientResponderModel;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Pooja
 */
public class ServletInitializer extends HttpServlet {
    @Override
    public void init() throws ServletException {
        try {
            ServletContext ctx = getServletContext();
            ClientResponderModel clientResponderModel = new ClientResponderModel();
            clientResponderModel.setDriverClass(ctx.getInitParameter("driverClass"));
            clientResponderModel.setConnectionString(ctx.getInitParameter("connectionString"));
            clientResponderModel.setDb_userName(ctx.getInitParameter("db_username"));
            clientResponderModel.setDb_userPasswrod(ctx.getInitParameter("db_password"));
            clientResponderModel.setConnection();
            clientResponderModel.updateErrorStateOfLoggedInJunctions() ;
            clientResponderModel.closeConnection();
        } catch (Exception e) {
            System.out.println("Exception in ServletInitializer: "+e);
        }
    }
  

}
