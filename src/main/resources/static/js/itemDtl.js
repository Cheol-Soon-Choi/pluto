$(document).ready(function () {

    calculateTotalPrice();

    $("#count").change(function () {
        calculateTotalPrice();
    });
});

function calculateTotalPrice() {
    var count = $("#count").val();
    var price = $("#price").val();
    var totalPrice = price * count;
    $("#totalPrice").html(totalPrice + '원');
}

function order() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var url = "/order";
    var paramData = {
        itemId: $("#itemId").val(),
        count: $("#count").val()
    };

    var param = JSON.stringify(paramData);

    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: param,
        dataType: "json",
        cache: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (result, status) {
            alert("주문이 완료 되었습니다.");
            location.href = '/';
        },
        error: function (jqXHR, status, error) {

            if (jqXHR.status == '401') {
                alert('로그인 후 이용해주세요');
                location.href = '/login';
            } else {
                alert(jqXHR.responseText);
            }
        }
    });
}

function addCart() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var url = "/cart";
    var paramData = {
        itemId: $("#itemId").val(),
        count: $("#count").val()
    };

    var param = JSON.stringify(paramData);

    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: param,
        dataType: "json",
        cache: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (result, status) {
            alert("상품을 장바구니에 담았습니다.");
            location.href = '/';
        },
        error: function (jqXHR, status, error) {
            if (jqXHR.status == '401') {
                alert('로그인 후 이용해주세요');
                location.href = '/login';
            } else {
                alert(jqXHR.responseText);
            }
        }
    });
}