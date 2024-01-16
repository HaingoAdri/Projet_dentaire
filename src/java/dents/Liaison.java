package dents;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dents.Dents;

public class Liaison{
    
    int id;
    String personne;
    int dents;
    int etats;
    int types;
    int remplacement;
    double cout;
    String nom;
    String type;
    int coeff;
    double cout_traite;
    int priorite;
    double repe;
    double net;

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPersonne() {
        return personne;
    }
    public void setPersonne(String personne) {
        this.personne = personne;
    }
    public int getDents() {
        return dents;
    }
    public void setDents(int dents) {
        this.dents = dents;
    }
    public int getEtats() {
        return etats;
    }
    public void setEtats(int etats) {
        this.etats = etats;
    }
    public int getTypes() {
        return types;
    }
    public void setTypes(int types) {
        this.types = types;
    }
    public int getRemplacement() {
        return remplacement;
    }
    public void setRemplacement(int remplacement) {
        this.remplacement = remplacement;
    }
    public double getCout() {
        return cout;
    }
    public void setCout(double cout) {
        this.cout = cout;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getCoeff() {
        return coeff;
    }
    public void setCoeff(int coeff) {
        this.coeff = coeff;
    }
    public double getCout_traite() {
        return cout_traite;
    }
    public void setCout_traite(double cout_traite) {
        this.cout_traite = cout_traite;
    }

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public double getRepe() {
        return repe;
    }

    public void setRepe(double repe) {
        this.repe = repe;
    }

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }
    


    public Liaison(int id, String personne, int dents, int etats, int types, int remplacement) {
        this.setId(id);
        this.setPersonne(personne);
        this.setDents(dents);
        this.setEtats(etats);
        this.setTypes(types);
        this.setRemplacement(remplacement);
    }

    public Liaison(String personne, int dents, int etats, int types, int remplacement) {
        this.setPersonne(personne);
        this.setDents(dents);
        this.setEtats(etats);
        this.setTypes(types);
        this.setRemplacement(remplacement);
    }

    public Liaison() {
    }

    public Liaison(String personne, int dents, int etats, double cout,String nom, String type, int coeff,double coutd, double cout_rep,int priorite) {
        this.setPersonne(personne);
        this.setDents(dents);
        this.setEtats(etats);
        this.setCout(cout);
        this.setNom(nom);
        this.setType(type);
        this.setCoeff(coeff);
        this.setRepe(coutd);
        this.setNet(cout_rep);
        this.setPriorite(priorite);
    }

    public void insertLiaison(Connection c, int coefficient, String types, double cout_remplacemnt, double cout_nettoyage, double cout_reparation) throws SQLException{
        String sql = "insert into liaison(personne,dents,etats,types_quoi,coefficient,type_coeff,cout_remplacement,cout_nettoyage,cout_reparation) values('"+this.getPersonne()+"',"+this.getDents()+","+this.getEtats()+","+this.getTypes()+","+coefficient+",'"+types+"',"+cout_remplacemnt+","+cout_nettoyage+" , "+cout_reparation+")";
        PreparedStatement st = c.prepareStatement(sql);
        st.executeUpdate();
        System.out.println(sql);
    }

    
    
    public List<Liaison> getDentsWhere(Connection c, int i, String name) throws SQLException{
        String sql = "select * from liaison where personne = '"+name+"' and dents = "+i;
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Liaison> listeLiaisons = new ArrayList<>();
        while(rs.next()){
            String personne = rs.getString("personne");
            int dents = rs.getInt("dents");
            int etats = rs.getInt("etats");
            int types = rs.getInt("types_quoi");
            int remplacement = rs.getInt("remplacement");
            Liaison liaison = new Liaison(personne, dents, etats, types, remplacement);
            listeLiaisons.add(liaison);
        }
        //System.out.println(sql);
        return listeLiaisons;
    }

    public List<Liaison> getListeProposition(Connection c, String personne) throws SQLException{
        String sql = "select liaison.*, (etats*coefficient) as priorite, etats, coefficient from liaison where liaison.personne='"+personne+"' order by priorite asc";
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Liaison> getListe = new ArrayList<>();
        while(rs.next()){
            String personnes = rs.getString("personne");
            int dents = rs.getInt("dents");
            int etats = rs.getInt("etats");
            String nom =  rs.getString("etat_nom");
            int coeff = rs.getInt("coefficient");
            String nom_coeff= rs.getString("type_coeff");
            double cout_remplacement = rs.getDouble("cout_remplacement");
            double cout_traite = rs.getDouble("cout_nettoyage");
            double cout_rep = rs.getDouble("cout_reparation");
            int priorite = rs.getInt("priorite");
            Liaison liaison = new Liaison(personnes, dents, etats, cout_remplacement,nom, nom_coeff, coeff,cout_traite,cout_rep,priorite);
            getListe.add(liaison);
        }
        System.out.println(sql);
        return getListe;
    }
    
    public double totalTraitement(Connection c, String pers) throws SQLException{
        String sql = " select sum(cout_remplacement) sommeRemplace, sum(cout_reparation*(etat_but-etats)) sommeTraite, sum(cout_nettoyage*(etat_but-etats)) as net from liaison where personne='"+pers+"'";
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        double traite = 0;
        double remplace = 0;
        double somme = 0;
        double rem = 0;
        while(rs.next()){
            traite = rs.getDouble("sommeRemplace");
            remplace = rs.getDouble("sommeTraite");
            rem = rs.getDouble("net");
            somme = traite + remplace+rem;
        }
        System.out.println(sql);
        return somme;
    }
    
    public double getReste(double budget, double total){
        return budget-total;
    }
}