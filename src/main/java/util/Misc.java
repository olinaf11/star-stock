package util;

import model.Stock;

import java.sql.Connection;
import java.sql.Date;

public class Misc {

    public static Stock[] getStock(Connection con, String idproduit) throws Exception{
        return Stock.convert(new Stock().getData("select * from stock where idproduit ='"+idproduit+"' order by id desc",con));
    }

    public static double getStockQuantite(Connection con, Stock[] stocks) throws Exception {
        double amount = 0;
        for (Stock stock : stocks){
            amount += stock.getQuantite();
        }
        return amount;
    }

    public static Stock[] getStockIN(Connection con) throws Exception {
        return Stock.convert(new Stock().getData("select * from stock where typeStock = 'TPS1'",con));
    }

    public static Stock[] getStockOUT(Connection con) throws Exception {
        return Stock.convert(new Stock().getData("select * from stock where typeStock = 'TPS2'",con));
    }

    public static Stock[] getStockBetween(Connection con, Date date1, Date date2) throws Exception{
        return Stock.convert(new Stock().getData( "select * from stock where " + "dateStock >= '"+date1.toString()+"' " + "and date <= '"+date2.toString()+"'",con));
    }

    public static Stock[] getStockWhen(Connection con, Date date) throws Exception{
        return Stock.convert(new Stock().getData( "select * from stock where dateStock = '"+date.toString()+"'",con));
    }

    public static Stock[] getStockSince(Connection con, Date date) throws Exception{
        return Stock.convert(new Stock().getData( "select * from stock where dateStock > '"+date.toString()+"'",con));
    }

    public static Stock[] getStockUntil(Connection con, Date date) throws Exception{
        return Stock.convert(new Stock().getData( "select * from stock where dateStock < '"+date.toString()+"'",con));
    }
}
