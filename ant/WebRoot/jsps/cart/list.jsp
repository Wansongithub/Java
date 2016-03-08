<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/list.css'/>">

<script type="text/javascript">
$(function(){
	
	showTotal();
	
	
	$("#selectAll").click(function(){
		
		var bool = $("#selectAll").attr("checked");
		
		setItemCheckBox(bool);
		
		setJieSuan(bool);
		
		showTotal();
		
	});
	
	$(":checkbox[name=checkboxBtn]").click(function(){
		var total = $(":checkbox[name=checkboxBtn]").length;
		var select = $(":checkbox[name=checkboxBtn][checked=true]").length;
		if(select==0){
			selectAll(false);
			setJieSuan(false);
		}
		else if(select==total){
			selectAll(true);
			setJieSuan(true);
		}
		else{
			selectAll(false);
			setJieSuan(true);
		}
		showTotal();
	});
	
	$(".jian").click(function(){
		
		// 获取cartItemId
		var id = $(this).attr("id").substring(0, 32);
		// 获取输入框中的数量
		var quantity = $("#" + id + "Quantity").val();
		
		if(quantity==1){
			if(confirm("该条目数量已经为1，你确定要删除该条目吗？"))
				location = "/ant/CartItemServlet?method=batchDelete&cartItemIds="+id;
		}
		else{
			sendUpdateQuantity(id,Number(quantity)-1);
		}	
	});
	
	$(".jia").click(function(){
		
		// 获取cartItemId
		var id = $(this).attr("id").substring(0, 32);
		// 获取输入框中的数量
		var quantity = $("#" + id + "Quantity").val();
		
		sendUpdateQuantity(id,Number(quantity)+1);
	});
	
});

/*
 * 计算总计
 */
function showTotal(){
	var total=0;
	$(":checkbox[name=checkboxBtn][checked=true]").each(function(){
	
		var id = $(this).val();
		var select = $("#" + id + "Subtotal").text();
		total += Number(select);
	})
	
	$("#total").text(round(total,2));
};

function selectAll(bool){
	$("#selectAll").attr("checked",bool);
};

function setItemCheckBox(bool){
	$(":checkbox[name=checkboxBtn]").attr("checked", bool);
};

function setJieSuan(bool){
	if(bool){
		$("#jiesuan").removeClass("kill").addClass("jiesuan");
		$("#jiesuan").unbind("click");
	}
	else{
		$("#jiesuan").removeClass("jiesuan").addClass("kill");
		$("#jiesuan").click(function(){return false;});
	}
	
};

/**
 * 批量删除购物车条目
 */
function batchDelete(){
	var cartItemIdArray = new Array();
	$(":checkbox[name=checkboxBtn][checked=true]").each(function(){
		cartItemIdArray.push($(this).val());
	});
	location = "/ant/CartItemServlet?method=batchDelete&cartItemIds="+cartItemIdArray;
};

function sendUpdateQuantity(id,quantity){
	$.ajax({
		async:false,
		cache:false,
		url:"/ant/CartItemServlet",
		data:{method:"updateQuantity",cartItemId:id,quantity:quantity},
		type:"POST",
		dataType:"json",
		success:function(result){
			//1. 修改数量
			$("#" + id + "Quantity").val(result.quantity);
			//2. 修改小计
			$("#" + id + "Subtotal").text(result.subtotal);
			//3. 重新计算总计
			showTotal();
		}
	});
};

function jiesuan(){
	//获取所有勾选的checkbox的id
	var cartItemIdArray = new Array();
	$(":checkbox[name=checkboxBtn][checked=true]").each(function(){
		cartItemIdArray.push($(this).val());
	});
	
	$("#cartItemIds").val(cartItemIdArray.toString());
	$("#hiddenTotal").val($("#total").text());
	
	$("#jieSuanForm").submit();
};

</script>

  </head>
  
  <body>

<c:choose>
	<c:when test="${empty cartItemList }">
	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
			</td>
			<td>
				<span class="spanEmpty">您的购物车中暂时没有商品</span>
			</td>
		</tr>
	</table>  
	</c:when>
	<c:otherwise>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr align="center" bgcolor="#efeae5">
		<td align="left" width="50px">
			<input type="checkbox" id="selectAll" checked="checked"/><label for="selectAll">全选</label>
		</td>
		<td colspan="2">商品名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
		<td>操作</td>
	</tr>



<c:forEach items="${cartItemList }" var="cartItem">
	<tr align="center">
		<td align="left">
			<input value="${cartItem.cartItemId }" type="checkbox" name="checkboxBtn" checked="checked"/>
		</td>
		<td align="left" width="70px">
			<a class="linkImage" href="<c:url value='BookServlet?method=load&bid=${cartItem.book.bid }'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.book.image_b }'/>"/></a>
		</td>
		<td align="left" width="400px">
		    <a href="<c:url value='BookServlet?method=load&bid=${cartItem.book.bid }'/>"><span>${cartItem.book.bname }</span></a>
		</td>
		<td><span>&yen;<span class="currPrice">${cartItem.book.currPrice }</span></span></td>
		<td>
			<a class="jian" id="${cartItem.cartItemId }Jian"></a><input class="quantity" readonly="readonly" id="${cartItem.cartItemId }Quantity" type="text" value="${cartItem.quantity }"/><a class="jia" id="${cartItem.cartItemId }Jia"></a>
		</td>
		<td width="100px">
			<span class="price_n">&yen;<span class="subTotal" id="${cartItem.cartItemId }Subtotal">${cartItem.subtotal }</span></span>
		</td>
		<td>
			<a href="<c:url value='/CartItemServlet?method=batchDelete&cartItemIds=${cartItem.cartItemId }'/>">删除</a>
		</td>
	</tr>
</c:forEach>

	
	<tr>
		<td colspan="4" class="tdBatchDelete">
			<a href="javascript:batchDelete();">批量删除</a>
		</td>
		<td colspan="3" align="right" class="tdTotal">
			<span>总计：</span><span class="price_t">&yen;<span id="total"> </span></span>
		</td>
	</tr>
	<tr>
		<td colspan="7" align="right">
			<a href="javascript:jiesuan();" id="jiesuan" class="jiesuan"></a>
		</td>
	</tr>
</table>
	<form id="jieSuanForm" action="<c:url value='/CartItemServlet'/>" method="post">
		<input type="hidden" name="cartItemIds" id="cartItemIds"/>
		<input type="hidden" name="total" id="hiddenTotal"/>
		<input type="hidden" name="method" value="loadCartItem"/>
	</form>

	</c:otherwise>
</c:choose>
  </body>
</html>
