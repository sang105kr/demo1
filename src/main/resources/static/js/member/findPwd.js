import {ajax} from '/js/common.js';

//비밀번호 찾기화면 렌더링
const render = () => {
  const $findPwdForm = document.createElement('div');
  $findPwdForm.innerHTML = `
       <div>
        <h3>비밀번호 찾기</h3>
        <form id="frm">
          <div>
            <input class="border" type="text" id="email" name="email" placeholder="아이디를 입력하세요">
            <span class='field-error' id="emailErr"></span>
          </div>
          <div>
            <input class="border" type="tel" id="tel" name="tel" placeholder="전화번호를 입력하세요">
            <span class='field-error' id="telErr"></span>
          </div>
          <div><span class="field-error"></span></div>
          <div>
            <input class="px-3 py-1 text-white bg-blue-500 rounded hover:bg-blue-700" id="findPwdBtn" type="button" value="비밀번호 찾기">
          </div>
          <div id="msg"></div>
        </form>
        <div>
      `;

  const $findPwdBtn = $findPwdForm.querySelector('#findPwdBtn');
  const $email = $findPwdForm.querySelector('#email');
  const $emailErr = $findPwdForm.querySelector('#emailErr');

  const $tel = $findPwdForm.querySelector('#tel');
  const $telErr = $findPwdForm.querySelector('#telErr');
  const $msg = $findPwdForm.querySelector('#msg');

  $findPwdBtn.addEventListener('click', e => {
    //유효성 체크
    if($email.value.trim().length === 0) {
      $emailErr.textContent = '아이디를 입력하세요';
      $email.focus();
      return;
    }else{
      $emailErr.textContent = '';
    }

    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if (!emailRegex.test($email.value)) {
      $emailErr.textContent = '이메일 형식과 맞지 않습니다.';
      $email.focus();
      return;
    } else {
      $emailErr.textContent = '';
    }

    if ($tel.value.trim().length === 0) {
      $telErr.textContent = '전화번호를 입력하세요';
      $tel.focus();
      return;
    }else{
      $telErr.textContent = '';
    }

    const telRegex = /^\d{3}-\d{4}-\d{4}$/;
    if (!telRegex.test($tel.value)) {
      $telErr.textContent = '전화번호 형식이 맞지 않습니다 .ex) xxx-xxxx-xxxx';
      $tel.focus();
      return;
    } else {
      $telErr.textContent = '';
    }

    //비밀번호 찾기
    findPwd($email.value, $tel.value, $msg)
  });
  const $main = document.querySelector('#content .main');
  $main.appendChild($findPwdForm);
};

//비밀번호 찾기 비동기 통신(요청,응답)
const findPwd = async (email, tel, $msg) => {
  const url = `http://localhost:9080/api/members/findPwd`;
  const data = { email, tel };

  const result = await ajax.post(url, data);
  console.log(result);

  if(result.header.rtcd === '00') {
    $msg.textContent = "회원 이메일로 임시 비밀번호를 전송했습니다."
  }

};

render();
