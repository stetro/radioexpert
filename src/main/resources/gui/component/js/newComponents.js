/*
Neue Sendung Komponente
*/
$(function(){
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
    });
    $("#submitAudio").click(function(){
        var from = $("#date").datepicker("getDate");
        var to = $("#date").datepicker("getDate");
        from.setHours($("#start").val().match(/^[0-9]{1,2}/));
        from.setMinutes($("#start").val().match(/[0-9]{1,2}$/));
        to.setHours($("#end").val().match(/^[0-9]{1,2}/));
        to.setMinutes($("#end").val().match(/[0-9]{1,2}$/));
        window.NewAudioHandler.newAudio($("#title").val(),from,to);
    });
});
