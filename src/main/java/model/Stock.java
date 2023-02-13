package model;

import bdd.Bddobject;

import java.sql.Connection;
import java.sql.Date;

public class Stock extends Bddobject {
    String id;
    String idproduit;
    double prixUnitaire;
    double quantite;
    boolean etat = false;
    Date datestock;
    String idcomposant;
    String nom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(String idproduit) {
        this.idproduit = idproduit;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public boolean getEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Date getDatestock() {
        return datestock;
    }

    public void setDatestock(Date datestock) {
        this.datestock = datestock;
    }

    public String getIdcomposant() {
        return idcomposant;
    }

    public void setIdcomposant(String idcomposant) {
        this.idcomposant = idcomposant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Stock(){
        super.setPrefix("STK");
        super.setLenPk(7);
        super.setSeqName("stkSeq");
        super.setTable("stock");
        super.setPrimaryKey("id");
    }
    public Stock(String idproduit){
        this();
        setIdproduit(idproduit);
    }
    public Stock(String idproduit, double prixUnitaire, double quantite, boolean etat, Date datestock) throws Exception {
        this();
        setId(constructPK(Bddobject.toConPostgres()));
        setIdproduit(idproduit);
        setPrixUnitaire(prixUnitaire);
        setQuantite(quantite);
        setEtat(etat);
        setDatestock(datestock);
    }
    public static Stock[] convert(Object[] objects){
        Stock[] stocks = new Stock[objects.length];
        for (int i = 0; i < stocks.length; i++) {
            stocks[i] = (Stock) objects[i];
        }
        return stocks;
    }

    public void insertObj(Connection con) {
    }
}
