package model;

import bdd.Bddobject;

public class Magasin extends Bddobject {
    String id;
    String name;


    public String getId() {
        return id;
    }

    public void setId(String id) throws Exception{
        if (!id.contains(this.getPrefix()) || id.length() != this.getLenPk())
            throw new Exception("Id is invalid");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Magasin(){
        super.setPrefix("MAG");
        super.setLenPk(7);
        super.setSeqName("mgseq");
        super.setTable("magasin");
        super.setPrimaryKey("id");
    }
    public Magasin(String name) throws Exception {
        this();
        setId(constructPK(null));
        setName(name);
    }
    public static Magasin[] convert(Object[] objects) throws Exception {
        if (objects[0].getClass()!=Magasin.class) throw new Exception("class non compatible");
        Magasin[] magasins = new Magasin[objects.length];
        for (int i = 0; i < magasins.length; i++) {
            magasins[i] = (Magasin) objects[i];
        }
        return magasins;
    }
}
