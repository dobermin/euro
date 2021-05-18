function btn_registration() {
    const $arr = [];
    let $val;
    $('input[type="text"],input[type="password"]').each(function () {
        $val = $(this).val();
        $arr.push($val);
    });
    post("registration", {
        'name': $arr[0],
        'surname': $arr[1],
        'login': $arr[2],
        'password': $arr[3]
    }, function (result) {
        resultMessage(result)
    })
}