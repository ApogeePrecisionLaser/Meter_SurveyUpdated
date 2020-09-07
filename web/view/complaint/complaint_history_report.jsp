<%-- 
    Document   : complaint
    Created on : Jul 26, 2013, 11:24:43 AM
    Author     : Shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--@taglib prefix="myfn" uri="http://myCustomTagFunctions" --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

            var popupwin;
            jQuery(function(){
                $("#site_name").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                        action: function() { return "siteList";},
                        add_sub_type2: function() { return $("#ooh_ast2").val();}
                    }

                });

                $("#posted_by").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                         action: function() { return "PostedBy";}

                    }
                });

                $("#assigned_for").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                         action: function() { return "AssingedFor";}

                    }
                });
                $("#s_posted_by").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                         action: function() { return "PostedBy";}

                    }
                });
                $("#s_assigned_for").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                         action: function() { return "AssingedFor";}

                    }
                });
               $("#s_complaint_no").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                         action: function() { return "ComplaintNo";}

                    }
                });
               $.ajax({url: "qtAdSubType1Cont.do", data: "advertising_type=OOH Display&q= ", success: function(response_data) {
                        var arr = response_data.split("&#;");
                        var i;
                        for(i = 0; i < arr.length - 1; i++) {
                            var opt        = document.createElement("option");
                            opt.text = arr[i];
                            opt.value = arr[i];
                            document.getElementById("ooh_ast1").options.add(opt);
                        }
                        if(i > 0) {
                            document.getElementById("ooh_ast1").value = "Hoarding";
                            ooh_fillAdSubType2();
                        }
                    }
                });
           

                $("#complaint_date").datepicker({

                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
                 


                $("#search_compalint_date_from").datepicker({

                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
                $("#search_compalint_date_to").datepicker({

                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });


                $("#site_name").result(function(event, data, formatted){
                    //   alert("gfhfh");
                    fillAddSubSite();

                });
      $("#assigned_for").result(function(event, data, formatted){
                    //   alert("gfhfh");
                    fillComplaintStatus();

                });   


            /* $("#new").result(function(event, data, formatted){
                    //   alert("gfhfh");
                    fillComplaintStatus();

                });  */

            });


            function ooh_fillAdSubType2() {
                var adSubType1 = document.getElementById("ooh_ast1").value;
                if(adSubType1 == 'Select') {
                    document.getElementById("ooh_ast1").focus();
                } else {
                    $.ajax({url: "qtAdSubType2Cont.do", async: false ,data: "advertising_type=OOH Display&adSubType1="
                            + adSubType1 + "&q= ", success: function(response_data) {
                            $("#ooh_ast2").html("");
                            var arr = response_data.split("&#;");
                            var i;
                            for(i = 0; i < arr.length - 1; i++) {
                                var opt        = document.createElement("option");
                                opt.text = arr[i];
                                opt.value = arr[i];
                                document.getElementById("ooh_ast2").options.add(opt);
                            }
                            //document.getElementById("ooh_ast2").disabled = false;
                            if(i == 1) {
                                document.getElementById("ooh_ast2").value = arr[0];
                            }
                            if(adSubType1 == 'Hoarding') {
                                //  document.getElementById("ooh_ast2").disabled = true;
                            } else if(adSubType1 == 'Kiosks') {
                                document.getElementById("ooh_ast2").value = "Street Light Pole Kiosk";
                            }
                            getComplainTypeList();
                        }
                    });
                }
              
            }

            function fillComplaintStatus(){
          
             try{           
                    var  assigned_for = document.getElementById("assigned_for").value;
                 // alert(aa);
                  var status= document.getElementById("status").value;                             
               $.ajax({url: "complaintregisterCont.do", async: false,data: "task=getComplaintStatus&assigned_for="
                            + assigned_for, success: function(response_data) {
                            $("#status").html("");
                            response_data= $.trim(response_data);
                            response_data=   $.trim(response_data.substr(1, response_data.length-2));
                     //     alert(response_data);
                           if(response_data != ''){
                            //  alert(response_data);
                              var arr = response_data.split(",");
                                var i;
                              var j=2;
                             var ss=5;
                           
                            for(i = 0; i < arr.length; i++) {
                                    var a1= arr[i].split("=");
                                   var opt = document.createElement("option");
                                // alert(arr);
                                 opt.text = a1[1];
                                    opt.value = a1[0];
                               // alert(opt.value);
                                 if(a1[0]==j &&($.trim(assigned_for).length != 0)){
                                   //alert(assigned_for);
                                   document.getElementById("status").options.add(opt);
                                    
                                 }
                                 
                          }
                            }
                       }
                            
                       
                    });
               
                } catch(e){
                    alert("error in getComplainTypeList :="+e)
                }
            }


            function fillNewComplaintStatus(){
          //   alert("bfdgfd");
             try{
                  var complaint_stsatus=document.getElementById("clickedButton").value;

                 // alert(aa);
                  var status= document.getElementById("status").value;
            //    alert(status);
             
               $.ajax({url: "complaintregisterCont.do", async: false,data: "task=getComplaintStatus&complaint_stsatus="
                            + complaint_stsatus, success: function(response_data) {
                            $("#status").html("");
                            response_data= $.trim(response_data);
                            response_data=   $.trim(response_data.substr(1, response_data.length-2));
                        //  alert(response_data);
                           if(response_data != ''){
                            //  alert(response_data);
                              var arr = response_data.split(",");
                                var i;
                              var j=2;
                             var ss=5;

                            for(i = 0; i < arr.length; i++) {
                                    var a1= arr[i].split("=");
                                   var opt = document.createElement("option");
                                // alert(arr);
                                 opt.text = a1[1];
                                    opt.value = a1[0];

                                 if(a1[0]==ss){
                                  // alert(ss);
                                   document.getElementById("status").options.add(opt);

                                 }
                                 //// else{
                             //  document.getElementById("status").options.add(opt);
                              //  }
                          }
                            }
                       }


                    });
              // }
                } catch(e){
                    alert("error in getComplainTypeList :="+e)
                }
            }
        /*
   function fillAddSubSite() {
   
        var  site_name = document.getElementById("site_name").value;
        
   $.ajax({url: "complaintregisterCont.do", async: false ,data: "task=getAddSubSite&ad_sub_site=OOH DisplayAdSubSite&site_name="  + site_name , success: function(response_data) {

                      $("#ad_sub_site").html("");
                 
               var  ad_sub_site= response_data;
       
          document.getElementById("ad_sub_site").value =ad_sub_site;
          }});                

}
             */
            function singleCheckUncheck(id){
                //   alert(document.getElementById('insertTable').rows.length);
                if ($('#check_print'+id).is(':checked')) {
                    $("#complaint_reg_no"+id).val("1");              
                }else{
                    $("#complaint_reg_no"+id).val("0");
                }
                if($('form input[type=checkbox]:checked').size() == 0){
                    $("#addAllRecords").attr("disabled", "disabled");
                }else{
                    $("#addAllRecords").removeAttr("disabled");
                }


            }

            function checkUncheck(id){
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;
                if(id == 'Check All'){
                    $('input[name=check_print]').attr("checked", true);
                    for(var i=1;i<rowCount;i++){
                        $("#complaint_reg_no"+i).val("1");
                    }
                    $('#checkUncheckAll').val('Uncheck All');
                    $("#addAllRecords").removeAttr("disabled");
                }else{
                    $('input[name=check_print]').attr("checked", false);
                    $('#checkUncheckAll').val('Check All');
                    $("#addAllRecords").attr("disabled", "disabled");
                    for(var i=1;i<rowCount;i++){
                        $("#complaint_reg_no"+i).val("2");
                    }
                }
            }
           



            function singleCheckAndUncheck(id){
                //   alert(document.getElementById('insertTable').rows.length);
               
                if ($('#checkPrint'+id).is(':checked')) {
                    $("#is_check"+id).val("1");
               
                }else{
                    $("#is_check"+id).val("0");
                }

            }


            function smsCheck(id){
                //  alert(id);
       
                if ($('#'+id).is(':checked')) {
                    // if($(this).is(':checked')){
                    //    alert(id);
                    $("#sms_check").val("1");

                }else{
                    $("#sms_check").val("0");
                }
            }

            function mailCheck(id){
     
                if ($('#'+id).is(':checked')) {
            
                    $("#mail_check").val("1");

                }else{
                   
                    $("#mail_check").val("0");
                }
                // alert("h");
            }


            function check(id){
           
                var table = document.getElementById('table1');
                var rowCount = table.rows.length;
                if(id == 'Check All'){
                    $('input[name=checkPrint]').attr("checked", true);
                    for(var i=1;i<rowCount;i++){
                        $("#is_check"+i).val("1");
                    }
                    $('#check_uncheck_all').val('Uncheck All');
                    $("#send").removeAttr("disabled");
             }else{
                    $('input[name=checkPrint]').attr("checked", false);                   
                    $('#check_uncheck_all').val('Check All');
                    $("#send").attr("disabled", "disabled");
                   for(var i=1;i<rowCount;i++){
                        $("#is_check"+i).val("0");
                    }
                }
               
            }  


function checkUncheckSmsAndMail(id){
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;
                if(id == 'Check All'){
                    $('input[name=check_print]').attr("checked", true);
                    for(var i=1;i<rowCount;i++){
                        $("#complaint_reg_no"+i).val("1");
                    }
                    $('#checkUncheckAll').val('Uncheck All');
                    $("#addAllRecords").removeAttr("disabled");
                }else{
                    $('input[name=check_print]').attr("checked", false);
                    $('#checkUncheckAll').val('Check All');
                    $("#addAllRecords").attr("disabled", "disabled");
                    for(var i=1;i<rowCount;i++){
                        $("#complaint_reg_no"+i).val("2");
                    }
                }
            }
            function verify() {
                try {
                    var result , site_name= "", complaint_date= "" ,posted_by= "", assigned_for= "", remark= "", complaint_type="";
                    if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                        site_name = $.trim(document.getElementById("site_name").value);
                        if($.trim(site_name).length == 0) {
                           
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Site Name is Required...</b></td>");
                            document.getElementById("site_name").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        complaint_date = document.getElementById("complaint_date").value;
                        if($.trim(complaint_date).length == 0) {
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Complaint Date is required...</b></td>");
                            document.getElementById("complaint_date").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        posted_by = document.getElementById("posted_by").value;
                        if($.trim(posted_by).length == 0) {
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Posted By is required...</b></td>");
                            document.getElementById("posted_by").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        assigned_for = document.getElementById("assigned_for").value;
                        //                        remark = document.getElementById("remark").value;
                        //                        if($.trim(remark).length == 0) {
                        //                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Remark is required...</b></td>");
                        //                            document.getElementById("remark").focus();
                        //                            return false; // code to stop from submitting the form2.
                        //                        }
                        complaint_type = document.getElementById("complaint_type").value;
                        if($.trim(complaint_type).length == 0) {
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>complaint Type is required...</b></td>");
                            document.getElementById("complaint_type").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        if(result == false);    
                        else result = true;
                        if(document.getElementById("clickedButton").value == 'Save AS New'){
                            result = confirm("Are you sure you want to save it as New record?")
                            return result;
                        }
                        
                    }else if(document.getElementById("clickedButton").value == 'Revise'){
                        result = confirm("Are you sure you want to Revise this complaint")
                        return result;
                    }
                    else result = confirm("Are you sure you want to delete this record?")
                    if(document.getElementById("complaint_reg_no").value == 0){
                        addRow( site_name,$("#complaint_type").val() ,$("#status").val() ,complaint_date, posted_by, assigned_for,$("#ad_sub_site_name").val());
                        $("#message").html("");
                        //document.getElementById('form3').reset();
                        return false;
                    }
                }catch(e){
                    alert("is there any error"+e);
                }
                return result;
            }

            function addRow( site_name, complaint_type, status, complaint_date, posted_by, assigned_for,ad_sub_site_name) {
                //  alert(ad_sub_site_name);
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;                // alert(rowCount);
                var row = table.insertRow(rowCount);
                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.type = "hidden";
                element1.name = "complaint_reg_no";
                element1.id = "complaint_reg_no"+rowCount;
                element1.size = 1;
                element1.value = 1;
                element1.readOnly = false;
                cell1.appendChild(element1);
                var element1 = document.createElement("input");
                element1.type = "checkbox";
                element1.name = "check_print";
                element1.id = "check_print"+rowCount;                //element1.size = 1;
                element1.value = "YesModify";
                element1.checked = true;
                element1.setAttribute('onclick', 'singleCheckUncheck('+rowCount+')');
                cell1.appendChild(element1);

                var cell2 = row.insertCell(1);
                var element2 = document.createElement("input");
                // var site_name = document.getElementById("site_name").value;
                element2.type = "text";
                element2.name = "site_name";
                element2.id = "site_name"+rowCount;
               // element2.size = 30;

                // element2.value = site_name;
                //alert(site_name);
                if(document.getElementById("clickedButton").value == 'Save')
                {                                  
                    element2.value = site_name;
                    cell2.appendChild(element2);
                }
                else
                {
                    $.ajax({url: "complaintregisterCont.do", async: false ,data: "task=getSiteName&complaint_site_name=OOH DisplayComplaint_Site_name&site_name="  + site_name , success: function(response_data) {
                                      
                            $("#site_name").html("");
                           
                            element2.value = response_data;;
                            cell2.appendChild(element2);
                        
                        }});
                }
                // cell2.appendChild(element2);

                var cell3 = row.insertCell(2);
                var element3 = document.createElement("input");
                element3.type = "hidden";
                element3.name = "complaint_type";
                element3.id = "complaint_type"+rowCount;
               
              
                element3.value = complaint_type;
                //alert(complaint_type);
                var element3_1 = document.createElement("input");
                element3_1.type = "text";
                element3_1.name = "complaint_type_name";
                element3_1.id = "complaint_type_name"+rowCount;
                element3_1.size = 11;
                element3_1.readOnly = true;
                element3_1.value = $("#complaint_type option:selected").text();
                // alert(complaint_type);
                cell3.appendChild(element3);
                cell3.appendChild(element3_1);
               
               
                var cell4 = row.insertCell(3);
                var element4 = document.createElement("input");
                element4.type = "text";
                element4.name = "status";
                element4.id = "status"+rowCount;
                element4.value = status;
                var element4_1 = document.createElement("input");
                element4_1.type = "text";
                element4_1.name = "status_name";
                element4_1.id = "status_name"+rowCount;
                element4_1.size = 10;
                element4_1.readOnly = true;
                element4_1.value = $("#status option:selected").text();
                cell4.appendChild(element4);
                cell4.appendChild(element4_1);

                var cell5 = row.insertCell(4);
                var element5 = document.createElement("input");
                element5.type = "text";
                element5.name = "complaint_date";
                element5.id = "complaint_date"+rowCount;
                element5.size = 8;
                element5.value = complaint_date;
                cell5.appendChild(element5);

                var cell6 = row.insertCell(5);
                var element6 = document.createElement("input");
                element6.type = "text";
                element6.name = "posted_by";
                element6.id = "posted_by"+rowCount;
                element6.size = 12;
                element6.value = posted_by;
                cell6.appendChild(element6);

                var cell7 = row.insertCell(6);
                var element7 = document.createElement("input");
                element7.type = "text";
                element7.name = "assigned_for";
                element7.id = "assigned_for"+rowCount;
                element7.size = 10;
                element7.value = assigned_for;
                cell7.appendChild(element7);

                var cell8 = row.insertCell(7);
                var element8 = document.createElement("input");
                element8.type = "text";
                element8.name = "remark";
                element8.id = "remark"+rowCount;
                element8.size = 8;
               
                element8.value = $("#remark").val();
                cell8.appendChild(element8);
                var cell9 = row.insertCell(8);
                var element9 = document.createElement("select");
                element9.option = "text";
                element9.name = "ad_sub_site_name";
                element9.id = "ad_sub_site_name"+rowCount;
                if(document.getElementById("clickedButton").value == 'Save')
                {              
                    var element9_1 = document.createElement("option");
                    element9_1.value =ad_sub_site_name;
                 //   alert(element9_1.value);
                    element9_1.text=$("#ad_sub_site_name option:selected").text();
                   
                    //alert(ad_sub_site_name);
                    element9.options.add(element9_1);
                    

                    cell9.appendChild(element9);
                    // cell9.appendChild(element9_1);
                }
                else
                {
                  
                     var site_name=element2.value;
                   //  alert(site_name);
                    $.ajax({url: "complaintregisterCont.do", async: false ,data: "task=getAddSubSite&ad_sub_site_name=OOH DisplayAdSubSite&site_name="  + site_name , success: function(response_data) {
                            //alert(site_name);
                            try{
                        
                                $("#ad_sub_site_name").html("");
                                response_data= $.trim(response_data);
                                response_data=   $.trim(response_data.substr(0, response_data.length-1));
                                if(response_data != '')
                                {

                                    //   var  ad_sub_site_name= response_data;
                                    //var arr = response_data.split("=" );
                                    //alert(arr);
                                  var arr = response_data.split("#" );

                                  var i;
                                    for(i = 0; i < arr.length; i++)
                                    {
                                         var a1 = arr[i].split("=" );
                                       //  var element9 = document.createElement("option");
                                        var  element9_1 = document.createElement("option");
                                        element9_1.text = a1[1];
                                        element9_1.value = a1[0];
                                        //               alert(element9.text);
                                        // alert(arr[0]);
                                        element9.options.add(element9_1);
                            
                                        // cell9.appendChild(element9);
                                        // alert(element9.text);
                                        cell9.appendChild(element9);
                                    }
                                }
                            } catch(e){
                                //   alert(e);
                            }
                        }});
                   
                }  //end of else condition
                var height = (rowCount * 40)+ 60;
                document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                document.getElementById("autoCreateTableDiv").style.height = height+'px';
                if(rowCount == 1){
                    $('#checkUncheckAll').attr('hidden', true);
                }else{
                    $('#checkUncheckAll').attr('hidden', false);
                }
            }
            function deleteRowWithoutResetForm() {
                try {
                    var table = document.getElementById('insertTable');
                    var rowCount = table.rows.length;
                    for(var i=1; i<rowCount; i++) {
                        table.deleteRow(1);
                    }
                    document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                    document.getElementById("autoCreateTableDiv").style.height = '0px';
                }catch(e) {
                    alert(e);
                }
            }
            function deleteRow() {
                try {
                    var table = document.getElementById('insertTable');
                    var rowCount = table.rows.length;
                    for(var i=1; i<rowCount; i++) {
                        table.deleteRow(1);
                    }
                    document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                    document.getElementById("autoCreateTableDiv").style.height = '0px';
                    document.getElementById('form3').reset();
                }catch(e) {
                    alert(e);
                }
            }


            function makeEditable(id) {
                $("#ooh_ast1").attr("disabled",false);
                $("#ooh_ast2").attr("disabled",false);
                $("#site_name").attr("disabled",false);
                $("#ad_sub_site_name").attr("disabled",false);
                $("#complaint_date").attr("disabled",false);
                $("#status").attr("disabled",false);
                $("#complaint_type").attr("disabled",false);
                $("#posted_by").attr("disabled",false);
                $("#assigned_for").attr("disabled",false);
                $("#complition_date").attr("disabled",false);
                $("#remark").attr("disabled",false);
                if(id == 'new') {
                    document.getElementById("clickedButton").value = 'new';
                   
                    document.getElementById("complaint_reg_no").value = '0';
                    document.getElementById("compalint_rev_no").value = "";
                    document.getElementById("posted_by").value = '';
                    document.getElementById("assigned_for").value = '';
                    document.getElementById("complaint_date").value = '';
                    document.getElementById("site_name").value = '';
                    document.getElementById("ad_sub_site_name").innerHTML = '';
                    // alert(ad_sub_site_name);
                    document.getElementById("complition_date").value = '';
                    document.getElementById("remark").value = '';
                   fillNewComplaintStatus();
                   $("#message").html("");
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("ho_sn_getSites").disabled = false;

                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 10);
                }
                if(id == 'edit') {
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("revise").disabled = false;
                    document.getElementById("ho_sn_getSites").disabled = true;
                   
                }
                document.getElementById("save").disabled = false;
                document.getElementById("message").innerHTML = "";
                $("#message").html("");
            }


            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }


            function   showComplitionDate(id){
                var datePlus="";
                datePlus = document.getElementById("complaint_date").value;
          
                var datePlus1 = datePlus.split('-');
                datePlus1[0] = Number(datePlus1[0])+1;
                var date=(datePlus1[0]+"-"+datePlus1[1]+"-"+datePlus1[2])
                //  var complition_date ="";
                //  alert(date);
                document.getElementById("complition_date").value =date;
          
             
            }

   



            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 10;
                var columnId = id;                            
                columnId = columnId.substring(3, id.length);  
                var lowerLimit, higherLimit, rowNo = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;      
                    higherLimit = (i + 1) * noOfColumns;   
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit))
                        break;
                }
                var lower= lowerLimit;
                var t1id = "t1c";     
                var t2id = "t2c";
                deleteRowWithoutResetForm();
                setDefaultColor(noOfRowsTraversed, noOfColumns); 
                document.getElementById("complaint_reg_no").value = document.getElementById("com_reg_id" + rowNo).value;

                //   document.getElementById("ad_sub_site_id").value = document.getElementById("sub_site_id" + rowNo).value;

                document.getElementById("compalint_rev_no").value = document.getElementById("compalint_rev_no" + rowNo).value;

                var ad_sub_type1=  document.getElementById("ad_sub_type1"+rowNo).value;
                $("#ooh_ast1 option" ).each(function()
                {
                    if($(this).val() == ad_sub_type1){
                        $(this).attr('selected', true);
                    }
                });
                ooh_fillAdSubType2();
                var ad_sub_type2=  document.getElementById("ad_sub_type2"+rowNo).value;
                $("#ooh_ast2 option" ).each(function()
                {
                    if($(this).val() == ad_sub_type2){
                        $(this).attr('selected', true);
                    }
                });

                /*
               var sub_site_name=  document.getElementById("sub_site_name"+rowNo).value;
              
               $("#ad_sub_site_name option" ).each(function()
                {
                    if($(this).val() == sub_site_name){
                        $(this).attr('selected', true);
                    }
                });
                fillAddSubSite();
                 */
                // document.getElementById("ad_sub_site_name").value = document.getElementById("sub_site_name" + rowNo).value;
               
                getComplainTypeList();
                document.getElementById("site_name").value = document.getElementById(t1id +(lower+2)).innerHTML;
                var role=  document.getElementById(t1id +(lower+3)).innerHTML;
                $("#status option" ).each(function()
                {
                    if($(this).text() == role){
                        $(this).attr('selected', true);
                    }
                });

                fillAddSubSite();
              // fillAddSubSiteId()
              var sub_site_name=  document.getElementById("sub_site_name"+rowNo).value;
              
                $("#ad_sub_site_name option" ).each(function()
                {
                    if($(this).text() == sub_site_name){
                        $(this).attr('selected', true);
                    }
                });
                  // fillAddSubSite();
                var complaint_type =  document.getElementById(t1id +(lower+4)).innerHTML;
                $("#complaint_type option" ).each(function()
                {
                    if($(this).text() == $.trim(complaint_type)){
                        $(this).attr('selected', true);
                    }
              
                });

                document.getElementById("complaint_date").value = document.getElementById(t1id +(lower+5)).innerHTML;
                document.getElementById("posted_by").value = document.getElementById(t1id +(lower+6)).innerHTML;
                document.getElementById("assigned_for").value = document.getElementById(t1id +(lower+7)).innerHTML;
                document.getElementById("complition_date").value = document.getElementById(t1id +(lower+8)).innerHTML;
                document.getElementById("remark").value = document.getElementById(t1id +(lower+9)).innerHTML;
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                document.getElementById("ho_sn_getSites").disabled = true;
                if(!document.getElementById("save").disabled) {
                    document.getElementById("save_As").disabled = true;
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                }
            }


            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'send'){
                    document.getElementById("clickedButton").value = "Send";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else if(id == 'edit') {
                    document.getElementById("clickedButton").value = "Edit";
                }else if(id == 'revise') {
                    document.getElementById("clickedButton").value = "Revise";
                } else
                    document.getElementById("clickedButton").value = "Delete";;
            }

            function setvalues(){               
                document.getElementById("s_complaint_no").value ="";
                document.getElementById("s_status").value =0;
                document.getElementById("s_posted_by").value = "";
                document.getElementById("s_assigned_for").value = "";
                document.getElementById("search_compalint_date_from").value = "";
                document.getElementById("search_compalint_date_to").value = "";
               //  document.getElementById("clickedButton").value = "Save";
            }
            function  setSiteDefaultName(){
                document.getElementById("site_name").value="";
                //  getComplainTypeList();
            }


            function getComplainTypeList(){
                try{
                    var adSubType2 = document.getElementById("ooh_ast2").value;
                    $.ajax({url: "complaintregisterCont.do", async: false,data: "task=compliantTypeList&adSubType2="
                            + adSubType2, success: function(response_data) {
                            $("#complaint_type").html("");
                            response_data= $.trim(response_data);
                            response_data=   $.trim(response_data.substr(1, response_data.length-2));
                            if(response_data != ''){
                            //  alert(response_data);
                              var arr = response_data.split(",");
                                var i;
                                for(i = 0; i < arr.length; i++) {
                                    var a1= arr[i].split("=");
                                    var opt        = document.createElement("option");
                                    opt.text = a1[1];
                                    opt.value = a1[0];
                                    document.getElementById("complaint_type").options.add(opt);
                                }
                            }
                            
                        }
                    });
                } catch(e){
                    alert("error in getComplainTypeList :="+e)
                }
            }

            function fillAddSubSite() {
                try{
                    var  site_name = document.getElementById("site_name").value;
  
                    $.ajax({url: "complaintregisterCont.do", async: false ,data: "task=getAddSubSite&ad_sub_site_name=OOH DisplayAdSubSite&site_name="  + site_name , success: function(response_data) {

                            // alert(response_data);
                            $("#ad_sub_site_name").html("");

                            // alert(response_data);
                            response_data= $.trim(response_data);
                           //  alert(response_data);
                            response_data=   $.trim(response_data.substr(0, response_data.length-1));
                        //  alert(response_data);
                            if(response_data != ''){
                             var arr = response_data.split("#" );
                              // alert(arr);
                                var i;
                                // alert(arr);
                                for(i = 0; i < arr.length; i++) {
                               //  alert(arr);
                                 var a1 = arr[i].split("=" );
                                    var opt        = document.createElement("option");                              
                                   opt.text = a1[1];
                                   opt.value = a1[0];

                              //    alert(a1[0]);
                                  
                                    document.getElementById("ad_sub_site_name").options.add(opt);
                                    // alert(a1);
                                }
                            }

                        }
                    });
                } catch(e){
                    alert("error in getComplainTypeList :="+e)
                }
                //  var  ad_sub_site= response_data;

                //  document.getElementById("ad_sub_site").value =ad_sub_site;
                //  }});
            }
   function  fillAddSubSiteId(){
     try{
                    var sub_site_name=  document.getElementById("sub_site_name"+rowNo).value;

                    $.ajax({url: "complaintregisterCont.do", async: false ,data: "task=getAddSubSiteName&ad_sub_site_name=OOH DisplayAdSubSite&sub_site_name="  + sub_site_name , success: function(response_data) {

                            // alert(response_data);
                            $("#ad_sub_site_name").html("");

                            // alert(response_data);
                            response_data= $.trim(response_data);
                           //  alert(response_data);
                            response_data=   $.trim(response_data.substr(0, response_data.length-1));
                        //  alert(response_data);
                            if(response_data != ''){
                             var arr = response_data.split("#" );
                              // alert(arr);
                                var i;
                                // alert(arr);
                                for(i = 0; i < arr.length; i++) {
                               //  alert(arr);
                                 var a1 = arr[i].split("=" );
                                    var opt        = document.createElement("option");
                                   opt.text = a1[1];
                                   opt.value = a1[0];

                              //    alert(a1[0]);

                                    document.getElementById("ad_sub_site_name").options.add(opt);
                                    // alert(a1);
                                }
                            }

                        }
                    });
                } catch(e){
                    alert("error in getComplainTypeList :="+e)
                }
                //  var  ad_sub_site= response_data;

                //  document.getElementById("ad_sub_site").value =ad_sub_site;
                //  }});

}

            function fillAddSubSites(site_name) {

                var site_name = document.getElementById("site_name").value;
                //  alert(complaint_no);
                if(complaint_no == 'Select') {
                    document.getElementById("complaint_no").focus();
                } else {
                    $.ajax({url: "complaintFeedbackCont.do", async: false ,data: "task=getAdSubSiteName&sub_site_name=OOH DisplayComplaint_no&site_name="  + site_name , success: function(response_data) {

                            $("#sub_site_name").html("");
                            //    var arr= response_data("&#;");
                            // alert(arr);
                            var  complaint_no_detail= response_data;
                            //   alert(complaint_no_detail);
                            document.getElementById("sub_site_name").value =complaint_no_detail;
                        }});
                }
            }
      
            function ComplaintReportPdf() {
                var s_complaint_no = document.getElementById("s_complaint_no").value;
             
                var s_posted_by = document.getElementById("s_posted_by").value;
                var s_status = document.getElementById("s_status").value;
                var delayed_work;
                if(document.getElementById("delayed_comp").checked){
                    delayed_work="Y";
                }else{
                    delayed_work="N";
                }  
                var s_assigned_for = document.getElementById("s_assigned_for").value;
                var queryString = "task=PRINTComplaintReport&s_posted_by="+s_posted_by+"&s_status="+s_status+"&s_assigned_for="+s_assigned_for+"&delayed_work="+delayed_work+"&s_complaint_no="+s_complaint_no;
                var url = "complaintHistoryReportCont.do?" + queryString;
                popupwin = openPopUp(url, "Complaint Report", 500, 750);
            }
   function ComplaintReportExcel() {
                var s_complaint_no = document.getElementById("s_complaint_no").value;
               //alert(s_complaint_no);
                var s_posted_by = document.getElementById("s_posted_by").value;
                var s_status = document.getElementById("s_status").value;
                var delayed_work;
                if(document.getElementById("delayed_comp").checked){
                    delayed_work="Y";
                }else{
                    delayed_work="N";
                }
                var s_assigned_for = document.getElementById("s_assigned_for").value;
                var queryString = "task=PRINTComplaintExcelReport&s_posted_by="+s_posted_by+"&s_status="+s_status+"&s_assigned_for="+s_assigned_for+"&delayed_work="+delayed_work+"&s_complaint_no="+s_complaint_no;
                var url = "complaintHistoryReportCont.do?" + queryString;
                popupwin = openPopUp(url, "Complaint Report", 500, 750);
            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url ,window_name, window_features);
            }
            if (!document.all) {
                document.captureEvents (Event.CLICK);
            }
            document.onclick = function() {
                if (popupwin != null && !popupwin.closed) {
                    popupwin.focus();
                }
            }


            function ho_getSites(selectionType) {
                var fwdUrl= "getSitesPopupView";
                var complaint_date= "" ,posted_by= "", assigned_for= "", remark= "", complaint_type="";
                complaint_date = document.getElementById("complaint_date").value;
                if($.trim(complaint_date).length == 0) {
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>Complaint Date is required...</b></td>");
                    document.getElementById("complaint_date").focus();
                    return false; // code to stop from submitting the form2.
                }
                posted_by = document.getElementById("posted_by").value;
                if($.trim(posted_by).length == 0) {
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>Posted By is required...</b></td>");
                    document.getElementById("posted_by").focus();
                    return false; // code to stop from submitting the form2.
                }
                assigned_for = document.getElementById("assigned_for").value;
            /*
            remark = document.getElementById("remark").value;
                if($.trim(remark).length == 0) {
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>Remark is required...</b></td>");
                    document.getElementById("remark").focus();
                    return false; // code to stop from submitting the form2.
                } */
                complaint_type = document.getElementById("complaint_type").value;
                if($.trim(complaint_type).length == 0) {
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>complaint Type is required...</b></td>");
                    document.getElementById("complaint_type").focus();
                    return false; // code to stop from submitting the form2.
                }
                var adSubType1 = document.getElementById("ooh_ast1").value;
                var adSubType2 = document.getElementById("ooh_ast2").value;
                var organisation_name='';
                var stateName, cityName;
                stateName = 'Madhya pradesh';
                cityName = 'jabalpur';
                var queryString = "task=getAllSiteDetails&adSubType1=" + adSubType1 + "&adSubType2=" + adSubType2 + "&state_name=" + stateName + "&city_name=" + cityName +
                    "&selectionType=" + selectionType+"&org_type=Associate&organisation_name="+organisation_name+"&display='block'&display_type=complaint&fwdUrl="
                    +fwdUrl+"&clickedFrom=&adSubType2_hidden="+adSubType2+"&totalSites=N";
                var url = "getSitesPopupCont.do?" + queryString;
                popupwin = openPopUp(url, "Site List", 560, 1040);
            }



            function getSelectedSitelist(id){
                var site_list=   document.getElementById(id).value;
                var site_name =  site_list.split("#&;");
                var i;
                for(i=0 ;i <site_name.length-1 ; i++){
                    addRow( site_name[i], $("#complaint_type").val(), $("#status").val(), $("#complaint_date").val(), $("#posted_by").val(),$("#assigned_for").val() ,$("#remark").val() );

                }
                document.getElementById("text").type = "hidden";
            }
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
           <%--<c:if test="${isSelectPriv eq 'Y'}">--%>
            <tr>
                <td>
                    <div id="main_div" class="maindiv" style="min-height: 600px ;max-height: auto">
                        <form name="form1" method="post" action="complaintHistoryReportCont.do" onsubmit="return verifyform()">
                            <table width="100%">
                                <tr>
                                    <td>
                                        <table width="100%" border="4">
                                            <tr>
                                                <td align="center" class="header_table" width="90%">Complaint History Report</td>
                                                <td><%@include file="/layout/complaint_menu.jsp" %> </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                  
                                <tr>
                                    <td>
                                        <table width="100%" class="content">
                                            <tr>
                                                <td class="heading">
                                                    Status <select id="s_status" name="s_status">
                                                        <option value="0"<c:if test="${s_status ==0}">selected</c:if>>Select</option>
                                                        <c:forEach var="status" items="${statuslist}">
                                                            <option  value="${status.key}" <c:if test="${s_status ==status.key}">selected</c:if>>${status.value}</option>
                                                        </c:forEach>
                                                    </select>                                                     
                                                </td >
                                                <td class="heading">
                                                    posted By<input type="text" name="s_posted_by" id="s_posted_by" value="${s_posted_by}">
                                                </td>
                                                <td class="heading">
                                                    Assigned For<input type="text" name="s_assigned_for" id="s_assigned_for" value="${s_assigned_for}" size="10">
                                                </td>
                                                 <td class="heading">
                                                    delayed Complition<input type="checkbox" name="delayed_comp" id="delayed_comp">
                                                </td>  

                                            </tr>
                                            <tr>
                                                <td class="heading">
                                                    Complaint Date From  <input type="text" id="search_compalint_date_from"name="search_compalint_date_from" value="${search_compalint_date_from}" size="15">
                                                    <%--    <a href="#" onclick="setYears(1947,2022);showCalender(this,'search_compalint_date_from')">
                                                            <img alt=""  src="images/calender.png">
                                                        </a>   --%>
                                                </td>
                                                <td class="heading">
                                                    Complaint Date To <input type="text" id="search_compalint_date_to"name="search_compalint_date_to" value="${search_compalint_date_to}" size="15">
                                                    <%--  <a href="#" onclick="setYears(1947,2022);showCalender(this,'search_compalint_date_to')">
                                                          <img alt=""  src="images/calender.png">
                                                      </a>   --%>
                                                </td>
                                                     <td class="heading">
                                                         Complaint No <input type="text" id="s_complaint_no" name="s_complaint_no" id="s_assigned_for" value="${s_complaint_no}" size="10">
                                                </td>
                                                <td class="heading" colspan="2">
                                                    <input type="submit" id="search" name="task" value="Search">
                                                    <input type="submit" id="clear" name="task" value="clear" onclick="setvalues()">
                                                    <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="ComplaintReportPdf()">  
                                                    <input class="excel_button" type="button" id="viewExcel" name="viewExcel" value="Excel" onclick="ComplaintReportExcel()">
                                                </td>
                                            </tr>


                                        </table>
                                    </td>
                                </tr>
                                

                                <tr>
                                    <td align="center">

                                        <DIV STYLE="overflow: auto; width: 990px; max-height:350px;padding:0px; margin-bottom:10px">
                                            <table id="table1" border="1" align="center" width="100%" class="reference">
                                            <c:forEach var="asso" items="${ComplaintHistoryReportList}" varStatus="loopCounter">
                                                 <tr>
                                                    <th class="heading1" >Complaint No.</th>                     
                                                    <td colspan="5">${asso.complaint_reg_no}</td>
                                                 </tr>                                                                                                                                                                                            
                                                 <tr>
                                                    <th class="heading">Complaint Type</th>
                                                    <th class="heading">Complaint sub Type</th>
                                                    <th class="heading">Status</th>
                                                    
                                                    <th class="heading">Posted By</th>
                                                    <th class="heading">Assigned for</th>
                                                    <th class="heading">Complaint Date</th>
                                                    <th class="heading">Complition Date</th>
                                                  <%--  <th class="heading">Remark</th>   --%>
                                                </tr>                                                                                       
                                                    <tr>                                                                                                       
                                                       <td >${asso.complaint_typeM} </td>
                                                       <td >${asso.complaint_sub_typeM} </td>
                                                       <td>${asso.statusM}</td>                                                                                                                                                                                                                        
                                                        <td>${asso.posted_byM}</td>
                                                        <td>${asso.assigned_forM}</td>
                                                       <td>${asso.complaint_dateM}</td>
                                                        <td>${asso.complition_date}</td>
                                                    </tr>
                                                <tr>
                                                     <th class="heading">S.No.</th>
                                                    <th class="heading">Complaint Feedback Type</th>
                                                    <th class="heading">Feedback Date</th>  
                                                    <th class="heading" colspan="4">Feedback Time</th>
                                                </tr>
                                               
                                              <tr>
                                                     <c:forEach var="feedback" items="${asso.feebackList}" varStatus="loopCounter">
                                                    
                                                      <td>${loopCounter.count}
                                                      </td>
                                                       <td>${feedback.feedback_type}</td> ${feebackList}
                                                       <td>${feedback.feedback_date}</td>
                                                       <td colspan="4" >${feedback.feedback_hr} ${feedback.feedback_min} ${feedback.feedback_time}</td>
                                                    </tr>
                                          </c:forEach>     
                                              </c:forEach>
                                                                                                                                        
                                                <tr>
                                                    <td align='center' colspan="11">                                                                                                        
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
                                          
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <%--   <input type="hidden" id="search" name="task" value="Search"> --%>
                                                <input type="hidden" id="search_compalint_date_from"name="search_compalint_date_from" value="${search_compalint_date_from}" size="15">
                                                <input type="hidden" id="search_compalint_date_to"name="search_compalint_date_to" value="${search_compalint_date_to}" size="15">
                                            </table>
                                        </DIV>


                                    </td>
                                </tr>
                                <tr>
                                    
                                <tr>
                                    
                            </table>
                        </form>

                    </div>  

                </td>

            </tr>
             <%--</c:if>--%>
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
