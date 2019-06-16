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
//import za.raretag.mawa.generic.PartnerQuery;
import za.raretag.mawa.beans.FieldOptionBeanLocal;
import za.raretag.mawa.beans.PartnerBeanLocal;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.generic.Contact;
import za.raretag.mawa.generic.Conversion;
import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.Employee;
import za.raretag.mawa.generic.Identity;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.PartnerQuery;
import za.raretag.mawa.generic.Person;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
public class PartnerServlet extends HttpServlet {

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
    private PartnerBeanLocal partnerBean;
    private GsonBuilder gsonBuilder;
    private String json;
    private final Gson gson = new Gson();
    private String operator;
    // List<MessageContainer> messageList;
    private Response resp;
    MessageContainer message;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try (PrintWriter out = response.getWriter()) {

            response.setContentType("application/json;charset=UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            String userAction = request.getParameter("userAction");
            String process = request.getParameter("process");
            String callback = request.getParameter("callback");
            HttpSession session = request.getSession(true);

            String roleType = request.getParameter("partnerType");
            String customerNo = request.getParameter("customerNo");
            String idnumber = request.getParameter("idNumber");
            String searchTerm = idnumber;
            String surname = request.getParameter("surname");
            String firstName = request.getParameter("firstName");
            String middleName = request.getParameter("middleName");
            String emailAddress = request.getParameter("email");
            String cellNumber = request.getParameter("cellphone");
            String alternateCellNumber = request.getParameter("alternative-cellphone");
            List<String> roleList = new ArrayList<>();
              if (roleType != null) {
                if (roleType.length() > 0) {
                    roleList.add(roleType);
                }
            }
            List<Identity> identityList = new ArrayList<>();
            if (idnumber != null) {
                if (idnumber.length() > 0) {
                    Identity ident = new Identity();
                    ident.setType("SAIDENTITY");
                    ident.setNumber(idnumber);
                    identityList.add(ident);
                }
            }

            List<Contact> contactList = new ArrayList<>();
            if (cellNumber != null) {
                Contact contact = new Contact();
                contact.setContactType("CEL1");
                contact.setContactValue(cellNumber);
                contactList.add(contact);
            }
            if (alternateCellNumber != null) {
                Contact contact = new Contact();
                contact.setContactType("CEL2");
                contact.setContactValue(alternateCellNumber);
                contactList.add(contact);
            }
            if (emailAddress != null) {
                Contact contact = new Contact();
                contact.setContactType("EMAIL");
                contact.setContactValue(emailAddress);
                contactList.add(contact);
            }

            switch (userAction) {
                case "create":
                    Person person = new Person();
                    person.setLastName(surname);
                    person.setFirstName(firstName);
                    person.setMiddleName(middleName);
                    person.setRoles(roleList);
                    person.setContacts(contactList);
                    person.setIdentity(identityList);
                    
                    json = gson.toJson(partnerBean.create(person));
                     
                    break;
                case "search":
                    PartnerQuery partnerQuery = new PartnerQuery();
                    partnerQuery.setSearchTerm(searchTerm);
                    json = gson.toJson(partnerBean.search(partnerQuery));
                    break;
                case "get":
                    json = gson.toJson(partnerBean.getCustomer(customerNo));
                    break;
            }

            if (callback != null && callback.length() > 1) {
                out.println(callback + "(" + json + ");");
            } else {
                out.println(json);
            }

        }
    }

//
//    private List<MessageContainer> addContact(Contact contact) {
//        List<MessageContainer> messageList = new ArrayList<>();
//        messageList.addAll(partnerBean.addContact(contact));
//        return messageList;
//    }
//    private String createPartner(HttpServletRequest request) {
//        String roleType = request.getParameter("roleType");
//        String idnumber = request.getParameter("idNumber");
//        String type = request.getParameter("partnerType");
//        String name1 = request.getParameter("surname");
//        String name2 = request.getParameter("firstname");
//        String name3 = request.getParameter("middlename");
//        String name4 = request.getParameter("partner[name4]");
//        String title = request.getParameter("title");
//        String gender = null;
//        if (idnumber != null) {
//            gender = Conversion.getGenderFromID(idnumber);
//        }
//
//        String maritalStatus = request.getParameter("marital-status");
//        String birthDate = request.getParameter("birth-date");
//
//        String cellNumber = request.getParameter("cellphone");
//        String alternateCellNumber = request.getParameter("alternative-cellphone");
//        String emailAddress = request.getParameter("email");
//
////        String resAddressLine1 = request.getParameter("partner[resAddressLine1]");
////        String resAddressLine2 = request.getParameter("partner[resAddressLine2]");
////        String resAddressLine3 = request.getParameter("partner[resAddressLine3]");
////        String resAddressLine4 = request.getParameter("partner[resAddressLine4]");
//        String customerNo = partnerBean.createPartner(idnumber, name1, name2, name3, type, title, gender, maritalStatus, birthDate, operator);
//
//        Partner partner = partnerBean.searchCustomer(customerNo);
//        if (partner != null) {
//            if (!"".equals(cellNumber) || cellNumber == null) {
//                partnerBean.addContact(partner, "CEL1", cellNumber, operator);
//            }
//            if (!"".equals(alternateCellNumber) || alternateCellNumber == null) {
//                partnerBean.addContact(partner, "CEL2", alternateCellNumber, operator);
//            }
//            if (!"".equals(emailAddress) || emailAddress == null) {
//                partnerBean.addContact(partner, "EMAI", emailAddress, operator);
//            }
//
//            //  partnerBean.addAddress(partner, "RESI", resAddressLine1, resAddressLine2, resAddressLine3, resAddressLine4, operator);
//        }
//
//        return customerNo;
//    }
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
