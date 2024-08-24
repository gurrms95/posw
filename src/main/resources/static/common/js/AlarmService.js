/** 알람 팝업 호출 */
function openAlarmPopup(userId){
    window.open('/alarm/getAlarm/'+userId, 'Popup', 'width=600,height=400,top=50%,left=50%');
}

class AlarmService {
    constructor() {
    }

    /** 브라우저 쿠키 설정 (SET) 하루 단위로 설정 */
    setCookie(name, value, days){
        let date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        let expires = "expires=" + date.toUTCString();
        document.cookie = name + "=" + value + ";" + expires + ";path=/";
    }

    /** 브라우저 쿠키 설정 (GET) */
    getCookie(name) {
        let nameEQ = name + "=";
        let ca = document.cookie.split(";");
        let result = null;

        ca.forEach(function(c){
            c = c.trim();
            if (c.indexOf(nameEQ) === 0) {
                result = c.substring(nameEQ.length, c.length);
            }
        });

        return result;
    }

    /** 알람 유무 확인 */
    hasAlarm(userId){
        fetch('/alarm/hasAlarm/'+userId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => response.json())
        .then(data => {
            if (data > 0) {
                openAlarmPopup(userId);
            }
        })
        .catch(error => {
            console.error('Error: ', error);
            alert(error);
        });
    }
}
export default AlarmService;