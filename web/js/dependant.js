/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


dependant = {
    cancel: function () {
        sessionStorage.action = "view";
        window.location.href = "../FuneralPolicy/View.html";
    },
    view: function () {

    },
    save: function (process) {
        var screenValid = $("#dependantForm").validate({
            onsubmit: false,
            rules: {
                idNumber: {
                    //required: true,
//                    minlength: 13,
//                    maxlength: 13,
                    digits: true,
                    validateID: true
                }
            }
        }).form();
        if (screenValid) {
            var data = $("#dependantForm").serializeArray();
            dependant.serverCall(process, data);

        }
    },
    serverCall: function (action, params) {
        params.push({"name": "userAction", "value": action});
        $.blockUI();
        try {
            $.ajax({
                url: eyedee.url + "/DependentController",
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
                            dependant.cancel();
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
                    $.unblockUI();

                    $.each(dataR.messages, function (index, row) {
                        eyedee.showMessage(row.type, row.message);
                    });
                }
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
    switch (sessionStorage.action) {
        case "create":
            $("#policyNo").val(sessionStorage.currentTransaction);
            $("#customer").val(sessionStorage.customerFullName);
            break;
        case "view":
            var data = [];
            data.push({"name": "userAction", "value": "get"});
            data.push({"name": "policyNo", "value": sessionStorage.currentTransaction});
            sessionStorage.action = "get";
            policy.serverCall(data);
            break;
    }
    eyedee.getWorkcenters();


});