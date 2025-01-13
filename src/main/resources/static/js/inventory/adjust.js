function selectAll(selectAll) {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked
    });
}

function selectItem(select) {
    const id = select.dataset.id;

    // 체크박스에 따라 실재고량과 공간변경 입력가능 설정
    document.getElementById("sectorName" + id).readOnly =
        !document.getElementById("sectorName" + id).readOnly;

    document.getElementById("realQuantity" + id).readOnly =
        !document.getElementById("realQuantity" + id).readOnly;

}

function updateRealQuantity() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    let idList = new Array();
    let realQuantityList = new Array();

    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            const inventoryId = parseInt(checkbox.getAttribute("data-id"));
            const realQuantity = parseInt(document.getElementById("realQuantity" + inventoryId).value);

            idList.push(inventoryId);
            realQuantityList.push(realQuantity);
        }
    });

    axios.put("/inventory/updateRealInventory", {
        idList: idList,
        realQuantityList: realQuantityList
    }).then(response => {
        if (response.data > 0) {
            alert("실사재고량 변경 완료!");
            location.reload();
        }
    }).catch(error => {
        alert("수정실패! " + error);
    });
}

function adjustQuantity() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    let idList = new Array();
    let realQuantityList = new Array();

    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            const inventoryId = parseInt(checkbox.getAttribute("data-id"));
            const realQuantity = parseInt(document.getElementById("realQuantity" + inventoryId).value);

            idList.push(inventoryId);
            realQuantityList.push(realQuantity);
        }
    });

    axios.put("/inventory/adjustQuantity", {
        idList: idList,
        realQuantityList: realQuantityList
    }).then(response => {
        if (response.data > 0) {
            alert("실사재고량 변경 완료!");
            location.reload();
        }
    }).catch(error => {
        alert("수정실패! " + error);
    });
}
