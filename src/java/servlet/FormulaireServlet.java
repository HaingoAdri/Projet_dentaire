/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.DbConnection;
import dents.Dents;
import dents.Liaison;
import dents.Personne;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Adrienne
 */
@WebServlet(name = "FormulaireServlet", urlPatterns = {"/FormulaireServlet"})
public class FormulaireServlet extends HttpServlet {
    Dents d =  new Dents();
    DbConnection c = new DbConnection();
    Liaison li = new Liaison();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        List<Dents> getTraitement = d.getTraitement(c.connectToPostgres());
        List<Dents> getTypeSimba = d.getQuoi(c.connectToPostgres());
        List<Dents> getDents = d.getAllDents(c.connectToPostgres());
        List<Dents> getRemplacement = d.getRemplacement(c.connectToPostgres());
        request.setAttribute("traitement", getTraitement);
        request.setAttribute("simba", getTypeSimba);
        request.setAttribute("remplacement", getRemplacement);
        request.setAttribute("dents", getDents);
        RequestDispatcher dispatch = request.getRequestDispatcher("NewForm.jsp");
        dispatch.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Cltrick on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FormulaireServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pers = request.getParameter("personne");
            String budget = request.getParameter("budget");
            String date = request.getParameter("date");
            String traitement = request.getParameter("traitement");
            String lsD = request.getParameter("dents");
            String ets = request.getParameter("etat");
            String[] dents = lsD.split("[;:]");
            //String[] simba = request.getParameterValues("simba");
            String[] etats = ets.split("[;:]");
            //String[] remplacement = request.getParameterValues("remplacement");

            Date dates = Date.valueOf(date);
            int traite = Integer.parseInt(traitement);
            double vola = Double.parseDouble(budget);

            Personne personne = new Personne(pers, vola, dates, traite);
            personne.insertPersonne(c.connectToPostgres());
            Personne nouvellePersonne = personne.getPersonne(c.connectToPostgres(), dates, pers);
            
            out.println(dents[0]);
            if(dents.length!=0)
            {for (int i = 0; i < dents.length; i++) {
                int idDent = Integer.parseInt(dents[i]);
                try {
                    Dents dd = d.getAllDentsWhere(c.connectToPostgres(), idDent);

                    int etat = Integer.parseInt(etats[i]);
                    int simbas = 1;
                    int remplace = 1;
                    Liaison liaison = new Liaison(nouvellePersonne.getId(), dd.getId(), etat, simbas, remplace);
                        
                    if (nouvellePersonne.getTraitement() == 1) {
                        if(etat == 0){
                            liaison.insertLiaison(c.connectToPostgres(), dd.getCoefficientEsthetic(), "Sanitary", 100000,0,0);
                        }
                        if(etat >=7 && etat<10 && etat == 10){
                            liaison.insertLiaison(c.connectToPostgres(), dd.getCoefficientEsthetic(), "Sanitary", 0,1000,0);
                        }
                        if (etat>=1 && etat<4) {
                            liaison.insertLiaison(c.connectToPostgres(), dd.getCoefficientSanitaire(), "Sanitary", 0,0,5000);
                        } else if (etat >=4 && etat <7) {
                            liaison.insertLiaison(c.connectToPostgres(), dd.getCoefficientSanitaire(), "Sanitary", 0,0,2000);
                        }
                    } else {
                        if(etat == 0){
                            liaison.insertLiaison(c.connectToPostgres(), dd.getCoefficientEsthetic(), "Esthetic", 100000,0,0);
                        }
                        if(etat >=7 && etat<=10){
                            liaison.insertLiaison(c.connectToPostgres(), dd.getCoefficientEsthetic(), "Esthetic", 0,1000,0);
                        }
                        if (etat>=1 && etat<4) {
                            liaison.insertLiaison(c.connectToPostgres(), dd.getCoefficientSanitaire(), "Esthetic", 0,0,5000);
                        } else if (etat >=4 && etat <7) {
                            liaison.insertLiaison(c.connectToPostgres(), dd.getCoefficientSanitaire(), "Esthetic", 0,0,2000);
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FormulaireServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            List<Liaison> getLiaison = li.getListeProposition(c.connectToPostgres(), nouvellePersonne.getId());
            Dents getTraite = d.getTraitementById(c.connectToPostgres(), nouvellePersonne.getTraitement());
            Double totalTraitement = li.totalTraitement(c.connectToPostgres(), nouvellePersonne.getId());
            Double reste = li.getReste(nouvellePersonne.getBudget(), totalTraitement);
            List<Liaison> dentsTraites = new ArrayList<>();
            List<Liaison> dentsNonTraites = new ArrayList<>();
            request.setAttribute("personne",nouvellePersonne);
            request.setAttribute("traitement",getTraite);
            request.setAttribute("vola", vola);
            request.setAttribute("listeLiaison", getLiaison);
            request.setAttribute("listes", dentsTraites);
            request.setAttribute("listesl", dentsNonTraites);
            request.setAttribute("total", totalTraitement);
            request.setAttribute("reste", reste);
            RequestDispatcher dispatch = request.getRequestDispatcher("Liste.jsp");
            dispatch.forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(FormulaireServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
