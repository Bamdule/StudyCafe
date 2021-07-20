class SignUpModal extends BaseModal {
    constructor(param) {
        super(param);

        this.callback = {
            signUpCallback: param.signUpCallback,
            emailCodeCallback: param.emailCodeCallback,
            phoneDuplicateCallback: param.phoneDuplicateCallback,
            requestEmailCodeCallback: param.requestEmailCodeCallback
        };
        this.init();
    }

    init() {
        let {baseDiv, contentDiv, actionsDiv} = this.ui;
        let {signUpCallback, requestEmailCodeCallback} = this.callback;
        let self = this;

        let emailInput = $("#email");
        let emailCodeInput = $("#emailCode");
        let nameInput = $("#name");
        let phoneInput = $("#phone");
        let passwordInput = $("#password");
        let repasswordInput = $("#repassword");

        $("#requestEmailCodeBtn").click(function () {
            let email = emailInput.val();
            if (email !== "" && email !== null && email !== undefined)
                requestEmailCodeCallback(email);
        });

        function getMember() {
            return {
                email: emailInput.val(),
                emailCode: emailCodeInput.val(),
                name: nameInput.val(),
                phone: phoneInput.val(),
                password: passwordInput.val(),
                repassword: repasswordInput.val()
            };
        }

        function reset() {
            emailInput.val("");
            emailCodeInput.val("");
            nameInput.val("");
            phoneInput.val("");
            passwordInput.val("");
            repasswordInput.val("");
        }


        baseDiv.css("width", "30%");
        contentDiv.append($("#signUpDiv"));
        actionsDiv.append($("<button>", {
            text: "취소", class: "ui button", click: function () {
                self.close();
            }
        }))
        actionsDiv.append($("<button>", {
            text: "확인", class: "ui button", click: function () {
                let member = getMember();

                if (member.password !== member.repassword) {
                    showToast("두 패스워드가 일치하지 않습니다.");
                    return false;
                }

                signUpCallback(getMember());
            }
        }))
        this.event({
            onHidden: function () {
                reset();
            }
        })
    }

}
