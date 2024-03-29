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

  // 내위치 주위에 편의점 찾기

  // 내위치 찾기
  const promise = new Promise((resolve, reject) => {
    navigator.geolocation.getCurrentPosition(resolve, reject);
  });
  promise
    .then(res => res.coords) // 내위치
    .then(coords => {
      const map = createMap(coords);
      return { map, coords };
    }) // 지도생성
    .then(res => {
      const myMarker = createMyMarker(res.map, res.coords);
      return res;
    }) //내위치 마커표시
    .then(res => findConvenienceStore(res.map, res.coords)) //편의점찾기
    .catch(err => console.log(err.message))
    .finally(() => {
      console.log('내위치 찾기 종료!');
    });

  //편의점 찾기
  function findConvenienceStore(map, { lat, lng }) {
    // 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
    const infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

    // 장소 검색 객체를 생성합니다
    const ps = new kakao.maps.services.Places(map);

    // 전체 검색 데이터
    let dataAll = [];

    // 카테고리로 편의점을 검색합니다
    const categoryOption = {
      location: new kakao.maps.LatLng(lat, lng), // 특정지역 기준검색
      radius: 5 * 1000, // 검색반경
      // sort : 'DISTANCE', // 거리순
      useMapBounds: true,
    };

    // 지도에 마커를 표시하는 함수입니다
    const displayMarker = place => {
      // 마커를 생성하고 지도에 표시합니다
      const marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x),
      });

      // 마커에 클릭이벤트를 등록합니다
      kakao.maps.event.addListener(marker, 'mouseover', function () {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.setContent(
          '<div style="padding:5px;font-size:12px;">' +
            place.place_name +
            '</div>',
        );
        infowindow.open(map, marker);
      });

      kakao.maps.event.addListener(marker, 'mouseout', function () {
        infowindow.close();
      });
    };

    const filter = data => {
      console.log('전체 데이터');
      console.log(data);
      const csArr = data
        .map(ele => ele.place_name.split(' ')[0])
        .reduce((acc, curr, idx) => {
          acc[curr] = curr in acc ? acc[curr] + 1 : 1;
          return acc;
        }, {});
      console.log(csArr);
    };

    // 키워드 검색 완료 시 호출되는 콜백함수 입니다

    const placesSearchCB = (data, status, pagination) => {
      console.log(data, status, pagination);
      if (status === kakao.maps.services.Status.OK) {
        dataAll = [...dataAll, ...data];
        for (let i = 0; i < data.length; i++) {
          displayMarker(data[i]); //[{},{},{}]
        }
        // if(pagination.hasNextPage) pagination.nextPage();
        pagination.hasNextPage && pagination.nextPage();

        filter(dataAll);
      }
    };

    //카테고리 검색
    ps.categorySearch('CS2', placesSearchCB, categoryOption);
  }