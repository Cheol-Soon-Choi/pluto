//회원가입
function joinUser() {
    let form = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    let id = $('#user_id').val();
    let pass = $('#pass').val();
    let pass2 = $('#pass2').val();

    if (id.replace(/\s | /gi, "").length == 0) {
        alert("이메일(아이디)을 입력해 주세요.");
        $('#user_id').focus();
        return;
    } else if (form.test(id) == false) {
        alert("이메일 형식이 올바르지 않습니다.");
        $('#user_id').focus();
        return;
    }
    if (pass.replace(/\s | /gi, "").length == 0) {
        alert("비밀번호를 입력해주세요.");
        $('#pass').focus();
        return;
    }
    if (pass2.replace(/\s | /gi, "").length == 0) {
        alert("비밀번호를 한번 더 입력해주세요.");
        $('#pass2').focus();
        return;
    }
    if (pass != pass2) {
        alert("비밀번호가 일치하지 않습니다.");
        $('#pass2').val("");
        $('#pass2').focus();
        return;
    }

    let data = {
        'userid': id,
        'password': pass
    };
    $.ajax({
        type: 'POST',
        url: '/users',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (response) {
            alert(response.userid + "님 회원가입을 축하드립니다!");
            window.location.href = "/login"
        }
    })
}

//제품등록
function addProduct() {
    let name = $('#product_name').val();
    let price = $('#product_price').val();
    let desc = $('#product_desc').val();
    let image = $('#product_image').val();
    let quantity = $('#product_quantity').val();
    let code = $('#product_code').val();
    let data = {
        'name': name,
        'price': price,
        'desc': desc,
        'image': image,
        'quantity': quantity,
        'code': code
    }
    $.ajax({
        type: 'POST',
        url: '/api/products',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (response) {
            alert("상품등록 완료!");
            window.location.href = "/test"
        }
    })
}

//제품 리스트
function getProductList() {
    $.ajax({
        type: 'GET',
        url: '/products',
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
        }
    })
}

// 제품 조회
function inquireProduct() {
    let type1 = $('input[name="type1"]:checked').val();
    let type2 = $('input[name="type2"]:checked').val();
    $.ajax({
        type: 'GET',
        url: `/products/category?type1=${type1}&type2=${type2}`,
        contentType: 'application/json',
        success: function (response) {
            console.log(response)
        }
    })
}

// 아이디 중복 확인
function checkid() {
    let checkid = $('#user_id').val();
    let data = {'checkid': checkid};
    $.ajax({
        type: 'POST',
        url: '/users/checkid',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (response) {
            if (response != 1) {
                $('.positive_id').css("display", "inline-block");
                $('.negative_id').css("display", "none");
            } else {
                $('.positive_id').css("display", "none");
                $('.negative_id').css("display", "inline-block");
            }
        }
    })
}

