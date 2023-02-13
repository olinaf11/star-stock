package bdd;

import java.sql.Date;

public class Historique extends Bddobject {
    String idHist;
    String tabName;
    String action;
    Date dateHist;
    String value;
    public String getIdHist() {
        return idHist;
    }
    public String getTabName() {
        return tabName;
    }
    public String getAction() {
        return action;
    }
    public Date getDateHist() {
        return dateHist;
    }
    public String getValue() {
        return value;
    }
    public void setIdHist(String idHist) {
        this.idHist = idHist;
    }public void setTabName(String tabName) {
        this.tabName = tabName;
    }public void setAction(String action) {
        this.action = action;
    }public void setDateHist(Date date) {
        this.dateHist = date;
    }public void setValue(String value) {
        this.value = value;
    }
    public void pk() {
        super.setPrefix("HIS");
        super.setLenPk(7);
        super.setSeqName("histSeq");
        super.setTable("historique");
    }
    public Historique() {
        pk();
    }
}