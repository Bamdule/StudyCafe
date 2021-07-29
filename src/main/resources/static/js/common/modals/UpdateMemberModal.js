class UpdateMemberModal extends BaseModal {
    constructor(param) {
        super(param);

        this.callback = {
            updateMemberCallback: param.updateMemberCallback
        };
        this.init();
    }

    init() {
        let {baseDiv, contentDiv, actionsDiv} = this.ui;
        let {updateMemberCallback} = this.callback;
        let self = this;
        baseDiv.css("width", "30%");

        let targetStudyHourInput = $("#targetStudyHour");
        let passwordInput = $("#memberPassword");
        let repasswordInput = $("#memberRepassword");


        contentDiv.append($("#updateMemberDiv"));
        actionsDiv.append($("<button>", {
            text: "취소", class: "ui button", click: function () {
                self.close();
            }
        }))
        actionsDiv.append($("<button>", {
            text: "확인", class: "ui button", click: function () {
                let password = passwordInput.val();
                let repassword = repasswordInput.val();
                let targetStudyHour = targetStudyHourInput.val();


                if (password != '' && password !== repassword) {
                    showToast("두 패스워드가 일치하지 않습니다.");
                    return false;
                }

                updateMemberCallback({
                    targetStudyHour: targetStudyHour,
                    password: password
                }, function () {
                    showToast("수정이 완료되었습니다.");
                    self.close();
                });
            }
        }))
        this.event({
            onHidden: function () {
                targetStudyHourInput.val("");
                passwordInput.val("");
                repasswordInput.val("");
            }
        })
    }

}
