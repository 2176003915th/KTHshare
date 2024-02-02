let formEdit=document.getElementById("form-edit");//회원정보 수정 form
let formPw=document.getElementById("form-password");//비밀번호 변경 form
let formSt=document.getElementById("form-style");//선호 스타일 변경 form

let oldPassword=document.getElementById("old-password");//현재 사용 중인 비밀번호
let password=document.getElementById("password");//바꿀 비밀번호

//요청에 따른 form의 action 변경
function edit(type){
    if(type=="edit" || type=="delete"){
        formEdit.action="/member/edit?type="+type;
        formEdit.submit();
    }else if(type="changePw"){
        formPw.action="/member/edit?type="+type;
        formPw.submit();
    }else if(type="changeSt"){
        formSt.action="/member/edit?type="+type;
        formSt.submit();
    }
}

//비밀번호 입력 로직
function compare(){
    if(oldPassword.value==""){
        alert("현재 비밀번호를 입력하십시오");
    }else if(password.value=""){
        alert("새로 입력할 비밀번호를 입력하십시오");
    }else if(oldPassword.value==password.value) {
        alert("새 비밀번호가 현재 사용 중인 비밀번호와 같습니다");
    }else{
        edit("changePw");
    }
}