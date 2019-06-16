/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.eyedee.servlets;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.BadElementException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import za.raretag.mawa.beans.*;
import za.raretag.mawa.generic.*;

/**
 *
 * @author tebogom
 */
public class Controller extends HttpServlet {

    @EJB
    private NumberRangeBeanLocal numberRangeBean;

//    @EJB
//    private ClaimBeanLocal claimBean;
//
//    @EJB
//    private PolicyBeanLocal policyBean;
//
    @EJB
    private UserBeanLocal userBean;
//    @EJB
//    private ReceiptRegisterBeanLocal receiptRegisterBean;
//
    @EJB
    private PartnerBeanLocal partnerBean;

//    @EJB
//    private OrderBeanLocal orderBean;
    @EJB
    private FieldOptionBeanLocal fieldOptionBean;
    private GsonBuilder gsonBuilder;
    private String json;
    private Gson gson = new Gson();
    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private String operator;
    private Response resp;

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
        String user_action = request.getParameter("userAction");
        String callback = request.getParameter("callback");

        HttpSession session = request.getSession(true);
        String user = session.getAttribute("userId").toString();

        try (PrintWriter out = response.getWriter()) {
            switch (user_action) {
                case "getTemplates":
                    response.setContentType("application/json;charset=UTF-8");
//                    ArrayList<String> files = getTemplates(request);
//                    String body = "<ul>";
//                    for (String file : files) {
//                        body = body + "<li><a href='./templates/" + file + "'>" + file + "</a></li>";

                    ArrayList<String> docList = getTemplates(request);
                    if (callback != null && callback.length() > 1) {
                        out.println(callback + "(" + (gson.toJson(docList)) + ");");
                    } else {
                        out.println(gson.toJson(docList));
                    }

//                    body = body + "</ul>";
//                    out.println(body);
                    break;
                case "createUser":
//                    out.println(createUser(request));
                    break;
//                case "createNumberRange":
//                    out.println(createNumberRange(request));
//                    break;
                case "getFieldOptions":
                    json = gson.toJson(fieldOptionBean.getFieldOptions());
                    break;
                case "createFieldOption":
//                    out.println(createFieldOptions(request));
                    break;

                case "getWorkcenters":
                    json = gson.toJson(userBean.getUserWorkcenter(user));
                    break;
            }

            if (callback != null && callback.length() > 1) {
                out.println(callback + "(" + json + ");");
            } else {
                out.println(json);
            }

        }
    }

    private String cashUp(HttpServletRequest request) throws IOException, ServletException {
        String salesArea = request.getParameter("sales-area");
        String employeeResponsible = request.getParameter("employee-responsible");
        String receiptFrom = request.getParameter("receipt-from");
        String receiptTo = request.getParameter("receipt-to");
        String cashAmount = request.getParameter("cash-amount");
        String depositAmount = request.getParameter("deposit-amount");
        Part depositCopy = request.getPart("deposit-copy");

        return "";
    }

//    private String createUser(HttpServletRequest request) {
//        try {
////            String employeeNo = createPartner(request);
//            String username = request.getParameter("username");
//            String password = request.getParameter("password");
//            String role = request.getParameter("role");
//            List<String> roles = new ArrayList<>();
//            roles.add(role);
//            userBean.createUser(username, password, employeeNo, roles, operator);
//        } catch (Exception ex) {
//            return "Error occured";
//        }
//        return "User created succesfully";
//    }
//    private String createPartner(HttpServletRequest request) {
//        String idnumber = request.getParameter("id-number");
//        String type = request.getParameter("partner-type");
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
    private ArrayList<String> getTemplates(HttpServletRequest request) {
        ArrayList<String> fileNames = new ArrayList<>();
//        File folder = new File("\\\\acuteline.dedicated.co.za\\phamoha\\online folder");
        File folder = new File("X:\\ONLINE FOLDER");
        File[] listOfFiles = folder.listFiles();
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                System.out.println("File " + listOfFile.getName());

            } else if (listOfFile.isDirectory()) {
                System.out.println("Directory " + listOfFile.getName());
            }
            fileNames.add(listOfFile.getName());
        }

        return fileNames;

    }

//    private String createNumberRange(HttpServletRequest request) {
//        try {
//
//            String object = request.getParameter("object-type");
//            String start = request.getParameter("start-number");
//            String end = request.getParameter("end-number");
//            boolean success = numberRangeBean.addRange(object, start, end);
//            if (!success) {
//                return "Number range creation unsuccesful";
//            } else {
//                return "Number range creation succesful";
//            }
//
//        } catch (Exception ex) {
//            return "Number range creation unsuccesful";
//        }
//
//    }
//
//    private String searchCustomer(HttpServletRequest request) {
//        String idnumber = request.getParameter("idnumber");
//        List<Customer> customer = new ArrayList<>();
////        List<Customer> customer = partnerBean.getCustomerByID(idnumber);
//        int count = 1;
//        while (count < 11) {
//            Customer cust = new Customer();
//            cust.setCustomerNo("23232322");
//            cust.setIdNumber("827382763287");
//            cust.setFullname("Test the Screen");
//            customer.add(cust);
//            count++;
//        }
//        gson = new Gson();
//        return gson.toJson(customer);
//    }
//
//    private String createOrder(HttpServletRequest request) {
//        String salesArea = request.getParameter("order[salesArea]");
//        String customerNo = request.getParameter("order[customerNo]");
//        String transactionType = request.getParameter("order[transactionType]");
//        String employeeResponsible = request.getParameter("order[employeeResponsible]");
//        Integer itemCount = Integer.parseInt(request.getParameter("order[itemCount]"));
//        String previousInsurer = request.getParameter("order[previousInsurer]");
//        Orderheader createdOrder = null;
//
//        if ("T001".equals(transactionType)) {
//            createdOrder = policyBean.create(customerNo, employeeResponsible, salesArea, previousInsurer, user);
//        } else {
//            createdOrder = orderBean.createTransaction(customerNo, transactionType, salesArea, employeeResponsible, user);
//        }
//
//        if (createdOrder != null) {
//            for (int i = 0; i < itemCount; i++) {
//                String product = request.getParameter("order[items][" + i + "][productCode]");
//                createdOrder = orderBean.addItem(createdOrder.getOrderId(), product, 1, user);
//            }
//
//        }
//
//        return convertOrderheader(createdOrder);
//
//    }
//
//    private String searchOrder(HttpServletRequest request) {
//        String localJson = null;
//        String orderType = request.getParameter("orderQuery[orderType]");
//        String idnumber = request.getParameter("orderQuery[idnumber]");
//        String orderNo = request.getParameter("orderQuery[orderno]");
//        String groupName = request.getParameter("orderQuery[groupname]");
//        String surname = request.getParameter("orderQuery[surname]");
//        String email = request.getParameter("orderQuery[email]");
//        String cellphone = request.getParameter("orderQuery[cellphone]");
//        String searchType = "";
//
//        if (idnumber.length() > 0) {
//            searchType = "ID";
//        } else if (orderNo.length() > 0) {
//            searchType = "ORDER";
//        } else if (groupName.length() > 0) {
//            searchType = "GROUPNAME";
//        } else if (surname.length() > 0) {
//            searchType = "SURNAME";
//        } else if (email.length() > 0) {
//            searchType = "EMAIL";
//        } else if (cellphone.length() > 0) {
//            searchType = "CELL";
//        }
//
//        switch (searchType) {
//            case "ID":
//                localJson = searchOrderByIdentity(idnumber);
//                break;
//            case "ORDER":
//                localJson = searchOrderByNo(orderNo);
//                break;
//            case "GROUPNAME":
//                localJson = searchOrderByName(groupName);
//                break;
//            case "SURNAME":
//                localJson = searchOrderByName(surname);
//                break;
//            case "EMAIL":
//                localJson = searchByContact(email);
//                break;
//            case "CELL":
//                localJson = searchByContact(cellphone);
//                break;
//            default:
//
//        }
//
//        json = localJson;
//        return json;
//    }
//
//    private String searchOrderByIdentity(String idnumber) {
//        List<Order> orderList = new ArrayList<>();
//        orderList = orderBean.getOrderListByCustomerID(idnumber);
//        gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Order.class, new OrderSerializer());
//        gson = gsonBuilder.create();
//        json = gson.toJson(orderList);
//        return json;
//    }
//
//    private String searchOrderByNo(String orderno) {
//        List<Order> orderList = new ArrayList<>();
//        orderList = orderBean.searchByOrderNo(orderno);
//        gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Order.class, new OrderSerializer());
//        gson = gsonBuilder.create();
//        json = gson.toJson(orderList);
//        return json;
//    }
//
//    private String searchOrderByName(String name) {
//        List<Order> orderList = new ArrayList<>();
//        orderList = orderBean.searchByName(name, "");
//        gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Order.class, new OrderSerializer());
//        gson = gsonBuilder.create();
//        json = gson.toJson(orderList);
//        return json;
//    }
//
//    private String searchByContact(String contact) {
//        List<Order> orderList = new ArrayList<>();
//        orderList = orderBean.searchByContact(contact);
//        gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Order.class, new OrderSerializer());
//        gson = gsonBuilder.create();
//        json = gson.toJson(orderList);
//        return json;
//    }
//
////       private String searchByEmail(String email) {
////        List<Order> orderList = new ArrayList<>();
////        orderList = orderBean.searchByEmail(email);
////        gsonBuilder = new GsonBuilder();
////        gsonBuilder.registerTypeAdapter(Order.class, new OrderSerializer());
////        gson = gsonBuilder.create();
////        json = gson.toJson(orderList);
////        return json;
////    }
//    private String getOrder(HttpServletRequest request) {
//        String orderNo = request.getParameter("orderQuery[orderno]");
//        gsonBuilder = new GsonBuilder();
//        Orderheader orderHeader = orderBean.getContract(orderNo);
//        return convertOrderheader(orderHeader);
//
//    }
//
//    private String getOrder(String orderNo) {
//        gsonBuilder = new GsonBuilder();
//        Orderheader orderHeader = orderBean.getContract(orderNo);
//        return convertOrderheader(orderHeader);
//
//    }
//
//    private String payOrder(HttpServletRequest request) {
//        String localJson = null;
//        String referenceNumber = request.getParameter("payment[referenceNumber]");
//        String paymentPeriod = request.getParameter("payment[paymentType]");
//        String amount = request.getParameter("payment[amount]");
//        String receiptDate = request.getParameter("payment[receiptDate]");
//        String receiptNumber = request.getParameter("payment[receiptNumber]");
//        String receivedBy = request.getParameter("payment[receivedBy]");
//        Boolean success = orderBean.payOrder(referenceNumber, receiptNumber, receiptDate, paymentPeriod, receivedBy, new BigDecimal(amount), user);
//        if (success == true) {
//            localJson = getOrder(referenceNumber);
//        }
//        return localJson;
//    }
//
//    private String addDependant(HttpServletRequest request) {
//        String localJson = null;
//        String idNumber = request.getParameter("dependant[idNumber]");
//        String surname = request.getParameter("dependant[surname]");
//        String firstname = request.getParameter("dependant[firstname]");
//        String middlename = request.getParameter("dependant[middlename]");
//        String dateJoined = request.getParameter("dependant[dateJoined]");
//        String birthDate = request.getParameter("dependant[birthDate]");
//        String addType = request.getParameter("dependant[type]");
//        String policyNo = request.getParameter("dependant[orderNo]");
//        String dependant = partnerBean.createPartner(idNumber, surname, firstname, middlename, "CUST", "", "", "", birthDate, user);
//        //Orderheader policy = orderBean.getOrder(policyNo);
//        Boolean success = policyBean.addDependant(policyNo, dependant, addType, dateJoined, user);
//        if (success == true) {
//            localJson = getOrder(policyNo);
//        }
//        return localJson;
//    }
//
//    private String replaceDependant(HttpServletRequest request) {
//        String localJson = null;
//        String policyNo = request.getParameter("orderNo");
//        String removeDependant = request.getParameter("dependant");
//        Boolean success = policyBean.updateDependantStatus(policyNo, removeDependant, "INAC", "REPL", user);
//        if (success == true) {
//            localJson = getOrder(policyNo);
//        }
//        return localJson;
//    }
//
//    private String changeProduct(HttpServletRequest request) {
//        String localJson = null;
//        String policyNo = request.getParameter("policy[orderNo]");
//        String newFuneralPlan = request.getParameter("newFuneralPlan");
//        Orderheader order = policyBean.upgrade(policyNo, newFuneralPlan, user);
//
//        return convertOrderheader(order);
//    }
//
//    private String convertOrderheader(Orderheader orderHeader) {
//        String convertedOrderHeader = null;
//        switch (orderHeader.getOrderType()) {
//            case "T001":
//                Policy policy = new Policy(orderHeader);
//                gsonBuilder = new GsonBuilder();
//                gsonBuilder.registerTypeAdapter(Policy.class, new PolicySerializer());
//                gson = gsonBuilder.create();
//                convertedOrderHeader = gson.toJson(policy);
//                break;
//            case "T002":
//                break;
//            case "T003":
//                break;
//            case "T004":
//                break;
//            default:
//                break;
//        }
//        return convertedOrderHeader;
//    }
//
//    private String convertOrderheader(List<Orderheader> orderHeaderList) {
//        String stringOrderHeader = null;
//        for (Orderheader orderHeader : orderHeaderList) {
//            switch (orderHeader.getOrderType()) {
//                case "T001":
//                    Policy policy = new Policy(orderHeader);
//                    gsonBuilder.registerTypeAdapter(Policy.class, new PolicySerializer());
//                    gsonBuilder.create();
//                    gson = new Gson();
//                    stringOrderHeader = gson.toJson(policy);
//                    break;
//                case "T002":
//                    break;
//                case "T003":
//                    break;
//                case "T004":
//                    break;
//                default:
//                    break;
//            }
//        }
//        return stringOrderHeader;
//    }
//
//    private String logReceiptUsage(HttpServletRequest request) {
//        String localJson = null;
//        Enumeration<String> par = request.getParameterNames();
//        Map<String, String[]> map = request.getParameterMap();
//        String salesArea = request.getParameter("receiptRegister[salesArea]");
//        String treasurer = request.getParameter("receiptRegister[treasurer]");
//        String firstReceipt = request.getParameter("receiptRegister[firstReceipt]");
//        String lastReceipt = request.getParameter("receiptRegister[lastReceipt]");
//        String dateFrom = request.getParameter("receiptRegister[dateFrom]");
//        String dateTo = request.getParameter("receiptRegister[dateTo]");
//        String amountReceived = request.getParameter("receiptRegister[amountReceived]");
//        String amountDeducted = request.getParameter("receiptRegister[amountDeducted]");
//        String amountDeposited = request.getParameter("receiptRegister[amountDeposited]");
//        Receiptregister recReg = new Receiptregister(salesArea + firstReceipt + lastReceipt);
//        Date date;
//        try {
//            date = formatter.parse(dateFrom);
//            recReg.setDateFrom(date);
//        } catch (ParseException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            date = formatter.parse(dateTo);
//            recReg.setDateTo(date);
//        } catch (ParseException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        int itemCount = Integer.parseInt(request.getParameter("receiptRegister[deductionCount]"));
//
//        recReg.setAmountReceived(new BigDecimal(amountReceived));
//        recReg.setAmountDeducted(new BigDecimal(amountDeducted));
//        //recReg.setAmountDeposited(new BigDecimal(amountDeducted));
////        recReg.setEmployeeResponsible(partnerBean.searchCustomer(treasurer));
//        recReg.setReceiptFirst(firstReceipt);
//        recReg.setReceiptLast(lastReceipt);
//        //     recReg.setSalesArea(fieldOptionBean.getConfigSalesArea(salesArea));
//        //     recReg.setCreatedBy(userBean.getUser(user).getEmployeeNo());
//        recReg.setCreatedOn(new Date());
//
//        boolean created = receiptRegisterBean.create(recReg, user);
//
//        if (created) {
//            for (int i = 0; i < itemCount; i++) {
//                Receiptregisterdeduction deduction = new Receiptregisterdeduction();
//
//                deduction.setDeductionReason(request.getParameter("receiptRegister[deductions][" + i + "][deductionCode]"));
//                deduction.setEmployeeResponsible(request.getParameter("receiptRegister[deductions][" + i + "][employeeResponsible]"));
//                try {
//
//                    date = formatter.parse(request.getParameter("receiptRegister[deductions][" + i + "][date]"));
//                    deduction.setDeductionDate(date);
//                } catch (ParseException ex) {
//                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                deduction.setAmount(new BigDecimal(request.getParameter("receiptRegister[deductions][" + i + "][amount]")));
//                deduction.setCreatedBy(userBean.getUser(user).getEmployeeNo().getPartnerNo());
//                deduction.setCashRegisterId(recReg.getCashRegisterId());
//                deduction.setCreatedOn(new Date());
//                receiptRegisterBean.addDeduction(deduction, user);
//            }
//
//            localJson = "{\"ReferenceNumber\":\"" + recReg.getCashRegisterId() + "\"}";
//
//        } else {
//            localJson = "{\"ReferenceNumber\":\"" + "" + "\"}";
//
//        }
//
//        return localJson;
//    }
//
    private String getFieldOptions() {
        Response options = fieldOptionBean.getFieldOptions();
        gson = new Gson();
        return gson.toJson(options);
    }

//    private boolean createFieldOptions(HttpServletRequest request) {
//        String tableName = request.getParameter("table_name");
//        String desc = request.getParameter("option_desc");
//        return fieldOptionBean.createFieldOption(tableName, desc);
//
//    }
//
//    private String saveEmployee(HttpServletRequest request) {
//        String idnumber = request.getParameter("employee[idnumber]");
//        String role = request.getParameter("employee[role]");
//        String name1 = request.getParameter("employee[name1]");
//        String name2 = request.getParameter("employee[name2]");
//        String name3 = request.getParameter("employee[name3]");
//        String name4 = request.getParameter("employee[name4]");
//        String username = request.getParameter("employee[username]");
//        String gender = request.getParameter("employee[gender]");
//        String maritalStatus = request.getParameter("employee[maritalStatus]");
//        String employeeNo = partnerBean.createPartner(idnumber, name1, name2, "", "EMPL", "", "", "", "", user);
//
//        List<String> roles = new ArrayList<>();
//        roles.add(role);
//        userBean.createUser(username, "abc123", employeeNo, roles, user);
//        gson = new Gson();
//        return gson.toJson(employeeNo);
//    }
//
//    private String createClaim(HttpServletRequest request) throws BadElementException, IOException {
//        Claim newClaim = new Claim();
//        String claimNo = claimBean.create(newClaim);
//
//        newClaim.setClaimNo(claimNo);
////        generateClaimForm(newClaim);
//        return gson.toJson(claimNo);
//    }
//
//    private String searchReceiptUsage(HttpServletRequest request) {
//
//        return gson.toJson(receiptRegisterBean.getAllReceiptRegisters());
//
//    }
//
////    private void generateClaimForm(Claim claim) throws BadElementException, IOException {
////        Document document = new Document();
////        try {
////            PdfWriter.getInstance(document, new FileOutputStream("C:\\CRM_ATTACHMENTS\\" + claim.getClaimNo() + "ClaimForm.pdf"));
////        } catch (DocumentException ex) {
////            Logger.getLogger(PrintContract.class.getName()).log(Level.SEVERE, null, ex);
////        } catch (FileNotFoundException ex) {
////            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        document.open();
////        try {
////            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
////            Font paragraphHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
////            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL);
////            Chunk chunk = new Chunk("Claim Form :" + claim.getClaimNo(), chapterFont);
////            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
////            chapter.setNumberDepth(2);
////            //chapter.add(new Paragraph("Claim Type : Cash", paragraphFont));
////
////            //  Chunk claimDetailChunk = new Chunk(, chapterFont);
////            Paragraph claimHeaderParagraph = new Paragraph("Claim Detail",paragraphHeaderFont);
////           // claimDetailParagraph.add(Chunk.NEWLINE);
////            //claimDetailChapter.setNumberDepth(0);
////            Paragraph claimDetailParagraph = new Paragraph();
////            claimDetailParagraph.add(new Phrase("Claim Type : Cash", paragraphFont));
////            claimDetailParagraph.add(Chunk.NEWLINE);
////            claimDetailParagraph.add(new Phrase("Claim Type : Cash", paragraphFont));
////            claimDetailParagraph.add(Chunk.NEWLINE);
////            claimDetailParagraph.add(new Phrase("Claim Type : Cash", paragraphFont));
////            claimDetailParagraph.add(Chunk.NEWLINE);
////            claimDetailParagraph.add(new Phrase("Claim Type : Cash", paragraphFont));
////            claimDetailParagraph.add(Chunk.NEWLINE);
////            claimDetailParagraph.add(new Phrase("Claim Type : Cash", paragraphFont));
////            
////            chapter.add(claimHeaderParagraph);
////            chapter.add(claimDetailParagraph);
////            
////            document.add(chapter);
////
//////            C claimNoChapter = new Paragraph();
//////            document.add();
//////            document.add(new Paragraph("Claim Type : Cash"));
//////            document.add(new );
////        } catch (DocumentException ex) {
////            Logger.getLogger(PrintContract.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        document.close();
////    }
////
////    private HttpServletResponse printClaim(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
////        File pdfFile = new File("C:\\CRM_ATTACHMENTS\\8000002ClaimForm.pdf");
////
////        response.setContentType("application/pdf");
////        response.addHeader("Content-Disposition", "inline; filename=" + pdfFile);
////        response.setContentLength((int) pdfFile.length());
////
////        FileInputStream fileInputStream = new FileInputStream(pdfFile);
////        OutputStream responseOutputStream = response.getOutputStream();
////        int bytes;
////        while ((bytes = fileInputStream.read()) != -1) {
////            responseOutputStream.write(bytes);
////        }
////
////        return response;
////    }
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
