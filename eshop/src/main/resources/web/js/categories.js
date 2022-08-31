docReady(function () {
    getCategories();
});

function drawCategories(json) {

    var categories = document.getElementById("categories");

    for (var k in json.categories) {

        var cellCategoryBlock = document.createElement("div");

        cellCategoryBlock.className = "category-block";

        var id = json.categories[k].id;
        var name = json.categories[k].name;

        name = name.replaceAll(" ", "");

        cellCategoryBlock.innerHTML = '<a href="/eshop/category?id=' + id + '"><span class="category-name">' + name + '</span></a>';
        cellCategoryBlock.setAttribute("style", "background-image: url(img/categories/" + name + ".png)");

        categories.appendChild(cellCategoryBlock);
    }
}
;

async function getCategories() {

    let response = await fetch('control/category/all', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    });

    if (response.ok) {
        let json = await response.json();
        drawCategories(json);

    } else {
        alert("Error HTTP: " + response.status);
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
