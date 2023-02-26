package gui.tuitionmanagergui;

import java.text.DecimalFormat;

public class Student {
	private int creditHours;
    private Profile profile;
    private boolean fullTime;
    private double tuitionPaid;
    private Date lastPaymentDate;
    private double tuitionOwed;

    public static final int fullTimeStudent = 12;
    public static final int creditThreshold = 16;
    public static final double universityFee = 3268;
    public static final double partTimeUniversityFee = 3268 * 0.8;


    public Student(Profile profile, int creditHours) {
        this.profile = profile;
        this.creditHours = creditHours;
        if(creditHours < fullTimeStudent){
            this.fullTime = false;
        }
        else{
            this.fullTime = true;
        }
        this.tuitionPaid = 0;
        this.lastPaymentDate = null;
        this.tuitionOwed = 0;
    }
    public void tuitionDue() {
    }

    public void payTuition(double tuition, Date date) {
        if (lastPaymentDate == null || tuition <= tuitionOwed &&
                (lastPaymentDate.compareTo(date) <= 0)) {
            tuitionPaid += tuition;
            lastPaymentDate = date;

        }
    }
    public Profile getProfile() {
        return profile;
    }
    public boolean getStudentStatus() {
        return fullTime;
    }
    public int getCreditHours() {
        return creditHours;
    }
    public double getTuitionPaid() {
        return tuitionPaid;
    }
    public Date getDate() {
        return lastPaymentDate;
    }

    public void setTuitionOwed(double tuitionOwed){
        this.tuitionOwed = tuitionOwed;
    }

    public void setTuitionPaid(double tuitionPaid){
        this.tuitionPaid = tuitionPaid;
    }

    public void setCreditHours(int credits){
        this.creditHours = credits;
    }

    public void setPaymentDate(Date date){
        this.lastPaymentDate = date;
    }

    public double getTuitionOwed(){
        return tuitionOwed;
    }
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        String studentInfo = profile.toString();
        studentInfo += ":" + creditHours + " credit hours:tuition due:" +
                df.format(tuitionOwed) + ":" + "total payment:" +
                df.format(tuitionPaid) + ":last payment date:";
        if (lastPaymentDate == null) {
            studentInfo += " --/--/--";
        }
        else {
            studentInfo += lastPaymentDate.getMonth()
                        + "/" + lastPaymentDate.getDay()
                        + "/" + lastPaymentDate.getYear();
        }
        return studentInfo;
    }
}
