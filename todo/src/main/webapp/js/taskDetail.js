const deleteBtn = document.getElementById('deleteBtn');
const deleteConfirmDialogTemplate = document.getElementById('deleteConfirmDialogTemplate');

deleteBtn.addEventListener('click', () => {
  const clone = deleteConfirmDialogTemplate.content.cloneNode(true);

  document.body.appendChild(clone);
  clone.showModal();
});