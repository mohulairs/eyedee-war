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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.raretag.eyedee.beans.OrderBeanLocal;
import za.raretag.eyedee.beans.PartnerBeanLocal;
import za.raretag.eyedee.beans.ProductBeanLocal;
import za.raretag.eyedee.beans.TransactionBeanLocal;
import za.raretag.eyedee.generic.Conversion;
import za.raretag.eyedee.generic.Customer;
import za.raretag.eyedee.generic.Data;
import za.raretag.eyedee.generic.Dependent;
import za.raretag.eyedee.generic.Identity;
import za.raretag.eyedee.generic.Item;
import za.raretag.eyedee.generic.MessageContainer;
import za.raretag.eyedee.generic.Order;
import za.raretag.eyedee.generic.OrderArea;
import za.raretag.eyedee.generic.OrderDate;
import za.raretag.eyedee.generic.OrderItem;
import za.raretag.eyedee.generic.OrderPartner;
import za.raretag.eyedee.generic.OrderPayment;
import za.raretag.eyedee.generic.PartnerQuery;
import za.raretag.eyedee.generic.Payment;
import za.raretag.eyedee.generic.Person;
import za.raretag.eyedee.generic.Policy;
import za.raretag.eyedee.generic.Request;
import za.raretag.eyedee.generic.Response;

/**
 *
 * @author tebogom
 */
public class PolicyServlet extends HttpServlet {

    @EJB
    private ProductBeanLocal productBean;

    @EJB
    private PartnerBeanLocal partnerBean;

    @EJB
    private TransactionBeanLocal transactionBean;

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
            String amount = request.getParameter("paymentAmount");
            String receiptDate = request.getParameter("receiptDate");
            String receiptNo = request.getParameter("receiptNumber");
            String policyNo = request.getParameter("policyNo");
            String idNumber = request.getParameter("idNumber");
            String surname = request.getParameter("surname");
            String middleName = request.getParameter("middleName");
            String firstName = request.getParameter("firstName");
            String paymentPeriod = request.getParameter("paymentPeriod");

            switch (userAction) {
                case "create":
                    List<OrderPartner> partnerList = new ArrayList<>();
                    OrderPartner mainMember = new OrderPartner();
                    mainMember.setPartnerNo(customerNo);
                    mainMember.setIdNumber(idNumber);
                    mainMember.setLastName(surname);
                    mainMember.setFirstName(firstName);
                    mainMember.setMiddleName(middleName);
                    mainMember.setType("MAINMEMBER");
                    partnerList.add(mainMember);

                    OrderPartner salesRep = new OrderPartner();
                    salesRep.setPartnerNo(salesRepresentative);
                    salesRep.setType("SALESREPRESENTATIVE");
                    partnerList.add(salesRep);

                    List<OrderArea> areaList = new ArrayList<>();
                    OrderArea orderArea = new OrderArea();
                    orderArea.setAreaID(salesArea);
                    orderArea.setType("SALESAREA");
                    areaList.add(orderArea);

                    List<OrderItem> itemList = new ArrayList<>();
                    OrderItem orderItem = new OrderItem();
                    orderItem.setCode(productCode);
                    orderItem.setQuantity("1");
                    itemList.add(orderItem);

                    List<OrderPayment> paymentList = new ArrayList<>();
                    OrderPayment orderPayment = new OrderPayment();
                    orderPayment.setPaymentDate(receiptDate);
                    orderPayment.setReceiptNo(receiptNo);
                    orderPayment.setAmount(amount);
                    orderPayment.setPaymentPeriod(paymentPeriod);
                    orderPayment.setReceivedBy(salesRepresentative);
                    paymentList.add(orderPayment);

                    List<OrderDate> dateList = new ArrayList<>();
                    OrderDate orderDate = new OrderDate();
                    orderDate.setType("DATEJOINED");
                    orderDate.setValue(new Date());
                    dateList.add(orderDate);
                    orderDate = new OrderDate();
                    orderDate.setType("DATEEFFECTIVE");
                    Date dateEffective = Conversion.addMonthsToDate(new Date(), Integer.parseInt(productBean.getProductAttribute(productCode, "WAITINGPERIOD")));
                    orderDate.setValue(dateEffective);
                    dateList.add(orderDate);

                    Order order = new Order();
                    order.setType("FUNERALPOLICY");
                    order.setPartners(partnerList);
                    order.setAreas(areaList);
                    order.setItems(itemList);
                    order.setPayments(paymentList);
                    order.setDates(dateList);

                    Request createRequest = new Request();
                    Data<Order> requestData = new Data();
                    requestData.set(order);
                    createRequest.setData(requestData);
                    createRequest.setRequester(user);
                    json = gson.toJson(transactionBean.create(createRequest));

                    break;
                case "search":
                    messageList = new ArrayList<>();
                    List<Policy> policies = new ArrayList<>();
                    Data<List<Policy>> data = new Data<>();
                    PartnerQuery partnerQuery = new PartnerQuery();
                    partnerQuery.setSearchTerm(idNumber);
                    Response searchResponse = partnerBean.search(partnerQuery);
                    Customer foundCustomer = null;
                    Data<List<Customer>> custResponseList = searchResponse.getData();
                    List<Customer> custList = custResponseList.get();
                    if (custList.size() > 0) {
                        foundCustomer = custList.get(0);
                        policies = foundCustomer.getPolicies();
                        data.set(policies);
                    }
                    if (policies.isEmpty()) {
                        MessageContainer mess = new MessageContainer("E", "No records found");
                        messageList.add(mess);
                    }
                    json = gson.toJson(new Response(data, messageList));
                    break;
                case "get":          
                case "getDependents":
                    json = gson.toJson(transactionBean.getTransaction(policyNo));
                    break;
                case "addDependent":
                    List<Identity> identityList = new ArrayList<>();
                    if (idNumber != null) {
                        if (idNumber.length() > 0) {
                            Identity ident = new Identity();
                            ident.setType("SAIDENTITY");
                            ident.setNumber(idNumber);
                            identityList.add(ident);
                        }
                    }
                    Dependent dependent = new Dependent();
                    dependent.setIdNumber(idNumber);
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
                case "submitClaim":

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
