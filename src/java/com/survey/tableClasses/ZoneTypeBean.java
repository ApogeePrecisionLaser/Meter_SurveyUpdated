/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author Administrator
 */
public class ZoneTypeBean {
private  int zone_id_m;
private String zone_m;
private  int division_id_m;
private String description;
private String division_name_n;

    public String getDivision_name_n() {
        return division_name_n;
    }

    public void setDivision_name_n(String division_name_n) {
        this.division_name_n = division_name_n;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDivision_id_m() {
        return division_id_m;
    }

    public void setDivision_id_m(int division_id_m) {
        this.division_id_m = division_id_m;
    }

    public int getZone_id_m() {
        return zone_id_m;
    }

    public void setZone_id_m(int zone_id_m) {
        this.zone_id_m = zone_id_m;
    }

    public String getZone_m() {
        return zone_m;
    }

    public void setZone_m(String zone_m) {
        this.zone_m = zone_m;
    }


}
