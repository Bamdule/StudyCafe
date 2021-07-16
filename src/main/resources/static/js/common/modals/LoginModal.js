class LoginModal extends BaseModal {
    constructor(param) {
        super(param);

        this.callback = {loginCallback: param.loginCallback};
        this.init();
    }

    init() {
        let {baseDiv, contentDiv, actionsDiv} = this.ui;
        let {loginCallback} = this.callback;
        baseDiv.css("width", "30%");

        let phoneInputDiv = $("<div>", {class: "ui input fluid"});
        let phoneInput = $("<input>", {type: "text", placeholder: "휴대폰번호"});
        phoneInputDiv.append(phoneInput);

        let passwordInputDiv = $("<div>", {class: "ui input fluid", css: {marginTop: "5px"}});
        let passwordInput = $("<input>", {type: "password", placeholder: "비밀번호"});
        passwordInputDiv.append(passwordInput);

        let loginBtn = $("<button>", {class: "ui button fluid", text: "로그인", css: {marginTop: "5px"}});

        contentDiv.append(phoneInputDiv);
        contentDiv.append(passwordInputDiv);
        contentDiv.append(loginBtn);

        loginBtn.click(function () {
            loginCallback(phoneInput.val(), passwordInput.val());
        });

        this.event({
            onHidden: function () {
                phoneInput.val("");
                passwordInput.val("");
            }
        })

    }
}
