/*
 * 공통 - Ajax 요청 클래스
 */
class AjaxService {
    constructor() {
        this.csrfToken = $('meta[name="_csrf"]').attr('content');
        this.csrfHeader = $('meta[name="_csrf_header"]').attr('content');
    }


    /**
     * 일반적인 요청 시 사용
     * 사용방법 : AjaxTestController 참조 
     * @param {string} url
     * @param {string} [method=GET]
     * @param {Object} [data={}]
     * @returns {Promise}
     */
    async request(url, method = 'GET', data = {}) {
        const options = {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                [this.csrfHeader]: this.csrfToken
            }
        };

        if (method !== 'GET') {
            options.body = JSON.stringify(data);
        } else if (Object.keys(data).length > 0) {
            const params = new URLSearchParams(data).toString();
            url += `?${params}`;
        }

        try {
            const response = await fetch(url, options);
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message);
            }
            return response;
        } catch (error) {
            return Promise.reject({ textStatus: error.message });
        }
    }
}

export default AjaxService;
