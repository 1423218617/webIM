


    var soc={
        send:function () {
            setTimeout(function () {
                websocket.send("asdfgh")
            },2000)




        }
    }

    var websocket = null;

    if('WebSocket' in window){
        websocket = new WebSocket("ws://localhost:8082/webSocket")
    }else{
        alert("该浏览器不支持websocket")
    }


    websocket.onopen = function(event){

        console.log('建立连接')
    }

    websocket.onclose = function(event){
        console.log('连接关闭')
    }

    websocket.onmessage = function(event){
        console.log('收到消息:'+event.data)
        document.body.innerHTML=event.data
    }

    websocket.onerror = function(){
        alert("websocket发生通信错误")
    }

    websocket.onbeforeunload = function(event){
        websocket.close()
    }







