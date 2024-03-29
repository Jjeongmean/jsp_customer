<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객 정보</title>
<link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
<link rel="stylesheet" href="./css/style.css"/>
<link rel="stylesheet" href="./library/bootstrap-5.1.1-dist/css/bootstrap.min.css">
<style>
    /* 추가된 스타일 */
    @media screen and (max-width: 600px) {
        table, thead, tbody, th, td, tr {
            display: block;
        }
        thead tr {
            position: absolute;
            top: -9999px;
            left: -9999px;
        }
        tr {
            border: 1px solid #ccc;
        }
        td {
            border: none;
            border-bottom: 1px solid #eee;
            position: relative;
            padding-left: 50%;
            text-align: left;
        }
        td:before {
            position: absolute;
            top: 6px;
            left: 6px;
            width: 45%;
            padding-right: 10px;
            white-space: nowrap;
        }
        td:nth-of-type(1):before { content: "아이디"; }
        td:nth-of-type(2):before { content: "이름"; }
        td:nth-of-type(3):before { content: "주소"; }
        td:nth-of-type(4):before { content: "전화번호"; }
        td:nth-of-type(5):before { content: "성별"; }
        td:nth-of-type(6):before { content: "나이"; }
        td:nth-of-type(7):before { content: "이미지"; }
        td:nth-of-type(8):before { content: "포인트"; }
        td:nth-of-type(9):before { content: "등급"; }
    }
</style>
</head>
<body>
    <div class="container" style="margin: 8rem;">
    <h2 style="color: #666;">고객정보</h2>
        <br>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>아이디</th>
                        <th>이름</th>
                        <th>주소</th>
                        <th>전화번호</th>
                        <th>성별</th>
                        <th>나이</th>
                        <th>이미지</th>
                        <th>포인트</th>
                        <th>등급</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${customer.id}</td>
                        <td>${customer.name}</td>
                        <td>${customer.address}</td>
                        <td>${customer.phone}</td>
                        <td>${customer.gender}</td>
                        <td>${customer.age}</td>
                        <td>
                            <c:if test="${customer.img != null}">
                                <img src="${customer.img}" alt="업로드 이미지" width="100%">
                            </c:if>
                        </td>
                        <td>${customer.point}</td>
                        <td>${customer.grade}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="container">
        <div style="margin-top: 30px; display: flex; justify-content: center">
            <button onclick="EditfnPageChange(${customer.id})" class="custom-btn btn-7"><span>수정</span></button>
            <button onclick="fnPageChange()" class="custom-btn btn-8"><span>취소</span></button>
            <script src="./library/bootstrap-5.1.1-dist/js/bootstrap.bundle.min.js"></script>
            <button onclick="chkDelete(${customer.id}); return false;" class="custom-btn btn-5"><span>삭제</span></button>
       </div>
    </div>
    </div>
    
    <script>
        function EditfnPageChange(id) {
            const url = location.origin;
            location.href = url + "/jsp_customer/edit?id=" + id ;
        }
    </script>
    <script>
        function fnPageChange() {
            const url = location.origin;
            location.href = url + "/jsp_customer/index";
        }
    </script>
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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script type="text/javascript" src="./js/script.js"></script>
</body>
</html>
