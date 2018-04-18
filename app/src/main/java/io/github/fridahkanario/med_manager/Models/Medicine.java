package io.github.fridahkanario.med_manager.Models;

/**
 * Created by gwaza on 4/3/2018.
 */

public class Medicine {
    private String medName;
    private String medDesc;
    private String medPrescription;
    private String startDate;

    public Medicine() {
    }

    public Medicine(String medName, String medDesc, String medPrescription, String startDate) {
        this.medName = medName;
        this.medDesc = medDesc;
        this.medPrescription = medPrescription;
        this.startDate = startDate;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedDesc() {
        return medDesc;
    }

    public void setMedDesc(String medDesc) {
        this.medDesc = medDesc;
    }

    public String getMedPrescription() {
        return medPrescription;
    }

    public void setMedPrescription(String medPrescription) {
        this.medPrescription = medPrescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
