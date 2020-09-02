package com.rewin.swhysc.bean.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "SYWGJG")
public class OpenDept {
    @Column(name = "OPEN_DEPT")
    private String openDept;

    @Column(name = "DESCCR")
    private String desccr;

    /**
     * @return OPEN_DEPT
     */
    public String getOpenDept() {
        return openDept;
    }

    /**
     * @param openDept
     */
    public void setOpenDept(String openDept) {
        this.openDept = openDept == null ? null : openDept.trim();
    }

    /**
     * @return DESCCR
     */
    public String getDesccr() {
        return desccr;
    }

    /**
     * @param desccr
     */
    public void setDesccr(String desccr) {
        this.desccr = desccr == null ? null : desccr.trim();
    }
}