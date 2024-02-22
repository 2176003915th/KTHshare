document.addEventListener('DOMContentLoaded',function changefun(){
    var mainImageElement = document.querySelector("#mainI");
    var subImageElements = document.querySelectorAll(".subI");

// 요소 존재 여부를 체크하는 함수
    function elementExists(element) {
        return element !== null;
    }

// 이벤트 핸들러 함수
    function changePic() {
        var subImageAttribute = this.getAttribute("src");
        if (elementExists(mainImageElement)) {
            mainImageElement.setAttribute("src", subImageAttribute);
        }
    }

// 이벤트 핸들러 함수를 등록
    for (var i = 0; i < subImageElements.length; i++) {
        subImageElements[i].addEventListener("click", changePic);
    }
});