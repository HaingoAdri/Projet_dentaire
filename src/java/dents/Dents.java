package dents;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dents.Liaison;

public class Dents{
    
    int id;
    double coutRemplacement;
    double coutTraitement;
    int coefficientEsthetic;
    int coefficientSanitaire;
    String nom;
    int priorite;
    double coutRepEst;
    double coutNetEst;
    double coutRepSan; 
    double coutNetSan;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getCoutRemplacement() {
        return coutRemplacement;
    }
    public void setCoutRemplacement(double coutRemplacement) {
        this.coutRemplacement = coutRemplacement;
    }
    public double getCoutTraitement() {
        return coutTraitement;
    }
    public void setCoutTraitement(double coutTraitement) {
        this.coutTraitement = coutTraitement;
    }
    public int getCoefficientEsthetic() {
        return coefficientEsthetic;
    }
    public void setCoefficientEsthetic(int coefficientEsthetic) {
        this.coefficientEsthetic = coefficientEsthetic;
    }
    public int getCoefficientSanitaire() {
        return coefficientSanitaire;
    }
    public void setCoefficientSanitaire(int coefficientSanitaire) {
        this.coefficientSanitaire = coefficientSanitaire;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getPriorite() {
        return priorite;
    }
    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public double getCoutRepEst() {
        return coutRepEst;
    }

    public void setCoutRepEst(double coutRepEst) {
        this.coutRepEst = coutRepEst;
    }

    public double getCoutNetEst() {
        return coutNetEst;
    }

    public void setCoutNetEst(double coutNetEst) {
        this.coutNetEst = coutNetEst;
    }

    public double getCoutRepSan() {
        return coutRepSan;
    }

    public void setCoutRepSan(double coutRepSan) {
        this.coutRepSan = coutRepSan;
    }

    public double getCoutNetSan() {
        return coutNetSan;
    }

    public void setCoutNetSan(double coutNetSan) {
        this.coutNetSan = coutNetSan;
    }
    

    public Dents(int id, double coutRemplacement, int coefficientEsthetic,
            int coefficientSanitaire, double coutRepEst, double coutNetEst, double coutRepSan, double coutNetSan) {
        this.setId(id);
        this.setCoutRemplacement(coutRemplacement);
        this.setCoefficientEsthetic(coefficientEsthetic);
        this.setCoefficientSanitaire(coefficientSanitaire);
        this.setCoutRepEst(coutRepEst);
        this.setCoutNetEst(coutNetEst);
        this.setCoutRepSan(coutRepSan);
        this.setCoutNetSan(coutNetSan);
    }

    public Dents(){

    }

    public Dents(int id, int etats){
        this.setId(id);
        this.setCoefficientEsthetic(etats);
    }

    public Dents(int id, String etats){
        this.setId(id);
        this.setNom(etats);
    }

    public List<Dents> getAllDents(Connection c) throws SQLException{
        List<Dents>  listeDents = new ArrayList<>();
        String sql = "select * from dents order by id asc";
        Statement st =  c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            int id = rs.getInt("id");
            double coutRemplacement = rs.getDouble("coutremplacement");
            double coutreparationEsthetique = rs.getDouble("cout_reparatio_esthetique");
            double coutNettoyageEsthetic = rs.getDouble("cout_nettoyage_etsthetique");
            double coutRepSanitaire = rs.getDouble("cout_reparation_sanitaire");
            double coutNetSanitaire = rs.getDouble("cout_nettoyage_sanitaire");
            int coefficientEsthetic = rs.getInt("coefficientesthetic");
            int coefficientSanitaire = rs.getInt("coefficientsanitaire");
            Dents d = new Dents(id, coutRemplacement,coefficientEsthetic, coefficientSanitaire,coutreparationEsthetique
            ,coutNettoyageEsthetic ,coutRepSanitaire,coutNetSanitaire);
            listeDents.add(d);
        }
        return listeDents;
    }

    public Dents getAllDentsWhere(Connection c, int i) throws SQLException{
        Dents d = new Dents();
        String sql = "select * from dents where id ="+i;
        Statement st =  c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            int id = rs.getInt("id");
            double coutRemplacement = rs.getDouble("coutremplacement");
            double coutreparationEsthetique = rs.getDouble("cout_reparatio_esthetique");
            double coutNettoyageEsthetic = rs.getDouble("cout_nettoyage_etsthetique");
            double coutRepSanitaire = rs.getDouble("cout_reparation_sanitaire");
            double coutNetSanitaire = rs.getDouble("cout_nettoyage_sanitaire");
            int coefficientEsthetic = rs.getInt("coefficientesthetic");
            int coefficientSanitaire = rs.getInt("coefficientsanitaire");
            d = new Dents(id, coutRemplacement,coefficientEsthetic, coefficientSanitaire,coutreparationEsthetique
            ,coutNettoyageEsthetic ,coutRepSanitaire,coutNetSanitaire);
        }
        System.out.println(sql);
        return d;
    }

    public List<Dents> getQuoi(Connection c)throws SQLException{
        List<Dents> listeQuoi = new ArrayList<>();
        String sql = "select * from quoi";
        Statement st =  c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            Dents d = new Dents(id, nom);
            listeQuoi.add(d);
        }
        return listeQuoi;
    }

    public List<Dents> getTraitement(Connection c)throws SQLException{
        List<Dents> listeQuoi = new ArrayList<>();
        String sql = "select * from traitement";
        Statement st =  c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            Dents d = new Dents(id, nom);
            listeQuoi.add(d);
        }
        return listeQuoi;
    }
    
    public List<Dents> getRemplacement(Connection c)throws SQLException{
        List<Dents> listeQuoi = new ArrayList<>();
        String sql = "select * from remplacement";
        Statement st =  c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            Dents d = new Dents(id, nom);
            listeQuoi.add(d);
        }
        return listeQuoi;
    }
    
    public Dents getTraitementById(Connection c , int id) throws SQLException{
        Dents d = new Dents();
        String sql = "select * from traitement where id = "+id;
        Statement st =  c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            int ids = rs.getInt("id");
            String noms = rs.getString("nom");
            d = new Dents(ids, noms);
        }
        return d;
    }
}