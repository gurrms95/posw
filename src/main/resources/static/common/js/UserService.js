/*
 * 공통 - 사용자 서비스 클래스
 */
let instance = null;
class UserService {
    constructor() {
        if (!instance) {
            this.selectedUsers = [];  // 선택된 사용자 목록을 저장
            instance = this;
        }

        return instance;
    }

    // 사용자 추가
    addSelectedUsers(users) {
        users.forEach(user => {
            // 중복 체크 후 사용자 추가
            if (!this.selectedUsers.some(u => u.userId === user.userId && u.roleId === user.roleId)) {
                this.selectedUsers.push(user);
            }
        });
    }

    // 선택된 사용자 목록 반환
    getSelectedUsers() {
        return this.selectedUsers;
    }

    // 선택된 사용자 목록 초기화
    clearSelectedUsers() {
        this.selectedUsers = [];
    }
}

export default new UserService();  // UserService 인스턴스가 전역적으로 동일하게 사용되도록 함