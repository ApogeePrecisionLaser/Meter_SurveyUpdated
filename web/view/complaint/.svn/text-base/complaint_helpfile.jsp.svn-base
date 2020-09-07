<%--
    Document   : complaint
    Created on : Jul 26, 2013, 11:24:43 AM
    Author     : Shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Complaint Register</title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" src="JS/table.js"></script>
        <link rel="stylesheet" type="text/css" href="css/calendar.css" >
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script language="javascript" type="text/javascript" src="JS/calendar.js"></script>
        <link rel="stylesheet" href="datePicker/jquery.ui.all.css">
        <script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
        <script type="text/javascript" language="javascript">

        </script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0"  class="main" style="table-layout: auto">
            <tr>

                <td></td>

            </tr>
            <%--<tr>
                <td><%@include file="/view/layout/header_layout.jsp" %> </td>

            </tr>--%>
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %></td>
            </tr>
            <tr>
                <td>
                    <div id="main_div" class="maindiv" style="min-height: 600px ;max-height: auto">

                            <table width="100%">
                                <tr>
                                    <td>
                                        <table width="100%" border="4">
                                            <tr>
                                                <td align="center" class="header_table" width="90%">Complaint Help File</td>
                                                <td><%@include file="/view/layout/complaint_menu.jsp" %> </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                           </table>
                                  <div id="main_div" class="maindiv" style="min-height: 1000px ;max-height: auto">
                                      <h4 align="center" style="color:blue">About complaint register: </h4>
                          <p>Complaint Register is very tiny part in “SSADVT”, it is used for the locking the Complaint for various Problems (or any damage) on the site of ssadvt.<br/><br/>
                             There is provision of locking the problem on the software and their status. After repair that damage user can change the status of defected site.<br/><br/>
                             The Advantage of Complaint Register is decrease the number of   damage of various Site of SSADVT. And provide the information of site Status.<br/><br/>
                             The usages of this complaint Register is very simple.<br/><br/>
                             1.	Select the complaint register and fill the information  of site.<br/><br/>
                             2. After repair of user change the status of site.<br/><br/>
                         </p>
                         <h4 align="left" style="color:blue">Ad Type1:</h4><p> It is advertising type name.User will select advertising type name according your  status of the site.<br/><br/></p>
                         <h4 align="left" style="color:blue">Ad Type2:</h4><p> It is  sub advertising type name.User will select sub advertising type name according your  status of the site.<br/><br/></p>
                         <h4 align="left" style="color:blue">Site Name:</h4><p> It is autocomplete field.It identifies the various information of  the site such as advertising type name,site number and address <br/><br/>
                                                                               User enter the space or enter the first letter of ad type2(sub advertising type name) and select your site name.
                                                                                        </p>
                         <h4 align="left" style="color:blue">Sub Site Name:</h4><p> It identifies the various information of  site name such as sub site name,location name and mounting name .User will select sub site name  <br/><br/>
                                                                               according your location name,mounting name and sub site name.
                                                                               </p>
                          <h4 align="left" style="color:blue">Complaint Date:</h4><p>In this field ,User will fill the complaint register date .<br/><br/></p>
                          <h4 align="left" style="color:blue">Status:</h4><p>It is status of complaint .User will not fill this field.It will be fill automatic.Status provide the various type information  <br/>
                          of the complaint:<br/> <br/>
                          1. New Complaint <br/> 
                          2. Pending       <br/> 
                          3. Under process <br/> 
                          4. Complete      <br/> 
                          5. Cancel        <br/> 
                          </p>
                                  <h4 align="left" style="color:black">New Complaint:</h4><p>If user register the complaint and do not fill assigned for field  than Complaint status is new complaint .<br/><br/>
                                                                                             If User register the complaint and fill the assigned for field than complaint status is pending.
                                                             </p>
                                 <h4 align="left" style="color:black">Pending:</h4><p>If user fill the  assigned for field  than Complaint status is pending .<br/><br/>                                                                                            
                                                             </p>
                                  <h4 align="left" style="color:black">Under Process:</h4><p>If user  get  the feedback your complaint and complaint is not completed than .<br/><br/>
                                                      complaint status is    under process.
                                                      </p>
                                  <h4 align="left" style="color:black">Complete:</h4><p>If user  get  the feedback your complaint and complaint is  completed than .<br/><br/>
                                                      complaint status is complete.
                                                      </p>
                                   <h4 align="left" style="color:black">Cancel:</h4><p>If user want to reassigned  complaint to technician than old complaint status will be cancel <br/><br/>
                                                      and  reassigned  complaint status will be pending.
                                                      </p>
                                <h4 align="left" style="color:blue">Complaint Type:</h4><p>In this field user will select complaint type according your problem  of the site .<br/><br/></p>
                                <h4 align="left" style="color:blue">Posted By:</h4><p>It is autocomplete field .In this field User will fill your name <br/><br/></p>
                                <h4 align="left" style="color:blue">Assigned For:</h4><p>In this field user will fill the  technician name.User can  assigned the complaint to technician  according your<br/><br/>
                                                                                    problem of  the site.   </p>
                                <h4 align="left" style="color:blue">Complition date :</h4><p>User will not fill this field.It will be fill automatic.It is complition date of complaint <br/><br/></p>
                               <%-- <h4 align="left" style="color:blue">Remark :</h4><p>in this field ,user will fill the information  of your site <br/><br/></p> --%>
                                 

                                  </div>


                    </div>
              </td>
            </tr>                                                                            
            <tr>
                <td>
                    <jsp:include page="calendar_view"/>

                </td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>


    </body>
</html>
