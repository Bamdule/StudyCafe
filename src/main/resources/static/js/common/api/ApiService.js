class ApiService {

    constructor() {
        this.contextPath = $("#contextPath").val();
    }

    getStudyCafe() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/studycafe`,
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
                url: `${$("#contextPath").val()}api/studycafe/room/${roomId}`,
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

    loginMember(phone, password) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/member/login`,
                type: "post",
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded",
                data: {phone, password},
                success: function (data) {
                    showToast("로그인이 완료되었습니다.");
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    signUpMember(member) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/member`,
                type: "post",
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(member),
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    requestEmailCode(email) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/member/email`,
                type: "post",
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded",
                data: {email},
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    extensionTimeSeat() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/studycafe/seat`,
                type: "put",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("authorization", sessionStorage.getItem("scToken"));
                },
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    exitSeat() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/studycafe/seat`,
                type: "delete",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("authorization", sessionStorage.getItem("scToken"));
                },
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    useSeat(roomId, seatId) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/studycafe/room/${roomId}/seat/${seatId}`,
                type: "post",
                dataType: 'json',
                contentType: "application/json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("authorization", sessionStorage.getItem("scToken"));
                },
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    getMySeat() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/studycafe/myseat`,
                type: "get",
                dataType: 'json',
                contentType: "application/json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("authorization", sessionStorage.getItem("scToken"));
                },
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    getAllInfo() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/studycafe/allInfo`,
                type: "get",
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded",
                data: {studyDate: "2021-07-01"},
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("authorization", sessionStorage.getItem("scToken"));
                },
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    studyInfo(studyDate) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/studycafe/studyInfo`,
                type: "get",
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded",
                data: {studyDate},
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("authorization", sessionStorage.getItem("scToken"));
                },
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    updateMember(member) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/member`,
                type: "put",
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded",
                data: {password: member.password, targetStudyHour: member.targetStudyHour},
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("authorization", sessionStorage.getItem("scToken"));
                },
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    saveReservation() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/studycafe/seat/reservation`,
                type: "post",
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("authorization", sessionStorage.getItem("scToken"));
                },
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    getSeatAvailability(roomId) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/studycafe/seat/availability?roomId=${roomId}`,
                type: "get",
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }

    getReservation() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: `${$("#contextPath").val()}api/studycafe/seat/reservation`,
                type: "get",
                dataType: 'json',
                success: function (data) {
                    resolve(data);
                },
                error: function (data) {
                    reject(data);
                }
            });
        });
    }
}