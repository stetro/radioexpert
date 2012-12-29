/*Darstellung von TImelineElementen im Detail*/

$(function(){
    $("#audio").each(function(){
        $("#title").val($.getUrlVar("title").replace("%20"," "));
        start = new Date(parseInt($.getUrlVar("from")));
        end = new Date(parseInt($.getUrlVar("to")));
        $("#start").val(start.getHours()+":"+start.getMinutes());
        $("#end").val(end.getHours()+":"+end.getMinutes());
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
});

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