package metier;

import bdd.Bddobject;
import model.Composant;
import model.Produitfinie;

public class ProduitFinie {
    Produitfinie[] produitFinies;
    String[] productName;

    public Produitfinie[] getProduitFinies() throws Exception {
        return Produitfinie.convert(new Produitfinie().getData(Bddobject.toConPostgres(), null));
    }
    public Produitfinie[] getProduitFiniesReport(String idproduit) throws Exception {
        Produitfinie produitfinie = new Produitfinie();
        produitfinie.setIdproduit(idproduit);
        return Produitfinie.convert(produitfinie.getData(Bddobject.toConPostgres(), null, "idproduit"));
    }

    public String[] getProductName() throws Exception {
        String[] name = new String[getProduitFinies().length];
        for (int i = 0; i < name.length; i++) {
            Composant cp = new Composant();
            cp.setIdcomposant(getProduitFinies()[i].getIdproduit());
            name[i] = Composant.convert(cp.getData(Bddobject.toConPostgres(),null,"idcomposant"))[0].getNom();
        }
        return name;
    }
}
