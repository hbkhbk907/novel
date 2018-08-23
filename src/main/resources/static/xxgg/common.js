// if(/Android|Windows Phone|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)){
//     url = window.location.toString();
//     id = url.match(/\/(\d+?)_(\d+?)\//);
//     if (id){
//         var currentHref=location.href;
//         currentHref=currentHref.replace("www.","m.");
//         currentHref=currentHref.replace("_","/");
//         location.href=currentHref;
//     }else{
//         location.href= 'http://m.biqukan.com/';
//     }
// }

function setCookie(c_name,value,expiredays)
{
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+365)
    document.cookie=c_name+ "=" +escape(value)+";expires="+exdate.toGMTString()+";path=/";
}

function getCookie(c_name)
{
    if (document.cookie.length>0){
        c_start=document.cookie.indexOf(c_name + "=");
        if (c_start!=-1){
            c_start=c_start + c_name.length+1;
            c_end=document.cookie.indexOf(";",c_start);
            if (c_end==-1) c_end=document.cookie.length;
            return unescape(document.cookie.substring(c_start,c_end));
        }
    }
    return "";
}

function delCookie(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    document.cookie= name + "=;expires="+exp.toGMTString();
}
var bookUserName=getCookie("username");
function topCase(){
    var sURL = "http://"+location.hostname;
    var sTitle = "笔趣阁";
    try{
        window.external.addFavorite(sURL, document.title);
    }catch (e){
        try{
            window.sidebar.addPanel(sTitle, sURL, "");
        }catch (e){
            alert("加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}

function footer(){
    document.getElementsByClassName("p")[0].getElementsByTagName("a")[0].innerHTML="首页";
    document.getElementsByClassName("logo")[0].getElementsByTagName("a")[0].innerHTML="https://www.hebaokang.top";
    var page = document.getElementsByClassName("page_chapter")[0];
    var title = document.getElementsByClassName("content")[0].getElementsByTagName("h1")[0];
    title.parentNode.insertBefore(page.cloneNode(true),title.nextSibling );
    var returnTop = document.createElement("li");
    var a = document.createElement("a");
    a.setAttribute("href","#top");
    a.innerText = "返回顶部";
    returnTop.insertBefore(a,null);
    page.getElementsByTagName("ul")[0].insertBefore(returnTop,page.getElementsByTagName("ul")[0].firstChild);

    //添加左右滑动翻页
    var ele = document.getElementById("content");
    var beginX, beginY, endX, endY, swipeLeft, swipeRight;
    ele.addEventListener('touchstart', function (event) {
        // event.stopPropagation();
        // event.preventDefault();
        beginX = event.targetTouches[0].screenX;
        beginY = event.targetTouches[0].screenY;
        swipeLeft = false, swipeRight = false;
    });

    ele.addEventListener('touchmove', function (event) {
        endX = event.targetTouches[0].screenX;
        endY = event.targetTouches[0].screenY;
        // 左右滑动
        if (Math.abs(endX - beginX) - Math.abs(endY - beginY) > 0) {
            event.stopPropagation();
            event.preventDefault();
            /*向右滑动*/
            if (endX - beginX > 200) {
                swipeRight = true;
                swipeLeft = false;
            }
            /*向左滑动*/
            else if(beginX - endX > 200){
                swipeLeft = true;
                swipeRight = false;
            }
        }
        else if(Math.abs(endX - beginX) - Math.abs(endY - beginY) < 0)
        {
            // 上下滑动
        }
    });
    ele.addEventListener('touchend', function (event) {


        if (Math.abs(endX - beginX) - Math.abs(endY - beginY) > 0) {
            event.stopPropagation();
            event.preventDefault();
            if (swipeRight) {
                event.stopPropagation();
                event.preventDefault();
                swipeRight = !swipeRight;
                /*向右滑动*/
                location.href=document.getElementsByClassName("page_chapter")[0].getElementsByTagName("a")[0].href;
            }
            if(swipeLeft) {
                event.stopPropagation();
                event.preventDefault();
                swipeLeft = !swipeLeft;
                /*向左滑动*/
                location.href=document.getElementsByClassName("page_chapter")[0].getElementsByTagName("a")[2].href;
            }
        }
    });
}

document.writeln("<script src=\'/xxgg/jquery.min.js'></script>");
document.writeln("<script src=\'/xxgg/book.js?v=1.0\'></script>");

$(document).ready(function(){
    $(".showall").click(function(){$(".noshow").toggle();$(".showall").html('');});
});