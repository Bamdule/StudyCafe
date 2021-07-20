$(document).ready(function () {
});

function showToast(message) {
    $('body')
        .toast({
            message: message,
            class: 'purple',
            className: {
                toast: 'ui message'
            }
        });
}

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