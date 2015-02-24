function loginCheck(){
	var id="dntech7"
	var pwd="dntechdams"
	var inputId = document.getElementById("A_ID").value;
	var inputPwd = document.getElementById("A_PWD").value;
	
	if(inputId == "" || inputId == null){
		alert("아이디를 입력해주세요.");
		return;
	}
	else if(inputPwd == "" || inputPwd == null){
		alert("비밀번호를 입력해주세요.");
		return;
	}
	else{
		if( id != inputId || pwd != inputPwd) {
			alert("로그인하지 못했습니다.\r\n입력하신 아이디와 비밀번호를 확인해 주세요.");
			return;
		}
		else{
			location.href = "admin_list.html"
		}
	}
}

function keyEvent(key){
	if(key == 13){loginCheck()};
}