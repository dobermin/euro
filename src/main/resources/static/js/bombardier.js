/*
* меняем активную команду
* */
function team() {
    let $team = $('option:selected').text();
    post("bombardier_player", {team: $team}, function (result) {
        try {
            $("tbody").html(result);
            $("footer#footer").css("position", "initial");
        } catch (e) {
        }
    })
}

/*
* сделать прогноз
* */
function btn_bombardier() {
    let $player = $('input:checked').attr('id');
    post("bombardier_save", {bombardier: $player}, function (result) {
        resultMessage(result);
    })
}