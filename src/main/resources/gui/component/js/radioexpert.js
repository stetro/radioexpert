/*
 * Chat Komponenten JavaScirpt
*/

var addMessage = function(message, sender, time) {
    $("#messages").append('<div class="message"><strong>' + sender + '</strong>&nbsp;(' + time + ') :<div class="text">' + message + '</div></div>');
    $("html, body").animate({
        scrollTop: $(document).height()
    }, "fast");
}

/*
 * Timeline Komponenten JavaScirpt
*/
var buildUpTimeLineTimes = function(from, to, step) {
    for(var j = 0, i = from; i < to; i = i + step * 60 * 1000, j++) {
        var time = new Date(i);
        var minutes = ((time.getMinutes()<10)?"0":"") + time.getMinutes();
        $("div#timeline").append('<span class="time" style="top:' + j * 40 + 'px">' + time.getHours() + ':' + minutes + '</span>');
    }
}

var updateTimeLineSize = function(){
    $("div#timeline").css("height", window.innerHeight-145);
}

$(function() {
    buildUpTimeLineTimes(1355583717886, 1355597317886, 10);
    updateTimeLineSize();
    $("#date").datepicker();
});

