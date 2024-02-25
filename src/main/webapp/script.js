$("document").ready(async function () {
    const productsJsonArray = await getProducts();
    const productsList = document.getElementById("productsList");

    for (let i = 0; i < productsJsonArray.length; i++) {
        productsList.innerHTML += `<li><h4>${productsJsonArray[i].prod_name}</h4>
<button id="${productsJsonArray[i].prod_id}" onclick="onPurchaseButtonMouseClicked(${productsJsonArray[i].prod_id})">Purchase</button></li>`
    }
});

async function getProducts() {
    const response = await fetch("/shop/products", { method: "GET" });
    return await response.json();
}

async function onPurchaseButtonMouseClicked(id) {}

function showPopup() {
    var popup = document.getElementById('productPopup');
    popup.style.display = 'block';

}

function closePopup() {
    var popup = document.getElementById('productPopup');
    popup.style.display = 'none';
}



async function handleProductClick(event) {
    var listItem = event.target.closest('li');

    if (listItem) {
        var productName = listItem.querySelector('h4').textContent;
        const productsJsonArray = await getProducts();
        var remainingQuantityPopup = document.getElementById('remainingQuantity');
        var productPricePopup = document.getElementById('productPrice');
        var productId;
        for (let i = 0; i < productsJsonArray.length; i++) {
            if (productsJsonArray[i].prod_name == productName) {
                remainingQuantityPopup.textContent = productsJsonArray[i].prod_amount;
                productPricePopup.textContent = productsJsonArray[i].prod_price;
                productId = productsJsonArray[i].prod_id;
                document.getElementById('productName').textContent = productName;
            }
        showPopup();
    }
}}

async function updateProductsOnServer(updatedProducts) {
    productsJsonArray = await getProducts();

    const response = await fetch('/shop/update-products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productsJsonArray)
    });

    if (!response.ok) {
        throw new Error('Failed to update products on server');
    }
}

async function purchaseProduct(productId, quantityToPurchase) {
        const productsJsonArray = await getProducts();
        const productName = document.getElementById("productName").textContent;
        const parsedQuantity = parseInt(quantityToPurchase);


                if (isNaN(parsedQuantity) || parsedQuantity <= 0) {
                    throw new Error('Invalid quantity to purchase');
                }
        var productId;
        alert("saxeli: " + productName);
        for(let i =0; i < productsJsonArray.length; i++){
               if(productsJsonArray[i].prod_name == productName){

                    productId = productsJsonArray[i].id;

                    alert("darcha" + productsJsonArray[i].prod_amount)
               }
        }
    alert("id:  " + productId);
    const response = await fetch('/shop/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            productId: productId,
            quantity: quantityToPurchase
        })
    });

    if (!response.ok) {
        throw new Error('Failed to purchase product');
    }


    const productIndex = productsJsonArray.findIndex(product => product.id === productId);
    if (productIndex !== -1) {
        alert(productsJsonArray[productId - 1].prod_amount)
        alert("minda" + parseInt(quantityToPurchase));
        productsJsonArray[productId -1].prod_amount = productsJsonArray[productId -1].prod_amount - parseInt(quantityToPurchase);
        alert(productsJsonArray[productId - 1].prod_amount)
    }else{
        alert("cant buy, try next time");
    }
     alert("aq"+productsJsonArray[0].prod_amount)
     for(let i =0; i <productsJsonArray.length;i++){
        alert(parseInt(productsJsonArray[i].prod_amount));
     }
     await updateProductsOnServer(productsJsonArray);

}

function handleCloseButtonClick() {
    closePopup();
}

async function handlePurchaseButtonClick() {
        const productsJsonArray = await getProducts();
        const productName = document.getElementById("productName");
        const quantityToPurchaseInput = document.getElementById("quantityInput").value.trim();

        var productId;
        for(let i =0; i < productsJsonArray.length; i++){
                    if(productsJsonArray[i].prod_name == productName){

                        productId = productsJsonArray[i].id;
                    }
                }

    const quantityToPurchase = parseInt(quantityToPurchaseInput);
        alert(quantityToPurchase);
        try {
            await purchaseProduct(productId, quantityToPurchase);
            alert('Purchase successful!');
        } catch (error) {
            alert('Failed to purchase product: ' + error.message);
        }
}


document.addEventListener('DOMContentLoaded', function() {
    var productList = document.getElementById('productsList');
    productList.addEventListener('click', handleProductClick);

    var closeButton = document.querySelector('.close');
    closeButton.addEventListener('click', handleCloseButtonClick);

    var purchaseButton = document.getElementById('purchaseButton');
    purchaseButton.addEventListener('click', handlePurchaseButtonClick);
});
