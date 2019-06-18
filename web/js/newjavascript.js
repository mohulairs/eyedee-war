/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


crm = {
    user: null,
    xmlhttp: null,
    valid: null,
    paymentList: null,
    fieldOptions: null,
    createTransactionDialog: null,
    selectedCustomer: null,
    selectedTransaction: null,
    customerSearchResult: [],
    selectedOrder: null,
    currentObject: null,
    selectedReceiptRegister: null,
    screens: null,
    menu: null,
    application: null,
    init: function () {
        crm.initialiseTable();
        var data = [];

        data.push({"name": "userAction", "value": "getFieldOptions"});
        $.ajax({
            type: "GET",
            url: "/CRM-war/Controller",
            data: data
        }).done(function (result) {
            crm.fieldOptions = result;

        });
    },
    clearPage: function () {
        $("#container-body").empty();
    },
    refreshFieldOptions: function () {
        crm.fieldOptions = null;
        var data = [];
        data.push({"name": "userAction", "value": "getFieldOptions"});
        $.ajax({
            async: false,
            type: "GET",
            url: "/CRM-war/Controller",
            data: data
        }).done(function (result) {
            crm.fieldOptions = result;

        });
    },
    getMenu: function () {
        $.blockUI();
        $.ajax({
            type: "POST",
            async: false,
            url: "/CRM-war/GetMenu"
        }).done(function (msg) {
            // crm.navigateScreen("screen");
            $("#menu-container").empty();
            $("#menu-container").append(msg);
        });
    },
    getPage: function (area, object, activity) {
        $.blockUI();
        $.ajax({
            type: "POST",
            async: false,
            url: "/CRM-war/GetPageController",
            data: {"object": object, "activity": activity}
        }).done(function (msg) {
            // crm.navigateScreen("screen");
            $("#" + area).empty();
            $("#" + area).append(msg);
            crm.initialiseDropDownFields();
        });
    },
    showPage: function (screen) {
        $("#container-body").append(screen);
    },
    navigate: function (object, activity) {
        $("#error-container").empty();
        $("#error-container").hide();

        crm.application = object;
        crm.refreshFieldOptions();
        crm.getPage("container-body", object, activity);

        crm.currentObject = object;
    },
    refreshObject: function (objectID) {
        switch (crm.currentObject) {
            case "Policy":
                crm.policy.get(objectID);
                break;

        }

    },
    saveAttachments: function (files) {
        $.blockUI();
        var refNo = "232323232";
        $.each(files, function (index, row) {
            $.ajax({
                type: "POST",
                async: false,
                url: "/CRM-war/FileUploadController",
                data: {"referenceNo": refNo, "file": row.file, "destination": row.destination}
            }).done(function (msg) {

            });
        });
    },
    checkUndefinedValue: function (value) {
        if (value === undefined) {
            value = '';
        }
        return value;
    },
    login: function () {
        $("#container-body").empty();
        crm.getPage("menu-container", "MenuBar", "SuperUserMenu");
    },
    initialiseDropDownFields: function () {
        // crm.loadDropDownValues("order-create-transaction-type", crm.fieldOptions["transactionType"]);
        crm.loadDropDownValues("policy-new-form-sales-area", crm.fieldOptions["salesArea"], "ASC", "D");
        crm.loadDropDownValues("group-policy-new-form-sales-area", crm.fieldOptions["salesArea"], "ASC", "D");
        crm.loadDropDownValues("group-policy-new-form-group-location", crm.fieldOptions["salesArea"], "ASC", "D");
        crm.loadDropDownValues("receipt-usage-search-form-sales-area", crm.fieldOptions["salesArea"], "ASC", "D");
        crm.loadDropDownValues("policy-new-form-sales-rep", crm.fieldOptions["employee"], "ASC", "D");
        crm.loadDropDownValues("group-policy-new-form-sales-rep", crm.fieldOptions["employee"], "ASC", "D");
        crm.loadDropDownValues("group-policy-new-form-received-by", crm.fieldOptions["employee"], "ASC", "D");
        crm.loadDropDownValues("receipt-new-form-sales-rep", crm.fieldOptions["employee"], "ASC", "D");
        crm.loadDropDownValues("policy-new-form-received-by", crm.fieldOptions["employee"], "ASC", "D");
        crm.loadDropDownValues("policy-new-form-previous-insurer", crm.fieldOptions["competitor"], "ASC", "D");
        crm.loadDropDownValues("claim-new-funeral-undertaker", crm.fieldOptions["competitor"], "ASC", "D");
        
        crm.loadDropDownValues("receipt-usage-new-form-sales-area", crm.fieldOptions["salesArea"], "ASC", "D");
        crm.loadDropDownValues("receipt-usage-new-form-employee-responsible", crm.fieldOptions["employee"], "ASC", "D");
        crm.loadDropDownValues("receipt-usage-new-form-deduction-employee-responsible", crm.fieldOptions["employee"], "ASC", "D");
        crm.loadDropDownValues("receipt-usage-search-form-deduction-employee-responsible", crm.fieldOptions["employee"], "ASC", "D");
        crm.loadDropDownValues("receipt-usage-search-form-employee-responsible", crm.fieldOptions["employee"], "ASC", "D");

        crm.loadDropDownValues("receipt-usage-new-form-deduction-code", crm.fieldOptions["expenseType"], "ASC", "D");

        crm.loadDropDownValues("receipt-new-form-payment-type", crm.fieldOptions["paymentPeriod"], "ASC", "I");
        crm.loadDropDownValues("customer-new-form-marital-status", crm.fieldOptions["maritalStatus"], "ASC", "D");
        crm.loadDropDownValues("customer-new-form-gender", crm.fieldOptions["gender"], "ASC", "D");

        crm.loadDropDownValues("claim-new-claim-type", crm.fieldOptions["claimType"], "ASC", "D");

        crm.loadDropDownValues("customer-new-form-home-addr-town", crm.fieldOptions["salesArea"], "ASC", "D");
        crm.loadDropDownValues("claim-new-burial-place", crm.fieldOptions["salesArea"], "ASC", "D");
        crm.loadDropDownValues("policy-upgrade-form-new-funeral-plan", crm.fieldOptions["funeralPlan"], "ASC", "D");
        
        crm.loadDropDownValues("claim-new-bank-name", crm.fieldOptions["bankName"], "ASC", "D");
        crm.loadDropDownValues("claim-new-bank-account-type", crm.fieldOptions["bankAccountType"], "ASC", "D");


    },
    //Sortvalue: I = ID, D = Description
    //SortOrder: ASC = Ascending, DESC = Descending

    sortFieldOptions: function (options, sortOrder, sortValue) {
        var sortedOptions;
        if (sortOrder === "ASC" && sortValue === "I") {
            sortedOptions = options.sort(function (a, b) {
                if (a.Id < b.Id)
                    return -1;
                if (a.Id > b.Id)
                    return 1;
                return 0;

            });
        } else if (sortOrder === "DESC" && sortValue === "I") {
            sortedOptions = options.sort(function (a, b) {
                if (a.Id > b.Id)
                    return -1;
                if (a.Id < b.Id)
                    return 1;
                return 0;

            });


        } else if (sortOrder === "ASC" && sortValue === "D") {
            sortedOptions = options.sort(function (a, b) {
                if (a.description < b.description)
                    return -1;
                if (a.description > b.description)
                    return 1;
                return 0;

            });

        } else if (sortOrder === "DESC" && sortValue === "D") {
            sortedOptions = options.sort(function (a, b) {
                if (a.description > b.description)
                    return -1;
                if (a.description < b.description)
                    return 1;
                return 0;

            });

        }
        return sortedOptions;
    },
    loadDropDownValues: function (field, options, sortOrder, sortValue) {

        var sortedOptions = crm.sortFieldOptions(options, sortOrder, sortValue);
        $.each(sortedOptions, function (index, row) {
            $("#" + field).append('<option value="' + row.Id + '">' + row.description + '</option>');
        });
    },
    getDropDownText: function (field, code) {
        var desc;
        var options = crm.fieldOptions[field];
        $.each(options, function (index, row) {
            if (row.Id === code) {
                desc = row.description;
            }
        });
        return desc;
    },
    initialiseTable: function () {
        $('#customerSearchResultTable').dataTable({
            "iDisplayLength": 4,
            "aoColumns": [{"sWidth": "20%"}, {"sWidth": "20%"}, {"sWidth": "20%"}, {"sWidth": "20%"}, {"sWidth": "20%"}]
//            "aoColumnDefs": [{
//                    /* For certain statuses, the column provides a link to edit the application based on status  */
//                    "mRender": function(oObj) {
//                        var elem = "<a href='javascript:crm.viewCustomer(" + oObj + ")'>" + oObj + "</a>";
//                        return elem;
//                    },
//                    "sType": "html",
//                    "aTargets": [0]
//                }]

        });
        $('#transaction_list').dataTable({
            "iDisplayLength": 4
//            "aoColumnDefs": [{
//                    /* For certain statuses, the column provides a link to edit the application based on status  */
//                    "mRender": function(oObj) {
//                        var elem = "<a href='javascript:crm.selectOrder(" + oObj + ")'>" + oObj + "</a>";
//                        return elem;
//                    },
//                    "sType": "html",
//                    "aTargets": [0]
//                }]


        });
        $('#policy-list-table').dataTable({
            "info": false,
            "ordering": false,
            "bFilter": false,
            "bLengthChange": false
        });

        $('#receipt-usage-search-list').dataTable({
            "info": false,
            "ordering": false,
            "bFilter": false,
            "bLengthChange": false
        });


        $('#receipt-order-list').dataTable({
            "info": false,
            "ordering": false,
            "bFilter": false,
            "bLengthChange": false
        });
        $('#policyOverview-PaymentList').dataTable({
            "info": false,
            "ordering": false,
            "bFilter": false,
            "bLengthChange": false
        });
        $('#policyOverview-DependantList').dataTable({
            "info": false,
            "ordering": false,
            "bFilter": false,
            "bLengthChange": false
        });
        $('#order-create-items_table').dataTable({
            "info": false,
            "ordering": false,
            "bFilter": false,
            "bLengthChange": false,
            "bPaginate": false
        });


    },
    validateReceiptNumber: function () {

        var recNo = jQuery("#PaymentReceiptNumber").val();
        //var recNo = document.getElementById("PaymentReceiptNumber");
        $.ajax({
            type: "GET",
            url: "/crm-war/validateReceiptNumber",
            data: {ReceiptNumber: recNo}
        })
                .done(function (msg) {
                    if (msg === "false") {
                        crm.valid = false;
                        alert("Receipt Number entered is invalid");
                    } else {

                        crm.valid = true;
                    }
                })
                .fail(function (msg) {
                    crm.valid = false;
                    alert("Failed to validate Receipt Number");
                });
    },
    validateIDNumber: function (idNumber) {
        // first clear any left over error messages
        //$('#error p').remove();
        // store the error div, to save typing
        // var error = $('#error');
        //var idNumber = $('#CustomerIDNumber').val();
        // assume everything is correct and if it later turns out not to be, just set this to false
        var correct = true;
        //Ref: http://www.sadev.co.za/content/what-south-african-id-number-made
        // SA ID Number have to be 13 digits, so check the length
        if (idNumber.length != 13 || !crm.isNumber(idNumber)) {
            crm.message.showError("ID Number is invalid");
            correct = false;
            return;
        }

        // get first 6 digits as a valid date
        var tempDate = new Date(idNumber.substring(0, 2), idNumber.substring(2, 4) - 1, idNumber.substring(4, 6));
        var id_date = tempDate.getDate();
        var id_month = tempDate.getMonth();
        var id_year = tempDate.getFullYear();
        var fullDate = id_date + "-" + (id_month + 1) + "-" + id_year;
        if (!((tempDate.getYear() == idNumber.substring(0, 2)) && (id_month == idNumber.substring(2, 4) - 1) && (id_date == idNumber.substring(4, 6)))) {
            crm.message.showError("ID Number is invalid");
            correct = false;
            return;
        }

        // get the gender
        var genderCode = idNumber.substring(6, 10);
        var gender = parseInt(genderCode) < 5000 ? "Female" : "Male";
        // get country ID for citzenship
        var citzenship = parseInt(idNumber.substring(10, 11)) == 0 ? "Yes" : "No";
        // apply Luhn formula for check-digits
        var tempTotal = 0;
        var checkSum = 0;
        var multiplier = 1;
        for (var i = 0; i < 13; ++i) {
            tempTotal = parseInt(idNumber.charAt(i)) * multiplier;
            if (tempTotal > 9) {
                tempTotal = parseInt(tempTotal.toString().charAt(0)) + parseInt(tempTotal.toString().charAt(1));
            }
            checkSum = checkSum + tempTotal;
            multiplier = (multiplier % 2 == 0) ? 1 : 2;
        }
        if ((checkSum % 10) != 0) {
            crm.message.showError("ID Number is invalid");
            correct = false;
            return;
        }
        ;
        // if no error found, hide the error message

        return correct;
    },
    isNumber: function (n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
    },
    clearMessage: function () {
        jQuery("#error").empty();
        jQuery("#error").removeClass("errorMessage");
        jQuery("#error").removeClass("successMessage");
    },
    populatePaymentList: function () {
        $.blockUI();
        var data = $('#PaymentListForm').serialize();
        $.ajax({
            type: "GET",
            url: "/crm-war/GetPaymentList",
            data: data
        }).done(function (data) {
            $('#paymentListTable').dataTable().fnClearTable();
            $.each(data, function (index, row) {
                $('#paymentListTable').dataTable().fnAddData([row.idNumber, row.customerName, row.receiptNumber, row.receiptDate, row.transactionType, row.period, row.payPoint, "R " + row.amount]);
            });
            $('#paymentListTable').dataTable().fnDraw();
            // crm.paymentList.fnAddData(data);
        });
    },
    searchCustomer: function (form) {
        crm.customerSearchResult = null;
        var screenValid = $("#" + form).validate({
            onsubmit: false
        }).form();
        if (screenValid === true) {
            if (jQuery("#Customer-Search-ID-Number").val() !== "") {
                idvalid = crm.validateIDNumber(jQuery("#Customer-Search-ID-Number").val());
            }
            $.blockUI();
            var data = $('#CustomerSearchForm').serialize();
            $.ajax({
                type: "GET",
                url: "/CRM-war/SearchCustomer",
                data: data
            }).done(function (data) {

                crm.customerSearchResult = [];
//                $.each(data, function(index, row) {
//
//                    $('#customerSearchResultTable').dataTable().fnAddData([row.partnerNo, row.idNumber, row.surname + " " + row.firstname + " " + row.middlename, row.surname, '<input type="button" value="View" name="viewCustomerButton" id="viewCustomerButton"/>']);
                crm.customerSearchResult.push(new Customer(row.partnerNo, row.idNumber, row.surname, row.firstname, row.middlename, row.orders));
//                });
//                $('#customer_search_results').show();
            });
        }
    },
    searchOrder: function (screen_form) {
        //  crm.customerSearchResult = null;
        $('#receipt-order-list').dataTable().fnClearTable();
        var screenValid = $("#" + screen_form).validate({
            onsubmit: false
        }).form();
        if (screenValid === true) {
            if (jQuery("#" + screen_form + "-id-number").length > 0) {
                idvalid = crm.validateIDNumber(jQuery("#" + screen_form + "-id-number").val());
            }
            $.blockUI();
            var data = $("#" + screen_form).serializeArray();
            data.push({"name": "screen_form", "value": screen_form});
            data.push({"name": "userAction", "value": "searchOrder"});
            $.ajax({
                type: "GET",
                url: "/CRM-war/Controller",
                data: data
            }).done(function (data) {
                //$.blockUI();
                $.ajax({
                    type: "POST",
                    async: false,
                    url: "/CRM-war/GetPageController",
                    data: {"object": "Receipts", "activity": "List"}
                }).done(function (msg) {
                    $("#container-body").append(msg);
                });
                $.each(data, function (index, row) {
                    $('#receipt-order-list').dataTable().fnAddData([row.order_no + " - " + row.order_type, row.idNumber, row.customer, '<input type="button" value="View" name="viewCustomerButton" id="viewCustomerButton"/>']);
                    //crm.customerSearchResult.push(new Customer(row.partnerNo, row.idNumber, row.surname, row.firstname, row.middlename, row.orders));
                });
            });
        }
    },
    saveCustomer: function () {

        var screenValid = jQuery("#CustomerCreateForm").validate({
            onsubmit: false
        }).form();
        if (screenValid === true) {
            $.blockUI();
            var data = $('#CustomerCreateForm').serializeArray();
            $.ajax({
                type: "POST",
                url: "/crm-war/CreateCustomer",
                data: data
            })
                    .done(function (data) {
                        crm.clearForm("CustomerCreateForm");
                        crm.selectedCustomer = new Customer(data.partnerNo, data.idNumber, data.surname, data.firstname, data.middlename, []);
                        crm.viewCustomer();
                    })
                    .fail(function (msg) {

                        jQuery("#error").addClass("errorMessage");
                        jQuery("#error").append("Payment posting failed");
                        //alert("Payment posting failed");

                    });
        }


    },
    viewCustomer: function () {
        //crm.selectedCustomer = customerNo;
//        $.blockUI();
//        $.ajax({
//            type: "POST",
//            url: "/crm-war/getCustomer",
//            data: {"customerNo": customerNo}
//        }).done(function(customerData) {
        var customerData = crm.selectedCustomer;
        if (customerData !== undefined) {

            $('#transaction_list').dataTable().fnClearTable();
            $('#customer-overview-partner-no').val(customerData.partnerNo);
            $('#customer-overview-id-number').val(customerData.idNumber);
            $('#customer-overview-surname').val(customerData.surname);
            $('#customer-overview-firstname').val(customerData.firstname);
            $('#customer-overview-middlename').val(customerData.middlename);
            //$("#CustomerOverviewForm").loadJSON(customerData);
//                $('#transaction_list').dataTable().fnAddData(customerData.orderList);
            $.each(customerData.orders, function (index, row) {
                $('#transaction_list').dataTable().fnAddData([row.orderID, row.transactionType, row.salesArea, row.status, '<input type="button" value="View" name="viewOrderButton" id="viewOrderButton"/>' + '<input type="button" value="Payment" name="payOrderButton" id="payOrderButton"/>']);
            });
            crm.navigateScreen("customerOverview");
        }
//        });
    },
    selectOrder: function (orderID) {
        crm.selectedTransaction = orderID;
        crm.viewOrder(orderID);
    },
    viewOrder: function () {
        //var data;
//        crm.selectedTransaction = orderID;
//        $('#policyOverview-PaymentList').dataTable().fnClearTable();
//        $.blockUI();
//        $.ajax({
//            type: "POST",
//            url: "/crm-war/GetOrder",
//            data: {"transactionNo": orderID}
//        }).done(function(transactionData) {
        var transactionData = crm.selectedOrder;
        if (transactionData !== undefined) {
            //window.location = "/crm-war/customerOverview.jsp";

//
//                if (transactionData.transactionType === "T001") {

            var screen = "policyOverview";
//                } else {
//                    //$("#TransactionOverviewForm").loadJSON(transactionData);
//                    screen = "orderOverview";
//                }
            //     $.each(transactionData.dependantList, function(index, row) {
            //     $('#dependantList').dataTable().fnAddData([row.IDNumber, row.Name]);
            //  });
            $('#policyOverview-PaymentList').dataTable().fnClearTable();
            $("#" + screen + "-orderID").val(transactionData.orderID);
            $("#" + screen + "-customer").val(crm.selectedCustomer.surname + " " + crm.selectedCustomer.firstname + " " + crm.selectedCustomer.middlename);
            $("#" + screen + "-status").val(transactionData.status);
            $("#" + screen + "-salesArea").val(transactionData.salesArea);
            if (transactionData.items.length > 0) {
                $("#" + screen + "-product").val(transactionData.items[0].productDescription);
            }
            $.each(transactionData.payments, function (index, row) {
                $('#policyOverview-PaymentList').dataTable().fnAddData([row.receiptNumber, row.receiptDate, row.paymentType, row.amount, row.receivedBy]);
            });
            crm.navigateScreen(screen);
        }
//        });
    },
    closeCustomer: function () {
        $('#transaction_list').dataTable().fnClearTable();
        crm.selectedCustomer = null;
        crm.navigateScreen("customerSearch");
    },
    closeOrder: function () {
        $('#policyOverview-PaymentList').dataTable().fnClearTable();
        crm.selectedOrder = null;
        crm.viewCustomer();
    },
    createCustomer: function () {
        $.blockUI();
    },
    clearForm: function (myFormElement) {
        var form = $('form[name="' + myFormElement + '"]');
        var elements = form[0].elements;
        form[0].reset();
        for (i = 0; i < elements.length; i++) {

            field_type = elements[i].type;
            switch (field_type) {

                case "text":
                case "password":
                case "textarea":
                case "hidden":

                    elements[i].value = "";
                    break;
                case "radio":
                case "checkbox":
                    if (elements[i].checked) {
                        elements[i].checked = false;
                    }
                    break;
                case "select-one":
                case "select-multi":
                    elements[i].selectedIndex = -1;
                    break;
                default:
                    break;
            }
        }

    },
    postPayment: function () {

        var screenValid = jQuery("#policy-PaymentForm").validate({
            onsubmit: false
        }).form();
        if (screenValid === true) {
            $.blockUI();
            var data = $('#policy-PaymentForm').serialize();
            $.ajax({
                type: "POST",
                url: "/crm-war/PostPayment",
                data: data
            })
                    .done(function (posted) {
                        var receiptDate = $('#payment-receiptDate').val();
                        var receiptNumber = $('#payment-receiptNumber').val();
                        var paymentType = $('#payment-paymentPeriod option:selected').text();
                        var receivedBy = $('#payment-receivedBy option:selected').text();
                        var amount = $('#payment-amount').val();
                        crm.clearForm("policy-PaymentForm");
                        dialog.dialog("close");
                        if (posted.posted === "true") {

                            crm.selectedOrder.payments.push(new Payment(receiptDate, receiptNumber, receivedBy, "", paymentType, amount));
                            crm.viewOrder();
                        }
                    })
                    .fail(function (msg) {
                    });
        }


    },
    createOrder: function (transaction) {
        var height;
        var screen;
        if (transaction === "T001") {
            height = 280;
            screen = "policyCreate";
        } else {
            height = 600;
            screen = "orderCreate";
        }

        //var customerNo = $("#customer-overview-partner-no").val();
        $("#" + screen + "-customerNo").val(crm.selectedCustomer.partnerNo);
        $("#" + screen + "-transactionType").val(transaction);
        crm.navigateScreen(screen);
//        dialog = $("#" + screen).dialog({
//            autoOpen: true,
//            resizable: false,
//            title: title,
//            height: height,
//            width: 800,
//            modal: false,
//            buttons: {
//                "Save": crm.createTransaction,
//                Cancel: function() {
//                    dialog.dialog("close");
//                }
//            },
//            close: function() {
//                crm.viewCustomer(crm.selectedCustomer);
//
//            }
//        });
    },
    savePolicy: function () {
        //var dialog = $(this);
        var screenValid = jQuery("#PolicyCreateForm").validate({
            onsubmit: false
        }).form();
        if (screenValid === true) {
            var data = $("#PolicyCreateForm").serialize();
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "/crm-war/CreateOrder",
                data: data
            }).done(function (transactionData) {

                if (transactionData.orderID !== null) {
                    var transactionType = $('#policyCreate-transactionType').val();
                    var transactionTypeDesc = $('#policyCreate-transactionType option:selected').text();
                    var product = $('#policyCreate-productCode').val();
                    var productDescription = $('#policyCreate-productCode option:selected').text();
                    var salesRep = $('#policyCreate-salesRep option:selected').text();
                    var salesArea = $('#policyCreate-salesArea option:selected').text();
                    var receiptDate = $('#policyCreate-receiptDate').val();
                    var receiptNumber = $('#policyCreate-receiptNumber').val();
                    var paymentType = $('#policyCreate-paymentPeriod option:selected').text();
                    var receivedBy = $('#policyCreate-receivedBy option:selected').text();
                    var amount = $('#policyCreate-amount').val();
                    crm.selectedCustomer.orders.push(new Order(transactionData.orderID, "Funeral Policy", salesArea, "Active", [], []));
//crm.selectedCustomer.orders.items = new Array();
                    var newOrderPos = crm.selectedCustomer.orders.length - 1;
                    crm.selectedCustomer.orders[newOrderPos].items.push(new Item(1, product, productDescription, 1));
                    crm.selectedCustomer.orders[newOrderPos].payments.push(new Payment(receiptDate, receiptNumber, receivedBy, "", paymentType, amount));
                    crm.clearForm("PolicyCreateForm");
                    crm.selectedOrder = crm.selectedCustomer.orders[newOrderPos];
                    //crm.viewCustomer(crm.selectedCustomer);
                    crm.viewOrder();
                }
            });
        }

    },
    saveOrder: function () {
        var dialog = $(this);
        var data = $("#PolicyCreateForm").serialize();
        $.blockUI();
        $.ajax({
            type: "POST",
            url: "/crm-war/CreateOrder",
            data: data
        }).done(function (msg) {
            crm.navigateScreen("screen");
        });
    },
    getCustomerTransactionList: function () {

        $.blockUI();
        $.ajax({
            type: "POST",
            url: "/crm-war/getCustomerOrderList",
            data: {"customerNo": crm.selectedCustomer}
        }).done(function (orderList) {

            if (orderList !== undefined) {
                //window.location = "/crm-war/customerOverview.jsp";
                $('#transaction_list').dataTable({
                    data: orderList});
                // $("#customer_search").hide();
                ////  $("#customer_overview").show();
                //  $("#CustomerOverviewForm").loadJSON(customerData);



            }
        });
    },
    orderPayment: function (screen) {
        $("#payment-orderID").val(crm.selectedOrder.orderID);
        dialog = $("#" + screen).dialog({
            autoOpen: true,
            resizable: false,
            title: "Transaction Payment",
            height: 280,
            width: 800,
            modal: true,
            buttons: {
                "Post": crm.postPayment,
                Cancel: function () {
                    dialog.dialog("close");
                }
            },
            close: function () {

            }
        });
    },
    customerCreateScreen: function () {
        //var data;

        crm.navigateScreen("customerCreate");
        //crm.clearForm("#CustomerCreateForm");

    },
    navigateScreen: function (object, activity) {
//        $.each(crm.screens, function(index) {
//            if (screen !== crm.screens[index]) {
//                $("#" + crm.screens[index]).hide();
//            } else {
//                $("#" + screen).show();
//
//            }
//        });




        //  $("#"+screen).removeAttr("style");
        // $.blockUI();
//        
//        $('#content').remove();
//
//        $.ajax({
//            type: "GET",
//            url: "/crm-war/getScreen",
//            data: {"screen": screen}
//        }).done(function(newScreen) {
//
//            if (newScreen !== undefined) {
//                $('#main-content-header').before(newScreen);
//            }
//        });
    },
    logout: function () {
        // $.blockUI();
        dialog = $("<p>Are you sure you want to logout?</p>").dialog(
                {
                    autoOpen: true,
                    resizable: false,
                    title: "Request processing....",
                    height: 220,
                    width: 400,
                    modal: true,
                    buttons: {
                        Yes: function () {
                            $.ajax({
                                type: "GET",
                                url: "/CRM-war/Logout"
                            }).done(function () {

                            });
                            dialog.dialog("close");
                        }
                    }
                });

    },
    convertToCurrency: function (field) {
        var cfgCulture = 'en-GB';
        $.preferCulture(cfgCulture);
        $('#' + field).maskMoney();
    },
    createReceiptRegister: function (screen_form) {

        crm.selectedReceiptRegister = new ReceiptRegister();

        crm.navigate('ReceiptUsage', 'New');
        $('#receipt-usage-new-form-deduction-list').dataTable({
            "info": false,
            "ordering": false,
            "bFilter": false,
            "bLengthChange": false,
            "iDisplayLength": 5
        });
        crm.screens.newDeductionLine(screen_form);
        crm.convertToCurrency("receipt-usage-new-form-amount-received");


    },
    saveReceiptRegister: function (screen_form) {
        var screenValid = jQuery("#" + screen_form).validate({
            onsubmit: false
        }).form();
        if (screenValid === true) {

            crm.selectedReceiptRegister.salesArea = $("#" + screen_form + "-sales-area option:selected").val();
            crm.selectedReceiptRegister.salesAreaText = $("#" + screen_form + "-sales-area option:selected").text();
            crm.selectedReceiptRegister.treasurer = $("#" + screen_form + "-employee-responsible option:selected").val();
            crm.selectedReceiptRegister.treasurerText = $("#" + screen_form + "-employee-responsible option:selected").text();
            crm.selectedReceiptRegister.firstReceipt = $("#" + screen_form + "-receipt-from").val();
            crm.selectedReceiptRegister.lastReceipt = $("#" + screen_form + "-receipt-to").val();
            crm.selectedReceiptRegister.amountReceived = $("#" + screen_form + "-amount-received").val();

            //crm.selectedReceiptRegister.amountDeposited = $("#receipt-register-create-amount-deposited").val();
            crm.selectedReceiptRegister.deductionCount = crm.selectedReceiptRegister.deductions.length;
            // var data = crm.selectedReceiptRegister;

            //  data.push({"name": "user_action", "value": "log_receipt_book_usage"});
            //data.push({"receiptRegister":crm.selectedReceiptRegister});
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "/CRM-war/SaveReceiptRegister",
                data: {"receiptRegister": crm.selectedReceiptRegister}
            }).done(function (data) {
//                crm.selectedReceiptRegister.referenceNumber = data.ReferenceNumber;
//                if (crm.selectedReceiptRegister.referenceNumber !== "") {
                crm.clearPage();
                crm.showPage(data);

//                }
            }).error(function (data) {
                crm.message.showError(data);
            });


        }
    },
    showDialogBog: function (data) {
        dialog = $(data).dialog({
            autoOpen: true,
            resizable: false,
            title: "Request processing....",
            height: 220,
            width: 400,
            modal: true,
            buttons: {
                Ok: function () {
                    dialog.dialog("close");
                }
            }
        });
    },
    viewReceiptRegister: function (receiptRegister) {
        $("#receipt-register-overview-reference-number").val(receiptRegister.referenceNumber);
        $("#receipt-register-overview-sales-area").val(receiptRegister.salesAreaText);
        $("#receipt-register-overview-treasurer").val(receiptRegister.treasurerText);
        $("#receipt-register-overview-first-receipt").val(receiptRegister.firstReceipt);
        $("#receipt-register-overview-last-receipt").val(receiptRegister.lastReceipt);
        $("#receipt-register-overview-date-from").val(receiptRegister.dateFrom);
        $("#receipt-register-overview-date-to").val(receiptRegister.dateTo);
        $("#receipt-register-overview-amount-received").val(receiptRegister.amountReceived);
        $("#receipt-register-overview-amount-deducted").val(receiptRegister.amountDeducted);
        $("#receipt-register-overview-amount-deposited").val(receiptRegister.amountReceived - receiptRegister.amountDeducted);

        $('#receipt-register-overview-deductions').dataTable().fnClearTable();
        $.each(receiptRegister.deductions, function (index, row) {
            $('#receipt-register-overview-deductions').dataTable().fnAddData([row.deductionText, row.date, row.amount, row.employeeResponsibleText, '<input type="button" id="removeDeductionButton" name="removeDeductionButton" value="Delete"/>']);

        });
        crm.navigateScreen("receiptRegisterOverview");
    }
};
//function customer(){
//    customerID = 
//    identityNumber : null
//    
//};


$.fn.dataTableExt.oApi.fnPagingInfo = function (oSettings)
{
    return {
        "iStart": oSettings._iDisplayStart,
        "iEnd": oSettings.fnDisplayEnd(),
        "iLength": oSettings._iDisplayLength,
        "iTotal": oSettings.fnRecordsTotal(),
        "iFilteredTotal": oSettings.fnRecordsDisplay(),
        "iPage": Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
        "iTotalPages": Math.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
    };
};
//$('#idCheck').submit(Validate);
$(document).on("click", "#viewCustomerButton", function () {
    var tableDisplayLength = $('#customerSearchResultTable').dataTable().fnPagingInfo().iLength;
    var pageNumber = $('#customerSearchResultTable').dataTable().fnPagingInfo().iPage;
    var rowIndex = $(this).closest('tr')[0].sectionRowIndex;
    crm.selectedCustomer = crm.customerSearchResult[ rowIndex + (tableDisplayLength * pageNumber)];
    crm.viewCustomer();
});

$(document).on("click", "#payOrderButton", function () {
    var tableDisplayLength = $('#transaction_list').dataTable().fnPagingInfo().iLength;
    var pageNumber = $('#transaction_list').dataTable().fnPagingInfo().iPage;
    var rowIndex = $(this).closest('tr')[0].sectionRowIndex;
    crm.selectedOrder = crm.selectedCustomer.orders[rowIndex + (tableDisplayLength * pageNumber)];
    crm.orderPayment("policyPayment");
});
$(document).ajaxStart(function () {
    $.blockUI();
});
$(document).ajaxStop(function () {
    $.unblockUI();
});
$(document).ajaxError(function () {
    $.unblockUI();
});

jQuery(document).ready(function ($) {

    $('button').click(function () {
        crm.message.defaultBar.hide();
    });

    $('#NewPaymentButton').click(function () {
        $("#payment-form").attr("style", "");
        dialog = $("#payment-form").dialog({
            autoOpen: true,
            title: "Enter Payment Details",
            height: 490,
            width: 500,
            modal: true,
            buttons: {
                "Post": crm.postPayment,
                Cancel: function () {
                    dialog.dialog("close");
                }
            },
            close: function () {

            }
        });
    });
    $('#NewLaybyeButton').click(function () {
        //var partnerNo = $("#partnerNo").val()
        //  $("#newoRDERForm").attr("style", "");
        //$("#new-customer-number").attr("value", partnerNo);
        dialog = $("#newOrderForm").dialog({
            autoOpen: true,
            title: "New Transaction",
            height: 490,
            width: 600,
            modal: true,
            buttons: {
                "Post": crm.createTransaction,
                Cancel: function () {
                    dialog.dialog("close");
                }
            },
            close: function () {

            }
        });
    });
    $('#logonButton').click(function () {
        crm.logon();
    });
    $('#LoadPaymentsButton').click(function () {
        crm.populatePaymentList();
    });
    $('#CreateCustomerButton').click(function () {
        crm.customerCreateScreen();
        //window.location = "/crm-war/customerCreate.jsp";
    });
    $('#SearchCustomerButton').click(function () {
        //  if (crm.validateIDNumber(jQuery("#IDNumber").val()) === true) {

        crm.searchCustomer();
        //}
    });
    $('#ExitCustomerOverview').click(function () {
        crm.closeCustomer();
        //window.location = "/crm-war/customerCreate.jsp";
    });
    $('#ExitOrderOverview').click(function () {
        crm.closeOrder();
        //window.location = "/crm-war/customerCreate.jsp";
    });
    $('#saveOrder').click(function () {
        //  if (crm.validateIDNumber(jQuery("#IDNumber").val()) === true) {

        crm.createTransaction();
        //}
    });
    $('#PostPaymentButton').click(function () {

//var recNo = jQuery("#PaymentReceiptNumber").val();
//var data = jQuery('#PaymentTrackerForm' + ' :input').map(function() {
// }).get();
        crm.clearMessage();
        var screenValid = jQuery("#PaymentTrackerForm").validate({
            onsubmit: false
        }).form();
        if (screenValid === true) {
            $.blockUI();
            var data = $('#PaymentTrackerForm').serialize();
            $.ajax({
                type: "POST",
                url: "/crm-war/PostPayment",
                data: data
            })
                    .done(function (msg) {

                        //jQuery("#error").addClass("successMessage");
                        //jQuery("#error").append("Payment posting succesfully");
                        window.location = "/crm-war/PaymentTracker.jsp";
                        alert("Payment posting succesfully");
                    })
                    .fail(function (msg) {

                        jQuery("#error").addClass("errorMessage");
                        jQuery("#error").append("Payment posting failed");
                        //alert("Payment posting failed");

                    });
        }
    });
    $("#TransactionType").change(function () {
// alert("The text has been changed.");
        var trans = $(this).val();
        if (trans === "T001" || trans === "T006")
        {
            $("#PaymentPeriod").show();
            $("#PaymentPeriodLabel").show();
            $("#PaymentPeriod").attr("required", true);
            $("#ProductCode").hide();
            $("#ProductCodeLabel").hide();
            $("#Balance").hide();
            $("#BalanceLabel").hide();
            $("#ProductCode").attr("required", false);
            $("#Balance").attr("required", false);
            $("#Balance").attr("value", "");
            $("#ProductCode").attr("value", "");
        }

        else if (trans === "T002")
        {
            $("#ProductCode").show();
            $("#ProductCodeLabel").show();
            $("#Balance").show();
            $("#BalanceLabel").show();
            $("#ProductCode").attr("required", true);
            $("#Balance").attr("required", true);
            $("#PaymentPeriod").hide();
            $("#PaymentPeriodLabel").hide();
            $("#PaymentPeriod").attr("required", false);
            $("#PaymentPeriod").attr("value", "");
        }
        else if (trans === "T004")
        {
            $("#ProductCode").show();
            $("#ProductCodeLabel").show();
            $("#Balance").hide();
            $("#BalanceLabel").hide();
            $("#ProductCode").attr("required", true);
            $("#Balance").attr("required", false);
            $("#PaymentPeriod").hide();
            $("#PaymentPeriodLabel").hide();
            $("#PaymentPeriod").attr("required", false);
            $("#PaymentPeriod").attr("value", "");
        }
        else if (trans === "T005")
        {
            $("#ProductCode").hide();
            $("#ProductCodeLabel").hide();
            $("#Balance").show();
            $("#BalanceLabel").show();
            $("#ProductCode").attr("required", false);
            $("#Balance").attr("required", true);
            $("#PaymentPeriod").hide();
            $("#PaymentPeriodLabel").hide();
            $("#PaymentPeriod").attr("required", false);
            $("#PaymentPeriod").attr("value", "");
        }

        else

        {
            $("#PaymentPeriod").hide();
            $("#PaymentPeriodLabel").hide();
            $("#PaymentPeriod").attr("required", false);
            $("#PaymentPeriod").attr("value", "");
            $("#ProductCode").hide();
            $("#ProductCodeLabel").hide();
            $("#Balance").hide();
            $("#BalanceLabel").hide();
            $("#ProductCode").attr("required", false);
            $("#Balance").attr("required", false);
            $("#Balance").attr("value", "");
            $("#ProductCode").attr("value", "");
        }

    });
    $("#PaymentPeriod").change(function () {
// alert("The text has been changed.");
        var trans = $(this).val();
        if (trans === "New")
        {
            $("#SalesRepresentative").show();
            $("#SalesRepresentativeLabel").show();
            $("#SalesRepresentative").attr("required", true);
        } else
        {
            $("#SalesRepresentative").hide();
            $("#SalesRepresentativeLabel").hide();
            $("#SalesRepresentative").attr("required", false);
            $("#SalesRepresentative").attr("value", "");
        }

    });

    $('#removeDeductionButton').click(function () {
        var bid = this.id; // button ID 
        var trid = $(this).closest('tr').attr('id'); // table row ID 

        var tableDisplayLength = $(trid).dataTable().fnPagingInfo().iLength;
        var pageNumber = $(trid).dataTable().fnPagingInfo().iPage;
        var rowIndex = $(trid).closest('tr')[0].sectionRowIndex;
        crm.screens.currentTableSelectedRow = rowIndex + (tableDisplayLength * pageNumber);
    });
});

Order_ = {
    Id: null,
    Items: null,
    initialise: function () {
        Order_.Items = [];
    },
    addOrderItem: function () {
        var newRow = '<tr>' +
                '<td><input type="text" id="order-create-item-product-code" name="order-create-item-product-code"/></td>' +
                '<td><input type="text" id="order-create-item-quantity" name="order-create-item-quantity"/></td>' +
                '<td></td>' +
                '<td></td>' +
                '<td></td>' +
                '<td><input type="button" value="Delete"/><input type="button" value="Add" onClick="crm.addOrderItem()"/></td>' +
                "</tr>";
        $('#order_create_items_table> tbody > tr:last').after(newRow);
    },
    addItem: function () {
        //var itemCount = orderItems.size();
        // var data = $("#orderCreateItemForm").serializeArray();
        var productCode = $("#order-create-item-product-code").val();
        var quantity = $("#order-create-item-quantity").val();
        $.ajax({
            type: "GET",
            url: "/crm-war/getProductDetails",
            data: {"productCode": productCode}
        }).done(function (data) {
            var productCode = data.productCode;
            var unitPrice = data.price;
            var productDescription = data.description;
            var totalPrice = quantity * unitPrice;
            Order_.Items.push(new Item(productCode, productDescription, quantity, unitPrice, totalPrice));
            Order_.refreshItemList();
        });
        $('#orderCreateNewItem').dialog('close');
    },
    removeItem: function (index) {
        Order_.Items.splice(index, 1);
        Order_.refreshItemList();
    },
    refreshItemList: function () {
        var totalPrice = 0;
        //    $('#order-create-items_table').DataTable().rows.add(Order.Items).fnDraw();
        $('#order-create-items_table').dataTable().fnClearTable();
        $.each(Order_.Items, function (index, row) {

            $('#order-create-items_table').dataTable().fnAddData([row.productCode, row.productDescription, row.quantity, "R " + row.unitPrice, "R " + row.totalPrice, '<input type="button" value="Remove" name="removeItemButton" id="removeItemButton"/>']);
            totalPrice = totalPrice + row.totalPrice;
        });
        crm.convertToCurrency("order-create-total-price", totalPrice);
        $('#order-create-items_table').dataTable().fnDraw();
    },
    save: function () {
        var table = $('#order_create_items_table').DataTable();
        var items = table.rows().data();
        var formData = $("#orderCreateForm").serialize();
        //   var data = {}""Order.Items;
        //var data = json.singlify(Order.Items);
        $.blockUI();
        $.ajax({
            type: "POST",
            url: "/crm-war/CreateOrder",
            data: [formData, Order_.Items, Order_.Items.size()]
        }).done(function (transactionData) {
            crm.selectedOrder = transactionData.orderID;
            Order_.Id = transactionData.orderID;
            crm.viewOrder(transactionData.orderID);
            crm.clearForm("PolicyCreateForm");
        });
    }


};
$(document).ready(function () {
    crm.init();

});

