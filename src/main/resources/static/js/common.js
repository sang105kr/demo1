function changeErrMsg() {
  [...document.querySelectorAll('.field-error')].forEach(
    ele => (ele.textContent = ele.innerHTML.replace('<br>', ' / ')),
  );
}
/*-----------------------------------------------------------------------*
 * client-server간 http api 비동기 통신
 *-----------------------------------------------------------------------*/
const ajax = {
  get: async url => {
    const option = {
      method: 'GET',
      headers: {
        Accept: 'application/json',
      },
    };
    try {
      const response = await fetch(url, option);
      const data = await response.json();
      return data;
    } catch (err) {
      console.error(err.message);
    }
  },
  post: async (url, payload) => {
    const option = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify(payload), // jsobject => json포맷의 문자열
    };
    try {
      const response = await fetch(url, option);
      const data = await response.json();// json포맷의 문자열 => jsobject
      return data;   // async함수의 return 값은 프라미스개체로 반환된다.
    } catch (err) {
      console.error(err.message);
    }
  },
  put: async url => {
    const option = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify(payload),
    };
    try {
      const response = await fetch(url, option);
      const data = await response.json();
      return data;
    } catch (err) {
      console.log(err.message);
    }
  },
  patch: async (url, payload) => {
    const option = {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify(payload),
    };
    try {
      const response = await fetch(url, option);
      const data = await response.json();
      return data;
    } catch (err) {
      console.log(err.message);
    }
  },
  delete: async url => {
    const option = {
      method: 'DELETE',
      headers: {
        Accept: 'application/json',
      },
    };
    try {
      const response = await fetch(url, option);
      const data = await response.json();
      return data;
    } catch (err) {
      console.log(err.message);
    }
  },
};
/*-----------------------------------------------------------------------*
 * 자식 요소를 포함하는 요소를 만들어 반환하는 함수
 * input :
 *    tagname    : 태그명
 *    attributes : 속성 - { 속성1:값1, 속성2:값2}
 *    contents   : 컨텐츠 - text or element
 * output :
 *    element
 *-----------------------------------------------------------------------*/
const makeElement = (tagname, attributes, ...contents) => {
    //요소생성
	const element = document.createElement(tagname);

	//요소의 속성 추가
	if( attributes ) {
		for(let attr in attributes) {
		    //부모요소 제외하고 속성 추가
			if(attributes.hasOwnProperty(attr)) {
				element.setAttribute(attr,attributes[attr]);
			}
		}
	}

	//컨텐츠 추가
    contents.forEach(content => {
        //text컨텐츠
		if( typeof content == "string" ) {
			element.textContent = content;
		//요소
		}else{
		    element.appendChild(content);
		}
	});
	return element;
}
export { changeErrMsg, ajax,makeElement };
