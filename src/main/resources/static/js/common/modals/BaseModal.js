class BaseModal {
    constructor(param) {
        let {title = ""} = param;
        console.log(title)

        let baseDiv = $("<div>", {class: "ui modal"});
        let headerDiv = $("<div>", {class: "ui header", text: title});
        let contentDiv = $("<div>", {class: "ui content"});
        let actionsDiv = $("<div>", {class: "actions"});

        baseDiv.append(headerDiv);
        baseDiv.append(contentDiv);
        baseDiv.append(actionsDiv);

        this.ui = {
            baseDiv,
            headerDiv,
            contentDiv,
            actionsDiv
        }

        baseDiv.modal();

    }

    event(param) {
        let {
            onHide = function () {
            }, onHidden = function () {
            }, onApprove = function () {
            }, closable = true, allowMultiple = true
        } = param;

        this.ui.baseDiv.modal({
            onHide: onHide,
            onHidden: onHidden,
            onApprove: onApprove,
            closable: closable,
            allowMultiple: allowMultiple
        });
    }

    show() {
        this.ui.baseDiv.modal('show');
        console.log(this.ui.baseDiv);
    }

    close() {
        this.ui.baseDiv.modal('hide');
    }
}