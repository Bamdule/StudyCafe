class AllInfoModal extends BaseModal {
    constructor(param) {
        super(param);
        this.callback = {
            onExit: param.onExit,
            onExtension: param.onExtension
        }
        this.init();
    }

    init() {
        let {baseDiv, contentDiv, actionsDiv} = this.ui;
        let {onExit, onExtension} = this.callback;
        let self = this;
        baseDiv.css("width", "50%");

        contentDiv.append($("#allInfoDiv"));
        $("#exitSeatBtn").click(function () {
            onExit();
            self.resetSeatInfo();
        });
        $("#expansionBtn").click(onExtension);


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
            $("#seatNumberView").text(seatUsage.number);
            $("#expansionView").text(seatUsage.expansion);
            $("#startDtView").text(seatUsage.startDtText);
            $("#endDtView").text(seatUsage.endDtText);
        } else {
            this.resetSeatInfo();
        }

        let fieldColor = ["white", "#B7F0B1", "#86E57F", "#47C83E", "#2F9D27"];

        let studyDays = studyInfo.studyDays;
        let studies = {};
        let totalMinutes = 0;
        let totalHour = 0;
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

        let last = new Date(2021, 7, 0).getDate();


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

        if (member.targetStudyHour != undefined) {
            $('#example6')
                .progress({
                    label: 'ratio',
                    value: totalHour,
                    total: member.targetStudyHour,
                    text: {
                        ratio: '{value} / {total}'
                    }
                })
            ;
        }


    }

    showModal(allInfo) {
        this.updateInfo(allInfo);
        this.show();
    }
}
