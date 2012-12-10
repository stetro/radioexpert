var addMessage = function(message,sender,time){
    $("#messages").append('<div class="message"><strong>'+sender+'</strong>('+time+') :<div class="text">'+message+'</div></div>');
    $("html, body").animate({ scrollTop: $(document).height() }, "fast");
}