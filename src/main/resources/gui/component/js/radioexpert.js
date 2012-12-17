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
var buildUpTimeLineTimes = function(from, to, step,size) {

    $("div#timeline span.time").remove();
    $("div#timeline hr").remove();

    for(var j = 0, i = from; i < to; i = i +1 * 60 * 1000, j++) {
        if(j%step==0){
            var time = new Date(i);
            var minutes = ((time.getMinutes()<10)?"0":"") + time.getMinutes();
            $("div#timeline").append('<span class="time" style="top:' + j * size + 'px">' + time.getHours() + ':' + minutes + '</span>');
            $("div#timeline").append('<hr class="fat" style="top:'+j*size+'px"/>');
        }
        else if(j%5==0)
        {
            $("div#timeline").append('<hr class="smooth" style="top:'+j*size+'px"/>');
        }
        else
        {
            $("div#timeline").append('<hr style="top:'+j*size+'px"/>');
        }

    }
}

var updateTimeLine = function(title,intro,start,end){
    $("div#header h4").empty().append(title);
    $("div#header blockquote").empty().append(intro);

    buildUpTimeLineTimes(start,end,10,10);
}

var updateTimeLineSize = function(){
    $("div#timeline").css("height", window.innerHeight-145);
}

$(function() {
    updateTimeLineSize();
    $("#date").datepicker({dateFormat: "dd.mm.yy"});
    $("#submitBroadcast").click(function(){

        var from = $("#date").datepicker("getDate");
        var to = $("#date").datepicker("getDate");
        from.setHours($("#start").val().match(/^[0-9]{1,2}/));
        from.setMinutes($("#start").val().match(/[0-9]{1,2}$/));
        to.setHours($("#end").val().match(/^[0-9]{1,2}/));
        to.setMinutes($("#end").val().match(/[0-9]{1,2}$/));
        console.log(to.getTime())
        console.log(from.getTime())

        window.NewBroadcastHandler.newBroadcast(
            from.getTime(),
            to.getTime(),
            $("#title").val(),
            $("#intro").val(),
            $("#description").val()
        );
    })
});

