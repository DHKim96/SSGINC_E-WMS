function registerOutgoing() {
    const shipper = document.getElementById("shipperName");
    const shipperName = shipper.options[shipper.selectedIndex].value;

    const outgoingType = document.getElementById("outgoingType").id;
    const branch = document.getElementById("branchName");
    const branchName = branch.options[branch.selectedIndex].value;
    const outgoingQuantity = document.getElementById("outgoingQuantity").value;

    console.log(shipperName + " " + outgoingType + " " + branchName + " " + outgoingQuantity);
    if (!shipperName || !outgoingType || !outgoingQuantity || !branchName) {
        alert("입력받지 않은 값이 있습니다.");
        return;
    }
    document.getElementById("register-form").submit();
}