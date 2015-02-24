function isValidFormat(value,format) {
   if (value.search(format) != -1) {
       return true; //올바른 포맷 형식
   }
   return false;
}

function isValidEmail(value) {
   var format = /^((\w|[\-\.\+\_\%])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
   return isValidFormat(value, format);
}

function isEmpty(strValue) {
	var flag=true;
	if (strValue!="") {
		for (var i=0; i < strValue.length; i++) {
			if (strValue.charAt(i)!=" "&&strValue.charAt(i)!='\n'&&strValue.charAt(i)!='\r') {
				flag=false;
				break;
			}
		}
	}
	return flag;
}

function onlyNumeric(){
	if(event.keyCode==8||event.keyCode==46||event.keyCode==9){	//백스페이스,DELETE,TAB 허용
	}else if((event.keyCode >= 48) && (event.keyCode <= 57)){
	}else if((event.keyCode >= 96) && (event.keyCode <= 105)){
	}else{
		event.returnValue=false;
	}


}

function chkDate(objValue){	//YYYY-MM-DD
	var dateArr;
	var tmpYear,tmpMonth,tmpDay;
	dataArr=objValue.split("-");
	if(dateArr.length!=3){
		return false;
	}
	for(var i=0;i<3;i++){
		if(i==0)
			tmpYear=dateArr[i];
		else if(i==1)	
			tmpMonth=dateArr[i];
		else if(i==2)	
			tmpDay=dateArr[i];
	}
	return chkDate(tmpYear,tmpMonth,tmpDay);
}

function chkDate(tmpYear,tmpMonth,tmpDay){
	var today=new Date();
	if(tmpYear.length!=4||!chkDigit(tmpYear)||tmpYear*1<1990){
		alert("연도가 잘못되었습니다");
		return false;
	}
	if(tmpMonth.length>2||tmpMonth=='0'||!chkDigit(tmpMonth)||tmpMonth*1>12){
		alert("월이 잘못되었습니다");
		return false;
	}
	if(tmpMonth=='1'||tmpMonth=='01'||tmpMonth=='3'||tmpMonth=='03'||tmpMonth=='5'||tmpMonth=='05'||tmpMonth=='7'||tmpMonth=='07'||tmpMonth=='8'||tmpMonth=='08'||tmpMonth=='10'||tmpMonth=='12'){
		if(tmpDay.length>2||tmpDay=='0'||!chkDigit(tmpDay)||tmpDay*1>31){
			alert("날짜가 잘못되었습니다");
			return false;
		}
	}else if(tmpMonth=='4'||tmpMonth=='04'||tmpMonth=='6'||tmpMonth=='06'||tmpMonth=='9'||tmpMonth=='09'||tmpMonth=='11'){
		if(tmpDay.length>2||tmpDay=='0'||!chkDigit(tmpDay)||tmpDay*1>30){
			alert("날짜가 잘못되었습니다");
			return false;
		}
	}else{
		if(tmpYear*1%4==0){
			if(tmpDay*1>29){
				alert("날짜가 잘못되었습니다");
				return false;
			}
		}else{
			if(tmpDay*1>28){
				alert("날짜가 잘못되었습니다");
				return false;
			}
		}
	}
	return true;
}

function chkDigit(strValue){
	var flag=true;

	if (strValue!="") {
		for (var i=0; i < strValue.length; i++) {
			if(!((48<=strValue.charCodeAt(i)&&strValue.charCodeAt(i)<=57))){
				flag=false;
				break;
			}
		}
	}
	else
	{
		flag=false;
	}
	return flag;
}

function CheckRID (sRIDFirst, sRIDLast) {
	var NUM = "0123456789";
	var ERRORMSG;
	var chk = 0;
	var nYear = sRIDFirst.substring(0,2);
	var nMondth = sRIDFirst.substring(2,4);
	var nDay = sRIDFirst.substring(4,6);
	var nSex = sRIDLast.charAt(0);
	if (!CheckType(sRIDFirst, NUM)) {
		alert("주민등록번호 앞부분에 잘못된 문자가 있습니다.");
		return -1;
	}
	if ( sRIDFirst.length!=6 ||  nMondth<1 || nMondth>12 || nDay<1 || nDay>31) {
		alert("주민등록번호 앞부분이 잘못되었습니다.");
		return -1;
	}
	if (!CheckType(sRIDLast, NUM)) {
		alert("주민등록번호 뒷부분에 잘못된 문자가 있습니다.");
		return 1;
	}
	if ( sRIDLast.length!=7 || (nSex!=1 && nSex!=2 && nSex!=3 && nSex!=4) ) {
		alert("주민등록번호 뒷부분이 잘못되었습니다.");
		return 1;
	}
	var i;
	for (i=0; i<6; i++) {
		chk += ( (i+2) * parseInt( sRIDFirst.charAt(i) ));
	} 
	for (i=6; i<12; i++) {
		chk += ( (i%8+2) * parseInt( sRIDLast.charAt(i-6) ));
	}
	chk = 11 - (chk%11);
	chk %= 10;
	if (chk != parseInt( sRIDLast.charAt(6))) {
		alert("유효하지 않은 주민등록번호입니다.");
		return -1;
	}
	return 0;
}

function fixPrice(fpPrice){
	var nPrice=new String(fpPrice);
	var price;
	if(nPrice.length<=2){
		return "0";
	}
	price=nPrice.substring(0,nPrice.length-2)+"00";
	return price;
}

function containsCharsOnly(value,chars) { 
	for (var inx = 0; inx < value.length; inx++) { 
		if (chars.indexOf(value.charAt(inx)) == -1) return false; 
	} 
	return true; 
}
/**
 * 입력값이 알파벳인지 체크
 * 아래 isAlphabet() 부터 isNumComma()까지의 메소드가
 * 자주 쓰이는 경우에는 var chars 변수를
 * global 변수로 선언하고 사용하도록 한다.
 * ex) var uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
 *     var lowercase = "abcdefghijklmnopqrstuvwxyz";
 *     var number    = "0123456789";
 *     function isAlphaNum(input) {
 *         var chars = uppercase + lowercase + number;
 *         return containsCharsOnly(input,chars);
 *     }
 */
function isAlphabet(value) {
	var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	return containsCharsOnly(value,chars);
}

/**
 * 입력값이 알파벳 대문자인지 체크
 */
function isUpperCase(value) {
	var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	return containsCharsOnly(value,chars);
}

/**
 * 입력값이 알파벳 소문자인지 체크
 */
function isLowerCase(value) {
	var chars = "abcdefghijklmnopqrstuvwxyz";
	return containsCharsOnly(value,chars);
}

/**
 * 입력값에 숫자만 있는지 체크
 */
function isNumber(value) {
	var chars = "0123456789";
	return containsCharsOnly(value,chars);
}

/**
 * 입력값이 알파벳,숫자로 되어있는지 체크
 */
function isAlphaNum(value) {
	var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	return containsCharsOnly(value,chars);
}

/**
 * 입력값이 숫자,대시(-)로 되어있는지 체크
 */
function isNumDash(value) {
	var chars = "-0123456789";
	return containsCharsOnly(value,chars);
}

/**
 * 입력값이 숫자,콤마(,)로 되어있는지 체크
 */
function isNumComma(value) {
	var chars = ",0123456789";
	return containsCharsOnly(value,chars);
}

function getCookie( name ){
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length )
	{
		var y = (x+nameOfCookie.length);
		if ( document.cookie.substring( x, y ) == nameOfCookie ) 
		{
			if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
			endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
		}
		x = document.cookie.indexOf( " ", x ) + 1;

		if ( x == 0 )
			break;
	}
	return "";
}

/**
 * 입력값을 금액 표시로 변환하여 반환
 */
function priceToComma(price) {
	var containsMinus = false;

	if(isEmpty(price)) return "";

	if(price.indexOf("-") == 0){
		containsMinus = true;
		price = price.substring(1);
	}

	var len = price.length;
	var sbuf = "";

	for(var i=0; i<len; i++){
		if(i != 0 && i % 3 == 0) sbuf += ",";
		sbuf += price.charAt((len-1)-i);
	}

	if(containsMinus)
		sbuf += "-";

	return reverse(sbuf);
}
/**     
*  입력값을 Reverse 하여 반환
*/  
function reverse(str) {
	var inp = str;
	var outp = "";

	for (i = 0; i <= inp.length; i++) {
		outp = inp.charAt (i) + outp
	}

	return outp;
}

function getByteLength(value) {
	/*
	var byteLength = 0;
	for(var inx = 0; inx < value.length; inx++){
		var oneChar = escape(value.charAt(inx));
		if(oneChar.length == 1) byteLength ++;
		else if(oneChar.indexOf("%u") != -1) byteLength += 2;
		else if(oneChar.indexOf("%") != -1) byteLength += oneChar.length/3;
	}
	return byteLength;
	*/
	var length = 0;

	for(var i = 0; i < value.length; i++){
		if(escape(value.charAt(i)).length >= 4)
			length += 2;
		else
			if(escape(value.charAt(i)) != "%0D")
				length++;
	}	

	return length;
}

function CheckType(s,spc) {
	var i;
	for(i=0; i<s.length; i++) {
		if (spc.indexOf( s.substring(i, i+1)) < 0) {
			return false;
		}
	}
	return true;
}

function memberView(userid){
    openWin("/admin/member/member_view_pop.jsp?userid="+userid,500,700);
}

/**
	replaceAll함수가 없어서 만듬
*/
function replaceAll(str, searchStr, replaceStr){
	while (str.indexOf(searchStr) != -1) {
		str = str.replace(searchStr, replaceStr);
	}
	return str;
}


// json parsing에러를 막기 위하여 [로 시작하고 ]로 끝나는 경우 false 반환
function checkJson(str) {

	if(str.length==0)		// 빈 문자열
		return true;
	
	if(str.substr(0, 1)!='[')	// [로 시작하지 않는 경우
		return true;

	if(str.substr(str.length-1)!=']')		// ] 로 끝나지 않는 경우
		return true;

	return false;
}
