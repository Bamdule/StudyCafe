class SeatManager {
    constructor(config) {
        this.config = config;
        this.seats = {};

        this.seatDivs = {};

        this.init();
        this.status = {
            "EMPTY": {
                backgroundColor: "#FFDEA9"
            },
            "USED": {
                backgroundColor: "#FFDEA9"
            },
            "BLANK": {
                backgroundColor: "#eeeeee"
            },
            "UNUSED": {
                backgroundColor: "#eeeeee"
            }
        };
    }

    init() {
    }

    createSeats(room, seats) {
        if (seats === undefined) {
            seats = [];
        }
        if (seats.length < 1) {
            alert('좌석이 존재하지 않습니다.');
            return;
        }

        let {width = 60, height = 60, row = room.width, column = room.height, seatBaseDiv} = this.config;


        seatBaseDiv.css({
            "display": "grid",
            "grid-template-columns": `repeat(${column}, ${width}px)`,
            "grid-template-rows": `repeat(${row}, ${height}px)`,
            "gap": "5px 5px"
        });

        let seatDivs = {};
        for (let seat of seats) {
            console.log(seat);
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
        let {number, memberName, memberId} = seat;
        let self = this;

        let css = {
            "border": "gray solid 1px",
            "backgroundColor": "#ffffff",
            cursor: "pointer",
            "user-select": "none",
            "display": "flex",
            "justify-content": "center",
            "align-items": "center",
            "text-align": "center"
        };
        let seatDiv = $("<div>", {text: number, css: css});

        seatDiv.click(function () {
            console.log(number, self.seats[number]);

        });

        this.seats[number] = {
            seat,
            seatDiv
        };

        return seatDiv;

    }
}