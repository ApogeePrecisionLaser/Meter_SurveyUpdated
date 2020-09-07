<%--
    Document   : poleDetailView
    Created on : Jul 10, 2014, 5:01:46 AM
    Author     : JPSS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" language="javascript">
    function openMap(logitude, lattitude) {
        var x = $.trim(document.getElementById(lattitude).value);
        var y = $.trim(document.getElementById(logitude).value);
   
        var url="poleDetailCont?task=showMapWindow&logitude="+y+"&lattitude="+x;
        popupwin = openPopUp(url, "",  580, 620);
    }
    // google.maps.event.addDomListener(window, 'load', initialize);


    jQuery(function(){
        if($('form[name="form1"] input[type=checkbox]:checked').size() == 0){
            $("#updateClientPoleNo").attr("disabled", "disabled");
        }else{
            $("#updateClientPoleNo").removeAttr("disabled");
        }
        
        $("#searchPoleType").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getPoleType"}
            }
        });
        $("#searchSwitchingPoint").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getSearchSwitchingPointName"}
            }
        });
      
        $("#searchPoleNo").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getPoleNo"},
                action2: function() { return  $("#searchSwitchingPoint").val();}
            }
        });
        $("#searchMountingType").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getMountingType"}
            }
        });
    });
    jQuery(function(){
        $("#pole_type").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getPoleType"}
            }
        });

        $("#mounting_type").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getMountingType"}
            }
        });
        //      for(var i=1; i<10 ; i++){
        $("#source_wattage").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getSourceWattageType"}
            }
        });
        for(var i=0; i<50; i++){
            $("#source_wattage"+i).autocomplete("poleDetailCont", {
              
                extraParams: {
                    action1: function() { return "getSourceWattageType"}
                }
            });
        } 
        $("#feeder_name").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getSwitchingFeederName"}
            }
        });
        $("#switching_point_name").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getSwitchingPointName"},
                action2: function() { return  $("#feeder_name").val();}
            }
        });

        $("#city").result(function(event, data, formatted){
            document.getElementById("ward_no").value = "";
            document.getElementById("area_name").value = "";
            $("#ward_no").flushCache();
            $("#area_name").flushCache();
        });
        $("#ward_no").result(function(event, data, formatted){
            document.getElementById("area_name").value = "";
            $("#area_name").flushCache();
        });

       $("#ward_no").change(function(){
                    $("#area_name").val('');
                    $("#area_name").flushCache();
             //    document.getElementById("feeder_name_filter").focus();
            });
       $("#road_category").result(function(event, data, formatted){
            document.getElementById("road_name").value = "";
            $("#road_name").flushCache();
        });
      /*  $("#road_use").result(function(event, data, formatted){
            document.getElementById("road_name").value = "";
            $("#road_name").flushCache();
        }); */

     $("#road_use").change(function(){
                    $("#road_name").val('');
                    $("#road_name").flushCache();
             //    document.getElementById("feeder_name_filter").focus();
            });
        $("#road_use").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getRoadUse"}
            }
        });
        $("#road_category").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getRoad_Category"}
            }
        });
        $("#road_name").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getRoadName"},
                action2: function() { return document.getElementById("road_category").value;},
                action3: function() { return document.getElementById("road_use").value;}
            }
        });

        $("#traffic_type").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getTrafficType"}
            }
        });


        $("#city").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getCity"}
            }
        });
        $("#ward_no").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getWard_No"},
                action2: function() { return document.getElementById("city").value;}
            }
        });

        $("#area_name").autocomplete("poleDetailCont", {
            extraParams: {
                action1: function() { return "getAreaName"},
                action2: function() { return document.getElementById("ward_no").value;}
            }
        });

    });
    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }

    /* function deleteRow() {
                 try {
                     var table = document.getElementById('table2');
                     var rowCount = table.rows.length-1;
                     //    alert("Before"+rowCount);
                     for(var i=50; i >0; i--) {
                         //alert(i);
                         //table.deleteRow(i);
                         //     $('#table2 tr:eq('+i+')').remove();
                         $("#dynamic"+i).css("display", "none");
                         //    alert(i);
                     }
                 }catch(e) {
                     alert(e);
                 }
             }  */
    function makeEditable(id) {
        // alert(id);
        //  document.getElementById("source_wattage").readOnly =true;
        document.getElementById("feeder_name").disabled = false;
        document.getElementById("switching_point_name").disabled = false;

        document.getElementById("pole_type").disabled = false;
        document.getElementById("mounting_type").disabled = false;
        document.getElementById("pole_span").disabled = false;
        document.getElementById("pole_height").disabled = false;
        document.getElementById("mounting_height").disabled = false;
        document.getElementById("created_by").disabled = true;
        document.getElementById("created_date").disabled = true;
        document.getElementById("pole_no").disabled = false;
        document.getElementById("pole_no_client").disabled = false;
        document.getElementById("gps_code").disabled = false;
        document.getElementById("max_avg_lux_level").disabled = false;
        document.getElementById("min_avg_lux_level").disabled = false;
        document.getElementById("avg_lux_level").disabled = false;
        document.getElementById("standard_lux_level").disabled = false;
        // document.getElementById("source_wattage").disabled = false;
        document.getElementById("quantity").disabled = false;
        document.getElementById("is_working").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("active").disabled = false;
        document.getElementById("pole_rev_no").disabled = false;

        document.getElementById("city").disabled = false;
        document.getElementById("ward_no").disabled = false;
        document.getElementById("road_use").disabled = false;
        document.getElementById("road_category").disabled = false;
        document.getElementById("area_name").disabled = false;
        document.getElementById("road_name").disabled = false;
        document.getElementById("traffic_type").disabled = false;
        //    document.getElementById("source_wattage").readOnly =false;

        // document.getElementById("addMore").disabled =true;
        document.getElementById("revise").disabled = false;
        document.getElementById("update").disabled = false;
        document.getElementById("delete").disabled = false;
        document.getElementById("save_As").disabled =false;
        document.getElementById("save").disabled = true;
        //

        if(id == 'new') {
            //  alert("gffg");
            // document.getElementById("created_date").disabled =false;
            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            //document.getElementById("source_wattage").readOnly =false;

            //  document.getElementById("addMore").disabled =false;
            document.getElementById("update").disabled = true;
            document.getElementById("revise").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;
            document.getElementById("is_switch").disabled =false;
            deleteRow();
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 32);
            //document.getElementById("pole_type").focus();
            document.getElementById("feeder_name").focus();
            document.getElementById("pole_id").value='';
            // document.getElementById("addRowCount").value = "0";
            document.getElementById("newRowCount").value = "0";
        }
        /*   if(id == 'edit'){
            document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
        } */
    }
    function addRowaddRow() {
        //alert("ghgh");
      var count = document.getElementById("newRowCount").value;
     /* var table = document.getElementById('table2');
            var rowCount = table.rows.length-1;
     
        for(var i=10; i<rowCount; i++){
                var a= $("#source_wattage"+i).val();

                var b=$("#quantity"+i).val()
                //alert(a);
                if(''== $("#source_wattage"+i).val()) {

                    // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Light Type should be unique...</b></td>");
                    document.getElementById("source_wattage"+i).focus();
                    return false; // code to stop from submitting the form2.
                }

       } */
        count++;
        // document.getElementById("source_wattage"+count).readOnly =false;
        $("#dynamic"+ count).css("display", "table-row");
        document.getElementById("newRowCount").value = count;
        var a = document.getElementById("newRowCount").value;
    //      alert(count);
    }
    function setStatus(id) {
        if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        }
        else if(id == 'update'){
            document.getElementById("clickedButton").value = "Update";
        }
        else if(id == 'revise'){
            document.getElementById("clickedButton").value = "Revise";
        }
        else if(id == 'save_As'){
            document.getElementById("clickedButton").value = "Save AS New";
        }else if(id == 'addMore'){
            document.getElementById("clickedButton").value = "Add More";
        }
        else document.getElementById("clickedButton").value = "Delete";
    }
    function verify() {
        // alert("hgh");
        var result, light_type="", quantity="";
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New'|| document.getElementById("clickedButton").value == 'Update' || document.getElementById("clickedButton").value == 'Revise'||document.getElementById("clickedButton").value == 'Delete') {
            var pole_type = document.getElementById("pole_type").value;
            var feeder_name =    document.getElementById("feeder_name").value;

            var switching_point_name =  document.getElementById("switching_point_name").value;
            //  var pole_type =  document.getElementById("pole_type").value;
            var mounting_type =  document.getElementById("mounting_type").value;
            var pole_span = document.getElementById("pole_span").value;
            var pole_height =  document.getElementById("pole_height").value;
            var mounting_height =  document.getElementById("mounting_height").value;
            //  var created_by =  document.getElementById("created_by").value;
            //  var created_date =   document.getElementById("created_date").value;
            var pole_no =   document.getElementById("pole_no").value;
            var pole_no_client =   document.getElementById("pole_no_client").value;
            var gps_code =  document.getElementById("gps_code").value;
            var max_avg_lux_level =   document.getElementById("max_avg_lux_level").value;
            var min_avg_lux_level =   document.getElementById("min_avg_lux_level").value;
            var avg_lux_level =   document.getElementById("avg_lux_level").value;
            var standard_lux_level =    document.getElementById("standard_lux_level").value;
            var source_wattage =     document.getElementById("source_wattage").value;
            var quantity =    document.getElementById("quantity").value;
            var is_working =    document.getElementById("is_working").value;
            var remark =    document.getElementById("remark").value;
            var area_name = document.getElementById("area_name").value;
            var road_name = document.getElementById("road_name").value;
            var traffic_type = document.getElementById("traffic_type").value;
            // var active =    document.getElementById("active").value;
            // var pole_rev_no =     document.getElementById("pole_rev_no").value;


            
            if(myLeftTrim(feeder_name).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Feeder Name is required...</b></td>");
                document.getElementById("feeder_name").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(switching_point_name).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Switching Point Name is required...</b></td>");
                document.getElementById("switching_point_name").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(pole_type).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Pole Type is required...</b></td>");
                document.getElementById("pole_type").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(pole_no).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Pole No is required...</b></td>");
                document.getElementById("pole_no").focus();
                return false; // code to stop from submitting the form2.
            }
//            if(myLeftTrim(pole_no_client).length == 0) {
//
//                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
//                $("#message").html("<td colspan='6' bgcolor='coral'><b>Pole No (Client) is required...</b></td>");
//                document.getElementById("pole_no_client").focus();
//                return false; // code to stop from submitting the form2.
//            }
            if(myLeftTrim(pole_span).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Pole Span is required...</b></td>");
                document.getElementById("pole_span").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(pole_height).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Pole height is required...</b></td>");
                document.getElementById("pole_height").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(mounting_type).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Mounting Type is required...</b></td>");
                document.getElementById("mounting_type").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(mounting_height).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Mounting height is required...</b></td>");
                document.getElementById("mounting_height").focus();
                return false; // code to stop from submitting the form2.
            }

            if(myLeftTrim(gps_code).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>GPS Code Name is required...</b></td>");
                document.getElementById("gps_code").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(max_avg_lux_level).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Max Avg Lux Level  is required...</b></td>");
                document.getElementById("max_avg_lux_level").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(min_avg_lux_level).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Min Avg Lux Level  is required...</b></td>");
                document.getElementById("min_avg_lux_level").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(avg_lux_level).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Avg Lux Levelis required...</b></td>");
                document.getElementById("avg_lux_level").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(standard_lux_level).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Standard Lux Level is required...</b></td>");
                document.getElementById("standard_lux_level").focus();
                return false; // code to stop from submitting the form2.
            }

            if(myLeftTrim(area_name).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Area Name is Required...</b></td>");
                document.getElementById("area_name").focus();
                return false;
            }
            if(myLeftTrim(road_name).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Road Name is Required...</b></td>");
                document.getElementById("road_name").focus();
                return false;
            }
            if(myLeftTrim(traffic_type).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Traffic Type is Required...</b></td>");
                document.getElementById("traffic_type").focus();
                return false;
            }

            if(myLeftTrim(source_wattage).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Light Type is required...</b></td>");
                document.getElementById("source_wattage").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(quantity).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Quantity is required...</b></td>");
                document.getElementById("quantity").focus();
                return false; // code to stop from submitting the form2.
            }

            //  var table = document.getElementById('table2');
           //   var row = table.rows.length;
             //   alert(row);
           for(var i=0; i<30; i++){

                var a= $("#source_wattage"+i).val();
                var b=$("#quantity"+i).val()
                //alert(a);
              
               if(source_wattage== $("#source_wattage"+i).val()) {
                    //     alert(i);
                    // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Light Type should be unique...</b></td>");
                    document.getElementById("source_wattage"+i).focus();
                    return false; // code to stop from submitting the form2.
                }
             /*   for(var  j= i+1; j<30; j++){
                     if($("#source_wattage"+j).val()== $("#source_wattage"+i).val()) {
                     //    alert(j);
                   // alert(i);
                   // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Light Type should be unique...</b></td>");
                    document.getElementById("source_wattage"+j).focus();
                    return false; // code to stop from submitting the form2.
                }*/
             //  }

             /* if($("#quantity"+i).val()='') {
                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Quantity is required....</b></td>");
                document.getElementById("quantity"+i).focus();
                return false; // code to stop from submitting the form2.
            }  */
         

            }

            if(document.getElementById("active").value =='N')
            {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>You can not perform any operation on InActive record...</b></td>");
                // document.getElementById("source_wattage"+i).focus();
                return false; // code to stop from submitting the form2.
            }
            if(result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

            }
            else{ result = true;
            }if(document.getElementById("clickedButton").value == 'Add More'){
                result = confirm("Are you sure you want to add More record?")
                return result;
            }

            if(document.getElementById("clickedButton").value == 'Save AS New'){
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
            else if(document.getElementById("clickedButton").value == 'Update'){
                result = confirm("Are you sure you want to update this record?")
                return result;
            }
            else if(document.getElementById("clickedButton").value == 'Revise'){
                result = confirm("Are you sure you want to revise this record?")
                return result;
            }
        } else result = confirm("Are you sure you want to Cancelled this record?")
        if(document.getElementById("source_wattage").value > 0){
          //  addRow();
            $("#message").html("");
            return false;
        }

        return result;
    }
    /*function addRow() {
        var table = document.getElementById('table2');
        var rowCount = table.rows.length;                // alert(rowCount);
        var row = table.insertRow(rowCount);
        var cell1 = row.insertCell(0);
        //cell1.innerHTML=rowCount;
        var id = rowCount - 8;
        cell1.width="15%";
        var th=document.createElement("th");
        th.setAttribute('class', "heading1");
        th.setAttribute('style', "width:20%");
        th.innerHTML = "Light Type"
        // th.innerHTML = "Sub Dept "+(rowCount)+":";
        cell1.appendChild(th);

        var cell2 = row.insertCell(1);
        var element1 = document.createElement("input");
        element1.type = "text";
        element1.name = "source_wattage";

        element1.id = "source_wattage"+id;
        // alert(element1.id);
        element1.size = 20;
        element1.value = "";
     //   alert(element1.value);
        element1.readOnly = false;
        cell2.appendChild(element1);

        var cell3 = row.insertCell(2);
        cell3.width="15%";
        var th1=document.createElement("th");
        th1.setAttribute('class', "heading1");
        th1.setAttribute('style', "width:20%");
        th1.innerHTML = "Quantity"
        // th.innerHTML = "Sub Dept "+(rowCount)+":";
        cell3.appendChild(th1);

        var cell4 = row.insertCell(3);
        var element2 = document.createElement("input");
        element2.type = "text";
        element2.name = "quantity";
        element2.id = "quantity"+id;
        element2.size = 20;
        element2.value = " ";
        cell4.appendChild(element2);


        var height = (rowCount * 40)+ 60;

    }  */


    function fillColumns(id) {
        deleteRow();
        //  deleteRow();
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 33;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit, rowNo = 0;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
            rowNo++;
            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        
        document.getElementById("pole_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("pole_type").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
        document.getElementById("pole_no").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        document.getElementById("pole_no_client").value = $.trim(document.getElementById("client_pole_no_edit" + rowNo).value);
        document.getElementById("pole_span").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
        document.getElementById("pole_height").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
        document.getElementById("mounting_type").value = document.getElementById(t1id +(lowerLimit+7)).innerHTML;
        document.getElementById("mounting_height").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        document.getElementById("feeder_name").value = document.getElementById(t1id +(lowerLimit+21)).innerHTML;
        document.getElementById("switching_point_name").value = document.getElementById(t1id +(lowerLimit+10)).innerHTML;
        var is_switch_point = document.getElementById(t1id +(lowerLimit+22)).innerHTML;
        //alert(is_switch_point);
      
        if(is_switch_point=='Yes'){
            // }
            //alert(is_switch_point);
            //document.getElementById("is_switch_point").value=checked;
            //document.getElementById("is_switch").checked = false;
            document.getElementById("is_switch").checked = true;
            document.getElementById("is_switch_point").value="Yes";
        }else
        {
            //  alert(is_switch_point);
            document.getElementById("is_switch").checked =false;
            document.getElementById("is_switch_point").value="No";
        }
        document.getElementById("is_switch").disabled  =true;

        // switching_point_name = 9
        // switching load = 10
        document.getElementById("gps_code").value = document.getElementById(t1id +(lowerLimit+12)).innerHTML;
        document.getElementById("max_avg_lux_level").value = document.getElementById(t1id +(lowerLimit+13)).innerHTML;
        document.getElementById("min_avg_lux_level").value = document.getElementById(t1id +(lowerLimit+14)).innerHTML;
        document.getElementById("avg_lux_level").value = document.getElementById(t1id +(lowerLimit+15)).innerHTML;
        document.getElementById("standard_lux_level").value = document.getElementById(t1id +(lowerLimit+16)).innerHTML;
        document.getElementById("is_working").value = document.getElementById(t1id +(lowerLimit+17)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+18)).innerHTML;
        document.getElementById("active").value = document.getElementById(t1id +(lowerLimit+19)).innerHTML;
        document.getElementById("is_switch_point").value = document.getElementById(t1id +(lowerLimit+22)).innerHTML;

        document.getElementById("area_name").value = document.getElementById(t1id +(lowerLimit+25)).innerHTML;
        document.getElementById("road_name").value = document.getElementById(t1id +(lowerLimit+26)).innerHTML;
        document.getElementById("traffic_type").value = document.getElementById(t1id +(lowerLimit+27)).innerHTML;
        document.getElementById("ward_no").value = document.getElementById(t1id +(lowerLimit+28)).innerHTML;
        document.getElementById("city").value = document.getElementById(t1id +(lowerLimit+29)).innerHTML;
        document.getElementById("road_category").value = document.getElementById(t1id +(lowerLimit+30)).innerHTML;
        document.getElementById("road_use").value = document.getElementById(t1id +(lowerLimit+31)).innerHTML;
        //var a = document.getElementById(t1id +(lowerLimit+21)).innerHTML;
        //alert(a);
        //     var a = document.getElementById(t1id +(lowerLimit+18)).innerHTML;
        // alert(a);
        document.getElementById("pole_rev_no").value = document.getElementById(t1id +(lowerLimit+20)).innerHTML;
        // document.getElementById("source_wattage").readOnly =false;
        var concatinated_map_id =  $.trim(document.getElementById("map_id" + rowNo).value);
        var xabc = concatinated_map_id.split("__");
        var xabcd = xabc[0];
        var xmap_id = xabcd.split("-")[0];
        var xlight_type_id = xabcd.split("-")[1];
        document.getElementById("mapp_id").value = xmap_id;
        document.getElementById("light_type_id").value = xlight_type_id;

        var query=document.getElementById(t1id +(lowerLimit+9)).innerHTML;
        var abc=query.split("__");
        var abcd=abc[0];
        var light= abcd.split(",")[0];
        var quantity= abcd.split(",")[1];
        document.getElementById("source_wattage").value = light;
                 
        document.getElementById("quantity").value = quantity;

        // var xyz  =  iswork.split("__");
        var rowCount=abc.length;
        //  var rowCounts=xyz.length;
        //  var xyzz = xyz[0];
        //  alert(xyzz);
        //  var xyzzz = xyzz.split(",")[1];
        //  document.getElementById("working").value = xyzzz.split("/")[0];
        // document.getElementById("n_working").value = xyzzz.split("/")[1];
        if(rowCount>1){
            document.getElementById("newRowCount").value = rowCount-1;
            for(var ii=1; ii<=30; ii++){
                document.getElementById("source_wattage"+ii).value = "";
                document.getElementById("mapp_id"+ii).value = "";
                document.getElementById("light_type_id"+ii).value = "";
                document.getElementById("quantity"+ii).value = "";
            } 

            for(i=1; i<rowCount; i++)
            {   var xquery1 = xabc[i];
                var xmap_idd = xquery1.split("-")[0];
                var xlight_type_idd = xquery1.split("-")[1];

                var query1 = abc[i];
                var light_type = query1.split(",")[0];
                var q = query1.split(",")[1];

                $("#dynamic"+ i).css("display", "table-row");


                // alert(q);

                //        var table = document.getElementById('table2');
                //         var row = table.rows.length;
                //       idd = row - 9 ;

                // var query2 = xyz[i];
                //alert(query2);
                //   var type_wrkingNwrk = query2.split(",")[1];

                //    var working = type_wrkingNwrk.split("/")[0];
                //   var not_working = type_wrkingNwrk.split("/")[1];
                //   document.getElementById("working"+i).value = working;
                //  document.getElementById("n_working"+i).value = not_working;


                document.getElementById("source_wattage"+i).value = light_type;
                //   document.getElementById("source_wattage"+i).readOnly =true;

                document.getElementById("quantity"+i).value = q;
                document.getElementById("mapp_id"+i).value = xmap_idd;
                document.getElementById("light_type_id"+i).value = xlight_type_idd;

            }
            // document.getElementById("addMore").disabled = false;
                   
            document.getElementById("addRowCount").value=rowCount-1;
            var table = document.getElementById('table2');
            var row = table.rows.length;
            //   alert("total:"+row);

        }
        /*     var abc=query.split("__");
   //   alert(abc);
      var abcd=abc[0];
        var light= abcd.split(",")[0];
       //var light= abcd.split(",")[0];
     //  alert(light);
     var light= abcd.split(",")[0];
     var quantity= abcd.split(",")[1];

    // var quantity= abcd.split("-")[1];
   //  alert(quantity);
      document.getElementById("source_wattage").value = light;
        document.getElementById("quantity").value = quantity;

        var rowCount=abc.length;
      //  alert(rowCount);
        if(rowCount>1){
            for(i=1; i<rowCount; i++)
            {
              $("#dynamic"+ i).css("display", "table-row");
                var query1 = abc[i];
                var light_type = query1.split(",")[0];
                var q = query1.split(",")[1];
                // alert(q);
              //  addRow();
              //  var table = document.getElementById('table2');
              //  var row = table.rows.length;
               // var id = row - 9 ;
                document.getElementById("source_wattage" + id).value = light_type;
                document.getElementById("quantity" + id).value = q;

            }
        } */

        /*  document.getElementById("revise").disabled = false;
                    document.getElementById("update").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled =false;
                    document.getElementById("save").disabled = true; */
      
        //  document.getElementById("revise").disabled = false;
        // document.getElementById("save_As").disabled = false;
        //   document.getElementById("delete").disabled = false;
        makeEditable('');
        /*    document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
        {
            document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
        } */
        //   if(document.getElementById("active").value == 'Cancelled' || document.getElementById("active").value == 'Revised')
        /*  if(document.getElementById("active").value =='N')
        {

            document.getElementById("feeder_name").value = "";
            document.getElementById("switching_point_name").value ="";
            document.getElementById("pole_id").value= "";
            document.getElementById("pole_type").value = "";
            document.getElementById("pole_no").value = "";
            document.getElementById("pole_span").value = "";
            document.getElementById("pole_height").value = "";
            document.getElementById("mounting_type").value = "";
            document.getElementById("mounting_height").value = "";
            //document.getElementById("source_wattage").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
            document.getElementById("gps_code").value = "";
            document.getElementById("max_avg_lux_level").value = "";
            document.getElementById("min_avg_lux_level").value = "";
            document.getElementById("avg_lux_level").value = "";
            document.getElementById("standard_lux_level").value = "";
            document.getElementById("is_working").value = "";
            document.getElementById("remark").value = "";
            document.getElementById("active").value = "";
            document.getElementById("pole_rev_no").value = "";
            document.getElementById("source_wattage").value = "";
            document.getElementById("quantity").value = "";   
             deleteRow();
        }  */
        //   makeEditable('');
        //  document.getElementById("message").innerHTML = "";      // Remove message
        $("#message").html("");

    }
    /*function deleteRow() {
        try {
            var table = document.getElementById('table2');
            var rowCount = table.rows.length;
            alert(rowCount);
            for(var i=rowCount; i >9; i--) {
                //alert(i);
                //table.deleteRow(i);
                $('#table2 tr:eq('+i+')').remove();
                //alert(i);
            }
        }catch(e) {
            alert(e);
        }
    } */
    function deleteRow() {
        try {
            var table = document.getElementById('table2');
            var rowCount = table.rows.length-1;
            //    alert("Before"+rowCount);
            for(var i=50; i >0; i--) {
                //alert(i);
                //table.deleteRow(i);
                //     $('#table2 tr:eq('+i+')').remove();
                $("#dynamic"+i).css("display", "none");
                //    alert(i);
            }
        }catch(e) {
            alert(e);
        }
    }
    var popupwin = null;
    function myLeftTrim(str) {
        var beginIndex = 0;
        for(var i = 0; i < str.length; i++) {
            if(str.charAt(i) == ' ')
                beginIndex++;
            else break;
        }
        return str.substring(beginIndex, str.length);
    }
    function displayMapList(){
        var searchSwitchingPoint= $("#searchSwitchingPoint").val();
        var searchMountingType= $("#searchMountingType").val();
        var searchPoleType= $("#searchPoleType").val();
        var searchPoleNo= $("#searchPoleNo").val();
        var queryString = "task=generateMapReport&searchPoleType="+searchPoleType+"&searchMountingType="+searchMountingType+"&searchSwitchingPoint="+searchSwitchingPoint+"&searchPoleNo="+searchPoleNo ;
        var url = "poleDetailCont?"+queryString;
        popupwin = openPopUp(url, "POLE DETAIL Type Map Details", 500, 1000);

    }
    function displayExcelList(){
        var searchSwitchingPoint = document.getElementById("searchSwitchingPoint").value;
         
        if(myLeftTrim(searchSwitchingPoint).length == 0) {

            // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
            $("#messagee").html("<td colspan='6' bgcolor='coral'><b>Switching Point Name is required...</b></td>");
            document.getElementById("searchSwitchingPoint").focus();
            return false; 
        }else{
            var searchSwitchingPoint= $("#searchSwitchingPoint").val();
            var searchMountingType= $("#searchMountingType").val();
            var searchPoleType= $("#searchPoleType").val();
             var searchPoleNo= $("#searchPoleNo").val();
            var queryString = "task=generatePoleExcelReport&searchPoleType="+searchPoleType+"&searchMountingType="+searchMountingType+"&searchSwitchingPoint="+searchSwitchingPoint+"&searchPoleNo="+searchPoleNo ;
            var url = "poleDetailCont?"+queryString;
            popupwin = openPopUp(url, "POLE DETAIL Excel", 500, 1000);
        }
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }
    function isSwitchPoint(id){
        document.getElementById("is_switch_point").value="Yes";
    }

    function singleCheckUncheck(id){
        //  alert(document.getElementById('insertTable').rows.length);
        if ($('#pole_checkbox'+id).is(':checked')) {
            $('#client_pole_no_id'+id).val("1");
            document.getElementById("client_pole_no_edit"+id).readOnly = false;
            // $('.ivrs_no_edit'+id).removeAttr('readonly');
            //document.getElementById("ivrs_no_edit"+id).disabled = false;
        }else{
            $('#client_pole_no_id'+id).val("0");
            document.getElementById("client_pole_no_edit"+id).readOnly = true;
            // $('.ivrs_no_edit'+id).attr('readonly', true);
            //document.getElementById("ivrs_no_edit"+id).disabled = true;
        }
        if($('form[name="form1"] input[type=checkbox]:checked').size() == 0){
            $("#updateClientPoleNo").attr("disabled", "disabled");
        }else{
            $("#updateClientPoleNo").removeAttr("disabled");
        }
        if($('form[name="form1"] input[type=checkbox]:checked').size() == 10){
            //   alert("ALL checked");
        }


    }
 function IsPoleLightTpyeQuantity(id) {
                // alert(id);
                var strString=    document.getElementById(id).value;
                var strValidChars = "+-.0123456789";
                var strChar;
                var blnResult = true;
                if (strString.length == 0) return false;
                for (var i = 0; i < strString.length && blnResult == true; i++)
                {
                    strChar = strString.charAt(i);
                    if (strValidChars.indexOf(strChar) == -1)
                    {
                        document.getElementById(id).value="";
                        alert("Value should be numeric");
                        blnResult = false;
                    }
                }
                return blnResult;
            }
</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pole Detail</title>
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
                                        <td align="center" class="header_table" width="100%">POLE DETAIL</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="poleDetailCont">
                                        <table align="center" class="heading1" width="600">

                                          <%---   <tr id="message1">
                                                <c:if test="${not empty messagee}">
                                                    <td colspan="12" bgcolor="${msgBgColorr}"><b>Result: ${messagee}</b></td>
                                                </c:if>
                                            </tr> --%>
                                            <tr>

                                               
                                                <td>Switching Point Name<input class="input" type="text" id="searchSwitchingPoint" name="searchSwitchingPoint" value="${searchSwitchingPoint}" size="20" ></td>
                                                <td>Pole No.<input class="input" type="text" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" size="20" ></td>
                                                <td>Pole Type<input class="input" type="text" id="searchPoleType" name="searchPoleType" value="${searchPoleType}" size="20" ></td>
                                                <td>Mounting Type<input class="input" type="text" id="searchMountingType" name="searchMountingType" value="${searchMountingType}" size="20" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>
                                                <td><input type="button" class="excel_button" id="viewExcel" name="viewExcel" value="View Excel" onclick="displayExcelList()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="poleDetailCont">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th style="white-space: normal" class="heading">S.No.</th>
                                                <th style="white-space: normal" class="heading">Pole Type*</th>
                                                <th style="white-space: normal" class="heading">Pole No*</th>
                                                <th style="white-space: normal; display: none" class="heading">Pole No (Client)</th>
                                                <th style="white-space: normal" class="heading">Pole Span(M)*</th>
                                                <th style="white-space: normal" class="heading">Pole Height(M)*</th>
                                                <th style="white-space: normal" class="heading">Mounting Type</th>
                                                <th style="white-space: normal" class="heading">Mounting Height(M)*</th>
                                                <th  class="heading"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Light type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                                <th style="white-space: normal" class="heading">Switching Point Name*</th>
                                                <th style="white-space: normal" class="heading">Switching Load (Watt.)</th>
                                                <th style="white-space: normal" class="heading">GPS code*</th>
                                                <th style="white-space: normal" class="heading">Max Avg Lux Level*</th>
                                                <th style="white-space: normal" class="heading">Min Avg Lux Level*</th>
                                                <th style="white-space: normal" class="heading">Avg Lux Level*</th>
                                                <th style="white-space: normal" class="heading">Standard Lux level*</th>
                                                <th style="white-space: normal" class="heading">Is Working*</th>
                                                <th style="white-space: normal" class="heading">Remark</th>
                                                <th style="white-space: normal" class="heading">Active</th>
                                                <th style="white-space: normal" class="heading">Pole Rev No</th>
                                                <th style="white-space: normal" class="heading">Feeder Name</th>
                                                <th style="white-space: normal" class="heading">Is Switch Point</th>
                                                <th style="white-space: normal" class="heading">View Map</th>

                                                <th style="white-space: normal" class="heading">Area Name</th>
                                                <th style="white-space: normal" class="heading">Road Name</th>
                                                <th style="white-space: normal" class="heading">Traffic Type</th>
                                                <th style="white-space: normal" class="heading">Ward No</th>
                                                <th style="white-space: normal" class="heading">City</th>
                                                <th style="white-space: normal" class="heading">Road Category</th>
                                                <th style="white-space: normal" class="heading">Road Used For</th>
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                            <c:forEach var="poleTypeBean" items="${requestScope['poleTypeList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                          <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                          <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                      </td> --%>
                                                    <td id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id)">${poleTypeBean.pole_id}</td>


                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_no}</td>

                                                    <td style="display: none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >
                                                        <input type="hidden" name="pole_id_showData" id="pole_id_showData${loopCounter.count}" value=${poleTypeBean.pole_id} />
                                                        <input id="pole_checkbox${loopCounter.count}" class="checkbox1" name="pole_checkbox" type="checkbox" onclick="singleCheckUncheck(${loopCounter.count})"/>
                                                        <input type="text" class="client_pole_no_id" id="client_pole_no_id${loopCounter.count}" name="client_pole_no_id" value="0" style="display:none"/>
                                                        <input type="text" class="client_pole_no_edit" id="client_pole_no_edit${loopCounter.count}" name="client_pole_no_edit" value="${poleTypeBean.pole_no_client}" readonly/>
                                                    </td>


                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_span}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_height}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mounting_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mounting_height}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.source_wattage}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.switching_point_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.switching_load}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.gps_code}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.max_avg_lux_level}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.min_avg_lux_level}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.avg_lux_level}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.standard_lux_level}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.is_working}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.active}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_rev_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.feeder_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.is_switch_point}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id)">
                                                        <input type="text" id="longitude${loopCounter.count}" name="longitude" value="${poleTypeBean.longitude}"/>
                                                        <input type="text"  id="lattitude${loopCounter.count}" name="lattitude" value="${poleTypeBean.latitude}"/>
                                                    </td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">
                                                        <input type="button" value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('longitude${loopCounter.count}' , 'lattitude${loopCounter.count}');"/>
                                                    </td>

                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.area_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.road_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.traffic_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.ward_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.city}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.road_category}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.road_use}</td>

                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none">
                                                        <input type="button"  id="map_id${loopCounter.count}" value="${poleTypeBean.map_id}" />
                                                    </td>

                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='right' colspan="" style="display: none"><input class="button" type="submit" name="task" id="updateClientPoleNo" value="Update Pole No's" ></td>
                                                <td align='center' colspan="15">
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
                                            <tr id="messagee">
                                                <c:if test="${not empty messagee}">
                                                    <td colspan="12" bgcolor="${msgBgColorr}"><b>Result: ${messagee}</b></td>
                                                </c:if>
                                            </tr>
                                            <!--- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" id="searchPoleType" name="searchPoleType" value="${searchPoleType}" >
                                            <input  type="hidden" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" >
                                            <input  type="hidden" id="searchSwitchingPoint" name="searchSwitchingPoint" value="${searchSwitchingPoint}" >
                                            <input  type="hidden" id="searchMountingType" name="searchMountingType" value="${searchMountingType}" >
                                        </table></DIV>
                                        <%--     <div id="map_container" title="Location Map"> --%>
                                        <%--      <div id="googleMap" style="width:600px;height:550px;"></div> --%>
                                        <%--   </div>   --%>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="poleDetailCont" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="7" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Feeder Name</th>
                                                <td>
                                                    <%--  <input class="input" type="hidden" id="pole_id" name="pole_id" value="$(searchPoleType)" >   ----%>
                                                    <input class="input" type="text" id="feeder_name" name="feeder_name" value="" size="20" disabled>
                                                </td>

                                                <th class="heading1">Switching Point name</th>
                                                <td>

                                                    <input class="input" type="text" id="switching_point_name" name="switching_point_name" value="" size="20" disabled>
                                                </td>
                                                <th class="heading1">Is Switch Point</th>
                                                <td><input type="checkbox"id="is_switch" name="pole_is_switch_point_name" value="" onclick="isSwitchPoint(id)"/></td>
                                                <td><input type="hidden"id="is_switch_point" name="pole_is_switch_point" value="No"/></td>
                                                    <%--    <th class="heading1">Pole Span</th>
                                                       <td><input class="input" type="text" id="pole_span" name="pole_span" value="" size="20" disabled></td>  --%>
                                            </tr>

                                            <tr>
                                                <th class="heading1">Pole Type</th>
                                                <td>
                                                    <input class="input" type="hidden" id="pole_id" name="pole_id" value="" />
                                                    <input class="input" type="text" id="pole_type" name="pole_type" value="" size="20" disabled/>
                                                </td>

                                                <th class="heading1">Pole No</th>
                                                <td>

                                                    <input class="input" type="text" id="pole_no" name="pole_no" value="" size="20" disabled/>
                                                </td>
                                                <th class="heading1" style="display: none">Pole No (Client)</th>
                                                <td style="display: none">

                                                    <input class="input" type="text" id="pole_no_client" name="pole_no_client" value="" size="20" disabled/>
                                                </td>


                                            </tr>
                                            <tr>
                                                <th class="heading1">Pole Span</th>
                                                <td><input class="input" type="text" id="pole_span" name="pole_span" value="" size="20" disabled></td>
                                                <th class="heading1">Pole Height</th>
                                                <td><input class="input" type="text" id="pole_height" name="pole_height" value="" size="20" disabled></td>

                                                <th class="heading1">Mounting Type</th>
                                                <td><input class="input" type="text" id="mounting_type" name="mounting_type" value="" size="20" disabled></td>


                                            </tr>
                                            <tr>
                                                <th class="heading1">Mounting Height</th>
                                                <td><input class="input" type="text" id="mounting_height" name="mounting_height" value="" size="20" disabled></td>
                                                <th class="heading1">GPS Code</th>
                                                <td><input class="input" type="text" id="gps_code" name="gps_code" value="" size="20" disabled></td>

                                                <th class="heading1">Max Avg Lux level</th>
                                                <td><input class="input" type="text" id="max_avg_lux_level" name="max_avg_lux_level" value="" size="20" disabled></td>


                                            </tr>
                                            <tr>
                                                <th class="heading1">Standard Lux level</th>
                                                <td><input class="input" type="text" id="standard_lux_level" name="standard_lux_level" value="" size="20" disabled></td>
                                                <th class="heading1">Min Avg Lux level</th>
                                                <td><input class="input" type="text" id="min_avg_lux_level" name="min_avg_lux_level" value="" size="20" disabled></td>

                                                <th class="heading1">Avg Lux level</th>
                                                <td><input class="input" type="text" id="avg_lux_level" name="avg_lux_level" value="" size="20" disabled></td>



                                            </tr>
                                            <tr>
                                                <th class="heading1">Is Working</th>
                                                <td><input class="input" type="text" id="is_working" name="is_working" value="" size="20" disabled></td>
                                                <th class="heading1">City</th>
                                                <td><input class="input" type="text" id="city" name="city" value="" size="20" disabled>
                                                </td>
                                                <th class="heading1">Ward No</th>
                                                <td><input class="input" type="text" id="ward_no" name="ward_no" value="" size="20" disabled>
                                                </td>

                                            </tr>
                                            <tr>
                                                <th class="heading1">Area Name</th>
                                                <td><input class="input" type="text" id="area_name" name="area_name" value="" size="20" disabled>
                                                </td>
                                                <th class="heading1">Road Category</th>
                                                <td>
                                                    <input class="input" type="text" id="road_category" name="road_category" value="" size="20" disabled>
                                                </td>
                                                <th class="heading1">Road Use</th>
                                                <td><input class="input" type="text" id="road_use" name="road_use" value="" size="20" disabled>
                                                </td>

                                            </tr>

                                            <tr>
                                                <th class="heading1">Road Name</th>
                                                <td><input class="input" type="text" id="road_name" name="road_name" value="" size="20" disabled></td>
                                                <th class="heading1">Traffic Type</th>
                                                <td>
                                                    <input class="input" type="text" id="traffic_type" name="traffic_type" value="" size="20" disabled>
                                                </td>
                                            </tr>

                                            <tr style="display:none">
                                                <th class="heading1">Created By</th>
                                                <td><input class="input" type="text" id="created_by" name="created_by" value="" size="20" disabled></td>
                                            </tr>
                                            <tr style="display:none">
                                                <th class="heading1">Created Date</th><td><input class="input" type="text" id="created_date" name="created_date" value="" size="20" disabled></td>
                                            </tr>


                                            <tr>



                                                <!--  <th class="heading1">Active</th>-->
                                                <td style="display:none">
                                                    <input class="input" type="text" id="active" name="active" value="" size="20" disabled>
                                                    <!--<select name="active" id="active" disabled>
                                                        <option>Y</option>
                                                        <option>N</option>
                                                    </select>-->
                                                </td>
                                                <td style="display:none">
                                                    <input class="input" type="text" id="pole_rev_no" name="pole_rev_no" value="" size="20" disabled>
                                                </td>

                                            </tr>
                                            <tr>
                                                <th class="heading1">Light Type</th>
                                                <td><input class="input" type="text" id="source_wattage" name="source_wattage" value="" size="20">
                                                    <input class="input" type="hidden" id="mapp_id" name="mapp_id" value="" size="20" readonly>
                                                    <input class="input" type="hidden" id="light_type_id" name="light_type_id" value="" size="20" readonly>
                                                </td>
                                                <th class="heading1">Quantity</th>
                                                <td><input class="input" type="text" id="quantity" name="quantity" value="" size="20"  onkeyup="IsPoleLightTpyeQuantity(id)"disabled></td>
                                                <td align='center'colspan="2">
                                                    <b class="heading1">Remark</b><input class="input" type="text" id="remark" name="remark" value="" size="20" disabled />
                                                    <input class="button" type="button" name="addMore" id="addMore" value="Add More" onclick="addRowaddRow()">
                                                </td>
                                            </tr>

                                            <c:forEach  begin="1" end="30" step="1" varStatus="loopCounter">
                                                <tr id="dynamic${loopCounter.count}" style="display:none">

                                                    <td><b class="heading1" id="sw${loopCounter.count}" >Light Type</b></td>
                                                    <td>  <input class="input" type="text" id="source_wattage${loopCounter.count}" name="source_wattage" value="" size="20">
                                                        <input class="input" type="hidden" id="mapp_id${loopCounter.count}" name="mapp_id" value="" size="20" readonly>
                                                        <input class="input" type="hidden" id="light_type_id${loopCounter.count}" name="light_type_id" value="" size="20" readonly>
                                                    </td>

                                                    <td><b class="heading1" id ="q${loopCounter.count}" >Quantity</b></td>
                                                    <td> <input class="input" type="text" id="quantity${loopCounter.count}" name="quantity" value="" size="20" onkeyup="IsPoleLightTpyeQuantity(id)">
                                                    </td>

                                                </tr>

                                            </c:forEach>  
                                        </table>
                                        <table>  <tr>
                                                <td align='center' colspan="6">
                                                    <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)"> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="delete" value="Cancel" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)"  disabled>
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchPoleType" value="${searchPoleType}" >
                                            <input type="hidden"  name="searchMountingType" value="${searchMountingType}" >
                                            <input  type="hidden" id="addRowCount" name="addRowCount" value="0" >
                                            <input  type="hidden" id="newRowCount" name="newRowCount" value="0" >
                                        </table>
                                        <div id="googleMap" style="width:600px;height:550px;"></div>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>

    </body>
</html>
