<%-- 
    Document   : newSwitchingPointSurveyView
    Created on : Jul 29, 2014, 11:36:10 PM
    Author     : JPSS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" src="JS/table.js"></script>

<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script language="javascript" type="text/javascript" src="JS/calendar.js"></script>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<link rel="stylesheet" type="text/css" href="css/calendar.css" > 
        <script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>

<%-- <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript" src="JS/calendar.js"></script>--%>
<link rel="stylesheet" type="text/css" href="css/calendar.css" > 
<script type="text/javascript" language="javascript">
    
    jQuery(function(){
         $("#searchByDate").datepicker({

                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'yy-mm-dd',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });
         $("#searchToDate").datepicker({

                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'yy-mm-dd',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });
         $("#searchFeeder").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getSearchFeeder"}
            }
        });
         $("#searchTypeOfConnection").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getSearchTypeOfConnection"}
            }
        });
        $("#searchPoleNo").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getSearchPole_No"}               
            }
        });
        $("#searchIvrsNo").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getSearchIvrsNo"}
            }
        });
        $("#pole_no").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getPole_No"},
                action2: function() { return document.getElementById("survey_type").value;}
            }
        });
        $("#survey_type").result(function(event, data, formatted){
            document.getElementById("pole_no").value = "";
            $("#pole_no").flushCache();
        });
        $("#fuse_type1").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
        $("#starter_type").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getStarterType"}
            }
        });
        $("#mccb_type1").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
      
        $("#fuse_type2").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
        $("#starter_make").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getStarterMake"}
            }
        });
        $("#mccb_type2").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
        
        $("#fuse_type3").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
       
        $("#mccb_type3").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
        $("#auto_switch_type").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getAutoSwitchType"}
            }
        });
        $("#main_switch_type").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getMainSwitchType"}
            }
        });
        $("#enclosure_type").autocomplete("newSwitchingPointSurveyCont.do", {
            extraParams: {
                action1: function() { return "getEnclosureType"}
            }
        });
        $("#showAllRecords").click(function(){
            $("#searchPoleNo").val('');
            $("#searchPoleNo").flushCache();
            $("#searchIvrsNo").val('');
            $("#searchIvrsNo").flushCache();
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
            }else if(check =='tubewell_type_survey'){
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
        }else if(survey_type=='Switching Point'){// alert('1');
            $("#survey_type").html("<option value='tubewell_type_survey'>Switching Point</option>"); // alert('2');
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
        $("#starter_functional").attr("disabled",false);
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
        $("#starter_make").attr("disabled",false);
        $("#starter_type").attr("disabled",false);
        $("#starter_capacity").attr("disabled",false);
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
        $("#remark2").attr("disabled",false);
        $("#survey_pole_no").attr("disabled",false);
        $("#longitude").attr("disabled",false);
        $("#latitude").attr("disabled",false);
        $("#hours").attr("disabled",false);
        $("#survey_meter_no").attr("disabled",false);
        document.getElementById("save").disabled = true;
        document.getElementById("save_As").disabled = false;
        document.getElementById("cancel").disabled = false;
        document.getElementById("revise").disabled = false;

        //  $('#pole_no').attr('readonly', true);
     
              

        if(id == 'new') {
            $('#pole_no').attr('readonly', false);
            //$("#survey_type").html("<option value='tubewell_type_survey'>TubeWell Type Survey</option>");
            var opt  = document.createElement("option");
            //opt.text = 'Pole Type Survey';
            //  opt.value='pole_type_survey';
            opt.text = 'Upload File Data';
            opt.value='upload file data';


            //document.getElementById("survey_type").options.add(opt);
                                   

            document.getElementById('form2').reset();
            //document.getElementById("survey_type").value = value;
            document.getElementById("survey_id").value = '';
            document.getElementById("survey_rev_no").value = '';
            document.getElementById("pole_no").focus();
            document.getElementById("revise").disabled = true;
            document.getElementById("save").disabled = false;
            document.getElementById("save_As").disabled = true;
            document.getElementById("cancel").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 47);
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
                            var noOfColumns =47;
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
                            document.getElementById("tube_well_survey_id").value= document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                            document.getElementById("tube_well_survey_rev_no").value= document.getElementById(t1id + (lowerLimit +4)).innerHTML;

                            document.getElementById("survey_date").value= document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                            document.getElementById("survey_file_no").value= document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
                            document.getElementById("survey_page_no").value= document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
                            document.getElementById("survey_by").value= document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
                            document.getElementById("pole_no").value= document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
                            document.getElementById("survey_pole_no").value= document.getElementById(t1id + (lowerLimit +10)).innerHTML;
                            //    document.getElementById("survey_type").value= document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
                            var survey_type = document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
                            selectServeyType(survey_type);
                            document.getElementById("status").value= document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
                            document.getElementById("meter_no").value= document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
                            document.getElementById("meter_functional").value= document.getElementById(t1id + (lowerLimit + 14)).innerHTML;
                            document.getElementById("r_phase").value= document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
                            document.getElementById("y_phase").value= document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
                            document.getElementById("b_phase").value= document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
                            document.getElementById("power").value= document.getElementById(t1id + (lowerLimit + 18)).innerHTML;
                            document.getElementById("fuse_functional").value= document.getElementById(t1id + (lowerLimit + 19)).innerHTML;
                            document.getElementById("fuse_1").value= document.getElementById(t1id + (lowerLimit + 20)).innerHTML;
                            document.getElementById("fuse_2").value= document.getElementById(t1id + (lowerLimit + 21)).innerHTML;
                            document.getElementById("fuse_3").value= document.getElementById(t1id + (lowerLimit + 22)).innerHTML;
                            document.getElementById("fuse_type1").value= document.getElementById(t1id + (lowerLimit + 23)).innerHTML;
                            document.getElementById("fuse_type2").value= document.getElementById(t1id + (lowerLimit + 24)).innerHTML;
                            document.getElementById("fuse_type3").value= document.getElementById(t1id + (lowerLimit + 25)).innerHTML;
                            document.getElementById("mccb_functional").value= document.getElementById(t1id + (lowerLimit + 26)).innerHTML;
                            document.getElementById("mccb_1").value= document.getElementById(t1id + (lowerLimit + 27)).innerHTML;
                            document.getElementById("mccb_2").value= document.getElementById(t1id + (lowerLimit + 28)).innerHTML;
                            document.getElementById("mccb_3").value= document.getElementById(t1id + (lowerLimit + 29)).innerHTML;
                            document.getElementById("mccb_type1").value= document.getElementById(t1id + (lowerLimit +30)).innerHTML;
                            document.getElementById("mccb_type2").value= document.getElementById(t1id + (lowerLimit + 31)).innerHTML;
                            document.getElementById("mccb_type3").value= document.getElementById(t1id + (lowerLimit + 32)).innerHTML;
                            document.getElementById("starter_functional").value= document.getElementById(t1id + (lowerLimit + 33)).innerHTML;
                            document.getElementById("starter_type").value= document.getElementById(t1id + (lowerLimit + 34)).innerHTML;
                            document.getElementById("starter_make").value= document.getElementById(t1id + (lowerLimit + 35)).innerHTML;
                            document.getElementById("starter_capacity").value= document.getElementById(t1id + (lowerLimit + 36)).innerHTML;
                            document.getElementById("latitude").value= document.getElementById(t1id + (lowerLimit + 37)).innerHTML;
                            document.getElementById("longitude").value= document.getElementById(t1id + (lowerLimit + 38)).innerHTML;
                                  
                            document.getElementById("auto_switch_type").value= document.getElementById(t1id + (lowerLimit + 39)).innerHTML;
                            // document.getElementById("main_switch_rating").value= document.getElementById(t1id + (lowerLimit + 40)).innerHTML;
                            document.getElementById("main_switch_type").value= document.getElementById(t1id + (lowerLimit +40)).innerHTML;
                            document.getElementById("main_switch_rating").value= document.getElementById(t1id + (lowerLimit +41)).innerHTML;
                            document.getElementById("enclosure_type").value= document.getElementById(t1id + (lowerLimit + 42)).innerHTML;
                            document.getElementById("meter_reading").value= document.getElementById(t1id + (lowerLimit + 43)).innerHTML;
                            document.getElementById("meter_phase").value= document.getElementById(t1id + (lowerLimit + 44)).innerHTML;
                            document.getElementById("survey_meter_no").value=document.getElementById(t1id + (lowerLimit + 45)).innerHTML;
                            document.getElementById("meter_name_auto").value=document.getElementById(t1id + (lowerLimit + 46)).innerHTML;
                            //  d
                            //
                            //  document.getElementById("remark1").value= document.getElementById(t1id + (lowerLimit + 21)).innerHTML;
                           
                            //alert(document.getElementById("switching_point_detail_id"+rowNo).value);
                            // document.getElementById("switching_point_detail_id").value= document.getElementById("switching_point_detail_id" + rowNo).value;
                            //  document.getElementById("switching_rev_no").value= document.getElementById("switching_rev_no" + rowNo).value;
                            //document.getElementById("pole_id").value= document.getElementById("pole_id" + rowNo).value;
                            



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
                        function displayMapList(id){
                            var poll_no=document.getElementById("searchPoleNo").value;
                            var ivrs_no=document.getElementById("searchIvrsNo").value;
                            var fileNo=document.getElementById("searchFileNo").value;
                            var pageNo=document.getElementById("searchPageNo").value;
                            var date=document.getElementById("searchByDate").value;
                            var feeder=document.getElementById("searchFeeder").value;
                            var typeOfConnection=document.getElementById("searchTypeOfConnection").value;
                            var dateTo=document.getElementById("searchToDate").value;
                            var meterFunctional=document.getElementById("searchMeterFunctional").value;
                            var queryString = "";
                            if(id == "viewPdf1")
                                queryString = "task1=generateMapReport&report_type=1&poll_no="+poll_no+"&ivrs_no="+ivrs_no+"&PageNo="+pageNo+"&FileNo="+fileNo+"&Date="+date+"&meterFunctional="+meterFunctional+"&feeder="+feeder+"&typeOfConnection="+typeOfConnection+"&dateTo="+dateTo;
                            else if(id == "viewPdf2")
                                queryString = "task1=generateMapReport&report_type=2&poll_no="+poll_no+"&ivrs_no="+ivrs_no+"&PageNo="+pageNo+"&FileNo="+fileNo+"&Date="+date+"&meterFunctional="+meterFunctional+"&feeder="+feeder+"&typeOfConnection="+typeOfConnection+"&dateTo="+dateTo;
                            else if(id == "viewXLS1")
                                queryString = "task1=generateXLSReport&report_type=1&poll_no="+poll_no+"&ivrs_no="+ivrs_no+"&PageNo="+pageNo+"&FileNo="+fileNo+"&Date="+date+"&meterFunctional="+meterFunctional+"&feeder="+feeder+"&typeOfConnection="+typeOfConnection+"&dateTo="+dateTo;
                            else if(id == "viewXLS2")
                                queryString = "task1=generateXLSReport&report_type=2&poll_no="+poll_no+"&ivrs_no="+ivrs_no+"&PageNo="+pageNo+"&FileNo="+fileNo+"&Date="+date+"&meterFunctional="+meterFunctional+"&feeder="+feeder+"&typeOfConnection="+typeOfConnection+"&dateTo="+dateTo;
                            
                            var url = "newSwitchingPointSurveyCont.do?"+queryString;
                            popupwin = openPopUp(url, "TUbeWell User Details", 500, 1000);

                        }
                        function displayMapList1(id){

                            var ivrs_no=document.getElementById("searchIvrsNo").value;       
                            var queryString = "";

                                 
                                 if(ivrs_no==null|| ivrs_no.length==0)
                                 {
                                        alert("IVRS IS REQUIRED");
                               }
                               else
                               {
                                    queryString = "task1=generatePDFReport&ivrs_no="+ivrs_no;
                                    var url = "newSwitchingPointSurveyCont.do?"+queryString;
                                    popupwin = openPopUp(url, "TUbeWell User Details", 500, 1000);
                               }
                            

                        }
                        function displayImagePdfList(survey_id,meter_name_auto)
                        {
                            var survey_id=survey_id;
                            var meter_name_auto=meter_name_auto;
                            
                            var queryString = "";
                                queryString = "task1=generatePDFReport&survey_id="+survey_id+"&meter_name_auto="+meter_name_auto;
                            var url = "newSwitchingPointSurveyCont.do?"+queryString;
                            popupwin = openPopUp1(url, "TUbeWell User Details", 500, 1000);

                        }
                        function openPopUp1(url, window_name, popup_height, popup_width) {
                            var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                            var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                            var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
                            return window.open(url, window_name, window_features);
                        }
                        function openPopUp(url, window_name, popup_height, popup_width) {
                            var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                            var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                            var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                            return window.open(url, window_name, window_features);
                        }
                        function openMap(longitude, lattitude) {
                            var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
                            var y = longitude;//$.trim(doc.getElementById(logitude).value);

                            var url="newSwitchingPointSurveyCont.do?task1=showMapWindow&logitude="+y+"&lattitude="+x;
                            popupwin = openPopUp(url, "",  580, 620);
                        }
                        function openCurrentMap(longitude, lattitude) {
                            var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
                            var y = longitude;//$.trim(doc.getElementById(logitude).value);
                            var url="surveyCordinatesCont.do?task=showMapWindow&logitude="+y+"&lattitude="+x;
                            window.open(url);
                            //popupwin = openPopUp(url, "",  580, 620);
                        }
                        function isNumberKey(evt){
                            var charCode = (evt.which) ? evt.which : event.keyCode
                            if (charCode > 31 && (charCode < 48 || charCode > 57))
                                return false;
                            return true;
                        }
                        function displayImageList(id){
                                 debugger;
                            var image = document.getElementById('image'+id).value;
                            var survey_id=document.getElementById('survey_id'+id).value;
                            var image_id=document.getElementById('image_id'+id).value;
                            alert(image,survey_id,image_id);
                            var queryString = "task1=viewImage&image_name="+image+"&general_image_details_id="+image_id+"&survey_id="+survey_id ;
                            var url = "newSwitchingPointSurveyCont.do?"+queryString;
                            popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);

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
                                    <form name="form0" method="POST" action="newSwitchingPointSurveyCont.do">
                                        <input id="map" class="" type="button" onclick="openCurrentMap('' , '');" value="Map">
                                        <table align="center" class="heading1" width="800" border="0">
                                            <tr>
                                                <td>Page No<input class="input" type="text" id="searchPageNo" name="searchPageNo" value="${searchPageNo}" size="15" ></td>
                                                <td>File No<input class="input" type="text" id="searchFileNo" name="searchFileNo" value="${searchFileNo}" size="15" ></td>
                                                <td>Pole No<input class="input" type="text" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" size="15" ></td>
                                                <td>Meter Functional
                                                     <select class="dropdown"  name="searchMeterFunctional" id="searchMeterFunctional" value="${searchMeterFunctional}">
                                                         <option value=""${searchMeterFunctional == ""?'selected':''}>Select</option>
                                                         <option value="Y"${searchMeterFunctional == "Y"?'selected':''} >Yes</option>
                                                        <option value="N" ${searchMeterFunctional == "N"?'selected':''}>No</option>
                                                    </select>
                                                </td>
                                                <td>Ivrs No<input class="input" type="text" id="searchIvrsNo" name="searchIvrsNo" value="${searchIvrsNo}" size="15" ></td>
                                            </tr>
                                            <tr align="center">
                                                <td>Feeder<input class="input" type="text" id="searchFeeder" name="searchFeeder" value="${searchFeeder}" size="15" ></td>
                                                <td>Type Of Connection<input class="input" type="text" id="searchTypeOfConnection" name="searchTypeOfConnection" value="${searchTypeOfConnection}" size="15" ></td>
                                                <td >From Date<input class="input" type="text" id="searchByDate" name="searchByDate" value="${searchByDate}" size="10"></td>
                                                <td >To Date<input class="input" type="text" id="searchToDate" name="searchToDate" value="${searchToDate}" size="10"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf3" name="viewPdf" value="    3" onclick="displayMapList1(id)" style="display: none">
                                            </tr>
                                                  <%--  <td style="display:none">Switching Point No<input class="input" type="text" id="searchIvrsNo" name="searchIvrsNo" value="${searchIvrsNo}" size="20" ></td>--%>
                                            <tr>    <td colspan="5" align="center"><input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                <input class="button" type="submit" name="task2" id="showAllRecords" value="Show All Records">
                                                <input type="button" class="pdf_button" id="viewPdf1" name="viewPdf" value="    1" onclick="displayMapList(id)">
                                                <input type="button" class="pdf_button" id="viewPdf2" name="viewPdf" value="    2" onclick="displayMapList(id)">
                                                <input type="button" class="" id="viewXLS1" name="viewXLS" value="Excel 1" onclick="displayMapList(id)">
                                                <input type="button" class="" id="viewXLS2" name="viewXLS" value="Excel 2" onclick="displayMapList(id)"></td>

                                                
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="newSwitchingPointSurveyCont.do">
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
                                                <th style="white-space: normal" class="heading">Survey Pole No</th>
                                                <!--  <th style="white-space: normal; display:none" class="heading">Pole Rev No</th>-->
                                                <th style="white-space: normal" class="heading">Survey type</th>
                                                <th style="white-space: normal" class="heading">Status</th>
                                                <!--   <th style="white-space: normal" class="heading">Switching Point No</th>
                                                   <th style="white-space: normal" class="heading">Switching Point Rev No</th> -->
                                                <th style="white-space: normal" class="heading">Meter Service No</th>
                                                <th style="white-space: normal" class="heading">Meter No</th>
                                                <th style="white-space: normal" class="heading">Meter Functional</th>
                                                <th style="white-space: normal" class="heading">R Phase</th>
                                                <th style="white-space: normal" class="heading">Y Phase</th>
                                                <th style="white-space: normal" class="heading">B Phase</th>
                                                <th style="white-space: normal" class="heading">Power</th>
                                                <th style="white-space: normal" class="heading">Calculated Power</th>
                                                <th style="white-space: normal" class="heading">Projected Consumption</th>
                                                <th style="white-space: normal" class="heading">Fuse Functional</th>
                                                <th style="white-space: normal" class="heading">Fuse1</th>
                                                <th style="white-space: normal" class="heading">Fuse2</th>
                                                <th style="white-space: normal" class="heading">Fuse3</th>
                                                <th style="white-space: normal" class="heading">Fuse Type1</th>
                                                <th style="white-space: normal" class="heading">Fuse Type2</th>
                                                <th style="white-space: normal" class="heading">Fuse Type3</th>
                                                <th style="white-space: normal" class="heading">Mccb Functional</th>
                                                <th style="white-space: normal" class="heading">Mccb1</th>
                                                <th style="white-space: normal" class="heading">Mccb2</th>
                                                <th style="white-space: normal" class="heading">Mccb3</th>
                                                <th style="white-space: normal" class="heading">Mccb Type1</th>
                                                <th style="white-space: normal" class="heading">Mccb Type2</th>
                                                <th style="white-space: normal" class="heading">Mccb Type3</th>
                                                <th style="white-space: normal" class="heading">Contacter Functional</th>
                                                <th style="white-space: normal" class="heading">Contacter Type</th>
                                                <th style="white-space: normal" class="heading">Contacter Make</th>
                                                <th style="white-space: normal" class="heading">Contacter Capacity</th>
                                                <th style="white-space: normal" class="heading">Latitude</th>
                                                <th style="white-space: normal" class="heading">Longitude</th>
                                                <th style="white-space: normal" class="heading">Auto Switch Type</th>
                                                <th style="white-space: normal" class="heading">Main Switch Type</th>
                                                <th style="white-space: normal" class="heading">Main Switch Rating</th>
                                                <th style="white-space: normal" class="heading">Enclosure Type</th>
                                                <th style="white-space: normal" class="heading">Meter Reading</th>
                                                <th style="white-space: normal" class="heading">Meter Phase</th>                                                
                                                <th style="white-space: normal" class="heading">Previous Reading</th>
                                                <th style="white-space: normal" class="heading">Consume Unit</th>
                                                <th style="white-space: normal" class="heading">Amount</th>
                                                <th style="white-space: normal" class="heading">Update Detail</th>
                                                <th style="white-space: normal" class="heading">View Map</th>
                                                <th style="white-space: normal" class="heading">View Image</th>
                                                <th style="white-space: normal" class="heading">View PDF</th>


                                            </tr>

                                            <c:forEach var="surveyTypeBean" items="${requestScope['surveyTypeList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >

                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${surveyTypeBean.survey_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td style="display:none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_rev_no}</td>
                                                    <td style="display:none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.tube_well_survey_id}</td>
                                                    <td style="display:none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.tube_well_survey_rev_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_date}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_file_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_page_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_by}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.pole_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_pole_no}</td>
                                                    <%--<td style="display:none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.pole_rev_no}</td>--%>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.survey_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.status}</td>
                                                    <%--    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.pole_no_s}</td>
                                                          <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.switching_point_detail_rev_no}</td> --%>
                                                    <td id=""  onclick="" >${surveyTypeBean.service_conn_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.meter_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.meter_functional}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.r_phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.y_phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.b_phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.power}</td>
                                                    <td id=""  onclick="" >${surveyTypeBean.calculated_power}</td>
                                                    <td id=""  onclick="" >${surveyTypeBean.projected_consumption}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.fuse_functional}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.fuse1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.fuse2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.fuse3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.fuse_type1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.fuse_type2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.fuse_type3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.mccb_functional}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.mccb1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.mccb2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.mccb3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.mccb_type1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.mccb_type2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.mccb_type3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.starter_functional}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.starter_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.starter_make}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.starter_capacity}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.latitude}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.longitude}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.auto_switch_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.main_switch_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.main_switch_rating}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.enclosure_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.meter_reading}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${surveyTypeBean.meter_phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display: none" onclick="fillColumns(id)" >${surveyTypeBean.survey_meter_no}</td>
                                                    <td id=""  onclick="" >${surveyTypeBean.previous_reading}</td>
                                                    <td id=""  onclick="" >${surveyTypeBean.consume_unit}</td>
                                                    <td id=""  onclick="" >${surveyTypeBean.amount}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display: none"  onclick="fillColumns(id)" >${surveyTypeBean.meter_name_auto}</td>
                                                    <td ><a target="_blank" href="switchingPointSurvey?task=showSurveyData&meter_name_auto=${surveyTypeBean.meter_name_auto}&service_conn_no=${surveyTypeBean.service_conn_no}&surveyId=${surveyTypeBean.survey_id}" id="surveyView${loopCounter.count}">Update</a></td>
                                                    <td>
                                                        <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${surveyTypeBean.longitude}' , '${surveyTypeBean.latitude}');"/>
                                                    </td>
                                                    <td>
                                                        <input type="button" class="button" id="${loopCounter.count}" name="view_image" value="View Image" onclick="displayImageList(id);" >
                                                    </td>
                                                    <td>
                                                        <input type="button" class="pdf_button" id="${loopCounter.count}" name="viewPdfimage" value="" onclick="displayImagePdfList('${surveyTypeBean.survey_id}' , '${surveyTypeBean.meter_name_auto}')" >
                                                    </td>
                                                </tr>
                                                <input type="hidden"  name="image" id="survey_id${loopCounter.count}" value="${surveyTypeBean.survey_id}">
                                                <input type="hidden"  name="image" id="image${loopCounter.count}" value="${surveyTypeBean.image_name}">
                                                <input type="hidden"  name="image_id" id="image_id${loopCounter.count}" value="${surveyTypeBean.general_image_detials_id}">
                                                <input type="hidden" id="size_of_list" value="${fn:length(requestScope['surveyTypeList'])}">
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="24">
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
                                            <input type="hidden" id="meter_name_auto" name="meter_name_auto" value="${meter_name_auto}">
                                            <input  type="hidden" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" >
                                            <input  type="hidden" id="searchIvrsNo" name="searchIvrsNo" value="${searchIvrsNo}" >
                                            <input type="hidden" id="searchFileNo" name="searchFileNo" value="${searchFileNo}"  >
                                            <input type="hidden" id="searchPageNo" name="searchPageNo" value="${searchPageNo}" >
                                            <input  type="hidden" id="searchByDate" name="searchByDate" value="${searchByDate}">
                                            <input  type="hidden" id="searchMeterFunctional" name="searchMeterFunctional" value="${searchMeterFunctional}">
                                            <input type="hidden" id="searchFeeder" name="searchFeeder" value="${searchFeeder}"  >
                                            <input type="hidden" id="searchTypeOfConnection" name="searchTypeOfConnection" value="${searchTypeOfConnection}" >
                                            <input  type="hidden" id="searchToDate" name="searchToDate" value="${searchToDate}">
                                            
                                            
                                            
                                            <input  type="hidden" id="searchMeterFunctional" name="searchMeterFunctional" value="${searchMeterFunctional}">
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div>
                                    <form id="form2" name="form2" method="POST" enctype="multipart/form-data"  action="newSwitchingPointSurveyCont.do" onsubmit="return verify()">
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
                                                <option value="tubewell_type_survey">Switching Point Type Survey</option>
                                                <option value="upload_file_data">Upload File Data</option>
                                            </select>
                                            <b class="heading">Pole No.</b>
                                            <input class="input" type="text" id="pole_no" name="pole_no" value="" required>

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

                                                    <th class="heading1">Upload Image</th>
                                                    <td>
                                                        <input type="file" id="image_name" name="image_name" value="" size="35" multiple="muliple" >
                                                    </td>
                                                    <th class="heading1" style=" width:18%">Survey Meter No</th>
                                                    <td><input class="input" type="text" id="survey_meter_no" name="survey_meter_no" value="" size="18" disabled>

                                                    </td>
                                                    <td><input class="input" type="hidden" id="service_conn_no" name="service_conn_no" value="" size="18" disabled>

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
                                                    <input class="input" type="text" id="meter_no" name="meter_no" value="" size="23" required disabled></td>
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
                                                        <option value="3">3</option>
                                                        <option value="2">2</option>
                                                        <option value="1">1</option>
                                                    </select> </td>
                                                <td><b class="heading1">Fuse Type1</b>
                                                    <input class="input" type="text" id="fuse_type1" name="fuse_type1" value="" size="20" disabled></td>

                                                <td><b class="heading1">Fuse1</b>
                                                    <input class="input" type="text" id="fuse_1" name="fuse_1" value="" size="20" onkeypress="return isNumberKey(event)" disabled></td>

                                            </tr>
                                            <tr>

                                                <td><b class="heading1">Fuse Type2</b>
                                                    <input class="input" type="text" id="fuse_type2" name="fuse_type2" value="" size="20" disabled></td>
                                                <td><b class="heading1">Fuse2</b>
                                                    <input class="input" type="text" id="fuse_2" name="fuse_2" value="" size="20" onkeypress="return isNumberKey(event)" disabled></td>
                                                <td><b class="heading1">Fuse Type3</b>
                                                    <input class="input" type="text" id="fuse_type3" name="fuse_type3" value="" size="20" disabled></td>
                                                <td><b class="heading1">Fuse3</b>
                                                    <input class="input" type="text" id="fuse_3" name="fuse_3" value="" size="20" onkeypress="return isNumberKey(event)" disabled></td>


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
                                                    <input class="input" type="text" id="mccb_1" name="mccb_1" value="" size="20" onkeypress="return isNumberKey(event)" disabled></td>


                                            </tr>
                                            <tr>
                                                <td><b class="heading1">MCCB Type2</b>
                                                    <input class="input" type="text" id="mccb_type2" name="mccb_type2" value="" size="20" disabled></td>

                                                <td><b class="heading1">MCCB2</b>
                                                    <input class="input" type="text" id="mccb_2" name="mccb_2" value="" size="20" onkeypress="return isNumberKey(event)" disabled></td>
                                                <td><b class="heading1">MCCB Type3</b>
                                                    <input class="input" type="text" id="mccb_type3" name="mccb_type3" value="" size="20" disabled></td>

                                                <td><b class="heading1">MCCB3</b>
                                                    <input class="input" type="text" id="mccb_3" name="mccb_3" value="" size="20" onkeypress="return isNumberKey(event)"disabled></td>

                                            </tr>
                                            <tr>
                                                <td>  <b class="heading1">Contactor Functional</b>
                                                    <select id="starter_functional" name="starter_functional" disabled>
                                                        <option value="Y">Yes</option>
                                                        <option value="N">No</option>
                                                    </select>
                                                </td>

                                                <td><b class="heading1">Contactor Type </b>
                                                    <input class="input" type="text" id="starter_type" name="starter_type" value="" size="20" disabled></td>
                                                <td><b class="heading1">Contactor Make</b>
                                                    <input class="input" type="text" id="starter_make" name="starter_make" value="" size="20" disabled></td>
                                                <td><b class="heading1">Contactor Capacity</b>
                                                    <input class="input" type="text" id="starter_capacity" name="starter_capacity" value="" size="20"onkeypress="return isNumberKey(event)" disabled></td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <b class="heading1">No. Of Phase</b>
                                                    <select id="phase_no" name="phase_no" disabled>
                                                        <option value="3">3</option>
                                                        <option value="2">2</option>
                                                        <option value="1">1</option>
                                                    </select> </td>
                                                <td><b class="heading1">B Phase</b>
                                                    <input class="input" type="text" id="b_phase" name="b_phase" value="" size="20"onkeypress="return isNumberKey(event)" required disabled></td>

                                                <td><b class="heading1">R Phase</b>
                                                    <input class="input" type="text" id="r_phase" name="r_phase" value="" size="20" onkeypress="return isNumberKey(event)"disabled></td>

                                                <td><b class="heading1">Y Phase</b>
                                                    <input class="input" type="text" id="y_phase" name="y_phase" value="" size="20"onkeypress="return isNumberKey(event)" disabled></td>
                                            </tr>
                                            <tr>
                                                <td> <b class="heading1">Meter Phase</b>
                                                    <input class="input" type="text" id="meter_phase" name="meter_phase" value="" size="20" required disabled></td>

                                                <td> <b class="heading1">Meter Reading</b>
                                                    <input class="input" type="text" id="meter_reading" name="meter_reading" value="" size="20" disabled></td>
                                                <td> <b class="heading1">Survey Pole No</b>
                                                    <input class="input" type="text" id="survey_pole_no" name="survey_pole_no" value="" size="20" disabled></td>
                                                <td> <b class="heading1">Power</b>
                                                    <input class="input" type="text" id="power" name="power" value="" size="20" onkeypress="return isNumberKey(event)" disabled></td>

                                            </tr>

                                            <tr>
                                                <td><b class="heading1">Auto Switch Type</b>
                                                    <input class="input" type="text" id="auto_switch_type" name="auto_switch_type" value="" size="20" disabled></td>

                                                <td><b class="heading1">Main Switch Type</b>
                                                    <input class="input" type="text" id="main_switch_type" name="main_switch_type" value="" size="20" disabled></td>
                                                <td><b class="heading1">Main Switch Rating</b>
                                                    <input class="input" type="text" id="main_switch_rating" name="main_switch_rating" value="" size="20" disabled></td>

                                                <td><b  class="heading1">Enclosure Type</b>
                                                    <input class="input" type="text" id="enclosure_type" name="enclosure_type" value="" size="20" disabled></td>

                                            </tr>

                                            <tr>

                                                <td> <b class="heading1">Remark</b>
                                                    <input class="input" type="text" id="remark2" name="remark2" value="" size="20" disabled></td>
                                                <td> <b class="heading1">Latitude</b>
                                                    <input class="input" type="text" id="latitude" name="latitude" value="" size="20" disabled></td>
                                                <td> <b class="heading1">Longitude</b>
                                                    <input class="input" type="text" id="longitude" name="longitude" value="" size="20" disabled></td>
                                                <td> <b class="heading1">Hours</b>
                                                    <input class="input" type="text" id="hours" name="hours" value="" size="20" disabled></td>

                                            </tr>
                                        </table>
                                        <table id="table4" class="content" border="0"  align="center" width="600" style="">
                                            <tr>
                                                <th class="heading1">Upload Image</th>
                                                <td>
                                                    <input type="file" id="image_name1" name="image_name1" value="" size="35" multiple="muliple" >
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
                                            <input  type="hidden" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" >
                                            <input  type="hidden" id="searchIvrsNo" name="searchIvrsNo" value="${searchIvrsNo}" >
                                            <input type="hidden" id="searchFileNo" name="searchFileNo" value="${searchFileNo}"  >
                                            <input type="hidden" id="searchPageNo" name="searchPageNo" value="${searchPageNo}" >
                                            <input  type="hidden" id="searchByDate" name="searchByDate" value="${searchByDate}">
                                            <input  type="hidden" id="searchMeterFunctional" name="searchMeterFunctional" value="${searchMeterFunctional}">
                                            <input type="hidden" id="searchFeeder" name="searchFeeder" value="${searchFeeder}"  >
                                            <input type="hidden" id="searchTypeOfConnection" name="searchTypeOfConnection" value="${searchTypeOfConnection}" >
                                            <input  type="hidden" id="searchToDate" name="searchToDate" value="${searchToDate}">
                                            <input type="hidden"  id="survey_id" name="survey_id" value="" >
                                            <input type="hidden"  id="survey_rev_no" name="survey_rev_no" value="" >
                                            <input type="hidden"  id="tube_well_survey_id" name="tube_well_survey_id" value="" >
                                            <input type="hidden"  id="tube_well_survey_rev_no" name="tube_well_survey_rev_no" value="" >
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
