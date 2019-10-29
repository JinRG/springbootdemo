$.ajaxSetup({
    complete:function(XMLHttpRequest,textStatus){
          if(textStatus=="parsererror"){
        	  if(confirm("登陆超时！请重新登陆")){//点击确定则跳转至登录页面
        		  window.location.href = 'loginindex';
        	  }else{
        		  //点取消按钮返回false
        	  }
              
          } else if(textStatus=="error"){
              alert("请求超时！请稍后再试！");
          }else{
        	  console.log("状态"+textStatus);
          }
    }
});