
//获取当前网址，如：
var curWwwPath=window.document.location.href;
//获取主机地址之后的目录如：/Tmall/index.jsp
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
//获取主机地址，如：//localhost:8080
var localhostPaht=curWwwPath.substring(0,pos);
//获取带"/"的项目名，如：/Tmall
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

$(function () {
    $.ajax({
        dataType: "json",
        async: false,
        type: 'post',
        url:projectName+ '/meeting/sitplan',
        data:{},
        success: function (data) {
            var a =0;
            var m =0;
            $.each(data.rows,function (index,temp) {//桌号 ，对应的人

             $("#tr1").append("<th>"+index+"</th>");

             for(var x=0;x<15;x++){
                 var n = "t"+x;
                 var y="";
                 if(temp[x]){
                     y=temp[x]
                 }
                 $("#"+n).append("<td>"+y+"</td>")

                }


                    a+=1;
                if(a%9==0){
                    m+=1;
                }
            })

        }
    });

})