$(document).ready(function () {
    console.log("hi");


    let seatService = new SeatService();
    let seatManager = new SeatManager({
        seatBaseDiv: $("#seatBaseDiv")
    })

    seatService.getRooms()
        .then(function (rooms) {
            console.log(rooms);
            let room = rooms[0];
            seatService.getSeats(room.id)
                .then(function (seats) {
                    seatManager.createSeats(room, seats);
                });
        })
        .then();

});
