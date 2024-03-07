let num=1;//슬라이드의 이동거리를 결정하는 변수
let slideWidth=0;
let width=1170;//이미지의 가로길이
let count=document.getElementsByClassName("eventImage").length;//이미지의 개수
let eventTab=document.getElementById("eventTab");//가로 슬라이드

document.addEventListener('DOMContentLoaded', function () {
    slideWidth = count * width;
    eventTab.style.width = `${slideWidth}px`;//frame의 길이를 이미지 개수와 길이를 통해 동적으로 결정

    //setInterval을 사용하여 일정 시간마다 moveSlide() 메서드 호출
    setInterval(moveEventSlide, 4000);
});

//imageTab의 위치에 따라 translateX의 값을 제어하는 메서드
function moveEventSlide(){
    if(num==count){//한바퀴를 다 돌았을 때 다시 처음으로 이동
        document.getElementById("eventTab").style.transform='translateX(0px)';
        num=1;
    }else{//슬라이드를 x와 이미지의 가로길이만큼 왼쪽으로 이동
        document.getElementById("eventTab").style.transform='translateX(-'+num*width+'px)';
        num++;
    }
}