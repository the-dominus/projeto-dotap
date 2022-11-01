const inputData = document.querySelector("#data")
const inputsHora = document.querySelectorAll("input[name='hora']")
const formData = document.querySelector("#formData")

inputData.addEventListener('change', () => {
	
	formData.action = "/projeto-dotap/colaborador/pegar-pontos-por-data"
	formData.method = "GET"
	
	inputsHora.forEach((input) => {
		input.checked = false;
	})
	
	 formData.submit()
})