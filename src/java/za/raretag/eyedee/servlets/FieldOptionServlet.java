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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.raretag.eyedee.beans.FieldOptionBeanLocal;
import za.raretag.eyedee.generic.Data;
import za.raretag.eyedee.generic.FieldOption;
import za.raretag.eyedee.generic.MessageContainer;
import za.raretag.eyedee.generic.Response;

/**
 *
 * @author tebogom
 */
public class FieldOptionServlet extends HttpServlet {

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
    private FieldOptionBeanLocal fieldOptionBean;
    private GsonBuilder gsonBuilder;
    private String json;
    private final Gson gson = new Gson();
    private String operator;
    List<MessageContainer> messageList;
    private Response resp;
    MessageContainer message;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {
            messageList = new ArrayList<>();
            response.setContentType("application/json;charset=UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            String user_action = request.getParameter("userAction");
            String callback = request.getParameter("callback");
            HttpSession session = request.getSession(true);
//            String json;
            try {
                operator = session.getAttribute("userId").toString();
            } catch (Exception ex) {
                message = new MessageContainer("E", "User not logged on");
                messageList.add(message);
                resp = new Response(messageList);
                json = gson.toJson(resp);
            }
            switch (user_action) {
                case "createFieldOption":
                    json = gson.toJson(createFieldOptions(request));
                    break;
                case "getFieldOptions":
                    json = gson.toJson(getFieldOptions());
                    break;
            }
            if (callback != null && callback.length() > 1) {
                out.println(callback + "(" + json + ");");
            } else {
                out.println(json);
            }

        }
//        catch (Exception ex) {
//            out.println(gson.toJson(new MessageContainer(ex.getMessage())));
//        }
    }

    private Response createFieldOptions(HttpServletRequest request) {

        String tableName = request.getParameter("tableName");
        tableName = "_config_"+tableName;
        String desc = request.getParameter("description");

        return fieldOptionBean.createFieldOption(tableName, desc);

    }

    private Response getFieldOptions() {
//        Data<List<FieldOption>> data = new Data();
//        List<FieldOption> options = fieldOptionBean.getFieldOptions();
//        data.set(options);
//        resp.setData(data);
        return fieldOptionBean.getFieldOptions();
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
