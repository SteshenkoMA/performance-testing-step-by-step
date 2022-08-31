docReady(function () {
    drawConfirmationInfo();

});

function drawConfirmationInfo() {
    drawMessageData();
    drawOrderInfoData();
    drawCustomerInfoData();
}
;

function drawOrderInfoData() {
    var orderInfo = document.getElementById("order-info-data");
    var confirmationInfo = JSON.parse(sessionStorage.confirmationInfo);

    var productFlexItem = document.createElement("div");
    productFlexItem.className = "flex-item-order-header";

    var productNameHeader = document.createElement("div");
    productNameHeader.className = "product-name-header";
    productNameHeader.innerHTML = "<span>Product</span>";

    var productPriceHeader = document.createElement("div");
    productPriceHeader.className = "product-price-header";
    productPriceHeader.innerHTML = "<span>Price</span>";

    var productQuantityHeader = document.createElement("div");
    productQuantityHeader.className = "product-quantity-header";
    productQuantityHeader.innerHTML = "<span>Quantity</span>";

    productFlexItem.appendChild(productNameHeader);
    productFlexItem.appendChild(productPriceHeader);
    productFlexItem.appendChild(productQuantityHeader);

    orderInfo.appendChild(productFlexItem);

    var cartItems = confirmationInfo.order.cartItems;

    for (var k in cartItems) {

        var id = cartItems[k].product.id;
        var nameValue = cartItems[k].product.name;
        var quantityValue = cartItems[k].quantity;
        var totalPriceValue = cartItems[k].totalPrice;

        var productFlexItem = document.createElement("div");
        productFlexItem.className = "flex-item-order-product";

        var productName = document.createElement("div");
        productName.className = "product-name";
        productName.innerHTML = "<span>" + nameValue + "</span>";

        var productPrice = document.createElement("div");
        productPrice.className = "product-price";
        productPrice.innerHTML = "<span>" + totalPriceValue + "</span>";

        var productQuantity = document.createElement("div");
        productQuantity.className = "product-quantity";
        productQuantity.innerHTML = "<span>" + quantityValue + "</span>";

        productFlexItem.appendChild(productName);
        productFlexItem.appendChild(productPrice);
        productFlexItem.appendChild(productQuantity);

        orderInfo.appendChild(productFlexItem);

    }
    
    var subtotalPrice = confirmationInfo.order.subtotalPrice;
    var totalItemsAmount = confirmationInfo.order.totalItemsAmount;
    
    
    var productFlexItem = document.createElement("div");
    productFlexItem.className = "flex-item-order-footer";

    var productNameFooter = document.createElement("div");
    productNameFooter.className = "product-name-footer";
    productNameFooter.innerHTML = "<span>Total</span>";
 
    var productPriceFooter = document.createElement("div");
    productPriceFooter.className = "product-price-footer";
    productPriceFooter.innerHTML = "<span>"+subtotalPrice+"</span>";

    var productQuantityFooter = document.createElement("div");
    productQuantityFooter.className = "product-quantity-footer";
    productQuantityFooter.innerHTML = "<span>"+totalItemsAmount+"</span>";

    productFlexItem.appendChild(productNameFooter);
    productFlexItem.appendChild(productPriceFooter);
    productFlexItem.appendChild(productQuantityFooter);

    orderInfo.appendChild(productFlexItem);
}
;

function drawMessageData() {
    var messageBlock = document.getElementById("message");
    var confirmationInfo = JSON.parse(sessionStorage.confirmationInfo);
    var confirmationNumber = confirmationInfo.order.confirmationNumber;
    var date = confirmationInfo.order.date;

    messageBlock.innerHTML = "<span style='font-weight: 600;'>Your order was successfully processed</span>" +
            "<table class='messageTable'><tr><td>confirmation number</td><td align='right'>" + confirmationNumber + "</td>" +
            "<tr><td>date</td><td align='right'>" + date + "</td></tr></table>";
};

function drawCustomerInfoData() {
    
    var customerInfoBlock = document.getElementById("customer-info-data");
    var confirmationInfo = JSON.parse(sessionStorage.confirmationInfo);

    var name = confirmationInfo.customer.name;
    var address = confirmationInfo.customer.address;
    var email = confirmationInfo.customer.email;
    var phone = confirmationInfo.customer.phone;

    var nameBlock = document.createElement("div");
    nameBlock.className = "customer-name-value";
    nameBlock.innerHTML = "<span>" + name + "</span>";

    var addressBlock = document.createElement("div");
    addressBlock.className = "customer-address-value";
    addressBlock.innerHTML = "<span>" + address + "</span>";

    var emailBlock = document.createElement("div");
    emailBlock.className = "customer-email-value";
    emailBlock.innerHTML = "<span>" + email + "</span>";

    var phoneBlock = document.createElement("div");
    phoneBlock.className = "customer-phone-value";
    phoneBlock.innerHTML = "<span>" + phone + "</span>";

    customerInfoBlock.appendChild(nameBlock);
    customerInfoBlock.appendChild(addressBlock);
    customerInfoBlock.appendChild(emailBlock);
    customerInfoBlock.appendChild(phoneBlock);
}
;

function docReady(fn) {
    if (document.readyState == "complete" || document.readyState == "interactive" || window.onload == "") {
        setTimeout(fn, 1);
    } else {
        document.addEventListener("DOMContentLoaded", fn);
    }
}
;