function showPopup(message, time) {
	$("document").ready(function(){
		$('body').append("<div id='note'>" + message + "</div>");
		setTimeout(closePopup, time);
	});
}

function closePopup() {
	$("document").ready(function(){
		var element = $('body').find('#note');
		element.remove();
	});
}