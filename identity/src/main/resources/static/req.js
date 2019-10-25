layui.define(['jquery','layer'],function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;

    var req = {
        loading:true,
        get:function (url,data,success,error) {
            ajax('GET',url,data,success,error)
        },
        post:function (url,data,success,error) {
            ajax('POST',url,data,success,error)
        },
        soc:new sock()
    };
     function sock(){
         this.websocket=new WebSocket("ws://localhost:8082/webSocket")
         this.websocket.onopen=function () {
             console.log("建立socket连接")
         }
    }


    var ajax =function(type,url,data,success,error) {
        var loading;
        $.ajax({
            url:url,
            type:type||'get',
            data:data||{},

            beforeSend:function (xhr) {
                if(req.loading) {
                    loading = layer.open({type: 3});
                    xhr.setRequestHeader("token", localStorage.getItem("token"));
                }
            },
            success:function (d) {
                success &&success(d);
                loading &&layer.close(loading);
            },
            error:function (d) {
                error&&error(d);
                loading&&layer.close(loading);
            }
        });
    }

    exports("req",req);
})