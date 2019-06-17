/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.eyedee.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import za.raretag.eyedee.generic.Cashup;
import za.raretag.eyedee.beans.CashupBeanLocal;
import za.raretag.eyedee.generic.CheckingQuery;
import za.raretag.eyedee.generic.Conversion;
import za.raretag.eyedee.generic.Deposit;
import za.raretag.eyedee.generic.Response;

/**
 *
 * @author tebogom
 */
public class CashupServlet extends HttpServlet {

    @EJB
    private CashupBeanLocal cashupBean;
    private String json;
    private final Gson gson = new Gson();
    String salesArea;
    String employeeResponsible;
    String receiptFrom;
    String receiptTo;
    String cashAmount;
    String depositAmount;

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
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        String userAction = request.getParameter("userAction");
        String callback = request.getParameter("callback");
        String checkingId = request.getParameter("checkingId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        HttpSession session = request.getSession(true);
        String user = session.getAttribute("userId").toString();
        JSONObject jsonObj = null;
        try (PrintWriter out = response.getWriter()) {

            // Object tst = request.("cash-up-bank-deposit");
            switch (userAction) {
                case "create":
                    String checkout = request.getParameter("checkout");
                    try {
                        jsonObj = new JSONObject(checkout);
                    } catch (JSONException ex) {
                        Logger.getLogger(CashupServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Cashup cashup = new Cashup(jsonObj);
                    cashup.setCreatedBy(user);
                    Response resp = cashupBean.create(cashup);
                    json = gson.toJson(resp);

                    break;
                case "search":
                    CheckingQuery checkoutQuery = new CheckingQuery();
                    checkoutQuery.setStartDate(startDate);
                    checkoutQuery.setEndDate(endDate);
                    json = gson.toJson(cashupBean.search(checkoutQuery));
                    break;
                case "get":
                    json = gson.toJson(cashupBean.get(checkingId));
                    break;
            }
            if (callback != null && callback.length() > 1) {
                out.println(callback + "(" + json + ");");
            } else {
                out.println(json);
            }

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
