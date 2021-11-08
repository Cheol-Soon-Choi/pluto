function memberFormSubmit() {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");

    let data = {
        name: $('#name').val(),
        email: $('#email').val(),
        password: $('#password').val(),
        address: $('#address').val()
    }

    $.ajax({
        type: 'POST',
        url: '/members',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token)
        }
    }).done(function (response) {
        alert("회원가입에 성공하였습니다.");
        window.location.href = "/"
    }).fail(function (error) {
        console.log(JSON.stringify(error))
        // window.location.href = "/members";
    })
}