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
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.raretag.eyedee.beans.ProductBeanLocal;
import za.raretag.eyedee.generic.Item;
import za.raretag.eyedee.generic.MessageContainer;
import za.raretag.eyedee.generic.Response;

/**
 *
 * @author tebogom
 */
public class ProductServlet extends HttpServlet {
    
    @EJB
    private ProductBeanLocal productBean;
    private GsonBuilder gsonBuilder;
    private String json;
    private final Gson gson = new Gson();
    private String operator;
    // List<MessageContainer> messageList;
    private Response resp;
    MessageContainer message;

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            String userAction = request.getParameter("userAction");
            String process = request.getParameter("process");
            String callback = request.getParameter("callback");
            HttpSession session = request.getSession(true);
            
            String productCategory = request.getParameter("productCategory");
            String productCode = request.getParameter("productCode");
            String productDescription = request.getParameter("productDescription");
            String unitPrice = request.getParameter("unitPrice");
            switch (userAction) {
                case "create":
                    Item item = new Item();
                    item.setProductCode(productCode);
                    item.setDescription(productDescription);
                    item.setProductCategory(productCategory);
                    item.setPrice(new BigDecimal(unitPrice.replace(",", "")));
                    Response resp = productBean.addProduct(item);
                    json = gson.toJson(resp);
                    break;
                case "get":
                    break;
                case "getList":
                    json = gson.toJson(productBean.getProductList());
                    break;
                case "getCategoryProducts":
                    json = gson.toJson(productBean.getProductListByCategory(productCategory));
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
