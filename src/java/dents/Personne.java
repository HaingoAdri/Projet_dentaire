package dents;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Personne{
    
    String id;
    String nom;
    double budget;
    Date dates;
    int traitement;
    

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public double getBudget() {
        return budget;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }
    public Date getDates() {
        return dates;
    }
    public void setDates(Date dates) {
        this.dates = dates;
    }
    public int getTraitement() {
        return traitement;
    }
    public void setTraitement(int traitement) {
        this.traitement = traitement;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    public Personne(String id,String nom, double budget, Date dates, int traitement) {
        this.setId(id);
        this.setNom(nom);
        this.setBudget(budget);
        this.setDates(dates);
        this.setTraitement(traitement);
    }

    public Personne(String nom, double budget, Date dates, int traitement) {
        this.setNom(nom);
        this.setBudget(budget);
        this.setDates(dates);
        this.setTraitement(traitement);
    }

    public Personne(String id) {
        this.setId(id);
    }

    public Personne() {

    }

    public Personne(String id2, int tr) {
        this.setId(id2);
        this.setTraitement(tr);
    }
    public void insertPersonne(Connection c)throws SQLException{
        String sql = "insert into personne(nom,budget,dates,traitement) values('"+this.getNom()+"', "+this.getBudget()+", '"+this.getDates()+"', "+this.getTraitement()+")";
        PreparedStatement st = c.prepareStatement(sql);
        st.executeUpdate();
        System.out.println(sql);
    }
    
    public Personne getPersonne(Connection c, Date date, String nom)throws SQLException{
        Personne personne = new Personne();
        String sql = "select id ,nom, budget, dates, traitement from personne where dates = '"+date+"' and nom ='"+nom+"'";
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            String id = rs.getString("id");
            String noms = rs.getString("nom");
            double budget = rs.getDouble("budget");
            Date d = rs.getDate("dates");
            int tr = rs.getInt("traitement");
            personne = new Personne(id,noms,budget,d,tr);
        }
        return personne;
    }
}