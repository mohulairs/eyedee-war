/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

receipt = {
    orderSelectionDialog: null,
    cancel: function () {
        sessionStorage.action = "view";
        window.location.href = "../FuneralPolicy/View.html";
    },
    search: function () {
        if ($('#idNumber').val().length === '') {

        }
        $("#selectOrder").dialog("open");
    },
    populate: function (data) {
        var transactionData = "";
        transactionData = transactionData + "<dt>Transaction No</dt>";
     //   transactionData = transactionData + "<dd>" + data.transactionNo + "</dd>";
        transactionData = transactionData + "<dt>Transaction Type</dt>";
   //     transactionData = transactionData + "<dd>" + data.transactionType + "</dd>";
        transactionData = transactionData + "<dt>Customer</dt>";
     //   transactionData = transactionData + "<dd>" + data.customer + "</dd>";
        transactionData = transactionData + "<dt>Sales Representative</dt>";
     //   transactionData = transactionData + "<dd>" + data.salesRepresentative + "</dd>";
        transactionData = transactionData + "<dt>Opening Balance</dt>";
      //  transactionData = transactionData + "<dd>" + data.openingBalance + "</dd>";
        transactionData = transactionData + "<dt>Current Balance</dt>";
      //  transactionData = transactionData + "<dd>" + data.currentBalance + "</dd>";
        transactionData = transactionData + "<dt>Monthly Instalment</dt>";
       // transactionData = transactionData + "<dd>" + data.monthlyInstalment + "</dd>";
        transactionData = transactionData + "<dt>Arrears</dt>";
      //  transactionData = transactionData + "<dd>" + data.Arrears + "</dd>";
        transactionData = transactionData + "<dt>Amount Due</dt>";
     //   transactionData = transactionData + "<dd>" + data.amountDue + "</dd>";
        transactionData = transactionData + "<dt>Status</dt>";
       // transactionData = transactionData + "<dd>" + data.status + "</dd>";
        transactionData = transactionData + "<dt>Status Reason</dt>";
       // transactionData = transactionData + "<dd>" + data.statusReason + "</dd>";
        transactionData = transactionData + "<dt>Status Date</dt>";
      //  transactionData = transactionData + "<dd>" + data.statusDate + "</dd>";
        $("#transactionData").html(transactionData);

    },

    save: function (action) {
        var screenValid = $("#receiptForm").validate({
            onsubmit: false,
            rules: {
                idNumber: {
                    required: true,
                    minlength: 13,
                    maxlength: 13,
                    digits: true,
                    validateID: true
                }
            }
        }).form();
        if (screenValid) {
            var data = $("#receiptForm").serializeArray();
            receipt.serverCall(action, data);

        }
    },
    serverCall: function (action, params) {
        params.push({"name": "userAction", "value": action});
        $.blockUI();
        try {
            $.ajax({
                url: eyedee.url + "/PaymentServlet",
                type: "POST",
                dataType: 'jsonp',
                crossDomain: true,
                data: params,
                async: false,
                timeout: 10000
            }).done(function (dataR, textStatus, jqXHR) {
                if (eyedee.checkErrors(dataR.messages) !== true) {
                    switch (action) {
                        case "create" :
                            receipt.cancel();
                            break;
                        case "save":
                            break;
                        case "search":
                            customer.populateResults(dataR.value.data);
                            break;
                        case "get":
                            customer.populateCustomerView(dataR.value.data);
                            break;
                    }
                }
                $.unblockUI();

                $.each(dataR.messages, function (index, row) {
                    eyedee.showMessage(row.type, row.message);
                });

            }).fail(function (jqXHR, textStatus, errorThrown) {

                $.unblockUI();
                eyedee.showMessage('E', errorThrown);

            }).always(function () {

            });
        } catch (err) {
            eyedee.showMessage('E', 'Server not available');

        }
    }


};

$(document).ready(function () {
    $("#tabs").tabs();
    eyedee.convertToCurrency("amount");
    eyedee.getFieldOptions();
    eyedee.getWorkcenters();
    receipt.populate();
    switch (sessionStorage.action) {
        case "create":
            $("#referenceNo").val(sessionStorage.currentTransaction);
            $("#customer").val(sessionStorage.customerFullName);
            break;
        case "New":
            receipt.populate();
            break;
        case "view":
            var data = [];
            data.push({"name": "userAction", "value": "get"});
            data.push({"name": "policyNo", "value": sessionStorage.currentTransaction});
            sessionStorage.action = "get";
            policy.serverCall(data);
            break;
    }
    $('#result-list').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });

    $('#order-list').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });
    $("#selectOrder").dialog({
        title: "Select Order",
        autoOpen: false,
        width: 800,
        height: 400
    });
    // $("input[name='radio']:checked").val()
    $("input[name='extReceipt']").click(function () {
        if ($(this).val() === 'Yes') {
            $("#extReceiptTab").show();
        } else {
            $("#extReceiptTab").hide();
        }
    });

});
