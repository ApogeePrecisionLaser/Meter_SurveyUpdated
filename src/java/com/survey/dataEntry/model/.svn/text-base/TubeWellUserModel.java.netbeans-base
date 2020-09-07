/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.survey.tableClasses.TubeWellUserTypeBean;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Administrator
 */
public class TubeWellUserModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
      String destination_path = "";
    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<TubeWellUserTypeBean> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in tubeWellUserTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public int insertRecord(TubeWellUserTypeBean tubeWellUserTypeBean, List list) {
     String image_uploaded_for = "Survey Image";
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String query = "INSERT INTO tubewell_user (serial_no, dept_consumer_code, refrence_number, user_name, address, date_of_installation, size, "
                + "meter_no, contact_person, contact_no, ward_id_m, type_of_premises_id,remark,image_name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
            String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description) "
                + " VALUES(?, ?, ?, ?)";

        String updateQuery = "UPDATE tubewell_user set general_image_details_id=? where image_name=? ";
        int rowsAffected = 0;
       
        try {
             connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, tubeWellUserTypeBean.getSerial_no());
            pstmt.setString(2, tubeWellUserTypeBean.getDept_consumer_code());
            pstmt.setString(3, tubeWellUserTypeBean.getRefrence_number());
            pstmt.setString(4, tubeWellUserTypeBean.getUser_name());
            pstmt.setString(5, tubeWellUserTypeBean.getAddress());
            pstmt.setDate(6, convertToSqlDate(tubeWellUserTypeBean.getDate_of_installation()));
            pstmt.setString(7, tubeWellUserTypeBean.getSize());
            pstmt.setString(8, tubeWellUserTypeBean.getMeter_no());
            pstmt.setString(9, tubeWellUserTypeBean.getContact_person());
            pstmt.setString(10, tubeWellUserTypeBean.getContact_no());
            pstmt.setInt(11, tubeWellUserTypeBean.getWard_id_m());
            pstmt.setInt(12, tubeWellUserTypeBean.getType_of_premises_id());
            pstmt.setString(13, tubeWellUserTypeBean.getRemark());
            pstmt.setString(14,tubeWellUserTypeBean.getImage_name());
            rowsAffected = pstmt.executeUpdate();
       
        if (rowsAffected > 0) {
            if (!tubeWellUserTypeBean.getImage_name().isEmpty()) {
                            try {
                                pstmt = connection.prepareStatement(imageQuery);
                                pstmt.setString(1, tubeWellUserTypeBean.getImage_name());
                                pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
                                pstmt.setString(3, current_date);
                                pstmt.setString(4, "this image is for survey");

                                rowsAffected = pstmt.executeUpdate();
                                pstmt.close();
                            } catch (Exception e) {
                                System.out.println("Error:keypersonModel--insertRecord-- " + e);
                            }
                            if (rowsAffected > 0) {
                                try {
                                    if (writeImage(tubeWellUserTypeBean.getImage_name(),list) > 0) {
                                        pstmt = connection.prepareStatement(updateQuery);
                                        pstmt.setInt(1, getgeneral_image_details_id(tubeWellUserTypeBean.getImage_name()));
                                        pstmt.setString(2, tubeWellUserTypeBean.getImage_name());
                                        pstmt.executeUpdate();
                                    }
                                } catch (Exception e) {
                                    System.out.println("Exception :" + e);
                                }
                            }
                        }
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
                connection.rollback();
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        }catch (Exception e) {
            rowsAffected = -1;
            System.out.println("Error while inserting record...." + e);
            try {
                connection.rollback();
            } catch (SQLException sql) {
                System.out.println("SQLException occured during Updation is: " + sql);
            }

        } finally {
            try {
                //if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
                    if (rowsAffected > 0) {
                        connection.commit();
                    }
               // }
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("SQLException occured while setting autoCommit = true duing new flex updation :" + ex);
                // Logger.getLogger(CreateEstimateModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;

    }
   public int getgeneral_image_details_id(String image_name) {
        String query;
        int key_person_id = 0;
        query = "select general_image_details_id from general_image_details where image_name='" + image_name + "' and active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("general_image_details_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return key_person_id;
    }
  public int getimage_destination_id(String image_uploaded_for) {
        String query;
        int image_destination_id = 0;
        query = " SELECT image_destination_id, destination_path from image_destination AS id , image_uploaded_for As i "
                + " WHERE id.image_uploaded_for_id=i.image_uploaded_for_id AND i.image_uploaded_for_name= ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, image_uploaded_for);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                image_destination_id = rset.getInt("image_destination_id");
            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return image_destination_id;
    }

    public int updateRecord(TubeWellUserTypeBean tubeWellUserTypeBean) {
        String query = "UPDATE tubewell_user  SET serial_no=?, dept_consumer_code=?, refrence_number=?, user_name=?, address=?, date_of_installation=?, size=?, "
                + "meter_no=?, contact_person=?, contact_no=?, ward_id_m=?, type_of_premises_id=?,remark=? WHERE tubewell_user_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, tubeWellUserTypeBean.getSerial_no());
            pstmt.setString(2, tubeWellUserTypeBean.getDept_consumer_code());
            pstmt.setString(3, tubeWellUserTypeBean.getRefrence_number());
            pstmt.setString(4, tubeWellUserTypeBean.getUser_name());
            pstmt.setString(5, tubeWellUserTypeBean.getAddress());
            pstmt.setDate(6, convertToSqlDate(tubeWellUserTypeBean.getDate_of_installation()));
            pstmt.setString(7, tubeWellUserTypeBean.getSize());
            pstmt.setString(8, tubeWellUserTypeBean.getMeter_no());
            pstmt.setString(9, tubeWellUserTypeBean.getContact_person());
            pstmt.setString(10, tubeWellUserTypeBean.getContact_no());
            pstmt.setInt(11, tubeWellUserTypeBean.getWard_id_m());
            pstmt.setInt(12, tubeWellUserTypeBean.getType_of_premises_id());
            pstmt.setString(13, tubeWellUserTypeBean.getRemark());
            pstmt.setInt(14, tubeWellUserTypeBean.getTubewell_user_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error while updating record........." + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getTubeWellUserType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT tubewell_user_id, user_name FROM tubewell_user GROUP BY user_name ORDER BY user_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String user_name = rset.getString("user_name");
                if (user_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(user_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("gettubeWellUserType ERROR inside tubeWellUserTypeModel - " + e);
        }
        return list;
    }
  public List<String> getWardNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT  ward_no_m FROM ward_m GROUP BY ward_no_m ORDER BY ward_no_m ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_no_m = rset.getString("ward_no_m");
                if (ward_no_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no_m);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("gettubeWellUserType ERROR inside tubeWellUserTypeModel - " + e);
        }
        return list;
    }

    public int getNoOfRows(String SearchTubeWellUserType) {
        String query = " SELECT Count(*) "
                + " FROM tubewell_user "
                + " WHERE IF('" + SearchTubeWellUserType + "' = '', user_name LIKE '%%',user_name =?) "
                + " ORDER BY user_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, SearchTubeWellUserType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows fuse type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<TubeWellUserTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String SearchTubeWellUserType) {
        List<TubeWellUserTypeBean> list = new ArrayList<TubeWellUserTypeBean>();

        String query = " SELECT tubewell_user_id, serial_no, dept_consumer_code, refrence_number, user_name, address, date_of_installation, size, meter_no,"
                + " contact_person, contact_no, w.ward_no_m, p.type_of_premsis, t.remark,t.image_name,t.general_image_details_id "
                + " FROM tubewell_user as t,ward_m as w ,type_of_premises as p "
                + " WHERE  t.ward_id_m=w.ward_id_m and t.type_of_premises_id=p.type_of_premises_id "
                + "AND IF('" + SearchTubeWellUserType + "' = '',user_name  LIKE '%%',user_name =?) "
                + " ORDER BY user_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, SearchTubeWellUserType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TubeWellUserTypeBean tubeWellUserType = new TubeWellUserTypeBean();
                tubeWellUserType.setTubewell_user_id(rset.getInt("tubewell_user_id"));
                tubeWellUserType.setSerial_no(rset.getString("serial_no"));
                tubeWellUserType.setDept_consumer_code(rset.getString("dept_consumer_code"));
                tubeWellUserType.setRefrence_number(rset.getString("refrence_number"));
                tubeWellUserType.setUser_name(rset.getString("user_name"));
                tubeWellUserType.setAddress(rset.getString("address"));
                tubeWellUserType.setDate_of_installation(rset.getString("date_of_installation"));
                tubeWellUserType.setSize(rset.getString("size"));
                tubeWellUserType.setMeter_no(rset.getString("meter_no"));
                tubeWellUserType.setContact_person(rset.getString("contact_person"));
                tubeWellUserType.setContact_no(rset.getString("contact_no"));
                tubeWellUserType.setWard_no_m(rset.getString("ward_no_m"));
                 tubeWellUserType.setType_of_premises(rset.getString("type_of_premsis"));
                 tubeWellUserType.setImage_name(rset.getString("image_name"));
                 tubeWellUserType.setGeneral_image_details_id(rset.getString("general_image_details_id"));

                list.add(tubeWellUserType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<TubeWellUserTypeBean> showAllData(String SearchTubeWellUserType) {
        List<TubeWellUserTypeBean> listAll = new ArrayList<TubeWellUserTypeBean>();

        String query = "  SELECT tubewell_user_id, serial_no, dept_consumer_code, refrence_number, user_name, address, date_of_installation, size, meter_no,"
                + " contact_person, contact_no, w.ward_no_m, p.type_of_premsis, t.remark,t.image_name,t.general_image_details_id "
                + " FROM tubewell_user as t,ward_m as w ,type_of_premises as p "
                + " WHERE  t.ward_id_m=w.ward_id_m and t.type_of_premises_id=p.type_of_premises_id "
                + "AND IF('" + SearchTubeWellUserType + "' = '',user_name  LIKE '%%',user_name =?) "
                + " ORDER BY user_name ";
        //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, SearchTubeWellUserType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TubeWellUserTypeBean tubeWellUserType = new TubeWellUserTypeBean();
                tubeWellUserType.setTubewell_user_id(rset.getInt("tubewell_user_id"));
                tubeWellUserType.setSerial_no(rset.getString("serial_no"));
                tubeWellUserType.setDept_consumer_code(rset.getString("dept_consumer_code"));
                tubeWellUserType.setRefrence_number(rset.getString("refrence_number"));
                tubeWellUserType.setUser_name(rset.getString("user_name"));
                tubeWellUserType.setAddress(rset.getString("address"));
                tubeWellUserType.setDate_of_installation(rset.getString("date_of_installation"));
                tubeWellUserType.setSize(rset.getString("size"));
                tubeWellUserType.setMeter_no(rset.getString("meter_no"));
                tubeWellUserType.setContact_person(rset.getString("contact_person"));
                tubeWellUserType.setContact_no(rset.getString("contact_no"));
                tubeWellUserType.setWard_no_m(rset.getString("ward_no_m"));
                tubeWellUserType.setType_of_premises(rset.getString("type_of_premsis"));
                 tubeWellUserType.setImage_name(rset.getString("image_name"));
                 tubeWellUserType.setGeneral_image_details_id(rset.getString("general_image_details_id"));
                listAll.add(tubeWellUserType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }
  public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }
    public int deleteRecord(int tubewell_user_id) {
        String query = "DELETE FROM tubewell_user WHERE tubewell_user_id=" + tubewell_user_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
       public int writeImage(String imageName, List<File> file) {
        boolean isCreated=false;
        System.out.println("Start to write payment images in Repository");
        try {
            File srcfile = null;
            //      String dayOfMonthFolder = createAppropriateDirectories(destination_path);
            //File folder = new File(dayOfMonthFolder);
            boolean isuploaded = false;
            Iterator<File> fileItr = file.iterator();
            //  int number_of_file = folder.list().length;
            while (fileItr.hasNext()) {
//                Object image = fileItr.next();
//                tempSource = image.toString();
                //  number_of_file++;
                srcfile = fileItr.next();
                // String ext = srcfile.getName().replace(".", "%#");
                //ext = ext.split("%#")[1];
                String image = srcfile.toString();
                int index = image.indexOf('.');
                System.out.println(index);
                String ext = image.substring(index, image.length());
                // String image_name = imageName + "_" + number_of_file + ext;
                // System.out.println("" + image_name);
                // String forlder_path = dayOfMonthFolder + "/" + imageName + "_" + number_of_file;
                getRepositoryPath("Survey Image");
                 File file1 = new File(destination_path);
                         
                        if (!file1.exists()) {
                            isCreated = file1.mkdirs();
                        }
                File desfile = new File(destination_path + "/" + imageName);
                isuploaded = srcfile.renameTo(desfile);

                if (isuploaded) {
                    message = "Image Uploaded Successfully.";
                    msgBgColor = COLOR_OK;

                }

            }
            File deleteFile = new File(getRepositoryPath("General"));
            deleteDirectory(deleteFile);
        } catch (Exception ex) {
            System.out.println("File write error" + ex);
            message = "Cannot upload the image, some error.";
            msgBgColor = COLOR_ERROR;
            PreparedStatement pstmt;
            int rowsAffected = 0, id;
            String query, query1;
            query = "UPDATE survey"
                    + "SET general_image_details_id = NULL  "
                    + " where image_name = ? ";
            try {
                pstmt = (PreparedStatement) connection.prepareStatement(query);
                pstmt.setString(1, imageName);
                rowsAffected = pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error: GeneralImageDetailsModel-record cannot updated when image is not uploaded successfully-" + e);
            }
            query1 = "DELETE from general_image_details WHERE general_image_details_id= ? ";
            try {
                pstmt = (PreparedStatement) connection.prepareStatement(query1);
                pstmt.setInt(1, getgeneral_image_details_id(imageName));
                rowsAffected = pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error: ReadMailModel-record cannot deleted when image is not uploaded successfully-" + e);
            }
        }
        return message.equals("Image Uploaded Successfully.") ? 1 : 0;
    }
  public boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
    }
      public int getWardId(String ward_no) {
        String query = " select ward_id_m from ward_m where ward_no_m='"+ward_no+"'";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         noOfRows=rs.getInt("ward_id_m");
         }
        } catch (Exception e) {
            System.out.println("Error inside getWardId division type model" + e);
        }

        return noOfRows;
    }
  public int getPremisesId(String premises_type) {
        String query = " select type_of_premises_id from type_of_premises where type_of_premsis='"+premises_type+"'";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         noOfRows=rs.getInt("type_of_premises_id");
         }
        } catch (Exception e) {
            System.out.println("Error inside getCircleId division type model" + e);
        }

        return noOfRows;
    }
    public String getRepositoryPath(String image_uploaded_for) {
        String query;

        query = " SELECT destination_path from image_destination AS id , image_uploaded_for As i "
                + " WHERE id.image_uploaded_for_id=i.image_uploaded_for_id AND i.image_uploaded_for_name= ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, image_uploaded_for);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                this.destination_path = rset.getString("destination_path");
            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return this.destination_path;
    }
      public String getdestinationPath(int general_image_details_id,String image_name) {
        String query;
        String destination ="";
        query = " SELECT id.destination_path from image_destination AS id , general_image_details as g"
                + " WHERE id.image_destination_id=g.image_destination_id AND g.general_image_details_id= '"+general_image_details_id+"' and "
                + "g.image_name='"+image_name+"'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
               destination = rset.getString("destination_path");
            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return destination;
    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection TrafficTypeModel:" + e);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
}
