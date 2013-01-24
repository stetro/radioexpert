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

var setModule = function(name, infotext, type, start, end, createdat, active) {
        var addingClass="";
        if(active == true){
            var addingClass="running";
        }
        beginning = Math.round((start - timeLineFrom) / 1000 / 60);
        ending = Math.round((end - timeLineFrom) / 1000 / 60);
        $("#timelineComponent #timeline #modules").append('<div data-createdat="'+createdat+'" class="module ' + type+' '+addingClass + '" style="' + 'height: ' + ((ending - beginning) * timeLineSize - 12) + 'px;' + 'top:' + (beginning * timeLineSize+6) + 'px">' + '' + name + '&nbsp;' + '<span>' + infotext + '</span><img class="play" src="img/play.png" width="20"  /></div>');
        $("#modules .module").unbind('click').bind({click:selectModule});
        $("#modules .module .play").unbind('click').bind({click:startModule});
    }

var selectModule = function (event){
    window.SelectElementHandler.showElement($(this).attr("data-createdat"));
    $(".active").removeClass("active");
    $(this).addClass("active");
}

var startModule = function(event){
    window.SelectElementHandler.startElement($(this).parent().attr("data-createdat"));
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
    if(!parseBoolean($.getUrlVar("editable")))
    {
        $("img").hide();
    }
    window.setInterval(function(){
        now = new Date();
        if(timeLineTo > now && now > timeLineFrom){
            top = (now - timeLineFrom) / 1000 / 60;
            $("#pointer").css("top",top * timeLineSize);
        }
    },1000);
});




/* HILFSMETHODEN */

function printDate(temp) {
    var temp = new Date();
    var dateStr = padStr(  padStr(temp.getDate())+ "." +padStr(1 + temp.getMonth())+"."+temp.getFullYear());
    return dateStr;
}

function padStr(i) {
    return(i < 10) ? "0" + i : "" + i;
}

$.extend({
    getUrlVars: function(){
        var vars = [], hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++)
        {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    },
        getUrlVar: function(name){
            return $.getUrlVars()[name];
    }
});
function parseBoolean(string) {
  switch (String(string).toLowerCase()) {
    case "true":
    case "1":
    case "yes":
    case "y":
      return true;
    case "false":
    case "0":
    case "no":
    case "n":
      return false;
    default:
      //you could throw an error, but 'undefined' seems a more logical reply
      return undefined;
  }
}