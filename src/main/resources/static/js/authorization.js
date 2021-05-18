function btn_authorization() {
    let arr = [];
    let val;
    $('input[type="text"],input[type="password"]').each(function () {
        val = $(this).val();
        arr.push(val);
    });
    let user = {
        login: arr[0],
        password: arr[1],
    };

    post("authorization_check", user, function (result) {
        try {
            if (result["address"]) {
                document.location.href = result["address"];
            } else {
                $('.error').parent().remove();
                $('table').append('<tr><td colspan="2" class="error">Неправильный логин или пароль</td></tr>');
            }
        } catch (e) {
        }
    })
}