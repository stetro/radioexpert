var addMessage = function(message, sender, time) {
		$("#messages").append('<div class="message"><strong>' + sender + '</strong>&nbsp;(' + time + ') :<div class="text">' + message + '</div></div>');
		$("html, body").animate({
			scrollTop: $(document).height()
		}, "fast");
	}


var buildUpTimeLineTimes = function(from, to, step) {
		for(var j = 0, i = from; i < to; i = i + step * 60 * 1000, j++) {
			var time = new Date(i);
			$("div#timeline").append('<span class="time" style="top:' + j * 40 + 'px">' + time.getHours() + ':' + time.getMinutes() + '</span>');
		}
	}

$(function() {
	buildUpTimeLineTimes(1355583717886, 1355597317886, 10);
	$("div#timeline").css("height", window.innerHeight-145);
});