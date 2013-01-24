/*Darstellung von TImelineElementen im Detail*/

$(function(){
    if(!parseBoolean($.getUrlVar("editable")))
    {
        $("img").hide();
    }
    $("#audio").each(function(){
        $("#title").val(unescape($.getUrlVar("title")));
        $("#title-text").text(unescape($.getUrlVar("title")));

        start = new Date(parseInt($.getUrlVar("from")));
        end = new Date(parseInt($.getUrlVar("to")));

        $("#start").val(start.getHours()+":"+start.getMinutes());
        $("#end").val(end.getHours()+":"+end.getMinutes());

        $("#start-text").text(start.getHours()+":"+start.getMinutes());
        $("#end-text").text(end.getHours()+":"+end.getMinutes());

        $("input,textarea").hide();

        $("#audio img").click(function(){
            $("#title-text, #start-text, #end-text").hide();
            $("input,textarea").show();
        });

        for(var i=0;$.getUrlVar("smsg["+i+"]") != undefined;i++){
            addMessage($.getUrlVar("smsg["+i+"]"));
        }

        $("#submitAudio").click(function(){
            var from = new Date();
            var to = new Date();
            from.setHours($("#start").val().match(/^[0-9]{1,2}/));
            from.setMinutes($("#start").val().match(/[0-9]{1,2}$/));
            from.setSeconds(0);
            to.setHours($("#end").val().match(/^[0-9]{1,2}/));
            to.setMinutes($("#end").val().match(/[0-9]{1,2}$/));
            to.setSeconds(0);

            window.NewElementHandler.updateAudio($("#title").val(),from,to);
        });
    });
    $("#interview").each(function(){

        $("#thema").val(unescape($.getUrlVar("title")));
        $("#thema-text").text(unescape($.getUrlVar("title")));
        $("#name").val(unescape($.getUrlVar("name")));
        $("#name-text").text(unescape($.getUrlVar("name")));
        $("#phone").val(unescape($.getUrlVar("phone")));
        $("#phone-text").text(unescape($.getUrlVar("phone")));
        $("#mail").val(unescape($.getUrlVar("mail")));
        $("#mail-text").text(unescape($.getUrlVar("mail")));
        $("#street").val(unescape($.getUrlVar("street")));
        $("#street-text").text(unescape($.getUrlVar("street")));
        $("#city").val(unescape($.getUrlVar("city")));
        $("#city-text").text(unescape($.getUrlVar("city")));
        $("#questions").val(unescape($.getUrlVar("questions")));
        $("#questions-text").text(unescape($.getUrlVar("questions")));
        $("#infotext").val(unescape($.getUrlVar("infotext")));
        $("#infotext-text").text(unescape($.getUrlVar("infotext")));

        start = new Date(parseInt($.getUrlVar("from")));
        end = new Date(parseInt($.getUrlVar("to")));

        $("#start").val(start.getHours()+":"+start.getMinutes());
        $("#end").val(end.getHours()+":"+end.getMinutes());

        $("#start-text").text(start.getHours()+":"+start.getMinutes());
        $("#end-text").text(end.getHours()+":"+end.getMinutes());

        $("input, .input").hide();
        $("#interviewpartner").show();
        $("#thema-text, #start-text, #end-text").show();

        for(var i=0;$.getUrlVar("smsg["+i+"]") != undefined;i++){
            addMessage($.getUrlVar("smsg["+i+"]"));
        }

        $("#interview img").click(function(){
            $("#thema-text, #start-text, #end-text").hide();
            $("#interviewpartner").hide();
            $("input,textarea, .input").show();
        });

        $("#submitInterview").click(function(){
            var from = new Date();
            var to = new Date();
            from.setHours($("#start").val().match(/^[0-9]{1,2}/));
            from.setMinutes($("#start").val().match(/[0-9]{1,2}$/));
            from.setSeconds(0);
            to.setHours($("#end").val().match(/^[0-9]{1,2}/));
            to.setMinutes($("#end").val().match(/[0-9]{1,2}$/));
            to.setSeconds(0);
            window.NewElementHandler.updateInterview($("#thema").val(),from,to,$("#name").val(),$("#phone").val(),$("#mail").val(),$("#street").val(),$("#city").val(),$("#questions").val(),$("#infotext").val());
        });

    });
    $.mask.definitions['H']='[012]';
    $.mask.definitions['M']='[012345]';
    $("#start, #end").mask("H9:?M9");
    $("#start, #end").blur(function(){
        if($(this).val().match(/[0-9]{1,2}$/)==null) $(this).val($(this).val() + "00");
    });
});

var addMessage = function(message){
    $("#socialMedia ul ").append("<li>"+unescape(message)+"</li>");
}

var highlightSocialMedia = function(){
    $("#socialMedia").css("border","3px solid #0F0");
}

var unHighlightSocialMedia = function(){
    $("#socialMedia").css("border","none");
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