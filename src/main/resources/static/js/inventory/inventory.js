// 체크 박스의 체크 여부 확인
let checked = false;

function selectItem() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');

    if (checked) {
        checkboxes.forEach((checkbox) => {
            if (!checkbox.checked) {
                checkbox.disabled = false;
            }
        });
    }
    else {
        checkboxes.forEach((checkbox) => {
            if (!checkbox.checked) {
                checkbox.disabled = true;
            }
        });
    }
    checked = !checked;
}

async function searchItem() {
    const sectorName = document.getElementById("sectorName").value;
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;
    const productName = document.getElementById("productName").value;
    const supplierName = document.getElementById("supplierName").value;

    console.log(productName);

    try {
        const response = await axios.get("/inventory/search", {
            params: {
                sectorName: sectorName,
                startDate: startDate,
                endDate: endDate,
                productName: productName,
                supplierName: supplierName
            }
        });

        if (response.status === 200) {
            const data = response.data;
            updateTable(data);
        }
    } catch (error) {
        alert("오류 발생!");
    }
}

function updateTable(data) {
    const tbody = document.querySelector('tbody');
    tbody.innerHTML = ''; // 기존 테이블 데이터 초기화

    data.forEach(item => {
        const row = `
        <tr class="text-center">
            <td style="padding-left: 18px; padding-top: 10px; width: 50px;">
                <input class="form-check"
                        type="checkbox"
                        onclick="selectItem(this)"
                        th:data-id="${item.inventoryId}"
                        th:id="check + ${item.inventoryId}">
            </td>
            <td>I-00${item.inventoryId}</td>
            <td>${item.sectorName}</td>
            <td>P-00${item.productId}</td>
            <td>${item.productName}</td>
            <td>${item.categoryName}</td>
            <td>${item.supplierName}</td>
            <td>${item.incomePrice}</td>
            <td>${item.outgoingPrice}</td>
            <td>${item.inventoryQuantity}</td>
            <td>${item.expirationDate}</td>
        </tr>`;
        tbody.insertAdjacentHTML('beforeend', row);
    });
}

function registerIncome() {
    if (!checked) {
        alert("입고 요청할 물품이 없습니다.");
        return;
    }
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');

    let inventoryId;
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            inventoryId = parseInt(checkbox.getAttribute("data-id"));
        }
    });
    location.href = "/income/register/" + inventoryId;
}

function registerOutgoing() {
    if (!checked) {
        alert("출고 요청할 물품이 없습니다.");
        return;
    }
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');

    let inventoryId;
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            inventoryId = parseInt(checkbox.getAttribute("data-id"));
        }
    });
    location.href = "/outgoing/register/" + inventoryId;
}