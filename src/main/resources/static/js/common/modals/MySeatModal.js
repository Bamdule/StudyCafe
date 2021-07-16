class MySeatModal extends BaseModal {
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
        baseDiv.css("width", "70%");

        let table = $("<table>", {class: "ui celled table"});
        let thead = $("<thead>");
        let thead_tr = $("<tr>");

        thead_tr.append($("<th>", {text: "사용 좌석 번호"}));
        thead_tr.append($("<th>", {text: "이름"}));
        thead_tr.append($("<th>", {text: "시작 시간"}));
        thead_tr.append($("<th>", {text: "종료 시간"}));
        thead.append(thead_tr);

        let tbody = $("<tbody>");
        let tbody_tr = $("<tr>");
        let seatnumber_td = $("<td>");
        let name_td = $("<td>");
        let startdt_td = $("<td>");
        let enddt_td = $("<td>");

        tbody_tr.append(seatnumber_td);
        tbody_tr.append(name_td);
        tbody_tr.append(startdt_td);
        tbody_tr.append(enddt_td);
        table.append(tbody_tr);

        table.append(thead);
        table.append(tbody);
        contentDiv.append(table);

        actionsDiv.append($("<button>", {text: "퇴실", class: "ui gray button", click: onExit}));
        actionsDiv.append($("<button>", {text: "시간 연장", class: "ui gray button", click: onExtension}));

        this.updateInfo = function (seatUsage) {
            updateInfo(seatUsage);
        }

        function updateInfo(seatUsage) {
            let {number = "", memberName = "", startDt = "", endDt = ""} = seatUsage;
            seatnumber_td.text(number);
            name_td.text(memberName);
            startdt_td.text(startDt);
            enddt_td.text(endDt);
        }

        this.event({
            onHidden: function () {
                updateInfo({});
            }
        });
    }

    showModal(seatUsage) {
        this.updateInfo(seatUsage);
        this.show();
    }


}
