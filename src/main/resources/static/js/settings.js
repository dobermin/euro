function btn_settings() {
    let $settings = {};
    $('input[type="text"]').each(function (i) {
        $settings[$(this).attr("id")] = $(this).val();
    })
    post("settings", $settings, function (result) {
        resultMessage(result);
        ButtonShow();
    })
}