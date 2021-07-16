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
    showToast(request.responseJSON.message)
});