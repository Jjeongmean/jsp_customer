<%@page import="DTO.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객리스트&고객정보등록</title>
<link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
<link rel="stylesheet" href="./css/style.css"/>
<link rel="stylesheet" href="./library/bootstrap-5.1.1-dist/css/bootstrap.min.css">
</head>
<body>
<div class="padding">
    <div style="text-align: center">
        <h2 style="color: #666;">고객리스트</h2>
        <br>
        <div class="table-responsive">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col">아이디</th>
                        <th scope="col">이름</th>
                        <th scope="col">주소</th>
                        <th scope="col">전화번호</th>
                        <th scope="col">성별</th>
                        <th scope="col">나이</th>
                        <th scope="col">이미지</th>
                        <th scope="col">포인트</th>
                        <th scope="col">등급</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="customer" items="${customerList}" varStatus="status">
                        <tr>
                            <td>${customer.id}</td>
                            <td><a href="./view?id=${customer.id}">${customer.name}</a></td>
                            <td>${customer.address}</td>
                            <td>${customer.phone}</td>
                            <td>${customer.gender}</td>
                            <td>${customer.age}</td>
                            <td>
                                <c:if test="${customer.img != null}">
                                    <img src="${customer.img}" alt="업로드 이미지" style="max-width: 100px;">
                                </c:if>
                            </td>
                            <td>${customer.point}</td>
                            <td>${customer.grade}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div style="text-align: center">
            <i class="mdi mdi-forum"></i>
            <br>
            <h2 style="color: #666;">고객 등록</h2>
            <br>
            <p class="text-center" style="color:#444;">등록을 진행하시겠습니까?</p>
            <br>
            <br>
            <button onclick="fnPageChange()" class="custom-btn btn-6"><span>등록하기</span></button>
        </div>
    </div>
</div>	
<script>
	//request 객체에 error가 있을 경우 에러메세지 출력
		<c:if test="${error != null}" >
			alert("${error}");
		</c:if>
		
		//쿼리스트링에 error가 있을경우 에러메세지 출력(request 객체에 포함되도록)
		<c:if test="${param.error != null}" >
			alert("${param.error}");
		</c:if>
	</script>
	<script>
		function fnPageChange() {
			const url = location.origin;
			location.href = url + "/jsp_customer/register";
		}
	</script>
	<script type="text/javascript" src="./js/script.js"></script>
	<script src="./library/bootstrap-5.1.1-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>