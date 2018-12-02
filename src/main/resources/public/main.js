(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/app.component.html":
/*!************************************!*\
  !*** ./src/app/app.component.html ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"d-flex h-100\">\r\n    <div class=\"p-2 bg-secondary flex-fill scrollable\">\r\n        <div class=\"editor\">\r\n            <!-- json editor here -->\r\n        </div>\r\n    </div>\r\n    <div class=\"p-1 bg-dark flex-1 dragger\">\r\n    </div>\r\n    <div class=\"p-2 bg-secondary flex-fill scrollable\">\r\n        <div class=\"viewer\">\r\n            <!-- json viewer here -->\r\n        </div>\r\n    </div>\r\n</div>\r\n\r\n<app-cookie></app-cookie>"

/***/ }),

/***/ "./src/app/app.component.scss":
/*!************************************!*\
  !*** ./src/app/app.component.scss ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".dragger {\n  cursor: ew-resize; }\n\n.scrollable {\n  overflow: scroll; }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvQzpcXFVzZXJzXFxhbmFuZFxcZXdzXFxyYXNvblxcc3JjXFxtYWluXFxuZy9zcmNcXGFwcFxcYXBwLmNvbXBvbmVudC5zY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0ksa0JBQWlCLEVBQ3BCOztBQUVEO0VBQ0ksaUJBQWdCLEVBQ25CIiwiZmlsZSI6InNyYy9hcHAvYXBwLmNvbXBvbmVudC5zY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLmRyYWdnZXJ7XHJcbiAgICBjdXJzb3I6IGV3LXJlc2l6ZTtcclxufVxyXG5cclxuLnNjcm9sbGFibGV7XHJcbiAgICBvdmVyZmxvdzogc2Nyb2xsO1xyXG59Il19 */"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");


var AppComponent = /** @class */ (function () {
    function AppComponent() {
        this.title = 'ng';
    }
    AppComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! ./app.component.html */ "./src/app/app.component.html"),
            styles: [__webpack_require__(/*! ./app.component.scss */ "./src/app/app.component.scss")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var ngx_cookieconsent__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ngx-cookieconsent */ "./node_modules/ngx-cookieconsent/esm5/ngx-cookieconsent.es5.js");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _cookie_cookie_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./cookie/cookie.component */ "./src/app/cookie/cookie.component.ts");






var cookieconfig = {
    "cookie": { "domain": window.location.hostname },
    "palette": {
        "popup": {
            "background": "#F47C79",
            "text": "#50514F",
            "link": "#F3FFBD"
        },
        "button": {
            "background": "#50514F",
            "text": "#F3FFBD"
        }
    },
    "theme": "classic",
    "position": "bottom-right",
    "content": {
        "link": "more info",
        "href": "ng/cookiepolicy"
    }
};
var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_4__["AppComponent"],
                _cookie_cookie_component__WEBPACK_IMPORTED_MODULE_5__["CookieComponent"]
            ],
            imports: [
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__["BrowserModule"],
                ngx_cookieconsent__WEBPACK_IMPORTED_MODULE_3__["NgcCookieConsentModule"].forRoot(cookieconfig)
            ],
            providers: [],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_4__["AppComponent"]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/cookie/cookie.component.ts":
/*!********************************************!*\
  !*** ./src/app/cookie/cookie.component.ts ***!
  \********************************************/
/*! exports provided: CookieComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CookieComponent", function() { return CookieComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var ngx_cookieconsent__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ngx-cookieconsent */ "./node_modules/ngx-cookieconsent/esm5/ngx-cookieconsent.es5.js");



var CookieComponent = /** @class */ (function () {
    function CookieComponent(ccService) {
        this.ccService = ccService;
    }
    CookieComponent.prototype.ngOnInit = function () {
        this.popupOpenSubscription = this.ccService.popupOpen$.subscribe(function () {
            // you can use this.ccService.getConfig() to do stuff...
        });
        this.popupCloseSubscription = this.ccService.popupClose$.subscribe(function () {
            // you can use this.ccService.getConfig() to do stuff...
        });
        this.initializeSubscription = this.ccService.initialize$.subscribe(function (event) {
            // you can use this.ccService.getConfig() to do stuff...
        });
        this.statusChangeSubscription = this.ccService.statusChange$.subscribe(function (event) {
            // you can use this.ccService.getConfig() to do stuff...
        });
        this.revokeChoiceSubscription = this.ccService.revokeChoice$.subscribe(function () {
            // you can use this.ccService.getConfig() to do stuff...
        });
        this.noCookieLawSubscription = this.ccService.noCookieLaw$.subscribe(function (event) {
            // you can use this.ccService.getConfig() to do stuff...
        });
    };
    CookieComponent.prototype.ngOnDestroy = function () {
        this.popupOpenSubscription.unsubscribe();
        this.popupCloseSubscription.unsubscribe();
        this.initializeSubscription.unsubscribe();
        this.statusChangeSubscription.unsubscribe();
        this.revokeChoiceSubscription.unsubscribe();
        this.noCookieLawSubscription.unsubscribe();
    };
    CookieComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-cookie',
            template: ''
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [ngx_cookieconsent__WEBPACK_IMPORTED_MODULE_2__["NgcCookieConsentService"]])
    ], CookieComponent);
    return CookieComponent;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.error(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! C:\Users\anand\ews\rason\src\main\ng\src\main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map