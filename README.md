1.
http://localhost:8080/realms/test/protocol/openid-connect/auth?client_id= &client_secret= &scope= &redirect_uri= &state= &response_type=code
![...](media/Screenshot_42.png)
2.
параметр code в урле
![...](media/Screenshot_46.png)
3.
http://localhost:8080/realms/test/protocol/openid-connect/token
в теле тот самый code
![...](media/Screenshot_43.png)
4.
из json access_token иденфицирует юзера с ролем USER
![...](media/Screenshot_47.png)
5.
который получает форбидден на эндпоинт для ADMIN
![...](media/Screenshot_44.png)
но имеет доступ к остальным
![...](media/Screenshot_45.png)