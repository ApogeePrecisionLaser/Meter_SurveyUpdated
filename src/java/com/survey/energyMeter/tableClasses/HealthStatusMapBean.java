/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.tableClasses;


/**
 *
 * @author jpss
 */
public class HealthStatusMapBean {
    private int health_status_map_id;
    private int health_status_id;
    private int switching_point_detail_id;
    private int switching_rev_no;
    private String switching_point_name;
    private String reading_date;
    private String reading_time;
    private String health_status;
    private String phase1_status;
    private String phase2_status;
    private String phase3_status;
    private String phase1_vc_status;
    private String phase2_vc_status;
    private String phase3_vc_status;
    private int phase;
    private int phase1_status_id;
    private int phase2_status_id;
    private int phase3_status_id;
    private int phase1_vc_status_id;
    private int phase2_vc_status_id;
    private int phase3_vc_status_id;
    private PhaseBean phase1Bean;
    private PhaseBean phase2Bean;
    private PhaseBean phase3Bean;
    private String contactor_status;
    private String contactor_command;

    public void setContactor_command(String contactor_command) {
        this.contactor_command = contactor_command;
    }

    public void setContactor_status(String contactor_status) {
        this.contactor_status = contactor_status;
    }

    public String getContactor_command() {
        return contactor_command;
    }

    public String getContactor_status() {
        return contactor_status;
    }

    public void setPhase1Bean(PhaseBean phase1Bean) {
        this.phase1Bean = phase1Bean;
    }

    public void setPhase2Bean(PhaseBean phase2Bean) {
        this.phase2Bean = phase2Bean;
    }

    public void setPhase3Bean(PhaseBean phase3Bean) {
        this.phase3Bean = phase3Bean;
    }

    public PhaseBean getPhase1Bean() {
        return phase1Bean;
    }

    public PhaseBean getPhase2Bean() {
        return phase2Bean;
    }

    public PhaseBean getPhase3Bean() {
        return phase3Bean;
    }

    public String getPhase1_status() {
        return phase1_status;
    }

    public void setPhase1_status(String phase1_status) {
        this.phase1_status = phase1_status;
    }

    public String getPhase1_vc_status() {
        return phase1_vc_status;
    }

    public void setPhase1_vc_status(String phase1_vc_status) {
        this.phase1_vc_status = phase1_vc_status;
    }

    public String getPhase2_status() {
        return phase2_status;
    }

    public void setPhase2_status(String phase2_status) {
        this.phase2_status = phase2_status;
    }

    public String getPhase2_vc_status() {
        return phase2_vc_status;
    }

    public void setPhase2_vc_status(String phase2_vc_status) {
        this.phase2_vc_status = phase2_vc_status;
    }

    public String getPhase3_status() {
        return phase3_status;
    }

    public void setPhase3_status(String phase3_status) {
        this.phase3_status = phase3_status;
    }

    public String getPhase3_vc_status() {
        return phase3_vc_status;
    }

    public void setPhase3_vc_status(String phase3_vc_status) {
        this.phase3_vc_status = phase3_vc_status;
    }

    public int getPhase1_vc_status_id() {
        return phase1_vc_status_id;
    }

    public int getPhase2_vc_status_id() {
        return phase2_vc_status_id;
    }

    public int getPhase3_vc_status_id() {
        return phase3_vc_status_id;
    }

    public void setPhase1_vc_status_id(int phase1_vc_status_id) {
        this.phase1_vc_status_id = phase1_vc_status_id;
    }

    public void setPhase2_vc_status_id(int phase2_vc_status_id) {
        this.phase2_vc_status_id = phase2_vc_status_id;
    }

    public void setPhase3_vc_status_id(int phase3_vc_status_id) {
        this.phase3_vc_status_id = phase3_vc_status_id;
    }




    public void setHealth_status(String health_status) {
        this.health_status = health_status;
    }

    public void setHealth_status_id(int health_status_id) {
        this.health_status_id = health_status_id;
    }

    public void setHealth_status_map_id(int health_status_map_id) {
        this.health_status_map_id = health_status_map_id;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setPhase1_status_id(int phase1_status_id) {
        this.phase1_status_id = phase1_status_id;
    }

    public void setPhase2_status_id(int phase2_status_id) {
        this.phase2_status_id = phase2_status_id;
    }

    public void setPhase3_status_id(int phase3_status_id) {
        this.phase3_status_id = phase3_status_id;
    }

    public void setReading_date(String reading_date) {
        this.reading_date = reading_date;
    }

    public void setReading_time(String reading_time) {
        this.reading_time = reading_time;
    }

    public void setSwitching_point_name(String switching_point_name) {
        this.switching_point_name = switching_point_name;
    }

    public void setSwitching_point_detail_id(int switching_point_detail_id) {
        this.switching_point_detail_id = switching_point_detail_id;
    }

    public void setSwitching_rev_no(int switching_rev_no) {
        this.switching_rev_no = switching_rev_no;
    }


    public String getHealth_status() {
        return health_status;
    }

    public int getHealth_status_id() {
        return health_status_id;
    }

    public int getHealth_status_map_id() {
        return health_status_map_id;
    }

    public int getPhase() {
        return phase;
    }

    public int getPhase1_status_id() {
        return phase1_status_id;
    }

    public int getPhase2_status_id() {
        return phase2_status_id;
    }

    public int getPhase3_status_id() {
        return phase3_status_id;
    }

    public String getReading_date() {
        return reading_date;
    }

    public String getReading_time() {
        return reading_time;
    }

    public String getSwitching_point_name() {
        return switching_point_name;
    }

    public int getSwitching_point_detail_id() {
        return switching_point_detail_id;
    }

    public int getSwitching_rev_no() {
        return switching_rev_no;
    }


}
