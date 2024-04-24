const btn = document.getElementById("foodBtn");
const btnRemoveFood = Object.values(document.getElementsByClassName("btnRemoveFood"));
let orderId = document.getElementById("inputOrderId").value;

// Add food to order
btn.addEventListener("click", () => {
    const inputFoodId = document.getElementById("inputFoodId");
    const foodSelect = document.getElementById("foodsData");

    // Actual value from dropdown menu
    const selectedValue = foodSelect.value;
    inputFoodId.value = selectedValue;

    console.log("A POST-kéréshez használt érték:", selectedValue);

    // POST-request with actual value
    const xhr = new XMLHttpRequest();
    xhr.open("POST", `http://localhost:8080/api/orders/order/id/${orderId}/food/id/${selectedValue}`);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");

    xhr.onload = () => {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("Kérés sikeres:", JSON.parse(xhr.responseText));
        } else {
            console.log("Hiba történt:", xhr.status);
        }
    };
    xhr.send();
    setTimeout(() => window.location.reload(), 1000);
});

//Remove food from order
for (let i = 0; i < btnRemoveFood.length; i++) {
    btnRemoveFood[i].addEventListener("click", () => {

        const foodIdToRemove = btnRemoveFood[i].closest("tr").querySelector("#foodIdToRemove");
        const selectedValue = foodIdToRemove.value;
        foodIdToRemove.value = selectedValue;

        console.log("A POST-kéréshez használt érték:", foodIdToRemove);

        // POST-request with actual value
        const xhr = new XMLHttpRequest();
        xhr.open("Delete", `http://localhost:8080/api/orders/order/id/${orderId}/food/id/${selectedValue}`);
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");

        xhr.onload = () => {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log("Kérés sikeres:", JSON.parse(xhr.responseText));
            } else {
                console.log("Hiba történt:", xhr.status);
            }
        };
        xhr.send();
        setTimeout(() => window.location.reload(), 1000);
    });
}

