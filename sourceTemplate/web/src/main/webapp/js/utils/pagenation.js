

//container 容器，totalPages 总页数 ,currentPage 当前页数,clickFunction点击页码时调用的函数,params 为 clickFunction的参数
  function setPage(container, totalPages, currentPage, clickFunction, params) {
	  
	  var container = container;
	  var totalPages = totalPages;
	  var currentPage = currentPage;
	  var a = [];
    //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
	  
	  //设置pre按钮样式
	  if (currentPage == 1) {
		  a[a.length] = "<a href=\"#\" class=\"aselect prev unclick\">prev</a>";
	  } else {
		  a[a.length] = "<a href=\"#\" class=\"aselect prev\">prev</a>";
	  }
	
	//总页数小于10
	  if (totalPages <= 10) {
		  for (var i = 1; i <= totalPages; i++) {
		    setPageList(a,currentPage,i);
		  }
	  }
	//总页数大于10页
	  else {
		  if (currentPage <= 4) {
		    for (var i = 1; i <= 5; i++) {
		    	setPageList(a,currentPage,i);
		    }
		    a[a.length] = "...<a href=\"#\" class=\"aselect\">" + totalPages + "</a>";
		  } else if (currentPage >= totalPages - 3) {
		    a[a.length] = "<a href=\"#\" class=\"aselect\">1</a>...";
		    for (var i = totalPages - 4; i <= totalPages; i++) {
		    	setPageList(a,currentPage,i);
		    }
		  }
		  else { //当前页在中间部分
		    a[a.length] = "<a href=\"#\" class=\"aselect\">1</a>...";
		    for (var i = currentPage - 2; i <= currentPage + 2; i++) {
		    	setPageList(a,currentPage,i);
		    }
		    a[a.length] = "...<a href=\"#\" class=\"aselect\">" + totalPages + "</a>";
		  }
	  }
	  //设置next按钮样式
	  if (currentPage == totalPages) {
		  a[a.length] = "<a href=\"#\" class=\"aselect next unclick\">next</a>";
	  } else {
		  a[a.length] = "<a href=\"#\" class=\"aselect next\">next</a>";
	  }
	  //在container容器中显示页码
	  container.html(a.join(""));
	  //绑定页码事件点击
	  pageClick(container,totalPages,currentPage, clickFunction, params);
  }
  
 function setPageList(a,currentPage,i) {
     if (currentPage == i) {
         a[a.length] = "<a href=\"#\" class=\"aselect on\">" + i + "</a>";
     } else {
    	 a[a.length] = "<a href=\"#\" class=\"aselect\">" + i + "</a>";
     }
 }
 
 function pageClick(container,totalPages,currentPage, clickFunction, params) {
      var inx = currentPage; //初始的页码
      //由于每次点击的页码值会变，所以params需要重新赋值，根据页码值通过ajax的方式从后台获取到对应的数据
      params["currentPage"] = currentPage;
      clickFunction(params);
      $(".aselect").each(function(){
    	  if ($(this).html() == "prev") {
    		  $(this).click(function(){
    			  if (inx == 1) {
			          return false;
			       }
			       inx--;
			       setPage(container, totalPages, inx, clickFunction, params);
			       return false;
    		  });
    	  }else if($(this).html() == "next"){
    		  $(this).click(function(){
    			  if (inx == totalPages) {
			          return false;
			        }
			        inx++;
			        setPage(container, totalPages, inx, clickFunction, params);
			        return false;
    		  });
    	  }else{
    		  $(this).click(function(){
    			  inx = parseInt($(this).html());
    			  setPage(container, totalPages, inx, clickFunction, params);
		          return false;
    		  });
    	  }
    	  
      });
    }
 

