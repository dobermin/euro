function btn_champion() {

    let $radio = $('input:checked').val();
    post("champion", {champion: $radio}, function (result) {
        resultMessage(result);
    })
}