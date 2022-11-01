const inputData = document.querySelector("#data")
const formData = document.querySelector("#formData")

inputData.addEventListener('change', () => {
	
	formData.action = "/projeto-dotap/colaborador/pegar-pontos-por-data"
	formData.method = "GET"
	formData.submit()
})