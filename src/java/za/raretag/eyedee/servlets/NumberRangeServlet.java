/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.eyedee.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.raretag.eyedee.beans.FieldOptionBeanLocal;
import za.raretag.eyedee.beans.NumberRangeBeanLocal;
import za.raretag.eyedee.generic.MessageContainer;
import za.raretag.eyedee.generic.Response;

/**
 *
 * @author tebogom
 */
public class NumberRangeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private NumberRangeBeanLocal numberRangeBean;
    private GsonBuilder gsonBuilder;
    private String json;
    private final Gson gson = new Gson();
    private String operator;
    List<MessageContainer> messageList;
    private Response resp;
    MessageContainer message;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        messageList = new ArrayList<>();
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        String user_action = request.getParameter("userAction");
        String callback = request.getParameter("callback");
        HttpSession session = request.getSession(true);
        //operator = session.getAttribute("userId").toString();
        try (PrintWriter out = response.getWriter()) {
            switch (user_action) {
                case "create":
                    json = gson.toJson(createNumberRange(request));
                    break;

            }
            if (callback != null && callback.length() > 1) {
                out.println(callback + "(" + json + ");");
            } else {
                out.println(json);
            }
        }
    }

    private Response createNumberRange(HttpServletRequest request) {
        String object = request.getParameter("objectType");
        String start = request.getParameter("startNumber");
        String end = request.getParameter("endNumber");
        return numberRangeBean.addRange(object, start, end);

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
