function registerIncome() {
    const shipper = document.getElementById("shipperName");
    const shipperName = shipper.options[shipper.selectedIndex].value;

    const incomeType = document.getElementById("incomeType").id;
    const incomeQuantity = document.getElementById("incomeQuantity").value;
    const incomeExpectedDate = document.getElementById("incomeExpectedDate").value;

    console.log(document.getElementById("shipperId"));

    console.log(shipperName + " " + incomeType + " " + incomeQuantity + " " + incomeExpectedDate);

    if (!shipperName || !incomeType || !incomeQuantity || !incomeExpectedDate) {
        alert("입력받지 않은 값이 있습니다.");
        return;
    }
    document.getElementById("register-form").submit();
}

