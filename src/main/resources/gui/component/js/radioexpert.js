var addMessage = function(message,sender,time){
    $("#messages").append('<div class="message"><strong>'+sender+': </strong>'+message+'</div>');
}