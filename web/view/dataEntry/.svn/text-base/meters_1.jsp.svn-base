

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Meters Table</title>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <%-- <link rel="stylesheet" type="text/css" href="css/jquery-ui.css"/> --%>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <link rel="stylesheet" href="datePicker/jquery.ui.all.css">
        <script type="text/javascript" src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
        <script type="text/javascript" language="javascript">
            var popupwin = null;
            /*$(document).ready(function(){
                $("#date").datepicker({
                    minDate: -700,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
                $("#effective_date").datepicker({
                    minDate: -700,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
                $("#date_filter").datepicker({
                    minDate: -700,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });

                document.getElementById("premises_type_hidden").value=$("option:selected", $("#premises_type")).text();
                document.getElementById("premises_type_search").value=$("option:selected", $("#conn")).text();
            });*/
            var popupwin = null;
            jQuery(function(){
                $("#organisation").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return  "Organisation" }
                    }
                });
              
                $("#city").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return  "City"}
                    }
                });
                
                $("#organ").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return  "Organisation"}
                    }
                });
                $("#location").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return  "Address"}
                    }
                });
                $("#switching_point").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return  "Street Light"},
                        Param1: function() { return $("option:selected", $("#company_name")).val()},
                        Param2: function() { return  $("option:selected", $("#circle_name")).val()}
                    }
                });
                $("#file_no").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return "file_no"},
                        Param1: function() { return document.getElementById("mete_ser_num_filter").value},
                        Param2: function() { return $("option:selected", $("#premises_type")).text()}
                    }
                });
                $("#file_no1").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return "file_no"},
                        Param1: function() { return document.getElementById("mete_ser_num_filter").value},
                        Param2: function() { return $("option:selected", $("#conn")).text()}
                    }
                });
                $("#con_type").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return "connection" },
                        Param1: function() { return document.getElementById("organ").value},
                        Param3 : function() { return  $("option:selected", $("#conn")).val()},
                        Param2: function() { return  $("option:selected", $("#conn")).text()},
                        Param6: function() { return  $("option:selected", $("#switching_point_sub_type")).text()},


                        Param4: function() { return $("option:selected", $("#search_company_name")).val()},
                        Param5: function() { return document.getElementById("search_circle_name").value}
                    }
                });
                $("#feeder_name").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return  "Feeder"},
                        Param1: function() { return $("option:selected", $("#company_name")).val()},
                        Param2: function() { return  $("option:selected", $("#circle_name")).val()}
                    }
                });

                $("#organisation").result(function(event, data, formatted){
                    setOrg_Name();
                });
                $("#division_filter").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return "division"},
                        //    Param1: function() { return $("option:selected", $("#search_company_name")).val();},
                        Param1: function() { return  $("#search_circle_name").val()}
                    }
                });
                $("#zone_filter").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return "Zone"},
                        Param1: function() { return document.getElementById("division_filter").value}
                    }
                });
                $("#feeder_filter").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return "feeder"},
                        Param1: function() { return document.getElementById("zone_filter").value}
                    }
                });
                $("#mete_ser_num_filter").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return "Meter"},
                        Param1: function() { return document.getElementById("feeder_filter").value}
                    }
                });

                $("#search_circle_name").autocomplete("meterCont1.do", {
                    extraParams: {
                        Param: function() { return "Circle"},
                        Param1: function() { return $("option:selected", $("#search_company_name")).val()}
                    }
                });


                $("#search_company_name").change(function(){
                    $("#search_circle_name").val('');
                    $("#search_circle_name").flushCache();
                    $("#division_filter").val('');
                    $("#division_filter").flushCache();
                    $("#zone_filter").val('');
                    $("#zone_filter").flushCache();
                    $("#feeder_filter").val('');
                    $("#feeder_filter").flushCache();
                    $("#mete_ser_num_filter").val('');
                    $("#mete_ser_num_filter").flushCache();
                });

                $("#conn").change(function(){
                    $("#con_type").val('');
                    $("#con_type").flushCache();

                });
                $("#search_circle_name").change(function(){
                    $("#division_filter").val('');
                    $("#division_filter").flushCache();
                    $("#zone_filter").val('');
                    $("#zone_filter").flushCache();
                    $("#feeder_filter").val('');
                    $("#feeder_filter").flushCache();
                    $("#mete_ser_num_filter").val('');
                    $("#mete_ser_num_filter").flushCache();
                });
                $("#division_filter").change(function(){
                    $("#zone_filter").val('');
                    $("#zone_filter").flushCache();
                    $("#feeder_filter").val('');
                    $("#feeder_filter").flushCache();
                    $("#mete_ser_num_filter").val('');
                    $("#mete_ser_num_filter").flushCache();
                });
                $("#zone_filter").change(function(){
                    $("#feeder_filter").val('');
                    $("#feeder_filter").flushCache();
                    $("#mete_ser_num_filter").val('');
                    $("#mete_ser_num_filter").flushCache();
                });
                $("#feeder_filter").change(function(){
                    $("#mete_ser_num_filter").val('');
                    $("#mete_ser_num_filter").flushCache();
                });

                $("#circle_name").change(function(){
                    $("#feeder_name").val('');
                    $("#feeder_name").flushCache();
                    $("#switching_point").val('');
                    $("#switching_point").flushCache();
                });
                $("#mete_ser_num_filter").change(function(){
                    $("#file_no1").val('');
                    $("#file_no1").flushCache();
                });
                $("#conn").change(function(){
                    $("#file_no1").val('');
                    $("#file_no1").flushCache();
                });
                //  var company_id=    document.getElementById("company_name").value;
                //  setCircle(company_id);
                //  setPremises(company_id)
            });
                        </script>
    <!--    <script>
            function setCircle(id){
                //     var company_id = $("#"+id).val();
                var company_id =document.getElementById("company_name").value;

                var queryString =  "task=getCircle&company_id=" + company_id;
                //  alert("queryString  :"+queryString);
                $.ajax({url: "meterCont1.do", async: false, data: queryString, success: function(response_data) {
                        //   alert("responce data"+response_data);
                        //  document.getElementById("circle_name").innerHTML = "<option value='0' selected>Select Circle..</option>";
                        $("#circle_name").html("");
                        var arr = response_data.split("&#;");
                        var i;
                        for(i = 0; i < arr.length - 1; i++) {
                            var opt = document.createElement("option");
                            opt.text = $.trim(arr[i].split(',')[0]);
                            opt.value = $.trim(arr[i].split(',')[1]);
                            document.getElementById("circle_name").options.add(opt);
                        }
                        if(i == 1) {
                            //document.getElementById("circle_name").value = arr[0];
                            // fillOrgDetails();
                        } else {
                            document.getElementById("circle_name").focus();
                        }
                    }
                });

                $("#circle_name option" ).each(function()
                {
                    //    alert(id);
                    if($(this).text() == $.trim(id)){
                        $(this).attr('selected', true);
                    }

                });
            }
            function setPremises(id){
                //  var company_id = $("#"+id).val();
                var company_id =document.getElementById("company_name").value;
                var queryString =  "task=getPremises&company_id=" + company_id;
                //  alert("queryString  :"+queryString);
                $.ajax({url: "meterCont1.do", async: false, data: queryString, success: function(response_data) {
                        //   alert("responce data"+response_data);
                        document.getElementById("premises_type").innerHTML = "<option value='0' selected>Select Premises..</option>";
                        var arr = response_data.split("&#;");
                        var i;
                        for(i = 0; i < arr.length - 1; i++) {
                            var opt = document.createElement("option");
                            opt.text = $.trim(arr[i].split(',')[0]);
                            opt.value = $.trim(arr[i].split(',')[1]);
                            document.getElementById("premises_type").options.add(opt);
                        }
                        if(i == 1) {
                            //document.getElementById("premises_type").value = arr[0];
                            // fillOrgDetails();
                        } else {
                            // document.getElementById("premises_type").focus();
                        }
                    }
                });
                $("#premises_type option" ).each(function()
                {
                    //    alert(id);
                    if($(this).text() == $.trim(id)){
                        $(this).attr('selected', true);
                    }

                });
            }
            function setOrg_Name(){
                var premises_type =document.getElementById("premises_type").value;
                //var chk_org =  $("option:selected", $("#premises_type")).text();
                var meter_name=  $.trim(document.getElementById("meter_name").value);
                var office_type=  $("option:selected", $("#office_type")).text();

                var organisation_name = $.trim(document.getElementById("organisation").value);
                if(organisation_name.length == 0) {
                    alert("Provide value for Organisation Name.");
                    document.getElementById("organisation").focus();
                    return;
                }

                //alert("prsass"+chk_org);
                //if(chk_org !='Street Light'){
                if(premises_type !='Y'){
                    var queryString =  "task=toOfficePlan&organisation_name=" + organisation_name+"&office_type="+office_type;
                    //  alert("queryString  :"+queryString);
                    $.ajax({url: "meterCont1.do", async: false, data: queryString, success: function(response_data) {
                            //   alert("responce data"+response_data);
                            document.getElementById("org_office").innerHTML = "<option selected>Select</option>";
                            var arr = response_data.split("&#;");
                            var i;
                            for(i = 0; i < arr.length - 1; i++) {
                                var opt = document.createElement("option");
                                opt.text = $.trim(arr[i]);
                                opt.value = $.trim(arr[i]);
                                document.getElementById("org_office").options.add(opt);
                            }
                            if(i == 1) {
                                document.getElementById("org_office").value = arr[0];
                                // fillOrgDetails();
                            } else {
                                document.getElementById("org_office").focus();
                            }
                        }
                    });
                }
            }
            function makeEditable(id) {
                document.getElementById("premises_type_hidden").value=$("option:selected", $("#premises_type")).text();
                document.getElementById("save_As").disabled = false;
                document.getElementById("update").disabled = false;
                document.getElementById("delete").disabled = false;
                document.getElementById("save").disabled = true;
                if(id == 'new') {
                    //document.getElementById("message").innerHTML = "";      // Remove message
                    var  message="";
                    $("#message").html(message);     // Remove message
                    document.getElementById("update").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("save").disabled = false;
                    document.getElementById("property_revise").disabled=true;
                    document.getElementById("revise").disabled=true;
                    document.getElementById("reason").disabled=true;
                    //   $("#displayContent").css("display", 'none');
                    document.getElementById("displayContent").style.display="none";
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 33);
                    document.getElementById("street_light").style.display="none";
                    document.getElementById("building").style.display="table-row";

                }
                document.getElementById("property_revise").disabled=false;
                document.getElementById("revise").disabled=false;
                document.getElementById("reason").disabled=false;
                // document.getElementById("cal").innerHTML = "<input type='text' id='t2c5' name='date'><a href='#' onclick='setYears(1947,2011);showCalender('form2','t2c5')'><img src='calender.png'></a>";

                /* if(id == 'edit'){
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("property_revise").disabled=false;
                    document.getElementById("revise").disabled=false;
                    document.getElementById("reason").disabled=false;
                } */
                //   document.getElementById("save").disabled = false;
                document.getElementById("tariff_code").disabled = false;
                document.getElementById("meter_name").disabled= false;
                document.getElementById("security_deposit").disabled= false;
                document.getElementById("sd_receipt_no").disabled= false;
                document.getElementById("initial_reading").disabled= false;
                document.getElementById("sanctioned_load_kw").disabled= false;
                document.getElementById("accessed_load_kw").disabled= false;
                document.getElementById("city").disabled= false;
                document.getElementById("mete_ser_num").disabled= false;
                document.getElementById("poll_no").disabled= false;
                document.getElementById("yes").disabled= false;
                document.getElementById("no").disabled= false;
                document.getElementById("organisation").disabled= false;
                document.getElementById("phase").disabled= false;
                document.getElementById("premises_type").disabled= false;
                document.getElementById("org_office").disabled= false;
                document.getElementById("effective_date").disabled= false;
                document.getElementById("description").disabled = false;
                document.getElementById("file_no").disabled = false;
                document.getElementById("latitude").disabled = false;
                document.getElementById("longitude").disabled = false;
                document.getElementById("ward_no").disabled = false;
                document.getElementById("msn_part1").focus();
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }

            function fillColumns(id, row_num) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 33;
                var columnId = id;
                columnId = columnId.substring(3, id.length);
                var lowerLimit, higherLimit;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                var t2id = "t2c";       // particular column id of table 2 e.g. t2c3.
                try{
                    document.getElementById("meter_name_auto").value   =document.getElementById("meter_name_auto"+row_num).value;
                    document.getElementById("latitude").value   =document.getElementById("latitude"+row_num).value;
                    document.getElementById("longitude").value   =document.getElementById("longitude"+row_num).value;
                    document.getElementById("ward_no").value   =document.getElementById("ward_no"+row_num).value;
                    document.getElementById("meter_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                    document.getElementById("meter_name").value=document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                    document.getElementById("mete_ser_num").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                    document.getElementById("poll_no").value=document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                    document.getElementById("security_deposit").value=document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
                    document.getElementById("sd_receipt_no").value=document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
                    document.getElementById("date").value=document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
                    document.getElementById("initial_reading").value=document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
                    var sacntion_load= document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
                    var split_load =$.trim(sacntion_load).split("-");
                    document.getElementById("sanctioned_load_kw").value=$.trim(split_load[0]);

                    //  document.getElementById("load_unit").value=$.trim(split_load[1]);
                    var  load_unit =$.trim(split_load[1]);
                    $("#load_unit option" ).each(function()
                    {
                        // alert(estimate_no_selected);
                        if($(this).text() == $.trim(load_unit)){
                            $(this).attr('selected', true);
                        }

                    });
                    setAccessedLoad();
                    document.getElementById("accessed_load_kw").value=document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
                    document.getElementById("city").value=document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
                    document.getElementById("description").value=document.getElementById(t1id + (lowerLimit + 30)).innerHTML;
                    document.getElementById("file_no").value=document.getElementById(t1id + (lowerLimit + 31)).innerHTML;
                    //       var tariff_code =document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
                    $("#msn_part1").val($("#msn1"+row_num).val());
                    $("#msn_part2").val($("#msn2"+row_num).val());
                    $("#msn_part3").val($("#msn3"+row_num).val());
                    $("#msn_part4").val($("#msn4"+row_num).val());
                    /*  $("#tariff_code option").each(function()
                    {
                        if($(this).text() == tariff_code){
                            $(this).attr('selected', true);
                        }
                    });  */
                    document.getElementById("revision").value=document.getElementById(t1id + (lowerLimit + 32)).innerHTML;
                    var chkvalue= document.getElementById(t1id + (lowerLimit + 14)).innerHTML;
                    var phase = document.getElementById(t1id + (lowerLimit + 22)).innerHTML;
                    if(phase != ""){
                        $("#phase option").each(function()
                        {
                            if($(this).text() == phase){
                                $(this).attr('selected', true);
                            }
                        });
                    }else{
                        $("#phase option[value='Select Phase']").attr('selected', 'selected');
                    }

                    document.getElementById("update_value").value="true";
                    if(chkvalue=='yes'){
                        document.getElementById("yes").checked =true;
                    }else{
                        document.getElementById("no").checked =true;
                    }
                    //                    alert(document.getElementById(t1id + (lowerLimit + 21)).innerHTML);
                    document.getElementById("effective_date").value = document.getElementById(t1id + (lowerLimit + 23)).innerHTML;
                    document.getElementById("organisation").value=document.getElementById(t1id + (lowerLimit + 15)).innerHTML;

                    var premises_individual_detail   =document.getElementById("premises_individual_detail"+row_num).value;

                    var company_name = document.getElementById(t1id + (lowerLimit + 24)).innerHTML;
                    $("#company_name option" ).each(function()
                    {

                        if($(this).text() == $.trim(company_name)){
                            $(this).attr('selected', true);
                        }

                    });
                    var circle_name = document.getElementById(t1id + (lowerLimit +25)).innerHTML;
                    //     alert(circle_name);
                    setCircle(circle_name);
                    var premises_type   =document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
                    setPremises(premises_type);
                    var tariff_code =document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
                    getTariffCode(tariff_code);
                    //   alert("premis type"+premises_type);
                    /*     $("#premises_type option").each(function()
                    {
                        if($(this).text() == premises_type){
                            $(this).attr('selected', true);
                        }
                    }); */

                    document.getElementById("premises_type_hidden").value=$("option:selected", $("#premises_type")).text();
                    if(premises_individual_detail=='Y'){
                        document.getElementById("street_light").style.display="table-row";
                        document.getElementById("building").style.display="none";
                        document.getElementById("building1").style.display="none";
                        document.getElementById("switching_point").value=document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
                    }
                    //if(premises_type=='Building')
                    else{
                        document.getElementById("building").style.display="table-row";
                        document.getElementById("building1").style.display="table-row";
                        document.getElementById("street_light").style.display="none";
                        var office_type=  document.getElementById(t1id + (lowerLimit + 20)).innerHTML;
                        //                        alert(office_type);
                        $("#office_type option").each(function()
                        {
                            if($(this).val() == office_type){
                                $(this).attr('selected', true);
                            }
                        });
                        setOrg_Name();
                        var org_office =document.getElementById(t1id + (lowerLimit + 18)).innerHTML;
                        $("#org_office option").each(function()
                        {
                            if($(this).val() == $.trim(org_office)){
                                $(this).attr('selected', true);
                            }
                        });
                        document.getElementById("feeder_name").value=document.getElementById(t1id + (lowerLimit + 19)).innerHTML;

                    }



                    for(var i = 0; i < noOfColumns; i++) {
                        document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                    }
                    makeEditable('');
                    /* document.getElementById("update").disabled = false;
                    if(!document.getElementById("save").disabled)
                    {// if save button is already enabled, then make edit, and delete button enabled too.
                        document.getElementById("save_As").disabled = true;
                        document.getElementById("delete").disabled = false;
                    }  */
                    //document.getElementById("message").innerHTML = "";
                    var  message="";
                    $("#message").html(message);     // Remove message
                    //  alert("phuch gye");
                    document.getElementById("property_revise").disabled=false;
                    // $("#property_revise").removeAttr("disabled");
                    //  alert("phuch gye 222");
                }catch(e){
                    alert(e);
                }

            }

            function setStatus(id) {
                if(id == 'save'){
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                    document.getElementById("update_value").value = "false";
                }else if(id=='revise' ){
                    document.getElementById("clickedButton").value = "Revise";
                    document.getElementById("update_value").value = "false";
                }
                else if(id=='update' ){
                    document.getElementById("clickedButton").value = "Update";
                    document.getElementById("update_value").value = "true";
                }
                else document.getElementById("clickedButton").value = "Delete";
            }


            function setRevisedStatus(id){

                if(id=='property_new'){
                    document.getElementById("displayContent").style.display="none";
                    document.getElementById("mete_ser_num").readOnly=false;
                }
                if(document.getElementById(id).checked){
                    if(id=='property_revise'){
                        document.getElementById("displayContent").style.display="table-cell";
                        document.getElementById("mete_ser_num").readOnly=true;
                    }
                }else{
                    document.getElementById("displayContent").style.display="none";
                    document.getElementById("mete_ser_num").readOnly=false;
                    // document.getElementById("message").innerHTML="";
                    var  message="";
                    $("#message").html(message);     // Remove message
                }
            }

            function myLeftTrim(str) {
                var beginIndex = 0;
                for(var i = 0; i < str.length; i++) {
                    if(str.charAt(i) == ' ')
                        beginIndex++;
                    else break;
                }
                return str.substring(beginIndex, str.length);
            }

            function verify() {
                var result;
                document.getElementById("premises_type_hidden").value=$("option:selected", $("#premises_type")).text();
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New'|| document.getElementById("clickedButton").value == 'Revise'||document.getElementById("clickedButton").value == 'Update') {
                    var meter_name = document.getElementById("meter_name").value;
                    if(myLeftTrim(meter_name).length == 0) {
                        //    document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Meter Name is required...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Meter Name is required...</b></td>");
                        document.getElementById("meter_name").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("latitude").value).length == 0) {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Meter Service Number is Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Latitude is Mandatory...</b></td>");
                        document.getElementById("latitude").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("longitude").value).length == 0) {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Meter Service Number is Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Longitude is Mandatory...</b></td>");
                        document.getElementById("longitude").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("ward_no").value).length == 0) {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Meter Service Number is Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Ward No is Mandatory...</b></td>");
                        document.getElementById("ward_no").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("mete_ser_num").value).length == 0) {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Meter Service Number is Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Meter Service Number is Mandatory...</b></td>");
                        document.getElementById("mete_ser_num").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("poll_no").value).length == 0) {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Pole No. is Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Pole No. is Mandatory...</b></td>");
                        document.getElementById("poll_no").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("security_deposit").value).length == 0) {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Security Deposit is Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Security Deposit is Mandatory...</b></td>");
                        document.getElementById("security_deposit").focus();
                        return false;
                    }
                    else{
                        var security_deposit=    document.getElementById("security_deposit").value;
                        result =  isNumeric(security_deposit);
                        if(!result){
                            //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Security Deposit Should be Numeric</b></td>";
                            $("#message").html("<td colspan='6' bgcolor='coral'><b>Security Deposit Should be Numeric</b></td>");
                            document.getElementById("security_deposit").focus();
                            return false;
                        }
                    }
                    if(myLeftTrim(document.getElementById("sd_receipt_no").value).length == 0) {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Security Deposit Receipt Number is required...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Sd Receipt No. is required...</b></td>");
                        document.getElementById("sd_receipt_no").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("date").value).length == 0) {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Erected Date is required...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Erected Date is required...</b></td>");
                        document.getElementById("date").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("initial_reading").value).length == 0) {
                        var ini_reading =  document.getElementById("initial_reading").value;
                        $("#message").html( "<td colspan='6' bgcolor='coral'><b>Initial Reading is Mandatory...</b></td>");
                        document.getElementById("initial_reading").focus();
                        //  document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Initial Reading is Mandatory...</b></td>";
                        return false;
                    }
                    else{
                        var initial_reading= document.getElementById("initial_reading").value;
                        result =  isNumeric(initial_reading);
                        if(!result){
                            document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>initial_reading Should be Numeric</b></td>";
                            //   $("#message").html("<td colspan='6' bgcolor='coral'><b>initial_reading Should be Numeric</b></td>");
                            document.getElementById("initial_reading").focus();
                            return false;
                        }
                    }
                    if(myLeftTrim(document.getElementById("sanctioned_load_kw").value).length == 0) {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Sanctioned load of Meter is required...</b></td>";
                        $("#message").html( "<td colspan='6' bgcolor='coral'><b>Sanctioned load of Meter is required...</b></td>");
                        document.getElementById("sanctioned_load_kw").focus();
                        return false;
                    }
                    else{
                        var sanctioned_load_kw=    document.getElementById("sanctioned_load_kw").value;
                        result =  isNumeric(sanctioned_load_kw);
                        if(!result){
                            // document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>sanctioned_load_kw Should be Numeric</b></td>";
                            $("#message").html( "<td colspan='6' bgcolor='coral'><b>sanctioned_load_kw Should be Numeric</b></td>");
                            document.getElementById("sanctioned_load_kw").focus();
                            return false;
                        }

                    }
                    if(myLeftTrim(document.getElementById("city").value).length == 0) {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>City is Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>City is Mandatory...</b></td>");
                        document.getElementById("city").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("organisation").value).length == 0) {
                        //  document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Meter Owner Orgaisation Name Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Meter Owner Orgaisation Name Mandatory...</b></td>");
                        document.getElementById("organisation").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("switching_point").value).length == 0) {
                        if(document.getElementById("premises_type").value=='Y'){
                            //  document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Switching Point...</b></td>";
                            $("#message").html("<td colspan='6' bgcolor='coral'><b>Switching Point...</b></td>");
                            document.getElementById("switching_point").focus();
                            return false;
                        }

                    }
                    if((myLeftTrim(document.getElementById("org_office").value).length == 0||document.getElementById("org_office").value=='Select') && (document.getElementById("org_office_code").value.length == 0)) {
                        if(document.getElementById("premises_type").value !='Y'){
                            //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Building Name is Mandatory...</b></td>";
                            $("#message").html("<td colspan='6' bgcolor='coral'><b>Building Name or Org Office Code is Mandatory...</b></td>");
                            document.getElementById("org_office").focus();
                            return false; // code to stop from submitting the form2.
                        }
                    }
                    if(myLeftTrim(document.getElementById("feeder_name").value).length == 0) {
                        if(document.getElementById("premises_type").value !='Y'){
                            //  document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Feeder Name is Manadotory...</b></td>";
                            $("#message").html("<td colspan='6' bgcolor='coral'><b>Feeder Name is Manadotory...</b></td>");
                            document.getElementById("feeder_name").focus();
                            return false;
                        }
                    }
                    if(myLeftTrim(document.getElementById("tariff_code").value) == 0) {
                        //  document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Meter Owner Orgaisation Name Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Select Tariff Code...</b></td>");
                        document.getElementById("tariff_code").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("effective_date").value).length == 0) {
                        //  document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Meter Owner Orgaisation Name Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Effective Date is Mandatory...</b></td>");
                        document.getElementById("effective_date").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("phase").value) == "Select Phase") {
                        //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Meter Service Number is Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Select Phase...</b></td>");
                        document.getElementById("phase").focus();
                        return false;
                    }
                    //                    alert(document.getElementById("file_no").value);
                    if(myLeftTrim(document.getElementById("file_no").value).length == 0) {
                        //  document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Meter Owner Orgaisation Name Mandatory...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>File No. is Mandatory...</b></td>");
                        document.getElementById("file_no").focus();
                        return false;
                    }
                    if(result == false);
                    else result = true;
                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                    else  if(document.getElementById("clickedButton").value == 'Update'){
                        result = confirm("Are you sure you want to  update this record?")
                        return result;
                    }
                    if(document.getElementById("clickedButton").value == 'Revise'){
                        if(myLeftTrim(document.getElementById("reason").value)==0) {
                            //   document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Reason  for Revise is Manadotory</b></td>";

                            $("#message").html("<td colspan='6' bgcolor='coral'><b>Reason  for Revise is Manadotory</b></td>");
                            document.getElementById("reason").focus();
                            return false;
                        }
                        result = confirm("Are you sure you want to Revise this Connection")
                        return result;
                    }
                } else result = confirm("Are you sure you want to delete this record?")
                return result;
            }
            function getTariffCode(id){
                var premises_type = $("option:selected", $("#premises_type")).text();
                var company_id = $("option:selected", $("#company_name")).val();
                //  var company_id =document.getElementById("company_name").value;
                // var premises_type = $("option:selected", $("#premises_type")).text();

                var queryString =  "task=getTariffCode&company_id=" + company_id+"&premises_type="+premises_type;
                //  alert("queryString  :"+queryString);
                $.ajax({url: "meterCont1.do", async: false, data: queryString, success: function(response_data) {
                        //   alert("responce data"+response_data);
                        document.getElementById("tariff_code").innerHTML = "<option value='0' selected>Select Tariff..</option>";
                        var arr = response_data.split("&#;");
                        var i;
                        for(i = 0; i < arr.length - 1; i++) {
                            var opt = document.createElement("option");
                            opt.text = $.trim(arr[i].split(',')[0]);
                            opt.value = $.trim(arr[i].split(',')[1]);
                            document.getElementById("tariff_code").options.add(opt);
                        }
                        if(i == 1) {
                            // document.getElementById("tariff_code").value = arr[0];
                            // fillOrgDetails();
                        } else {
                            // document.getElementById("premises_type").focus();
                        }
                    }
                });

                $("#tariff_code option" ).each(function()
                {
                    //    alert(id);
                    if($(this).text() == $.trim(id)){
                        $(this).attr('selected', true);
                    }

                });

            }

            function getchangeValue(){
                var premises_type =document.getElementById("premises_type").value;
                //alert(premises_type);
                if(premises_type=='Y'){
                    document.getElementById("street_light").style.display="table-row";
                    document.getElementById("building1").style.display="none";
                    document.getElementById("building").style.display="none";
                }
                else{
                    document.getElementById("building").style.display="table-row";
                    document.getElementById("building1").style.display="table-row";
                    document.getElementById("street_light").style.display="none";
                    setOrg_Name();
                }
                document.getElementById("premises_type_hidden").value=$("option:selected", $("#premises_type")).text();

            }

            function isNumeric(strString) {
                var strValidChars = "0123456789.";
                var strChar;
                var blnResult = true;
                var i;
                for (i = 0; i < strString.length && blnResult == true; i++)
                {
                    strChar = strString.charAt(i);
                    if (strValidChars.indexOf(strChar) == -1)
                    {
                        blnResult = false;
                    }
                }
                return blnResult;
            }
            function viewmeterExcellist(){
                var organisation= document.getElementById("organ").value;
                if(myLeftTrim(organisation).length==0){
                    alert("First select Organisation Name");
                    return;
                }
                var premises =   $("#premises_type_search").val();
                var company =  $("option:selected", $("#search_company_name")).text();
                var circle =   $("#search_circle_name").val();
                var division =   $("#division_filter").val();
                //                var con_type= document.getElementById("con_type").value;
                //                // alert(conn_value);
                //                  var conn_value= document.getElementById("conn").value;
                //                var status = document.getElementById("act_meter").value;
                //                var zone_filter= document.getElementById("zone_filter").value;
                //                var date_filter = document.getElementById("date_filter").value;
                //                var mete_ser_num_filter= document.getElementById("mete_ser_num_filter").value;
                //                var feeder_filter = document.getElementById("feeder_filter").value;
                //                var file_no = document.getElementById("file_no1").value;
                var queryString = "requestprinrt=PRINTExcel&organisation="+organisation+"&premises="+premises+"&division="+division
                    +"&company="+company +"&circle="+circle;
                var url = "meterCont1.do?" + queryString;
                popupwin = openPopUp(url, "Meter List", 600, 900);
            }
            function viewmeterlist(){
                var organisation= document.getElementById("organ").value;
                if(myLeftTrim(organisation).length==0){
                    alert("First select Organisation Name");
                    return;
                }

                //   var comp_id= document.getElementById("search_company_name").value;
                //   var circle_id = document.getElementById("search_circle_name").value;

                var comp_id= $("option:selected", $("#search_company_name")).text();
                var circle_id =document.getElementById("search_circle_name").value;

                var division_filter = document.getElementById("division_filter").value;
                var conn_value= document.getElementById("conn").value;
                var conn = $("option:selected", $("#conn")).text();
                var con_type= document.getElementById("con_type").value;
                //alert(conn_value);
                var status = document.getElementById("act_meter").value;
                var zone_filter= document.getElementById("zone_filter").value;
                var date_filter = document.getElementById("date_filter").value;
                var mete_ser_num_filter= document.getElementById("mete_ser_num_filter").value;
                var feeder_filter = document.getElementById("feeder_filter").value;
                var file_no = document.getElementById("file_no1").value;
                var queryString = "requestprinrt=PRINT&organisation="+organisation+"&conn="+conn+"&active="+status+"&con_type="+con_type +"&zone_filter="
                    +zone_filter+"&date_filter=" +date_filter+"&mete_ser_num_filter="+mete_ser_num_filter+"&feeder_filter="+feeder_filter +"&division_filter="+division_filter
                    +"&file_no="+file_no+"&conn_value="+conn_value+"&comp_id="+comp_id +"&circle_id="+circle_id;
                var url = "meterCont1.do?" + queryString;
                popupwin = openPopUp(url, "Meter List", 600, 900);
            }

            function viewIncorrectEntrylist(){
                var organisation= document.getElementById("organ").value;
                if(myLeftTrim(organisation).length==0){
                    alert("First select Organisation Name");
                    return;
                }


                var comp_id= $("option:selected", $("#search_company_name")).text();
                var circle_id =document.getElementById("search_circle_name").value;

                var division_filter = document.getElementById("division_filter").value;
                //  var conn= document.getElementById("conn").value;
                var conn = $("option:selected", $("#conn")).text();
                var con_type= document.getElementById("con_type").value;
                var status = document.getElementById("act_meter").value;
                var zone_filter= document.getElementById("zone_filter").value;
                var date_filter = document.getElementById("date_filter").value;
                var mete_ser_num_filter= document.getElementById("mete_ser_num_filter").value;
                var feeder_filter = document.getElementById("feeder_filter").value;
                var queryString = "requestprinrt=PRINT_Incorrect_Entry&organisation="+organisation+"&conn="+conn+"&active="+status+"&con_type="+con_type +"&zone_filter="
                    +zone_filter+"&date_filter=" +date_filter+"&mete_ser_num_filter="+mete_ser_num_filter+"&feeder_filter="+feeder_filter +"&division_filter="+division_filter+"&comp_id="+comp_id +"&circle_id="+circle_id;
                var url = "meterCont1.do?" + queryString;
                popupwin = openPopUp(url, "Meter List", 600, 900);
            }

            function viewDeactivelist() {
                var organisation= document.getElementById("organ").value;
                if(myLeftTrim(organisation).length==0){
                    alert("First select Organisation Name");
                    return;
                }

                var comp_id= $("option:selected", $("#search_company_name")).text();
                var circle_id =document.getElementById("search_circle_name").value;

                var division_filter = document.getElementById("division_filter").value;
                //  var conn= document.getElementById("conn").value;
                var conn = $("option:selected", $("#conn")).text();
                var con_type= document.getElementById("con_type").value;
                var status = document.getElementById("act_meter").value;
                var zone_filter= document.getElementById("zone_filter").value;
                var date_filter = document.getElementById("date_filter").value;
                var mete_ser_num_filter= document.getElementById("mete_ser_num_filter").value;
                var feeder_filter = document.getElementById("feeder_filter").value;
                var queryString = "requestprinrt=PRINT_Deactive_msn&organisation="+organisation+"&conn="+conn+"&active="+status+"&con_type="+con_type +"&zone_filter="
                    +zone_filter+"&date_filter=" +date_filter+"&mete_ser_num_filter="+mete_ser_num_filter+"&feeder_filter="+feeder_filter +"&division_filter="+division_filter+"&comp_id="+comp_id +"&circle_id="+circle_id;
                var url = "meterCont1.do?" + queryString;
                popupwin = openPopUp(url, "Meter List", 600, 900);
            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
            if (!document.all) {
                document.captureEvents (Event.CLICK);
            }
            document.onclick = function() {
                if (popupwin != null && !popupwin.closed) {
                    popupwin.focus();
                }
            }

            function  chkOganisation(){
                var organisation= document.getElementById("organ").value;
                if(myLeftTrim(organisation).length==0){
                    alert("First select Organisation Name");
                    return false;
                }else{
                    return true;
                }

            }

            function resetFields(){
                document.getElementById("search_company_name").value='0';
                document.getElementById("search_circle_name").value='';
                document.getElementById("file_no1").value='';
                document.getElementById("file_no1").value='';
                document.getElementById("division_filter").value='';
                document.getElementById("act_meter").value='Active';
                $("#meter_no").val("");
                //  document.getElementById("con_type").value = '';
                document.getElementById("conn").value = 'ALL';
                document.getElementById("premises_type_search").value = 'ALL';
                document.getElementById("mete_ser_num_filter").value = '';
                document.getElementById("feeder_filter").value = '';
                $("#zone_filter").val("");
                $("#file_no").val("");
                document.getElementById("date_filter").value = '';
            }
            function setConn(){
                var conn = $("option:selected", $("#conn")).text();
                // var a= $("option:selected", $("#conn")).val();
                //alert(a);
                if(conn == 'ALL'){
                    document.getElementById("con_type").disabled = true;
                    document.getElementById("con_type").style.backgroundColor = 'lightgrey';
                }else{
                    document.getElementById("con_type").disabled = false;
                    document.getElementById("con_type").style.backgroundColor = '';
                }
                document.getElementById("premises_type_search").value=conn;
            }

            function setAccessedLoad(){
                var sanctioned_load_kw = $("#sanctioned_load_kw").val();
                var load_unit = $("#load_unit").val();
                $("#load_unit_id").val(load_unit.split("#")[0]);
                var factor = load_unit.split("#")[1];
                $("#accessed_load_kw").val(sanctioned_load_kw * factor);
            }


            function setServiceNummber(){
                var msn1= $("#msn_part1").val();
                var msn2= $("#msn_part4").val();
                var meter_service_num= msn1.toString()+msn2.toString();
                $("#mete_ser_num").val(meter_service_num);
            }

            function emailServCon(){
                var premises =   $("#premises_type_search").val();
                var company =  $("option:selected", $("#search_company_name")).text();
                var circle =   $("#search_circle_name").val();
                var division =   $("#division_filter").val();
                var zone =   $("#zone_filter").val();

                $("#mail_company").val(company);
                $("#mail_circle").val(circle);
                $("#mail_division").val(division);
                $("#mail_premises").val(premises);
                $("#mail_zone").val(zone);
                $("#mailForm").attr("action", "meterCont1.do");
                $("#mailForm").submit();
            }

            function readEmail(){
                var company =  $("option:selected", $("#search_company_name")).val();
                $("#read_mail_company").val(company);
                $("#readMailForm").attr("action", "meterCont1.do");
                $("#readMailForm").submit();
            }


            function getSwitchingPointSubType(){
                var premises_type_search = document.getElementById("premises_type_search").value;
                //if(premises_type_search != "ALL"){
                //$("#switching_point_sub_type").val(switching_point_type);
                $.ajax({
                    url:"meterCont1.do",
                    data:"task=getSwitchingPointSubType&switching_point_type="+premises_type_search,
                    success: function(response_data) {
                        var html = "";
                        var select = "selected";
                        var data = response_data.split("_");
                        $("#switching_point_sub_type_data").html("");
                        html = '<select class="dropdown" id="switching_point_sub_type" name="switching_point_sub_type" ><option value="0">Switching Point Sub Type</option>';
                        for(var i=0;i<data.length;i++){
                            if(data[i] != ""){
                                var keyValue = data[i].split("#");
                                var key = keyValue[0];
                                var value = keyValue[1];
                                html=html + '<option value="'+ key +'" '+ select +'>'+ value+'</option>';
                                select = "";
                            }
                        }
                        html = html + '</select>';
                        $("#switching_point_sub_type_data").html(html);

                    }
                });
                //}else{
                //  $("#switching_point_sub_type").val("0");
                //}
            }



        </script>-->
    </head>
    <body>
        <table align="center"  class="main" cellpadding="0" cellspacing="0" border="0" >            <!--DWLayoutDefaultTable-->
            
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr>
                <td>
                    <div class="maindiv" id="body" >
                        <table>                            
                            <tr>
                                <td>
                                    <form name="form1" method="post" action="meterCont1.do" onsubmit="return chkOganisation()">
                                        <table align="center"  width="100%">                                            
                                            <tr>
                                                <td>
                                                    <div id="wrapper" align="center" style="margin-bottom: 0px;padding-bottom: 0px">
                                                        <div class="block1" style="width: 800px">
                                                            <div><h1> Service Connection Table</h1></div><br>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr align="left">
                                                    <th>
                                                        <span>Organization</span> <input class="input1" type="text" id="organ" name="organ" value="${organ}" size="15" style="width: 10%">
                                                        <span>Connection Type</span>
                                                        <select id="conn" name="premises_type" class="dropdown1" onchange="setConn(),getSwitchingPointSubType()" style="width: 13%">
                                                            <option <c:if test="${premises_type1=='ALL'}"> selected</c:if> value="ALL">ALL</option>
                                                            <%--<c:forEach var="premises" items="${premises_type}">
                                                                <option <c:if test="${premises_type1==premises.key}"> selected</c:if> value="${premises.value}">${premises.key}</option>
                                                            </c:forEach>--%>
                                                        </select>
                                                        <input type="hidden" name="premises_type_search" id="premises_type_search" value="${premises_type1}">
                                                        <span>S.P. Sub Type</span>
                                                        <span id="switching_point_sub_type_data">
                                                            <select class="dropdown" id="switching_point_sub_type" name="switching_point_sub_type">
                                                                <option value="0">Switching Point Sub Type</option>
                                                                <c:forEach items="${switching_pt_sub_type_map}" var="map">
                                                                    <option  <c:if test="${switching_point_sub_type1==map.key}">selected</c:if> value="${map.key}">${map.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </span>
                                                        <%--<<select id="conn" name="conn"  onclick="setConn();">
                                                            <option <c:if test="${con=='ALL'}"> selected</c:if>>ALL</option>
                                                            <option <c:if test="${con=='Switching Point'}">selected</c:if>>Switching Point</option>
                                                            <option <c:if test="${con=='Building'}">selected</c:if>>Building</option>
                                                        </select>--%>
                                                        <input class="input1" type="text" id="con_type" name="con_type" value="${con_type}" size="15" style="background-color: lightgrey;" disabled>
                                                        <span>Status</span> <select id="act_meter" name="act_meter" >
                                                            <option <c:if test="${active_meter=='Active'}"> selected</c:if>>Active</option>
                                                            <option <c:if test="${active_meter=='InActive'}"> selected</c:if>>InActive</option>
                                                            <option <c:if test="${active_meter=='All'}"> selected</c:if>>All</option>
                                                        </select>
                                                    </th>
                                                </tr>
                                                <tr align="left">
                                                    <th colspan="3">
                                                        <span>Company</span>   <select class="dropdown1" id="search_company_name" name="search_company_name" onchange="setSearchCircle(id)"style="width: 125px;" >
                                                            <c:forEach var="compny_name" items="${company_name}">
                                                                <option value="${compny_name.key}" ${compny_name.key ==search_company_id? 'selected' : ''}>${compny_name.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <%-- <span>Circle</span>  <select class="dropdown1" id="search_circle_name" name="search_circle_name" onchange="setSearchDivision(id)">
                                                             <option value="0">Select..</option>
                                                        <%--  <c:forEach var="circle_name" items="${search_circle_name}">
                                                             <option value="${circle_name.key}" ${circle_name.key ==s_circle_id? 'selected' : ''}>${circle_name.value}</option>
                                                         </c:forEach>   --%>
                                                        <%--  </select> --%>
                                                        <span>Circle</span><input class="input1" type="text" id="search_circle_name" name="search_circle_name" value="${s_circle_name}" style="width: 105px;"/>
                                                        <span>Division</span><input class="input1" type="text" id="division_filter" name="division_filter" value="${division_filter}" style="width: 125px;"/>
                                                        <span>Zone</span> <input class="input1" type="text" name="zone_filter" id="zone_filter" value="${zone_filter == '' ? '' : zone_filter}" style="width: 125px;" >
                                                        <span>Feeder</span> <input class="input1" type="text" name="feeder_filter" id="feeder_filter" value="${feeder_filter eq '' ? '' : feeder_filter}" >

                                                    </th>
                                                </tr>
                                                <tr align="left">
                                                    <th colspan="3">
                                                        <span>Meter Service No.</span>  <input class="input1" type="text" id="mete_ser_num_filter" name="mete_ser_num_filter"  value="${mete_ser_num_filter eq '' ? '' : mete_ser_num_filter}" >
                                                        <span>File No.</span> <input class="input1" type="text" name="file_no" id="file_no1" value="${file_no == '' ? '' : file_no}" style="width: 100px;">
                                                        <span>Date</span> <input class="input_cal" type="text" id="date_filter" name="date_filter" value="${date_filter}" >

                                                        <span>Meter No.</span> <input class="input1" type="text" name="meter_no" id="meter_no" value="${meter_no == '' ? '' : meter_no}"style="width: 70px;"  >


                                                        <%--<option ${premises_type == 'Street Light' ? 'selected' : ''}>Street Light</option>
                                                        <option ${premises_type == 'Building' ? 'selected' : ''}>Building</option>--%>
                                                        <input class="button" type="submit" id="search" name="search" value="Search">
                                                        <input class="button" type="submit" id="new" name="new" value="Show All" onclick="resetFields();">
                                                    </th>
                                                </tr>
                                            </table>
                                            <DIV class="content_div">
                                                <table  id="table1" align="center" class="content">
                                                    <tr>
                                                        <th class="heading" style="display: none"><!-- Location ID --></th>
                                                        <th class="heading">S.No.</th>
                                                        <th class="heading">Meter Service No.</th>
                                                        <th class="heading">IVRS No</th>
                                                        <th class="heading">Meter No.</th>
                                                        <th class="heading" style="display: none">Pole No.</th>
                                                        <th class="heading" style="display: none">Security Deposit</th>
                                                        <th class="heading" style="display: none">Sd Reciept No.</th>
                                                        <th class="heading">Date</th>
                                                        <th class="heading" style="display: none">Initial Reading</th>
                                                        <th class="heading" style="white-space: normal">Sanctioned load</th>
                                                        <th class="heading" style="white-space: normal">Accessed load kw</th>
                                                        <th class="heading" style="display: none">City</th>
                                                        <th class="headingWrap">Tariff Code</th>
                                                        <th class="heading">Active</th>
                                                        <th class="heading" style="display: none">Meter Owner</th>
                                                        <th class="heading" style="white-space: normal">Type of Connection</th>
                                                        <th class="heading">Switching point</th>
                                                        <th class="heading">Office Name</th>
                                                        <th class="heading" style="display: none">Feeder</th>
                                                        <th class="heading" style="display: none">office_type</th>
                                                        <th class="heading" >Reason</th>
                                                        <th class="heading" >Phase</th>
                                                        <th class="heading">Effective Date</th>

                                                        <th class="heading" >Company Name</th>
                                                        <th class="heading" >Circle</th>

                                                        <th class="heading" >Division</th>
                                                        <th class="heading" >Zone</th>
                                                        <th class="heading" >Feeder</th>
                                                        <th class="headingWrap" style="white-space: normal">Calculated Load</th>
                                                        <th class="heading">Remark</th>
                                                        <th class="heading">File No</th>
                                                        <th class="heading">Latitude</th>
                                                        <th class="heading">Longitude</th>
                                                        <th class="heading" style="display:none"></th>

                                                    </tr>
                                                    <c:forEach var="meters" items="${requestScope['meterList']}" varStatus="loopCounter">
                                                        <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                            <td id="t1c${IDGenerator.uniqueID}" style="display: none" onclick="fillColumns(id,${loopCounter.count})">${meters.meter_id}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})" align="center">
                                                                ${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                                <input type="hidden" name="msn1" id="msn1${loopCounter.count}" value="${meters.msn_part1}">
                                                                <input type="hidden" name="msn1" id="msn2${loopCounter.count}" value="${meters.msn_part2}">
                                                                <input type="hidden" name="msn1" id="msn3${loopCounter.count}" value="${meters.msn_part3}">
                                                                <input type="hidden" name="msn1" id="msn4${loopCounter.count}" value="${meters.msn_part4}">
                                                            </td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.meter_service_num}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.ivrs_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.meter_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id ,  ${loopCounter.count})" style="display: none">${meters.poll_num}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id ,  ${loopCounter.count})" style="display: none">${meters.security_deposit}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id, ${loopCounter.count})" style="display: none">${meters.sd_receipt_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.date}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})" style="display: none">${meters.initial_reading}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.sanctioned_load_kw}-${meters.load_unit}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.accessed_load_kw}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})" style="display: none">${meters.city_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.tariff_code}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.active}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})" style="display: none">${meters.organisation}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.type_of_premises}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.switching_point}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id, ${loopCounter.count})">${meters.org_office}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})" style="display: none">${meters.feeder_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})" style="display: none">${meters.office_type}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.reason}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.phase}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.effective_date}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.company_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.circle_name}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.division_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.zone}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.feeder}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.calculated_load}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.description}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.file_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.latitude}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})">${meters.longitude}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id , ${loopCounter.count})" style="display:none">${meters.revision}</td>
                                                        </tr>
                                                        <input type="hidden" name="premises_individual_detail" id="premises_individual_detail${loopCounter.count}" value="${meters.premises_individual_detail}">
                                                        <input type="hidden" name="meter_name_autoUpper" id="meter_name_auto${loopCounter.count}" value="${meters.meter_name_auto}">
                                                        <input type="hidden" name="latitude" id="latitude${loopCounter.count}" value="${meters.latitude}">
                                                        <input type="hidden" name="longitude" id="longitude${loopCounter.count}" value="${meters.longitude}">
                                                        <input type="hidden" name="ward_no" id="ward_no${loopCounter.count}" value="${meters.ward_no}">
                                                    </c:forEach>
                                                    <tr>
                                                        <td align='center' colspan="21">
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
                                                        </td>  </tr>
                                                    <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                    <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                    <input type="hidden"  name="location" value="${location}" >
                                                    <input type="hidden" name="organ" value="${organ}" >
                                                    <input type="hidden" name="zone_filter" value="${zone_filter}" >
                                                    <input type="hidden" name="date_filter" value="${date_filter}" >
                                                    <input type="hidden" name="mete_ser_num_filter" value="${mete_ser_num_filter}" >
                                                    <input type="hidden" name="feeder_filter" value="${feeder_filter}" >
                                                    <input type="hidden" name="con_type" value="${con_type}" >
                                                    <input type="hidden" name="active_meter" value="${active_meter}" >
                                                    <input type="hidden" name="meter_no" value="${meter_no}" >
                                                    <input type="hidden" id="division_filter" name="division_filter" value="${division_filter}">
                                                    <input type="hidden" name="file_no" value="${file_no}">
                                                </table>
                                            </DIV>
                                        </form>
                                    </td>
                                </tr>
                        </table>
                    </div>
                             
        </table>
    </body>
</html>

