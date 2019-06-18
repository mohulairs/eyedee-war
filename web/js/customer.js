/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

customer = {
    formData: null,
    create: function () {
        eyedee.navigate('Customer', 'Create');
    },
    cancel: function () {
        window.location.href = "../Customer/View.html";
        eyedee.setUserAction("view");
    },
    search: function () {
        var screenValid = $("#customerForm").validate({
            onsubmit: false,
            rules: {
                idNumber: {
                    required: true,
                    //minlength: 13,
                    //maxlength: 13,
                    digits: true,
                    validateID: true
                }
            }
        }).form();
        if (screenValid === true) {
            customer.formData = $("#customerForm").serializeArray();
            customer.formData.push({"name": "userAction", "value": "search"});
            sessionStorage.action = "search";
            customer.serverCall("search", customer.formData);

        }

    },
    view: function (customerNo) {
        customer.setCurrent(customerNo);
        sessionStorage.action = "view";
        sessionStorage.workcenter = "Customer";
        window.location.href = "../Customer/View.html";
    },
    salesOrder: function () {
        sessionStorage.action = "create";
        window.location.href = "../SalesOrder/New.html";
    },
    policy: function () {
        sessionStorage.action = "create";
        window.location.href = "../FuneralPolicy/New.html";
    },
    quotation: function () {
        sessionStorage.action = "create";
        window.location.href = "../Quotation/New.html";
    },
    serviceRequest: function () {
        sessionStorage.action = "create";
        window.location.href = "../ServiceRequest/New.html";
    },
    serviceOrder: function () {
        sessionStorage.action = "create";
        window.location.href = "../ServiceOrder/New.html";
    },
    populate: function (data) {
        //  sessionStorage.customer = data;
        sessionStorage.setItem("customer", JSON.stringify(data));
        var customerData = "";
        customerData = customerData + "<dt>Customer No</dt>";
        customerData = customerData + "<dd>" + data.id + "</dd>";
        customerData = customerData + "<dt>ID Number</dt>";
        customerData = customerData + "<dd>" + data.idNumber + "</dd>";
        customerData = customerData + "<dt>Surname</dt>";
        customerData = customerData + "<dd>" + data.lastName + "</dd>";
        customerData = customerData + "<dt>First Name</dt>";
        customerData = customerData + "<dd>" + data.firstName + "</dd>";
        customerData = customerData + "<dt>Middle Name</dt>";
        customerData = customerData + "<dd>" + data.middleName + "</dd>";
        $("#customerData").html(customerData);
        $("#customerNo").val(data.id);
        $("#idNumber").val(data.idNumber);
        $("#surname").val(data.lastName);
        $("#firstName").val(data.firstName);
        $("#middleName").val(data.middleName);
        sessionStorage.customerFullName = data.fullName;
        $("#overviewAnchor").html("<span id='overviewSpan' class='glyphicon glyphicon-triangle-right'></span>" + data.idNumber + " - " + data.fullName);
        var t = $('#policyList').DataTable().clear().draw();
        $.each(data.policies, function (index, row) {
            var paymentButton = "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#myModal'>Payment</button>";
            var type = "Policy";
            var viewButton = '<a href="#" onClick="eyedee.viewTransaction(' + row.policyNo + ')">' + row.policyNo + '</a>';
            var button = '<div class="form-group" style="position:relative;left:20px;"><div class="dropdown"><button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Action<span class="caret"></span></button><ul class="dropdown-menu"><li><a onclick="policy.dependent()">Add New</a></li> <li><a href="../Dependant/Replace.html">Replace</a></li></ul></div></div>'
            t.row.add([viewButton, row.productDescription, row.salesRepresentative, row.dateJoined, row.dateEffective, row.status, row.statusDate]).draw(false);
        });

        var d = $('#dependentList').DataTable().clear().draw();
        $.each(data.dependents, function (index, row) {
            var fullname = row.lastName + " " + row.firstName + " " + row.middleName;
            fullname = fullname.replace("undefined", "");
            var idNumber = "Not Supplied";
            if (row.idNumber !== null && row.idNumber !== undefined) {
                idNumber = row.idNumber;
            }
            var paymentButton = "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#myModal'>Payment</button>";
//            var viewButton = "<button type='button' class='btn btn-primary' onClick='policy.view(" + row.policyNo + ")'>View</button>";
            d.row.add([row.id, idNumber, fullname, row.dateAdded, row.dateEffective, row.status, row.statusDate, row.statusReason]).draw(false);
        });

        var p = $('#paymentList').DataTable().clear().draw();
        $.each(data.payments, function (index, row) {
            p.row.add([row.paymentDate, row.paymentType, row.receivedBy, row.extReceiptNo, row.amount]).draw(false);
        });

        var c = $('#claimList').DataTable().clear().draw();
        $.each(data.claims, function (index, row) {
            c.row.add([row.claimNo, '', '', '', '', '', '']).draw(false);

        });
    },
    setCurrent: function (customerNo) {
        sessionStorage.currentCustomer = customerNo;
    },
    save: function (action) {
        var screenValid = $("#customerForm").validate({
            onsubmit: false,
            rules: {
                receiptNumber: {
                    required: true,
                    minlength: 5,
//                    maxlength: 10,
                    digits: true
                },
                idNumber: {
                    required: true,
                    //minlength: 13,
                    //maxlength: 13,
                    digits: true,
                    validateID: true
                }
            }
        }).form();

        //var idValid = eyedee.validateIDNumber($("#idNumber").val());

        if (screenValid === true) {
            customer.formData = $("#customerForm").serializeArray();
            customer.formData.push({"name": "userAction", "value": "create"});
            customer.formData.push({"name": "customerNo", "value": sessionStorage.currentCustomer});
            customer.serverCall(action, customer.formData);
            sessionStorage.action = action;
        }
    },
    fillResult: function (data) {
        var t = $('#customerList').DataTable().clear().draw();
        $.each(data, function (index, row) {
            var paymentButton = "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#myModal'>Payment</button>";
            var viewButton = "<a href='#' onClick='customer.view(" + row.id + ")'>" + row.id + "</a>";
            var button = '<div class="form-group" style="position:relative;left:20px;"><div class="dropdown"><button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Action<span class="caret"></span></button><ul class="dropdown-menu"><li><a onclick="policy.dependent()">Add New</a></li> <li><a href="../Dependant/Replace.html">Replace</a></li></ul></div></div>'
            t.row.add([viewButton, row.idNumber, row.lastName, row.firstName, row.middleName]).draw(false);
        });

    },
    serverCall: function (action, params) {
        $.blockUI();
        try {
            $.ajax({
                url: eyedee.url + "/PartnerServlet",
                type: "POST",
                dataType: 'jsonp',
                crossDomain: true,
                data: params,
                async: false,
                timeout: 10000
            }).done(function (dataR, textStatus, jqXHR) {
                if (eyedee.checkErrors(dataR.messages) !== true) {
                    switch (action) {
                        case "search" :
                            customer.fillResult(dataR.value.data);
                            break;
                        case "create":
                            customer.view(dataR.value.data);
                            break;
                        case "get":
                            customer.populate(dataR.value.data);
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
    eyedee.getFieldOptions();


    $('#customerList').DataTable({
        "pagingType": "full_numbers",
        "bLengthChange": false,
        "bPaginate": true,
        "pageLength": 9,
        "autoWidth": true
    });
    $('#policyList').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });
    $('#claimList').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });
    $('#paymentList').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });
    $("input[name='extReceipt']").click(function () {
        if ($(this).val() === 'Yes') {
            $("#extReceiptTab").show();
        } else {
            $("#extReceiptTab").hide();
        }
    });

    $("#productCode").change(function () {
        var amount = eyedee.getProductPrice($("#productCode").val());
        $("#paymentAmount").val(amount);

    });
    switch (sessionStorage.action) {
        case "create":
            $("#fullName").val(sessionStorage.customerFullName);
            break;
        case "view":
            var data = [];
            data.push({"name": "userAction", "value": "get"});
            data.push({"name": "customerNo", "value": sessionStorage.currentCustomer});
            customer.serverCall("get", data);
            break;
    }

    eyedee.getProducts("FUNERALPOLICY");

    eyedee.getWorkcenters();
});


