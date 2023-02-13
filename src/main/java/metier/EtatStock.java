package metier;

import bdd.Bddobject;

import java.util.Date;

public class EtatStock extends Bddobject{
    String idproduit;
    double entree;
    double sortie;
    String idmagasin;
    Date date;
    public String getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(String idproduit) {
        this.idproduit = idproduit;
    }
    public double getEntree() {
        return entree;
    }
    public double getReportEntree(double reportQuantite){
        return getEntree()+reportQuantite;
    }
    public void setEntree(double entree) {
        this.entree = entree;
    }

    public double getSortie() {
        return sortie;
    }

    public void setSortie(double sortie) {
        this.sortie = sortie;
    }

    public String getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(String idmagasin) {
        this.idmagasin = idmagasin;
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
    public EtatStock(){
        super.setTable("etatstock");
    }
    public double getReste(){
        return getEntree()-getSortie();
    }
    public double getReportReste(double qut){
        return  getReportEntree(qut)-getSortie();
    }
    public static EtatStock[] convert(Object[] objects){
        EtatStock[] mouvementStocks = new EtatStock[objects.length];
        for (int i = 0; i < mouvementStocks.length; i++) {
            mouvementStocks[i] = (EtatStock) objects[i];
        }
        return mouvementStocks;
    }
}
