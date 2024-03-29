  // 지도 생성 함수
  const createMap = ({ latitude, longitude }) => {
    const mapContainer = document.getElementById('map'); // 지도를 표시할 div
    const mapOption = {
      center: new kakao.maps.LatLng(latitude, longitude), // 지도의 중심좌표
      level: 3, // 지도의 확대 레벨
    };

    // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
    const map = new kakao.maps.Map(mapContainer, mapOption);
    return map;
  };

  // 내위치 마커 생성 함수
  const createMyMarker = (map, { latitude, longitude }) => {
    const icon = new kakao.maps.MarkerImage(
      '/img/me.png',
      new kakao.maps.Size(40, 50),
    );
    const marker = new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(latitude, longitude),
      image: icon,
    });
    marker.setMap(map); //지도위에 마커표시
    return marker;
  };

  const createDisplayMarker = map => {
    // 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
    const infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

    // 지도에 마커를 표시하는 함수
    return place => {
      // 마커를 생성하고 지도에 표시합니다
      const marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.wgs84Lat, place.wgs84Lon),
      });

      // 마커에 클릭이벤트를 등록합니다
      kakao.maps.event.addListener(marker, 'mouseover', function () {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.setContent(
          `<div style="padding:5px;font-size:12px;">${place.org}-${place.buildplace}</div>`,
        );
        infowindow.open(map, marker);
      });

      kakao.maps.event.addListener(marker, 'mouseout', function () {
        infowindow.close();
      });
    };
  };

  // 공공데이터로부터 aed 위치찾기
  const findAed = async coords => {
    const serviceKey =
      'CryKKi6HaVVnP0WXU4sIp8dcrZgn2wui0UPEU%2BeivronhsULZ8SFW3qxmqgGmyqgpj59gqzMmd8H%2BhWEzjcvBw%3D%3D';
    // const lat = 35.53235; //위도
    // const lng = 129.3076; //경도
    const lat = coords.latitude; //위도
    const lng = coords.longitude; //경도
    const pageNo = 1; //페이지 번호
    const numOfRows = 30; //목록 건수
    const url = `http://apis.data.go.kr/B552657/AEDInfoInqireService/getAedLcinfoInqire`;
    const param = `?serviceKey=${serviceKey}&WGS84_LON=${lng}&WGS84_LAT=${lat}&pageNo=${pageNo}&numOfRows=${numOfRows}`;

    const option = {
      method: 'GET',
      headers: {
        Accept: 'application/json', //응답 메시지 바디 타입
      },
    };
    try {
      const response = await fetch(url + param, option);
      const data = await response.json();
      return data;
    } catch (err) {
      console.error(err.message);
    }
  };

  // 내위치 찾기
  const findMYLocation = async () => {
    try {
      //1)내위치
      const { coords } = await new Promise((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
      });

      //2)지도생성
      const map = createMap(coords);

      //3)내위치 마커에 표시
      const myMarker = createMyMarker(map, coords);

      //4)aed 정보가져오기
      const data = await findAed(coords);
      if (data.response.header.resultCode === '00') {
        const { item } = data.response.body.items;
        const displayMarker = createDisplayMarker(map);
        [...item].forEach(displayMarker);
      } else {
        throw new Error('오류!');
      }
    } catch (err) {
      console.log(err.message);
    }
  };

  findMYLocation();