
window.addEventListener("load", () => {
	const colaborador = new URLSearchParams(window.location.search).get("colaborador");
	
	if (colaborador) {
		document.querySelector('#selectColaborador').value = colaborador
	}
})

