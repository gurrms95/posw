class FormService {
  constructor(formId) {
    this.form = document.getElementById(formId);
    if (!this.form) {
      throw new Error(`Form with id ${formId} not found`);
    }

    // 실시간 유효성 검증을 위해 이벤트 리스너 추가
    this.addEventListeners();
  }

  /**
   * Form의 Item에 value 세팅
   * @param {Object} data - 백엔드에서 호출받은 데이터
   */
  setFormValues(data) {
    for (const key in data) {
      if (data.hasOwnProperty(key)) {
        const element = this.form.querySelector(`#${key}`);
        if (element) {
          element.value = data[key];
        }
      }
    }
    // 모든 필드의 유효성 검증 상태 초기화
    this.clearAllValidationStates();
  }

  /**
   * Form의 Item에서 값을 가져와 객체에 저장
   * @returns {Object} - 폼 데이터 객체
   */
  getFormValues() {
    const data = {};
    const elements = this.form.querySelectorAll('input, select, textarea');
    elements.forEach(element => {
      if (element.id) {
        data[element.id] = element.value;
      }
    });
    return data;
  }

  /**
   * 유효성 검증을 위한 이벤트 리스너 추가
   */
  addEventListeners() {
    const elements = this.form.querySelectorAll('[required]');
    elements.forEach(element => {
      element.addEventListener('input', () => this.validateField(element));
      element.addEventListener('change', () => this.validateField(element));
      element.addEventListener('blur', () => this.validateField(element));
    });
  }

  /**
   * 개별 필드 유효성 검증
   * @param {HTMLElement} element - 검증할 폼 요소
   */
  validateField(element) {
    const feedbackMessage = element.dataset.invalidFeedback;
    const invalidFeedback = element.nextElementSibling;
    if (!element.value.trim()) {
      element.classList.add('is-invalid');
      if (invalidFeedback && invalidFeedback.classList.contains('invalid-feedback')) {
        invalidFeedback.textContent = feedbackMessage;
        invalidFeedback.style.display = 'block';
      } else if (feedbackMessage) {
        // invalid-feedback 요소가 없으면 새로 생성해서 추가
        const feedbackDiv = document.createElement('div');
        feedbackDiv.className = 'invalid-feedback';
        feedbackDiv.textContent = feedbackMessage;
        feedbackDiv.style.display = 'block';
        element.parentNode.insertBefore(feedbackDiv, element.nextSibling);
      }
      return false;
    } else {
      element.classList.remove('is-invalid');
      if (invalidFeedback && invalidFeedback.classList.contains('invalid-feedback')) {
        invalidFeedback.style.display = 'none';
      }
      return true;
    }
  }

  /**
   * 모든 필드의 유효성 검증 상태 초기화
   */
  clearAllValidationStates() {
    const elements = this.form.querySelectorAll('.is-invalid');
    elements.forEach(element => {
      this.clearValidationState(element);
    });
  }

  /**
   * 개별 필드 유효성 검증 상태 초기화
   * @param {HTMLElement} element - 초기화할 폼 요소
   */
  clearValidationState(element) {
    element.classList.remove('is-invalid');
    const invalidFeedback = element.nextElementSibling;
    if (invalidFeedback && invalidFeedback.classList.contains('invalid-feedback')) {
      invalidFeedback.style.display = 'none';
    }
  }

  /**
   * Form 전체 유효성 검사
   * @returns {boolean} 유효한지 여부
   */
  onValidation() {
    let isValid = true;

    // 폼 요소들을 반복하면서 유효성 검사
    const requiredElements = this.form.querySelectorAll('[required]');
    requiredElements.forEach(element => {
      if (!this.validateField(element)) {
        isValid = false;
      }
    });

    return isValid;
  }
}

export default FormService;
