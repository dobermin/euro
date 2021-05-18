function forecast() {

    let $tour = $('#tour').val();
    let $user = $('#member').val();

    post("forecast_tour", {tour: $tour, user: $user}, function (result) {
        try {
            $("thead").remove();
            $("tbody").remove();
            $("tfoot").before(result);
            $("footer#footer").css("position", "initial");
        } catch (e) {
        }
    })
}