$(document).on("pfAjaxStart",function(){$.mobile.loading("show")});$(document).on("pfAjaxComplete",function(){$.mobile.loading("hide")});PrimeFaces.Mobile={navigate:function(e,a){a.changeHash=a.changeHash||false;var d=$(e);if(d.hasClass("ui-lazypage")){var b=e.substring(1),c={source:b,process:b,update:b,params:[{name:b+"_lazyload",value:true}],oncomplete:function(){$(e).page();$("body").pagecontainer("change",e,a)}};PrimeFaces.ajax.Request.handle(c)}else{$("body").pagecontainer("change",e,a)}}};