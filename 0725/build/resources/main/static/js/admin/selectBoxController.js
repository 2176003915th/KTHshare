let subCategoryId=document.getElementById("selectSC").value;
let mainMaterialId=document.getElementById("selectMM").value;
let styleId=document.getElementById("selectStyle").value;
let eventId=document.getElementById("selectEvent").value;

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
function sendFromRegister(value){
    location.href="/admin/product/register?mainCategoryId="+value+"&&subCategoryId="+subCategoryId+"&&mainMaterialId="+mainMaterialId+"&&styleId="+styleId;
}

function sendFromEdit(value){
    let productId=document.getElementById("productId").value;
    location.href="/admin/product/edit?productId="+productId+"&&mainCategoryId="+value;
}