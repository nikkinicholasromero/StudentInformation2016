$(document).on('ready', function () {
    $('.close-notification-modal').on('click', function() {
        $('.notification-modal').removeClass('notification-modal-show');
    });
});

function showNotificationModal(message) {
    $('.notification-modal p').html(message);
    $('.notification-modal').addClass('notification-modal-show');
    setTimeout(function() {
        $('.notification-modal').removeClass('notification-modal-show');
    }, 3000);
}

function clearFormWarnings() {
    $('#modal-form .form-group').each(function() {
        $(this).find('.help-block').css('display', 'none');
        $(this).removeClass('has-error');
    });
}