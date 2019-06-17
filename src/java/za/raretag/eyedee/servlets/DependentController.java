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
import za.raretag.eyedee.beans.TransactionBeanLocal;
import za.raretag.eyedee.generic.Data;
import za.raretag.eyedee.generic.Dependent;
import za.raretag.eyedee.generic.Identity;
import za.raretag.eyedee.generic.MessageContainer;
import za.raretag.eyedee.generic.Policy;
import za.raretag.eyedee.generic.Request;
import za.raretag.eyedee.generic.Response;

/**
 *
 * @author tebogom
 */
public class DependentController extends HttpServlet {

    @EJB
    private TransactionBeanLocal transactionBean;
    private GsonBuilder gsonBuilder;
    private String json;
    private final Gson gson = new Gson();
    private String operator;
    // List<MessageContainer> messageList;
    private Response resp;
    MessageContainer message;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            String userAction = request.getParameter("userAction");
            String process = request.getParameter("process");
            String callback = request.getParameter("callback");
            HttpSession session = request.getSession(true);
            String user = session.getAttribute("userId").toString();
            String customerNo = request.getParameter("customerNo");
            String salesRepresentative = request.getParameter("employeeResponsible");
            String salesArea = request.getParameter("salesArea");
            String productCode = request.getParameter("productCode");
            String externalReceipt = request.getParameter("externalReceipt");
            String receiptDate = request.getParameter("receiptDate");
            String receiptNo = request.getParameter("receiptNo");
            String policyNo = request.getParameter("policyNo");
            String idnumber = request.getParameter("idNumber");
            String surname = request.getParameter("surname");
            String middleName = request.getParameter("middleName");
            String firstName = request.getParameter("firstName");
             List<Identity> identityList = new ArrayList<>();
            if (idnumber != null) {
                if (idnumber.length() > 0) {
                    Identity ident = new Identity();
                    ident.setType("SAIDENTITY");
                    ident.setNumber(idnumber);
                    identityList.add(ident);
                }
            }
            switch (userAction) {
                case "create":                   
                    Dependent dependent = new Dependent();
                    dependent.setIdNumber(idnumber);
                    dependent.setLastName(surname);
                    dependent.setFirstName(firstName);
                    dependent.setMiddleName(middleName);
                    dependent.setPolicyNo(policyNo);
                    dependent.setIdentity(identityList);
                    Request addDependentRequest = new Request();
                    Data<Dependent> dependentData = new Data();
                    dependentData.set(dependent);
                    addDependentRequest.setData(dependentData);
                    addDependentRequest.setRequester(user);                    
                    json = gson.toJson(transactionBean.addDependent(addDependentRequest));
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
