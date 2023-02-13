package model;

import bdd.Bddobject;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Composant
 */
public class Composant extends Bddobject{
    String idcomposant;
    String nom;
    double prixUnitaire;
    int idunite;
    double quantIngredient;
    double quantProd;
    String produit;
    Composant[] composition;

    public String getIdcomposant() {
        return idcomposant;
    }
    public String getNom() {
        return nom;
    }
    public double getPrixUnitaire() {
        return prixUnitaire;
    }public int getIdunite() {
        return idunite;
    }
    public double getQuantIngredient() {
        return quantIngredient;
    }public double getQuantProd() {
        return quantProd;
    }public String getProduit() {
        return produit;
    }public Composant[] getComposition() {
        return composition;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    public void setIdcomposant(String idcomposant) throws Exception {
        if (!idcomposant.contains(this.getPrefix()) || idcomposant.length() != this.getLenPk())
            throw new Exception("Idcomp is invalid");
        this.idcomposant = idcomposant;
    }
    public void setIdunite(int idunite) {
        this.idunite = idunite;
    }
    public void setQuantIngredient(double quantIngredient) {
        this.quantIngredient = quantIngredient;
    }public void setQuantProd(double quantProd) {
        this.quantProd = quantProd;
    }public void setProduit(String produit) throws Exception {
        if (!produit.contains(this.getPrefix()) || produit.length() != this.getLenPk())
            throw new Exception("produit is invalid");
        this.produit = produit;
    }
    public void setComposition(Composant[] composition) {
        this.composition = composition;
    }

    public Composant(){
        super.setPrefix("CMP");
        super.setLenPk(7);
        super.setSeqName("cmpSeq");
        super.setTable("composant");
        super.setPrimaryKey("idcomposant");
    } 
    public Composant(String nom, double prixUnitaire, int unite) throws Exception {
        this();
        setIdcomposant(constructPK(Bddobject.toConPostgres()));
        setNom(nom);
        setPrixUnitaire(prixUnitaire);
        setIdunite(unite);
    }
    public Composant(String idcomposant,String nom, double prixUnitaire, int unite) throws Exception {
        setIdcomposant(idcomposant);
        setNom(nom);
        setPrixUnitaire(prixUnitaire);
        setIdunite(unite);
    }
    public boolean isMatPremiere() throws Exception {
        Ingredient temp = new Ingredient();
        temp.setProduit(this.getIdcomposant());
        if (temp.getData(Bddobject.toConPostgres(), null, "produit").length == 0 || temp.getData(Bddobject.toConPostgres(), null, "produit") == null ) {
            return true;
        }
        return false;
    }
    public static Composant[] convert(Object[] objects) {
        Composant[] composants = new Composant[objects.length];
        for (int i = 0; i < composants.length; i++)
            composants[i] = (Composant) objects[i];
        return composants;
    }
    public void decomposition(String produit) throws Exception {
        if (isMatPremiere()) {
            return;
        }
        Composant comp = new Composant();
        comp.setTable("decomposition");
        comp.setProduit(produit);
        setComposition(Composant.convert(comp.getData(Bddobject.toConPostgres(), null, "produit")));
        for (int i = 0; i < getComposition().length; i++) {
            getComposition()[i].decomposition(getComposition()[i].getIdcomposant());
        }
    }
    public double prix() throws Exception{
        double val = 0;
        decomposition(getIdcomposant());
        if (isMatPremiere()) {
            val+=(getPrixUnitaire()*getQuantIngredient())/getQuantProd();
        }
        if (getComposition()==null) {
            return val;
        }
        for (int i = 0; i < getComposition().length; i++) {
            if (!getComposition()[i].isMatPremiere()) {
                val+=getComposition()[i].prix()*getComposition()[i].getQuantIngredient();
            }
            val+=(getComposition()[i].getPrixUnitaire()*getComposition()[i].getQuantIngredient())/getComposition()[i].getQuantProd();
        }
        return val;
    }
    public Composant[] getProdPrimary(double quantite) throws Exception {
        decomposition(getIdcomposant());
        ArrayList<Composant> matPrem = new ArrayList<>();
        for (int i = 0; i < getComposition().length; i++) {
            if (getComposition()[i].isMatPremiere()){
                getComposition()[i].setQuantIngredient(getComposition()[i].getQuantIngredient()*quantite);
                matPrem.add(getComposition()[i]);
            }else{
                matPrem.addAll(List.of(getComposition()[i].getProdPrimary(getComposition()[i].getQuantIngredient())));
            }
        }
        return convert(matPrem.toArray());
    }
    public double getPump() throws Exception {
        Stock[] stocks = Stock.convert(new Stock(idcomposant).getData(null, null, "idproduit"));
        double sum1 = 0.0;
        double sum2 = 0.0;
        for (Stock stock : stocks) {
            sum1 += stock.getQuantite() * stock.getPrixUnitaire();
            sum2 += stock.getQuantite();
        }
        return sum1/sum2;
    }
    public void build(Connection con,double quantite) throws Exception {
        Composant[] produits = getProdPrimary(quantite);
        Date date = new Date(System.currentTimeMillis());
        for (Composant comp: produits) {
            Stock stock = new Stock(comp.getIdcomposant(), comp.prixUnitaire, comp.getQuantIngredient()*quantite, false, date);
            stock.insertObj(con);
        }
        Stock stock = new Stock(getIdcomposant(), prix(), quantite, true, date);
        stock.insertObj(con);
    }
}