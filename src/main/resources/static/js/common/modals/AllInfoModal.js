class AllInfoModal extends BaseModal {
    constructor(param) {
        super(param);
        this.callback = {
            onExit: param.onExit,
            onExtension: param.onExtension,
            onStudyInfo: param.onStudyInfo,
            updateMemberCallback: param.updateMemberCallback
        }
        this.init();
        this.currentDate = new Date();
    }

    init() {

        let {baseDiv, contentDiv, actionsDiv} = this.ui;
        let {onExit, onExtension, updateMemberCallback} = this.callback;
        let self = this;
        baseDiv.css("width", "50%");

        contentDiv.append($("#allInfoDiv"));
        $("#exitSeatBtn").click(function () {
            onExit();
            self.resetSeatInfo();
        });
        $("#expansionBtn").click(onExtension);


        let updateMemberModal = new UpdateMemberModal({
            title: "내 정보 수정",
            updateMemberCallback: updateMemberCallback
        });

        $("#updateMemberBtn").click(function () {
            updateMemberModal.show();
        })


        this.event({
            onHidden: function () {
            }
        });
    }

    resetSeatInfo() {
        $("#seatNumberView").text("-");
        $("#expansionView").text("-");
        $("#startDtView").text("-");
        $("#endDtView").text("-");
    }

    updateInfo(allInfo) {
        let {member = {}, seatUsage, studyInfo = {}} = allInfo;

        $("#nameView").text(member.name);
        $("#joinDtView").text(member.joinDtText);
        $("#emailView").text(member.email);
        $("#phoneView").text(member.phone);

        if (seatUsage !== null) {
            $("#seatNumberView").text(`(${seatUsage.roomName}) ${seatUsage.number}번 좌석`);
            $("#expansionView").text(seatUsage.expansion);
            $("#startDtView").text(seatUsage.startDtText);
            $("#endDtView").text(seatUsage.endDtText);
        } else {
            this.resetSeatInfo();
        }

        // let fieldColor = ["white", "#B7F0B1", "#86E57F", "#47C83E", "#2F9D27"];


    }


    drawStudyInfo(studyInfo) {
        let studyDays = studyInfo.studyDays;
        let studies = {};
        let totalMinutes = 0;
        let totalHour = 0;
        let self = this;
        if (studyDays !== null) {
            for (let studyDay of studyDays) {
                let day = studyDay.day;
                studies[day.substring(day.length - 2, day.length)] = parseInt(studyDay.studyMinutes / 60);
                totalMinutes += studyDay.studyMinutes;

            }

            totalHour = parseInt(totalMinutes / 60);
        }

        let studyField = $("#studyField");
        studyField.empty();

        let currentDate = this.currentDate;
        let last = new Date(2021, currentDate.getMonth() + 1, 0).getDate();

        $("#dateView").text(`${currentDate.getFullYear()}년 ${currentDate.getMonth() + 1}월`);

        for (let index = 0; index < last; index++) {
            let studyHour = studies[index + 1];
            let backgroundColor = "white";

            if (studyHour !== undefined) {
                if (studyHour >= 0 && studyHour < 2) {
                    backgroundColor = "#B7F0B1";
                } else if (studyHour >= 2 && studyHour < 4) {
                    backgroundColor = "#86E57F";
                } else if (studyHour >= 4 && studyHour < 6) {
                    backgroundColor = "#47C83E";
                } else if (studyHour >= 6 && studyHour < 8) {
                    backgroundColor = "#2F9D27";
                } else if (studyHour >= 8) {
                    backgroundColor = "#22741C";
                }
            }
            studyField.append($("<div>", {
                css: {
                    border: "lightgray solid 1px",
                    "background-color": backgroundColor,
                    "border-radius": "5px"
                }
            }));
        }

        let targetStudyHour = studyInfo.targetStudyHour;

        if (targetStudyHour != null) {
            $('#studyGauge')
                .progress({
                    label: 'ratio',
                    value: totalHour,
                    total: targetStudyHour,
                    text: {
                        ratio: '{value} / {total}'
                    }
                })
            ;
        } else {
            $('#studyGauge')
                .progress({
                    label: 'ratio',
                    value: 0,
                    total: 0,
                    text: {
                        ratio: '{value} / {total}'
                    }
                })
        }

    }

    updateStudyInfo(studyInfo) {
        this.drawStudyInfo(studyInfo);
        let self = this;

        $("#leftMonthBtn").unbind('click');
        $("#rightMonthBtn").unbind('click');


        $("#leftMonthBtn").click(function () {
            let currentDate = self.currentDate;

            currentDate.setMonth(currentDate.getMonth() - 1);
            self.callback.onStudyInfo(self.dateConvertString(currentDate), function (studyInfo) {
                self.drawStudyInfo(studyInfo);
            });
        });
        $("#rightMonthBtn").click(function () {
            let currentDate = self.currentDate;
            currentDate.setMonth(self.currentDate.getMonth() + 1);
            self.callback.onStudyInfo(self.dateConvertString(currentDate), function (studyInfo) {
                self.drawStudyInfo(studyInfo);
            });
        });

    }

    dateConvertString(date) {
        let month = date.getMonth() + 1;
        return `${date.getFullYear()}-${month > 9 ? month : '0' + month}-01`
    }

    showModal(allInfo) {
        this.currentDate = new Date();
        this.updateInfo(allInfo);
        let self = this;

        this.callback.onStudyInfo(this.dateConvertString(new Date()), function (studyInfo) {
            self.updateStudyInfo(studyInfo);
        });

        this.show();
    }
}
