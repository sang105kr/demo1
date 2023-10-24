function changeErrMsg() {
  [...document.querySelectorAll('.field-error')].forEach(
    ele => (ele.textContent = ele.innerHTML.replace('<br>', ' / ')),
  );
}

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
      const data = await response.json();
      return data;
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

export { changeErrMsg, ajax };
