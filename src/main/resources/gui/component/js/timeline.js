/*
 * Timeline Komponenten JavaScirpt
 */

var timeLineFrom;
var timeLineTo;
var timeLineSize;

var Active = {"broadcast":{"class":"broadcast"},"song":{"class":"broadcast"}}

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

var updatePointer = function(time){
    $("#pointer").animate({
        top: Math.round((time - timeLineFrom) / 1000 / 60)*timeLineSize
    },'fast');

}

var setModule = function(name, infotext, type, start, end) {
        a=new Date(start);
        b=new Date(end);

        beginning = Math.round((start - timeLineFrom) / 1000 / 60);
        ending = Math.round((end - timeLineFrom) / 1000 / 60);
        $("#timelineComponent #timeline #modules").append('<div class="module ' + type + '" style="' + 'height: ' + ((ending - beginning) * timeLineSize - 12) + 'px;' + 'top:' + (beginning * timeLineSize+6) + 'px">' + '<h5>' + name + '</h5>&nbsp;' + '<span>' + infotext + '</span></div>');

    }

var selectElement = function(active, start, end){
    $("#timelineComponent svg").empty();

    positionX=window.innerWidth;
    positionY=0;

    width=100;
    height=150;

    points = ""+positionX - width+","+positionY+height+" ";
    points += ""+positionX+","+positionY+height+" ";
    points += ""+positionX+","+positionY+" ";
    points += ""+positionX - width+","+positionY+"";

    $("svg").append("<polygon class=\""+active.class+"\" points=\""+points+"\"/>")

}

var updateTimeLine = function(title, intro, start, end) {
        $("div#header h4").empty().append(title).append("&nbsp;<span class=\"descr\">" + printDate(start) + "</span>");
        $("div#header blockquote").empty().append(intro);
        buildUpTimeLineTimes(start, end, 10, 10);
        $("div#header").addClass("active");
    }

var updateTimeLineSize = function() {
        $("div#timeline").css("height", window.innerHeight - 145);
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