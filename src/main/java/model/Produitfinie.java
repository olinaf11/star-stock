package model;

import bdd.Bddobject;

public class Produitfinie extends Bddobject{
    String idproduitfinie;
    String idproduit;

    public void setIdproduit(String idproduit) throws Exception {
        Composant cmp = new Composant();
        if (!idproduit.contains(cmp.getPrefix()) || idproduit.length() != cmp.getLenPk())
            throw new Exception("Idproduit is invalid");
        this.idproduit = idproduit;
    }
    public void setIdproduitfinie(String idproduitfinie) throws Exception {
        if (!idproduitfinie.contains(this.getPrefix()) || idproduitfinie.length() != this.getLenPk())
            throw new Exception("Idproduitfinie is invalid");
        this.idproduitfinie = idproduitfinie;
    }
    public String getIdproduit() {
        return idproduit;
    }
    public String getIdproduitfinie() {
        return idproduitfinie;
    }

    public Produitfinie() {
        super.setPrefix("PRD");
        super.setLenPk(7);
        super.setSeqName("prdSeq");
        super.setTable("produitfinie");
        super.setPrimaryKey("idproduitfinie");
    }
    public Produitfinie(String idproduit) throws Exception {
        this();
        setIdproduitfinie(constructPK(Bddobject.toConPostgres()));
        setIdproduit(idproduit);
    }
    public static Produitfinie[] convert(Object[] objects) {
        Produitfinie[] produitfinies = new Produitfinie[objects.length];
        for (int i = 0; i < produitfinies.length; i++)
            produitfinies[i] = (Produitfinie) objects[i];
        return produitfinies;
    }
}
