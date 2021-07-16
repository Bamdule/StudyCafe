class Websocket {
    constructor(config) {

        this.config = config;
    }

    connect() {
        let {
            serverUri, onMessage = function () {
            }, onOpen = function () {
            }, onError = function () {
            }
        } = this.config;

        let websocket = new SockJS(serverUri);
        websocket.onopen = function (event) {
            onOpen(event)
        };
        websocket.onmessage = function (event) {
            onMessage(event)
        };
        websocket.onerror = function (event) {
            onError(event)
        };
        this.websocket = websocket;
    }

    disconnect() {
        if (!this.websocket) {
            this.websocket.close();
        }
    }


}