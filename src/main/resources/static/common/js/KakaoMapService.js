class KakaoMapService {
    constructor() {
    }

    //사용자 위치 좌표 가져오기
    getMyLocation(){
        return new Promise((resolve, reject) => {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    (position) => {
                        // 위치 정보를 Promise로 반환
                        resolve({
                            lat: position.coords.latitude,
                            lon: position.coords.longitude
                        });
                    },
                    (error) => {
                        console.error("Unable to retrieve your location.", error);
                        reject(error);
                    }
                );
            } else {
                alert("Geolocation is not supported by this browser.");
                reject(new Error("Geolocation is not supported by this browser."));
            }
        });
    }

    //카카오지도 설정하기
    setKakaoMap(divMap,position,zoom) {
        let container = divMap[0];
        let lat = position.lat;
        let lon = position.lon;

        if(zoom===""){
         zoom = 3;
        }

        let options = {
            center: new kakao.maps.LatLng(lat, lon),
            level: zoom
        };

        return new kakao.maps.Map(container, options);
    }

    //주소를 통해서 좌표 가져오기
    getAddressSearch(addList){
        let geocoder = new kakao.maps.services.Geocoder();
        let promises = addList.map(postion =>{
            return new Promise((resolve, reject) => {
                geocoder.addressSearch(postion.dAddress, function (result, status){
                    if(status === kakao.maps.services.Status.OK){
                        resolve({
                            name:postion.name,
                            dposition:postion.dAddress,
                            gposition:postion.gAddress,
                            lon:result[0].x,
                            lat:result[0].y
                        })
                    } else {
                        // reject(new Error(`Failed to find coordinates for address: ${postion.address}`));
                    }
                })
            })
        })
        return Promise.all(promises);
    }

    //좌표를 통해서 주소 가져오기
    getCoordSearch(myLoc){
        return new Promise((resolve, reject)=>{
            let geocoder = new kakao.maps.services.Geocoder();
            let coord = new kakao.maps.LatLng(myLoc.lat, myLoc.lon);
            let callback = function (result, status){
                if(status === kakao.maps.services.Status.OK){
                    resolve(result[0]);
                } else{

                }
            };
            geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
        });
    }

    //카카오지도에 마커표시
    setMarker(map,coorList){
       return  coorList.map(position =>{
            let coords = new kakao.maps.LatLng(position.lat, position.lon);

            let marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });

            return marker;
        });
    }

    setMarkerImage(info){
        let imageSrc = '/img/marker_red.png';
        let imageSize = new kakao.maps.Size(info.width, info.height);
        // let imageOption = {};
        return new kakao.maps.MarkerImage(imageSrc,imageSize);
    }

    //원으로 범위표시
    setDrawCircle(map,myLoc,radius){
       let circle =  new kakao.maps.Circle({
            center: new kakao.maps.LatLng(myLoc.lat, myLoc.lon),
            radius: radius, // 미터 단위의 반경
            strokeWeight: 1, // 선의 두께입니다
            strokeColor: '#00a0e9', // 선의 색깔입니다
            strokeOpacity: 0.1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
            strokeStyle: 'solid', // 선의 스타일입니다
            fillColor: '#00a0e9', // 채우기 색깔입니다
            fillOpacity: 0.2 // 채우기 불투명도입니다
        });

       circle.setMap(map);
    }

    setDrawCircleReturn(myLoc,radius){
        return new Promise((resolve, rejet) => {
            try {
                let circle = new kakao.maps.Circle({
                    center: new kakao.maps.LatLng(myLoc.lat, myLoc.lon),
                    radius: radius, // 미터 단위의 반경
                    strokeWeight: 1, // 선의 두께입니다
                    strokeColor: '#00a0e9', // 선의 색깔입니다
                    strokeOpacity: 0.1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
                    strokeStyle: 'solid', // 선의 스타일입니다
                    fillColor: '#00a0e9', // 채우기 색깔입니다
                    fillOpacity: 0.2 // 채우기 불투명도입니다
                });
                resolve(circle);
            } catch (error){
                rejet(error);
            }
        })
    }

    //반경 xxM안에 있는 것만 가져오기
    filterCoordinatesWithinRadius(myLoc,addToCoor,radius) {
        return addToCoor.filter(coord => {
            const distance = this.calculateDistance(myLoc.lat, myLoc.lon, coord.lat, coord.lon);
            return distance <= radius;
        })
    }

    //반경 xxM안에 있는 마커만 가져오기
    filterMarkerWithinRadius(myLoc,markers,nearbyCoordinates,radius){
        let nearMarker = [];
        for(let i = 0 ; i < markers.length ; i++){
            const distance = this.calculateDistance(myLoc.lat, myLoc.lon,
                markers[i].getPosition().Ma, markers[i].getPosition().La);
            const sLocation = this.calculateDistance(markers[i].getPosition().Ma, markers[i].getPosition().La,
            nearbyCoordinates[i].lat,nearbyCoordinates[i].lon);
            if(distance <= radius){
                if(sLocation <= 3){
                    nearMarker.push({marker:markers[i], name:nearbyCoordinates[i].name});
                }
            }
        }
        return nearMarker;
    }

    //마커들 위치정보 분리
    getMarkersPosition(markers){
        let markerInfo = [];
        for(let i = 0 ; i < markers.length ; i++){
            markerInfo.push({lon:markers[i].getPosition().La,lat:markers[i].getPosition().Ma});
        }
        return markerInfo;
    }

    //하나의 마커 위치정보 분리
    getMarkerPosition(marker){
        let markerInfo = [];
            markerInfo.push({lon:marker.getPosition().La,lat:marker.getPosition().Ma});
        return markerInfo;
    }

    //좌표를 통해서 반경안에 있는 좌표 구하는 공식
    calculateDistance(lat1, lon1, lat2, lon2) {
        let R = 6371e3; // 지구의 반지름 (단위: 미터)
        let lat1Rad = lat1 * Math.PI/180; // 위도를 라디안으로 변환
        let lat2Rad = lat2 * Math.PI/180;
        let deltaLat = (lat2 - lat1) * Math.PI/180;
        let deltaLon = (lon2 - lon1) * Math.PI/180;

        let a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
            Math.cos(lat1Rad) * Math.cos(lat2Rad) *
            Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
        let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        let d = R * c; // 두 좌표 사이의 거리 (단위: 미터)
        return d;
    }
}

export default KakaoMapService;