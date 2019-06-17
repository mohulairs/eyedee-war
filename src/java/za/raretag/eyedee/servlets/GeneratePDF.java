/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.eyedee.servlets;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transaction;
import za.raretag.eyedee.beans.TransactionBeanLocal;
import za.raretag.eyedee.generic.BankAccount;
import za.raretag.eyedee.generic.Conversion;
import za.raretag.eyedee.generic.Data;
import za.raretag.eyedee.generic.Order;
import za.raretag.eyedee.generic.OrderArea;
import za.raretag.eyedee.generic.OrderDate;
import za.raretag.eyedee.generic.OrderItem;
import za.raretag.eyedee.generic.OrderPartner;
import za.raretag.eyedee.generic.ReferenceDocument;
import za.raretag.eyedee.generic.Response;

/**
 *
 * @author tebogom
 */
public class GeneratePDF extends HttpServlet {

    @EJB
    private TransactionBeanLocal transactionBean;

    public static final String filename
            = "results/part1/chapter01/hello.pdf";
    private static final Font docHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static final Font sectionHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    private static final Font dataFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (ServletOutputStream os = response.getOutputStream()) {
            String referenceNo = request.getParameter("referenceNo");

            ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
            response.setContentType("application/pdf");
            String docType = "CLAIM";
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, baosPDF);
            switch (docType) {
                case "CLAIM":
                    createClaimDocument(document, referenceNo);
                    // downloadFile(response, referenceNo);
                    break;
            }

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename= Claim:" + referenceNo + ".pdf");
            response.setHeader(headerKey, headerValue);
            response.setContentLength(baosPDF.size());

            baosPDF.writeTo(os);
            os.flush();
        } catch (DocumentException ex) {
            Logger.getLogger(GeneratePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void downloadFile(HttpServletResponse response, String referenceNo) throws FileNotFoundException, IOException {
        Response respData = transactionBean.getTransaction(referenceNo);
        Data<Order> orderData = respData.getData();
        Order order = orderData.get();
        String filePath = "C:\\" + order.getType() + "\\" + order.getId() + ".pdf";
        File downloadFile = new File(filePath);
        FileInputStream inStream = new FileInputStream(downloadFile);

        // if you want to use a relative path to context root:
        String relativePath = getServletContext().getRealPath("");
        System.out.println("relativePath = " + relativePath);

        // obtains ServletContext
        ServletContext context = getServletContext();

        // gets MIME type of the file
        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }

    private void createClaimDocument(Document document, String transactionNo) {
        try {
            Response respon = transactionBean.getTransaction(transactionNo);
            Data<Order> orderData = respon.getData();
            Order order = orderData.get();
            document.open();
            Paragraph paragraph = new Paragraph("CLAIM APPLICATION FORM", docHeaderFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            addEmptyLine(paragraph, 1);
            document.add(paragraph);
            paragraph = new Paragraph("CLAIM SUBMISSION DETAILS", sectionHeaderFont);
            paragraph.add(new Paragraph("Claim Reference Number: " + order.getId(), dataFont));

            for (OrderDate orderDate : order.getDates()) {
                if ("SUBMISSIONDATE".equals(orderDate.getType())) {
                    paragraph.add(new Paragraph("Claim Submission Date: " + Conversion.dateToString(orderDate.getValue()), dataFont));
                }
            }
            for (OrderArea orderArea : order.getAreas()) {
                if ("SUBMISSIONOFFICE".equals(orderArea.getType())) {
                    paragraph.add(new Paragraph("Submission Office: " + orderArea.getAreaDescription(), dataFont));
                }
            }

            for (OrderPartner orderPartner : order.getPartners()) {
                if ("CONSULTANT".equals(orderPartner.getType())) {
                    paragraph.add(new Paragraph("Submitted By: " + orderPartner.getFullname(), dataFont));
                }
            }
            for (ReferenceDocument refDoc : order.getReferenceDocuments()) {
                if ("FUNERALPOLICY".equals(refDoc.getDocType())) {
                    paragraph.add(new Paragraph("Policy Number: " + refDoc.getDocID(), dataFont));
                }
            }
            addEmptyLine(paragraph, 1);
            document.add(paragraph);
            paragraph = new Paragraph("CLAIMED ITEMS", sectionHeaderFont);
            for (OrderItem orderItem : order.getItems()) {
                List list = new List(true, false, 10);
                list.add(new ListItem(orderItem.getItemDescription(), dataFont));
                paragraph.add(list);
            }
            addEmptyLine(paragraph, 1);
            document.add(paragraph);
            Paragraph policyHolder = null;
            Paragraph deceased = null;
            Paragraph claimant = null;
            for (OrderPartner orderPartner : order.getPartners()) {
                if ("MAINMEMBER".equals(orderPartner.getType())) {
                    policyHolder = new Paragraph("POLICY HOLDER DETAILS", sectionHeaderFont);
                    policyHolder.add(new Paragraph("ID Number: " + orderPartner.getIdNumber(), dataFont));
                    policyHolder.add(new Paragraph("Full Names: " + orderPartner.getFullname(), dataFont));
                    policyHolder.add(new Paragraph("Contact Number: " + orderPartner.getCellNumber(), dataFont));
//                    paragraph.add(new Paragraph("Physical Address: " + transactionNo, dataFont));
//                    addEmptyLine(paragraph, 1);
//                    document.add(paragraph);
                }
                if ("DECEASED".equals(orderPartner.getType())) {
                    deceased = new Paragraph("DECEASED DETAILS", sectionHeaderFont);
                    deceased.add(new Paragraph("ID Number: " + orderPartner.getIdNumber(), dataFont));
                    deceased.add(new Paragraph("Full Names: " + orderPartner.getFullname(), dataFont));
                    deceased.add(new Paragraph("Contact Number: " + orderPartner.getCellNumber(), dataFont));
//                    paragraph.add(new Paragraph("Physical Address: " + transactionNo, dataFont));
//                    addEmptyLine(paragraph, 1);
//                    document.add(paragraph);
                }
                if ("CLAIMANT".equals(orderPartner.getType())) {
                    claimant = new Paragraph("CLAIMANT DETAILS", sectionHeaderFont);
                    claimant.add(new Paragraph("ID Number: " + orderPartner.getIdNumber(), dataFont));
                    claimant.add(new Paragraph("Full Names: " + orderPartner.getFullname(), dataFont));
                    claimant.add(new Paragraph("Contact Number: " + orderPartner.getCellNumber(), dataFont));
//                    paragraph.add(new Paragraph("Physical Address: " + transactionNo, dataFont));
//                    addEmptyLine(paragraph, 1);
//                    document.add(paragraph);
                }
            }
            if (policyHolder != null) {
                addEmptyLine(policyHolder, 1);
                document.add(policyHolder);
            }
            if (claimant != null) {
                addEmptyLine(claimant, 1);
                document.add(claimant);
            }
            if (deceased != null) {
                addEmptyLine(deceased, 1);
                document.add(deceased);
            }
//
//            PdfPTable table = new PdfPTable(2);
//            table.setWidthPercentage(100);
//            PdfPCell seller = getPartyAddress("From:",
//                    basic.getSellerName(),
//                    basic.getSellerLineOne(),
//                    basic.getSellerLineTwo(),
//                    basic.getSellerCountryID(),
//                    basic.getSellerPostcode(),
//                    basic.getSellerCityName());
//            table.addCell(seller);
//            PdfPCell buyer = getPartyAddress("To:",
//                    basic.getBuyerName(),
//                    basic.getBuyerLineOne(),
//                    basic.getBuyerLineTwo(),
//                    basic.getBuyerCountryID(),
//                    basic.getBuyerPostcode(),
//                    basic.getBuyerCityName());
//            table.addCell(buyer);
//            seller = getPartyTax(basic.getSellerTaxRegistrationID(),
//                    basic.getSellerTaxRegistrationSchemeID());
//            table.addCell(seller);
//            buyer = getPartyTax(basic.getBuyerTaxRegistrationID(),
//                    basic.getBuyerTaxRegistrationSchemeID());
//            table.addCell(buyer);
//            document.add(table);

            for (BankAccount bankAccount : order.getBankAccounts()) {
                if ("CASHPAYOUT".equals(bankAccount.getUsageType())) {
                    paragraph = new Paragraph("CASH PAYOUT - BANKING DETAILS", sectionHeaderFont);
                    paragraph.add(new Paragraph("Account Holder ID Number: " + bankAccount.getAccountHolderIDNumber(), dataFont));
                    paragraph.add(new Paragraph("Account Holder: " + bankAccount.getAccountHolder(), dataFont));
                    paragraph.add(new Paragraph("Bank Name: " + bankAccount.getBankName(), dataFont));
                    paragraph.add(new Paragraph("Account Number: " + bankAccount.getAccountNumber(), dataFont));
                    paragraph.add(new Paragraph("Account Type: " + bankAccount.getAccountType(), dataFont));
                    addEmptyLine(paragraph, 1);
                    document.add(paragraph);
                }
            }
            document.close();

        } catch (DocumentException ex) {
            Logger.getLogger(GeneratePDF.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

//    public PdfPCell getPartnerInfo(String who, String name, String line1, String line2, String countryID, String postcode, String city) {
//        PdfPCell cell = new PdfPCell();
//        cell.setBorder(PdfPCell.NO_BORDER);
//        cell.addElement(new Paragraph(who, font12b));
//        cell.addElement(new Paragraph(name, font12));
//        cell.addElement(new Paragraph(line1, font12));
//        cell.addElement(new Paragraph(line2, font12));
//        cell.addElement(new Paragraph(String.format("%s-%s %s", countryID, postcode, city), font12));
//        return cell;
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
