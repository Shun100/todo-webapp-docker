import { ConfirmDialog } from './ConfirmDialog.js';

document.getElementById('deleteBtn').addEventListener('click', () => {
  const templateElem = document.getElementById('deleteConfirmDialogTemplate');
  const formElem = document.getElementById('taskDetailForm');
  const confirmDialog = new ConfirmDialog(templateElem, formElem);
  confirmDialog.open();
});