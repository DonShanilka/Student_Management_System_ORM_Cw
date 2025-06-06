package lk.ijse.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int rId;

    //private String sId;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)

    private Student student;

    //private String pId;
    @ManyToOne
    private Program program;
   private double upfrontpayment;

   @OneToOne
   private Payment payment;
   private LocalDate date;
//   private double totalPayment;

    public Registration() {

    }

    public Registration(/*int rId,*/ Student student, Program program, double upfrontpayment, Payment payment, LocalDate date /*,double totalPayment*/) {
        //this.rId = rId;
        this.student = student;
        this.program = program;
        this.upfrontpayment = upfrontpayment;
        this.payment = payment;
        this.date = date;
//        this.totalPayment = totalPayment;
    }

    public int getrId() {
        return rId;
    }


    public void setrId(int rId) {
        this.rId = rId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public double getUpfrontpayment() {
        return upfrontpayment;
    }

    public void setUpfrontpayment(double upfrontpayment) {
        this.upfrontpayment = upfrontpayment;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate  date) {
        this.date = date;
    }

    /* public Registration(String rId, String sId, String pId, double upfrontpayment, Date date, double totalPayment) {
        this.rId = rId;
        this.sId = sId;
        this.pId = pId;
        this.upfrontpayment = upfrontpayment;
        this.date = date;
        this.totalPayment = totalPayment;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public double getUpfrontpayment() {
        return upfrontpayment;
    }

    public void setUpfrontpayment(double upfrontpayment) {
        this.upfrontpayment = upfrontpayment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }*/
}
