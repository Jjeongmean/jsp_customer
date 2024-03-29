//게시물 등록 전 체크
function chkForm() {
	var f = document.frm; //form 태그 요소
	
	if(f.name.value == '') {
		alert("이름을 입력해주세요.");
		return false; //함수 종료시킴(걸리면 뒤에 있는 코드 실행할 필요X)
	}
	
	if(f.address.value == '') {
		alert("주소를 입력해주세요.")
		return false; //함수 종료시킴
	}
	
	if(f.gender.value == '') {
		alert("성별을 체크해주세요.")
		return false; //함수 종료시킴
	}
	
	if(f.age.value == '') {
		alert("나이를 입력해주세요.")
		return false; //함수 종료시킴
	}
	
	if(f.grade.value == '') {
		alert("등급을 입력해주세요.")
		return false; //함수 종료시킴
	}
	
	
	f.submit(); //서버로 폼 태그 안의 데이터 전송
}
	
	function chkDelete(id) {
		const result = confirm("삭제하시겠습니까?");
		
		if(result) {
			const url = location.origin;
			location.href = url + "/jsp_customer/delete?id=" + id;
		} else {
			return false;
		}
}


