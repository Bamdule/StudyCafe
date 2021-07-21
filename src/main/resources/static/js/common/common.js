$(document).ready(function () {
});

function showToast(message) {
    $('body')
        .toast({
            message: message,
            class: 'purple',
            className: {
                toast: 'ui message'
            },
            position : "top left"
        });
}

$(document).ajaxSend(function (e, xhr, options) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    xhr.setRequestHeader(header, token);
});

$(document).ajaxError(function (event, request, settings) {
    console.log(request);
    try {
        let {message, errors} = request.responseJSON;
        if (errors !== undefined) {
            for (let error of errors) {
                showToast(error.message)
            }
        } else {
            showToast(message);
        }
    } catch (e) {
        console.log(e);
    }
});