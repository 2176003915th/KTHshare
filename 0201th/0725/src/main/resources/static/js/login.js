let wrapper = document.getElementById("wrapper");
let x = document.getElementById("login");
let y = document.getElementById("register");
let z = document.getElementById("btn");

let styleId = 1;
let modern = document.getElementById("modern");
let classic = document.getElementById("classic");
let upcycling = document.getElementById("upcycling");

let register = document.getElementById("register");

function goLogin(){
    x.style.left = "50px";
    y.style.left = "450px";
    y.style.width = "280px";
    z.style.left = "0";
    wrapper.style.width= "380px";
    wrapper.style.height= "480px";
}
function goRegister(){
    x.style.left = "-400px";
    y.style.left = "50px";
    y.style.width = "600px";
    z.style.left = "110px";
    wrapper.style.width= "700px";
    wrapper.style.height= "1100px";
}

function emailCheck(email){
    alert(email);
}

function setStyle(event) {
    if(event.target.checked)  {
        if(event.target.id == "modern") {
            classic.checked=false;
            upcycling.checked=false;
            styleId = event.target.value;
        } else if(event.target.id == "classic") {
            modern.checked=false;
            upcycling.checked=false;
            styleId = event.target.value;
        } else if(event.target.id == "upcycling") {
            modern.checked=false;
            classic.checked=false;
            styleId = event.target.value;
        }
        register.action = "/member/register/"+styleId;
    }
}