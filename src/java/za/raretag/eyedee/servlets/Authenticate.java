/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.eyedee.servlets;

//import za.raretag.eyedee.entities.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.raretag.eyedee.beans.UserBeanLocal;
import za.raretag.eyedee.entities.*;
import za.raretag.eyedee.generic.Data;
import za.raretag.eyedee.generic.MessageContainer;
import za.raretag.eyedee.generic.Response;
import za.raretag.eyedee.generic.User;

/**
 *
 * @author tebogom
 */
public class Authenticate extends HttpServlet {

    @EJB
    private UserBeanLocal userBean;
    Gson gson = new Gson();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
      List<MessageContainer> messageList;
    private Response resp;
    MessageContainer message;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        // response.addHeader("Access-Control-Allow-Origin", "*");
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String callback = request.getParameter("callback");
            User user = new User();
            user.setUserID(username);
            Response authentication = userBean.authenticate(username, password);
            Data<User> userData = authentication.getData();
            if (userData.get().isAuthenticated()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", userData.get().getUserID());
                session.setAttribute("authenticated", userData.get().isAuthenticated());
                String role = userBean.getUserRole(username);
                session.setAttribute("role", role);
            } 
            if (callback != null && callback.length() > 1) {
                out.println(callback + "(" + (gson.toJson(authentication)) + ");");
            } else {
                out.println(gson.toJson(authentication));
            }
        } catch (Exception ex) {

            response.sendError(401, "Incorrect username/password");
            }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
