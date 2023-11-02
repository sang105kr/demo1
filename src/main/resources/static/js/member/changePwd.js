import {ajax, makeElement} from "/js/common.js";

//사용자 아이디 = 이메일
const email = document.documentElement.dataset.sEmail;

//비밀번호 렌더링
const render = ()=>{
  const $div = makeElement("div");
  $div.innerHTML = `
    <h3>비밀번호 변경</h3>
    <form id="frm">
      <div>
        <input class="border" type="password" id="beforePasswd" name="beforePasswd" placeholder="현재 비밀번호 입력하세요">
        <span class='field-error' id="beforePasswdErr"></span>
      </div>
      <div>
        <input class="border" type="password" id="afterPasswd" name="afterPasswd" placeholder="변경할 비밀번호 입력하세요">
        <span class='field-error' id="afterPasswdErr"></span>
      </div>
      <div><span class="field-error"></span></div>
      <div id="msg" class="field-error"></div>
      <div>
        <input class="px-3 py-1 text-white bg-blue-500 rounded hover:bg-blue-700" id="changePwdBtn" type="button" value="비밀번호 변경">
      </div>
    </form>
  `;
  const $changePwdBtn = $div.querySelector('#changePwdBtn');
  $changePwdBtn.addEventListener('click',e=>{

    const $beforePasswd = $div.querySelector('#beforePasswd');
    const $afterPasswd = $div.querySelector('#afterPasswd');

    const beforePasswd = $beforePasswd.value;
    const afterPasswd = $afterPasswd.value;

    const $beforePasswdErr = $div.querySelector('#beforePasswdErr');
    const $afterPasswdErr = $div.querySelector('#afterPasswdErr');

    const $msg = $div.querySelector('#msg');

    //유효성 검증
    if ($beforePasswd.value.trim().length == 0){
      $beforePasswdErr.textContent = '필수입력 항목 입니다.';
      $beforePasswd.focus();
      return;
    }else{
      $beforePasswdErr.textContent = '';
    }

    if ($afterPasswd.value.trim().length == 0) {
      $afterPasswdErr.textContent = '필수입력 항목 입니다.';
      $afterPasswd.focus();
      return;
    } else {
      $afterPasswdErr.textContent = '';
    }

    //비밀번호 변경요청
    chanagePwd(email, beforePasswd, afterPasswd, $msg);
  })

  document.querySelector('#content .main').appendChild($div);
}


//비밀번호 변경요청
const chanagePwd = async(email, beforePasswd, afterPasswd, $msg)=>{
  const url = 'http://localhost:9080/api/members/changePwd';
  const payload = { email, beforePasswd, afterPasswd }
  const result = await ajax.post(url, payload);
  console.log(result);
  if(result.header.rtcd === '00') {
    $msg.textContent = '비밀번호가 변경되었습니다';
  }
}


render();