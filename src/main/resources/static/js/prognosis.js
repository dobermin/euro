/*
* меняем активный тур
* */
$(document).ready(function () {
    ButtonShow();
})
function tour() {
    let $tour = $('thead select').val();
    post("prognosis_tour", {tour: $tour}, function (result) {
        try {
            $("thead").remove();
            $("tbody").remove();
            $("tfoot").before(result);
            $("footer#footer").css("position", "initial");
            ButtonShow();
        } catch (e) {
        }
    })
}

/*
* сделать прогноз
* */
function btn_prognosis() {
    let $prognosis = [];
    let $match = [];
    let $next = [];
    let $tour = [];
    $tour[0] = $('thead option:selected').text();
    $('input[type="text"]').each(function (i) {
        $prognosis[i] = $(this).val();
    });
    $('.match span').each(function (i) {
        $match[i] = $(this).text();
    });
    $('tbody select').each(function (i) {
        $next[i] = $(this).val();
    });
    post("prognosis_save", {tour: $tour, prognosis: $prognosis, country: $match, next: $next}, function (result) {
        resultMessage(result)
    })
    ButtonShow();
}

$(document).on('keyup', 'input[type="text"]', function () {
    let $val_1,
        $val_2,
        $val,
        $tr,
        $preg = /[0-9]/;
    $val = $(this).val().match($preg);
    if ($val) {
        $tr = $(this).parents('tr')[0];
        $val_1 = $($('td input', $tr)[0]).val();
        $val_2 = $($('td input', $tr)[1]).val();
        if ($val_1 === $val_2) {
            $('select', $tr).attr('class', '');
        } else {
            $('select', $tr).addClass('d-none');
            $($('option', $tr)[0]).attr("selected", "selected");
        }
    } else {
        $(this).val('');
    }
});