package model;

import bdd.Bddobject;

import java.sql.Date;

public class ReportProduit extends Bddobject {
    Integer id;
    String idreport;
    String idproduit;
    double quantite;
    Date date;
    String idmagasin;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdreport() {
        return idreport;
    }

    public void setIdreport(String idreport) {
        this.idreport = idreport;
    }

    public String getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(String idproduit) {
        this.idproduit = idproduit;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        if (quantite<0) quantite=Math.abs(quantite);
        this.quantite = quantite;
    }
    public void setQuantite(String quantite){
        if (quantite==null || quantite=="") setQuantite(0);
        else setQuantite(Double.parseDouble(quantite));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setDate(String date) {
        if (date==null || date==""){
            setDate(new Date(System.currentTimeMillis()));
        }else {
            setDate(Date.valueOf(date));
        }
    }

    public String getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(String idmagasin) {
        this.idmagasin = idmagasin;
    }

    public ReportProduit(){
        super.setTable("report_produit");
    }
    public ReportProduit(String quantite, String idproduit, String idreport){
        this();
        setQuantite(quantite);
        setIdproduit(idproduit);
        setIdreport(idreport);
    }
    public ReportProduit(String quantite, String idproduit, String idreport,String date, String idmagasin){
        super.setTable("reportfinal");
        setQuantite(quantite);
        setIdproduit(idproduit);
        setIdreport(idreport);
        setDate(date);
        setIdmagasin(idmagasin);
    }
    public static ReportProduit[] convert(Object[] objects){
        ReportProduit[] reportProduits = new ReportProduit[objects.length];
        for (int i = 0; i < reportProduits.length; i++) {
            reportProduits[i] = (ReportProduit) objects[i];
        }
        return reportProduits;
    }
}
