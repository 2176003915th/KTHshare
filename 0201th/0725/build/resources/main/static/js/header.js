let searchBox = document.getElementById("searchBox");

function controlHeader(state){
    if(state=='open'){
        searchBox.style.display='flex';
    }else if(state='close'){
        searchBox.style.display='none';
    }
}