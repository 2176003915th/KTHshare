var x=1;//슬라이드의 이동거리를 결정하는 변수
let width=1000;//이미지의 가로길이
let count=document.getElementsByClassName("eventImage").length;//이미지의 개수
let frame=document.getElementById("photoFrame");//이미지들을 감싸는 div

//페이지 로드 후 실행되는 익명함수
window.onload = function(){
    frame.style.width=width*count;//frame의 길이를 이미지 개수와 길이를 통해 동적으로 결정

    //setInterval을 사용하여 일정 시간마다 moveSlide() 메서드 호출
    setInterval(moveSlide, 4000);
}

//imageTab의 위치에 따라 translateX의 값을 제어하는 메서드
function moveSlide(){
    if(x==count){//한바퀴를 다 돌았을 때 다시 처음으로 이동
        document.getElementById("imageTab").style.transform='translateX(0px)';
        x=1;
    }else{//슬라이드를 x와 이미지의 가로길이만큼 왼쪽으로 이동
        document.getElementById("imageTab").style.transform='translateX(-'+x*width+'px)';
        x++;
    }
}