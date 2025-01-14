let isAscending = true; // 초기 정렬 상태 (오름차순)

document.addEventListener('DOMContentLoaded', resetButtonClick);

document.getElementById('search-btn').addEventListener('click', searchAndUpdateTable);

function resetButtonClick() {
    // 입력 필드 초기화
    document.getElementById('status').value = '2';

    // 초기 상태로 데이터 로드
    searchAndUpdateTable();
}

async function searchAndUpdateTable() {
    const startDate = document.getElementById("start-date").value;
    const endDate = document.getElementById("end-date").value;
    const productName = document.getElementById("product-name").value;
    const productStatus = document.getElementById("status").value;

    if (new Date(startDate) > new Date(endDate)) {
        alert("시작일이 종료일보다 이후일 수 없습니다.");
        return;
    }

    // 현재 선택된 행의 ID를 저장
    const selectedRows = getSelectedRows();

    try {
        const response = await axios.get('/outgoing/searchByPickingDate', {
            params: {startDate, endDate, productName, productStatus}
        });
        if (response.status === 200) {
            const data = response.data;
            updateTable(data, selectedRows); // 저장된 상태를 함께 전달하여 업데이트
        } else {
            alert("데이터를 가져오는 데 실패했습니다.");
        }
    } catch (error) {
        console.error("AJAX 요청 중 오류 발생:", error);
        alert("서버와의 통신 중 오류가 발생했습니다.");
    }
}

// 테이블 업데이트 함수
function updateTable(data, selectedRows = []) {
    const tbody = document.querySelector('tbody');
    tbody.innerHTML = ''; // 기존 테이블 데이터 초기화
    console.log(data);
    if (data.length === 0) {
        tbody.innerHTML = '<tr><td colspan="9">데이터가 없습니다.</td></tr>'; // 데이터가 없는 경우 메시지 표시
        return;
    }

    data.forEach((item) => {
        const isChecked = selectedRows.includes(item.outgoingId.toString()) ? 'checked' : ''; // 저장된 상태 확인
        const isDisabled = item.outgoingStatus !== 1 ? 'disabled' : ''; // 출고 대기 상태가 아니면 체크박스 비활성화
        // 천 단위 콤마 추가
        const formattedQuantity = item.outgoingQuantity.toLocaleString(); // 수량 포맷팅
        const formattedUnitPrice = item.unitPrice.toLocaleString(); // 단가 포맷팅
        const formattedTotalPrice = item.totalPrice.toLocaleString(); // 총 금액 포맷팅
        const formattedInventoryQuantity = item.inventoryQuantity.toLocaleString(); // 총 금액 포맷팅

        const row = `
        <tr class="text-center" data-row-id="${item.outgoingId}"> <!-- 고유 ID 저장 -->
            <td>OG-${item.outgoingId}</td>
            <td>${item.branchName}</td>
            <td>${item.productName}</td>
            <td>${formattedQuantity}</td>
            <td>${formattedInventoryQuantity}</td>
            <td>${formattedUnitPrice} 원</td>
            <td>${formattedTotalPrice} 원</td>
            <td>${getStatusText(item.outgoingStatus)}</td>
            <td>${item.outgoingDate}</td>
        </tr>
        `;
        tbody.insertAdjacentHTML('beforeend', row);
    });
}

// 상태 텍스트 반환
function getStatusText(status) {
    switch (status) {
        case 0:
            return '출고 예정';
        case 1:
            return '출고 대기';
        case 2:
            return '출고 완료';
        case 3:
            return '출고 취소';
        default:
            return '알 수 없음';
    }
}

document.getElementById('sort-date-btn').addEventListener('click', function () {
    const tbody = document.querySelector('tbody'); // 테이블 본문 가져오기
    const rows = Array.from(tbody.querySelectorAll('tr')); // 모든 행을 배열로 변환

    // 날짜를 기준으로 행 정렬
    rows.sort((a, b) => {
        const dateA = new Date(a.querySelector('td:nth-child(9)').textContent.trim());
        const dateB = new Date(b.querySelector('td:nth-child(9)').textContent.trim());

        // 오름차순/내림차순에 따라 정렬
        return isAscending ? dateA - dateB : dateB - dateA;
    });

    // 정렬된 행을 테이블 본문에 추가
    rows.forEach(row => tbody.appendChild(row));

    // 정렬 상태 반전
    isAscending = !isAscending;
});

// 선택된 체크박스 상태 저장
function getSelectedRows() {
    const selectedRows = [];
    const checkboxes = document.querySelectorAll('.rowCheckbox:checked'); // 선택된 체크박스 가져오기
    checkboxes.forEach(checkbox => {
        const row = checkbox.closest('tr');
        const rowId = row.dataset.rowId; // 데이터 행 ID 가져오기 (rowId는 고유 값으로 설정)
        selectedRows.push(rowId);
    });
    return selectedRows;
}