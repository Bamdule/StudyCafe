class SeatManager {
    constructor(config) {
        this.config = config;
        this.seats = {};

        this.init();
        this.status = {

            "USED": {
                backgroundColor: "beige",
                code: "USED"
            },
            "UNUSED": {
                backgroundColor: "white",
                code: "UNUSED"
            },
            "LIMIT": {
                backgroundColor: "lightgray",
                code: "LIMIT"
            },
        };
        this.type = {
            EXPIRED_SEAT: "EXPIRED_SEAT", EXIT_SEAT: "EXIT_SEAT", USE_SEAT: "USE_SEAT"
        }
    }

    init() {
    }

    createSeats(room, seats) {
        let {row = room.height, column = room.width, seatBaseDiv} = this.config;
        seatBaseDiv.empty();

        if (seats === undefined) {
            seats = [];
        }
        if (seats.length < 1) {
            alert('좌석이 존재하지 않습니다.');
            return;
        }


        seatBaseDiv.css({
            "display": "grid",
            "grid-template-columns": `repeat(${column}, 50px)`,
            "grid-template-rows": `repeat(${row}, 50px)`,
            "gap": "5px 5px"
        });

        let seatDivs = {};
        for (let seat of seats) {
            seatDivs[`${seat.row}${seat.col}`] = this.createSeat(seat);
        }

        for (let w = 0; w < row; w++) {
            for (let h = 0; h < column; h++) {

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
            "backgroundColor": this.status[seat.status].backgroundColor,
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
}