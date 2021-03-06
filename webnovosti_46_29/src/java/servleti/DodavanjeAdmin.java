/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servleti;

import beans.korisnik;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.DB;

/**
 *
 * @author korisnik
 */
@WebServlet(name = "DodavanjeAdmin", urlPatterns = {"/DodavanjeAdmin"})
public class DodavanjeAdmin extends HttpServlet {

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
            throws ServletException, IOException {
      
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
         korisnik k = new korisnik();

        String un = request.getParameter("username");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");
        String tel = request.getParameter("telefon");
        String ime = request.getParameter("ime");
        String prezime = request.getParameter("prezime");
        String uloga = request.getParameter("uloga");

        k.setEmail(email);
        k.setUsername(un);
        k.setPassword(pass);
        k.setPrezime(prezime);
        k.setIme(ime);
        k.setTelefon(tel);
        k.setUloga(uloga);

    
        String upit = "insert into korisnikinfo(username, password, email, telefon, ime, prezime, uloga) values ('" + un + "', '" + pass + "', '" + email + "', '" + tel + "', '" + ime + "', '" + prezime + "', '" + uloga + "')";
        String upit2 = "select * from korisnikinfo";
        Connection con = null;
        Statement stmt = null;
        String address = "dodajAdmin.jsp";
        String poruka = "";
        ResultSet rs = null;
         boolean postoji = false;
         boolean pogresnaUloga = false;
 
        try {
            con = DB.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(upit2);
            while (rs.next()) {
                String unBaza = rs.getString("username");
                String emailBaza = rs.getString("email");
                if(k.getUsername().equalsIgnoreCase(unBaza) || k.getEmail().equalsIgnoreCase(emailBaza)){
                    postoji = true;
                }
                if(!k.getUloga().equalsIgnoreCase("admin") && !k.getUloga().equalsIgnoreCase("editor") && !k.getUloga().equalsIgnoreCase("user")){
                    pogresnaUloga=true;
                }
            } 
            if(postoji){
                    poruka = "Username ili email vec postoji!";
                    request.setAttribute("poruka", poruka);
             
            }else if(pogresnaUloga){
                    poruka = "Nije prepoznata unesena uloga.";
                    request.setAttribute("poruka", poruka);
            }
            else{
              stmt.executeUpdate(upit);
              poruka = "Uspjesno dodan novi korisnik.";
              request.setAttribute("poruka", poruka);
            }

        } catch (SQLException e) {
           
            String err = e.getMessage();
            request.setAttribute("errormsg", err);
            address = "error";
        }
        RequestDispatcher rd = request.getRequestDispatcher(address);
        rd.forward(request, response);
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
