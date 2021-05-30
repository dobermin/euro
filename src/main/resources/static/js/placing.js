$(document).ready(function () {

    $(document).on('click', 'select.placing', function () {
        SelectClick(this);
    });

});

function btn_default($i) {
    let $pow = 3 + 7 * ($i - 1);
    for (let $j = 0; $j < 4; $j++) {
        let $tr = $('table tr')[$pow + $j];
        let $option = $('option', $tr);

        $($option[0]).attr({'selected': 'true', 'disabled': 'true'});
        for (let $a = 1; $a < 5; $a++) {
            $($option[$a]).removeAttr('selected disabled');
        }

    }
}

$('tfoot tr:last-child td').show();

function SelectClick($this) {
    let $tr = $($this).parents()[1];
    let $index = $($tr).index();
    let $arr = [];

    for (let $i = 0; $i < 5; $i++) {
        let $option = $('option', $tr)[$i];
        if ($($option).val() === $($this).val()) {
            $($option).attr('selected', 'true');
            $($('option', $tr)[0]).removeAttr('selected');
        }
    }
    for (let $i = $index - 3; $i <= $index + 3; $i++) {
        if ($i < 3) continue;
        let $t = $('table tr')[$i];
        $('select option:selected', $($t)).each(function () {
            let $val = $(this).val();
            if ($val.length > 0)
                $arr.push($val);
        });
    }

    for (let $a = $index - 3; $a <= $index + 3; $a++) {
        $tr = $('table tr')[$a];
        for (let $i = 1; $i < 5; $i++) {
            let $option = $('option', $($tr))[$i];
            let $val = $($($option)).val();
            $($option).removeAttr('disabled');
            for (let $j = 0; $j < $arr.length; $j++) {
                if ($val === $arr[$j]) {
                    $($option).attr('disabled', 'true');
                }
            }
        }
    }
}

function btn_placing() {
    let $team = [];
    let $position = [];
    let $select = $('select option:selected');
    $($select).each(function (i) {
        $position[i] = $(this).text();
    });
    $($select).parents('td').prev().each(function (i) {
        $team[i] = $(this).text().match('[А-Я][а-яА-Я \-\.]{1,}')[0];
    });
    post("placing_save", {team: $team.reverse(), position: $position}, function (result) {
        resultMessage(result)
    })
}