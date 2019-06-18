/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

policy = {
    formData: null,
    create: function () {
        window.location.href = "../FuneralPolicy/New.html";
    },
    cancel: function () {
        switch (sessionStorage.workcenter) {
            case "Customer" :
                eyedee.setUserAction("view");
                window.location.href = "../Customer/View.html";
                break;
            case "FuneralPolicy":
                eyedee.setUserAction("search");
                window.location.href = "../FuneralPolicy/Search.html";
                break;
        }

    },
    search: function () {
        var screenValid = $("#policyForm").validate({
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
            policy.formData = $("#policyForm").serializeArray();
            policy.formData.push({"name": "userAction", "value": "search"});
            sessionStorage.action = "search";
            policy.serverCall("search", policy.formData);

        }

    },
    view: function (policyNo) {
        policy.setCurrent(policyNo);
        sessionStorage.action = "view";
        window.location.href = "../FuneralPolicy/View.html";
    },
    dependent: function () {
        sessionStorage.action = "create";
        window.location.href = "../Dependant/New.html";
    },
    payment: function () {
        sessionStorage.action = "create";
        window.location.href = "../Payment/New.html";
    },
    claim: function () {
        sessionStorage.action = "Create";
        window.location.href = "../Claim/New.html";
    },
    populatePolicy: function (data) {
        
              var policyData = "";
        policyData = policyData + "<dt>Policy No</dt>";
        policyData = policyData + "<dd>" + data.policyNo + "</dd>";
        policyData = policyData + "<dt>Customer</dt>";
        policyData = policyData + "<dd>" + data.customer + "</dd>";
        policyData = policyData + "<dt>Sales Representative</dt>";
        policyData = policyData + "<dd>" + data.salesRepresentative + "</dd>";
        policyData = policyData + "<dt>Sales Area</dt>";
        policyData = policyData + "<dd>" + data.salesArea + "</dd>";
        policyData = policyData + "<dt>Funeral Plan</dt>";
        policyData = policyData + "<dd>" + data.productDescription + "</dd>";
        policyData = policyData + "<dt>Monthly Premium</dt>";
        policyData = policyData + "<dd>" + data.premium + "</dd>";
        policyData = policyData + "<dt>Date Joined</dt>";
        policyData = policyData + "<dd>" + data.dateJoined + "</dd>";
        policyData = policyData + "<dt>Date Effective</dt>";
        policyData = policyData + "<dd>" + data.dateEffective + "</dd>";
        policyData = policyData + "<dt>Status</dt>";
        policyData = policyData + "<dd>" + data.status + "</dd>";
        policyData = policyData + "<dt>Status Date</dt>";
        policyData = policyData + "<dd>" + data.statusDate + "</dd>";
        $("#policyData").html(policyData);
        $("#policyNo").val(data.policyNo);
        $("#customer").val(data.customer);

        sessionStorage.customerFullName = data.customer;
        $("#salesRepresentative").val(data.salesRepresentative);
        $("#salesArea").val(data.salesArea);
        $("#product").val(data.productDescription);
        $("#monthlyPremium").val(data.premium);
        $("#dateJoined").val(data.dateJoined);
        $("#dateEffective").val(data.dateEffective);
        $("#status").val(data.status);
        $("#statusDate").val(data.statusDate);

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
    setCurrent: function (policyNo) {
        sessionStorage.currentTransaction = policyNo;
    },
    save: function (action) {
        var screenValid = $("#policyForm").validate({
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
                },
                productCode:{
                    validateProduct: true
                }
            }
        }).form();

        //var idValid = eyedee.validateIDNumber($("#idNumber").val());

        
        if (screenValid === true) {
            policy.formData = $("#policyForm").serializeArray();
            policy.formData.push({"name": "userAction", "value": "create"});
            policy.formData.push({"name": "customerNo", "value": sessionStorage.currentCustomer});
            policy.serverCall(action, policy.formData);
            sessionStorage.action = action;
        }
    },
    validateProduct: function () {
        var valid = true;
        var product = $("#productCode").val();
        var customer = JSON.parse(sessionStorage.getItem("customer"));
        $.each(customer.policies, function (index, row) {
            if (product === row.productCode){
                valid = false;
            }
        });
        return valid;
    },
    fillResult: function (data) {
        var t = $('#policyList').DataTable().clear().draw();
        $.each(data, function (index, row) {
            var paymentButton = "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#myModal'>Payment</button>";
            var viewButton = "<a href='#' onClick='policy.view(" + row.policyNo + ")'>" + row.policyNo + "</a>";
            var button = '<div class="form-group" style="position:relative;left:20px;"><div class="dropdown"><button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Action<span class="caret"></span></button><ul class="dropdown-menu"><li><a onclick="policy.dependent()">Add New</a></li> <li><a href="../Dependant/Replace.html">Replace</a></li></ul></div></div>'
            t.row.add([viewButton, row.customer, row.productDescription, row.status]).draw(false);
        });

    },
    serverCall: function (action, params) {
        $.blockUI();
        try {
            $.ajax({
                url: eyedee.url + "/PolicyServlet",
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
                            policy.fillResult(dataR.value.data);
                            break;
                        case "create":
                            policy.view(dataR.value.data);
                            break;
                        case "get":
                            policy.populatePolicy(dataR.value.data);
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

    $('#policyList').DataTable({
        "pagingType": "full_numbers",
        "bLengthChange": false,
        "bPaginate": true,
        "pageLength": 9,
        "autoWidth": true
    });
    $('#dependentList').dataTable({
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
    
    
     jQuery.validator.addMethod("validateProduct", function () {
        return  policy.validateProduct();
    }, "Policy with selected product already exist for customer");
    
    switch (sessionStorage.action) {
        case "create":
            $("#customer").val(sessionStorage.customerFullName);
            break;
        case "view":
            var data = [];
            data.push({"name": "userAction", "value": "get"});
            data.push({"name": "policyNo", "value": sessionStorage.currentTransaction});
            policy.serverCall("get", data);
            break;
    }

    eyedee.getProducts("FUNERALPOLICY");
    eyedee.getWorkcenters();


});


