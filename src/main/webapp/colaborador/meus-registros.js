const inputData = document.querySelector("#data")
const formData = document.querySelector("#formData")

inputData.addEventListener('change', () => {
	formData.submit()
})