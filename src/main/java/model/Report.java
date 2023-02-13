package model;

import bdd.Bddobject;

import java.util.Date;

public class Report extends Bddobject {
    String id;
    Date date;
    String idmagasin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setDate(String date){
        if(date==null || date==""){
            setDate(new java.sql.Date(System.currentTimeMillis()));
        }else {
            setDate(java.sql.Date.valueOf(date));
        }
    }

    public String getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(String idmagasin) throws Exception {
        Magasin magasin = new Magasin();
        if (!idmagasin.contains(magasin.getPrefix()) || idmagasin.length() != magasin.getLenPk())
            throw new Exception("idmagasin is invalid");
        this.idmagasin = idmagasin;
    }

    public Report(){
        super.setPrefix("RPT");
        super.setLenPk(7);
        super.setSeqName("reportseq");
        super.setTable("report");
        super.setPrimaryKey("id");
    }
    public Report(Date date, String idmagasin) throws Exception {
        this();
        setId(constructPK(null));
        setDate(date);
        setIdmagasin(idmagasin);
    }
    public Report(String date, String idmagasin) throws Exception {
        this();
        setId(constructPK(null));
        setDate(date);
        setIdmagasin(idmagasin);
    }
    public static Report[] convert(Object[] objects){
        Report[] reports = new Report[objects.length];
        for (int i = 0; i < reports.length; i++) {
            reports[i] = (Report) objects[i];
        }
        return reports;
    }
}
