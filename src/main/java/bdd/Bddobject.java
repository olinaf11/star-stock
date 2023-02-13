package bdd;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.StringBuilder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Bddobject {
    String table;
    String primaryKey;
    Object primaryValue;
    String prefix;
    int lenPk;
    String seqName;
    Connection con;
    boolean connect;

    public Connection getCon() {
        return con;
    }
    public String getPrimaryKey() {
        return primaryKey;
    }public Object getPrimaryValue() {
        return primaryValue;
    }public String getTable() {
        return table;
    }
    public String getPrefix() {
        return prefix;
    }
    public int getLenPk() {
        return lenPk;
    }
    public String getSeqName() {
        return seqName;
    }
    public void setCon(Connection con) {
        this.con = con;
    }
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }public void setPrimaryValue(Object primaryValue) {
        this.primaryValue = primaryValue;
    }public void setTable(String table) {
        this.table = table;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public void setLenPk(int lenPk) {
        this.lenPk = lenPk;
    }
    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }
    public Connection toConOracle() throws Exception{
        Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "music", "tiger");
        c.setAutoCommit(false);
        return c;
    }
    public static Connection toConPostgres() throws Exception{
        Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/star", "star", "star");
        c.setAutoCommit(false);
        return c;
    }
    public Statement createStat(Connection c) throws Exception{
        return c.createStatement();
    }
    public ResultSet getResultSet(Statement stmt, String query) throws SQLException{
        return stmt.executeQuery(query);
    }
    public void execute(Statement stmt, String query) throws SQLException{
        stmt.executeUpdate(query);
    }
    public void closeAll(Connection c, Statement stmt) throws Exception{
        stmt.close();
        c.close();
    }
    public void rollback(Connection c)throws Exception {
        c.rollback();
    }
    public int getCurrentValue(Connection c) throws Exception{
        String getSeq = "get"+toUpperFirstChar(getSeqName());
        int val = 0;
        String query = "select "+getSeq+" from dual";
        System.out.println(query);
        Statement stmt = c.createStatement();
        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                val = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("SeqVal = "+ val);
        stmt.close();
        return val;
    }
    public int getCurrentValuePg(Connection c) throws Exception{
        String getSeq = "get"+toUpperFirstChar(getSeqName());
        int val = 0;
        String query = "select "+getSeq+"()";
        System.out.println(query);
        Statement stmt = c.createStatement();
        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                val = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("SeqVal = "+ val);
        stmt.close();
        return val;
    }
    public String completeZero(int seq, int lc) {
        String val = ""+seq;
        if (val.length()<lc) {
            String zero = "";
            for (int i = 0; i < lc-val.length(); i++) {
                zero+="0";
            }
            val = zero.concat(val);
        }
        return val;
    }
    public String constructPK(Connection c) throws Exception{
        boolean isnull = false;
        if (c == null){
            isnull=true;
            //avadika oracle raha oracle
            c = toConPostgres();
        }
        String val;
        if (c.getMetaData().getDatabaseProductName().compareTo("PostgreSQL")==0) {
            val = getPrefix()+completeZero(getCurrentValuePg(c), getLenPk()-getPrefix().length());
            if (isnull) c.close();
            return val;
        }
        val = getPrefix()+completeZero(getCurrentValue(c), getLenPk()-getPrefix().length());
        if (isnull) c.close();
        return val;
    }
    //mila fantatra ny isan ny colonne ana table iray
    public int countCol(Connection c, String tabName) throws Exception{
        Statement stmt = this.createStat(c);
        String sql = "select * from " + tabName;
        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsm = rs.getMetaData();
        int rep = rsm.getColumnCount();
        rs.close();
        return rep;
    }
    public String toUpperFirstChar(String str) {
        String tmp;
        tmp=str.substring(0,1);//alaina le lettre voalohany
        str=str.substring(1);//de atao string iray ny ambiny
        tmp=tmp.toUpperCase();//atao maj le lettre voalohany
        tmp=tmp.concat(str);//de concatenena
        return tmp;
    }
    public String[] getAtt(Object obj) {
        Field[] f = obj.getClass().getDeclaredFields();
        String[] att = new String[f.length];
        for (int i = 0; i < f.length; i++) {
            att[i] = f[i].getName();
        }
        return att;
    }
    public Class<?>[] getFieldType(Object obj) {
        Field[] f = obj.getClass().getDeclaredFields();
        Class<?>[] ft = new Class<?>[f.length];
        for (int i = 0; i < f.length; i++) {
            ft[i] = f[i].getType();
        }
        return ft;
    }


    public String[] getFieldTypeName(Class<?>[] ft) {
        String[] val = new String[ft.length];
        for (int i = 0; i < ft.length; i++) {
            String[] ftn = ft[i].getTypeName().split("\\.");
            String ftname = ftn[ftn.length-1];
            val[i] = ftname;
        }
        return val;
    }
    public String getValueTab() throws Exception{
        StringBuilder val = new StringBuilder();
        String[] listAtt = getAtt(this);
        for (int i = 0; i < listAtt.length; i++) {
            Object attVal = this.getClass().getMethod("get"+toUpperFirstChar(listAtt[i])).invoke(this);
            val.append(listAtt[i]).append("=").append(attVal).append(";");
        }
        System.out.println("valeur = "+val);
        return val.toString();
    }
    public void historiser(Connection c, String action) throws Exception{
        String value = getValueTab();
        Historique his = new Historique();
        his.setIdHist(his.constructPK(c));
        his.setTabName(getTable());
        his.setValue(value);
        his.setDateHist(new Date(System.currentTimeMillis()));
        his.setAction(action);
        his.insertObj(c);
    }

    public Vector<Object> sqlToObj(ResultSet rs) throws SQLException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String[] fn = getAtt(this);
        Vector<Object> rep = new Vector<>();
        Class<?>[] ft = getFieldType(this);
        String[] ftn = getFieldTypeName(ft);
        ResultSetMetaData rsm = rs.getMetaData();
        while (rs.next()) {
            Object temp = this.getClass().getConstructor().newInstance();
            for (int i = 0; i < rsm.getColumnCount(); i++) {
                Method getArg = rs.getClass().getMethod("get"+toUpperFirstChar(ftn[i]) , String.class);
                getArg.setAccessible(true);
                Object getRsField = getArg.invoke(rs, fn[i]);
                Method setField = temp.getClass().getMethod("set"+toUpperFirstChar(fn[i]), ft[i]);
                setField.invoke(temp, getRsField);
            }
            rep.add(temp);
        }
        rs.close();
        return rep;
    }
    public void insertObj( Connection c ) throws Exception{
        boolean connect = false;
        if (c == null) {
            c = Bddobject.toConPostgres();
            connect = true;
        }
        Statement st = c.createStatement();
        String ins = "insert into " + getTable() + " values (";
        String[] att = getAtt(this);
        for (int i = 0; i < countCol(c, getTable()); i++) {
            Object attribut = this.getClass().getMethod("get"+toUpperFirstChar(att[i])).invoke(this);
            if (attribut!=null){
                if (attribut.getClass() == Date.class) {
                    ins += "TO_DATE('"+attribut+"', 'YYYY-MM-DD')";
                } else if (attribut.getClass() == String.class) {
                    ins += "'"+attribut+"'";
                }else {
                    ins += attribut;
                }
                if (i < countCol(c, getTable())-1) {
                    ins += ",";
                }
            }else {
                ins+= "default,";
            }
        }
        ins += ")";
        System.out.println(ins);
        st.executeUpdate(ins);
        st.close();
        if(connect) {c.commit(); c.close();}
    }
    public void updateObj(Object value, String colName, String ref, Connection c) throws Exception{
        Statement st = c.createStatement();
        String req = "update "+ this.getTable() + " set "+ colName + " = ";
        if (value == Date.class) {
            req += "To_DATE('"+value+"' , 'YYYY-MM-DD'";
        }else if (value == String.class) {
            req += "'"+value+"'";
        }else{
            req += "'"+value+"'";
        }
        req +=" where "+getPrimaryKey() + " = '" + ref+"'";
        System.out.println(req);
        st.executeUpdate(req);
        st.close();
    }
    public String convertToLegal(Object args) {
        return (args == null) ? "null"
                : (args.getClass() == Date.class) ? "TO_DATE('" + args + "', 'YYYY-MM-DD')"
                : (args.getClass() == String.class) ? "'"+ args +"'"
                : args.toString();
    }
    public String predicat(String[] predicats) throws Exception {
        String sql = ""; // Condition with AND clause
        for (String predicat : predicats)
            sql += predicat + "=" + convertToLegal(this.getClass().getMethod("get" + toUpperFirstChar(predicat)).invoke(this)) + " AND ";
        return sql.substring(0, sql.length() - 5); // Delete last " AND " in sql
    }
    public Object[] getData(Connection connection, String order, String... predicat) throws Exception {
        String sql = (predicat.length == 0) ? "SELECT * FROM " + this.getTable()
                : "SELECT * FROM " + this.getTable() + " WHERE " + predicat(predicat);
        if (order != null) sql += " ORDER BY "+ order;
        // System.out.println(sql);
        return getData(sql, connection);
    }
    public Object[] getData(String query, Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        String[] liste = listColumn(query, connection); 
        Object[] employees = convertToObject(result, liste.length);
        result.close();
        statement.close();
        connection.close();
        return employees;
    }
    public Object[] convertToObject(ResultSet result, int attribut) throws Exception {
        Field[] attributs = this.getClass().getDeclaredFields();
        List<Object> objects = new ArrayList<>();
        while (result.next()) {
            Object object = this.getClass().getConstructor().newInstance();
            for (int i = 0; i < attribut; i++) {
                Method setter = this.getClass().getMethod("set" + toUpperFirstChar(attributs[i].getName()), attributs[i].getType()); // Setter of this object
                Method getter = null;
                if (attributs[i].getType().getSimpleName().equals("Integer")){
                    getter = ResultSet.class.getMethod("getInt",int.class);
                }else{
                    getter = ResultSet.class.getMethod("get" + toUpperFirstChar(attributs[i].getType().getSimpleName()), int.class);  // Method in ResultSet
                }
                getter.setAccessible(true); // get(int.class) method isn't accessible
                setter.invoke(object, getter.invoke(result, i + 1));
            }
            objects.add(object);
        }
        return objects.toArray();
    }
    public static String[] listColumn(String query, Connection connection) throws Exception {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        String[] colonnes = new String[count];
        int increment = 0;
        for(int i = 1; i <= count; i++) {
            colonnes[increment] = rsMetaData.getColumnName(i);
            increment++;
        }
        return colonnes;
    }

}