package com.survey.tableClasses;

public class Meters {

    private int meter_id;
    private String meter_name;
    private int tarrif_gen_details_id;
    private String load_unit;
    private double security_deposit;
    private String sd_receipt_no;
    private String date;
    private double initial_reading;
    private double sanctioned_load_kw;
    private Integer location_id;
    private int mpeb_je_id;
    private String address1;
    private String address2;
    private String address3;
    private String city_name, zone, feeder;
    private int city_id;
    private String je_ddress1;
    private String je_ddress2;
    private String je_ddress3;
    private String meter_service_num;
    private String poll_num;
    private String active;
    private int organisation_id;
    private String organisation;
    private int org_office_id;
    private int site_id;
    private String site_name;
    private String org_office;
    private int type_of_premises_id;
    private String type_of_premises;
    private String switching_point;
    private int switching_point_id;

    private String switching_offcie_name;

    private String image_path;


    private int feeder_id;
    private String feeder_name;
    private String office_type;
    private int revision;
    private int pre_meter_id;
    private String reason;
    private String phase;
    private String location_name;
    private String junior_engineer;
    private double accessed_load_kw;
    private String effective_date;
    private String division_name;
    private Double calculated_load;
    private String description;
    private String tariff_code;
    private String ivrs_no;
    private String file_no;
    private String msn_part1;
    private String msn_part2;
    private String msn_part3;
    private String msn_part4;
    private String premises_individual_detail;
    public String meter_name_auto;
    public String latitude;
    public String longitude;
    public String ward_no;
    private String company_name;
    private String circle_name;
    private String address_asper_Bill;
    private double bill_sanction_load;

    Double bill_amount=0.0;
    Double mpeb_bill_amount=0.0;
    Double penalty=0.0;
    Double sanction_amount=0.0;
    Double payment_amount=0.0;
    Double meter_consumed_unit=0.0;
    Double prev_consumed_unit=0.0;
    Double column_amt=0.0;
    Double req_amt=0.0;
    Double amount_adjusted=0.0;
    Double net_amount=0.0;

    String billMonth="";
    String paymentStatus="";
    String paymentReqNo="";
    String month="";
    String year="";
    String req_no="";
    String remark="";
    String status_req="";

    String[] statusList;
    String[] reqList;
    String[] msnList;
    String[] isUpdateList;
    String[] reasonReqList;
    String[] req_monthList;
    String[] req_yearList;
    String[] remarkList;
    String[] amountAdjustedList;
    private int premises_tariff_map_id;
    private int premises_tariff_map_rev;

    public double getBill_sanction_load() {
        return bill_sanction_load;
    }

    public void setBill_sanction_load(double bill_sanction_load) {
        this.bill_sanction_load = bill_sanction_load;
    }

    public String getAddress_asper_Bill() {
        return address_asper_Bill;
    }

    public void setAddress_asper_Bill(String address_asper_Bill) {
        this.address_asper_Bill = address_asper_Bill;
    }

        public int getPremises_tariff_map_id() {
        return premises_tariff_map_id;
    }

    public void setPremises_tariff_map_id(int premises_tariff_map_id) {
        this.premises_tariff_map_id = premises_tariff_map_id;
    }

    public String getStatus_req() {
        return status_req;
    }

    public void setStatus_req(String status_req) {
        this.status_req = status_req;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReq_no() {
        return req_no;
    }

    public void setReq_no(String req_no) {
        this.req_no = req_no;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String[] getAmountAdjustedList() {
        return amountAdjustedList;
    }

    public void setAmountAdjustedList(String[] amountAdjustedList) {
        this.amountAdjustedList = amountAdjustedList;
    }

    public Long getAmount_adjusted() {
        return amount_adjusted.longValue();
    }

    public void setAmount_adjusted(Double amount_adjusted) {
        this.amount_adjusted = amount_adjusted;
    }

    public Long getNet_amount() {
        return net_amount.longValue();
    }

    public void setNet_amount(Double net_amount) {
        this.net_amount = net_amount;
    }

    public String[] getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(String[] remarkList) {
        this.remarkList = remarkList;
    }

    public String[] getReasonReqList() {
        return reasonReqList;
    }

    public void setReasonReqList(String[] reasonReqList) {
        this.reasonReqList = reasonReqList;
    }

    public String[] getReq_monthList() {
        return req_monthList;
    }

    public void setReq_monthList(String[] req_monthList) {
        this.req_monthList = req_monthList;
    }

    public String[] getReq_yearList() {
        return req_yearList;
    }

    public void setReq_yearList(String[] req_yearList) {
        this.req_yearList = req_yearList;
    }

    public String[] getIsUpdateList() {
        return isUpdateList;
    }

    public void setIsUpdateList(String[] isUpdateList) {
        this.isUpdateList = isUpdateList;
    }

    public String[] getMsnList() {
        return msnList;
    }

    public void setMsnList(String[] msnList) {
        this.msnList = msnList;
    }

    public String[] getReqList() {
        return reqList;
    }

    public void setReqList(String[] reqList) {
        this.reqList = reqList;
    }

    public String[] getStatusList() {
        return statusList;
    }

    public void setStatusList(String[] statusList) {
        this.statusList = statusList;
    }

    public String getPaymentReqNo() {
        return paymentReqNo;
    }

    public void setPaymentReqNo(String paymentReqNo) {
        this.paymentReqNo = paymentReqNo;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getMpeb_bill_amount() {
        return mpeb_bill_amount.longValue();
    }

    public void setMpeb_bill_amount(Double mpeb_bill_amount) {
        this.mpeb_bill_amount = mpeb_bill_amount;
    }

    public Double getReq_amt() {
        return req_amt;
    }

    public void setReq_amt(Double req_amt) {
        this.req_amt = req_amt;
    }

    public Long getColumn_amt() {
        return column_amt.longValue();
    }

    public void setColumn_amt(Double column_amt) {
        this.column_amt = column_amt;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public Double getPrev_consumed_unit() {
        return prev_consumed_unit;
    }

    public void setPrev_consumed_unit(Double prev_consumed_unit) {
        this.prev_consumed_unit = prev_consumed_unit;
    }

    public Double getMeter_consumed_unit() {
        return meter_consumed_unit;
    }

    public void setMeter_consumed_unit(Double meter_consumed_unit) {
        this.meter_consumed_unit = meter_consumed_unit;
    }

    public Double getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(Double bill_amount) {
        this.bill_amount = bill_amount;
    }

    public Long getPayment_amount() {
        return payment_amount.longValue();
    }

    public void setPayment_amount(Double payment_amount) {
        this.payment_amount = payment_amount;
    }

    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }

    public Double getSanction_amount() {
        return sanction_amount;
    }

    public void setSanction_amount(Double sanction_amount) {
        this.sanction_amount = sanction_amount;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public String getWard_no() {
        return ward_no;
    }

    public void setWard_no(String ward_no) {
        this.ward_no = ward_no;
    }


    public String getMeter_name_auto() {
        return meter_name_auto;
    }

    public void setMeter_name_auto(String meter_name_auto) {
        this.meter_name_auto = meter_name_auto;
    }

    public String getPremises_individual_detail() {
        return premises_individual_detail;
    }

    public void setPremises_individual_detail(String premises_individual_detail) {
        this.premises_individual_detail = premises_individual_detail;
    }


    public String getJunior_engineer() {
        return junior_engineer;
    }

    public void setJunior_engineer(String junior_engineer) {
        this.junior_engineer = junior_engineer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getInitial_reading() {
        return initial_reading;
    }

    public void setInitial_reading(double initial_reading) {
        this.initial_reading = initial_reading;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public int getMeter_id() {
        return meter_id;
    }

    public void setMeter_id(int meter_id) {
        this.meter_id = meter_id;
    }

    public String getMeter_name() {
        return meter_name;
    }

    public void setMeter_name(String meter_name) {
        this.meter_name = meter_name;
    }

    public int getMpeb_je_id() {
        return mpeb_je_id;
    }

    public void setMpeb_je_id(int mpeb_je_id) {
        this.mpeb_je_id = mpeb_je_id;
    }

    public double getSanctioned_load_kw() {
        return sanctioned_load_kw;
    }

    public void setSanctioned_load_kw(double sanctioned_load_kw) {
        this.sanctioned_load_kw = sanctioned_load_kw;
    }

    public String getSd_receipt_no() {
        return sd_receipt_no;
    }

    public void setSd_receipt_no(String sd_receipt_no) {
        this.sd_receipt_no = sd_receipt_no;
    }

    public double getSecurity_deposit() {
        return security_deposit;
    }

    public void setSecurity_deposit(double security_deposit) {
        this.security_deposit = security_deposit;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getJe_ddress1() {
        return je_ddress1;
    }

    public void setJe_ddress1(String je_ddress1) {
        this.je_ddress1 = je_ddress1;
    }

    public String getJe_ddress2() {
        return je_ddress2;
    }

    public void setJe_ddress2(String je_ddress2) {
        this.je_ddress2 = je_ddress2;
    }

    public String getJe_ddress3() {
        return je_ddress3;
    }

    public void setJe_ddress3(String je_ddress3) {
        this.je_ddress3 = je_ddress3;
    }

    public String getPoll_num() {
        return poll_num;
    }

    public void setPoll_num(String poll_num) {
        this.poll_num = poll_num;
    }

    public String getMeter_service_num() {
        return meter_service_num;
    }

    public void setMeter_service_num(String meter_service_num) {
        this.meter_service_num = meter_service_num;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public int getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(int organisation_id) {
        this.organisation_id = organisation_id;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String Site_name) {
        this.site_name = Site_name;
    }

    public String getOrg_office() {
        return org_office;
    }

    public void setOrg_office(String org_office) {
        this.org_office = org_office;
    }

    public int getOrg_office_id() {
        return org_office_id;
    }

    public void setOrg_office_id(int org_office_id) {
        this.org_office_id = org_office_id;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

    public String getType_of_premises() {
        return type_of_premises;
    }

    public void setType_of_premises(String type_of_premises) {
        this.type_of_premises = type_of_premises;
    }

    public int getType_of_premises_id() {
        return type_of_premises_id;
    }

    public void setType_of_premises_id(int type_of_premises_id) {
        this.type_of_premises_id = type_of_premises_id;
    }

    public String getSwitching_point() {
        return switching_point;
    }

    public void setSwitching_point(String switching_point) {
        this.switching_point = switching_point;
    }

    public int getSwitching_point_id() {
        return switching_point_id;
    }

    public void setSwitching_point_id(int switching_point_id) {
        this.switching_point_id = switching_point_id;
    }

    public int getFeeder_id() {
        return feeder_id;
    }

    public void setFeeder_id(int feeder_id) {
        this.feeder_id = feeder_id;
    }

    public String getFeeder_name() {
        return feeder_name;
    }

    public void setFeeder_name(String feeder_name) {
        this.feeder_name = feeder_name;
    }

    public String getOffice_type() {
        return office_type;
    }

    public void setOffice_type(String office_type) {
        this.office_type = office_type;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public int getPre_meter_id() {
        return pre_meter_id;
    }

    public void setPre_meter_id(int pre_meter_id) {
        this.pre_meter_id = pre_meter_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getFeeder() {
        return feeder;
    }

    public void setFeeder(String feeder) {
        this.feeder = feeder;
    }

    public double getAccessed_load_kw() {
        return accessed_load_kw;
    }

    public void setAccessed_load_kw(double accessed_load_kw) {
        this.accessed_load_kw = accessed_load_kw;
    }

    public String getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(String effective_date) {
        this.effective_date = effective_date;
    }

    public String getDivision_name() {
        return division_name;
    }

    public void setDivision_name(String division_name) {
        this.division_name = division_name;
    }

    public Double getCalculated_load() {
        return calculated_load;
    }

    public void setCalculated_load(Double calculated_load) {
        this.calculated_load = calculated_load;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTariff_code() {
        return tariff_code;
    }

    public void setTariff_code(String tariff_code) {
        this.tariff_code = tariff_code;
    }

    public String getFile_no() {
        return file_no;
    }

    public void setFile_no(String file_no) {
        this.file_no = file_no;
    }

    public String getIvrs_no() {
        return ivrs_no;
    }

    public void setIvrs_no(String ivrs_no) {
        this.ivrs_no = ivrs_no;
    }

    public String getMsn_part1() {
        return msn_part1;
    }

    public void setMsn_part1(String msn_part1) {
        this.msn_part1 = msn_part1;
    }

    public String getMsn_part2() {
        return msn_part2;
    }

    public void setMsn_part2(String msn_part2) {
        this.msn_part2 = msn_part2;
    }

    public String getMsn_part3() {
        return msn_part3;
    }

    public void setMsn_part3(String msn_part3) {
        this.msn_part3 = msn_part3;
    }

    public String getMsn_part4() {
        return msn_part4;
    }

    public void setMsn_part4(String msn_part4) {
        this.msn_part4 = msn_part4;
    }

    public int getTarrif_gen_details_id() {
        return tarrif_gen_details_id;
    }

    public void setTarrif_gen_details_id(int tarrif_gen_details_id) {
        this.tarrif_gen_details_id = tarrif_gen_details_id;
    }

    public String getLoad_unit() {
        return load_unit;
    }

    public void setLoad_unit(String load_unit) {
        this.load_unit = load_unit;
    }

    /**
     * @return the company_name
     */
    public String getCompany_name() {
        return company_name;
    }

    /**
     * @param company_name the company_name to set
     */
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    /**
     * @return the circle_name
     */
    public String getCircle_name() {
        return circle_name;
    }

    /**
     * @param circle_name the circle_name to set
     */
    public void setCircle_name(String circle_name) {
        this.circle_name = circle_name;
    }

    /**
     * @return the premises_tariff_map_rev
     */
    public int getPremises_tariff_map_rev() {
        return premises_tariff_map_rev;
    }

    /**
     * @param premises_tariff_map_rev the premises_tariff_map_rev to set
     */
    public void setPremises_tariff_map_rev(int premises_tariff_map_rev) {
        this.premises_tariff_map_rev = premises_tariff_map_rev;
    }

    /**
     * @return the switching_offcie_name
     */
    public String getSwitching_offcie_name() {
        return switching_offcie_name;
    }

    /**
     * @param switching_offcie_name the switching_offcie_name to set
     */
    public void setSwitching_offcie_name(String switching_offcie_name) {
        this.switching_offcie_name = switching_offcie_name;
    }

    /**
     * @return the image_path
     */
    public String getImage_path() {
        return image_path;
    }

    /**
     * @param image_path the image_path to set
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }



}
