function memberType(){
    var formEdit = document.getElementById("choiceCart")
    if(memberId != 'guest'){
        formEdit.action="/pay/cart/" +  memberId + "/" + productId;
        formEdit.submit();
    } else {
        alert('로그인이 필요한 서비스입니다!');
        return false;
    }
}


function buyNow() { /* 추가된 부분 */
    if(memberId != 'guest'){
        var count = document.getElementById('product-quantity').value;
        var form = document.createElement('form');
        form.method = 'post';
        form.action = '/pay/cart/one?memberId=' + memberId + '&productId=' + productId;
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'count';
        input.value = count;
        form.appendChild(input);
        document.body.appendChild(form);
        form.submit();
    } else {
        alert('로그인이 필요한 서비스입니다!');
        return false;
    }

}