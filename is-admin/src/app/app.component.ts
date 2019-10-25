import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {

    title = 'imooc microservice security test！！！';
    authenticated = false;
    credentials = {
        username: 'zhangsan',
        password: '123456'
    };

    order = {};


    constructor(private http: HttpClient, private cookieService: CookieService) {
        this.http.get("api/user/me").subscribe(data => {
            if (data) {
                this.authenticated = true
            }
            if (!this.authenticated) {
                window.location.href = "http://auth.magic.com:9090/oauth/authorize?"
                    + "client_id=admin"
                    + "&redirect_uri=http://admin.magic.com:8080/oauth/callback"
                    + "&response_type=code"
                    + "&state=abc";
            }
        })
    }

    login() {
        this.http.post('login', this.credentials).subscribe(() => {
            this.authenticated = true;

        }, () => {
            alert("login fail");
        })
    }

    getOrder() {
        this.http.get('api/order/orders/1').subscribe(data => {
            this.order = data;
        }, () => {
            alert('get order fail');
        });
    }

    // 退出
    logout() {
        this.cookieService.delete("magic_access_token", "/", "magic.com");
        this.cookieService.delete("magic_refresh_token", "/", "magic.com");
        this.http.post('logout', this.credentials).subscribe(() => {
            this.authenticated = false;
            // 本地客户端退出登录，清空session，跳转到认证中心也去清除session，成功后回调到当前服务的首页（登录页面）
            window.location.href = "http://auth.magic.com:9090/logout?redirect_uri=http://admin.magic.com:8080";
        }, () => {
            alert("logout fail");
        })
    }

}
