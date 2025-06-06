package lk.ijse.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;

public class RegistrationDTO {
    //private String rid;
    private String sid;
    private String pid;
    private double upfrontpayment;
    private LocalDate date;

    public RegistrationDTO() {
    }

    public RegistrationDTO(/*String rid,*/ String sid, String pid, double upfrontpayment, LocalDate date) {
        //this.rid = rid;
        this.sid = sid;
        this.pid = pid;
        this.upfrontpayment = upfrontpayment;
        this.date = date;
    }

   /* public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }*/

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public double getUpfrontpayment() {
        return upfrontpayment;
    }

    public void setUpfrontpayment(double upfrontpayment) {
        this.upfrontpayment = upfrontpayment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
