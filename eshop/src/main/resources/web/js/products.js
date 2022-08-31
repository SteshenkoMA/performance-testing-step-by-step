docReady(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const categoryId = urlParams.get('id');

    getCategories(categoryId);
    getCategoryProducts(categoryId);
    getCartOptions();
})
;

async function addToCart(productId) {

    let data = {
        "id": Number(productId)
    };

    let response = await fetch('control/cart/additem', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
    });

    if (response.ok) {
        let json = await response.json();

        var itemsInCartValue = Number(json.itemsInCart);

        var itemsInCart = document.getElementById("items-in-cart");
        if (itemsInCart == null) {
            var cartOptions = document.getElementById("cart-options");
            var itemsInCart = document.createElement("div");
            itemsInCart.innerHTML = '<span>Items in cart: ' + itemsInCartValue + '</span>';
            itemsInCart.id = "items-in-cart";
            cartOptions.appendChild(itemsInCart);

            var viewCart = document.createElement("div");
            viewCart.innerHTML = '<a href="/eshop/cart">view cart</a>';
            viewCart.id = "view-cart";
            cartOptions.appendChild(viewCart);

            var proceedToCheckout = document.createElement("div");
            proceedToCheckout.innerHTML = '<a href="/eshop/checkout">proceed to checkout</a>';
            proceedToCheckout.id = "proceed-to-checkout";
            cartOptions.appendChild(proceedToCheckout);

        } else {
            itemsInCart.innerHTML = '<span>Items in cart: ' + itemsInCartValue + '</span>';
        }
    } else {
        alert("Error HTTP: " + response.status);
    }
}
;

async function getCartOptions() {

    let response = await fetch('control/cart/getInfo', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    });

    if (response.ok) {
        let json = await response.json();
        drawCartOptions(json);
    } else {
        alert("Error HTTP: " + response.status);
    }
}
;

async function drawCartOptions(json) {

    var itemsInCartValue = json.cartInfo.itemsInCart;
    var itemsInCart = document.getElementById("items-in-cart");
    if (itemsInCartValue > 0 && itemsInCart == null) {

        var cartOptions = document.getElementById("cart-options");

        var itemsInCart = document.createElement("div");
        itemsInCart.innerHTML = '<span>Items in cart: ' + itemsInCartValue + '</span>';
        itemsInCart.id = "items-in-cart";
        cartOptions.appendChild(itemsInCart);

        var viewCart = document.createElement("div");
        viewCart.innerHTML = '<a href="/eshop/cart">view cart</a>';
        viewCart.id = "view-cart";
        cartOptions.appendChild(viewCart);

        var proceedToCheckout = document.createElement("div");
        proceedToCheckout.innerHTML = '<a href="/eshop/checkout">proceed to checkout</a>';
        proceedToCheckout.id = "proceed-to-checkout";
        cartOptions.appendChild(proceedToCheckout);
    }

}
;

function drawCategoryProducts(json) {

 try{
        var products = document.getElementById("products");

        var rowNum = 1;
        for (var k in json.products) {

            var productFlexItem = document.createElement("div");
            productFlexItem.className = "flex-item-product";

            var id = json.products[k].id;
            var name = json.products[k].name;
            var description = json.products[k].description;
            var price = json.products[k].price;

            productFlexItem.id = name;
            var nameFormatted = name.replaceAll(" ", "_");

            var img = document.createElement("div");
            img.id = "img_" + nameFormatted;
            img.className = "product-img";
            img.setAttribute("style", "background-image:url(img/products/" + nameFormatted + ".png)");

            productFlexItem.appendChild(img);

            var productName = document.createElement("div");
            productName.id = "name_" + nameFormatted;
            productName.className = "product-name";
            productName.innerHTML = "<span>" + name + "</span>";
            productFlexItem.appendChild(productName);

            var descriptionBlock = document.createElement("div");
            descriptionBlock.id = "description_" + name;
            descriptionBlock.className = "product-description";
            descriptionBlock.innerHTML = "<span>" + description + "</span>";
            productFlexItem.appendChild(descriptionBlock);

            var priceBlock = document.createElement("div");
            priceBlock.id = "price_" + nameFormatted;
            priceBlock.className = "product-price";
            priceBlock.innerHTML = "<span>" + price + "</span>";
            productFlexItem.appendChild(priceBlock);

            var ad_block = document.createElement("div");
            ad_block.id = "product-add_" + nameFormatted;
            ad_block.className = "product-add";
            ad_block.innerHTML = '<button onclick="addToCart(' + id + ');">add to cart</button>';
            productFlexItem.appendChild(ad_block);

            if (rowNum % 2 == 0)
            {
                productFlexItem.setAttribute("style", "background-color: #f0fbf5 ;");
            }

            products.appendChild(productFlexItem);
            rowNum++;

        }
    } catch (e) {
        alert("Error : " + e);
    }
}
;

async function getCategoryProducts(categoryId) {
  
    let response = await fetch('control/category/'+ Number(categoryId) +'/products', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    });

    if (response.ok) {
        let json = await response.json();
        drawCategoryProducts(json);
    } else {
        alert("Error HTTP: " + response.status);
    }
}
;

async function getCategories(categoryId) {

    let response = await fetch('control/category/all', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    });

    if (response.ok) {
        let json = await response.json();
        drawCategories(json, categoryId);
    } else {
        alert("Error HTTP: " + response.status);
    }
}
;

function drawCategories(json, categoryId) {

    try{

        var categories = document.getElementById("categories");

        for (var k in json.categories) {

            var categoryFlexItem = document.createElement("div");
            categoryFlexItem.className = "flex-item-category";

            var id = json.categories[k].id;
            var name = json.categories[k].name;
            name = name.replaceAll(" ", "");

            var nameBlock = document.createElement("div");
            nameBlock.id = "category_" + id;
            nameBlock.className = "category-name";
            nameBlock.innerHTML = '<a href="/eshop/category?id=' + id + '">' + name + '</a>';
            categoryFlexItem.appendChild(nameBlock);

            if (id == categoryId) {
                nameBlock.setAttribute("style", "background: floralwhite; border: 2px solid black;font-weight: 600;");
            }

            categories.appendChild(categoryFlexItem);
        }
    } catch(e) {
        alert("Error : " +e);
    }
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
