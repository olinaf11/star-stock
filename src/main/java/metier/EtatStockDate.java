package metier;

import bdd.Bddobject;
import model.MouvementStock;

import java.sql.Date;

public class EtatStockDate {
    EtatStock[] etatStocks;
    Date date;

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

    public EtatStock[] getEtatStocks() throws Exception {
        String sql = "select idproduit, sum(entree) entree,sum(sortie) sortie, idmagasin from mvtstock where date<='"+getDate().toString()+"' group by idproduit,idmagasin";
        System.out.println(sql);
        return EtatStock.convert(new EtatStock().getData(sql, Bddobject.toConPostgres()));
    }
    public EtatStockDate(String Date){
        setDate(Date);
    }
}
