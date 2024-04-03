/**
 * 
 */
 
 var emailCheck = false;
var passwordCheck = false;
var passwordConfirm = false;
var nameCheck = false;
var mobileCheck = false;
var agreeCheck = false;

const fnGetContextPath = ()=>{
 const host = location.host;  /* localhost:8080 */
 const url = location.href;   /* http://localhost:8080/mvc/getDate.do */
 const begin = url.indexOf(host) + host.length;
 const end = url.indexOf('/', begin + 1);
 return url.substring(begin, end);
}

const fnCheckEmail = ()=>{
  
  /* 
   <$.ajax 버전 - ajax 후 ajax 이 동작할 때 promise 가 필요>
   new Promise((resolve, reject=>{
     $.ajax({
       url: '이메일중복체크요청'
     })
     .done(resData =>{
       if(resData.enableEmail){
         resolve();    // then() 메소드 호출
       } else{
         reject();
       }
   })
   .then(()=>{
      $.ajax({
        url:'인증코드전송요청'
      })
      .done(resData=>{
        if(resData.code === 인증코드입력값)
      })
      }
   })
   .catch(()=>{
     실패처리
   })
  })
  */
  
  /*
   -----------------------------------
   fetch('이메일중복체크요청', {})
   .then(response=>response.json())
   .then(resData=>{    // resData = {"enableEmail": true}
      if(resData.enableEmail){
        fetch('인증코드전송요청', {})
        .then(response=>response.json())
        .then(resData=>{  // {"code": "123asd"}
          if(resData.code === 인증코드입력값)
        })
     }
   })
  */
  
let inpEmail = document.getElementById('inp-email');
let regEmail = /^[A-Za-z0-9-_]{2,}@[A-Za-z0-9]+(\.[A-Za-z]{2,6}){1,2}$/;
if(!regEmail.test(inpEmail.value)){
  alert('이메일 형식이 올바르지 않습니다.');
  emailCheck = false;
  return;
}
  // ajax : 페이지는 유지하되 DB 를 사용해야 할 때 (POST 방식으로 JSON 형태로 보낸다. 받는 쪽에선 @RequestBody와 map으로 받을 것)
  // fetch(주소, {옵션});
fetch(fnGetContextPath() + '/user/checkEmail.do', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    'email': inpEmail.value
    })
  })
  .then(response => response.json())  // .then( (response) => { return response.json(); } )
  .then(resData => {
    if(resData.enableEmail){
      document.getElementById('msg-email').innerHTML = '';
      fetch(fnGetContextPath() + '/user/sendCode.do', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          'email': inpEmail.value
        })
      })
      .then(response => response.json())
      .then(resData => {  // resData = {"code": "123qaz"}
        alert(inpEmail.value + '로 인증코드를 전송했습니다.');
        let inpCode = document.getElementById('inp-code');
        let btnVerifyCode = document.getElementById('btn-verify-code');
        inpCode.disabled = false;
        btnVerifyCode.disabled = false;
        btnVerifyCode.addEventListener('click', (evt) => {
          if(resData.code === inpCode.value) {
            alert('인증되었습니다.');
            emailCheck = true;
          } else {
            alert('인증되지 않았습니다.');
            emailCheck = false;
          }
        })
      })
    } else {
      document.getElementById('msg-email').innerHTML = '이미 사용 중인 이메일입니다.';
      emailCheck = false;
      return;
    }
  })
}

const fnCheckPassword = () => {
  // 비밀번호 4~12자, 영문/숫자/특수문자 중 2개 이상 포함
  let inpPw = document.getElementById('inp-pw');
  let validCount = /[A-Za-z]/.test(inpPw.value)       // 영문 포함되어 있으면 true (JavaScrip 에서 true 는 숫자 1과 같다.)
                 + /[0-9]/.test(inpPw.value)         // 숫자 포함되어 있으면 true
                 + /[^A-Za-z0-9]/.test(inpPw.value);  // 영문/숫자가 아니면(=특수문자) 포함되어 있으면 true
  let passwordLength = inpPw.value.length; 
  passwordCheck = passwordLength >= 4
               && passwordLength <= 12
               && validCount >= 2;
  let msgPw = document.getElementById('msg-pw');
  if(passwordCheck){
    msgPw.innerHTML = '사용 가능한 비밀번호입니다.';
  }else{
    msgPw.innerHTML = '비밀번호 4~12자, 영문/숫자/특수문자 중 2개 이상 포함되어야 합니다.';
  }
}

const fnConfirmPassword = () => {
  let inpPw = document.getElementById('inp-pw');
  let inpPw2 = document.getElementById('inp-pw2');
  passwordConfirm = (inpPw.value !== '') 
                 && (inpPw.value === inpPw2.value);
  let msgPw2 = document.getElementById('msg-pw2');
  if(passwordConfirm) {
    msgPw2.innerHTML = '';
  } else {
    msgPw2.innerHTML = '비밀번호 입력을 확인하세요'; 
  }
}

const fnCheckName = () => {
  let inpName = document.getElementById('inp-name');
  let name = inpName.value;
  let totalByte = 0;
  // ascii code(0~127)
  for(let i = 0 ; i < name.length; i++){
	  if(name.charCodeAt(i) > 127){		// 코드값이 127 초과이면 한 글자 당 2바이트 처리한다.
		   totalByte += 2;
	  } else {
		  totalByte++;
	  }
  }
  nameCheck = (totalByte <= 100);
  let msgName = document.getElementById('msg-name');
  if(!nameCheck){
	  msgName.innerHTML = '이름은 100 바이트를 초과할 수 없습니다.'; 
  } else {
	  msgName.innerHTML = '';
  }
}

const fnCheckMobile = () => {
	let inpMobile = document.getElementById('inp-mobile');
	let mobile = inpMobile.value;
	mobile  = mobile.replaceAll(/[^0-9]/g, '');		// 슬래시 뒤 g : global option
	mobileCheck = /^010[0-9]{8}$/.test(mobile);
	let msgMobile = document.getElementById('msg-mobile');
	if(mobileCheck){
		msgMobile.innerHTML = '';
	} else {
		msgMobile.innerHTML = '휴대전화를 확인하세요.';
	}
	
}

const fnCheckAgree = () => {
	let chkService = document.getElementById('chk-service');
	agreeCheck = chkService.checked;
	
}

const fnSignup = () => {
    document.getElementById('frm-signup').addEventListener('submit', (evt)=>{
    	fnCheckAgree();
      if(!emailCheck){
        alert('이메일을 확인하세요.');
        evt.preventDefault();
        return;
      } else if(!passwordCheck || !passwordConfirm){
        alert('비밀번호를 확인하세요.');
        evt.preventDefault();
        return;
      } else if(!nameCheck){
        alert('이름을 확인하세요.');
        evt.preventDefault();
        return;
      } else if(!mobileCheck){
        alert('휴대전화를 확인하세요.');
        evt.preventDefault();
        return;
      } else if(!agreeCheck){
  			alert('서비스 약관에 동의해야 서비스를 이용할 수 있습니다.');
  			evt.preventDefault();
  			return;
  		}
    })
  }

document.getElementById('btn-code').addEventListener('click', fnCheckEmail);
// keyup - 입력 하나하나 할 때, blur - 입력 다 하고 포커스 잃었을 때
document.getElementById('inp-pw').addEventListener('keyup', fnCheckPassword);
document.getElementById('inp-pw2').addEventListener('blur', fnConfirmPassword);
document.getElementById('inp-name').addEventListener('blur', fnCheckName);
document.getElementById('inp-mobile').addEventListener('blur', fnCheckMobile);

fnSignup();