/*
 * Timeline Komponenten JavaScirpt
 */

var timeLineFrom;
var timeLineTo;
var timeLineSize;

var buildUpTimeLineTimes = function(from, to, step, size) {
        timeLineFrom = from;
        timeLineTo = to;
        timeLineSize = size;
        $("div#timeline span.time").remove();
        $("div#timeline hr").remove();
        for(var j = 0, i = from; i < to; i = i + 1 * 60 * 1000, j++) {
            if(j % step == 0) {
                var time = new Date(i);
                var minutes = ((time.getMinutes() < 10) ? "0" : "") + time.getMinutes();
                $("div#timeline").append('<span class="time" style="top:' + j * size + 'px">' + time.getHours() + ':' + minutes + '</span>');
                $("div#timeline").append('<hr class="fat" style="top:' + j * size + 'px"/>');
            } else if(j % 5 == 0) {
                $("div#timeline").append('<hr class="smooth" style="top:' + j * size + 'px"/>');
            } else {
                $("div#timeline").append('<hr style="top:' + j * size + 'px"/>');
            }
        }
    }

var setModule = function(name, infotext, type, start, end) {
        beginning = Math.round((start - timeLineFrom) / 1000 / 60);
        ending = Math.round((end - timeLineFrom) / 1000 / 60);
        $("#timelineComponent #timeline #modules").append('<div class="module ' + type + '" style="' + 'background-color: rgba(150, 150, 150, 0.7);' + 'height: ' + (ending - beginning) * timeLineSize + 'px;' + 'top:' + beginning * timeLineSize + 'px">' + '<h5>' + name + '</h5>' + '<p>' + infotext + '</p></div>');

    }

var updateTimeLine = function(title, intro, start, end) {
        $("div#header h4").empty().append(title).append("&nbsp;<span class=\"descr\">" + printDate(start) + "</span>");
        console.log("<span>" + printDate(start) + "</span>");
        $("div#header blockquote").empty().append(intro);
        buildUpTimeLineTimes(start, end, 10, 10);
    }

var updateTimeLineSize = function() {
        $("div#timeline").css("height", window.innerHeight - 145);
        console.log("foo")
    }

$(function() {
    window.onresize = updateTimeLineSize;
    updateTimeLineSize();
});


/* HILFSMETHODEN */

function printDate(temp) {
    var temp = new Date();
    var dateStr = padStr(temp.getFullYear()) + "." + padStr(1 + temp.getMonth()) + "." + padStr(temp.getDate());
    return dateStr;
}

function padStr(i) {
    return(i < 10) ? "0" + i : "" + i;
}