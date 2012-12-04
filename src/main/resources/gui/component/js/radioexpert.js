var addMessage = function(message,sender,time){
    var date = new Date();
    date.setTime(time*10);
    $("#messages").append('<div class="message"><strong>'+sender+'</strong>('+date.getHours()+':'+date.getMinutes()+') :<div class="text">'+message+'</div></div>');
    $("html, body").animate({ scrollTop: $(document).height() }, "fast");
}