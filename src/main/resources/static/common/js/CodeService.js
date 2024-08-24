import AjaxService from './AjaxService.js';
import FormService from './FormService.js';
import MessageService from './MessageService.js';

/*
 * 공통 - 공통코드 서비스 클래스
 */
class CodeService {

    constructor(formId) {
        this.ajaxService = new AjaxService();
        this.codeDetailFormService = new FormService(formId);
        this.messageService = new MessageService();
    }

    getDetail(cmmnCd) {
        this.ajaxService.request('/system/code/detail', 'GET', { cmmnCd })
            .then(response => response.json())
            .then(data => {
                this.codeDetailFormService.setFormValues(data.code);
            })
            .catch(error => {
                console.error(error.textStatus);
            });
    }

    add() {
      const addForm = this.codeDetailFormService.getFormValues();
      console.log('addForm : ', addForm);
    }

    modify() {
        const isValid = this.codeDetailFormService.onValidation();
        if (isValid) {
            this.messageService.showSuccess('수정이 완료되었습니다.', '공통코드');
        }
    }

    async remove() {
      const removeForm = this.codeDetailFormService.getFormValues();
      const isConfirmed = await this.messageService.showConfirm('정말로 삭제하시겠습니까?', `${removeForm.cmmnCd} - ${removeForm.cdNae}`);
      if (isConfirmed) {
        this.messageService.showAlert('삭제가 완료되었습니다.', `${removeForm.cmmnCd} - ${removeForm.cdNae}`);
      } else {
        this.messageService.showError('삭제가 취소되었습니다.', `${removeForm.cmmnCd} - ${removeForm.cdNae}`);
      }
    }
}

export default CodeService;