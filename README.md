나프콘 2016 Play를 이용한 WebSocket
===============================

## 설치
이 Play WebApp을 빌드 후 실행 하시기 위해서는 SBT 혹은 Activator가 설치 되어 있어야 합니다.

설치방법은 해당 툴 웹사이트에서 확인해 주세요.
* SBT: http://www.scala-sbt.org/
* Lightbend Activator: https://www.lightbend.com/activator/download

**둘중에 하나만 있으면 됩니다.**

## Controller

- Napucon2016Controller.scala:

  내부적으로 `play.api.libs.ws.WSClient`를 이용해서 http://openweathermap.org/api 접속후 날짜 정보를 받아 옵니다

## Open Weather Map API Key 설정

`conf/application.conf`파일을 여시고, 맨 밑에 있는 `weather`부분을 수정해 주세요


```
weather {
  api.key="이부분에 API Key를 넣어주세요"
  api.key=${?OPEN_WEATHER_MAP_API_KEY}
}
```
OpenWeatherMap 계정이 없는 분은 아래 주소를 방문 하셔서 가입해 주세요.
https://home.openweathermap.org/users/sign_up
가입 하신후 API Key를 발급 받을 수 있습니다.

## 실행
### Activator 사용
```shell
activator run 
```

### SBT 사용
```shell
sbt run 
```


### 웹소켓 주소
ws://localhost:9000/ws

실행후 http://localhost:9000/ws 에 접속해서, 아래와 같은 메세지가 뜬다면 정상 작동 하는것입니다.
```
Upgrade to WebSocket required
```

**Client app을 통해 WebSocket에 접속해야 합니다.**

## 질문 및 문의 사항
질문 및 문의 사항은 아래 주소를 방문하셔서 Issue Ticket을 작성해 주세요.

https://github.com/Kevin-Lee/napucon2016-weather-app/issues