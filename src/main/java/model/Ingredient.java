package model;

import bdd.Bddobject;

public class Ingredient extends Bddobject {
    String idingredient;
    String produit;
    String ingredient;
    double quantIngredient;
    double quantProd;

    public String getIdingredient() {
        return idingredient;
    }
    public String getProduit() {
        return produit;
    }
    public String getIngredient() {
        return ingredient;
    }
    public double getQuantIngredient() {
        return quantIngredient;
    }
    public double getQuantProd() {
        return quantProd;
    }
    public void setIdingredient(String idingredient) throws Exception {
        if (!idingredient.contains(this.getPrefix()) || idingredient.length() != this.getLenPk())
            throw new Exception("IdIngredient is invalid");
        this.idingredient = idingredient;
    }
    public void setProduit(String produit) throws Exception {
        Composant composant = new Composant();
        if (!produit.contains(composant.getPrefix()) || produit.length() != composant.getLenPk())
            throw new Exception("Idcomp is invalid");
        this.produit = produit;
    }
    public void setIngredient(String ingredient) throws Exception {
        Composant composant = new Composant();
        if (!ingredient.contains(composant.getPrefix()) || ingredient.length() != composant.getLenPk())
            throw new Exception("Idcomp is invalid");
        this.ingredient = ingredient;
    }
    public void setQuantIngredient(double quantIngredient) {
        this.quantIngredient = quantIngredient;
    }
    public void setQuantProd(double quantProd) {
        this.quantProd = quantProd;
    }
    public Ingredient(){
        super.setPrefix("ING");
        super.setLenPk(7);
        super.setSeqName("ingSeq");
        super.setTable("ingredient");
        super.setPrimaryKey("idingredient");
    }
    public Ingredient(String produit, String ingredient, double quantIngredient, double quantProd) throws Exception {
        this();
        setIdingredient(constructPK(Bddobject.toConPostgres()));
        setProduit(produit);
        setIngredient(ingredient);
        setQuantIngredient(quantIngredient);
        setQuantProd(quantProd);
    }
    public static Ingredient[] convert(Object[] objects) {
        Ingredient[] ingredients = new Ingredient[objects.length];
        for (int i = 0; i < ingredients.length; i++)
            ingredients[i] = (Ingredient) objects[i];
        return ingredients;
    }
}