package metier;

import bdd.Bddobject;
import model.ReportProduit;

import java.sql.Date;

public class EtatdateOptimise {
    EtatStockDate[] stockDates;
    ReportProduit reportProduitLast;
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
    public ReportProduit[] getReportProduitLast() throws Exception {
        ReportProduit reportProduit = new ReportProduit();
        String sql = "select * from reportfinal where date<'"+getDate()+"' order by date desc";
        System.out.println(sql);
        System.out.println(ReportProduit.convert(reportProduit.getData(sql,Bddobject.toConPostgres()))[0].getIdproduit());
        if (ReportProduit.convert(reportProduit.getData(sql,Bddobject.toConPostgres())).length==0){
            throw new Exception("Le dernier report n'existe pas");
        }
        return ReportProduit.convert(reportProduit.getData(sql,Bddobject.toConPostgres()));
    }
    public EtatStock[] getEtatStocks() throws Exception {
        String sql = "select idproduit, sum(entree) entree,sum(sortie) sortie, idmagasin from mvtstock where date<='"+getDate().toString()+"' and date>'"+getReportProduitLast()[0].getDate().toString()+"' group by idproduit,idmagasin";
        System.out.println(sql);
        return EtatStock.convert(new EtatStock().getData(sql, Bddobject.toConPostgres()));
    }
    public EtatdateOptimise(String date){
        setDate(date);
    }
}
