export class ConfirmDialog {
  constructor(templateElem, formElem) {
    this.templateElem = templateElem;
    this.formElem = formElem;
  }

  /**
   * 確認画面 開く
   */
  open() {
    const clone = this.templateElem.content.cloneNode(true);
    this.formElem.appendChild(clone);
    this.formElem.querySelector('dialog').showModal();

    const submitBtn = document.getElementById('submitBtn');
    const cancelBtn = document.getElementById('cancelBtn');

    submitBtn.addEventListener('click', () => {
      this.formElem.submit();
      this.close();
    });

    cancelBtn.addEventListener('click', () => {
      this.close();
    });
  }

  /**
   * 確認画面 閉じる
   */
  close() {
    const dialogElem = this.formElem.querySelector('dialog');
    dialogElem.close();
    dialogElem.remove();
  }
}