let x=1;//슬라이드의 이동거리를 결정하는 변수
let width=1170;//이미지의 가로길이
let categoryTab=document.getElementById("categoryTab");
let bar = document.getElementById('bar');//슬라이드를 제어하는 바 모양의 div

document.addEventListener('DOMContentLoaded', function () {


    // 마우스의 위치값 저장
    let x = 0;

    // 마우스 누른 순간 이벤트
    const mouseDownHandler = function (e) {
        // 누른 마우스 위치값을 가져와 저장
        x = e.clientX;

        if(bar.offsetLeft>870){
            bar.style.left = `870px`;
            categoryTab.style.left= `-172px`;
        }else if(bar.offsetLeft<0){
            bar.style.left = `0px`;
            categoryTab.style.left= `0px`;
        }
        // 마우스 이동 및 해제 이벤트를 등록
        document.addEventListener('mousemove', mouseMoveHandler);
        document.addEventListener('mouseup', mouseUpHandler);
    };

    const mouseMoveHandler = function (e) {
        // 마우스 이동시 초기 위치와의 거리차 계산
        const dx = e.clientX - x;

        // 마우스 이동 거리 만큼 Element의 left 위치값에 반영
        bar.style.left = `${bar.offsetLeft + dx}px`;
        categoryTab.style.left = `${categoryTab.offsetLeft - dx*1.7}px`;

        // 기준 위치 값을 현재 마우스 위치로 update
        x = e.clientX;

        if(bar.offsetLeft>870 || bar.offsetLeft<0){
            document.removeEventListener('mousemove', mouseMoveHandler);
            document.removeEventListener('mouseup', mouseUpHandler);
        }
    };

    const mouseUpHandler = function () {
        // 마우스가 해제되면 이벤트도 같이 해제
        document.removeEventListener('mousemove', mouseMoveHandler);
        document.removeEventListener('mouseup', mouseUpHandler);
    };

    bar.addEventListener('mousedown', mouseDownHandler);
});



