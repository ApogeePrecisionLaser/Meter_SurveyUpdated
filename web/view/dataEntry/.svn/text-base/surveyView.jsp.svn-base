<%-- 
    Document   : surveyView
    Created on : Jul 29, 2014, 11:36:10 PM
    Author     : JPSS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" src="JS/table.js"></script>
<link rel="stylesheet" type="text/css" href="css/calendar.css" >
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script language="javascript" type="text/javascript" src="JS/calendar.js"></script>

<%-- <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript" src="JS/calendar.js"></script>
<link rel="stylesheet" type="text/css" href="css/calendar.css" > --%>
<script type="text/javascript" language="javascript">
    jQuery(function(){
        $("#searchPoleNo").autocomplete("surveyCont", {
            extraParams: {
                action1: function() { return "getSearchPole_No"}               
            }
        });
        $("#pole_no").autocomplete("surveyCont", {
            extraParams: {
                action1: function() { return "getPole_No"},
                action2: function() { return document.getElementById("survey_type").value;}
            }
        });
        $("#survey_type").result(function(event, data, formatted){
            document.getElementById("pole_no").value = "";
            $("#pole_no").flushCache();
        });
        $("#fuse_type1").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
        $("#contacter_type").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getContacterType"}
            }
        });
        $("#mccb_type1").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
      
        $("#fuse_type2").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
        $("#contacter_capacity").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getContacterType"}
            }
        });
        $("#mccb_type2").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
        
        $("#fuse_type3").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
       
        $("#mccb_type3").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
          $("#auto_switch_type").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getAutoSwitchType"}
            }
        });
        $("#main_switch_type").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getMainSwitchType"}
            }
        });
        $("#enclosure_type").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getEnclosureType"}
            }
        });
       
    });
    $(document).ready(function(){
        selectServeyType('onLoad');
    });

    var value;
    function myLeftTrim(str) {
        var beginIndex = 0;
        for(var i = 0; i < str.length; i++) {
            if(str.charAt(i) == ' ') {
                beginIndex++;
            } else {
                break;
            }
        }
        return str.substring(beginIndex, str.length);
    }
                    
    function selectServeyType(survey_type){ 
        if(myLeftTrim(survey_type).length == 0){   
            //   value =$("option:selected", $("#"+survey_type)).val();
            value ='newCase';
        }else{   
            value =  survey_type;
        }
       
        //   document.getElementById("pole_no").value = "";
        $("#pole_no").flushCache();
        //   value =  $("#survey_type").val();
    
        //    alert('010'+value);
        //   var opt  = document.createElement("option");
        if(value=='pole_type_survey'){  //alert('dsfdsfs');
            $("#survey_type").html("<option value='pole_type_survey' >Pole Type Survey</option>");
            //   opt.text = 'Pole Type Survey';
            $("#table3").css("display", "none");
            $("#remark1").css("display", "inline-block");
            $("#remark_header").css("display", "inline-block");
        }else if(value == 'newCase'){  //alert('sdasda');
            var check = $("option:selected", $('#survey_type')).val();  //alert(check);
            if(check =='pole_type_survey'){
                $("#table5").css("display", "table");
                $("#table3").css("display", "none");
                $("#table4").css("display", "none");
                $("#remark1").css("display", "inline-block");
                $("#remark_header").css("display", "inline-block");
            }else if(check =='switching_type_survey'){
                $("#table3").css("display", "table");
                $("#table5").css("display", "table");
                $("#remark1").css("display", "none");
                $("#remark_header").css("display", "none");
                $("#table4").css("display", "none");
            }
            else if(check =='upload_file_data'){
                $("#table4").css("display", "table");
                $("#table3").css("display", "none");
                $("#table5").css("display", "none");
                $("#remark_header").css("display", "none");
            }
        }else if(survey_type == 'onLoad'){
            $("#table3").css("display", "none");
            $("#remark1").css("display", "inline-block");
            $("#remark_header").css("display", "inline-block");
            $("#table4").css("display", "none");
        }else if(survey_type=='switching_type_survey'){// alert('1');
            $("#survey_type").html("<option value='switching_type_survey'>Switching Point Type Survey</option>"); // alert('2');
            //  opt.text = 'Switching Point Type Survey';
            $("#table3").css("display", "table");
            $("#remark1").css("display", "none");
            $("#remark_header").css("display", "none");
            $("#table4").css("display", "none");
        }
        else if(survey_type=='upload_file_data'){// alert('1');
            $("#survey_type").html("<option value='upload_file_data'>Upload File Data</option>"); // alert('2');
            //  opt.text = 'Switching Point Type Survey';
            $("#table4").css("display", "table");
            $("#table3").css("display", "none");
            $("#table5").css("display", "none");
            //$("#remark_header").css("display", "none");
        }
        //   opt.value = value;
        //   document.getElementById("#survey_type").options.add(opt);
    }

    function makeEditable(id) {
        $("#survey_file_no").attr("disabled",false);
        $("#survey_page_no").attr("disabled",false);
        $("#survey_by").attr("disabled",false);
        $("#survey_date").attr("disabled",false);
        $("#meter_functional").attr("disabled",false);
        $("#fuse_functional").attr("disabled",false);
        $("#timer_functional").attr("disabled",false);
        $("#mccb_functional").attr("disabled",false);
        $("#contacter_functional").attr("disabled",false);
        $("#b_phase").attr("disabled",false);
        $("#r_phase").attr("disabled",false);
        $("#y_phase").attr("disabled",false);
        $("#power").attr("disabled",false);
        $("#meter_no").attr("disabled",false);
        $("#remark1").attr("disabled",false);
        $("#fuse_1").attr("disabled",false);
        $("#fuse_2").attr("disabled",false);
        $("#fuse_3").attr("disabled",false);
        $("#mccb_1").attr("disabled",false);
        $("#mccb_2").attr("disabled",false);
        $("#mccb_3").attr("disabled",false);
        $("#contacter_make").attr("disabled",false);
        $("#contacter_type").attr("disabled",false);
        $("#contacter_capacity").attr("disabled",false);
        $("#fuse_quantity").attr("disabled",false);
        $("#mccb_quantity").attr("disabled",false);
        $("#phase_no").attr("disabled",false);
        $("#reason_type").attr("disabled",false);
        $("#meter_status").attr("disabled",false);
        $("#fuse_type1").attr("disabled",false);
        $("#fuse_type2").attr("disabled",false);
        $("#fuse_type3").attr("disabled",false);
        $("#mccb_type1").attr("disabled",false);
        $("#mccb_type2").attr("disabled",false);
        $("#mccb_type3").attr("disabled",false);
        $("#auto_switch_type").attr("disabled",false);
        $("#main_switch_type").attr("disabled",false);
        $("#main_switch_rating").attr("disabled",false);
        $("#enclosure_type").attr("disabled",false);
        $("#meter_phase").attr("disabled",false);
        $("#meter_reading").attr("disabled",false);
        $("#remark").attr("disabled",false);
        
        document.getElementById("save").disabled = true;
        document.getElementById("save_As").disabled = false;
        document.getElementById("cancel").disabled = false;
        document.getElementById("revise").disabled = false;

        $('#pole_no').attr('readonly', true);
     
              

        if(id == 'new') {
            $('#pole_no').attr('readonly', false);
            $("#survey_type").html("<option value='switching_type_survey'>Switching Point Type Survey</option>");
            var opt  = document.createElement("option");
            //opt.text = 'Pole Type Survey';
            //  opt.value='pole_type_survey';
            opt.text = 'Upload File Data';
            opt.value='upload file data';

            document.getElementById("survey_type").options.add(opt);
                                   

            document.getElementById('form2').reset();
            document.getElementById("survey_type").value = value;
            document.getElementById("survey_id").value = '';
            document.getElementById("survey_rev_no").value = '';
            document.getElementById("pole_no").focus();
            document.getElementById("revise").disabled = true;
            document.getElementById("save").disabled = false;
            document.getElementById("save_As").disabled = true;
            document.getElementById("cancel").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 25);
        }

        document.getElementById("message").innerHTML = "";
        $("#message").html("");
    }


    function verify() {
        var result;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Revise' || document.getElementById("clickedButton").value == 'Cancel') {
            var status = document.getElementById("status").value;
    <%--     var org_office_name = document.getElementById("org_office_name").value;
         var office_type = document.getElementById("office_type").value;
         var address_line1 = document.getElementById("address_line1").value;
         var state_name = document.getElementById("state_name").value;
         var city_name = document.getElementById("city_name").value;
         var email_id1 = document.getElementById("email_id1").value;
         var email_id2 = document.getElementById("email_id2").value;
         var landline_no1 = document.getElementById("landline_no1").value;  --%>

                     if(status == 'N') {
                         // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                         $("#message").html( "<td colspan='8' bgcolor='coral'><b>Can't Do any Operation on De-Active Record...</b></td>");
                      
                         return false;
                     }
    <%--            if(myLeftTrim(org_office_name).length == 0) {
                    // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Office Name is required...</b></td>";
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>Organisation Office Name is required...</b></td>");
                    document.getElementById("org_office_name").focus();
                    return false;
                }
                if(myLeftTrim(office_type).length == 0) {
                    // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Org Office Type is required...</b></td>";
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>Org Office Type is required...</b></td>");
                    document.getElementById("office_type").focus();
                    return false;
                }
                if(myLeftTrim(state_name).length == 0) {
                    //document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>State Name is required...</b></td>";
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>State Name is required...</b></td>");
                    document.getElementById("state_name").focus();
                    return false;// code to stop from submitting the form2.
                }
                if(myLeftTrim(city_name).length == 0) {
                    //document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>City Name is required...</b></td>";
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>City Name is required...</b></td>");
                    document.getElementById("city_name").focus();
                    return false;// code to stop from submitting the form2.
                }
                if(myLeftTrim(address_line1).length == 0) {
                    // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Address Line 1 is required...</b></td>";
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>Address Line 1 is required...</b></td>");
                    document.getElementById("address_line1").focus();
                    return false;
                }
                var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

                    if(reg.test(email_id1) == false) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Please Enter Correct 1st Email ID...</b></td>";
                        $("#message").html( "<td colspan='8' bgcolor='coral'><b>Please Enter Correct 1st Email ID...</b></td>");
                        document.getElementById("email_id1").focus();
                        return false;// code to stop from submitting the form2.
                    }
                    if(myLeftTrim(email_id2).length > 0) {
                        if(reg.test(email_id2) == false) {
                            //document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Please Enter Correct 2nd Email ID...</b></td>";
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Please Enter Correct 2nd Email ID...</b></td>");
                            document.getElementById("email_id2").focus();
                            return false;// code to stop from submitting the form2.
                        }
                    }
                    if(myLeftTrim(landline_no1).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Landline No 1 is required...</b></td>";
                        $("#message").html( "<td colspan='8' bgcolor='coral'><b>Landline No 1 is required...</b></td>");
                        document.getElementById("landline_no1").focus();
                        return false;// code to stop from submitting the form2.
                    }  --%>
                                if(result == false)
                                {// if result has value false do nothing, so result will remain contain value false.
                                }
                                else{ result = true;
                                }
                                if(document.getElementById("clickedButton").value == 'Save AS New'){
                                    result = confirm("Are you sure you want to save it as New record?")
                                    return result;
                                }
                            } else result = confirm("Are you sure you want to delete this record?")
                            return result;
                        }

                        function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                            for(var i = 0; i < noOfRowsTraversed; i++) {
                                for(var j = 1; j <= noOfColumns; j++) {
                                    document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                                }
                            }
                        }

                        function fillColumns(id) {

                            var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                            var noOfColumns =25;
                            var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                            columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                            var lowerLimit, higherLimit, rowNo = 0;

                            for(var i = 0; i < noOfRowsTraversed; i++) {
                                lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                                higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                                rowNo++;
                                if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                            }

                            setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                            var t1id = "t1c";

                            document.getElementById("survey_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;

                            document.getElementById("survey_rev_no").value= document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                            document.getElementById("survey_date").value= document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                            document.getElementById("survey_file_no").value= document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                            document.getElementById("survey_page_no").value= document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                            document.getElementById("survey_by").value= document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
                            document.getElementById("pole_no").value= document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
                            document.getElementById("pole_rev_no").value= document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
                            //    document.getElementById("survey_type").value= document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
                            var survey_type = document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
                            selectServeyType(survey_type);
                            document.getElementById("status").value= document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
                            document.getElementById("meter_no").value= document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
                            document.getElementById("meter_functional").value= document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
                            document.getElementById("r_phase").value= document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
                            document.getElementById("y_phase").value= document.getElementById(t1id + (lowerLimit + 14)).innerHTML;
                            document.getElementById("b_phase").value= document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
                            document.getElementById("power").value= document.getElementById(t1id + (lowerLimit + 16)).innerHTML; 
                            document.getElementById("fuse_functional").value= document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
                            document.getElementById("contacter_functional").value= document.getElementById(t1id + (lowerLimit + 18)).innerHTML;
                            document.getElementById("timer_functional").value= document.getElementById(t1id + (lowerLimit + 19)).innerHTML;
                            document.getElementById("mccb_functional").value= document.getElementById(t1id + (lowerLimit + 20)).innerHTML;
                            document.getElementById("remark1").value= document.getElementById(t1id + (lowerLimit + 21)).innerHTML;
                            document.getElementById("switching_point_survey_id").value= document.getElementById(t1id + (lowerLimit + 22)).innerHTML; 
                            document.getElementById("switching_point_survey_rev_no").value= document.getElementById(t1id + (lowerLimit + 23)).innerHTML;

                            //alert(document.getElementById("switching_point_detail_id"+rowNo).value);
                            document.getElementById("switching_point_detail_id").value= document.getElementById("switching_point_detail_id" + rowNo).value;
                            document.getElementById("switching_rev_no").value= document.getElementById("switching_rev_no" + rowNo).value;
                            document.getElementById("pole_id").value= document.getElementById("pole_id" + rowNo).value;
                            



                            for(var i = 0; i < noOfColumns; i++) {
                                document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                            }
                            makeEditable('');
               
                            $("#message").html("");
                        }

                        function setStatus(id) {
                            if(id == 'save'){
                                document.getElementById("clickedButton").value = "Save";
                            }
                            else if(id == 'save_As'){
                                document.getElementById("clickedButton").value = "Save AS New";
                            }else if(id == 'revise'){
                                document.getElementById("clickedButton").value = "Revise";

                            }else if(id == 'update'){
                                document.getElementById("clickedButton").value = "Update";
                            }else document.getElementById("clickedButton").value = "Cancel";
                        }



</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Survey Page</title>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%">SURVEY DETAIL</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="surveyCont">
                                        <table align="center" class="heading1" width="600">
                                            <tr>
                                                <td>Pole No<input class="input" type="text" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" size="20" ></td>
                                                <td style="display:none">Switching Point No<input class="input" type="text" id="searchSwitchNo" name="searchSwitchNo" value="${searchSwitchNo}" size="20" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="surveyCont">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th style="white-space: normal" class="heading">S.No.</th>
                                                <th style="white-space: normal; display:none" class="heading">Survey Rev No</th>
                                                <th style="white-space: normal" class="heading">Survey Date</th>
                                                <th style="white-space: normal" class="heading">Survey File No</th>
                                                <th style="white-space: normal" class="heading">Survey Page No</th>
                                                <th style="white-space: normal" class="heading">Survey By</th>
                                                <th style="white-space: normal" class="heading">Pole No</th> 
                                                <th style="white-space: normal; display:none" class="heading">Pole Rev No</th>
                                                <th style="white-space: normal" class="heading">Survey type</th>
                                                <th style="white-space: normal" class="heading">Status</th>
                                                <!--   <th style="white-space: normal" class="heading">Switching Point No</th>
                                                   <th style="white-space: normal" class="heading">Switching Point Rev No</th> -->
                                                <th style="white-space: normal" class="heading">Meter No</th>
                                                <th style="white-space: normal" class="heading">Meter Functional</th>
                                                <th style="white-space: normal" class="heading">R Phase</th>
                                                <th style="white-space: normal" class="heading">Y Phase</th>
                                                <th style="white-space: normal" class="heading">B Phase</th>
                                                <th style="white-space: normal" class="heading">Power</th>
                                                <th style="white-space: normal" class="heading">Fuse Functional</th>
                                                <th style="white-space: normal" class="heading">Fuse Type</th>
                                                <th style="white-space: normal" class="heading">Fuse1</th>
                                                <th style="white-space: normal" class="heading">Fuse2</th>
                                                <th style="white-space: normal" class="heading">Fuse3</th>
                                                <th style="white-space: normal" class="heading">Contracter Functional</th>
                                                <th style="white-space: normal" class="heading">Contracter Type</th>
                                                <th style="white-space: normal" class="heading">Contracter1</th>
                                                <th style="white-space: normal" class="heading">Contracter2</th>
                                                <th style="white-space: normal" class="heading">Contracter3</th>
                                                <th style="white-space: normal" class="heading">Timer Functional</th>
                                                <th style="white-space: normal" class="heading">Timer Type</th>
                                                <th style="white-space: normal" class="heading">Timer1</th>
                                                <th style="white-space: normal" class="heading">Timer2</th>
                                                <th style="white-space: normal" class="heading">Timer3</th>
                                                <th style="white-space: normal" class="heading">Mccb Functional</th>
                                                <th style="white-space: normal" class="heading">Mccb Type</th>
                                                <th style="white-space: normal" class="heading">Mccb1</th>
                                                <th style="white-space: normal" class="heading">Mccb2</th>
                                                <th style="white-space: normal" class="heading">Mccb3</th>

                                                <th style="white-space: normal" class="heading">Remark</th>

                                            </tr>

                                            <c:forEach var="surveyTypeBean" items="${requestScope['surveyTypeList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >

                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${surveyTypeBean.survey_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td style="display:none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_rev_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_date}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_file_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_page_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_by}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.pole_no}</td>  
                                                    <td style="display:none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.pole_rev_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.status}</td>
                                                    <%--    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.pole_no_s}</td>
                                                          <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.switching_point_detail_rev_no}</td> --%>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.meter_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.meter_functional}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.r_phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.y_phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.b_phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.power}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.fuse_functional}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.contacter_funactional}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.timer_functional}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.mccb_functional}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${surveyTypeBean.switching_point_survey_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${surveyTypeBean.switching_point_survey_rev_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">
                                                        <input type="text" id="switching_point_detail_id${loopCounter.count}"  value="${surveyTypeBean.switching_point_detail_id}"/>
                                                        <input type="text" id="switching_rev_no${loopCounter.count}"  value="${surveyTypeBean.switching_rev_no}"/>
                                                        <input type="text" id="pole_id${loopCounter.count}"  value="${surveyTypeBean.pole_id}"/>
                                                    </td>

                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="16">
                                                    <c:choose>
                                                        <c:when test="${showFirst eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='First'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showPrevious == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Previous' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Previous'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showNext eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Next' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Next'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showLast == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Last' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Last'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>

                                            </tr>
                                            <!--- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" >
                                            <input  type="hidden" id="searchSwitchNo" name="searchSwitchNo" value="${searchSwitchNo}" >
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div>
                                    <form id="form2" name="form2" method="POST" enctype="multipart/form-data"  action="surveyCont" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="953">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                            <b class="heading">Survey Type</b>
                                            <select id="survey_type" name="survey_type" onchange="selectServeyType('');">
                                                <option value="pole_type_survey">Pole Type Survey</option>
                                                <option value="switching_type_survey">Switching Point Type Survey</option>
                                                <option value="upload_file_data">Upload File Data</option>
                                            </select>
                                            <b class="heading">Pole No.</b>
                                            <input class="input" type="text" id="pole_no" name="pole_no" value="" >

                                            </tr><br><br>
                                            <table id="table5"  class="content" border="0"  align="center" width="953">
                                                <tr>


                                                    <th class="heading1" style=" width:18%">Survey File No</th>
                                                    <td>

                                                        <input class="input" type="text" id="survey_file_no" name="survey_file_no" value="" size="22" disabled>
                                                    </td>
                                                    <th class="heading1" style=" width:16%">Survey Page No</th>
                                                    <td><input class="input" type="text" id="survey_page_no" name="survey_page_no" value="" size="26" disabled></td>

                                                    <th class="heading1" style=" width:11%">Survey By</th>
                                                    <td><input class="input" type="text" id="survey_by" name="survey_by" value="" size="26" disabled></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" style=" width:18%">Survey Date</th>
                                                    <td><input class="input" type="text" id="survey_date" name="survey_date" value="" size="18" disabled>
                                                        <a href="#" onclick="setYears(1947,2022);showCalender(this,'survey_date')">
                                                            <img alt=""  src="images/calender.png">
                                                        </a>
                                                    </td>
                                                    <th class="heading1" id="remark_header">Remark</th>
                                                    <td><input class="input" type="text" id="remark1" name="remark1" value="" size="26" disabled></td>
                                                    <th class="heading1">Upload Image</th>
                                                    <td>
                                                        <input type="file" id="image_name" name="image_name" value="" size="35" multiple="muliple" >
                                                    </td>
                                                </tr>
                                            </table>
                                        </table>

                                        <!-- hidden fields-->
                                        <table id="table3" class="content" border="0"  align="center" width="600" style="">
                                            <tr>
                                                <td>
                                                    <b class="heading1">Meter Status</b>
                                                    <select id="meter_status" name="meter_status" disabled>
                                                        <option value="Y">Yes</option>
                                                        <option value="N">No</option>
                                                    </select> </td>
                                                <td>
                                                    <b class="heading1">Meter Functional</b>
                                                    <select id="meter_functional" name="meter_functional" disabled>
                                                        <option value="Y">Yes</option>
                                                        <option value="N">No</option>
                                                    </select> </td>
                                                <td>        <b class="heading1">Meter No</b>
                                                    <input class="input" type="text" id="meter_no" name="meter_no" value="" size="23" disabled></td>
                                                <td id="reason"><b class="heading1">Reason</b>
                                                    <input class="input" type="text" id="reason_type" name="reason_type" value="" size="20" disabled></td>

                                            </tr>
                                            <tr>
                                                <td>     <b class="heading1">Fuse Functional</b>
                                                    <select id="fuse_functional" name="fuse_functional" disabled>
                                                        <option value="Y">Yes</option>
                                                        <option value="N">No</option>
                                                    </select> </td>
                                                <td>

                                                    <b class="heading1">Fuse quantity</b>
                                                    <select id="fuse_quantity" name="fuse_quantity" disabled>
                                                        <option value="Y">3</option>
                                                        <option value="N">2</option>
                                                        <option value="N">1</option>
                                                    </select> </td>
                                                <td><b class="heading1">Fuse Type1</b>
                                                    <input class="input" type="text" id="fuse_type1" name="fuse_type1" value="" size="20" disabled></td>

                                                <td><b class="heading1">Fuse1</b>
                                                    <input class="input" type="text" id="fuse_1" name="fuse_1" value="" size="20" disabled></td>

                                            </tr>
                                            <tr>

                                                <td><b class="heading1">Fuse Type2</b>
                                                    <input class="input" type="text" id="fuse_type2" name="fuse_type2" value="" size="20" disabled></td>
                                                <td><b class="heading1">Fuse2</b>
                                                    <input class="input" type="text" id="fuse_2" name="fuse_2" value="" size="20" disabled></td>
                                                <td><b class="heading1">Fuse Type3</b>
                                                    <input class="input" type="text" id="fuse_type3" name="fuse_type3" value="" size="20" disabled></td>
                                                <td><b class="heading1">Fuse3</b>
                                                    <input class="input" type="text" id="fuse_3" name="fuse_3" value="" size="20" disabled></td>


                                            </tr>

                                            <tr>
                                                <td>
                                                    <b class="heading1">MCCB Functional</b>
                                                    <select id="mccb_functional" name="mccb_functional" disabled>
                                                        <option value="Y">Yes</option>
                                                        <option value="N">No</option>
                                                    </select> </td>
                                                <td>
                                                    <b class="heading1">MCCB quantity</b>
                                                    <select id="mccb_quantity" name="mccb_quantity" disabled>
                                                        <option value="Y">3</option>
                                                        <option value="N">2</option>
                                                        <option value="N">1</option>
                                                    </select> </td>
                                                <td><b class="heading1">MCCB Type1</b>
                                                    <input class="input" type="text" id="mccb_type1" name="mccb_type1" value="" size="20" disabled></td>

                                                <td><b class="heading1">MCCB1</b>
                                                    <input class="input" type="text" id="mccb_1" name="mccb_1" value="" size="20" disabled></td>


                                            </tr>
                                            <tr>
                                                <td><b class="heading1">MCCB Type2</b>
                                                    <input class="input" type="text" id="mccb_type2" name="mccb_type2" value="" size="20" disabled></td>

                                                <td><b class="heading1">MCCB2</b>
                                                    <input class="input" type="text" id="mccb_2" name="mccb_2" value="" size="20" disabled></td>
                                                <td><b class="heading1">MCCB Type3</b>
                                                    <input class="input" type="text" id="mccb_type3" name="mccb_type3" value="" size="20" disabled></td>

                                                <td><b class="heading1">MCCB3</b>
                                                    <input class="input" type="text" id="mccb_3" name="mccb_3" value="" size="20" disabled></td>

                                            </tr>
                                            <tr>
                                                <td>  <b class="heading1">Contacter Functional</b>
                                                    <select id="contacter_functional" name="contacter_functional" disabled>
                                                        <option value="Y">Yes</option>
                                                        <option value="N">No</option>
                                                    </select>
                                                </td>

                                                <td><b class="heading1">Contacter Type </b>
                                                    <input class="input" type="text" id="contacter_type" name="contacter_type" value="" size="20" disabled></td>
                                                <td><b class="heading1">Contacter Make</b>
                                                    <input class="input" type="text" id="contacter_make" name="contacter_make" value="" size="20" disabled></td>
                                                <td><b class="heading1">Contacter Capacity</b>
                                                    <input class="input" type="text" id="contacter_capacity" name="contacter_capacity" value="" size="20" disabled></td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <b class="heading1">No. Of Phase</b>
                                                    <select id="phase_no" name="phase_no" disabled>
                                                        <option value="Y">3</option>
                                                        <option value="N">2</option>
                                                        <option value="N">1</option>
                                                    </select> </td>
                                                <td><b class="heading1">B Phase</b>
                                                    <input class="input" type="text" id="b_phase" name="b_phase" value="" size="20" disabled></td>

                                                <td><b class="heading1">R Phase</b>
                                                    <input class="input" type="text" id="r_phase" name="r_phase" value="" size="20" disabled></td>

                                                <td><b class="heading1">Y Phase</b>
                                                    <input class="input" type="text" id="y_phase" name="y_phase" value="" size="20" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Meter Phase</th>
                                                <td><input class="input" type="text" id="meter_phase" name="meter_phase" value="" size="20" disabled></td>



                                                <th class="heading1">Meter Reading</th>
                                                <td><input class="input" type="text" id="meter_reading" name="meter_reading" value="" size="20" disabled></td>

                                            </tr>
                                            <tr>
                                                <th class="heading1">Power</th>
                                                <td><input class="input" type="text" id="power" name="power" value="" size="20" disabled></td>

                                                <th class="heading1">Remark</th>
                                                <td><input class="input" type="text" id="remark2" name="remark2" value="" size="20" disabled></td>

                                            </tr>
                                            <tr>
                                                <th class="heading1">Auto Switch Type</th>
                                                <td><input class="input" type="text" id="auto_switch_type" name="auto_switch_type" value="" size="20" disabled></td>

                                                <th class="heading1">Main Switch Type</th>
                                                <td><input class="input" type="text" id="main_switch_type" name="main_switch_type" value="" size="20" disabled></td>

                                            </tr>
                                            <tr>
                                                <th class="heading1">Main Switch Rating</th>
                                                <td><input class="input" type="text" id="main_switch_rating" name="main_switch_rating" value="" size="20" disabled></td>

                                                <th class="heading1">Enclosure Type</th>
                                                <td><input class="input" type="text" id="enclosure_type" name="enclosure_type" value="" size="20" disabled></td>

                                            </tr>
                                        </table>
                                        <table id="table4" class="content" border="0"  align="center" width="600" style="">
                                            <tr>
                                                <th class="heading1">Upload Image</th>
                                                <td>
                                                    <input type="file" id="image_name" name="image_name" value="" size="35" multiple="muliple" >
                                                </td>

                                            </tr>


                                        </table>
                                        <table>  <tr>
                                                <td align='center' colspan="6">                                                    
                                                    <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="button" name="new" id="new" value="New" onclick="makeEditable(id)"> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="cancel" value="Cancel" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchPoleNo" value="${searchPoleNo}" >
                                            <input type="hidden"  name="searchSwitchNo" value="${searchSwitchNo}" >
                                            <input type="hidden"  id="survey_id" name="survey_id" value="" >
                                            <input type="hidden"  id="survey_rev_no" name="survey_rev_no" value="" >
                                            <input type="hidden"  id="switching_point_survey_id" name="switching_point_survey_id" value="" >
                                            <input type="hidden"  id="switching_point_survey_rev_no" name="switching_point_survey_rev_no" value="" >
                                            <input type="hidden"  id="switching_point_detail_id" name="switching_point_detail_id" value="" >
                                            <input type="hidden"  id="switching_rev_no" name="switching_rev_no" value="" >
                                            <input type="hidden"  id="pole_id" name="pole_id" value="" >
                                            <input type="hidden"  id="pole_rev_no" name="pole_rev_no" value="" >
                                            <input type="hidden"  id="status" name="status" value="" >

                                        </table>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </table>

                </DIV>
            </td>
            <tr>
                <td>
                    <jsp:include page="calendar_view"/>

                </td>
            </tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>
