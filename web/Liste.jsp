<%-- 
    Document   : Liste
    Created on : 4 janv. 2024, 09:25:03
    Author     : Adrienne
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="dents.Liaison"%>
<%@page import="java.util.List"%>
<%@page import="dents.Dents"%>
<%@page import="dents.Personne"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Personne pers = (Personne)request.getAttribute("personne");
    Dents d = (Dents)request.getAttribute("traitement");
    List<Liaison> getListe = (List<Liaison>)request.getAttribute("listeLiaison");
    List<Liaison> dentsTraites = (List<Liaison>)request.getAttribute("listes");
    List<Liaison> dentsNonTraites = (List<Liaison>)request.getAttribute("listesl");
    Double total = 0.0;
    Double reste = 0.0;
    Double totatB = (Double)request.getAttribute("total");
    Double vola = (Double)request.getAttribute("vola");
%>
<!doctype html>
<html lang="en">
  <meta http-equiv="content-type" content="text/html;charset=utf-8" />
  <head>
    <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
        <meta name="generator" content="Hugo 0.88.1">
        <title>-----</title>

        <link rel="canonical" href="index.html">



      <!-- Bootstrap core CSS -->
  <link href="dist/css/bootstrap.min.css" rel="stylesheet" >

      <!-- Favicons -->
  <link rel="apple-touch-icon" href="assets/img/favicons/apple-touch-icon.png" sizes="180x180">
  <link rel="icon" href="assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
  <link rel="icon" href="assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
  <link rel="manifest" href="assets/img/favicons/manifest.json">
  <link rel="mask-icon" href="assets/img/favicons/safari-pinned-tab.svg" color="#7952b3">
  <link rel="icon" href="assets/img/favicons/favicon.ico">
  <meta name="theme-color" content="#7952b3">


      <style>
        .bd-placeholder-img {
          font-size: 1.125rem;
          text-anchor: middle;
          -webkit-user-select: none;
          -moz-user-select: none;
          user-select: none;
        }

        @media (min-width: 768px) {
          .bd-placeholder-img-lg {
            font-size: 3.5rem;
          }
        }
      </style>


      <!-- Custom styles for this template -->
      <link href="starter-template.css" rel="stylesheet">
    </head>
  <body>

<div class="col-lg-8 mx-auto p-3 py-md-5">
  <header class="d-flex align-items-center pb-3 mb-5 border-bottom">
    <a href="index.html" class="d-flex align-items-center text-dark text-decoration-none">
      <span class="fs-4">🦷 Projet Nify / <a href="FormulaireServlet" class="text-dark text-decoration-none">Insertion </a>  /  <a href="" class="text-dark text-decoration-none">Proposition </a></span>
    </a>
  </header>
 
  <main>
    
    <div class="row g-5">
      <div class="col-md-3">
        <div class="mb-3">
          <label for="exampleFormControlInput1" class="form-label">Personne : <%=pers.getNom()%></label>
        </div>
      </div>
      <div class="col-md-3">
        <div class="mb-3">
          <label for="exampleFormControlInput1" class="form-label">Date : <%=pers.getDates()%></label>
          
        </div>
      </div>
      <div class="col-md-3">
        <div class="mb-3">
          <label for="exampleFormControlInput1" class="form-label">Budget :<%=pers.getBudget()%></label>
          
        </div>
      </div>
      <div class="col-md-3">
        <div class="mb-3">
          <label for="exampleFormControlInput1" class="form-label">Traitement: <%=d.getNom()%></label>
          
        </div>
      </div>
    </div>
    <div class="table">
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th scope="col">Nify</th>
            <th scope="col">Etats</th>
            <th scope="col">Coefficient</th>
            <th scope="col">Priorite</th>
            <th scope="col">Reparation</th>
            <th scope="col">Reparation +1</th>
            <th scope="col">Nettoyage</th>
            <th scope="col">Nettoyage +1</th>
            <th scope="col">Cout_reparation</th>
            <th scope="col">Cout_reparation_unitaire</th>
            <th scope="col">Cout_nettoyage</th>
            <th scope="col">Cout_nettoyage_unitaire</th>
            <th scope="col">Cout de remplacement</th>
            
          </tr>
        </thead>
        <tbody>
          <% for(Liaison l : getListe){
              String[] value = new String[(10-l.getEtats())];
          %>
          <tr>
            <th scope="row">n°: <%=l.getDents()%></th>
            <td><%=l.getEtats()%></td>
            <td><%=l.getCoeff()%></td>
            <td><%=l.getPriorite()%></td>
            <td>+ <%=10-l.getEtats()%></td>
            <td><%for(int i = 0; i<(10-l.getEtats()); i++){
                  String j = "+ 1";%>
                  <%=j%>
              <%}%></td>
            <td>+ <%=10-l.getEtats()%></td>
            <td><%for(int i = 0; i<(10-l.getEtats()); i++){
                  String j = "+ 1";%>
                  <%=j%>
            <%}%></td>
            <td><%=((10-l.getEtats())*l.getNet())%></td>
            <td><%=(l.getNet())%></td>
            <td><%=((10-l.getEtats())*l.getRepe())%></td>
            <td><%=(l.getRepe())%></td>
            <td><%=l.getCout()%></td>
            <% 
                double tt = totatB;
                total += tt;
                reste =(totatB - pers.getBudget());
                double v =  pers.getBudget();
                if (tt <= v) {
                    dentsTraites.add(l);
                    pers.setBudget(pers.getBudget()-tt);
                }else{
                    dentsNonTraites.add(l);
                }
            %>
          </tr>
          <%}%>
        </tbody>
      </table>
    </div>
    
    <div class="mb-3 text-end">
        
        <h6>Total traitement : <%=totatB%></h6>
    </div>
    <div class="mb-3 text-end">
        
        <h6>Total traitement : <%=reste%></h6>
    </div>
      </main>
  <footer class="pt-5 my-5 text-muted border-top">
    2069
  </footer>
</div>


    <script src="dist/js/bootstrap.bundle.min.js" ></script>


  </body>


</html>
