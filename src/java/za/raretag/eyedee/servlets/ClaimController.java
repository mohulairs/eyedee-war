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
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.raretag.mawa.beans.PartnerBeanLocal;
import za.raretag.mawa.beans.TransactionBeanLocal;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.generic.BankAccount;
import za.raretag.mawa.generic.OrderQuery;
import za.raretag.mawa.generic.Conversion;
import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.Dependent;
import za.raretag.mawa.generic.Identity;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Order;
import za.raretag.mawa.generic.OrderArea;
import za.raretag.mawa.generic.OrderDate;
import za.raretag.mawa.generic.OrderItem;
import za.raretag.mawa.generic.OrderPartner;
import za.raretag.mawa.generic.Person;
import za.raretag.mawa.generic.ReferenceDocument;
import za.raretag.mawa.generic.Request;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
public class ClaimController extends HttpServlet {

    @EJB
    private PartnerBeanLocal partnerBean;

    @EJB
    private TransactionBeanLocal transactionBean;
    private GsonBuilder gsonBuilder;
    private String json;
    private final Gson gson = new Gson();
    private String operator;
    List<MessageContainer> messageList;
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
            String user = session.getAttribute("userId").toString();
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            String policyNo = request.getParameter("policyNo");
            String mainMember = null;
            Dependent dependent = new Dependent();
            List<ReferenceDocument> refDocs = new ArrayList<>();
            if (policyNo != null) {
                ReferenceDocument refDoc = new ReferenceDocument();
                refDoc.setDocType("FUNERALPOLICY");
                refDoc.setDocID(policyNo);
                refDocs.add(refDoc);

                Response searchResponse = transactionBean.getTransactionPartner(policyNo, "MAINMEMBER");
                Data<Partner> partnerData = searchResponse.getData();
                mainMember = partnerData.get().getPartnerNo();
            }

            String claimType = request.getParameter("claimType");
            List<OrderItem> itemList = new ArrayList<>();
            if (claimType != null) {
                OrderItem orderItem = new OrderItem();
                orderItem.setCode(claimType);
                orderItem.setQuantity("1");
                itemList.add(orderItem);
            }

            String deathDate = request.getParameter("deathDate");
            List<OrderDate> dateList = new ArrayList<>();
            if (deathDate != null) {
                OrderDate orderDate = new OrderDate();
                orderDate.setType("DEATHDATE");
                orderDate.setValue(Conversion.stringToDate(deathDate));
                dateList.add(orderDate);
            }

            String serviceDate = request.getParameter("serviceDate");
            if (serviceDate != null) {
                OrderDate orderDate = new OrderDate();
                orderDate.setType("SERVICEDATE");
                orderDate.setValue(Conversion.stringToDate(serviceDate));
                dateList.add(orderDate);
            }

            OrderDate orderDate = new OrderDate();
            orderDate.setType("SUBMISSIONDATE");
            orderDate.setValue(new Date());
            dateList.add(orderDate);

            String grocery = request.getParameter("groceryClaim");
            if ("DELIVERY".equals(grocery)) {
                OrderItem orderItem = new OrderItem();
                orderItem.setCode("GROCERY");
                orderItem.setQuantity("1");
                itemList.add(orderItem);
            }

            String claimantIDNumber = request.getParameter("claimantIdNumber");
            String claimantSurname = request.getParameter("claimantSurname");
            String claimantFirstName = request.getParameter("claimantFirstName");
            String claimantMiddleName = request.getParameter("claimantMiddleName");
            String claimantContactNo = request.getParameter("claimantContactNumber");
            String claimBy = request.getParameter("claimBy");
            List<OrderPartner> partnerList = new ArrayList<>();
            if ("OTHER".equals(claimBy)) {
                OrderPartner orderPartner = new OrderPartner();
                orderPartner.setType("CLAIMANT");
                orderPartner.setLastName(claimantSurname);
                orderPartner.setFirstName(claimantFirstName);
                orderPartner.setMiddleName(claimantMiddleName);
                orderPartner.setCellNumber(claimantContactNo);
                if (claimantIDNumber != null && claimantIDNumber.length() > 0) {
                    orderPartner.setIdNumber(claimantIDNumber);
                }
                partnerList.add(orderPartner);
            } else {
                if (mainMember != null) {
                    OrderPartner orderPartner = new OrderPartner();
                    orderPartner.setType("CLAIMANT");
                    orderPartner.setPartnerNo(mainMember);
                    partnerList.add(orderPartner);
                }
            }

            String deceased = request.getParameter("deceased");
            if (deceased != null) {
                OrderPartner orderPartner = new OrderPartner();
                orderPartner.setType("DECEASED");
                orderPartner.setPartnerNo(deceased);
                partnerList.add(orderPartner);
                dependent.setId(deceased);
                dependent.setPolicyNo(policyNo);

            }
            if (mainMember != null) {
                OrderPartner orderPartner = new OrderPartner();
                orderPartner.setType("MAINMEMBER");
                orderPartner.setPartnerNo(mainMember);
                partnerList.add(orderPartner);
            }

            if (user != null) {
                OrderPartner orderPartner = new OrderPartner();
                orderPartner.setType("CONSULTANT");
                orderPartner.setPartnerNo(user);
                partnerList.add(orderPartner);
            }

            String payoutMethod = request.getParameter("payoutMethod");
            String bankAccountHolder = request.getParameter("accountHolder");
            String bankAccountHolderIDNumber = request.getParameter("accountHolderIdNumber");
            String bankName = request.getParameter("bankName");
            String bankAccountType = request.getParameter("accountType");
            String bankAccountNumber = request.getParameter("accountNumber");
            List<BankAccount> bankAccountList = new ArrayList<>();
            if ("EFT".equals(payoutMethod)) {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setUsageType("CASHPAYOUT");
                bankAccount.setAccountHolder(bankAccountHolder);
                bankAccount.setAccountHolderIDNumber(bankAccountHolderIDNumber);
                bankAccount.setAccountType(bankAccountType);
                bankAccount.setBankName(bankName);
                bankAccount.setAccountNumber(bankAccountNumber);
                bankAccountList.add(bankAccount);
            }

            String collectionOffice = request.getParameter("collectionOffice");
            List<OrderArea> areaList = new ArrayList<>();
            if (collectionOffice != null) {
                OrderArea area = new OrderArea();
                area.setType("COLLECTIONOFFICE");
                area.setAreaID(collectionOffice);
                areaList.add(area);
            }
            String submissionOffice = request.getParameter("submissionOffice");
            if (submissionOffice != null) {
                OrderArea area = new OrderArea();
                area.setType("SUBMISSIONOFFICE");
                area.setAreaID(submissionOffice);
                areaList.add(area);
            }

            switch (userAction) {
                case "create":
                    Order order = new Order();
                    order.setType("CLAIM");
                    order.setAreas(areaList);
                    order.setDates(dateList);
                    order.setPartners(partnerList);
                    order.setItems(itemList);
                    order.setReferenceDocuments(refDocs);
                    order.setBankAccounts(bankAccountList);

                    Request createRequest = new Request();
                    Data<Order> requestData = new Data();
                    requestData.set(order);
                    createRequest.setData(requestData);
                    createRequest.setRequester(user);

                    Request deactivateDependentRequest = new Request();
                    Data<Dependent> dependentData = new Data();
                    dependentData.set(dependent);
                    deactivateDependentRequest.setData(dependentData);
                    deactivateDependentRequest.setRequester(user);
                    Response createResponse = transactionBean.create(createRequest);
                    Response deactivateResponse = transactionBean.deactivateDependent(deactivateDependentRequest);
                    createResponse.getMessages().addAll(deactivateResponse.getMessages());
                    json = gson.toJson(createResponse);

                    break;
                case "search":
                    OrderQuery orderQuery = new OrderQuery();
                    orderQuery.setStartDate(startDate);
                    orderQuery.setEndDate(endDate);
                    json = gson.toJson(transactionBean.search(orderQuery));
                    break;
                case "getDependents":
                    json = gson.toJson(transactionBean.getTransaction(policyNo));
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
