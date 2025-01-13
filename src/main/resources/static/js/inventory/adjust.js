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