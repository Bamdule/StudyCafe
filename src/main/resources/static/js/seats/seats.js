$(document).ready(function () {
    console.log("hi");


    let jwtUtils = new JwtUtils();
    let apiService = new ApiService();

    apiService.getRooms()
        .then(function (rooms) {
            console.log(rooms);
            let room = rooms[0];
            apiService.getSeats(room.id)
                .then(function (seats) {
                    seatManager.createSeats(room, seats);
                    websocket.connect();
                });
        })
        .then();


    let seatManager = new SeatManager({
        seatBaseDiv: $("#seatBaseDiv"),
        seatClick: function (seatInfo) {
            let {seat, seatDiv} = seatInfo;

            if (sessionStorage.getItem("scToken") !== null) {
                if (seat.status === "UNUSED") {
                    apiService.useSeat(seat.id).then(function () {
                        showToast(`${seat.number} 좌석을 선택하셨습니다.`);
                    });

                }
            }
        }
    });

    let websocket = new Websocket({
        serverUri: "http://localhost:8080/websocket",
        onMessage: function (message) {
            let data = JSON.parse(message.data);
            seatManager.updateSeat(data);
            console.log(data);
        }
    });


    let loginModal = new LoginModal({
        title: "로그인",
        loginCallback: function (phone, password) {
            apiService.loginMember(phone, password)
                .then(function (data) {
                    sessionStorage.setItem("scToken", data.token);
                    updateMemberUI();
                    loginModal.close();
                    logoutTimer();
                });
        }
    });
    let mySeatModel = new MySeatModal({
        title: "내 좌석 정보",
        onExit: function () {

            apiService.exitSeat().then(function () {
                mySeatModel.close();
            });

        },
        onExtension: function () {
            apiService.extensionTimeSeat().then(function () {
                mySeatModel.close();
            });
        }
    });


    let loginBtn = $("#loginBtn");
    let mySeatBtn = $("#mySeatBtn");
    let logoutBtn = $("#logoutBtn");

    loginBtn.click(function () {
        loginModal.show();
    });

    mySeatBtn.click(function () {
        let expiration = jwtUtils.checkExpiration(sessionStorage.getItem("scToken"));
        if (!expiration) {
            apiService.getMySeat()
                .then(function (data) {
                    console.log(data);
                    mySeatModel.showModal(data);
                })
        }
    });
    logoutBtn.click(function () {
        logout();
    });

    function logout() {
        sessionStorage.removeItem("scToken");
        updateNonMemberUI();
        showToast("로그아웃이 완료되었습니다.")
    }


    function updateMemberUI() {
        loginBtn.css("display", "none");
        mySeatBtn.css("display", "block");
        logoutBtn.css("display", "block");
    }

    function updateNonMemberUI() {
        loginBtn.css("display", "block");
        mySeatBtn.css("display", "none");
        logoutBtn.css("display", "none");
    }


    function logoutTimer() {
        let loginInterval = setInterval(() => {
            let scToken = sessionStorage.getItem("scToken");
            if (scToken !== null) {
                if (jwtUtils.checkExpiration(scToken)) {
                    logout();
                    clearInterval(loginInterval);
                }
            }
        }, 60000);
    }

    if (sessionStorage.getItem("scToken") !== null) {
        updateMemberUI();
        logoutTimer();

    } else {
        updateNonMemberUI();
    }
});
