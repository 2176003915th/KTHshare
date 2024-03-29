let subCategoryId=document.getElementById("selectSC").value;
let mainMaterialId=document.getElementById("selectMM").value;
let styleId=document.getElementById("selectStyle").value;
let eventId=document.getElementById("selectEvent").value;
let registerForm=document.getElementById("register-form");

function changedSC(value){
    subCategoryId=value;
}

function changedMM(value){
    mainMaterialId=value;
}

function changedSt(value){
    styleId=value;
}

function changedEv(value){
    eventId=value;
}

function changedCategory(){
    registerForm.action="/admin/product/register?type='categoryChanged'";
    registerForm.method="post";
    registerForm.submit();
}

function sendFromRegister(){
    registerForm.action="/admin/product/register";
    registerForm.method="post";
    registerForm.submit();
}

function sendFromEdit(value){
    let productId=document.getElementById("productId").value;
    location.href="/admin/product/edit?productId="+productId+"&&mainCategoryId="+value;
}