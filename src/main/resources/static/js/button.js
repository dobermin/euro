$(document).ready(function () {
    // появление/затухание кнопки #back-top
    $(function () {
        // прячем кнопку #back-top
        let $backTop = $("#back-top");
        $($backTop).hide();

        $(window).scroll(function () {
            if ($(this).scrollTop() > 100) {
                $($backTop).fadeIn();
            } else {
                $($backTop).fadeOut();
            }
        });

        // при клике на ссылку плавно поднимаемся вверх
        $($backTop).click(function () {
            $("body,html").animate({
                scrollTop: 0
            }, 800);
            return false;
        });
    });
});