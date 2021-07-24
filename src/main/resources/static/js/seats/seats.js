$(document).ready(function () {
    console.log("hi");

    let roomSelect = $('#roomSelect');


    let jwtUtils = new JwtUtils();
    let apiService = new ApiService();
    let currentRoom = null;
    let roomInfo = {};

    apiService.getStudyCafe()
        .then(function (cafe) {

            let rooms = cafe.rooms;
            let selectValues = [];
            for (let room of rooms) {
                roomInfo[room.id] = room;

                selectValues.push({
                    name: room.name,
                    value: room.id
                })
            }
            selectValues[0].selected = true;

            initRoomSelect(selectValues);
            updateSeatsByRoomId(rooms[0]);
            apiService.getReservation().then(function (res) {
                updateReservationInfo(res);
            });
            websocket.connect();
        })
        .then();

    function initRoomSelect(values) {
        roomSelect.dropdown({
            onChange: function (value, text, $selectedItem) {
                updateSeatsByRoomId(roomInfo[value]);
            },
            values: values
        });
    }

    function updateSeatsByRoomId(room) {
        currentRoom = room;
        apiService.getSeats(room.id)
            .then(function (seats) {
                seatManager.createSeats(room, seats);
            });

        updateSeatAvailability(currentRoom.id);
    }


    let seatManager = new SeatManager({
        seatBaseDiv: $("#seatBaseDiv"),
        seatClick: function (seatInfo) {
            let {seat, seatDiv} = seatInfo;

            if (sessionStorage.getItem("scToken") !== null) {
                if (seat.status === "UNUSED") {
                    confirmModal.show({
                        message: "정말 좌석을 선택하시겠습니까?",
                        onApprove: function () {
                            apiService.useSeat(currentRoom.id, seat.id).then(function () {
                                showToast(`${seat.number} 좌석을 선택하셨습니다.`);
                            });
                        }
                    });
                }
            }
        }
    });

    let websocket = new Websocket({
        serverUri: "/websocket",
        onMessage: function (result) {
            console.log(result);
            let data = JSON.parse(result.data);
            switch (data.type) {
                case "EXPIRED_SEAT":
                case "EXIT_SEAT":
                case "USE_SEAT":
                    seatManager.updateSeat(data);
                    updateSeatAvailability(currentRoom.id);
                    break;
                case "SAVE_RESERVATION":
                case "DELETE_RESERVATION":
                    updateReservationInfo(data.message);
                    break;
            }

        }
    });

    let usedSeats = $("#usedSeats");
    let unusedSeats = $("#unusedSeats");
    let limitedSeats = $("#limitedSeats");

    function updateSeatAvailability(roomId) {
        apiService.getSeatAvailability(roomId)
            .then(function (data) {
                usedSeats.text(data.usedSeats);
                unusedSeats.text(data.unusedSeats);
                limitedSeats.text(data.limitedSeats);
            });
    }

    let reservationCountText = $("#reservationCountText");

    function updateReservationInfo(reservationCount) {
        reservationCountText.text(reservationCount);
    }

    let confirmModal = new ConfirmModal({
        title: "알림",
        onApprove: function () {
        },
        onDeny: function () {

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
                showToast("퇴실이 완료되었습니다.");

                mySeatModel.close();
            });

        },
        onExtension: function () {
            apiService.extensionTimeSeat().then(function () {
                showToast("시간 연장이 완료되었습니다..");
                mySeatModel.close();
            });
        }
    });

    let signUpModal = new SignUpModal({
        title: "회원 가입",
        signUpCallback: function (member) {
            apiService.signUpMember(member)
                .then(function (data) {
                    console.log(data);
                    showToast("회원가입이 완료되었습니다.");
                    signUpModal.close();
                });
        },
        requestEmailCodeCallback: function (email) {
            showToast("이메일 인증 코드 발송이 요청되었습니다. <br/>조금만 기다려주세요.");
            apiService.requestEmailCode(email)
                .then(function () {
                    showToast("이메일 인증 코드 발송이 완료되었습니다.");
                })
        }
    });

    let loginBtn = $("#loginBtn");
    let signUpBtn = $("#signUpBtn");
    let mySeatBtn = $("#mySeatBtn");
    let logoutBtn = $("#logoutBtn");
    let reservationBtn = $("#reservationBtn");

    reservationBtn.click(function () {
        apiService
            .saveReservation()
            .then(function () {
                showToast("예약이 완료되었습니다.");
            });

    });

    signUpBtn.click(function () {
        signUpModal.show();
    });

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
        } else {
            logout();
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
        signUpBtn.css("display", "none");

        mySeatBtn.css("display", "flex");
        logoutBtn.css("display", "flex");
        reservationBtn.css("display", "flex");
    }

    function updateNonMemberUI() {
        loginBtn.css("display", "flex");
        signUpBtn.css("display", "flex");

        mySeatBtn.css("display", "none");
        logoutBtn.css("display", "none");
        reservationBtn.css("display", "none");
    }


    function logoutTimer() {
        let loginInterval = setInterval(() => {
            let scToken = sessionStorage.getItem("scToken");
            if (scToken !== null) {
                if (jwtUtils.checkExpiration(scToken)) {
                    logout();
                    clearInterval(loginInterval);
                }
            } else {
                clearInterval(loginInterval);
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
