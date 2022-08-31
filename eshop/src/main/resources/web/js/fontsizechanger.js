flexFont = function () {
    var divs = document.getElementsByClassName("flex-font");
    for (var i = 0; i < divs.length; i++) {
        var relFontsize = divs[i].offsetWidth * 0.07;
        divs[i].style.fontSize = relFontsize + 'px';
    }
};

window.onload = function () {
    flexFont();
};

window.onresize = function () {
    flexFont();
};