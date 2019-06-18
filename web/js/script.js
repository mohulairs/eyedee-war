/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


eyedee = {
    url: "http://localhost:8080/eyedee-war",
    //url: "http://raretagqa.ddns.net:27937/eyedee-war",
    fieldOption: null,
    fieldOptions: null,
    dropdownFilled: null,
    error_message: null,
    success_message: null,
    action: null,
    products: null,
    loadDropDownValues: function (options) {
        // eyedee.resetDropDowns();
        //eyedee.dropdownFilled = true;
        $.each(options, function (index, row) {
            $("#" + row.fieldName).append('<option value="' + row.code + '">' + row.description + '</option>');
        });

        $('.selectpicker').selectpicker('refresh');

    },
    navigateHome: function () {
        window.location.href = "../";
    },
    navigateFromRoot: function (workcenter, action) {
        sessionStorage.action = action;
        sessionStorage.workcenter = workcenter;
        window.location.href = workcenter + "/" + action + ".html";
    },
    navigate: function (workcenter, action) {
        sessionStorage.action = action;
        sessionStorage.workcenter = workcenter;
        window.location.href = "../" + workcenter + "/" + action + ".html";
    },
    navigateBack: function (workcenter, action) {
        sessionStorage.action = action;
        sessionStorage.workcenter = workcenter;
        window.location.href = "../" + workcenter + "/" + action + ".html";
    },
    buildNavbar: function () {
        var data = eyedee.serverCall("getWorkcenters", []);
        $('#sidebar').empty();
        data.sort(function (a, b) {
            return a.sequence - b.sequence;
        });
        $.each(data, function (index, row) {
            var li = "<a onclick='eyedee.navigate(&quot;" + row.ID + "&quot; ,&quot;Search&quot;)'>" + row.Description + "</a>";
            $('#sidebar').append(li);
            if (sessionStorage.workcenter === row.ID) {
                $("#" + row.ID).addClass("active");
            }
        });
    },
    buildHomeNavbar: function () {
        var data = eyedee.serverCall("getWorkcenters", []);
        $("#sidebar").empty();
        data.sort(function (a, b) {
            return a.sequence - b.sequence;
        });
        $.each(data, function (index, row) {
            var li = "<a onclick='eyedee.navigate(&quot;" + row.ID + "&quot; ,&quot;Search&quot;)'>" + row.Description + "</a>";
            $('#sidebar').append(li);
            if (sessionStorage.workcenter === row.ID) {
                $("#" + row.ID).addClass("active");
            }
        });
    },
    download: function (transaction) {
        $.fileDownload('some/file.pdf')
                .done(function () {
                    alert('File download a success!');
                })
                .fail(function () {
                    alert('File download failed!');
                });
    },
    downloadPDF: function (transationNo) {
        sessionStorage.currentTransaction = transationNo;
        $.ajax({
            url: eyedee.url + "/FileDownloadServlet",
            type: "POST",
            contentType: 'application/octet-stream',
//            dataType: 'jsonp',
            crossDomain: true,
            xhrFields: {withCredentials: false},
            data: {"referenceNo": sessionStorage.currentTransaction}
        }).done(function (response, status, error)
        {
            if (eyedee.checkErrors(response.messages) !== true) {
//                window.location.href = "../" + response.value.data + "/View.html";
            }
            $.each(response.messages, function (index, row) {
                eyedee.showMessage(row.type, row.message);
            });
        }).fail(function (response, status, error)
        {
            eyedee.showMessage("E", error);
        });

    },
    generatePDF: function (transationNo) {
        sessionStorage.currentTransaction = transationNo;

        $.ajax({
            url: eyedee.url + "/GeneratePDF",
            type: "POST",
            //   contentType: 'application/json; charset=utf-8',
            dataType: 'jsonp',
            crossDomain: true,
            data: {"referenceNo": sessionStorage.currentTransaction}
        }).done(function (response, status, error)
        {
            if (eyedee.checkErrors(response.messages) !== true) {
                eyedee.downloadPDF(transationNo);
            }
            $.each(response.messages, function (index, row) {
                eyedee.showMessage(row.type, row.message);
            });
        }).fail(function (response, status, error)
        {
            eyedee.showMessage("E", error);
        });

    },
    viewTransaction: function (transationNo) {
        sessionStorage.currentTransaction = transationNo;
        sessionStorage.action = "view";
        $.ajax({
            url: eyedee.url + "/TransactionController",
            type: "POST",
            //   contentType: 'application/json; charset=utf-8',
            dataType: 'jsonp',
            crossDomain: true,
            data: {"transactionNo": sessionStorage.currentTransaction, "userAction": "getTransactionType"}
        }).done(function (response, status, error)
        {
            if (eyedee.checkErrors(response.messages) !== true) {
                window.location.href = "../" + response.value.data + "/View.html";
            }
            $.each(response.messages, function (index, row) {
                eyedee.showMessage(row.type, row.message);
            });
        }).fail(function (response, status, error)
        {
            eyedee.showMessage("E", error);
        });

    },
    resetDropDowns: function () {
        $('select option').remove();
        $('select').append('<option></option>');
    },
    getFieldOptions: function () {
        eyedee.serverCall("getFieldOptions", []);

    },
    getWorkcenters: function () {
        return eyedee.serverCall("getWorkcenters", []);
    },
    login: function () {
        window.location.href = './login.html';
    },
    logout: function () {
        sessionStorage.authenticated = false;
        window.location.href = '../';
    },
    authenticate: function () {
        $.blockUI();
        $.ajax({
            url: eyedee.url + "/Authenticate",
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: 'jsonp',
            crossDomain: true,
            data: {"username": ($("#username").val()), "password": ($("#password").val())}
        }).done(function (dataR, status, error)
        {
            if (eyedee.checkErrors(dataR.messages) !== true) {
                if (dataR.value.data.authenticated === true) {
                    sessionStorage.authenticated = true;
                    if (dataR.value.data.passwordStatus === "INIT") {
                        window.location.href = './changePassword.html';
                    } else {
                        window.location.href = './';
                    }
                } else {
                    eyedee.showMessage("E", "Incorrect username/password");

                }
            }
            $.each(dataR.messages, function (index, row) {
                eyedee.showMessage(row.type, row.message);
            });

        }).fail(function (data, status, error)
        {
            noty({
                text: error,
                type: 'error'
            });
//            formWrapper.clearMessages();
//            displayError(data);
        });

    },
    changePassword: function () {
        var screenValid = $("#changePasswordForm").validate({
            rules: {
                currentPassword: "required",
                newPassword: "required",
                confirmNewPassword: {
                    equalTo: "#newPassword"
                }
            }
        }).form();
        if (screenValid)
        {
            var data = $("#changePasswordForm").serializeArray();
            $.ajax({
                url: eyedee.url + "/ChangePassword",
                type: "POST",
                //   contentType: 'application/json; charset=utf-8',
                dataType: 'jsonp',
                crossDomain: true,
                data: data
            }).done(function (data, status, error)
            {
                if (eyedee.checkErrors(data.messages) !== true) {
                    sessionStorage.authenticated = true;
                    window.location.href = './';
                }
                $.each(data.messages, function (index, row) {
                    eyedee.showMessage(row.type, row.message);
                });
            }).fail(function (data, status, error)
            {

            });
        }
    },
    forgotPassword: function () {

        $.ajax({
            url: eyedee.url + "/ForgotPassword",
            type: "POST",
            //   contentType: 'application/json; charset=utf-8',
            dataType: 'jsonp',
            crossDomain: true,
            data: {"email": ($("#email").val())}
        }).done(function (data, status, error)
        {
            if (eyedee.checkErrors(data.messages) !== true) {
                window.location.href = './forgotPasswordConfirmation.html';
            }
            $.each(data.messages, function (index, row) {
                eyedee.showMessage(row.type, row.message);
            });
        }).fail(function (data, status, error)
        {
            eyedee.showMessage("E", error);
        });

    },
    setUserAction: function (action) {
        sessionStorage.action = action;
        //eyedee.action = action;
    },
    serverCall: function (action, params) {
        params.push({"name": "userAction", "value": action});
        $.blockUI();
        try {
            $.ajax({
                url: eyedee.url + "/Controller",
                type: "POST",
                //dataType: 'jsonp',
                // crossDomain: true,
                data: params,
                async: false,
                timeout: 10000
            }).done(function (dataR, textStatus, jqXHR) {
                switch (action) {
                    case "getFieldOptions" :

                        eyedee.fieldOptions = dataR.value.data;
                        eyedee.loadDropDownValues(dataR.value.data);
                        break;

                    case "getWorkcenters":
                        //eyedee.buildNavbar(dataR.value.data);
                        return (dataR.value.data);
                        break;

                }
                $.unblockUI();

                noty({
                    text: 'Request Succesfully processed',
                    type: 'success',
                    timeout: true
                });


            }).fail(function (jqXHR, textStatus, errorThrown) {

                $.unblockUI();
                noty({
                    text: errorThrown,
                    type: 'error'
                });

            }).always(function () {
                //alert("complete");
            });
        } catch (err) {
            eyedee.showMessage("E", "Server not available");
        }
    },
    checkErrors: function (messages) {
        var errors = false;
        $.each(messages, function (index, row) {
            if (row.type === "E") {
                errors = true;
            }
        });
        return errors;
    },
    getProducts: function (productCategory) {
        var params = [{"name": "productCategory", "value": productCategory}];
        params.push({"name": "userAction", "value": "getCategoryProducts"});
        $.blockUI();
        try {
            $.ajax({
                url: eyedee.url + "/ProductServlet",
                type: "POST",
                dataType: 'jsonp',
                crossDomain: true,
                data: params,
                async: false,
                timeout: 10000
            }).done(function (dataR, textStatus, jqXHR) {
                $.unblockUI();
                eyedee.products = dataR.value.data;
                $('#productCode option').remove();
                $('#productCode').append('<option></option>');
                $.each(eyedee.products, function (index, row) {
                    $("#productCode").append('<option value="' + row.productCode + '">' + row.description + '</option>');
                });
                $('.selectpicker').selectpicker('refresh');
                $.each(dataR.messages, function (index, row) {
                    eyedee.showMessage(row.type, row.message);
                });


            }).fail(function (dataR, textStatus, errorThrown) {

                $.unblockUI();
                eyedee.showMessage('E', errorThrown);

            }).always(function () {

            });
        } catch (err) {
            eyedee.showMessage('E', 'Server not available');

        }

    },
    getProductPrice: function (product) {
        var amount;
        $.each(eyedee.products, function (index, row) {
            if (row.productCode === product) {
                amount = row.price;
            }
        });
        return amount;
    },
    showMessage: function (type, message) {
        var l_type;
        var l_timeout;
        switch (type) {
            case 'S':
                l_type = 'success';
                l_timeout = true;
                break;
            case 'E':
                l_type = 'error';
                l_timeout = false;
                break;
            case 'N':
                l_type = 'notification';
                l_timeout = false;
                break;
        }
        noty({
            text: message,
            type: l_type,
            timeout: l_timeout
        });
    },
    clearMessage: function () {
        $("#message").empty();
    },
    convertToCurrency: function (field) {
//        var cfgCulture = 'en-GB';
//        $.preferCulture(cfgCulture);
        $('#' + field).maskMoney();

    },
    validateIDNumber: function (idNumber) {
        if (idNumber.length === 0) {
            return true;
        }

        if (idNumber.length !== 13 || !eyedee.isNumber(idNumber)) {
            return false;
        }

        var tempDate = new Date(idNumber.substring(0, 2), idNumber.substring(2, 4) - 1, idNumber.substring(4, 6));
        var id_date = tempDate.getDate();
        var id_month = tempDate.getMonth();
        var id_year = tempDate.getFullYear();
        var fullDate = id_date + "-" + (id_month + 1) + "-" + id_year;
//        if (!((tempDate.getYear() === idNumber.substring(0, 2)) && (id_month === idNumber.substring(2, 4) - 1) && (id_date === idNumber.substring(4, 6)))) {
//         
//            return false;
//        }

        // get the gender
        var genderCode = idNumber.substring(6, 10);
        var gender = parseInt(genderCode) < 5000 ? "Female" : "Male";
        // get country ID for citzenship
        var citzenship = parseInt(idNumber.substring(10, 11)) === 0 ? "Yes" : "No";
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
            multiplier = (multiplier % 2 === 0) ? 1 : 2;
        }
        if ((checkSum % 10) !== 0) {
            return false;
        }
        ;
        // if no error found, hide the error message

        return true;
    },
    isNumber: function (n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
    }
};

$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);


$(document).ready(function () {
//    setTimeout(function () {
//        eyedee.logout();
//    }, 3000);
      sessionStorage.authenticated = true;
    $('.collapse').on('hidden.bs.collapse', function () {
        $("#overviewSpan").removeClass("glyphicon-triangle-bottom").addClass("glyphicon-triangle-right");
    });
    $('.collapse').on('shown.bs.collapse', function () {
        $("#overviewSpan").removeClass("glyphicon-triangle-right").addClass("glyphicon-triangle-bottom");
    });


    jQuery.validator.addMethod("validateID", function (value) {
        return  eyedee.validateIDNumber(value);
    }, "ID Number is invalid");

    jQuery.validator.classRuleSettings.validateID = {validateID: true};

    $.blockUI.defaults.css = {
        padding: 0,
        margin: 0,
        width: '30%',
        top: '40%',
        left: '35%',
        textAlign: 'center',
        cursor: 'wait'
    };

    $("#homeLink").click(function () {
        eyedee.navigateHome();

    });

//    $("#message").dialog({
//        title : ""
//    });
});
$(document).idle({
    onIdle: function () {
        bootbox.dialog({
            closeButton: false,
            message: "Your session has expired",
            buttons: {
                btn1: {
                    label: "Login",
                    className: "btn-success",
                    callback: function () {
                        eyedee.logout();
                    }
                }
            }
        });

    },
    idle: 300000
});

function Option() {
    this.code;
    this.description;
}

function Item() {
    this.itemNo;
    this.category;
    this.product;
    this.quantity;
    this.unitPrice;
    this.taxAmount;
    this.totalExcl;
    this.totalIncl;
}

function ServiceItem() {
    this.itemNo;
    this.productCode;
    this.productDescription;
    this.requestorCode;
    this.requestorName;
    this.recipientCode;
    this.recipientName;
    this.amount;

}
