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

        window.NewElementHandler.newBroadcast(
            from.getTime(),
            to.getTime(),
            $("#title").val(),
            $("#intro").val(),
            $("#description").val()
        );
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
            window.NewElementHandler.newInterview($("#thema").val(),from,to,$("#name").val(),$("#phone").val(),$("#mail").val(),$("#street").val(),$("#city").val(),$("#questions").val(),$("#infotext").val());
        });
    $("#submitAudio").click(function(){
        var from = new Date();
        var to = new Date();
        from.setHours($("#start").val().match(/^[0-9]{1,2}/));
        from.setMinutes($("#start").val().match(/[0-9]{1,2}$/));
        from.setSeconds(0);
        to.setHours($("#end").val().match(/^[0-9]{1,2}/));
        to.setMinutes($("#end").val().match(/[0-9]{1,2}$/));
        to.setSeconds(0);
        window.NewElementHandler.newAudio($("#title").val(),from,to);
    });
});
