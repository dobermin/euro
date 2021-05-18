function btn_score() {
    let $tr = $('tbody tr');
    let $score = [];
    $tr.each(function () {
        let match = {};
        let $td = $('td', this);
        match.id = $($td[0]).text();
        match.mainHome = $('input', $td[2]).val();
        match.mainAway = $('input', $td[4]).val();
        match.scoreHome = $('input', $td[5]).val();
        match.scoreAway = $('input', $td[7]).val();
        match.next = $('select', $($td[8])).val();
        $score.push(match);
    });
    $score.pop();
    post("score_save", {score: $score}, function (result) {
        resultMessage(result)
    })
}