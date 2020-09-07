/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.util;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Shruti
 */
public class RepositryPath {

    public static String getRepTempFilePathForSConn(Connection con, int company_id) {
        String tempfilePath = null;
        try {
            String query = "SELECT destination_path FROM image_destination WHERE "
                    + " image_uploaded_for_id = (SELECT image_uploaded_for_id FROM image_uploaded_for WHERE image_uploaded_for_name = 'Temp Service Connection') "
                    + " AND company_id =" + company_id;
            ResultSet rset = con.prepareStatement(query).executeQuery();
            if (rset.next()) {
                tempfilePath = rset.getString("destination_path");
            }
        } catch (Exception e) {
            System.out.println("Error in unpaidBillController getRepositoryPath" + e);
        }
        return tempfilePath;
    }

    public static String getRepTempPathForSConnMail(Connection con, int company_id) {
        String tempfilePath = null;
        try {
            String query = "SELECT destination_path FROM image_destination WHERE "
                    + " image_uploaded_for_id = (SELECT image_uploaded_for_id FROM image_uploaded_for WHERE image_uploaded_for_name = 'Temp Mail Service Connection') "
                    + " AND company_id =" + company_id;
            ResultSet rset = con.prepareStatement(query).executeQuery();
            if (rset.next()) {
                tempfilePath = rset.getString("destination_path");
            }
        } catch (Exception e) {
            System.out.println("Error in unpaidBillController getRepositoryPath" + e);
        }
        return tempfilePath;
    }

    public static String getRepPathForRecieveSConnMail(Connection con, int company_id) {
        String tempfilePath = null;
        try {
            String query = "SELECT destination_path FROM image_destination WHERE "
                    + " image_uploaded_for_id = (SELECT image_uploaded_for_id FROM image_uploaded_for WHERE image_uploaded_for_name = 'Recieved Mail Service Connection') "
                    + " AND company_id =" + company_id;
            ResultSet rset = con.prepareStatement(query).executeQuery();
            if (rset.next()) {
                tempfilePath = rset.getString("destination_path");
            }
        } catch (Exception e) {
            System.out.println("Error in unpaidBillController getRepositoryPath" + e);
        }
        return tempfilePath;
    }
    public static String getRepPath(Connection con) {
        String tempfilePath = null;
        try {
            String query = "SELECT destination_path FROM image_destination WHERE "
                    + " image_uploaded_for_id = (SELECT image_uploaded_for_id FROM image_uploaded_for WHERE image_uploaded_for_name = 'General') ";
                    //+ " AND company_id =" + company_id;
            ResultSet rset = con.prepareStatement(query).executeQuery();
            if (rset.next()) {
                tempfilePath = rset.getString("destination_path");
            }
        } catch (Exception e) {
            System.out.println("Error in unpaidBillController getRepositoryPath" + e);
        }
        return tempfilePath;
    }

    public static String getRepPathForSConnSentMail(Connection con, int company_id) {
        String tempfilePath = null;
        try {
            String query = "SELECT destination_path FROM image_destination WHERE "
                    + " image_uploaded_for_id = (SELECT image_uploaded_for_id FROM image_uploaded_for WHERE image_uploaded_for_name = 'Mail Service Connection') "
                    + " AND company_id =" + company_id;
            ResultSet rset = con.prepareStatement(query).executeQuery();
            if (rset.next()) {
                tempfilePath = rset.getString("destination_path");
            }
        } catch (Exception e) {
            System.out.println("Error in unpaidBillController getRepositoryPath" + e);
        }
        return tempfilePath;
    }
    public static String getRepositoryTempFilePathForBill(Connection con, int company_id) {
        String tempfilePath = null;
        try {
            String query = "SELECT destination_path FROM image_destination WHERE "
                    + " image_uploaded_for_id = (SELECT image_uploaded_for_id FROM image_uploaded_for WHERE image_uploaded_for_name = 'Temp Single Bill') "
                    + " AND company_id =" + company_id;
            ResultSet rset = con.prepareStatement(query).executeQuery();
            if (rset.next()) {
                tempfilePath = rset.getString("destination_path");
            }
        } catch (Exception e) {
            System.out.println("Error in unpaidBillController getRepositoryPath" + e);
        }
        return tempfilePath;
    }

    public static String getRepositoryTempFilePath(Connection con) {
        String tempfilePath = null;
        try {
            String query = "select repository_path from organisation_name  where is_home='Y'";
            ResultSet rset = con.prepareStatement(query).executeQuery();
            if (rset.next()) {
                tempfilePath = rset.getString("repository_path");
            }
            tempfilePath = tempfilePath + "temp_single_bill";

        } catch (Exception e) {
            System.out.println("Error in unpaidBillController getRepositoryPath" + e);
        }
        return tempfilePath;
    }

    public static String getRepositoryTempFilePathForRequisition(Connection con) {
        String tempfilePath = null;
        try {
            String query = "select repository_path from organisation_name  where is_home='Y'";
            ResultSet rset = con.prepareStatement(query).executeQuery();
            if (rset.next()) {
                tempfilePath = rset.getString("repository_path");
            }
            tempfilePath = tempfilePath + "temp_requisition";

        } catch (Exception e) {
            System.out.println("Error in unpaidBillController getRepositoryPath" + e);
        }
        return tempfilePath;
    }

    public static String getRepositoryPath(Connection con) {
        String repository_path = null;
        try {
            String query = "select repository_path from organisation_name  where is_home='Y'";
            ResultSet rset = con.prepareStatement(query).executeQuery();
            if (rset.next()) {
                repository_path = rset.getString("repository_path");
            }
        } catch (Exception e) {
            System.out.println("Error in unpaidBillController getRepositoryPath" + e);
        }
        return repository_path;
    }
}
