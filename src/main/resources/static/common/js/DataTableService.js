import AjaxService from './AjaxService.js';
import MessageService from './MessageService.js';

/*
 * 공통 - 데이터테이블 서비스 클래스
 */
class DataTableService {

    constructor(selector, dataToSendUrl, options) {

        this.dataToSendUrl = dataToSendUrl;
        this.originalData = []; // 원본 데이터를 저장할 변수

        // 체크박스 사용 여부 : 기본값은 false
        this.useCheckboxesMode = options.hasOwnProperty('useCheckboxesMode') ? options.useCheckboxesMode : false;

        this.table = $(selector).DataTable({
            ...options,
            initComplete: () => { // 데이터 로드 완료 시 호출되는 콜백
                this.originalData = this.initializeOriginalData();
                console.log('originalData : ', this.originalData);

                if (this.useCheckboxesMode) {
                    this.addCheckboxes();
                }
            }
        });

        // 편집모드 - 활성화 여부 : 기본값은 false
        this.editMode = options.editMode || false;
        // 편집모드 - 값 수정 가능 여부 : 기본값은 true
        this.modifyMode = options.hasOwnProperty('modifyMode') ? options.modifyMode : true;
        // 편집모드 - 추가 기능 사용여부 : 기본값은 true
        this.addMode = options.hasOwnProperty('addMode') ? options.addMode : true;
        // 편집모드 - 삭제 기능 사용여부 : 기본값은 true
        this.removeMode = options.hasOwnProperty('removeMode') ? options.removeMode : true;

        this.formSelector = options.formSelector || '';

        this.ajaxService = new AjaxService(); // AjaxService 초기화
        this.messageService = new MessageService(); // MessageService 초기화

        this.addedRows = []; // 추가된 행을 추적하기 위한 배열
        this.deletedRows = new Set(); // 삭제된 행의 인덱스를 저장

        this.buttonContainer = $(selector + '-container').find('.dataTable-button-container');

        if (this.editMode) {
            this.addEditButton();
        }
    }

    initializeOriginalData() {
        // 초기 데이터의 깊은 복사본을 생성하여 변경되지 않도록 보장
        return this.table.rows().data().toArray().map(row => JSON.parse(JSON.stringify(row)));
    }

    addRowData(rowData) {
        // 새로운 데이터를 테이블에 추가하고 행의 노드 반환
        const newRow = this.table.row.add(rowData).draw(false).node();

        // 새로운 행의 첫 번째 셀에 체크박스 추가
        const checkbox = $('<input>').attr('type', 'checkbox');
        $(newRow).prepend($('<td>').css('width', '60px').append(checkbox)); // 선택 열의 너비 제한

        // 새로운 행에 대해 gridStatus를 'I'(추가됨)로 설정
        rowData.gridStatus = 'I';

        // 추가된 행을 추적하기 위해 저장
        this.addedRows.push(this.table.row(newRow).index()); // 새로 추가된 행의 인덱스를 저장
    }

    getRowData(row) {
        const rowData = this.table.row(row).data();

        // 현재 셀의 값을 rowData에 업데이트
        $(row).find('td:not(:first)').each((index, td) => {
            const cellValue = $(td).text().trim();
            const column = this.table.settings()[0].aoColumns[index];
            if (column && column.data) {
                rowData[column.data] = cellValue;
            }
        });

        return rowData;
    }

    // 사용자 정의 버튼을 편집 모드에 추가
    addCustomEditModeButton(text, className, onClick , attributes = {}) {
        if (!this.editModeCustomButtons) {
            this.editModeCustomButtons = [];
        }

        const customButton = $('<button>')
            .text(text)
            .addClass(className)
            .click(onClick);

        // 추가적인 속성 설정 (ex: data-toggle, data-target)
        $.each(attributes, (key, value) => {
            customButton.attr(key, value);
        });

        this.editModeCustomButtons.push(customButton);
    }

    addEditButton() {
        const editButton = $('<button>')
            .text('편집')
            .addClass('btn btn-primary')
            .click(() => this.enterEditMode());

        this.buttonContainer.prepend(editButton);
    }

    enterEditMode() {
        this.editMode = true;

        this.buttonContainer.find('button:contains("편집")').hide();

        // 취소 버튼은 항상 추가 (가장 나중에 추가되어 가장 오른쪽에 보이도록)
        const cancelButton = $('<button>')
            .text('취소')
            .addClass('btn btn-secondary')
            .click(() => this.exitEditMode());

        this.buttonContainer.prepend(cancelButton);

        // 저장 버튼은 항상 추가 (취소 버튼 다음으로 추가되어 그 왼쪽에 보이도록)
        const saveButton = $('<button>')
            .text('저장')
            .addClass('btn btn-primary')
            .click(() => this.saveChanges());

        this.buttonContainer.prepend(saveButton);

        // 삭제 버튼은 this.removeMode가 true일 경우에만 추가
        if (this.removeMode) {
            const deleteButton = $('<button>')
                .text('삭제')
                .addClass('btn btn-danger')
                .click(() => this.deleteSelectedRows());

            this.buttonContainer.prepend(deleteButton);
        }

        // 추가 버튼은 this.addMode가 true일 경우에만 추가
        if (this.addMode) {
            const addButton = $('<button>')
                .text('추가')
                .addClass('btn btn-success')
                .click(() => this.addRow());

            this.buttonContainer.prepend(addButton);
        }

        // 사용자 정의 버튼 추가
        if (this.editModeCustomButtons && Array.isArray(this.editModeCustomButtons)) {
            this.editModeCustomButtons.forEach(customButton => {
                this.buttonContainer.prepend(customButton);
            });
        }

        if (this.modifyMode) {
            const columnSettings = this.table.settings().init().columns;
            this.table.rows().every(function () {
                const row = this.node();
                $(row).find('td').each(function (index, td) {
                    const column = columnSettings[index];
                    // isEdit이 명시적으로 false인 경우에만 편집 불가능하도록 설정
                    if (column.isEdit === false) {
                        $(td).attr('contenteditable', 'false');
                    } else {
                        $(td).attr('contenteditable', 'true');
                    }
                });
            });
        }

        if (!this.useCheckboxesMode) { // 체크박스 사용 여부가 true가 아닌 경우에만 (편집 모드에서만 체크박스를 사용하는 경우)
            this.addCheckboxes();
        }
    }

    exitEditMode() {
        this.editMode = false;

        // 추가, 삭제, 저장, 취소 버튼 및 사용자 정의 버튼 제거
        $(this.table.table().container()).find('button:contains("추가"), button:contains("삭제"), button:contains("저장"), button:contains("취소")').remove();

        if (this.editModeCustomButtons && Array.isArray(this.editModeCustomButtons)) {
            this.editModeCustomButtons.forEach(customButton => {
                $(customButton).remove();
            });
        }

        $(this.table.table().container()).find('button:contains("편집")').show();

        // 추가된 행 중 비어 있는 행 제거
        this.addedRows.forEach(rowIdx => {
            const row = this.table.row(rowIdx).node();
            const isEmpty = $(row).find('td:not(:first)').toArray().every(td => $(td).text().trim() === '');

            if (isEmpty) {
                this.table.row(rowIdx).remove();
            }
        });
        this.addedRows = [];

        // 삭제 상태였던 행을 복구
        this.deletedRows.forEach(rowIdx => {
            const row = this.table.row(rowIdx).node();
            $(row).removeClass('deleted-row');
            $(row).css('background-color', '');
            $(row).find('td:not(:first)').attr('contenteditable', 'true');
            $(row).find('input[type="checkbox"]').prop('disabled', false);
        });
        this.deletedRows.clear();

        this.table.draw();

        this.table.rows().every(function () {
            const row = this.node();
            $(row).find('td').attr('contenteditable', 'false');
        });

        if (!this.useCheckboxesMode) { // 체크박스 사용 여부가 true가 아닌 경우에만 (편집 모드에서만 체크박스를 사용하는 경우)
            this.removeCheckboxes();
        }
    }

    addCheckboxes() {
        // 헤더에 체크박스 열 추가
        const header = $(this.table.table().header());
        const selectAllCheckbox = $('<input>')
            .attr('type', 'checkbox')
            .click(() => this.toggleSelectAll(selectAllCheckbox));

        const checkboxHeader = $('<th>')
            .css('width', '60px') // 선택 열의 너비 제한
            .append(selectAllCheckbox);

        header.find('tr').prepend(checkboxHeader);

        // 본문에 체크박스 열 추가
        this.table.rows().every(function () {
            const row = this.node();
            const checkbox = $('<input>').attr('type', 'checkbox');
            $(row).prepend($('<td>').css('width', '60px').append(checkbox)); // 선택 열의 너비 제한
        });
    }

    toggleSelectAll(selectAllCheckbox) {
        const isChecked = selectAllCheckbox.is(':checked');
        this.table.rows().every(function () {
            const row = this.node();
            $(row).find('input[type="checkbox"]').prop('checked', isChecked);
        });
    }

    removeCheckboxes() {
        // 헤더에서 체크박스 열 제거
        const header = $(this.table.table().header());
        header.find('th:first-child').remove();

        // 본문에서 체크박스 열 제거
        this.table.rows().every(function () {
            const row = this.node();
            $(row).find('td:first-child').remove();
        });
    }

    addRow() {
        // 각 열에 맞는 기본 데이터를 생성
        const columnData = {};
        this.table.settings().init().columns.forEach(column => {
            columnData[column.data] = column.defaultContent || '';
        });

        // gridStatus를 'I'로 설정
        columnData.gridStatus = 'I';

        // 새로운 행 추가
        const newRow = this.table.row.add(columnData).draw().node();

        // 새로운 행에도 체크박스 추가
        const checkbox = $('<input>').attr('type', 'checkbox');
        $(newRow).prepend($('<td>').css('width', '60px').append(checkbox)); // 선택 열의 너비 제한

        // 새로운 행도 편집 가능하도록 설정
        if (this.modifyMode) {
            const columnSettings = this.table.settings().init().columns;
            this.table.rows().every(function () {
                const row = this.node();
                $(row).find('td').each(function (index, td) {
                    const column = columnSettings[index];
                    // isEdit이 명시적으로 false인 경우에만 편집 불가능하도록 설정
                    // if (column.isEdit === false) {
                    //     $(td).attr('contenteditable', 'false');
                    // } else {
                    //     $(td).attr('contenteditable', 'true');
                    // }
                    $(td).attr('contenteditable', 'true');
                });
            });
        }

        // 추가된 행을 추가된 목록에 저장
        this.addedRows.push(this.table.row(newRow).index());
    }

    deleteSelectedRows() {
        const that = this; // 클래스 인스턴스 참조
        let checkedCount = 0; // 체크된 행의 수를 저장하는 변수

        this.table.rows().every(function () {
            const row = this.node();
            if ($(row).find('input[type="checkbox"]').is(':checked')) {
                checkedCount++; // 체크된 행이 있을 경우 카운트 증가
                $(row).data('gridStatus', 'D'); // 삭제된 행은 'D'로 상태 지정
                $(row).addClass('deleted-row'); // 삭제된 행을 시각적으로 표시
                $(row).css('background-color', '#f0f0f0'); // 행을 회색으로 표시
                $(row).find('td:not(:first)').attr('contenteditable', 'false'); // 편집 비활성화
                $(row).find('input[type="checkbox"]').prop('disabled', true); // 체크박스 비활성화

                // 삭제된 행의 인덱스를 저장
                that.deletedRows.add(this.index());
            }
        });

        // 체크된 행이 하나도 없을 경우 메시지 표시
        if (checkedCount === 0) {
            that.messageService.showWarning('체크박스를 통해 삭제할 행을 선택하세요.', '주의');
        }
    }

    saveChanges() {
        const dataToSend = [];

        const originalData = this.originalData; // 원본 데이터에 접근

        const self = this; // 'this'에 대한 참조를 self에 저장

        this.table.rows().every(function (rowIdx) {
            const rowNode = this.node(); // 현재 행의 DOM 노드를 가져옴
            const rowData = self.getRowData(rowNode); // getRowData를 사용하여 데이터 수집

            if ($(rowNode).hasClass('deleted-row')) {
                rowData.gridStatus = 'D'; // 삭제된 행의 gridStatus를 'D'로 설정
            } else if (rowData.gridStatus === 'I') {
                rowData.gridStatus = 'I'; // 새로 추가된 행은 gridStatus 'I'를 가짐
            } else {
                // 수정된 행 감지
                let isModified = false;

                const originalRowData = originalData[rowIdx] || {};
                for (let key in rowData) {
                    if (rowData.hasOwnProperty(key)) {
                        const originalValue = originalRowData[key] || '';
                        const currentValue = rowData[key] || '';

                        // 값을 문자열로 변환하여 비교
                        if (String(originalValue) !== String(currentValue)) {
                            isModified = true;
                            break;
                        }
                    }
                }

                if (isModified) {
                    rowData.gridStatus = 'U'; // 수정된 행의 gridStatus를 'U'로 설정
                } else if (!rowData.gridStatus) {
                    rowData.gridStatus = ''; // 변경되지 않은 행의 gridStatus를 기본값으로 설정
                }
            }

            dataToSend.push(rowData); // 수집된 데이터를 전송할 배열에 추가
        });

        console.log('dataToSend :', dataToSend);

        // AjaxService를 사용하여 데이터를 JSON 형식으로 전송
        this.ajaxService.request(this.dataToSendUrl, 'POST', dataToSend)
            .then(response => response.json())
            .then(data => {
                this.deletedRows.clear(); // 데이터가 성공적으로 저장되면 삭제된 행의 인덱스 초기화
                this.table.ajax.reload(null, false); // 데이터 테이블을 다시 로드하여 최신 데이터 반영
            })
            .catch(error => {
                this.table.ajax.reload(null, false); // 에러 상태가 반환되더라도, 데이터 테이블을 다시 로드하여 최신 데이터 반영
                console.error('DataTableService save error : ', error);
            });

        this.exitEditMode(); // 저장 후 편집 모드 종료
    }

}

export default DataTableService;
