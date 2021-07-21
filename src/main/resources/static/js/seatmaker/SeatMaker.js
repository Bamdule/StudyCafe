class SeatMaker {
    constructor(config) {
        this.config = config;
        this.seats = {};
        this.currentNumber = 1;
        this.seatNumbers = {};

        this.init();

        this.type = {
            LIMIT: "LIMIT", SEAT: "SEAT", BLANK: "BLANK"
        }
    }

    seat = {
        id: "",
        number: "",
        roomId: "",
        row: "",
        column: "",
        active: ""
    }

    init(param) {
        let {seats = [], width = 0, height = 0} = param;

        if (seats.length > 0) {

        } else {

        }
    }

    createSeats(room, seats) {
        if (seats === undefined) {
            seats = [];
        }
        if (seats.length < 1) {
            alert('좌석이 존재하지 않습니다.');
            return;
        }

        let {width = 50, height = 50, row = room.width, column = room.height, seatBaseDiv} = this.config;


        seatBaseDiv.css({
            "display": "grid",
            "grid-template-columns": `repeat(${column}, ${width}px)`,
            "grid-template-rows": `repeat(${row}, ${height}px)`,
            "gap": "5px 5px"
        });

        let seatDivs = {};
        for (let seat of seats) {
            seatDivs[`${seat.row}${seat.col}`] = this.createSeat(seat);
        }

        for (let w = 0; w < room.width; w++) {
            for (let h = 0; h < room.height; h++) {

                let seatDiv = seatDivs[`${w}${h}`];

                if (seatDiv === null || seatDiv === undefined) {
                    seatBaseDiv.append(this.createBlank());
                } else {
                    seatBaseDiv.append(seatDiv)
                }
            }
        }

        this.seatBaseDiv = seatBaseDiv;
    }

    createBlank() {
        let seatDiv = $("<div>", {});

        return seatDiv;
    }

    createSeat(seat) {
        let {id, number, memberName, memberId} = seat;
        let self = this;

        let css = {
            "border": "gray solid 1px",
            // "backgroundColor": this.status[seat.status].backgroundColor,
            cursor: "pointer",
            "user-select": "none",
            "display": "flex",
            "justify-content": "center",
            "align-items": "center",
            "text-align": "center"
        };
        let seatDiv = $("<div>", {text: number, css: css});

        seatDiv.click(function () {
            self.config.seatClick(self.seats[id])
        });

        this.seats[id] = {
            seat,
            seatDiv
        };

        return seatDiv;
    }

    updateSeatInfo(seatId, status) {
        let seatInfo = this.seats[seatId];
        let {seat, seatDiv} = seatInfo;
        seat.status = status.code;
        seatDiv.css("background-color", status.backgroundColor);
    }

    updateSeat(data) {
        let {type, message} = data;

        switch (type) {
            case this.type.EXPIRED_SEAT:
                for (let seat of message) {
                    this.updateSeatInfo(seat.seatId, this.status.UNUSED);
                }
                break;
            case this.type.USE_SEAT:
                this.updateSeatInfo(message.seatId, this.status.USED);
                break;
            case this.type.EXIT_SEAT:
                this.updateSeatInfo(message.seatId, this.status.UNUSED);
                break;
        }
    }

    getNextNumber() {
        for (let number in this.seatNumbers) {
            if (this.seatNumbers[number] === 0) {
                return number;
            }
        }
        this.seatNumbers[this.currentNumber] = 1;
        return this.currentNumber++;
    }
}