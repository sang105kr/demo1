 function changeErrMsg(){
    [...document.querySelectorAll('.field-error')].forEach(ele=>ele.textContent = ele.innerHTML.replace('<br>',' / '));
 }
 export {changeErrMsg}