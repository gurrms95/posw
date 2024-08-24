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
 * 공통 - Calendar 서비스 클래스
 */
class CalendarService {
    
    constructor() {
        this.calendarInitialized = this.initCalendar();
    }

    async initCalendar() {
        try {
            await loadCSS('/css/miscellaneous/fullcalendar/fullcalendar.bundle.css');
            await loadJS('/js/miscellaneous/fullcalendar/fullcalendar.bundle.js');
            console.log('FullCalendar loaded successfully');
        } catch (error) {
            console.error('FullCalendar initialization failed:', error);
        }
    }

    async renderCalendar(elementId, options) {
        await this.calendarInitialized;
        if (typeof FullCalendar !== 'undefined') {
            const calendarEl = document.getElementById(elementId);
            if (calendarEl) {
                const calendar = new FullCalendar.Calendar(calendarEl, options);
                calendar.render();
                console.log('Calendar rendered successfully');
                return calendar;
            } else {
                console.error(`Element with id ${elementId} not found`);
            }
        } else {
            console.error('FullCalendar is not loaded');
        }
    }
}

export default CalendarService;
