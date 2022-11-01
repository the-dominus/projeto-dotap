const tagHora = document.querySelector("#hora");

function getTime() {
	const date = new Date();



	tagHora.textContent = date.toLocaleTimeString(navigator.language, {
		hour: '2-digit',
		minute: '2-digit'
	});
}

function getLoopBySecond() {
	const secondsInMs = 1000 * 60
	const currentSecondsInMs = new Date().getSeconds() * 1000
	
	return secondsInMs - currentSecondsInMs
}

getTime();
setInterval(getTime, getLoopBySecond());