$(document).on('change', 'input[type="checkbox"]', function () {
    let $team;
    change(4);
    change(2);
    if ($(this).attr('name') === 'tour_1/4') {
        const $tfoot = $('tbody td');
        const $team_1_2 = [];
        const $team_1_4 = [];

        $('input[name="tour_1/2"]:checked').each(function () {
            $team_1_2.push($(this).val());
        });
        $('input[name="tour_1/4"]:checked').each(function () {
            $team_1_4.push($(this).val());
        });
        if (!$(this).prop('checked')) {
            $team = $(this).val();
            const $index = $team_1_2.indexOf($team);
            if ($index !== -1) {
                $team_1_2.splice($index, 1);
            }
        }
        const $length_1_2 = $team_1_2.length;
        const $length_1_4 = $team_1_4.length;
        if ($length_1_4 === 8) {
            $('tbody').show();
        } else {
            $('tbody').hide();
            // setPrognosis('next',{tour_1_4:$team_1_4},'',true,false);
        }
        /**
         * убираем команды в 1/2
         * */
        $('span[class="change_1_2"]').text(4 - $length_1_2);
        $('input[name="tour_1/2"]').removeAttr('disabled');
        $('label:not(.checkbox),input', $tfoot).each(function () {
            $(this).text('').val('').removeAttr('checked title').prop('checked', false);
        });
        /**
         * добавляем команды в 1/2
         * */
        for (let $i = 0; $i < $length_1_4; $i++) {
            $team = $team_1_4[$i];
            $($('label:not(.checkbox)', $tfoot)[$i]).text($team);
            $($('input', $tfoot)[$i]).attr({'title': $team, 'value': $team});
            if ($.inArray($team, $team_1_2) !== -1)
                $($('input', $tfoot)[$i]).prop('checked', true).removeAttr('disabled')
            else
                $($('input', $tfoot)[$i]).prop('checked', false);
        }
        if ($length_1_2 === 4)
            $('input[name="tour_1/2"]:not(":checked")').attr('disabled', 'true');
    }
});

function btn_next() {

    let $tour_4 = getTeam(4);
    let $tour_2 = getTeam(2);

    post("next_save", {tour_4: $tour_4, tour_2: $tour_2}, function (result) {
        resultMessage(result)
    })

}

function getTeam($tour) {
    const $t = [];
    $('input[name="tour_1/' + $tour + '"]:checked').each(function () {
        $t.push($(this).val());
    });

    return $t;
}

function change($tour) {
    const $num = $tour * 2;
    const $change = $('input[name="tour_1/' + $tour + '"]:checked').length;

    $('span[class="change_1_' + $tour + '"]').text($num - $change);
    if ($('input:checked').length === 12) {
        $('input[name="tour_1/' + $tour + '"]:not(":checked")').attr('disabled', 'true');
        $('[name="next"]').parent(2).show();
    }
    if ($change !== $num) {
        $('input[name="tour_1/' + $tour + '"]').removeAttr('disabled');
        $('[name="next"]').parent(2).hide();
    } else {
        $('input[name="tour_1/' + $tour + '"]:not(":checked")').attr('disabled', 'true');
    }
}

if ($url === 'next' && $('input[name="tour_1/4"]:checked').length < 8) {
    $('tbody').hide();
}

$('footer#footer').css("position", "initial");