// Get the modal
var modalAdd = document.getElementById("modal-add");
var modalRemove = document.getElementById("modal-remove");

// Get the button that opens the modal
var btnAddRegister = document.getElementById("btnAddRegister");
var btnRemoveRegister = document.getElementById("btnRemoveRegister");


// When the user clicks on the button, open the modal
btnAddRegister.onclick = function() {
  modalAdd.style.display = "block";
}
btnRemoveRegister.onclick = function() {
  modalRemove.style.display = "block";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  console.log("")
  if (event.target == modalAdd) {
    modalAdd.style.display = "none";
  }
  if (event.target == modalRemove) {
    modalRemove.style.display = "none";
  }
}
