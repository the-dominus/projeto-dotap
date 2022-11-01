const modalAdd = document.getElementById("modal-add");
const modalRemove = document.getElementById("modal-remove");

const btnAddRegister = document.getElementById("btnAddRegister");
const btnRemoveRegister = document.getElementById("btnRemoveRegister");

btnAddRegister.onclick = function() {
  modalAdd.style.display = "block";
}
btnRemoveRegister.onclick = function() {
  modalRemove.style.display = "block";
}

window.onclick = function(event) {
  if (event.target == modalAdd) {
    modalAdd.style.display = "none";
  }
  if (event.target == modalRemove) {
    modalRemove.style.display = "none";
  }
}


const inputData = document.querySelector("#data-modal-remove")
const inputsHora = document.querySelectorAll("#hora-modal-remove")
const formData = document.querySelector("#formModalRemove")

inputData.addEventListener('change', () => {
	
	formData.action = "/projeto-dotap/colaborador/pegar-pontos-por-data"
	formData.method = "GET"
	
	inputsHora.forEach((input) => {
		input.checked = false;
	})
	
	 formData.submit()
})

window.addEventListener("load", () => {
	const data = new URLSearchParams(window.location.search).get("data");
	
	if (data !== null) {
		modalRemove.style.display = "block";
	}
})

