
import bdd.*;
import model.Composant;
import model.Magasin;
import model.Produitfinie;
import model.Stock;

public class App {
    public static void main(String[] args) throws Exception{
        // Composant cmp. = new Composant("limonade GM", 0, Unite.BOUTEILLE.getVal());
        // Ingredient ing. = new Ingredient("CMP0010", "CMP0017", 1, 1);
        Produitfinie[] produitfinies = Produitfinie.convert(new Produitfinie().getData(Bddobject.toConPostgres(), null));
//        TypeStock typeStock = new TypeStock("sortie");
        Composant comp = new Composant();
        Magasin magasin = new Magasin("Tsena2");
        Stock stock = new Stock("CMP0002");
        for (int i = 0; i < produitfinies.length; i++) {
            comp.setIdcomposant(produitfinies[i].getIdproduit());
            comp = Composant.convert(comp.getData(Bddobject.toConPostgres(), null, "idcomposant"))[0];
            comp.decomposition(comp.getIdcomposant());
            // showDecomposition(comp);
    
            System.out.print(comp.getNom()+" : "+comp.prix());
            comp.setIdcomposant("CMP0002");
            comp = Composant.convert(comp.getData(Bddobject.toConPostgres(), null, "idcomposant"))[0];
            comp.decomposition(comp.getIdcomposant());
            System.out.println("  "+ comp.getNom()+" : "+comp.prix());
        }
        comp.setIdcomposant("CMP0010");
        comp = Composant.convert(comp.getData(Bddobject.toConPostgres(), null, "idcomposant"))[0];
        comp.decomposition(comp.getIdcomposant());
        double sum = 0.0;
        for (Composant composant : comp.getProdPrimary(1)) {
            System.out.println(composant.getNom()+" :" +composant.isMatPremiere()+" avec quantite "+ composant.getQuantIngredient());
            sum += composant.getQuantIngredient()*composant.getPrixUnitaire();
        }
        System.out.println("sum "+sum);
        try {
            comp.setCon(Bddobject.toConPostgres());
            magasin.insertObj(comp.getCon());
            comp.getCon().commit();
//            typeStock.setCon(Bddobject.toConPostgres());
//            typeStock.insertObj(typeStock.getCon());
//            typeStock.getCon().commit();
            // comp.setCon(Bddobject.toConPostgres());
            // Produitfinie produitfinie = new Produitfinie("CMP0016");
            // produitfinie.insertObj(comp.getCon());
            // cmp.setCon(Bddobject.toConPostgres());
            // cmp.insertObj(cmp.getCon());
            // cmp.getCon().commit();
            // ing.setCon(Bddobject.toConPostgres());
            // ing.insertObj(ing.getCon());
            // ing.getCon().commit();
            // comp.getCon().commit();
        } catch (Exception e) {
            comp.getCon().rollback();
            // ing.getCon().rollback();
            // comp.getCon().rollback();
//            typeStock.getCon().rollback();
            e.printStackTrace();
            // cmp.getCon().rollback();
        }finally{
            comp.getCon().close();
//            typeStock.getCon().close();
            // ing.getCon().close();
            // cmp.getCon().close();
            // comp.getCon().close();
        }

    }
    public static void showDecomposition(Composant comp) throws Exception {
        if (comp.getComposition() == null) {
            System.out.println(comp.getNom());
            return;
        }
        for (int i = 0; i < comp.getComposition().length; i++) {
            System.out.print(comp.getComposition()[i].getNom()+" ");
            if (!comp.getComposition()[i].isMatPremiere()) {
                System.out.print("->");
                showDecomposition(comp.getComposition()[i]);
            }
        }
        System.out.println();
    }
}
