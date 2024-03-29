<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/style.css"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
</head>
<body>
	<br>
<br>
    <div class="cont">
        <div class="form sign-in">
            <h2 style="display: flex; flex-direction: column; margin-left: 12px;">Welcome!</h2>
           <div style="display: flex; justify-content: center;">
            <button onclick="fnPageChange()" type="button" class="submit">돌아가기</button>
           </div> 
        </div>
        <div class="sub-cont">
            <div class="img">
                <div class="img__text m--up">
                    <h5>계정이 없다면 계정을 생성하세요</h5>
                </div>
                <div class="img__text m--in">
                
                    <h5>계정을 생성했다면 등록하기 버튼을 눌러주세요 </h5>
                </div>
                <div class="img__btn">
                    <span class="m--up">등록하기</span>
                    <span class="m--in">되돌아가기</span>
                </div>
            </div>
            <form name="frm" method="post" action="insert" enctype="multipart/form-data">
            <div class="form sign-up">

                <h2>정보 등록</h2>

                <label>
                    <span>이름</span>
                    <input type="text" name="name"/>
                </label>
                <label>
                    <span>주소</span>
                    <input type="text" name="address"/>
                </label>
                <label>
                    <span>전화번호</span>
                    <input type="text" name="phone" />
                </label>
                <label>
                    <span>성별</span>
                   <input type="radio" name="gender" value="남성">남성
                   <input type="radio" name="gender" value="여성">여성
                </label>
                <label>
                    <span>나이</span>
                    <input type="text" name="age"/>
                </label>
                <label>
                    <span>이미지</span>
						<input type="file" name="file" id="file">
                </label>
                <label>
                    <span>포인트</span>
                    <input type="text" name="point"/>
                </label>
                <label>
                    <span>등급</span>
                    <select name="grade" id="grade" >
                    <option value="sliver">SLIVER</option> 
                    <option value="gold">GOLD</option> 
                    <option value="vip">VIP</option> 
                    </select>
                </label>
                
            <button onclick="chkForm()" type="button" class="submit">등록하기</button>
                
            </div>
            </form>
        </div>
    </div>
	<script>
		function fnPageChange() {
			const url = location.origin;
			location.href = url + "/jsp_customer/index";
		}
	</script>
    <script>
        document.querySelector('.img__btn').addEventListener('click', function() {
            document.querySelector('.cont').classList.toggle('s--signup');
        });
    </script>
	<script type="text/javascript" src="js/register.js"></script>
	<script type="text/javascript" src="./js/script.js"></script>
	
</body>
</html>