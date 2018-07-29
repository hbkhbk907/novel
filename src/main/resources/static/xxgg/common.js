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
}

document.writeln("<script src=\'/xxgg/jquery.min.js'></script>");
document.writeln("<script src=\'/xxgg/book.js?v=1.0\'></script>");

$(document).ready(function(){
    $(".showall").click(function(){$(".noshow").toggle();$(".showall").html('');});
});