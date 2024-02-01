
var x=1;
let imageWidth=1000;

//페이지 로드 후 실행되는 익명함수
window.onload = function(){
    //setInterval을 사용하여 일정 시간마다 moveSlide() 메서드 호출
    setInterval(moveSlide, 4000);
}

//imageTab의 위치에 따라 translateX의 값을 제어하는 메서드
function moveSlide(){
    if(x==5){
        document.getElementById("imageTab").style.transform='translateX(0px)';
        x=1;
    }else{
        document.getElementById("imageTab").style.transform='translateX(-'+x*imageWidth+'px)';
        x++;
    }
}