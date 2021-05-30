/*
* получаем url
* */
let $url = window.location['pathname'].substr(1);

$.getScript('js/core.js');

switch ($url) {
    case 'registration':
        $.getScript('js/registration.js');
        break;
    case 'authorization':
        $.getScript('js/authorization.js');
        break;
    case 'prognosis':
        $.getScript('js/prognosis.js');
        break;
    case 'placing':
        $.getScript('js/placing.js');
        break;
    case 'champion':
        $.getScript('js/champion.js');
        break;
    case 'forecast':
        $.getScript('js/forecast.js');
        break;
    case 'bombardier':
        $.getScript('js/bombardier.js');
        break;
    case 'next':
        $.getScript('js/next.js');
        break;
    case 'settings':
        $.getScript('js/settings.js');
        break;
    case 'users':
        $.getScript('js/users.js');
        break;
}

$(document).on('click',
    'input[type="radio"]',
    function () {
        ButtonShow();
    });
$(document).on('change',
    '.placing',
    function () {
        ButtonShow();
    })
$(document).on('click', '.nav-item.dropdown, .navbar-toggler', function () {
    FooterBottom();
});

$(document).ready(function () {
    FooterBottom();
});


function FooterBottom() {
    if (document.documentElement.clientHeight > document.body.scrollHeight) {
        $('footer').attr('id', 'footer')
    } else
        $('footer').removeAttr('id');
}

/*
* удаляем active с навигации
* */
$(document).ready(function () {
    $('a.nav-link').on('click', function () {
        $('a.nav-link').removeClass('active');
        $(this).addClass('active');
    });
    $(this).on('click', function () {
        $('a.nav-link').removeClass('active');
    });
});
/**
 * меняем background
 * */
$(document).ready(function () {
    if (document.documentElement.clientWidth < document.documentElement.clientHeight) {
        $('body').addClass('bg-90');
    } else {
        $('body').removeClass('bg-90');
    }
});
/**
 * меняем шрифт
 * */
$(document).ready(function () {
    if ($url === 'rating') {
        const $width = document.documentElement.clientWidth;
        if ($width < 992) {
            const $w_d = 991;
            const $f_s = 1.1;
            const $k = $width / $w_d;
            $('.standing').css('font-size', $f_s * $k + 'rem');
        }
    }
});
/**
 * если нажата кнопка 'enter'
 * */
$(document).keypress(function (e) {
    if (e.which === 13) {
        $($button).click();
    }
});