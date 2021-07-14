class SeatService {

    constructor() {
    }

    getRooms() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `/api/studycafe/room`,
                type: "get",
                dataType: 'json',
                contentType: "application/json",
                data: {},
                success: function (data) {
                    resolve(data);
                },
                error: function () {
                    reject('failed');
                }
            });
        });
    }

    getSeats(roomId) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `/api/studycafe/room/${roomId}`,
                type: "get",
                dataType: 'json',
                contentType: "application/json",
                data: {},
                success: function (data) {
                    resolve(data);
                },
                error: function () {
                    reject('failed');
                }
            });
        });
    }

}