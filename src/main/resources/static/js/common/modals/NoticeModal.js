class NoticeModal {
    constructor() {
    }

    initialize(param) {
        let {
            title, message, actions = null, onHide = function () {
            }, onHidden = function () {
            }, onApprove = function () {
            }, onDeny = function () {
            }, iconClass = ""
        } = param;

        let modalDiv = $("<div>", {class: "ui modal basic", css: {textAlign: "center"}});
        let iconElement = $("<i>", {class: iconClass});
        let header = $("<div>", {class: "ui icon header"});
        let content = $("<div>", {class: "content", text: message});
        header.append(iconElement);
        header.append(title);
        modalDiv.append(header);
        modalDiv.append(content);
        if (actions !== null) {
            modalDiv.append(actions);
        }


        modalDiv.modal({
            onHide,
            onHidden,
            onApprove,
            onDeny
        });


        this.content = content;
        this.modalDiv = modalDiv;
    }

    show(param = {}) {
        let {
            message = null,
            onHidden = function () {
            }, onApprove = function () {
            }

        } = param;
        if (message !== null) {
            this.content.html(message);
        }

        this.modalDiv.modal({onHidden, onApprove});

        this.modalDiv.modal('show');
    }

    isActive() {
        return this.modalDiv.modal('is active');
    }

    hide() {
        this.modalDiv.modal("hide");
    }
}

class ConfirmModal extends NoticeModal {
    constructor(param) {
        super();

        let {onApprove = null, onDeny = null} = param;

        if (onApprove !== null || onDeny !== null) {
            let actions = $("<div>", {class: "actions", css: {textAlign: "center"}});

            if (onApprove !== null) {
                actions.append($("<div>", {class: "ui green ok inverted button", text: "확인"}));
            }
            if (onDeny !== null) {
                actions.append($("<div>", {class: "ui red basic cancel inverted button", text: "취소"}));
            }
            param.actions = actions;
        }

        super.initialize(param);
    }

}