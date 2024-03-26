/**
 * 
 */
// 전역변수 (vXXX)
var vPage = 1;
var vDisplay = 20;

// jQuery 객체 선언 (jqXXX)
var jqMembers = $('#members');
var jqTotal = $('#total');
var jqPaging = $('#paging');
var jqDisplay = $('#display');
var jqEmail = $('#email');
var jqName = $('#name');
var jqGender = $(':radio[name=gender]');  // :radio[name=gender] = input[type=radio][name=gender]
var jqZonecode = $('#zonecode');
var jqAddress = $('#address');
var jqDetailAddress = $('#detailAddress');
var jqExtraAddress = $('#extraAddress');

var btnInit = $('#btn-init');
var btnRegister = $('#btn-register');
var btnModify = $('#btn-modify');
var btnRemove = $('#btn-remove');
var btnSelectRemove = $('#btn-select-remove');

// 함수 표현식(함수만들기)
const getContextPath = ()=>{
    
  const host = location.host; /* localhost:8080 */
  const url = location.href; /* http://localhost:8080/mvc/getDate.do */
  const begin = url.indexOf(host) + host.length;
  const end = url.indexOf('/', begin + 1);
  return url.substring(begin, end); // begin 포함, end 위치 앞까지
}

const fnInit = ()=>{
  jqEmail.val('');
  jqName.val('');
  $('#none').prop('checked', true);
  // $('#none').attr('checked', 'checked');	// <input checked="checked">
  jqZonecode.val('');
  jqAddress.val('');
  jqDetailAddress.val('');
  jqExtraAddress.val('');
}

const fnRegisterMember = ()=>{
	$.ajax({
		// 요청
		type: 'POST',												// POST 방식이므로 요청 본문(RequestBody)에 실어서 보낸다.
		url: getContextPath() + '/members',
		contentType: 'application/json',		// java로 보내는 데이터의 타입
		data: JSON.stringify({							// java로 보내는 데이터 (문자열 형식의 JSON 데이터)
			'email': jqEmail.val(),
			'name': jqName.val(),
			'gender': $(':radio:checked').val(),
			'zonecode': jqZonecode.val(),
			'address': jqAddress.val(),
			'detailAddress': jqDetailAddress.val(),
			'extraAddress': jqExtraAddress.val()
		}),			// JSON.stringify (String으로 작성된 것을 json 으로 변환하는 객체와 함수)
		// 응답
		dataType: 'json',										// 받아오는 데이터 타입
	}).done(resData=>{										// resData = {"insertCount" : 2}
		if(resData.insertCount === 2){
			alert('정상적으로 등록되었습니다.');
			fnInit();
			fnGetMemberByNo();
		}
	}).fail(jqXHR=>{											// jqXHR = 예외 발생시 예외가 저장되는 객체 이름
		alert(jqXHR.responseText);
	})
}

const fnMemberList = ()=>{
  $.ajax({
    type: 'GET',
    url: getContextPath() + '/members/page/' + vPage + '/display/' + vDisplay,
    dataType: 'json',
    success: (resData)=>{    /* 
    	 													resData = {
    															"members": [
    																{
    																	"addressNo: 1",
    																	"zonecode": '12345',
    																	"address": '서울시 구로구',
    																	"detailAddress": '디지털로',
    																	"extraAddress": '(가산동)',
    																	"member": {
    																		"memberNo": 1,
    																		"email": 'aaa@bbb',
    																		"name": 'gildong',
    																		"gender": 'none'
    																	}
    																}, ...
    															], 
    															"total": 30,
    															"paging": '< 1 2 3 4 5 6 7 8 9 10 >'
    															}
    												 */
      jqTotal.html('총 회원 ' + resData.total + '명');
      jqMembers.empty();
      $.each(resData.members, (i, member)=>{
        let str='<tr>';
        str += '<td>' + '<input type="checkbox" class="chk-member" value="'+member.member.memberNo+ '"></td>';
        str += '<td>' + member.member.email + '</td>';
        str += '<td>' + member.member.name + '</td>';
        str += '<td>' + member.member.gender + '</td>';
        str += '<td><button type="button" class="btn-detail" data-member-no="'+ member.member.memberNo + '">조회</button></td>';
        str += '</tr>';
        jqMembers.append(str);
      })
      jqPaging.html(resData.paging);
    },
    error: (jqXHR)=>{
      alert(jqXHR.statusText + '(' + jqXHR.status + ')');
    }
  })
}

// 페이징 함수
const fnPaging = (p)=>{
	vPage = p;
	fnMemberList();
}

const fnChangeDisplay = ()=>{
	vDisplay = jqDisplay.val();
	fnMemberList();
} 

// 함수 호출 및 이벤트
fnInit();
btnInit.on('click', fnInit);
btnRegister.on('click', fnRegisterMember);
fnMemberList();
jqDisplay.on('change', fnChangeDisplay);

  