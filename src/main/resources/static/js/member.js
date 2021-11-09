function memberFormSubmit() {
    // let token = $("meta[name='_csrf']").attr("content");
    // let header = $("meta[name='_csrf_header']").attr("content");

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
        // beforeSend: function (xhr) {
        //     xhr.setRequestHeader(header, token)
        // }
    }).done(function (response) {
        alert("회원가입에 성공하였습니다.");
        window.location.href = "/"
    }).fail(function (error) {
        //모든 reason 삭제
        let fieldErrorList = document.getElementsByClassName("fieldError")
        for (let i = 0; i < fieldErrorList.length; i++) {
            fieldErrorList[i].innerText = "";
        }
        //name에 대한 error가 있으면 view에 reason추가
        if (error.responseJSON.errors.some(x => {
            return x.field == "name"
        })) {
            let name = error.responseJSON.errors.filter(x => x.field.includes('name'));
            document.getElementById("nameError").innerText = name[0].reason;
        }
        //email에 대한 error가 있으면 view에 reason추가
        if (error.responseJSON.errors.some(x => {
            return x.field == "email"
        })) {
            let email = error.responseJSON.errors.filter(x => x.field.includes('email'));
            document.getElementById("emailError").innerText = email[0].reason;
        }
        //password에 대한 error가 있으면 view에 reason추가
        if (error.responseJSON.errors.some(x => {
            return x.field == "password"
        })) {
            let password = error.responseJSON.errors.filter(x => x.field.includes('password'));
            document.getElementById("passwordError").innerText = password[0].reason;
        }
        //address에 대한 error가 있으면 view에 reason추가
        if (error.responseJSON.errors.some(x => {
            return x.field == "address"
        })) {
            let address = error.responseJSON.errors.filter(x => x.field.includes('address'));
            document.getElementById("addressError").innerText = address[0].reason;
        }
    })
}