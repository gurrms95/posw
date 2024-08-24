import AjaxService from './AjaxService.js';

/*
 * 공통 - 역할(ROLE) 서비스 클래스
 */
class RoleService {

    constructor() {
        this.ajaxService = new AjaxService();
    }

    async getUserMappingList(roleId) {
      let userMappingList = [];
  
      try {
          const response = await this.ajaxService.request('/system/role/userMapping/list', 'GET', { roleId });
          
          if (!response.ok) {
              throw new Error('Network response was not ok');
          }
  
          const data = await response.json();
          userMappingList = data.userMappingList;
      } catch (error) {
          console.error('There was a problem with the fetch operation:', error);
          throw error;
      }
  
      return userMappingList;
  }
}

export default RoleService;