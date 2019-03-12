$(function () {
  	
  	// 操作提示收起
  	$(".alert-info .pack_up").click(function() {
  		if ($(this).find("i").hasClass("fa-minus-square")) {
  			$(this).html("<i class='fa fa-plus-square'></i>");
  			$(this).closest(".alert-info").find("p").hide();
  		}
  		else {
  			$(this).html("<i class='fa fa-minus-square'></i>");
  			$(this).closest(".alert-info").find("p").show();
  		}
  	});
}), setTabHeight(), $(window).bind("resize", function() {
	setTabHeight()
});

function setTabHeight() {
	var wins = $(window).height(),main = $("#main-bd"),a=$(".main-header").outerHeight()+$(".main-footer").outerHeight(),het = wins - a;
	main.height(het);
}