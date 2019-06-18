/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
cashup = {
    search_fields: ["ID Number", "Surname"],
    bankDepositPopup: null,
    totalDeposit: 0.00,
    current: null,
    deposits: [],
    create: function () {
        sessionStorage.action = "create";
        window.location.href = "New.html";
    },
    view: function (data) {
        $("#checkingId").val(data.checkingId);
        $("#salesArea").val(data.salesArea);
        $("#employeeResponsible").val(data.employeeResponsible);
        $("#receiptFrom").val(data.receiptFrom);
        $("#receiptTo").val(data.receiptTo);
        $("#cashAmount").val(data.cashAmount);
    },
    cancel: function () {
        window.location.href = "Search.html";
    },
    get: function () {
        var data = [];
        data.push({"name": "checkingId", "value": sessionStorage.current});
        cashup.serverCall("get", data);
    },
    search: function () {
        var screenValid = $("#cashupForm").validate({
            rules: {
                referenceNumber: {
                    require_from_group: [1, ".search-group"]
                },
                receiptNumber: {
                    require_from_group: [1, ".search-group"]
                },
                receiptDateFrom: {
                    require_from_group: [1, ".search-group"]
                },
                receiptDateTo: {
                    require_from_group: [1, ".search-group"]
                },
                salesArea: {
                    require_from_group: [1, ".search-group"]
                }
            }
        }).form();
        if (screenValid) {
            var data = $("#cashupForm").serializeArray();
            cashup.serverCall("search", data);
        }
    },
    addBankDeposit: function () {
        cashup.bankDepositPopup.dialog("open");
    },
    deleteDeposit: function (index) {
        var depo = cashup.current.deposits[index];
        cashup.current.depositAmount = cashup.current.depositAmount - parseFloat(depo.amount, 10);
        $('#totalDepositAmount').val(cashup.current.depositAmount);
        cashup.current.splice(index, 1);
        cashup.refreshDepositTable();

    },
    fillResult: function (data) {
        var t = $('#checkingList').DataTable().clear().draw();
        $.each(data, function (index, row) {
            t.row.add([row.checkingId, row.createdOn, row.createdBy, row.salesArea, row.receiptFrom,row.receiptTo, row.cashAmount]).draw(false);
        });

    },
    refreshDepositTable: function () {
        $('#cash-up-bank-deposit').dataTable().fnClearTable();
        $.each(cashup.current.deposits, function (index, row) {
            var deleteButton = "<input class='action-cancel' type='button' id='deleteDeposit' onClick='cashup.deleteDeposit(" + index + ")' value='Delete'/>";
            $('#cash-up-bank-deposit').dataTable().fnAddData([row.date, row.reference, row.amount, deleteButton]);
        });
        $('#cash-up-bank-deposit').dataTable().fnDraw();
    },
    save: function () {

        var screenValid = $("#cashupForm").validate({
            onsubmit: false,
            rules: {
                receiptFrom: {
                    required: true,
                    minlength: 5,
                    digits: true
                },
                receiptTo: {
                    required: true,
                    minlength: 5,
                    digits: true
                }
            }
        }).form();

        if (screenValid) {
            cashup.current.salesArea = $('#salesArea').val();
            cashup.current.employeeResponsible = $('#employeeResponsible').val();
            cashup.current.receiptFrom = $('#receiptFrom').val();
            cashup.current.receiptTo = $('#receiptTo').val();
            cashup.current.cashAmount = $('#cashAmount').val();
//            var data = $("#cashUpForm").serializeArray();
            var data = [];
            data.push({"name": "checkout", "value": JSON.stringify(cashup.current)});
            data.push({"name": "depositsCount", "value": cashup.current.deposits.length});
            cashup.serverCall("create", data);
        }

    },
    serverCall: function (action, params) {
        params.push({"name": "userAction", "value": action});
        $.blockUI();
        try {
            $.ajax({
                url: eyedee.url + "/CashupServlet",
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
                        {
                            sessionStorage.current = dataR.value.data.checkingId;
                            sessionStorage.action = "view";
                            window.location.href = "View.html";
                            break;
                        }
                        case "search":
                            cashup.fillResult(dataR.value.data);
                            break;
                        case "get":
                        {
                            cashup.view(dataR.value.data);
                            break;
                        }
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
    cashup.current = new Checkout();
    eyedee.convertToCurrency("cashAmount");
    eyedee.convertToCurrency("depositAmount");
    eyedee.convertToCurrency("totalDepositAmount");

    $('#checkingList').DataTable({
        "pagingType": "full_numbers",
        "bLengthChange": false,
        "bPaginate": true,
        "pageLength": 9,
        dom: 'Bfrtip',
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ]

    });

    $('#cash-up-attachment').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });

    $('#cash-up-status').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });

    $('#cash-up-bank-deposit').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });

    cashup.bankDepositPopup = $("#addBankDeposit").dialog({
        title: "Add Bank Deposit",
        autoOpen: false,
        height: 250,
        width: 500,
        modal: true,
        buttons: {
            "Ok": function () {
                cashup.current.depositAmount = cashup.current.depositAmount + parseFloat($('#depositAmount').val(), 10);
                $('#totalDepositAmount').val(cashup.current.depositAmount);

                var depo = new Deposit();
                depo.date = $('#depositDate').val();
                depo.reference = $('#depositReference').val();
                depo.amount = $('#depositAmount').val();
                cashup.current.deposits.push(depo);

                cashup.refreshDepositTable();

                cashup.bankDepositPopup.dialog("close");
            },
            Cancel: function () {
                cashup.bankDepositPopup.dialog("close");
            }
        },
        close: function () {
            $('#depositDate').val("");
            $('#depositReference').val("");
            $('#depositAmount').val("");
        }
    });
    $("#tabs").tabs();
    eyedee.getFieldOptions();   

    switch (sessionStorage.action) {
        case "create":
            break;
        case "view":
            cashup.get();
            break;
    }

eyedee.getWorkcenters();
});

function Deposit() {
    this.date;
    this.reference;
    this.amount;
}

function Checkout()
{
    this.salesArea;
    this.employeeResponsible;
    this.receiptFrom;
    this.receiptTo;
    this.cashAmount;
    this.depositAmount = 0;
    this.deposits = [];
}