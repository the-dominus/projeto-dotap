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
  console.log("")
  if (event.target == modalAdd) {
    modalAdd.style.display = "none";
  }
  if (event.target == modalRemove) {
    modalRemove.style.display = "none";
  }
}
