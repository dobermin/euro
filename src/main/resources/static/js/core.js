const SUCCESS = "Операция выполнена успешно";
const ERROR = "Операция закончилась ошибкой";

let $button = 'button:not(.navbar-toggler)';

let $messages = $('.messages');

/*
* стиль для загрузчика
* */
function LoadingStyle($bool) {
    const $in = 1000;
    const $out = 1000;
    let $loader = $('.loader');
    if ($bool === true) {
        $($loader).css({
            'left': ($('body').width() - $($loader).width()) / 2 + 'px',
            'top': ($(window).height() - $($loader).height()) / 2 + 'px'
        });
        $('.overlay').fadeIn($in);
        $($loader).fadeIn($in);
    } else {
        $($loader).fadeOut($out);
        $('.overlay').fadeOut($out);
        $($messages).fadeIn($in);
        $($messages).fadeOut(4 * $out);
    }
}

/*
* сделать прогноз
* */
function post($url, $data, $function) {
    LoadingStyle(true)
    $.ajax({
        url: "/" + $url,
        contentType: "application/json; charset=UTF-8",
        datatype: "json",
        method: "POST",
        data: JSON.stringify($data),
        success: function (result) {
            $function(result)
            LoadingStyle(false)
        }
    });
}

/*
* спуск вниз
* */
function ScrollBottom() {
    $('body, html').animate({
        scrollTop: $(document).height()
    }, 2000);
}

/*
* показать кнопку
* */
function ButtonShow($scroll) {
    if ($scroll === undefined) $scroll = true;

    $($button).parents(1).show();
    $($button).show();

    if ($scroll === true) ScrollBottom();
}

function resultMessage(result) {
    $($messages).removeClass("d-none");
    try {
        if (!result) {
            $($messages).text(ERROR);
            $($messages).addClass("error");
        } else {
            $($messages).text(SUCCESS);
            $($messages).removeClass("error");
            // $($button).hide();
        }
    } catch (e) {
        $($messages).text(ERROR);
        $($messages).addClass("error");
    }
}

