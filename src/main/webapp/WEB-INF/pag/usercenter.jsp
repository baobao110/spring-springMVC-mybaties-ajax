<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<jsp:include page="common/head.jsp"></jsp:include>

<body>
<h1>个人中心页面</h1>
<img src="/user/showPicture.do" width="66px" height="66px" onerror="javascript:this.src='/images/dayuan.jpg';" ><br>
<a href="/user/toUpload.do">上传头像</a>
<h1>用户:${username}</h1>
<a href="/blog/toOpenAccount.do" target="_blank">开户</a><br>
<a href="/user/back.do" target="_blank">退出</a><br>

</body>

<table>
	<tr>
		<td>卡号</td>
		<!-- <td>金额</td> -->
		<td>时间</td>
		<td>手续</td>
	</tr>
	
	<c:forEach items="${fenye.getObject()}" var="list">
		<tr>
			<td>${list.getNumber()}</td>
			<%-- <td>${list.getMoney()}</td> --%>
			<td><fmt:formatDate value="${list.getCreatetime()}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
			<a href="/blog/toWrite.do?number=${list.getNumber()}" target="_blank">写博客</a>	<!--这边需要特别注意链接参数  -->
			<a href="/blog/toList.do?number=${list.getNumber()}" target="_blank">进入</a>
			<a href="/blog/toChangePassword.do?number=${list.getNumber()}" target="_blank">修改密码</a>
			<a href="/blog/toDelete.do?number=${list.getNumber()}" target="_blank">销户</a>
			</td>
		</tr>
	</c:forEach>
	
	<c:if test="${not empty fenye }">
		<button onclick="first()">首页</button>
		<button onclick="pre()">上一页</button>
		<button onclick="next()">下一页</button>
		<button onclick="last()">尾页</button>
		${fenye.getCurrentPage()}/${fenye.getTotalPage()}
	</c:if>
	<!--这里注意jstl标签的使用在表单页面可以用ajax 也可以用ajax  -->
</table>

<jsp:include page="common/foot.jsp"></jsp:include>
</body>

<script type="text/javascript" src="/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/js/json2.js"></script>
<script type="text/javascript">
	var currentPage = "${fenye.getCurrentPage()}";
	
	var totalPageNum = "${fenye.getTotalPage()}";
	/* 这里需要注意分页技术的前端实现 ，注意false返回 */
	
	function next() {
		if (currentPage == totalPageNum) {
			return false;
		}
		
		currentPage = parseInt(currentPage) + 1;
		window.location.href='/user/toUsercenter.do?currentPage=' + currentPage;
	}
	
	function pre() {
		
		if (currentPage == 1) {
			return false;
		}
		
		currentPage = parseInt(currentPage) - 1;
		window.location.href='/user/toUsercenter.do?currentPage=' + currentPage;
	}
	
	function first() {
		currentPage = 1;
		window.location.href='/user/toUsercenter.do?currentPage=' + currentPage;
	}
	
	function last() {
		currentPage = totalPageNum;
		window.location.href='/user/toUsercenter.do?currentPage=' + currentPage;
	}
		
	 /* function flow(){
		var param = {
				currentPage:currentPage
		};
		
	$.post('/user/toFlow.do',param, callback);
	}
	
	function callback(data, status) {
		alert('点击');
		
		//data = eval('(' + data + ')');
		var ajaxDAO = JSON.parse(data);
		var result=ajaxDAO.data.obj;
		var msg='<tr><td>卡号</td><td>时间</td><td>手续</td></tr>';
		for (var i=0; i<result.length;i++) {
			msg+='<tr>';
			msg+='<td>'+result[i].getNumber()+'</td>';
			msg+='<td>'+result[i].getCreatetime()+'</td>';
			msg+='<td>';
			msg+='<a href="/blog/toWrite.do?number='+result[i].getNumber()+'" target="_blank">写博客</a>';
			msg+='<a href="/blog/toList.do?number='+result[i].getNumber()+'" target="_blank">进入</a>';
			msg+='<a href="/blog/toChangePassword.do?number='+result[i].getNumber()+'" target="_blank">修改密码</a>';
			msg+='<a href="/blog/toDelete.do?number='+result[i].getNumber()+'" target="_blank">销户</a>';
			msg+='</td></tr>';
			totalPageNum = ajaxDAO.data.totalPageNum;
			$('#blog').html(msg);
			$('#pag').html(currentPage+ '/' + totalPageNum);
		} 
	} */
	
</script>


</html>