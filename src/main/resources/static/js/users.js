function btn_users() {

    let $users = {};
    let $list = [];
    $('tbody tr').each(function () {

        $users["id"] = $("td", this).first().attr("id")
        $('input[type="text"]', this).each(function () {
            $users[$(this).attr("id")] = $(this).val();
        })
        $list.push($users)
        $users = {}
    })
    post("users", $list.slice(0, -1), function (result) {
        resultMessage(result);
    })
}