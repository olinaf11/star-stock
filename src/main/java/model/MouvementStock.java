package model;

import bdd.Bddobject;

import java.sql.Date;

public class MouvementStock extends Bddobject {
    String id;
    String idproduit;
    Date date;
    double entree;
    double sortie;
    String idmagasin;

    public String getId() {
        return id;
    }
    public double getReste(){
        return getEntree()-getSortie();
    }
    public void setId(String id) throws Exception{
        if (!id.contains(this.getPrefix()) || id.length() != this.getLenPk())
            throw new Exception("Id is invalid");
        this.id = id;
    }

    public String getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(String idproduit) throws Exception {
        Composant cmp = new Composant();
        if (!idproduit.contains(cmp.getPrefix()) || idproduit.length() != cmp.getLenPk())
            throw new Exception("idproduit is invalid");
        this.idproduit = idproduit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setDate(String date){
        if(date==null || date==""){
            setDate(new Date(System.currentTimeMillis()));
        }else {
            setDate(Date.valueOf(date));
        }
    }

    public double getEntree() {
        return entree;
    }

    public void setEntree(double entree) {
        if (entree<0) entree=Math.abs(entree);
        this.entree = entree;
    }
    public void setEntree(String entree) throws Exception {
        if (entree==null || entree==""){
            setEntree(0);
        }else {
            setEntree(Double.parseDouble(entree));
        }
    }

    public double getSortie() {
        return sortie;
    }

    public void setSortie(double sortie) throws Exception {
        if (sortie<0) sortie=Math.abs(sortie);
        this.sortie = sortie;
    }
    public void setSortie(String sortie) throws Exception {
        if (sortie==null || sortie==""){
            setSortie(0);
        }else {
            setSortie(Double.parseDouble(sortie));
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
    public MouvementStock(){
        super.setPrefix("ETA");
        super.setLenPk(7);
        super.setSeqName("etatseq");
        super.setTable("mvtstock");
        super.setPrimaryKey("id");
    }
    public MouvementStock(String idproduit, Date date, double entree, double sortie, String idmagasin) throws Exception {
        this();
        setId(constructPK(null));
        setIdproduit(idproduit);
        setDate(date);
        setEntree(entree);
        setSortie(sortie);
        setIdmagasin(idmagasin);
    }
    public MouvementStock(String idproduit, String date, String entree, String sortie, String idmagasin) throws Exception {
        this();
        setIdproduit(idproduit);
        setDate(date);
        setEntree(entree);
        setSortie(sortie);
        setIdmagasin(idmagasin);
        setId(constructPK(null));
    }
    public static MouvementStock[] convert(Object[] objects){
        MouvementStock[] etats = new MouvementStock[objects.length];
        for (int i = 0; i < etats.length; i++) {
            etats[i] = (MouvementStock) objects[i];
        }
        return etats;
    }
}
