// 유틸리티 함수: CSS 파일 로드
function loadCSS(url) {
    return new Promise((resolve, reject) => {
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = url;
        link.onload = () => resolve();
        link.onerror = () => reject(new Error(`Failed to load CSS: ${url}`));
        document.head.appendChild(link);
    });
}

// 유틸리티 함수: JS 파일 로드
function loadJS(url) {
    return new Promise((resolve, reject) => {
        const script = document.createElement('script');
        script.src = url;
        script.onload = () => resolve();
        script.onerror = () => reject(new Error(`Failed to load JS: ${url}`));
        document.head.appendChild(script);
    });
}

/*
 * 공통 - Alert, Confirm, Toast 등의 메세지 클래스
 */
class MessageService {
    constructor() {
        this.toastrInitialized = this.initToastr();
        this.sweetAlertInitialized = this.initSweetAlert();
    }

    async initToastr() {
        try {
            await loadCSS('/css/notifications/toastr/toastr.css');
            await loadJS('/js/notifications/toastr/toastr.js');
            
            // Toastr 기본 설정
            toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": false,
                "progressBar": true,
                "positionClass": "toast-top-right",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "1500",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            };
        } catch (error) {
            console.error('Toastr initialization failed:', error);
        }
    }

    async initSweetAlert() {
        try {
            await loadCSS('/css/notifications/sweetalert2/sweetalert2.bundle.css');
            await loadJS('/js/notifications/sweetalert2/sweetalert2.bundle.js');
        } catch (error) {
            console.error('SweetAlert2 initialization failed:', error);
        }
    }

    async showSuccess(message, title = 'Success') {
        await this.toastrInitialized;
        if (typeof toastr !== 'undefined') {
            toastr.success(message, title);
        } else {
            console.error('Toastr is not loaded');
        }
    }

    async showInfo(message, title = 'Info') {
        await this.toastrInitialized;
        if (typeof toastr !== 'undefined') {
            toastr.info(message, title);
        } else {
            console.error('Toastr is not loaded');
        }
    }

    async showWarning(message, title = 'Warning') {
        await this.toastrInitialized;
        if (typeof toastr !== 'undefined') {
            toastr.warning(message, title);
        } else {
            console.error('Toastr is not loaded');
        }
    }

    async showError(message, title = 'Error') {
        await this.toastrInitialized;
        if (typeof toastr !== 'undefined') {
            toastr.error(message, title);
        } else {
            console.error('Toastr is not loaded');
        }
    }

    async showAlert(message, title = 'Alert', icon = 'info') {
        await this.sweetAlertInitialized;
        if (typeof Swal !== 'undefined') {
            Swal.fire({
                title: title,
                text: message,
                icon: icon,
                confirmButtonText: 'OK'
            });
        } else {
            console.error('SweetAlert2 is not loaded');
        }
    }

    async showConfirm(message, title = 'Confirm') {
        await this.sweetAlertInitialized;
        if (typeof Swal !== 'undefined') {
            return Swal.fire({
                title: title,
                text: message,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes',
                cancelButtonText: 'No'
            }).then(result => {
                return result.isConfirmed;
            });
        } else {
            console.error('SweetAlert2 is not loaded');
            return false;
        }
    }

    async showPrompt(message, title = 'Prompt', inputType = 'text', inputValue = '') {
        await this.sweetAlertInitialized;
        if (typeof Swal !== 'undefined') {
            return Swal.fire({
                title: title,
                text: message,
                input: inputType,
                inputValue: inputValue,
                showCancelButton: true,
                confirmButtonText: 'Submit',
                cancelButtonText: 'Cancel'
            }).then(result => {
                if (result.isConfirmed) {
                    return result.value;
                } else {
                    return null;
                }
            });
        } else {
            console.error('SweetAlert2 is not loaded');
            return null;
        }
    }
}

export default MessageService;
